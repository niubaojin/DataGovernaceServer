create table DAC_STORAGE_PROVIDER_BILL(
    SJTGF_DZDBH   VARCHAR2(64),
    SJJSWZ_JYQK   VARCHAR2(255),
    SJCCXSDM      NUMBER,
    SJ_DZWJMC     VARCHAR2(128),
    SJZYBSF       VARCHAR2(64),
    SJZYMC        VARCHAR2(64),
    BZSJXJBM      VARCHAR2(64),
    SJTS          NUMBER,
    SJ_DZWJDX     NUMBER,
    SJQSBH        VARCHAR2(64),
    SJJWBH        VARCHAR2(64),
    SJJYZ         VARCHAR2(64),
    SJJYSFMC      VARCHAR2(20),
    SCSB_DZDBH    VARCHAR2(64),
    SCSJ_RQSJ     NUMBER,
    SJTGFGLY_XM   VARCHAR2(32),
    SJTGFGLY_LXDH VARCHAR2(32),
    XXRWBH        VARCHAR2(128)
);
alter table DAC_STORAGE_PROVIDER_BILL add constraint DAC_SSPB_PRI primary key (SJTGF_DZDBH);


create table DAC_ACCESS_INSIDE_BILL
(
    PROVIDER_BILL_NO   VARCHAR2(50) not null,
    ACCEPTOR_BILL_NO   VARCHAR2(50) not null,
    PROVIDER_NO        VARCHAR2(50),
    ACCEPTRO_NO        VARCHAR2(50),
    RESOURCE_ID        VARCHAR2(50),
    DATA_COUNT         NUMBER,
    DATA_FINGERPRINT   VARCHAR2(50),
    FINGERPRINT_TYPE   VARCHAR2(20),
    DATA_SIZE          VARCHAR2(20),
    START_NO           VARCHAR2(20),
    END_NO             VARCHAR2(20),
    SOURCE_LOCATION    VARCHAR2(255),
    CHECK_METHOD       NUMBER,
    LAST_FAIL_BILL     VARCHAR2(50),
    DATA_SEND_TIME     DATE,
    DATA_ARRIVE_TIME   DATE,
    DATA_CHECK_TIME    DATE,
    DATA_SOURCE_NAME   VARCHAR2(255),
    DATA_ID            VARCHAR2(255) not null,
    BILL_STATE         NUMBER,
    BILL_TYPE          NUMBER,
    RESULT_DES         VARCHAR2(255),
    DATA_ADMINISTRATOR VARCHAR2(50),
    ADMINISTRATOR_TEL  VARCHAR2(20),
    DATA_SOURCE_TYPE   NUMBER,
    SERVICE_IP         VARCHAR2(20),
    SERVICE_NO         VARCHAR2(20),
    CREATE_TIME        DATE,
    DATA_DIRECTION     VARCHAR2(50) default '未知',
    JOB_NAME           VARCHAR2(255),
    USER_ID			 VARCHAR2(255),
    USER_NAME			 VARCHAR2(255)
)
PARTITION BY RANGE (DATA_SEND_TIME) INTERVAL('1 hour')
(partition part_001 VALUES less than(to_date('2021-11-26','yyyy-mm-dd')));

create index INDEX_AIB_ABN on DAC_ACCESS_INSIDE_BILL (ACCEPTOR_BILL_NO) local;
create index INDEX_AIB_BT on DAC_ACCESS_INSIDE_BILL (BILL_TYPE) local;
create index INDEX_AIB_DI on DAC_ACCESS_INSIDE_BILL (DATA_ID) local;
create index INDEX_AIB_PBN on DAC_ACCESS_INSIDE_BILL (PROVIDER_BILL_NO) local;
alter table DAC_ACCESS_INSIDE_BILL enable row movement;

create table DAC_ACCESS_STORAGE_INSIDE_BILL
(
    PROVIDER_BILL_NO   VARCHAR2(50),
    ACCEPTOR_BILL_NO   VARCHAR2(50),
    PROVIDER_NO        VARCHAR2(50),
    ACCEPTRO_NO        VARCHAR2(50),
    RESOURCE_ID        VARCHAR2(50),
    DATA_COUNT         NUMBER,
    DATA_FINGERPRINT   VARCHAR2(50),
    FINGERPRINT_TYPE   VARCHAR2(20),
    DATA_SIZE          VARCHAR2(20),
    START_NO           VARCHAR2(20),
    END_NO             VARCHAR2(20),
    SOURCE_LOCATION    VARCHAR2(255),
    CHECK_METHOD       NUMBER,
    LAST_FAIL_BILL     VARCHAR2(50),
    DATA_SEND_TIME     DATE,
    DATA_ARRIVE_TIME   DATE,
    DATA_CHECK_TIME    DATE,
    DATA_SOURCE_NAME   VARCHAR2(255),
    DATA_ID            VARCHAR2(255) not null,
    BILL_STATE         NUMBER,
    BILL_TYPE          NUMBER,
    RESULT_DES         VARCHAR2(255),
    DATA_ADMINISTRATOR VARCHAR2(50),
    ADMINISTRATOR_TEL  VARCHAR2(20),
    DATA_SOURCE_TYPE   NUMBER,
    SERVICE_IP         VARCHAR2(20),
    SERVICE_NO         VARCHAR2(20),
    CREATE_TIME        DATE,
    DATA_DIRECTION     VARCHAR2(50),
    JOB_NAME           VARCHAR2(255),
    IS_LOCAL           VARCHAR2(1) default '0',
    TABLE_NAME_EN      VARCHAR2(400),
    USER_ID			 VARCHAR2(255),
    USER_NAME			 VARCHAR2(255),
    SYN_INCEPT_DATA_TIME DATE,
    SYN_INCEPT_DATA_ID VARCHAR2(255)
)
PARTITION BY RANGE (DATA_ARRIVE_TIME) INTERVAL('1 hour')
(partition part_002 VALUES less than(to_date('2021-11-26 00:00:00','yyyy-mm-dd HH24:mi:ss')));
create index INDEX_ASIB_ABN on DAC_ACCESS_STORAGE_INSIDE_BILL (ACCEPTOR_BILL_NO) local;
create index INDEX_ASIB_BT on DAC_ACCESS_STORAGE_INSIDE_BILL (BILL_TYPE) local;
create index INDEX_ASIB_DI on DAC_ACCESS_STORAGE_INSIDE_BILL (DATA_ID) local;
create index INDEX_ASIB_PBN on DAC_ACCESS_STORAGE_INSIDE_BILL (PROVIDER_BILL_NO) local;
alter table DAC_ACCESS_STORAGE_INSIDE_BILL enable row movement;

