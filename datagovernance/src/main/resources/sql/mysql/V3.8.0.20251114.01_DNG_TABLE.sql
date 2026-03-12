-- 创建 DGN_COMMON_SETTING 表
CREATE TABLE IF NOT EXISTS `DGN_COMMON_SETTING` (
    PARENT_ID        VARCHAR(300) COMMENT '阈值所属的父类ID',
    NAME             VARCHAR(300) COMMENT '名称',
    LOGICAL_JUDGMENT VARCHAR(200) COMMENT '逻辑判断',
    THRESHOLD_VALUE  TEXT         COMMENT '阈值',
    ID               VARCHAR(300) NOT NULL COMMENT '主键id',
    TREE_TYPE        VARCHAR(100) COMMENT '该行数据所属的类别，值为1,2,3 数字越小级别越大',
    IS_ACTIVE        VARCHAR(1)   COMMENT '是否启用(1:是; 0:否)',
    PAGE_URL         VARCHAR(300) COMMENT '配置页面地址'
)COMMENT = '数据阈值设置的相关数据';

-- 创建 DGN_DATA_LARGE_SCREEN 表
CREATE TABLE IF NOT EXISTS `DGN_DATA_LARGE_SCREEN` (
    MODULE_ID   VARCHAR(255) COMMENT '模块id',
    MODULE_NAME VARCHAR(255) COMMENT '模块名称',
    DATA_INFO   TEXT         COMMENT '数据信息json',
    CREATE_TIME DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
)COMMENT = '数据大屏-数据信息';

-- 创建导航栏表：DGN_NAVBAR
CREATE TABLE IF NOT EXISTS `DGN_NAVBAR` (
    NAV_ID          VARCHAR(55)  COMMENT 'id，不重复即可',
    NAV_NAME        VARCHAR(100) COMMENT '导航栏名字（需与统一认证管理平台上资源管理配置数据工厂下的资源别名需对应）',
    NAV_CLASS       VARCHAR(55)  COMMENT '一级菜单的类型，用于图片',
    NAV_LINK        VARCHAR(200) COMMENT '菜单导向的uri',
    NAV_SHOW        VARCHAR(10)  COMMENT '是否展示，0为不展示，1为展示',
    NAV_BLANK       TEXT         COMMENT '开新窗口标志：[空、_blank]',
    NAV_LEVEL       VARCHAR(10)  COMMENT '菜单项等级',
    NAV_PARENT_NAME VARCHAR(100) COMMENT '父级英文名',
    NAV_ORDER       VARCHAR(10)  COMMENT '菜单栏排序',
    NAV_NAME_EN     VARCHAR(100) COMMENT '英文名',
    NAV_IP          VARCHAR(100) COMMENT '菜单项的ip,默认值为配置中心的nginx地址，如需改变地址，则按照格式填入即可',
    NAV_REDIRECT    TEXT         COMMENT '[可选]跳转路径',
    NAV_HIDDEN      INT DEFAULT 0 COMMENT '[可选]是否隐藏，默认为false'
)COMMENT = '创建导航栏表';

-- 审批表：DgnApproval
CREATE TABLE IF NOT EXISTS `DGN_APPROVAL` (
  APPROVAL_ID       VARCHAR (64)  COMMENT 'UUID唯一标识',
  MODULE_NAME       VARCHAR (200) COMMENT '业务模块',
  OPERATION_NAME    VARCHAR (200) COMMENT '申请操作',
  APPLICATION_INFO  TEXT          COMMENT '申请信息',
  CALLBACK_DATA     TEXT          COMMENT '回传业务系统数据',
  CALLBACK_URL      VARCHAR (64)  COMMENT '回调业务系统地址',
  CREATE_TIME       DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  STATUS            TINYINT UNSIGNED COMMENT '状态（例如：初始化、审批中、退回等）',
  MODULE_ID         VARCHAR (64)  COMMENT '业务模块标识(dataDefinition: 数据定义; createTable: 建表; standardTable: 新建标准表; register: 注册)',
  OPERATOR_ID       VARCHAR (64)  COMMENT '申请人id',
  TASK_ID           VARCHAR (64)  COMMENT '事务id',
  VIEW_URL          VARCHAR (255) COMMENT '申请详情查看地址',
  PROCESSINSTANCEID VARCHAR (64)  COMMENT '流程实例ID',
  EXECUTE_RESULT    TEXT          COMMENT '回调执行情况'
) comment = '申请审批表';

