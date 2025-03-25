create table DAC_STORAGE_PROVIDER_BILL(
    SJTGF_DZDBH   VARCHAR(64),
    SJJSWZ_JYQK   VARCHAR(255),
    SJCCXSDM      NUMERIC,
    SJ_DZWJMC     VARCHAR(128),
    SJZYBSF       VARCHAR(64),
    SJZYMC        VARCHAR(64),
    BZSJXJBM      VARCHAR(64),
    SJTS          NUMERIC,
    SJ_DZWJDX     NUMERIC,
    SJQSBH        VARCHAR(64),
    SJJWBH        VARCHAR(64),
    SJJYZ         VARCHAR(64),
    SJJYSFMC      VARCHAR(20),
    SCSB_DZDBH    VARCHAR(64),
    SCSJ_RQSJ     NUMERIC,
    SJTGFGLY_XM   VARCHAR(32),
    SJTGFGLY_LXDH VARCHAR(32),
    XXRWBH        VARCHAR(128)
);
alter table DAC_STORAGE_PROVIDER_BILL add constraint DAC_SSPB_PRI primary key (SJTGF_DZDBH);


create table DAC_ACCESS_INSIDE_BILL
(
    PROVIDER_BILL_NO   VARCHAR(50) not null,
    ACCEPTOR_BILL_NO   VARCHAR(50) not null,
    PROVIDER_NO        VARCHAR(50),
    ACCEPTRO_NO        VARCHAR(50),
    RESOURCE_ID        VARCHAR(50),
    DATA_COUNT         NUMERIC,
    DATA_FINGERPRINT   VARCHAR(50),
    FINGERPRINT_TYPE   VARCHAR(20),
    DATA_SIZE          VARCHAR(20),
    START_NO           VARCHAR(20),
    END_NO             VARCHAR(20),
    SOURCE_LOCATION    VARCHAR(255),
    CHECK_METHOD       NUMERIC,
    LAST_FAIL_BILL     VARCHAR(50),
    DATA_SEND_TIME     TIMESTAMP,
    DATA_ARRIVE_TIME   TIMESTAMP,
    DATA_CHECK_TIME    TIMESTAMP,
    DATA_SOURCE_NAME   VARCHAR(255),
    DATA_ID            VARCHAR(255) not null,
    BILL_STATE         NUMERIC,
    BILL_TYPE          NUMERIC,
    RESULT_DES         VARCHAR(255),
    DATA_ADMINISTRATOR VARCHAR(50),
    ADMINISTRATOR_TEL  VARCHAR(20),
    DATA_SOURCE_TYPE   NUMERIC,
    SERVICE_IP         VARCHAR(20),
    SERVICE_NO         VARCHAR(20),
    CREATE_TIME        TIMESTAMP,
    DATA_DIRECTION     VARCHAR(50) default '未知',
    JOB_NAME           VARCHAR(255),
    USER_ID			 VARCHAR(255),
    USER_NAME			 VARCHAR(255)
);

create table DAC_ACCESS_STORAGE_INSIDE_BILL
(
    PROVIDER_BILL_NO   VARCHAR(50),
    ACCEPTOR_BILL_NO   VARCHAR(50),
    PROVIDER_NO        VARCHAR(50),
    ACCEPTRO_NO        VARCHAR(50),
    RESOURCE_ID        VARCHAR(50),
    DATA_COUNT         NUMERIC,
    DATA_FINGERPRINT   VARCHAR(50),
    FINGERPRINT_TYPE   VARCHAR(20),
    DATA_SIZE          VARCHAR(20),
    START_NO           VARCHAR(20),
    END_NO             VARCHAR(20),
    SOURCE_LOCATION    VARCHAR(255),
    CHECK_METHOD       NUMERIC,
    LAST_FAIL_BILL     VARCHAR(50),
    DATA_SEND_TIME     TIMESTAMP,
    DATA_ARRIVE_TIME   TIMESTAMP,
    DATA_CHECK_TIME    TIMESTAMP,
    DATA_SOURCE_NAME   VARCHAR(255),
    DATA_ID            VARCHAR(255) not null,
    BILL_STATE         NUMERIC,
    BILL_TYPE          NUMERIC,
    RESULT_DES         VARCHAR(255),
    DATA_ADMINISTRATOR VARCHAR(50),
    ADMINISTRATOR_TEL  VARCHAR(20),
    DATA_SOURCE_TYPE   NUMERIC,
    SERVICE_IP         VARCHAR(20),
    SERVICE_NO         VARCHAR(20),
    CREATE_TIME        TIMESTAMP,
    DATA_DIRECTION     VARCHAR(50),
    JOB_NAME           VARCHAR(255),
    IS_LOCAL           VARCHAR(1) default '0',
    TABLE_NAME_EN      VARCHAR(400),
    USER_ID			 VARCHAR(255),
    USER_NAME			 VARCHAR(255),
    SYN_INCEPT_DATA_TIME TIMESTAMP,
    SYN_INCEPT_DATA_ID VARCHAR(255)
);

