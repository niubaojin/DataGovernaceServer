create table DGN_COMMON_SETTING (
    PARENT_ID        VARCHAR(300),
    NAME             VARCHAR(300),
    LOGICAL_JUDGMENT VARCHAR(200),
    THRESHOLD_VALUE  VARCHAR(4000),
    ID               VARCHAR(300) not null,
    TREE_TYPE        VARCHAR(100),
    IS_ACTIVE        VARCHAR(1),
    PAGE_URL         VARCHAR(300)
);

comment on table DGN_COMMON_SETTING is '数据阈值设置的相关数据';
comment on column DGN_COMMON_SETTING.PARENT_ID is '阈值所属的父类ID';
comment on column DGN_COMMON_SETTING.NAME is '名称';
comment on column DGN_COMMON_SETTING.LOGICAL_JUDGMENT is '逻辑判断';
comment on column DGN_COMMON_SETTING.THRESHOLD_VALUE is '阈值';
comment on column DGN_COMMON_SETTING.ID is '主键id';
comment on column DGN_COMMON_SETTING.TREE_TYPE is '该行数据所属的类别，值为1,2,3 数字越小级别越大';
comment on column DGN_COMMON_SETTING.IS_ACTIVE is '是否启用(1:是;0:否)';
comment on column DGN_COMMON_SETTING.PAGE_URL is '配置页面地址';

-- 数据大屏-数据信息
create table DGN_DATA_LARGE_SCREEN
(
    MODULE_ID   VARCHAR(20),
    MODULE_NAME VARCHAR(20),
    DATA_INFO   TEXT,
    CREATE_TIME DATE
);
comment on table DGN_DATA_LARGE_SCREEN is '数据大屏-数据信息';
comment on column DGN_DATA_LARGE_SCREEN.MODULE_ID is '模块id';
comment on column DGN_DATA_LARGE_SCREEN.MODULE_NAME is '模块名称';
comment on column DGN_DATA_LARGE_SCREEN.DATA_INFO is '数据信息json';
comment on column DGN_DATA_LARGE_SCREEN.CREATE_TIME is '创建时间';

-- 导航栏
create table DGN_NAVBAR
(
    NAV_ID          VARCHAR(50),
    NAV_NAME        VARCHAR(100),
    NAV_CLASS       VARCHAR(50),
    NAV_LINK        VARCHAR(200),
    NAV_SHOW        VARCHAR(10),
    NAV_BLANK       VARCHAR(1000),
    NAV_LEVEL       VARCHAR(10),
    NAV_PARENT_NAME VARCHAR(100),
    NAV_ORDER       VARCHAR(10),
    NAV_NAME_EN     VARCHAR(100),
    NAV_IP          VARCHAR(100),
    NAV_REDIRECT    VARCHAR(1000),
    NAV_HIDDEN      NUMERIC default 0
);
-- Add comments to the columns
comment on column DGN_NAVBAR.NAV_ID is 'id，不重复即可';
comment on column DGN_NAVBAR.NAV_NAME is '导航栏名字（需与统一认证管理平台上资源管理配置数据工厂下的资源别名需对应）';
comment on column DGN_NAVBAR.NAV_CLASS is '一级菜单的类型，用于图片';
comment on column DGN_NAVBAR.NAV_LINK is '菜单导向的uri';
comment on column DGN_NAVBAR.NAV_SHOW is '是否展示，0为不展示，1为展示';
comment on column DGN_NAVBAR.NAV_BLANK is '0页面内跳转，1打开新页面';
comment on column DGN_NAVBAR.NAV_LEVEL is '菜单项等级';
comment on column DGN_NAVBAR.NAV_PARENT_NAME is '父级英文名';
comment on column DGN_NAVBAR.NAV_ORDER is '菜单栏排序';
comment on column DGN_NAVBAR.NAV_NAME_EN is '英文名';
comment on column DGN_NAVBAR.NAV_IP is '菜单项的ip,默认值为配置中心的nginx地址，如需改变地址，则按照格式填入即可';


