package com.synway.property.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.github.pagehelper.PageInterceptor;
import com.synway.property.common.Common;
import com.synway.property.interceptor.SqlExecutorInterceptor;
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
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Configuration
@MapperScan(basePackages = {"com.synway.property.dao", "com.synway.property.mapper"}, sqlSessionFactoryRef = "masterSqlSessionFactory")
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

    @Bean(name="datasource")
    @Primary
    public DruidDataSource dataSource(){
        DruidDataSource druidDataSource = new DruidDataSource();

        String dataType = environment.getProperty("database.type");
        if(Common.DAMENG.equalsIgnoreCase(dataType)){
            druidDataSource.setDriverClassName("dm.jdbc.driver.DmDriver");
        }
        if(Common.KINGBASE.equalsIgnoreCase(dataType)){
            druidDataSource.setDriverClassName("com.kingbase8.Driver");
        }
        if(Common.ORACLE.equalsIgnoreCase(dataType)){
            druidDataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        }
        if(Common.VASTDATA.equalsIgnoreCase(dataType)){
            druidDataSource.setDriverClassName("cn.com.vastbase.Driver");
            druidDataSource.setValidationQuery("select 'x'");
            druidDataSource.setValidationQueryTimeout(60000);
        }
        if(Common.POSTGRESQL.equalsIgnoreCase(dataType)){
            druidDataSource.setDriverClassName("org.postgresql.Driver");
        }
        if(Common.MYSQL.equalsIgnoreCase(dataType)){
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

    /*用于连接统计hbase信息的数据库*/
    @Bean
    public DruidDataSource dataSourceForStatisHbase(){
        DruidDataSource druidDataSource = new DruidDataSource();

        String dataType = environment.getProperty("databaseStatisHbase.type");
        if(Common.DAMENG.equalsIgnoreCase(dataType)){
            druidDataSource.setDriverClassName("dm.jdbc.driver.DmDriver");
        }
        if(Common.KINGBASE.equalsIgnoreCase(dataType)){
            druidDataSource.setDriverClassName("com.kingbase8.Driver");
        }
        if(Common.ORACLE.equalsIgnoreCase(dataType)){
            druidDataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        }
        if(Common.VASTDATA.equalsIgnoreCase(dataType)){
            druidDataSource.setDriverClassName("cn.com.vastbase.Driver");
            druidDataSource.setValidationQuery("select 'x'");
            druidDataSource.setValidationQueryTimeout(60000);
        }
        if(Common.POSTGRESQL.equalsIgnoreCase(dataType)){
            druidDataSource.setDriverClassName("org.postgresql.Driver");
        }
        if(Common.MYSQL.equalsIgnoreCase(dataType)){
            druidDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
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
    public MybatisSqlSessionFactoryBean masterSqlSessionFactory(@Qualifier("datasource") DataSource dataSource) throws Exception{
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        String dataType = environment.getProperty("database.type");
        if(Common.DAMENG.equalsIgnoreCase(dataType)){
            bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/daMeng/*.xml"));
        }
        if(Common.KINGBASE.equalsIgnoreCase(dataType)){
            bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/kingBase/*.xml"));
        }
        if(Common.ORACLE.equalsIgnoreCase(dataType)){
            bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/oracle/*.xml"));
        }
        if(Common.VASTDATA.equalsIgnoreCase(dataType)){
            bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/vastdata/*.xml"));
        }
        if(Common.POSTGRESQL.equalsIgnoreCase(dataType)){
            bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/postgresql/*.xml"));
        }
        if(Common.MYSQL.equalsIgnoreCase(dataType)){
            bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/mysql/*.xml"));
        }

        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setMapUnderscoreToCamelCase(false);
        // 如果数据为空，则用null代替（用于返回map时，值为null也能获取到字段名）
        configuration.setCallSettersOnNulls(true);
        configuration.setLogPrefix("mybatis.");

        //增加分页处理器
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        paginationInnerInterceptor.setDbType(DbConfigProperties.getMyBatisPlusDbType(dataType.toLowerCase()));
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());

        //分页插件
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        if (Common.VASTDATA.equalsIgnoreCase(dataType)
                || Common.DAMENG.equalsIgnoreCase(dataType)
                || Common.ORACLE.equalsIgnoreCase(dataType)
                || Common.GAUSS.equalsIgnoreCase(dataType)) {
            interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.ORACLE));
            properties.setProperty("helperDialect", "oracle");
        }
        if (Common.MYSQL.equalsIgnoreCase(dataType)) {
            interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
            properties.setProperty("helperDialect", "mysql");
        }
        if (Common.POSTGRESQL.equalsIgnoreCase(dataType)) {
            interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.POSTGRE_SQL));
            properties.setProperty("helperDialect", "postgresql");
        }
        pageInterceptor.setProperties(properties);
        bean.addPlugins(pageInterceptor);
        configuration.addInterceptor(interceptor);
        bean.setConfiguration(configuration);

        MybatisConfiguration configuration1 = new MybatisConfiguration();
        configuration1.addInterceptor(new SqlExecutorInterceptor(environment));
        bean.setConfiguration(configuration1);

        return bean;
    }

    @Bean
    public PlatformTransactionManager transactionManager(@Qualifier("datasource") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
