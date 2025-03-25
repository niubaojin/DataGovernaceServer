-- 数据运维-告警信息
create table DO_ALARM_MSG
(
    ALARM_ID      VARCHAR2(100),
    ALARM_SYSTEM  VARCHAR2(100),
    ALARM_MODULE  VARCHAR2(100),
    ALARM_TIME    DATE,
    ALARM_FLAG    VARCHAR2(100),
    ALARM_LEVEL   VARCHAR2(100),
    ALARM_CONTENT VARCHAR2(2000),
    TABLE_NAME_EN VARCHAR2(100),
    TABLE_NAME_CH VARCHAR2(100),
    TABLEID       VARCHAR2(100)
);
comment on table DO_ALARM_MSG is '数据运维-告警信息';
comment on column DO_ALARM_MSG.ALARM_ID is '主键id';
comment on column DO_ALARM_MSG.ALARM_SYSTEM is '系统名称';
comment on column DO_ALARM_MSG.ALARM_MODULE is '模块名称';
comment on column DO_ALARM_MSG.ALARM_TIME is '故障产生时间';
comment on column DO_ALARM_MSG.ALARM_FLAG is '告警标记、必填，0：产生1：消除2：事件3：重启或倒换4：异常连接';
comment on column DO_ALARM_MSG.ALARM_LEVEL is '故障等级、必填，0：次要 1：一般；2：严重；3：紧急';
comment on column DO_ALARM_MSG.ALARM_CONTENT is '故障描述';
comment on column DO_ALARM_MSG.TABLE_NAME_EN is '数据英文名称';
comment on column DO_ALARM_MSG.TABLE_NAME_CH is '数据中文名称';
comment on column DO_ALARM_MSG.TABLEID is '协议编码';


-- 数据运维-数据治理跟踪表
create table DO_DATAGOVERNANCE_FOLLOW
(
    DG_ID            VARCHAR2(100),
    DG_LINK          VARCHAR2(100),
    DG_DATANAME      VARCHAR2(100),
    DG_TABLENAME     VARCHAR2(100),
    DG_TABLEID       VARCHAR2(100),
    DG_MSG           VARCHAR2(1000),
    DG_UPDATETIME    DATE,
    DG_SPONSOR       VARCHAR2(100),
    DG_SPONSOR_TIME  DATE,
    DG_MANAGER       VARCHAR2(100),
    DG_MANAGER_TIME  DATE,
    DG_REVIEWER_TIME DATE,
    DG_CONCLUSION    CLOB,
    DG_STATUS        VARCHAR2(100)
);
comment on table DO_DATAGOVERNANCE_FOLLOW is '数据运维-数据治理跟踪表';
comment on column DO_DATAGOVERNANCE_FOLLOW.DG_ID is '主键id';
comment on column DO_DATAGOVERNANCE_FOLLOW.DG_LINK is '环节';
comment on column DO_DATAGOVERNANCE_FOLLOW.DG_DATANAME is '数据名称';
comment on column DO_DATAGOVERNANCE_FOLLOW.DG_TABLENAME is '数据表名';
comment on column DO_DATAGOVERNANCE_FOLLOW.DG_TABLEID is '数据协议';
comment on column DO_DATAGOVERNANCE_FOLLOW.DG_MSG is '内容';
comment on column DO_DATAGOVERNANCE_FOLLOW.DG_UPDATETIME is '最新时间';
comment on column DO_DATAGOVERNANCE_FOLLOW.DG_SPONSOR is '发起人员';
comment on column DO_DATAGOVERNANCE_FOLLOW.DG_SPONSOR_TIME is '流程发起时间';
comment on column DO_DATAGOVERNANCE_FOLLOW.DG_MANAGER is '治理人员';
comment on column DO_DATAGOVERNANCE_FOLLOW.DG_MANAGER_TIME is '治理时间';
comment on column DO_DATAGOVERNANCE_FOLLOW.DG_REVIEWER_TIME is '审核时间';
comment on column DO_DATAGOVERNANCE_FOLLOW.DG_CONCLUSION is '治理审核结论json';
comment on column DO_DATAGOVERNANCE_FOLLOW.DG_STATUS is '状态';

-- Create table（数据积压监控表）
create table DO_DATA_PILED_MONITOR
(
    DATA_NAME          VARCHAR2(100),
    DATA_WAREHOUSENAME VARCHAR2(100),
    CONSUM_TOPIC       VARCHAR2(100),
    CONSUM_GROUP       VARCHAR2(100),
    OFF_SET             NUMBER,
    LOGSIZE            NUMBER,
    LAG                NUMBER,
    PILED_RATE         VARCHAR2(10),
    PUSH_HOUR          VARCHAR2(10),
    INSERTTIME         DATE default sysdate,
    DATA_TYPE          VARCHAR2(10) default 1
);
comment on table DO_DATA_PILED_MONITOR is '数据积压监控表（kafka）';
comment on column DO_DATA_PILED_MONITOR.DATA_NAME is '数据名称';
comment on column DO_DATA_PILED_MONITOR.DATA_WAREHOUSENAME is '数据源名称';
comment on column DO_DATA_PILED_MONITOR.CONSUM_TOPIC is '消费主题';
comment on column DO_DATA_PILED_MONITOR.CONSUM_GROUP is '消费组';
comment on column DO_DATA_PILED_MONITOR.OFF_SET is '消费偏移量';
comment on column DO_DATA_PILED_MONITOR.LOGSIZE is '最大偏移量';
comment on column DO_DATA_PILED_MONITOR.LAG is '剩余偏移量';
comment on column DO_DATA_PILED_MONITOR.PILED_RATE is '积压率';
comment on column DO_DATA_PILED_MONITOR.PUSH_HOUR is '推送时点';
comment on column DO_DATA_PILED_MONITOR.INSERTTIME is '数据入库时间';
comment on column DO_DATA_PILED_MONITOR.DATA_TYPE is '数据种类：1.正常数据种类，2.堆积数据种类，3.异常数据种类';