create table DAC_ACCESS_BILL_STATISTICS
(
    ACCESS_SYSTEM        VARCHAR2(255) default sysdate,
    DATA_SOURCE          VARCHAR2(255),
    RESOURCE_ID          VARCHAR2(255),
    OUTSIDE_PROVIDE      NUMBER default 0,
    INSIDE_PROVIDE       NUMBER default 0,
    INSIDE_ACCEPT        NUMBER default 0,
    TACHE                NUMBER,
    DIRCTION             VARCHAR2(50),
    DATA_TIME            DATE,
    CREATETIME           DATE,
    OPERATION            NUMBER default 0,
    START_NO             VARCHAR2(50),
    IS_ISSUED            VARCHAR2(1) default '0',
    TABLE_NAME           VARCHAR2(400),
    JOB_NAME             VARCHAR2(255),
    IS_LOCAL             VARCHAR2(1) default '0',
    TABLE_NAME_EN        VARCHAR2(400),
    USER_ID              VARCHAR2(255),
    USER_NAME            VARCHAR2(255),
    SYN_INCEPT_DATA_TIME DATE
);

create table DAC_STANDARD_BILL_STATISTICS
(
    ACCESS_SYSTEM        VARCHAR2(255) default sysdate,
    DATA_SOURCE          VARCHAR2(255),
    RESOURCE_ID          VARCHAR2(255),
    INSIDE_PROVIDE       VARCHAR2(255),
    INSIDE_ACCEPT        VARCHAR2(255),
    TACHE                NUMBER,
    DIRCTION             VARCHAR2(50),
    DATA_TIME            DATE,
    CREATETIME           DATE,
    START_NO             VARCHAR2(255),
    SYS_CODE             VARCHAR2(255),
    DATA_SOURCE_CODE     VARCHAR2(255),
    TABLE_NAME_EN        VARCHAR2(400),
    USER_ID              VARCHAR2(255),
    USER_NAME            VARCHAR2(255),
    SYN_INCEPT_DATA_TIME DATE,
    OUTSIDE_ACCEPT       NUMBER default 0
);
-- Add comments to the columns
comment on column DAC_STANDARD_BILL_STATISTICS.ACCESS_SYSTEM
  is '接入系统';
comment on column DAC_STANDARD_BILL_STATISTICS.DATA_SOURCE
  is '数据来源';
comment on column DAC_STANDARD_BILL_STATISTICS.RESOURCE_ID
  is '协议编码';
comment on column DAC_STANDARD_BILL_STATISTICS.INSIDE_PROVIDE
  is '内部提供';
comment on column DAC_STANDARD_BILL_STATISTICS.INSIDE_ACCEPT
  is '内部接收';
comment on column DAC_STANDARD_BILL_STATISTICS.TACHE
  is '环节';
comment on column DAC_STANDARD_BILL_STATISTICS.DIRCTION
  is '去向';
comment on column DAC_STANDARD_BILL_STATISTICS.DATA_TIME
  is '数据时间';
comment on column DAC_STANDARD_BILL_STATISTICS.CREATETIME
  is '创建时间';
comment on column DAC_STANDARD_BILL_STATISTICS.START_NO
  is '数据开始时间';
comment on column DAC_STANDARD_BILL_STATISTICS.SYS_CODE
  is '系统代码';
comment on column DAC_STANDARD_BILL_STATISTICS.DATA_SOURCE_CODE
  is '厂商代码';
comment on column DAC_STANDARD_BILL_STATISTICS.TABLE_NAME_EN
  is '表名';
comment on column DAC_STANDARD_BILL_STATISTICS.USER_ID
  is '用户ID';
comment on column DAC_STANDARD_BILL_STATISTICS.USER_NAME
  is '用户名';
comment on column DAC_STANDARD_BILL_STATISTICS.SYN_INCEPT_DATA_TIME
  is 'ETL任务时间';
comment on column DAC_STANDARD_BILL_STATISTICS.OUTSIDE_ACCEPT
  is '外部接收';

create table DAC_BILL_ALARM_STATISTICS (
    DATA_NAME            VARCHAR2(255),
    ACCESS_INPUT         NUMBER,
    ACCESS_OUTPUT        NUMBER,
    ACCESS_SAME_PERIOD   VARCHAR2(20),
    STANDARD_INPUT       NUMBER,
    DATA_TIME            DATE,
    CREATE_TIME          DATE,
    RESOURCE_ID          VARCHAR2(255),
    ALARM_TYPE           VARCHAR2(12),
    STANDARD_OUTPUT      NUMBER,
    STANDARD_CHAIN_RATIO VARCHAR2(20),
    STORAGE_INPUT        NUMBER,
    STORAGE_OUTPUT       NUMBER,
    STORAGE_CHAIN_RATIO  VARCHAR2(20),
    DATA_DIRECTION       VARCHAR2(100),
    IS_PUSH              VARCHAR2(1),
    IS_SUMMARY           VARCHAR2(1),
    ACCESS_OUTPUT_PREV   NUMBER,
    ACCESS_INPUT_PREV    NUMBER,
    TABLE_NAME_EN        VARCHAR2(400),
    USER_ID              VARCHAR2(255),
    USER_NAME            VARCHAR2(255),
    IS_ISSUED            VARCHAR2(1) default '0'
)
partition by range (CREATE_TIME) INTERVAL('1 year')
(partition part_002 VALUES less than(to_date('2021-11-26 00:00:00','yyyy-mm-dd HH24:mi:ss')));

comment on table DAC_BILL_ALARM_STATISTICS
  is '数据对账异常告警信息';
comment on column DAC_BILL_ALARM_STATISTICS.DATA_NAME
  is '数据资源名称';
comment on column DAC_BILL_ALARM_STATISTICS.ACCESS_INPUT
  is '数据接入输入';
comment on column DAC_BILL_ALARM_STATISTICS.ACCESS_OUTPUT
  is '数据接入输出';
comment on column DAC_BILL_ALARM_STATISTICS.ACCESS_SAME_PERIOD
  is '数据接入输入同比';
comment on column DAC_BILL_ALARM_STATISTICS.STANDARD_INPUT
  is '数据处理输入';
comment on column DAC_BILL_ALARM_STATISTICS.DATA_TIME
  is '监控时间';
comment on column DAC_BILL_ALARM_STATISTICS.CREATE_TIME
  is '创建时间';
comment on column DAC_BILL_ALARM_STATISTICS.RESOURCE_ID
  is '数据资源ID';
comment on column DAC_BILL_ALARM_STATISTICS.ALARM_TYPE
  is '异常类型(1:数据接入异常;2:数据处理异常;3:数据入库异常,多个异常逗号拼接)';
comment on column DAC_BILL_ALARM_STATISTICS.STANDARD_OUTPUT
  is '数据处理输出';
comment on column DAC_BILL_ALARM_STATISTICS.STANDARD_CHAIN_RATIO
  is '数据处理环比';
comment on column DAC_BILL_ALARM_STATISTICS.STORAGE_INPUT
  is '数据入库输入';
comment on column DAC_BILL_ALARM_STATISTICS.STORAGE_OUTPUT
  is '数据入库输出';
comment on column DAC_BILL_ALARM_STATISTICS.STORAGE_CHAIN_RATIO
  is '数据入库环比';
