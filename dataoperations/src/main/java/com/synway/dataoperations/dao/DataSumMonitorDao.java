package com.synway.dataoperations.dao;

import com.synway.dataoperations.pojo.dataSizeMonitor.SjgcDataSummary;
import com.synway.dataoperations.pojo.dataSizeMonitor.ThresholdConfigSetting;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DataSumMonitorDao {

    // 左侧树数据
    List<SjgcDataSummary> getDSMLeftTree(@Param("searchName") String searchName);

    // 获取数据种类
    List<SjgcDataSummary> getDSMDataType();

    // 获取卡片数据
    List<SjgcDataSummary> getCardData();

    // 折线图数据（接入、处理）
    List<SjgcDataSummary> getChartData(@Param("tableId") String tableId);
    // 折线图数据（插入）
    List<SjgcDataSummary> getChartData3(@Param("tableId") String tableId);

    // 获取数据量监控告警配置
    List<ThresholdConfigSetting> getDataVolumeAlarmSetting();

    // 获取告警推送数据
    List<SjgcDataSummary> getSjgcDataSummarys(@Param("days") Integer days);
}
