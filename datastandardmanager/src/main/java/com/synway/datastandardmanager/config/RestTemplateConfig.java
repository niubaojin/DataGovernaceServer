package com.synway.datastandardmanager.config;

import com.synway.datastandardmanager.interceptor.RestTemplateTrackInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * @author
 * @date 2018/11/7 13:58
 */
@Configuration
public class RestTemplateConfig {
   
    /**
     * 调用时均衡负载，@LoadBalanced必需要加上
     * @return
     */
    @LoadBalanced
    @Bean
    public RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Collections.singletonList(new RestTemplateTrackInterceptor()));
        return restTemplate;
    }

}
