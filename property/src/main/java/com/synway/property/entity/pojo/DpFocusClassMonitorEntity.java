package com.synway.property.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 存储需要监控的重点组织信息
 *
 * @author DataGovernaceServer
 * @date 2025-11-21
 */
@Data
@TableName("DP_FOCUS_CLASS_MONITOR")
public class DpFocusClassMonitorEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 一级名称
     */
    @TableField("PRIMARY_NAME")
    private String primaryName;

    /**
     * 二级名称
     */
    @TableField("SECONDARY_NAME")
    private String secondaryName;

    /**
     * 该条记录插入的时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("INSERT_TIME")
    private Date insertTime;

    /**
     * 是否被删除 0：被删除 1：没有被删除
     */
    @TableField("DEL_FLAG")
    private Integer delFlag;

    /**
     * 大类名称  数据组织/数据资源来源/数据资源分类major
     */
    @TableField("MAJOR_CLASS")
    private String majorClass;

    /**
     * 用户id
     */
    @TableField("USER_ID")
    private String userId;

    /**
     * 权限表用户id
     */
    @TableField("USER_AUTHORITY_ID")
    private String userAuthorityId;

    /**
     * 用户名
     */
    @TableField("USER_NAME")
    private String userName;

    /**
     * 是否管理员
     */
    @TableField("IS_ADMIN")
    private String isAdmin;

    /**
     * 最后一级分类代码
     */
    @TableField("LAST_LEVEL_CLASSIFYCODE")
    private String lastLevelClassifyCode;
}
