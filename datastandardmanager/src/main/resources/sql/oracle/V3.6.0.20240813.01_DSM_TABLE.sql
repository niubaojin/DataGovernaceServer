-- Create table
create table STANDARDIZE_OBJECT_RELATION
(
    ID          VARCHAR2(32) not null,
    OBJECT_ID   NUMBER(10) not null,
    OBJECT_NAME VARCHAR2(80) not null,
    TABLE_ID    VARCHAR2(40) not null,
    PARENT_ID   VARCHAR2(32),
    CREATE_TIME DATE,
    UPDATE_TIME DATE
);
-- Add comments to the columns
comment on column STANDARDIZE_OBJECT_RELATION.ID
  is '表 id';
comment on column STANDARDIZE_OBJECT_RELATION.OBJECT_ID
  is '数据集 id';
comment on column STANDARDIZE_OBJECT_RELATION.OBJECT_NAME
  is '数据名称';
comment on column STANDARDIZE_OBJECT_RELATION.TABLE_ID
  is '标准编码';
comment on column STANDARDIZE_OBJECT_RELATION.PARENT_ID
  is '当object_id原始汇聚，是：-1，否：关联的原始汇聚标准的object_id';
comment on column STANDARDIZE_OBJECT_RELATION.CREATE_TIME
  is '创建时间';
comment on column STANDARDIZE_OBJECT_RELATION.UPDATE_TIME
  is '更新时间';

-- Create table
create table STANDARDIZE_OBJECTFIELD_REL
(
    SET_ID                VARCHAR2(32) not null,
    ID                    VARCHAR2(32) not null,
    RECNO                 NUMBER(4),
    COLUMN_NAME           VARCHAR2(100),
    GADSJ_FIELDID         VARCHAR2(40),
    DICTIONARY_REF_ID     VARCHAR2(40),
    DICTIONARY_REF        VARCHAR2(40),
    DICTIONARY_CONTENT_ID VARCHAR2(40),
    DICTIONARY_CONTENT    VARCHAR2(40),
    PARENT_ID             VARCHAR2(32) not null,
    PARENT_COLUMN_NAME    VARCHAR2(100),
    CREATE_TIME           DATE,
    UPDATE_TIME           DATE,
    FIELDCHINESENAME      VARCHAR2(100)
);
-- Add comments to the columns
comment on column STANDARDIZE_OBJECTFIELD_REL.SET_ID
  is '数据集id';
comment on column STANDARDIZE_OBJECTFIELD_REL.ID
  is '数据项 id';
comment on column STANDARDIZE_OBJECTFIELD_REL.RECNO
  is '字段序号值';
comment on column STANDARDIZE_OBJECTFIELD_REL.COLUMN_NAME
  is '字段名称';
comment on column STANDARDIZE_OBJECTFIELD_REL.GADSJ_FIELDID
  is '数据元';
comment on column STANDARDIZE_OBJECTFIELD_REL.DICTIONARY_REF_ID
  is '引用数据字典id';
comment on column STANDARDIZE_OBJECTFIELD_REL.DICTIONARY_REF
  is '引用数据字典';
comment on column STANDARDIZE_OBJECTFIELD_REL.DICTIONARY_CONTENT_ID
  is '数据字典ID';
comment on column STANDARDIZE_OBJECTFIELD_REL.DICTIONARY_CONTENT
  is '数据字典内容';
comment on column STANDARDIZE_OBJECTFIELD_REL.PARENT_ID
  is '数据集 id,当object_id原始汇聚，是：-1，否：关联的原始汇聚标的object_id';
comment on column STANDARDIZE_OBJECTFIELD_REL.PARENT_COLUMN_NAME
  is '对标字段名称';
comment on column STANDARDIZE_OBJECTFIELD_REL.CREATE_TIME
  is '创建时间';
comment on column STANDARDIZE_OBJECTFIELD_REL.UPDATE_TIME
  is '更新时间';
comment on column STANDARDIZE_OBJECTFIELD_REL.FIELDCHINESENAME
  is '字段中文名称';

