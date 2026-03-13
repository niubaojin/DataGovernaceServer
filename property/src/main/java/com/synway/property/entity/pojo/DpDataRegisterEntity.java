package com.synway.property.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 数据表注册信息
 * 用于存储数据表的审批注册信息
 *
 * @author DataGovernaceServer
 * @date 2025-11-21
 */
@Data
@TableName("DP_DATA_REGISTER")
public class DpDataRegisterEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 协议编号
     */
    @TableField("SJXJBM")
    private String sjxjbm;

    /**
     * 表名
     */
    @TableField("TABLE_NAME_EN")
    private String tableNameEn;

    /**
     * 标准表 id
     */
    @TableField("OBJECT_ID")
    private String objectId;

    /**
     * 审批 id
     */
    @TableField("APPROVAL_ID")
    private String approvalId;

    /**
     * 审批状态 (1 为审批成功，2 为审批中，4 为审批失败)
     */
    @TableField("APPROVAL_STATUS")
    private String approvalStatus;

    /**
     * 审批创建时间
     */
    @TableField("APPROVAL_CREATE_TIME")
    private String approvalCreateTime;

    /**
     * 审批更新时间
     */
    @TableField("APPROVAL_UPDATE_TIME")
    private String approvalUpdateTime;

    /**
     * 审批类型
     */
    @TableField("APPROVAL_TYPE")
    private String approvalType;

    /**
     * 库名
     */
    @TableField("TABLE_PROJECT")
    private String tableProject;
}
