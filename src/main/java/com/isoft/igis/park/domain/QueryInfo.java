package com.isoft.igis.park.domain;

import java.io.Serializable;

public class QueryInfo implements Serializable {
    private static final long serialVersionUID = -1234140902152875512L;
    private Integer pageNum;
    private Integer pageSize;
    private String parkName;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }


    public String getParkName() {
        return parkName;
    }

    public void setParkName(String parkName) {
        this.parkName = parkName;
    }

    @Override
    public String toString() {
        return "QueryInfo{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", parkName='" + parkName + '\'' +
                '}';
    }
}
