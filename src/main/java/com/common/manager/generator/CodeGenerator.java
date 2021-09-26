package com.common.manager.generator;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.List;

// 演示例子，执行 main 方法控制台输入模块表名回车自动生成对应项目目录中
public class CodeGenerator {

    private final static String outputDir = "E:/generator";
    private final static String entityName = "%sDO";

    private final static String dbDriverName = "com.mysql.jdbc.Driver";
    private final static String dbUserName = "root";
    private final static String dbPassword = "Gdjk@2020";
    private final static String dbUrl = "jdbc:mysql://localhost:3306/tower_register?useSSL=false&characterEncoding=utf-8&serverTimezone=GMT%2b8";

    private final static String parentPackage = "com.common.manager";
    private final static String entityPackage = "repository.entity.register";
    private final static String mapperPackage = "repository.dao.register";
    private final static String xmlPackage = "repository.mappers.register";
    private final static String servicePackage = "service.register";
    private final static String serviceImplPackage = "service.impl.register";
    private final static String controllerPackage = "controller.register";

    public static void main(final String[] args) {

        // generator global config
        final GlobalConfig config = new GlobalConfig();
        config.setOutputDir(outputDir);
        config.setFileOverride(true);
        config.setBaseResultMap(true); // XML resultMap
        config.setBaseColumnList(false); // XML
        config.setEntityName(entityName);

        // database config
        final DataSourceConfig dataConfig = new DataSourceConfig();
        dataConfig.setDriverName(dbDriverName);
        dataConfig.setUsername(dbUserName);
        dataConfig.setPassword(dbPassword);
        dataConfig.setUrl(dbUrl);

        // strategy config
        final StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setInclude(
                "register_approval","register_records"


        );
        //strategy.setExclude("alembic_version");
        strategy.setRestControllerStyle(true);
        strategy.entityTableFieldAnnotationEnable(true); //字段属性注解。生成dto时去掉
        strategy.setEntityBooleanColumnRemoveIsPrefix(true);//去掉前缀。生成dto时去掉
        // auto fill
        final List<TableFill> tableFillList = new ArrayList<>();
        tableFillList.add(new TableFill("update_time", FieldFill.INSERT_UPDATE));
        tableFillList.add(new TableFill("create_time", FieldFill.INSERT_UPDATE));
        tableFillList.add(new TableFill("create_user", FieldFill.INSERT_UPDATE));
        tableFillList.add(new TableFill("update_user", FieldFill.INSERT_UPDATE));
        //tableFillList.add(new TableFill("create_date", FieldFill.INSERT));
        //tableFillList.add(new TableFill("create_user", FieldFill.INSERT));
        strategy.setTableFillList(tableFillList);

        // package config
        final PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent(parentPackage);
        packageConfig.setEntity(entityPackage);
        packageConfig.setMapper(mapperPackage);
        packageConfig.setXml(xmlPackage);
        packageConfig.setService(servicePackage);
        packageConfig.setServiceImpl(serviceImplPackage);
        packageConfig.setController(controllerPackage);

        final AutoGenerator autoGenerator = new AutoGenerator();
        autoGenerator.setGlobalConfig(config);
        autoGenerator.setDataSource(dataConfig);
        autoGenerator.setStrategy(strategy);
        autoGenerator.setPackageInfo(packageConfig);

        autoGenerator.execute();
    }
}
