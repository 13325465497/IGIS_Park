package com.isoft.igis.common.run;

import com.isoft.igis.common.config.IGISParkConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;

/**
 * 项目启动时,执行方法1
 */
@Component
@Order(1)
public class RunTimeMethod implements CommandLineRunner {
    @Autowired
    private IGISParkConfig igisConfig;
    private Logger logger = LoggerFactory.getLogger(RunTimeMethod.class);

    /**
     * 项目启动是判断临时目录是否存在 , 不存在则创建 , 并根据配置路径, 写入js文件
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        File parkPath = new File(igisConfig.getParkPath());
        File uploadPath = new File(igisConfig.getUploadPath());
        if (!parkPath.exists()) {
            parkPath.mkdirs();
        }
        if (!uploadPath.exists()) {
            uploadPath.mkdirs();
        }
        String path = RunTimeMethod.class.getClassLoader().getResource("").getPath();
        String webappConfig = "";
        String templatesConfig = "";
        //获取webapp下的config.js 写入地址 和 获取template下的static/的config.js 写入地址
        if (System.getProperties().getProperty("os.name").toLowerCase().startsWith("windows")) {
            logger.info("当前系统为 : {}","windows");
            webappConfig = path.substring(1, path.length() - 17);
            webappConfig = webappConfig + "/js/config.js";
            templatesConfig = path.substring(1, path.length()) + "static/js/config/config.js";
        }else {
            logger.info("当前系统为 : {}","linux");
            webappConfig = path.substring(0, path.length() - 17);
            webappConfig = webappConfig + "/js/config.js";
            templatesConfig = path.substring(0, path.length()) + "static/js/config/config.js";
        }

        //如果能获取到config.js , 则写入, 获取不到则不写入
        if (new File(webappConfig).exists() && new File(templatesConfig).exists()) {
            FileWriter webAppConfigFileWriter = new FileWriter(webappConfig, false);
            String ipPort = igisConfig.getIp() + ":" + igisConfig.getPort();
            webAppConfigFileWriter.write("var ipPort = '" + ipPort + "';");
            webAppConfigFileWriter.flush();
            webAppConfigFileWriter.close();
            FileWriter templatesConfigFileWriter = new FileWriter(templatesConfig, false);
            templatesConfigFileWriter.write("var ipPort = '" + ipPort + "';");
            templatesConfigFileWriter.flush();
            templatesConfigFileWriter.close();
            logger.info("配置写入config.js,{},地址1:{},地址2:{}", "成功!", webappConfig, templatesConfig);
        } else {
            logger.error("配置写入config.js,{},地址:{},地址2:{}", "失败!", webappConfig, templatesConfig);
        }
    }

}
