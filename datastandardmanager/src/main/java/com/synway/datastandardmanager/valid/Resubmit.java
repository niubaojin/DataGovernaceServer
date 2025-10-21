package com.synway.datastandardmanager.valid;

import java.lang.annotation.*;

/**
 * 防止重复提交的注解信息
 *
 * @author wdw
 * @version 1.0
 * @date 2021/6/16 11:15
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Resubmit {

    /**
     * 延迟时间 在延迟多久之后可以再次重复提交
     * 单位是 秒
     *
     * @return
     */
    int delaySeconds() default 2;

}
