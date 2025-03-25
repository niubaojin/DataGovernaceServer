package com.synway.datastandardmanager.constant;

import java.util.Arrays;
import java.util.List;

/**
 * 公共变量
 * @author wangdongwei
 */
public class Common {
    // 数据库类型 用于
    public static final String ORACLE = "ORACLE";
    public static final String DAMENG = "DAMENG";
    public static final String HAILIANG = "HAILIANG";
    public static final String KING_BASE_ES = "KINGBASEES";
    public static final String SHEN_TONG = "SHENTONG";
    public static final String KINGBASE = "kingBaseES";
    public static final String POSTGRESQL = "POSTGRESQL";

    public static final String ALI_YUN ="aliyun";
    public static final String HUA_WEI_YUN = "huaweiyun";

    // 数据仓库接口地址
    public static final String DATARESOURCE_BASEURL="http://dataresource/dataresource/api";

    // 数据运维接口地址
    public static final String DATAOPERATION_BASEURL="http://dataoperations/dataoperations";
    // 数据资产接口地址
    public static final String PROPERTY_BASEURL="http://property/property";
    // 主框架接口地址
    public static final String DATAGOVERNANCE_BASEURL="http://datagovernance/datagovernance";

    /**
     * 数据组织分类
     */
    public static final String DATA_ORGANIZATION_CLASSIFY = "dataOrganizationClassify";

    //数据组织分类主码表值
    public static final String DATA_ORGANIZATION_CODE = "JZCODEGASJZZFL";

    /**
     *数据来源分类
     */
    public static final String DATA_SOURCE_CLASSIFY = "dataSourceClassify";

    /**
     *数据来源分类码表值
     */
    public static final String DATA_SOURCE_CODE = "GACODE000404";

    /**
     *数据标签分类
     */
    public static final String DATA_LABEL_CLASSIFY = "dataLabelClassify";

    /**
     *数据要素分类
     */
    public static final String DATA_ELEMENT_CLASSIFY = "dataElementClassify";

    public static final String CREATED_TABLES = "createdTables";

    public static final String DESC = "desc";
    public static final String ASC = "asc";

    public static final String QI_YONG = "启用";
    public static final String WEI_ZHI = "未知";

    public static final String YI_FA_BU ="已发布";
    public static final String WEI_FA_BU ="未发布";

    // 数据库类型
    public static final String ODPS = "odps";
    public static final String ADS = "ads";
    public static final String HBASECDH = "hbase-cdh";
    public static final String HBASEHUAWEI = "hbase-huawei";
    public static final String HIVECDH = "hive-cdh";
    public static final String HIVEHUAWEI = "hive-huawei";
    public static final String ES = "elasticsearch";
    public static final String CLICKHOUSE = "clickhouse";
    public static final String LIBRA = "libra";
    public static final String TRS = "TRS";
    public static final String DATAHUB="datahub";
    public static final String KAFKA="kafka";
    public static final String MQ="mq";
    public static final String REDIS="redis";
    public static final String GBASE="gbase";
    public static final String ADB="adb";
    public static final String FTP="ftp";
    public static final String ORACLETYPE="oracle";
    public static final String HC = "hc";
    public static final String HP = "hp";

    public static final String DATA_PLAT_FORM_TYPE="dataPlatFormType";

    public static final String TABLE_ID = "tableId";
    public static final String SYS_CODE = "sysCode";
    public static final String TARGET_COUNT = "targetCount";

    public static final String SELECT = "select";

    public static final String ROOT_URL= "distinguishWhatPlatformByLocalWareHoese";

    public static final String CACHE_ALL_LOCAL_TABLE="findAllLocalTableInformation";

    public static final String CACHE_LOCAL_TABLE_INFORMATION="findAllTableImformation";

    public static final String COMMON_ID="DQ00001";

    public static final String DQ="dq";

    public static final String STATUS="status";

    public static final String FIELDCLASS="fieldClass";

    public static final String SECURITYLEVEL="securityLevel";

    public static final String STORETYPE="storeType";

    public static final String PROJECTNAME="projectName";

    public static final String CREATER="creater";

    public static final String RESNAME="dataid";

    public static final String CAPTURE_TIME="capture_time";

    public static final String DATE_FUNCTION="dateFunction";
    public static final String COLUMN="column";

    public static final String MESSAGE="message";
    public static final String DATA="data";
    public static final String REQUEST_UUID="requestUuid";

    public static final List<String> DATA_TYPE_LIST=Arrays.asList(
            "ODPS",
            "ADS",
            "HIVE-CDH",
            "HIVE-HUAWEI",
            "CLICKHOUSE",
            "ORACLE");

}