create table DAC_ACCESS_BILL_STATISTICS
(
    ACCESS_SYSTEM        VARCHAR(255) default CURRENT_DATE,
    DATA_SOURCE          VARCHAR(255),
    RESOURCE_ID          VARCHAR(255),
    OUTSIDE_PROVIDE      NUMERIC default 0,
    INSIDE_PROVIDE       NUMERIC default 0,
    INSIDE_ACCEPT        NUMERIC default 0,
    TACHE                NUMERIC,
    DIRCTION             VARCHAR(50),
    DATA_TIME            TIMESTAMP,
    CREATETIME           TIMESTAMP,
    OPERATION            NUMERIC default 0,
    START_NO             VARCHAR(50),
    IS_ISSUED            VARCHAR(1) default '0',
    TABLE_NAME           VARCHAR(400),
    JOB_NAME             VARCHAR(255),
    IS_LOCAL             VARCHAR(1) default '0',
    TABLE_NAME_EN        VARCHAR(400),
    USER_ID              VARCHAR(255),
    USER_NAME            VARCHAR(255),
    SYN_INCEPT_DATA_TIME TIMESTAMP
);

create table DAC_STANDARD_BILL_STATISTICS
(
    ACCESS_SYSTEM        VARCHAR(255) default CURRENT_DATE,
    DATA_SOURCE          VARCHAR(255),
    RESOURCE_ID          VARCHAR(255),
    INSIDE_PROVIDE       VARCHAR(255),
    INSIDE_ACCEPT        VARCHAR(255),
    TACHE                NUMERIC,
    DIRCTION             VARCHAR(50),
    DATA_TIME            TIMESTAMP,
    CREATETIME           TIMESTAMP,
    START_NO             VARCHAR(255),
    SYS_CODE             VARCHAR(255),
    DATA_SOURCE_CODE     VARCHAR(255),
    TABLE_NAME_EN        VARCHAR(400),
    USER_ID              VARCHAR(255),
    USER_NAME            VARCHAR(255),
    SYN_INCEPT_DATA_TIME TIMESTAMP,
    OUTSIDE_ACCEPT       NUMERIC default 0
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
    DATA_NAME            VARCHAR(255),
    ACCESS_INPUT         NUMERIC,
    ACCESS_OUTPUT        NUMERIC,
    ACCESS_SAME_PERIOD   VARCHAR(20),
    STANDARD_INPUT       NUMERIC,
    DATA_TIME            TIMESTAMP,
    CREATE_TIME          TIMESTAMP,
    RESOURCE_ID          VARCHAR(255),
    ALARM_TYPE           VARCHAR(12),
    STANDARD_OUTPUT      NUMERIC,
    STANDARD_CHAIN_RATIO VARCHAR(20),
    STORAGE_INPUT        NUMERIC,
    STORAGE_OUTPUT       NUMERIC,
    STORAGE_CHAIN_RATIO  VARCHAR(20),
    DATA_DIRECTION       VARCHAR(100),
    IS_PUSH              VARCHAR(1),
    IS_SUMMARY           VARCHAR(1),
    ACCESS_OUTPUT_PREV   NUMERIC,
    ACCESS_INPUT_PREV    NUMERIC,
    TABLE_NAME_EN        VARCHAR(400),
    USER_ID              VARCHAR(255),
    USER_NAME            VARCHAR(255),
    IS_ISSUED            VARCHAR(1) default '0'
);

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

