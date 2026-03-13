package com.synway.property.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 价值密度表
 *
 * @author DataGovernaceServer
 * @date 2025-11-21
 */
@Data
@TableName("DP_VAL_DENSITY")
public class DpValDensityEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 英文表名
     */
    @TableField("TABLE_NAME_EN")
    private String tableNameEn;

    /**
     * 平台类型
     */
    @TableField("TABLE_TYPE")
    private String tableType;

    /**
     * 项目名
     */
    @TableField("TABLE_PROJECT")
    private String tableProject;

    /**
     * 是否非结构化数据
     */
    @TableField("UNSTRUCTED_DATA")
    private Integer unStructedData;

    /**
     * 是否二次解析
     */
    @TableField("TWOHANDLE")
    private Integer twoHandle;

    /**
     * 是否可进行文本提取
     */
    @TableField("TEXTHANDLE")
    private Integer textHandle;

    /**
     * 被调用工作流
     */
    @TableField("WORKFLOW_USED")
    private Integer workflowUsed;

    /**
     * 被调用应用系统
     */
    @TableField("APPLICATION_USED")
    private Integer applicationUsed;

    /**
     * 提取主题库
     */
    @TableField("ZHUTIKU_USED")
    private Integer zhutikuUsed;

    /**
     * 提取资源库
     */
    @TableField("ZIYUANKU_USED")
    private Integer ziyuankuUsed;

    /**
     * 提取要素库
     */
    @TableField("YAOSUKU_USED")
    private Integer yaosukuUsed;

    /**
     * 提取标签
     */
    @TableField("TAG_USED")
    private Integer tagUsed;

    /**
     * 价值密度
     */
    @TableField("VAL_DENSITY")
    private String valDensity;

    /**
     * 更新价值密度状态
     */
    @TableField("UPDATE_VAL_DENSITY_STATUS")
    private Integer updateValDensityStatus;
}
