package com.synway.dataoperations.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 操作日志
 */
@Data
@TableName("DO_OPERATOR_LOG")
public class DoOperatorLogEntity {
    //id
    @TableField("NUM_ID")
    private Integer numId;

    //操作时间
    @TableField("OPERATE_TIME")
    private String operateTime;

    //终端ip
    @TableField("TERMINAL_ID")
    private String terminalId;

    //操作类型
    @TableField("OPERATE_TYPE")
    private Integer operateType;

    //操作结果
    @TableField("OPERATE_RESULT")
    private String operateResult;

    //错误代码
    @TableField("ERROR_CODE")
    private String errorCode;

    //操作模块名称
    @TableField("OPERATE_NAME")
    private String operateName;

    //操作内容
    @TableField("OPERATE_CONDITION")
    private String operateCondition;

    //display
    @TableField("DISPLAY")
    private String display;

    //数据级别
    @TableField("DATA_LEVEL")
    private Integer dataLevel;

    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    @TableField("CREATE_TIME")
    private Date createTime;

    //更新时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    @TableField("UPDATE_TIME")
    private Date updateTime;

    //是否发送
    @TableField("IF_OUT_SEND")
    private Integer ifOutSend;

    //用户名称
    @TableField("USER_NAME")
    private String userName;

    //用户id（证件号码）
    @TableField("USER_ID")
    private String userId;

    //用户id
    @TableField("USER_NUM")
    private String userNum;

    //机构
    @TableField("ORGANIZATION")
    private String organization;

    //机构id
    @TableField("ORGANIZATION_ID")
    private String organizationId;
}
