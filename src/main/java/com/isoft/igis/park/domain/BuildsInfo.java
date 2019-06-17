package com.isoft.igis.park.domain;

import java.util.List;

public class BuildsInfo {

    private String buildId;
    private String buildName;
    private List<String> floorName;
    private List<String> floorId;
    private List<String> center;

    @Override
    public String toString() {
        return "BuildsInfo{" +
                "buildId='" + buildId + '\'' +
                ", buildName='" + buildName + '\'' +
                ", floorName=" + floorName +
                ", floorId=" + floorId +
                ", center=" + center +
                '}';
    }

    public String getBuildId() {
        return buildId;
    }

    public void setBuildId(String buildId) {
        this.buildId = buildId;
    }

    public String getBuildName() {
        return buildName;
    }

    public void setBuildName(String buildName) {
        this.buildName = buildName;
    }

    public List<String> getFloorName() {
        return floorName;
    }

    public void setFloorName(List<String> floorName) {
        this.floorName = floorName;
    }

    public List<String> getFloorId() {
        return floorId;
    }

    public void setFloorId(List<String> floorId) {
        this.floorId = floorId;
    }

    public List<String> getCenter() {
        return center;
    }

    public void setCenter(List<String> center) {
        this.center = center;
    }
}
