package com.synway.property.pojo.aliAssetStatistics;

import lombok.Data;

/**
 * 阿里资产统计信息对象
 */
@Data
public class SyndmgTableAll {
    private String tableName;                   // 表名
    private String tableProject;                // 项目名
    private String tableComment;                // 表注释
    private int tableType;                      // 平台类型：1、odps, 2、hc, 3、hp
    private long lifeCycle;                     // 生命周期
    private long tableSize = 0;                 // 表大小
    private String tableLastDataModifiedTime;   // 数据最后修改时间
    private String partitionData;               // 分区日期
    private long partitionCount;                // 分区数据量（条数）
    private int isPartition;                    // 是否分区 0是 1不是
    private String isPartitionStr;              // 是否分区
    private String tableLastMetaModifiedTime;   // 表最后修改时间(DDL最后变更时间)
    private String tableCreatedTime;            // 表创建时间
    private String insertDataTime;              // 数据插入时间
    private long tableAllCount = 0;             // 表总数据量（条数）
    private long partitionSize;                 // 分区大小
    private int partitionNum;                   // 分区个数
    private String partColumn;                  // 分区列
    private String dataId;                      // 数据源关联字段

}
