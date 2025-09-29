package com.synway.dataoperations.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class AlarmReturnResultMap implements Serializable {
    // 告警环节图表数据
    private List<Map<String,String>> alarmModuleChart;
    // 告警状态图表数据
    private List<Map<String,String>> alarmStatusChart;
    // 告警状态过滤列表
    private List<Map<String,String>> alarmStatusFilterList;
    // 告警环节过滤列表
    private List<Map<String,String>> alarmModuleFilterList;
    // 告警数据
    private List<AlarmMessage> data;

}