-- Create table
create table LABEL_CODE_TABLE
(
    LABEL_LEVEL NUMBER(30),
    LABEL_NAME  VARCHAR2(255)
);
-- Add comments to the columns
comment on column LABEL_CODE_TABLE.LABEL_LEVEL
  is '标签代号';
comment on column LABEL_CODE_TABLE.LABEL_NAME
  is '标签名称';

-- Create table
create table ALL_CODE_DATA
(
    ID         VARCHAR2(100) default SYS_GUID() not null,
    CODE_ID    VARCHAR2(100) not null,
    CODE_VALUE VARCHAR2(100) not null,
    CODE_TEXT  VARCHAR2(100) not null,
    MEMO       VARCHAR2(100)
);
-- Add comments to the table
comment on table ALL_CODE_DATA
  is '所有的代码信息';
-- Add comments to the columns
comment on column ALL_CODE_DATA.ID
  is '每个码值的唯一uuid';
comment on column ALL_CODE_DATA.CODE_ID
  is '每种码值的id值';
comment on column ALL_CODE_DATA.MEMO
  is '注释';

-- Create table
create table STANDARDIZE_ORIGINAL_DICT
(
    ID                     VARCHAR2(100) not null,
    DICTIONARY_NAME        VARCHAR2(100) not null,
    FACTURER_ID            VARCHAR2(40) not null,
    FACTURER               VARCHAR2(100) not null,
    CREATE_DATE            DATE,
    CREATOR                VARCHAR2(100),
    STANDARD_DICTIONARY    VARCHAR2(50),
    STANDARD_DICTIONARY_ID VARCHAR2(50),
    MEMO                   VARCHAR2(800),
    CREATE_TIME            DATE,
    UPDATE_TIME            DATE
);
-- Add comments to the columns
comment on column STANDARDIZE_ORIGINAL_DICT.ID
  is '原始字典表id';
comment on column STANDARDIZE_ORIGINAL_DICT.DICTIONARY_NAME
  is '字典名称';
comment on column STANDARDIZE_ORIGINAL_DICT.FACTURER_ID
  is '厂商信息id';
comment on column STANDARDIZE_ORIGINAL_DICT.FACTURER
  is '厂商信息';
comment on column STANDARDIZE_ORIGINAL_DICT.CREATE_DATE
  is '创建日期';
comment on column STANDARDIZE_ORIGINAL_DICT.CREATOR
  is '创建人';
comment on column STANDARDIZE_ORIGINAL_DICT.STANDARD_DICTIONARY
  is '对应标准字典';
comment on column STANDARDIZE_ORIGINAL_DICT.STANDARD_DICTIONARY_ID
  is '标准字典编码';
comment on column STANDARDIZE_ORIGINAL_DICT.MEMO
  is '备注说明';
comment on column STANDARDIZE_ORIGINAL_DICT.CREATE_TIME
  is '创建时间';
comment on column STANDARDIZE_ORIGINAL_DICT.UPDATE_TIME
  is '更新时间';
-- Create/Recreate primary, unique and foreign key constraints
alter table STANDARDIZE_ORIGINAL_DICT
    add constraint PK_ORIGINAL_DICTIONARIES primary key (ID);

-- Create table
create table STANDARDIZE_ORIGINAL_D_F
(
    GROUP_ID               VARCHAR2(100) not null,
    ID                     VARCHAR2(100) not null,
    RECNO                  NUMBER(2) not null,
    CODE_VALTEXT           VARCHAR2(100) not null,
    CODE_VALVALUE          VARCHAR2(50) not null,
    STANDARD_CODE_VALTEXT  VARCHAR2(100) not null,
    STANDARD_CODE_VALVALUE VARCHAR2(50),
    CREATE_TIME            DATE,
    UPDATE_TIME            DATE
);
-- Add comments to the columns
comment on column STANDARDIZE_ORIGINAL_D_F.GROUP_ID
  is '原始字典表id';
comment on column STANDARDIZE_ORIGINAL_D_F.ID
  is '字典代码项id';