-- Create table
create table BILL_STATISTIC_HISTORY
(
    RESOURCE_ID VARCHAR(100),
    DIRECTION   VARCHAR(50),
    DATA_TIME   TIMESTAMP,
    TACHE       NUMERIC,
    SYSTEM      NUMERIC,
    STATUS      NUMERIC,
    ID          VARCHAR(50),
    CREATETIME  TIMESTAMP
);
-- Add comments to the columns
comment on column BILL_STATISTIC_HISTORY.TACHE is '环节1.接入2、入库';
comment on column BILL_STATISTIC_HISTORY.SYSTEM is '1.etl 2.标准化';
comment on column BILL_STATISTIC_HISTORY.STATUS is '1.更新中2.更新完成,3.更新失败';

create table DAC_CITY_DIVISION_CODE
(
    CODE        NUMERIC,
    NAME        VARCHAR(30),
    DESCRIPTION VARCHAR(64)
);

create table DAC_ACCEPTOR_BILL
(
    SJJRF_DZDBH   VARCHAR(64) not null,
    SJTGF_DZDBH   VARCHAR(64),
    SJJSWZ_JYQK   VARCHAR(255),
    SJCCXSDM      NUMERIC,
    SJ_DZWJMC     VARCHAR(128),
    SJZYBSF       VARCHAR(64),
    SJZYMC        VARCHAR(64),
    BZSJXJBM      VARCHAR(64),
    SJTS          NUMERIC,
    SJ_DZWJDX     NUMERIC,
    SJQSBH        VARCHAR(64),
    SJJWBH        VARCHAR(64),
    SJJYZ         VARCHAR(64),
    SJJYSFMC      VARCHAR(20),
    SCSJ_RQSJ     NUMERIC,
    DZSJ_RQSJ     NUMERIC,
    DZFFDM        NUMERIC,
    DZDLXDM       NUMERIC,
    DZDZTDM       NUMERIC,
    DZJG_JYQK     VARCHAR(255),
    SCSB_DZDBH    VARCHAR(64),
    SJJRFGLY_XM   VARCHAR(32),
    SJJRFGLY_LXDH VARCHAR(32),
    XXRWBH        VARCHAR(128),
    DZZDHJ        NUMERIC,
    CREATE_TIME   TIMESTAMP,
    UPDATE_TIME   TIMESTAMP,
    IF_SEND_ALARM VARCHAR(32),
    IF_SEND_OUT   VARCHAR(32)
);
alter table DAC_ACCEPTOR_BILL add constraint DAC_DAB_PRI primary key (SJJRF_DZDBH);
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
    SJJRF_DZDBH   VARCHAR(64),
    SJTGF_DZDBH   VARCHAR(64),
    SJJSWZ_JYQK   VARCHAR(255),
    SJCCXSDM      NUMERIC,
    SJ_DZWJMC     VARCHAR(128),
    SJZYBSF       VARCHAR(64),
    SJZYMC        VARCHAR(64),
    BZSJXJBM      VARCHAR(64),
    SJTS          NUMERIC,
    SJ_DZWJDX     NUMERIC,
    SJQSBH        VARCHAR(64),
    SJJWBH        VARCHAR(64),
    SJJYZ         VARCHAR(64),
    SJJYSFMC      VARCHAR(20),
    SCSJ_RQSJ     NUMERIC,
    DZSJ_RQSJ     NUMERIC,
    DZFFDM        NUMERIC,
    DZDLXDM       NUMERIC,
    DZDZTDM       NUMERIC,
    DZJG_JYQK     VARCHAR(255),
    SCSB_DZDBH    VARCHAR(64),
    SJJRFGLY_XM   VARCHAR(32),
    SJJRFGLY_LXDH VARCHAR(32),
    XXRWBH        VARCHAR(128),
    JOB_ID        VARCHAR(128),
    BATCH_ID      VARCHAR(128),
    ID            VARCHAR(50),
    CREATE_TIME   TIMESTAMP,
    UPDATE_TIME   TIMESTAMP,
    DZZDHJ        NUMERIC,
    SFYQ          VARCHAR(50)
);

