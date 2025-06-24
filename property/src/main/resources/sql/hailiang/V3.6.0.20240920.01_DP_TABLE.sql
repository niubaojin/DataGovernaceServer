-- Create table 表组织资产
create table TABLE_ORGANIZATION_ASSETS
(
    SJXJBM                     VARCHAR2(50),
    NAME                       VARCHAR2(600),
    TABLE_NAME_EN              VARCHAR2(600),
    PRIMARY_DATASOURCE_CH      VARCHAR2(150),
    SECONDARY_DATASOURCE_CH    VARCHAR2(150),
    PRIMARY_ORGANIZATION_CH    VARCHAR2(150),
    SECONDARY_ORGANIZATION_CH  VARCHAR2(150),
    FACTOR_ONE_NAME            VARCHAR2(150),
    FACTOR_TWO_NAME            VARCHAR2(150),
    FACTOR_DETAILED_NAME       VARCHAR2(150),
    FACTOR_ATTRIBUTES_NAME     VARCHAR2(150),
    TABLE_ALL_COUNT            NUMBER,
    TABLE_SIZE                 NUMBER,
    TABLE_PROJECT              VARCHAR2(150),
    LIFE_CYCLE                 VARCHAR2(150),
    TABLE_TYPE                 VARCHAR2(150),
    YESTERDAY_COUNT            NUMBER,
    PARTITION_MESSAGE          VARCHAR2(150),
    AVERAGE_DAILY_VOLUME       NUMBER,
    VALUE_RATE                 NUMBER,
    NULL_RATE                  NUMBER,
    TABLE_STATE                VARCHAR2(150),
    UPDATE_TYPE                VARCHAR2(150),
    STATISTICS_TIME            DATE,
    FREQUENCY                  VARCHAR2(650),
    LIVETYPE                   VARCHAR2(650),
    REGISTER_STATE             VARCHAR2(255) default -1,
    OBJECT_ID                  VARCHAR2(100),
    AVERAGE_DAILY_SIZE         NUMBER,
    OBJECT_STATE               VARCHAR2(50),
    THREE_ORGANIZATION_CH      VARCHAR2(150),
    LABELS                     VARCHAR2(500),
    UPDATE_PERIOD              VARCHAR2(100),
    UPDATE_DATE                VARCHAR2(100),
    DATARESOURCE_CODE          VARCHAR2(100),
    EXCEPTION_MESSAGE          VARCHAR2(100),
    LIFE_CYCLE_STATUS          VARCHAR2(50),
    LASTDATAMODIFIEDTIME       VARCHAR2(100),
    TABLECREATEDTIME           VARCHAR2(100),
    PARTITION_NUM              VARCHAR2(255),
    LASTVISITEDTIME            VARCHAR2(255),
    TABLEUSECOUNTOFMONTH       VARCHAR2(255),
    RESOURCEID                 VARCHAR2(255),
    THREELEVEL_ORGANIZATION_CH VARCHAR2(200),
    ORGANIZATION_ID_LAST_LEVEL VARCHAR2(150),
    DATASOURCE_LAST_LEVEL      VARCHAR2(150),
    RES_NAME                   varchar2(200),
    ISSTANDARD                 varchar2(100),
    ALARM_LEVEL                varchar2(100),
	SJZYBQ5                    VARCHAR2(50)
);
-- Add comments to the table
comment on table TABLE_ORGANIZATION_ASSETS
    is '表组织资产的相关数据';
-- Add comments to the columns
comment on column TABLE_ORGANIZATION_ASSETS.SJXJBM
    is '表协议名';
comment on column TABLE_ORGANIZATION_ASSETS.NAME
    is '表中文名';
comment on column TABLE_ORGANIZATION_ASSETS.TABLE_NAME_EN
    is '表英文名';
comment on column TABLE_ORGANIZATION_ASSETS.PRIMARY_DATASOURCE_CH
    is '数据来源一级分类中文名';
comment on column TABLE_ORGANIZATION_ASSETS.SECONDARY_DATASOURCE_CH
    is '数据来源二级分类中文名';
comment on column TABLE_ORGANIZATION_ASSETS.PRIMARY_ORGANIZATION_CH
    is '数据组织一级分类中文名';
