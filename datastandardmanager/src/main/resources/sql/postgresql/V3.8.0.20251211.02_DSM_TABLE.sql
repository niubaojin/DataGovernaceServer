-- Create table
create table DSM_LABEL_CODE_TABLE
(
    LABEL_LEVEL NUMERIC,
    LABEL_NAME  VARCHAR(255)
);
-- Add comments to the columns
comment on column DSM_LABEL_CODE_TABLE.LABEL_LEVEL
    is '标签代号';
comment on column DSM_LABEL_CODE_TABLE.LABEL_NAME
    is '标签名称';

-- Create table
create table DSM_ALL_CODE_DATA
(
    ID         VARCHAR(100) not null,
    CODE_ID    VARCHAR(100) not null,
    CODE_VALUE VARCHAR(100) not null,
    CODE_TEXT  VARCHAR(100) not null,
    MEMO       VARCHAR(100)
);
-- Add comments to the table
comment on table DSM_ALL_CODE_DATA
    is '所有的代码信息';
-- Add comments to the columns
comment on column DSM_ALL_CODE_DATA.ID
    is '每个码值的唯一uuid';
comment on column DSM_ALL_CODE_DATA.CODE_ID
    is '每种码值的id值';
comment on column DSM_ALL_CODE_DATA.MEMO
    is '注释';

create table DSM_MODULE_CREATED_OBJECT
(
    TABLEID      VARCHAR(200) not null,
    MODULE_NAME  VARCHAR(500) not null,
    CREATED_TIME DATE default CURRENT_DATE not null,
    OBJECTID     NUMERIC
);

create table DSM_DD_BDDM
(
    DMZD    VARCHAR(128),
    DMZDZWM VARCHAR(128),
    DM      VARCHAR(128),
    DMMC    VARCHAR(128)
);
comment on table DSM_DD_BDDM
    is '地标';
comment on column DSM_DD_BDDM.DMZD
    is '代码字段';
comment on column DSM_DD_BDDM.DMZDZWM
    is '代码字段中文名';
comment on column DSM_DD_BDDM.DM
    is '代码';
comment on column DSM_DD_BDDM.DMMC
    is '代码名称';

-- Create table
create table DSM_SOURCEINFO
(
    ID             VARCHAR(50),
    CREATE_TIME    DATE,
    UPDATE_TIME    DATE,
    SOURCEPROTOCOL VARCHAR(256),
    TABLENAME      VARCHAR(1000),
    SOURCESYSTEM   VARCHAR(256),
    SOURCEFIRM     VARCHAR(256),
    DATANAME       VARCHAR(1000),
    DATA_ID        VARCHAR(256),
    PROJECT_NAME   VARCHAR(1000),
    CENTER_NAME    VARCHAR(1000),
    CENTER_ID      VARCHAR(256)
);
comment on table DSM_SOURCEINFO
    is '来源信息表';
-- Add comments to the columns
comment on column DSM_SOURCEINFO.SOURCEPROTOCOL
    is '来源数据协议';
comment on column DSM_SOURCEINFO.TABLENAME
    is '来源数据名(表英文名)';
comment on column DSM_SOURCEINFO.SOURCESYSTEM
    is '来源系统代码';
comment on column DSM_SOURCEINFO.SOURCEFIRM
    is '来源厂商';
comment on column DSM_SOURCEINFO.DATANAME
    is '来源数据名(表中文名)';
comment on column DSM_SOURCEINFO.DATA_ID
    is '数据源';
comment on column DSM_SOURCEINFO.PROJECT_NAME
    is '项目空间';
comment on column DSM_SOURCEINFO.CENTER_NAME
    is '数据中心名称';
comment on column DSM_SOURCEINFO.CENTER_ID
    is '数据中心id';

-- Create table
create table DSM_SOURCE_FIELD_INFO
(
    ID               VARCHAR(50),
    SOURCEINFOID     VARCHAR(50),
    TABLEID          VARCHAR(1000),
    RESOURCEID       VARCHAR(1000),
    FIELDNAME        VARCHAR(1000),
    FIELDDESCRIPTION VARCHAR(1000),
    FIELDTYPE        VARCHAR(64),
    FIELDLENGTH      VARCHAR(64),
    ISNONNULL        VARCHAR(16),
    ISDELETED        VARCHAR(16),
    ISPRIMARYKEY     VARCHAR(16),
    ISFOREIGNKEY     VARCHAR(16),
    INSERTTIME       VARCHAR(128),
    UPDATETIME       VARCHAR(128),
    DATABASEURL      VARCHAR(128),
    SCHEMEID         VARCHAR(128),
    FIELDSOURCETYPE  NUMERIC,
    FIELDCODE        VARCHAR(128),
    GADSJFIELDID     VARCHAR(128),
    DETERMINERID     VARCHAR(128),
    ELEMENTID        VARCHAR(128)
);
comment on table DSM_SOURCE_FIELD_INFO
    is '来源字段信息表';
