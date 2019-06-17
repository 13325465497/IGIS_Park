package com.isoft.igis.park.service.impl;

import com.isoft.igis.common.config.IGISParkConfig;
import com.isoft.igis.common.utils.DeleteDirUtils;
import com.isoft.igis.common.utils.HanyuPinyinHelper;
import com.isoft.igis.common.utils.R;
import com.isoft.igis.common.utils.ZipUtils;
import com.isoft.igis.edit.service.UserPointService;
import com.isoft.igis.park.dao.FloorDao;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class FloorServiceImpl implements FloorService {
    @Autowired
    private FloorDao floorDao;
    @Autowired
    IGISParkConfig igisConfig;
    @Autowired
    private ParkService parkService;
    @Autowired
    private BuildingService buildingService;
    @Autowired
    private UserPointService userPointService;
    private HanyuPinyinHelper hanyuPinyinHelper = new HanyuPinyinHelper();

    @Override
    public FloorDO get(Long floorid) {
        return floorDao.get(floorid);
    }

    @Override
    public List<FloorDO> list(Map<String, Object> map) {
        return floorDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return floorDao.count(map);
    }

    @Override
    @Transactional
    public R save(FloorDO floor) {
        BuildingDO building = buildingService.get(floor.getBuildid());
        floor.setFloordir(building.getBuilddir() + hanyuPinyinHelper.toHanyuPinyin(floor.getFloorname()) + "/");
        File file = new File(floor.getFloordir());
        if (!file.mkdirs()) {
            return R.error();
        }
        floor.setFloorDataStatus(1);//首次创建没数据
        floor.setNavigationDataStatus(1);
        Double random = (Math.random() * 9 + 1) * 10000;
        floor.setFloorid(random.longValue());
        floor.setFloorfilename(hanyuPinyinHelper.toHanyuPinyin(floor.getFloorname()));//楼层文件名
        if (floorDao.save(floor) > 0) {
            return R.ok();
        }
        return R.error();
    }

    @Override
    public int update(FloorDO floor) {
        return floorDao.update(floor);
    }

    @Override
    @Transactional
    public boolean remove(Long floorid) {
        FloorDO floor = this.get(floorid);
        BuildingDO buildingDO = buildingService.get(floor.getBuildid());
        ParkDO parkDO = parkService.get(buildingDO.getParkid());
        File file = new File(floor.getFloordir());
        if (file.exists()) {
            DeleteDirUtils.deleteDir(file);
            Map<String,Object>params=new HashMap<>();
            params.put("parkName",parkDO.getParkfilename());
            params.put("buildName",buildingDO.getBuildfilename());
            params.put("floorName",floor.getFloorfilename());
            userPointService.deleteByParkNameAndBuildNameANdFloorName(params);//删除楼层新增poi点
            if (floorDao.remove(floorid) > 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int batchRemove(Long[] floorids) {
        return floorDao.batchRemove(floorids);
    }

    @Override
    public R indoorAdd(Long floorid, MultipartFile[] files) {
        FloorDO floorDO = this.get(floorid);
        String[] rename = {"poi", "region", "subregion", "floor", "link"};//数据名称
        try {
            if (files != null) {
                Map<String, Object> param = new HashMap<>();
                for (int i = 0; i < files.length; i++) {
                    if (files[i] != null && files[i].getOriginalFilename().trim().length() > 0) {
                        String end1Name = files[i].getOriginalFilename();
                        end1Name = end1Name.substring(end1Name.lastIndexOf(".") + 1).trim().toLowerCase();
                        //临时zip位置
                        String ZipPath = igisConfig.getUploadPath() + System.currentTimeMillis() + "." + end1Name;
                        files[i].transferTo(new File(ZipPath));
                        //解压到楼层目录位置
                        String parkDirPath = floorDO.getFloordir();
                        // ZipPath 临时zip存放目录 , parkDirPath 楼层存放目录
                        boolean flag = ZipUtils.JieYa(ZipPath, parkDirPath, rename[i]);
                        if (!flag) {
                            return R.error("上传园区数据失败");
                        }
                        if (i == 4) {//是否上传道路数据
                            if (files[4] != null && files[4].getOriginalFilename().trim().length() > 0) {
                                param.put("NavigationDataStatus", 0);
                            }
                        }
                    }
                }
                param.put("floorDataStatus", 0);//表示已上传必须数据
                param.put("floorid", floorid);
                floorDao.setFloorDataStatus(param);
                return R.ok();
            }
        } catch (Exception e) {
            return R.error("上传室内数据失败");
        }
        return R.error();
    }

    @Override
    public String getFileName(Long floorid) {
        return floorDao.getFileName(floorid);
    }

    @Override
    public String findFloorLableByFloorFileName(String floorFileName, Long buildId) {
        Map<String, Object> map = new HashMap<>();
        map.put("floorFileName", floorFileName);
        map.put("buildId", buildId);
        return floorDao.findFloorLableByFloorFileName(map);
    }

}
