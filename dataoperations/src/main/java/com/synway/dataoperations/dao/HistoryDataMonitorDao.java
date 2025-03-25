package com.synway.dataoperations.dao;

import com.synway.dataoperations.pojo.AlarmMessage;
import com.synway.dataoperations.pojo.historyDataMonitor.DataClass;
import com.synway.dataoperations.pojo.historyDataMonitor.DataCountTrend;
import com.synway.dataoperations.pojo.historyDataMonitor.EventType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author nbj
 * @description 历史数据监测相关数据库操作
 * @date 2022年6月1日16:56:49
 */
@Mapper
@Repository
public interface HistoryDataMonitorDao {

    // 获取左侧树
    List<EventType> getLeftTree();

    // 获取数据量
    BigDecimal getDataNum(@Param("minDt") String minDt,
                          @Param("maxDt") String maxDt);

    // 获取昨日数据种类
    int getYesterdayDataCategory(@Param("dt") String dt);

    // 获取下拉菜单
    List<String> getMenu(@Param("menuType") String menuType,
                         @Param("minDt") String minDt,
                         @Param("maxDt") String maxDt,
                         @Param("searchName") String searchName,
                         @Param("areaName") String areaName,
                         @Param("operatorName") String operatorName,
                         @Param("networkTypeName") String networkTypeName);

    // 获取数据量趋势
    List<DataCountTrend> getDataCountTrend(@Param("operatorNet") String operatorNet,
                                           @Param("minDt") String minDt,
                                           @Param("maxDt") String maxDt,
                                           @Param("searchName") String searchName,
                                           @Param("areaName") String areaName,
                                           @Param("operatorName") String operatorName,
                                           @Param("networkTypeName") String networkTypeName);

    // 获取数据区域分布
    List<DataCountTrend> getDataArea(@Param("minDt") String minDt,
                                     @Param("maxDt") String maxDt,
                                     @Param("searchName") String searchName,
                                     @Param("areaName") String areaName,
                                     @Param("operatorName") String operatorName,
                                     @Param("networkTypeName") String networkTypeName);

    // 获取数据种类
    List<DataClass> getDataClass(@Param("minDt") String minDt,
                                 @Param("maxDt") String maxDt,
                                 @Param("searchName") String searchName,
                                 @Param("areaName") String areaName,
                                 @Param("operatorName") String operatorName,
                                 @Param("networkTypeName") String networkTypeName);

    // 获取事件种类
    List<DataClass> getEventClass(@Param("minDt") String minDt,
                                  @Param("maxDt") String maxDt,
                                  @Param("searchName") String searchName,
                                  @Param("areaName") String areaName,
                                  @Param("operatorName") String operatorName,
                                  @Param("networkTypeName") String networkTypeName);

    // 获取应用种类
    List<DataClass> getAppClass(@Param("minDt") String minDt,
                                @Param("maxDt") String maxDt,
                                @Param("searchName") String searchName,
                                @Param("areaName") String areaName,
                                @Param("operatorName") String operatorName,
                                @Param("networkTypeName") String networkTypeName);

    // 获取告警数据
    List<AlarmMessage> getHDMMsg(@Param("minDt") String minDt,
                                 @Param("maxDt") String maxDt);

}