CREATE TABLE IF NOT EXISTS `DGN_PROPERTY_LARGE` (
    ID          VARCHAR(100) NOT NULL  COMMENT '记录的ID值',
    DATA        TEXT NOT NULL          COMMENT '页面需要用到的json数据',
    CREATE_TIME DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '记录的生成时间',
    TYPE        INT NOT NULL           COMMENT '这个数据对应的模块 0:数据总资产(包含水位图) 1:原始库日接入情况 2:资源库资产情况 3:主题库资产情况 4:标签库资产情况 5:数据日接入情况(分为jz版本和tz版本) 6:字段分类的数据 7:资产使用情况 8:对外共享 9:地图数据',
    SAVE_FLAG   INT DEFAULT 0 NOT NULL COMMENT '0:该条记录是定时任务生成的 1:假数据,在定时更新时不能删除该数据'
) COMMENT = '数据大屏存储需要前端的请求结果';

CREATE TABLE IF NOT EXISTS `DGN_CITY_ABB` (
    CITY_ABB   VARCHAR(100) NOT NULL COMMENT '地市简写',
    CITY_CODE  VARCHAR(100) NOT NULL COMMENT '地市的编码',
    CITY_NAME  VARCHAR(100) NOT NULL COMMENT '地市中文名'
) COMMENT = '新疆统计表里面城市的简写码表';

CREATE TABLE IF NOT EXISTS `DGN_INSPECT_OPERATORS` (
    TABLEID      VARCHAR(35)  COMMENT '数据协议代码',
    TABLENAME    VARCHAR(55)  COMMENT '数据表名',
    TABLENAME_CH VARCHAR(255) COMMENT '数据中文表名',
    DATA_TYPE    VARCHAR(55)  COMMENT '数据来源分类',
    OPERATOR_NET VARCHAR(10)  COMMENT '运营商',
    NETMODE      VARCHAR(10)  COMMENT '网络制式',
    CITYCODE     VARCHAR(10)  COMMENT '城市区号',
    CITYNAME     VARCHAR(35)  COMMENT '城市名称',
    RECORDS_ALL  INT          COMMENT '记录数',
    `PARTITION`  VARCHAR(25)  COMMENT '日期分区',
    INSERTTIME   DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '插入时间（系统自动填充）'
) COMMENT = '数据大屏统计-运营商分布';

-- 创建表 DGN_BSCDATA_CITY
CREATE TABLE DGN_BSCDATA_CITY (
    PRO_ID     INT NOT NULL,
    CITY_ID    INT NOT NULL,
    CITY_NAME  VARCHAR(30) DEFAULT '' NOT NULL,
    CITY_QUHAO VARCHAR(8) DEFAULT '',
    CITY_ZIP   VARCHAR(20) DEFAULT '',
    CITY_CODE  VARCHAR(20) DEFAULT '' NOT NULL
) COMMENT = '城市码表';

-- 创建表 DGN_BSCDATA_PROVINCE
CREATE TABLE DGN_BSCDATA_PROVINCE (
    PRO_ID     INT NOT NULL,
    PRO_NAME   VARCHAR(20) DEFAULT '',
    ISDIRECT   INT,
    CANTONCODE INT
) COMMENT = '省码表';

-- 创建表 DGN_DISTRIBUTION_STATISTIC
CREATE TABLE DGN_DISTRIBUTION_STATISTIC (
    BIAOMING      VARCHAR(300) NOT NULL COMMENT '表名',
    NAME          VARCHAR(300) NOT NULL COMMENT '数据中文名',
    TOTALCOUNT    VARCHAR(300) NOT NULL COMMENT '下发数据总量',
    STATISTICTIME VARCHAR(8)   NOT NULL COMMENT '统计时间,时间格式为yyyyMMdd',
    PARTITIONTIME VARCHAR(300) NOT NULL COMMENT '分区时间',
    DT            VARCHAR(300) NOT NULL COMMENT 'DT',
    DZ            VARCHAR(300) NOT NULL COMMENT '地州名称'
) COMMENT = '新疆大屏数据下发量的统计表（只保留90天）';

