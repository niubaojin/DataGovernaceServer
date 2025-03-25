package com.synway.governace.pojo.alarmMessage;

/**
 * 实时告警信息的实体类
 */
public class AlarmMessage {
    public final static String DATA_MONITOR_ALARM = "dataMonitorAlarm";// 实时告警的类别 数据流监控告警
    public final static String DATA_ASSET_ALARM = "dataAssetAlarm";// 实时告警的类别 数据资产告警
    public final static String DATA_RELATION_ALARM = "modelRelationAlarm";// 实时告警的类别 模型血缘异常告警
    public final static String IMPORTANT_DATA_ALARM = "importantDataAlarm";// 实时告警的类别 重点数据告警
    public final static String DATA_SOURCE_ALARM = "dataSourceAlarm";// 实时告警的类别 数据接入告警
    public final static String DATA_RECONCILIATION_ALARM = "dataReconciliationAlarm";// 实时告警的类别 数据对账告警

    private String alarm_id;
    private String alarm_time;
    private String alarm_type;
    private String alarm_desc;

    @Override
    public String toString() {
        return "AlarmMessage{" +
                "alarm_id='" + alarm_id + '\'' +
                ", alarm_time='" + alarm_time + '\'' +
                ", alarm_type='" + alarm_type + '\'' +
                ", alarm_desc='" + alarm_desc + '\'' +
                '}';
    }

    public String getAlarm_id() {
        return alarm_id;
    }

    public void setAlarm_id(String alarm_id) {
        this.alarm_id = alarm_id;
    }

    public String getAlarm_time() {
        return alarm_time;
    }

    public void setAlarm_time(String alarm_time) {
        this.alarm_time = alarm_time;
    }

    public String getAlarm_type() {
        return alarm_type;
    }

    public void setAlarm_type(String alarm_type) {
        this.alarm_type = alarm_type;
    }

    public String getAlarm_desc() {
        return alarm_desc;
    }

    public void setAlarm_desc(String alarm_desc) {
        this.alarm_desc = alarm_desc;
    }
}
