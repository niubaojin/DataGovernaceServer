package com.synway.datarelation.config.database;

import com.synway.datarelation.constant.Common;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;


/**
 * @ClassName OracleConditional
 * @description 后台数据库是否使用oracle数据库
 * @author wangdongwei
 * @date 2020/8/19 17:33
 */
public class OracleConditional implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Environment environment = context.getEnvironment();
        String msgMethod = environment.getProperty("database.type");
        if (StringUtils.isBlank(msgMethod)){
            throw new RuntimeException("配置异常，未找到数据库配置类型");
        }
        if(Common.ORACLE.equalsIgnoreCase(msgMethod)){
            return true;
        }else{
            return false;
        }
    }
}
