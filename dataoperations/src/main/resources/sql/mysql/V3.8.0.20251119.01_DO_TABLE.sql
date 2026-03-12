-- 数据运维-告警信息
CREATE TABLE `DO_ALARM_MSG` (
    `ALARM_ID`      VARCHAR(100) NOT NULL COMMENT '主键id',
    `ALARM_SYSTEM`  VARCHAR(100) COMMENT '系统名称',
    `ALARM_MODULE`  VARCHAR(100) COMMENT '模块名称',
    `ALARM_TIME`    DATETIME     COMMENT '故障产生时间',
    `ALARM_FLAG`    VARCHAR(100) COMMENT '告警标记、必填，0：产生1：消除2：事件3：重启或倒换4：异常连接',
    `ALARM_LEVEL`   VARCHAR(100) COMMENT '故障等级、必填，0：次要 1：一般；2：严重；3：紧急',
    `ALARM_CONTENT` TEXT         COMMENT '故障描述',
    `TABLE_NAME_EN` VARCHAR(100) COMMENT '数据英文名称',
    `TABLE_NAME_CH` VARCHAR(100) COMMENT '数据中文名称',
    `TABLEID` VARCHAR(100)       COMMENT '协议编码'
) COMMENT = '数据运维-告警信息';

-- 数据运维-数据治理跟踪表
CREATE TABLE `DO_DATAGOVERNANCE_FOLLOW` (
    `DG_ID`            VARCHAR(100) NOT NULL COMMENT '主键id',
    `DG_LINK`          VARCHAR(100) COMMENT '环节',
    `DG_DATANAME`      VARCHAR(100) COMMENT '数据名称',
    `DG_TABLENAME`     VARCHAR(100) COMMENT '数据表名',
    `DG_TABLEID`       VARCHAR(100) COMMENT '数据协议',
    `DG_MSG`           VARCHAR(1000) COMMENT '内容',
    `DG_UPDATETIME`    DATETIME     COMMENT '最新时间',
    `DG_SPONSOR`       VARCHAR(100) COMMENT '发起人员',
    `DG_SPONSOR_TIME`  DATETIME     COMMENT '流程发起时间',
    `DG_MANAGER`       VARCHAR(100) COMMENT '治理人员',
    `DG_MANAGER_TIME`  DATETIME     COMMENT '治理时间',
    `DG_REVIEWER_TIME` DATETIME     COMMENT '审核时间',
    `DG_CONCLUSION`    TEXT         COMMENT '治理审核结论json',
    `DG_STATUS`        VARCHAR(100) COMMENT '状态'
) COMMENT = '数据运维-数据治理跟踪表';

-- Create table（数据积压监控表）
CREATE TABLE `DO_DATA_PILED_MONITOR` (
    `DATA_NAME`          VARCHAR(100) COMMENT '数据名称',
    `DATA_WAREHOUSENAME` VARCHAR(100) COMMENT '数据源名称',
    `CONSUM_TOPIC`       VARCHAR(100) COMMENT '消费主题',
    `CONSUM_GROUP`       VARCHAR(100) COMMENT '消费组',
    `OFFSET`             BIGINT       COMMENT '消费偏移量',
    `LOGSIZE`            BIGINT       COMMENT '最大偏移量',
    `LAG`                BIGINT       COMMENT '剩余偏移量',
    `PILED_RATE`         VARCHAR(10)  COMMENT '积压率',
    `PUSH_HOUR`          VARCHAR(10)  COMMENT '推送时点',
    `INSERTTIME`         DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '数据入库时间',
    `DATA_TYPE`          VARCHAR(10) DEFAULT '1' COMMENT '数据种类：1.正常数据种类，2.堆积数据种类，3.异常数据种类'
) COMMENT = '数据积压监控表（kafka）';

-- 数据运维-历史数据监测-应用类型
CREATE TABLE `DO_APP_TYPE` (
    `TABLENAME`    VARCHAR(100) COMMENT '数据表名',
    `TABLEID`      VARCHAR(100) COMMENT '数据协议代码',
    `TABLENAME_CH` VARCHAR(100) COMMENT '数据中文名称',
    `DATA_TYPE`    VARCHAR(100) COMMENT '数据来源分类',
    `OPERATOR_NET` VARCHAR(100) COMMENT '运营商',
    `DATA_SOURCE`  VARCHAR(100) COMMENT '网络制式',
    `CITYCODE`     VARCHAR(100) COMMENT '城市区号',
    `PROTOCOL`     VARCHAR(100) COMMENT '协议类型',
    `VALTEXT`      VARCHAR(100) COMMENT '协议名称',
    `RECORDS_ALL`  BIGINT       COMMENT '记录数',
    `DT`           VARCHAR(50)  COMMENT '数据日期'
) COMMENT = '历史数据监测-应用类型';

-- 数据运维-历史数据监测-事件类型
CREATE TABLE `DO_EVENT_TYPE` (
    `TABLENAME`    VARCHAR(100) COMMENT '数据表名',
    `TABLEID`      VARCHAR(100) COMMENT '数据协议代码',
    `TABLENAME_CH` VARCHAR(100) COMMENT '数据中文名称',
    `DATA_TYPE`    VARCHAR(100) COMMENT '数据来源分类',
    `OPERATOR_NET` VARCHAR(100) COMMENT '运营商',
    `DATA_SOURCE`  VARCHAR(100) COMMENT '网络制式',
    `CITYCODE`     VARCHAR(100) COMMENT '城市区号',
    `CALLTYPE`     VARCHAR(100) COMMENT '呼叫类型',
    `VALTEXT`      VARCHAR(100) COMMENT '呼叫名称',
    `RECORDS_ALL`  BIGINT       COMMENT '记录数',
    `DT`           VARCHAR(50)  COMMENT '数据日期'
) COMMENT = '历史数据监测-事件类型';

CREATE TABLE `DO_OPERATOR_LOG` (
    `NUM_ID`            BIGINT NOT NULL COMMENT 'NUM_ID',
    `OPERATE_TIME`      VARCHAR(100) COMMENT '操作时间',
    `TERMINAL_ID`       VARCHAR(100) COMMENT '操作中断ip',
    `OPERATE_TYPE`      INT          COMMENT '操作类型',
    `OPERATE_RESULT`    VARCHAR(100) COMMENT '操作结果',
    `ERROR_CODE`        VARCHAR(100) COMMENT '错误代码',
    `OPERATE_NAME`      VARCHAR(100) COMMENT '操作模块名称',
    `OPERATE_CONDITION` TEXT         COMMENT '操作内容',
    `DISPLAY`           VARCHAR(100) COMMENT '是否显示',
    `DATA_LEVEL`        INT          COMMENT '数据等级',
    `CREATE_TIME`       DATETIME     COMMENT '创建时间',
    `UPDATE_TIME`       DATETIME     COMMENT '更新时间',
    `IF_OUT_SEND`       INT          COMMENT '是否输出发送',
    `USER_NAME`         VARCHAR(100) COMMENT '用户名称',
    `USER_ID`           VARCHAR(100) COMMENT '用户证件号码',
    `USER_NUM`          VARCHAR(100) COMMENT '用户id',
    `ORGANIZATION`      TEXT         COMMENT '机构名称',
    `ORGANIZATION_ID`   VARCHAR(100) COMMENT '机构id'
) COMMENT = '操作日志表';
