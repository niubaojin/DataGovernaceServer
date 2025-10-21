package com.synway.datastandardmanager.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 限定词历史库
 * @author nbj
 * @date 2025年5月12日16:25:10
 */
@Data
@TableName("SYNLTE.FIELDDETERMINER_HISTORY")
public class FieldDeterminerHisEntity {
    //标准表版本主键
    @TableField("FIELDDETERMINER_VERSION")
    private String fieldDeterminerVersion;

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
    private String determinerState;

    @TableField(exist = false)
    private String determinerStateCh;

    //限定词类型
    @TableField("DETERMINERTYPE")
    private Integer determinerType;

    @TableField(exist = false)
    private String determinerTypeCh;

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
