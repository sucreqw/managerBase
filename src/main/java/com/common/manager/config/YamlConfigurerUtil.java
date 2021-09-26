package com.common.manager.config;

import java.util.Properties;

/**
 * @author 72038611
 */
public class YamlConfigurerUtil {
    private static Properties ymlProperties = new Properties();

    public YamlConfigurerUtil(Properties properties){
        ymlProperties = properties;
    }

    public static String getStrYmlVal(String key){
        return ymlProperties.getProperty(key);
    }

    public static Integer getIntegerYmlVal(String key){
        return Integer.valueOf(ymlProperties.getProperty(key));
    }
}