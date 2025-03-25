package com.synway.dataoperations.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.synway.dataoperations.constant.Common;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.annotation.Resource;


@Configuration
public class DataSourceConfig {

    @Resource
    private Environment environment;


    @Bean
    @Primary
    public DruidDataSource dataSource(){
        DruidDataSource druidDataSource = new DruidDataSource();

        String dataType = environment.getProperty("database.type");
        if(Common.DAMENG.equalsIgnoreCase(dataType)){
            druidDataSource.setDriverClassName("dm.jdbc.driver.DmDriver");
        }
        if(Common.ORACLE.equalsIgnoreCase(dataType)){
            druidDataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        }
        if(Common.HAILIANG.equalsIgnoreCase(dataType)){
            druidDataSource.setDriverClassName("cn.com.vastbase.Driver");
            druidDataSource.setValidationQuery("select 'x'");
            druidDataSource.setValidationQueryTimeout(60000);
        }
        if(Common.POSTGRESQL.equalsIgnoreCase(dataType)){
            druidDataSource.setDriverClassName("org.postgresql.Driver");
        }

        druidDataSource.setUrl(environment.getProperty("database.url"));
        druidDataSource.setUsername(environment.getProperty("database.name"));
        druidDataSource.setPassword(environment.getProperty("database.password"));
        druidDataSource.setMaxActive(10);
        druidDataSource.setInitialSize(1);
        druidDataSource.setMaxWait(480000);
        druidDataSource.setMinIdle(1);
        druidDataSource.setTimeBetweenEvictionRunsMillis(60000);
        druidDataSource.setMinEvictableIdleTimeMillis(300000);
        druidDataSource.setTestOnBorrow(false);
        druidDataSource.setTestWhileIdle(true);
        druidDataSource.setTestOnReturn(true);
        druidDataSource.setRemoveAbandoned(true);
        druidDataSource.setRemoveAbandonedTimeout(1800);
        druidDataSource.setLogAbandoned(false);
        return druidDataSource;
    }


    @Bean("masterSqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory() throws Exception{
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource());

        String dataType = environment.getProperty("database.type");
        if(Common.DAMENG.equalsIgnoreCase(dataType)){
            bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/daMeng/*.xml"));
        }
        if(Common.ORACLE.equalsIgnoreCase(dataType)){
            bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/oracle/*.xml"));
        }
        if(Common.HAILIANG.equalsIgnoreCase(dataType)){
            bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/hailiang/*.xml"));
        }
        if(Common.POSTGRESQL.equalsIgnoreCase(dataType)){
            bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/postgresql/*.xml"));
        }

        bean.setTypeAliasesPackage("com.synway.dataoperations.pojo");
        return bean.getObject();
    }

    @Primary
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager(){
        return new DataSourceTransactionManager(dataSource());
    }

    @Primary
    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }


}
