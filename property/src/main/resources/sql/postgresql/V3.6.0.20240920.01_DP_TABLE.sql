-- Create table 表组织资产
create table DP_TABLE_ORGANIZATION_ASSETS
(
    SJXJBM                     VARCHAR(50),
    NAME                       VARCHAR(600),
    TABLE_NAME_EN              VARCHAR(600),
    PRIMARY_DATASOURCE_CH      VARCHAR(150),
    SECONDARY_DATASOURCE_CH    VARCHAR(150),
    PRIMARY_ORGANIZATION_CH    VARCHAR(150),
    SECONDARY_ORGANIZATION_CH  VARCHAR(150),
    FACTOR_ONE_NAME            VARCHAR(150),
    FACTOR_TWO_NAME            VARCHAR(150),
    FACTOR_DETAILED_NAME       VARCHAR(150),
    FACTOR_ATTRIBUTES_NAME     VARCHAR(150),
    TABLE_ALL_COUNT            NUMERIC,
    TABLE_SIZE                 NUMERIC,
    TABLE_PROJECT              VARCHAR(150),
    LIFE_CYCLE                 VARCHAR(150),
    TABLE_TYPE                 VARCHAR(150),
    YESTERDAY_COUNT            NUMERIC,
    PARTITION_MESSAGE          VARCHAR(150),
    AVERAGE_DAILY_VOLUME       NUMERIC,
    VALUE_RATE                 NUMERIC,
    NULL_RATE                  NUMERIC,
    TABLE_STATE                VARCHAR(150),
    UPDATE_TYPE                VARCHAR(150),
    STATISTICS_TIME            DATE,
    FREQUENCY                  VARCHAR(650),
    LIVETYPE                   VARCHAR(650),
    REGISTER_STATE             VARCHAR(255) default -1,
    OBJECT_ID                  VARCHAR(100),
    AVERAGE_DAILY_SIZE         NUMERIC,
    OBJECT_STATE               VARCHAR(50),
    THREE_ORGANIZATION_CH      VARCHAR(150),
    LABELS                     VARCHAR(500),
    UPDATE_PERIOD              VARCHAR(100),
    UPDATE_DATE                VARCHAR(100),
    DATARESOURCE_CODE          VARCHAR(100),
    EXCEPTION_MESSAGE          VARCHAR(100),
    LIFE_CYCLE_STATUS          VARCHAR(50),
    LASTDATAMODIFIEDTIME       VARCHAR(100),
    TABLECREATEDTIME           VARCHAR(100),
    PARTITION_NUM              VARCHAR(255),
    LASTVISITEDTIME            VARCHAR(255),
    TABLEUSECOUNTOFMONTH       VARCHAR(255),
    RESOURCEID                 VARCHAR(255),
    THREELEVEL_ORGANIZATION_CH VARCHAR(200),
    ORGANIZATION_ID_LAST_LEVEL VARCHAR(150),
    DATASOURCE_LAST_LEVEL      VARCHAR(150),
    RES_NAME                   VARCHAR(200),
    ISSTANDARD                 VARCHAR(100),
    ALARM_LEVEL                VARCHAR(100),
	SJZYBQ5                    VARCHAR(50)
);
-- Add comments to the table
comment on table DP_TABLE_ORGANIZATION_ASSETS
    is '表组织资产的相关数据';
-- Add comments to the columns
comment on column DP_TABLE_ORGANIZATION_ASSETS.SJXJBM
    is '表协议名';
comment on column DP_TABLE_ORGANIZATION_ASSETS.NAME
    is '表中文名';
comment on column DP_TABLE_ORGANIZATION_ASSETS.TABLE_NAME_EN
    is '表英文名';
comment on column DP_TABLE_ORGANIZATION_ASSETS.PRIMARY_DATASOURCE_CH
    is '数据来源一级分类中文名';
comment on column DP_TABLE_ORGANIZATION_ASSETS.SECONDARY_DATASOURCE_CH
    is '数据来源二级分类中文名';
comment on column DP_TABLE_ORGANIZATION_ASSETS.PRIMARY_ORGANIZATION_CH
    is '数据组织一级分类中文名';
comment on column DP_TABLE_ORGANIZATION_ASSETS.SECONDARY_ORGANIZATION_CH
    is '数据组织二级分类中文名';
comment on column DP_TABLE_ORGANIZATION_ASSETS.FACTOR_ONE_NAME
    is '数据资源要素一级分类中文名';