comment on column STANDARDIZE_ORIGINAL_D_F.RECNO
  is '字段序号值';
comment on column STANDARDIZE_ORIGINAL_D_F.CODE_VALTEXT
  is '代码名称';
comment on column STANDARDIZE_ORIGINAL_D_F.CODE_VALVALUE
  is '代码值';
comment on column STANDARDIZE_ORIGINAL_D_F.STANDARD_CODE_VALTEXT
  is '对应标准代码名称';
comment on column STANDARDIZE_ORIGINAL_D_F.STANDARD_CODE_VALVALUE
  is '对应标准代码值';
comment on column STANDARDIZE_ORIGINAL_D_F.CREATE_TIME
  is '创建时间';
comment on column STANDARDIZE_ORIGINAL_D_F.UPDATE_TIME
  is '更新时间';
-- Create/Recreate primary, unique and foreign key constraints
alter table STANDARDIZE_ORIGINAL_D_F
    add foreign key (GROUP_ID)
        references STANDARDIZE_ORIGINAL_DICT (ID);

create table MODULE_CREATED_OBJECT
(
    TABLEID      VARCHAR2(200) not null,
    MODULE_NAME  VARCHAR2(500) not null,
    CREATED_TIME DATE default sysdate not null,
    OBJECTID     NUMBER
);

create table DD_BDDM
(
    DMZD    VARCHAR2(128),
    DMZDZWM VARCHAR2(128),
    DM      VARCHAR2(128),
    DMMC    VARCHAR2(128)
);
comment on table DD_BDDM
  is '地标';
comment on column DD_BDDM.DMZD
  is '代码字段';
comment on column DD_BDDM.DMZDZWM
  is '代码字段中文名';
comment on column DD_BDDM.DM
  is '代码';
comment on column DD_BDDM.DMMC
  is '代码名称';

-- Create table
create table SOURCEINFO
(
    ID             VARCHAR2(50),
    CREATE_TIME    DATE,
    UPDATE_TIME    DATE,
    SOURCEPROTOCOL VARCHAR2(256),
    TABLENAME      VARCHAR2(1000),
    SOURCESYSTEM   VARCHAR2(256),
    SOURCEFIRM     VARCHAR2(256),
    DATANAME       VARCHAR2(1000),
    DATA_ID        VARCHAR2(256),
    PROJECT_NAME   VARCHAR2(1000),
    CENTER_NAME    VARCHAR2(1000),
    CENTER_ID      VARCHAR2(256)
);
comment on table SOURCEINFO
  is '来源信息表';
-- Add comments to the columns
comment on column SOURCEINFO.SOURCEPROTOCOL
  is '来源数据协议';
comment on column SOURCEINFO.TABLENAME
  is '来源数据名(表英文名)';
comment on column SOURCEINFO.SOURCESYSTEM
  is '来源系统代码';
comment on column SOURCEINFO.SOURCEFIRM
  is '来源厂商';
comment on column SOURCEINFO.DATANAME
  is '来源数据名(表中文名)';
comment on column SOURCEINFO.DATA_ID
  is '数据源';
comment on column SOURCEINFO.PROJECT_NAME
  is '项目空间';
comment on column SOURCEINFO.CENTER_NAME
  is '数据中心名称';
comment on column SOURCEINFO.CENTER_ID
  is '数据中心id';

-- Create table
create table SOURCE_FIELD_INFO
(
    ID               VARCHAR2(50),
    SOURCEINFOID     VARCHAR2(50),
    TABLEID          VARCHAR2(1000),
    RESOURCEID       VARCHAR2(1000),
    FIELDNAME        VARCHAR2(1000),
    FIELDDESCRIPTION VARCHAR2(1000),
    FIELDTYPE        VARCHAR2(64),
    FIELDLENGTH      VARCHAR2(64),
    ISNONNULL        VARCHAR2(16),
    ISDELETED        VARCHAR2(16),
    ISPRIMARYKEY     VARCHAR2(16),
    ISFOREIGNKEY     VARCHAR2(16),
    INSERTTIME       VARCHAR2(128),
    UPDATETIME       VARCHAR2(128),
    DATABASEURL      VARCHAR2(128),
    SCHEMEID         VARCHAR2(128),
    FIELDSOURCETYPE  NUMBER(1) default 0,
    FIELDCODE        VARCHAR2(128),
    GADSJFIELDID     VARCHAR2(128),
    DETERMINERID     VARCHAR2(128),
    ELEMENTID        VARCHAR2(128)
);
comment on table SOURCE_FIELD_INFO
  is '来源字段信息表';
