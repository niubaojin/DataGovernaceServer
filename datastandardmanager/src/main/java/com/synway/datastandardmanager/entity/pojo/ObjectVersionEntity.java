package com.synway.datastandardmanager.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 数据表版本管理
 * @author nbj
 * @date 2025年5月12日16:59:51
 */
@Data
@TableName("SYNLTE.OBJECTE_VERSION")
public class ObjectVersionEntity {

    @TableField(exist = false)
    private int num;

    //标准表版本主键
    @TableField("OBJECTID_VERSION")
    private String objectidVersion;

    //基准表ID
    @TableField("OBJECTID")
    private String objectId;

    //数据集名称
    @NotNull
    @TableField(exist = false)
    private String objectName;

    //基准表名
    @TableField("TABLENAME")
    private String tableName;

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
