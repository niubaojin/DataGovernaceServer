package com.synway.property.config;

import com.synway.property.interceptor.RestTemplateTrackInterceptor;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

/**
 * @author
 * @date 2018/11/7 13:58
 */
@Configuration
public class RestTemplateConfig {
    @LoadBalanced
    @Bean
    @Primary
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(5*60*1000);
        requestFactory.setReadTimeout(5*60*1000);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        restTemplate.setInterceptors(Collections.singletonList(new RestTemplateTrackInterceptor()));
        setRestTemplateEncode(restTemplate);
        return restTemplate;
    }

    //调用外部接口用
    @Bean(value = "restTemplateApi")
    public RestTemplate restTemplateApi() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(5*60*1000);
        requestFactory.setReadTimeout(5*60*1000);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        restTemplate.setInterceptors(Collections.singletonList(new RestTemplateTrackInterceptor()));
        setRestTemplateEncode(restTemplate);
        return restTemplate;
    }

    public static void setRestTemplateEncode(RestTemplate restTemplate) {
        if (null == restTemplate || ObjectUtils.isEmpty(restTemplate.getMessageConverters())) {
            return;
        }

        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        for (int i = 0; i < messageConverters.size(); i++) {
            HttpMessageConverter<?> httpMessageConverter = messageConverters.get(i);
            if (httpMessageConverter.getClass().equals(StringHttpMessageConverter.class)) {
                messageConverters.set(i, new StringHttpMessageConverter(StandardCharsets.UTF_8));
            }
        }
    }

    /**
     * 调用时均衡负载，@LoadBalanced必需要加上
     *
     * @return
     */
    @LoadBalanced
    @Bean(value = "restTemplateBalanced")
    public RestTemplate restTemplateBalanced() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Collections.singletonList(new RestTemplateTrackInterceptor()));
        return restTemplate;
    }

}
