package com.synway.dataoperations.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.Properties;

@Slf4j
@Component
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class SqlInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        BoundSql boundSql = statementHandler.getBoundSql();

        MetaObject metaObject = MetaObject.forObject(statementHandler, new DefaultObjectFactory(), new DefaultObjectWrapperFactory(), new DefaultReflectorFactory());
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");

        // 只修改拦截insert语句的信息
        if (!StringUtils.equalsIgnoreCase("insert", mappedStatement.getSqlCommandType().toString())) {
            return invocation.proceed();
        }
        String sql = boundSql.getSql();
        sql = sql.replaceAll(" CONSTRAINT,", " \"constraint\",")
                .replaceAll(",CONSTRAINT ", ",\"constraint\" ")
                .replaceAll(",CONSTRAINT,", ",\"constraint\",")
                .replaceAll(" CONSTRAINT ", " \"constraint\" ")
                .replaceAll(" \'CONSTRAINT\',", " \"constraint\",")
                .replaceAll(",\'CONSTRAINT\' ", ",\"constraint\" ")
                .replaceAll(",\'CONSTRAINT\',", ",\"constraint\",")
                .replaceAll(" \'CONSTRAINT\' ", " \"constraint\" ");
        log.info(">>>>>>新的插入sql语句为：{}", sql);
        metaObject.setValue("delegate.boundSql.sql", sql);

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }

}
