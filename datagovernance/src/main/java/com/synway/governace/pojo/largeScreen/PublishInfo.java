package com.synway.governace.pojo.largeScreen;

import java.io.Serializable;

/**
 * 对外共享的数据
 * @author wangdongwei
 * @date 2021/4/25 14:13
 */
public class PublishInfo implements Serializable {
    /**
     * 共享数据种类数
     */
    private  long  publishDataTypes;

    /**
     * 共享服务接口数
     */
    private  long  publishServiceTypes;

    /**
     * 接口调用次数 页面展示值
     * 如果大于 10亿  则用亿表示
     *  如果大于 100万  则 用万表示
     *  保留 3个小数点
     */
    private  String  publishServiceInfoCountStr;

    /**
     * 共享数据量 具体值
     */
    private  long  publishDataInfoCount;

    /**
     * 共享数据量 页面展示值
     * 如果大于 100亿  则用亿表示
     * 如果大于 100万  则 用万表示
     * 保留 3个小数点
     */
    private  String  publishDataInfoCountStr;
    /**
     * 接口调用次数 具体值
     */
    private  long  publishServiceInfoCount;

    public String getPublishDataInfoCountStr() {
        return publishDataInfoCountStr;
    }

    public void setPublishDataInfoCountStr(String publishDataInfoCountStr) {
        this.publishDataInfoCountStr = publishDataInfoCountStr;
    }

    public String getPublishServiceInfoCountStr() {
        return publishServiceInfoCountStr;
    }

    public void setPublishServiceInfoCountStr(String publishServiceInfoCountStr) {
        this.publishServiceInfoCountStr = publishServiceInfoCountStr;
    }

    public long getPublishDataTypes() {
        return publishDataTypes;
    }

    public void setPublishDataTypes(long publishDataTypes) {
        this.publishDataTypes = publishDataTypes;
    }

    public long getPublishServiceTypes() {
        return publishServiceTypes;
    }

    public void setPublishServiceTypes(long publishServiceTypes) {
        this.publishServiceTypes = publishServiceTypes;
    }

    public long getPublishDataInfoCount() {
        return publishDataInfoCount;
    }

    public void setPublishDataInfoCount(long publishDataInfoCount) {
        this.publishDataInfoCount = publishDataInfoCount;
    }

    public long getPublishServiceInfoCount() {
        return publishServiceInfoCount;
    }

    public void setPublishServiceInfoCount(long publishServiceInfoCount) {
        this.publishServiceInfoCount = publishServiceInfoCount;
    }
}
