package com.synway.dataoperations.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 接入处理入库数据汇总表
 */
@Data
@TableName("SHPTSTATISTICS.SJGC_DATA_SUMMARY")
public class SjgcDataSummaryEntity {
    //记录序列号
    @TableField("SERIAL_NUMBER")
    private String serialNumber;

    //实例ID
    @TableField("INSTANCE_ID")
    private String instanceId;

    //标识
    @TableField("DATA_TYPE")
    private String dataType;

    //节点IP
    @TableField("NODE_IP")
    private String nodeIp;

    //节点端口
    @TableField("NODE_PORT")
    private String nodePort;

    //输入数据名称
    @TableField("IN_CN_NAME")
    private String inCnName;

    //输入数据协议名
    @TableField("IN_PROTOCOL")
    private String inProtocol;

    //输入数据条数
    @TableField("IN_SUCCESS_NUM")
    private long inSuccessNum;

    //输入数据大小
    @TableField("IN_SUCCESS_SIZE")
    private long inSuccessSize;

    //输出数据名称
    @TableField("OUT_CN_NAME")
    private String outCnName;

    //输出数据英文表名
    @TableField("OUT_EN_NAME")
    private String outEnName;

    //输出数据协议名
    @TableField("OUT_PROTOCOL")
    private String outProtocol;

    //输出成功条数
    @TableField("OUT_SUCCESS_NUM")
    private long outSuccessNum;

    //输出异常条数
    @TableField("OUT_ERROR_NUM")
    private long outErrorNum;

    //输出中标条数
    @TableField("OUT_BIDDING_NUM")
    private long outBiddingNum;

    //输出平台
    @TableField("STORE_TYPE")
    private String storeType;

    //输出平台项目空间
    @TableField("PROJECT_NAME")
    private String projectName;

    //推送日期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    @TableField("PUSH_DATE")
    private Date pushDate;

    //数据时点
    @TableField("PUSH_HOUR")
    private Integer pushHour;

    //入表时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    @TableField("CREATE_TIME")
    private Date createTime;
}
