package com.synway.datastandardmanager.constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * 公共变量
 *
 * @author nbj
 */
@Component
public class Common {

    @Autowired
    public Common(Environment env) {
        this.DATA_ORGANIZATION_CODE = env.getProperty("sjzzflCodeId");
    }

    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>数据库类型>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
    public static final String ORACLE = "oracle";
    public static final String DAMENG = "dm";
    public static final String KINGBASE = "kingBaseES";
    public static final String VASTBASE = "vastbase";
    public static final String HAILIANG = "hailiang";
    public static final String POSTGRESQL = "postgresql";
    public static final String POLAR_DB = "polardb";

    //未适配
    public static final String MYSQL = "mysql";
    public static final String HIGHGO = "highgo";
    public static final String GAUSS = "gauss";

    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>数据库类型>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
    public static final String ALI_YUN = "aliyun";
    public static final String HUA_WEI_YUN = "huaweiyun";
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
    public static final String DATAHUB = "datahub";
    public static final String KAFKA = "kafka";
    public static final String REDIS = "redis";
    public static final String GBASE = "gbase";
    public static final String ADB = "adb";
    public static final String FTP = "ftp";
    public static final String DATA_PLAT_FORM_TYPE = "dataPlatFormType";

    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>数据元过滤值>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
    public static final String STATUS = "status";
    public static final String FIELDCLASS = "fieldClass";
    public static final String SECURITYLEVEL = "securityLevel";

    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>数据集>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
    //数据组织分类
    public static final String DATA_ORGANIZATION_CLASSIFY = "dataOrganizationClassify";
    //数据来源分类
    public static final String DATA_SOURCE_CLASSIFY = "dataSourceClassify";
    //数据组织分类主码表值
    public static String DATA_ORGANIZATION_CODE = "JZCODEGASJZZFL";
    //数据来源分类码表值
    public static final String DATA_SOURCE_CODE = "GACODE000404";
    //数据标签分类
    public static final String DATA_LABEL_CLASSIFY = "dataLabelClassify";
    //数据要素分类
    public static final String DATA_ELEMENT_CLASSIFY = "dataElementClassify";
    public static final String WEI_ZHI = "未知";
    public static final String YI_FA_BU ="已发布";
    public static final String WEI_FA_BU ="未发布";
    public static final String CREATED_TABLES = "createdTables";
    public static final String DESC = "desc";
    public static final String ASC = "asc";

    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>数据库操作>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
    public static final String ADD_SUCCESS = "新增成功";
    public static final String ADD_FAIL = "新增失败";
    public static final String UPDATE_SUCCESS = "更新成功";
    public static final String UPDATE_FAIL = "更新失败";
    public static final String DEL_SUCCESS = "删除成功";
    public static final String DEL_FAIL = "删除失败";
    public static final String NOT_CHANGED = "未做改动";
    public static final String IMPORT_SUCCESS = "导入成功";
    public static final String IMPORT_FAIL = "导入失败";
    public static final String SAVE_SUCCESS = "保存成功";
    public static final String SAVE_FAIL = "保存失败";
    public static final String NULL = "";

    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>枚举类型>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
    public static final String SYNLTEFIELDSTATUS = "SYNLTEFIELDSTATUS";  // 数据元定义状态
    public static final String OBJECT_STATE = "OBJECTSTATE";             // 数据元定义状态，标准表object的ObjectState状态
    public static final String OBJECT_DATATYPE = "OBJECTDATATYPE";       // Object表的dataType对应信息
    public static final String STORETYPE = "STORETYPE";                  // 平台类型
    public static final String DATAPROCESS = "DATAPROCESS";              // 数据处理字段类型
    public static final String DATASTORETYPE = "DATASTORETYPE";          // 数据平台类型
    public static final String MANUFACTURER_NAME = "MANUFACTURER_NAME";  // 厂商名称
    public static final String DETERMINER_ENUM = "DETERMINER_ENUM";      // 限定词定义状态
    public static final String DATASECURITYLEVEL = "DATASECURITYLEVEL";  // 数据安全分级
    public static final String ORGANIZATIONCLASS = "ORGANIZATIONCLASS";  // 数据组织分类
    public static final String DATAELEMENTCODE = "DATAELEMENTCODE";      // 数据要素代码枚举
    public static final String BUILDTABLEFIELD = "BUILDTABLEFIELD";      // 建表信息管理字段类型

    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>nacos服务名>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
    // 数据仓库接口地址：根据数据源id获取数据源信息
    public static final String ds_getResourceById = "http://dataresource/dataresource/api/getResourceById";
    public static final String ds_getDataResourceByisLocal = "http://dataresource/dataresource/api/getDataResourceByisLocal";
    public static final String ds_getTableDetectInfo = "http://dataresource/dataresource/api/getTableDetectInfo";
    public static final String ds_getTableStructure = "http://dataresource/dataresource/api/getTableStructure";
    public static final String ds_getProjectList = "http://dataresource/dataresource/api/getProjectList";
    public static final String ds_getDetectedTables = "http://dataresource/dataresource/api/getDetectedTables";
    public static final String ds_getDataCenterByApproved = "http://dataresource/dataresource/api/getDataCenterByApproved";
    public static final String ds_getDataResourceByCenterId = "http://dataresource/dataresource/api/getDataResourceByCenterId";
    public static final String ds_getTablesIncludeDetectedInfo = "http://dataresource/dataresource/api/getTablesIncludeDetectedInfo";
    public static final String ds_addTableColumn = "http://dataresource/dataresource/api/addTableColumn";
    public static final String ds_createTable = "http://dataresource/dataresource/api/createTable";
    public static final String ds_getDatasetDetectResult = "http://dataresource/dataresource/api/getDatasetDetectResult";

    // 数据运维接口地址
    public static final String DATAOPERATION_BASEURL = "http://dataoperations/dataoperations";

    // 数据资产接口地址
    public static final String PROPERTY_BASEURL = "http://property/property";

    // 主框架接口地址
    public static final String DATAGOVERNANCE_BASEURL = "http://datagovernance/datagovernance";

    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>限定词相关>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
    public static final String COMMON_ID = "DQ00001";
    public static final String DQ = "dq";

    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>接口返回对象/建表相关>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
    public static final String MESSAGE="message";
    public static final String DATA="data";
    public static final String REQUEST_UUID="requestUuid";
    public static final String DATE_FUNCTION="dateFunction";
    public static final String COLUMN="column";

    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>数据库类型列表>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
    public static final List<String> DATA_TYPE_LIST = Arrays.asList(
            "ADS",
            "ADB",
            "ODPS",
            "HIVE-CDH",
            "HIVE-HUAWEI",
            "CLICKHOUSE",
            "ORACLE",
            "DATAHUB");

    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>过滤权限接口>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
    public static final String ROOT_URL = "distinguishWhatPlatformByLocalWareHoese";

    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>end>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/

}
