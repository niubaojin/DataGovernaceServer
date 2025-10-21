package com.synway.datastandardmanager.entity.vo.createTable;

import com.synway.datastandardmanager.entity.pojo.ObjectFieldEntity;
import lombok.Data;

import java.util.List;

/**
 * @author
 * @date 2019/10/25 13:46
 */
@Data
public class BuildTableInfoVO {

    private String tableId;                         //表协议id
    private String objectId;                        //标准表id值
    private String tableName;                       //表英文名 YSK_01001_CS12222_001
    private String tableNameCH = "";                //表中文名
    private String schema;                          //项目名：阿里云平台使用
    private String projectName;                     //项目名：华为云平台使用
    private String schemaName;                      //表空间
    private String dsType;                          //数据库类型 HIVE/ODPS等
    private String dataId;                          //数据仓库中的数据源id
    private List<ObjectFieldEntity> objectFields;   //odps 建表中使用的字段信息
    private List<ObjectFieldEntity> columnData;     //华为云使用这个字段信息

    /**
     * 分类信息
     */
    private String organizationId;  //组织分类
    private String organizationCh;
    private String datasourceId;    //来源分类
    private String datasourceCh;
    private String primaryOrganizationCh = "";
    private String secondaryOrganizationCh = "";
    private String primaryDatasourceCh = "";
    private String secondaryDatasourceCh = "";
    private String threeOrganizationCh = "";    // 新增数据组织三级分类
    private String approvalId;                  // 审批信息标识
    private List<String> partitionColumns;      //odps分区字段
    private String isActiveTable;               //更新表类型 阿里云使用

    /**
     * 20200603 在标准化跳转到表登记页面时，需要添加 数据中心名 数据源名称 将这2个字段添加到表 STANDARD_TABLE_CREATED
     */
    private String dataCenterName = "";         //存储的是数据中心id
    private String dataResourceName = "";       //与dataid值相同
    private String userId;                      //20200722  用户id信息
    private String topicName;                   //20200923：在datahub上进行建表，topic信息
    private String shardCount;                  //通道数
    private String comment;                     //datahub 用到的备注信息
    private String createTableMemo;             //建表描述信息 不是用在建表里面
    private String userName;                    //登录的用户名

    /**
     * hbase分区类型，0为按天分区，1为按固定分区，2为按天进行二次分区 3多天 一分区
     */
    private Byte regionType;

    /**
     * hbase多天一分区则表示天数，如果是一天多分区，则表示一天的分区数，一天一分区和固定分区该值不生效
     */
    private Short regionCount;
    private String rowkeyField;         //rowkeyfield表示组成datarowkey的业务字段所在列的列名
    private Byte esSplitType;           //es索引类型
    private Short esSplitCount;         //es多天一索引天数，如果是按天建索引，该值不生效
    private Short esShards;             //es索引分片数
    private Boolean geoRedundant;       //geohash是否冗余
    private Boolean esSourceOnly;       //只查询es source，如果该值为1 则字段必须存在source中
    private Boolean disabled;           //是否禁用: 0否 1是
    private Integer lifeCycle;          //表的生命周期，ck/hbase使用
    private Integer lifeCycleDays;      //生命周期天数
    private String prestoMemo;          //hbase会用到的注释信息

    /**
     * ads 用到的分区信息
     */
    private String partitionFirst;
    private String partitionFirstNum;
    private String partitionSecond;
    private String partitionSecondNum;

    /**
     * 以下为 clickhouse特有的参数
     */
    private String engine = "MergeTree";        //表引擎  必填
    private List<CkColumnVO> ckOrderByColumn;   //排序键，必填，可以存在多个用英文逗号分隔
    private List<CkColumnVO> ckPartitionColumn; //分区主键的配置信息
    private Integer isPartition;                //是否分区
    private Integer partitionType;              //分区类型
    private Integer partitionCount;             //分区数(KAFKA创建主题时配置的分区数值；datahub平台代表通道数)

    /**
     * 以下为presto特有的参数
     */
    private Short objectFlag;           //定义该类型是否生效 0未生效 1生效
    private String dataSource;          //数据来源名称
    private Integer objectRowkey;

    //是否建表工具
    private Boolean isCreatTableTool;

}