comment on column DAC_ACCESS_ACCEPTOR_BILL.JOB_ID is '接入任务Id';
comment on column DAC_ACCESS_ACCEPTOR_BILL.BATCH_ID is '接入任务批次Id';
comment on column DAC_ACCESS_ACCEPTOR_BILL.DZZDHJ is '对账账单环节';
comment on column DAC_ACCESS_ACCEPTOR_BILL.SFYQ is '是否逾期';

create table DAC_ACCESS_PROVIDER_BILL
(
    SJTGF_DZDBH   VARCHAR(64),
    SJJSWZ_JYQK   VARCHAR(255),
    SJCCXSDM      NUMERIC,
    SJ_DZWJMC     VARCHAR(128),
    SJZYBSF       VARCHAR(64),
    SJZYMC        VARCHAR(64),
    BZSJXJBM      VARCHAR(64),
    SJTS          NUMERIC,
    SJ_DZWJDX     NUMERIC,
    SJQSBH        VARCHAR(64),
    SJJWBH        VARCHAR(64),
    SJJYZ         VARCHAR(64),
    SJJYSFMC      VARCHAR(20),
    SCSB_DZDBH    VARCHAR(64),
    SCSJ_RQSJ     NUMERIC,
    SJTGFGLY_XM   VARCHAR(32),
    SJTGFGLY_LXDH VARCHAR(32),
    XXRWBH        VARCHAR(128),
    JOB_ID        VARCHAR(128),
    BATCH_ID      VARCHAR(128),
    ID            VARCHAR(50),
    CREATE_TIME   TIMESTAMP,
    UPDATE_TIME   TIMESTAMP,
    DZZDHJ        NUMERIC,
    SFYQ          VARCHAR(50)
);
comment on column DAC_ACCESS_PROVIDER_BILL.DZZDHJ is '对账账单环节';
comment on column DAC_ACCESS_PROVIDER_BILL.SFYQ is '是否逾期';


create table DAC_STANDARD_ACCEPTOR_BILL
(
    SJJRF_DZDBH   VARCHAR(64),
    SJTGF_DZDBH   VARCHAR(64),
    SJJSWZ_JYQK   VARCHAR(255),
    SJCCXSDM      NUMERIC,
    SJ_DZWJMC     VARCHAR(128),
    SJZYBSF       VARCHAR(64),
    SJZYMC        VARCHAR(64),
    BZSJXJBM      VARCHAR(64),
    SJTS          NUMERIC,
    SJ_DZWJDX     NUMERIC,
    SJQSBH        VARCHAR(64),
    SJJWBH        VARCHAR(64),
    SJJYZ         VARCHAR(64),
    SJJYSFMC      VARCHAR(20),
    SCSJ_RQSJ     NUMERIC,
    DZSJ_RQSJ     NUMERIC,
    DZFFDM        NUMERIC,
    DZDLXDM       NUMERIC,
    DZDZTDM       NUMERIC,
    DZJG_JYQK     VARCHAR(255),
    SCSB_DZDBH    VARCHAR(64),
    SJJRFGLY_XM   VARCHAR(32),
    SJJRFGLY_LXDH VARCHAR(32),
    XXRWBH        VARCHAR(128)
);
alter table DAC_STANDARD_ACCEPTOR_BILL add constraint DAC_SAB_PRI primary key (SJJRF_DZDBH);


create table DAC_STANDARD_PROVIDER_BILL (
    SJTGF_DZDBH   VARCHAR(64),
    SJJSWZ_JYQK   VARCHAR(255),
    SJCCXSDM      NUMERIC,
    SJ_DZWJMC     VARCHAR(128),
    SJZYBSF       VARCHAR(64),
    SJZYMC        VARCHAR(64),
    BZSJXJBM      VARCHAR(64),
    SJTS          NUMERIC,
    SJ_DZWJDX     NUMERIC,
    SJQSBH        VARCHAR(64),
    SJJWBH        VARCHAR(64),
    SJJYZ         VARCHAR(64),
    SJJYSFMC      VARCHAR(20),
    SCSB_DZDBH    VARCHAR(64),
    SCSJ_RQSJ     NUMERIC,
    SJTGFGLY_XM   VARCHAR(32),
    SJTGFGLY_LXDH VARCHAR(32),
    XXRWBH        VARCHAR(128)
);
alter table DAC_STANDARD_PROVIDER_BILL add constraint DAC_SPB_PRI primary key (SJTGF_DZDBH);


