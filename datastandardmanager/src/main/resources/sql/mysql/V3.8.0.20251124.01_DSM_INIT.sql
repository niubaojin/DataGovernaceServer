-- 标签分类信息
insert into DSM_LABEL_CODE_TABLE (LABEL_LEVEL, LABEL_NAME)
values (6, '标签六');
insert into DSM_LABEL_CODE_TABLE (LABEL_LEVEL, LABEL_NAME)
values (1, '标签一');
insert into DSM_LABEL_CODE_TABLE (LABEL_LEVEL, LABEL_NAME)
values (2, '标签二');
insert into DSM_LABEL_CODE_TABLE (LABEL_LEVEL, LABEL_NAME)
values (3, '标签三');
insert into DSM_LABEL_CODE_TABLE (LABEL_LEVEL, LABEL_NAME)
values (4, '标签四');
insert into DSM_LABEL_CODE_TABLE (LABEL_LEVEL, LABEL_NAME)
values (5, '标签五');
commit;

-- 插入平台类型数据
insert into DSM_ALL_CODE_DATA (ID, CODE_ID, CODE_VALUE, CODE_TEXT, MEMO) values ('10001', 'store_type', '1', 'ODPS', 'ODPS');
insert into DSM_ALL_CODE_DATA (ID, CODE_ID, CODE_VALUE, CODE_TEXT, MEMO) values ('10021', 'store_type', '21', 'ADS', 'ADS');
insert into DSM_ALL_CODE_DATA (ID, CODE_ID, CODE_VALUE, CODE_TEXT, MEMO) values ('10022', 'store_type', '22', 'ADB', 'ADB');
insert into DSM_ALL_CODE_DATA (ID, CODE_ID, CODE_VALUE, CODE_TEXT, MEMO) values ('10003', 'store_type', '4', 'HBase-HUAWEI', 'HBase-HUAWEI');
insert into DSM_ALL_CODE_DATA (ID, CODE_ID, CODE_VALUE, CODE_TEXT, MEMO) values ('10004', 'store_type', '5', 'Hive-HUAWEI', 'Hive-HUAWEI');
insert into DSM_ALL_CODE_DATA (ID, CODE_ID, CODE_VALUE, CODE_TEXT, MEMO) values ('10007', 'store_type', '7', 'ClickHouse', 'ClickHouse');
insert into DSM_ALL_CODE_DATA (ID, CODE_ID, CODE_VALUE, CODE_TEXT, MEMO) values ('10008', 'store_type', '9', 'TRSHybase', 'TRSHybase');
insert into DSM_ALL_CODE_DATA (ID, CODE_ID, CODE_VALUE, CODE_TEXT, MEMO) values ('10010', 'store_type', '10', 'Oracle', 'Oracle');
insert into DSM_ALL_CODE_DATA (ID, CODE_ID, CODE_VALUE, CODE_TEXT, MEMO) values ('10011', 'store_type', '11', 'HBase-CDH', 'HBase-CDH');
insert into DSM_ALL_CODE_DATA (ID, CODE_ID, CODE_VALUE, CODE_TEXT, MEMO) values ('10012', 'store_type', '12', 'Hive-CDH', 'Hive-CDH');
insert into DSM_ALL_CODE_DATA (ID, CODE_ID, CODE_VALUE, CODE_TEXT, MEMO) values ('10013', 'store_type', '13', 'DATAHUB', 'DATAHUB');
insert into DSM_ALL_CODE_DATA (ID, CODE_ID, CODE_VALUE, CODE_TEXT, MEMO) values ('10014', 'store_type', '14', 'Kafka', 'Kafka');
insert into DSM_ALL_CODE_DATA (ID, CODE_ID, CODE_VALUE, CODE_TEXT, MEMO) values ('10015', 'store_type', '15', 'RocketMq', 'RocketMq');
insert into DSM_ALL_CODE_DATA (ID, CODE_ID, CODE_VALUE, CODE_TEXT, MEMO) values ('10016', 'store_type', '16', 'Redis', 'Redis');
insert into DSM_ALL_CODE_DATA (ID, CODE_ID, CODE_VALUE, CODE_TEXT, MEMO) values ('10019', 'store_type', '19', 'FTP', 'FTP');
insert into DSM_ALL_CODE_DATA (ID, CODE_ID, CODE_VALUE, CODE_TEXT, MEMO) values ('SYNLTEFIELD_STATUS01', 'SYNLTEFIELD_STATUS', '01', '新建', '数据元页面中的状态-新建');
insert into DSM_ALL_CODE_DATA (ID, CODE_ID, CODE_VALUE, CODE_TEXT, MEMO) values ('SYNLTEFIELD_STATUS05', 'SYNLTEFIELD_STATUS', '05', '发布', '数据元页面中的状态-发布');
insert into DSM_ALL_CODE_DATA (ID, CODE_ID, CODE_VALUE, CODE_TEXT, MEMO) values ('SYNLTEFIELD_STATUS07', 'SYNLTEFIELD_STATUS', '07', '停用', '数据元页面中的状态-停用');
insert into DSM_ALL_CODE_DATA (ID, CODE_ID, CODE_VALUE, CODE_TEXT, MEMO) values ('field_type0', 'field_type', '0', 'integer', '整数型');
insert into DSM_ALL_CODE_DATA (ID, CODE_ID, CODE_VALUE, CODE_TEXT, MEMO) values ('field_type1', 'field_type', '1', 'float', '浮点型');
insert into DSM_ALL_CODE_DATA (ID, CODE_ID, CODE_VALUE, CODE_TEXT, MEMO) values ('field_type2', 'field_type', '2', 'string', '字符串型');
insert into DSM_ALL_CODE_DATA (ID, CODE_ID, CODE_VALUE, CODE_TEXT, MEMO) values ('field_type3', 'field_type', '3', 'date', '日期型');
insert into DSM_ALL_CODE_DATA (ID, CODE_ID, CODE_VALUE, CODE_TEXT, MEMO) values ('field_type4', 'field_type', '4', 'datetime', '日期时间型');
insert into DSM_ALL_CODE_DATA (ID, CODE_ID, CODE_VALUE, CODE_TEXT, MEMO) values ('field_type6', 'field_type', '6', 'long', '长整型');
insert into DSM_ALL_CODE_DATA (ID, CODE_ID, CODE_VALUE, CODE_TEXT, MEMO) values ('field_type7', 'field_type', '7', 'double', '双精度浮点型');
insert into DSM_ALL_CODE_DATA (ID, CODE_ID, CODE_VALUE, CODE_TEXT, MEMO) values ('EXPRESSION_WORD1', 'EXPRESSION_WORD', '名称', '名称', '数据元页面中的表示词');
insert into DSM_ALL_CODE_DATA (ID, CODE_ID, CODE_VALUE, CODE_TEXT, MEMO) values ('EXPRESSION_WORD2', 'EXPRESSION_WORD', '描述', '描述', '数据元页面中的表示词');
insert into DSM_ALL_CODE_DATA (ID, CODE_ID, CODE_VALUE, CODE_TEXT, MEMO) values ('EXPRESSION_WORD3', 'EXPRESSION_WORD', '金额', '金额', '数据元页面中的表示词');
insert into DSM_ALL_CODE_DATA (ID, CODE_ID, CODE_VALUE, CODE_TEXT, MEMO) values ('EXPRESSION_WORD4', 'EXPRESSION_WORD', '日期', '日期', '数据元页面中的表示词');
insert into DSM_ALL_CODE_DATA (ID, CODE_ID, CODE_VALUE, CODE_TEXT, MEMO) values ('EXPRESSION_WORD5', 'EXPRESSION_WORD', '日期时间', '日期时间', '数据元页面中的表示词');
insert into DSM_ALL_CODE_DATA (ID, CODE_ID, CODE_VALUE, CODE_TEXT, MEMO) values ('EXPRESSION_WORD6', 'EXPRESSION_WORD', '代码', '代码', '数据元页面中的表示词');
insert into DSM_ALL_CODE_DATA (ID, CODE_ID, CODE_VALUE, CODE_TEXT, MEMO) values ('EXPRESSION_WORD7', 'EXPRESSION_WORD', '号码', '号码', '数据元页面中的表示词');
insert into DSM_ALL_CODE_DATA (ID, CODE_ID, CODE_VALUE, CODE_TEXT, MEMO) values ('EXPRESSION_WORD8', 'EXPRESSION_WORD', '百分比', '百分比', '数据元页面中的表示词');
insert into DSM_ALL_CODE_DATA (ID, CODE_ID, CODE_VALUE, CODE_TEXT, MEMO) values ('EXPRESSION_WORD9', 'EXPRESSION_WORD', '量', '量', '数据元页面中的表示词');
insert into DSM_ALL_CODE_DATA (ID, CODE_ID, CODE_VALUE, CODE_TEXT, MEMO) values ('EXPRESSION_WORD10', 'EXPRESSION_WORD', '比率', '比率', '数据元页面中的表示词');
insert into DSM_ALL_CODE_DATA (ID, CODE_ID, CODE_VALUE, CODE_TEXT, MEMO) values ('EXPRESSION_WORD11', 'EXPRESSION_WORD', '指示符', '指示符', '数据元页面中的表示词');
insert into DSM_ALL_CODE_DATA (ID, CODE_ID, CODE_VALUE, CODE_TEXT, MEMO) values ('ELEMENTOBJECT1', 'ELEMENTOBJECT', '1', '人员', '数据元素中的关联主体');
insert into DSM_ALL_CODE_DATA (ID, CODE_ID, CODE_VALUE, CODE_TEXT, MEMO) values ('ELEMENTOBJECT2', 'ELEMENTOBJECT', '2', '物', '数据元素中的关联主体');
insert into DSM_ALL_CODE_DATA (ID, CODE_ID, CODE_VALUE, CODE_TEXT, MEMO) values ('ELEMENTOBJECT3', 'ELEMENTOBJECT', '3', '组织', '数据元素中的关联主体');
insert into DSM_ALL_CODE_DATA (ID, CODE_ID, CODE_VALUE, CODE_TEXT, MEMO) values ('ELEMENTOBJECT4', 'ELEMENTOBJECT', '4', '地', '数据元素中的关联主体');
insert into DSM_ALL_CODE_DATA (ID, CODE_ID, CODE_VALUE, CODE_TEXT, MEMO) values ('ELEMENTOBJECT5', 'ELEMENTOBJECT', '5', '事', '数据元素中的关联主体');
insert into DSM_ALL_CODE_DATA (ID, CODE_ID, CODE_VALUE, CODE_TEXT, MEMO) values ('ELEMENTOBJECT6', 'ELEMENTOBJECT', '6', '时间', '数据元素中的关联主体');
insert into DSM_ALL_CODE_DATA (ID, CODE_ID, CODE_VALUE, CODE_TEXT, MEMO) values ('ELEMENTOBJECT7', 'ELEMENTOBJECT', '7', '信息', '数据元素中的关联主体');
insert into DSM_ALL_CODE_DATA (ID, CODE_ID, CODE_VALUE, CODE_TEXT, MEMO) values ('01', 'SECURITYLEVEL', '01', '第一级', '字段定义中的数据安全级别');
insert into DSM_ALL_CODE_DATA (ID, CODE_ID, CODE_VALUE, CODE_TEXT, MEMO) values ('02', 'SECURITYLEVEL', '02', '第二级', '字段定义中的数据安全级别');
insert into DSM_ALL_CODE_DATA (ID, CODE_ID, CODE_VALUE, CODE_TEXT, MEMO) values ('02', 'SECURITYLEVEL', '03', '第三级', '字段定义中的数据安全级别');
insert into DSM_ALL_CODE_DATA (ID, CODE_ID, CODE_VALUE, CODE_TEXT, MEMO) values ('03', 'SECURITYLEVEL', '04', '第四级', '字段定义中的数据安全级别');
insert into DSM_ALL_CODE_DATA (ID, CODE_ID, CODE_VALUE, CODE_TEXT, MEMO) values ('03', 'SECURITYLEVEL', '05', '第五级', '字段定义中的数据安全级别');
insert into DSM_ALL_CODE_DATA (ID, CODE_ID, CODE_VALUE, CODE_TEXT, MEMO) values ('03', 'SECURITYLEVEL', '06', '第六级', '字段定义中的数据安全级别');
insert into DSM_ALL_CODE_DATA (ID, CODE_ID, CODE_VALUE, CODE_TEXT, MEMO) values ('04', 'SECURITYLEVEL', '07', '第七级', '字段定义中的数据安全级别');
insert into DSM_ALL_CODE_DATA (ID, CODE_ID, CODE_VALUE, CODE_TEXT, MEMO) values ('04', 'SECURITYLEVEL', '08', '第八级', '字段定义中的数据安全级别');
insert into DSM_ALL_CODE_DATA (ID, CODE_ID, CODE_VALUE, CODE_TEXT, MEMO) values ('ckFun1', 'CLICKHOUSE_FUNCTION', 'toDayOfYear', 'toDayOfYear', 'clickhouse建表时使用的函数名称');
insert into DSM_ALL_CODE_DATA (ID, CODE_ID, CODE_VALUE, CODE_TEXT, MEMO) values ('ckFun2', 'CLICKHOUSE_FUNCTION', 'toMonth', 'toMonth', 'clickhouse建表时使用的函数名称');
insert into DSM_ALL_CODE_DATA (ID, CODE_ID, CODE_VALUE, CODE_TEXT, MEMO) values ('ckFun3', 'CLICKHOUSE_FUNCTION', 'toYYYYMM', 'toYYYYMM', 'clickhouse建表时使用的函数名称');
insert into DSM_ALL_CODE_DATA (ID, CODE_ID, CODE_VALUE, CODE_TEXT, MEMO) values ('ckFun4', 'CLICKHOUSE_FUNCTION', 'toYYYYMMDD', 'toYYYYMMDD', 'clickhouse建表时使用的函数名称');
insert into DSM_ALL_CODE_DATA (ID, CODE_ID, CODE_VALUE, CODE_TEXT, MEMO) values ('ckFun5', 'CLICKHOUSE_FUNCTION', 'toYear', 'toYear', 'clickhouse建表时使用的函数名称');
commit ;

insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150424', '内蒙古自治区赤峰市林西县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150425', '内蒙古自治区赤峰市克什克腾旗');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150426', '内蒙古自治区赤峰市翁牛特旗');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150428', '内蒙古自治区赤峰市喀喇沁旗');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150429', '内蒙古自治区赤峰市宁城县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150430', '内蒙古自治区赤峰市敖汉旗');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150500', '内蒙古自治区通辽市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150501', '内蒙古自治区通辽市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150502', '内蒙古自治区通辽市科尔沁区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150521', '内蒙古自治区通辽市科尔沁左翼中旗');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150522', '内蒙古自治区通辽市科尔沁左翼后旗');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150523', '内蒙古自治区通辽市开鲁县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150524', '内蒙古自治区通辽市库伦旗');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150525', '内蒙古自治区通辽市奈曼旗');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150526', '内蒙古自治区通辽市扎鲁特旗');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150581', '内蒙古自治区通辽市霍林郭勒市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150600', '内蒙古自治区鄂尔多斯市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150602', '内蒙古自治区鄂尔多斯市东胜区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150621', '内蒙古自治区鄂尔多斯市达拉特旗');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150622', '内蒙古自治区鄂尔多斯市准格尔旗');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150623', '内蒙古自治区鄂尔多斯市鄂托克前旗');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150624', '内蒙古自治区鄂尔多斯市鄂托克旗');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150625', '内蒙古自治区鄂尔多斯市杭锦旗');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150626', '内蒙古自治区鄂尔多斯市乌审旗');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150627', '内蒙古自治区鄂尔多斯市伊金霍洛旗');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150700', '内蒙古自治区呼伦贝尔市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150701', '内蒙古自治区呼伦贝尔市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150702', '内蒙古自治区呼伦贝尔市海拉尔区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150721', '内蒙古自治区呼伦贝尔市阿荣旗');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150722', '内蒙古自治区呼伦贝尔市莫力达瓦达斡尔族自治旗');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150723', '内蒙古自治区呼伦贝尔市鄂伦春自治旗');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150724', '内蒙古自治区呼伦贝尔市鄂温克族自治旗');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150725', '内蒙古自治区呼伦贝尔市陈巴尔虎旗');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150726', '内蒙古自治区呼伦贝尔市新巴尔虎左旗');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150727', '内蒙古自治区呼伦贝尔市新巴尔虎右旗');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150781', '内蒙古自治区呼伦贝尔市满洲里市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150782', '内蒙古自治区呼伦贝尔市牙克石市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150783', '内蒙古自治区呼伦贝尔市扎兰屯市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150784', '内蒙古自治区呼伦贝尔市额尔古纳市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150785', '内蒙古自治区呼伦贝尔市根河市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150800', '内蒙古自治区巴彦淖尔市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150801', '内蒙古自治区巴彦淖尔市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150802', '内蒙古自治区巴彦淖尔市临河区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150821', '内蒙古自治区巴彦淖尔市五原县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150822', '内蒙古自治区巴彦淖尔市磴口县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150823', '内蒙古自治区巴彦淖尔市乌拉特前旗');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150824', '内蒙古自治区巴彦淖尔市乌拉特中旗');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150825', '内蒙古自治区巴彦淖尔市乌拉特后旗');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150826', '内蒙古自治区巴彦淖尔市杭锦后旗');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150900', '内蒙古自治区乌兰察布市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150901', '内蒙古自治区乌兰察布市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150902', '内蒙古自治区乌兰察布市集宁区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150921', '内蒙古自治区乌兰察布市卓资县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150922', '内蒙古自治区乌兰察布市化德县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150923', '内蒙古自治区乌兰察布市商都县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150924', '内蒙古自治区乌兰察布市兴和县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150925', '内蒙古自治区乌兰察布市凉城县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150926', '内蒙古自治区乌兰察布市察哈尔右翼前旗');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150927', '内蒙古自治区乌兰察布市察哈尔右翼中旗');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150928', '内蒙古自治区乌兰察布市察哈尔右翼后旗');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150929', '内蒙古自治区乌兰察布市四子王旗');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150981', '内蒙古自治区乌兰察布市丰镇市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '152200', '内蒙古自治区兴安盟');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '152201', '内蒙古自治区兴安盟乌兰浩特市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '152202', '内蒙古自治区兴安盟阿尔山市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '152221', '内蒙古自治区兴安盟科尔沁右翼前旗');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '152222', '内蒙古自治区兴安盟科尔沁右翼中旗');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '152223', '内蒙古自治区兴安盟扎赉特旗');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '152224', '内蒙古自治区兴安盟突泉县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '152500', '内蒙古自治区锡林郭勒盟');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '152501', '内蒙古自治区锡林郭勒盟二连浩特市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '152502', '内蒙古自治区锡林郭勒盟锡林浩特市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '152522', '内蒙古自治区锡林郭勒盟阿巴嘎旗');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '152523', '内蒙古自治区锡林郭勒盟苏尼特左旗');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '152524', '内蒙古自治区锡林郭勒盟苏尼特右旗');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '152525', '内蒙古自治区锡林郭勒盟东乌珠穆沁旗');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '152526', '内蒙古自治区锡林郭勒盟西乌珠穆沁旗');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '152527', '内蒙古自治区锡林郭勒盟太仆寺旗');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '152528', '内蒙古自治区锡林郭勒盟镶黄旗');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '152529', '内蒙古自治区锡林郭勒盟正镶白旗');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '152530', '内蒙古自治区锡林郭勒盟正蓝旗');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '152531', '内蒙古自治区锡林郭勒盟多伦县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '152600', '内蒙古自治区乌兰察布盟');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '152900', '内蒙古自治区阿拉善盟');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '152921', '内蒙古自治区阿拉善盟阿拉善左旗');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '152922', '内蒙古自治区阿拉善盟阿拉善右旗');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '152923', '内蒙古自治区阿拉善盟额济纳旗');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210000', '辽宁省');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210100', '辽宁省沈阳市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210101', '辽宁省沈阳市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210102', '辽宁省沈阳市和平区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210103', '辽宁省沈阳市沈河区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210104', '辽宁省沈阳市大东区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210105', '辽宁省沈阳市皇姑区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210106', '辽宁省沈阳市铁西区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210111', '辽宁省沈阳市苏家屯区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210112', '辽宁省沈阳市东陵区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210113', '辽宁省沈阳市新城子区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210114', '辽宁省沈阳市于洪区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210122', '辽宁省沈阳市辽中县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210123', '辽宁省沈阳市康平县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210124', '辽宁省沈阳市法库县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210181', '辽宁省沈阳市新民市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210200', '辽宁省大连市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210201', '辽宁省大连市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210202', '辽宁省大连市中山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210203', '辽宁省大连市西岗区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210204', '辽宁省大连市沙河口区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210211', '辽宁省大连市甘井子区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210212', '辽宁省大连市旅顺口区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210213', '辽宁省大连市金州区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210224', '辽宁省大连市长海县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210281', '辽宁省大连市瓦房店市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210282', '辽宁省大连市普兰店市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210283', '辽宁省大连市庄河市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210300', '辽宁省鞍山市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210301', '辽宁省鞍山市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210302', '辽宁省鞍山市铁东区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210303', '辽宁省鞍山市铁西区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210304', '辽宁省鞍山市立山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210311', '辽宁省鞍山市千山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210321', '辽宁省鞍山市台安县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210323', '辽宁省鞍山市岫岩满族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210381', '辽宁省鞍山市海城市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210400', '辽宁省抚顺市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210401', '辽宁省抚顺市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210402', '辽宁省抚顺市新抚区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210403', '辽宁省抚顺市东洲区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210404', '辽宁省抚顺市望花区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210411', '辽宁省抚顺市顺城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210421', '辽宁省抚顺市抚顺县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210422', '辽宁省抚顺市新宾满族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210423', '辽宁省抚顺市清原满族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210500', '辽宁省本溪市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210501', '辽宁省本溪市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210502', '辽宁省本溪市平山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210503', '辽宁省本溪市溪湖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210504', '辽宁省本溪市明山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210505', '辽宁省本溪市南芬区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210521', '辽宁省本溪市本溪满族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210522', '辽宁省本溪市桓仁满族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210600', '辽宁省丹东市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210601', '辽宁省丹东市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210602', '辽宁省丹东市元宝区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210603', '辽宁省丹东市振兴区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210604', '辽宁省丹东市振安区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210624', '辽宁省丹东市宽甸满族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210681', '辽宁省丹东市东港市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210682', '辽宁省丹东市凤城市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210700', '辽宁省锦州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210701', '辽宁省锦州市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210702', '辽宁省锦州市古塔区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210703', '辽宁省锦州市凌河区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210711', '辽宁省锦州市太和区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210726', '辽宁省锦州市黑山县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210727', '辽宁省锦州市义县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210781', '辽宁省锦州市凌海市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210782', '辽宁省锦州市北宁市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210800', '辽宁省营口市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210801', '辽宁省营口市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210802', '辽宁省营口市站前区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210803', '辽宁省营口市西市区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210804', '辽宁省营口市鲅鱼圈区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210811', '辽宁省营口市老边区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210881', '辽宁省营口市盖州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210882', '辽宁省营口市大石桥市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210900', '辽宁省阜新市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210901', '辽宁省阜新市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210902', '辽宁省阜新市海州区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210903', '辽宁省阜新市新邱区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210904', '辽宁省阜新市太平区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210905', '辽宁省阜新市清河门区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210911', '辽宁省阜新市细河区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210921', '辽宁省阜新市阜新蒙古族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '210922', '辽宁省阜新市彰武县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '211000', '辽宁省辽阳市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '211001', '辽宁省辽阳市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '211002', '辽宁省辽阳市白塔区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '211003', '辽宁省辽阳市文圣区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '211004', '辽宁省辽阳市宏伟区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '211005', '辽宁省辽阳市弓长岭区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '211011', '辽宁省辽阳市太子河区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '211021', '辽宁省辽阳市辽阳县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '211081', '辽宁省辽阳市灯塔市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '211100', '辽宁省盘锦市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '211101', '辽宁省盘锦市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '211102', '辽宁省盘锦市双台子区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '211103', '辽宁省盘锦市兴隆台区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '211121', '辽宁省盘锦市大洼县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '211122', '辽宁省盘锦市盘山县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '211200', '辽宁省铁岭市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '211201', '辽宁省铁岭市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '211202', '辽宁省铁岭市银州区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '211204', '辽宁省铁岭市清河区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '211221', '辽宁省铁岭市铁岭县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '211223', '辽宁省铁岭市西丰县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '211224', '辽宁省铁岭市昌图县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '211281', '辽宁省铁岭市调兵山市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '211282', '辽宁省铁岭市开原市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '211300', '辽宁省朝阳市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '211301', '辽宁省朝阳市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '211302', '辽宁省朝阳市双塔区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '211303', '辽宁省朝阳市龙城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '211321', '辽宁省朝阳市朝阳县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '211322', '辽宁省朝阳市建平县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '211324', '辽宁省朝阳市喀喇沁左翼蒙古族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '211381', '辽宁省朝阳市北票市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '211382', '辽宁省朝阳市凌源市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '211400', '辽宁省葫芦岛市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '211401', '辽宁省葫芦岛市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '211402', '辽宁省葫芦岛市连山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '211403', '辽宁省葫芦岛市龙港区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '211404', '辽宁省葫芦岛市南票区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '211421', '辽宁省葫芦岛市绥中县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '211422', '辽宁省葫芦岛市建昌县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '211481', '辽宁省葫芦岛市兴城市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220000', '吉林省');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220100', '吉林省长春市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220101', '吉林省长春市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220102', '吉林省长春市南关区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220103', '吉林省长春市宽城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220104', '吉林省长春市朝阳区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220105', '吉林省长春市二道区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220106', '吉林省长春市绿园区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220112', '吉林省长春市双阳区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220122', '吉林省长春市农安县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220181', '吉林省长春市九台市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220182', '吉林省长春市榆树市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220183', '吉林省长春市德惠市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220200', '吉林省吉林市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220201', '吉林省吉林市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220202', '吉林省吉林市昌邑区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220203', '吉林省吉林市龙潭区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '10000', '危害国家安全案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '10100', '背叛、分裂国家案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '10110', '背叛国家案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '10120', '分裂国家案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '10130', '煽动分裂国家案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '10140', '武装叛乱、暴乱案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '10150', '策动武装暴乱案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '10160', '颠覆国家政权案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '10170', '煽动颠履国家政权案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '10180', '资助危害国家安全犯罪活动案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '10200', '叛变、叛逃案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '10210', '投敌叛变案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '10220', '叛逃案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '10300', '间谍、资敌案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '10310', '间谍案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '10320', '资敌案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '10400', '为境外窃取、刺探、收买、非法提供国家秘密情报案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '10410', '为境外窃取国家秘密情报案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '10420', '为境外刺探国家秘密情报案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '10430', '为境外收买国家秘密情报案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '10440', '为境外非法提供国家秘密情报案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '19900', '其他危害国家安全案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20000', '危害公共安全案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20100', '以危险方法危害公共安全案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20101', '放火案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20102', '决水案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20103', '爆炸案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20104', '投毒案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20109', '以其他危险方法危害公共安全案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20111', '失火案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20112', '过失决水案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20113', '过失爆炸案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20114', '过失投毒案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20119', '过失以其他危险方法危害公共安全案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20200', '危害交通运输、公用设备安全案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20201', '破坏交通工具案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20202', '破坏交通设施案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20203', '破坏电力设备案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20204', '破坏燃气设备案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20205', '破坏易燃易爆设备案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20206', '破坏广播电视设施案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20207', '破坏公用电信设施案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20221', '过失损坏交通工具案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20222', '过失损坏交通设施案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20223', '过失损坏电力设备案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20224', '过失损坏燃气设备案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20225', '过失损坏易燃易爆设备案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20226', '过失损坏广播电视设施案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20227', '过失损坏公用电信设施案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20300', '实施恐怖、劫持案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20301', '组织、领导参加恐怖组织案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20311', '劫持航空器案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20312', '劫持船只案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20313', '劫持汽车案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20331', '暴力危及飞行安全案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20400', '非法制造、买卖、运输、邮寄、储存枪支弹药、爆炸物案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20401', '非法制造枪支弹药案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20402', '非法买卖枪支弹药案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20403', '非法运输枪支弹药案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20404', '非法邮寄枪支弹药案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20405', '非法储存枪支弹药案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20406', '非法制造爆炸物案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20407', '非法买卖爆炸物案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20408', '非法运输爆炸物案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20409', '非法邮寄爆炸物案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20410', '非法储存爆炸物案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20411', '非法买卖核材料案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20412', '非法运输核材料案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20500', '违反枪支弹药管理案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20501', '企业违规制造枪支案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20502', '企业违规销售枪支案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20511', '非法持有枪支弹药案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20512', '非法私藏枪支弹药案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20521', '非法出借枪支案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20522', '非法出租枪支案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20531', '丢失枪支不报案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20541', '非法携带枪支弹药危及公共安全案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20600', '违反危险物品管理案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20601', '非法携带管制刀具危及公共安全案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20602', '非法携带危险品危及公共安全案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20700', '重大责任事故案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20701', '重大飞行事故案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20702', '铁路运营安全事故案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20703', '交通肇事案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20711', '重大劳动安全事故案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20712', '危险物品肇事案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20713', '工程重大安全事故案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20714', '教育设施重大安全事故案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20715', '消防责任事故案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20800', '抢劫枪支、弹药、爆炸物案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20810', '抢劫枪支、弹药案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20811', '抢劫国家机关枪支弹药案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20812', '抢劫军、警、民兵枪支弹药案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20820', '抢劫爆炸物案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20821', '抢劫国家机关爆炸物案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20822', '抢劫军、警、民兵爆炸物案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20900', '盗窃、抢夺枪支、弹药、爆炸物案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20910', '盗窃枪支、弹药、爆炸物案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20911', '盗窃枪支、弹药案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20912', '盗窃国家机关枪支、弹药案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20913', '盗窃军、警、民兵枪支、弹药案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20914', '盗窃爆炸物案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20915', '盗窃国家机关爆炸物案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20916', '盗窃军、警、民兵爆炸物案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20920', '抢夺枪支弹药、爆炸物案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130724', '河北省张家口市沽源县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130725', '河北省张家口市尚义县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130726', '河北省张家口市蔚县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130727', '河北省张家口市阳原县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130728', '河北省张家口市怀安县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130729', '河北省张家口市万全县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130730', '河北省张家口市怀来县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130731', '河北省张家口市涿鹿县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130732', '河北省张家口市赤城县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130733', '河北省张家口市崇礼县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130800', '河北省承德市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130801', '河北省承德市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130802', '河北省承德市双桥区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130803', '河北省承德市双滦区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130804', '河北省承德市鹰手营子矿区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130821', '河北省承德市承德县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130822', '河北省承德市兴隆县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130823', '河北省承德市平泉县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130824', '河北省承德市滦平县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130825', '河北省承德市隆化县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130826', '河北省承德市丰宁满族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130827', '河北省承德市宽城满族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130828', '河北省承德市围场满族蒙古族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130900', '河北省沧州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130901', '河北省沧州市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130902', '河北省沧州市新华区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130903', '河北省沧州市运河区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130921', '河北省沧州市沧县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130922', '河北省沧州市青县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130923', '河北省沧州市东光县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130924', '河北省沧州市海兴县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130925', '河北省沧州市盐山县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130926', '河北省沧州市肃宁县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130927', '河北省沧州市南皮县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130928', '河北省沧州市吴桥县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130929', '河北省沧州市献县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130930', '河北省沧州市孟村回族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130981', '河北省沧州市泊头市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130982', '河北省沧州市任丘市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130983', '河北省沧州市黄骅市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130984', '河北省沧州市河间市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '131000', '河北省廊坊市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '131001', '河北省廊坊市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '131002', '河北省廊坊市安次区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '131003', '河北省廊坊市广阳区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '131022', '河北省廊坊市固安县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '131023', '河北省廊坊市永清县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '131024', '河北省廊坊市香河县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '131025', '河北省廊坊市大城县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '131026', '河北省廊坊市文安县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '131028', '河北省廊坊市大厂回族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '131081', '河北省廊坊市霸州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '131082', '河北省廊坊市三河市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '131100', '河北省衡水市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '131101', '河北省衡水市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '131102', '河北省衡水市桃城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '131121', '河北省衡水市枣强县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '131122', '河北省衡水市武邑县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '131123', '河北省衡水市武强县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '131124', '河北省衡水市饶阳县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '131125', '河北省衡水市安平县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '131126', '河北省衡水市故城县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '131127', '河北省衡水市景县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '131128', '河北省衡水市阜城县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '131181', '河北省衡水市冀州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '131182', '河北省衡水市深州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140000', '山西省');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140100', '山西省太原市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140101', '山西省太原市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140105', '山西省太原市小店区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140106', '山西省太原市迎泽区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140107', '山西省太原市杏花岭区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140108', '山西省太原市尖草坪区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140109', '山西省太原市万柏林区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140110', '山西省太原市晋源区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140121', '山西省太原市清徐县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140122', '山西省太原市阳曲县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140123', '山西省太原市娄烦县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140181', '山西省太原市古交市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140200', '山西省大同市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140201', '山西省大同市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140202', '山西省大同市城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140203', '山西省大同市矿区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140211', '山西省大同市南郊区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140212', '山西省大同市新荣区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140221', '山西省大同市阳高县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140222', '山西省大同市天镇县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140223', '山西省大同市广灵县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140224', '山西省大同市灵丘县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140225', '山西省大同市浑源县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140226', '山西省大同市左云县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140227', '山西省大同市大同县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140300', '山西省阳泉市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140301', '山西省阳泉市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140302', '山西省阳泉市城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140303', '山西省阳泉市矿区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140311', '山西省阳泉市郊区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140321', '山西省阳泉市平定县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140322', '山西省阳泉市盂县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140400', '山西省长治市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140401', '山西省长治市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140402', '山西省长治市城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140411', '山西省长治市郊区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140421', '山西省长治市长治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140423', '山西省长治市襄垣县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140424', '山西省长治市屯留县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140425', '山西省长治市平顺县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140426', '山西省长治市黎城县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140427', '山西省长治市壶关县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140428', '山西省长治市长子县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140429', '山西省长治市武乡县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140430', '山西省长治市沁县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140431', '山西省长治市沁源县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140481', '山西省长治市潞城市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140500', '山西省晋城市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140501', '山西省晋城市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140502', '山西省晋城市城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140521', '山西省晋城市沁水县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140522', '山西省晋城市阳城县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140524', '山西省晋城市陵川县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140525', '山西省晋城市泽州县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140581', '山西省晋城市高平市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140600', '山西省朔州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140601', '山西省朔州市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140602', '山西省朔州市朔城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140603', '山西省朔州市平鲁区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140621', '山西省朔州市山阴县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140622', '山西省朔州市应县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140623', '山西省朔州市右玉县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140624', '山西省朔州市怀仁县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140700', '山西省晋中市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140701', '山西省晋中市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140702', '山西省晋中市榆次区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140721', '山西省晋中市榆社县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140722', '山西省晋中市左权县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140723', '山西省晋中市和顺县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140724', '山西省晋中市昔阳县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140725', '山西省晋中市寿阳县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140726', '山西省晋中市太谷县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140727', '山西省晋中市祁县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140728', '山西省晋中市平遥县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140729', '山西省晋中市灵石县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140781', '山西省晋中市介休市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140800', '山西省运城市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140801', '山西省运城市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140802', '山西省运城市盐湖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140821', '山西省运城市临猗县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140822', '山西省运城市万荣县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140823', '山西省运城市闻喜县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140824', '山西省运城市稷山县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140825', '山西省运城市新绛县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140826', '山西省运城市绛县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140827', '山西省运城市垣曲县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140828', '山西省运城市夏县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140829', '山西省运城市平陆县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140830', '山西省运城市芮城县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140881', '山西省运城市永济市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140882', '山西省运城市河津市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140900', '山西省忻州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140901', '山西省忻州市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140902', '山西省忻州市忻府区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140921', '山西省忻州市定襄县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140922', '山西省忻州市五台县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140923', '山西省忻州市代县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140924', '山西省忻州市繁峙县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140925', '山西省忻州市宁武县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140926', '山西省忻州市静乐县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140927', '山西省忻州市神池县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140928', '山西省忻州市五寨县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140929', '山西省忻州市岢岚县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140930', '山西省忻州市河曲县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140931', '山西省忻州市保德县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140932', '山西省忻州市偏关县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '140981', '山西省忻州市原平市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '141000', '山西省临汾市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '141001', '山西省临汾市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '141002', '山西省临汾市尧都区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '141021', '山西省临汾市曲沃县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '141022', '山西省临汾市翼城县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '141023', '山西省临汾市襄汾县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '141024', '山西省临汾市洪洞县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '141025', '山西省临汾市古县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '141026', '山西省临汾市安泽县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '141027', '山西省临汾市浮山县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '141028', '山西省临汾市吉县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '141029', '山西省临汾市乡宁县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '141030', '山西省临汾市大宁县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '141031', '山西省临汾市隰县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '141032', '山西省临汾市永和县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '141033', '山西省临汾市蒲县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '141034', '山西省临汾市汾西县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '141081', '山西省临汾市侯马市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '141082', '山西省临汾市霍州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '141100', '山西省吕梁市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '141101', '山西省吕梁市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '141102', '山西省吕梁市离石区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '141121', '山西省吕梁市文水县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '141122', '山西省吕梁市交城县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '141123', '山西省吕梁市兴县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '141124', '山西省吕梁市临县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '141125', '山西省吕梁市柳林县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '141126', '山西省吕梁市石楼县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '141127', '山西省吕梁市岚县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '141128', '山西省吕梁市方山县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '141129', '山西省吕梁市中阳县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '141130', '山西省吕梁市交口县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '141181', '山西省吕梁市孝义市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '141182', '山西省吕梁市汾阳市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150000', '内蒙古自治区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150100', '内蒙古自治区呼和浩特市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150101', '内蒙古自治区呼和浩特市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150102', '内蒙古自治区呼和浩特市新城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150103', '内蒙古自治区呼和浩特市回民区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150104', '内蒙古自治区呼和浩特市玉泉区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150105', '内蒙古自治区呼和浩特市赛罕区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150121', '内蒙古自治区呼和浩特市土默特左旗');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150122', '内蒙古自治区呼和浩特市托克托县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150123', '内蒙古自治区呼和浩特市和林格尔县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150124', '内蒙古自治区呼和浩特市清水河县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150125', '内蒙古自治区呼和浩特市武川县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150200', '内蒙古自治区包头市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150201', '内蒙古自治区包头市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150202', '内蒙古自治区包头市东河区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150203', '内蒙古自治区包头市昆都仑区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150204', '内蒙古自治区包头市青山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150205', '内蒙古自治区包头市石拐区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150206', '内蒙古自治区包头市白云矿区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150207', '内蒙古自治区包头市九原区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150221', '内蒙古自治区包头市土默特右旗');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150222', '内蒙古自治区包头市固阳县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150223', '内蒙古自治区包头市达尔罕茂明安联合旗');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150300', '内蒙古自治区乌海市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150301', '内蒙古自治区乌海市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150302', '内蒙古自治区乌海市海勃湾区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150303', '内蒙古自治区乌海市海南区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150304', '内蒙古自治区乌海市乌达区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150400', '内蒙古自治区赤峰市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150401', '内蒙古自治区赤峰市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150402', '内蒙古自治区赤峰市红山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150403', '内蒙古自治区赤峰市元宝山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150404', '内蒙古自治区赤峰市松山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150421', '内蒙古自治区赤峰市阿鲁科尔沁旗');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150422', '内蒙古自治区赤峰市巴林左旗');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '150423', '内蒙古自治区赤峰市巴林右旗');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '70500', '破坏军事通信案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '70600', '故意提供不合格武器装备案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '70700', '故意提供不合格军事设施案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '70800', '过失提供不合格武器装备案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '70900', '过失提供不合格军事设施案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '71000', '聚众冲击军事禁区案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '71100', '聚众扰乱军事管理区秩序案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '71200', '冒充军人招摇撞骗案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '71300', '煽动军人逃离部队案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '71400', '雇用逃离部队军人案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '71500', '接送不合格兵员案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '71600', '伪造、变造、买卖部队公文、证件、印章案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '71700', '盗窃部队公文、证件、印章案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '71800', '抢夺部队公文、证件、印章案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '72000', '非法生产、买卖军用标志案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '72100', '战时拒绝、逃避征召案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '72200', '战时拒绝、逃避军事训练案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '72300', '战时拒绝、逃避服役案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '72400', '战时故意提供虚假敌情案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '72500', '战时造谣惑众扰乱军心案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '72600', '战时窝藏逃离部队军人案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '72700', '战时拒绝军事订货案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '72800', '战时延误军事订货案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '72900', '战时拒绝军事征用案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '79900', '其他危害国防利益案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '80000', '贪污贿赂案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '90000', '渎职案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '100000', '军人违反职责案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('BXQKDM', '表现情况代码', '1', '好');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('BXQKDM', '表现情况代码', '2', '中');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('BXQKDM', '表现情况代码', '3', '差');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('SEC_DPLYDM', '毒品来源代码', '1', '黑市购买');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('SEC_DPLYDM', '毒品来源代码', '2', '医生处方');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('SEC_DPLYDM', '毒品来源代码', '3', '朋友提供');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('SEC_DPLYDM', '毒品来源代码', '4', '其他');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '4', '阿富汗');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '8', '阿尔巴尼亚');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '10', '南极洲');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '12', '阿尔及利亚');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '16', '美属萨摩亚');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '20', '安道尔');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '24', '安哥拉');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '28', '安提瓜和巴布达');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '31', '阿塞拜疆');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '32', '阿根廷');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '36', '澳大利亚');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '40', '奥地利');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '44', '巴哈马');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '48', '巴林');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '50', '孟加拉国');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '51', '亚美尼亚');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '52', '巴巴多斯');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '56', '比利时');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '60', '百慕大');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '64', '不丹');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '68', '玻利维亚');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '70', '波黑');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '72', '博茨瓦纳');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '74', '布维岛');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '76', '巴西');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '84', '伯利兹');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '86', '英属印度洋领地');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '90', '所罗门群岛');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '92', '英属维尔京群岛');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '96', '文莱');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '100', '保加利亚');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '104', '缅甸');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '108', '布隆迪');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '112', '白俄罗斯');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '116', '柬埔寨');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '120', '喀麦隆');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '124', '加拿大');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '132', '佛得角');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '136', '开曼群岛');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '140', '中非');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '144', '斯里兰卡');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '148', '乍得');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '152', '智利');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '156', '中国');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '158', '中国台湾');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '162', '圣诞岛');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '166', '科科斯(基林)群岛');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '170', '哥伦比亚');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '174', '科摩罗');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '175', '马约特');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '178', '刚果(布)');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '180', '刚果(金)');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '184', '库克群岛');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '188', '哥斯达黎加');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '191', '克罗地亚');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '192', '古巴');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '196', '塞浦路斯');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '203', '捷克');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '204', '贝宁');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '208', '丹麦');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '212', '多米尼克');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '214', '多米尼加共和国');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '218', '厄瓜多尔');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '222', '萨尔瓦多');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '226', '赤道几内亚');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '231', '埃塞俄比亚');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '232', '厄立特里亚');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '233', '爱沙尼亚');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '234', '法罗群岛');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '238', '福克兰群岛(马尔维纳斯)');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '242', '斐济');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '246', '芬兰');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '250', '法国');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '254', '法属圭亚那');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '258', '法属波利尼西亚');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '260', '法属南部领地');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '262', '吉布提');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '266', '加蓬');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '268', '格鲁吉亚');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '270', '冈比亚');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '275', '巴勒斯坦');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '276', '德国');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '288', '加纳');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '292', '直布罗陀');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '296', '基里巴斯');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '300', '希腊');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '304', '格陵兰');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '308', '格林纳达');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '312', '瓜德罗普');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '316', '关岛');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '320', '危地马拉');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '324', '几内亚');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '328', '圭亚那');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '332', '海地');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '334', '赫德岛和麦克唐纳岛');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '336', '梵蒂冈');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '340', '洪都拉斯');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '344', '香港');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '348', '匈牙利');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '352', '冰岛');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '356', '印度');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '360', '印度尼西亚');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '364', '伊朗');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '368', '伊拉克');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '372', '爱尔兰');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '376', '以色列');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '380', '意大利');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '384', '科特迪瓦');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '388', '牙买加');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '392', '日本');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '398', '哈萨克斯坦');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '400', '约旦');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '404', '肯尼亚');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '408', '朝鲜');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '410', '韩国');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '414', '科威特');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '417', '吉尔吉斯斯坦');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '418', '老挝');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '422', '黎巴嫩');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '426', '莱索托');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '428', '拉脱维亚');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '430', '利比里亚');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '434', '利比亚');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '438', '列支敦士登');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '440', '立陶宛');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '442', '卢森堡');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '446', '澳门');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '450', '马达加斯加');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '454', '马拉维');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '458', '马来西亚');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '462', '马尔代夫');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '466', '马里');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '470', '马耳他');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '474', '马提尼克');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '478', '毛里塔尼亚');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '480', '毛里求斯');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '484', '墨西哥');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '492', '摩纳哥');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '496', '蒙古');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '498', '摩尔多瓦');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '500', '蒙特塞拉特');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '504', '摩洛哥');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '508', '莫桑比克');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '512', '阿曼');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '516', '纳米比亚');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '520', '瑙鲁');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '524', '尼泊尔');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '528', '荷兰');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '530', '荷属安的列斯');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '533', '阿鲁巴');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '540', '新喀里多尼亚');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '548', '瓦努阿图');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '554', '新西兰');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '558', '尼加拉瓜');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '562', '尼日尔');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '566', '尼日利亚');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '570', '纽埃');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '574', '诺福克岛');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '578', '挪威');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '580', '北马里亚纳');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '581', '美国本土外小岛屿');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '583', '密克罗尼西亚联邦');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '584', '马绍尔群岛');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '585', '帕劳');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '586', '巴基斯坦');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '591', '巴拿马');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '598', '巴布亚新几内亚');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '600', '巴拉圭');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '604', '秘鲁');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '608', '菲律宾');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '612', '皮特凯恩');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '616', '波兰');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '620', '葡萄牙');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '624', '几内亚比绍');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '626', '东帝汶');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '630', '波多黎各');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '634', '卡塔尔');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '638', '留尼汪');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '642', '罗马尼亚');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '643', '俄罗斯');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '646', '卢旺达');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '654', '圣赫勒拿');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '659', '圣基茨和尼维斯');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '660', '安圭拉');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '662', '圣卢西亚');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '666', '圣皮埃尔和密克隆');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '670', '圣文森特和格林纳丁斯');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '674', '圣马力诺');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '678', '圣多美和普林西比');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '682', '沙特阿拉伯');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '686', '塞内加尔');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '690', '塞舌尔');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '694', '塞拉利昂');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '702', '新加坡');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '703', '斯洛伐克');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '704', '越南');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '705', '斯洛文尼亚');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '706', '索马里');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '710', '南非');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '716', '津巴布韦');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '724', '西班牙');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '732', '西撒哈拉');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '736', '苏丹');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '740', '苏里南');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '744', '斯瓦尔巴岛和扬马延岛');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '748', '斯威士兰');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '752', '瑞典');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '756', '瑞士');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '760', '叙利亚');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '762', '塔吉克斯坦');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '764', '泰国');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '768', '多哥');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '772', '托克劳');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '776', '汤加');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '780', '特立尼达和多巴哥');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '784', '阿联酋');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '788', '突尼斯');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '792', '土耳其');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '795', '土库曼斯坦');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '796', '特克斯和凯科斯群岛');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '798', '图瓦卢');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '800', '乌干达');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '804', '乌克兰');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '807', '前南马斯顿');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '818', '埃及');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '826', '英国');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '834', '坦桑尼亚');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '840', '美国');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '850', '美属维尔京群岛');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '854', '布基纳法索');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '858', '乌拉圭');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '860', '乌兹别克斯坦');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '862', '委内瑞拉');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '876', '瓦利斯和富图纳');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '882', '萨摩亚');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '887', '也门');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '891', '南斯拉夫');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '894', '赞比亚');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('GJQYBM', '国家区域编码', '999', '未知国籍');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '110000', '北京市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '110100', '北京市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '110101', '北京市东城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '110102', '北京市西城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '110103', '北京市崇文区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '110104', '北京市宣武区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '110105', '北京市朝阳区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '110106', '北京市丰台区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '110107', '北京市石景山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '110108', '北京市海淀区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '110109', '北京市门头沟区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '110111', '北京市房山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '110112', '北京市通州区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '110113', '北京市顺义区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '110114', '北京市昌平区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '110115', '北京市大兴区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '110116', '北京市怀柔区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '110117', '北京市平谷区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '110200', '北京市县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '110228', '北京市密云县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '110229', '北京市延庆县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '120000', '天津市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '120100', '天津市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '120101', '天津市和平区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '120102', '天津市河东区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '120103', '天津市河西区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '120104', '天津市南开区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '120105', '天津市河北区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '120106', '天津市红桥区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '120107', '天津市塘沽区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '120108', '天津市汉沽区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '120109', '天津市大港区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '120110', '天津市东丽区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '120111', '天津市西青区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '120112', '天津市津南区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '120113', '天津市北辰区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '120114', '天津市武清区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '120115', '天津市宝坻区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '120200', '天津市县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '120221', '天津市宁河县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '120223', '天津市静海县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '120225', '天津市蓟县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130000', '河北省');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130100', '河北省石家庄市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130101', '河北省石家庄市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130102', '河北省石家庄市长安区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130103', '河北省石家庄市桥东区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130104', '河北省石家庄市桥西区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130105', '河北省石家庄市新华区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130107', '河北省石家庄市井陉矿区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130108', '河北省石家庄市裕华区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130121', '河北省石家庄市井陉县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130123', '河北省石家庄市正定县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130124', '河北省石家庄市栾城县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130125', '河北省石家庄市行唐县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130126', '河北省石家庄市灵寿县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130127', '河北省石家庄市高邑县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130128', '河北省石家庄市深泽县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130129', '河北省石家庄市赞皇县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130130', '河北省石家庄市无极县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130131', '河北省石家庄市平山县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130132', '河北省石家庄市元氏县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130133', '河北省石家庄市赵县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130181', '河北省石家庄市辛集市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130182', '河北省石家庄市藁城市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130183', '河北省石家庄市晋州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130184', '河北省石家庄市新乐市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130185', '河北省石家庄市鹿泉市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130200', '河北省唐山市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130201', '河北省唐山市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130202', '河北省唐山市路南区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130203', '河北省唐山市路北区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130204', '河北省唐山市古冶区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130205', '河北省唐山市开平区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130207', '河北省唐山市丰南区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130208', '河北省唐山市丰润区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130223', '河北省唐山市滦县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130224', '河北省唐山市滦南县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130225', '河北省唐山市乐亭县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130227', '河北省唐山市迁西县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130229', '河北省唐山市玉田县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130230', '河北省唐山市唐海县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130281', '河北省唐山市遵化市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130283', '河北省唐山市迁安市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130300', '河北省秦皇岛市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130301', '河北省秦皇岛市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130302', '河北省秦皇岛市海港区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130303', '河北省秦皇岛市山海关区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130304', '河北省秦皇岛市北戴河区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130321', '河北省秦皇岛市青龙满族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130322', '河北省秦皇岛市昌黎县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130323', '河北省秦皇岛市抚宁县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130324', '河北省秦皇岛市卢龙县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130400', '河北省邯郸市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130401', '河北省邯郸市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130402', '河北省邯郸市邯山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130403', '河北省邯郸市丛台区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130404', '河北省邯郸市复兴区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130406', '河北省邯郸市峰峰矿区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130421', '河北省邯郸市邯郸县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130423', '河北省邯郸市临漳县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130424', '河北省邯郸市成安县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130425', '河北省邯郸市大名县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130426', '河北省邯郸市涉县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130427', '河北省邯郸市磁县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130428', '河北省邯郸市肥乡县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130429', '河北省邯郸市永年县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130430', '河北省邯郸市邱县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130431', '河北省邯郸市鸡泽县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130432', '河北省邯郸市广平县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130433', '河北省邯郸市馆陶县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130434', '河北省邯郸市魏县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130435', '河北省邯郸市曲周县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130481', '河北省邯郸市武安市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130500', '河北省邢台市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130501', '河北省邢台市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130502', '河北省邢台市桥东区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130503', '河北省邢台市桥西区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130521', '河北省邢台市邢台县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130522', '河北省邢台市临城县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130523', '河北省邢台市内丘县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130524', '河北省邢台市柏乡县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130525', '河北省邢台市隆尧县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130526', '河北省邢台市任县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130527', '河北省邢台市南和县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130528', '河北省邢台市宁晋县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130529', '河北省邢台市巨鹿县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130530', '河北省邢台市新河县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130531', '河北省邢台市广宗县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130532', '河北省邢台市平乡县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130533', '河北省邢台市威县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130534', '河北省邢台市清河县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130535', '河北省邢台市临西县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130581', '河北省邢台市南宫市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130582', '河北省邢台市沙河市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130600', '河北省保定市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130601', '河北省保定市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130602', '河北省保定市新市区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130603', '河北省保定市北市区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130604', '河北省保定市南市区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130621', '河北省保定市满城县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130622', '河北省保定市清苑县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130623', '河北省保定市涞水县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130624', '河北省保定市阜平县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130625', '河北省保定市徐水县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130626', '河北省保定市定兴县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130627', '河北省保定市唐县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130628', '河北省保定市高阳县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130629', '河北省保定市容城县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130630', '河北省保定市涞源县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130631', '河北省保定市望都县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130632', '河北省保定市安新县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130633', '河北省保定市易县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130634', '河北省保定市曲阳县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130635', '河北省保定市蠡县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130636', '河北省保定市顺平县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130637', '河北省保定市博野县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130638', '河北省保定市雄县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130681', '河北省保定市涿州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130682', '河北省保定市定州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130683', '河北省保定市安国市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130684', '河北省保定市高碑店市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130700', '河北省张家口市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130701', '河北省张家口市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130702', '河北省张家口市桥东区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130703', '河北省张家口市桥西区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130705', '河北省张家口市宣化区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130706', '河北省张家口市下花园区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130721', '河北省张家口市宣化县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130722', '河北省张家口市张北县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '130723', '河北省张家口市康保县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340204', '安徽省芜湖市新芜区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340207', '安徽省芜湖市鸠江区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340221', '安徽省芜湖市芜湖县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340222', '安徽省芜湖市繁昌县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340223', '安徽省芜湖市南陵县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340300', '安徽省蚌埠市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340301', '安徽省蚌埠市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340302', '安徽省蚌埠市东市区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340303', '安徽省蚌埠市中市区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340304', '安徽省蚌埠市西市区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340311', '安徽省蚌埠市郊区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340321', '安徽省蚌埠市怀远县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340322', '安徽省蚌埠市五河县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340323', '安徽省蚌埠市固镇县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340400', '安徽省淮南市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340401', '安徽省淮南市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340402', '安徽省淮南市大通区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340403', '安徽省淮南市田家庵区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340404', '安徽省淮南市谢家集区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340405', '安徽省淮南市八公山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340406', '安徽省淮南市潘集区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340421', '安徽省淮南市凤台县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340500', '安徽省马鞍山市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340501', '安徽省马鞍山市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340502', '安徽省马鞍山市金家庄区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340503', '安徽省马鞍山市花山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340504', '安徽省马鞍山市雨山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340521', '安徽省马鞍山市当涂县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340600', '安徽省淮北市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340601', '安徽省淮北市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340602', '安徽省淮北市杜集区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340603', '安徽省淮北市相山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340604', '安徽省淮北市烈山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340621', '安徽省淮北市濉溪县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340700', '安徽省铜陵市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340701', '安徽省铜陵市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340702', '安徽省铜陵市铜官山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340703', '安徽省铜陵市狮子山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340711', '安徽省铜陵市郊区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340721', '安徽省铜陵市铜陵县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340800', '安徽省安庆市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340801', '安徽省安庆市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340802', '安徽省安庆市迎江区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340803', '安徽省安庆市大观区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340811', '安徽省安庆市郊区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340822', '安徽省安庆市怀宁县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340823', '安徽省安庆市枞阳县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340824', '安徽省安庆市潜山县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340825', '安徽省安庆市太湖县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340826', '安徽省安庆市宿松县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340827', '安徽省安庆市望江县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340828', '安徽省安庆市岳西县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340881', '安徽省安庆市桐城市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341000', '安徽省黄山市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341001', '安徽省黄山市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341002', '安徽省黄山市屯溪区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341003', '安徽省黄山市黄山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341004', '安徽省黄山市徽州区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341021', '安徽省黄山市歙县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341022', '安徽省黄山市休宁县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341023', '安徽省黄山市黟县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341024', '安徽省黄山市祁门县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341100', '安徽省滁州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341101', '安徽省滁州市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341102', '安徽省滁州市琅琊区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341103', '安徽省滁州市南谯区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341122', '安徽省滁州市来安县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341124', '安徽省滁州市全椒县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341125', '安徽省滁州市定远县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341126', '安徽省滁州市凤阳县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341181', '安徽省滁州市天长市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341182', '安徽省滁州市明光市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341200', '安徽省阜阳市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341201', '安徽省阜阳市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341202', '安徽省阜阳市颍州区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341203', '安徽省阜阳市颍东区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341204', '安徽省阜阳市颍泉区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341221', '安徽省阜阳市临泉县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341222', '安徽省阜阳市太和县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341225', '安徽省阜阳市阜南县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341226', '安徽省阜阳市颍上县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341282', '安徽省阜阳市界首市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341300', '安徽省宿州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341301', '安徽省宿州市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341302', '安徽省宿州市墉桥区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341321', '安徽省宿州市砀山县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341322', '安徽省宿州市萧县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341323', '安徽省宿州市灵璧县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341324', '安徽省宿州市泗县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341400', '安徽省巢湖市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341401', '安徽省巢湖市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341402', '安徽省巢湖市居巢区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341421', '安徽省巢湖市庐江县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341422', '安徽省巢湖市无为县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341423', '安徽省巢湖市含山县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341424', '安徽省巢湖市和县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341500', '安徽省六安市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341501', '安徽省六安市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341502', '安徽省六安市金安区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341503', '安徽省六安市裕安区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341521', '安徽省六安市寿县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341522', '安徽省六安市霍邱县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341523', '安徽省六安市舒城县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341524', '安徽省六安市金寨县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20921', '抢夺枪支、弹药案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20922', '抢夺国家机关枪支弹药案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20923', '抢夺军、警、民兵枪支弹药案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20924', '抢夺爆炸物案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20925', '抢夺国家机关爆炸物案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '20926', '抢夺军、警、民兵爆炸物案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '29900', '其他危害公共安全案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30000', '破坏社会主义市场经济秩序案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30100', '生产、销售伪劣商品（产品）案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30101', '生产、销售伪劣产品案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30102', '生产、销售假药案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30103', '生产、销售劣药案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30104', '生产、销售伪劣兽药案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30105', '生产、销售伪劣农药案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30111', '生产销售伪劣化肥案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30121', '生产销售伪劣种子案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30131', '生产、销售不符合卫生标准的食品案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30132', '生产、销售有毒、有害食品案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30141', '生产、销售不符合标准的医用器材案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30151', '生产、销售不符合安全标准的产品案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30161', '生产销售不符合卫生标准的化妆品案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30200', '走私案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30201', '走私武器、弹药案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30210', '走私核材料案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30220', '走私文物案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30230', '走私假币案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30240', '走私贵重金属案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30241', '走私黄金案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30242', '走私白银案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30250', '走私珍贵动物及其制品案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30260', '走私珍稀植物及其制品案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30270', '走私淫秽物品案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30280', '走私一般货物物品案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30290', '走私固体废物案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30300', '妨害对公司、企业管理秩序案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30301', '虚报注册资本案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30311', '虚假出资案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30312', '抽逃出资案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30321', '欺诈发行股票案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30322', '欺诈发行债券案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30331', '提供虚假财会报告案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30332', '妨害清算案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30341', '公司、企业人员受贿案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30342', '对公司、企业人员行贿案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30351', '非法经营同类营业案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30352', '为亲友非法牟利案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30361', '签订、履行合同失职被骗案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30371', '构私舞弊造成破产案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30372', '拘私舞弊造成亏损案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30373', '拘私舞弊低价折股国有资产案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30374', '拘私舞弊低价出售国有资产案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30400', '破坏金融管理秩序案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30401', '伪造货币案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30411', '出售假币案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30415', '购买假币案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30420', '运输假币案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30421', '金融工作人员购买假币以假币换取货币案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30440', '持有、使用假币案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30441', '变造货币案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30442', '伪造、变造国库券案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30443', '伪造、变造国家其他有价证券案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30444', '伪造、变造股票案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30445', '伪造、变造公司企业债券案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30446', '伪造、变造金融票证案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30447', '伪造、变造、转让金融机构经营许可证案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30451', '擅自设立金融机构案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30452', '转让金融机构许可证案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30453', '高利转贷案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30454', '非法吸收公众存款案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30455', '擅自发行股票、公司企业债券案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30456', '内幕交易、泄露内幕信息案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30457', '编造并传播证券交易虚假信息案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30458', '诱骗投资者买卖证券案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30459', '操纵证券交易价格案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30460', '保险公司人员虚假理赔案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30461', '金融机构人员受贿案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30462', '违法发放贷款案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30463', '违法向关系人发放货款案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30464', '用帐外客户资金非法拆借、发放贷款案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30466', '非法出具金融票证案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30471', '对违法票据承兑付款、保证案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30474', '逃汇案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30475', '套汇案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30476', '骗汇案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30477', '洗钱案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30478', '非法买卖外汇案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30500', '金融诈骗案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30510', '集资诈骗案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30520', '贷款诈骗案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30530', '票据诈骗案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30540', '金融凭证诈骗案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30550', '信用证诈骗案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30560', '信用卡诈骗案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30570', '有价证券诈骗案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30580', '保险诈骗案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30600', '危害税收征管案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30601', '偷税案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30602', '抗税案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30603', '逃避追缴欠税案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30604', '骗取出口退税案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30605', '骗取国家出口免征税款案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30611', '虚开增值税专用发票用于骗取出口退税、抵扣税款发票案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30612', '伪造增值税专用发票案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30613', '出售伪造的增值税专用发票案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30621', '非法出售增值税专用发票案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30622', '非法购买增值税专用发票案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30623', '购买伪造的增值税专用发票案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30631', '非法制造用于骗取出口退税、抵扣税款发票案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30632', '出售非法制造的用于骗取出口退税、抵扣税款发票案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30633', '非法制造发票案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30641', '出售非法制造的发票案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30651', '非法出售用于骗取出口退税、抵扣税款发票案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30661', '非法出售发票案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30662', '盗窃增值税专用发票案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30663', '盗窃退税、抵扣税款专用发票案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30700', '侵犯知识产权案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30701', '假冒注册商标案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30710', '销售假冒注册商标的商品案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30720', '非法制造的注册商标标识案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30730', '非法出版物案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30740', '销售非法制造的注册商标标识案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30750', '假冒专利案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30760', '侵犯著作权案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30770', '销售侵权复制品案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30780', '侵犯商业秘密案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30800', '扰乱市场秩序案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30801', '损害商业信誉案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30802', '损害商品声誉案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30803', '虚假广告案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30804', '串通投标案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30805', '合同诈骗案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30806', '非法经营案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30807', '强迫交易案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30811', '伪造车票案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30812', '伪造船票案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30813', '伪造邮票案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30819', '伪造其他有价票证案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30821', '倒卖伪造车票案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30822', '倒卖伪造船票案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30823', '倒卖伪造邮票案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30829', '倒卖伪造其他有价票证案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30831', '倒卖车票案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30832', '倒卖船票案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30841', '非法转让土地使用权案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30842', '非法倒卖土地使用权案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30851', '中介组织人员提供虚假证明文件案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30852', '中介组织人员出具证明文件重大失实案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '30861', '逃避商检案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '39900', '其他破坏社会主义市场经济秩序案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '40000', '侵犯公民人身权利、民主权利案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '40100', '侵犯人身权利案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '40101', '故意杀人案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '40102', '过失致人死亡案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '40103', '故意伤害案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '40104', '过失致人重伤案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '40105', '强奸案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '40106', '奸淫幼女案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '40107', '强制猥亵、侮辱妇女案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '40108', '猥亵儿童案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '40109', '非法拘禁案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '40110', '绑架案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '40111', '偷盗婴幼儿勒索案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '40112', '拐卖妇女、儿童案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '40113', '收买被拐卖的妇女、儿童案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '40114', '聚众阻碍解救被收买的妇女、儿童案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '40115', '暴力阻碍解救被收买的妇女、儿童案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '40116', '强迫职工劳动案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '40117', '非法搜查案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '40118', '非法侵入住宅案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '40119', '诬告陷害案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '40120', '侮辱案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '40121', '诽谤案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '40122', '刑讯逼供案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '40123', '暴力取证案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '40124', '虐待被监管人员案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '40200', '侵犯民主权利案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '40210', '侵犯通讯自由案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '40220', '私拆邮件案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '40230', '隐匿邮件电报案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '40240', '毁弃邮件电报案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '40250', '报复陷害案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '40260', '打击报复会计人员案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '40270', '打击报复统计人员案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '40280', '破坏选举案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '40300', '破坏民族平等、宗教信仰案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '40310', '煽动民族仇恨、民族歧视案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '40320', '出版歧视、侮辱少数民族作品案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '40330', '非法剥夺公民宗教信仰自由案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '40340', '侵犯少数民族风俗习惯案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '40400', '妨害婚姻家庭权利案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '40410', '暴力干涉婚姻自由案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '40420', '重婚案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '40430', '破坏军婚案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '40440', '虐待案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '40450', '遗弃案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '40460', '拐骗儿童案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '49900', '其他侵犯公民人身权利、民主权利案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '50000', '侵犯财产案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '50100', '抢劫案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '50101', '入户抢劫案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '50102', '拦路抢劫案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '50103', '在公共交通工具上抢劫案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '50110', '抢劫银行或其他金融机构案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '50111', '抢劫珠宝店案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '50112', '抢劫提（送）款员案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '50113', '抢劫运钞车案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '50120', '抢劫出租汽车案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '50130', '抢劫军用物资案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '50131', '抢劫抢险、救灾、救济物资案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '50132', '抢劫牲畜案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '50140', '抢劫精神药物和麻醉药品案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '50150', '冒充军警持枪抢劫案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '50160', '持枪抢劫案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '50200', '盗窃案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '50201', '入室盗窃案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '50202', '盗窃精神药物和麻醉药品案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '50203', '盗窃易制毒化学品案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '50204', '盗窃金融机构案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '50210', '盗窃运输物资案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '50211', '盗窃铁路器材案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '50212', '盗窃珍贵文物案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '50216', '盗窃电脑芯片案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '50220', '盗窃货物案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '50221', '盗窃旅财案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '50222', '盗窃路财案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '50223', '盗窃汽车案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '50224', '盗窃摩托车案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '50227', '盗窃自行车案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '50230', '盗窃保险柜案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '50235', '盗用他人通讯设施案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '50236', '盗接通信线路案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '50237', '盗窃牲畜案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '50240', '扒窃案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '50300', '诈骗');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '50400', '抢夺案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '50500', '侵占案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '50600', '职务侵占案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '50700', '挪用特定款物案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '50710', '挪用资金案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '50720', '挪用公款案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '50730', '挪用救灾、抢险、防汛款物案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '50740', '挪用优抚款物案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '50750', '挪用扶贫、移民救济款物案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '50800', '敲诈勒索案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '50900', '故意毁坏财物案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '51000', '破坏生产经营案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '51100', '聚众哄抢案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60000', '妨害社会管理秩序案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60100', '扰乱公共秩序案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60101', '阻碍执行职务案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60102', '阻碍人大代表执行职务案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60103', '阻碍红十字会依法覆行职责案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60104', '阻碍安全机关、公安机关执行职务案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60105', '煽动暴力抗拒法律实施案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60106', '招摇撞骗案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60107', '冒充国家工作人员招摇撞骗案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60108', '冒充警察招摇撞骗案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60109', '伪造、变造公文、证件、印章案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60110', '买卖公文、证件、印章案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60111', '盗窃、抢夺公文、证件、印章案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60112', '毁灭公文、证件、印章案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60113', '伪造、变造居民身份证案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60114', '非法生产警服、警用标志、警械案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60115', '非法买卖警服、警用标志、警械案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60116', '非法获取国家秘密案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60117', '非法持有国家绝密、机密文件、资料、物品案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60118', '非法生产、销售间谍专用器材案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60119', '非法使用窃听器材案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60120', '非法使用窃照器材案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60121', '非法侵入计算机信息系统案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60122', '破坏计算机信息系统案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60123', '破坏计算机信息系统数据和应用程序案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60124', '故意制作传播计算机破坏性程序案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60125', '利用计算机金融诈骗案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60126', '利用计算机盗窃案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60127', '利用计算机贪污案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60128', '利用计算机挪用公款案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60129', '利用计算机窃取国家机密案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60130', '利用计算机实施其他犯罪案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60131', '扰乱无线电通讯管理秩序案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60132', '聚众扰乱社会秩序案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60133', '聚众冲击国家机关案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60134', '聚众扰乱公共场所秩序案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60135', '聚众扰乱交通秩序案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60136', '聚众斗殴案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60137', '寻衅滋事案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60138', '传授犯罪方法案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60139', '组织、领导黑社会性质组织案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60140', '参加黑社会性质组织案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60141', '入境发展黑社会组织案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60142', '包庇、纵容黑社会性质组织案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60143', '非法集会、游行、示威案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60146', '非法携带武器参加集会、游行、示威案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60150', '非法携带管制刀具参加集会、游行、示威案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60154', '非法携带爆炸物参加集会、游行、示威案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60158', '破坏集会、游行、示威案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60160', '侮辱国旗、国徽案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60165', '组织和利用会道门、邪教组织或者利用迷信破坏法律实施案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60166', '组织和利用会道门破坏法律实施案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60167', '组织和利用邪教组织破坏法律实施案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60168', '组织和利用迷信破坏法律实施案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60170', '组织和利用会道门、邪教组织或者利用迷信致人死亡案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60171', '组织和利用会道门致人死亡案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60172', '组织和利用邪教组织致人死亡案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60173', '组织和利用迷信致人死亡案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60180', '组织和利用会道门、邪教组织或者利用迷信奸淫妇女或诈骗钱财案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60181', '组织和利用会道门、邪教组织或者利用迷信奸淫妇女案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60182', '组织和利用会道门奸淫妇女案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60183', '组织和利用邪教组织奸淫妇女案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60184', '组织和利用迷信奸淫妇女案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60185', '组织和利用会道门、邪教组织或者利用迷信诈骗钱财案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60186', '组织和利用会道门诈骗钱财案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60187', '组织和利用邪教级诈骗钱财案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60188', '组织和利用迷信诈骗钱财案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60190', '聚众淫乱案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60193', '引诱未成年人聚众淫乱案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60196', '盗窃、侮辱尸体案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60197', '赌博案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60198', '故意延误投递邮件案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60200', '妨害司法案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60201', '伪证案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60211', '辩护人、诉讼代理人毁灭、伪造证据案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60212', '帮助毁灭、伪造证据案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60213', '司法人员毁灭、伪造证据案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60214', '妨害作证案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60215', '打击报复证人案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60221', '扰乱法庭秩序案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60222', '窝藏、包庇案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60223', '拒绝提供间谍犯罪证据案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60224', '窝藏、转移、收购、销售赃物案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60231', '拒不执行判决、裁定案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60241', '非法处置查封、扣押、冻结财产案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60251', '破坏监管秩序案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60252', '脱逃案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60253', '劫夺被押解人员案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60254', '组织越狱案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60255', '暴动越狱案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60256', '聚众持械劫狱案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60300', '妨害国（边）境管理案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60310', '偷越国（边）境案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60320', '组织他人偷越国（边）境案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60330', '运送他人偷越国（边）境案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60340', '骗取出境证件案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60350', '提供伪造、变造的出入境证件案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60360', '出售出入境证件案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60370', '破坏界碑、界桩案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60380', '破坏永久性测量标志案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60400', '妨害文物管理案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60401', '故意损毁文物案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60402', '过失损毁文物案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60411', '非法向外国人出售珍贵文物案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '310200', '上海市县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '310230', '上海市崇明县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320000', '江苏省');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320100', '江苏省南京市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320101', '江苏省南京市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320102', '江苏省南京市玄武区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320103', '江苏省南京市白下区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320104', '江苏省南京市秦淮区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320105', '江苏省南京市建邺区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320106', '江苏省南京市鼓楼区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320107', '江苏省南京市下关区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320111', '江苏省南京市浦口区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320113', '江苏省南京市栖霞区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320114', '江苏省南京市雨花台区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320115', '江苏省南京市江宁区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320116', '江苏省南京市六合区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320124', '江苏省南京市溧水县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320125', '江苏省南京市高淳县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320200', '江苏省无锡市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320201', '江苏省无锡市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320202', '江苏省无锡市崇安区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320203', '江苏省无锡市南长区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320204', '江苏省无锡市北塘区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320205', '江苏省无锡市锡山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320206', '江苏省无锡市惠山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320211', '江苏省无锡市滨湖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320281', '江苏省无锡市江阴市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320282', '江苏省无锡市宜兴市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320300', '江苏省徐州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320301', '江苏省徐州市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320302', '江苏省徐州市鼓楼区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320303', '江苏省徐州市云龙区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320304', '江苏省徐州市九里区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320305', '江苏省徐州市贾汪区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320311', '江苏省徐州市泉山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320321', '江苏省徐州市丰县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320322', '江苏省徐州市沛县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320323', '江苏省徐州市铜山县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320324', '江苏省徐州市睢宁县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320381', '江苏省徐州市新沂市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320382', '江苏省徐州市邳州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320400', '江苏省常州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320401', '江苏省常州市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320402', '江苏省常州市天宁区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320404', '江苏省常州市钟楼区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320405', '江苏省常州市戚墅堰区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320411', '江苏省常州市新北区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320412', '江苏省常州市武进区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320481', '江苏省常州市溧阳市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320482', '江苏省常州市金坛市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320500', '江苏省苏州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320501', '江苏省苏州市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320502', '江苏省苏州市沧浪区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320503', '江苏省苏州市平江区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320504', '江苏省苏州市金阊区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320505', '江苏省苏州市虎丘区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320506', '江苏省苏州市吴中区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320507', '江苏省苏州市相城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320581', '江苏省苏州市常熟市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320582', '江苏省苏州市张家港市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320583', '江苏省苏州市昆山市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320584', '江苏省苏州市吴江市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320585', '江苏省苏州市太仓市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320600', '江苏省南通市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320601', '江苏省南通市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320602', '江苏省南通市崇川区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320611', '江苏省南通市港闸区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320621', '江苏省南通市海安县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320623', '江苏省南通市如东县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320681', '江苏省南通市启东市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320682', '江苏省南通市如皋市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320683', '江苏省南通市通州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320684', '江苏省南通市海门市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320700', '江苏省连云港市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320701', '江苏省连云港市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320703', '江苏省连云港市连云区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320705', '江苏省连云港市新浦区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320706', '江苏省连云港市海州区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320721', '江苏省连云港市赣榆县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320722', '江苏省连云港市东海县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320723', '江苏省连云港市灌云县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320724', '江苏省连云港市灌南县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320800', '江苏省淮安市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320801', '江苏省淮安市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320802', '江苏省淮安市清河区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320803', '江苏省淮安市楚州区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320804', '江苏省淮安市淮阴区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320811', '江苏省淮安市清浦区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320826', '江苏省淮安市涟水县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320829', '江苏省淮安市洪泽县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320830', '江苏省淮安市盱眙县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320831', '江苏省淮安市金湖县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320900', '江苏省盐城市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320901', '江苏省盐城市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320902', '江苏省盐城市亭湖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320903', '江苏省盐城市盐都区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320921', '江苏省盐城市响水县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320922', '江苏省盐城市滨海县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320923', '江苏省盐城市阜宁县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320924', '江苏省盐城市射阳县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320925', '江苏省盐城市建湖县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320981', '江苏省盐城市东台市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '320982', '江苏省盐城市大丰市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '321000', '江苏省扬州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '321001', '江苏省扬州市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '321002', '江苏省扬州市广陵区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '321003', '江苏省扬州市邗江区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '321011', '江苏省扬州市郊区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '321023', '江苏省扬州市宝应县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '321081', '江苏省扬州市仪征市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '321084', '江苏省扬州市高邮市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '321088', '江苏省扬州市江都市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '321100', '江苏省镇江市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '321101', '江苏省镇江市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '321102', '江苏省镇江市京口区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '321111', '江苏省镇江市润州区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '321112', '江苏省镇江市丹徒区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '321181', '江苏省镇江市丹阳市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '321182', '江苏省镇江市扬中市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '321183', '江苏省镇江市句容市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '321200', '江苏省泰州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '321201', '江苏省泰州市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '321202', '江苏省泰州市海陵区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '321203', '江苏省泰州市高港区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '321281', '江苏省泰州市兴化市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '321282', '江苏省泰州市靖江市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '321283', '江苏省泰州市泰兴市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '321284', '江苏省泰州市姜堰市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '321300', '江苏省宿迁市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '321301', '江苏省宿迁市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '321302', '江苏省宿迁市宿城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '321321', '江苏省宿迁市宿豫县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '321322', '江苏省宿迁市沭阳县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '321323', '江苏省宿迁市泗阳县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '321324', '江苏省宿迁市泗洪县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330000', '浙江省');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330100', '浙江省杭州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330101', '浙江省杭州市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330102', '浙江省杭州市上城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330103', '浙江省杭州市下城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330104', '浙江省杭州市江干区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330105', '浙江省杭州市拱墅区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330106', '浙江省杭州市西湖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330108', '浙江省杭州市滨江区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330109', '浙江省杭州市萧山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330110', '浙江省杭州市余杭区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330122', '浙江省杭州市桐庐县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330127', '浙江省杭州市淳安县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330182', '浙江省杭州市建德市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330183', '浙江省杭州市富阳市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330185', '浙江省杭州市临安市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330200', '浙江省宁波市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330201', '浙江省宁波市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330203', '浙江省宁波市海曙区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330204', '浙江省宁波市江东区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330205', '浙江省宁波市江北区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330206', '浙江省宁波市北仑区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330211', '浙江省宁波市镇海区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330212', '浙江省宁波市鄞州区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330225', '浙江省宁波市象山县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330226', '浙江省宁波市宁海县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330281', '浙江省宁波市余姚市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330282', '浙江省宁波市慈溪市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330283', '浙江省宁波市奉化市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330300', '浙江省温州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330301', '浙江省温州市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330302', '浙江省温州市鹿城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330303', '浙江省温州市龙湾区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330304', '浙江省温州市瓯海区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330322', '浙江省温州市洞头县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330324', '浙江省温州市永嘉县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330326', '浙江省温州市平阳县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330327', '浙江省温州市苍南县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330328', '浙江省温州市文成县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330329', '浙江省温州市泰顺县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330381', '浙江省温州市瑞安市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330382', '浙江省温州市乐清市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330400', '浙江省嘉兴市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330401', '浙江省嘉兴市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330402', '浙江省嘉兴市秀城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330411', '浙江省嘉兴市秀洲区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330421', '浙江省嘉兴市嘉善县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330424', '浙江省嘉兴市海盐县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330481', '浙江省嘉兴市海宁市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330482', '浙江省嘉兴市平湖市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330483', '浙江省嘉兴市桐乡市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330500', '浙江省湖州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330501', '浙江省湖州市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330502', '浙江省湖州市吴兴区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330503', '浙江省湖州市南浔区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330521', '浙江省湖州市德清县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330522', '浙江省湖州市长兴县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330523', '浙江省湖州市安吉县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330600', '浙江省绍兴市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330601', '浙江省绍兴市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330602', '浙江省绍兴市越城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330621', '浙江省绍兴市绍兴县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330624', '浙江省绍兴市新昌县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330681', '浙江省绍兴市诸暨市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330682', '浙江省绍兴市上虞市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330683', '浙江省绍兴市嵊州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330700', '浙江省金华市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330701', '浙江省金华市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330702', '浙江省金华市婺城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330703', '浙江省金华市金东区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330723', '浙江省金华市武义县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330726', '浙江省金华市浦江县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330727', '浙江省金华市磐安县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330781', '浙江省金华市兰溪市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330782', '浙江省金华市义乌市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330783', '浙江省金华市东阳市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330784', '浙江省金华市永康市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330800', '浙江省衢州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330801', '浙江省衢州市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330802', '浙江省衢州市柯城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330803', '浙江省衢州市衢江区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330822', '浙江省衢州市常山县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330824', '浙江省衢州市开化县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330825', '浙江省衢州市龙游县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330881', '浙江省衢州市江山市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330900', '浙江省舟山市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330901', '浙江省舟山市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330902', '浙江省舟山市定海区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360101', '江西省南昌市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360102', '江西省南昌市东湖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360103', '江西省南昌市西湖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360104', '江西省南昌市青云谱区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360105', '江西省南昌市湾里区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360111', '江西省南昌市青山湖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360121', '江西省南昌市南昌县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360122', '江西省南昌市新建县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360123', '江西省南昌市安义县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360124', '江西省南昌市进贤县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360200', '江西省景德镇市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360201', '江西省景德镇市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360202', '江西省景德镇市昌江区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360203', '江西省景德镇市珠山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360222', '江西省景德镇市浮梁县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360281', '江西省景德镇市乐平市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360300', '江西省萍乡市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360301', '江西省萍乡市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360302', '江西省萍乡市安源区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360313', '江西省萍乡市湘东区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360321', '江西省萍乡市莲花县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360322', '江西省萍乡市上栗县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360323', '江西省萍乡市芦溪县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360400', '江西省九江市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360401', '江西省九江市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360402', '江西省九江市庐山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360403', '江西省九江市浔阳区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360421', '江西省九江市九江县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360423', '江西省九江市武宁县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360424', '江西省九江市修水县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360425', '江西省九江市永修县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360426', '江西省九江市德安县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360427', '江西省九江市星子县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360428', '江西省九江市都昌县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360429', '江西省九江市湖口县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360430', '江西省九江市彭泽县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360481', '江西省九江市瑞昌市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360500', '江西省新余市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360501', '江西省新余市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360502', '江西省新余市渝水区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360521', '江西省新余市分宜县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360600', '江西省鹰潭市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360601', '江西省鹰潭市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360602', '江西省鹰潭市月湖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360622', '江西省鹰潭市余江县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360681', '江西省鹰潭市贵溪市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360700', '江西省赣州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360701', '江西省赣州市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360702', '江西省赣州市章贡区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360721', '江西省赣州市赣县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360722', '江西省赣州市信丰县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360723', '江西省赣州市大余县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360724', '江西省赣州市上犹县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360725', '江西省赣州市崇义县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360726', '江西省赣州市安远县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360727', '江西省赣州市龙南县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360728', '江西省赣州市定南县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360729', '江西省赣州市全南县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360730', '江西省赣州市宁都县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360731', '江西省赣州市于都县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360732', '江西省赣州市兴国县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360733', '江西省赣州市会昌县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360734', '江西省赣州市寻乌县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360735', '江西省赣州市石城县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360781', '江西省赣州市瑞金市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360782', '江西省赣州市南康市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360800', '江西省吉安市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360801', '江西省吉安市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360802', '江西省吉安市吉州区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360803', '江西省吉安市青原区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360821', '江西省吉安市吉安县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360822', '江西省吉安市吉水县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360823', '江西省吉安市峡江县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360824', '江西省吉安市新干县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360825', '江西省吉安市永丰县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360826', '江西省吉安市泰和县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360827', '江西省吉安市遂川县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360828', '江西省吉安市万安县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360829', '江西省吉安市安福县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360830', '江西省吉安市永新县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360881', '江西省吉安市井冈山市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360900', '江西省宜春市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360901', '江西省宜春市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360902', '江西省宜春市袁州区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360921', '江西省宜春市奉新县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360922', '江西省宜春市万载县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360923', '江西省宜春市上高县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360924', '江西省宜春市宜丰县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360925', '江西省宜春市靖安县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360926', '江西省宜春市铜鼓县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360981', '江西省宜春市丰城市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360982', '江西省宜春市樟树市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360983', '江西省宜春市高安市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '361000', '江西省抚州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '361001', '江西省抚州市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '361002', '江西省抚州市临川区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '361021', '江西省抚州市南城县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '361022', '江西省抚州市黎川县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '361023', '江西省抚州市南丰县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '361024', '江西省抚州市崇仁县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '361025', '江西省抚州市乐安县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '361026', '江西省抚州市宜黄县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '361027', '江西省抚州市金溪县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '361028', '江西省抚州市资溪县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '361029', '江西省抚州市东乡县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '361030', '江西省抚州市广昌县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '361100', '江西省上饶市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '361101', '江西省上饶市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '361102', '江西省上饶市信州区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '361121', '江西省上饶市上饶县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '361122', '江西省上饶市广丰县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '361123', '江西省上饶市玉山县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '361124', '江西省上饶市铅山县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '361125', '江西省上饶市横峰县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '361126', '江西省上饶市弋阳县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '361127', '江西省上饶市余干县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '361128', '江西省上饶市鄱阳县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '361129', '江西省上饶市万年县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '361130', '江西省上饶市婺源县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '361181', '江西省上饶市德兴市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370000', '山东省');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370100', '山东省济南市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370101', '山东省济南市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370102', '山东省济南市历下区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370103', '山东省济南市市中区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370104', '山东省济南市槐荫区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370105', '山东省济南市天桥区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370112', '山东省济南市历城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370113', '山东省济南市长清区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370124', '山东省济南市平阴县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370125', '山东省济南市济阳县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370126', '山东省济南市商河县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370181', '山东省济南市章丘市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370200', '山东省青岛市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370201', '山东省青岛市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370202', '山东省青岛市市南区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370203', '山东省青岛市市北区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370205', '山东省青岛市四方区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370211', '山东省青岛市黄岛区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370212', '山东省青岛市崂山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370213', '山东省青岛市李沧区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370214', '山东省青岛市城阳区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370281', '山东省青岛市胶州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370282', '山东省青岛市即墨市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370283', '山东省青岛市平度市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370284', '山东省青岛市胶南市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370285', '山东省青岛市莱西市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370300', '山东省淄博市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370301', '山东省淄博市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370302', '山东省淄博市淄川区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370303', '山东省淄博市张店区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370304', '山东省淄博市博山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370305', '山东省淄博市临淄区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370306', '山东省淄博市周村区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370321', '山东省淄博市桓台县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370322', '山东省淄博市高青县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370323', '山东省淄博市沂源县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370400', '山东省枣庄市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370401', '山东省枣庄市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370402', '山东省枣庄市市中区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370403', '山东省枣庄市薛城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370404', '山东省枣庄市峄城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370405', '山东省枣庄市台儿庄区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370406', '山东省枣庄市山亭区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370481', '山东省枣庄市滕州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370500', '山东省东营市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370501', '山东省东营市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370502', '山东省东营市东营区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370503', '山东省东营市河口区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370521', '山东省东营市垦利县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370522', '山东省东营市利津县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370523', '山东省东营市广饶县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370600', '山东省烟台市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370601', '山东省烟台市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370602', '山东省烟台市芝罘区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370611', '山东省烟台市福山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370612', '山东省烟台市牟平区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370613', '山东省烟台市莱山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370634', '山东省烟台市长岛县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370681', '山东省烟台市龙口市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370682', '山东省烟台市莱阳市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370683', '山东省烟台市莱州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370684', '山东省烟台市蓬莱市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370685', '山东省烟台市招远市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370686', '山东省烟台市栖霞市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370687', '山东省烟台市海阳市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370700', '山东省潍坊市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370701', '山东省潍坊市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370702', '山东省潍坊市潍城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370703', '山东省潍坊市寒亭区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370704', '山东省潍坊市坊子区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370705', '山东省潍坊市奎文区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370724', '山东省潍坊市临朐县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370725', '山东省潍坊市昌乐县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370781', '山东省潍坊市青州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370782', '山东省潍坊市诸城市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370783', '山东省潍坊市寿光市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370784', '山东省潍坊市安丘市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370785', '山东省潍坊市高密市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370786', '山东省潍坊市昌邑市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370800', '山东省济宁市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370801', '山东省济宁市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370802', '山东省济宁市市中区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370811', '山东省济宁市任城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370826', '山东省济宁市微山县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370827', '山东省济宁市鱼台县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370828', '山东省济宁市金乡县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370829', '山东省济宁市嘉祥县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370830', '山东省济宁市汶上县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370831', '山东省济宁市泗水县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370832', '山东省济宁市梁山县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370881', '山东省济宁市曲阜市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370882', '山东省济宁市兖州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370883', '山东省济宁市邹城市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370900', '山东省泰安市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370901', '山东省泰安市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370902', '山东省泰安市泰山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370903', '山东省泰安市岱岳区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370921', '山东省泰安市宁阳县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370923', '山东省泰安市东平县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370982', '山东省泰安市新泰市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '370983', '山东省泰安市肥城市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371000', '山东省威海市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371001', '山东省威海市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371002', '山东省威海市环翠区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371081', '山东省威海市文登市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371082', '山东省威海市荣成市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371083', '山东省威海市乳山市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371100', '山东省日照市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371101', '山东省日照市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371102', '山东省日照市东港区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371121', '山东省日照市五莲县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371122', '山东省日照市莒县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371200', '山东省莱芜市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371201', '山东省莱芜市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371202', '山东省莱芜市莱城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371203', '山东省莱芜市钢城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371300', '山东省临沂市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371301', '山东省临沂市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371302', '山东省临沂市兰山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371311', '山东省临沂市罗庄区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371312', '山东省临沂市河东区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371321', '山东省临沂市沂南县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371322', '山东省临沂市郯城县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371323', '山东省临沂市沂水县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371324', '山东省临沂市苍山县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371325', '山东省临沂市费县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371326', '山东省临沂市平邑县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371327', '山东省临沂市莒南县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371328', '山东省临沂市蒙阴县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371329', '山东省临沂市临沭县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371400', '山东省德州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371401', '山东省德州市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510903', '四川省遂宁市船山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510904', '四川省遂宁市安居区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510921', '四川省遂宁市蓬溪县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510922', '四川省遂宁市射洪县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510923', '四川省遂宁市大英县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511000', '四川省内江市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511001', '四川省内江市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511002', '四川省内江市市中区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511011', '四川省内江市东兴区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511024', '四川省内江市威远县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511025', '四川省内江市资中县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511028', '四川省内江市隆昌县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511100', '四川省乐山市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511101', '四川省乐山市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511102', '四川省乐山市市中区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511111', '四川省乐山市沙湾区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511112', '四川省乐山市五通桥区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511113', '四川省乐山市金口河区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511123', '四川省乐山市犍为县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511124', '四川省乐山市井研县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511126', '四川省乐山市夹江县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511129', '四川省乐山市沐川县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511132', '四川省乐山市峨边彝族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511133', '四川省乐山市马边彝族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511181', '四川省乐山市峨眉山市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511300', '四川省南充市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511301', '四川省南充市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511302', '四川省南充市顺庆区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511303', '四川省南充市高坪区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511304', '四川省南充市嘉陵区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511321', '四川省南充市南部县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511322', '四川省南充市营山县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511323', '四川省南充市蓬安县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511324', '四川省南充市仪陇县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511325', '四川省南充市西充县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511381', '四川省南充市阆中市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511400', '四川省眉山市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511401', '四川省眉山市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60412', '非法向外国人赠送珍贵文物案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60421', '倒卖文物案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60422', '非法出售文物藏品案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60423', '非法私赠文物藏品案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60431', '故意损毁名胜古迹案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60441', '盗掘古文化遗址案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60442', '盗掘古墓葬案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60443', '盗掘古人类化石、古脊椎动物化石案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60451', '抢夺国有档案案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60452', '窃取国有档案案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60453', '擅自出卖国家档案案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60454', '擅自转让国家档案案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60500', '危害公共卫生案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60501', '妨害传染病防治案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60510', '传染病菌种、毒种扩散案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60520', '妨害国境卫生检疫案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60530', '非法组织卖血案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60531', '强迫卖血案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60532', '非法采集、供应血液案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60533', '非法制作、供应血液制品案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60534', '采集、供应血液事故案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60535', '制作、供应血液制品事故案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60540', '医疗事故案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60550', '非法行医案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60560', '非法进行节育手术案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60570', '逃避动植物检疫案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60600', '破坏环境资源保护案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60601', '重大环境污染事故案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60602', '非法处置进口固体废物案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60603', '擅自进口固体废物案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60611', '非法猎捕、杀害珍贵、濒危野生动物案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60612', '非法收购珍贵、濒危野生动物及其制品案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60613', '非法运输珍贵、濒危野生动物及其制品案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60614', '非法出售珍贵、濒危野生动物及其制品案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60621', '非法采伐、毁坏珍贵树木案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60622', '盗伐林木案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60623', '滥伐林木案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60624', '非法收购盗伐、滥伐的林木案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60631', '非法捕捞水产品案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60632', '非法狩猎案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60633', '非法占用耕地案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60634', '非法采矿案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60635', '破坏性采矿案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60700', '走私、贩卖、运输、制造毒品案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60701', '走私毒品案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60702', '贩卖毒品案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60703', '运输毒品案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60704', '制造毒品案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60710', '非法持有毒品案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60720', '包庇毒品犯罪分子案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60721', '窝藏、转移、隐瞒毒品、毒赃案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60722', '走私制毒物品案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60730', '非法买卖制毒物品案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60731', '非法种植毒品原植物案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60732', '非法买卖毒品原植物种苗案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60733', '非法运输毒品原植物种苗案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60734', '非法携带毒品原植物种苗案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60735', '非法持有毒品原植物种苗案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60736', '非法运输携带制毒物品进出境案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60740', '引诱、教唆、强迫他人吸毒案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60741', '引诱他人吸毒案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60742', '教唆他人吸毒案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60743', '欺骗他人吸毒案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60744', '强迫他人吸毒案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60745', '容留他人吸毒案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60750', '非法提供麻醉药品案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60760', '非法提供精神药品案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60800', '组织、强迫、引诱、容留、介绍卖淫案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60801', '组织卖淫案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60810', '强迫卖淫案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60820', '协助组织卖淫案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60830', '引诱卖淫案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60840', '引诱幼女卖淫案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60850', '容留卖淫案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60860', '介绍卖淫案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60870', '传播性病案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60880', '嫖宿幼女案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60900', '制作、复制、出版、贩卖、传播淫秽物品牟利案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60910', '制作淫秽物品案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60920', '复制淫秽物品案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60930', '出版淫秽物品案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60940', '贩卖淫秽物品案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60950', '传播淫秽物品案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60960', '提供书号出版淫秽书刊案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60970', '组织播放淫秽音像制品案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '60980', '组织淫秽表演案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '69900', '其他妨害社会管理秩序案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '70000', '危害国防利益案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '70100', '阻碍军人执行职务案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '70200', '阻碍军事行动案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '70300', '破坏武器装备案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('AJLBDM', '案件类别代码', '70400', '破坏军事设施案');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341525', '安徽省六安市霍山县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341600', '安徽省亳州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341601', '安徽省亳州市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341602', '安徽省亳州市谯城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341621', '安徽省亳州市涡阳县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341622', '安徽省亳州市蒙城县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341623', '安徽省亳州市利辛县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341700', '安徽省池州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341701', '安徽省池州市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341702', '安徽省池州市贵池区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341721', '安徽省池州市东至县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341722', '安徽省池州市石台县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341723', '安徽省池州市青阳县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341800', '安徽省宣城市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341801', '安徽省宣城市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341802', '安徽省宣城市宣州区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341821', '安徽省宣城市郎溪县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341822', '安徽省宣城市广德县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341823', '安徽省宣城市泾县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341824', '安徽省宣城市绩溪县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341825', '安徽省宣城市旌德县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '341881', '安徽省宣城市宁国市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350000', '福建省');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350100', '福建省福州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350101', '福建省福州市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350102', '福建省福州市鼓楼区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350103', '福建省福州市台江区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350104', '福建省福州市仓山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350105', '福建省福州市马尾区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350111', '福建省福州市晋安区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350121', '福建省福州市闽侯县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350122', '福建省福州市连江县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350123', '福建省福州市罗源县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350124', '福建省福州市闽清县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350125', '福建省福州市永泰县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350128', '福建省福州市平潭县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350181', '福建省福州市福清市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350182', '福建省福州市长乐市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350200', '福建省厦门市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350201', '福建省厦门市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350203', '福建省厦门市思明区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350205', '福建省厦门市海沧区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350206', '福建省厦门市湖里区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350211', '福建省厦门市集美区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350212', '福建省厦门市同安区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350213', '福建省厦门市翔安区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350300', '福建省莆田市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350301', '福建省莆田市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350302', '福建省莆田市城厢区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350303', '福建省莆田市涵江区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350304', '福建省莆田市荔城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350305', '福建省莆田市秀屿区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350322', '福建省莆田市仙游县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350400', '福建省三明市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350401', '福建省三明市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350402', '福建省三明市梅列区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350403', '福建省三明市三元区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350421', '福建省三明市明溪县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350423', '福建省三明市清流县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350424', '福建省三明市宁化县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350425', '福建省三明市大田县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350426', '福建省三明市尤溪县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350427', '福建省三明市沙县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350428', '福建省三明市将乐县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350429', '福建省三明市泰宁县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350430', '福建省三明市建宁县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350481', '福建省三明市永安市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350500', '福建省泉州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350501', '福建省泉州市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350502', '福建省泉州市鲤城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350503', '福建省泉州市丰泽区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350504', '福建省泉州市洛江区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350505', '福建省泉州市泉港区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350521', '福建省泉州市惠安县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350524', '福建省泉州市安溪县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350525', '福建省泉州市永春县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350526', '福建省泉州市德化县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350527', '福建省泉州市金门县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350581', '福建省泉州市石狮市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350582', '福建省泉州市晋江市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350583', '福建省泉州市南安市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350600', '福建省漳州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350601', '福建省漳州市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350602', '福建省漳州市芗城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350603', '福建省漳州市龙文区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350622', '福建省漳州市云霄县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350623', '福建省漳州市漳浦县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350624', '福建省漳州市诏安县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350625', '福建省漳州市长泰县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350626', '福建省漳州市东山县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350627', '福建省漳州市南靖县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350628', '福建省漳州市平和县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350629', '福建省漳州市华安县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350681', '福建省漳州市龙海市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350700', '福建省南平市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350701', '福建省南平市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350702', '福建省南平市延平区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350721', '福建省南平市顺昌县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350722', '福建省南平市浦城县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350723', '福建省南平市光泽县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350724', '福建省南平市松溪县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350725', '福建省南平市政和县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350781', '福建省南平市邵武市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350782', '福建省南平市武夷山市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350783', '福建省南平市建瓯市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350784', '福建省南平市建阳市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350800', '福建省龙岩市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350801', '福建省龙岩市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350802', '福建省龙岩市新罗区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350821', '福建省龙岩市长汀县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350822', '福建省龙岩市永定县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350823', '福建省龙岩市上杭县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350824', '福建省龙岩市武平县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350825', '福建省龙岩市连城县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350881', '福建省龙岩市漳平市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350900', '福建省宁德市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350901', '福建省宁德市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350902', '福建省宁德市蕉城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350921', '福建省宁德市霞浦县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350922', '福建省宁德市古田县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350923', '福建省宁德市屏南县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350924', '福建省宁德市寿宁县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350925', '福建省宁德市周宁县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350926', '福建省宁德市柘荣县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350981', '福建省宁德市福安市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '350982', '福建省宁德市福鼎市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360000', '江西省');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '360100', '江西省南昌市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371402', '山东省德州市德城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371421', '山东省德州市陵县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371422', '山东省德州市宁津县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371423', '山东省德州市庆云县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371424', '山东省德州市临邑县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371425', '山东省德州市齐河县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371426', '山东省德州市平原县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371427', '山东省德州市夏津县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371428', '山东省德州市武城县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371481', '山东省德州市乐陵市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371482', '山东省德州市禹城市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371500', '山东省聊城市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371501', '山东省聊城市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371502', '山东省聊城市东昌府区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371521', '山东省聊城市阳谷县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371522', '山东省聊城市莘县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371523', '山东省聊城市茌平县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371524', '山东省聊城市东阿县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371525', '山东省聊城市冠县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371526', '山东省聊城市高唐县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371581', '山东省聊城市临清市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371600', '山东省滨州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371601', '山东省滨州市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371602', '山东省滨州市滨城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371621', '山东省滨州市惠民县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371622', '山东省滨州市阳信县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371623', '山东省滨州市无棣县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371624', '山东省滨州市沾化县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371625', '山东省滨州市博兴县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371626', '山东省滨州市邹平县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220204', '吉林省吉林市船营区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220211', '吉林省吉林市丰满区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220221', '吉林省吉林市永吉县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220281', '吉林省吉林市蛟河市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220282', '吉林省吉林市桦甸市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220283', '吉林省吉林市舒兰市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220284', '吉林省吉林市磐石市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220300', '吉林省四平市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220301', '吉林省四平市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220302', '吉林省四平市铁西区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220303', '吉林省四平市铁东区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220322', '吉林省四平市梨树县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220323', '吉林省四平市伊通满族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220381', '吉林省四平市公主岭市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220382', '吉林省四平市双辽市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220400', '吉林省辽源市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220401', '吉林省辽源市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220402', '吉林省辽源市龙山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220403', '吉林省辽源市西安区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220421', '吉林省辽源市东丰县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220422', '吉林省辽源市东辽县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220500', '吉林省通化市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220501', '吉林省通化市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220502', '吉林省通化市东昌区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220503', '吉林省通化市二道江区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220521', '吉林省通化市通化县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220523', '吉林省通化市辉南县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220524', '吉林省通化市柳河县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220581', '吉林省通化市梅河口市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220582', '吉林省通化市集安市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220600', '吉林省白山市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220601', '吉林省白山市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220602', '吉林省白山市八道江区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220621', '吉林省白山市抚松县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220622', '吉林省白山市靖宇县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220623', '吉林省白山市长白朝鲜族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220625', '吉林省白山市江源县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220681', '吉林省白山市临江市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220700', '吉林省松原市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220701', '吉林省松原市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220702', '吉林省松原市宁江区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220721', '吉林省松原市前郭尔罗斯蒙古族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220722', '吉林省松原市长岭县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220723', '吉林省松原市乾安县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220724', '吉林省松原市扶余县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220800', '吉林省白城市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220801', '吉林省白城市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220802', '吉林省白城市洮北区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220821', '吉林省白城市镇赉县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220822', '吉林省白城市通榆县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220881', '吉林省白城市洮南市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '220882', '吉林省白城市大安市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '222400', '吉林省延边朝鲜族自治州');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '222401', '吉林省延边朝鲜族自治州延吉市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '222402', '吉林省延边朝鲜族自治州图们市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '222403', '吉林省延边朝鲜族自治州敦化市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '222404', '吉林省延边朝鲜族自治州珲春市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '222405', '吉林省延边朝鲜族自治州龙井市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '222406', '吉林省延边朝鲜族自治州和龙市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '222424', '吉林省延边朝鲜族自治州汪清县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '222426', '吉林省延边朝鲜族自治州安图县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230000', '黑龙江省');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230100', '黑龙江省哈尔滨市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230101', '黑龙江省哈尔滨市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230102', '黑龙江省哈尔滨市道里区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230103', '黑龙江省哈尔滨市南岗区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230104', '黑龙江省哈尔滨市道外区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230105', '黑龙江省哈尔滨市太平区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230106', '黑龙江省哈尔滨市香坊区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230107', '黑龙江省哈尔滨市动力区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230108', '黑龙江省哈尔滨市平房区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230121', '黑龙江省哈尔滨市呼兰县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230123', '黑龙江省哈尔滨市依兰县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230124', '黑龙江省哈尔滨市方正县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230125', '黑龙江省哈尔滨市宾县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230126', '黑龙江省哈尔滨市巴彦县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230127', '黑龙江省哈尔滨市木兰县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230128', '黑龙江省哈尔滨市通河县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230129', '黑龙江省哈尔滨市延寿县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230181', '黑龙江省哈尔滨市阿城市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230182', '黑龙江省哈尔滨市双城市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230183', '黑龙江省哈尔滨市尚志市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230184', '黑龙江省哈尔滨市五常市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230200', '黑龙江省齐齐哈尔市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230201', '黑龙江省齐齐哈尔市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230202', '黑龙江省齐齐哈尔市龙沙区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230203', '黑龙江省齐齐哈尔市建华区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230204', '黑龙江省齐齐哈尔市铁锋区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230205', '黑龙江省齐齐哈尔市昂昂溪区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230206', '黑龙江省齐齐哈尔市富拉尔基区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230207', '黑龙江省齐齐哈尔市碾子山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230208', '黑龙江省齐齐哈尔市梅里斯达斡尔族区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230221', '黑龙江省齐齐哈尔市龙江县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230223', '黑龙江省齐齐哈尔市依安县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230224', '黑龙江省齐齐哈尔市泰来县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230225', '黑龙江省齐齐哈尔市甘南县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230227', '黑龙江省齐齐哈尔市富裕县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230229', '黑龙江省齐齐哈尔市克山县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230230', '黑龙江省齐齐哈尔市克东县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230231', '黑龙江省齐齐哈尔市拜泉县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230281', '黑龙江省齐齐哈尔市讷河市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230300', '黑龙江省鸡西市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230301', '黑龙江省鸡西市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230302', '黑龙江省鸡西市鸡冠区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230303', '黑龙江省鸡西市恒山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230304', '黑龙江省鸡西市滴道区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230305', '黑龙江省鸡西市梨树区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230306', '黑龙江省鸡西市城子河区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230307', '黑龙江省鸡西市麻山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230321', '黑龙江省鸡西市鸡东县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230381', '黑龙江省鸡西市虎林市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230382', '黑龙江省鸡西市密山市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230400', '黑龙江省鹤岗市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230401', '黑龙江省鹤岗市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230402', '黑龙江省鹤岗市向阳区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230403', '黑龙江省鹤岗市工农区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230404', '黑龙江省鹤岗市南山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230405', '黑龙江省鹤岗市兴安区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230406', '黑龙江省鹤岗市东山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230407', '黑龙江省鹤岗市兴山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230421', '黑龙江省鹤岗市萝北县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230422', '黑龙江省鹤岗市绥滨县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230500', '黑龙江省双鸭山市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230501', '黑龙江省双鸭山市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230502', '黑龙江省双鸭山市尖山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230503', '黑龙江省双鸭山市岭东区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230505', '黑龙江省双鸭山市四方台区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230506', '黑龙江省双鸭山市宝山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230521', '黑龙江省双鸭山市集贤县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230522', '黑龙江省双鸭山市友谊县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230523', '黑龙江省双鸭山市宝清县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230524', '黑龙江省双鸭山市饶河县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230600', '黑龙江省大庆市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230601', '黑龙江省大庆市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230602', '黑龙江省大庆市萨尔图区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230603', '黑龙江省大庆市龙凤区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230604', '黑龙江省大庆市让胡路区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230605', '黑龙江省大庆市红岗区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230606', '黑龙江省大庆市大同区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230621', '黑龙江省大庆市肇州县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230622', '黑龙江省大庆市肇源县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230623', '黑龙江省大庆市林甸县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230624', '黑龙江省大庆市杜尔伯特蒙古族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230700', '黑龙江省伊春市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230701', '黑龙江省伊春市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230702', '黑龙江省伊春市伊春区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230703', '黑龙江省伊春市南岔区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230704', '黑龙江省伊春市友好区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230705', '黑龙江省伊春市西林区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230706', '黑龙江省伊春市翠峦区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230707', '黑龙江省伊春市新青区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230708', '黑龙江省伊春市美溪区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230709', '黑龙江省伊春市金山屯区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230710', '黑龙江省伊春市五营区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230711', '黑龙江省伊春市乌马河区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230712', '黑龙江省伊春市汤旺河区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230713', '黑龙江省伊春市带岭区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230714', '黑龙江省伊春市乌伊岭区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230715', '黑龙江省伊春市红星区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230716', '黑龙江省伊春市上甘岭区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230722', '黑龙江省伊春市嘉荫县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230781', '黑龙江省伊春市铁力市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230800', '黑龙江省佳木斯市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230801', '黑龙江省佳木斯市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230802', '黑龙江省佳木斯市永红区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230803', '黑龙江省佳木斯市向阳区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230804', '黑龙江省佳木斯市前进区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230805', '黑龙江省佳木斯市东风区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230811', '黑龙江省佳木斯市郊区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230822', '黑龙江省佳木斯市桦南县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230826', '黑龙江省佳木斯市桦川县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230828', '黑龙江省佳木斯市汤原县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230833', '黑龙江省佳木斯市抚远县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230881', '黑龙江省佳木斯市同江市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230882', '黑龙江省佳木斯市富锦市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230900', '黑龙江省七台河市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230901', '黑龙江省七台河市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230902', '黑龙江省七台河市新兴区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230903', '黑龙江省七台河市桃山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230904', '黑龙江省七台河市茄子河区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '230921', '黑龙江省七台河市勃利县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '231000', '黑龙江省牡丹江市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '231001', '黑龙江省牡丹江市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '231002', '黑龙江省牡丹江市东安区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '231003', '黑龙江省牡丹江市阳明区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '231004', '黑龙江省牡丹江市爱民区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '231005', '黑龙江省牡丹江市西安区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '231024', '黑龙江省牡丹江市东宁县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '231025', '黑龙江省牡丹江市林口县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '231081', '黑龙江省牡丹江市绥芬河市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '231083', '黑龙江省牡丹江市海林市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '231084', '黑龙江省牡丹江市宁安市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '231085', '黑龙江省牡丹江市穆棱市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '231100', '黑龙江省黑河市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '231101', '黑龙江省黑河市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '231102', '黑龙江省黑河市爱辉区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '231121', '黑龙江省黑河市嫩江县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '231123', '黑龙江省黑河市逊克县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '231124', '黑龙江省黑河市孙吴县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '231181', '黑龙江省黑河市北安市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '231182', '黑龙江省黑河市五大连池市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '231200', '黑龙江省绥化市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '231201', '黑龙江省绥化市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '231202', '黑龙江省绥化市北林区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '231221', '黑龙江省绥化市望奎县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '231222', '黑龙江省绥化市兰西县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '231223', '黑龙江省绥化市青冈县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '231224', '黑龙江省绥化市庆安县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '231225', '黑龙江省绥化市明水县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '231226', '黑龙江省绥化市绥棱县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '231281', '黑龙江省绥化市安达市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '231282', '黑龙江省绥化市肇东市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '231283', '黑龙江省绥化市海伦市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '232700', '黑龙江省大兴安岭地区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '232721', '黑龙江省大兴安岭地区呼玛县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '232722', '黑龙江省大兴安岭地区塔河县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '232723', '黑龙江省大兴安岭地区漠河县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '310000', '上海市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '310100', '上海市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '310101', '上海市黄浦区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '310103', '上海市卢湾区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '310104', '上海市徐汇区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '310105', '上海市长宁区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '310106', '上海市静安区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '310107', '上海市普陀区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '310108', '上海市闸北区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '310109', '上海市虹口区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '310110', '上海市杨浦区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '310112', '上海市闵行区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '310113', '上海市宝山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '310114', '上海市嘉定区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '310115', '上海市浦东新区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '310116', '上海市金山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '310117', '上海市松江区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '310118', '上海市青浦区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '310119', '上海市南汇区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '310120', '上海市奉贤区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440282', '广东省韶关市南雄市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440300', '广东省深圳市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440301', '广东省深圳市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440303', '广东省深圳市罗湖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440304', '广东省深圳市福田区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440305', '广东省深圳市南山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440306', '广东省深圳市宝安区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440307', '广东省深圳市龙岗区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440308', '广东省深圳市盐田区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440400', '广东省珠海市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440401', '广东省珠海市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440402', '广东省珠海市香洲区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440403', '广东省珠海市斗门区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440404', '广东省珠海市金湾区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440500', '广东省汕头市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440501', '广东省汕头市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440507', '广东省汕头市龙湖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440511', '广东省汕头市金平区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440512', '广东省汕头市濠江区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440513', '广东省汕头市潮阳区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440514', '广东省汕头市潮南区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440515', '广东省汕头市澄海区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440523', '广东省汕头市南澳县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440600', '广东省佛山市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440601', '广东省佛山市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440604', '广东省佛山市禅城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440605', '广东省佛山市南海区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440606', '广东省佛山市顺德区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440607', '广东省佛山市三水区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440608', '广东省佛山市高明区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440700', '广东省江门市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440701', '广东省江门市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440703', '广东省江门市蓬江区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440704', '广东省江门市江海区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440705', '广东省江门市新会区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440781', '广东省江门市台山市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440783', '广东省江门市开平市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440784', '广东省江门市鹤山市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440785', '广东省江门市恩平市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440800', '广东省湛江市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440801', '广东省湛江市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440802', '广东省湛江市赤坎区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440803', '广东省湛江市霞山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440804', '广东省湛江市坡头区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440811', '广东省湛江市麻章区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440823', '广东省湛江市遂溪县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440825', '广东省湛江市徐闻县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440881', '广东省湛江市廉江市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440882', '广东省湛江市雷州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440883', '广东省湛江市吴川市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440900', '广东省茂名市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440901', '广东省茂名市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440902', '广东省茂名市茂南区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440903', '广东省茂名市茂港区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440923', '广东省茂名市电白县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440981', '广东省茂名市高州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440982', '广东省茂名市化州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440983', '广东省茂名市信宜市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '441200', '广东省肇庆市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '441201', '广东省肇庆市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '441202', '广东省肇庆市端州区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '441203', '广东省肇庆市鼎湖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '441223', '广东省肇庆市广宁县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '441224', '广东省肇庆市怀集县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '441225', '广东省肇庆市封开县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '441226', '广东省肇庆市德庆县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '441283', '广东省肇庆市高要市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '441284', '广东省肇庆市四会市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '441300', '广东省惠州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '441301', '广东省惠州市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '441302', '广东省惠州市惠城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '441303', '广东省惠州市惠阳区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '441322', '广东省惠州市博罗县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '441323', '广东省惠州市惠东县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '441324', '广东省惠州市龙门县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '441400', '广东省梅州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '441401', '广东省梅州市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '441402', '广东省梅州市梅江区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '441421', '广东省梅州市梅县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '441422', '广东省梅州市大埔县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '441423', '广东省梅州市丰顺县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '441424', '广东省梅州市五华县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '441426', '广东省梅州市平远县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '441427', '广东省梅州市蕉岭县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '441481', '广东省梅州市兴宁市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '441500', '广东省汕尾市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '441501', '广东省汕尾市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '441502', '广东省汕尾市城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '441521', '广东省汕尾市海丰县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '441523', '广东省汕尾市陆河县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '441581', '广东省汕尾市陆丰市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '441600', '广东省河源市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '441601', '广东省河源市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '441602', '广东省河源市源城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '441621', '广东省河源市紫金县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '441622', '广东省河源市龙川县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '441623', '广东省河源市连平县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '441624', '广东省河源市和平县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '441625', '广东省河源市东源县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '441700', '广东省阳江市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '441701', '广东省阳江市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '441702', '广东省阳江市江城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '441721', '广东省阳江市阳西县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '441723', '广东省阳江市阳东县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '441781', '广东省阳江市阳春市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '441800', '广东省清远市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '441801', '广东省清远市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '441802', '广东省清远市清城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '441821', '广东省清远市佛冈县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '441823', '广东省清远市阳山县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '441825', '广东省清远市连山壮族瑶族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '441826', '广东省清远市连南瑶族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '441827', '广东省清远市清新县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '441881', '广东省清远市英德市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '441882', '广东省清远市连州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '441900', '广东省东莞市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '442000', '广东省中山市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '445100', '广东省潮州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '445101', '广东省潮州市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '445102', '广东省潮州市湘桥区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '445121', '广东省潮州市潮安县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '445122', '广东省潮州市饶平县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '445200', '广东省揭阳市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '445201', '广东省揭阳市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '445202', '广东省揭阳市榕城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '445221', '广东省揭阳市揭东县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '445222', '广东省揭阳市揭西县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '445224', '广东省揭阳市惠来县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '445281', '广东省揭阳市普宁市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '445300', '广东省云浮市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '445301', '广东省云浮市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '445302', '广东省云浮市云城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '445321', '广东省云浮市新兴县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '445322', '广东省云浮市郁南县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '445323', '广东省云浮市云安县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '445381', '广东省云浮市罗定市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450000', '广西壮族自治区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450100', '广西壮族自治区南宁市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450101', '广西壮族自治区南宁市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450102', '广西壮族自治区南宁市兴宁区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450103', '广西壮族自治区南宁市新城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450104', '广西壮族自治区南宁市城北区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450105', '广西壮族自治区南宁市江南区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450106', '广西壮族自治区南宁市永新区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450121', '广西壮族自治区南宁市邕宁县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450122', '广西壮族自治区南宁市武鸣县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450123', '广西壮族自治区南宁市隆安县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450124', '广西壮族自治区南宁市马山县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450125', '广西壮族自治区南宁市上林县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450126', '广西壮族自治区南宁市宾阳县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450127', '广西壮族自治区南宁市横县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450200', '广西壮族自治区柳州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450201', '广西壮族自治区柳州市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450202', '广西壮族自治区柳州市城中区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450203', '广西壮族自治区柳州市鱼峰区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450204', '广西壮族自治区柳州市柳南区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450205', '广西壮族自治区柳州市柳北区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450221', '广西壮族自治区柳州市柳江县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450222', '广西壮族自治区柳州市柳城县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450223', '广西壮族自治区柳州市鹿寨县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450224', '广西壮族自治区柳州市融安县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450225', '广西壮族自治区柳州市融水苗族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450226', '广西壮族自治区柳州市三江侗族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450300', '广西壮族自治区桂林市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450301', '广西壮族自治区桂林市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450302', '广西壮族自治区桂林市秀峰区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450303', '广西壮族自治区桂林市叠彩区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450304', '广西壮族自治区桂林市象山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450305', '广西壮族自治区桂林市七星区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450311', '广西壮族自治区桂林市雁山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450321', '广西壮族自治区桂林市阳朔县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450322', '广西壮族自治区桂林市临桂县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450323', '广西壮族自治区桂林市灵川县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450324', '广西壮族自治区桂林市全州县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450325', '广西壮族自治区桂林市兴安县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450326', '广西壮族自治区桂林市永福县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450327', '广西壮族自治区桂林市灌阳县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450328', '广西壮族自治区桂林市龙胜各族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450329', '广西壮族自治区桂林市资源县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450330', '广西壮族自治区桂林市平乐县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450331', '广西壮族自治区桂林市荔蒲县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450332', '广西壮族自治区桂林市恭城瑶族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450400', '广西壮族自治区梧州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450401', '广西壮族自治区梧州市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450403', '广西壮族自治区梧州市万秀区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450404', '广西壮族自治区梧州市蝶山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450405', '广西壮族自治区梧州市长洲区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450421', '广西壮族自治区梧州市苍梧县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450422', '广西壮族自治区梧州市藤县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450423', '广西壮族自治区梧州市蒙山县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450481', '广西壮族自治区梧州市岑溪市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450500', '广西壮族自治区北海市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450501', '广西壮族自治区北海市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450502', '广西壮族自治区北海市海城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450503', '广西壮族自治区北海市银海区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450512', '广西壮族自治区北海市铁山港区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450521', '广西壮族自治区北海市合浦县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450600', '广西壮族自治区防城港市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450601', '广西壮族自治区防城港市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450602', '广西壮族自治区防城港市港口区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450603', '广西壮族自治区防城港市防城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450621', '广西壮族自治区防城港市上思县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450681', '广西壮族自治区防城港市东兴市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450700', '广西壮族自治区钦州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450701', '广西壮族自治区钦州市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450702', '广西壮族自治区钦州市钦南区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450703', '广西壮族自治区钦州市钦北区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450721', '广西壮族自治区钦州市灵山县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450722', '广西壮族自治区钦州市浦北县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450800', '广西壮族自治区贵港市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450801', '广西壮族自治区贵港市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450802', '广西壮族自治区贵港市港北区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450803', '广西壮族自治区贵港市港南区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450804', '广西壮族自治区贵港市覃塘区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450821', '广西壮族自治区贵港市平南县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450881', '广西壮族自治区贵港市桂平市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450900', '广西壮族自治区玉林市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450901', '广西壮族自治区玉林市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450902', '广西壮族自治区玉林市玉州区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450921', '广西壮族自治区玉林市容县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450922', '广西壮族自治区玉林市陆川县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450923', '广西壮族自治区玉林市博白县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450924', '广西壮族自治区玉林市兴业县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '450981', '广西壮族自治区玉林市北流市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '451000', '广西壮族自治区百色市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '451001', '广西壮族自治区百色市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '451002', '广西壮族自治区百色市右江区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '451021', '广西壮族自治区百色市田阳县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '451022', '广西壮族自治区百色市田东县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '451023', '广西壮族自治区百色市平果县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '451024', '广西壮族自治区百色市德保县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '451025', '广西壮族自治区百色市靖西县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '451026', '广西壮族自治区百色市那坡县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '451027', '广西壮族自治区百色市凌云县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '451028', '广西壮族自治区百色市乐业县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '451029', '广西壮族自治区百色市田林县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '451030', '广西壮族自治区百色市西林县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '451031', '广西壮族自治区百色市隆林各族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '451100', '广西壮族自治区贺州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '451101', '广西壮族自治区贺州市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '451102', '广西壮族自治区贺州市八步区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '451121', '广西壮族自治区贺州市昭平县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '451122', '广西壮族自治区贺州市钟山县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '451123', '广西壮族自治区贺州市富川瑶族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '451200', '广西壮族自治区河池市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '451201', '广西壮族自治区河池市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '451202', '广西壮族自治区河池市金城江区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '451221', '广西壮族自治区河池市南丹县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '451222', '广西壮族自治区河池市天峨县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '451223', '广西壮族自治区河池市凤山县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '451224', '广西壮族自治区河池市东兰县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '451225', '广西壮族自治区河池市罗城仫佬族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '451226', '广西壮族自治区河池市环江毛南族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '451227', '广西壮族自治区河池市巴马瑶族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '451228', '广西壮族自治区河池市都安瑶族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '451229', '广西壮族自治区河池市大化瑶族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '451281', '广西壮族自治区河池市宜州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '451300', '广西壮族自治区来宾市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '451301', '广西壮族自治区来宾市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '451302', '广西壮族自治区来宾市兴宾区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '451321', '广西壮族自治区来宾市忻城县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '451322', '广西壮族自治区来宾市象州县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '451323', '广西壮族自治区来宾市武宣县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '451324', '广西壮族自治区来宾市金秀瑶族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '451381', '广西壮族自治区来宾市合山市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '451400', '广西壮族自治区崇左市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '451401', '广西壮族自治区崇左市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '451402', '广西壮族自治区崇左市江洲区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '451421', '广西壮族自治区崇左市扶绥县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '451422', '广西壮族自治区崇左市宁明县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '451423', '广西壮族自治区崇左市龙州县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '451424', '广西壮族自治区崇左市大新县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '451425', '广西壮族自治区崇左市天等县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '451481', '广西壮族自治区崇左市凭祥市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '460000', '海南省');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '460100', '海南省海口市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '460101', '海南省海口市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '460105', '海南省海口市秀英区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '460106', '海南省海口市龙华区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '460107', '海南省海口市琼山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '460108', '海南省海口市美兰区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '460200', '海南省三亚市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '460201', '海南省三亚市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '469000', '海南省省直辖县级行政单位');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '469001', '海南省省直辖县级行政单位五指山市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '469002', '海南省省直辖县级行政单位琼海市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '469003', '海南省省直辖县级行政单位儋州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '469005', '海南省省直辖县级行政单位文昌市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '469006', '海南省省直辖县级行政单位万宁市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '469007', '海南省省直辖县级行政单位东方市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '469025', '海南省省直辖县级行政单位定安县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '469026', '海南省省直辖县级行政单位屯昌县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '469027', '海南省省直辖县级行政单位澄迈县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '469028', '海南省省直辖县级行政单位临高县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '469030', '海南省省直辖县级行政单位白沙黎族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '469031', '海南省省直辖县级行政单位昌江黎族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '469033', '海南省省直辖县级行政单位乐东黎族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '469034', '海南省省直辖县级行政单位陵水黎族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '469035', '海南省省直辖县级行政单位保亭黎族苗族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '469036', '海南省省直辖县级行政单位琼中黎族苗族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '469037', '海南省省直辖县级行政单位西沙群岛');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '469038', '海南省省直辖县级行政单位南沙群岛');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '469039', '海南省省直辖县级行政单位中沙群岛的岛礁及其海域');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '500000', '重庆市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '500100', '重庆市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '500101', '重庆市万州区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '500102', '重庆市涪陵区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '500103', '重庆市渝中区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '500104', '重庆市大渡口区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '500105', '重庆市江北区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '500106', '重庆市沙坪坝区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '500107', '重庆市九龙坡区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '500108', '重庆市南岸区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '500109', '重庆市北碚区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '500110', '重庆市万盛区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '500111', '重庆市双桥区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '500112', '重庆市渝北区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '500113', '重庆市巴南区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '500114', '重庆市黔江区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '500115', '重庆市长寿区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '500116', '重庆市江津区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '500117', '重庆市合川区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '500118', '重庆市永川区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '500119', '重庆市南川区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '500200', '重庆市县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '500222', '重庆市綦江县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '500223', '重庆市潼南县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '500224', '重庆市铜梁县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '500225', '重庆市大足县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '500226', '重庆市荣昌县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '500227', '重庆市璧山县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '500228', '重庆市梁平县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '500229', '重庆市城口县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '500230', '重庆市丰都县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '500231', '重庆市垫江县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '500232', '重庆市武隆县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '500233', '重庆市忠县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '500234', '重庆市开县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '500235', '重庆市云阳县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '500236', '重庆市奉节县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '500237', '重庆市巫山县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '500238', '重庆市巫溪县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '500240', '重庆市石柱土家族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '500241', '重庆市秀山土家族苗族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '500242', '重庆市酉阳土家族苗族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '500243', '重庆市彭水苗族土家族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '500300', '重庆市市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '500381', '重庆市江津市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '500382', '重庆市合川市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '500383', '重庆市永川市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '500384', '重庆市南川市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510000', '四川省');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510100', '四川省成都市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510101', '四川省成都市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510104', '四川省成都市锦江区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510105', '四川省成都市青羊区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510106', '四川省成都市金牛区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510107', '四川省成都市武侯区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510108', '四川省成都市成华区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510112', '四川省成都市龙泉驿区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510113', '四川省成都市青白江区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510114', '四川省成都市新都区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510115', '四川省成都市温江区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510121', '四川省成都市金堂县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510122', '四川省成都市双流县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510124', '四川省成都市郫县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510129', '四川省成都市大邑县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510131', '四川省成都市蒲江县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510132', '四川省成都市新津县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510181', '四川省成都市都江堰市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510182', '四川省成都市彭州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510183', '四川省成都市邛崃市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510184', '四川省成都市崇州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510300', '四川省自贡市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510301', '四川省自贡市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510302', '四川省自贡市自流井区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510303', '四川省自贡市贡井区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510304', '四川省自贡市大安区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510311', '四川省自贡市沿滩区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510321', '四川省自贡市荣县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510322', '四川省自贡市富顺县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510400', '四川省攀枝花市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510401', '四川省攀枝花市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510402', '四川省攀枝花市东区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510403', '四川省攀枝花市西区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510411', '四川省攀枝花市仁和区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510421', '四川省攀枝花市米易县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510422', '四川省攀枝花市盐边县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510500', '四川省泸州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510501', '四川省泸州市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510502', '四川省泸州市江阳区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510503', '四川省泸州市纳溪区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510504', '四川省泸州市龙马潭区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510521', '四川省泸州市泸县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510522', '四川省泸州市合江县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510524', '四川省泸州市叙永县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510525', '四川省泸州市古蔺县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510600', '四川省德阳市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510601', '四川省德阳市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510603', '四川省德阳市旌阳区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510623', '四川省德阳市中江县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510626', '四川省德阳市罗江县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510681', '四川省德阳市广汉市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510682', '四川省德阳市什邡市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510683', '四川省德阳市绵竹市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510700', '四川省绵阳市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510701', '四川省绵阳市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510703', '四川省绵阳市涪城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510704', '四川省绵阳市游仙区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510722', '四川省绵阳市三台县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510723', '四川省绵阳市盐亭县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510724', '四川省绵阳市安县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510725', '四川省绵阳市梓潼县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510726', '四川省绵阳市北川羌族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510727', '四川省绵阳市平武县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510781', '四川省绵阳市江油市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510800', '四川省广元市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510801', '四川省广元市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510802', '四川省广元市市中区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510811', '四川省广元市元坝区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510812', '四川省广元市朝天区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510821', '四川省广元市旺苍县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510822', '四川省广元市青川县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510823', '四川省广元市剑阁县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510824', '四川省广元市苍溪县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510900', '四川省遂宁市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '510901', '四川省遂宁市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '431201', '湖南省怀化市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '431202', '湖南省怀化市鹤城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '431221', '湖南省怀化市中方县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '431222', '湖南省怀化市沅陵县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '431223', '湖南省怀化市辰溪县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '431224', '湖南省怀化市溆浦县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '431225', '湖南省怀化市会同县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '431226', '湖南省怀化市麻阳苗族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '431227', '湖南省怀化市新晃侗族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '431228', '湖南省怀化市芷江侗族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '431229', '湖南省怀化市靖州苗族侗族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '431230', '湖南省怀化市通道侗族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '431281', '湖南省怀化市洪江市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '431300', '湖南省娄底市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '431301', '湖南省娄底市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '431302', '湖南省娄底市娄星区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '431321', '湖南省娄底市双峰县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '431322', '湖南省娄底市新化县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '431381', '湖南省娄底市冷水江市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '431382', '湖南省娄底市涟源市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '433100', '湖南省湘西土家族苗族自治州');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '433101', '湖南省湘西土家族苗族自治州吉首市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '433122', '湖南省湘西土家族苗族自治州泸溪县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '433123', '湖南省湘西土家族苗族自治州凤凰县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '433124', '湖南省湘西土家族苗族自治州花垣县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '433125', '湖南省湘西土家族苗族自治州保靖县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '433126', '湖南省湘西土家族苗族自治州古丈县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '433127', '湖南省湘西土家族苗族自治州永顺县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '433130', '湖南省湘西土家族苗族自治州龙山县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440000', '广东省');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440100', '广东省广州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440101', '广东省广州市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440102', '广东省广州市东山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440103', '广东省广州市荔湾区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440104', '广东省广州市越秀区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440105', '广东省广州市海珠区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440106', '广东省广州市天河区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440107', '广东省广州市芳村区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440111', '广东省广州市白云区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440112', '广东省广州市黄埔区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440113', '广东省广州市番禺区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440114', '广东省广州市花都区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440183', '广东省广州市增城市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440184', '广东省广州市从化市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440200', '广东省韶关市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440201', '广东省韶关市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440202', '广东省韶关市北江区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440203', '广东省韶关市武江区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440204', '广东省韶关市浈江区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511402', '四川省眉山市东坡区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511421', '四川省眉山市仁寿县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511422', '四川省眉山市彭山县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511423', '四川省眉山市洪雅县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511424', '四川省眉山市丹棱县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511425', '四川省眉山市青神县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511500', '四川省宜宾市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511501', '四川省宜宾市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371700', '山东省菏泽市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371701', '山东省菏泽市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371702', '山东省菏泽市牡丹区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371721', '山东省菏泽市曹县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371722', '山东省菏泽市单县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371723', '山东省菏泽市成武县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371724', '山东省菏泽市巨野县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371725', '山东省菏泽市郓城县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371726', '山东省菏泽市鄄城县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371727', '山东省菏泽市定陶县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '371728', '山东省菏泽市东明县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410000', '河南省');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410100', '河南省郑州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410101', '河南省郑州市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410102', '河南省郑州市中原区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410103', '河南省郑州市二七区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410104', '河南省郑州市管城回族区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410105', '河南省郑州市金水区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410106', '河南省郑州市上街区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410108', '河南省郑州市邙山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410122', '河南省郑州市中牟县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410181', '河南省郑州市巩义市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410182', '河南省郑州市荥阳市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410183', '河南省郑州市新密市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410184', '河南省郑州市新郑市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410185', '河南省郑州市登封市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410200', '河南省开封市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410201', '河南省开封市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410202', '河南省开封市龙亭区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410203', '河南省开封市顺河回族区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410204', '河南省开封市鼓楼区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410205', '河南省开封市南关区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410211', '河南省开封市郊区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410221', '河南省开封市杞县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410222', '河南省开封市通许县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410223', '河南省开封市尉氏县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410224', '河南省开封市开封县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410225', '河南省开封市兰考县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410300', '河南省洛阳市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410301', '河南省洛阳市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410302', '河南省洛阳市老城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410303', '河南省洛阳市西工区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410304', '河南省洛阳市廛河回族区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410305', '河南省洛阳市涧西区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410306', '河南省洛阳市吉利区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410307', '河南省洛阳市洛龙区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410322', '河南省洛阳市孟津县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410323', '河南省洛阳市新安县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410324', '河南省洛阳市栾川县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410325', '河南省洛阳市嵩县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410326', '河南省洛阳市汝阳县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410327', '河南省洛阳市宜阳县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410328', '河南省洛阳市洛宁县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410329', '河南省洛阳市伊川县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410381', '河南省洛阳市偃师市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410400', '河南省平顶山市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410401', '河南省平顶山市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410402', '河南省平顶山市新华区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410403', '河南省平顶山市卫东区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410404', '河南省平顶山市石龙区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410411', '河南省平顶山市湛河区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410421', '河南省平顶山市宝丰县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410422', '河南省平顶山市叶县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410423', '河南省平顶山市鲁山县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410425', '河南省平顶山市郏县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410481', '河南省平顶山市舞钢市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410482', '河南省平顶山市汝州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410500', '河南省安阳市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410501', '河南省安阳市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410502', '河南省安阳市文峰区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410503', '河南省安阳市北关区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410505', '河南省安阳市殷都区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410506', '河南省安阳市龙安区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410522', '河南省安阳市安阳县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410523', '河南省安阳市汤阴县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410526', '河南省安阳市滑县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410527', '河南省安阳市内黄县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410581', '河南省安阳市林州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410600', '河南省鹤壁市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410601', '河南省鹤壁市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410602', '河南省鹤壁市鹤山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410603', '河南省鹤壁市山城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410611', '河南省鹤壁市淇滨区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410621', '河南省鹤壁市浚县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410622', '河南省鹤壁市淇县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410700', '河南省新乡市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410701', '河南省新乡市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410702', '河南省新乡市红旗区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410703', '河南省新乡市卫滨区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410704', '河南省新乡市凤泉区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410711', '河南省新乡市牧野区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410721', '河南省新乡市新乡县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410724', '河南省新乡市获嘉县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410725', '河南省新乡市原阳县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410726', '河南省新乡市延津县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410727', '河南省新乡市封丘县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410728', '河南省新乡市长垣县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410781', '河南省新乡市卫辉市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410782', '河南省新乡市辉县市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410800', '河南省焦作市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410801', '河南省焦作市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410802', '河南省焦作市解放区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410803', '河南省焦作市中站区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410804', '河南省焦作市马村区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410811', '河南省焦作市山阳区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410821', '河南省焦作市修武县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410822', '河南省焦作市博爱县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410823', '河南省焦作市武陟县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410825', '河南省焦作市温县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410881', '河南省焦作市济源市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410882', '河南省焦作市沁阳市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410883', '河南省焦作市孟州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410900', '河南省濮阳市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410901', '河南省濮阳市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410902', '河南省濮阳市华龙区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410922', '河南省濮阳市清丰县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410923', '河南省濮阳市南乐县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410926', '河南省濮阳市范县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410927', '河南省濮阳市台前县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '410928', '河南省濮阳市濮阳县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411000', '河南省许昌市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411001', '河南省许昌市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411002', '河南省许昌市魏都区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411023', '河南省许昌市许昌县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411024', '河南省许昌市鄢陵县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411025', '河南省许昌市襄城县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411081', '河南省许昌市禹州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411082', '河南省许昌市长葛市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411100', '河南省漯河市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411101', '河南省漯河市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411102', '河南省漯河市源汇区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411121', '河南省漯河市舞阳县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411122', '河南省漯河市临颍县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411123', '河南省漯河市郾城县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411200', '河南省三门峡市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411201', '河南省三门峡市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411202', '河南省三门峡市湖滨区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411221', '河南省三门峡市渑池县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411222', '河南省三门峡市陕县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411224', '河南省三门峡市卢氏县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411281', '河南省三门峡市义马市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411282', '河南省三门峡市灵宝市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411300', '河南省南阳市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411301', '河南省南阳市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411302', '河南省南阳市宛城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411303', '河南省南阳市卧龙区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411321', '河南省南阳市南召县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411322', '河南省南阳市方城县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411323', '河南省南阳市西峡县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411324', '河南省南阳市镇平县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411325', '河南省南阳市内乡县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411326', '河南省南阳市淅川县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411327', '河南省南阳市社旗县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411328', '河南省南阳市唐河县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411329', '河南省南阳市新野县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411330', '河南省南阳市桐柏县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411381', '河南省南阳市邓州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411400', '河南省商丘市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411401', '河南省商丘市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411402', '河南省商丘市梁园区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411403', '河南省商丘市睢阳区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411421', '河南省商丘市民权县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411422', '河南省商丘市睢县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411423', '河南省商丘市宁陵县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411424', '河南省商丘市柘城县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411425', '河南省商丘市虞城县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411426', '河南省商丘市夏邑县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411481', '河南省商丘市永城市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411500', '河南省信阳市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411501', '河南省信阳市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411502', '河南省信阳市师河区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411503', '河南省信阳市平桥区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411521', '河南省信阳市罗山县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411522', '河南省信阳市光山县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411523', '河南省信阳市新县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411524', '河南省信阳市商城县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411525', '河南省信阳市固始县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411526', '河南省信阳市潢川县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411527', '河南省信阳市淮滨县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411528', '河南省信阳市息县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411600', '河南省周口市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411601', '河南省周口市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411602', '河南省周口市川汇区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411621', '河南省周口市扶沟县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411622', '河南省周口市西华县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411623', '河南省周口市商水县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411624', '河南省周口市沈丘县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411625', '河南省周口市郸城县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411626', '河南省周口市淮阳县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411627', '河南省周口市太康县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411628', '河南省周口市鹿邑县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411681', '河南省周口市项城市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411700', '河南省驻马店市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411701', '河南省驻马店市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411702', '河南省驻马店市驿城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411721', '河南省驻马店市西平县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411722', '河南省驻马店市上蔡县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411723', '河南省驻马店市平舆县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411724', '河南省驻马店市正阳县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411725', '河南省驻马店市确山县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411726', '河南省驻马店市泌阳县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411727', '河南省驻马店市汝南县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411728', '河南省驻马店市遂平县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '411729', '河南省驻马店市新蔡县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420000', '湖北省');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420100', '湖北省武汉市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420101', '湖北省武汉市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420102', '湖北省武汉市江岸区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420103', '湖北省武汉市江汉区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420104', '湖北省武汉市乔口区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420105', '湖北省武汉市汉阳区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420106', '湖北省武汉市武昌区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420107', '湖北省武汉市青山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420111', '湖北省武汉市洪山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420112', '湖北省武汉市东西湖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420113', '湖北省武汉市汉南区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420114', '湖北省武汉市蔡甸区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420115', '湖北省武汉市江夏区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420116', '湖北省武汉市黄陂区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420117', '湖北省武汉市新洲区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420200', '湖北省黄石市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420201', '湖北省黄石市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420202', '湖北省黄石市黄石港区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420203', '湖北省黄石市西塞山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420204', '湖北省黄石市下陆区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420205', '湖北省黄石市铁山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420222', '湖北省黄石市阳新县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420281', '湖北省黄石市大冶市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420300', '湖北省十堰市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420301', '湖北省十堰市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420302', '湖北省十堰市茅箭区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420303', '湖北省十堰市张湾区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420321', '湖北省十堰市郧县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420322', '湖北省十堰市郧西县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420323', '湖北省十堰市竹山县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420324', '湖北省十堰市竹溪县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420325', '湖北省十堰市房县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420381', '湖北省十堰市丹江口市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420500', '湖北省宜昌市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420501', '湖北省宜昌市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420502', '湖北省宜昌市西陵区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420503', '湖北省宜昌市伍家岗区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420504', '湖北省宜昌市点军区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420505', '湖北省宜昌市虎亭区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420506', '湖北省宜昌市夷陵区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420525', '湖北省宜昌市远安县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420526', '湖北省宜昌市兴山县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420527', '湖北省宜昌市秭归县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420528', '湖北省宜昌市长阳土家族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420529', '湖北省宜昌市五峰土家族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420581', '湖北省宜昌市宜都市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420582', '湖北省宜昌市当阳市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420583', '湖北省宜昌市枝江市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420600', '湖北省襄樊市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420601', '湖北省襄樊市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420602', '湖北省襄樊市襄城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420606', '湖北省襄樊市樊城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420607', '湖北省襄樊市襄阳区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420624', '湖北省襄樊市南漳县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420625', '湖北省襄樊市谷城县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420626', '湖北省襄樊市保康县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420682', '湖北省襄樊市老河口市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420683', '湖北省襄樊市枣阳市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420684', '湖北省襄樊市宜城市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420700', '湖北省鄂州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420701', '湖北省鄂州市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420702', '湖北省鄂州市梁子湖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420703', '湖北省鄂州市华容区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420704', '湖北省鄂州市鄂城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420800', '湖北省荆门市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420801', '湖北省荆门市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420802', '湖北省荆门市东宝区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420804', '湖北省荆门市掇刀区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420821', '湖北省荆门市京山县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420822', '湖北省荆门市沙洋县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420881', '湖北省荆门市钟祥市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420900', '湖北省孝感市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420901', '湖北省孝感市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420902', '湖北省孝感市孝南区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420921', '湖北省孝感市孝昌县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420922', '湖北省孝感市大悟县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420923', '湖北省孝感市云梦县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420981', '湖北省孝感市应城市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420982', '湖北省孝感市安陆市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '420984', '湖北省孝感市汉川市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '421000', '湖北省荆州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '421001', '湖北省荆州市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '421002', '湖北省荆州市沙市区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '421003', '湖北省荆州市荆州区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '421022', '湖北省荆州市公安县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '421023', '湖北省荆州市监利县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '421024', '湖北省荆州市江陵县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '421081', '湖北省荆州市石首市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '421083', '湖北省荆州市洪湖市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '421087', '湖北省荆州市松滋市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '421100', '湖北省黄冈市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '421101', '湖北省黄冈市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '421102', '湖北省黄冈市黄州区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '421121', '湖北省黄冈市团风县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '421122', '湖北省黄冈市红安县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '421123', '湖北省黄冈市罗田县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '421124', '湖北省黄冈市英山县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '421125', '湖北省黄冈市浠水县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '421126', '湖北省黄冈市蕲春县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '421127', '湖北省黄冈市黄梅县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '421181', '湖北省黄冈市麻城市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '421182', '湖北省黄冈市武穴市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '421200', '湖北省咸宁市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '421201', '湖北省咸宁市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '421202', '湖北省咸宁市咸安区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '421221', '湖北省咸宁市嘉鱼县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '421222', '湖北省咸宁市通城县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '421223', '湖北省咸宁市崇阳县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '421224', '湖北省咸宁市通山县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '421281', '湖北省咸宁市赤壁市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '421300', '湖北省随州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '421301', '湖北省随州市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '421302', '湖北省随州市曾都区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '421381', '湖北省随州市广水市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '422800', '湖北省恩施土家族苗族自治州');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '422801', '湖北省恩施土家族苗族自治州恩施市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '422802', '湖北省恩施土家族苗族自治州利川市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '422822', '湖北省恩施土家族苗族自治州建始县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '422823', '湖北省恩施土家族苗族自治州巴东县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '422825', '湖北省恩施土家族苗族自治州宣恩县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '422826', '湖北省恩施土家族苗族自治州咸丰县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '422827', '湖北省恩施土家族苗族自治州来凤县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '422828', '湖北省恩施土家族苗族自治州鹤峰县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '429000', '湖北省省直辖行政单位');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '429004', '湖北省省直辖行政单位仙桃市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '429005', '湖北省省直辖行政单位潜江市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '429006', '湖北省省直辖行政单位天门市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '429021', '湖北省省直辖行政单位神农架林区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430000', '湖南省');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430100', '湖南省长沙市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430101', '湖南省长沙市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430102', '湖南省长沙市芙蓉区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430103', '湖南省长沙市天心区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430104', '湖南省长沙市岳麓区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430105', '湖南省长沙市开福区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430111', '湖南省长沙市雨花区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430121', '湖南省长沙市长沙县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430122', '湖南省长沙市望城县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430124', '湖南省长沙市宁乡县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430181', '湖南省长沙市浏阳市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430200', '湖南省株洲市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430201', '湖南省株洲市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430202', '湖南省株洲市荷塘区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430203', '湖南省株洲市芦淞区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430204', '湖南省株洲市石峰区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430211', '湖南省株洲市天元区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430221', '湖南省株洲市株洲县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430223', '湖南省株洲市攸县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430224', '湖南省株洲市茶陵县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430225', '湖南省株洲市炎陵县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430281', '湖南省株洲市醴陵市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430300', '湖南省湘潭市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430301', '湖南省湘潭市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430302', '湖南省湘潭市雨湖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430304', '湖南省湘潭市岳塘区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430321', '湖南省湘潭市湘潭县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430381', '湖南省湘潭市湘乡市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430382', '湖南省湘潭市韶山市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430400', '湖南省衡阳市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430401', '湖南省衡阳市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430405', '湖南省衡阳市珠晖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430406', '湖南省衡阳市雁峰区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430407', '湖南省衡阳市石鼓区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430408', '湖南省衡阳市蒸湘区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430412', '湖南省衡阳市南岳区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430421', '湖南省衡阳市衡阳县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430422', '湖南省衡阳市衡南县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430423', '湖南省衡阳市衡山县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430424', '湖南省衡阳市衡东县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430426', '湖南省衡阳市祁东县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430481', '湖南省衡阳市耒阳市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430482', '湖南省衡阳市常宁市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430500', '湖南省邵阳市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430501', '湖南省邵阳市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430502', '湖南省邵阳市双清区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430503', '湖南省邵阳市大祥区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430511', '湖南省邵阳市北塔区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430521', '湖南省邵阳市邵东县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430522', '湖南省邵阳市新邵县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430523', '湖南省邵阳市邵阳县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430524', '湖南省邵阳市隆回县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430525', '湖南省邵阳市洞口县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430527', '湖南省邵阳市绥宁县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430528', '湖南省邵阳市新宁县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430529', '湖南省邵阳市城步苗族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430581', '湖南省邵阳市武冈市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430600', '湖南省岳阳市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430601', '湖南省岳阳市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430602', '湖南省岳阳市岳阳楼区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430603', '湖南省岳阳市云溪区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430611', '湖南省岳阳市君山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430621', '湖南省岳阳市岳阳县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430623', '湖南省岳阳市华容县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430624', '湖南省岳阳市湘阴县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430626', '湖南省岳阳市平江县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430681', '湖南省岳阳市汨罗市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430682', '湖南省岳阳市临湘市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430700', '湖南省常德市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430701', '湖南省常德市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430702', '湖南省常德市武陵区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430703', '湖南省常德市鼎城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430721', '湖南省常德市安乡县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430722', '湖南省常德市汉寿县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430723', '湖南省常德市澧县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430724', '湖南省常德市临澧县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430725', '湖南省常德市桃源县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430726', '湖南省常德市石门县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430781', '湖南省常德市津市市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430800', '湖南省张家界市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430801', '湖南省张家界市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430802', '湖南省张家界市永定区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430811', '湖南省张家界市武陵源区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430821', '湖南省张家界市慈利县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430822', '湖南省张家界市桑植县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430900', '湖南省益阳市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430901', '湖南省益阳市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430902', '湖南省益阳市资阳区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430903', '湖南省益阳市赫山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430921', '湖南省益阳市南县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430922', '湖南省益阳市桃江县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430923', '湖南省益阳市安化县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '430981', '湖南省益阳市沅江市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '431000', '湖南省郴州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '431001', '湖南省郴州市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '431002', '湖南省郴州市北湖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '431003', '湖南省郴州市苏仙区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '431021', '湖南省郴州市桂阳县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '431022', '湖南省郴州市宜章县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '431023', '湖南省郴州市永兴县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '431024', '湖南省郴州市嘉禾县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '431025', '湖南省郴州市临武县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '431026', '湖南省郴州市汝城县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '431027', '湖南省郴州市桂东县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '431028', '湖南省郴州市安仁县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '431081', '湖南省郴州市资兴市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '431100', '湖南省永州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '431101', '湖南省永州市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '431102', '湖南省永州市芝山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '431103', '湖南省永州市冷水滩区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '431121', '湖南省永州市祁阳县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '431122', '湖南省永州市东安县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '431123', '湖南省永州市双牌县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '431124', '湖南省永州市道县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '431125', '湖南省永州市江永县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '431126', '湖南省永州市宁远县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '431127', '湖南省永州市蓝山县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '431128', '湖南省永州市新田县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '431129', '湖南省永州市江华瑶族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '431200', '湖南省怀化市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330903', '浙江省舟山市普陀区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330921', '浙江省舟山市岱山县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '330922', '浙江省舟山市嵊泗县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '331000', '浙江省台州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '331001', '浙江省台州市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '331002', '浙江省台州市椒江区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '331003', '浙江省台州市黄岩区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '331004', '浙江省台州市路桥区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '331021', '浙江省台州市玉环县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '331022', '浙江省台州市三门县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '331023', '浙江省台州市天台县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '331024', '浙江省台州市仙居县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '331081', '浙江省台州市温岭市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '331082', '浙江省台州市临海市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '331100', '浙江省丽水市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '331101', '浙江省丽水市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '331102', '浙江省丽水市莲都区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '331121', '浙江省丽水市青田县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '331122', '浙江省丽水市缙云县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '331123', '浙江省丽水市遂昌县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '331124', '浙江省丽水市松阳县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '331125', '浙江省丽水市云和县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '331126', '浙江省丽水市庆元县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '331127', '浙江省丽水市景宁畲族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '331181', '浙江省丽水市龙泉市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340000', '安徽省');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340100', '安徽省合肥市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340101', '安徽省合肥市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340102', '安徽省合肥市瑶海区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340103', '安徽省合肥市庐阳区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340104', '安徽省合肥市蜀山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340111', '安徽省合肥市包河区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340121', '安徽省合肥市长丰县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340122', '安徽省合肥市肥东县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340123', '安徽省合肥市肥西县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340200', '安徽省芜湖市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340201', '安徽省芜湖市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340202', '安徽省芜湖市镜湖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '340203', '安徽省芜湖市马塘区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440221', '广东省韶关市曲江县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440222', '广东省韶关市始兴县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440224', '广东省韶关市仁化县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440229', '广东省韶关市翁源县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440232', '广东省韶关市乳源瑶族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440233', '广东省韶关市新丰县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '440281', '广东省韶关市乐昌市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530323', '云南省曲靖市师宗县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530324', '云南省曲靖市罗平县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530325', '云南省曲靖市富源县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530326', '云南省曲靖市会泽县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530328', '云南省曲靖市沾益县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530381', '云南省曲靖市宣威市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530400', '云南省玉溪市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530401', '云南省玉溪市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530402', '云南省玉溪市红塔区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530421', '云南省玉溪市江川县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530422', '云南省玉溪市澄江县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530423', '云南省玉溪市通海县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530424', '云南省玉溪市华宁县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530425', '云南省玉溪市易门县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530426', '云南省玉溪市峨山彝族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530427', '云南省玉溪市新平彝族傣族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530428', '云南省玉溪市元江哈尼族彝族傣族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530500', '云南省保山市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530501', '云南省保山市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530502', '云南省保山市隆阳区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530521', '云南省保山市施甸县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530522', '云南省保山市腾冲县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530523', '云南省保山市龙陵县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530524', '云南省保山市昌宁县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530600', '云南省昭通市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530601', '云南省昭通市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530602', '云南省昭通市昭阳区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530621', '云南省昭通市鲁甸县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530622', '云南省昭通市巧家县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530623', '云南省昭通市盐津县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530624', '云南省昭通市大关县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530625', '云南省昭通市永善县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530626', '云南省昭通市绥江县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530627', '云南省昭通市镇雄县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530628', '云南省昭通市彝良县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530629', '云南省昭通市威信县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530630', '云南省昭通市水富县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530700', '云南省丽江市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530701', '云南省丽江市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530702', '云南省丽江市古城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530721', '云南省丽江市玉龙纳西族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530722', '云南省丽江市永胜县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530723', '云南省丽江市华坪县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530724', '云南省丽江市宁蒗彝族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530800', '云南省思茅市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530801', '云南省思茅市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530802', '云南省思茅市翠云区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530821', '云南省思茅市普洱哈尼族彝族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530822', '云南省思茅市墨江哈尼族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530823', '云南省思茅市景东彝族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530824', '云南省思茅市景谷傣族彝族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530825', '云南省思茅市镇沅彝族哈尼族拉祜族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530826', '云南省思茅市江城哈尼族彝族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530827', '云南省思茅市孟连傣族拉祜族佤族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530828', '云南省思茅市澜沧拉祜族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530829', '云南省思茅市西盟佤族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530900', '云南省临沧市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530901', '云南省临沧市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530902', '云南省临沧市临翔区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530921', '云南省临沧市凤庆县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610632', '陕西省延安市黄陵县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610700', '陕西省汉中市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610701', '陕西省汉中市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610702', '陕西省汉中市汉台区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610721', '陕西省汉中市南郑县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610722', '陕西省汉中市城固县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610723', '陕西省汉中市洋县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610724', '陕西省汉中市西乡县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610725', '陕西省汉中市勉县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610726', '陕西省汉中市宁强县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610727', '陕西省汉中市略阳县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610728', '陕西省汉中市镇巴县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610729', '陕西省汉中市留坝县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610730', '陕西省汉中市佛坪县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610800', '陕西省榆林市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610801', '陕西省榆林市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610802', '陕西省榆林市榆阳区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610821', '陕西省榆林市神木县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610822', '陕西省榆林市府谷县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610823', '陕西省榆林市横山县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610824', '陕西省榆林市靖边县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610825', '陕西省榆林市定边县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610826', '陕西省榆林市绥德县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610827', '陕西省榆林市米脂县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610828', '陕西省榆林市佳县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610829', '陕西省榆林市吴堡县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610830', '陕西省榆林市清涧县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610831', '陕西省榆林市子洲县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610900', '陕西省安康市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610901', '陕西省安康市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610902', '陕西省安康市汉滨区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610921', '陕西省安康市汉阴县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610922', '陕西省安康市石泉县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610923', '陕西省安康市宁陕县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610924', '陕西省安康市紫阳县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610925', '陕西省安康市岚皋县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610926', '陕西省安康市平利县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610927', '陕西省安康市镇坪县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610928', '陕西省安康市旬阳县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610929', '陕西省安康市白河县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '611000', '陕西省商洛市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '611001', '陕西省商洛市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '611002', '陕西省商洛市商州区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '611021', '陕西省商洛市洛南县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '611022', '陕西省商洛市丹凤县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '611023', '陕西省商洛市商南县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '611024', '陕西省商洛市山阳县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '611025', '陕西省商洛市镇安县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '611026', '陕西省商洛市柞水县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620000', '甘肃省');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620100', '甘肃省兰州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620101', '甘肃省兰州市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620102', '甘肃省兰州市城关区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620103', '甘肃省兰州市七里河区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620104', '甘肃省兰州市西固区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620105', '甘肃省兰州市安宁区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620111', '甘肃省兰州市红古区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620121', '甘肃省兰州市永登县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620122', '甘肃省兰州市皋兰县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620123', '甘肃省兰州市榆中县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620200', '甘肃省嘉峪关市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620201', '甘肃省嘉峪关市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620300', '甘肃省金昌市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620301', '甘肃省金昌市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620302', '甘肃省金昌市金川区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620321', '甘肃省金昌市永昌县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620400', '甘肃省白银市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620401', '甘肃省白银市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620402', '甘肃省白银市白银区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620403', '甘肃省白银市平川区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620421', '甘肃省白银市靖远县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620422', '甘肃省白银市会宁县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620423', '甘肃省白银市景泰县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620500', '甘肃省天水市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620501', '甘肃省天水市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620502', '甘肃省天水市秦城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620503', '甘肃省天水市北道区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620521', '甘肃省天水市清水县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620522', '甘肃省天水市秦安县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620523', '甘肃省天水市甘谷县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620524', '甘肃省天水市武山县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620525', '甘肃省天水市张家川回族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620600', '甘肃省武威市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620601', '甘肃省武威市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620602', '甘肃省武威市凉州区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620621', '甘肃省武威市民勤县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620622', '甘肃省武威市古浪县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620623', '甘肃省武威市天祝藏族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620700', '甘肃省张掖市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620701', '甘肃省张掖市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620702', '甘肃省张掖市甘州区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620721', '甘肃省张掖市肃南裕固族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620722', '甘肃省张掖市民乐县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620723', '甘肃省张掖市临泽县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620724', '甘肃省张掖市高台县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620725', '甘肃省张掖市山丹县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620800', '甘肃省平凉市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620801', '甘肃省平凉市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620802', '甘肃省平凉市崆峒区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620821', '甘肃省平凉市泾川县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620822', '甘肃省平凉市灵台县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620823', '甘肃省平凉市崇信县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620824', '甘肃省平凉市华亭县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620825', '甘肃省平凉市庄浪县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620826', '甘肃省平凉市静宁县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620900', '甘肃省酒泉市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620901', '甘肃省酒泉市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620902', '甘肃省酒泉市肃州区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620921', '甘肃省酒泉市金塔县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620922', '甘肃省酒泉市瓜州县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620923', '甘肃省酒泉市肃北蒙古族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620924', '甘肃省酒泉市阿克塞哈萨克族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620981', '甘肃省酒泉市玉门市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '620982', '甘肃省酒泉市敦煌市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '621000', '甘肃省庆阳市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '621001', '甘肃省庆阳市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '621002', '甘肃省庆阳市西峰区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '621021', '甘肃省庆阳市庆城县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '621022', '甘肃省庆阳市环县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '621023', '甘肃省庆阳市华池县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '621024', '甘肃省庆阳市合水县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '621025', '甘肃省庆阳市正宁县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '621026', '甘肃省庆阳市宁县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '621027', '甘肃省庆阳市镇原县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '621100', '甘肃省定西市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '621101', '甘肃省定西市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '621102', '甘肃省定西市安定区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '621121', '甘肃省定西市通渭县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '621122', '甘肃省定西市陇西县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '621123', '甘肃省定西市渭源县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '621124', '甘肃省定西市临洮县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '621125', '甘肃省定西市漳县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '621126', '甘肃省定西市岷县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '622600', '甘肃省陇南地区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '622621', '甘肃省陇南地区武都县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '622623', '甘肃省陇南地区宕昌县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '622624', '甘肃省陇南地区成县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '622625', '甘肃省陇南地区康县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '622626', '甘肃省陇南地区文县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '622627', '甘肃省陇南地区西和县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '622628', '甘肃省陇南地区礼县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '622629', '甘肃省陇南地区两当县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '622630', '甘肃省陇南地区徽县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '622900', '甘肃省临夏回族自治州');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '622901', '甘肃省临夏回族自治州临夏市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '622921', '甘肃省临夏回族自治州临夏县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '622922', '甘肃省临夏回族自治州康乐县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '622923', '甘肃省临夏回族自治州永靖县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '622924', '甘肃省临夏回族自治州广河县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '622925', '甘肃省临夏回族自治州和政县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '622926', '甘肃省临夏回族自治州东乡族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '622927', '甘肃省临夏回族自治州积石山保安族东乡族撒拉族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '623000', '甘肃省甘南藏族自治州');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '623001', '甘肃省甘南藏族自治州合作市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '623021', '甘肃省甘南藏族自治州临潭县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '623022', '甘肃省甘南藏族自治州卓尼县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '623023', '甘肃省甘南藏族自治州舟曲县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '623024', '甘肃省甘南藏族自治州迭部县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '623025', '甘肃省甘南藏族自治州玛曲县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '623026', '甘肃省甘南藏族自治州碌曲县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '623027', '甘肃省甘南藏族自治州夏河县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '630000', '青海省');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '630100', '青海省西宁市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '630101', '青海省西宁市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '630102', '青海省西宁市城东区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '630103', '青海省西宁市城中区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '630104', '青海省西宁市城西区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '630105', '青海省西宁市城北区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '630121', '青海省西宁市大通回族土族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '630122', '青海省西宁市湟中县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '630123', '青海省西宁市湟源县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '632100', '青海省海东地区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '632121', '青海省海东地区平安县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '632122', '青海省海东地区民和回族土族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '632123', '青海省海东地区乐都县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '632126', '青海省海东地区互助土族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '632127', '青海省海东地区化隆回族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '632128', '青海省海东地区循化撒拉族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '632200', '青海省海北藏族自治州');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '632221', '青海省海北藏族自治州门源回族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '632222', '青海省海北藏族自治州祁连县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '632223', '青海省海北藏族自治州海晏县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '632224', '青海省海北藏族自治州刚察县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '632300', '青海省黄南藏族自治州');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '632321', '青海省黄南藏族自治州同仁县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '632322', '青海省黄南藏族自治州尖扎县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '632323', '青海省黄南藏族自治州泽库县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '632324', '青海省黄南藏族自治州河南蒙古族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '632500', '青海省海南藏族自治州');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '632521', '青海省海南藏族自治州共和县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '632522', '青海省海南藏族自治州同德县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '632523', '青海省海南藏族自治州贵德县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '632524', '青海省海南藏族自治州兴海县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '632525', '青海省海南藏族自治州贵南县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '632600', '青海省果洛藏族自治州');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '632621', '青海省果洛藏族自治州玛沁县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '632622', '青海省果洛藏族自治州班玛县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '632623', '青海省果洛藏族自治州甘德县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '632624', '青海省果洛藏族自治州达日县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '632625', '青海省果洛藏族自治州久治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '632626', '青海省果洛藏族自治州玛多县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '632700', '青海省玉树藏族自治州');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '632721', '青海省玉树藏族自治州玉树县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '632722', '青海省玉树藏族自治州杂多县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '632723', '青海省玉树藏族自治州称多县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '632724', '青海省玉树藏族自治州治多县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '632725', '青海省玉树藏族自治州囊谦县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '632726', '青海省玉树藏族自治州曲麻莱县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '632800', '青海省海西蒙古族藏族自治州');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '632801', '青海省海西蒙古族藏族自治州格尔木市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '632802', '青海省海西蒙古族藏族自治州德令哈市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '632821', '青海省海西蒙古族藏族自治州乌兰县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '632822', '青海省海西蒙古族藏族自治州都兰县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '632823', '青海省海西蒙古族藏族自治州天峻县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '640000', '宁夏回族自治区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '640100', '宁夏回族自治区银川市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '640101', '宁夏回族自治区银川市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '640104', '宁夏回族自治区银川市兴庆区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '640105', '宁夏回族自治区银川市西夏区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '640106', '宁夏回族自治区银川市金凤区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '640121', '宁夏回族自治区银川市永宁县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '640122', '宁夏回族自治区银川市贺兰县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '640181', '宁夏回族自治区银川市灵武市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '640200', '宁夏回族自治区石嘴山市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '640201', '宁夏回族自治区石嘴山市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '640202', '宁夏回族自治区石嘴山市大武口区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '640205', '宁夏回族自治区石嘴山市惠农区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '640221', '宁夏回族自治区石嘴山市平罗县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '640300', '宁夏回族自治区吴忠市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '640301', '宁夏回族自治区吴忠市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '640302', '宁夏回族自治区吴忠市利通区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '640323', '宁夏回族自治区吴忠市盐池县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '640324', '宁夏回族自治区吴忠市同心县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '640381', '宁夏回族自治区吴忠市青铜峡市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '301', '组织、胁迫、诱骗进行恐怖、残忍表演');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '302', '强迫劳动');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '303', '非法限制人身自由');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '304', '非法侵入住宅');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '305', '非法搜查身体');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '306', '胁迫、诱骗、利用他人乞讨');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '307', '以滋扰他人的方式乞讨');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '308', '威胁人身安全');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '309', '侮辱');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '310', '诽谤');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '311', '诬告陷害');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '312', '威胁、侮辱、殴打、打击报复证人及其近亲属');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '313', '发送信息干扰正常生活');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '314', '侵犯隐私');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '315', '殴打他人');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '316', '故意伤害');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '317', '猥亵');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '318', '在公共场所故意裸露身体');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '319', '虐待');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '320', '遗弃');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '321', '强迫交易');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '322', '煽动民族仇恨、民族歧视');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '323', '刊载民族歧视、侮辱内容');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '324', '冒领、隐匿、毁弃、私自开拆、非法检查他人邮件');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '325', '盗窃');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '326', '诈骗');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '327', '哄抢');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '328', '抢夺');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '329', '敲诈勒索');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '330', '故意损毁财物');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '399', '其他侵犯他人人身权利、财产权利的案件');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '400', '妨害社会管理的案件');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '401', '拒不执行紧急状态下的决定、命令');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '402', '阻碍执行职务');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '403', '阻碍特种车辆通行');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '404', '冲闯警戒带、警戒区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '405', '招摇撞骗');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '406', '伪造、变造、买卖公文、证件、证明文件、印章');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '407', '买卖、使用伪造、变造的公文、证件、证明文件');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '408', '伪造、变造、倒卖有价票证、凭证');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '409', '伪造、变造船舶户牌');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '410', '买卖、使用伪造、变造的船舶户牌');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '411', '涂改船舶发动机号码');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '412', '驾船擅自进入、停靠国家管制的水域、岛屿');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '413', '非法以社团名义活动');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '414', '被撤销登记的社团继续活动');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '415', '擅自经营需公安机关许可的行业');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '416', '煽动、策划非法集会、游行、示威');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '417', '不按规定登记住宿旅客信息');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '418', '不制止住宿旅客带入危险物质');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '419', '明知住宿旅客是犯罪嫌疑人不报告');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '420', '将房屋出租给无身份证件人居住');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '421', '不按规定登记承租人信息');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '422', '明知承租人利用出租屋犯罪不报告');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '423', '制造噪声干扰正常生活');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '424', '违法承接典当物品');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '425', '典当业工作人员发现违法犯罪嫌疑人、赃物不报告');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '426', '违法收购废旧专用器材');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '427', '收购赃物、有赃物嫌疑的物品');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '428', '收购国家禁止收购的其他物品');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '429', '隐藏、转移、变卖、损毁依法扣押、查封、冻结的财物');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '430', '伪造、隐匿、毁灭证据');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '431', '提供虚假证言');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '432', '谎报案情');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '433', '窝藏、转移、代销赃物');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '434', '违反监督管理规定');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '435', '协助组织、运送他人偷越国(边)境');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '436', '为偷越国(边)境人员提供条件');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '437', '偷越国(边)境');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '438', '故意损坏文物、名胜古迹');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '439', '违法实施危及文物安全的活动');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '440', '偷开机动车');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '441', '无证驾驶、偷开航空器、机动船舶');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '442', '破坏、污损坟墓');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '443', '毁坏、丢弃尸骨、骨灰');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '444', '违法停放尸体');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '445', '卖淫');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '446', '嫖娼');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '447', '拉客招嫖');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '448', '引诱、容留、介绍卖淫');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '449', '制作、运输、复制、出售、出租淫秽物品');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '450', '传播淫秽信息');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '451', '组织播放淫秽音像');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '452', '组织淫秽表演');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '453', '进行淫秽表演');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '454', '参与聚众淫乱');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '455', '为淫秽活动提供条件');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '456', '为赌博提供条件');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '457', '赌博');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '458', '非法种植毒品原植物');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '459', '非法买卖、运输、携带、持有毒品原植物种苗');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '460', '非法运输、买卖、储存、使用罂粟壳');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '461', '非法持有毒品');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '462', '向他人提供毒品');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '463', '吸毒');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '464', '胁迫、欺骗开具麻醉药品、精神药品');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '465', '教唆、引诱、欺骗吸毒');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '466', '为吸毒、赌博、卖淫、嫖娼人员通风报信');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '467', '饲养动物干扰正常生活');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '468', '放任动物恐吓他人');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '499', '其他妨害社会管理的案件');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '500', '妨害司法审判');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '501', '哄闹冲击法庭侮辱诽谤威胁殴打审判人员严重扰乱法庭秩序');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '502', '伪造毁灭重要证据妨碍人民法院审理案件');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '503', '以暴力威胁贿买方法阻止证人作证或者指使贿买胁迫他人作伪证');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '504', '隐藏转移变卖毁损已被查封扣押冻结财产');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '505', '侮辱诽谤诬陷殴打或打击报复司法人员诉讼参加人证人等');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '506', '以暴力威胁或者其他方法阻碍司法工作人员执行职务');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '507', '拒不履行人民法院已经发生法律效力的判决裁定');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '508', '非法拘禁他人或者非法私自扣押他人财产追索债务');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '599', '其他妨害司法审判行为');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '600', '违反道路交通管理');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '601', '无证驾车');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '602', '醉酒驾车');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '603', '阻碍执行职务');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '604', '交通肇事逃逸');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '699', '其他违反道路交通管理');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '999', '其他');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZC', '专长', '1', '驾车');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZC', '专长', '2', '驾船');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZC', '专长', '3', '驾机');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZC', '专长', '4', '爆破');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZC', '专长', '5', '射击');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZC', '专长', '6', '枪械');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZC', '专长', '7', '屠宰');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZC', '专长', '8', '解剖');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZC', '专长', '9', '绘画');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZC', '专长', '10', '书法');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZC', '专长', '11', '表演');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZC', '专长', '12', '印刷');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZC', '专长', '14', '誉印');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZC', '专长', '15', '打字');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZC', '专长', '16', '雕刻');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZC', '专长', '17', '摄影');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZC', '专长', '18', '摄像');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZC', '专长', '19', '装裱');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZC', '专长', '20', '装潢设计');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZC', '专长', '21', '缝纫');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZC', '专长', '22', '钳工');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZC', '专长', '23', '机床操作');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZC', '专长', '24', '焊割');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZC', '专长', '25', '铸造');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZC', '专长', '26', '木工');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZC', '专长', '27', '瓦工');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZC', '专长', '28', '电工');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZC', '专长', '29', '油漆工');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZC', '专长', '30', '通讯');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZC', '专长', '31', '计算机技术');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZC', '专长', '32', '财会');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZC', '专长', '33', '修电器');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZC', '专长', '34', '修钟表');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZC', '专长', '35', '修机动车');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZC', '专长', '36', '修非机动车');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZC', '专长', '37', '修锁');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZC', '专长', '39', '饰物制作');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZC', '专长', '40', '化妆');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZC', '专长', '41', '化验');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZC', '专长', '42', '医药');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZC', '专长', '43', '潜水');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZC', '专长', '44', '攀高');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZC', '专长', '45', '武术');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZC', '专长', '46', '魔术');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZC', '专长', '47', '巫术');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZC', '专长', '48', '外语');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZC', '专长', '49', '民族语言');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZC', '专长', '50', '手语');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZC', '专长', '51', '模仿');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZC', '专长', '90', '其他专长');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('JKZK', '健康状况', '1', '健康或良好');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('JKZK', '健康状况', '2', '一般或较弱');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('JKZK', '健康状况', '3', '有病');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('JKZK', '健康状况', '4', '有生理缺陷');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('JKZK', '健康状况', '5', '残废');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('LX', '脸型', '1', '椭圆脸');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('LX', '脸型', '2', '圆脸');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('LX', '脸型', '3', '长方脸');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('LX', '脸型', '4', '方脸');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('LX', '脸型', '5', '倒大脸');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('LX', '脸型', '6', '三角脸');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('LX', '脸型', '7', '狭长脸');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('LX', '脸型', '8', '菱形脸');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('LX', '脸型', '9', '畸形脸');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('LX', '脸型', '31', '平形脸');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('LX', '脸型', '32', '凹形脸');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '1', '汉族');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '2', '蒙古族');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '3', '回族');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '4', '藏族');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '5', '维吾尔族');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '6', '苗族');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '7', '彝族');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '8', '壮族');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '9', '布依族');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '10', '朝鲜族');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '11', '满族');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '12', '侗族');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '13', '瑶族');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '14', '白族');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '15', '土家族');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '16', '哈尼族');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '17', '哈萨克族');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '18', '傣族');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '19', '黎族');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '20', '傈僳族');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '21', '佤族');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '22', '畲族');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '23', '高山族');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '24', '拉祜族');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610104', '陕西省西安市莲湖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610111', '陕西省西安市灞桥区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610112', '陕西省西安市未央区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610113', '陕西省西安市雁塔区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610114', '陕西省西安市阎良区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610115', '陕西省西安市临潼区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610116', '陕西省西安市长安区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610122', '陕西省西安市蓝田县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610124', '陕西省西安市周至县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610125', '陕西省西安市户县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610126', '陕西省西安市高陵县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610200', '陕西省铜川市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610201', '陕西省铜川市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610202', '陕西省铜川市王益区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610203', '陕西省铜川市印台区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610204', '陕西省铜川市耀州区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610222', '陕西省铜川市宜君县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610300', '陕西省宝鸡市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610301', '陕西省宝鸡市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610302', '陕西省宝鸡市渭滨区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610303', '陕西省宝鸡市金台区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610304', '陕西省宝鸡市陈仓区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610322', '陕西省宝鸡市凤翔县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610323', '陕西省宝鸡市岐山县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610324', '陕西省宝鸡市扶风县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610326', '陕西省宝鸡市眉县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610327', '陕西省宝鸡市陇县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610328', '陕西省宝鸡市千阳县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610329', '陕西省宝鸡市麟游县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610330', '陕西省宝鸡市凤县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610331', '陕西省宝鸡市太白县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610400', '陕西省咸阳市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610401', '陕西省咸阳市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610402', '陕西省咸阳市秦都区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610403', '陕西省咸阳市杨凌区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610404', '陕西省咸阳市渭城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610422', '陕西省咸阳市三原县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610423', '陕西省咸阳市泾阳县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610424', '陕西省咸阳市乾县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610425', '陕西省咸阳市礼泉县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610426', '陕西省咸阳市永寿县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610427', '陕西省咸阳市彬县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610428', '陕西省咸阳市长武县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610429', '陕西省咸阳市旬邑县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610430', '陕西省咸阳市淳化县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610431', '陕西省咸阳市武功县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610481', '陕西省咸阳市兴平市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610500', '陕西省渭南市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610501', '陕西省渭南市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610502', '陕西省渭南市临渭区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610521', '陕西省渭南市华县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610522', '陕西省渭南市潼关县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610523', '陕西省渭南市大荔县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610524', '陕西省渭南市合阳县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610525', '陕西省渭南市澄城县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610526', '陕西省渭南市蒲城县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610527', '陕西省渭南市白水县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610528', '陕西省渭南市富平县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610581', '陕西省渭南市韩城市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610582', '陕西省渭南市华阴市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610600', '陕西省延安市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610601', '陕西省延安市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610602', '陕西省延安市宝塔区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610621', '陕西省延安市延长县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610622', '陕西省延安市延川县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610623', '陕西省延安市子长县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610624', '陕西省延安市安塞县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610625', '陕西省延安市志丹县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610626', '陕西省延安市吴旗县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610627', '陕西省延安市甘泉县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610628', '陕西省延安市富县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610629', '陕西省延安市洛川县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610630', '陕西省延安市宜川县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610631', '陕西省延安市黄龙县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '640400', '宁夏回族自治区固原市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '640401', '宁夏回族自治区固原市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '640402', '宁夏回族自治区固原市原州区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '640422', '宁夏回族自治区固原市西吉县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '640423', '宁夏回族自治区固原市隆德县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '640424', '宁夏回族自治区固原市泾源县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '640425', '宁夏回族自治区固原市彭阳县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '640500', '宁夏回族自治区中卫市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '640501', '宁夏回族自治区中卫市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '640502', '宁夏回族自治区中卫市沙坡头区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '640521', '宁夏回族自治区中卫市中宁县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '640522', '宁夏回族自治区中卫市海原县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '650000', '新疆维吾尔自治区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '650100', '新疆维吾尔自治区乌鲁木齐市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '650101', '新疆维吾尔自治区乌鲁木齐市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '650102', '新疆维吾尔自治区乌鲁木齐市天山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '650103', '新疆维吾尔自治区乌鲁木齐市沙依巴克区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '650104', '新疆维吾尔自治区乌鲁木齐市新市区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '650105', '新疆维吾尔自治区乌鲁木齐市水磨沟区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '650106', '新疆维吾尔自治区乌鲁木齐市头屯河区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '650107', '新疆维吾尔自治区乌鲁木齐市达坂城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '650108', '新疆维吾尔自治区乌鲁木齐市米东区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '650121', '新疆维吾尔自治区乌鲁木齐市乌鲁木齐县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '650200', '新疆维吾尔自治区克拉玛依市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '650201', '新疆维吾尔自治区克拉玛依市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '650202', '新疆维吾尔自治区克拉玛依市独山子区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '650203', '新疆维吾尔自治区克拉玛依市克拉玛依区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '650204', '新疆维吾尔自治区克拉玛依市白碱滩区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '650205', '新疆维吾尔自治区克拉玛依市乌尔禾区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '652100', '新疆维吾尔自治区吐鲁番地区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '652101', '新疆维吾尔自治区吐鲁番地区吐鲁番市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '652122', '新疆维吾尔自治区吐鲁番地区鄯善县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '652123', '新疆维吾尔自治区吐鲁番地区托克逊县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '652200', '新疆维吾尔自治区哈密地区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '652201', '新疆维吾尔自治区哈密地区哈密市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '652222', '新疆维吾尔自治区哈密地区巴里坤哈萨克自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '652223', '新疆维吾尔自治区哈密地区伊吾县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '652300', '新疆维吾尔自治区昌吉回族自治州');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '652301', '新疆维吾尔自治区昌吉回族自治州昌吉市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '652302', '新疆维吾尔自治区昌吉回族自治州阜康市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '652303', '新疆维吾尔自治区昌吉回族自治州米泉市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '652323', '新疆维吾尔自治区昌吉回族自治州呼图壁县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '652324', '新疆维吾尔自治区昌吉回族自治州玛纳斯县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '652325', '新疆维吾尔自治区昌吉回族自治州奇台县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '652327', '新疆维吾尔自治区昌吉回族自治州吉木萨尔县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '652328', '新疆维吾尔自治区昌吉回族自治州木垒哈萨克自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '652700', '新疆维吾尔自治区博尔塔拉蒙古自治州');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '652701', '新疆维吾尔自治区博尔塔拉蒙古自治州博乐市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '652722', '新疆维吾尔自治区博尔塔拉蒙古自治州精河县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '652723', '新疆维吾尔自治区博尔塔拉蒙古自治州温泉县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '652800', '新疆维吾尔自治区巴音郭楞蒙古自治州');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '652801', '新疆维吾尔自治区巴音郭楞蒙古自治州库尔勒市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '652822', '新疆维吾尔自治区巴音郭楞蒙古自治州轮台县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '652823', '新疆维吾尔自治区巴音郭楞蒙古自治州尉犁县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '652824', '新疆维吾尔自治区巴音郭楞蒙古自治州若羌县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '652825', '新疆维吾尔自治区巴音郭楞蒙古自治州且末县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '652826', '新疆维吾尔自治区巴音郭楞蒙古自治州焉耆回族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '652827', '新疆维吾尔自治区巴音郭楞蒙古自治州和静县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '652828', '新疆维吾尔自治区巴音郭楞蒙古自治州和硕县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '652829', '新疆维吾尔自治区巴音郭楞蒙古自治州博湖县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '652900', '新疆维吾尔自治区阿克苏地区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '652901', '新疆维吾尔自治区阿克苏地区阿克苏市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '652922', '新疆维吾尔自治区阿克苏地区温宿县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '652923', '新疆维吾尔自治区阿克苏地区库车县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '652924', '新疆维吾尔自治区阿克苏地区沙雅县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '652925', '新疆维吾尔自治区阿克苏地区新和县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '652926', '新疆维吾尔自治区阿克苏地区拜城县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '652927', '新疆维吾尔自治区阿克苏地区乌什县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '652928', '新疆维吾尔自治区阿克苏地区阿瓦提县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '652929', '新疆维吾尔自治区阿克苏地区柯坪县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '653000', '新疆维吾尔自治区克孜勒苏柯尔克孜自治州');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '653001', '新疆维吾尔自治区克孜勒苏柯尔克孜自治州阿图什市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '653022', '新疆维吾尔自治区克孜勒苏柯尔克孜自治州阿克陶县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '653023', '新疆维吾尔自治区克孜勒苏柯尔克孜自治州阿合奇县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '653024', '新疆维吾尔自治区克孜勒苏柯尔克孜自治州乌恰县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '653100', '新疆维吾尔自治区喀什地区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '653101', '新疆维吾尔自治区喀什地区喀什市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '653121', '新疆维吾尔自治区喀什地区疏附县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '653122', '新疆维吾尔自治区喀什地区疏勒县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '653123', '新疆维吾尔自治区喀什地区英吉沙县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '653124', '新疆维吾尔自治区喀什地区泽普县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '653125', '新疆维吾尔自治区喀什地区莎车县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '653126', '新疆维吾尔自治区喀什地区叶城县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '653127', '新疆维吾尔自治区喀什地区麦盖提县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '653128', '新疆维吾尔自治区喀什地区岳普湖县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '653129', '新疆维吾尔自治区喀什地区伽师县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '653130', '新疆维吾尔自治区喀什地区巴楚县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '653131', '新疆维吾尔自治区喀什地区塔什库尔干塔吉克自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '653200', '新疆维吾尔自治区和田地区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '653201', '新疆维吾尔自治区和田地区和田市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '653221', '新疆维吾尔自治区和田地区和田县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '653222', '新疆维吾尔自治区和田地区墨玉县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '653223', '新疆维吾尔自治区和田地区皮山县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '653224', '新疆维吾尔自治区和田地区洛浦县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '653225', '新疆维吾尔自治区和田地区策勒县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '653226', '新疆维吾尔自治区和田地区于田县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '653227', '新疆维吾尔自治区和田地区民丰县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '654000', '新疆维吾尔自治区伊犁哈萨克自治州');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '654002', '新疆维吾尔自治区伊犁哈萨克自治州伊宁市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '654003', '新疆维吾尔自治区伊犁哈萨克自治州奎屯市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '654021', '新疆维吾尔自治区伊犁哈萨克自治州伊宁县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '654022', '新疆维吾尔自治区伊犁哈萨克自治州察布查尔锡伯自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '654023', '新疆维吾尔自治区伊犁哈萨克自治州霍城县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '654024', '新疆维吾尔自治区伊犁哈萨克自治州巩留县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '654025', '新疆维吾尔自治区伊犁哈萨克自治州新源县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '654026', '新疆维吾尔自治区伊犁哈萨克自治州昭苏县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '654027', '新疆维吾尔自治区伊犁哈萨克自治州特克斯县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '654028', '新疆维吾尔自治区伊犁哈萨克自治州尼勒克县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '654200', '新疆维吾尔自治区塔城地区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '654201', '新疆维吾尔自治区塔城地区塔城市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '654202', '新疆维吾尔自治区塔城地区乌苏市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '654221', '新疆维吾尔自治区塔城地区额敏县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '654223', '新疆维吾尔自治区塔城地区沙湾县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '654224', '新疆维吾尔自治区塔城地区托里县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '654225', '新疆维吾尔自治区塔城地区裕民县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '654226', '新疆维吾尔自治区塔城地区和布克赛尔蒙古自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '654300', '新疆维吾尔自治区阿勒泰地区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '654301', '新疆维吾尔自治区阿勒泰地区阿勒泰市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '654321', '新疆维吾尔自治区阿勒泰地区布尔津县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '654322', '新疆维吾尔自治区阿勒泰地区富蕴县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '654323', '新疆维吾尔自治区阿勒泰地区福海县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '654324', '新疆维吾尔自治区阿勒泰地区哈巴河县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '654325', '新疆维吾尔自治区阿勒泰地区青河县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '654326', '新疆维吾尔自治区阿勒泰地区吉木乃县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '659000', '新疆维吾尔自治区省直辖行政单位');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '659001', '新疆维吾尔自治区省直辖行政单位石河子市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '659002', '新疆维吾尔自治区省直辖行政单位阿拉尔市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '659003', '新疆维吾尔自治区省直辖行政单位图木舒克市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '659004', '新疆维吾尔自治区省直辖行政单位五家渠市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '710000', '台湾省');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '810000', '香港特别行政区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '820000', '澳门特别行政区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511502', '四川省宜宾市翠屏区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511521', '四川省宜宾市宜宾县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511522', '四川省宜宾市南溪县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511523', '四川省宜宾市江安县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511524', '四川省宜宾市长宁县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511525', '四川省宜宾市高县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511526', '四川省宜宾市珙县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511527', '四川省宜宾市筠连县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511528', '四川省宜宾市兴文县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511529', '四川省宜宾市屏山县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511600', '四川省广安市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511601', '四川省广安市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511602', '四川省广安市广安区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511621', '四川省广安市岳池县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511622', '四川省广安市武胜县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511623', '四川省广安市邻水县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511681', '四川省广安市华蓥市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511700', '四川省达州市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511701', '四川省达州市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511702', '四川省达州市通川区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511721', '四川省达州市达县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511722', '四川省达州市宣汉县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511723', '四川省达州市开江县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511724', '四川省达州市大竹县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511725', '四川省达州市渠县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511781', '四川省达州市万源市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511800', '四川省雅安市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511801', '四川省雅安市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511802', '四川省雅安市雨城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511821', '四川省雅安市名山县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511822', '四川省雅安市荥经县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511823', '四川省雅安市汉源县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511824', '四川省雅安市石棉县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511825', '四川省雅安市天全县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511826', '四川省雅安市芦山县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511827', '四川省雅安市宝兴县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511900', '四川省巴中市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511901', '四川省巴中市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511902', '四川省巴中市巴州区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511921', '四川省巴中市通江县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511922', '四川省巴中市南江县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '511923', '四川省巴中市平昌县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '512000', '四川省资阳市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '512001', '四川省资阳市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '512002', '四川省资阳市雁江区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '512021', '四川省资阳市安岳县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '512022', '四川省资阳市乐至县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '512081', '四川省资阳市简阳市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '513200', '四川省阿坝藏族羌族自治州');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '513221', '四川省阿坝藏族羌族自治州汶川县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '513222', '四川省阿坝藏族羌族自治州理县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '513223', '四川省阿坝藏族羌族自治州茂县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '513224', '四川省阿坝藏族羌族自治州松潘县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '513225', '四川省阿坝藏族羌族自治州九寨沟县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '513226', '四川省阿坝藏族羌族自治州金川县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '513227', '四川省阿坝藏族羌族自治州小金县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '513228', '四川省阿坝藏族羌族自治州黑水县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '513229', '四川省阿坝藏族羌族自治州马尔康县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '513230', '四川省阿坝藏族羌族自治州壤塘县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '513231', '四川省阿坝藏族羌族自治州阿坝县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '513232', '四川省阿坝藏族羌族自治州若尔盖县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '513233', '四川省阿坝藏族羌族自治州红原县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '513300', '四川省甘孜藏族自治州');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '513321', '四川省甘孜藏族自治州康定县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '513322', '四川省甘孜藏族自治州泸定县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '513323', '四川省甘孜藏族自治州丹巴县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '513324', '四川省甘孜藏族自治州九龙县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '513325', '四川省甘孜藏族自治州雅江县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '513326', '四川省甘孜藏族自治州道孚县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '513327', '四川省甘孜藏族自治州炉霍县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '513328', '四川省甘孜藏族自治州甘孜县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '513329', '四川省甘孜藏族自治州新龙县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '513330', '四川省甘孜藏族自治州德格县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '513331', '四川省甘孜藏族自治州白玉县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '513332', '四川省甘孜藏族自治州石渠县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '513333', '四川省甘孜藏族自治州色达县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '513334', '四川省甘孜藏族自治州理塘县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '513335', '四川省甘孜藏族自治州巴塘县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '513336', '四川省甘孜藏族自治州乡城县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '513337', '四川省甘孜藏族自治州稻城县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '513338', '四川省甘孜藏族自治州得荣县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '513400', '四川省凉山彝族自治州');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '513401', '四川省凉山彝族自治州西昌市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '513422', '四川省凉山彝族自治州木里藏族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '513423', '四川省凉山彝族自治州盐源县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '513424', '四川省凉山彝族自治州德昌县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '513425', '四川省凉山彝族自治州会理县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '513426', '四川省凉山彝族自治州会东县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '513427', '四川省凉山彝族自治州宁南县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '513428', '四川省凉山彝族自治州普格县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '513429', '四川省凉山彝族自治州布拖县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '513430', '四川省凉山彝族自治州金阳县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '513431', '四川省凉山彝族自治州昭觉县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '513432', '四川省凉山彝族自治州喜德县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '513433', '四川省凉山彝族自治州冕宁县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '513434', '四川省凉山彝族自治州越西县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '513435', '四川省凉山彝族自治州甘洛县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '513436', '四川省凉山彝族自治州美姑县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '513437', '四川省凉山彝族自治州雷波县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '520000', '贵州省');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '520100', '贵州省贵阳市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '520101', '贵州省贵阳市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '520102', '贵州省贵阳市南明区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '520103', '贵州省贵阳市云岩区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '520111', '贵州省贵阳市花溪区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '520112', '贵州省贵阳市乌当区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '520113', '贵州省贵阳市白云区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '520114', '贵州省贵阳市小河区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '520121', '贵州省贵阳市开阳县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '520122', '贵州省贵阳市息烽县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '520123', '贵州省贵阳市修文县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '520181', '贵州省贵阳市清镇市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '520200', '贵州省六盘水市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '520201', '贵州省六盘水市钟山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '520203', '贵州省六盘水市六枝特区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '520221', '贵州省六盘水市水城县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '520222', '贵州省六盘水市盘县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '520300', '贵州省遵义市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '520301', '贵州省遵义市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '520302', '贵州省遵义市红花岗区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '520303', '贵州省遵义市汇川区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '520321', '贵州省遵义市遵义县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '520322', '贵州省遵义市桐梓县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '520323', '贵州省遵义市绥阳县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '520324', '贵州省遵义市正安县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '520325', '贵州省遵义市道真仡佬族苗族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '520326', '贵州省遵义市务川仡佬族苗族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '520327', '贵州省遵义市凤冈县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '520328', '贵州省遵义市湄潭县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '520329', '贵州省遵义市余庆县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '520330', '贵州省遵义市习水县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '520381', '贵州省遵义市赤水市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '520382', '贵州省遵义市仁怀市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '520400', '贵州省安顺市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '520401', '贵州省安顺市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '520402', '贵州省安顺市西秀区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '520421', '贵州省安顺市平坝县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '520422', '贵州省安顺市普定县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '520423', '贵州省安顺市镇宁布依族苗族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '520424', '贵州省安顺市关岭布依族苗族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '520425', '贵州省安顺市紫云苗族布依族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '522200', '贵州省铜仁地区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '522201', '贵州省铜仁地区铜仁市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '522222', '贵州省铜仁地区江口县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '522223', '贵州省铜仁地区玉屏侗族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '522224', '贵州省铜仁地区石阡县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '522225', '贵州省铜仁地区思南县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '522226', '贵州省铜仁地区印江土家族苗族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '522227', '贵州省铜仁地区德江县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '522228', '贵州省铜仁地区沿河土家族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '522229', '贵州省铜仁地区松桃苗族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '522230', '贵州省铜仁地区万山特区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '522300', '贵州省黔西南布依族苗族自治州');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '522301', '贵州省黔西南布依族苗族自治州兴义市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '522322', '贵州省黔西南布依族苗族自治州兴仁县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '522323', '贵州省黔西南布依族苗族自治州普安县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '522324', '贵州省黔西南布依族苗族自治州晴隆县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '522325', '贵州省黔西南布依族苗族自治州贞丰县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '522326', '贵州省黔西南布依族苗族自治州望谟县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '522327', '贵州省黔西南布依族苗族自治州册亨县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '522328', '贵州省黔西南布依族苗族自治州安龙县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '522400', '贵州省毕节地区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '522401', '贵州省毕节地区毕节市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '522422', '贵州省毕节地区大方县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '522423', '贵州省毕节地区黔西县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '522424', '贵州省毕节地区金沙县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '522425', '贵州省毕节地区织金县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '522426', '贵州省毕节地区纳雍县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '522427', '贵州省毕节地区威宁彝族回族苗族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '522428', '贵州省毕节地区赫章县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '522600', '贵州省黔东南苗族侗族自治州');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '522601', '贵州省黔东南苗族侗族自治州凯里市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '522622', '贵州省黔东南苗族侗族自治州黄平县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '522623', '贵州省黔东南苗族侗族自治州施秉县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '522624', '贵州省黔东南苗族侗族自治州三穗县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '522625', '贵州省黔东南苗族侗族自治州镇远县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '522626', '贵州省黔东南苗族侗族自治州岑巩县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '522627', '贵州省黔东南苗族侗族自治州天柱县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '522628', '贵州省黔东南苗族侗族自治州锦屏县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '522629', '贵州省黔东南苗族侗族自治州剑河县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '522630', '贵州省黔东南苗族侗族自治州台江县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '522631', '贵州省黔东南苗族侗族自治州黎平县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '522632', '贵州省黔东南苗族侗族自治州榕江县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '522633', '贵州省黔东南苗族侗族自治州从江县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '522634', '贵州省黔东南苗族侗族自治州雷山县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '522635', '贵州省黔东南苗族侗族自治州麻江县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '522636', '贵州省黔东南苗族侗族自治州丹寨县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '522700', '贵州省黔南布依族苗族自治州');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '522701', '贵州省黔南布依族苗族自治州都匀市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '522702', '贵州省黔南布依族苗族自治州福泉市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '522722', '贵州省黔南布依族苗族自治州荔波县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '522723', '贵州省黔南布依族苗族自治州贵定县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '522725', '贵州省黔南布依族苗族自治州瓮安县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '522726', '贵州省黔南布依族苗族自治州独山县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '522727', '贵州省黔南布依族苗族自治州平塘县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '522728', '贵州省黔南布依族苗族自治州罗甸县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '522729', '贵州省黔南布依族苗族自治州长顺县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '522730', '贵州省黔南布依族苗族自治州龙里县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '522731', '贵州省黔南布依族苗族自治州惠水县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '522732', '贵州省黔南布依族苗族自治州三都水族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530000', '云南省');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530100', '云南省昆明市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530101', '云南省昆明市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530102', '云南省昆明市五华区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530103', '云南省昆明市盘龙区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530111', '云南省昆明市官渡区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530112', '云南省昆明市西山区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530113', '云南省昆明市东川区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530121', '云南省昆明市呈贡县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530122', '云南省昆明市晋宁县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530124', '云南省昆明市富民县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530125', '云南省昆明市宜良县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530126', '云南省昆明市石林彝族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530127', '云南省昆明市嵩明县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530128', '云南省昆明市禄劝彝族苗族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530129', '云南省昆明市寻甸回族彝族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530181', '云南省昆明市安宁市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530300', '云南省曲靖市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530301', '云南省曲靖市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530302', '云南省曲靖市麒麟区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530321', '云南省曲靖市马龙县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530322', '云南省曲靖市陆良县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-69', '其他机械制造加工人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-7', '机电产品装配人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-71', '基础件、部件装配人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-72', '机械设备装配人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-73', '动力设备装配人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-74', '电气元件及设备装配人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-75', '电子专用设备装配调试人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-76', '仪器仪表装配调试人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-77', '运输车辆装配人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-78', '膜法水处理设备制造人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-79', '医疗器械装配及假肢与矫形制作人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-81', '日用机械电器制造装配人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-82', '五金制品制作、装配人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-83', '装甲车辆装试人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-84', '枪炮制造人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-85', '弹制造人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-86', '引信加工制造人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-87', '火工品制造人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-88', '防化器材制造人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-89', '船舶制造人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-91', '航空产品装配与试验人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-92', '航空产品试验人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-93', '导弹卫星装配测试人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-94', '火箭发动机装配试验人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-95', '航空器结构强度、温度、环境试验人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-96', '靶场试验人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-99', '其他机电产品装配人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '7', '生产、运输设备操作人员及有关人员（二）');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '7-1', '机械设备修理人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '7-11', '机械设备修理人员.');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '7-12', '仪器仪表修理人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '7-13', '民用航空器维修人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '7-19', '其他机械设备修理人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '7-2', '电力设备安装、运行、检修及供电人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '7-21', '电力设备安装人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '7-22', '发电运行值班人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '7-23', '输电、配电、变电设备值班人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '7-24', '电力设备检修人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '7-25', '供用电人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '7-26', '生活、生产电力设备安装、操作、修理人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '7-29', '其他电力设备安装、运行、检修及供电人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '7-3', '电子元器件与设备制造、装配、调试及维修人');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '7-31', '电子器件制造人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '7-32', '电子元件制造人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '7-33', '电池制造人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '7-34', '电子设备装配套、调试人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '7-35', '电子产品维修人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '7-39', '其他电子元器件与设备制造，装配、调试及维');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '7-4', '橡胶和塑料制品和生产人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '7-41', '橡胶制品生产人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '7-42', '塑料制品加工人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '7-49', '其他橡胶和塑料制品生产人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '7-5', '纺织、针织、印染人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '7-51', '纤维预处理人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '7-52', '纺纱人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '7-53', '织造人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '7-54', '针织人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '7-55', '印染人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '7-59', '其他纺织、针织、印染人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '7-6', '裁剪、缝纫和皮毛、毛皮制品加工制作人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '7-61', '裁剪、缝纫人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '7-62', '鞋帽制作人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '7-63', '皮革、毛皮加工人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '7-64', '缝纫制品再加工人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '7-69', '其他裁剪、缝纫和皮革、毛皮制品加工制作人');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '7-7', '粮油、食品、饮料产品加工及饲料生产加工人');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '7-71', '粮油生产加工人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '7-72', '制糖和糖制品加工人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '7-73', '乳品、冷食品及罐头、饮料制作人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '7-74', '酿酒、食品添加剂及调味品制作人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '7-75', '粮油食品制作人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '7-76', '屠宰加工人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '7-77', '肉、蛋食品加工人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '7-78', '饲料生产加工人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '7-79', '其他粮油、食品、饮料生产加工及饲料生产加');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '7-8', '烟草及其制品加工人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '7-81', '原烟复烤人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '7-82', '卷烟生产人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '7-83', '烟用醋酸纤维丝束滤棒制作人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '7-89', '其他烟草及其制品加工人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '7-9', '药品生产人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '7-91', '合成药物制造人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '7-92', '生物技术制药（品）人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '7-93', '药物制剂人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '7-94', '中药制药人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '7-99', '其他药品生产人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8', '生产、运输设备操作人员及有关人员（三）');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-1', '木材加工、人造板生产、木制品制作及制浆、');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-11', '木材加工人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-12', '人造板生产人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-13', '木材制品制作人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-14', '制浆人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-15', '造纸人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-16', '纸制品制作人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-19', '其他木材加工、人造板生产、木制品制作及制');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-2', '建筑材料生产加工人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-21', '水泥及水泥制品生产加工人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-22', '墙体屋面材料生产加工人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-23', '建筑防水密封材料生产人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-24', '建筑保温及吸音材料生产人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-25', '装饰石材生产人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-26', '非金属矿及其制品生产加工人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-27', '耐火材料生产人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-29', '其他建筑材料生产加工人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-3', '玻璃、陶瓷、搪瓷及其制品生产加工人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-31', '玻璃熔制人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-32', '玻璃纤维及其制品生产人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-33', '石英玻璃制品加工人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-34', '陶瓷制品生产人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-35', '搪瓷制品生产人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-39', '其他玻璃、陶瓷、搪瓷及其制品生产加工人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-4', '广播影视制品制作、播放及文物保护作业人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-41', '影视制品制作人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-42', '音像制品制作、播放及文物保护作业人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-43', '广播影视舞台设备安装调试及运行操作人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-44', '电影放映人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-45', '文物保护作业人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-49', '其他广播影视制品制作、播放及文物保护作业');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-5', '印刷人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-51', '印前处理人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-52', '印刷操作人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-53', '印后制作人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-59', '其他印刷人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-6', '工艺、美术品制作人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-61', '珠宝首饰加工人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-62', '地毯制作人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-63', '玩具制作人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-64', '漆器工艺品制作人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-65', '抽纱、刺绣工艺品制作人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-66', '金属工艺品制作人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-67', '雕刻工艺品制作人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-68', '美术品制作人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-69', '其他工艺、美术品制作人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-7', '文化教育、体育用品制作人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-71', '文教用品制作人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-72', '体育用品制作人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-73', '乐器制作人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-79', '其化文化教育、体育用品制作人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-8', '工程施工人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-81', '土石方施工人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-82', '砌筑人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-83', '混凝土配作及制品加工人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-84', '钢筋加工人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-85', '施工架子搭设人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-86', '工程防水人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-87', '装饰、装修人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-88', '古建筑修建人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-89', '筑路、养护、维修人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-91', '工程设备安装人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '8-99', '其他工程施工人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '9', '生产、运输设备操作人员及有关人员（四）');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '9-1', '运输设备操作人员及有关人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '9-11', '公（道）路运输机械设备操作及有关人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '9-12', '铁路、地铁运输机械设备操作及有关人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '9-13', '民用航空设备操作及有关人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '9-14', '水上运输设备操作及有关人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '9-15', '起重装卸机械操作及有关人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '9-19', '其他运输设备操作人员及有关人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '9-2', '环境监测与废物处理人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '9-21', '环境监测人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '9-22', '海洋环境调查与监测人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '9-23', '废物处理人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '9-29', '其他环境监测与废物处理人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '9-3', '检验、计量人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '9-31', '检验人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '9-32', '航空产品检验人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '9-33', '航天器检验、测试人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '9-34', '计量人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '9-39', '其他检验、计量人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '9-9', '其他生产、运输设备操作人员及有关人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '9-91', '包装人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '9-92', '机泵操作人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '9-93', '简单体力劳动人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', 'X', '军人');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', 'X-0', '军人.');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', 'X-00', '军人..');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', 'Y', '不便分类的其他从业人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', 'Y-0', '不便分类的其他从业人员.');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', 'Y-00', '不便分类的其他从业人员..');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '100', '扰乱公共秩序的案件');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '101', '扰乱单位秩序');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '102', '扰乱公共场所秩序');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '103', '扰乱公共交通工具上的秩序');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '104', '妨碍交通工具正常行驶');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '105', '破坏选举秩序');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '106', '聚众扰乱单位秩序');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '107', '聚众扰乱公共场所秩序');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '108', '聚众扰乱公共交通工具上的秩序');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '109', '聚众妨碍交通工具正常行驶');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '110', '聚众破坏选举秩序');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '111', '强行进入大型活动场内');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '112', '违规在大型活动场内燃放物品');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '113', '在大型活动场内展示侮辱性物品');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '114', '围攻大型活动工作人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '115', '向大型活动场内投掷杂物');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '116', '其他扰乱大型活动秩序的行为');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '117', '虚构事实扰乱公共秩序');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '118', '投放虚假危险物质扰乱公共秩序');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '119', '扬言实施放火、爆炸、投放危险物质扰乱公共秩序');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '120', '寻衅滋事');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '121', '组织、教唆、胁迫、诱骗、煽动从事邪教、会道门活动');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '122', '利用邪教、会道门、迷信活动危害社会');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '123', '冒用宗教、气功名义危害社会');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '124', '故意干扰无线电业务正常进行');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '125', '拒不消除对无线电台');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '126', '非法侵入计算机信息系统');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '127', '非法改变计算机信息系统功能');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '128', '非法改变计算机信息系统数据和应用程序');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '129', '故意制作、传播计算机破坏性程序');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '199', '其他扰乱公共秩序的案件');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '200', '妨害公共安全的案件');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '201', '非法制造、买卖、储存、运输、邮寄、携带、使用、提供、处置危险物质');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '202', '危险物质被盗、被抢、丢失后不按规定报告');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '203', '非法携带枪支、弹药、管制器具');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '204', '盗窃、损毁公共设施');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '205', '移动、损毁边境、领土、领海标志设施');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '206', '非法进行影响国(边)界线走向的活动');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '207', '非法修建有碍国(边)境管理的设施');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '208', '盗窃、损坏、擅自移动航空设施');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '209', '强行进入航空器驾驶舱');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '210', '在航空器上非法使用器具、工具');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '211', '盗窃、损毁、擅自移动铁路设施、设备、机车车辆配件、安全标志');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '212', '在铁路线上放置障碍物');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '213', '故意向列车投掷物品');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '214', '在铁路沿线非法挖掘坑穴、采石取沙');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '215', '在铁路线路上私设道口、平交过道');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '216', '擅自进入铁路防护网');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '217', '违法在铁路线上行走坐卧、抢越铁路');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '218', '擅自安装、使用电网');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '219', '安装、使用电网不符合安全规定');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '220', '道路施工不设置安全防护设施');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '221', '故意损毁、移动道路施工安全防护设施');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '222', '盗窃、损毁路面公共设施');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '223', '违反规定举办大型活动');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '224', '公共场所经营管理人员违反安全规定');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '299', '其他妨害公共安全的案件');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZAAJLBDM', '治安案件类别代码', '300', '侵犯他人人身权利、财产权利的案件');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-48', '广播、电影、电视工程技术人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-49', '交通工程技术人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-51', '民用航空工程技术人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-52', '铁路工程技术人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-53', '建筑工程技术人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-54', '建材工程技术人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-55', '林业工程技术人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-56', '水利工程技术人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-57', '海洋工程技术人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-58', '水产工程技术人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-59', '纺织工程技术人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-61', '食品工程技术人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-62', '气象工程技术人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-63', '地震工程技术人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-64', '环境保护工程技术人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-65', '安全工程技术人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-66', '标准化、计量、质量工程技术人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-67', '管理（工业）工程技术人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-69', '其他工程技术人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-7', '农业技术人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-71', '土壤肥料技术人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-72', '植物保护技术人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-73', '园艺技术人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-74', '作物遗传育种栽培技术人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-75', '兽医、兽药技术人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-76', '畜牧与草业技术人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-79', '其他农业技术人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-8', '飞机和船舶技术人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-81', '飞行人员和领航人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-82', '船舶指挥和引航人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-89', '其他飞机和船舶技术人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-9', '卫生专业技术人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-91', '西医医师');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-92', '中医医师');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-93', '中西医师结合医师');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-94', '民族医生');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-95', '公共卫生医师');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-96', '药剂人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-97', '医疗技术人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-98', '护理人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-99', '其他卫生专业技术人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '2', '专业技术人员（二）');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '2-1', '经济业务人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '2-11', '经济计划人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '2-12', '统计人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '2-13', '会计人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '2-14', '审计人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '2-15', '国际商务人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '2-19', '其他经济业务人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '2-2', '金融业务人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '2-21', '银行业务人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '2-22', '保险业务人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '2-23', '证券业务人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '2-29', '其他金融业务人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '2-3', '法律专业人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '2-31', '法官');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '2-32', '检察官');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '2-33', '律师');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '2-34', '公证员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '2-35', '司法鉴定人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '2-36', '书记员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '2-39', '其他法律专业人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '2-4', '教学人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '2-41', '高等教育教师');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '2-42', '中等职业教育教师');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '2-43', '中学教师');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '2-44', '小学教师');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '2-45', '幼儿教师');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '2-46', '特殊教育教师');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '2-49', '其他教学人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '2-5', '文学艺术工作人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '2-51', '文艺创作和评论人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '2-52', '编导和音乐指挥人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '2-53', '演员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '2-54', '乐器演秦员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '2-55', '电影、电视制作及舞台专业人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '2-56', '美术专业人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '2-57', '工艺美术专业人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '2-59', '其他文学艺术工作人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '2-6', '体育工作人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '2-60', '体育工作人员.');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '2-7', '新闻出版、文化工作人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '2-71', '记者');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '2-72', '编辑');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '2-73', '校对员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '2-74', '播音员及节目主持人');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '2-75', '翻译');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '2-76', '图书资料与档案业务人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '2-77', '考古及文物保护工作人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '2-79', '其他新闻出版、文化工作人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '2-8', '宗教职业者');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '2-80', '宗教职业者.');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '2-9', '其他专业技术人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '2-90', '其他专业技术人员.');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '3', '办事人员和有关人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '3-1', '行政办公人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '3-11', '行政业务人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '3-12', '行政事务人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '3-19', '其他行政办公人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '3-2', '安全保卫和消防人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '3-21', '人民警察');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '3-22', '治安保卫人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '3-23', '消防人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '3-29', '其他安全保卫和消防人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '3-3', '邮政和电信业务人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '3-31', '邮政业务人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '3-32', '电信业务人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '3-33', '电信通信传输业务人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '3-39', '其他邮政和电信业务人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '3-9', '其他办事人员和有关人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '3-90', '其他办事人员和有关人员.');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '4', '商业、服务业人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '4-1', '购销人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '4-11', '营业人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '4-12', '推销、展销人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '4-13', '采购人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '4-14', '拍卖、典当及租赁业务人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '4-15', '废旧物资回收利用人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '4-16', '粮油管理人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '4-17', '商品监督和市场管理人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '4-19', '其他购销人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '4-2', '仓储人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '4-21', '保管人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '4-22', '储运人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '4-29', '其他仓储人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '4-3', '餐饮服务人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '4-31', '中餐烹饪人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '4-32', '西餐烹饪人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '4-33', '调酒和茶艺人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '4-34', '营养配餐人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '4-35', '餐厅服务人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '4-39', '其他餐饮服务人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '4-4', '饭店、旅游及健身娱乐场所服务人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '4-41', '饭店服务人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '4-42', '旅游及公共游览场所服务人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '4-43', '健身和娱乐场所服务人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '4-49', '其他饭店、旅游及健身娱乐场所服务人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '4-5', '运输服务人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '4-51', '公路、道路运输服务人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '4-52', '航空运输服务人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '4-53', '铁路客货运输服务人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '4-54', '水上运输服务人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '4-59', '其他运输服务人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '4-6', '医疗卫生辅助服务人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '4-60', '医疗卫生辅助服务人员.');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '4-7', '社会服务和居民生活服务人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '4-71', '社会中介服务人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '4-72', '牧业 管理人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '4-73', '供水、供热及生活燃料供应服务人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '4-74', '美容美发人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '4-75', '摄影服务人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '4-76', '验光配镜人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '4-77', '洗染织补人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '4-78', '浴池服务人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '4-79', '印章刻字人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '4-81', '日用机电产品维修人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '4-82', '办公设备维修人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '4-83', '保育、家庭服务人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '4-84', '环境卫生人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '4-85', '殡葬服务人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '4-89', '其他社会服务和居民生活服务人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '4-9', '其他商业、服务业人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '4-90', '其他商业、服务业人员.');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '5', '农、林、牧、渔、水利业生产人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '5-1', '种植业生产人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '5-11', '大田作物生产人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '5-12', '农业实验人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '5-13', '园艺作物生产人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '5-14', '热带作物生产人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '5-15', '中药材生产人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '5-16', '农副林特产品加工人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '5-19', '其他种植业生产人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '5-21', '林业生产及野生动植物保护人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '5-22', '森林资源管护人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '5-23', '野生动植物保护及自然保护区人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '5-24', '木材采运人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '5-29', '其他林业生产及野生动植物保护人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '5-3', '畜牧业生产人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '5-31', '家畜饲养人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '5-32', '家禽饲养人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '5-33', '蜜蜂饲养人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '5-34', '实验动物饲养人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '5-35', '动物疫病防治人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '5-36', '草业生产人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '5-39', '其他畜牧业生产人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '5-4', '渔业生产人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '5-41', '水产养殖人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '5-42', '水产捕捞及有关人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '5-43', '水产品加工人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '5-49', '其他渔业生产人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '5-5', '水利设施管理养护人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '5-51', '河道、水库管养人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '5-52', '农田灌排工程建设管理维护人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '5-53', '水土保持作业人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '5-54', '水文勘测作业人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '5-59', '其他水利设施管理养护人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '5-9', '其他农、林、牧、渔、水利业生产人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '5-91', '农林专用机械操作人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '5-92', '农林能源开发利用人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6', '生产、运输设备操作人员及有关人员（一）');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-1', '勘测及矿物开采人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-11', '地质勘查人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-12', '测绘人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('HYZK', '婚姻状况', '1', '未婚');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('HYZK', '婚姻状况', '2', '已婚');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('HYZK', '婚姻状况', '3', '丧偶');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('HYZK', '婚姻状况', '4', '离婚');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('HYZK', '婚姻状况', '9', '其他');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('WXDJDM', '危险等级代码', '1', '安全');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('WXDJDM', '危险等级代码', '2', '危险');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('WXDJDM', '危险等级代码', '9', '其他');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('JL_SF', '拘留所身份', '1', '国家公务员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('JL_SF', '拘留所身份', '2', '企事业管理人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('JL_SF', '拘留所身份', '3', '企事业职员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('JL_SF', '拘留所身份', '4', '工人');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('JL_SF', '拘留所身份', '5', '农民');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('JL_SF', '拘留所身份', '51', '农民(务工)');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('JL_SF', '拘留所身份', '52', '农民(务农)');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('JL_SF', '拘留所身份', '53', '农民(经商)');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('JL_SF', '拘留所身份', '54', '农民(服务)');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('JL_SF', '拘留所身份', '59', '农民(其他)');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('JL_SF', '拘留所身份', '6', '个体工商业者');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('JL_SF', '拘留所身份', '7', '在校学生');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('JL_SF', '拘留所身份', '8', '离退休人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('JL_SF', '拘留所身份', '9', '无业人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('JL_SF', '拘留所身份', '10', '军人');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('JL_SF', '拘留所身份', '99', '其他');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '25', '水族');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '26', '东乡族');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '27', '纳西族');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '28', '景颇族');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '29', '柯尔克孜族');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '30', '土族');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '31', '达斡尔族');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '32', '仫佬族');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '33', '羌族');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '34', '布朗族');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '35', '撒拉族');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '36', '毛南族');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '37', '仡佬族');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '38', '锡伯族');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '39', '阿昌族');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '40', '普米族');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '41', '塔吉克族');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '42', '怒族');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '43', '乌孜别克族');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '44', '俄罗斯族');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '45', '鄂温克族');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '46', '德昂族');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '47', '保安族');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '48', '裕固族');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '49', '京族');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '50', '塔塔尔族');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '51', '独龙族');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '52', '鄂伦春族');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '53', '郝哲族');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '54', '门巴族');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '55', '珞巴族');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '56', '基诺族');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '57', '港澳人');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '58', '台湾人');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '59', '华侨');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '60', '外国人');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '97', '其它');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('MZ', '民族', '98', '外籍');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('SEC_QJCSLX', '强戒出所类型', '1', '戒毒康复出所');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('SEC_QJCSLX', '强戒出所类型', '2', '戒毒期满');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('SEC_QJCSLX', '强戒出所类型', '3', '限期戒毒出所');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('SEC_QJCSLX', '强戒出所类型', '4', '转劳教');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('SEC_QJCSLX', '强戒出所类型', '5', '转刑拘');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('SEC_QJCSLX', '强戒出所类型', '6', '转逮捕');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('SEC_QJCSLX', '强戒出所类型', '7', '死亡');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('SEC_QJCSLX', '强戒出所类型', '8', '逃跑');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('SEC_QJCSLX', '强戒出所类型', '9', '转收容教育');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('SEC_QJCSLX', '强戒出所类型', '10', '转行政拘留');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('SEC_QJCSLX', '强戒出所类型', '11', '行政诉讼撤销或变更');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('SEC_QJCSLX', '强戒出所类型', '12', '行政复议撤销或变更');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('SEC_QJCSLX', '强戒出所类型', '13', '转司法继续执行');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('SEC_QJCSLX', '强戒出所类型', '14', '变更为社区戒毒');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('SEC_QJCSLX', '强戒出所类型', '15', '请假未按规定时间回');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('SEC_QJCSLX', '强戒出所类型', '16', '办理所外就医');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('SEC_QJCSLX', '强戒出所类型', '17', '刑拘或者逮捕人员戒毒后转出');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('SEC_QJCSLX', '强戒出所类型', '18', '一年后评估提前解除');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('SEC_QJCSLX', '强戒出所类型', '19', '二年后评估延长解除');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('SEC_QJCSLX', '强戒出所类型', '99', '其他');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('SEC_QJSYLX', '收押类型', 'QJ_0', '正常收治');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('SEC_QJSYLX', '收押类型', 'QJ_1', '投送劳教未收');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('SEC_QJSYLX', '收押类型', 'QJ_2', '限期戒毒回所');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('SEC_QJSYLX', '收押类型', 'QJ_3', '脱逃追回');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('SEC_QJSYLX', '收押类型', 'QJ_4', '戒毒康复未收');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('SEC_QJSYLX', '收押类型', 'QJ_9', '其他收回');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('TSSFDM', '特殊身份代码', '1', '人大代表');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('TSSFDM', '特殊身份代码', '2', '政协委员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('TSSFDM', '特殊身份代码', '3', '民主党首');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('TSSFDM', '特殊身份代码', '4', '中高干部');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('TSSFDM', '特殊身份代码', '5', '高知高工');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('TSSFDM', '特殊身份代码', '6', '公安民警');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('TSSFDM', '特殊身份代码', '7', '其它民警');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('TSSFDM', '特殊身份代码', '8', '检法干部');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('TSSFDM', '特殊身份代码', '9', '工商税务');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('TSSFDM', '特殊身份代码', '10', '海关稽查');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('TSSFDM', '特殊身份代码', '11', '保安警卫');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('TSSFDM', '特殊身份代码', '12', '外交官员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('TSSFDM', '特殊身份代码', '13', '外交家属');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('TSSFDM', '特殊身份代码', '14', '外商职员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('TSSFDM', '特殊身份代码', '15', '华侨人士');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('TSSFDM', '特殊身份代码', '16', '侨眷侨属');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('TSSFDM', '特殊身份代码', '17', '留学人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('TSSFDM', '特殊身份代码', '18', '外籍人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('TSSFDM', '特殊身份代码', '19', '港澳台人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('TSSFDM', '特殊身份代码', '20', '宗教界人士');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('TSSFDM', '特殊身份代码', '21', '邪教人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('TSSFDM', '特殊身份代码', '22', '民族分裂分子');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('TSSFDM', '特殊身份代码', '23', '民运人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('TSSFDM', '特殊身份代码', '24', '未成年人');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('TSSFDM', '特殊身份代码', '99', '其它');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('TX', '体型', '1', '特胖');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('TX', '体型', '2', '较胖');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('TX', '体型', '3', '中等');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('TX', '体型', '4', '较瘦');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('TX', '体型', '5', '特瘦');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('WHCD', '文化程度', '40', '中等专业学校或中等技术学校');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('WHCD', '文化程度', '41', '中专毕业');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('WHCD', '文化程度', '42', '中技毕业');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('WHCD', '文化程度', '48', '相当中专或中技毕业');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('WHCD', '文化程度', '49', '中专或中技肄业');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('WHCD', '文化程度', '50', '技工学校');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('WHCD', '文化程度', '51', '技工学校毕业');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('WHCD', '文化程度', '59', '技工学校肄业');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('WHCD', '文化程度', '60', '高中');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('WHCD', '文化程度', '61', '高中毕业');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('WHCD', '文化程度', '62', '职业高中毕业');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('WHCD', '文化程度', '63', '农业高中毕业');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('WHCD', '文化程度', '68', '相当高中毕业');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('WHCD', '文化程度', '69', '高中肄业');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('WHCD', '文化程度', '70', '初中');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('WHCD', '文化程度', '71', '初中毕业');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('WHCD', '文化程度', '72', '职业初中毕业');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('WHCD', '文化程度', '73', '农业初中毕业');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('WHCD', '文化程度', '78', '相当初中毕业');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('WHCD', '文化程度', '79', '初中肄业');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('WHCD', '文化程度', '80', '小学');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('WHCD', '文化程度', '30', '大学专科和专科学校');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('WHCD', '文化程度', '31', '专科毕业');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('WHCD', '文化程度', '38', '相当专科毕业');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('WHCD', '文化程度', '81', '小学毕业');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('WHCD', '文化程度', '88', '相当小学毕业');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('SEC_XDFS', '吸毒方式', '1', '口服');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('SEC_XDFS', '吸毒方式', '2', '咀嚼');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('SEC_XDFS', '吸毒方式', '3', '烟吸');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('SEC_XDFS', '吸毒方式', '4', '鼻吸');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('SEC_XDFS', '吸毒方式', '5', '烫吸');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('SEC_XDFS', '吸毒方式', '6', '皮肤吸收');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('SEC_XDFS', '吸毒方式', '7', '肌注');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('SEC_XDFS', '吸毒方式', '8', '静注');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XB', '性别', '0', '未知的性别');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XB', '性别', '1', '男性');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XB', '性别', '2', '女性');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XB', '性别', '9', '未说明的性别');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XB', '性别', '5', '女性改（变）为男性');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XB', '性别', '6', '男性改（变）为女性');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XX', '血型', '0', 'A型');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XX', '血型', '1', 'B型');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XX', '血型', '2', 'O型');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XX', '血型', '3', 'AB型');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZJLXDM', '证件类型代码', '11', '身份证');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZJLXDM', '证件类型代码', '12', '暂住证');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZJLXDM', '证件类型代码', '13', '户口簿');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZJLXDM', '证件类型代码', '14', '军官证');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZJLXDM', '证件类型代码', '15', '警官证');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZJLXDM', '证件类型代码', '16', '士兵证');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZJLXDM', '证件类型代码', '17', '护照');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZJLXDM', '证件类型代码', '99', '其它证件');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZZMM', '政治面貌', '1', '中国共产党党员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZZMM', '政治面貌', '2', '中国共产党预备党员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZZMM', '政治面貌', '3', '中国共产主义青年团团员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZZMM', '政治面貌', '4', '中国国民党革命委员会会员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZZMM', '政治面貌', '5', '中国民主同盟盟员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZZMM', '政治面貌', '6', '中国民主建国会会员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZZMM', '政治面貌', '7', '中国民主促进会会员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZZMM', '政治面貌', '8', '中国农工民主党党员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZZMM', '政治面貌', '9', '中国致公党党员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZZMM', '政治面貌', '10', '九三学社社员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZZMM', '政治面貌', '11', '台湾民主自治同盟盟员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZZMM', '政治面貌', '12', '无党派民主人士');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZZMM', '政治面貌', '13', '群众');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZZMM', '政治面貌', '99', '其他');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '100', '国家公务员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '1001', '一级公务员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '1002', '二级公务员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '1003', '三级公务员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '1004', '四级公务员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '1005', '五级公务员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '1006', '六级公务员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '1007', '七级公务员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '1008', '八级公务员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '1009', '九级公务员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '1010', '十级公务员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '1011', '十一级公务员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '1012', '十二级公务员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '1013', '十三级公务员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '1014', '十四级公务员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '1015', '十五级公务员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '1099', '未定级公务员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '111', '总理职');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '112', '副总理（国务委员）职');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '121', '正部、省职');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '122', '副部、省职');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '131', '正司、局、厅（巡视员）职');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '132', '副司、局、厅（助理巡视员）职');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '141', '正处、县（调研员）职');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '142', '副处、县（助理调研员）职');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '151', '正科（主任科员）职');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '152', '副科（副主任科员）职');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '161', '科员职');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '171', '办事员职');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '199', '未定职公务员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '2000', '职员别');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '2011', '一级职员（正部）');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '2012', '一级职员（副部）');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '2021', '二级职员（正局）');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '2022', '二级职员（副局）');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '2031', '三级职员（正处）');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '2032', '三级职员（副处）');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '2041', '四级职员（正科）');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '2042', '四级职员（副科）');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '2050', '五级职员（科员）');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '2060', '六级职员（办事员）');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '2099', '未定职职员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '3000', '行员级别');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '3012', '一级行员（副部）');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '3021', '二级行员（正局）');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '3032', '三级行员（副局）');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '3041', '四级行员（正处）');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '3042', '四级行员（副处）');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '3051', '五级行员（正科）');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '3052', '五级行员（副科）');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '3060', '六级行员（科员）');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '3070', '七级行员（办事员）');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '3099', '未定级行员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '4000', '技术人员级别');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '4011', '高级专业技术职务（教授级）');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '4012', '高级专业技术职务');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '4023', '中级专业技术职务');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '4034', '初级专业技术职务（助教级）');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '4035', '初级专业技术职务');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '4036', '初级专业技术职务（小教三级）');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '4049', '未定级专业技术人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '6000', '军队干部职别');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '6100', '军事、政治、后勤军官职别');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '6101', '军委主席职');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '6102', '军委副主席职');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '6106', '军委委员职');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '6121', '正大军区职');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '6123', '副大军区职');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '6141', '正军职');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '6143', '副军职');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '6151', '正师职');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '6153', '副师职(正旅职)');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '6161', '正团职(副旅职)');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '6163', '副团职');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '6171', '正营职');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '6173', '副营职');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '6181', '正连职');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '6183', '副连职');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '6191', '正排职');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '6200', '非专业技术文职干部职务级别');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '6252', '正局级');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '6254', '副局级');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '6262', '正处级');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '6264', '副处级');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '6272', '正科级');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '6274', '副科级');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '6282', '一级科员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '6284', '二级科员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '6292', '办事员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '6300', '专业技术军官和专业技术文职干部技术等级');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '6301', '技术一级');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '6302', '技术二级');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '6303', '技术三级');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '6304', '技术四级');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '6305', '技术五级');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '6306', '技术六级');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '6307', '技术七级');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '6308', '技术八级');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '6309', '技术九级');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '630A', '技术十级');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '630B', '技术十一级');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '630C', '技术十二级');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '630D', '技术十三级');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '630E', '技术十四级');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '630F', '技术十五级');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZWJBDM', '职务级别代码', '630G', '未定级的军队专业技术干部');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '0', '国家机关、党群组织、企业、事业单位负责人');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '0-1', '中国共产党中央委员会和地方各级组织负责人');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '0-10', '中国共产党中央委员会和地方各级组织负责人.');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '0-2', '国家机关及其工作机构负责人');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '0-21', '国家权力机关及其工作机构负责人');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '0-22', '人民政协及其工作机构负责人');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '0-23', '人民法院负责人');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '0-24', '人民检察院负责人');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '0-25', '国家行政机关及其工作机构负责人');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '0-29', '其他国家机关及其工作机构负责人');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '0-3', '民主党派和社会团体及其工作机构负责人');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '0-31', '民主党派负责人');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '0-32', '工会、共青团、妇联、其他人民团体及其工作');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '0-33', '群众自治组织负责人');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '0-39', '其他社会团体及其工作机构负责人');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '0-4', '事业单位负责人');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '0-41', '教育教学单位负责人');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '0-42', '卫生单位负责人');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '0-43', '科研单位负责人');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '0-49', '其他事业单位负责人');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '0-5', '企业负责人');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '0-50', '企业负责人.');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1', '专业技术人员（一）');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-1', '科学研究人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-11', '哲学研究人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-12', '经济学研究人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-13', '法学研究人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-14', '社会学研究人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-15', '教育科学研究人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-16', '文学、艺术研究人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-17', '图书馆学、情报学研究人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-18', '历史学研究人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-19', '管理科学研究人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-21', '数学研究人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-22', '物理学研究人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-23', '化学研究人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-24', '天文学研究人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-25', '地球科学研究人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-26', '生物科学研究人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-27', '农业科学研究人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-28', '医学研究人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-29', '其他科学研究人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-3', '工程技术人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-31', '地质勘探工程技术人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-32', '测绘工程技术人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-33', '矿山工程技术人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-34', '石油工程技术人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-35', '冶金工程技术人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-36', '化工工程技术人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-37', '机械工程技术人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-38', '兵器工程技术人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-39', '航空工程技术人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-41', '航天工程技术人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-42', '电子工程技术人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-43', '通信工程技术人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-44', '计算机应用工程技术人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-45', '电气工程技术人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-46', '电力工程技术人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '1-47', '邮政工程技术人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530922', '云南省临沧市云县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530923', '云南省临沧市永德县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530924', '云南省临沧市镇康县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530925', '云南省临沧市双江拉祜族佤族布朗族傣族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530926', '云南省临沧市耿马傣族佤族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '530927', '云南省临沧市沧源佤族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '532300', '云南省楚雄彝族自治州');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '532301', '云南省楚雄彝族自治州楚雄市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '532322', '云南省楚雄彝族自治州双柏县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '532323', '云南省楚雄彝族自治州牟定县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '532324', '云南省楚雄彝族自治州南华县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '532325', '云南省楚雄彝族自治州姚安县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '532326', '云南省楚雄彝族自治州大姚县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '532327', '云南省楚雄彝族自治州永仁县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '532328', '云南省楚雄彝族自治州元谋县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '532329', '云南省楚雄彝族自治州武定县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '532331', '云南省楚雄彝族自治州禄丰县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '532500', '云南省红河哈尼族彝族自治州');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '532501', '云南省红河哈尼族彝族自治州个旧市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '532502', '云南省红河哈尼族彝族自治州开远市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '532522', '云南省红河哈尼族彝族自治州蒙自县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '532523', '云南省红河哈尼族彝族自治州屏边苗族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '532524', '云南省红河哈尼族彝族自治州建水县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '532525', '云南省红河哈尼族彝族自治州石屏县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '532526', '云南省红河哈尼族彝族自治州弥勒县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '532527', '云南省红河哈尼族彝族自治州泸西县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '532528', '云南省红河哈尼族彝族自治州元阳县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '532529', '云南省红河哈尼族彝族自治州红河县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '532530', '云南省红河哈尼族彝族自治州金平苗族瑶族傣族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '532531', '云南省红河哈尼族彝族自治州绿春县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '532532', '云南省红河哈尼族彝族自治州河口瑶族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '532600', '云南省文山壮族苗族自治州');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '532621', '云南省文山壮族苗族自治州文山县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '532622', '云南省文山壮族苗族自治州砚山县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '532623', '云南省文山壮族苗族自治州西畴县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '532624', '云南省文山壮族苗族自治州麻栗坡县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '532625', '云南省文山壮族苗族自治州马关县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '532626', '云南省文山壮族苗族自治州丘北县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '532627', '云南省文山壮族苗族自治州广南县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '532628', '云南省文山壮族苗族自治州富宁县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '532800', '云南省西双版纳傣族自治州');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '532801', '云南省西双版纳傣族自治州景洪市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '532822', '云南省西双版纳傣族自治州勐海县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '532823', '云南省西双版纳傣族自治州勐腊县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '532900', '云南省大理白族自治州');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '532901', '云南省大理白族自治州大理市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '532922', '云南省大理白族自治州漾濞彝族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '532923', '云南省大理白族自治州祥云县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '532924', '云南省大理白族自治州宾川县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '532925', '云南省大理白族自治州弥渡县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '532926', '云南省大理白族自治州南涧彝族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '532927', '云南省大理白族自治州巍山彝族回族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '532928', '云南省大理白族自治州永平县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '532929', '云南省大理白族自治州云龙县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '532930', '云南省大理白族自治州洱源县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '532931', '云南省大理白族自治州剑川县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '532932', '云南省大理白族自治州鹤庆县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '533100', '云南省德宏傣族景颇族自治州');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '533102', '云南省德宏傣族景颇族自治州瑞丽市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '533103', '云南省德宏傣族景颇族自治州潞西市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '533122', '云南省德宏傣族景颇族自治州梁河县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '533123', '云南省德宏傣族景颇族自治州盈江县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '533124', '云南省德宏傣族景颇族自治州陇川县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '533300', '云南省怒江傈僳族自治州');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '533321', '云南省怒江傈僳族自治州泸水县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '533323', '云南省怒江傈僳族自治州福贡县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '533324', '云南省怒江傈僳族自治州贡山独龙族怒族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '533325', '云南省怒江傈僳族自治州兰坪白族普米族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '533400', '云南省迪庆藏族自治州');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '533421', '云南省迪庆藏族自治州香格里拉县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '533422', '云南省迪庆藏族自治州德钦县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '533423', '云南省迪庆藏族自治州维西傈僳族自治县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '540000', '西藏自治区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '540100', '西藏自治区拉萨市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '540101', '西藏自治区拉萨市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '540102', '西藏自治区拉萨市城关区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '540121', '西藏自治区拉萨市林周县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '540122', '西藏自治区拉萨市当雄县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '540123', '西藏自治区拉萨市尼木县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '540124', '西藏自治区拉萨市曲水县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '540125', '西藏自治区拉萨市堆龙德庆县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '540126', '西藏自治区拉萨市达孜县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '540127', '西藏自治区拉萨市墨竹工卡县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542100', '西藏自治区昌都地区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542121', '西藏自治区昌都地区昌都县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542122', '西藏自治区昌都地区江达县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542123', '西藏自治区昌都地区贡觉县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542124', '西藏自治区昌都地区类乌齐县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542125', '西藏自治区昌都地区丁青县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542126', '西藏自治区昌都地区察雅县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542127', '西藏自治区昌都地区八宿县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542128', '西藏自治区昌都地区左贡县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542129', '西藏自治区昌都地区芒康县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542132', '西藏自治区昌都地区洛隆县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542133', '西藏自治区昌都地区边坝县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542200', '西藏自治区山南地区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542221', '西藏自治区山南地区乃东县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542222', '西藏自治区山南地区扎囊县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542223', '西藏自治区山南地区贡嘎县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542224', '西藏自治区山南地区桑日县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542225', '西藏自治区山南地区琼结县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542226', '西藏自治区山南地区曲松县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542227', '西藏自治区山南地区措美县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542228', '西藏自治区山南地区洛扎县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542229', '西藏自治区山南地区加查县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542231', '西藏自治区山南地区隆子县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542232', '西藏自治区山南地区错那县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542233', '西藏自治区山南地区浪卡子县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542300', '西藏自治区日喀则地区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542301', '西藏自治区日喀则地区日喀则市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542322', '西藏自治区日喀则地区南木林县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542323', '西藏自治区日喀则地区江孜县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542324', '西藏自治区日喀则地区定日县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542325', '西藏自治区日喀则地区萨迦县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542326', '西藏自治区日喀则地区拉孜县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542327', '西藏自治区日喀则地区昂仁县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542328', '西藏自治区日喀则地区谢通门县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542329', '西藏自治区日喀则地区白朗县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542330', '西藏自治区日喀则地区仁布县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542331', '西藏自治区日喀则地区康马县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542332', '西藏自治区日喀则地区定结县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542333', '西藏自治区日喀则地区仲巴县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542334', '西藏自治区日喀则地区亚东县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542335', '西藏自治区日喀则地区吉隆县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542336', '西藏自治区日喀则地区聂拉木县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542337', '西藏自治区日喀则地区萨嘎县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542338', '西藏自治区日喀则地区岗巴县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542400', '西藏自治区那曲地区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542421', '西藏自治区那曲地区那曲县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542422', '西藏自治区那曲地区嘉黎县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542423', '西藏自治区那曲地区比如县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542424', '西藏自治区那曲地区聂荣县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542425', '西藏自治区那曲地区安多县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542426', '西藏自治区那曲地区申扎县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542427', '西藏自治区那曲地区索县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542428', '西藏自治区那曲地区班戈县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542429', '西藏自治区那曲地区巴青县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542430', '西藏自治区那曲地区尼玛县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542500', '西藏自治区阿里地区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542521', '西藏自治区阿里地区普兰县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542522', '西藏自治区阿里地区札达县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542523', '西藏自治区阿里地区噶尔县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542524', '西藏自治区阿里地区日土县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542525', '西藏自治区阿里地区革吉县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542526', '西藏自治区阿里地区改则县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542527', '西藏自治区阿里地区措勤县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542600', '西藏自治区林芝地区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542621', '西藏自治区林芝地区林芝县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542622', '西藏自治区林芝地区工布江达县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542623', '西藏自治区林芝地区米林县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542624', '西藏自治区林芝地区墨脱县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542625', '西藏自治区林芝地区波密县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542626', '西藏自治区林芝地区察隅县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '542627', '西藏自治区林芝地区朗县');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610000', '陕西省');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610100', '陕西省西安市');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610101', '陕西省西安市市辖区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610102', '陕西省西安市新城区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('XZQH', '行政区划', '610103', '陕西省西安市碑林区');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-13', '矿物开采人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-14', '矿物处理人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-15', '钻井人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-16', '石油、天然气开采人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-17', '盐业生产人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-19', '其他勘测及矿物开采人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-2', '金属冶炼、轧制人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-21', '炼铁人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-22', '炼钢人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-23', '铁合金冶炼人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-24', '重有色金属冶炼人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-25', '轻有色金属冶炼人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-26', '稀贵金属冶炼人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-27', '半导体材料制备人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-28', '金属轧制人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-29', '铸铁管人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-31', '炭素制品生产人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-32', '硬质合金生产人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-39', '其他金属冶炼、轧制人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-4', '化工产品生产人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-41', '化工产品生产通用工艺人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-42', '石油炼制生产人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-43', '煤化工生产人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-44', '化学肥料生产人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-45', '无机化工产品生产人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-46', '基本有机化工产品生产人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-47', '合成树脂生产人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-48', '合成橡胶生产人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-49', '化学纤维生产人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-51', '合成革生产人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-52', '精细化工产品生产人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-53', '信息记录材料生产人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-54', '火药、炸药制造人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-55', '林产化工产品生产人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-56', '复合材料加工人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-57', '日用化学品生产人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-59', '其他化工产品生产人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-6', '机械制造加工人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-61', '机械冷加工人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-62', '机械热加工人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-63', '特种加工设备操作人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-64', '冷作钣金加工人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-65', '工件表面处理加工人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-66', '磨料磨具制造加工人员');
insert into DSM_DD_BDDM (DMZD, DMZDZWM, DM, DMMC)
values ('ZYDM', '职业代码', '6-67', '航天器件加工成型人员');
commit ;
insert into DSM_OBJECT_MANAGE_STANDARD (ID, VALUE, TYPE, REMARKS, DELETED, PARID, TEXT)
values ('JZCODEGASJZZFL01', 'ysk', 'SJZZ', '数据组织分类', '0', 'JZCODEGASJZZFL', '原始库');
insert into DSM_OBJECT_MANAGE_STANDARD (ID, VALUE, TYPE, REMARKS, DELETED, PARID, TEXT)
values ('JZCODEGASJZZFL03', 'ztk', 'SJZZ', '数据组织分类', '0', 'JZCODEGASJZZFL', '主题库');
insert into DSM_OBJECT_MANAGE_STANDARD (ID, VALUE, TYPE, REMARKS, DELETED, PARID, TEXT)
values ('JZCODEGASJZZFL02', 'zyk', 'SJZZ', '数据组织分类', '0', 'JZCODEGASJZZFL', '资源库');
insert into DSM_OBJECT_MANAGE_STANDARD (ID, VALUE, TYPE, REMARKS, DELETED, PARID, TEXT)
values ('JZCODEGASJZZFL05', 'ywk', 'SJZZ', '数据组织分类', '0', 'JZCODEGASJZZFL', '业务库');
insert into DSM_OBJECT_MANAGE_STANDARD (ID, VALUE, TYPE, REMARKS, DELETED, PARID, TEXT)
values ('JZCODEGASJZZFL06', 'ywys', 'SJZZ', '数据组织分类', '0', 'JZCODEGASJZZFL', '业务要素索引库');
insert into DSM_OBJECT_MANAGE_STANDARD (ID, VALUE, TYPE, REMARKS, DELETED, PARID, TEXT)
values ('数据产品线', '01', 'GSCCBS', '公司层次标识', '0', null, '数据产品线');
insert into DSM_OBJECT_MANAGE_STANDARD (ID, VALUE, TYPE, REMARKS, DELETED, PARID, TEXT)
values ('应用产品线', '02', 'GSCCBS', '公司层次标识', '0', null, '应用产品线');
insert into DSM_OBJECT_MANAGE_STANDARD (ID, VALUE, TYPE, REMARKS, DELETED, PARID, TEXT)
values ('管理产品线', '03', 'GSCCBS', '公司层次标识', '0', null, '管理产品线');
insert into DSM_OBJECT_MANAGE_STANDARD (ID, VALUE, TYPE, REMARKS, DELETED, PARID, TEXT)
values ('无线产品线', '04', 'GSCCBS', '公司层次标识', '0', null, '无线产品线');
insert into DSM_OBJECT_MANAGE_STANDARD (ID, VALUE, TYPE, REMARKS, DELETED, PARID, TEXT)
values ('新疆分公司', '20', 'GSCCBS', '公司层次标识', '0', null, '新疆分公司');
insert into DSM_OBJECT_MANAGE_STANDARD (ID, VALUE, TYPE, REMARKS, DELETED, PARID, TEXT)
values ('天津分公司', '30', 'GSCCBS', '公司层次标识', '0', null, '天津分公司');
insert into DSM_OBJECT_MANAGE_STANDARD (ID, VALUE, TYPE, REMARKS, DELETED, PARID, TEXT)
values ('广西分公司', '40', 'GSCCBS', '公司层次标识', '0', null, '广西分公司');
insert into DSM_OBJECT_MANAGE_STANDARD (ID, VALUE, TYPE, REMARKS, DELETED, PARID, TEXT)
values ('开发环境', 'dev', 'XMKJSZHJ', '项目空间所在环境', '0', null, '开发环境');
insert into DSM_OBJECT_MANAGE_STANDARD (ID, VALUE, TYPE, REMARKS, DELETED, PARID, TEXT)
values ('生产环境', 'prod', 'XMKJSZHJ', '项目空间所在环境', '0', null, '生产环境');
insert into DSM_OBJECT_MANAGE_STANDARD (ID, VALUE, TYPE, REMARKS, DELETED, PARID, TEXT)
values ('日分区', 'd', 'FQBS', '分区标识', '0', null, '日分区');
insert into DSM_OBJECT_MANAGE_STANDARD (ID, VALUE, TYPE, REMARKS, DELETED, PARID, TEXT)
values ('周分区', 'w', 'FQBS', '分区标识', '0', null, '周分区');
insert into DSM_OBJECT_MANAGE_STANDARD (ID, VALUE, TYPE, REMARKS, DELETED, PARID, TEXT)
values ('月分区', 'm', 'FQBS', '分区标识', '0', null, '月分区');
insert into DSM_OBJECT_MANAGE_STANDARD (ID, VALUE, TYPE, REMARKS, DELETED, PARID, TEXT)
values ('年分区', 'y', 'FQBS', '分区标识', '0', null, '年分区');
insert into DSM_OBJECT_MANAGE_STANDARD (ID, VALUE, TYPE, REMARKS, DELETED, PARID, TEXT)
values ('无分区', 'a', 'FQBS', '分区标识', '0', null, '无分区');
insert into DSM_OBJECT_MANAGE_STANDARD (ID, VALUE, TYPE, REMARKS, DELETED, PARID, TEXT)
values ('分区数据增量更新', 'd', 'ZQLBS', '增全量标识', '0', null, '分区数据增量更新');
insert into DSM_OBJECT_MANAGE_STANDARD (ID, VALUE, TYPE, REMARKS, DELETED, PARID, TEXT)
values ('分区数据全量更新', 'f', 'ZQLBS', '增全量标识', '0', null, '分区数据全量更新');
insert into DSM_OBJECT_MANAGE_STANDARD (ID, VALUE, TYPE, REMARKS, DELETED, PARID, TEXT)
values ('JZCODEGASJZZFL020202', 'ysglk', 'ZYKLX', '资源库分类', '0', 'JZCODEGASJZZFL02', '要素关联库');
insert into DSM_OBJECT_MANAGE_STANDARD (ID, VALUE, TYPE, REMARKS, DELETED, PARID, TEXT)
values ('JZCODEGASJZZFL020201', 'ysgxk', 'ZYKLX', '资源库分类', '0', 'JZCODEGASJZZFL02', '要素关系库');
insert into DSM_OBJECT_MANAGE_STANDARD (ID, VALUE, TYPE, REMARKS, DELETED, PARID, TEXT)
values ('JZCODEGASJZZFL020203', 'yszdxwk', 'ZYKLX', '资源库分类', '0', 'JZCODEGASJZZFL02', '要素重点行为库');
insert into DSM_OBJECT_MANAGE_STANDARD (ID, VALUE, TYPE, REMARKS, DELETED, PARID, TEXT)
values ('JZCODEGASJZZFL020204', 'yszdnrk', 'ZYKLX', '资源库分类', '0', 'JZCODEGASJZZFL02', '要素重点言论');
insert into DSM_OBJECT_MANAGE_STANDARD (ID, VALUE, TYPE, REMARKS, DELETED, PARID, TEXT)
values ('JZCODEGASJZZFL020205', 'ysfbk', 'ZYKLX', '资源库分类', '0', 'JZCODEGASJZZFL02', '要素分布库');
insert into DSM_OBJECT_MANAGE_STANDARD (ID, VALUE, TYPE, REMARKS, DELETED, PARID, TEXT)
values ('JZCODEGASJZZFL030301', 'ry', 'ZTKLX', '主题库分类', '0', 'JZCODEGASJZZFL03', '人员');
insert into DSM_OBJECT_MANAGE_STANDARD (ID, VALUE, TYPE, REMARKS, DELETED, PARID, TEXT)
values ('JZCODEGASJZZFL030303', 'wscs', 'ZTKLX', '主题库分类', '0', 'JZCODEGASJZZFL03', '网上场所');
insert into DSM_OBJECT_MANAGE_STANDARD (ID, VALUE, TYPE, REMARKS, DELETED, PARID, TEXT)
values ('JZCODEGASJZZFL030302', 'wxcs', 'ZTKLX', '主题库分类', '0', 'JZCODEGASJZZFL03', '网下场所');
insert into DSM_OBJECT_MANAGE_STANDARD (ID, VALUE, TYPE, REMARKS, DELETED, PARID, TEXT)
values ('JZCODEGASJZZFL030304', 'cl', 'ZTKLX', '主题库分类', '0', 'JZCODEGASJZZFL03', '车辆');
insert into DSM_OBJECT_MANAGE_STANDARD (ID, VALUE, TYPE, REMARKS, DELETED, PARID, TEXT)
values ('JZCODEGASJZZFL030305', 'swzd', 'ZTKLX', '主题库分类', '0', 'JZCODEGASJZZFL03', '上网终端设备');
insert into DSM_OBJECT_MANAGE_STANDARD (ID, VALUE, TYPE, REMARKS, DELETED, PARID, TEXT)
values ('JZCODEGASJZZFL030306', 'qdcjsb', 'ZTKLX', '主题库分类', '0', 'JZCODEGASJZZFL03', '前端采集设备');
insert into DSM_OBJECT_MANAGE_STANDARD (ID, VALUE, TYPE, REMARKS, DELETED, PARID, TEXT)
values ('JZCODEGASJZZFL030307', 'xsaj', 'ZTKLX', '主题库分类', '0', 'JZCODEGASJZZFL03', '刑事案件');
insert into DSM_OBJECT_MANAGE_STANDARD (ID, VALUE, TYPE, REMARKS, DELETED, PARID, TEXT)
values ('JZCODEGASJZZFL030308', 'xzaj', 'ZTKLX', '主题库分类', '0', 'JZCODEGASJZZFL03', '行政案件');
insert into DSM_OBJECT_MANAGE_STANDARD (ID, VALUE, TYPE, REMARKS, DELETED, PARID, TEXT)
values ('JZCODEGASJZZFL030309', 'jq', 'ZTKLX', '主题库分类', '0', 'JZCODEGASJZZFL03', '警情');
insert into DSM_OBJECT_MANAGE_STANDARD (ID, VALUE, TYPE, REMARKS, DELETED, PARID, TEXT)
values ('JZCODEGASJZZFL030310', 'sj', 'ZTKLX', '主题库分类', '0', 'JZCODEGASJZZFL03', '事件');
insert into DSM_OBJECT_MANAGE_STANDARD (ID, VALUE, TYPE, REMARKS, DELETED, PARID, TEXT)
values ('JZCODEGASJZZFL030311', 'xx', 'ZTKLX', '主题库分类', '0', 'JZCODEGASJZZFL03', '信息');
insert into DSM_OBJECT_MANAGE_STANDARD (ID, VALUE, TYPE, REMARKS, DELETED, PARID, TEXT)
values ('JZCODEGASJZZFL030312', 'shdw', 'ZTKLX', '主题库分类', '0', 'JZCODEGASJZZFL03', '社会单位');
insert into DSM_OBJECT_MANAGE_STANDARD (ID, VALUE, TYPE, REMARKS, DELETED, PARID, TEXT)
values ('JZCODEGASJZZFL030313', 'zrzj', 'ZTKLX', '主题库分类', '0', 'JZCODEGASJZZFL03', '自然组织');
insert into DSM_OBJECT_MANAGE_STANDARD (ID, VALUE, TYPE, REMARKS, DELETED, PARID, TEXT)
values ('JZCODEGASJZZFL030314', 'qt', 'ZTKLX', '主题库分类', '0', 'JZCODEGASJZZFL03', '群体');
insert into DSM_OBJECT_MANAGE_STANDARD (ID, VALUE, TYPE, REMARKS, DELETED, PARID, TEXT)
values ('JZCODEGASJZZFL040404', 'gz', 'ZSKLX', '知识库分类', '0', 'JZCODEGASJZZFL04', '规则库');
insert into DSM_OBJECT_MANAGE_STANDARD (ID, VALUE, TYPE, REMARKS, DELETED, PARID, TEXT)
values ('JZCODEGASJZZFL040401', 'jczs', 'ZSKLX', '知识库分类', '0', 'JZCODEGASJZZFL04', '基础信息库');
insert into DSM_OBJECT_MANAGE_STANDARD (ID, VALUE, TYPE, REMARKS, DELETED, PARID, TEXT)
values ('JZCODEGASJZZFL040402', 'jcsf', 'ZSKLX', '知识库分类', '0', 'JZCODEGASJZZFL04', '基础算法库');
insert into DSM_OBJECT_MANAGE_STANDARD (ID, VALUE, TYPE, REMARKS, DELETED, PARID, TEXT)
values ('JZCODEGASJZZFL040403', 'znxxcl', 'ZSKLX', '知识库分类', '0', 'JZCODEGASJZZFL04', '智能信息处理知识库');
insert into DSM_OBJECT_MANAGE_STANDARD (ID, VALUE, TYPE, REMARKS, DELETED, PARID, TEXT)
values ('JZCODEGASJZZFL050503', 'ywzs', 'YWKLX', '业务库分类', '0', 'JZCODEGASJZZFL05', '业务知识库');
insert into DSM_OBJECT_MANAGE_STANDARD (ID, VALUE, TYPE, REMARKS, DELETED, PARID, TEXT)
values ('JZCODEGASJZZFL050502', 'ywsc', 'YWKLX', '业务库分类', '0', 'JZCODEGASJZZFL05', '业务生产库');
insert into DSM_OBJECT_MANAGE_STANDARD (ID, VALUE, TYPE, REMARKS, DELETED, PARID, TEXT)
values ('JZCODEGASJZZFL050501', 'ywzy', 'YWKLX', '业务库分类', '0', 'JZCODEGASJZZFL05', '业务资源库');
insert into DSM_OBJECT_MANAGE_STANDARD (ID, VALUE, TYPE, REMARKS, DELETED, PARID, TEXT)
values ('JZCODEGASJZZFL04', 'zsk', 'SJZZ', '数据组织分类', '0', 'JZCODEGASJZZFL', '知识库');
insert into DSM_OBJECT_MANAGE_STANDARD (ID, VALUE, TYPE, REMARKS, DELETED, PARID, TEXT)
values ('JZCODEGASJZZFL99', 'other', 'SJZZ', '数据组织分类', '0', 'JZCODEGASJZZFL', '其他');
commit ;