-- 数据运维-历史数据监测-应用类型
create table DO_APP_TYPE
(
    TABLENAME    VARCHAR2(100),
    TABLEID      VARCHAR2(100),
    TABLENAME_CH VARCHAR2(100),
    DATA_TYPE    VARCHAR2(100),
    OPERATOR_NET VARCHAR2(100),
    DATA_SOURCE  VARCHAR2(100),
    CITYCODE     VARCHAR2(100),
    PROTOCOL     VARCHAR2(100),
    VALTEXT      VARCHAR2(100),
    RECORDS_ALL  NUMBER,
    DT           VARCHAR2(50)
);
comment on table DO_APP_TYPE is '历史数据监测-应用类型';
comment on column DO_APP_TYPE.TABLENAME is '数据表名';
comment on column DO_APP_TYPE.TABLEID is '数据协议代码';
comment on column DO_APP_TYPE.TABLENAME_CH is '数据中文名称';
comment on column DO_APP_TYPE.DATA_TYPE is '数据来源分类';
comment on column DO_APP_TYPE.OPERATOR_NET is '运营商';
comment on column DO_APP_TYPE.DATA_SOURCE is '网络制式';
comment on column DO_APP_TYPE.CITYCODE is '城市区号';
comment on column DO_APP_TYPE.PROTOCOL is '协议类型';
comment on column DO_APP_TYPE.VALTEXT is '协议名称';
comment on column DO_APP_TYPE.RECORDS_ALL is '记录数';
comment on column DO_APP_TYPE.DT is '数据日期';

-- 数据运维-历史数据监测-事件类型
create table DO_EVENT_TYPE
(
    TABLENAME    VARCHAR2(100),
    TABLEID      VARCHAR2(100),
    TABLENAME_CH VARCHAR2(100),
    DATA_TYPE    VARCHAR2(100),
    OPERATOR_NET VARCHAR2(100),
    DATA_SOURCE  VARCHAR2(100),
    CITYCODE     VARCHAR2(100),
    CALLTYPE     VARCHAR2(100),
    VALTEXT      VARCHAR2(100),
    RECORDS_ALL  NUMBER,
    DT           VARCHAR2(50)
);
comment on table DO_EVENT_TYPE is '历史数据监测-事件类型';
comment on column DO_EVENT_TYPE.TABLENAME is '数据表名';
comment on column DO_EVENT_TYPE.TABLEID is '数据协议代码';
comment on column DO_EVENT_TYPE.TABLENAME_CH is '数据中文名称';
comment on column DO_EVENT_TYPE.DATA_TYPE is '数据来源分类';
comment on column DO_EVENT_TYPE.OPERATOR_NET is '运营商';
comment on column DO_EVENT_TYPE.DATA_SOURCE is '网络制式';
comment on column DO_EVENT_TYPE.CITYCODE is '城市区号';
comment on column DO_EVENT_TYPE.CALLTYPE is '呼叫类型';
comment on column DO_EVENT_TYPE.VALTEXT is '呼叫名称';
comment on column DO_EVENT_TYPE.RECORDS_ALL is '记录数';
comment on column DO_EVENT_TYPE.DT is '数据日期';

create table DO_OPERATOR_LOG(
    NUM_ID    NUMBER,
    OPERATE_TIME      VARCHAR2(100),
    TERMINAL_ID VARCHAR2(100),
    OPERATE_TYPE    NUMBER,
    OPERATE_RESULT VARCHAR2(100),
    ERROR_CODE  VARCHAR2(100),
    OPERATE_NAME     VARCHAR2(100),
    OPERATE_CONDITION     VARCHAR2(4000),
    DISPLAY     VARCHAR2(100),
    DATA_LEVEL  NUMBER,
    CREATE_TIME   DATE,
    UPDATE_TIME   DATE,
    IF_OUT_SEND   NUMBER,
    USER_NAME   VARCHAR2(100),
    USER_ID VARCHAR2(100),
    USER_NUM  VARCHAR2(100),
    ORGANIZATION  VARCHAR2(1000),
    ORGANIZATION_ID  VARCHAR2(100)
)
PARTITION BY RANGE ( CREATE_TIME ) INTERVAL ('7 day')
(partition part_002 VALUES less than (to_date( '2024-06-26 00:00:00', 'yyyy-mm-dd HH24:mi:ss' )));