create table DAC_STORAGE_ACCEPTOR_BILL
(
    SJJRF_DZDBH   VARCHAR(64),
    SJTGF_DZDBH   VARCHAR(64),
    SJJSWZ_JYQK   VARCHAR(255),
    SJCCXSDM      NUMERIC,
    SJ_DZWJMC     VARCHAR(128),
    SJZYBSF       VARCHAR(64),
    SJZYMC        VARCHAR(64),
    BZSJXJBM      VARCHAR(64),
    SJTS          NUMERIC,
    SJ_DZWJDX     NUMERIC,
    SJQSBH        VARCHAR(64),
    SJJWBH        VARCHAR(64),
    SJJYZ         VARCHAR(64),
    SJJYSFMC      VARCHAR(20),
    SCSJ_RQSJ     NUMERIC,
    DZSJ_RQSJ     NUMERIC,
    DZFFDM        NUMERIC,
    DZDLXDM       NUMERIC,
    DZDZTDM       NUMERIC,
    DZJG_JYQK     VARCHAR(255),
    SCSB_DZDBH    VARCHAR(64),
    SJJRFGLY_XM   VARCHAR(32),
    SJJRFGLY_LXDH VARCHAR(32),
    XXRWBH        VARCHAR(128)
);
alter table DAC_STORAGE_ACCEPTOR_BILL add constraint DAC_STAB_PRI primary key (SJJRF_DZDBH);


CREATE TABLE DAC_THIRD_ACCEPTOR_BILL
(
    BATCH_ID		   VARCHAR(32),
    DATA_NO	   		   VARCHAR(10),
    DATA_NAME    	   VARCHAR(400),
    RESOURCE_ID		   VARCHAR(20),
    DATA_TIME    	   TIMESTAMP,
    IP_ADDRESS		   VARCHAR(20),
    MAC_ADDRESS  	   VARCHAR(20),
    SEND_NUM           NUMERIC,
    SEND_SIZE          NUMERIC,
    RECEIVE_NUM        NUMERIC,
    RECEIVE_SIZE        NUMERIC,
    RECEIVE_FAIL_NUM    NUMERIC,
    RECEIVE_FAIL_SIZE   NUMERIC
);

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
    RRID NUMERIC(38),
    RESOURCE_ID VARCHAR(128),
    RESOURCE_NAME VARCHAR(256),
    LINK_TYPE NUMERIC(1),
    BILL_TYPE NUMERIC(1),
    CHECK_TIME TIMESTAMP,
    SUM_COUNT NUMERIC(38),
    SUCCESS_COUNT NUMERIC(38),
    SUCCESS_PERCENT NUMERIC(4,1),
    FAILD_COUNT NUMERIC(38),
    CREATE_TIME TIMESTAMP,
    UPDATE_TIME TIMESTAMP
);

