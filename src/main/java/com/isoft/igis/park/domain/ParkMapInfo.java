package com.isoft.igis.park.domain;

import java.io.Serializable;
import java.util.List;

public class ParkMapInfo implements Serializable {

    private static final long serialVersionUID = 2704885878928873587L;
    //园区id
    private Long parkid;
    //用户给的名字汉字
    private String parkUserName;
    //园区文件夹名字
    private String parkName;
    //园区标签
    private String parklable;
    //园区文件夹全路径
    private String parkDirPath;
    //园区数据状态(底图,内轮廓,poi,步,车)0有1没有
    private String parkDataStatus;
    //是否有底图数据 0 有 1 没有
    private Integer baseMapStatus;
    //是否有底图数据 0 有 1 没有
    private Integer outlineStatus;
    //是否有点数据 0 有 1 没有
    private Integer poiStatus;
    //是否有步道数据 0 有 1 没有
    private Integer walkStatus;
    //是否有车导数据 0 有 1 没有
    private Integer driveStatus;
    //是否有楼层数据 0 有 1 没有
    private Integer floorDataStatus;
    //园区面积
    private Double parkArea;
    //所有楼栋
    private List<BuildMapInfo> builds;

    @Override
    public String toString() {
        return "ParkMapInfo{" +
                "parkid=" + parkid +
                ", parkUserName='" + parkUserName + '\'' +
                ", parkName='" + parkName + '\'' +
                ", parklable='" + parklable + '\'' +
                ", parkDirPath='" + parkDirPath + '\'' +
                ", parkDataStatus='" + parkDataStatus + '\'' +
                ", baseMapStatus=" + baseMapStatus +
                ", outlineStatus=" + outlineStatus +
                ", poiStatus=" + poiStatus +
                ", walkStatus=" + walkStatus +
                ", driveStatus=" + driveStatus +
                ", floorDataStatus=" + floorDataStatus +
                ", parkArea=" + parkArea +
                ", builds=" + builds +
                '}';
    }

    public String getParklable() {
        return parklable;
    }

    public void setParklable(String parklable) {
        this.parklable = parklable;
    }

    public Double getParkArea() {
        return parkArea;
    }

    public void setParkArea(Double parkArea) {
        this.parkArea = parkArea;
    }

    public Long getParkid() {
        return parkid;
    }

    public void setParkid(Long parkid) {
        this.parkid = parkid;
    }

    public String getParkUserName() {
        return parkUserName;
    }

    public void setParkUserName(String parkUserName) {
        this.parkUserName = parkUserName;
    }

    public String getParkName() {
        return parkName;
    }

    public void setParkName(String parkName) {
        this.parkName = parkName;
    }

    public String getParkDirPath() {
        return parkDirPath;
    }

    public void setParkDirPath(String parkDirPath) {
        this.parkDirPath = parkDirPath;
    }

    public Integer getBaseMapStatus() {
        return baseMapStatus;
    }

    public void setBaseMapStatus(Integer baseMapStatus) {
        this.baseMapStatus = baseMapStatus;
    }

    public Integer getOutlineStatus() {
        return outlineStatus;
    }

    public void setOutlineStatus(Integer outlineStatus) {
        this.outlineStatus = outlineStatus;
    }

    public Integer getPoiStatus() {
        return poiStatus;
    }

    public void setPoiStatus(Integer poiStatus) {
        this.poiStatus = poiStatus;
    }

    public Integer getWalkStatus() {
        return walkStatus;
    }

    public void setWalkStatus(Integer walkStatus) {
        this.walkStatus = walkStatus;
    }

    public Integer getDriveStatus() {
        return driveStatus;
    }

    public void setDriveStatus(Integer driveStatus) {
        this.driveStatus = driveStatus;
    }

    public Integer getFloorDataStatus() {
        return floorDataStatus;
    }

    public void setFloorDataStatus(Integer floorDataStatus) {
        this.floorDataStatus = floorDataStatus;
    }

    public List<BuildMapInfo> getBuilds() {
        return builds;
    }

    public void setBuilds(List<BuildMapInfo> builds) {
        this.builds = builds;
    }

    public String getParkDataStatus() {
        return parkDataStatus;
    }

    public void setParkDataStatus(String parkDataStatus) {
        this.parkDataStatus = parkDataStatus;
    }
}
