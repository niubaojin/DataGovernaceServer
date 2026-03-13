package com.synway.property.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 监控的 ADS 表的详细数据信息
 * 用于存储 ADS 表的监控统计数据
 *
 * @author DataGovernaceServer
 * @date 2025-11-21
 */
@Data
@TableName("DP_DATA_STORAGE_TABLE_FULL")
public class DpDataStorageTableFullEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 表英文名
     */
    @TableField("TABLE_NAME_EN")
    private String tableNameEn;

    /**
     * 表中文名
     */
    @TableField("TABLE_NAME_CH")
    private String tableNameCh;

    /**
     * 表当日的数据量
     */
    @TableField("TABLE_TODAY_SUM")
    private Long tableTodaySum;

    /**
     * 表当日表数据总量
     */
    @TableField("TABLE_ALL_SUM")
    private Long tableAllSum;

    /**
     * 表统计日期 yyyyMMddHH24miss
     */
    @TableField("STATISTIC_DAY")
    private String statisticDay;

    /**
     * 表项目名
     */
    @TableField("TABLE_PROJECT")
    private String tableProject;

    /**
     * 唯一性 ID
     */
    @TableField("STATISTIC_ID")
    private String statisticId;

    /**
     * 数据库类型 odps/ads
     */
    @TableField("DATABASE_TYPE")
    private String databaseType;
}
