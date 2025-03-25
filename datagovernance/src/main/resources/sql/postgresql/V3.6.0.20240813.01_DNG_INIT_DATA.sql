-- DGN_COMMON_SETTING 表
insert into DGN_COMMON_SETTING (PARENT_ID, NAME, LOGICAL_JUDGMENT, THRESHOLD_VALUE, ID, TREE_TYPE, IS_ACTIVE, PAGE_URL)
values ('dataQuality', '数据质量配置', '', '{"abnormalDataPersistDays":"1","abnormalDataPersistNums":"1000","passScore":80,"qualityReportPersisttDays":"60","threshold":{"alarmLow":60,"alarmUp":75,"integrityCheck":"true","normalLow":75,"normalUp":90,"realTimeAlarmLow":60,"realTimeAlarmUp":120,"realTimeCheck":"true","realTimeNormalLow":30,"realTimeNormalUp":60,"realTimeSeriousLow":11,"seriousLow":49}}', '48fa0b1d636a4adc84ade34f49d4a649', '3', '1', '');

insert into DGN_COMMON_SETTING (PARENT_ID, NAME, LOGICAL_JUDGMENT, THRESHOLD_VALUE, ID, TREE_TYPE, IS_ACTIVE, PAGE_URL)
values ('dataProperty', '数据资产配置', '', '{"assetsStoreDays":"80","fluctuateAlarmLevel":"警告","fluctuateCheck":"true","fluctuateDays":"7","fluctuateRate":"30","noDataAlarmLevel":"严重","noDataCheck":"true"}', 'd4ecd3c046a84a7b98b7366c9b646297', '3', '1', '');

insert into DGN_COMMON_SETTING (PARENT_ID, NAME, LOGICAL_JUDGMENT, THRESHOLD_VALUE, ID, TREE_TYPE, IS_ACTIVE, PAGE_URL)
values ('alarmPush', '告警推送配置', '', '{"alarmLevel":"告警,严重,一般","id":"d81e7ccde852439e808aaa037bdeaaae","isEnable":"true","pushAlarmInfoUrl":"http://176.101.52.51:9093/MSG-ALARM","pushMode":"http","remark":"test"}', 'd81e7ccde852439e808aaa037bdeaaae', '3', '1', '');

insert into DGN_COMMON_SETTING (PARENT_ID, NAME, LOGICAL_JUDGMENT, THRESHOLD_VALUE, ID, TREE_TYPE, IS_ACTIVE, PAGE_URL)
values ('dataReconciliation', '对账汇总启用环节', '大于等于', 'dataAccess,dataStorage,dataDistribution,dataIssued,standardization', '0cf0cc18c0df4179ba1cbd946264cba5', '3', '1', '');

insert into DGN_COMMON_SETTING (PARENT_ID, NAME, LOGICAL_JUDGMENT, THRESHOLD_VALUE, ID, TREE_TYPE, IS_ACTIVE, PAGE_URL)
values ('20191216192027336', '数据资产配置', '', '{"fluctuateDays":"7","fluctuateRate":"35","noDataCheck":true,"fluctuateCheck":true}', '数据资产配置', '3', '1', '');

insert into DGN_COMMON_SETTING (PARENT_ID, NAME, LOGICAL_JUDGMENT, THRESHOLD_VALUE, ID, TREE_TYPE, IS_ACTIVE, PAGE_URL)
values ('dataReconciliation', '同比接入设置', '大于等于', '{"acceptError":"22","acceptErrorRate":"15","acceptErrorAlarmLevel":"一般"}', 'd538e9f20a62467486cc1e8dd8a71009', '3', '1', '');

insert into DGN_COMMON_SETTING (PARENT_ID, NAME, LOGICAL_JUDGMENT, THRESHOLD_VALUE, ID, TREE_TYPE, IS_ACTIVE, PAGE_URL)
values ('dataReconciliation', '环比数据处理设置', '大于等于', '{"standardError":" 11","standardErrorRate":"10","standardErrorAlarmLevel":"一般"}', 'e14896c78b104a22a7defd2c721f1045', '3', '1', '');

insert into DGN_COMMON_SETTING (PARENT_ID, NAME, LOGICAL_JUDGMENT, THRESHOLD_VALUE, ID, TREE_TYPE, IS_ACTIVE, PAGE_URL)
values ('dataReconciliation', '环比入库设置', '大于等于', '{"storageError":"","storageErrorRate":"10","storageErrorAlarmLevel":"警告"}', '54ee36a9f6b642389737d51f65c86cbd', '3', '1', '');

insert into DGN_COMMON_SETTING (PARENT_ID, NAME, LOGICAL_JUDGMENT, THRESHOLD_VALUE, ID, TREE_TYPE, IS_ACTIVE, PAGE_URL)
values ('dataReconciliation', '告警数据保留周期设置', '大于等于', '80', '888c4e89932549fca3f483ea1efceb75', '3', '1', '');

insert into DGN_COMMON_SETTING (PARENT_ID, NAME, LOGICAL_JUDGMENT, THRESHOLD_VALUE, ID, TREE_TYPE, IS_ACTIVE, PAGE_URL)
values ('dataReconciliation', '自动进入销账时间', '大于等于', '66', 'f92c9e5684be4e9c92332376df7a67bd', '3', '1', '');

insert into DGN_COMMON_SETTING (PARENT_ID, NAME, LOGICAL_JUDGMENT, THRESHOLD_VALUE, ID, TREE_TYPE, IS_ACTIVE, PAGE_URL)
values ('dataReconciliation', '账单保留周期设置', '大于等于', '14', '9ff213a522ec45e993b682612eb21c9c', '3', '1', '');

insert into DGN_COMMON_SETTING (PARENT_ID, NAME, LOGICAL_JUDGMENT, THRESHOLD_VALUE, ID, TREE_TYPE, IS_ACTIVE, PAGE_URL)
values ('dataReconciliation', '对账失败账单保留周期设置', '大于等于', '30', '7ee1363aed204653ac4ba3ff39c8bd76', '3', '1', '');

insert into DGN_COMMON_SETTING (PARENT_ID, NAME, LOGICAL_JUDGMENT, THRESHOLD_VALUE, ID, TREE_TYPE, IS_ACTIVE, PAGE_URL)
values ('', '标准版本管理设置', '', '{"objectVersions":"1.0","synlteFieldVersions":"1.0","fieldDeterminerVersions":"1.0"}', '标准版本配置', '', '', '');

insert into DGN_COMMON_SETTING (PARENT_ID, NAME, LOGICAL_JUDGMENT, THRESHOLD_VALUE, ID, TREE_TYPE, IS_ACTIVE, PAGE_URL)
values ('dataReconciliation', '对账单存储周期', '大于等于', '90', '6692210798c141a1a0a04e33c38ae5c5', '3', '1', '');

insert into DGN_COMMON_SETTING (PARENT_ID, NAME, LOGICAL_JUDGMENT, THRESHOLD_VALUE, ID, TREE_TYPE, IS_ACTIVE, PAGE_URL)
values ('dataReconciliation', '对账结果存储周期', '大于等于', '180', 'd4e4f7bb34d84682bad8ce15343c246f', '3', '1', '');

insert into DGN_COMMON_SETTING (PARENT_ID, NAME, LOGICAL_JUDGMENT, THRESHOLD_VALUE, ID, TREE_TYPE, IS_ACTIVE, PAGE_URL)
values ('dataReconciliation', '对账分析存储周期', '大于等于', '720', '9cbdf9e998c4406f89f31542e66c8867', '3', '1', '');

