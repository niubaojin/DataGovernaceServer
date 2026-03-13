package com.synway.property.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 申请审批表
 *
 * @author DataGovernaceServer
 * @date 2025-11-21
 */
@Data
@TableName("DP_APPROVAL_INFO")
public class DpApprovalInfoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * UUID 唯一标识
     */
    @TableField("APPROVAL_ID")
    private String approvalId;

    /**
     * 业务模块
     */
    @TableField("MODULE_NAME")
    private String moduleName;

    /**
     * 申请操作
     */
    @TableField("OPERATION_NAME")
    private String operationName;

    /**
     * 申请信息
     */
    @TableField("APPLICATION_INFO")
    private String applicationInfo;

    /**
     * 回传业务系统数据
     */
    @TableField("CALLBACK_DATA")
    private String callbackData;

    /**
     * 回调业务系统地址
     */
    @TableField("CALLBACK_URL")
    private String callbackUrl;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("CREATE_TIME")
    private Date createTime;

    /**
     * 状态 (0:初始化;1:审批中;2:退回;结束;4:手动终止)
     */
    @TableField("STATUS")
    private String status;

    /**
     * 业务模块标识 (dataDefinition:数据定义;createTable:建表;standardTable:新建标准表;register:注册)
     */
    @TableField("MODULE_ID")
    private String moduleId;

    /**
     * 申请人 id
     */
    @TableField("OPERATOR_ID")
    private String operatorId;

    /**
     * 事务 id
     */
    @TableField("TASK_ID")
    private String taskId;

    /**
     * 申请详情查看地址
     */
    @TableField("VIEW_URL")
    private String viewUrl;

    /**
     * 流程实例 ID
     */
    @TableField("PROCESSINSTANCEID")
    private String processInstanceId;

    /**
     * 回调执行情况
     */
    @TableField("EXECUTE_RESULT")
    private String executeResult;
}
