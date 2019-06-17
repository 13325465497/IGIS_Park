package com.isoft.igis.edit.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;

import com.isoft.igis.common.config.IGISParkConfig;
import com.isoft.igis.common.utils.FormatShp;
import com.isoft.igis.common.utils.SystemUtils;
import com.isoft.igis.edit.domain.EditDataInfo;
import com.isoft.igis.edit.domain.UserPointDO;
import com.isoft.igis.edit.service.IPointEditService;
import com.isoft.igis.edit.service.UserPointService;

@Service
public class PointEditServiceImpl implements IPointEditService {

    private static Logger logger = LoggerFactory.getLogger(PointEditServiceImpl.class);

    @Autowired
    IGISParkConfig igisConfig;

    @Autowired
    private UserPointService userPointService;

    @Override
    public EditDataInfo getParkData(String parkName) {
        String jsonPath = ClassUtils.getDefaultClassLoader().getResource("").getPath() + "static/json/";
        if (SystemUtils.isWindows()) {
            jsonPath = jsonPath.substring(1);
        }
        String poiPath = igisConfig.getParkPath() + parkName + "/outdoor/wfs/poi.shp";
        String poiJsonFileName = parkName + "_temp_park_poi" + System.currentTimeMillis() + ".json";
        String regionPath = igisConfig.getParkPath() + parkName + "/outdoor/wfs/region.shp";
        String regionJsonFileName = parkName + "_temp_park_region" + System.currentTimeMillis() + ".json";
        EditDataInfo editDataInfo = new EditDataInfo();
        // shp转成json
        if (FormatShp.shpTOJson(poiPath, jsonPath + poiJsonFileName)) {
            editDataInfo.setPoiDataUrl("/json/" + poiJsonFileName);
        }
        if (FormatShp.shpTOJson(regionPath, jsonPath + regionJsonFileName)) {
            editDataInfo.setRegionDataUrl("/json/" + regionJsonFileName);
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("parkname", parkName);
        List<UserPointDO> userPointList = userPointService.list(params);
        if (null != userPointList && userPointList.size() > 0) {
            editDataInfo.setHasData(true);
        } else {
            editDataInfo.setHasData(false);
        }
        return editDataInfo;
    }

    @Override
    public Object editParkData(List<UserPointDO> pointList) {
        String parkname = pointList.get(0).getParkname();
        if (parkname!=null){
            userPointService.deleteByParkName(parkname);//删除之前园区的poi
        }
        for (UserPointDO pointInfo : pointList) {
            userPointService.save(pointInfo);//添加新的园区poi点
        }
        return true;
    }

    @Override
        public EditDataInfo getFloorData(String parkName, String buildName, String floorName) {
        String jsonPath = ClassUtils.getDefaultClassLoader().getResource("").getPath() + "static/json/";
        if (SystemUtils.isWindows()) {
            jsonPath = jsonPath.substring(1);
        }
        String poiPath = igisConfig.getParkPath() + parkName + "/indoor/" + buildName + "/" + floorName + "/poi.shp";
        String poiJsonFileName = parkName + buildName + floorName + "_temp_floor_poi" + System.currentTimeMillis()
                + ".json";
        String regionPath = igisConfig.getParkPath() + parkName + "/indoor/" + buildName + "/" + floorName
                + "/region.shp";
        String regionJsonFileName = parkName + buildName + floorName + "_temp_floor_region" + System.currentTimeMillis()
                + ".json";
        EditDataInfo editDataInfo = new EditDataInfo();
        // shp转成json
        if (FormatShp.shpTOJson(poiPath, jsonPath + poiJsonFileName)) {
            editDataInfo.setPoiDataUrl("/json/" + poiJsonFileName);
        }
        if (FormatShp.shpTOJson(regionPath, jsonPath + regionJsonFileName)) {
            editDataInfo.setRegionDataUrl("/json/" + regionJsonFileName);
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("parkName", parkName);
        params.put("buildName", buildName);
        params.put("floorName", floorName);
        List<UserPointDO> userPointList = userPointService.list(params);
        if (null != userPointList && userPointList.size() > 0) {
            editDataInfo.setHasData(true);
        } else {
            editDataInfo.setHasData(false);
        }
        return editDataInfo;
    }

    @Override
    public Object editFloorData(List<UserPointDO> pointList) {
        String parkname = pointList.get(0).getParkname();
        String buildname = pointList.get(0).getBuildname();
        String floorname = pointList.get(0).getFloorname();

        if (parkname!=null&&buildname!=null&&floorname!=null){
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("parkName", parkname);
            params.put("buildName", buildname);
            params.put("floorName", floorname);
            userPointService.deleteByParkNameAndBuildNameANdFloorName(params);
        }
        for (UserPointDO pointInfo : pointList) {
            userPointService.save(pointInfo);
        }
        return true;
    }

}