insert into DGN_COMMON_SETTING (PARENT_ID, NAME, LOGICAL_JUDGMENT, THRESHOLD_VALUE, ID, TREE_TYPE, IS_ACTIVE, PAGE_URL)
values ('dataReconciliation', '对账调度策略', '大于等于', '{"val":1,"unit":"HOUR"}', '91e917f2d46f437691f9554fdb689285', '3', '1', '');

insert into DGN_COMMON_SETTING (PARENT_ID, NAME, LOGICAL_JUDGMENT, THRESHOLD_VALUE, ID, TREE_TYPE, IS_ACTIVE, PAGE_URL)
values ('dataReconciliation', '自主销账策略', '大于等于', '3', '892f0f7585334b6da4cb36890bc374a7', '3', '1', '');

insert into DGN_COMMON_SETTING (PARENT_ID, NAME, LOGICAL_JUDGMENT, THRESHOLD_VALUE, ID, TREE_TYPE, IS_ACTIVE, PAGE_URL)
values ('dataReconciliation', '对账告警策略', '大于等于', '{"alarmLevel":"common","accDefeat":false,"recordCount":5,"totalSize":0,"fingerMark":false}', 'e0bf4aa7bf85462098fc40cd36919903', '3', '1', '');

insert into DGN_COMMON_SETTING (PARENT_ID, NAME, LOGICAL_JUDGMENT, THRESHOLD_VALUE, ID, TREE_TYPE, IS_ACTIVE, PAGE_URL)
values ('dataReconciliation', '账单流水逾期策略', '大于等于', '3', '1734c2d86cf340b794f5d47fa482487d', '3', '1', '');

insert into DGN_COMMON_SETTING (PARENT_ID, NAME, LOGICAL_JUDGMENT, THRESHOLD_VALUE, ID, TREE_TYPE, IS_ACTIVE, PAGE_URL)
values ('dataReconciliation', '账单流水存储周期', '大于等于', '15', '328e49ef03f1411780328367f22c6660', '3', '1', '');

insert into DGN_COMMON_SETTING (PARENT_ID, NAME, LOGICAL_JUDGMENT, THRESHOLD_VALUE, ID, TREE_TYPE, IS_ACTIVE, PAGE_URL)
values ('dataReconciliation', '账单流水合并策略', '大于等于', '{"val":3,"unit":"MINUTE"}', '35eb9e5cadbf4e12b8972474230d8cef', '3', '1', '');

insert into DGN_COMMON_SETTING (PARENT_ID, NAME, LOGICAL_JUDGMENT, THRESHOLD_VALUE, ID, TREE_TYPE, IS_ACTIVE, PAGE_URL)
values ('dataReconciliation', '列筛选器', '等于', 'accDateTimeStr,dataResourceName,dataResourceId,dataOrg,prdBillNum,acpBillNum,accSuccessNum,accFailNum,cancelBillNum,notAccountNum,prdRecNum,acpRecNum,acpRatio,reReportBillCount,reReportRecordNum,reReportSuccessNum,reReportSuccessRatio,cancelNum,notCancelNum,cancelRatio,updateTimeStr,successRatio,accSuccessRatio', 'd00d568bbfd34aa28abd56ac2b3e85f9', '3', '1', '');

commit;