comment on column DAC_BILL_ALARM_STATISTICS.DATA_DIRECTION
  is '数据入库去向';
comment on column DAC_BILL_ALARM_STATISTICS.IS_PUSH
  is '是否推送运维(1:是;0:否)';
comment on column DAC_BILL_ALARM_STATISTICS.IS_SUMMARY
  is '是否汇总数据(1:是;0:否)';
comment on column DAC_BILL_ALARM_STATISTICS.ACCESS_OUTPUT_PREV
  is '前一天数据接入输出';
comment on column DAC_BILL_ALARM_STATISTICS.ACCESS_INPUT_PREV
  is '前一天数据接入输入';
comment on column DAC_BILL_ALARM_STATISTICS.TABLE_NAME_EN
  is '项目名.表名';
comment on column DAC_BILL_ALARM_STATISTICS.USER_ID
  is '用户ID';
comment on column DAC_BILL_ALARM_STATISTICS.USER_NAME
  is '用户名';
comment on column DAC_BILL_ALARM_STATISTICS.IS_ISSUED
  is '是否下发数据';
-- Create/Recreate indexes
create index INDEX_ALARM_CREATE_TIME on DAC_BILL_ALARM_STATISTICS (CREATE_TIME) local;

-- Create table
create table BILL_STATISTIC_HISTORY
(
    RESOURCE_ID VARCHAR2(100),
    DIRECTION   VARCHAR2(50),
    DATA_TIME   DATE,
    TACHE       NUMBER,
    SYSTEM      NUMBER,
    STATUS      NUMBER,
    ID          VARCHAR2(50),
    CREATETIME  DATE
);
-- Add comments to the columns
comment on column BILL_STATISTIC_HISTORY.TACHE
  is '环节1.接入2、入库';
comment on column BILL_STATISTIC_HISTORY.SYSTEM
  is '1.etl 2.标准化';
comment on column BILL_STATISTIC_HISTORY.STATUS
  is '1.更新中2.更新完成,3.更新失败';

create table DAC_CITY_DIVISION_CODE
(
    CODE        NUMBER,
    NAME        VARCHAR2(30),
    DESCRIPTION VARCHAR2(64)
);

create table DAC_ACCEPTOR_BILL
(
    SJJRF_DZDBH   VARCHAR2(64) not null,
    SJTGF_DZDBH   VARCHAR2(64),
    SJJSWZ_JYQK   VARCHAR2(255),
    SJCCXSDM      NUMBER,
    SJ_DZWJMC     VARCHAR2(128),
    SJZYBSF       VARCHAR2(64),
    SJZYMC        VARCHAR2(64),
    BZSJXJBM      VARCHAR2(64),
    SJTS          NUMBER,
    SJ_DZWJDX     NUMBER,
    SJQSBH        VARCHAR2(64),
    SJJWBH        VARCHAR2(64),
    SJJYZ         VARCHAR2(64),
    SJJYSFMC      VARCHAR2(20),
    SCSJ_RQSJ     NUMBER,
    DZSJ_RQSJ     NUMBER,
    DZFFDM        NUMBER,
    DZDLXDM       NUMBER,
    DZDZTDM       NUMBER,
    DZJG_JYQK     VARCHAR2(255),
    SCSB_DZDBH    VARCHAR2(64),
    SJJRFGLY_XM   VARCHAR2(32),
    SJJRFGLY_LXDH VARCHAR2(32),
    XXRWBH        VARCHAR2(128),
    DZZDHJ        NUMBER,
    CREATE_TIME   DATE,
    UPDATE_TIME   DATE,
    IF_SEND_ALARM VARCHAR2(32),
    IF_SEND_OUT   VARCHAR2(32)
);
alter table DAC_ACCEPTOR_BILL add constraint DAC_DAB_PRI primary key (SJJRF_DZDBH);
create index DAC_DAB_IDX1 on DAC_ACCEPTOR_BILL (SJTGF_DZDBH);
create index DAC_DAB_IDX3 on DAC_ACCEPTOR_BILL (SCSJ_RQSJ);

comment on column DAC_ACCEPTOR_BILL.SJJRF_DZDBH
  is '数据接入方对账单编号';
comment on column DAC_ACCEPTOR_BILL.SJTGF_DZDBH
  is '数据提供方对账单编号';
comment on column DAC_ACCEPTOR_BILL.SJJSWZ_JYQK
  is '数据接收位置_简要情况';
comment on column DAC_ACCEPTOR_BILL.SJCCXSDM
  is '数据存储形式代码';
comment on column DAC_ACCEPTOR_BILL.SJ_DZWJMC
  is '数据电子文件名称';
comment on column DAC_ACCEPTOR_BILL.SJZYBSF
  is '数据资源标识符';
comment on column DAC_ACCEPTOR_BILL.SJZYMC
  is '数据资源名称';
comment on column DAC_ACCEPTOR_BILL.BZSJXJBM
  is '标准数据项集编码';
comment on column DAC_ACCEPTOR_BILL.SJTS
  is '数据条数';
comment on column DAC_ACCEPTOR_BILL.SJ_DZWJDX
  is '数据电子文件大小';
comment on column DAC_ACCEPTOR_BILL.SJQSBH
  is '数据起始编号';
comment on column DAC_ACCEPTOR_BILL.SJJWBH
  is '数据结尾编号';
comment on column DAC_ACCEPTOR_BILL.SJJYZ
  is '数据校验值';
comment on column DAC_ACCEPTOR_BILL.SJJYSFMC
  is '数据校验算法名称';
comment on column DAC_ACCEPTOR_BILL.SCSJ_RQSJ
  is '生成时间';
comment on column DAC_ACCEPTOR_BILL.DZSJ_RQSJ
  is '对账时间';
comment on column DAC_ACCEPTOR_BILL.DZFFDM
  is '对账方法代码';
comment on column DAC_ACCEPTOR_BILL.DZDLXDM
  is '对账单类型代码';
comment on column DAC_ACCEPTOR_BILL.DZDZTDM
  is '对账单状态代码';
comment on column DAC_ACCEPTOR_BILL.DZJG_JYQK
  is '对账结果简要情况';
comment on column DAC_ACCEPTOR_BILL.SCSB_DZDBH
  is '上次失败对账单编号';
comment on column DAC_ACCEPTOR_BILL.SJJRFGLY_XM
  is '数据接入方管理员姓名';
comment on column DAC_ACCEPTOR_BILL.SJJRFGLY_LXDH
  is '数据接入方管理员联系电话';
comment on column DAC_ACCEPTOR_BILL.XXRWBH
  is '消息任务编号';
comment on column DAC_ACCEPTOR_BILL.DZZDHJ
  is '对账账单环节（新加）';

