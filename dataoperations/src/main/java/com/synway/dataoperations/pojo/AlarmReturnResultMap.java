package com.synway.dataoperations.pojo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

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

    public List<Map<String, String>> getAlarmModuleChart() {
        return alarmModuleChart;
    }

    public void setAlarmModuleChart(List<Map<String, String>> alarmModuleChart) {
        this.alarmModuleChart = alarmModuleChart;
    }

    public List<Map<String, String>> getAlarmStatusChart() {
        return alarmStatusChart;
    }

    public void setAlarmStatusChart(List<Map<String, String>> alarmStatusChart) {
        this.alarmStatusChart = alarmStatusChart;
    }

    public List<Map<String, String>> getAlarmStatusFilterList() {
        return alarmStatusFilterList;
    }

    public void setAlarmStatusFilterList(List<Map<String, String>> alarmStatusFilterList) {
        this.alarmStatusFilterList = alarmStatusFilterList;
    }

    public List<Map<String, String>> getAlarmModuleFilterList() {
        return alarmModuleFilterList;
    }

    public void setAlarmModuleFilterList(List<Map<String, String>> alarmModuleFilterList) {
        this.alarmModuleFilterList = alarmModuleFilterList;
    }

    public List<AlarmMessage> getData() {
        return data;
    }

    public void setData(List<AlarmMessage> data) {
        this.data = data;
    }
}
