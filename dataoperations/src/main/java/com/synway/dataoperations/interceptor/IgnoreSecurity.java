package com.synway.dataoperations.interceptor;

import java.lang.annotation.*;

/**
 * 使用这个注解之后，前端请求不再需要通过用户权限管理
 * @author wangdongwei
 * @date 2021/5/26 14:27
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreSecurity {
}
