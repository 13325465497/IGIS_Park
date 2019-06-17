package com.isoft.igis.park.domain;

import java.io.Serializable;

public class ParkTree implements Serializable {

    private static final long serialVersionUID = -4506340702516484445L;
    private Long id; //统一id
    private Integer type;//类型 , 1 园区 park, 2 , 楼栋building , 3 老楼层floor
    private String name; //名称
    private String lable; //标签
    private Long parentId; //父id
    private Double area; //面积
    private String description;//描述

    public Long getId() {
        return id;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return "ParkTree{" +
                "id=" + id +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", lable='" + lable + '\'' +
                ", parentId=" + parentId +
                ", area=" + area +
                ", description='" + description + '\'' +
                '}';
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
