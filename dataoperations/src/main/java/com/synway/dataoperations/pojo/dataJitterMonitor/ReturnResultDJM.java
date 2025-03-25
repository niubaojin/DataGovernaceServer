package com.synway.dataoperations.pojo.dataJitterMonitor;

import lombok.Data;

import java.util.List;

@Data
public class ReturnResultDJM {
    private List<DataJitterChart> dataJitterCharts; // 数据抖动折线图数据
    private List<DataJitterChart> dataJitterTables; // 数据抖动表格数据
}
