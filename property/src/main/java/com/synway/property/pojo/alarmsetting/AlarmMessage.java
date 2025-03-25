package com.synway.property.pojo.alarmsetting;

/**
 * 运维告警消息
 * @author ywj
 */
public class AlarmMessage {

    private String ID;              // 主键id
    private String alarmsystem;     // 系统名称，必填
    private String alarmmodule;     // 模块名称，必填
    private String alarmtime;       // 故障产生时间，必填
    private int alarmflag;          // 告警标记代码，必填 0：产生1：消除2：事件3：重启或倒换4：异常连接
    private String alarmflagName;   // 告警标记名称
    private int level;              // 故障等级代码，必填 0：一般 1：警告；2：严重
    private String levelName;       // 故障等级名称
    private String alarmcontent;    // 故障描述，必填
    private String tableNameEn;     // 数据英文名称，必填
    private String tableNameCh;     // 数据中文名称，必填
    private String tableId;         // 协议编码，必填


    public String getTableNameEn() {
        return tableNameEn;
    }

    public void setTableNameEn(String tableNameEn) {
        this.tableNameEn = tableNameEn;
    }

    public String getTableNameCh() {
        return tableNameCh;
    }

    public void setTableNameCh(String tableNameCh) {
        this.tableNameCh = tableNameCh;
    }

    public String getAlarmflagName() {
        return alarmflagName;
    }

    public void setAlarmflagName(String alarmflagName) {
        this.alarmflagName = alarmflagName;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getAlarmsystem() {
        return alarmsystem;
    }

    public void setAlarmsystem(String alarmsystem) {
        this.alarmsystem = alarmsystem;
    }

    public String getAlarmmodule() {
        return alarmmodule;
    }

    public void setAlarmmodule(String alarmmodule) {
        this.alarmmodule = alarmmodule;
    }

    public String getAlarmtime() {
        return alarmtime;
    }

    public void setAlarmtime(String alarmtime) {
        this.alarmtime = alarmtime;
    }

    public int getAlarmflag() {
        return alarmflag;
    }

    public void setAlarmflag(int alarmflag) {
        this.alarmflag = alarmflag;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getAlarmcontent() {
        return alarmcontent;
    }

    public void setAlarmcontent(String alarmcontent) {
        this.alarmcontent = alarmcontent;
    }
}
