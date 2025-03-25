package com.synway.reconciliation.config;

/**
 * @author
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 分布式锁的相关接口 使用aop添加
 * @author
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface HazelcastLock {

    String lockName() default "";

    String methodValue() default "定时任务";

}