create table DAC_BILL_LINK_STATISTIC
(
    RESOURCE_ID          VARCHAR(128),
    RESOURCE_NAME        VARCHAR(128),
    ACCESS_ACCEPTOR      NUMERIC,
    ACCESS_PROVIDER      NUMERIC,
    STANDARD_ACCEPTOR    NUMERIC,
    STANDARD_PROVIDER    NUMERIC,
    STORAGE_ACCEPTOR     NUMERIC,
    STORAGE_PROVIDER     NUMERIC,
    ACCESS_SAME_PERIOD   VARCHAR(32),
    STANDARD_CHAIN_RATIO VARCHAR(32),
    STORAGE_CHAIN_RATIO  VARCHAR(32),
    DATA_TIME            TIMESTAMP,
    STATISTIC_TIME       TIMESTAMP,
    ALARM_STATE          NUMERIC,
    IS_PUSH              NUMERIC,
    ACCESS_ACCEPTOR_PRE  NUMERIC,
    ACCESS_PROVIDER_PRE  NUMERIC
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
    SJTGF_DZDBH   VARCHAR(64) not null,
    SJJSWZ_JYQK   VARCHAR(255),
    SJCCXSDM      NUMERIC,
    SJ_DZWJMC     VARCHAR(128),
    SJZYBSF       VARCHAR(64),
    SJZYMC        VARCHAR(64),
    BZSJXJBM      VARCHAR(64),
    SJTS          NUMERIC,
    SJ_DZWJDX     NUMERIC,
    SJQSBH        VARCHAR(64),
    SJJWBH        VARCHAR(64),
    SJJYZ         VARCHAR(64),
    SJJYSFMC      VARCHAR(20),
    SCSB_DZDBH    VARCHAR(64),
    SCSJ_RQSJ     NUMERIC,
    SJTGFGLY_XM   VARCHAR(32),
    SJTGFGLY_LXDH VARCHAR(32),
    XXRWBH        VARCHAR(128),
    DZZDHJ        NUMERIC,
    CREATE_TIME   TIMESTAMP,
    UPDATE_TIME   TIMESTAMP,
    SFYQ          VARCHAR(50)
);
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
    SJJRF_DZDBH   VARCHAR(64),
    SJTGF_DZDBH   VARCHAR(64),
    SJZYBSF       VARCHAR(64),
    SJZYMC        VARCHAR(64),
    BZSJXJBM      VARCHAR(64),
    SJTS          NUMERIC,
    SJ_DZWJDX     NUMERIC,
    SJQSBH        VARCHAR(32),
    SJJWBH        VARCHAR(32),
    SJJYZ         VARCHAR(64),
    SJJYSFMC      VARCHAR(20),
    SCSJ_RQSJ     NUMERIC,
    DZSJ_RQSJ     NUMERIC,
    DZFFDM        NUMERIC,
    DZDLXDM       NUMERIC,
    DZDZTDM       NUMERIC,
    DZJG_JYQK     VARCHAR(255),
    SCSB_DZDBH    VARCHAR(64),
    SJJRFGLY_XM   VARCHAR(32),
    SJJRFGLY_LXDH VARCHAR(32),
    DZYQSJFW_KSSJ NUMERIC,
    DZYQSJFW_JSSJ NUMERIC,
    DZZDHJ        NUMERIC
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
    SJTGF_DZDBH   VARCHAR(64),
    SJZYBSF       VARCHAR(64),
    SJZYMC        VARCHAR(64),
    BZSJXJBM      VARCHAR(64),
    SJTS          NUMERIC,
    SJ_DZWJDX     NUMERIC,
    SJQSBH        VARCHAR(32),
    SJJWBH        VARCHAR(32),
    SJJYZ         VARCHAR(64),
    SJJYSFMC      VARCHAR(20),
    SCSJ_RQSJ     NUMERIC,
    SCSB_DZDBH    VARCHAR(64),
    SJTGFGLY_XM   VARCHAR(32),
    SJTGFGLY_LXDH VARCHAR(32),
    DZYQSJFW_KSSJ NUMERIC,
    DZYQSJFW_JSSJ NUMERIC,
    DZZDHJ        NUMERIC
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
    JOB_ID                VARCHAR(64),
    CHECK_TIME            TIMESTAMP,
    DATA_NAME_ZH          VARCHAR(255),
    DATA_NAME_EN          VARCHAR(255),
    IS_PARTITION          NUMERIC,
    ACCEPT_CITY           VARCHAR(64),
    EXCHANGE_POSITION     VARCHAR(255),
    SOURCE_COUNT          NUMERIC,
    PROVIDER_READ_COUNT   NUMERIC,
    PROVIDER_WRITE_COUNT  NUMERIC,
    ACCEPTOR_READ_COUNT   NUMERIC,
    ACCEPTOR_WRITE_COUNT  NUMERIC,
    DEST_COUNT            NUMERIC,
    COURSE_CHAIN_RATIO    VARCHAR(16),
    HISTORICAL_COMPARISON VARCHAR(16),
    PROVIDER_START_TIME   TIMESTAMP,
    PROVIDER_END_TIME     TIMESTAMP,
    ACCEPTOR_START_TIME   TIMESTAMP,
    ACCEPTOR_END_TIME     TIMESTAMP,
    PROVIDER_DURATION     NUMERIC,
    ACCEPTOR_DURATION     NUMERIC,
    ALL_DURATION          NUMERIC,
    TASK_STATE            VARCHAR(8),
    UPDATE_RESULT         NUMERIC,
    UPDATE_RESULT_REASON  VARCHAR(255),
    BASE_TIME             VARCHAR(64),
    BASE_TIME_ENABLE      NUMERIC,
    ID                    NUMERIC not null,
    EXCEPTION_FIELD       VARCHAR(255)
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
    JOB_ID           VARCHAR(64),
    DATA_NAME_EN     VARCHAR(64),
    BASE_TIME        VARCHAR(16),
    BASE_TIME_ENABLE NUMERIC
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
    SJTGF_DZDBH   VARCHAR(64),
    SJJSWZ_JYQK   VARCHAR(255),
    SJCCXSDM      NUMERIC,
    SJ_DZWJMC     VARCHAR(128),
    SJZYBSF       VARCHAR(64),
    SJZYMC        VARCHAR(64),
    BZSJXJBM      VARCHAR(64),
    SJTS          NUMERIC,
    SJ_DZWJDX     NUMERIC,
    SJQSBH        VARCHAR(64),
    SJJWBH        VARCHAR(64),
    SJJYZ         VARCHAR(64),
    SJJYSFMC      VARCHAR(20),
    SCSB_DZDBH    VARCHAR(64),
    SCSJ_RQSJ     NUMERIC,
    SJTGFGLY_XM   VARCHAR(32),
    SJTGFGLY_LXDH VARCHAR(32),
    XXRWBH        VARCHAR(128),
    JOB_ID        VARCHAR(128),
    BATCH_ID      VARCHAR(128),
    ID            VARCHAR(50),
    CREATE_TIME   TIMESTAMP,
    UPDATE_TIME   TIMESTAMP,
    DZZDHJ        NUMERIC,
    SFYQ          VARCHAR(50)
);