create table DAC_ACCESS_ACCEPTOR_BILL
(
    SJJRF_DZDBH   VARCHAR2(64),
    SJTGF_DZDBH   VARCHAR2(64),
    SJJSWZ_JYQK   VARCHAR2(255),
    SJCCXSDM      NUMBER,
    SJ_DZWJMC     VARCHAR2(128),
    SJZYBSF       VARCHAR2(64),
    SJZYMC        VARCHAR2(64),
    BZSJXJBM      VARCHAR2(64),
    SJTS          NUMBER,
    SJ_DZWJDX     NUMBER,
    SJQSBH        VARCHAR2(64),
    SJJWBH        VARCHAR2(64),
    SJJYZ         VARCHAR2(64),
    SJJYSFMC      VARCHAR2(20),
    SCSJ_RQSJ     NUMBER,
    DZSJ_RQSJ     NUMBER,
    DZFFDM        NUMBER,
    DZDLXDM       NUMBER,
    DZDZTDM       NUMBER,
    DZJG_JYQK     VARCHAR2(255),
    SCSB_DZDBH    VARCHAR2(64),
    SJJRFGLY_XM   VARCHAR2(32),
    SJJRFGLY_LXDH VARCHAR2(32),
    XXRWBH        VARCHAR2(128),
    JOB_ID        VARCHAR2(128),
    BATCH_ID      VARCHAR2(128),
    ID            VARCHAR2(50),
    CREATE_TIME   DATE,
    UPDATE_TIME   DATE,
    DZZDHJ        NUMBER,
    SFYQ          VARCHAR2(50)
)
PARTITION BY RANGE(CREATE_TIME) INTERVAL('1 DAY')
(
  PARTITION DAC_ACCESS_ACCEPTOR_BILL_P0 VALUES LESS THAN (TO_DATE('2024-01-01','YYYY-MM-DD'))
);
create index DAC_DAACB_IDX1 on DAC_ACCESS_ACCEPTOR_BILL (SJJRF_DZDBH) LOCAL;
create index DAC_DAACB_IDX2 on DAC_ACCESS_ACCEPTOR_BILL (SJTGF_DZDBH) LOCAL;
comment on column DAC_ACCESS_ACCEPTOR_BILL.JOB_ID
  is '接入任务Id';
comment on column DAC_ACCESS_ACCEPTOR_BILL.BATCH_ID
  is '接入任务批次Id';
comment on column DAC_ACCESS_ACCEPTOR_BILL.DZZDHJ
  is '对账账单环节';
comment on column DAC_ACCESS_ACCEPTOR_BILL.SFYQ
  is '是否逾期';

create table DAC_ACCESS_PROVIDER_BILL (
    SJTGF_DZDBH   VARCHAR2(64),
    SJJSWZ_JYQK   VARCHAR2(255),
    SJCCXSDM      NUMBER,
    SJ_DZWJMC     VARCHAR2(128),
    SJZYBSF       VARCHAR2(64),
    SJZYMC        VARCHAR2(64),
    BZSJXJBM      VARCHAR2(64),
    SJTS          NUMBER,
    SJ_DZWJDX     NUMBER,
    SJQSBH        VARCHAR2(64),
    SJJWBH        VARCHAR2(64),
    SJJYZ         VARCHAR2(64),
    SJJYSFMC      VARCHAR2(20),
    SCSB_DZDBH    VARCHAR2(64),
    SCSJ_RQSJ     NUMBER,
    SJTGFGLY_XM   VARCHAR2(32),
    SJTGFGLY_LXDH VARCHAR2(32),
    XXRWBH        VARCHAR2(128),
    JOB_ID        VARCHAR2(128),
    BATCH_ID      VARCHAR2(128),
    ID            VARCHAR2(50),
    CREATE_TIME   DATE,
    UPDATE_TIME   DATE,
    DZZDHJ        NUMBER,
    SFYQ          VARCHAR2(50)
)
PARTITION BY RANGE(CREATE_TIME) INTERVAL('1 DAY')
(
  PARTITION DAC_ACCESS_PROVIDER_BILL_P0 VALUES LESS THAN (TO_DATE('2024-01-01','YYYY-MM-DD'))
);
create index DAC_DAPB_IDX1 on DAC_ACCESS_PROVIDER_BILL (SJTGF_DZDBH) LOCAL;

-- DAC_ACCESS_PROVIDER_BILL
comment on column DAC_ACCESS_PROVIDER_BILL.DZZDHJ
  is '对账账单环节';
comment on column DAC_ACCESS_PROVIDER_BILL.SFYQ
  is '是否逾期';

create table DAC_STANDARD_ACCEPTOR_BILL
(
    SJJRF_DZDBH   VARCHAR2(64),
    SJTGF_DZDBH   VARCHAR2(64),
    SJJSWZ_JYQK   VARCHAR2(255),
    SJCCXSDM      NUMBER,
    SJ_DZWJMC     VARCHAR2(128),
    SJZYBSF       VARCHAR2(64),
    SJZYMC        VARCHAR2(64),
    BZSJXJBM      VARCHAR2(64),
    SJTS          NUMBER,
    SJ_DZWJDX     NUMBER,
    SJQSBH        VARCHAR2(64),
    SJJWBH        VARCHAR2(64),
    SJJYZ         VARCHAR2(64),
    SJJYSFMC      VARCHAR2(20),
    SCSJ_RQSJ     NUMBER,
    DZSJ_RQSJ     NUMBER,
    DZFFDM        NUMBER,
    DZDLXDM       NUMBER,
    DZDZTDM       NUMBER,
    DZJG_JYQK     VARCHAR2(255),
    SCSB_DZDBH    VARCHAR2(64),
    SJJRFGLY_XM   VARCHAR2(32),
    SJJRFGLY_LXDH VARCHAR2(32),
    XXRWBH        VARCHAR2(128)
);
alter table DAC_STANDARD_ACCEPTOR_BILL add constraint DAC_SAB_PRI primary key (SJJRF_DZDBH);


create table DAC_STANDARD_PROVIDER_BILL (
    SJTGF_DZDBH   VARCHAR2(64),
    SJJSWZ_JYQK   VARCHAR2(255),
    SJCCXSDM      NUMBER,
    SJ_DZWJMC     VARCHAR2(128),
    SJZYBSF       VARCHAR2(64),
    SJZYMC        VARCHAR2(64),
    BZSJXJBM      VARCHAR2(64),
    SJTS          NUMBER,
    SJ_DZWJDX     NUMBER,
    SJQSBH        VARCHAR2(64),
    SJJWBH        VARCHAR2(64),
    SJJYZ         VARCHAR2(64),
    SJJYSFMC      VARCHAR2(20),
    SCSB_DZDBH    VARCHAR2(64),
    SCSJ_RQSJ     NUMBER,
    SJTGFGLY_XM   VARCHAR2(32),
    SJTGFGLY_LXDH VARCHAR2(32),
    XXRWBH        VARCHAR2(128)
);
alter table DAC_STANDARD_PROVIDER_BILL add constraint DAC_SPB_PRI primary key (SJTGF_DZDBH);

