package com.synway.dataoperations.config;

import com.synway.dataoperations.interceptor.GlobalInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 需要对静态资源进行放行   ,"/asserts/**","/webjars/**"
        registry.addInterceptor(new GlobalInterceptor())
                .excludePathPatterns("/**/*.js","/**/*.css","/**/*.png")
                .addPathPatterns("/**");
    }

}
