package com.synway.property.service;

/**
 * @author 数据接入
 */
public interface DataStorageMonitorService {
    /**
     * 调用ckw下的odps/ads接口
     * 统计需要监控的表每天数据量
     */
    void statisticsTableTodayVolume();

    void getAllOrganizationData();

    void updateAssetsInfo();

    void getHbaseData();

    void getHiveData();

    void getDataResourceInfo();

    void getDataBaseStatus();

    void getClickhouseData();

    void deleteOverTimeAssets();
}
