package com.synway.datastandardmanager.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.github.pagehelper.PageInterceptor;
import com.synway.datastandardmanager.constants.Common;
import com.synway.datastandardmanager.interceptor.SqlExecutorInterceptor;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import java.util.Properties;


@Configuration
@MapperScan(basePackages = "com.synway.datastandardmanager.mapper", sqlSessionFactoryRef = "masterSqlSessionFactory")
public class DataSourceConfig {

    @Resource
    private Environment environment;
    @Resource
    private SqlInterceptor sqlInterceptor;

    public static String dsType;

    @PostConstruct
    public void init() {
        dsType = environment.getProperty("database.type", "").toLowerCase();
    }

    @Bean
    @Primary
    public DruidDataSource dataSource(){
        DruidDataSource druidDataSource = new DruidDataSource();

        if(Common.DAMENG.equalsIgnoreCase(dsType)){
            druidDataSource.setDriverClassName("dm.jdbc.driver.DmDriver");
        }
        if(Common.KINGBASE.equalsIgnoreCase(dsType)){
            druidDataSource.setDriverClassName("com.kingbase8.Driver");
        }
        if(Common.ORACLE.equalsIgnoreCase(dsType)){
            druidDataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        }
        if(Common.VASTBASE.equalsIgnoreCase(dsType) || Common.HAILIANG.equalsIgnoreCase(dsType)){
            druidDataSource.setDriverClassName("cn.com.vastbase.Driver");
            druidDataSource.setValidationQuery("select 'x'");
            druidDataSource.setValidationQueryTimeout(60000);
        }
        if(Common.POSTGRESQL.equalsIgnoreCase(dsType)){
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
    public MybatisSqlSessionFactoryBean masterSqlSessionFactory() throws Exception{
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setDataSource(dataSource());

        //  如果数据为空，则用null代替（用于返回map时，值为null也能获取到字段名）
        MybatisConfiguration configuration = new com.baomidou.mybatisplus.core.MybatisConfiguration();
        configuration.setCallSettersOnNulls(true);
        configuration.setLogPrefix("mybatis.");

        if(Common.DAMENG.equalsIgnoreCase(dsType)){
            bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/Dameng/*.xml"));
            configuration.setDatabaseId("dm");
        }
        if(Common.KINGBASE.equalsIgnoreCase(dsType)){
            bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/kingBase/*.xml"));
            configuration.setDatabaseId("kingbase");
        }
        if(Common.ORACLE.equalsIgnoreCase(dsType)){
            bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/oracle/*.xml"));
            configuration.setDatabaseId("oracle");
        }
        if(Common.VASTBASE.equalsIgnoreCase(dsType) || Common.HAILIANG.equalsIgnoreCase(dsType)){
            bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/hailiang/*.xml"));
            configuration.setDatabaseId("postgresql");
        }
        if(Common.POSTGRESQL.equalsIgnoreCase(dsType)){
            bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/postgresql/*.xml"));
            configuration.setDatabaseId("postgresql");
        }
        bean.setConfiguration(configuration);

        //增加分页处理器
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        String dbType = dsType.equalsIgnoreCase("hailiang") ? "vastbase" : dsType;
        paginationInnerInterceptor.setDbType(DbConfigProperties.getMyBatisPlusDbType(dbType));
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());

        //分页插件
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        if (Common.VASTBASE.equalsIgnoreCase(dsType)
                || Common.HAILIANG.equalsIgnoreCase(dsType)
                || Common.DAMENG.equalsIgnoreCase(dsType)
                || Common.ORACLE.equalsIgnoreCase(dsType)
                || Common.GAUSS.equalsIgnoreCase(dsType)) {
            interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.ORACLE));
            properties.setProperty("helperDialect", "oracle");
        }
        if (Common.MYSQL.equalsIgnoreCase(dsType)) {
            interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
            properties.setProperty("helperDialect", "mysql");
        }
        if (Common.POSTGRESQL.equalsIgnoreCase(dsType)) {
            interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.POSTGRE_SQL));
            properties.setProperty("helperDialect", "postgresql");
        }
        pageInterceptor.setProperties(properties);
        bean.addPlugins(pageInterceptor);
        bean.setConfiguration(configuration);
        configuration.addInterceptor(interceptor);

        //拦截语句用，替换关键字
        MybatisConfiguration configuration1 = new MybatisConfiguration();
        configuration1.addInterceptor(sqlInterceptor);
        bean.setConfiguration(configuration1);

        return bean;
    }

    @Primary
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager(){
        return new DataSourceTransactionManager(dataSource());
    }

    @Primary
    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("masterSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    public String myInterceptor(SqlSessionFactory sqlSessionFactory){
        sqlSessionFactory.getConfiguration().addInterceptor(new SqlExecutorInterceptor(environment));
        return "interceptor";
    }


}
