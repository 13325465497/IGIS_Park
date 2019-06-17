package com.isoft.igis.edit.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.isoft.igis.common.config.IGISParkConfig;
import com.isoft.igis.edit.domain.EditDataInfo;
import com.isoft.igis.edit.domain.UserPointDO;
import com.isoft.igis.edit.domain.UserPointsInfo;
import com.isoft.igis.edit.service.IPointEditService;
import com.isoft.igis.edit.service.UserPointService;

@Controller
@RequestMapping("/edit")
public class PointEditController {

	@Autowired
	IPointEditService pointEditServiceImpl;
	@Autowired
    private UserPointService userPointService;
	@Autowired
	IGISParkConfig igisConfig;

	@ResponseBody
	@RequestMapping("/getParkData")
	public EditDataInfo getParkData(@RequestParam String parkName) {
		return pointEditServiceImpl.getParkData(parkName);
	}
	
    @ResponseBody
    @GetMapping("/getUserData")
    public Object list(@RequestParam Map<String, Object> params) {
        //查询园区/(园区楼栋楼层下的) poi新增点
        return userPointService.getUserData(params);
    }

	@ResponseBody
	@RequestMapping(value = "/editParkData", method = RequestMethod.POST)
	public Object editParkData(@RequestBody UserPointsInfo userPointList) {
		return pointEditServiceImpl.editParkData(userPointList.getUserPointList());
	}

	@ResponseBody
	@RequestMapping("/getFloorData")
	public EditDataInfo getFloorData(@RequestParam String parkName, @RequestParam String buildName,
			@RequestParam String floorName) {
		return pointEditServiceImpl.getFloorData(parkName, buildName, floorName);
	}

	@ResponseBody
	@RequestMapping(value = "/editFloorData", method = RequestMethod.POST)
	public Object editFloorData(@RequestBody UserPointsInfo userPointList) {
		return pointEditServiceImpl.editFloorData(userPointList.getUserPointList());
	}

}
