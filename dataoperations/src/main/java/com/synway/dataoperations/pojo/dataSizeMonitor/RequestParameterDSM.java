package com.synway.dataoperations.pojo.dataSizeMonitor;

import lombok.Data;

@Data
public class RequestParameterDSM {
    String tableId;             // tableId
    String dataSumTime;         // 数据量时间选择（today、24hours）
    String dataSumTimeAbnormal; // 数据异常量时间选择（today、24hours）
}
