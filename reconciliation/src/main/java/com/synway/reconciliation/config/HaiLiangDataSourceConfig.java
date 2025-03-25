package com.synway.reconciliation.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.synway.reconciliation.conditional.DamengDbCondition;
import com.synway.reconciliation.conditional.HaiLiangDbCondition;
import com.synway.reconciliation.dao.AlarmSettingDao;
import com.synway.reconciliation.interceptor.SqlExecutorInterceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * 达梦数据源
 * @author ywj
 */
@Configuration
@Conditional(HaiLiangDbCondition.class)
@MapperScan(basePackages = {"com.synway.reconciliation.dao"})
public class HaiLiangDataSourceConfig {

    @Autowired
    private Environment env;

    @Bean("hailiangDataSource")
    public DruidDataSource dataSource(){
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName("cn.com.vastbase.Driver");
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
    public SqlSessionFactory sqlSessionFactory() throws Exception{
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource());
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/hailiang/*.xml"));
        bean.setTypeAliasesPackage("com.synway.reconciliation.pojo");
        return bean.getObject();
    }

    @Bean
    public PlatformTransactionManager oracleTransactionManager(@Qualifier("hailiangDataSource") DruidDataSource datasource){
            return  new DataSourceTransactionManager(datasource);
    }

    @Bean
    public String myInterceptor(SqlSessionFactory sqlSessionFactory){
        sqlSessionFactory.getConfiguration().addInterceptor(new SqlExecutorInterceptor());
        return "interceptor";
    }

}
