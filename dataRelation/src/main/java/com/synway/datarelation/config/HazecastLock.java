package com.synway.datarelation.config;

import java.lang.annotation.*;

/**
 * 分布式锁的相关接口 使用aop添加
 * @author wangdongwei
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface HazecastLock {

    String lockName() default "";

    String methodValue() default "测试定时任务";

}
