package com.synway.property.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.synway.property.common.UrlConstants;
import com.synway.property.interceptor.SqlExecutorInterceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;


@Configuration
public class DataSourceConfig {

    @Resource
    private Environment environment;

    /**
     * 线程池
     */
    @Bean
    public HandlerThreadPool getPool(){
        return new HandlerThreadPool(15,40,5000, TimeUnit.SECONDS,new LinkedBlockingQueue<>());
    }

    @Bean
    @Primary
    public DruidDataSource dataSource(){
        DruidDataSource druidDataSource = new DruidDataSource();

        String dataType = environment.getProperty("database.type");
        if(UrlConstants.DAMENG.equalsIgnoreCase(dataType)){
            druidDataSource.setDriverClassName("dm.jdbc.driver.DmDriver");
        }
        if(UrlConstants.KINGBASE.equalsIgnoreCase(dataType)){
            druidDataSource.setDriverClassName("com.kingbase8.Driver");
        }
        if(UrlConstants.ORACLE.equalsIgnoreCase(dataType)){
            druidDataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        }
        if(UrlConstants.VASTDATA.equalsIgnoreCase(dataType)){
            druidDataSource.setDriverClassName("cn.com.vastbase.Driver");
            druidDataSource.setValidationQuery("select 'x'");
            druidDataSource.setValidationQueryTimeout(60000);
        }
        if(UrlConstants.POSTGRESQL.equalsIgnoreCase(dataType)){
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

    /*用于连接统计hbase信息的数据库*/
    @Bean
    public DruidDataSource dataSourceForStatisHbase(){
        DruidDataSource druidDataSource = new DruidDataSource();

        String dataType = environment.getProperty("databaseStatisHbase.type");
        if(UrlConstants.DAMENG.equalsIgnoreCase(dataType)){
            druidDataSource.setDriverClassName("dm.jdbc.driver.DmDriver");
        }
        if(UrlConstants.KINGBASE.equalsIgnoreCase(dataType)){
            druidDataSource.setDriverClassName("com.kingbase8.Driver");
        }
        if(UrlConstants.ORACLE.equalsIgnoreCase(dataType)){
            druidDataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        }
        if(UrlConstants.VASTDATA.equalsIgnoreCase(dataType)){
            druidDataSource.setDriverClassName("cn.com.vastbase.Driver");
            druidDataSource.setValidationQuery("select 'x'");
            druidDataSource.setValidationQueryTimeout(60000);
        }
        if(UrlConstants.POSTGRESQL.equalsIgnoreCase(dataType)){
            druidDataSource.setDriverClassName("org.postgresql.Driver");
        }

        druidDataSource.setUrl(environment.getProperty("databaseStatisHbase.url"));
        druidDataSource.setUsername(environment.getProperty("databaseStatisHbase.name"));
        druidDataSource.setPassword(environment.getProperty("databaseStatisHbase.password"));
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
        if(UrlConstants.DAMENG.equalsIgnoreCase(dataType)){
            bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/daMeng/*.xml"));
        }
        if(UrlConstants.KINGBASE.equalsIgnoreCase(dataType)){
            bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/kingBase/*.xml"));
        }
        if(UrlConstants.ORACLE.equalsIgnoreCase(dataType)){
            bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/oracle/*.xml"));
        }
        if(UrlConstants.VASTDATA.equalsIgnoreCase(dataType)){
            bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/vastdata/*.xml"));
        }
        if(UrlConstants.POSTGRESQL.equalsIgnoreCase(dataType)){
            bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/postgresql/*.xml"));
        }

        bean.setTypeAliasesPackage("com.synway.property.pojo");
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


    @Bean
    public PlatformTransactionManager oracleTransactionManager(DruidDataSource datasource){
        return new DataSourceTransactionManager(datasource);
    }

    @Bean
    public String myInterceptor(SqlSessionFactory sqlSessionFactory){
        sqlSessionFactory.getConfiguration().addInterceptor(new SqlExecutorInterceptor(environment));
        return "interceptor";
    }

}
