package com.synway.datastandardmanager.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 通过标准管理页面已经创建的表
 */
@Data
@TableName("STANDARD_TABLE_CREATED")
public class StandardTableCreatedEntity {

    //在数据库中创建的表名
    @TableField("TABLE_NAME")
    private String tableName;

    // 包含项目名的表名
    @TableField(exist = false)
    private String tableNameEn;

    //在数据库中创建的表对应的项目名
    @TableField("TABLE_PROJECT")
    private String tableProject;

    //对应的数据库类型
    @TableField("TABLE_BASE")
    private String tableBase;

    //objectId
    @TableField("OBJECT_ID")
    private String objectId;

    //自增主键ID
    @TableField("ID")
    private String id;

    //表的创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("CREATE_TIME")
    private Date createTime;

    //表中文名
    @TableField("TABLE_NAME_CH")
    private String tableNameCh;

    //数据中心名
    @TableField("DATA_CENTER_NAME")
    private String dataCenterName;

    //数据源名称
    @TableField("DATA_RESOURCE_NAME")
    private String dataResourceName;

    //创建人
    @TableField(exist = false)
    private String createUser;

    // 因为有后续操作，所以需要 dataId;
    @TableField(exist = false)
    private String dataId;

    @TableField(exist = false)
    private int importFlag = 0;

    @TableField(exist = false)
    private String tableId;

}
