package com.isoft.igis.park.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.isoft.igis.common.config.IGISParkConfig;
import com.isoft.igis.common.utils.FileUtil;
import com.isoft.igis.common.utils.R;
import com.isoft.igis.park.dao.ParkServiceDao;
import com.isoft.igis.park.domain.*;
import com.isoft.igis.park.service.BuildingService;
import com.isoft.igis.park.service.FloorService;
import com.isoft.igis.park.service.ParkService;
import com.isoft.igis.park.service.ParkServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ParkServiceServiceImpl implements ParkServiceService {
    @Autowired
    private ParkServiceDao parkServiceDao;
    @Autowired
    private ParkService parkService;
    @Autowired
    private BuildingService buildingService;
    @Autowired
    private FloorService floorService;
    @Autowired
    IGISParkConfig igisConfig;

    @Override
    public ParkServiceDO get(Long id) {
        return parkServiceDao.get(id);
    }

    @Override
    public List<ParkServiceDO> list(Map<String, Object> map) {
        return parkServiceDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return parkServiceDao.count(map);
    }

    @Override
    public int save(ParkServiceDO parkService) {
        return parkServiceDao.save(parkService);
    }

    @Override
    public R update(ParkServiceDO parkServiceDO) {
        String parkServiceStr = parkServiceDO.getIndoorservice();
        if (parkServiceStr != null && parkServiceStr.trim().length() > 0) {
            JSONObject indoorObject = JSON.parseObject(parkServiceStr);//室内json对象
            String parkName = (String) indoorObject.get("parkName");//获取园区名称
            parkName = parkName.substring(0, parkName.indexOf("_"));
            JSONArray buildInfoArrayJson = indoorObject.getJSONArray("buildinfo");//获取室内list
            List<BuildsInfo> buildsInfos = JSON.parseObject(buildInfoArrayJson.toString(), new TypeReference<List<BuildsInfo>>() {
            });
            for (BuildsInfo buildsInfo : buildsInfos) {
                buildsInfo.setBuildId(buildsInfo.getBuildName());
                Long parkId = parkService.findParkIdByParkFileName(parkName);
                //根据楼栋文件名及园区id查询楼栋标签
                buildsInfo.setBuildName(buildingService.findBuildLableByBuildFileName(buildsInfo.getBuildName(), parkId));
                List<String> floorName = buildsInfo.getFloorName();
                List<String> floorIdList = new ArrayList<>(floorName.size());
                List<String> floorNameList = new ArrayList<>(floorName.size());
                for (String floorname : floorName) {
                    floorIdList.add(floorname);
                    Long buildId = buildingService.findBuildIdByBuildFIleName(buildsInfo.getBuildId(), parkId);
                    //根据楼层文件名及楼栋id查询楼栋标签
                    String floorLableByFloorFileName = floorService.findFloorLableByFloorFileName(floorname, buildId);
                    floorNameList.add(floorLableByFloorFileName);
                }
                buildsInfo.setFloorId(floorIdList);
                buildsInfo.setFloorName(floorNameList);
            }
            indoorObject.put("buildinfo", buildsInfos);
            parkServiceDO.setIndoorservice(indoorObject.toJSONString());
        }
        parkServiceDao.update(parkServiceDO);
        String parkDir = igisConfig.getParkPath() + parkServiceDO.getParkname() + "/" + "outdoor/wms/";
        File originalMapFile = new File(parkDir + "original.map");
        if (originalMapFile.exists()) {     //查询是否有原始.map文件
            File file = new File(parkDir);
            File[] files = file.listFiles();
            String serviceMapName = null;
            for (File f : files) {
                String fileName = f.getName();
                if (fileName.endsWith(".map") && !fileName.equals("original.map")) {
                    serviceMapName = f.getAbsolutePath();//获取生成服务的.map文件名 然后删除
                    f.delete();
                    File serviceMapFIle = new File(serviceMapName);
                    FileUtil.fileChannelCopy(originalMapFile, serviceMapFIle);
                }
            }
        }
        return R.ok();
    }

    @Override
    public int remove(String parkname) {
        return parkServiceDao.remove(parkname);
    }

    @Override
    public int batchRemove(Long[] ids) {
        return parkServiceDao.batchRemove(ids);
    }

    @Override
    public Map<String, String> getFileName(Integer type, Long id) {
        Map<String, String> map = new HashMap<>();
        if (type == 0) {//园区
            String fileName = parkService.getFileName(id);
            map.put("parkFileName", fileName);
        } else if (type == 2) {//楼层
            FloorDO floorDO = floorService.get(id);
            String floorFileName = floorService.getFileName(id);//楼层名名称
            BuildingDO buildingDO = buildingService.get(floorDO.getBuildid());
            String buildFileName = buildingService.getFileName(buildingDO.getBuildid());//楼栋名称
            ParkDO parkDO = parkService.get(buildingDO.getParkid());
            String parkFileName = parkService.getFileName(parkDO.getParkid());//园区名称
            map.put("parkFileName", parkFileName);
            map.put("buildFileName", buildFileName);
            map.put("floorFileName", floorFileName);
        }
        return map;
    }

}