create table DAC_STORAGE_ACCEPTOR_BILL
(
    SJJRF_DZDBH   VARCHAR2(64),
    SJTGF_DZDBH   VARCHAR2(64),
    SJJSWZ_JYQK   VARCHAR2(255),
    SJCCXSDM      NUMBER,
    SJ_DZWJMC     VARCHAR2(128),
    SJZYBSF       VARCHAR2(64),
    SJZYMC        VARCHAR2(64),
    BZSJXJBM      VARCHAR2(64),
    SJTS          NUMBER,
    SJ_DZWJDX     NUMBER,
    SJQSBH        VARCHAR2(64),
    SJJWBH        VARCHAR2(64),
    SJJYZ         VARCHAR2(64),
    SJJYSFMC      VARCHAR2(20),
    SCSJ_RQSJ     NUMBER,
    DZSJ_RQSJ     NUMBER,
    DZFFDM        NUMBER,
    DZDLXDM       NUMBER,
    DZDZTDM       NUMBER,
    DZJG_JYQK     VARCHAR2(255),
    SCSB_DZDBH    VARCHAR2(64),
    SJJRFGLY_XM   VARCHAR2(32),
    SJJRFGLY_LXDH VARCHAR2(32),
    XXRWBH        VARCHAR2(128)
);
alter table DAC_STORAGE_ACCEPTOR_BILL add constraint DAC_SAB2_PRI primary key (SJJRF_DZDBH);


CREATE TABLE DAC_THIRD_ACCEPTOR_BILL
(
    BATCH_ID			  VARCHAR2(32),
    DATA_NO	   		  VARCHAR2(10),
    DATA_NAME    		  VARCHAR2(400),
    RESOURCE_ID		  VARCHAR2(20),
    DATA_TIME    		  DATE,
    IP_ADDRESS		  VARCHAR2(20),
    MAC_ADDRESS  		  VARCHAR2(20),
    SEND_NUM            NUMBER,
    SEND_SIZE           NUMBER,
    RECEIVE_NUM         NUMBER,
    RECEIVE_SIZE        NUMBER,
    RECEIVE_FAIL_NUM    NUMBER,
    RECEIVE_FAIL_SIZE   NUMBER
)
PARTITION BY RANGE (DATA_TIME) INTERVAL('1 month')(partition part_001 VALUES less than(to_date('2021-11-26','yyyy-mm-dd')));

comment on column DAC_THIRD_ACCEPTOR_BILL.BATCH_ID is '批次ID';
comment on column DAC_THIRD_ACCEPTOR_BILL.DATA_NO is '包序号';
comment on column DAC_THIRD_ACCEPTOR_BILL.DATA_NAME is '接入数据名';
comment on column DAC_THIRD_ACCEPTOR_BILL.RESOURCE_ID is '资源标识';
comment on column DAC_THIRD_ACCEPTOR_BILL.DATA_TIME is '时间';
comment on column DAC_THIRD_ACCEPTOR_BILL.IP_ADDRESS is 'IP地址';
comment on column DAC_THIRD_ACCEPTOR_BILL.MAC_ADDRESS is '推送节点(MAC地址)';
comment on column DAC_THIRD_ACCEPTOR_BILL.SEND_NUM is '分发数据量';
comment on column DAC_THIRD_ACCEPTOR_BILL.SEND_SIZE is '分发数据大小';
comment on column DAC_THIRD_ACCEPTOR_BILL.RECEIVE_NUM is '接收成功数据量';
comment on column DAC_THIRD_ACCEPTOR_BILL.RECEIVE_SIZE is '接收成功数据大小';
comment on column DAC_THIRD_ACCEPTOR_BILL.RECEIVE_FAIL_NUM is '接收失败数据量';
comment on column DAC_THIRD_ACCEPTOR_BILL.RECEIVE_FAIL_SIZE is '接收失败数据大小';

CREATE TABLE DAC_RECONCILIATION_ANALYSIS (
    RRID NUMBER(38),
    RESOURCE_ID VARCHAR2(128 BYTE),
    RESOURCE_NAME VARCHAR2(256 BYTE),
    LINK_TYPE NUMBER(1),
    BILL_TYPE NUMBER(1),
    CHECK_TIME DATE,
    SUM_COUNT NUMBER(38),
    SUCCESS_COUNT NUMBER(38),
    SUCCESS_PERCENT NUMBER(4,1),
    FAILD_COUNT NUMBER(38),
    CREATE_TIME DATE,
    UPDATE_TIME DATE
)
PARTITION BY RANGE(CHECK_TIME) INTERVAL('1 month')
(partition p_before_2021 values less than (to_date('2022-01-01','YYYY-MM-DD'))) enable row movement;

create table DAC_BILL_LINK_STATISTIC (
    RESOURCE_ID          VARCHAR2(128),
    RESOURCE_NAME        VARCHAR2(128),
    ACCESS_ACCEPTOR      NUMBER,
    ACCESS_PROVIDER      NUMBER,
    STANDARD_ACCEPTOR    NUMBER,
    STANDARD_PROVIDER    NUMBER,
    STORAGE_ACCEPTOR     NUMBER,
    STORAGE_PROVIDER     NUMBER,
    ACCESS_SAME_PERIOD   VARCHAR2(32),
    STANDARD_CHAIN_RATIO VARCHAR2(32),
    STORAGE_CHAIN_RATIO  VARCHAR2(32),
    DATA_TIME            DATE,
    STATISTIC_TIME       DATE,
    ALARM_STATE          NUMBER,
    IS_PUSH              NUMBER,
    ACCESS_ACCEPTOR_PRE  NUMBER,
    ACCESS_PROVIDER_PRE  NUMBER
);
-- Add comments to the columns
comment on column DAC_BILL_LINK_STATISTIC.RESOURCE_ID
  is '资源ID';
comment on column DAC_BILL_LINK_STATISTIC.RESOURCE_NAME
  is '资源名称';
comment on column DAC_BILL_LINK_STATISTIC.ACCESS_ACCEPTOR
  is '接入环节接入数据量';
comment on column DAC_BILL_LINK_STATISTIC.ACCESS_PROVIDER
  is '接入环节提供数据量';
comment on column DAC_BILL_LINK_STATISTIC.STANDARD_ACCEPTOR
  is '处理环节接入数据量';
comment on column DAC_BILL_LINK_STATISTIC.STANDARD_PROVIDER
  is '处理环节提供数据量';
comment on column DAC_BILL_LINK_STATISTIC.STORAGE_ACCEPTOR
  is '入库环节接入数据量';
comment on column DAC_BILL_LINK_STATISTIC.STORAGE_PROVIDER
  is '入库环节提供数据量';
comment on column DAC_BILL_LINK_STATISTIC.ACCESS_SAME_PERIOD
  is '接入同比（与前一天接入数据量比）';
comment on column DAC_BILL_LINK_STATISTIC.STANDARD_CHAIN_RATIO
  is '处理环节（与接入环节数据量比）';
comment on column DAC_BILL_LINK_STATISTIC.STORAGE_CHAIN_RATIO
  is '入库环节（与处理环节数据量比）';
