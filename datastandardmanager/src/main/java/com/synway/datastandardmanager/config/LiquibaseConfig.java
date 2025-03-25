package com.synway.datastandardmanager.config;

import liquibase.integration.spring.SpringLiquibase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.io.File;

/**
 * @Author chenfei
 * @Data 2024/9/29 14:15
 * @Description 通过liquibase维护数据库sql版本
 */
@Slf4j
@Configuration
public class LiquibaseConfig {

    @Autowired
    private Environment env;

    @Bean
    @ConfigurationProperties(prefix = "spring.liquibase")
    public LiquibaseProperties getLiquibaseProperties(){
        return new LiquibaseProperties();
    }

    /**
     * @author chenfei
     * @date 2024/9/29 15:46
     * @Description
     * changelog-xml以数据库类型区分， 通过数据源类自动匹配xml文件
     */
    @Bean
    public SpringLiquibase liquibase(DataSource dataSource){
        SpringLiquibase liquibase = new SpringLiquibase();
        //判断无changelog xml文件不运行
        File changelogFile = this.getChangelogFile();
        if (changelogFile == null){
            liquibase.setShouldRun(false);
            return liquibase;
        }
        //执行
        liquibase.setShouldRun(true);
        liquibase.setDataSource(dataSource);
        //必须使用绝对路径，不然sql分开打包不能识别
        String changeLogXml = String.format("file:%s", changelogFile.getAbsolutePath());
        liquibase.setChangeLog(changeLogXml);
        /**
         * changelog xml执行后，写执行记录的表名
         * DBR_SCHEMA_CHANGE_LOG、DBR_SCHEMA_CHANGE_LOG_LOCK为数据血缘自定义私有表，
         * 应用模块根据模块表前缀自行定义
         */
        liquibase.setDatabaseChangeLogTable("DSM_SCHEMA_CHANGE_LOG");
        //多用户场景使用
        liquibase.setDatabaseChangeLogLockTable("DSM_SCHEMA_CHANGE_LOG_LOCK");
        return  liquibase;
    }

    private File getChangelogFile(){
        ApplicationHome home = new ApplicationHome(getClass());
        String path = home.getDir().getAbsolutePath();
        String dbType =  env.getProperty("database.type");
        String changelogXml = path + File.separatorChar + "sql" + File.separatorChar +
                dbType.toLowerCase() + File.separatorChar + "changelog.xml";
        log.info("changelog xml文件绝对路径：{}", changelogXml);

        File changelogFile = new File(changelogXml);
        if (changelogFile.exists()){
            return changelogFile;
        }else {
            return null;
        }
    }


}
