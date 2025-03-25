package com.synway.dataoperations.pojo.dataSizeMonitor;

import lombok.Data;

import java.util.List;


/**
 * 数据量图表
 */
@Data
public class DataSumChart {
    private String dataType;        // 数据类型
    private List<String> dataSums;  // 数据量
    private List<String> pushHours; // 推送数据时点
}
