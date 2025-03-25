package com.synway.property.config;


import com.alibaba.druid.pool.DruidDataSource;
import com.synway.property.conditional.DaMengDbConditional;
import com.synway.property.interceptor.SqlExecutorInterceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;


/**
 * 这个是达梦数据库的连接方式
 * @author majia
 */
@Configuration
@Conditional(DaMengDbConditional.class)
public class DaMengDataSourceConfig {

    @Autowired()private Environment env;



    @Bean
    @Primary
    public DruidDataSource dataSource(){
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName("dm.jdbc.driver.DmDriver");
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
        druidDataSource.setRemoveAbandonedTimeout(1800);
        druidDataSource.setLogAbandoned(false);
        return druidDataSource;
    }


    @Bean("masterSqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory() throws Exception{
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource());
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/daMeng/**/*.xml"));
        bean.setTypeAliasesPackage("com.synway.property.pojo");
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
    public org.apache.ibatis.session.Configuration mybatisConfiguration(){
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        return configuration;
    }

    @Bean
    public String myInterceptor(SqlSessionFactory sqlSessionFactory){
        sqlSessionFactory.getConfiguration().addInterceptor(new SqlExecutorInterceptor());
        return "interceptor";
    }


}