-- Add comments to the columns
comment on column DSM_SOURCE_FIELD_INFO.SOURCEINFOID
    is 'SOURCEINFOID表的ID';
comment on column DSM_SOURCE_FIELD_INFO.TABLEID
    is '表英文名';
comment on column DSM_SOURCE_FIELD_INFO.FIELDNAME
    is '字段英文名';
comment on column DSM_SOURCE_FIELD_INFO.FIELDDESCRIPTION
    is '字段中文名（描述）';
comment on column DSM_SOURCE_FIELD_INFO.FIELDTYPE
    is '字段类型';
comment on column DSM_SOURCE_FIELD_INFO.FIELDLENGTH
    is '字段长度';
comment on column DSM_SOURCE_FIELD_INFO.ISNONNULL
    is '是否为空';
comment on column DSM_SOURCE_FIELD_INFO.ISDELETED
    is '是否已经删除';
comment on column DSM_SOURCE_FIELD_INFO.ISPRIMARYKEY
    is '是否为主键';
comment on column DSM_SOURCE_FIELD_INFO.ISFOREIGNKEY
    is '是否为外键';
comment on column DSM_SOURCE_FIELD_INFO.INSERTTIME
    is '插入时间';
comment on column DSM_SOURCE_FIELD_INFO.UPDATETIME
    is '更新时间';
comment on column DSM_SOURCE_FIELD_INFO.DATABASEURL
    is '数据库的URL';
comment on column DSM_SOURCE_FIELD_INFO.SCHEMEID
    is '数据库/项目名';
comment on column DSM_SOURCE_FIELD_INFO.FIELDSOURCETYPE
    is '1：是 0：否 默认值：0';
comment on column DSM_SOURCE_FIELD_INFO.FIELDCODE
    is '相似度分析获取到的元素编码 add20200818';
comment on column DSM_SOURCE_FIELD_INFO.GADSJFIELDID
    is '数据元内部标识符';
comment on column DSM_SOURCE_FIELD_INFO.DETERMINERID
    is '限定词id';
comment on column DSM_SOURCE_FIELD_INFO.ELEMENTID
    is '数据要素id';

-- Create table
create table DSM_OBJECT_MANAGE_STANDARD
(
    ID      VARCHAR(50),
    VALUE   VARCHAR(50),
    TYPE    VARCHAR(10),
    REMARKS VARCHAR(4000),
    DELETED VARCHAR(1),
    PARID   VARCHAR(50),
    TEXT    VARCHAR(200)
);
comment on table DSM_OBJECT_MANAGE_STANDARD
    is '数据库对象管理规范';
comment on column DSM_OBJECT_MANAGE_STANDARD.ID
    is '组织分类ID';
comment on column DSM_OBJECT_MANAGE_STANDARD.VALUE
    is '规范值';
comment on column DSM_OBJECT_MANAGE_STANDARD.TYPE
    is '规范类型';
comment on column DSM_OBJECT_MANAGE_STANDARD.REMARKS
    is '备注';
comment on column DSM_OBJECT_MANAGE_STANDARD.DELETED
    is '是否已删除(0:否;1:是)';
comment on column DSM_OBJECT_MANAGE_STANDARD.PARID
    is '父级组织分类ID';
comment on column DSM_OBJECT_MANAGE_STANDARD.TEXT
    is '组织分类名称';

create table DSM_STANDARD_TABLE_CREATED
(
    TABLE_NAME         VARCHAR(100) not null,
    TABLE_PROJECT      VARCHAR(100) not null,
    TABLE_BASE         VARCHAR(100) not null,
    OBJECT_ID          VARCHAR(100),
    ID                 VARCHAR(100) not null,
    CREATE_TIME        DATE default CURRENT_DATE not null,
    TABLE_NAME_CH      VARCHAR(100),
    DATA_CENTER_NAME   VARCHAR(100),
    DATA_RESOURCE_NAME VARCHAR(100)
);
comment on table DSM_STANDARD_TABLE_CREATED
    is '通过标准管理页面已经创建的表';
