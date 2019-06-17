package com.isoft.igis.common.config;

import com.isoft.igis.common.utils.FileUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "parkdo")
public class IGISParkConfig {

    /**
     * 上传路径
     */
    private String uploadPath = FileUtil.getUploadPath();
    private String parkPath = FileUtil.getParkPath();
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * ip
     */
    private String ip;
    /**
     * 端口
     */
    private String port;


    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    public String getParkPath() {
        return parkPath;
    }

    public void setParkPath(String parkPath) {
        this.parkPath = parkPath;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
