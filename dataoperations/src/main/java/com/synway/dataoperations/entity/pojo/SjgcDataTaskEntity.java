package com.synway.dataoperations.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 任务状态表
 */
@Data
@TableName("SHPTSTATISTICS.SJGC_DATA_TASK")
public class SjgcDataTaskEntity {
    //记录序列号
    @TableField("SERIAL_NUMBER")
    private String serialNumber;

    //任务ID
    @TableField("TASK_ID")
    private String taskId;

    //实例ID
    @TableField("INSTANCE_ID")
    private String instanceId;

    //任务名称
    @TableField("TASK_NAME")
    private String taskName;

    //大类
    @TableField("TASK_CLASS")
    private String taskClass;

    //小类
    @TableField("TASK_SUBCLASS")
    private String taskSubclass;

    //输入数据源名称
    @TableField("IN_SOURCE_NAME")
    private String inSourceName;

    //输入数据名称
    @TableField("IN_CN_NAME")
    private String inCnName;

    //输入数据协议名
    @TableField("IN_PROTOCOL")
    private String inProtocol;

    //输出数据源名称
    @TableField("OUT_SOURCE_NAME")
    private String outSourceName;

    //任务状态
    @TableField("TASK_STATUS")
    private String taskStatus;

    //节点IP
    @TableField("NODE_IP")
    private String nodeIp;

    //节点端口
    @TableField("NODE_PORT")
    private String nodePort;

    //来源文件类型
    @TableField("SOURCE_FILETYPE")
    private String sourceFileType;

    //任务开始时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    @TableField("TASK_START_TIME")
    private Date taskStartTime;

    //任务结束时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    @TableField("TASK_END_TIME")
    private Date taskEndTime;

    //任务运行时长
    @TableField("TASK_RUNNING_TIME")
    private String taskRunningTime;

    //入表时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    @TableField("CREATE_TIME")
    private Date createTime;
}
