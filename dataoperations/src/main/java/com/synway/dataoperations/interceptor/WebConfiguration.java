//package com.synway.dataoperations.interceptor;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
///**
// * @author wangdongwei
// * @date 2021/5/25 9:48
// */
//@Configuration
//public class WebConfiguration implements WebMvcConfigurer {
//
//
////    @Override
////    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> list){
////        list.add(new CurrentUserHandlerMethodArgReslover());
////    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry){
//        // 需要对静态资源进行放行   ,"/asserts/**","/webjars/**"
//        registry.addInterceptor(new GlobalInterceptor())
//                .excludePathPatterns("/**/*.js","/**/*.css","/**/*.png")
//                .addPathPatterns("/**");
//    }
//
//}
