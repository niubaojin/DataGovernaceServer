package com.synway.reconciliation.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web配置
 * @author
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new GlobalInterceptor())
                .excludePathPatterns("/**/*.js","/**/*.css","/**/*.png")
                .addPathPatterns("/**");
    }

}
