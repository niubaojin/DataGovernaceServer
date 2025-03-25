package com.synway.datastandardmanager.interceptor;

import java.lang.annotation.*;

/**
 * 注解类 用于将header里面的 用户信息
 * 用于方法参数解析器
 * 弃用
 * @author wangdongwei
 */
@Deprecated
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CurrentUser {
}
