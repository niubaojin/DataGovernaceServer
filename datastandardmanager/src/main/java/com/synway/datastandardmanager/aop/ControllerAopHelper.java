//package com.synway.datastandardmanager.aop;
//
//import com.alibaba.fastjson.JSONObject;
//import com.synway.datastandardmanager.interceptor.AuthorizedUserUtils;
//import com.synway.datastandardmanager.pojo.AuthorizedUser;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * 当请求结束之后，删除全局缓存中的 authorizedUserMap 中 指定线程id
// * @author wangdongwei
// * @date 2021/5/27 17:30
// */
//@Aspect
//@Component
//@Slf4j
//public class ControllerAopHelper {
//
//
//    @Pointcut("execution(public * com.synway.datastandardmanager.controller..*(..)))")
//    public void controllerAuthorizedAop(){
//    }
//
//    @Around("controllerAuthorizedAop()")
//    public Object controllerAuthorizedAop(ProceedingJoinPoint pjp) throws Throwable{
//        try{
//            Object returnStr = (Object)pjp.proceed();
//            return returnStr;
//        }finally {
//            AuthorizedUserUtils.getInstance().removeAuthor();
//        }
//    }
//
//}
