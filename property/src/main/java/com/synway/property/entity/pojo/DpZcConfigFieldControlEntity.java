package com.synway.property.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 表组织字段全局显示配置
 *
 * @author DataGovernaceServer
 * @date 2025-11-21
 */
@Data
@TableName("DP_ZC_CONFIG_FIELD_CONTROL")
public class DpZcConfigFieldControlEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 页面名
     */
    @TableField("NAME")
    private String name;

    /**
     * 数据最大保留天数
     */
    @TableField("OVERTIMEDAYS")
    private Integer overTimeDays;

    /**
     * 显示字段列表
     */
    @TableField("SHOW_FIELD_LIST")
    private String showFieldList;

    /**
     * 所属用户
     */
    @TableField("USERNAME")
    private String userName;
}
