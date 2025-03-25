-- 创建表空间
create tablespace SHPTSTATISTICS datafile '/xxxx/xxxx/xxx/SHPTSTATISTICS.dbf' size 1G autoextend on next 100M maxsize unlimited extent management local;
-- 创建用户
create user SHPTSTATISTICS identified by SHPTSTATISTICS default tablespace SHPTSTATISTICS temporary tablespace TEMP profile DEFAULT account unlock;
-- 赋权
grant dba to SHPTSTATISTICS;
grant connect to SHPTSTATISTICS;
grant resource to SHPTSTATISTICS;
grant unlimited tablespace to SHPTSTATISTICS;
grant create public database link to system;
grant select any table to SHPTSTATISTICS;

-- Create table 任务状态表
create table SJGC_DATA_TASK
(
  SERIAL_NUMBER     VARCHAR2(64),
  TASK_ID           VARCHAR2(64),
  INSTANCE_ID       VARCHAR2(64),
  TASK_NAME         VARCHAR2(256),
  TASK_CLASS        VARCHAR2(256),
  TASK_SUBCLASS     VARCHAR2(256),
  IN_SOURCE_NAME    VARCHAR2(256),
  IN_CN_NAME        VARCHAR2(256),
  IN_PROTOCOL       VARCHAR2(256),
  OUT_SOURCE_NAME   VARCHAR2(256),
  TASK_STATUS       VARCHAR2(64),
  NODE_IP           VARCHAR2(64),
  NODE_PORT         VARCHAR2(64),
  SOURCE_FILETYPE   VARCHAR2(256),
  TASK_START_TIME   DATE,
  TASK_END_TIME     DATE,
  TASK_RUNNING_TIME NUMBER,
  CREATE_TIME       DATE
);
-- Add comments to the table 
comment on table SJGC_DATA_TASK
  is '任务状态表';
-- Add comments to the columns 
comment on column SJGC_DATA_TASK.SERIAL_NUMBER
  is '记录序列号';
comment on column SJGC_DATA_TASK.TASK_ID
  is '任务ID';
comment on column SJGC_DATA_TASK.INSTANCE_ID
  is '实例ID';
comment on column SJGC_DATA_TASK.TASK_NAME
  is '任务名称';
comment on column SJGC_DATA_TASK.TASK_CLASS
  is '大类';
comment on column SJGC_DATA_TASK.TASK_SUBCLASS
  is '小类';
comment on column SJGC_DATA_TASK.IN_SOURCE_NAME
  is '输入数据源名称';
comment on column SJGC_DATA_TASK.IN_CN_NAME
  is '输入数据名称';
comment on column SJGC_DATA_TASK.IN_PROTOCOL
  is '输入数据协议名';
comment on column SJGC_DATA_TASK.OUT_SOURCE_NAME
  is '输出数据源名称';
comment on column SJGC_DATA_TASK.TASK_STATUS
  is '任务状态';
comment on column SJGC_DATA_TASK.NODE_IP
  is '节点IP';
comment on column SJGC_DATA_TASK.NODE_PORT
  is '节点端口';
comment on column SJGC_DATA_TASK.SOURCE_FILETYPE
  is '来源文件类型';
comment on column SJGC_DATA_TASK.TASK_START_TIME
  is '任务开始时间';
comment on column SJGC_DATA_TASK.TASK_END_TIME
  is '任务结束时间';
comment on column SJGC_DATA_TASK.TASK_RUNNING_TIME
  is '任务运行时长';
comment on column SJGC_DATA_TASK.CREATE_TIME
  is '入表时间';


-- Create table 接入处理入库数据汇总表
create table SJGC_DATA_SUMMARY
(
  SERIAL_NUMBER   VARCHAR2(64),
  INSTANCE_ID     VARCHAR2(64),
  DATA_TYPE       VARCHAR2(64),
  NODE_IP         VARCHAR2(64),
  NODE_PORT       VARCHAR2(64),
  IN_CN_NAME      VARCHAR2(256),
  IN_PROTOCOL     VARCHAR2(256),
  IN_SUCCESS_NUM  NUMBER,
  IN_SUCCESS_SIZE NUMBER,
  OUT_CN_NAME     VARCHAR2(256),
  OUT_EN_NAME     VARCHAR2(256),
  OUT_PROTOCOL    VARCHAR2(256),
  OUT_SUCCESS_NUM NUMBER,
  OUT_ERROR_NUM   NUMBER,
  OUT_BIDDING_NUM NUMBER,
  STORE_TYPE      VARCHAR2(256),
  PROJECT_NAME    VARCHAR2(256),
  PUSH_DATE       DATE,
  PUSH_HOUR       NUMBER,
  CREATE_TIME     DATE
);
-- Add comments to the table 
comment on table SJGC_DATA_SUMMARY
  is '接入处理入库数据汇总表';
-- Add comments to the columns 
comment on column SJGC_DATA_SUMMARY.SERIAL_NUMBER
  is '记录序列号';
comment on column SJGC_DATA_SUMMARY.INSTANCE_ID
  is '实例ID';
comment on column SJGC_DATA_SUMMARY.DATA_TYPE
  is '标识';
comment on column SJGC_DATA_SUMMARY.NODE_IP
  is '节点IP';
comment on column SJGC_DATA_SUMMARY.NODE_PORT
  is '节点端口';
comment on column SJGC_DATA_SUMMARY.IN_CN_NAME
  is '输入数据名称';
comment on column SJGC_DATA_SUMMARY.IN_PROTOCOL
  is '输入数据协议名';
comment on column SJGC_DATA_SUMMARY.IN_SUCCESS_NUM
  is '输入数据条数';
comment on column SJGC_DATA_SUMMARY.IN_SUCCESS_SIZE
  is '输入数据大小';
comment on column SJGC_DATA_SUMMARY.OUT_CN_NAME
  is '输出数据名称';
comment on column SJGC_DATA_SUMMARY.OUT_EN_NAME
  is '输出数据英文表名';
comment on column SJGC_DATA_SUMMARY.OUT_PROTOCOL
  is '输出数据协议名';
comment on column SJGC_DATA_SUMMARY.OUT_SUCCESS_NUM
  is '输出成功条数';
comment on column SJGC_DATA_SUMMARY.OUT_ERROR_NUM
  is '输出异常条数';
comment on column SJGC_DATA_SUMMARY.OUT_BIDDING_NUM
  is '输出中标条数';
comment on column SJGC_DATA_SUMMARY.STORE_TYPE
  is '输出平台';
comment on column SJGC_DATA_SUMMARY.PROJECT_NAME
  is '输出平台项目空间';
comment on column SJGC_DATA_SUMMARY.PUSH_DATE
  is '推送日期';
comment on column SJGC_DATA_SUMMARY.PUSH_HOUR
  is '数据时点';
comment on column SJGC_DATA_SUMMARY.CREATE_TIME
  is '入表时间';

-- 创建视图（数据资产-工作流信息）
create or replace view v_sjgc_data_organiztion as
select lower(t.rowid) as SERIAL_NUMBER,
       t.project_name as TABLE_PROJECT,
       t.biz_name as TASK_NAME,
       t.status as TASK_STATE,
       to_date(t.insert_time) as PUSH_DATE,
       to_char(insert_time, 'hh24') as PUSH_HOUR,
       t.cpu_consumption as USE_CPU,
       t.memory_consumption as USE_MEMORY,
       t.running_time as TASK_RUNNING_TIME,
       t.insert_time as CREATE_TIME
  from SYNDG.M_BUSINESS_INSTANCE t
 where t.insert_time >= sysdate;