comment on column TABLE_ORGANIZATION_ASSETS.SECONDARY_ORGANIZATION_CH
    is '数据组织二级分类中文名';
comment on column TABLE_ORGANIZATION_ASSETS.FACTOR_ONE_NAME
    is '数据资源要素一级分类中文名';
comment on column TABLE_ORGANIZATION_ASSETS.FACTOR_TWO_NAME
    is '数据资源要素二级分类中文名';
comment on column TABLE_ORGANIZATION_ASSETS.FACTOR_DETAILED_NAME
    is '数据资源要素细目分类代码名称';
comment on column TABLE_ORGANIZATION_ASSETS.FACTOR_ATTRIBUTES_NAME
    is '数据资源属性分类代码名称';
comment on column TABLE_ORGANIZATION_ASSETS.TABLE_ALL_COUNT
    is '表数据总行数 到昨天分区';
comment on column TABLE_ORGANIZATION_ASSETS.TABLE_SIZE
    is '表数据总大小 到昨天分区 byte';
comment on column TABLE_ORGANIZATION_ASSETS.TABLE_PROJECT
    is '表的项目名';
comment on column TABLE_ORGANIZATION_ASSETS.LIFE_CYCLE
    is '表的生命周期';
comment on column TABLE_ORGANIZATION_ASSETS.TABLE_TYPE
    is '表类型 odps/ads/''''';
comment on column TABLE_ORGANIZATION_ASSETS.YESTERDAY_COUNT
    is '昨天分区的数据量';
comment on column TABLE_ORGANIZATION_ASSETS.PARTITION_MESSAGE
    is '是否为分区表  0:分区表  1:不是分区表  -1：不确定是否为分区表';
comment on column TABLE_ORGANIZATION_ASSETS.AVERAGE_DAILY_VOLUME
    is '日均数据量';
comment on column TABLE_ORGANIZATION_ASSETS.VALUE_RATE
    is '有值率';
comment on column TABLE_ORGANIZATION_ASSETS.NULL_RATE
    is '空值率';
comment on column TABLE_ORGANIZATION_ASSETS.TABLE_STATE
    is '状态  正常/异常';
comment on column TABLE_ORGANIZATION_ASSETS.UPDATE_TYPE
    is '全量/增量/';
comment on column TABLE_ORGANIZATION_ASSETS.STATISTICS_TIME
    is '统计时间';
comment on column TABLE_ORGANIZATION_ASSETS.FREQUENCY
    is '最近一个月使用次数/总使用次数';
comment on column TABLE_ORGANIZATION_ASSETS.LIVETYPE
    is 'NONLIVING：死表 LIVING：活表';
comment on column TABLE_ORGANIZATION_ASSETS.REGISTER_STATE
    is '注册状态 -1:未注册 1:已注册';
comment on column TABLE_ORGANIZATION_ASSETS.OBJECT_ID
    is 'object表中的objectid';
comment on column TABLE_ORGANIZATION_ASSETS.AVERAGE_DAILY_SIZE
    is '日均数据大小';
comment on column TABLE_ORGANIZATION_ASSETS.OBJECT_STATE
    is '停用，启用状态，1为启用';
comment on column TABLE_ORGANIZATION_ASSETS.THREE_ORGANIZATION_CH
    is '数据组织三级分类中文名';
comment on column TABLE_ORGANIZATION_ASSETS.LABELS
    is '数据资源标签';
comment on column TABLE_ORGANIZATION_ASSETS.UPDATE_PERIOD
    is '更新批次';
comment on column TABLE_ORGANIZATION_ASSETS.UPDATE_DATE
    is '更新周期';
comment on column TABLE_ORGANIZATION_ASSETS.DATARESOURCE_CODE
    is '资源服务平台组织机构代码';
comment on column TABLE_ORGANIZATION_ASSETS.EXCEPTION_MESSAGE
    is '异常信息';
comment on column TABLE_ORGANIZATION_ASSETS.LIFE_CYCLE_STATUS
    is '生命周期审批状态';
comment on column TABLE_ORGANIZATION_ASSETS.LASTDATAMODIFIEDTIME
    is '最后修改时间';
comment on column TABLE_ORGANIZATION_ASSETS.TABLECREATEDTIME
    is '表创建时间';
comment on column TABLE_ORGANIZATION_ASSETS.PARTITION_NUM
    is '分区数量';
comment on column TABLE_ORGANIZATION_ASSETS.LASTVISITEDTIME
    is '最后访问时间';
comment on column TABLE_ORGANIZATION_ASSETS.TABLEUSECOUNTOFMONTH
    is '月使用量';
comment on column TABLE_ORGANIZATION_ASSETS.RESOURCEID
    is '数据资源id，用于管理权限表';
comment on column TABLE_ORGANIZATION_ASSETS.THREELEVEL_ORGANIZATION_CH
    is '数据组织三级分类中文名';
comment on column TABLE_ORGANIZATION_ASSETS.ORGANIZATION_ID_LAST_LEVEL
    is '数据组织分类id(最后一级)';
comment on column TABLE_ORGANIZATION_ASSETS.DATASOURCE_LAST_LEVEL
    is '数据来源分类id(最后一级)';
comment on column TABLE_ORGANIZATION_ASSETS.RES_NAME
    is '数据源名称';
comment on column TABLE_ORGANIZATION_ASSETS.ISSTANDARD
    is '是否对标';
comment on column TABLE_ORGANIZATION_ASSETS.ALARM_LEVEL
    is '告警级别';
comment on column TABLE_ORGANIZATION_ASSETS.SJZYBQ5
    is '数据资源标签5';
-- Create/Recreate indexes
create index DATE1_INDEX1 on TABLE_ORGANIZATION_ASSETS (STATISTICS_TIME);
--------------------------------------------------------------------------------

create table DATA_STORAGE_ADD_TABLE
(
    DATABASE_TYPE    VARCHAR2(20),
    PROJECT_NAME     VARCHAR2(100),
    TABLENAME_EN     VARCHAR2(600),
    TABLENAME_CH     VARCHAR2(600),
    TABLE_ID         VARCHAR2(600) not null,
    PARTITIONED      VARCHAR2(10),
    DATA_SOURCE_NAME VARCHAR2(100),
    DATA_SOURCE_ID   VARCHAR2(100),
    DATA_CENTER_NAME VARCHAR2(100),
    DATA_CENTER_ID   VARCHAR2(100),
    ISSTANDARD       VARCHAR2(50),
    ISADDMONITOR     VARCHAR2(50),
    ISDELETE         VARCHAR2(50),
    PARTITION_COLUMN VARCHAR2(50)
);
comment on table DATA_STORAGE_ADD_TABLE
    is '需要监控的ADS表信息';
-- Add comments to the columns
comment on column DATA_STORAGE_ADD_TABLE.DATABASE_TYPE
    is '数据库类型ADS';
comment on column DATA_STORAGE_ADD_TABLE.PROJECT_NAME
    is '项目名称（英文）';
comment on column DATA_STORAGE_ADD_TABLE.TABLENAME_EN
    is '表英文名';
comment on column DATA_STORAGE_ADD_TABLE.TABLENAME_CH
    is '表中文名';
comment on column DATA_STORAGE_ADD_TABLE.TABLE_ID
    is '记录的唯一ID';
comment on column DATA_STORAGE_ADD_TABLE.PARTITIONED
    is '是否为分区表 1:true  0:false';
comment on column DATA_STORAGE_ADD_TABLE.DATA_SOURCE_NAME
    is '数据源名称';
comment on column DATA_STORAGE_ADD_TABLE.DATA_SOURCE_ID
    is '数据源id';
comment on column DATA_STORAGE_ADD_TABLE.DATA_CENTER_NAME
    is '数据中心名称';
comment on column DATA_STORAGE_ADD_TABLE.DATA_CENTER_ID
    is '数据中心id';
comment on column DATA_STORAGE_ADD_TABLE.ISSTANDARD
    is '是否标准 1:是 0:否';
comment on column DATA_STORAGE_ADD_TABLE.ISADDMONITOR
    is '是否监控 （已控，待控）';
comment on column DATA_STORAGE_ADD_TABLE.ISDELETE
    is '是否删除 1:已删除 0:未删除';
comment on column DATA_STORAGE_ADD_TABLE.PARTITION_COLUMN
    is '分区字段';

--------------------------------------------------------------------------------------------------------
create table DATA_STORAGE_TABLE_FULL
(
    TABLE_NAME_EN   VARCHAR2(600) not null,
    TABLE_NAME_CH   VARCHAR2(600),
    TABLE_TODAY_SUM NUMBER,
    TABLE_ALL_SUM   NUMBER,
    STATISTIC_DAY   VARCHAR2(20),
    TABLE_PROJECT   VARCHAR2(40),
    STATISTIC_ID    VARCHAR2(60) not null,
    DATABASE_TYPE   VARCHAR2(60)
);
comment on table DATA_STORAGE_TABLE_FULL
    is '监控的ADS表的详细数据信息';
-- Add comments to the columns
comment on column DATA_STORAGE_TABLE_FULL.TABLE_NAME_EN
    is '表英文名';
comment on column DATA_STORAGE_TABLE_FULL.TABLE_NAME_CH
    is '表中文名';
comment on column DATA_STORAGE_TABLE_FULL.TABLE_TODAY_SUM
    is '表当日的数据量';
comment on column DATA_STORAGE_TABLE_FULL.TABLE_ALL_SUM
    is '表当日表数据总量';
comment on column DATA_STORAGE_TABLE_FULL.STATISTIC_DAY
    is '表统计日期yyyyMMddHH24miss';
comment on column DATA_STORAGE_TABLE_FULL.TABLE_PROJECT
    is '表项目名';
comment on column DATA_STORAGE_TABLE_FULL.STATISTIC_ID
    is '唯一性ID';
comment on column DATA_STORAGE_TABLE_FULL.DATABASE_TYPE
    is '数据库类型odps/ads';

-----------------------------------------------------------------------------------------------------
create table DATA_REGISTER
(
    SJXJBM               VARCHAR2(100),
    TABLE_NAME_EN        VARCHAR2(100),
    OBJECT_ID            VARCHAR2(100),
    APPROVAL_ID          VARCHAR2(100),
    APPROVAL_STATUS      VARCHAR2(10),
    APPROVAL_CREATE_TIME VARCHAR2(100),
    APPROVAL_UPDATE_TIME VARCHAR2(100),
    APPROVAL_TYPE        VARCHAR2(50),
    TABLE_PROJECT        VARCHAR2(50)
);
-- Add comments to the columns
comment on column DATA_REGISTER.SJXJBM
    is '协议编号';
comment on column DATA_REGISTER.TABLE_NAME_EN
    is '表名';
comment on column DATA_REGISTER.OBJECT_ID
    is '标准表id';
comment on column DATA_REGISTER.APPROVAL_ID
    is '审批id';
comment on column DATA_REGISTER.APPROVAL_STATUS
    is '审批状态(1为审批成功，2为审批中，4为审批失败)';
comment on column DATA_REGISTER.APPROVAL_CREATE_TIME
    is '审批创建时间';
comment on column DATA_REGISTER.APPROVAL_UPDATE_TIME
    is '审批更新时间';
comment on column DATA_REGISTER.APPROVAL_TYPE
    is '审批类型';
comment on column DATA_REGISTER.TABLE_PROJECT
    is '库名';

-----------------------------------------------------------------------------------------------------
create table STORECYCLEANDUSEHEAT
(
    ID                   VARCHAR2(36),
    LASTDATE             VARCHAR2(32),
    TABLETYPE            VARCHAR2(2),
    CLASSNAME            VARCHAR2(32),
    SUBCLASS             VARCHAR2(1000),
    TABLENAMEEN          VARCHAR2(1000),
    TABLENAMEZH          VARCHAR2(1000),
    LIFECYCLE            NUMBER(8),
    TABLEALLCOUNT        NUMBER(36),
    TABLESIZE            NUMBER(36),
    TABLEUSECOUNT        NUMBER(36),
    TABLEUSECOUNTOFDAY   NUMBER(36),
    TABLEUSECOUNTOFWEEK  NUMBER(36),
    TABLEUSECOUNTOFMONTH NUMBER(36)
);

-----------------------------------------------------------------------------------------------------
-- 存储数据历程的相关信息
create table DATAPROCESS
(
    AREA_ID        VARCHAR2(100),
    DEPT           VARCHAR2(100),
    OPERATOR       VARCHAR2(100),
    POLICENO       VARCHAR2(100),
    APP_ID         VARCHAR2(100),
    MODULE_ID      VARCHAR2(100),
    OPERATE_TIME   VARCHAR2(100) not null,
    IP             VARCHAR2(100),
    LOG_TYPE       VARCHAR2(100),
    DIGEST         VARCHAR2(1000),
    DATA_BASE_TYPE VARCHAR2(100),
    TABLE_NAME_EN  VARCHAR2(100),
    TABLE_ID       VARCHAR2(100),
    INSERT_TIME    DATE default sysdate not null,
    TASK_NAME      VARCHAR2(200),
    ID             NUMBER not null
);
comment on table DATAPROCESS
    is '存储数据历程的相关信息';
comment on column DATAPROCESS.AREA_ID
    is '地区行政编号（参考标准 附录一）';
comment on column DATAPROCESS.DEPT
    is '部门名称';
comment on column DATAPROCESS.OPERATOR
    is '操作人名称';
comment on column DATAPROCESS.POLICENO
    is '警号';
comment on column DATAPROCESS.APP_ID
    is '系统代码';
comment on column DATAPROCESS.MODULE_ID
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
comment on column DATAPROCESS.OPERATE_TIME
    is '操作时间   YYYY-MM-DD HH:mm:ss';
comment on column DATAPROCESS.IP
    is 'IP地址/操作设备地址';
comment on column DATAPROCESS.LOG_TYPE
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
comment on column DATAPROCESS.DIGEST
    is '日志摘要   操作行为的简要说明　不同模块对应的格式不同';
comment on column DATAPROCESS.DATA_BASE_TYPE
    is '数据库类型 odps/ads/hive/hbase';
comment on column DATAPROCESS.TABLE_NAME_EN
    is '包括  项目名.表名';
comment on column DATAPROCESS.TABLE_ID
    is '表协议ID';
comment on column DATAPROCESS.INSERT_TIME
    is '该条记录的插入时间';
comment on column DATAPROCESS.TASK_NAME
    is '任务名 只有数据接入，质量检测才有这个';
comment on column DATAPROCESS.ID
    is '唯一主键';

-----------------------------------------------------------------------------------------------------
--数据库概况
create table TABLE_ORGANIZATION_DB_STATE
(
    NAME            VARCHAR2(255),
    USED_CAPACITY   VARCHAR2(255),
    BARE_CAPACITY   VARCHAR2(255),
    TABLE_COUNT     VARCHAR2(255),
    LIVE_TABLE_ROTE VARCHAR2(255),
    MODIFY_TIME     DATE,
    TABLE_SUM		  VARCHAR2(10)
);
comment on table TABLE_ORGANIZATION_DB_STATE
    is '数据库概况';
comment on column TABLE_ORGANIZATION_DB_STATE.NAME
    is '名字';
comment on column TABLE_ORGANIZATION_DB_STATE.USED_CAPACITY
    is '已使用物理存储(GB)';
comment on column TABLE_ORGANIZATION_DB_STATE.BARE_CAPACITY
    is '总物理存储(GB)';
comment on column TABLE_ORGANIZATION_DB_STATE.TABLE_COUNT
    is '记录数';
comment on column TABLE_ORGANIZATION_DB_STATE.LIVE_TABLE_ROTE
    is '活表率';
comment on column TABLE_ORGANIZATION_DB_STATE.MODIFY_TIME
    is '修改时间';
comment on column TABLE_ORGANIZATION_DB_STATE.TABLE_SUM
    is '表数量';

-----------------------------------------------------------------------------------------------------
-- Create table 首页数据库状况展示数据
create table T_ADS_ODPS
(
    ID            VARCHAR2(128) not null,
    NAME          VARCHAR2(128),
    USED_CAPACITY NUMBER,
    BARE_CAPACITY NUMBER,
    DT            DATE,
    UT            DATE,
    RATE          NUMBER(2),
    FILE_COUNT    VARCHAR2(255)
);
-- Add comments to the table
comment on table T_ADS_ODPS
    is '首页数据库状况展示数据';
-- Add comments to the columns
comment on column T_ADS_ODPS.ID
    is 'ID';
comment on column T_ADS_ODPS.NAME
    is '4种名字：1.ADS,2.ODPS,3.OSS,4.TRS';
comment on column T_ADS_ODPS.USED_CAPACITY
    is '已使用容量';
comment on column T_ADS_ODPS.BARE_CAPACITY
    is '裸容量';
comment on column T_ADS_ODPS.DT
    is '入库时间';
comment on column T_ADS_ODPS.UT
    is '更新时间';
comment on column T_ADS_ODPS.RATE
    is '已使用容量的百分比值，例如20%记录值为20';
comment on column T_ADS_ODPS.FILE_COUNT
    is '文件数，主要针对oss';


-----------------------------------------------------------------------------------------------------
-- 审批表
create table APPROVAL_INFO
(
    APPROVAL_ID      VARCHAR2(32),
    MODULE_NAME      VARCHAR2(200),
    OPERATION_NAME   VARCHAR2(200),
    APPLICATION_INFO CLOB,
    CALLBACK_DATA    CLOB,
    CALLBACK_URL     VARCHAR2(200),
    CREATE_TIME      DATE,
    STATUS           VARCHAR2(2),
    MODULE_ID        VARCHAR2(50),
    OPERATOR_ID      VARCHAR2(50),
    TASK_ID          VARCHAR2(50),
    VIEW_URL         NVARCHAR2(200)
);
comment on table APPROVAL_INFO
    is '申请审批表';
comment on column APPROVAL_INFO.APPROVAL_ID
    is 'UUID唯一标识';
comment on column APPROVAL_INFO.MODULE_NAME
    is '业务模块';
comment on column APPROVAL_INFO.OPERATION_NAME
    is '申请操作';
comment on column APPROVAL_INFO.APPLICATION_INFO
    is '申请信息';
comment on column APPROVAL_INFO.CALLBACK_DATA
    is '回传业务系统数据';
comment on column APPROVAL_INFO.CALLBACK_URL
    is '回调业务系统地址';
comment on column APPROVAL_INFO.CREATE_TIME
    is '创建时间';
comment on column APPROVAL_INFO.STATUS
    is '状态(0:初始化;1:审批中;2:退回;结束;4:手动终止)';
comment on column APPROVAL_INFO.MODULE_ID
    is '业务模块标识(dataDefinition:数据定义;createTable:建表;standardTable:新建标准表;register:注册)';
comment on column APPROVAL_INFO.OPERATOR_ID
    is '申请人id';
comment on column APPROVAL_INFO.TASK_ID
    is '事务id';
comment on column APPROVAL_INFO.VIEW_URL
    is '申请详情查看地址';

alter table APPROVAL_INFO add (PROCESSINSTANCEID VARCHAR2(64),EXECUTE_RESULT CLOB);
comment on column APPROVAL_INFO.PROCESSINSTANCEID is '流程实例ID';
comment on column APPROVAL_INFO.EXECUTE_RESULT is '回调执行情况';

-----------------------------------------------------------------------------------------------------
-- 表组织字段全局显示配置表
create table ZC_CONFIG_FIELD_CONTROL
(
    NAME            VARCHAR2(255),
    OVERTIMEDAYS    NUMBER,
    SHOW_FIELD_LIST VARCHAR2(2000),
    USERNAME        varchar2(100)
);
-- Add comments to the table
comment on table ZC_CONFIG_FIELD_CONTROL
    is '表组织字段全局显示配置';
-- Add comments to the columns
comment on column ZC_CONFIG_FIELD_CONTROL.NAME
    is '页面名';
comment on column ZC_CONFIG_FIELD_CONTROL.OVERTIMEDAYS
    is '数据最大保留天数';
comment on column ZC_CONFIG_FIELD_CONTROL.SHOW_FIELD_LIST
    is '显示字段列表';
comment on column ZC_CONFIG_FIELD_CONTROL.USERNAME
    is '所属用户';

-----------------------------------------------------------------------------------------------------
-- 表使用次数中使用
create table UT_ODPSSQLCOUNT
(
    PROJECT         VARCHAR2(128),
    TABLENAME       VARCHAR2(256),
    TABLE_USE_COUNT NUMBER(10),
    SQLTYPE         NUMBER(2),
    DT              VARCHAR2(36),
    ID              VARCHAR2(36)
);
-- Add comments to the columns
comment on column UT_ODPSSQLCOUNT.PROJECT
    is '项目名';
comment on column UT_ODPSSQLCOUNT.TABLENAME
    is '表名';
comment on column UT_ODPSSQLCOUNT.TABLE_USE_COUNT
    is '某一个类型的使用次数';
comment on column UT_ODPSSQLCOUNT.SQLTYPE
    is '值包含:
1.SELECT
2.INSERT
3.COUNT
';
comment on column UT_ODPSSQLCOUNT.DT
    is '该条数据的插入时间，时间格式yyyy-MM-dd';
comment on column UT_ODPSSQLCOUNT.ID
    is '外键，关联DBOperate_monitor的主键';

-----------------------------------------------------------------------------------------------------
create table DBOPERATE_MONITOR
(
    ID              VARCHAR2(4000),
    DB_TYPE         VARCHAR2(4000),
    PROJECT_NAME    VARCHAR2(4000),
    SQL_TYPE        NUMBER(10),
    EXECUTE_STATE   VARCHAR2(4000),
    START_TIME      VARCHAR2(4000),
    END_TIME        VARCHAR2(4000),
    EXECUTE_TIME    VARCHAR2(4000),
    INST_OWNER_NAME VARCHAR2(4000),
    MONITOR_TIME    VARCHAR2(4000),
    SQL             CLOB
);
comment on table DBOPERATE_MONITOR
    is '数据库操作监控（数据取自阿里m_task）';
comment on column DBOPERATE_MONITOR.ID
    is '主键';
comment on column DBOPERATE_MONITOR.DB_TYPE
    is '数据库类型';
comment on column DBOPERATE_MONITOR.PROJECT_NAME
    is '项目名';
comment on column DBOPERATE_MONITOR.SQL_TYPE
    is 'SQL类型（0:未解析出来  1:select查询   2:insert插入   3:count计数   4:delete删出   5:update修改   6:create建表   7:drop删表 ）';
comment on column DBOPERATE_MONITOR.EXECUTE_STATE
    is '执行状态(4:Failed,
5:Terminated(成功)
)';
comment on column DBOPERATE_MONITOR.START_TIME
    is '开始时间（XX时XX分XX秒）';
comment on column DBOPERATE_MONITOR.END_TIME
    is '结束时间';
comment on column DBOPERATE_MONITOR.EXECUTE_TIME
    is '执行时长';
comment on column DBOPERATE_MONITOR.INST_OWNER_NAME
    is '账户名';
comment on column DBOPERATE_MONITOR.MONITOR_TIME
    is '监控日期（年-月-日）';

-----------------------------------------------------------------------------------------------------
-- Create table 存储需要监控的重点组织信息
create table FOCUS_ORGANIZATION_ADD_MONITOR
(
    PRIMARY_NAME   VARCHAR2(200),
    SECONDARY_NAME VARCHAR2(200),
    INSERT_TIME    DATE,
    DEL_FLAG       NUMBER default 1,
    MAJOR_CLASS    VARCHAR2(200),
    USER_ID        varchar2(100),
    USER_AUTHORITY_ID varchar2(100),
    USER_NAME      varchar2(100),
    IS_ADMIN       varchar2(50),
    LAST_LEVEL_CLASSIFYCODE varchar2(100)
);
-- Add comments to the table
comment on table FOCUS_ORGANIZATION_ADD_MONITOR
    is '存储需要监控的重点组织信息';
-- Add comments to the columns
comment on column FOCUS_ORGANIZATION_ADD_MONITOR.PRIMARY_NAME
    is '一级名称';
comment on column FOCUS_ORGANIZATION_ADD_MONITOR.SECONDARY_NAME
    is '二级名称';
comment on column FOCUS_ORGANIZATION_ADD_MONITOR.INSERT_TIME
    is '该条记录插入的时间';
comment on column FOCUS_ORGANIZATION_ADD_MONITOR.DEL_FLAG
    is '是否被删除 0：被删除 1：没有被删除';
comment on column FOCUS_ORGANIZATION_ADD_MONITOR.MAJOR_CLASS
    is '大类名称  数据组织/数据资源来源/数据资源分类major';
comment on column FOCUS_ORGANIZATION_ADD_MONITOR.USER_ID
    is '用户id';
comment on column FOCUS_ORGANIZATION_ADD_MONITOR.USER_AUTHORITY_ID
    is '权限表用户id';
comment on column FOCUS_ORGANIZATION_ADD_MONITOR.USER_NAME
    is '用户名';
comment on column FOCUS_ORGANIZATION_ADD_MONITOR.IS_ADMIN
    is '是否管理员';
comment on column FOCUS_ORGANIZATION_ADD_MONITOR.LAST_LEVEL_CLASSIFYCODE
    is '最后一级分类代码';

-----------------------------------------------------------------------------------------------------
-- Create table 价值密度表
create table TABLE_ORGANIZATION_VAL_DENSITY
(
    TABLE_NAME_EN             VARCHAR2(255),
    TABLE_TYPE                VARCHAR2(255),
    TABLE_PROJECT             VARCHAR2(255),
    UNSTRUCTED_DATA           NUMBER,
    TWOHANDLE                 NUMBER,
    TEXTHANDLE                NUMBER,
    WORKFLOW_USED             NUMBER,
    APPLICATION_USED          NUMBER,
    ZHUTIKU_USED              NUMBER,
    ZIYUANKU_USED             NUMBER,
    YAOSUKU_USED              NUMBER,
    TAG_USED                  NUMBER,
    VAL_DENSITY               VARCHAR2(255),
    UPDATE_VAL_DENSITY_STATUS NUMBER
);
-- Add comments to the table
comment on table TABLE_ORGANIZATION_VAL_DENSITY
    is '价值密度表';
-- Add comments to the columns
comment on column TABLE_ORGANIZATION_VAL_DENSITY.TABLE_NAME_EN
    is '英文表名';
comment on column TABLE_ORGANIZATION_VAL_DENSITY.TABLE_TYPE
    is '平台类型';
comment on column TABLE_ORGANIZATION_VAL_DENSITY.TABLE_PROJECT
    is '项目名';
comment on column TABLE_ORGANIZATION_VAL_DENSITY.UNSTRUCTED_DATA
    is '是否非结构化数据';
comment on column TABLE_ORGANIZATION_VAL_DENSITY.TWOHANDLE
    is '是否二次解析';
comment on column TABLE_ORGANIZATION_VAL_DENSITY.TEXTHANDLE
    is '是否可进行文本提取';
comment on column TABLE_ORGANIZATION_VAL_DENSITY.WORKFLOW_USED
    is '被调用工作流';
comment on column TABLE_ORGANIZATION_VAL_DENSITY.APPLICATION_USED
    is '被调用应用系统';
comment on column TABLE_ORGANIZATION_VAL_DENSITY.ZHUTIKU_USED
    is '提取主题库';
comment on column TABLE_ORGANIZATION_VAL_DENSITY.ZIYUANKU_USED
    is '提取资源库';
comment on column TABLE_ORGANIZATION_VAL_DENSITY.YAOSUKU_USED
    is '提取要素库';
comment on column TABLE_ORGANIZATION_VAL_DENSITY.TAG_USED
    is '提取标签';
comment on column TABLE_ORGANIZATION_VAL_DENSITY.VAL_DENSITY
    is '价值密度';
comment on column TABLE_ORGANIZATION_VAL_DENSITY.UPDATE_VAL_DENSITY_STATUS
    is '更新价值密度状态';

-----------------------------------------------------------------------------------------------------
create table OPERATE_LOG
(
    ID        VARCHAR2(256),
    LOGNAME   VARCHAR2(256),
    LOGTIME   DATE,
    DATACOUNT NUMBER
);
comment on column OPERATE_LOG.ID
    is '主键';
comment on column OPERATE_LOG.LOGNAME
    is '操作名称';
comment on column OPERATE_LOG.LOGTIME
    is '操作时间';
comment on column OPERATE_LOG.DATACOUNT
    is '数据量';


