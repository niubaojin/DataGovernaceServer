package com.synway.dataoperations.pojo.historyDataMonitor;

import lombok.Data;

@Data
public class DataAreaCount {
    private long[] yiDongCount;   // 移动数据量
    private long[] lianTongCount; // 联通数据量
    private long[] dianXinCount;  // 电信数据量
    private long[] allCount;      // 全部数据量
    private String[] city;        // 城市
}