-- 审批表
create table DGN_APPROVAL
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
    VIEW_URL         VARCHAR(200),
    PROCESSINSTANCEID   VARCHAR(64),
    EXECUTE_RESULT   TEXT
);
comment on table DGN_APPROVAL is '申请审批表';
comment on column DGN_APPROVAL.APPROVAL_ID is 'UUID唯一标识';
comment on column DGN_APPROVAL.MODULE_NAME is '业务模块';
comment on column DGN_APPROVAL.OPERATION_NAME is '申请操作';
comment on column DGN_APPROVAL.APPLICATION_INFO is '申请信息';
comment on column DGN_APPROVAL.CALLBACK_DATA is '回传业务系统数据';
comment on column DGN_APPROVAL.CALLBACK_URL is '回调业务系统地址';
comment on column DGN_APPROVAL.CREATE_TIME is '创建时间';
comment on column DGN_APPROVAL.STATUS is '状态(0:初始化;1:审批中;2:退回;结束;4:手动终止)';
comment on column DGN_APPROVAL.MODULE_ID is '业务模块标识(dataDefinition:数据定义;createTable:建表;standardTable:新建标准表;register:注册)';
comment on column DGN_APPROVAL.OPERATOR_ID is '申请人id';
comment on column DGN_APPROVAL.TASK_ID is '事务id';
comment on column DGN_APPROVAL.VIEW_URL is '申请详情查看地址';
comment on column DGN_APPROVAL.PROCESSINSTANCEID is '流程实例ID';
comment on column DGN_APPROVAL.EXECUTE_RESULT is '回调执行情况';

-- 资产大屏
create table DGN_PROPERTY_LARGE
(
    ID          VARCHAR(100) not null,
    DATA        TEXT not null,
    CREATE_TIME DATE default CURRENT_DATE not null,
    TYPE        NUMERIC not null,
    SAVE_FLAG   NUMERIC default 0 not null
);
comment on table DGN_PROPERTY_LARGE is '数据大屏存储需要前端的请求结果';
comment on column DGN_PROPERTY_LARGE.ID is '记录的ID值';
comment on column DGN_PROPERTY_LARGE.DATA is '页面需要用到的json数据';
comment on column DGN_PROPERTY_LARGE.CREATE_TIME is '记录的生成时间';
comment on column DGN_PROPERTY_LARGE.TYPE is '这个数据对应的模块 0:数据总资产(包含水位图)
1:原始库日接入情况
2:资源库资产情况
3:主题库资产情况
4:标签库资产情况
5:数据日接入情况(分为jz版本和tz版本)
6:字段分类的数据
7:资产使用情况
8:对外共享
9:地图数据';
comment on column DGN_PROPERTY_LARGE.SAVE_FLAG is '0:该条记录是定时任务生成的 1:假数据,在定时更新时不能删除该数据';

create table DGN_CITY_ABB (
    CITY_ABB  VARCHAR(100) not null,
    CITY_CODE VARCHAR(100) not null,
    CITY_NAME VARCHAR(100) not null
);
-- Add comments to the table
comment on table DGN_CITY_ABB is '新疆统计表里面城市的简写码表';
comment on column DGN_CITY_ABB.CITY_ABB is '地市简写';
comment on column DGN_CITY_ABB.CITY_CODE is '地市的编码';
comment on column DGN_CITY_ABB.CITY_NAME is '地市中文名';

create table DGN_INSPECT_OPERATORS
(
    TABLEID      VARCHAR(30),
    TABLENAME    VARCHAR(50),
    TABLENAME_CH VARCHAR(200),
    DATA_TYPE    VARCHAR(50),
    OPERATOR_NET VARCHAR(10),
    NETMODE      VARCHAR(10),
    CITYCODE     VARCHAR(10),
    CITYNAME     VARCHAR(30),
    RECORDS_ALL  NUMERIC,
    PARTITION    VARCHAR(20),
    INSERTTIME   DATE default CURRENT_DATE
);
comment on table DGN_INSPECT_OPERATORS is '数据大屏统计-运营商分布';
comment on column DGN_INSPECT_OPERATORS.TABLEID is '数据协议代码';
comment on column DGN_INSPECT_OPERATORS.TABLENAME is '数据表名';
comment on column DGN_INSPECT_OPERATORS.TABLENAME_CH is '数据中文表名';
comment on column DGN_INSPECT_OPERATORS.DATA_TYPE is '数据来源分类';
comment on column DGN_INSPECT_OPERATORS.OPERATOR_NET is '运营商';
comment on column DGN_INSPECT_OPERATORS.NETMODE is '网络制式';
comment on column DGN_INSPECT_OPERATORS.CITYCODE is '城市区号';
comment on column DGN_INSPECT_OPERATORS.CITYNAME is '城市名称';
comment on column DGN_INSPECT_OPERATORS.RECORDS_ALL is '记录数';
comment on column DGN_INSPECT_OPERATORS.PARTITION is '数据日期';

