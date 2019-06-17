package com.isoft.igis.edit.domain;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class PointDataInfo implements Serializable {
    private static final long serialVersionUID = 1564994954668220724L;
    private String parkName;//所在园区
    private String buildName;//所在楼栋
    private String floorName;//所在楼层
    private Integer pointType;//业务数据类型
    private Double[] point;//查询面范围
    private Integer zoom;//查询地图级别
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

    public Integer getPointType() {
        return pointType;
    }

    public void setPointType(Integer pointType) {
        this.pointType = pointType;
    }

    @Override
    public String toString() {
        return "PointDataInfo{" +
                "parkName='" + parkName + '\'' +
                ", buildName='" + buildName + '\'' +
                ", floorName='" + floorName + '\'' +
                ", pointType=" + pointType +
                ", point=" + Arrays.toString(point) +
                ", zoom=" + zoom +
                '}';
    }

    public Integer getZoom() {
        return zoom;
    }

    public void setZoom(Integer zoom) {
        this.zoom = zoom;
    }

    public Double[] getPoint() {
        return point;
    }

    public void setPoint(Double[] point) {
        this.point = point;
    }
}
