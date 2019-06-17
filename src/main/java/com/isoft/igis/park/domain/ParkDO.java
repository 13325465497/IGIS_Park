package com.isoft.igis.park.domain;

import java.io.Serializable;


/**
 * @author whwlei
 * @email whwlei@163.com
 * @date 2018-12-06 09:25:43
 */
public class ParkDO implements Serializable {
    private static final long serialVersionUID = -5353112349445016365L;

    // 园区id
    private Long parkid;
    // 园区文件目录
    private String parkdir;
    // 园区名称
    private String parkname;
    // 园区标签
    private String parklable;
    // 园区文件名
    private String parkfilename;
    //省
    private String province;
    //市
    private String city;
    //区
    private String region;
    //园区地址
    private String parkaddress;
    //园区面积
    private Double parkarea;
    //园区描述
    private String parkdescription;
    //园区图片
    private String parkimages;
    //总楼栋数
    private Integer buildcount;
    //园区数据状态 (底图,内轮廓,poi,步,车)0有1没有  : 如11011 表示该园区只上传了poi数据
    private String parkDataStatus;
    //
    private Long deptId;

    /**
     * 设置：
     */
    public void setParkid(Long parkid) {
        this.parkid = parkid;
    }

    public String getParkDataStatus() {
        return parkDataStatus;
    }

    public String getParkfilename() {
        return parkfilename;
    }

    @Override
    public String toString() {
        return "ParkDO{" +
                "parkid=" + parkid +
                ", parkdir='" + parkdir + '\'' +
                ", parkname='" + parkname + '\'' +
                ", parklable='" + parklable + '\'' +
                ", parkfilename='" + parkfilename + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", region='" + region + '\'' +
                ", parkaddress='" + parkaddress + '\'' +
                ", parkarea=" + parkarea +
                ", parkdescription='" + parkdescription + '\'' +
                ", parkimages='" + parkimages + '\'' +
                ", buildcount=" + buildcount +
                ", parkDataStatus='" + parkDataStatus + '\'' +
                ", deptId=" + deptId +
                '}';
    }

    public void setParkfilename(String parkfilename) {
        this.parkfilename = parkfilename;
    }

    public String getParklable() {
        return parklable;
    }

    public void setParklable(String parklable) {
        this.parklable = parklable;
    }

    public void setParkDataStatus(String parkDataStatus) {
        this.parkDataStatus = parkDataStatus;
    }

    /**
     * 获取：
     */
    public Long getParkid() {
        return parkid;
    }

    /**
     * 设置：
     */
    public void setParkdir(String parkdir) {
        this.parkdir = parkdir;
    }

    /**
     * 获取：
     */
    public String getParkdir() {
        return parkdir;
    }

    /**
     * 设置：
     */
    public void setParkname(String parkname) {
        this.parkname = parkname;
    }

    /**
     * 获取：
     */
    public String getParkname() {
        return parkname;
    }


    /**
     * 设置：
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * 获取：
     */
    public String getProvince() {
        return province;
    }

    /**
     * 设置：
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 获取：
     */
    public String getCity() {
        return city;
    }

    /**
     * 设置：
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * 获取：
     */
    public String getRegion() {
        return region;
    }

    /**
     * 设置：
     */
    public void setParkaddress(String parkaddress) {
        this.parkaddress = parkaddress;
    }

    /**
     * 获取：
     */
    public String getParkaddress() {
        return parkaddress;
    }

    /**
     * 设置：
     */
    public void setParkarea(Double parkarea) {
        this.parkarea = parkarea;
    }

    /**
     * 获取：
     */
    public Double getParkarea() {
        return parkarea;
    }

    /**
     * 设置：
     */
    public void setParkdescription(String parkdescription) {
        this.parkdescription = parkdescription;
    }

    /**
     * 获取：
     */
    public String getParkdescription() {
        return parkdescription;
    }

    /**
     * 设置：
     */
    public void setParkimages(String parkimages) {
        this.parkimages = parkimages;
    }

    /**
     * 获取：
     */
    public String getParkimages() {
        return parkimages;
    }

    /**
     * 设置：
     */
    public void setBuildcount(Integer buildcount) {
        this.buildcount = buildcount;
    }

    /**
     * 获取：
     */
    public Integer getBuildcount() {
        return buildcount;
    }

    /**
     * 设置：
     */
    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    /**
     * 获取：
     */
    public Long getDeptId() {
        return deptId;
    }

}
