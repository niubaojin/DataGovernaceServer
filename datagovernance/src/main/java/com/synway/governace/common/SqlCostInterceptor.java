package com.synway.governace.common;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.sql.Statement;
import java.util.Properties;

/**
 * mybatis自定义插件
 */
@Intercepts({@Signature(type = StatementHandler.class,method = "query",args={Statement.class,ResultHandler.class}),
        @Signature(type = StatementHandler.class,method = "update",args={Statement.class}),
        @Signature(type = StatementHandler.class,method = "batch",args={Statement.class})
})
@Component
@Configuration
public class SqlCostInterceptor implements Interceptor {
    private static Logger LOGGER = Logger.getLogger(SqlCostInterceptor.class);
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        long startTime = System.currentTimeMillis();
        try{
            return invocation.proceed();
        }finally {
            long endTime = System.currentTimeMillis();
            long costTime = endTime-startTime;
            LOGGER.info("本次SQL执行花费时长["+costTime+"]毫秒");
        }
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o,this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
