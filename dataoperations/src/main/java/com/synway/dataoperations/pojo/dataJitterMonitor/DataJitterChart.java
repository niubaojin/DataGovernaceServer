package com.synway.dataoperations.pojo.dataJitterMonitor;

import lombok.Data;

import java.util.List;

@Data
public class DataJitterChart {
    private String dataType;            // 数据类型
    private List<String> dataSums;      // 数据量
    private List<String> jitterRate;    // 抖动率
    private List<String> pushHours;     // 推送数据时点
}
