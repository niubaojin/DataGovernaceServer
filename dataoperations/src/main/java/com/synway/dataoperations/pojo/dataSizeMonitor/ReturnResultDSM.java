package com.synway.dataoperations.pojo.dataSizeMonitor;

import lombok.Data;

import java.util.List;

/**
 * 数据量页面图表数据返回结果
 */
@Data
public class ReturnResultDSM {
    private List<DataSumChart> dataSumCharts;           // 数据量图表
    private DataSumTable dataSumTable;                  // 数据量表格数据
    private List<DataSumChart> dataSumChartsAbnormal;   // 数据异常量图表
    private DataSumTable dataSumTableAbnormal;          // 数据异常量表格数据
}
