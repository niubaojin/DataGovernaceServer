package com.synway.property.interceptor;

import javax.validation.constraints.NotNull;
import java.lang.annotation.*;

/**
 * 修改sql权限的相关内容
 * 达蒙数据库和oracle数据库表名存在不同的情况
 * 可能需要配置多个　需要一一对应
 * @author wangdongwei
 * @date 2021/5/26 14:40
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
//@Deprecated
public @interface AuthorControl {
    /**
     * 如果表名存在双引号  需要  synlte.\"OBJECT\"
     * 表名忽略大小写
     * @return
     */
    @NotNull
    String[] tableNames();
    @NotNull
    String[] columnNames();

    String methodValue() default "测试拦截信息";
}
