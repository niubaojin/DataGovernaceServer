package com.synway.datastandardmanager.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 机构单位管理表
 * @author nbj
 * @date 2025年5月13日09:00:35
 */
@Data
@TableName("SYNLTE.STANDARDIZE_UNIT_MANAGE")
public class StandardizeUnitManageEntity {

    @TableField(exist = false)
    private Integer recno;

    //机构编码
    @TableId(type = IdType.NONE)
    @TableField("UNIT_CODE")
    private String unitCode;

    //机构名称
    @TableField("UNIT_NAME")
    private String unitName;

    //机构类型
    @TableField("UNIT_TYPE")
    private Integer unitType;

    @TableField(exist = false)
    private String unitTypeCh;

    //机构级别
    @TableField("UNIT_LEVEL")
    private Integer unitLevel;

    //所属地区
    @TableField("UNIT_AREA")
    private String unitArea;

    //所属地区编码
    @TableField("UNIT_AREA_ID")
    private String unitAreaId;

    //父级机构编码
    @TableField("P_UNIT_CODE")
    private String pUnitCode;

    //父级机构名称
    @TableField("P_UNIT_NAME")
    private String pUnitName;

    //机构信息描述
    @TableField("MEMO")
    private String memo;

    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("CREATE_TIME")
    private Date createTime;

    //更新时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("UPDATE_TIME")
    private Date updateTime;
}
