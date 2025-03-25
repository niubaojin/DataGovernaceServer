package com.synway.dataoperations.dao;

import com.synway.dataoperations.pojo.dataSizeMonitor.SjgcDataSummary;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DataJitterMonitorDao {

    // 获取卡片数据
    List<SjgcDataSummary> getCardDataDJM(@Param("nowHour") String nowHour);

    // 获取图表数据
    List<SjgcDataSummary> getChartDataDJM(@Param("tableId") String tableId);

    // 获取图表数据（入库）
    List<SjgcDataSummary> getChartDataDJM3(@Param("tableId") String tableId);

    // 获取告警数据
    List<SjgcDataSummary> getSjgcDataSummarysByHour(@Param("nowHour") String nowHour);

}
