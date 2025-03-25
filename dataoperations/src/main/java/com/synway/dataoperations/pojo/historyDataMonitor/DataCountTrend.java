package com.synway.dataoperations.pojo.historyDataMonitor;

import lombok.Data;

/**
 * @description 历史数据监测-数据量趋势
 */
@Data
public class DataCountTrend {
    private long yiDongCount;   // 移动数据量
    private long lianTongCount; // 联通数据量
    private long dianXinCount;  // 电信数据量
    private long allCount;      // 全部数据量
    private String dt;          // 统计日期
    private String city;        // 城市
    private String operatorNet; // 运营商
}
