package com.isoft.igis.park.service.impl;

import com.isoft.igis.common.utils.DeleteDirUtils;
import com.isoft.igis.common.utils.HanyuPinyinHelper;
import com.isoft.igis.common.utils.R;
import com.isoft.igis.park.dao.BuildingDao;
import com.isoft.igis.park.domain.BuildingDO;
import com.isoft.igis.park.domain.FloorDO;
import com.isoft.igis.park.domain.ParkDO;
import com.isoft.igis.park.service.BuildingService;
import com.isoft.igis.park.service.FloorService;
import com.isoft.igis.park.service.ParkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class BuildingServiceImpl implements BuildingService {
    @Autowired
    private BuildingDao buildingDao;
    @Autowired
    private ParkService parkService;

    @Override
    public BuildingDO get(Long buildid) {
        return buildingDao.get(buildid);
    }

    @Override
    public List<BuildingDO> list(Map<String, Object> map) {
        return buildingDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return buildingDao.count(map);
    }

    @Override
    public R save(BuildingDO building) {
        ParkDO park = parkService.get(building.getParkid());
        HanyuPinyinHelper hanyuPinyinHelper = new HanyuPinyinHelper();
        building.setBuilddir(park.getParkdir() + "indoor/" + hanyuPinyinHelper.toHanyuPinyin(building.getBuildname()) + "/");
        System.out.println(building.getBuilddir());
        File file = new File(building.getBuilddir());
        if (file.exists()) {
            return R.error("园区建筑已经存在！");
        } else if (!file.mkdirs()) {
            return R.error();
        }
        building.setBuildfilename(hanyuPinyinHelper.toHanyuPinyin(building.getBuildname()));//楼栋文件名
        Double random = (Math.random() * 9 + 1) * 10000;
        building.setBuildid(random.longValue());
        if (buildingDao.save(building) > 0) {
            return R.ok();
        }
        return R.error();
    }

    @Override
    public int update(BuildingDO building) {
        return buildingDao.update(building);
    }

    @Autowired
    private FloorService floorService;

    @Override
    @Transactional
    public boolean remove(Long buildid) {
        BuildingDO buildingDO = this.get(buildid);
        File file = new File(buildingDO.getBuilddir());//楼栋数据
        if (file.exists()) {
            Map<String, Object> map = new HashMap<>();
            map.put("buildid", buildingDO.getBuildid());
            // 查询该楼栋 - 所有楼层
            List<FloorDO> floorDOS = floorService.list(map);
            if (floorDOS != null) {
                for (FloorDO floorDO : floorDOS) {
                    floorService.remove(floorDO.getFloorid());//删除该楼栋的楼层
                }
            }
            DeleteDirUtils.deleteDir(file);
            if (buildingDao.remove(buildid) > 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int batchRemove(Long[] buildids) {
        return buildingDao.batchRemove(buildids);
    }

    @Override
    public String getFileName(Long buildid) {
        return buildingDao.getFileName(buildid);
    }

    @Override
    public String findBuildLableByBuildFileName(String buildFileName, Long parkId) {
        Map<String, Object> map = new HashMap();
        map.put("buildFileName", buildFileName);
        map.put("parkId", parkId);
        return buildingDao.findBuildLableByBuildFileName(map);
    }

    @Override
    public Long findBuildIdByBuildFIleName(String buildFileName, Long parkId) {
        Map<String, Object> map = new HashMap<>();
        map.put("buildFileName", buildFileName);
        map.put("parkId", parkId);
        return buildingDao.findBuildIdByBuildFIleName(map);
    }

}
