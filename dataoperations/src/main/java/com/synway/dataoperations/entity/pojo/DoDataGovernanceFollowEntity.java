package com.synway.dataoperations.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 数据运维-数据治理跟踪表
 */
@Data
@TableName("DO_DATAGOVERNANCE_FOLLOW")
public class DoDataGovernanceFollowEntity {
    //主键id
    @TableField("DG_ID")
    private String id;

    //环节
    @TableField("DG_LINK")
    private String link;

    //数据名称
    @TableField("DG_DATANAME")
    private String dataName;

    //数据表名
    @TableField("DG_TABLENAME")
    private String tableName;

    //数据协议
    @TableField("DG_TABLEID")
    private String tableId;

    //内容
    @TableField("DG_MSG")
    private String message;

    //最新时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    @TableField("DG_UPDATETIME")
    private Date updateTime;

    //发起人员
    @TableField("DG_SPONSOR")
    private String sponsor;

    //流程发起时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    @TableField("DG_SPONSOR_TIME")
    private Date sponsorTime;

    //治理人员
    @TableField("DG_MANAGER")
    private String manager;

    //治理时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    @TableField("DG_MANAGER_TIME")
    private Date managerTime;

    //审核时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    @TableField("DG_REVIEWER_TIME")
    private Date reviewerTime;

    //治理审核结论json
    @TableField("DG_CONCLUSION")
    private String conclusion;

    //状态
    @TableField("DG_STATUS")
    private String status;
}