-- Add comments to the columns
comment on column SOURCE_FIELD_INFO.SOURCEINFOID
  is 'SOURCEINFOID表的ID';
comment on column SOURCE_FIELD_INFO.TABLEID
  is '表英文名';
comment on column SOURCE_FIELD_INFO.FIELDNAME
  is '字段英文名';
comment on column SOURCE_FIELD_INFO.FIELDDESCRIPTION
  is '字段中文名（描述）';
comment on column SOURCE_FIELD_INFO.FIELDTYPE
  is '字段类型';
comment on column SOURCE_FIELD_INFO.FIELDLENGTH
  is '字段长度';
comment on column SOURCE_FIELD_INFO.ISNONNULL
  is '是否为空';
comment on column SOURCE_FIELD_INFO.ISDELETED
  is '是否已经删除';
comment on column SOURCE_FIELD_INFO.ISPRIMARYKEY
  is '是否为主键';
comment on column SOURCE_FIELD_INFO.ISFOREIGNKEY
  is '是否为外键';
comment on column SOURCE_FIELD_INFO.INSERTTIME
  is '插入时间';
comment on column SOURCE_FIELD_INFO.UPDATETIME
  is '更新时间';
comment on column SOURCE_FIELD_INFO.DATABASEURL
  is '数据库的URL';
comment on column SOURCE_FIELD_INFO.SCHEMEID
  is '数据库/项目名';
comment on column SOURCE_FIELD_INFO.FIELDSOURCETYPE
  is '1：是 0：否 默认值：0';
comment on column SOURCE_FIELD_INFO.FIELDCODE
  is '相似度分析获取到的元素编码 add20200818';
comment on column SOURCE_FIELD_INFO.GADSJFIELDID
  is '数据元内部标识符';
comment on column SOURCE_FIELD_INFO.DETERMINERID
  is '限定词id';
comment on column SOURCE_FIELD_INFO.ELEMENTID
  is '数据要素id';

-- Create table
create table OBJECT_MANAGE_STANDARD
(
    ID      VARCHAR2(50),
    VALUE   VARCHAR2(50),
    TYPE    VARCHAR2(10),
    REMARKS VARCHAR2(4000),
    DELETED VARCHAR2(1),
    PARID   VARCHAR2(50),
    TEXT    VARCHAR2(200)
);
comment on table OBJECT_MANAGE_STANDARD
  is '数据库对象管理规范';
comment on column OBJECT_MANAGE_STANDARD.ID
  is '组织分类ID';
comment on column OBJECT_MANAGE_STANDARD.VALUE
  is '规范值';
comment on column OBJECT_MANAGE_STANDARD.TYPE
  is '规范类型';
comment on column OBJECT_MANAGE_STANDARD.REMARKS
  is '备注';
comment on column OBJECT_MANAGE_STANDARD.DELETED
  is '是否已删除(0:否;1:是)';
comment on column OBJECT_MANAGE_STANDARD.PARID
  is '父级组织分类ID';
comment on column OBJECT_MANAGE_STANDARD.TEXT
  is '组织分类名称';

create table STANDARD_TABLE_CREATED
(
    TABLE_NAME         VARCHAR2(100) not null,
    TABLE_PROJECT      VARCHAR2(100) not null,
    TABLE_BASE         VARCHAR2(100) not null,
    OBJECT_ID          VARCHAR2(100),
    ID                 VARCHAR2(100) not null,
    CREATE_TIME        DATE default sysdate not null,
    TABLE_NAME_CH      VARCHAR2(100),
    DATA_CENTER_NAME   VARCHAR2(100),
    DATA_RESOURCE_NAME VARCHAR2(100)
);
comment on table STANDARD_TABLE_CREATED
  is '通过标准管理页面已经创建的表';
