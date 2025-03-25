package com.synway.governace.pojo.largeScreen.ningbo;

import lombok.Data;

import java.util.List;


/**
 * 数据接入量图表
 */

@Data
public class PropertyStatisticsChart {
    private String dataType;        // 数据类型
    private List<String> dataSums;  // 数据量
    private List<String> pushDates; // 推送数据日期
}
