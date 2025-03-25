package com.synway.datarelation.aop;

import com.hazelcast.core.HazelcastInstance;
import com.synway.common.exception.ExceptionUtil;
import com.synway.datarelation.config.HazecastLock;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.locks.Lock;

/**
 *  定时任务增加分布式锁
 * @author wangdongwei
 * @date 2021/3/17 15:51
 */
@Aspect
@Component
public class AopHelper {
    private Logger logger = LoggerFactory.getLogger(AopHelper.class);
    private final HazelcastInstance hazelcastInstance;
    @Autowired
    public AopHelper(HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
    }


    @Pointcut(value = "@annotation(com.synway.datarelation.config.HazecastLock)")
    public void hazecastLockMethod(){

    }

    @Around("hazecastLockMethod()")
    public Object hazecastLockMethod(ProceedingJoinPoint pjp) throws Throwable {
        Object result = null;
        Signature signature = pjp.getSignature();
        try{
            MethodSignature methodSignature = (MethodSignature) signature;
            Method method = methodSignature.getMethod();
            if(method == null){
                return null;
            }
            HazecastLock hazecastLock = method.getAnnotation(HazecastLock.class);
            if(hazecastLock == null){
                return null;
            }
            String lockName = hazecastLock.lockName();
            String methodValue = hazecastLock.methodValue();
            if(StringUtils.isBlank(lockName)){
                result = pjp.proceed();
                return result;
            }
            Lock lock = hazelcastInstance.getCPSubsystem().getLock(lockName);
            if(lock.tryLock()){
                try {
                    logger.info("定时任务【"+methodValue+"】开始运行----------------");
                    result = pjp.proceed();
                    logger.info("定时任务【"+methodValue+"】运行结束--------------");
                    return result;
                }catch (Exception e){
                    logger.error(ExceptionUtil.getExceptionTrace(e));
                    throw new Exception(ExceptionUtil.getExceptionTrace(e));
                }finally {
                    lock.unlock();
                }
            }else{
                logger.info("定时任务【"+methodValue+"】争抢锁失败，定时任务不是由该机器运行");
                return null;
            }
        }catch (Exception e){
            logger.error(ExceptionUtil.getExceptionTrace(e));
            return null;
        }

    }


}
