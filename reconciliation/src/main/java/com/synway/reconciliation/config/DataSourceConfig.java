package com.synway.reconciliation.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.synway.reconciliation.common.ErrorCode;
import com.synway.reconciliation.common.SystemException;
import com.synway.reconciliation.conditional.OracleDbCondition;
import com.synway.reconciliation.interceptor.SqlExecutorInterceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.IOException;

/**
 * 数据源配置
 * @author ym
 */
@Configuration
@Conditional(OracleDbCondition.class)
@MapperScan(basePackages = {"com.synway.reconciliation.dao"})
public class DataSourceConfig {

    @Autowired()private Environment env;

    @Bean("oracleDataSource")
    public DruidDataSource dataSource(){
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        druidDataSource.setUrl(env.getProperty("database.url"));
        druidDataSource.setUsername(env.getProperty("database.name"));
        druidDataSource.setPassword(env.getProperty("database.password"));
        druidDataSource.setMaxActive(20);
        druidDataSource.setInitialSize(1);
        druidDataSource.setMaxWait(180000);
        druidDataSource.setMinIdle(1);
        druidDataSource.setTimeBetweenEvictionRunsMillis(60000);
        druidDataSource.setMinEvictableIdleTimeMillis(300000);
        druidDataSource.setTestOnBorrow(false);
        druidDataSource.setTestWhileIdle(true);
        druidDataSource.setTestOnReturn(false);
        druidDataSource.setRemoveAbandoned(true);
        druidDataSource.setRemoveAbandonedTimeout(7200);
        druidDataSource.setLogAbandoned(false);
        return druidDataSource;
    }

    @Bean("masterSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() {
        SqlSessionFactory factory = null;
        try {
            SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
            bean.setDataSource(dataSource());
            bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/oracle/*.xml"));
            bean.setTypeAliasesPackage("com.synway.reconciliation.pojo");
            factory = bean.getObject();
        } catch (IOException e) {
            throw SystemException.asSystemException(ErrorCode.FILE_GET_ERROR, String.format("加载资源文件:%s 失败.", "classpath:/mapper/oracle/*.xml"), e);
        } catch (Exception e) {
            throw SystemException.asSystemException(ErrorCode.FACTORY_GET_ERROR, e);
        }
        return factory;
    }


    /**
     *
     * 配置更多的mybatis配置信息
     * @return
     */
    public org.apache.ibatis.session.Configuration mybatisConfiguration(){
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();

        return configuration;
    }

    @Bean
    public PlatformTransactionManager oracleTransactionManager(@Qualifier("oracleDataSource") DruidDataSource datasource){
            return  new DataSourceTransactionManager(datasource);
    }

    @Bean
    public String myInterceptor(SqlSessionFactory sqlSessionFactory){
        sqlSessionFactory.getConfiguration().addInterceptor(new SqlExecutorInterceptor());
        return "interceptor";
    }
}
