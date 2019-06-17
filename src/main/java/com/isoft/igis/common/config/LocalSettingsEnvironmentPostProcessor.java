package com.isoft.igis.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class LocalSettingsEnvironmentPostProcessor implements EnvironmentPostProcessor {
    private Logger logger = LoggerFactory.getLogger(LocalSettingsEnvironmentPostProcessor.class);
    private static String NEW_LINE = System.getProperty("file.separator");
    private static String TomcatUrl = System.getProperty("catalina.home");

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment configurableEnvironment, SpringApplication springApplication) {
        String LOCATION = TomcatUrl + NEW_LINE + "webapps" + NEW_LINE + "application.properties";
        logger.info("加载外部配置文件路径为 : {}", LOCATION);
        File file = new File(LOCATION);
        if (file.exists()) {
            MutablePropertySources propertySources = configurableEnvironment.getPropertySources();
            System.out.println("Loading local settings from " + file.getAbsolutePath());
            Properties properties = loadProperties(file);
            System.out.println(properties.toString());
            propertySources.addFirst(new PropertiesPropertySource("Config", properties));
        }
    }

    private Properties loadProperties(File f) {
        FileSystemResource resource = new FileSystemResource(f);
        try {
            return PropertiesLoaderUtils.loadProperties(resource);
        } catch (IOException ex) {
            throw new IllegalStateException("Failed to load local settings from " + f.getAbsolutePath(), ex);
        }
    }

}
