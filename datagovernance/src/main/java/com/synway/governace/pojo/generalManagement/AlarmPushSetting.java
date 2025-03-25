package com.synway.governace.pojo.generalManagement;

public class AlarmPushSetting {
    private String id;                  // 主键id
    private String pushMode;            // 推送方式
    private String alarmLevel;          // 告警级别
    private String isEnable;            // 是否启用
    private String pushAlarmInfoUrl;    // 推送告警信息url
    private String zookeeperIP;         // zookeeperIP
    private String clusterAddr;         // 集群地址，多个以逗号分隔
    private String topic;               // 主题名称
    private String remark;              // 备注

    public String getPushAlarmInfoUrl() {
        return pushAlarmInfoUrl;
    }

    public void setPushAlarmInfoUrl(String pushAlarmInfoUrl) {
        this.pushAlarmInfoUrl = pushAlarmInfoUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPushMode() {
        return pushMode;
    }

    public void setPushMode(String pushMode) {
        this.pushMode = pushMode;
    }

    public String getAlarmLevel() {
        return alarmLevel;
    }

    public void setAlarmLevel(String alarmLevel) {
        this.alarmLevel = alarmLevel;
    }

    public String getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(String isEnable) {
        this.isEnable = isEnable;
    }

    public String getZookeeperIP() {
        return zookeeperIP;
    }

    public void setZookeeperIP(String zookeeperIP) {
        this.zookeeperIP = zookeeperIP;
    }

    public String getClusterAddr() {
        return clusterAddr;
    }

    public void setClusterAddr(String clusterAddr) {
        this.clusterAddr = clusterAddr;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
