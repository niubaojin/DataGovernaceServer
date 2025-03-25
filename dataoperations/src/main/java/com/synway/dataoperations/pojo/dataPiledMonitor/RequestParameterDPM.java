package com.synway.dataoperations.pojo.dataPiledMonitor;

import lombok.Data;

@Data
public class RequestParameterDPM {
    String dataName;        // 数据名称
    String dataPiledTime;   // 数据堆积时间选择（today、24hours）
}