comment on column STANDARD_TABLE_CREATED.TABLE_NAME
  is '在数据库中创建的表名';
comment on column STANDARD_TABLE_CREATED.TABLE_PROJECT
  is '在数据库中创建的表对应的项目名';
comment on column STANDARD_TABLE_CREATED.TABLE_BASE
  is '对应的数据库类型';
comment on column STANDARD_TABLE_CREATED.OBJECT_ID
  is 'objectId';
comment on column STANDARD_TABLE_CREATED.ID
  is '自增主键ID';
comment on column STANDARD_TABLE_CREATED.CREATE_TIME
  is '表的创建时间';
comment on column STANDARD_TABLE_CREATED.TABLE_NAME_CH
  is '表中文名';
comment on column STANDARD_TABLE_CREATED.DATA_CENTER_NAME
  is '数据中心名';
comment on column STANDARD_TABLE_CREATED.DATA_RESOURCE_NAME
  is '数据源名称';

-- Create table 表组织资产的相关数据
create table TABLE_ORGANIZATION_ASSETS_TEMP
(
    SJXJBM                    VARCHAR2(50),
    NAME                      VARCHAR2(600),
    TABLE_NAME_EN             VARCHAR2(600),
    PRIMARY_DATASOURCE_CH     VARCHAR2(150),
    SECONDARY_DATASOURCE_CH   VARCHAR2(150),
    PRIMARY_ORGANIZATION_CH   VARCHAR2(150),
    SECONDARY_ORGANIZATION_CH VARCHAR2(150),
    FACTOR_ONE_NAME           VARCHAR2(150),
    FACTOR_TWO_NAME           VARCHAR2(150),
    FACTOR_DETAILED_NAME      VARCHAR2(150),
    FACTOR_ATTRIBUTES_NAME    VARCHAR2(150),
    TABLE_ALL_COUNT           NUMBER,
    TABLE_SIZE                NUMBER,
    TABLE_PROJECT             VARCHAR2(150),
    LIFE_CYCLE                VARCHAR2(150),
    TABLE_TYPE                VARCHAR2(150),
    YESTERDAY_COUNT           NUMBER,
    PARTITION_MESSAGE         VARCHAR2(150),
    AVERAGE_DAILY_VOLUME      NUMBER,
    VALUE_RATE                NUMBER,
    NULL_RATE                 NUMBER,
    TABLE_STATE               VARCHAR2(150),
    UPDATE_TYPE               VARCHAR2(150),
    STATISTICS_TIME           DATE,
    FREQUENCY                 VARCHAR2(650),
    LIVETYPE                  VARCHAR2(650),
    REGISTER_STATE            VARCHAR2(255) default -1,
    OBJECT_ID                 VARCHAR2(100),
    AVERAGE_DAILY_SIZE        NUMBER,
    OBJECT_STATE              VARCHAR2(50),
    THREE_ORGANIZATION_CH     VARCHAR2(150),
    LABELS                    VARCHAR2(500),
    UPDATE_PERIOD             VARCHAR2(100),
    UPDATE_DATE               VARCHAR2(100),
    DATARESOURCE_CODE         VARCHAR2(100)
);
-- Add comments to the table
comment on table TABLE_ORGANIZATION_ASSETS_TEMP
  is '表组织资产的相关数据';
-- Add comments to the columns
comment on column TABLE_ORGANIZATION_ASSETS_TEMP.SJXJBM
  is '表协议名';
comment on column TABLE_ORGANIZATION_ASSETS_TEMP.NAME
  is '表中文名';
comment on column TABLE_ORGANIZATION_ASSETS_TEMP.TABLE_NAME_EN
  is '表英文名';
comment on column TABLE_ORGANIZATION_ASSETS_TEMP.PRIMARY_DATASOURCE_CH
  is '数据来源一级分类中文名';
