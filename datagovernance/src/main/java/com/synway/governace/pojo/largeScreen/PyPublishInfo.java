package com.synway.governace.pojo.largeScreen;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wangdongwei
 * @date 2021/8/25 11:25
 */
@Data
public class PyPublishInfo implements Serializable {
    private static final long serialVersionUID = -6982990272088241378L;
    /**
     * 注册平台数
     */
    private  long  registerPlatformCount;

    /**
     * 注册服务数
     */
    private  long  registerServiceCount;

    /**
     * 服务调用次数
     */
    private  long  publishServiceInfoCount;

    /**
     * 服务调用次数 页面展示值
     * 如果大于 100亿  则用亿表示
     * 如果大于 100万  则 用万表示
     * 保留 3个小数点
     */
    private  String  publishServiceInfoCountStr="0";


    /**
     * 注册平台数 页面展示值
     * 如果大于 10亿  则用亿表示
     *  如果大于 100万  则 用万表示
     *  保留 3个小数点
     */
    private  String  registerPlatformCountStr="0";


    /**
     * 注册服务数 页面展示值
     * 如果大于 10亿  则用亿表示
     *  如果大于 100万  则 用万表示
     *  保留 3个小数点
     */
    private  String  registerServiceCountStr="0";

}