comment on column DP_TABLE_ORGANIZATION_ASSETS.FACTOR_TWO_NAME
    is '数据资源要素二级分类中文名';
comment on column DP_TABLE_ORGANIZATION_ASSETS.FACTOR_DETAILED_NAME
    is '数据资源要素细目分类代码名称';
comment on column DP_TABLE_ORGANIZATION_ASSETS.FACTOR_ATTRIBUTES_NAME
    is '数据资源属性分类代码名称';
comment on column DP_TABLE_ORGANIZATION_ASSETS.TABLE_ALL_COUNT
    is '表数据总行数 到昨天分区';
comment on column DP_TABLE_ORGANIZATION_ASSETS.TABLE_SIZE
    is '表数据总大小 到昨天分区 byte';
comment on column DP_TABLE_ORGANIZATION_ASSETS.TABLE_PROJECT
    is '表的项目名';
comment on column DP_TABLE_ORGANIZATION_ASSETS.LIFE_CYCLE
    is '表的生命周期';
comment on column DP_TABLE_ORGANIZATION_ASSETS.TABLE_TYPE
    is '表类型 odps/ads/''''';
comment on column DP_TABLE_ORGANIZATION_ASSETS.YESTERDAY_COUNT
    is '昨天分区的数据量';
comment on column DP_TABLE_ORGANIZATION_ASSETS.PARTITION_MESSAGE
    is '是否为分区表  0:分区表  1:不是分区表  -1：不确定是否为分区表';
comment on column DP_TABLE_ORGANIZATION_ASSETS.AVERAGE_DAILY_VOLUME
    is '日均数据量';
comment on column DP_TABLE_ORGANIZATION_ASSETS.VALUE_RATE
    is '有值率';
comment on column DP_TABLE_ORGANIZATION_ASSETS.NULL_RATE
    is '空值率';
comment on column DP_TABLE_ORGANIZATION_ASSETS.TABLE_STATE
    is '状态  正常/异常';
comment on column DP_TABLE_ORGANIZATION_ASSETS.UPDATE_TYPE
    is '全量/增量/';
comment on column DP_TABLE_ORGANIZATION_ASSETS.STATISTICS_TIME
    is '统计时间';
comment on column DP_TABLE_ORGANIZATION_ASSETS.FREQUENCY
    is '最近一个月使用次数/总使用次数';
comment on column DP_TABLE_ORGANIZATION_ASSETS.LIVETYPE
    is 'NONLIVING：死表 LIVING：活表';
comment on column DP_TABLE_ORGANIZATION_ASSETS.REGISTER_STATE
    is '注册状态 -1:未注册 1:已注册';
comment on column DP_TABLE_ORGANIZATION_ASSETS.OBJECT_ID
    is 'object表中的objectid';
comment on column DP_TABLE_ORGANIZATION_ASSETS.AVERAGE_DAILY_SIZE
    is '日均数据大小';
comment on column DP_TABLE_ORGANIZATION_ASSETS.OBJECT_STATE
    is '停用，启用状态，1为启用';
comment on column DP_TABLE_ORGANIZATION_ASSETS.THREE_ORGANIZATION_CH
    is '数据组织三级分类中文名';
comment on column DP_TABLE_ORGANIZATION_ASSETS.LABELS
    is '数据资源标签';
comment on column DP_TABLE_ORGANIZATION_ASSETS.UPDATE_PERIOD
    is '更新批次';
comment on column DP_TABLE_ORGANIZATION_ASSETS.UPDATE_DATE
    is '更新周期';
comment on column DP_TABLE_ORGANIZATION_ASSETS.DATARESOURCE_CODE
    is '资源服务平台组织机构代码';
comment on column DP_TABLE_ORGANIZATION_ASSETS.EXCEPTION_MESSAGE
    is '异常信息';
comment on column DP_TABLE_ORGANIZATION_ASSETS.LIFE_CYCLE_STATUS
    is '生命周期审批状态';
comment on column DP_TABLE_ORGANIZATION_ASSETS.LASTDATAMODIFIEDTIME
    is '最后修改时间';
comment on column DP_TABLE_ORGANIZATION_ASSETS.TABLECREATEDTIME
    is '表创建时间';
comment on column DP_TABLE_ORGANIZATION_ASSETS.PARTITION_NUM
    is '分区数量';
comment on column DP_TABLE_ORGANIZATION_ASSETS.LASTVISITEDTIME
    is '最后访问时间';
comment on column DP_TABLE_ORGANIZATION_ASSETS.TABLEUSECOUNTOFMONTH
    is '月使用量';
comment on column DP_TABLE_ORGANIZATION_ASSETS.RESOURCEID
    is '数据资源id，用于管理权限表';
comment on column DP_TABLE_ORGANIZATION_ASSETS.THREELEVEL_ORGANIZATION_CH
    is '数据组织三级分类中文名';
comment on column DP_TABLE_ORGANIZATION_ASSETS.ORGANIZATION_ID_LAST_LEVEL
    is '数据组织分类id(最后一级)';
comment on column DP_TABLE_ORGANIZATION_ASSETS.DATASOURCE_LAST_LEVEL
    is '数据来源分类id(最后一级)';
comment on column DP_TABLE_ORGANIZATION_ASSETS.RES_NAME
    is '数据源名称';
comment on column DP_TABLE_ORGANIZATION_ASSETS.ISSTANDARD
    is '是否对标';
comment on column DP_TABLE_ORGANIZATION_ASSETS.ALARM_LEVEL
    is '告警级别';
comment on column DP_TABLE_ORGANIZATION_ASSETS.SJZYBQ5
    is '数据资源标签5';
-- Create/Recreate indexes
create index DATE1_INDEX3 on DP_TABLE_ORGANIZATION_ASSETS (STATISTICS_TIME);
--------------------------------------------------------------------------------

create table DP_DATA_STORAGE_ADD_TABLE
(
    DATABASE_TYPE    VARCHAR(20),
    PROJECT_NAME     VARCHAR(100),
    TABLENAME_EN     VARCHAR(600),
    TABLENAME_CH     VARCHAR(600),
    TABLE_ID         VARCHAR(600) not null,
    PARTITIONED      VARCHAR(10),
    DATA_SOURCE_NAME VARCHAR(100),
    DATA_SOURCE_ID   VARCHAR(100),
    DATA_CENTER_NAME VARCHAR(100),
    DATA_CENTER_ID   VARCHAR(100),
    ISSTANDARD       VARCHAR(50),
    ISADDMONITOR     VARCHAR(50),
    ISDELETE         VARCHAR(50),
    PARTITION_COLUMN VARCHAR(50)
);
comment on table DP_DATA_STORAGE_ADD_TABLE
    is '需要监控的ADS表信息';
-- Add comments to the columns
comment on column DP_DATA_STORAGE_ADD_TABLE.DATABASE_TYPE
    is '数据库类型ADS';
comment on column DP_DATA_STORAGE_ADD_TABLE.PROJECT_NAME
    is '项目名称（英文）';
comment on column DP_DATA_STORAGE_ADD_TABLE.TABLENAME_EN
    is '表英文名';
comment on column DP_DATA_STORAGE_ADD_TABLE.TABLENAME_CH
    is '表中文名';
comment on column DP_DATA_STORAGE_ADD_TABLE.TABLE_ID
    is '记录的唯一ID';
comment on column DP_DATA_STORAGE_ADD_TABLE.PARTITIONED
    is '是否为分区表 1:true  0:false';
comment on column DP_DATA_STORAGE_ADD_TABLE.DATA_SOURCE_NAME
    is '数据源名称';
comment on column DP_DATA_STORAGE_ADD_TABLE.DATA_SOURCE_ID
    is '数据源id';
comment on column DP_DATA_STORAGE_ADD_TABLE.DATA_CENTER_NAME
    is '数据中心名称';
comment on column DP_DATA_STORAGE_ADD_TABLE.DATA_CENTER_ID
    is '数据中心id';
comment on column DP_DATA_STORAGE_ADD_TABLE.ISSTANDARD
    is '是否标准 1:是 0:否';
comment on column DP_DATA_STORAGE_ADD_TABLE.ISADDMONITOR
    is '是否监控 （已控，待控）';
comment on column DP_DATA_STORAGE_ADD_TABLE.ISDELETE
    is '是否删除 1:已删除 0:未删除';
comment on column DP_DATA_STORAGE_ADD_TABLE.PARTITION_COLUMN
    is '分区字段';

--------------------------------------------------------------------------------------------------------
create table DP_DATA_STORAGE_TABLE_FULL
(
    TABLE_NAME_EN   VARCHAR(600) not null,
    TABLE_NAME_CH   VARCHAR(600),
    TABLE_TODAY_SUM NUMERIC,
    TABLE_ALL_SUM   NUMERIC,
    STATISTIC_DAY   VARCHAR(20),
    TABLE_PROJECT   VARCHAR(40),
    STATISTIC_ID    VARCHAR(60) not null,
    DATABASE_TYPE   VARCHAR(60)
);
comment on table DP_DATA_STORAGE_TABLE_FULL
    is '监控的ADS表的详细数据信息';
-- Add comments to the columns
comment on column DP_DATA_STORAGE_TABLE_FULL.TABLE_NAME_EN
    is '表英文名';
comment on column DP_DATA_STORAGE_TABLE_FULL.TABLE_NAME_CH
    is '表中文名';
comment on column DP_DATA_STORAGE_TABLE_FULL.TABLE_TODAY_SUM
    is '表当日的数据量';
comment on column DP_DATA_STORAGE_TABLE_FULL.TABLE_ALL_SUM
    is '表当日表数据总量';
comment on column DP_DATA_STORAGE_TABLE_FULL.STATISTIC_DAY
    is '表统计日期yyyyMMddHH24miss';
comment on column DP_DATA_STORAGE_TABLE_FULL.TABLE_PROJECT
    is '表项目名';
comment on column DP_DATA_STORAGE_TABLE_FULL.STATISTIC_ID
    is '唯一性ID';
comment on column DP_DATA_STORAGE_TABLE_FULL.DATABASE_TYPE
    is '数据库类型odps/ads';

-----------------------------------------------------------------------------------------------------
create table DP_DATA_REGISTER
(
    SJXJBM               VARCHAR(100),
    TABLE_NAME_EN        VARCHAR(100),
    OBJECT_ID            VARCHAR(100),
    APPROVAL_ID          VARCHAR(100),
    APPROVAL_STATUS      VARCHAR(10),
    APPROVAL_CREATE_TIME VARCHAR(100),
    APPROVAL_UPDATE_TIME VARCHAR(100),
    APPROVAL_TYPE        VARCHAR(50),
    TABLE_PROJECT        VARCHAR(50)
);
-- Add comments to the columns
comment on column DP_DATA_REGISTER.SJXJBM
    is '协议编号';
comment on column DP_DATA_REGISTER.TABLE_NAME_EN
    is '表名';
comment on column DP_DATA_REGISTER.OBJECT_ID
    is '标准表id';
comment on column DP_DATA_REGISTER.APPROVAL_ID
    is '审批id';
comment on column DP_DATA_REGISTER.APPROVAL_STATUS
    is '审批状态(1为审批成功，2为审批中，4为审批失败)';
comment on column DP_DATA_REGISTER.APPROVAL_CREATE_TIME
    is '审批创建时间';
comment on column DP_DATA_REGISTER.APPROVAL_UPDATE_TIME
    is '审批更新时间';
comment on column DP_DATA_REGISTER.APPROVAL_TYPE
    is '审批类型';
comment on column DP_DATA_REGISTER.TABLE_PROJECT
    is '库名';

-----------------------------------------------------------------------------------------------------
create table DP_STORECYCLEANDUSEHEAT
(
    ID                   VARCHAR(36),
    LASTDATE             VARCHAR(32),
    TABLETYPE            VARCHAR(2),
    CLASSNAME            VARCHAR(32),
    SUBCLASS             VARCHAR(1000),
    TABLENAMEEN          VARCHAR(1000),
    TABLENAMEZH          VARCHAR(1000),
    LIFECYCLE            NUMERIC(8),
    TABLEALLCOUNT        NUMERIC(36),
    TABLESIZE            NUMERIC(36),
    TABLEUSECOUNT        NUMERIC(36),
    TABLEUSECOUNTOFDAY   NUMERIC(36),
    TABLEUSECOUNTOFWEEK  NUMERIC(36),
    TABLEUSECOUNTOFMONTH NUMERIC(36)
);

-----------------------------------------------------------------------------------------------------
-- 存储数据历程的相关信息
create table DP_DATAPROCESS
(
    AREA_ID        VARCHAR(100),
    DEPT           VARCHAR(100),
    OPERATOR       VARCHAR(100),
    POLICENO       VARCHAR(100),
    APP_ID         VARCHAR(100),
    MODULE_ID      VARCHAR(100),
    OPERATE_TIME   VARCHAR(100) not null,
    IP             VARCHAR(100),
    LOG_TYPE       VARCHAR(100),
    DIGEST         VARCHAR(1000),
    DATA_BASE_TYPE VARCHAR(100),
    TABLE_NAME_EN  VARCHAR(100),
    TABLE_ID       VARCHAR(100),
    INSERT_TIME    DATE default CURRENT_DATE not null,
    TASK_NAME      VARCHAR(200),
    ID             NUMERIC not null
);
comment on table DP_DATAPROCESS
    is '存储数据历程的相关信息';
comment on column DP_DATAPROCESS.AREA_ID
    is '地区行政编号（参考标准 附录一）';
comment on column DP_DATAPROCESS.DEPT
    is '部门名称';
comment on column DP_DATAPROCESS.OPERATOR
    is '操作人名称';
comment on column DP_DATAPROCESS.POLICENO
    is '警号';
comment on column DP_DATAPROCESS.APP_ID
    is '系统代码';
comment on column DP_DATAPROCESS.MODULE_ID
    is '模块代码 (大写) 仓库(SJCK)
探查(SJTC)
接入(SJJR)
标准(BZGL)
质检(ZLJC)
对账(SJDZ)
资产(ZCGL)
血缘(SJXY)
家产登记(JCDJ)
运维管理(YWGL)';
comment on column DP_DATAPROCESS.OPERATE_TIME
    is '操作时间   YYYY-MM-DD HH:mm:ss';
comment on column DP_DATAPROCESS.IP
    is 'IP地址/操作设备地址';
comment on column DP_DATAPROCESS.LOG_TYPE
    is '操作类型代码　(大写)探查
  业务探查：SJTC001
接入任务
  新建：SJJR001
  删除：SJJR001
  修改：SJJR001
标准
  建表：BZGL001
  登记：BZGL002
质检任务
  新建：ZLJC001
  删除：ZLJC001
  修改：ZLJC001';
comment on column DP_DATAPROCESS.DIGEST
    is '日志摘要   操作行为的简要说明　不同模块对应的格式不同';
comment on column DP_DATAPROCESS.DATA_BASE_TYPE
    is '数据库类型 odps/ads/hive/hbase';
comment on column DP_DATAPROCESS.TABLE_NAME_EN
    is '包括  项目名.表名';
comment on column DP_DATAPROCESS.TABLE_ID
    is '表协议ID';
comment on column DP_DATAPROCESS.INSERT_TIME
    is '该条记录的插入时间';
comment on column DP_DATAPROCESS.TASK_NAME
    is '任务名 只有数据接入，质量检测才有这个';
comment on column DP_DATAPROCESS.ID
    is '唯一主键';

-----------------------------------------------------------------------------------------------------
--数据库概况
create table DP_DB_STATE
(
    NAME            VARCHAR(255),
    USED_CAPACITY   VARCHAR(255),
    BARE_CAPACITY   VARCHAR(255),
    TABLE_COUNT     VARCHAR(255),
    LIVE_TABLE_ROTE VARCHAR(255),
    MODIFY_TIME     DATE,
    TABLE_SUM		  VARCHAR(10)
);
comment on table DP_DB_STATE
    is '数据库概况';
comment on column DP_DB_STATE.NAME
    is '名字';
comment on column DP_DB_STATE.USED_CAPACITY
    is '已使用物理存储(GB)';
comment on column DP_DB_STATE.BARE_CAPACITY
    is '总物理存储(GB)';
comment on column DP_DB_STATE.TABLE_COUNT
    is '记录数';
comment on column DP_DB_STATE.LIVE_TABLE_ROTE
    is '活表率';
comment on column DP_DB_STATE.MODIFY_TIME
    is '修改时间';
comment on column DP_DB_STATE.TABLE_SUM
    is '表数量';

-----------------------------------------------------------------------------------------------------
-- Create table 首页数据库状况展示数据
create table DP_T_ADS_ODPS
(
    ID            VARCHAR(128) not null,
    NAME          VARCHAR(128),
    USED_CAPACITY NUMERIC,
    BARE_CAPACITY NUMERIC,
    DT            DATE,
    UT            DATE,
    RATE          NUMERIC(2),
    FILE_COUNT    VARCHAR(255)
);
-- Add comments to the table
comment on table DP_T_ADS_ODPS
    is '首页数据库状况展示数据';
-- Add comments to the columns
comment on column DP_T_ADS_ODPS.ID
    is 'ID';
comment on column DP_T_ADS_ODPS.NAME
    is '4种名字：1.ADS,2.ODPS,3.OSS,4.TRS';
comment on column DP_T_ADS_ODPS.USED_CAPACITY
    is '已使用容量';
comment on column DP_T_ADS_ODPS.BARE_CAPACITY
    is '裸容量';
comment on column DP_T_ADS_ODPS.DT
    is '入库时间';
comment on column DP_T_ADS_ODPS.UT
    is '更新时间';
comment on column DP_T_ADS_ODPS.RATE
    is '已使用容量的百分比值，例如20%记录值为20';
comment on column DP_T_ADS_ODPS.FILE_COUNT
    is '文件数，主要针对oss';


-----------------------------------------------------------------------------------------------------
-- 审批表
create table DP_APPROVAL_INFO
(
    APPROVAL_ID      VARCHAR(32),
    MODULE_NAME      VARCHAR(200),
    OPERATION_NAME   VARCHAR(200),
    APPLICATION_INFO TEXT,
    CALLBACK_DATA    TEXT,
    CALLBACK_URL     VARCHAR(200),
    CREATE_TIME      DATE,
    STATUS           VARCHAR(2),
    MODULE_ID        VARCHAR(50),
    OPERATOR_ID      VARCHAR(50),
    TASK_ID          VARCHAR(50),
    VIEW_URL         VARCHAR(200)
);
comment on table DP_APPROVAL_INFO
    is '申请审批表';
comment on column DP_APPROVAL_INFO.APPROVAL_ID
    is 'UUID唯一标识';
comment on column DP_APPROVAL_INFO.MODULE_NAME
    is '业务模块';
comment on column DP_APPROVAL_INFO.OPERATION_NAME
    is '申请操作';
comment on column DP_APPROVAL_INFO.APPLICATION_INFO
    is '申请信息';
comment on column DP_APPROVAL_INFO.CALLBACK_DATA
    is '回传业务系统数据';
comment on column DP_APPROVAL_INFO.CALLBACK_URL
    is '回调业务系统地址';
comment on column DP_APPROVAL_INFO.CREATE_TIME
    is '创建时间';
comment on column DP_APPROVAL_INFO.STATUS
    is '状态(0:初始化;1:审批中;2:退回;结束;4:手动终止)';
comment on column DP_APPROVAL_INFO.MODULE_ID
    is '业务模块标识(dataDefinition:数据定义;createTable:建表;standardTable:新建标准表;register:注册)';
comment on column DP_APPROVAL_INFO.OPERATOR_ID
    is '申请人id';
comment on column DP_APPROVAL_INFO.TASK_ID
    is '事务id';
comment on column DP_APPROVAL_INFO.VIEW_URL
    is '申请详情查看地址';

alter table DP_APPROVAL_INFO
    add column PROCESSINSTANCEID VARCHAR(64),
    add column EXECUTE_RESULT TEXT;
comment on column DP_APPROVAL_INFO.PROCESSINSTANCEID is '流程实例ID';
comment on column DP_APPROVAL_INFO.EXECUTE_RESULT is '回调执行情况';

-----------------------------------------------------------------------------------------------------
-- 表组织字段全局显示配置表
create table DP_ZC_CONFIG_FIELD_CONTROL
(
    NAME            VARCHAR(255),
    OVERTIMEDAYS    NUMERIC,
    SHOW_FIELD_LIST VARCHAR(2000),
    USERNAME        VARCHAR(100)
);
-- Add comments to the table
comment on table DP_ZC_CONFIG_FIELD_CONTROL
    is '表组织字段全局显示配置';
-- Add comments to the columns
comment on column DP_ZC_CONFIG_FIELD_CONTROL.NAME
    is '页面名';
comment on column DP_ZC_CONFIG_FIELD_CONTROL.OVERTIMEDAYS
    is '数据最大保留天数';
comment on column DP_ZC_CONFIG_FIELD_CONTROL.SHOW_FIELD_LIST
    is '显示字段列表';
comment on column DP_ZC_CONFIG_FIELD_CONTROL.USERNAME
    is '所属用户';

-----------------------------------------------------------------------------------------------------
-- 表使用次数中使用
create table DP_UT_ODPSSQLCOUNT
(
    PROJECT         VARCHAR(128),
    TABLENAME       VARCHAR(256),
    TABLE_USE_COUNT NUMERIC(10),
    SQLTYPE         NUMERIC(2),
    DT              VARCHAR(36),
    ID              VARCHAR(36)
);
-- Add comments to the columns
comment on column DP_UT_ODPSSQLCOUNT.PROJECT
    is '项目名';
comment on column DP_UT_ODPSSQLCOUNT.TABLENAME
    is '表名';
comment on column DP_UT_ODPSSQLCOUNT.TABLE_USE_COUNT
    is '某一个类型的使用次数';
comment on column DP_UT_ODPSSQLCOUNT.SQLTYPE
    is '值包含:
1.SELECT
2.INSERT
3.COUNT
';
comment on column DP_UT_ODPSSQLCOUNT.DT
    is '该条数据的插入时间，时间格式yyyy-MM-dd';
comment on column DP_UT_ODPSSQLCOUNT.ID
    is '外键，关联DP_DBOPERATE_MONITOR的主键';

-----------------------------------------------------------------------------------------------------
create table DP_DBOPERATE_MONITOR
(
    ID              VARCHAR(4000),
    DB_TYPE         VARCHAR(4000),
    PROJECT_NAME    VARCHAR(4000),
    SQL_TYPE        NUMERIC(10),
    EXECUTE_STATE   VARCHAR(4000),
    START_TIME      VARCHAR(4000),
    END_TIME        VARCHAR(4000),
    EXECUTE_TIME    VARCHAR(4000),
    INST_OWNER_NAME VARCHAR(4000),
    MONITOR_TIME    VARCHAR(4000),
    SQL             TEXT
);
comment on table DP_DBOPERATE_MONITOR
    is '数据库操作监控（数据取自阿里m_task）';
comment on column DP_DBOPERATE_MONITOR.ID
    is '主键';
comment on column DP_DBOPERATE_MONITOR.DB_TYPE
    is '数据库类型';
comment on column DP_DBOPERATE_MONITOR.PROJECT_NAME
    is '项目名';
comment on column DP_DBOPERATE_MONITOR.SQL_TYPE
    is 'SQL类型（0:未解析出来  1:select查询   2:insert插入   3:count计数   4:delete删出   5:update修改   6:create建表   7:drop删表 ）';
comment on column DP_DBOPERATE_MONITOR.EXECUTE_STATE
    is '执行状态(4:Failed,
5:Terminated(成功)
)';
comment on column DP_DBOPERATE_MONITOR.START_TIME
    is '开始时间（XX时XX分XX秒）';
comment on column DP_DBOPERATE_MONITOR.END_TIME
    is '结束时间';
comment on column DP_DBOPERATE_MONITOR.EXECUTE_TIME
    is '执行时长';
comment on column DP_DBOPERATE_MONITOR.INST_OWNER_NAME
    is '账户名';
comment on column DP_DBOPERATE_MONITOR.MONITOR_TIME
    is '监控日期（年-月-日）';

-----------------------------------------------------------------------------------------------------
-- Create table 存储需要监控的重点组织信息
create table DP_FOCUS_CLASS_MONITOR
(
    PRIMARY_NAME   VARCHAR(200),
    SECONDARY_NAME VARCHAR(200),
    INSERT_TIME    DATE,
    DEL_FLAG       NUMERIC default 1,
    MAJOR_CLASS    VARCHAR(200),
    USER_ID        VARCHAR(100),
    USER_AUTHORITY_ID VARCHAR(100),
    USER_NAME      VARCHAR(100),
    IS_ADMIN       VARCHAR(50),
    LAST_LEVEL_CLASSIFYCODE VARCHAR(100)
);
-- Add comments to the table
comment on table DP_FOCUS_CLASS_MONITOR
    is '存储需要监控的重点组织信息';
-- Add comments to the columns
comment on column DP_FOCUS_CLASS_MONITOR.PRIMARY_NAME
    is '一级名称';
comment on column DP_FOCUS_CLASS_MONITOR.SECONDARY_NAME
    is '二级名称';
comment on column DP_FOCUS_CLASS_MONITOR.INSERT_TIME
    is '该条记录插入的时间';
comment on column DP_FOCUS_CLASS_MONITOR.DEL_FLAG
    is '是否被删除 0：被删除 1：没有被删除';
comment on column DP_FOCUS_CLASS_MONITOR.MAJOR_CLASS
    is '大类名称  数据组织/数据资源来源/数据资源分类major';
comment on column DP_FOCUS_CLASS_MONITOR.USER_ID
    is '用户id';
comment on column DP_FOCUS_CLASS_MONITOR.USER_AUTHORITY_ID
    is '权限表用户id';
comment on column DP_FOCUS_CLASS_MONITOR.USER_NAME
    is '用户名';
comment on column DP_FOCUS_CLASS_MONITOR.IS_ADMIN
    is '是否管理员';
comment on column DP_FOCUS_CLASS_MONITOR.LAST_LEVEL_CLASSIFYCODE
    is '最后一级分类代码';

-----------------------------------------------------------------------------------------------------
-- Create table 价值密度表
create table DP_VAL_DENSITY
(
    TABLE_NAME_EN             VARCHAR(255),
    TABLE_TYPE                VARCHAR(255),
    TABLE_PROJECT             VARCHAR(255),
    UNSTRUCTED_DATA           NUMERIC,
    TWOHANDLE                 NUMERIC,
    TEXTHANDLE                NUMERIC,
    WORKFLOW_USED             NUMERIC,
    APPLICATION_USED          NUMERIC,
    ZHUTIKU_USED              NUMERIC,
    ZIYUANKU_USED             NUMERIC,
    YAOSUKU_USED              NUMERIC,
    TAG_USED                  NUMERIC,
    VAL_DENSITY               VARCHAR(255),
    UPDATE_VAL_DENSITY_STATUS NUMERIC
);
-- Add comments to the table
comment on table DP_VAL_DENSITY
    is '价值密度表';
-- Add comments to the columns
comment on column DP_VAL_DENSITY.TABLE_NAME_EN
    is '英文表名';
comment on column DP_VAL_DENSITY.TABLE_TYPE
    is '平台类型';
comment on column DP_VAL_DENSITY.TABLE_PROJECT
    is '项目名';
comment on column DP_VAL_DENSITY.UNSTRUCTED_DATA
    is '是否非结构化数据';
comment on column DP_VAL_DENSITY.TWOHANDLE
    is '是否二次解析';
comment on column DP_VAL_DENSITY.TEXTHANDLE
    is '是否可进行文本提取';
comment on column DP_VAL_DENSITY.WORKFLOW_USED
    is '被调用工作流';
comment on column DP_VAL_DENSITY.APPLICATION_USED
    is '被调用应用系统';
comment on column DP_VAL_DENSITY.ZHUTIKU_USED
    is '提取主题库';
comment on column DP_VAL_DENSITY.ZIYUANKU_USED
    is '提取资源库';
comment on column DP_VAL_DENSITY.YAOSUKU_USED
    is '提取要素库';
comment on column DP_VAL_DENSITY.TAG_USED
    is '提取标签';
comment on column DP_VAL_DENSITY.VAL_DENSITY
    is '价值密度';
comment on column DP_VAL_DENSITY.UPDATE_VAL_DENSITY_STATUS
    is '更新价值密度状态';

-----------------------------------------------------------------------------------------------------
create table DP_OPERATE_LOG
(
    ID        VARCHAR(256),
    LOGNAME   VARCHAR(256),
    LOGTIME   DATE,
    DATACOUNT NUMERIC
);
comment on column DP_OPERATE_LOG.ID
    is '主键';
comment on column DP_OPERATE_LOG.LOGNAME
    is '操作名称';
comment on column DP_OPERATE_LOG.LOGTIME
    is '操作时间';
comment on column DP_OPERATE_LOG.DATACOUNT
    is '数据量';

-----------------------------------------------------------------------------------------------------
-- CREATE OR REPLACE VIEW synlte.V_DS_DATA_RESOURCE AS
-- SELECT  ds.RES_ID, ds.RES_NAME, ds.RES_TYPE, ds.STATUS, ds.CONNECT_INFO,
--     dc.CENTER_NAME, ds.VERSION, ds.IS_LOCAL, ds.REMARK,  ds.APPROVED, ds.GMT_CREATE, ds.GMT_MODIFIED
-- FROM SYNDG.DS_DATA_RESOURCE ds LEFT JOIN SYNDG.ds_data_center dc
-- ON ds.CENTER_ID = dc.CENTER_ID;

