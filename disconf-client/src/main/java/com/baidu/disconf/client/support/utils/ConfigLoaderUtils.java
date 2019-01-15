package com.baidu.disconf.client.support.utils;

import com.baidu.disconf.core.common.utils.ClassLoaderUtil;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.Properties;

/**
 * 配置导入工具
 */
public final class ConfigLoaderUtils {

    private ConfigLoaderUtils() {
    }


    /**
     * 配置文件载入器助手
     */
    public static Properties loadConfig(final String propertyFilePath) throws Exception {

        try {
            return ConfigLoaderUtils.loadConfigWithTomcatMode(propertyFilePath);
        } catch (Exception e1) {
            try {
                return loadConfigWithNormalMode(propertyFilePath);
            } catch (Exception e2) {
                throw new Exception("cannot load config file: " + propertyFilePath);
            }
        }
    }


    /**
     * 使用TOMCAT模式来导入
     */
    private static Properties loadConfigWithTomcatMode(final String propertyFilePath) throws Exception {

        Properties props = new Properties();
        try {
            // 先用TOMCAT模式进行导入
            // http://blog.csdn.net/minfree/article/details/1800311
            // http://stackoverflow.com/questions/3263560/sysloader-getresource-problem-in-java
            URL url = ClassLoaderUtil.getLoader().getResource(propertyFilePath);
            URI uri = new URI(url.toString());
            props.load(new InputStreamReader(new FileInputStream(uri.getPath()), "utf-8"));

        } catch (Exception e) {
            // http://stackoverflow.com/questions/574809/load-a-resource-contained-in-a-jar
            props.load(new InputStreamReader(ClassLoaderUtil.getLoader()
                    .getResourceAsStream(propertyFilePath), "utf-8"));
        }
        return props;
    }

    /**
     * 使用普通模式导入
     */
    private static Properties loadConfigWithNormalMode(final String propertyFilePath) throws Exception {
        Properties props = new Properties();
        props.load(new InputStreamReader(new FileInputStream(propertyFilePath), "utf-8"));
        return props;
    }
}
