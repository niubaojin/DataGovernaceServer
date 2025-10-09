package com.synway.dataoperations.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.github.pagehelper.PageInterceptor;
import com.synway.dataoperations.constant.Common;
import com.synway.dataoperations.interceptor.SqlExecutorInterceptor;
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

    @Resource
    private SqlInterceptor sqlInterceptor;

    @Bean
    @Primary
    public DruidDataSource dataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();

        String dataType = environment.getProperty("database.type");
        if (Common.DAMENG.equalsIgnoreCase(dataType)) {
            druidDataSource.setDriverClassName("dm.jdbc.driver.DmDriver");
        }
        if (Common.ORACLE.equalsIgnoreCase(dataType)) {
            druidDataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        }
        if (Common.HAILIANG.equalsIgnoreCase(dataType) || Common.VASTBASE.equalsIgnoreCase(dataType)) {
            druidDataSource.setDriverClassName("cn.com.vastbase.Driver");
            druidDataSource.setValidationQuery("select 'x'");
            druidDataSource.setValidationQueryTimeout(60000);
        }
        if (Common.POSTGRESQL.equalsIgnoreCase(dataType)) {
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
    public MybatisSqlSessionFactoryBean sqlSessionFactory() throws Exception {
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setDataSource(dataSource());
        String dataType = environment.getProperty("database.type");
        if (Common.DAMENG.equalsIgnoreCase(dataType)) {
            bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/daMeng/*.xml"));
        }
        if (Common.ORACLE.equalsIgnoreCase(dataType)) {
            bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/oracle/*.xml"));
        }
        if (Common.HAILIANG.equalsIgnoreCase(dataType) || Common.VASTBASE.equalsIgnoreCase(dataType)) {
            bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/hailiang/*.xml"));
        }
        if (Common.POSTGRESQL.equalsIgnoreCase(dataType)) {
            bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/postgresql/*.xml"));
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
        if (Common.HAILIANG.equalsIgnoreCase(dataType)
                || Common.VASTBASE.equalsIgnoreCase(dataType)
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
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Primary
    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Primary
    @Bean
    public String myInterceptor(SqlSessionFactory sqlSessionFactory) {
        sqlSessionFactory.getConfiguration().addInterceptor(new SqlExecutorInterceptor(environment));
        return "interceptor";
    }


}
