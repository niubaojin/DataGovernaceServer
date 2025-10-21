package com.synway.datastandardmanager.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 数据标准表建表信息
 * @author nbj
 * @date 2025年5月12日20:05:43
 */
@Data
@TableName("SYNLTE.OBJECT_STORE_INFO")
public class ObjectStoreInfoEntity {
    //表存储信息ID
    @TableId(type = IdType.NONE)
    @TableField("TABLEINFOID")
    private String tableInfoId;

    //标准数据项集编码
    @TableField("TABLEID")
    private String tableId;

    //表英文名称
    @TableField("TABLENAME")
    private String tableName;

    //数据中文名称
    @TableField("OBJECTNAME")
    private String objectName;

    //平台类型
    @TableField("STORE_TYPE")
    private Integer storeType;

    @TableField(exist = false)
    private String storeTypeCh;

    //表空间名称
    @TableField("PROJECT_NAME")
    private String projectName;

    //表空间中文名称
    @TableField("PROJECT_NAME_CN")
    private String projectNameCh;

    //存储描述信息
    @TableField("MEMO")
    private String memo;

    //创建者
    @TableField("CREATER")
    private String creater;

    //创建者代码
    @TableField("CREATER_ID")
    private String createrId;

    //表创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("TABLE_CREATE_TIME")
    private Date tableCreateTime;

    //表结构修改时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("TABLE_MOD_TIME")
    private Date tableModTime;

    //是否入库
    @TableField("IMPORT_FLAG")
    private Integer importFlag;

    //是否为分区表
    @TableField("ISPARTITION")
    private Integer isPartition;

    //是否实时表
    @TableField("ISACTIVETABLE")
    private Integer isActiveTable;

    //生命周期
    @TableField("LIFECYCLE")
    private Integer lifeCycle;

    @TableField(exist = false)
    private String lifeCycleStr;

    //数据源关联字段
    @TableField("DATAID")
    private String dataId;

    //服务默认查询标识
    @TableField("SEARCHFLAG")
    private Integer searchFlag;

    //分区类型
    @TableField("PARTITIONTYPE")
    private Integer partitionType;

    //分区数
    @TableField("PARTITIONCOUNT")
    private Integer partitionCount;

    /**
     * 建表信息管理页面需要展示的字段信息
     */
    //标准英文名
    @TableField(exist = false)
    private String standTableName;
    //数据中心名称
    @TableField(exist = false)
    private String centerName;
    @TableField(exist = false)
    private String centerId;
    //存储数据源(数据源名称)
    @TableField(exist = false)
    private String resName;
    // 数据源id
    @TableField(exist = false)
    private String resId;
    //注册资源数
    @TableField(exist = false)
    private Integer registerCount;
    //数据组织id
    @TableField(exist = false)
    private String organizationClassificationId;
    //数据组织中文
    @TableField(exist = false)
    private String organizationClassificationCh;
    //表字段信息
    @TableField(exist = false)
    private List<ObjectStoreFieldInfoEntity> objectStoreFieldInfos;
    // 真实表名（物理表名）
    @TableField(exist = false)
    private String realTableName;
    @TableField(exist = false)
    private Integer objectId;

}
