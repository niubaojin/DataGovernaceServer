package com.synway.datastandardmanager.aop;

import com.alibaba.fastjson.JSONObject;
import com.synway.datastandardmanager.annotation.Resubmit;
import com.synway.datastandardmanager.config.ResubmitLock;
import com.synway.common.bean.ServerResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author wdw
 * @version 1.0
 * @date 2021/6/16 12:18
 */
@Aspect
@Component
public class ResubmitDataAspect {

    private final static Object PRESENT = new Object();

    @Pointcut(value = "@annotation(com.synway.datastandardmanager.annotation.Resubmit)")
    public void Resubmit(){

    }

    @Around("Resubmit()")
    public Object handleResubmit(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        //获取注解信息
        Resubmit annotation = method.getAnnotation(Resubmit.class);
        int delaySeconds = annotation.delaySeconds();
        Object[] pointArgs = joinPoint.getArgs();
        String key = "";
        //获取第一个参数
        Object firstParam = pointArgs[0];
        if (firstParam != null) {
            //生成加密参数 使用了content_MD5的加密方式
            String paramStr = JSONObject.toJSONString(firstParam);
            key = ResubmitLock.handleKey(paramStr);
        }else{
            return joinPoint.proceed();
        }
        //执行锁
        boolean lock = false;
        try {
            //设置解锁key
            lock = ResubmitLock.getInstance().lock(key, PRESENT);
            if (lock) {
                //放行
                return joinPoint.proceed();
            } else {
                //响应重复提交异常
                return ServerResponse.asErrorResponse("存在重复提交情况");
            }
        } finally {
            //设置解锁key和解锁时间
            ResubmitLock.getInstance().unLock(lock, key, delaySeconds);
        }
    }
}