comment on column DAC_BILL_LINK_STATISTIC.DATA_TIME
  is '数据时间';
comment on column DAC_BILL_LINK_STATISTIC.STATISTIC_TIME
  is '统计时间';
comment on column DAC_BILL_LINK_STATISTIC.ALARM_STATE
  is '1.正常 2.异常';
comment on column DAC_BILL_LINK_STATISTIC.IS_PUSH
  is '是否推送运维（1.是2否）';
comment on column DAC_BILL_LINK_STATISTIC.ACCESS_ACCEPTOR_PRE
  is '前一天接入环节接入数据量';
comment on column DAC_BILL_LINK_STATISTIC.ACCESS_PROVIDER_PRE
  is '前一天接入环节提供数据量';

create table DAC_PROVIDER_BILL
(
    SJTGF_DZDBH   VARCHAR2(64) not null,
    SJJSWZ_JYQK   VARCHAR2(255),
    SJCCXSDM      NUMBER,
    SJ_DZWJMC     VARCHAR2(128),
    SJZYBSF       VARCHAR2(64),
    SJZYMC        VARCHAR2(64),
    BZSJXJBM      VARCHAR2(64),
    SJTS          NUMBER,
    SJ_DZWJDX     NUMBER,
    SJQSBH        VARCHAR2(64),
    SJJWBH        VARCHAR2(64),
    SJJYZ         VARCHAR2(64),
    SJJYSFMC      VARCHAR2(20),
    SCSB_DZDBH    VARCHAR2(64),
    SCSJ_RQSJ     NUMBER,
    SJTGFGLY_XM   VARCHAR2(32),
    SJTGFGLY_LXDH VARCHAR2(32),
    XXRWBH        VARCHAR2(128),
    DZZDHJ        NUMBER,
    CREATE_TIME   DATE,
    UPDATE_TIME   DATE,
    SFYQ          VARCHAR2(50)
);
create index DAC_DPB_IDX1 on DAC_PROVIDER_BILL (SCSJ_RQSJ);
create index DAC_DPB_IDX2 on DAC_PROVIDER_BILL (SJZYBSF);
alter table DAC_PROVIDER_BILL add constraint DAC_DPB_PRI primary key (SJTGF_DZDBH);
-- DAC_PROVIDER_BILL
comment on column DAC_PROVIDER_BILL.SJTGF_DZDBH
  is '数据提供方_对账单编号';
comment on column DAC_PROVIDER_BILL.SJJSWZ_JYQK
  is '数据接收位置_简要情况';
comment on column DAC_PROVIDER_BILL.SJCCXSDM
  is '数据存储形式代码';
comment on column DAC_PROVIDER_BILL.SJ_DZWJMC
  is '数据_电子文件名称';
comment on column DAC_PROVIDER_BILL.SJZYBSF
  is '数据资源标识符';
comment on column DAC_PROVIDER_BILL.SJZYMC
  is '数据资源名称';
comment on column DAC_PROVIDER_BILL.BZSJXJBM
  is '标准数据项集编码';
comment on column DAC_PROVIDER_BILL.SJTS
  is '数据条数';
comment on column DAC_PROVIDER_BILL.SJ_DZWJDX
  is '数据_电子文件大小';
comment on column DAC_PROVIDER_BILL.SJQSBH
  is '数据起始位置';
comment on column DAC_PROVIDER_BILL.SJJWBH
  is '数据结尾位置';
comment on column DAC_PROVIDER_BILL.SJJYZ
  is '数据校验值';
comment on column DAC_PROVIDER_BILL.SJJYSFMC
  is '数据校验算法名称';
comment on column DAC_PROVIDER_BILL.SCSB_DZDBH
  is '上次失败_对账单编号';
comment on column DAC_PROVIDER_BILL.SCSJ_RQSJ
  is '生成时间_日期时间';
comment on column DAC_PROVIDER_BILL.SJTGFGLY_XM
  is '数据提供管理员_姓名';
comment on column DAC_PROVIDER_BILL.SJTGFGLY_LXDH
  is '数据提供管理员_联系电话';
comment on column DAC_PROVIDER_BILL.XXRWBH
  is '消息任务编号';
comment on column DAC_PROVIDER_BILL.DZZDHJ
  is '对账账单环节（新加）';

create table DAC_ACCEPTOR_INVENTORY_BILL
(
    SJJRF_DZDBH   VARCHAR2(64),
    SJTGF_DZDBH   VARCHAR2(64),
    SJZYBSF       VARCHAR2(64),
    SJZYMC        VARCHAR2(64),
    BZSJXJBM      VARCHAR2(64),
    SJTS          NUMBER,
    SJ_DZWJDX     NUMBER,
    SJQSBH        VARCHAR2(32),
    SJJWBH        VARCHAR2(32),
    SJJYZ         VARCHAR2(64),
    SJJYSFMC      VARCHAR2(20),
    SCSJ_RQSJ     NUMBER,
    DZSJ_RQSJ     NUMBER,
    DZFFDM        NUMBER,
    DZDLXDM       NUMBER,
    DZDZTDM       NUMBER,
    DZJG_JYQK     VARCHAR2(255),
    SCSB_DZDBH    VARCHAR2(64),
    SJJRFGLY_XM   VARCHAR2(32),
    SJJRFGLY_LXDH VARCHAR2(32),
    DZYQSJFW_KSSJ NUMBER,
    DZYQSJFW_JSSJ NUMBER,
    DZZDHJ        NUMBER
);
-- Add comments to the columns
comment on column DAC_ACCEPTOR_INVENTORY_BILL.SJJRF_DZDBH
  is '数据接入方对账单编号';
comment on column DAC_ACCEPTOR_INVENTORY_BILL.SJTGF_DZDBH
  is '数据提供方对账单编号';
comment on column DAC_ACCEPTOR_INVENTORY_BILL.SJZYBSF
  is '数据资源标识符';
comment on column DAC_ACCEPTOR_INVENTORY_BILL.SJZYMC
  is '数据资源名称';
comment on column DAC_ACCEPTOR_INVENTORY_BILL.BZSJXJBM
  is '标准数据项集编码';
comment on column DAC_ACCEPTOR_INVENTORY_BILL.SJTS
  is '数据条数';
comment on column DAC_ACCEPTOR_INVENTORY_BILL.SJ_DZWJDX
  is '数据电子文件大小';
comment on column DAC_ACCEPTOR_INVENTORY_BILL.SJQSBH
  is '数据起始编号';
comment on column DAC_ACCEPTOR_INVENTORY_BILL.SJJWBH
  is '数据结尾编号';
comment on column DAC_ACCEPTOR_INVENTORY_BILL.SJJYZ
  is '数据校验值';
comment on column DAC_ACCEPTOR_INVENTORY_BILL.SJJYSFMC
  is '数据校验算法名称';
comment on column DAC_ACCEPTOR_INVENTORY_BILL.SCSJ_RQSJ
  is '生成时间';
