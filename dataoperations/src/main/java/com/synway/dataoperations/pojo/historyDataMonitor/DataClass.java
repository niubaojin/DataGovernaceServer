package com.synway.dataoperations.pojo.historyDataMonitor;

import lombok.Data;


/**
 * @description 数据种类、应用种类、事件种类
 */
@Data
public class DataClass {
    private String dataClass;           // 数据种类
    private String appClass;            // 应用种类
    private String eventClass;          // 事件种类
    private long dataCount;             // 数据量
    private String ratio;               // 占比
}
