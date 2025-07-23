package com.synway.datastandardmanager.interceptor;

import com.synway.datastandardmanager.constant.Common;
import com.synway.datastandardmanager.pojo.LoginUser;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.Properties;

/**
 * @author wangdongwei
 * @date 2021/5/26 9:59
 */
@Intercepts({@Signature(type = StatementHandler.class,method = "prepare",args = {Connection.class,Integer.class})})
@Component
@Slf4j
public class SqlExecutorInterceptor implements Interceptor {

    private final Environment env;

    // 通过构造函数注入 Environment
    @Autowired
    public SqlExecutorInterceptor(Environment env) {
        this.env = env;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler =  (StatementHandler)invocation.getTarget();
        // 这个是获取参数信息，如果没有  author 这个参数名称/这个里面的值为null/为管理员权限
        // 则不进行sql权限拦截和修改
        BoundSql boundSql = statementHandler.getBoundSql();
        LoginUser loginUser = AuthorizedUserUtils.getInstance().getAuthor();
        if(loginUser == null){
            log.info("本次请求:"+Thread.currentThread().getId()+"没有用户信息");
            return invocation.proceed();
        }
        if(loginUser.isAdmin()){
            log.info("本次请求:"+Thread.currentThread().getId()+"管理员权限，无需进行数据库拦截");
            return invocation.proceed();
        }
        MetaObject metaObject = MetaObject.forObject(statementHandler,new DefaultObjectFactory()
                ,new DefaultObjectWrapperFactory(),new DefaultReflectorFactory());
        MappedStatement mappedStatement = (MappedStatement)metaObject.getValue("delegate.mappedStatement");
        // 只修改拦截select语句的信息
        if(!StringUtils.equalsIgnoreCase(Common.SELECT ,mappedStatement.getSqlCommandType().toString())){
            return invocation.proceed();
        }
        Class<?> classType = Class.forName(mappedStatement.getId().substring(0,mappedStatement.getId().lastIndexOf(".")));
        String mName = mappedStatement.getId().substring(mappedStatement.getId().lastIndexOf(".")+1);
        String dsType = env.getProperty("database.type").toLowerCase();
        for(Method method: classType.getDeclaredMethods()){
            // 拥有这个注解的 才需要修改sql信息 AuthorControl
            if(method.isAnnotationPresent(AuthorControl.class)
                    && (mName.equalsIgnoreCase(method.getName()) || mName.equalsIgnoreCase(method.getName()+"_count"))){
                AuthorControl authorControl = method.getAnnotation(AuthorControl.class);
                String sql = boundSql.getSql();
                log.info("拦截器StatementInterceptor拦截前的sql语句为:"+(StringUtils.isBlank(sql)?"":
                        sql.replaceAll("\n"," ")));
                String newSql = SqlAuthorParse.getNewSql(sql,authorControl,loginUser,mName, dsType);
                metaObject.setValue("delegate.boundSql.sql", newSql);
                return invocation.proceed();
            }
        }
        return invocation.proceed();
    }

    /**
     * 是否触发拦截
     * @param target
     * @return
     */
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target,this);
    }

    /**
     * 给自定义拦截器传递xml配置的属性参数
     * @param properties
     */
    @Override
    public void setProperties(Properties properties) {

    }
}
