//package com.synway.datarelation.config.database;
//
//import com.synway.datarelation.constant.Common;
//import org.springframework.context.annotation.Condition;
//import org.springframework.context.annotation.ConditionContext;
//import org.springframework.core.env.Environment;
//import org.springframework.core.type.AnnotatedTypeMetadata;
//
///**
// * @ClassName DaMengDbConditional
// * @description 后台数据库是否为达梦数据库
// * @author wangdongwei
// * @date 2020/8/19 17:33
// */
//public class DaMengConditional implements Condition {
//    @Override
//    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
//        Environment environment = context.getEnvironment();
//        String msgMethod = environment.getProperty("database.type");
//        if(Common.DAMENG.equalsIgnoreCase(msgMethod)){
//            return true;
//        }else{
//            return false;
//        }
//    }
//}
