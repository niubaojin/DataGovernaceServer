package com.synway.property.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * ADS 监控表信息实体类
 * 用于存储需要监控的 ADS 表的基本信息
 *
 * @author DataGovernaceServer
 * @date 2025-11-21
 */
@Data
@TableName("DP_DATA_STORAGE_ADD_TABLE")
public class DpDataStorageAddTableEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 记录的唯一 ID（主键）
     */
    @TableField(value = "TABLE_ID")
    private String tableId;

    /**
     * 数据库类型 ADS
     */
    @TableField("DATABASE_TYPE")
    private String databaseType;

    /**
     * 项目名称（英文）
     */
    @TableField("PROJECT_NAME")
    private String projectName;

    /**
     * 表英文名
     */
    @TableField("TABLENAME_EN")
    private String tableNameEn;

    /**
     * 表中文名
     */
    @TableField("TABLENAME_CH")
    private String tableNameCh;

    /**
     * 是否为分区表 1:true 0:false
     */
    @TableField("PARTITIONED")
    private String partitioned;

    /**
     * 数据源名称
     */
    @TableField("DATA_SOURCE_NAME")
    private String dataSourceName;

    /**
     * 数据源 id
     */
    @TableField("DATA_SOURCE_ID")
    private String dataSourceId;

    /**
     * 数据中心名称
     */
    @TableField("DATA_CENTER_NAME")
    private String dataCenterName;

    /**
     * 数据中心 id
     */
    @TableField("DATA_CENTER_ID")
    private String dataCenterId;

    /**
     * 是否标准 1:是 0:否
     */
    @TableField("ISSTANDARD")
    private String isStandard;

    /**
     * 是否监控（已控，待控）
     */
    @TableField("ISADDMONITOR")
    private String isAddMonitor;

    /**
     * 是否删除 1:已删除 0:未删除
     */
    @TableField("ISDELETE")
    private String isDelete;

    /**
     * 分区字段
     */
    @TableField("PARTITION_COLUMN")
    private String partitionColumn;
}
