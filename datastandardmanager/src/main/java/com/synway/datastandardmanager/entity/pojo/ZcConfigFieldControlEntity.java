package com.synway.datastandardmanager.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 表组织字段全局显示配置
 *
 * @author nbj
 * @date 2025年8月14日13:49:24
 */
@Data
@TableName("ZC_CONFIG_FIELD_CONTROL")
public class ZcConfigFieldControlEntity {

    //页面名
    @TableField("NAME")
    private String name;

    //数据最大保留天数
    @TableField("OVERTIMEDAYS")
    private Integer overTimeDays;

    //显示字段列表
    @TableField("SHOW_FIELD_LIST")
    private String showFieldList;

    //所属用户
    @TableField("USERNAME")
    private String userName;

}