create table DAC_ISSUE_ACCEPTOR_BILL
(
    SJJRF_DZDBH   VARCHAR(64),
    SJTGF_DZDBH   VARCHAR(64),
    SJJSWZ_JYQK   VARCHAR(255),
    SJCCXSDM      NUMERIC,
    SJ_DZWJMC     VARCHAR(128),
    SJZYBSF       VARCHAR(64),
    SJZYMC        VARCHAR(64),
    BZSJXJBM      VARCHAR(64),
    SJTS          NUMERIC,
    SJ_DZWJDX     NUMERIC,
    SJQSBH        VARCHAR(64),
    SJJWBH        VARCHAR(64),
    SJJYZ         VARCHAR(64),
    SJJYSFMC      VARCHAR(20),
    SCSJ_RQSJ     NUMERIC,
    DZSJ_RQSJ     NUMERIC,
    DZFFDM        NUMERIC,
    DZDLXDM       NUMERIC,
    DZDZTDM       NUMERIC,
    DZJG_JYQK     VARCHAR(255),
    SCSB_DZDBH    VARCHAR(64),
    SJJRFGLY_XM   VARCHAR(32),
    SJJRFGLY_LXDH VARCHAR(32),
    XXRWBH        VARCHAR(128),
    JOB_ID        VARCHAR(128),
    BATCH_ID      VARCHAR(128),
    ID            VARCHAR(50),
    CREATE_TIME   TIMESTAMP,
    UPDATE_TIME   TIMESTAMP,
    DZZDHJ        NUMERIC,
    SFYQ          VARCHAR(50)
);

create table DAC_RELATE_JOB
(
    JOB_ID          VARCHAR(64),
    BATCH_ID        VARCHAR(255),
    STATUS          VARCHAR(16),
    DATA_START_TIME TIMESTAMP,
    DATA_END_TIME   TIMESTAMP,
    CREATE_TIME     TIMESTAMP,
    LOCAL           VARCHAR(64)
);

create table DAC_RELATE_TAG
(
    TYPE NUMERIC,
    TAG  VARCHAR(128)
);





