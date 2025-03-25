package com.synway.reconciliation.interceptor;

import com.synway.reconciliation.common.Constants;
import com.synway.reconciliation.pojo.AuthorizedUser;
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

import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.Properties;

/**
 * SQL拦截
 * @author
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
@Component
@Slf4j
public class SqlExecutorInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        BoundSql boundSql = statementHandler.getBoundSql();
        AuthorizedUser userInfo = AuthorizedUserUtils.getInstance().getAuthor();
        if (null == userInfo) {
            log.error("本次请求:" + Thread.currentThread().getId() + "没有用户信息");
            return invocation.proceed();
        }
        MetaObject metaObject = MetaObject.forObject(statementHandler, new DefaultObjectFactory()
                , new DefaultObjectWrapperFactory(), new DefaultReflectorFactory());
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        // 只修改拦截select语句的信息
        if (!StringUtils.equalsIgnoreCase(Constants.SELECT, mappedStatement.getSqlCommandType().toString())) {
            return invocation.proceed();
        }
        Class<?> classType = Class.forName(mappedStatement.getId().substring(0, mappedStatement.getId().lastIndexOf(Constants.CHARACTER_POINT)));
        String mName = mappedStatement.getId().substring(mappedStatement.getId().lastIndexOf(Constants.CHARACTER_POINT) + 1);
        for (Method method : classType.getDeclaredMethods()) {
            // 拥有AuthorControl注解的 才需要修改sql信息
            if(method.isAnnotationPresent(AuthorControl.class)
                    && (mName.equalsIgnoreCase(method.getName()) || mName.equalsIgnoreCase(method.getName()+"_count"))){
                AuthorControl authorControl = method.getAnnotation(AuthorControl.class);
                String sql = boundSql.getSql();
                log.info("拦截器StatementInterceptor拦截前的sql语句为:" + (StringUtils.isBlank(sql) ? "" :
                        sql.replaceAll("\n", " ")));
                String newSql = SqlAuthorParse.getNewSql(sql, authorControl, userInfo);
                log.info("拦截后的sql语句为:" + newSql);
                metaObject.setValue("delegate.boundSql.sql", newSql);
                return invocation.proceed();
            }
        }
        return invocation.proceed();
    }

    /**
     * 是否触发拦截
     *
     * @param target
     * @return
     */
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    /**
     * 给自定义拦截器传递xml配置的属性参数
     *
     * @param properties
     */
    @Override
    public void setProperties(Properties properties) {

    }
}
