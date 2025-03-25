package com.synway.property.config;

import com.alibaba.druid.pool.DruidDataSource;
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
public class AdsOrMysql {
    private Logger logger = LoggerFactory.getLogger(AdsOrMysql.class);

    @Autowired()private Environment env;

    /**
     * 新增阿里后台mysql库的连接信息(meta库)
     * @return
     */
    @Bean("adsDataSourceMeta")
    public DruidDataSource adsDataSourceMeta(){
        DruidDataSource druidDataSource = new DruidDataSource();
//        // 8版本
//        druidDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        // 5版本
        druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        druidDataSource.setUrl(env.getProperty("adsMeta.url"));
        druidDataSource.setUsername(env.getProperty("adsMeta.username"));
        druidDataSource.setPassword(env.getProperty("adsMeta.password"));
        druidDataSource.setValidationQuery("select 1");
        druidDataSource.setTestWhileIdle(true);
        druidDataSource.setBreakAfterAcquireFailure(true);
        druidDataSource.setMaxWait(15000);
        druidDataSource.setConnectionErrorRetryAttempts(3);
        druidDataSource.setTimeBetweenEvictionRunsMillis(60000);
        druidDataSource.setMaxActive(20);
        druidDataSource.setInitialSize(1);

        return druidDataSource;
    }

    /**
     * 新增阿里后台mysql库的连接信息(ads库)meta_accessid
     * @return
     */
    @Bean("adsDataSourceSys")
    public DruidDataSource adsDataSourceSys(){
        DruidDataSource druidDataSource = new DruidDataSource();
//        // 8版本
//        druidDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        // 5版本
        druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        druidDataSource.setUrl(env.getProperty("adsSys.url"));
        druidDataSource.setUsername(env.getProperty("adsSys.username"));
        druidDataSource.setPassword(env.getProperty("adsSys.password"));
        druidDataSource.setValidationQuery("select 1");
        druidDataSource.setTestWhileIdle(true);
        druidDataSource.setBreakAfterAcquireFailure(true);
        druidDataSource.setMaxWait(15000);
        druidDataSource.setConnectionErrorRetryAttempts(3);
        druidDataSource.setTimeBetweenEvictionRunsMillis(60000);
        druidDataSource.setMaxActive(20);
        druidDataSource.setInitialSize(1);

        return druidDataSource;
    }

}