-- 导航栏的相关信息
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('1','首页','Layout',NULL,'1','0','1','dataFactory','01',' ',NULL,'/dataStorageMonitoring',0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('2','首页',NULL,'page/tableOrganizationIndex/DataStorageMonitoring','1','0','2',' ','01','dataStorageMonitoring',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('3','数据仓库','Layout',NULL,'1','0','1','dataFactory','02','dataWareHouse',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('4','数据中心',NULL,'page/dataWareHouse/dataCenter','1','0','2','dataWareHouse','0201','dataCenter',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('5','数据仓库',NULL,'page/dataWareHouse/dataWarehouse','1','0','2','dataWareHouse','0202','dataWareHouses',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('6','资源注册',NULL,'page/dataWareHouse/ResourceRegister','1','0','1','dataWareHouse','0203','resourceRegister',NULL,NULL,1);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('7','数据作业','Layout',NULL,'1','0','1','dataFactory','03','dataJobs',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('8','数据探查',NULL,'page/dataJobs/dataExploration/index','1','0','2','dataJobs','0301','dataExploration',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('9','数据探查分析',NULL,'page/dataJobs/dataExploration/explorationAnalysis','1','0','2','dataJobs','030101','explorationAnalysis',NULL,NULL,1);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('10','数据定义',NULL,'page/dataJobs/dataDefinition/index','1','0','2','dataJobs','0302','dataDefinition',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('11','建表规则',NULL,'page/dataStandardManager/prestoExposition','1','0','3','dataDefinition','030204','prestoExposition',NULL,NULL,1);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('12','入库错误数据',NULL,'page/dataBilling/errorDataList','1','0','3','dataDefinition','030205','errorDataList',NULL,NULL,1);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('13','数据标准定义',NULL,'page/dataJobs/dataDefinition/dataStandardDefinition','1','0','2','dataJobs','0303','dataStandardDefinition',NULL,NULL,1);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('14','数据集成',NULL,'page/dataJobs/dataIntegration/index','1','0','2','dataJobs','0304','dataIntegration',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('15','作业流程',NULL,'page/dataJobs/dataIntegration/jobCanvas','0','0','3','dataIntegration','030401','jobCanvas',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('16','数据链路对账',NULL,'page/dataJobs/dataReconciliation/dataLink/index','1','0','3','dataReconciliation','030401','dataLink',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('17','作业管理',NULL,'page/dataJobs/dataIntegration/jobManagement','0','0','3','dataIntegration','030402','jobManagement',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('18','数据对账统计',NULL,'page/dataBilling/billingAlarm','1','0','3','dataReconciliation','030402','billingAlarm',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('20','数据对账详单',NULL,'page/dataBilling/index','1','0','3','dataReconciliation','030403','dataBilling',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('19','作业监控',NULL,'page/dataJobs/dataIntegration/jobMonitoring','0','0','3','dataIntegration','030403','jobMonitoring',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('21','错误数据展现',NULL,'page/dataJobs/dataReconciliation/errorData','0','0','3','dataReconciliation','030404','errorData',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('22','作业模板',NULL,'page/dataJobs/dataIntegration/templateManagement','0','0','3','dataIntegration','030404','templateManagement',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('23','数据处理',NULL,'page/dataProcessing/dataProcessing/index','1','0','3','dataIntegration','030405','dataProcessing',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('24','数据对账分析',NULL,'page/dataBilling/reconciliatioAnalysis','1','0','3','dataReconciliation','030405','reconciliatioAnalysis',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('25','空规则展示',NULL,'page/dataProcessing/dataProcessing/emptyRule','1','0','3','dataIntegration','030406','emptyRule',NULL,NULL,1);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('26','提取规则',NULL,'page/dataProcessing/extractRulesGro/extractRulesGro','1','0','3','dataIntegration','030407','extractRulesGro',NULL,NULL,1);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('27','创建映射',NULL,'page/dataProcessing/creatMap/creatMap','1','0','3','dataIntegration','030408','creatMap',NULL,NULL,1);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('28','清洗规则',NULL,'page/dataProcessing/cleanRule/cleanRule','1','0','3','dataIntegration','030409','cleanRule',NULL,NULL,1);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('29','基础配置',NULL,'page/dataJobs/dataReconciliation/basicConfiguration/index','1','0','3','dataReconciliation','030409','basicConfiguration',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('31','对账配置',NULL,'page/dataJobs/dataReconciliation/reconcliliationConfiguration/index','1','0','3','dataReconciliation','030410','reconcliliationConfiguration',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('30','关联规则',NULL,'page/dataProcessing/associaRule/associaRule','1','0','3','dataIntegration','030410','associaRule',NULL,NULL,1);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('33','账单管理',NULL,'page/dataJobs/dataReconciliation/billingManagement/index','1','0','3','dataReconciliation','030411','billingManagement',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('32','协议数据分发',NULL,'page/dataProcessing/distributeData/distributeData','1','0','3','dataIntegration','030411','distributeData',NULL,NULL,1);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('35','对账明细',NULL,'page/dataJobs/dataReconciliation/dataReconcilitionItem/index','1','0','3','dataReconciliation','030412','dataReconcilitionItem',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('34','协议语种标注',NULL,'page/dataProcessing/markRule/markRule','1','0','3','dataIntegration','030412','markRule',NULL,NULL,1);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('36','协议比对规则',NULL,'page/dataProcessing/matchRule/matchRule','1','0','3','dataIntegration','030413','matchRule',NULL,NULL,1);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('37','对账分析',NULL,'page/dataJobs/dataReconciliation/dataReconcilitionStatistics/index','1','0','3','dataReconciliation','030413','dataReconcilitionStatistics',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('38','协议其他规则',NULL,'page/dataProcessing/otherRules/otherRules','1','0','3','dataIntegration','030414','otherRules',NULL,NULL,1);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('39','实时数据下发监控',NULL,'page/dataJobs/dataReconciliation/deliveryMonitoring/index','1','0','3','dataReconciliation','030414','deliveryMonitoring',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('41','实时数据下发监控历史',NULL,'page/dataJobs/dataReconciliation/historicalAnalysis/index','1','0','3','dataReconciliation','030415','historicalAnalysis',NULL,NULL,1);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('40','其他配置项',NULL,'page/dataProcessing/systemInfoManage/otherSettingManage','1','0','3','dataIntegration','030415','otherSettingManage',NULL,NULL,1);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('42','下发对账监控',NULL,'page/dataJobs/dataReconciliation/distribute/reconciliationMonitoring/indexJZ','1','0','3','dataReconciliation','030416','reconciliationMonitoring',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('43','入库管理',NULL,'page/dataProcessing/warehouseManage/warehouseManage','1','0','3','dataIntegration','030416','warehouseManage',NULL,NULL,1);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('45','回收站',NULL,'page/dataJobs/dataIntegration/jobRecycleBin','0','0','3','dataIntegration','030417','jobRecycleBin',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('44','下发监控基线管理',NULL,'page/dataJobs/dataReconciliation/distribute/reconciliationMonitoring/BaseTimeManage','1','0','3','dataReconciliation','030417','BaseTimeManage',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('46','下发历史比对分析',NULL,'page/dataReconciliation/distribute/dataJobs/reconciliationMonitoring/HistoricalComparisonAnalysis','1','0','3','dataReconciliation','030418','HistoricalComparisonAnalysis',NULL,NULL,1);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('47','数据接入',NULL,'page/dataAccess/dataAccess/index','1','0','3','dataIntegration','030418','dataAccess',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('48','接入任务配置',NULL,'page/dataAccess/dataAccess/accessTaskConfig','1','0','3','dataIntegration','030419','accessTaskConfig',NULL,NULL,1);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('49','排队队列管理',NULL,'page/dataAccess/queueManage/index','1','0','3','dataIntegration','030420','queueManage',NULL,NULL,1);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('50','历史实例管理',NULL,'page/dataAccess/historicalInstanceManage/index','1','0','3','dataIntegration','030421','historicalInstanceManage',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('51','集群监控',NULL,'page/dataAccess/clusterMonitor/index','1','0','3','dataIntegration','030422','clusterMonitor',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('52','公共字段',NULL,'page/dataProcessing/dataProcessing/publicFieldManage','1','0','3','dataIntegration','030424','publicFieldManage',NULL,NULL,1);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('53','数据对账',NULL,'page/dataJobs/dataReconciliation/index','1','0','2','dataJobs','0305','dataReconciliation',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('54','数据质量规则管理',NULL,'page/dataGovernance/dataQuality/ruleManage','1','0','3','dataQuality','030501','ruleManage',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('55','数据质量指标管理',NULL,'page/dataGovernance/dataQuality/quotaManage','1','0','3','dataQuality','030502','quotaManage',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('56','数据质量知识库',NULL,'page/dataGovernance/dataQuality/knowledgeBase','1','0','3','dataQuality','030503','knowledgeBase',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('57','数据质量核检',NULL,'page/dataGovernance/dataQuality/task','1','0','3','dataQuality','030504','task',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('58','数据质量核检配置',NULL,'page/dataGovernance/dataQuality/taskConfig','1','0','3','dataQuality','030505','taskConfig',NULL,NULL,1);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('59','数据质量知识库配置',NULL,'page/dataGovernance/dataQuality/knowledgeBaseConfig','1','0','3','dataQuality','030506','knowledgeBaseConfig',NULL,NULL,1);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('60','数据质量报告',NULL,'page/dataGovernance/dataQuality/report','1','0','3','dataQuality','030507','report',NULL,NULL,1);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('61','数据质量任务执行记录',NULL,'page/dataGovernance/dataQuality/executeRecord','1','0','3','dataQuality','030508','executeRecord',NULL,NULL,1);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('62','数据质量错误数据',NULL,'page/dataGovernance/dataQuality/dirtyData','1','0','3','dataQuality','030509','dirtyData',NULL,NULL,1);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('63','数据建表',NULL,'page/dataJobs/dataDefinition/dataTableBuilding','1','0','2','dataJobs','0306','dataTableBuilding',NULL,NULL,1);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('64','问题库管理',NULL,'page/dataWareHouse/problemBaseManage/index','1','0','2','dataJobs','0307','problemBaseManage',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('65','问题库管理',NULL,'page/dataWareHouse/problemBaseManage/problemManage/index','1','0','3','problemBaseManage','030701','problemManage',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('66','数据组织','Layout',NULL,'1','0','1','dataFactory','04','dataOrgnization',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('67','原始库',NULL,'page/tableOrganizationIndex/DataOrganization','1','{"type": "原始库","name": "1"}','2','dataOrgnization','0401','rawbase',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('68','业务库',NULL,'page/tableOrganizationIndex/DataOrganization','1','{"type": "业务库","name": "1"}','2','dataOrgnization','0402','business',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('69','资源库',NULL,'page/tableOrganizationIndex/DataOrganization','1','{"type": "资源库","name": "1"}','2','dataOrgnization','0403','resource',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('70','主题库',NULL,'page/tableOrganizationIndex/DataOrganization','1','{"type": "主题库","name": "1"}','2','dataOrgnization','0404','theme',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('71','知识库',NULL,'page/tableOrganizationIndex/DataOrganization','1','{"type": "知识库","name": "1"}','2','dataOrgnization','0405','knowledge',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('72','业务要素索引库',NULL,'page/tableOrganizationIndex/DataOrganization','1','{"type": "业务要素索引库","name": "1"}','2','dataOrgnization','0406','ywyssyk',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('73','数据治理','Layout',NULL,'1','0','1','dataFactory','05','dataGovernance',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('74','数据资产',NULL,'page/dataGovernance/dataAssets/index','1','0','2','dataGovernance','0501','dataAssets',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('75','数据组织资产',NULL,'page/tableOrganizationIndex/TableOrganizationIndex','1','{"type": "全部","name": "1"}','3','dataAssets','050101','tableOrganizationIndex',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('76','数据表资产',NULL,'page/tableOrganizationIndex/FormOrganizationIndex','1','0','3','dataAssets','050102','formOrganizationIndex',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('77','生命周期管理',NULL,'page/tableOrganizationIndex/LifeCycleManagement','1','0','3','dataAssets','050103','lifeCycleManagement',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('78','资产详情',NULL,'page/tableOrganizationIndex/OrganizationDetail','1','0','3','dataAssets','050104','organizationDetail',NULL,NULL,1);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('79','血缘管理',NULL,'page/dataGovernance/dataMap/index','1','0','2','dataGovernance','0502','dataMap',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('80','数据血缘',NULL,'page/dataGovernance/dataMap/dataBloodline/index','1','0','3','dataMap','050201','dataBloodline',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('81','应用血缘管理',NULL,'page/dataGovernance/dataMap/applicationBloodManage/index','1','0','3','dataMap','050202','applicationBloodManage',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('82','影响分析',NULL,'page/dataGovernance/dataMap/dataBloodline/ImpactAnalysis/index','1','0','3','dataMap','050203','impactAnalysis',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('83','字段血缘',NULL,'page/dataGovernance/dataMap/dataBloodline/columnBlood','1','0','3','dataMap','050204','columnBlood',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('84','数据质量',NULL,'page/dataGovernance/dataQuality/index','1','0','2','dataGovernance','0503','dataQuality',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('85','数据质量首页',NULL,'page/generalReport/generalReport','0','0','3','dataQuality','050301','generalReport',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('86','数据运维',NULL,'page/dataGovernance/dataUpdate/index','1','0','2','dataGovernance','0504','dataUpdate',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('87','数据开发监控',NULL,'page/dataWorksTaskDefinition/monitor/BusinessMonitor','1','0','3','dataUpdate','050401','businessMonitor',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('88','告警中心',NULL,'page/dataGovernance/dataUpdate/alarmCenter','1','0','3','dataUpdate','050406','alarmCenter',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('89','数据治理跟踪',NULL,'page/dataGovernance/dataUpdate/governanceTracking','1','0','3','dataUpdate','050407','governanceTracking',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('90','历史数据监测',NULL,'page/dataGovernance/dataUpdate/historicalDataMonitor','1','0','3','dataUpdate','050408','historicalDataMonitor',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('91','数据量监测',NULL,'page/dataGovernance/dataMonitoring/dataVolumeMonitor','1','0','3','dataUpdate','050409','dataVolumeMonitor',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('92','数据抖动监测',NULL,'page/dataGovernance/dataMonitoring/dataJitterMonitor','1','0','3','dataUpdate','050410','dataJitterMonitor',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('93','数据堆积监测',NULL,'page/dataGovernance/dataMonitoring/dataDockingMonitor','1','0','3','dataUpdate','050411','dataDockingMonitor',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('94','操作日志管理',NULL,'page/dataGovernance/dataUpdate/operateLog/index','1','0','3','dataUpdate','050412','operateLog',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('95','数据标准','Layout',NULL,'1','0','1','dataFactory','06','dataStandardManager',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('96','数据元管理',NULL,'page/dataStandardManager/dataElementManage/index','1','0','2','dataStandardManager','0601','dataElementManage',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('97','历史版本信息',NULL,'page/dataStandardManager/historyVersion/HistoryVersionInfo','1','0','2','dataStandardManager','06010','historyVersionInfo',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('98','标准字典代码集配置',NULL,'page/dataStandardManager/dataDictionaryManage/elementCodeSetConfig','1','0','2','dataStandardManager','06011','elementCodeSetConfig',NULL,NULL,1);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('99','数据集标准',NULL,'page/dataStandardManager/resourceManage','1','0','2','dataStandardManager','06012','resourceManage',NULL,NULL,1);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('100','数据表命名规范',NULL,'page/dataStandardManager/tableNameExposition','1','0','2','dataStandardManager','06013','tableNameExposition',NULL,NULL,1);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('101','限定词管理',NULL,'page/dataStandardManager/determinerManage/index','1','0','2','dataStandardManager','0602','determinerManage',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('102','数据集管理',NULL,'page/dataStandardManager/dataSetManage/index','1','0','2','dataStandardManager','0603','dataSetManage',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('103','数据集管理',NULL,'page/dataStandardManager/dataSetManage/dataSetManagement','1','0','3','dataSetManage','060301','dataSetManagement',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('104','公共数据项管理',NULL,'page/dataStandardManager/dataSetManage/publicDataManage','1','0','3','dataSetManage','060302','publicDataManage',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('105','建表信息管理',NULL,'page/dataStandardManager/dataSetManage/tableBuildingManage','1','0','3','dataSetManage','060303','tableBuildingManage',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('106','添加建表信息',NULL,'page/dataStandardManager/dataSetManage/tableBuildingAdd','1','0','3','dataSetManage','060304','tableBuildingAdd',NULL,NULL,1);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('107','字典代码集管理',NULL,'page/dataStandardManager/dataDictionaryManage/index','1','0','2','dataStandardManager','0604','dataDictionaryManage',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('108','标准字典代码集管理',NULL,'page/dataStandardManager/dataDictionaryManage/elementCodeSetManage','0','0','3','dataDictionaryManage','060401','elementCodeSetManage',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('109','标准字典代码集管理',NULL,'page/dataStandardManager/dataDictionaryManage/standardDictionary','1','0','3','dataDictionaryManage','060401','standardDictionary',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('110','原始字典代码集管理',NULL,'page/dataStandardManager/dataDictionaryManage/originalDictionary','1','0','3','dataDictionaryManage','060402','originalDictionary',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('111','单位机构管理',NULL,'page/dataStandardManager/dataDictionaryManage/unitOrganizationManage','1','0','3','dataDictionaryManage','060403','unitOrganizationManage',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('112','数据要素管理',NULL,'page/dataStandardManager/synlteElementManage/index','1','0','2','dataStandardManager','0606','synlteElementManage',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('113','资源标签管理',NULL,'page/dataStandardManager/resourceTagManage/index','1','0','2','dataStandardManager','0607','resourceTagManage',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('114','数据语义管理',NULL,'page/dataStandardManager/SemanticTableManageHtml','1','0','2','dataStandardManager','0608','semanticTableManageHtml',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('115','地标码管理',NULL,'page/dataStandardManager/regionalCodeTableMonitor','1','0','2','dataStandardManager','0609','regionalCodeTableMonitor',NULL,NULL,1);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('117','数据大屏',NULL,'page/customLargeScreen/index','0','0','1','dataFactory','07','assetsLargeScreen',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('116','数据大屏',NULL,'page/ofcomLargeScreen/index','1','2','1','dataFactory','07','ofcomLargeScreen',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('119','资产检测异常告警设置',NULL,'page/tableOrganizationIndex/OrganizationAlarmSetting','1','0','1','dataFactory','08','OrganizationAlarmSetting',NULL,NULL,1);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('118','设置','Layout',NULL,'1','0','1','dataFactory','08','allSetting',NULL,'/setting/index',1);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('120','设置',NULL,'page/setComponents/Setting','1','0','2','allSetting','0801','setting',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('122','数据对账异常告警设置',NULL,'page/dataBilling/billingAlarmSetting','1','0','1','dataFactory','09','billingAlarmSetting',NULL,NULL,1);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('121',NULL,'Layout',NULL,'1','0','1','dataFactory','09','*',NULL,'/404',1);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('124','质量检测异常告警设置',NULL,'page/detectionException/detectionExceptionSetting','1','0','1','dataFactory','10','detectionExceptionSetting',NULL,NULL,1);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('123',NULL,'404',NULL,'1','0','1','dataFactory','10','404',NULL,NULL,1);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('125','质量检测报告管理',NULL,'page/dataGovernance/dataQuality/report','1','0','1','dataFactory','11','reportManageFrame',NULL,NULL,1);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('126','配置管理','Layout',NULL,'1','0','1','dataFactory','12','configerManage',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('127','通用管理',NULL,'page/configerManage/generalManage/index','1','0','2','configerManage','1201','generalManage',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('128','通知设置',NULL,'page/configerManage/notificationSetting/index','1','0','2','configerManage','1202','notificationSetting',NULL,NULL,1);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('129','平台信息',NULL,'page/configerManage/notificationSetting/platformInfo','0','0','3','notificationSetting','120201','platformInfo',NULL,NULL,1);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('130','标准信息',NULL,'page/configerManage/notificationSetting/standardInfo','0','0','3','notificationSetting','120202','standardInfo',NULL,NULL,1);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('131','处理节点管理',NULL,'page/configerManage/nodeManage/index','1','0','2','configerManage','1203','nodeManage',NULL,NULL,0);
insert into dgn_navbar (nav_id,nav_name,nav_class,nav_link,nav_show,nav_blank,nav_level,nav_parent_name,nav_order,nav_name_en,nav_ip,nav_redirect,nav_hidden)
values ('132','新疆数据大屏',NULL,'page/xjLargeScreen/index','0','0','1','dataFactory','07','xjLargeScreen',NULL,NULL,0);

commit;

-- 新疆地市简称的码表
insert into DGN_CITY_ABB(CITY_ABB,CITY_CODE,CITY_NAME) values('wlmq','650100','乌鲁木齐');
insert into DGN_CITY_ABB(CITY_ABB,CITY_CODE,CITY_NAME) values('alt','654300','阿勒泰');
insert into DGN_CITY_ABB(CITY_ABB,CITY_CODE,CITY_NAME) values('ht','653200','和田');
insert into DGN_CITY_ABB(CITY_ABB,CITY_CODE,CITY_NAME) values('tlf','650400','吐鲁番');
insert into DGN_CITY_ABB(CITY_ABB,CITY_CODE,CITY_NAME) values('hm','650500','哈密');
insert into DGN_CITY_ABB(CITY_ABB,CITY_CODE,CITY_NAME) values('ks','653100','喀什');
insert into DGN_CITY_ABB(CITY_ABB,CITY_CODE,CITY_NAME) values('bz','652800','巴州');
insert into DGN_CITY_ABB(CITY_ABB,CITY_CODE,CITY_NAME) values('kz','653000','克州');
commit ;

insert into DGN_BSCDATA_PROVINCE (PRO_ID, PRO_NAME, ISDIRECT, CANTONCODE)
values (1, '浙江', 0, 330000);

insert into DGN_BSCDATA_PROVINCE (PRO_ID, PRO_NAME, ISDIRECT, CANTONCODE)
values (2, '黑龙江', 0, 230000);

insert into DGN_BSCDATA_PROVINCE (PRO_ID, PRO_NAME, ISDIRECT, CANTONCODE)
values (3, '广东', 0, 440000);

insert into DGN_BSCDATA_PROVINCE (PRO_ID, PRO_NAME, ISDIRECT, CANTONCODE)
values (4, '天津', 1, 120000);

insert into DGN_BSCDATA_PROVINCE (PRO_ID, PRO_NAME, ISDIRECT, CANTONCODE)
values (5, '福建', 0, 350000);

insert into DGN_BSCDATA_PROVINCE (PRO_ID, PRO_NAME, ISDIRECT, CANTONCODE)
values (6, '安徽', 0, 340000);

insert into DGN_BSCDATA_PROVINCE (PRO_ID, PRO_NAME, ISDIRECT, CANTONCODE)
values (7, '江苏', 0, 320000);

insert into DGN_BSCDATA_PROVINCE (PRO_ID, PRO_NAME, ISDIRECT, CANTONCODE)
values (8, '河北', 0, 130000);

insert into DGN_BSCDATA_PROVINCE (PRO_ID, PRO_NAME, ISDIRECT, CANTONCODE)
values (9, '北京', 1, 110000);

insert into DGN_BSCDATA_PROVINCE (PRO_ID, PRO_NAME, ISDIRECT, CANTONCODE)
values (10, '江西', 0, 360000);

insert into DGN_BSCDATA_PROVINCE (PRO_ID, PRO_NAME, ISDIRECT, CANTONCODE)
values (11, '湖北', 0, 420000);

insert into DGN_BSCDATA_PROVINCE (PRO_ID, PRO_NAME, ISDIRECT, CANTONCODE)
values (12, '海南', 0, 460000);

insert into DGN_BSCDATA_PROVINCE (PRO_ID, PRO_NAME, ISDIRECT, CANTONCODE)
values (13, '陕西', 0, 610000);

insert into DGN_BSCDATA_PROVINCE (PRO_ID, PRO_NAME, ISDIRECT, CANTONCODE)
values (14, '上海', 1, 310000);

insert into DGN_BSCDATA_PROVINCE (PRO_ID, PRO_NAME, ISDIRECT, CANTONCODE)
values (15, '辽宁', 0, 210000);

insert into DGN_BSCDATA_PROVINCE (PRO_ID, PRO_NAME, ISDIRECT, CANTONCODE)
values (16, '山东', 0, 370000);

insert into DGN_BSCDATA_PROVINCE (PRO_ID, PRO_NAME, ISDIRECT, CANTONCODE)
values (17, '广西', 0, 450000);

insert into DGN_BSCDATA_PROVINCE (PRO_ID, PRO_NAME, ISDIRECT, CANTONCODE)
values (18, '湖南', 0, 430000);

insert into DGN_BSCDATA_PROVINCE (PRO_ID, PRO_NAME, ISDIRECT, CANTONCODE)
values (19, '吉林', 0, 220000);

insert into DGN_BSCDATA_PROVINCE (PRO_ID, PRO_NAME, ISDIRECT, CANTONCODE)
values (20, '河南', 0, 410000);

insert into DGN_BSCDATA_PROVINCE (PRO_ID, PRO_NAME, ISDIRECT, CANTONCODE)
values (21, '宁夏', 0, 640000);

insert into DGN_BSCDATA_PROVINCE (PRO_ID, PRO_NAME, ISDIRECT, CANTONCODE)
values (22, '云南', 0, 530000);

insert into DGN_BSCDATA_PROVINCE (PRO_ID, PRO_NAME, ISDIRECT, CANTONCODE)
values (23, '四川', 0, 510000);

insert into DGN_BSCDATA_PROVINCE (PRO_ID, PRO_NAME, ISDIRECT, CANTONCODE)
values (24, '重庆', 1, 500000);

insert into DGN_BSCDATA_PROVINCE (PRO_ID, PRO_NAME, ISDIRECT, CANTONCODE)
values (25, '新疆', 0, 650000);

insert into DGN_BSCDATA_PROVINCE (PRO_ID, PRO_NAME, ISDIRECT, CANTONCODE)
values (26, '贵州', 0, 520000);

insert into DGN_BSCDATA_PROVINCE (PRO_ID, PRO_NAME, ISDIRECT, CANTONCODE)
values (27, '内蒙古', 0, 150000);

insert into DGN_BSCDATA_PROVINCE (PRO_ID, PRO_NAME, ISDIRECT, CANTONCODE)
values (28, '山西', 0, 140000);

insert into DGN_BSCDATA_PROVINCE (PRO_ID, PRO_NAME, ISDIRECT, CANTONCODE)
values (29, '甘肃', 0, 620000);

insert into DGN_BSCDATA_PROVINCE (PRO_ID, PRO_NAME, ISDIRECT, CANTONCODE)
values (30, '青海', 0, 630000);

insert into DGN_BSCDATA_PROVINCE (PRO_ID, PRO_NAME, ISDIRECT, CANTONCODE)
values (31, '西藏', 0, 540000);

insert into DGN_BSCDATA_PROVINCE (PRO_ID, PRO_NAME, ISDIRECT, CANTONCODE)
values (32, '香港', 0, 810000);

insert into DGN_BSCDATA_PROVINCE (PRO_ID, PRO_NAME, ISDIRECT, CANTONCODE)
values (33, '澳门', 0, 820000);

insert into DGN_BSCDATA_PROVINCE (PRO_ID, PRO_NAME, ISDIRECT, CANTONCODE)
values (34, '台湾', 0, 710000);

commit;

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (18, 197, '娄底', '738', '431300', '431300');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (18, 198, '邵阳', '739', '430500', '430500');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (18, 200, '怀化', '745', '431200', '431200');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (18, 201, '永州', '746', '431100', '431100');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (19, 203, '四平', '434', '220300', '220300');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (19, 204, '辽源', '437', '220400', '220400');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (19, 205, '通化', '435', '220500', '220500');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (19, 206, '白山', '439', '220600', '220600');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (19, 207, '白城', '436', '220800', '220800');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (19, 208, '松原', '438', '220700', '220700');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (19, 209, '吉林', '438', '220200', '220200');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (19, 210, '延边', '433', '222400', '222400');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (19, 211, '珲春', '440', '222400', '222404');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (19, 212, '梅河口', '448', '220500', '220581');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (20, 213, '郑州', '371', '410100', '410100');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (20, 214, '开封', '378', '410200', '410200');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (20, 215, '周口', '394', '411600', '411600');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (20, 216, '商丘', '370', '411400', '411400');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (20, 217, '洛阳', '379', '410300', '410300');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (20, 218, '焦作', '391', '410800', '410800');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (20, 220, '新乡', '373', '410700', '410700');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (20, 221, '安阳', '372', '410500', '410500');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (20, 222, '鹤壁', '392', '410600', '410600');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (20, 223, '濮阳', '393', '410900', '410900');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (20, 224, '漯河', '395', '411100', '411100');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (20, 225, '潢川', '397', '411500', '411526');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (20, 226, '许昌', '374', '411000', '411000');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (20, 228, '信阳', '376', '411500', '411500');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (20, 229, '南阳', '377', '411300', '411300');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (20, 230, '驻马店', '396', '411700', '411700');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (21, 232, '石嘴山', '952', '640200', '640200');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (21, 233, '吴忠', '953', '640300', '640300');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (21, 408, '中卫', '955', '640500', '640500');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (22, 235, '昆明', '871', '530100', '530100');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (22, 238, '普洱', '879', '530800', '530802');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (22, 239, '大理', '872', '532900', '532900');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (22, 240, '楚雄', '878', '532300', '532300');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (22, 242, '保山', '875', '530500', '530500');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (22, 244, '德宏', '692', '533100', '533100');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (22, 245, '临沧', '883', '530900', '530900');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (22, 246, '红河', '873', '532500', '532500');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (22, 247, '文山', '876', '532600', '532600');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (22, 248, '曲靖', '874', '530300', '530300');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (22, 249, '昭通', '870', '530600', '530600');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (22, 250, '迪庆', '887', '533400', '533400');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (23, 251, '成都', '28', '510100', '510100');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (23, 253, '绵阳', '816', '510700', '510700');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (23, 254, '广元', '839', '510800', '510800');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (23, 256, '攀枝花', '812', '510400', '510400');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (23, 261, '宜宾', '831', '511500', '511500');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (23, 262, '内江', '832', '511000', '511000');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (23, 263, '遂宁', '825', '510900', '510900');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (23, 265, '达州', '818', '511700', '511700');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (23, 266, '巴中', '827', '511900', '511900');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (23, 267, '广安', '826', '511600', '511600');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (23, 268, '甘孜', '836', '513300', '513300');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (23, 269, '阿坝', '837', '513200', '513200');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (23, 270, '眉山', '833', '511400', '511181');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (23, 271, '资阳', '832', '512000', '512000');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (24, 272, '重庆', '23', '500000', '500000');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (25, 276, '乌鲁木齐', '991', '650100', '650100');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (25, 280, '吐鲁番', '995', '652100', '652100');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (25, 281, '库尔勒', '996', '652800', '652801');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (25, 282, '阿克苏', '997', '652900', '652900');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (25, 283, '喀什', '998', '653100', '653100');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (25, 284, '伊犁', '999', '654000', '654000');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (25, 285, '哈密', '902', '652200', '652200');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (25, 286, '奎屯', '992', '654000', '654003');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (25, 287, '阿勒泰', '906', '654300', '654300');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (25, 288, '塔城', '901', '654200', '654200');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (25, 290, '阿图什', '908', '653000', '653001');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (25, 291, '博乐', '909', '652700', '652701');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (26, 292, '贵阳', '851', '520100', '520100');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (26, 293, '六盘水', '858', '520200', '520200');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (26, 294, '黔东南', '855', '522600', '522600');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (26, 295, '黔西南', '859', '522300', '522300');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (26, 297, '黔南', '854', '522700', '522700');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (26, 298, '毕节', '857', '522400', '522400');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (26, 299, '遵义', '852', '520300', '520300');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (27, 301, '呼和浩特', '471', '150100', '150100');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (27, 302, '包头', '472', '150200', '150200');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (27, 303, '乌兰察布盟', '474', '150900', '150900');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (27, 304, '鄂尔多斯', '477', '150600', '150600');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (27, 305, '锡林郭勒盟', '479', '152500', '152500');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (27, 306, '兴安盟', '482', '152200', '152200');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (27, 307, '乌海', '473', '150300', '150300');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (27, 308, '阿拉善盟', '483', '152900', '152900');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (27, 309, '通辽', '475', '150500', '150500');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (27, 311, '赤峰', '476', '150400', '150400');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (27, 312, '巴彦卓尔盟', '478', '150800', '150800');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (28, 313, '太原', '351', '140100', '140100');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (28, 314, '阳泉', '353', '140300', '140300');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (28, 315, '晋中', '354', '140700', '140700');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (28, 316, '长治', '355', '140400', '140400');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (28, 317, '晋城', '356', '140500', '140500');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (28, 319, '忻州', '350', '140900', '140900');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (28, 320, '运城', '359', '140800', '140800');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (28, 321, '大同', '352', '140200', '140200');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (28, 322, '吕梁', '358', '141100', '141100');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (28, 323, '朔州', '349', '140600', '140600');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (29, 325, '白银', '943', '620400', '620400');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (29, 326, '临夏', '930', '622900', '622900');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (29, 327, '甘南', '941', '623000', '623000');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (29, 329, '金昌', '935', '620600', '620300');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (29, 330, '酒泉', '937', '620900', '620900');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (29, 331, '天水', '938', '620500', '620500');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (29, 332, '定西', '932', '621100', '621100');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (29, 333, '平凉', '933', '620800', '620800');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (29, 334, '庆阳', '934', '621000', '621000');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (29, 335, '陇南', '939', '621200', '621200');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (29, 406, '嘉峪关', '937', '620900', '620200');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (29, 407, '武威', '935', '620600', '620600');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (30, 336, '西宁', '971', '630100', '630100');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (30, 337, '海东', '972', '632100', '632100');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (30, 338, '海西', '977', '632800', '632800');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (30, 339, '海北', '970', '632200', '632200');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (30, 340, '海南', '974', '632500', '632500');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (30, 341, '格尔木', '979', '632800', '632801');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (30, 342, '果洛', '975', '632600', '632600');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (30, 343, '黄南', '973', '632300', '632300');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (30, 344, '玉树', '976', '632700', '632700');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (31, 345, '拉萨', '891', '540100', '540100');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (31, 346, '日喀则', '892', '542300', '542300');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (31, 347, '山南', '893', '542200', '542200');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (31, 348, '林芝', '894', '542600', '542600');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (31, 349, '昌都', '895', '542100', '542100');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (31, 350, '那曲', '896', '542400', '542400');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (31, 351, '阿里', '897', '542500', '542500');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (11, 119, '武汉', '27', '420100', '420100');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (11, 126, '鄂州', '711', '420700', '420700');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (11, 128, '黄冈', '713', '421100', '421100');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (18, 195, '衡阳', '734', '430400', '430400');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (18, 191, '益阳', '737', '430900', '430900');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (18, 199, '湘西', '743', '433100', '433100');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (3, 54, '深圳', '755', '440300', '440300');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (3, 49, '汕头', '754', '440500', '440500');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (3, 34, '江门', '750', '440700', '440700');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (3, 45, '茂名', '668', '440900', '440900');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (3, 52, '梅州', '753', '441400', '441400');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (3, 35, '阳江', '662', '441700', '441700');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (3, 55, '东莞', '769', '441900', '441900');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (3, 51, '揭阳', '663', '445200', '445200');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (17, 186, '钦州', '777', '450700', '450700');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (17, 179, '百色', '776', '451000', '451000');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (17, 402, '贺州', '774', '450400', '451100');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (17, 404, '来宾', '772', '450200', '451300');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (12, 133, '海口', '898', '460200', '460100');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (23, 259, '自贡', '813', '510300', '510300');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (23, 255, '乐山', '833', '511400', '511100');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (23, 258, '雅安', '835', '511800', '511800');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (23, 257, '凉山', '834', '513400', '513400');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (26, 300, '铜仁', '856', '522200', '522200');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (22, 236, '玉溪', '877', '530400', '530400');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (22, 241, '丽江', '888', '530700', '530700');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (22, 237, '西双版纳', '691', '532800', '532800');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (22, 243, '怒江', '886', '533300', '533300');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (13, 145, '宝鸡', '917', '610300', '610300');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (13, 137, '延安', '911', '610600', '610600');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (13, 141, '安康', '915', '610900', '610900');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (29, 324, '兰州', '931', '620100', '620100');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (29, 328, '张掖', '936', '620700', '620700');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (21, 231, '银川', '951', '640100', '640100');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (21, 234, '固原', '954', '640400', '640400');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (25, 277, '克拉玛依', '990', '650200', '650200');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (25, 279, '昌吉', '994', '652300', '652300');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (25, 289, '和田', '903', '653200', '653200');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (25, 278, '石河子', '993', '659001', '659001');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (10, 118, '赣州', '797', '360700', '360700');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (11, 127, '咸宁', '715', '421200', '421200');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (14, 146, '上海', '21', '310000', '310000');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (15, 151, '锦州', '416', '210700', '210700');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (15, 158, '辽阳', '419', '211000', '211000');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (16, 164, '青岛', '532', '370200', '370200');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (16, 171, '济宁', '537', '370800', '370800');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (17, 185, '北海', '779', '450500', '450500');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (18, 190, '株洲', '733', '430200', '430200');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (18, 194, '常德', '736', '430700', '430700');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (19, 202, '长春', '431', '220100', '220100');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (20, 219, '三门峡', '398', '411200', '411200');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (20, 227, '平顶山', '375', '410400', '410400');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (23, 252, '德阳', '838', '510600', '510600');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (23, 260, '泸州', '830', '510500', '510500');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (23, 264, '南充', '817', '511300', '511300');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (26, 296, '安顺', '853', '520400', '520400');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (27, 310, '呼伦贝尔', '470', '150700', '150700');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (28, 318, '临汾', '357', '141000', '141000');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (2, 17, '大兴安岭', '457', '232700', '232700');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (1, 1, '杭州', '571', '330100', '330100');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (1, 2, '金华', '579', '330700', '330700');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (1, 3, '丽水', '578', '331100', '331100');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (1, 4, '衢州', '570', '330800', '330800');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (1, 5, '绍兴', '575', '330600', '330600');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (1, 6, '宁波', '574', '330200', '330200');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (1, 7, '舟山', '580', '330900', '330900');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (1, 8, '台州', '576', '331000', '331000');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (1, 9, '温州', '577', '330300', '330300');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (1, 10, '嘉兴', '573', '330400', '330400');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (1, 11, '湖州', '572', '330500', '330500');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (2, 12, '哈尔滨', '451', '230100', '230100');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (2, 13, '大庆', '459', '230600', '230600');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (2, 14, '伊春', '458', '230700', '230700');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (2, 15, '绥化', '455', '231200', '231200');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (2, 16, '齐齐哈尔', '452', '230200', '230200');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (2, 18, '黑河', '456', '231100', '231100');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (2, 19, '佳木斯', '454', '230800', '230800');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (2, 20, '牡丹江', '453', '231000', '231000');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (2, 21, '七台河', '464', '230900', '230900');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (2, 22, '鸡西', '467', '230300', '230300');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (2, 23, '鹤岗', '468', '230400', '230400');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (2, 24, '双鸭山', '469', '230500', '230500');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (3, 25, '广州', '20', '440100', '440100');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (3, 30, '韶关', '751', '440200', '440200');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (3, 31, '清远', '763', '441800', '441800');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (3, 32, '肇庆', '758', '441200', '441200');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (3, 33, '云浮', '766', '445300', '445300');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (3, 37, '佛山', '757', '440600', '440600');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (3, 42, '中山', '760', '442000', '442000');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (3, 43, '珠海', '756', '440400', '440400');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (3, 44, '湛江', '759', '440800', '440800');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (3, 46, '惠州', '752', '441300', '441300');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (3, 47, '河源', '762', '441600', '441600');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (3, 48, '汕尾', '660', '441500', '441500');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (3, 53, '潮州', '768', '445100', '445100');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (4, 56, '天津', '22', '120000', '120000');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (5, 57, '福州', '591', '350100', '350100');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (5, 58, '宁德', '593', '350900', '350900');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (5, 59, '南平', '599', '350700', '350700');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (5, 60, '莆田', '594', '350300', '350300');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (5, 61, '泉州', '595', '350500', '350500');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (5, 62, '厦门', '592', '350200', '350200');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (5, 63, '龙岩', '597', '350800', '350800');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (5, 64, '三明', '598', '350400', '350400');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (5, 65, '漳州', '596', '350600', '350600');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (6, 66, '合肥', '551', '340100', '340100');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (6, 67, '巢湖', '565', '341400', '341400');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (6, 68, '六安', '564', '341500', '341500');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (6, 69, '淮南', '554', '340400', '340400');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (6, 70, '蚌埠', '552', '340300', '340300');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (6, 71, '滁州', '550', '341100', '341100');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (6, 72, '芜湖', '553', '340200', '340200');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (6, 73, '马鞍山', '555', '340500', '340500');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (6, 74, '安庆', '556', '340800', '340800');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (6, 75, '池州', '566', '341700', '341700');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (6, 76, '铜陵', '562', '340700', '340700');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (6, 77, '黄山', '559', '341000', '341000');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (6, 78, '宣城', '563', '341800', '341800');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (6, 79, '阜阳', '558', '341200', '341200');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (6, 401, '亳州', '558', '341600', '341600');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (6, 80, '宿州', '557', '341300', '341300');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (6, 81, '淮北', '561', '340600', '340600');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (7, 82, '南京', '25', '320100', '320100');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (7, 83, '扬州', '514', '321000', '321000');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (7, 84, '泰州', '523', '321200', '321200');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (7, 85, '淮安', '517', '320800', '320800');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (7, 86, '宿迁', '527', '321300', '321300');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (7, 87, '盐城', '515', '320900', '320900');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (7, 88, '连云港', '518', '320700', '320700');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (7, 89, '徐州', '516', '320300', '320300');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (7, 90, '无锡', '510', '320200', '320200');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (7, 91, '镇江', '511', '321100', '321100');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (7, 92, '常州', '519', '320400', '320400');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (7, 93, '苏州', '512', '320500', '320500');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (7, 95, '南通', '513', '320600', '320600');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (8, 96, '石家庄', '311', '130100', '130100');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (8, 97, '保定', '312', '130600', '130600');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (8, 98, '邯郸', '310', '130400', '130400');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (8, 99, '衡水', '318', '131100', '131100');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (8, 100, '邢台', '319', '130500', '130500');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (8, 101, '唐山', '315', '130200', '130200');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (8, 102, '张家口', '313', '130700', '130700');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (8, 103, '承德', '314', '130800', '130800');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (8, 104, '秦皇岛', '335', '130300', '130300');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (8, 105, '沧州', '317', '130900', '130900');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (8, 106, '廊坊', '316', '131000', '131000');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (9, 107, '北京', '10', '110000', '110000');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (10, 108, '南昌', '791', '360100', '360100');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (10, 109, '九江', '792', '360400', '360400');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (10, 110, '景德镇', '798', '360200', '360200');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (10, 111, '上饶', '793', '361100', '361100');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (10, 112, '鹰潭', '701', '360600', '360600');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (10, 113, '抚州', '794', '361000', '361000');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (10, 114, '宜春', '795', '360900', '360900');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (10, 115, '新余', '790', '360500', '360500');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (10, 116, '萍乡', '799', '360300', '360300');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (10, 117, '吉安', '796', '360800', '360800');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (11, 120, '宜昌', '717', '420500', '420500');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (11, 121, '恩施', '718', '422800', '422800');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (11, 122, '荆州', '716', '421000', '421000');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (11, 123, '江汉', '728', '420100', '420103');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (11, 124, '荆门', '724', '420800', '420800');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (11, 125, '黄石', '714', '420200', '420200');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (11, 129, '襄樊', '710', '420600', '420600');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (11, 130, '随州', '722', '421300', '421300');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (11, 131, '十堰', '719', '420300', '420300');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (11, 132, '孝感', '712', '420900', '420900');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (12, 134, '三亚', '898', '460200', '460200');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (12, 135, '儋州', '898', '460200', '469003');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (13, 136, '西安', '29', '610100', '610100');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (13, 138, '榆林', '912', '610800', '610800');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (13, 139, '渭南', '913', '610500', '610500');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (13, 140, '商洛', '914', '611000', '611000');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (13, 142, '汉中', '916', '610700', '610700');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (13, 143, '铜川', '919', '610200', '610200');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (13, 144, '咸阳', '910', '610400', '610400');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (15, 147, '沈阳', '24', '210100', '210100');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (15, 148, '铁岭', '410', '211200', '211200');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (15, 149, '抚顺', '413', '210400', '210400');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (15, 150, '丹东', '415', '210600', '210600');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (15, 152, '阜新', '418', '210900', '210900');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (15, 153, '朝阳', '421', '211300', '211300');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (15, 154, '葫芦岛', '429', '211400', '211400');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (15, 155, '本溪', '414', '210500', '210500');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (15, 156, '鞍山', '412', '210300', '210300');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (15, 157, '盘锦', '427', '211100', '211100');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (15, 159, '营口', '417', '210800', '210800');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (15, 160, '大连', '411', '210200', '210200');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (16, 161, '济南', '531', '370100', '370100');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (16, 162, '德州', '534', '371400', '371400');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (16, 163, '聊城', '635', '371500', '371500');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (16, 165, '淄博', '533', '370300', '370300');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (16, 166, '烟台', '535', '370600', '370600');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (16, 167, '威海', '631', '371000', '371000');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (16, 168, '潍坊', '536', '370700', '370700');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (16, 169, '滨州', '543', '371600', '371600');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (16, 170, '东营', '546', '370500', '370500');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (16, 172, '莱芜', '634', '371200', '371200');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (16, 173, '泰安', '538', '370900', '370900');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (16, 174, '菏泽', '530', '371700', '371700');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (16, 175, '临沂', '539', '371300', '371300');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (16, 176, '枣庄', '632', '370400', '370400');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (16, 177, '日照', '633', '371100', '371100');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (17, 178, '南宁', '771', '450100', '450100');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (17, 180, '柳州', '772', '450200', '450200');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (17, 181, '桂林', '773', '450300', '450300');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (17, 182, '梧州', '774', '450400', '450400');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (17, 183, '河池', '778', '451200', '451200');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (17, 184, '玉林', '775', '450800', '450900');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (17, 187, '防城港', '770', '450600', '450600');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (17, 403, '贵港', '775', '450800', '450800');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (17, 405, '崇左', '771', '450100', '451400');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (18, 188, '长沙', '731', '430100', '430100');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (18, 189, '湘潭', '732', '430300', '430300');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (18, 192, '岳阳', '730', '430600', '430600');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (18, 193, '张家界', '744', '430800', '430800');

insert into DGN_BSCDATA_CITY (PRO_ID, CITY_ID, CITY_NAME, CITY_QUHAO, CITY_ZIP, CITY_CODE)
values (18, 196, '郴州', '735', '431000', '431000');

commit ;





