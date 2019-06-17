package com.isoft.igis.park.domain;

import java.io.Serializable;
import java.util.List;

public class BuildMapInfo implements Serializable {

    private static final long serialVersionUID = 1826586517966633004L;
    //楼栋id
    private Long buildid;
    //楼栋用户给的名字
    private String buildUserName;
    //楼栋文件夹名
    private String buildName;
    //楼栋标签
    private String buildlable;
    //所属园区id
    private Long parkid;
    //楼栋面积
    private Double buildArea;
    //所有楼层信息
    public List<FloorMaoInfo> floors;

    public Long getBuildid() {
        return buildid;
    }

    public void setBuildid(Long buildid) {
        this.buildid = buildid;
    }

    public String getBuildUserName() {
        return buildUserName;
    }

    public void setBuildUserName(String buildUserName) {
        this.buildUserName = buildUserName;
    }

    public String getBuildName() {
        return buildName;
    }

    public void setBuildName(String buildName) {
        this.buildName = buildName;
    }

    public List<FloorMaoInfo> getFloors() {
        return floors;
    }

    public void setFloors(List<FloorMaoInfo> floors) {
        this.floors = floors;
    }

    public Double getBuildArea() {
        return buildArea;
    }

    public void setBuildArea(Double buildArea) {
        this.buildArea = buildArea;
    }

    @Override
    public String toString() {
        return "BuildMapInfo{" +
                "buildid=" + buildid +
                ", buildUserName='" + buildUserName + '\'' +
                ", buildName='" + buildName + '\'' +
                ", buildlable='" + buildlable + '\'' +
                ", parkid=" + parkid +
                ", buildArea=" + buildArea +
                ", floors=" + floors +
                '}';
    }

    public String getBuildlable() {
        return buildlable;
    }

    public void setBuildlable(String buildlable) {
        this.buildlable = buildlable;
    }

    public Long getParkid() {
        return parkid;
    }

    public void setParkid(Long parkid) {
        this.parkid = parkid;
    }
}
