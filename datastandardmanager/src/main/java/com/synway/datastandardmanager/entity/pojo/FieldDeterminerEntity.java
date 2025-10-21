package com.synway.datastandardmanager.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 限定词库
 * @author nbj
 * @date 2025年5月12日16:17:41
 */
@Data
@TableName("SYNLTE.FIELDDETERMINER")
public class FieldDeterminerEntity {

    @TableField(exist = false)
    private int num;

    //限定词编码
    @TableField("DETERMINERID")
    private String determinerId;

    //限定词中文名称
    @TableField("DCHINSENAME")
    private String dchinseName;

    //限定词标示符
    @TableField("DNAME")
    private String dname;

    //说明信息
    @TableField("MEMO")
    private String memo;

    //修订人
    @TableField("AUTHOR")
    private String author;

    //修订时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("MODDATE")
    private Date modDate;

    //限定词状态
    @TableField("DETERMINERSTATE")
    private String determinerStateNum;

    /**
     * 前端必传
     * 限定词状态，与数据元保持一致，01：新建原始状态，05：发布，07：废纸禁用
     */
    @TableField(exist = false)
    private String determinerState;

    //限定词类型
    @TableField("DETERMINERTYPE")
    private Integer determinerTypeNum;

    /**
     * 前端必传
     * 限定词类型，1：公安标准，0：非标准
     */
    @TableField(exist = false)
    private String determinerType;

    //版本信息
    @TableField("VERSIONS")
    private String versions;

    //版本发布日期
    @TableField("RELEASEDATE")
    private Integer releaseDate;

    //制作厂商
    @TableField("FACTURER")
    private String facturer;

    //提交机构
    @TableField("REG_ORG")
    private String regOrg;

    //审核时间
    @JsonFormat(pattern = "yyyyMMdd",timezone = "GMT+8")
    @DateTimeFormat(pattern ="yyyyMMdd")
    @TableField("ON_DATE")
    private Date onDate;
}