comment on column DAC_ACCEPTOR_INVENTORY_BILL.DZSJ_RQSJ
  is '对账时间';
comment on column DAC_ACCEPTOR_INVENTORY_BILL.DZFFDM
  is '对账方法代码';
comment on column DAC_ACCEPTOR_INVENTORY_BILL.DZDLXDM
  is '对账单类型代码 1.接入2.标准化3.入库';
comment on column DAC_ACCEPTOR_INVENTORY_BILL.DZDZTDM
  is '对账单状态代码1.成功2.失败';
comment on column DAC_ACCEPTOR_INVENTORY_BILL.DZJG_JYQK
  is '对账结果';
comment on column DAC_ACCEPTOR_INVENTORY_BILL.SCSB_DZDBH
  is '上次失败对账单编号';
comment on column DAC_ACCEPTOR_INVENTORY_BILL.SJJRFGLY_XM
  is '数据接入方管理员姓名';
comment on column DAC_ACCEPTOR_INVENTORY_BILL.SJJRFGLY_LXDH
  is '数据接入方管理员联系电话';
comment on column DAC_ACCEPTOR_INVENTORY_BILL.DZYQSJFW_KSSJ
  is '对账要求时间范围开始时间';
comment on column DAC_ACCEPTOR_INVENTORY_BILL.DZYQSJFW_JSSJ
  is '对账要求时间范围结束时间';
comment on column DAC_ACCEPTOR_INVENTORY_BILL.DZZDHJ
  is '对账账单环节（新加）';

create table DAC_PROVIDER_INVENTORY_BILL
(
    SJTGF_DZDBH   VARCHAR2(64),
    SJZYBSF       VARCHAR2(64),
    SJZYMC        VARCHAR2(64),
    BZSJXJBM      VARCHAR2(64),
    SJTS          NUMBER,
    SJ_DZWJDX     NUMBER,
    SJQSBH        VARCHAR2(32),
    SJJWBH        VARCHAR2(32),
    SJJYZ         VARCHAR2(64),
    SJJYSFMC      VARCHAR2(20),
    SCSJ_RQSJ     NUMBER,
    SCSB_DZDBH    VARCHAR2(64),
    SJTGFGLY_XM   VARCHAR2(32),
    SJTGFGLY_LXDH VARCHAR2(32),
    DZYQSJFW_KSSJ NUMBER,
    DZYQSJFW_JSSJ NUMBER,
    DZZDHJ        NUMBER
);
-- Add comments to the columns
comment on column DAC_PROVIDER_INVENTORY_BILL.SJTGF_DZDBH
  is '数据提供方对账单编号';
comment on column DAC_PROVIDER_INVENTORY_BILL.SJZYBSF
  is '数据资源标识符';
comment on column DAC_PROVIDER_INVENTORY_BILL.SJZYMC
  is '数据资源名称';
comment on column DAC_PROVIDER_INVENTORY_BILL.BZSJXJBM
  is '标准数据项集编码';
comment on column DAC_PROVIDER_INVENTORY_BILL.SJTS
  is '数据条数';
comment on column DAC_PROVIDER_INVENTORY_BILL.SJ_DZWJDX
  is '数据电子文件大小';
comment on column DAC_PROVIDER_INVENTORY_BILL.SJQSBH
  is '数据起始编号';
comment on column DAC_PROVIDER_INVENTORY_BILL.SJJWBH
  is '数据结尾编号';
comment on column DAC_PROVIDER_INVENTORY_BILL.SJJYZ
  is '数据校验值';
comment on column DAC_PROVIDER_INVENTORY_BILL.SJJYSFMC
  is '数据校验算法名称';
comment on column DAC_PROVIDER_INVENTORY_BILL.SCSJ_RQSJ
  is '生成日期';
comment on column DAC_PROVIDER_INVENTORY_BILL.SCSB_DZDBH
  is '上次失败对账单编号';
comment on column DAC_PROVIDER_INVENTORY_BILL.SJTGFGLY_XM
  is '数据提供方管理员姓名';
comment on column DAC_PROVIDER_INVENTORY_BILL.SJTGFGLY_LXDH
  is '数据提供方管理员联系电话';
comment on column DAC_PROVIDER_INVENTORY_BILL.DZYQSJFW_KSSJ
  is '对账要求时间范围开始时间';
comment on column DAC_PROVIDER_INVENTORY_BILL.DZYQSJFW_JSSJ
  is '对账要求时间范围开始时间';
comment on column DAC_PROVIDER_INVENTORY_BILL.DZZDHJ
  is '对账账单环节（新加）';


create table DAC_ISSUE_DAY_STATISTICS
(
    JOB_ID                VARCHAR2(64),
    CHECK_TIME            DATE,
    DATA_NAME_ZH          VARCHAR2(255),
    DATA_NAME_EN          VARCHAR2(255),
    IS_PARTITION          NUMBER,
    ACCEPT_CITY           VARCHAR2(64),
    EXCHANGE_POSITION     VARCHAR2(255),
    SOURCE_COUNT          NUMBER,
    PROVIDER_READ_COUNT   NUMBER,
    PROVIDER_WRITE_COUNT  NUMBER,
    ACCEPTOR_READ_COUNT   NUMBER,
    ACCEPTOR_WRITE_COUNT  NUMBER,
    DEST_COUNT            NUMBER,
    COURSE_CHAIN_RATIO    VARCHAR2(16),
    HISTORICAL_COMPARISON VARCHAR2(16),
    PROVIDER_START_TIME   DATE,
    PROVIDER_END_TIME     DATE,
    ACCEPTOR_START_TIME   DATE,
    ACCEPTOR_END_TIME     DATE,
    PROVIDER_DURATION     NUMBER,
    ACCEPTOR_DURATION     NUMBER,
    ALL_DURATION          NUMBER,
    TASK_STATE            VARCHAR2(8 CHAR),
    UPDATE_RESULT         NUMBER,
    UPDATE_RESULT_REASON  VARCHAR2(255),
    BASE_TIME             VARCHAR2(64),
    BASE_TIME_ENABLE      NUMBER,
    ID                    NUMBER not null,
    EXCEPTION_FIELD       VARCHAR2(255)
);
-- Add comments to the columns
comment on column DAC_ISSUE_DAY_STATISTICS.JOB_ID
  is '同一任务的唯一标识';
comment on column DAC_ISSUE_DAY_STATISTICS.CHECK_TIME
  is '统计账单核账日期';
comment on column DAC_ISSUE_DAY_STATISTICS.DATA_NAME_ZH
  is '数据中文名称';
comment on column DAC_ISSUE_DAY_STATISTICS.DATA_NAME_EN
  is '数据英文名称';
comment on column DAC_ISSUE_DAY_STATISTICS.IS_PARTITION
  is '表类型 是否分区表 0非分区表1分区表';
comment on column DAC_ISSUE_DAY_STATISTICS.ACCEPT_CITY
  is '数据推送的目标地市';
