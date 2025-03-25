package com.synway.datarelation.config.database;

import com.alibaba.druid.pool.DruidDataSource;
import com.synway.datarelation.constant.Common;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * @ClassName DataSourceConfig
 * @description 这个是oracle数据库的连接方式
 * @author wangdongwei
 * @date 2020/8/19 17:33
 */
@Configuration
@Conditional(OracleConditional.class)
public class OracleDataResourceConfig {

    @Autowired()
    private Environment env;

    @Bean("dataGovernanceOracle")
    @Primary
    public DruidDataSource dataSource(){
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(Common.ORACLE_CLASS);
        druidDataSource.setUrl(env.getProperty("database.url"));
        druidDataSource.setUsername(env.getProperty("database.name"));
        druidDataSource.setPassword(env.getProperty("database.password"));
        druidDataSource.setMaxActive(10);
        druidDataSource.setInitialSize(1);
        druidDataSource.setMaxWait(180000);
        druidDataSource.setMinIdle(1);
        druidDataSource.setTimeBetweenEvictionRunsMillis(60000);
        druidDataSource.setMinEvictableIdleTimeMillis(300000);
        druidDataSource.setTestOnBorrow(false);
        druidDataSource.setTestWhileIdle(true);
        druidDataSource.setTestOnReturn(false);
        druidDataSource.setRemoveAbandoned(true);
        druidDataSource.setRemoveAbandonedTimeout(180);
        druidDataSource.setLogAbandoned(false);
        return druidDataSource;
    }

    @Bean("masterSqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory() throws Exception{
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource());
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/oracle/*.xml"));
        bean.setTypeAliasesPackage("com.synway.datarelation.pojo");
        return bean.getObject();
    }

    @Bean(name = "transactionManager")
    @Primary
    public DataSourceTransactionManager transactionManager(){
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean(name = "sqlSessionTemplate")
    @Primary
    public SqlSessionTemplate sqlSessionTemplate() throws Exception{
        SqlSessionTemplate template= new SqlSessionTemplate(sqlSessionFactory());
        return template;
    }

    /**
     *
     * 配置更多的mybatis配置信息
     * @return
     */
//    public org.apache.ibatis.session.Configuration mybatisConfiguration(){
//        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
//        return configuration;
//    }




}