comment on column TABLE_ORGANIZATION_ASSETS_TEMP.SECONDARY_DATASOURCE_CH
  is '数据来源二级分类中文名';
comment on column TABLE_ORGANIZATION_ASSETS_TEMP.PRIMARY_ORGANIZATION_CH
  is '数据组织一级分类中文名';
comment on column TABLE_ORGANIZATION_ASSETS_TEMP.SECONDARY_ORGANIZATION_CH
  is '数据组织二级分类中文名';
comment on column TABLE_ORGANIZATION_ASSETS_TEMP.FACTOR_ONE_NAME
  is '数据资源要素一级分类中文名';
comment on column TABLE_ORGANIZATION_ASSETS_TEMP.FACTOR_TWO_NAME
  is '数据资源要素二级分类中文名';
comment on column TABLE_ORGANIZATION_ASSETS_TEMP.FACTOR_DETAILED_NAME
  is '数据资源要素细目分类代码名称';
comment on column TABLE_ORGANIZATION_ASSETS_TEMP.FACTOR_ATTRIBUTES_NAME
  is '数据资源属性分类代码名称';
comment on column TABLE_ORGANIZATION_ASSETS_TEMP.TABLE_ALL_COUNT
  is '表数据总行数 到昨天分区';
comment on column TABLE_ORGANIZATION_ASSETS_TEMP.TABLE_SIZE
  is '表数据总大小 到昨天分区 byte';
comment on column TABLE_ORGANIZATION_ASSETS_TEMP.TABLE_PROJECT
  is '表的项目名';
comment on column TABLE_ORGANIZATION_ASSETS_TEMP.LIFE_CYCLE
  is '表的生命周期';
