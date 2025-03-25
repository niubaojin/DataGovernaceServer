package com.synway.dataoperations.pojo;

import lombok.Data;

/**
 * 运维告警消息
 * @author ywj
 */
@Data
public class AlarmMessage {

    private String ID;              // 主键id
    private String alarmsystem;     // 系统名称，必填
    private String alarmmodule;     // 模块名称，必填
    private String alarmtime;       // 故障产生时间，必填
    private int alarmflag;          // 告警标记代码，必填 0：产生1：消除2：事件3：重启或倒换4：异常连接
    private String alarmflagName;   // 告警标记名称
    private int level;              // 故障等级代码，必填 0：一般 1：警告；2：严重(一般<->次要 警告<->一般 严重<->严重)
    private String levelName;       // 故障等级名称
    private String alarmcontent;    // 故障描述，必填
    private String tableNameEn;     // 数据英文名称，必填
    private String tableNameCh;     // 数据中文名称，必填
    private String tableId;         // 协议编码，必填
}