comment on column DSM_STANDARD_TABLE_CREATED.TABLE_NAME
    is '在数据库中创建的表名';
comment on column DSM_STANDARD_TABLE_CREATED.TABLE_PROJECT
    is '在数据库中创建的表对应的项目名';
comment on column DSM_STANDARD_TABLE_CREATED.TABLE_BASE
    is '对应的数据库类型';
comment on column DSM_STANDARD_TABLE_CREATED.OBJECT_ID
    is 'objectId';
comment on column DSM_STANDARD_TABLE_CREATED.ID
    is '自增主键ID';
comment on column DSM_STANDARD_TABLE_CREATED.CREATE_TIME
    is '表的创建时间';
comment on column DSM_STANDARD_TABLE_CREATED.TABLE_NAME_CH
    is '表中文名';
comment on column DSM_STANDARD_TABLE_CREATED.DATA_CENTER_NAME
    is '数据中心名';
comment on column DSM_STANDARD_TABLE_CREATED.DATA_RESOURCE_NAME
    is '数据源名称';

-- Create table 表组织资产的相关数据
create table DP_TABLE_ORGANIZATION_ASSETS_TEMP
(
    SJXJBM                    VARCHAR(50),
    NAME                      VARCHAR(600),
    TABLE_NAME_EN             VARCHAR(600),
    PRIMARY_DATASOURCE_CH     VARCHAR(150),
    SECONDARY_DATASOURCE_CH   VARCHAR(150),
    PRIMARY_ORGANIZATION_CH   VARCHAR(150),
    SECONDARY_ORGANIZATION_CH VARCHAR(150),
    FACTOR_ONE_NAME           VARCHAR(150),
    FACTOR_TWO_NAME           VARCHAR(150),
    FACTOR_DETAILED_NAME      VARCHAR(150),
    FACTOR_ATTRIBUTES_NAME    VARCHAR(150),
    TABLE_ALL_COUNT           NUMERIC,
    TABLE_SIZE                NUMERIC,
    TABLE_PROJECT             VARCHAR(150),
    LIFE_CYCLE                VARCHAR(150),
    TABLE_TYPE                VARCHAR(150),
    YESTERDAY_COUNT           NUMERIC,
    PARTITION_MESSAGE         VARCHAR(150),
    AVERAGE_DAILY_VOLUME      NUMERIC,
    VALUE_RATE                NUMERIC,
    NULL_RATE                 NUMERIC,
    TABLE_STATE               VARCHAR(150),
    UPDATE_TYPE               VARCHAR(150),
    STATISTICS_TIME           DATE,
    FREQUENCY                 VARCHAR(650),
    LIVETYPE                  VARCHAR(650),
    REGISTER_STATE            VARCHAR(255) default -1,
    OBJECT_ID                 VARCHAR(100),
    AVERAGE_DAILY_SIZE        NUMERIC,
    OBJECT_STATE              VARCHAR(50),
    THREE_ORGANIZATION_CH     VARCHAR(150),
    LABELS                    VARCHAR(500),
    UPDATE_PERIOD             VARCHAR(100),
    UPDATE_DATE               VARCHAR(100),
    DATARESOURCE_CODE         VARCHAR(100)
);
-- Add comments to the table
comment on table DP_TABLE_ORGANIZATION_ASSETS_TEMP
    is '表组织资产的相关数据';
-- Add comments to the columns
comment on column DP_TABLE_ORGANIZATION_ASSETS_TEMP.SJXJBM
    is '表协议名';
comment on column DP_TABLE_ORGANIZATION_ASSETS_TEMP.NAME
    is '表中文名';
comment on column DP_TABLE_ORGANIZATION_ASSETS_TEMP.TABLE_NAME_EN
    is '表英文名';
comment on column DP_TABLE_ORGANIZATION_ASSETS_TEMP.PRIMARY_DATASOURCE_CH
    is '数据来源一级分类中文名';
comment on column DP_TABLE_ORGANIZATION_ASSETS_TEMP.SECONDARY_DATASOURCE_CH
    is '数据来源二级分类中文名';
comment on column DP_TABLE_ORGANIZATION_ASSETS_TEMP.PRIMARY_ORGANIZATION_CH
    is '数据组织一级分类中文名';