comment on column TABLE_ORGANIZATION_ASSETS_TEMP.TABLE_TYPE
  is '表类型 odps/ads/''''';
comment on column TABLE_ORGANIZATION_ASSETS_TEMP.YESTERDAY_COUNT
  is '昨天分区的数据量';
comment on column TABLE_ORGANIZATION_ASSETS_TEMP.PARTITION_MESSAGE
  is '是否为分区表  0:分区表  1:不是分区表  -1：不确定是否为分区表';
comment on column TABLE_ORGANIZATION_ASSETS_TEMP.AVERAGE_DAILY_VOLUME
  is '日均数据量';
comment on column TABLE_ORGANIZATION_ASSETS_TEMP.VALUE_RATE
  is '有值率';
comment on column TABLE_ORGANIZATION_ASSETS_TEMP.NULL_RATE
  is '空值率';
comment on column TABLE_ORGANIZATION_ASSETS_TEMP.TABLE_STATE
  is '状态  正常/异常';
comment on column TABLE_ORGANIZATION_ASSETS_TEMP.UPDATE_TYPE
  is '全量/增量/';
comment on column TABLE_ORGANIZATION_ASSETS_TEMP.STATISTICS_TIME
  is '统计时间';
comment on column TABLE_ORGANIZATION_ASSETS_TEMP.FREQUENCY
  is '最近一个月使用次数/总使用次数';
comment on column TABLE_ORGANIZATION_ASSETS_TEMP.LIVETYPE
  is 'NONLIVING：死表 LIVING：活表';
comment on column TABLE_ORGANIZATION_ASSETS_TEMP.REGISTER_STATE
  is '注册状态 -1:未注册 1:已注册';
comment on column TABLE_ORGANIZATION_ASSETS_TEMP.OBJECT_ID
  is 'object表中的objectid';
comment on column TABLE_ORGANIZATION_ASSETS_TEMP.AVERAGE_DAILY_SIZE
  is '日均数据大小';
comment on column TABLE_ORGANIZATION_ASSETS_TEMP.OBJECT_STATE
  is '停用，启用状态，1为启用';
comment on column TABLE_ORGANIZATION_ASSETS_TEMP.THREE_ORGANIZATION_CH
  is '数据组织三级分类中文名';
comment on column TABLE_ORGANIZATION_ASSETS_TEMP.LABELS
  is '数据资源标签';
comment on column TABLE_ORGANIZATION_ASSETS_TEMP.UPDATE_PERIOD
  is '更新批次';
comment on column TABLE_ORGANIZATION_ASSETS_TEMP.UPDATE_DATE
  is '更新周期';
comment on column TABLE_ORGANIZATION_ASSETS_TEMP.DATARESOURCE_CODE
  is '资源服务平台组织机构代码';

-- Create table
create table STANDARDIZE_OUTPUTOBJECT
(
    OBJ_GUID             VARCHAR2(40) not null,
    OOBJ_GUID            VARCHAR2(40) default SYS_GUID() not null,
    OOBJ_RESERVENAME_XML VARCHAR2(4000) not null,
    OOBJ_TYPE            NUMBER not null,
    OOBJ_MEMO            VARCHAR2(256),
    OOBJ_STATUS          NUMBER default 1,
    OOBJ_SOURCE          NUMBER default 0 not null
);
comment on table STANDARDIZE_OUTPUTOBJECT
  is '输出对象（协议）表';
comment on column STANDARDIZE_OUTPUTOBJECT.OBJ_GUID
  is '对象唯一标识';
comment on column STANDARDIZE_OUTPUTOBJECT.OOBJ_GUID
  is '输入对象唯一标识';
comment on column STANDARDIZE_OUTPUTOBJECT.OOBJ_RESERVENAME_XML
  is '对象英文扩展名XML';
comment on column STANDARDIZE_OUTPUTOBJECT.OOBJ_TYPE
  is '对象类型';
comment on column STANDARDIZE_OUTPUTOBJECT.OOBJ_MEMO
  is '备注';
comment on column STANDARDIZE_OUTPUTOBJECT.OOBJ_STATUS
  is '状态。0：禁用；1：启用。';
comment on column STANDARDIZE_OUTPUTOBJECT.OOBJ_SOURCE
  is '各厂商专有协议，1：普天；2：汇智；3：三所；4：烽火；默认为0表示适用于所有厂商';
alter table STANDARDIZE_OUTPUTOBJECT
    add constraint PK_OUTPUTOBJECT primary key (OOBJ_GUID, OOBJ_SOURCE);
alter table STANDARDIZE_OUTPUTOBJECT
    add constraint UQ_OUTPUTOBJECT unique (OBJ_GUID);

-- Create table
create table STANDARDIZE_OBJECT
(
    SYS_ID      NUMBER not null,
    OBJ_GUID    VARCHAR2(40) default SYS_GUID() not null,
    OBJ_ENGNAME VARCHAR2(256) not null,
    OBJ_CHINAME VARCHAR2(256) not null,
    OBJ_MEMO    VARCHAR2(256),
    SYS_SOURCE  NUMBER default 0,
    DATA_ID     VARCHAR2(1256),
    TABLE_ID    VARCHAR2(256),
    CENTER_ID   VARCHAR2(256),
    INSERT_DATE DATE default sysdate not null
);
comment on table STANDARDIZE_OBJECT
  is '对象（协议）表';
comment on column STANDARDIZE_OBJECT.SYS_ID
  is '系统代号';
comment on column STANDARDIZE_OBJECT.OBJ_GUID
  is '对象唯一标识';
comment on column STANDARDIZE_OBJECT.OBJ_ENGNAME
  is '对象英文名';
comment on column STANDARDIZE_OBJECT.OBJ_CHINAME
  is '对象中文名';
comment on column STANDARDIZE_OBJECT.OBJ_MEMO
  is '备注';
comment on column STANDARDIZE_OBJECT.SYS_SOURCE
  is '各厂商专有协议，1：普天；2：汇智；3：三所；4：烽火；默认为0表示适用于所有厂商';
comment on column STANDARDIZE_OBJECT.DATA_ID
  is '数据仓库的data_id数据，用于跳转到数据接入';
comment on column STANDARDIZE_OBJECT.TABLE_ID
  is '数据仓库的table_id数据，用于跳转到数据接入';
comment on column STANDARDIZE_OBJECT.CENTER_ID
  is '数据仓库的center_id数据，用于跳转到数据接入';
comment on column STANDARDIZE_OBJECT.INSERT_DATE
  is '数据的插入时间';
alter table STANDARDIZE_OBJECT
    add constraint PK_OBJECT primary key (OBJ_GUID);

create table STANDARDIZE_INPUTOBJECTRELATE
(
    IOBJ_GUID  VARCHAR2(40) not null,
    OOBJ_GUID  VARCHAR2(40) not null,
    IOR_GUID   VARCHAR2(40) default SYS_GUID() not null,
    IOR_MEMO   VARCHAR2(256),
    IOR_STATUS NUMBER default 1 not null,
    IOR_SOURCE NUMBER default 0
);
comment on table STANDARDIZE_INPUTOBJECTRELATE
  is '输入阶段-对象（协议）关系表';
comment on column STANDARDIZE_INPUTOBJECTRELATE.IOBJ_GUID
  is '与【输入协议表】主键对应';
comment on column STANDARDIZE_INPUTOBJECTRELATE.OOBJ_GUID
  is '与【输出协议表】主键对应';
comment on column STANDARDIZE_INPUTOBJECTRELATE.IOR_STATUS
  is '0：禁用；1：启用';
comment on column STANDARDIZE_INPUTOBJECTRELATE.IOR_SOURCE
  is '各厂商专有协议，1：普天；2：汇智；3：三所；4：烽火；默认为0表示适用于所有厂商';
alter table STANDARDIZE_INPUTOBJECTRELATE
    add constraint PK_INPUTOBJECTRELATE primary key (IOR_GUID);

-- Create table
create table STANDARDIZE_INPUTOBJECT
(
    OBJ_GUID      VARCHAR2(40) not null,
    IOBJ_GUID     VARCHAR2(40) default SYS_GUID() not null,
    IOBJ_TYPE     NUMBER not null,
    IOBJ_MODULE   VARCHAR2(256),
    IOBJ_MEMO     VARCHAR2(256),
    IOBJ_STATUS   NUMBER default 1,
    IOBJ_SAVEAS   NUMBER default 0,
    IOBJ_ENCODE   NUMBER default 0,
    IOBJ_SOURCE   NUMBER default 0 not null,
    IOBJ_ALLMATCH NUMBER default 0
);
comment on table STANDARDIZE_INPUTOBJECT
  is '输入对象（协议）表';
comment on column STANDARDIZE_INPUTOBJECT.OBJ_GUID
  is '对象唯一标识';
comment on column STANDARDIZE_INPUTOBJECT.IOBJ_GUID
  is '输入对象唯一标识';
comment on column STANDARDIZE_INPUTOBJECT.IOBJ_TYPE
  is '对象类型';
comment on column STANDARDIZE_INPUTOBJECT.IOBJ_MODULE
  is '需要处理该协议的模块';
comment on column STANDARDIZE_INPUTOBJECT.IOBJ_MEMO
  is '备注';
comment on column STANDARDIZE_INPUTOBJECT.IOBJ_STATUS
  is '状态。0：禁用；1：启用。';
comment on column STANDARDIZE_INPUTOBJECT.IOBJ_SAVEAS
  is '是否另存。0：否；1：是。';
comment on column STANDARDIZE_INPUTOBJECT.IOBJ_ENCODE
  is '编码 0:UTF8 1:GB2312 2:GBK';
comment on column STANDARDIZE_INPUTOBJECT.IOBJ_SOURCE
  is '各厂商专有协议，1：普天；2：汇智；3：三所；4：烽火；默认为0表示适用于所有厂商';
comment on column STANDARDIZE_INPUTOBJECT.IOBJ_ALLMATCH
  is '是否全中标。0：否；1：是。';
alter table STANDARDIZE_INPUTOBJECT
    add constraint PK_INPUTOBJECT primary key (IOBJ_GUID, IOBJ_SOURCE);
alter table STANDARDIZE_INPUTOBJECT
    add constraint UQ_INPUTOBJECT unique (OBJ_GUID);

