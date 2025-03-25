package com.synway.reconciliation.service;


/**
 * 缓存管理
 * @author DZH
 */
public interface CacheManageService {

    /**
     * 缓存接入账单相关信息
     */
    void cacheBillRelateInfo();

    /**
     * 缓存任务执行最大实例id
     */
    void cacheTaskInfoTag();

    /**
     * 缓存任务相关信息
     */
    void cacheRelateJob();

    /**
     * 缓存下发统计历史
     */
    void cacheIssueHistoryBill();

    /**
     * 缓存下发基线时间
     */
    void cacheIssueBaseTime();

    /**
     * 缓存源表信息
     */
    void  cacheSourceTable();

    /**
     * 获取数据下发时所有行政区划编码
     */
    void getAllCodes();


}
