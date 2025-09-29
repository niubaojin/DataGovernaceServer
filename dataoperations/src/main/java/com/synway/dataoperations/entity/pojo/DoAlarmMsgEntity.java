package com.synway.dataoperations.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 数据运维-告警信息
 */
@Data
@TableName("DO_ALARM_MSG")
public class DoAlarmMsgEntity {

    @TableField(exist = false)
    private String ID;

    //主键id
    @TableField("ALARM_ID")
    private String ALARM_ID;

    //系统名称，必填
    @TableField("ALARM_SYSTEM")
    private String alarmsystem;

    //模块名称，必填
    @TableField("ALARM_MODULE")
    private String alarmmodule;

    // 模块数据量
    @TableField(exist = false)
    private Integer alarmmoduleCount;

    //故障产生时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    @TableField("ALARM_TIME")
    private Date alarmtime;

    //告警标记、必填，0：产生1：消除2：事件3：重启或倒换4：异常连接
    @TableField("ALARM_FLAG")
    private String alarmflagName;
    // 告警标记名称
    @TableField(exist = false)
    private Integer alarmflag;


    //故障等级、必填，0：次要 1：一般；2：严重；3：紧急
    @TableField("ALARM_LEVEL")
    private String levelName;
    @TableField(exist = false)
    private Integer levelNameCount;
    @TableField(exist = false)
    private Integer level;

    //故障描述
    @TableField("ALARM_CONTENT")
    private String alarmcontent;

    //数据英文名称
    @TableField("TABLE_NAME_EN")
    private String tableNameEn;

    //数据中文名称
    @TableField("TABLE_NAME_CH")
    private String tableNameCh;

    //协议编码
    @TableField("TABLEID")
    private String tableId;
}