-- 创建表 DGN_NOSTD_DISTRIBUTE_STATISTIC
CREATE TABLE DGN_NOSTD_DISTRIBUTE_STATISTIC (
    ID            VARCHAR(256) COMMENT '主键（uuid）',
    NAME          VARCHAR(256) COMMENT '数据名称',
    TOTALCOUNT    INT          COMMENT '传过来的数据量',
    STATISTICTIME VARCHAR(256) COMMENT '数据统计时间（格式yyyymmdd）',
    PARTITIONTIME VARCHAR(256) COMMENT '数据接入时间（格式yyyymmdd）',
    TABLENAME     VARCHAR(255) COMMENT '数据表名',
    PROJECT       VARCHAR(255) COMMENT '项目名',
    DZ            VARCHAR(255) COMMENT '下发目标'
);

-- 创建表 DGN_PUYUANMESSAGEBUS
CREATE TABLE DGN_PUYUANMESSAGEBUS (
    PLATFORM      VARCHAR(255),
    PLATFORM_CODE VARCHAR(255),
    INTERFACE_NUM VARCHAR(255),
    SUMTOTAL      VARCHAR(255),
    LAST_TIME     VARCHAR(255)
) COMMENT = '普元的统计表';

-- 创建表 DGn_INSTRUCTIONS
CREATE TABLE DGn_INSTRUCTIONS (
    TREE_NAME   VARCHAR(1000),
    PARENT_ID   VARCHAR(255),
    TREE_TYPE   INT,
    CONTENT     VARCHAR(1000),
    ID          VARCHAR(50)
);

-- 权限表 USER_AUTHORITY
CREATE TABLE USER_AUTHORITY (
    id           VARCHAR(255) NOT NULL COMMENT '业务资源ID,仓库模块数据源ID、协议编码（tableID）',
    cmn_memo     VARCHAR(255)          COMMENT '业务资源ID对应信息',
    modulecode   VARCHAR(255) NOT NULL COMMENT '模块编码',
    modulename   VARCHAR(255) NOT NULL COMMENT '模块名称',
    iscreater    INT NOT NULL          COMMENT '0：非创建人 1：创建人',
    username     VARCHAR(255) NOT NULL COMMENT '有访问权限的用户名',
    cmn_name     VARCHAR(255)          COMMENT '业务资源名',
    userid       INT NOT NULL          COMMENT '有访问权限的用户id',
    create_time  TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    organid      VARCHAR(255)          COMMENT '机构ID',
    organname    VARCHAR(1024)         COMMENT '机构名称'
);

-- 创建表 DGn_INSPECT_ORGANIZATION_Stat
CREATE TABLE DGN_INSPECT_ORGANIZATION_STAT (
    TABLEID               VARCHAR(30)  COMMENT '数据协议代码',
    TABLENAME             VARCHAR(50)  COMMENT '数据表名',
    TABLENAME_CH          VARCHAR(200) COMMENT '数据中文表名',
    ORGANIZATION_TYPE     VARCHAR(30)  COMMENT '数据组织类型代码',
    ORGANIZATION_NAME     VARCHAR(50)  COMMENT '数据组织类型名称',
    SUB_ORGANIZATION_TYPE VARCHAR(30)  COMMENT '数据组织类型代码（二级）',
    SUB_ORGANIZATION_NAME VARCHAR(100) COMMENT '数据组织类型名称（二级）',
    VALVALUE              VARCHAR(30)  COMMENT '细类字典',
    VALTEXT               VARCHAR(30)  COMMENT '细类名称',
    OBJECT_COUNT          INT          COMMENT '目标对象个数',
    RECORDS_ALL           INT          COMMENT '记录数',
    `PARTITION`           VARCHAR(20)  COMMENT '数据日期',
    INSERTTIME            DATETIME DEFAULT CURRENT_TIMESTAMP
) COMMENT = '数据大屏统计-数据组织资产分布';
