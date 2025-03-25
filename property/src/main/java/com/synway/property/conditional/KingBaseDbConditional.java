package com.synway.property.conditional;

import com.synway.property.common.UrlConstants;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author majia
 * @version 1.0
 * @date 2020/7/9 13:44
 */
public class KingBaseDbConditional  implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Environment environment = context.getEnvironment();
        String msgMethod = environment.getProperty("database.type");
        if(UrlConstants.KINGBASE.equalsIgnoreCase(msgMethod)){
            return true;
        }else{
            return false;
        }
    }
}
