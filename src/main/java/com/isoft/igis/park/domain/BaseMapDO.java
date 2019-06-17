package com.isoft.igis.park.domain;

import java.io.Serializable;


/**
 * @author whwei
 * @email whwlei@163.com
 * @date 2019-04-15 10:43:17
 */
public class BaseMapDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //底图id
    private Long id;
    //底图url
    private String basemapurl;

    /**
     * 设置：底图id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取：底图id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置：底图url
     */
    public void setBasemapurl(String basemapurl) {
        this.basemapurl = basemapurl;
    }

    /**
     * 获取：底图url
     */
    public String getBasemapurl() {
        return basemapurl;
    }
}
