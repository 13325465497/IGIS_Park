package com.isoft.igis.park.domain;

import java.io.Serializable;


/**
 * @author whwlei
 * @email whwlei@163.com
 * @date 2018-12-06 09:25:37
 */
public class BuildingDO implements Serializable {
    private static final long serialVersionUID = -8889330724807313400L;

    //楼栋ID
    private Long buildid;
    //楼栋目录
    private String builddir;
    //楼栋名称
    private String buildname;
    //楼栋标签
    private String buildlable;
    //楼栋文件名
    private String buildfilename;
    //总楼层数
    private Integer floorcount;
    //楼栋面积
    private Double buildarea;
    //楼栋描述
    private String builddescription;
    //所属园区ID
    private Long parkid;
    //所属园区名称
    private String parkName;

    public String getParkName() {
        return parkName;
    }

    public void setParkName(String parkName) {
        this.parkName = parkName;
    }

    public String getBuildfilename() {
        return buildfilename;
    }

    @Override
    public String toString() {
        return "BuildingDO{" +
                "buildid=" + buildid +
                ", builddir='" + builddir + '\'' +
                ", buildname='" + buildname + '\'' +
                ", buildlable='" + buildlable + '\'' +
                ", buildfilename='" + buildfilename + '\'' +
                ", floorcount=" + floorcount +
                ", buildarea=" + buildarea +
                ", builddescription='" + builddescription + '\'' +
                ", parkid=" + parkid +
                ", parkName='" + parkName + '\'' +
                '}';
    }

    public void setBuildfilename(String buildfilename) {
        this.buildfilename = buildfilename;
    }

    public String getBuildlable() {
        return buildlable;
    }

    public void setBuildlable(String buildlable) {
        this.buildlable = buildlable;
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

    /**
     * 设置：
     */
    public void setBuilddir(String builddir) {
        this.builddir = builddir;
    }

    /**
     * 获取：
     */
    public String getBuilddir() {
        return builddir;
    }

    /**
     * 设置：
     */
    public void setBuildname(String buildname) {
        this.buildname = buildname;
    }

    /**
     * 获取：
     */
    public String getBuildname() {
        return buildname;
    }

    /**
     * 设置：
     */
    public void setFloorcount(Integer floorcount) {
        this.floorcount = floorcount;
    }

    /**
     * 获取：
     */
    public Integer getFloorcount() {
        return floorcount;
    }

    /**
     * 设置：
     */
    public void setBuildarea(Double buildarea) {
        this.buildarea = buildarea;
    }

    /**
     * 获取：
     */
    public Double getBuildarea() {
        return buildarea;
    }

    /**
     * 设置：
     */
    public void setBuilddescription(String builddescription) {
        this.builddescription = builddescription;
    }

    /**
     * 获取：
     */
    public String getBuilddescription() {
        return builddescription;
    }

    /**
     * 设置：
     */
    public void setParkid(Long parkid) {
        this.parkid = parkid;
    }

    /**
     * 获取：
     */
    public Long getParkid() {
        return parkid;
    }

}
