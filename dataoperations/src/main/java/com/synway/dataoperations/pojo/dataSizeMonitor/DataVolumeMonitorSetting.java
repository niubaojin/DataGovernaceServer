package com.synway.dataoperations.pojo.dataSizeMonitor;

import lombok.Data;

import java.util.List;

/**
 * @author nbj
 * @date 2023年6月29日16:00:04
 */

@Data
public class DataVolumeMonitorSetting {
    private List<String> settingTache;
    private boolean acceptCheck;             // 数据接入选择框
    private boolean processCheck;            // 数据处理选择框
    private boolean storageCheck;            // 数据入库选择框
    private boolean abnormalDataAlarmCheck;  // 异常数据告警阈值选择框
    private boolean dataJitterAlarmCheck;    // 数据抖动告警阈值选择框
    private boolean dataVolumeAlarmCheck;    // 数据流量告警阈值选择框
    private String abnormalDataAlarmNum;     // 异常数据告警（条数）
    private String abnormalDataAlarmLevel;   // 异常数据告警（级别）
    private String dataJitterAlarmYiban;     // 数据抖动告警（一般阈值）
    private String dataJitterAlarmJinggao;   // 数据抖动告警（警告阈值）
    private String dataJitterAlarmYanzhong;  // 数据抖动告警（严重阈值）
    private String dataVolumeAlarmHour;      // 数据流量告警（小时）
    private String dataVolumeAlarmSum;       // 数据流量告警（条数）
    private String dataVolumeAlarmLevel;     // 数据流量告警（级别）
}
