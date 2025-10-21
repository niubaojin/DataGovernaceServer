package com.synway.datastandardmanager.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 数据元版本管理
 * @author
 * @date 2025年5月13日10:03:36
 */
@Data
@TableName("SYNLTE.SYNLTEFIELD_VERSION")
public class SynlteFieldVersionEntity {

    @TableField(exist = false)
    private Integer num;

    //标准表版本主键
    @TableField("FIELDID_VERSION")
    private String fieldIdVersion;

    //数据元唯一编码
    @TableField("FIEDID")
    private String fieldId;

    //数据库字段名称
    @TableField("COLUMNNAME")
    private String columnName;

    //数据元中文名称
    @NotNull
    @TableField(exist = false)
    private String fieldChineseName;

    //修订内容
    @TableField("MEMO")
    private String memo;

    //小版本号
    @TableField("VERSION")
    private Integer version;

    //大版本号
    @TableField("VERSIONS")
    private String versions;

    //更新日期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("UPDATETIME")
    private Date updateTime;

    //更新人
    @TableField("AUTHOR")
    private String author;
}
