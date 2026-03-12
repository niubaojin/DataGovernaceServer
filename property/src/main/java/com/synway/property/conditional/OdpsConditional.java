package com.synway.property.conditional;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author majia
 * @version 1.0
 * @date 2020/7/8 17:31
 */
@RefreshScope
@DependsOn("restTemplateBalanced")
public class OdpsConditional implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        // nacos增加平台类型配置项
        Environment environment = context.getEnvironment();
        String platformType = environment.getProperty("platformType");
        if (platformType.toLowerCase().equalsIgnoreCase("ali")){
            return true;
        }else {
            return false;
        }
    }

    public static  boolean isEmptyString(String args){
        if(args.isEmpty()){
            return true;
        }else{
            return false;
        }
    }

}