create table DGN_BSCDATA_CITY
(
    PRO_ID     NUMERIC(5) not null,
    CITY_ID    NUMERIC(5) not null,
    CITY_NAME  VARCHAR(30) default '' not null,
    CITY_QUHAO VARCHAR(8) default '',
    CITY_ZIP   VARCHAR(20) default '',
    CITY_CODE  VARCHAR(20) default '' not null
);
comment on table DGN_BSCDATA_CITY is '城市码表';

create table DGN_BSCDATA_PROVINCE
(
    PRO_ID     NUMERIC(5) not null,
    PRO_NAME   VARCHAR(20) default '',
    ISDIRECT   NUMERIC(2),
    CANTONCODE NUMERIC(10)
);
comment on table DGN_BSCDATA_PROVINCE is '省码表';

create table DGN_DISTRIBUTION_STATISTIC
(
    BIAOMING      VARCHAR(300) not null,
    NAME          VARCHAR(300) not null,
    TOTALCOUNT    VARCHAR(300) not null,
    STATISTICTIME VARCHAR(8) not null,
    PARTITIONTIME VARCHAR(300) not null,
    DT            VARCHAR(300) not null,
    DZ            VARCHAR(300) not null
);
comment on table DGN_DISTRIBUTION_STATISTIC is '新疆大屏数据下发量的统计表(只保留90天)';
comment on column DGN_DISTRIBUTION_STATISTIC.NAME is '数据中文名';
comment on column DGN_DISTRIBUTION_STATISTIC.TOTALCOUNT is '下发数据总量';
comment on column DGN_DISTRIBUTION_STATISTIC.STATISTICTIME is '统计时间,时间格式为yyyyMMdd';
comment on column DGN_DISTRIBUTION_STATISTIC.PARTITIONTIME is '分区时间';
comment on column DGN_DISTRIBUTION_STATISTIC.DZ is '地州名称';

create table DGN_NOSTD_DISTRIBUTE_STATISTIC
(
    ID            VARCHAR(256),
    NAME          VARCHAR(256),
    TOTALCOUNT    NUMERIC,
    STATISTICTIME VARCHAR(256),
    PARTITIONTIME VARCHAR(256),
    TABLENAME     VARCHAR(255),
    PROJECT       VARCHAR(255),
    DZ            VARCHAR(255)
);
comment on column DGN_NOSTD_DISTRIBUTE_STATISTIC.ID is '主键（uuid）';
comment on column DGN_NOSTD_DISTRIBUTE_STATISTIC.NAME is '数据名称';
comment on column DGN_NOSTD_DISTRIBUTE_STATISTIC.TOTALCOUNT is '传过来的数据量';
comment on column DGN_NOSTD_DISTRIBUTE_STATISTIC.STATISTICTIME is '数据统计时间(格式yyyymmdd)';
comment on column DGN_NOSTD_DISTRIBUTE_STATISTIC.PARTITIONTIME is '数据接入时间（格式yyyymmdd）';
comment on column DGN_NOSTD_DISTRIBUTE_STATISTIC.TABLENAME is '数据表名';
comment on column DGN_NOSTD_DISTRIBUTE_STATISTIC.PROJECT is '项目名';
comment on column DGN_NOSTD_DISTRIBUTE_STATISTIC.DZ is '下发目标';

create table DGN_PUYUANMESSAGEBUS
(
    PLATFORM      VARCHAR(255),
    PLATFORM_CODE VARCHAR(255),
    INTERFACE_NUM VARCHAR(255),
    SUMTOTAL      VARCHAR(255),
    LAST_TIME     VARCHAR(255)
);
-- Add comments to the table
comment on table DGN_PUYUANMESSAGEBUS
  is '普元的统计表';