comment on column DAC_ISSUE_DAY_STATISTICS.EXCHANGE_POSITION
  is '数据推送位置，是总队和地市核账的关联标识';
comment on column DAC_ISSUE_DAY_STATISTICS.SOURCE_COUNT
  is '源表数据量';
comment on column DAC_ISSUE_DAY_STATISTICS.PROVIDER_READ_COUNT
  is '总队读出数据量';
comment on column DAC_ISSUE_DAY_STATISTICS.PROVIDER_WRITE_COUNT
  is '总队推送数据量';
comment on column DAC_ISSUE_DAY_STATISTICS.ACCEPTOR_READ_COUNT
  is '地市接收数据量';
comment on column DAC_ISSUE_DAY_STATISTICS.ACCEPTOR_WRITE_COUNT
  is '地市入库数据量';
comment on column DAC_ISSUE_DAY_STATISTICS.DEST_COUNT
  is '目标表数据量';
comment on column DAC_ISSUE_DAY_STATISTICS.COURSE_CHAIN_RATIO
  is '历程环比';
comment on column DAC_ISSUE_DAY_STATISTICS.HISTORICAL_COMPARISON
  is '历史同比';
comment on column DAC_ISSUE_DAY_STATISTICS.PROVIDER_START_TIME
  is '总队开始读出时间';
comment on column DAC_ISSUE_DAY_STATISTICS.PROVIDER_END_TIME
  is '总队推送完成时间';
comment on column DAC_ISSUE_DAY_STATISTICS.ACCEPTOR_START_TIME
  is '地市开始接收时间';
comment on column DAC_ISSUE_DAY_STATISTICS.ACCEPTOR_END_TIME
  is '地市入库完成时间';
comment on column DAC_ISSUE_DAY_STATISTICS.PROVIDER_DURATION
  is '总队推送时长';
comment on column DAC_ISSUE_DAY_STATISTICS.ACCEPTOR_DURATION
  is '地市入库时长';
comment on column DAC_ISSUE_DAY_STATISTICS.ALL_DURATION
  is '总时长';
comment on column DAC_ISSUE_DAY_STATISTICS.TASK_STATE
  is '任务状态';
comment on column DAC_ISSUE_DAY_STATISTICS.UPDATE_RESULT
  is '更新结果 0异常 1正常';
comment on column DAC_ISSUE_DAY_STATISTICS.UPDATE_RESULT_REASON
  is '更新结果原因';
comment on column DAC_ISSUE_DAY_STATISTICS.BASE_TIME
  is '基线时间';
comment on column DAC_ISSUE_DAY_STATISTICS.BASE_TIME_ENABLE
  is '基线时间是否启用';
comment on column DAC_ISSUE_DAY_STATISTICS.ID
  is '主键id';
comment on column DAC_ISSUE_DAY_STATISTICS.EXCEPTION_FIELD
  is '异常字段';


create table DAC_ISSUE_BASE_TIME
(
    JOB_ID           VARCHAR2(64),
    DATA_NAME_EN     VARCHAR2(64),
    BASE_TIME        VARCHAR2(16),
    BASE_TIME_ENABLE NUMBER
);
comment on column DAC_ISSUE_BASE_TIME.JOB_ID
  is '同一任务的唯一标识';
comment on column DAC_ISSUE_BASE_TIME.DATA_NAME_EN
  is '数据表名- 数据英文名称；';
comment on column DAC_ISSUE_BASE_TIME.BASE_TIME
  is '基线时间：由用户自主设置的基线时间，意为用户允许地市入库完成的最迟时间；';
comment on column DAC_ISSUE_BASE_TIME.BASE_TIME_ENABLE
  is '是否启用 0禁用1启用';


create table DAC_ISSUE_PROVIDER_BILL
(
    SJTGF_DZDBH   VARCHAR2(64),
    SJJSWZ_JYQK   VARCHAR2(255),
    SJCCXSDM      NUMBER,
    SJ_DZWJMC     VARCHAR2(128),
    SJZYBSF       VARCHAR2(64),
    SJZYMC        VARCHAR2(64),
    BZSJXJBM      VARCHAR2(64),
    SJTS          NUMBER,
    SJ_DZWJDX     NUMBER,
    SJQSBH        VARCHAR2(64),
    SJJWBH        VARCHAR2(64),
    SJJYZ         VARCHAR2(64),
    SJJYSFMC      VARCHAR2(20),
    SCSB_DZDBH    VARCHAR2(64),
    SCSJ_RQSJ     NUMBER,
    SJTGFGLY_XM   VARCHAR2(32),
    SJTGFGLY_LXDH VARCHAR2(32),
    XXRWBH        VARCHAR2(128),
    JOB_ID        VARCHAR2(128),
    BATCH_ID      VARCHAR2(128),
    ID            VARCHAR2(50),
    CREATE_TIME   DATE,
    UPDATE_TIME   DATE,
    DZZDHJ        NUMBER,
    SFYQ          VARCHAR2(50)
);

create table DAC_ISSUE_ACCEPTOR_BILL
(
    SJJRF_DZDBH   VARCHAR2(64),
    SJTGF_DZDBH   VARCHAR2(64),
    SJJSWZ_JYQK   VARCHAR2(255),
    SJCCXSDM      NUMBER,
    SJ_DZWJMC     VARCHAR2(128),
    SJZYBSF       VARCHAR2(64),
    SJZYMC        VARCHAR2(64),
    BZSJXJBM      VARCHAR2(64),
    SJTS          NUMBER,
    SJ_DZWJDX     NUMBER,
    SJQSBH        VARCHAR2(64),
    SJJWBH        VARCHAR2(64),
    SJJYZ         VARCHAR2(64),
    SJJYSFMC      VARCHAR2(20),
    SCSJ_RQSJ     NUMBER,
    DZSJ_RQSJ     NUMBER,
    DZFFDM        NUMBER,
    DZDLXDM       NUMBER,
    DZDZTDM       NUMBER,
    DZJG_JYQK     VARCHAR2(255),
    SCSB_DZDBH    VARCHAR2(64),
    SJJRFGLY_XM   VARCHAR2(32),
    SJJRFGLY_LXDH VARCHAR2(32),
    XXRWBH        VARCHAR2(128),
    JOB_ID        VARCHAR2(128),
    BATCH_ID      VARCHAR2(128),
    ID            VARCHAR2(50),
    CREATE_TIME   DATE,
    UPDATE_TIME   DATE,
    DZZDHJ        NUMBER,
    SFYQ          VARCHAR2(50)
);

create table DAC_RELATE_JOB
(
    JOB_ID          VARCHAR2(64),
    BATCH_ID        VARCHAR2(255),
    STATUS          VARCHAR2(16),
    DATA_START_TIME DATE,
    DATA_END_TIME   DATE,
    CREATE_TIME     DATE,
    LOCAL           VARCHAR2(64)
);

create table DAC_RELATE_TAG
(
    TYPE NUMBER,
    TAG  VARCHAR2(128)
);





