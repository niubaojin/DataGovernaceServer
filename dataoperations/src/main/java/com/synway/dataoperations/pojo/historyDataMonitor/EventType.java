package com.synway.dataoperations.pojo.historyDataMonitor;

import lombok.Data;

/**
 * @author nbj
 * @description 历史数据监测-事件类型
 */
@Data
public class EventType {
    private String tableName;   // 数据表名
    private String tableId;     // 数据协议代码
    private String tableNameCh; // 数据中文名称
    private String dataType;    // 数据来源分类
    private String operatorNet; // 运营商
    private String dataSource;  // 网络制式
    private String cityCode;    // 城市区号
    private String callType;    // 呼叫类型
    private String valText;     // 呼叫名称
    private long recordsAll;    // 记录数
    private String dt;          // 数据日期
}
