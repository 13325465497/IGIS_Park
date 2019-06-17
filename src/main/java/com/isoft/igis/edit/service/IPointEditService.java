package com.isoft.igis.edit.service;

import java.util.List;

import com.isoft.igis.edit.domain.EditDataInfo;
import com.isoft.igis.edit.domain.UserPointDO;

public interface IPointEditService {

	public EditDataInfo getParkData(String parkName);

	public Object editParkData(List<UserPointDO> pointList);

	public EditDataInfo getFloorData(String parkName, String buildName, String floorName);

	public Object editFloorData(List<UserPointDO> pointList);
}
