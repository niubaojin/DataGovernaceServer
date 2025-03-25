package com.synway.datarelation.config;


import com.alibaba.druid.pool.DruidDataSource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @ClassName Config
 * @description 在阿里v2版本中，配置后台的mysql数据库
 * @author wangdongwei
 * @date 2020/8/19 17:33
 */
@Configuration
public class AliMysqlConfig {
    private Logger logger = LoggerFactory.getLogger(AliMysqlConfig.class);

    @Autowired()private Environment env;

    /**
     * 新增阿里后台mysql库的连接信息
     * @return
     */
    @Bean("mysqlDataSource")
    public DruidDataSource mysqlDataSource(){
        DruidDataSource druidDataSource = new DruidDataSource();
        String mysqlFlag = env.getProperty("mysql.config","");
        if(StringUtils.isNotEmpty(mysqlFlag) && "true".equals(mysqlFlag)){
            druidDataSource.setDriverClassName(env.getProperty("mysql.className"));
            druidDataSource.setUrl(env.getProperty("mysql.url"));
            druidDataSource.setUsername(env.getProperty("mysql.username"));
            druidDataSource.setPassword(env.getProperty("mysql.password"));
        }else{
            logger.info("不需要连接阿里后台的mysql数据库");
        }
        return druidDataSource;
    }

    /**
     * 新增阿里后台mysql库的连接信息
     * @return
     */
    @Bean("mysqlDpbiztenantDataSource")
    public DruidDataSource mysqlDpbiztenantDataSource(){
        DruidDataSource druidDataSource = new DruidDataSource();
        String mysql2Flag = env.getProperty("mysql2.config","");
        if(StringUtils.isNotEmpty(mysql2Flag) && "true".equals(mysql2Flag)) {
            druidDataSource.setDriverClassName(env.getProperty("mysql2.className"));
            druidDataSource.setUrl(env.getProperty("mysql2.url"));
            druidDataSource.setUsername(env.getProperty("mysql2.username"));
            druidDataSource.setPassword(env.getProperty("mysql2.password"));
        }else{
            logger.info("不需要连接阿里后台的mysql数据库");
        }
        return druidDataSource;
    }

}
