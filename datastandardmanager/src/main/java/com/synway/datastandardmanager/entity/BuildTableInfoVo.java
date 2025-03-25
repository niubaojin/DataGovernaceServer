package com.synway.datastandardmanager.entity;

import com.synway.datastandardmanager.pojo.ObjectField;
import lombok.Data;

import java.util.List;

/**
 * @author
 * @date 2019/10/25 13:46
 */
@Data
public class BuildTableInfoVo {

    /**
     * 表中文名
     */
    private String tableNameCH = "";
    /**
     * 标准表id值
     */
    private String objectId;
    /**
     * 表协议id
     */
    private String tableId;
    /**
     * 项目名 阿里云平台使用
     */
    private String schema;
    /**
     * 华为云平台使用
     */
    private String projectName;
    /**
     * 数据库类型 HIVE/ODPS 等
     */
    private String dsType;
    /**
     * 数据仓库中的数据源id
     */
    private String dataId;
    /**
     * 表英文名  YSK_01001_CS12222_001
     */
    private String tableName;
    /**
     * odps 建表中使用的字段信息
     */
    private List<ObjectField> objectFields;
    /**
     * ads 用到的分区信息
     */
    private String partitionFirst;
    private String partitionFirstNum;
    private String partitionSecond;
    private String partitionSecondNum;
    /**
     * 分类信息
     */

    //组织分类
    private String organizationId;
    private String organizationCh;
    //来源分类
    private String datasourceId;
    private String datasourceCh;

    private String primaryOrganizationCh="";
    private String secondaryOrganizationCh="";
    private String primaryDatasourceCh="";
    private String secondaryDatasourceCh="";
    // 新增数据组织三级分类
    private String threeOrganizationCh="";
    // 审批信息标识
    private String approvalId;
    //odps分区字段
    private List<String> partitionColumns;

    //更新表类型 阿里云使用
    private String isActiveTable;
    /**
     *     20200603 在标准化跳转到表登记页面时，需要添加 数据中心名 数据源名称 将这2个字段添加到表 STANDARD_TABLE_CREATED
     *     存储的是数据中心id
     */
    private String dataCenterName="";
    /**
     * 与dataid值相同
     */
    private String dataResourceName="";
    /**
     * 20200722  用户id信息
     */
    private String userId;
    /**
     * 20200923 在datahub上进行建表
     * topic信息
     */
    private String topicName;
    /**
     * 通道数
     */
    private String shardCount;
    /**
     * datahub 用到的备注信息
     */
    private String comment;
    /**
     * 建表描述信息 不是用在建表里面
     */
    private String createTableMemo;
    /**
     * 登录的用户名
     */
    private String userName;

    /**
     * 华为云使用这个字段信息
     */
    private List<ObjectField> columnData;

    // 以下为presto特有的参数
    /**
     * 定义该类型是否生效 0未生效 1生效
     */
    private Short objectFlag;

    /**
     * 02e0016：数据来源名称
     */
    private String dataSource;

    private Integer objectRowkey;

    /**
     * hbase分区类型，0为按天分区，1为按固定分区，2为按天进行二次分区 3多天 一分区
     */
    private Byte regionType;

    /**
     * hbase多天一分区则表示天数，如果是一天多分区，则表示一天的分区数，一天一分区和固定分区该值不生效
     */
    private Short regionCount;

    /**
     * rowkeyfield表示组成datarowkey的业务字段所在列的列名
     */
    private String rowkeyField;

    /**
     * es索引类型
     */
    private Byte esSplitType;

    /**
     * es多天一索引天数，如果是按天建索引，该值不生效
     */
    private Short esSplitCount;

    /**
     * es索引分片数
     */
    private Short esShards;

    /**
     * geohash是否冗余
     */
    private Boolean geoRedundant;

    /**
     * 只查询es source，如果该值为1 则字段必须存在source中
     */
    private Boolean esSourceOnly;

    /**
     * 表空间
     */
    private String schemaName;

    /**
     * 是否禁用: 0否 1是
     */
    private Boolean disabled;

    /**
     * 表的生命周期  ck  hbase 使用
     */
    private Integer lifeCycle;

    /**
     * 生命周期天数
     */
    private Integer lifeCycleDays;

    /**
     * hbase会用到的注释信息
     */
    private String prestoMemo;

    // 以下为 clickhouse特有的参数
    /**
     * 表引擎  必填
     */
    private String engine = "MergeTree";

    /**
     * 排序键  必填
     * 可以存在多个 用英文逗号分隔
     */
    private List<CkColumn> ckOrderByColumn;

    /**
     * 分区主键的配置信息
     */
    private List<CkColumn> ckPartitionColumn;

    //是否分区
    private Integer isPartition;

    /**
     * 分区类型
     */
    private Integer partitionType;

    //分区数(KAFKA创建主题时配置的分区数值；datahub平台代表通道数)
    private Integer partitionCount;


}
