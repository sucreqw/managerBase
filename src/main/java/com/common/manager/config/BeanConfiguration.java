package com.common.manager.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.Properties;

/**
 * @author 72038611
 */
@Configuration
public class BeanConfiguration {
    @Autowired
    private ApplicationContext context;
    private final Logger logger = LoggerFactory.getLogger(BeanConfiguration.class);
    @Bean
    public YamlConfigurerUtil ymlConfigurerUtil() {
        //1:加载配置文件
        Resource app = new ClassPathResource("application.yml");
        Resource appDev = new ClassPathResource("application-dev.yml");
        Resource appProd = new ClassPathResource("application-prod.yml");
        YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
        // 2:将加载的配置文件交给 YamlPropertiesFactoryBean
        yamlPropertiesFactoryBean.setResources(app);
        // 3：将yml转换成 key：val
        Properties properties = yamlPropertiesFactoryBean.getObject();
        String active = context.getEnvironment().getActiveProfiles()[0];//properties.getProperty("spring.profiles.active");
        if (active == "" || active == null) {
            logger.error("未找到spring.profiles.active配置！");
        }else {
            //判断当前配置是什么环境
            if ("dev".equals(active)) {
                yamlPropertiesFactoryBean.setResources(app,appDev);
            }else if("prod".equals(active)){
                yamlPropertiesFactoryBean.setResources(app,appProd);
            }
        }
        // 4: 将Properties 通过构造方法交给我们写的工具类
        YamlConfigurerUtil ymlConfigurerUtil = new YamlConfigurerUtil(yamlPropertiesFactoryBean.getObject());
        return ymlConfigurerUtil;
    }
}