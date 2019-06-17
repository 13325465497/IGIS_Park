package com.isoft.igis.edit.domain;


import java.awt.*;
import java.io.Serializable;

public class PointInfo implements Serializable {

    private static final long serialVersionUID = -4043630193560391799L;
    // id
    private Long pointID;
    // 所属园区
    private String parkName;
    // 所属楼栋
    private String buildName;
    // 所属楼层
    private String floorName;
    // 标签名
    private String lable;
    //图标url
    private String iconUrl;
    //坐标点
    private Double lon;
    private Double lat;
    // 点类型 , 1 烟感报警器 ,2 , 灭火器 , 3 摄像头
    private Integer pointType;
    //
    private String displayLevel; //地图级别

    public Long getPointID() {
        return pointID;
    }

    public void setPointID(Long pointID) {
        this.pointID = pointID;
    }

    public String getParkName() {
        return parkName;
    }

    public void setParkName(String parkName) {
        this.parkName = parkName;
    }

    public String getBuildName() {
        return buildName;
    }

    public void setBuildName(String buildName) {
        this.buildName = buildName;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    @Override
    public String toString() {
        return "PointInfo{" +
                "pointID=" + pointID +
                ", parkName='" + parkName + '\'' +
                ", buildName='" + buildName + '\'' +
                ", floorName='" + floorName + '\'' +
                ", lable='" + lable + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", lon=" + lon +
                ", lat=" + lat +
                ", pointType=" + pointType +
                ", displayLevel='" + displayLevel + '\'' +
                '}';
    }

    public Integer getPointType() {
        return pointType;
    }

    public void setPointType(Integer pointType) {
        this.pointType = pointType;
    }

    public String getDisplayLevel() {
        return displayLevel;
    }

    public void setDisplayLevel(String displayLevel) {
        this.displayLevel = displayLevel;
    }
}