comment on column DP_TABLE_ORGANIZATION_ASSETS_TEMP.SECONDARY_ORGANIZATION_CH
    is '数据组织二级分类中文名';
comment on column DP_TABLE_ORGANIZATION_ASSETS_TEMP.FACTOR_ONE_NAME
    is '数据资源要素一级分类中文名';
comment on column DP_TABLE_ORGANIZATION_ASSETS_TEMP.FACTOR_TWO_NAME
    is '数据资源要素二级分类中文名';
comment on column DP_TABLE_ORGANIZATION_ASSETS_TEMP.FACTOR_DETAILED_NAME
    is '数据资源要素细目分类代码名称';
comment on column DP_TABLE_ORGANIZATION_ASSETS_TEMP.FACTOR_ATTRIBUTES_NAME
    is '数据资源属性分类代码名称';
comment on column DP_TABLE_ORGANIZATION_ASSETS_TEMP.TABLE_ALL_COUNT
    is '表数据总行数 到昨天分区';
comment on column DP_TABLE_ORGANIZATION_ASSETS_TEMP.TABLE_SIZE
    is '表数据总大小 到昨天分区 byte';
comment on column DP_TABLE_ORGANIZATION_ASSETS_TEMP.TABLE_PROJECT
    is '表的项目名';
comment on column DP_TABLE_ORGANIZATION_ASSETS_TEMP.LIFE_CYCLE
    is '表的生命周期';
comment on column DP_TABLE_ORGANIZATION_ASSETS_TEMP.TABLE_TYPE
    is '表类型 odps/ads/''''';
comment on column DP_TABLE_ORGANIZATION_ASSETS_TEMP.YESTERDAY_COUNT
    is '昨天分区的数据量';
comment on column DP_TABLE_ORGANIZATION_ASSETS_TEMP.PARTITION_MESSAGE
    is '是否为分区表  0:分区表  1:不是分区表  -1：不确定是否为分区表';
comment on column DP_TABLE_ORGANIZATION_ASSETS_TEMP.AVERAGE_DAILY_VOLUME
    is '日均数据量';
comment on column DP_TABLE_ORGANIZATION_ASSETS_TEMP.VALUE_RATE
    is '有值率';
comment on column DP_TABLE_ORGANIZATION_ASSETS_TEMP.NULL_RATE
    is '空值率';
comment on column DP_TABLE_ORGANIZATION_ASSETS_TEMP.TABLE_STATE
    is '状态  正常/异常';
comment on column DP_TABLE_ORGANIZATION_ASSETS_TEMP.UPDATE_TYPE
    is '全量/增量/';
comment on column DP_TABLE_ORGANIZATION_ASSETS_TEMP.STATISTICS_TIME
    is '统计时间';
comment on column DP_TABLE_ORGANIZATION_ASSETS_TEMP.FREQUENCY
    is '最近一个月使用次数/总使用次数';
comment on column DP_TABLE_ORGANIZATION_ASSETS_TEMP.LIVETYPE
    is 'NONLIVING：死表 LIVING：活表';
comment on column DP_TABLE_ORGANIZATION_ASSETS_TEMP.REGISTER_STATE
    is '注册状态 -1:未注册 1:已注册';
comment on column DP_TABLE_ORGANIZATION_ASSETS_TEMP.OBJECT_ID
    is 'object表中的objectid';
comment on column DP_TABLE_ORGANIZATION_ASSETS_TEMP.AVERAGE_DAILY_SIZE
    is '日均数据大小';
comment on column DP_TABLE_ORGANIZATION_ASSETS_TEMP.OBJECT_STATE
    is '停用，启用状态，1为启用';
comment on column DP_TABLE_ORGANIZATION_ASSETS_TEMP.THREE_ORGANIZATION_CH
    is '数据组织三级分类中文名';
comment on column DP_TABLE_ORGANIZATION_ASSETS_TEMP.LABELS
    is '数据资源标签';
comment on column DP_TABLE_ORGANIZATION_ASSETS_TEMP.UPDATE_PERIOD
    is '更新批次';
comment on column DP_TABLE_ORGANIZATION_ASSETS_TEMP.UPDATE_DATE
    is '更新周期';
comment on column DP_TABLE_ORGANIZATION_ASSETS_TEMP.DATARESOURCE_CODE
    is '资源服务平台组织机构代码';
