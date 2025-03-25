package com.synway.property.conditional;

import com.aliyun.odps.utils.StringUtils;
import com.synway.property.interceptor.RestTemplateTrackInterceptor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.MethodMetadata;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * @author majia
 * @version 1.0
 * @date 2020/7/8 17:31
 */
@RefreshScope
@DependsOn("restTemplateBalanced")
public class OdpsConditional implements Condition {
    /**
     * Determine if the condition matches.
     *
     * @param context  the condition context
     * @param metadata metadata of the {@link AnnotationMetadata class}
     *                 or {@link MethodMetadata method} being checked
     * @return {@code true} if the condition matches and the component can be registered,
     * or {@code false} to veto the annotated component's registration
     */
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Collections.singletonList(new RestTemplateTrackInterceptor()));
        Environment environment = context.getEnvironment();

//        String nginxIp = environment.getProperty("nginxIp");
//        String result = restTemplate.getForObject(nginxIp + "/factorygateway/dataresource/dataresource/api/getDataResourceByisLocal?isLocal=2&isApproved=0", String.class);
//        if(StringUtils.isNotBlank(result)&&result.toLowerCase().contains("odps")){
//            return true;
//        }else{
//            return false;
//        }
        // nacos增加平台类型配置项
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
