package com.isoft.igis.park.domain;

import java.io.Serializable;


/**
 * @author whwlei
 * @email whwlei@163.com
 * @date 2019-03-25 15:57:35
 */
public class ParkServiceDO implements Serializable {

    private static final long serialVersionUID = -2884037880773932075L;
    //园区服务id
    private Long id;
    //园区名(英)
    private String parkname;
    //园区名(汉)
    private String parkusername;
    //园区室内服务json
    private String indoorservice;
    //园区室外wms服务json
    private String outdoorwmsservice;
    //园区室外wfs服务json
    private String outdoorwfsservice;
    //园区经度
    private Double lon;
    //园区纬度
    private Double lat;

    /**
     * 设置：园区服务id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取：园区服务id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置：园区名(英)
     */
    public void setParkname(String parkname) {
        this.parkname = parkname;
    }

    /**
     * 获取：园区名(英)
     */
    public String getParkname() {
        return parkname;
    }

    /**
     * 设置：园区名(汉)
     */
    public void setParkusername(String parkusername) {
        this.parkusername = parkusername;
    }

    /**
     * 获取：园区名(汉)
     */
    public String getParkusername() {
        return parkusername;
    }

    /**
     * 设置：园区室内服务json
     */
    public void setIndoorservice(String indoorservice) {
        this.indoorservice = indoorservice;
    }

    /**
     * 获取：园区室内服务json
     */
    public String getIndoorservice() {
        return indoorservice;
    }

    /**
     * 设置：园区室外wms服务json
     */
    public void setOutdoorwmsservice(String outdoorwmsservice) {
        this.outdoorwmsservice = outdoorwmsservice;
    }

    /**
     * 获取：园区室外wms服务json
     */
    public String getOutdoorwmsservice() {
        return outdoorwmsservice;
    }

    /**
     * 设置：园区室外wfs服务json
     */
    public void setOutdoorwfsservice(String outdoorwfsservice) {
        this.outdoorwfsservice = outdoorwfsservice;
    }

    /**
     * 获取：园区室外wfs服务json
     */
    public String getOutdoorwfsservice() {
        return outdoorwfsservice;
    }

    /**
     * 设置：园区经度
     */
    public void setLon(Double lon) {
        this.lon = lon;
    }

    /**
     * 获取：园区经度
     */
    public Double getLon() {
        return lon;
    }

    /**
     * 设置：园区纬度
     */
    public void setLat(Double lat) {
        this.lat = lat;
    }

    /**
     * 获取：园区纬度
     */
    public Double getLat() {
        return lat;
    }

    @Override
    public String toString() {
        return "ParkServiceDO{" +
                "id=" + id +
                ", parkname='" + parkname + '\'' +
                ", parkusername='" + parkusername + '\'' +
                ", indoorservice='" + indoorservice + '\'' +
                ", outdoorwmsservice='" + outdoorwmsservice + '\'' +
                ", outdoorwfsservice='" + outdoorwfsservice + '\'' +
                ", lon=" + lon +
                ", lat=" + lat +
                '}';
    }
}
