package com.synway.property.common;

/**
 * @author majia
 */
public class Common {

    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>分类>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
    //数据组织分类
    public static final String DATA_ORGANIZATION_CLASSIFY = "dataOrganizationClassify";
    //数据来源分类
    public static final String DATA_SOURCE_CLASSIFY = "dataSourceClassify";
    //数据资源分类
    public static final String DATA_RESOURCE_CLASSIFY = "dataResourceClassify";
    //数据资源标签
//    public static final String DATA_LABELS_CLASSIFY = "dataLabelClassify";

    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>统计单位>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
    public static final String RECORDS_UNIT = "亿条";
    public static final String STORAGE_UNIT = "GB";

    public static final String MONITOR_OK = "已控";
    public static final String MONITOR_NO = "待控";

    //开关值
    public static final String SWITCH_ON = "1";


    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>数据对账相关接口地址>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
    public static final String re_getAbnormalReconciliationNum = "http://reconciliation/reconciliation/getAbnormalReconciliationNum";


    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>数据仓库相关接口地址>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
    //获取所有数据源
    public static final String dr_getAllResources = "http://dataresource/dataresource/api/getAllResources";
    //获取数据源
    public static final String dr_getDataResourceByisLocal = "http://dataresource/dataresource/api/getDataResourceByisLocal";
    //根据数据源id获取数据源
    public static final String dr_getResourceById = "http://dataresource/dataresource/api/getResourceById";
    //根据数据源id获取项目列表
    public static final String dr_getProjectList = "http://dataresource/dataresource/api/getProjectList";
    //根据数据源id获取表探查信息
    public static final String dr_getTablesIncludeDetectedInfo = "http://dataresource/dataresource/api/getTablesIncludeDetectedInfo";
    //根据数据源id获取分区信息
    public static final String dr_getPartitionInfo = "http://dataresource/dataresource/api/getPartitionInfo";
    //获取本地数据源
    public static final String dr_findResourceForLocalBase = "http://dataresource/dataresource/findResourceForLocalBase";
    //获取样例数据
    public static final String dr_getExampleData = "http://dataresource/dataresource/api/getExampleData";
    //获取表结构信息
    public static final String dr_getTableStructure = "http://dataresource/dataresource/api/getTableStructure";
    //获取表元信息
    public static final String dr_getTableMetaInfo = "http://dataresource/dataresource/api/getTableMetaInfo";
    //获取数据源概览
    public static final String dr_getResourceOverview = "http://dataresource/dataresource/api/getResourceOverview";
    //获取探查表信息
    public static final String dr_getTableDetectInfo = "http://dataresource/dataresource/api/getTableDetectInfo";
    //执行sql
    public static final String dr_excuteSql = "http://dataresource/dataresource/api/excuteSql";


    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>主框架相关接口地址>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
    //获取导航栏状态
    public static final String dgn_getNavStatusByName = "http://datagovernance/datagovernance/navbar/getNavStatusByName";
    //获取列表信息
    public static final String dgn_tableList = "http://datagovernance/datagovernance/dataOperation/tableList";
    //获取项目信息
    public static final String dgn_getProjectName = "http://datagovernance/datagovernance/dataOperation/getProjectName";
    //保存审批信息
    public static final String dgn_saveOrUpdateApprovalInfo = "http://datagovernance/datagovernance/process/saveOrUpdateApprovalInfo";


    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>数据血缘相关接口地址>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
    //获取目标表信息
    public static final String ds_getTargetTables = "http://datarelation/datarelation/api/getTargetTables";
    //获取工作流信息
    public static final String ds_getImpactStatistic = "http://datarelation/datarelation/api/getImpactStatistic";
    //获取表使用热度
    public static final String ds_getTableHot = "http://datarelation/datarelation/api/getTableHot";


    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>数据运维相关接口地址>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
    //发送告警信息
    public static final String do_pushAlarmInfo = "http://dataoperations/dataoperations/pushAlarmInfo";
    //保存操作日志
    public static final String do_saveOperatorLog = "http://dataoperations/dataoperations/saveOperatorLog";


    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>数据库类型>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
    public static final String ORACLE = "oracle";
    public static final String KINGBASE = "kingbasees";
    public static final String DAMENG = "dameng";
    public static final String VASTDATA = "hailiang";
    public static final String POSTGRESQL = "postgresql";
    public static final String POLAR_DB = "polardb";
    public static final String MYSQL = "mysql";
    public static final String HIGHGO = "highgo";
    public static final String GAUSS = "gauss";
    public static final String NULL = "null";

    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>平台类型>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
    public static final String HUAWEI = "huaweiyun";
    public static final String HUAWEICH = "华为";
    public static final String ALI = "aliyun";
    public static final String ALICH = "阿里";

}

