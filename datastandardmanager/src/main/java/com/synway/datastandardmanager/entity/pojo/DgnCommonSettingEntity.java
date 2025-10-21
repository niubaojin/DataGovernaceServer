package com.synway.datastandardmanager.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 数据阈值设置的相关数据
 *
 * @author nbj
 * @date 2025年7月2日17:13:05
 */
@Data
@TableName("DGN_COMMON_SETTING")
public class DgnCommonSettingEntity {

    //阈值所属的父类ID
    @TableField("PARENT_ID")
    private String parentId;

    //名称
    @TableField("NAME")
    private String name;

    //逻辑判断
    @TableField("LOGICAL_JUDGMENT")
    private String logicalJudgment;

    //阈值
    @TableField("THRESHOLD_VALUE")
    private String thresholdValue;

    //主键id
    @TableField("ID")
    private String id;

    //该行数据所属的类别，值为1,2,3 数字越小级别越大
    @TableField("TREE_TYPE")
    private String treeType;

    //是否启用(1:是;0:否)
    @TableField("IS_ACTIVE")
    private String isActive;

    //配置页面地址
    @TableField("PAGE_URL")
    private String pageUrl;

}
