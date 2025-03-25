package com.synway.dataoperations.pojo.dataJitterMonitor;

import lombok.Data;

@Data
public class RequestParameterDJM {
    String tableId;         // 协议id
    String dataJitterTime;  // 数据抖动时间选择（today、24hours）
}