create table DGN_INSTRUCTIONS
(
    TREE_NAME VARCHAR(1000),
    PARENT_ID VARCHAR(255),
    TREE_TYPE NUMERIC,
    CONTENT VARCHAR(1000),
    ID VARCHAR(50)
);

--权限表
create table USER_AUTHORITY
(
    id          VARCHAR(255) not null,
    cmn_memo    VARCHAR(255),
    modulecode  VARCHAR(255) not null,
    modulename  VARCHAR(255) not null,
    iscreater   NUMERIC(1) not null,
    username    VARCHAR(255) not null,
    cmn_name    VARCHAR(255),
    userid      NUMERIC not null,
    create_time DATE default CURRENT_DATE not null,
    organid     VARCHAR(255),
    organname   VARCHAR(1024)
);
comment on column USER_AUTHORITY.id
  is '业务资源ID,仓库模块数据源ID、协议编码（tableID）';
comment on column USER_AUTHORITY.cmn_memo
  is '业务资源ID对应信息';
comment on column USER_AUTHORITY.modulecode
  is '模块编码';
comment on column USER_AUTHORITY.modulename
  is '模块名称';
comment on column USER_AUTHORITY.iscreater
  is '0：非创建人 1：创建人';
comment on column USER_AUTHORITY.username
  is '有访问权限的用户名';
comment on column USER_AUTHORITY.cmn_name
  is '业务资源名';
comment on column USER_AUTHORITY.userid
  is '有访问权限的用户id';
comment on column USER_AUTHORITY.organid
  is '机构ID';
comment on column USER_AUTHORITY.organname
  is '机构名称';

-- Create table
create table DGN_INSPECT_ORGANIZATION_STAT
(
    TABLEID               VARCHAR(30),
    TABLENAME             VARCHAR(50),
    TABLENAME_CH          VARCHAR(200),
    ORGANIZATION_TYPE     VARCHAR(30),
    ORGANIZATION_NAME     VARCHAR(50),
    SUB_ORGANIZATION_TYPE VARCHAR(30),
    SUB_ORGANIZATION_NAME VARCHAR(100),
    VALVALUE              VARCHAR(30),
    VALTEXT               VARCHAR(30),
    OBJECT_COUNT          NUMERIC,
    RECORDS_ALL           NUMERIC,
    PARTITION             VARCHAR(20),
    INSERTTIME            DATE default CURRENT_DATE
);
-- Add comments to the table
comment on table DGN_INSPECT_ORGANIZATION_STAT
    is '数据大屏统计-数据组织资产分布';
-- Add comments to the columns
comment on column DGN_INSPECT_ORGANIZATION_STAT.TABLEID
    is '数据协议代码';
comment on column DGN_INSPECT_ORGANIZATION_STAT.TABLENAME
    is '数据表名';
comment on column DGN_INSPECT_ORGANIZATION_STAT.TABLENAME_CH
    is '数据中文表名';
comment on column DGN_INSPECT_ORGANIZATION_STAT.ORGANIZATION_TYPE
    is '数据组织类型代码';
comment on column DGN_INSPECT_ORGANIZATION_STAT.ORGANIZATION_NAME
    is '数据组织类型名称';
comment on column DGN_INSPECT_ORGANIZATION_STAT.SUB_ORGANIZATION_TYPE
    is '数据组织类型代码（二级）';
comment on column DGN_INSPECT_ORGANIZATION_STAT.SUB_ORGANIZATION_NAME
    is '数据组织类型名称（二级）';
comment on column DGN_INSPECT_ORGANIZATION_STAT.VALVALUE
    is '细类字典';
comment on column DGN_INSPECT_ORGANIZATION_STAT.VALTEXT
    is '细类名称';
comment on column DGN_INSPECT_ORGANIZATION_STAT.OBJECT_COUNT
    is '目标对象个数';
comment on column DGN_INSPECT_ORGANIZATION_STAT.RECORDS_ALL
    is '记录数';
comment on column DGN_INSPECT_ORGANIZATION_STAT.PARTITION
    is '数据日期';
