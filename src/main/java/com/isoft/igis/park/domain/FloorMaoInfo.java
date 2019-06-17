package com.isoft.igis.park.domain;

import java.io.Serializable;

public class FloorMaoInfo implements Serializable {

    private static final long serialVersionUID = 7111601637997497345L;
    //楼层id
    private Long floorid;
    //楼层用户起的名字
    private String floorUserName;
    //楼层标签
    private String floorlable;
    //楼层文件夹名
    private String floorName;
    //是否有楼层(必)数据 0 有 1 没有
    private Integer floorDataStatus;
    //是否有导航数据	数据 0 有 1 没有
    private Integer NavigationDataStatus;
    private Long buildid;
    //楼层面积
    private Double floorArea;

    @Override
    public String toString() {
        return "FloorMaoInfo{" +
                "floorid=" + floorid +
                ", floorUserName='" + floorUserName + '\'' +
                ", floorlable='" + floorlable + '\'' +
                ", floorName='" + floorName + '\'' +
                ", floorDataStatus=" + floorDataStatus +
                ", NavigationDataStatus=" + NavigationDataStatus +
                ", buildid=" + buildid +
                ", floorArea=" + floorArea +
                '}';
    }

    public String getFloorlable() {
        return floorlable;
    }

    public void setFloorlable(String floorlable) {
        this.floorlable = floorlable;
    }

    public Double getFloorArea() {
        return floorArea;
    }

    public void setFloorArea(Double floorArea) {
        this.floorArea = floorArea;
    }

    public Long getBuildid() {
        return buildid;
    }

    public void setBuildid(Long buildid) {
        this.buildid = buildid;
    }

    public Long getFloorid() {
        return floorid;
    }

    public void setFloorid(Long floorid) {
        this.floorid = floorid;
    }

    public String getFloorUserName() {
        return floorUserName;
    }

    public void setFloorUserName(String floorUserName) {
        this.floorUserName = floorUserName;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
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
}
