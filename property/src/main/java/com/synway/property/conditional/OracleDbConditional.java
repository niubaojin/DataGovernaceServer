package com.synway.property.conditional;

import com.synway.property.common.UrlConstants;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 *  判断是否使用了 oracle数据库
 * @author majia
 */
public class OracleDbConditional implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Environment environment = context.getEnvironment();
        String msgMethod = environment.getProperty("database.type");
        if(UrlConstants.ORACLE.equalsIgnoreCase(msgMethod)){
            return true;
        }else{
            return false;
        }
    }
}
