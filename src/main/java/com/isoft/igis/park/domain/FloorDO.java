package com.isoft.igis.park.domain;

import java.io.Serializable;



/**
 * 
 * 
 * @author whwlei
 * @email whwlei@163.com
 * @date 2018-12-06 09:25:40
 */
public class FloorDO implements Serializable {
	private static final long serialVersionUID = -3653567819455025949L;
	
	//楼层ID
	private Long floorid;
	//楼层目录
	private String floordir;
	//楼层名称
	private String floorname;
	//楼层标签
	private String floorlable;
	//楼层文件名
	private String floorfilename;
	//编号
	private Integer floornum;
	//楼层面积
	private Double floorarea;
	//楼层描述
	private String floordescription;
	//所属楼栋编号
	private Long buildid;
	//所属园区名称
	private String parkName;
	//所属楼栋名称
	private String buildingName;
	//是否有楼层(必须)数据  0 有 1 没有
	private Integer floorDataStatus;
	//是否有导航数据 0 有 1 没有
	private Integer NavigationDataStatus;

	@Override
	public String toString() {
		return "FloorDO{" +
				"floorid=" + floorid +
				", floordir='" + floordir + '\'' +
				", floorname='" + floorname + '\'' +
				", floorlable='" + floorlable + '\'' +
				", floorfilename='" + floorfilename + '\'' +
				", floornum=" + floornum +
				", floorarea=" + floorarea +
				", floordescription='" + floordescription + '\'' +
				", buildid=" + buildid +
				", parkName='" + parkName + '\'' +
				", buildingName='" + buildingName + '\'' +
				", floorDataStatus=" + floorDataStatus +
				", NavigationDataStatus=" + NavigationDataStatus +
				'}';
	}

	public String getFloorfilename() {
		return floorfilename;
	}

	public void setFloorfilename(String floorfilename) {
		this.floorfilename = floorfilename;
	}

	public String getFloorlable() {
		return floorlable;
	}

	public void setFloorlable(String floorlable) {
		this.floorlable = floorlable;
	}

	public Integer getFloorDataStatus() {
		return floorDataStatus;
	}

	public void setFloorDataStatus(Integer floorDataStatus) {
		this.floorDataStatus = floorDataStatus;
	}

	public Integer getNavigationDataStatus() {
		return NavigationDataStatus;
	}

	public void setNavigationDataStatus(Integer navigationDataStatus) {
		NavigationDataStatus = navigationDataStatus;
	}

	/**
	 * 设置：
	 */
	public void setFloorid(Long floorid) {
		this.floorid = floorid;
	}
	/**
	 * 获取：
	 */
	public Long getFloorid() {
		return floorid;
	}
	/**
	 * 设置：
	 */
	public void setFloordir(String floordir) {
		this.floordir = floordir;
	}
	/**
	 * 获取：
	 */
	public String getFloordir() {
		return floordir;
	}
	/**
	 * 设置：楼层名称
	 */
	public void setFloorname(String floorname) {
		this.floorname = floorname;
	}
	/**
	 * 获取：楼层名称
	 */
	public String getFloorname() {
		return floorname;
	}
	/**
	 * 设置：楼层
	 */
	public void setFloornum(Integer floornum) {
		this.floornum = floornum;
	}
	/**
	 * 获取：楼层
	 */
	public Integer getFloornum() {
		return floornum;
	}
	/**
	 * 设置：
	 */
	public void setFloorarea(Double floorarea) {
		this.floorarea = floorarea;
	}
	/**
	 * 获取：
	 */
	public Double getFloorarea() {
		return floorarea;
	}
	/**
	 * 设置：
	 */
	public void setFloordescription(String floordescription) {
		this.floordescription = floordescription;
	}
	/**
	 * 获取：
	 */
	public String getFloordescription() {
		return floordescription;
	}
	/**
	 * 设置：
	 */
	public void setBuildid(Long buildid) {
		this.buildid = buildid;
	}
	/**
	 * 获取：
	 */
	public Long getBuildid() {
		return buildid;
	}

	public String getParkName() {
		return parkName;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

}
