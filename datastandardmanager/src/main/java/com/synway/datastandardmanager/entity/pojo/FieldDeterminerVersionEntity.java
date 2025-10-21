package com.synway.datastandardmanager.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@TableName("SYNLTE.FIELDDETERMINER_VERSION")
public class FieldDeterminerVersionEntity {

    @TableField(exist = false)
    private int num;

    //标准表版本主键
    @TableField("FIELDDETERMINER_VERSION")
    private String fieldDeterminerVersion;

    //限定词中文名称
    @TableField("DCHINSENAME")
    private String dChineseName;

    //限定词编码
    @NotNull
    @TableField(exist = false)
    private String determinerId;

    //限定词标示符
    @TableField("DNAME")
    private String dName;

    //说明信息
    @TableField("MEMO")
    private String memo;

    //说明信息
    @TableField("VERSION")
    private Integer version;

    //说明信息
    @TableField("VERSIONS")
    private String versions;

    //说明信息
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("UPDATETIME")
    private Date updateTime;

    //修订人
    @TableField("AUTHOR")
    private String author;
}
