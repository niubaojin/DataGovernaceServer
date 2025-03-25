package com.synway.dataoperations.pojo.historyDataMonitor;

import lombok.Data;

/**
 * @description 历史数据监控请求参数
 */
@Data
public class RequestParameterHDM {
    private String searchName;      // 搜索名称
    private String areaName;        // 所选区域名称
    private String operatorName;    // 所选运营商名称
    private String networkTypeName; // 所选网络类型名称
    private String dt;              // 所选日期
}
