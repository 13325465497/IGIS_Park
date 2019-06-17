package com.isoft.igis.edit.domain;

import java.io.Serializable;


/**
 * @author whwei
 * @email whwlei@163.com
 * @date 2019-04-12 15:28:10
 */
public class UserPointDO implements Serializable {


    private static final long serialVersionUID = 614433908127564703L;
    //id
    private Long id;
    //园区名
    private String parkname;
    //楼栋名
    private String buildname;
    //楼层名
    private String floorname;
    //经纬
    private Double lon;
    //纬度
    private Double lat;
    //url
    private String iconurl;
    //标签
    private String lable;

    /**
     * 设置：id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取：id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置：园区名
     */
    public void setParkname(String parkname) {
        this.parkname = parkname;
    }

    /**
     * 获取：园区名
     */
    public String getParkname() {
        return parkname;
    }

    /**
     * 设置：楼栋名
     */
    public void setBuildname(String buildname) {
        this.buildname = buildname;
    }

    /**
     * 获取：楼栋名
     */
    public String getBuildname() {
        return buildname;
    }

    /**
     * 设置：楼层名
     */
    public void setFloorname(String floorname) {
        this.floorname = floorname;
    }

    /**
     * 获取：楼层名
     */
    public String getFloorname() {
        return floorname;
    }

    /**
     * 设置：经纬
     */
    public void setLon(Double lon) {
        this.lon = lon;
    }

    /**
     * 获取：经纬
     */
    public Double getLon() {
        return lon;
    }

    /**
     * 设置：纬度
     */
    public void setLat(Double lat) {
        this.lat = lat;
    }

    /**
     * 获取：纬度
     */
    public Double getLat() {
        return lat;
    }

    /**
     * 设置：url
     */
    public void setIconurl(String iconurl) {
        this.iconurl = iconurl;
    }

    /**
     * 获取：url
     */
    public String getIconurl() {
        return iconurl;
    }

    /**
     * 设置：标签
     */
    public void setLable(String lable) {
        this.lable = lable;
    }

    /**
     * 获取：标签
     */
    public String getLable() {
        return lable;
    }
}
