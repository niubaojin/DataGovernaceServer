package com.synway.governace.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("DGN_COMMON_SETTING")
public class DgnCommonSettingEntity {
    @TableField("ID")
    private String id;

    @TableField("PARENT_ID")
    private String parentId;

    @TableField("NAME")
    private String name;

    @TableField("LOGICAL_JUDGMENT")
    private String logicalJudgment;

    @TableField("THRESHOLD_VALUE")
    private String thresholdValue;

    @TableField("TREE_TYPE")
    private String treeType;

    @TableField("IS_ACTIVE")
    private String isActive;

    @TableField("PAGE_URL")
    private String pageUrl;

}
