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
    public static final String RECONCILIATION_BASEURL = "http://reconciliation";


    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>数据仓库相关接口地址>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
    //数据仓库地址
    public static final String DATARESOURCE_BASEURL = "http://dataresource";
    public static final String DATARESOURCE_BASEURL_API = "http://dataresource/dataresource/api";


    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>主框架相关接口地址>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
    //主程序地址
    public static final String DATAGOVERNANCE_BASEURL = "http://datagovernance";


    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>数据血缘相关接口地址>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
    //数据血缘
    public static final String DATARELATION_BASEURL = "http://datarelation/datarelation/api";
    //获取表使用热度
    public static final String ds_getTableHot = "http://datarelation/datarelation/api/getTableHot";


    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>数据运维相关接口地址>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
    public static final String DATAOPERATIONS_BASEURL = "http://dataoperations/dataoperations";


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
}

