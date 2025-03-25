//package com.synway.datarelation.config.database;
//
//
//import com.alibaba.druid.pool.DruidDataSource;
//import com.synway.datarelation.constant.Common;
//import org.apache.ibatis.session.AutoMappingBehavior;
//import org.apache.ibatis.session.ExecutorType;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Conditional;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.env.Environment;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//
//
///**
// * @ClassName CorsConfig
// * @description 这个是达梦数据库的连接方式
// * @author wangdongwei
// * @date 2020/8/19 17:33
// */
//@Configuration
//@Conditional(DaMengConditional.class)
//public class DaMengDataSourceConfig {
//
//    @Autowired()private Environment env;
//
//
//
//    @Bean
//    @Primary
//    public DruidDataSource dataSource(){
//        DruidDataSource druidDataSource = new DruidDataSource();
//        druidDataSource.setDriverClassName(Common.DAMENG_CLASS);
//        druidDataSource.setUrl(env.getProperty("database.url"));
//        druidDataSource.setUsername(env.getProperty("database.name"));
//        druidDataSource.setPassword(env.getProperty("database.password"));
//        druidDataSource.setMaxActive(10);
//        druidDataSource.setInitialSize(1);
//        druidDataSource.setMaxWait(180000);
//        druidDataSource.setMinIdle(1);
//        druidDataSource.setTimeBetweenEvictionRunsMillis(60000);
//        druidDataSource.setMinEvictableIdleTimeMillis(300000);
//        druidDataSource.setTestOnBorrow(false);
//        druidDataSource.setTestWhileIdle(true);
//        druidDataSource.setTestOnReturn(false);
//        druidDataSource.setRemoveAbandoned(true);
//        druidDataSource.setRemoveAbandonedTimeout(180);
//        druidDataSource.setLogAbandoned(false);
//        return druidDataSource;
//    }
//
//
//    @Bean("masterSqlSessionFactory")
//    @Primary
//    public SqlSessionFactory sqlSessionFactory() throws Exception{
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(dataSource());
//        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/daMeng/*.xml"));
//        bean.setTypeAliasesPackage("com.synway.datarelation.pojo");
////        Interceptor[] plugins = new Interceptor[2];
////        plugins[0] = new PageInterceptor();
////        Properties properties = new Properties();
////        properties.setProperty("reasonable","true");
////        properties.setProperty("helperDialect","oracle");
////        plugins[0].setProperties(properties);
////        plugins[1] = new SqlCostInterceptor();
////        bean.setPlugins(plugins);
//
//        return bean.getObject();
//    }
//
//    @Bean(name = "transactionManager")
//    @Primary
//    public DataSourceTransactionManager transactionManager(){
//        return new DataSourceTransactionManager(dataSource());
//    }
//
//    @Bean(name = "sqlSessionTemplate")
//    @Primary
//    public SqlSessionTemplate sqlSessionTemplate() throws Exception{
//        SqlSessionTemplate template= new SqlSessionTemplate(sqlSessionFactory());
//        return template;
//    }
//
//    /**
//     *
//     * 配置更多的mybatis配置信息
//     * @return
//     */
//    public org.apache.ibatis.session.Configuration mybatisConfiguration(){
//        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
//        configuration.setCacheEnabled(true);
//        configuration.setLazyLoadingEnabled(true);
//        configuration.setAggressiveLazyLoading(false);
//        configuration.setMultipleResultSetsEnabled(true);
//        configuration.setUseColumnLabel(true);
//        configuration.setUseGeneratedKeys(false);
//        configuration.setAutoMappingBehavior(AutoMappingBehavior.FULL);
//        configuration.setDefaultExecutorType(ExecutorType.REUSE);
//        configuration.setDefaultStatementTimeout(25000);
//
//        return configuration;
//    }
//
//
//
//}
