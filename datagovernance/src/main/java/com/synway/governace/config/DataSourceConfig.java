package com.synway.governace.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.github.pagehelper.PageInterceptor;
import com.synway.governace.common.DataBaseConstant;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.annotation.Resource;
import java.util.Properties;


@Configuration
public class DataSourceConfig {

    @Resource
    private Environment environment;

    @Bean
    @Primary
    public DruidDataSource dataSource(){
        DruidDataSource druidDataSource = new DruidDataSource();

        String dataType = environment.getProperty("database.type");
        if(DataBaseConstant.DAMENG.equalsIgnoreCase(dataType)){
            druidDataSource.setDriverClassName("dm.jdbc.driver.DmDriver");
        }
        if(DataBaseConstant.ORACLE.equalsIgnoreCase(dataType)){
            druidDataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        }
        if(DataBaseConstant.HAILIANG.equalsIgnoreCase(dataType) || DataBaseConstant.VASTBASE.equalsIgnoreCase(dataType)){
            druidDataSource.setDriverClassName("cn.com.vastbase.Driver");
            druidDataSource.setValidationQuery("select 'x'");
            druidDataSource.setValidationQueryTimeout(60000);
        }
        if(DataBaseConstant.POSTGRESQL.equalsIgnoreCase(dataType)){
            druidDataSource.setDriverClassName("org.postgresql.Driver");
        }
        if(DataBaseConstant.MYSQL.equalsIgnoreCase(dataType)){
            druidDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
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
    public MybatisSqlSessionFactoryBean sqlSessionFactory() throws Exception{
        String dataType = environment.getProperty("database.type");
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setDataSource(dataSource());

        //  如果数据为空，则用null代替（用于返回map时，值为null也能获取到字段名）
        MybatisConfiguration configuration = new com.baomidou.mybatisplus.core.MybatisConfiguration();
        configuration.setCallSettersOnNulls(true);
        configuration.setLogPrefix("mybatis.");
        if(DataBaseConstant.DAMENG.equalsIgnoreCase(dataType)){
            bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mybatisMapper/dameng/*.xml"));
            configuration.setDatabaseId("dm");
        }
        if(DataBaseConstant.ORACLE.equalsIgnoreCase(dataType)){
            bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mybatisMapper/oracle/*.xml"));
            configuration.setDatabaseId("oracle");
        }
        if(DataBaseConstant.HAILIANG.equalsIgnoreCase(dataType) || DataBaseConstant.VASTBASE.equalsIgnoreCase(dataType)){
            bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mybatisMapper/hailiang/*.xml"));
            configuration.setDatabaseId("postgresql");
        }
        if(DataBaseConstant.POSTGRESQL.equalsIgnoreCase(dataType)){
            bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mybatisMapper/postgresql/*.xml"));
            configuration.setDatabaseId("postgresql");
        }
        if(DataBaseConstant.MYSQL.equalsIgnoreCase(dataType)){
            bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mybatisMapper/mysql/*.xml"));
            configuration.setDatabaseId("mysql");
        }

        //增加分页处理器
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        String dbType = dataType.equalsIgnoreCase("hailiang") ? "vastbase" : dataType;
        paginationInnerInterceptor.setDbType(DbConfigProperties.getMyBatisPlusDbType(dbType.toUpperCase()));
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());

        //分页插件
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        if (DataBaseConstant.HAILIANG.equalsIgnoreCase(dataType)
            || DataBaseConstant.VASTBASE.equalsIgnoreCase(dataType)
            || DataBaseConstant.DAMENG.equalsIgnoreCase(dataType)
            || DataBaseConstant.ORACLE.equalsIgnoreCase(dataType)) {
            interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.ORACLE));
            properties.setProperty("helperDialect", "oracle");
        }
        if (DataBaseConstant.MYSQL.equalsIgnoreCase(dataType)) {
            interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
            properties.setProperty("helperDialect", "mysql");
        }
        if (DataBaseConstant.POSTGRESQL.equalsIgnoreCase(dataType)) {
            interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.POSTGRE_SQL));
            properties.setProperty("helperDialect", "postgresql");
        }
        pageInterceptor.setProperties(properties);
        bean.addPlugins(pageInterceptor);
        bean.setConfiguration(configuration);
        configuration.addInterceptor(interceptor);

//        //拦截语句用，替换关键字
//        MybatisConfiguration configuration1 = new MybatisConfiguration();
//        configuration1.addInterceptor(sqlInterceptor);
//        bean.setConfiguration(configuration1);

//        bean.setTypeAliasesPackage("com.synway.governace.pojo");
        return bean;
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
