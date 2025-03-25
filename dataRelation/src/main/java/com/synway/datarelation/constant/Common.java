package com.synway.datarelation.constant;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author wangdongwei
 */
public class Common {
    /**
     * 正则表达式 是否包含中文
     */
    public static final Pattern PATTERN_REG = Pattern.compile("[\u4e00-\u9fa5]");

    // 数据库类型 用于
    public static final String ORACLE = "ORACLE";
    public static final String DAMENG = "DAMENG";
    public static final String KING_BASE_ES = "KINGBASEES";
    public static final String ORACLE_CLASS = "oracle.jdbc.driver.OracleDriver";
    public static final String DAMENG_CLASS = "dm.jdbc.driver.DmDriver";

    public static final String SQL = "sql";
    /**
     * 查询系统的组id
     */
    public static final String GROUP_ID = "JZCODE0024";


    /**
     *  排序字段的相关信息
     */
    public static final String SORT_COLUMNS = "onelevelmodule,twolevelmodule,threelevelmodule,";

    public static final String ONE_LEVEL_MODULE = "onelevelmodule";
    public static final String TWO_LEVEL_MODULE = "twolevelmodule";
    public static final String THREE_LEVEL_MODULE = "threelevelmodule";

    public static final String DESC = "desc";
    public static final String ASC = "asc";

    /**
     * 虚线
     */
    public static final String DOTTED_LINE = "dottedLine";

    /**
     * 实线
     */
    public static final String SOLID_LINE = "solidLine";

    /**
     * 这些是分布式锁的相关key值
     */
    public static final String UPDATE_CACHE_DATA = "updateCacheData";
    public static final String DELETE_COLUMN_CACHE = "deleteColumnCache";
    public static final String DELETE_TABLE = "deleteTable";
    public static final String DATA_WORK_TASK_SCHEDULED = "dataWorkTaskScheduled";
    public static final String MODEL_MONITOR_STATISTIC = "modelMonitorStatistic";
    public static final String MODEL_RELATION_GET_ALLNODE = "modelRelationGetAllNode";
    public static final String DELETE_CACHE_COLUMN = "deleteCacheColumn";
    public static final String GET_ALL_TABLE_CLASSIFY = "getAllTableClassify";
    public static final String GET_DAG = "getDag";
    public static final String GET_DATA_WORKS_MONITOR  = "getDataWorksMonitor";
    public static final String  QUERY_LOG ="queryLog";
    public static final String INSERT_TASK_CRON="insertTaskCron";
    public static final String DEL_ALL_NODE_CACHE ="delAllNodeCache";

    /**
     * 以下为分布式缓存中相关map的参数
     */
    /**
     * 数据加工血缘信息
     */
    public static final String DATA_PROCESS_BLOOD = "dataProcessBloodHashMap";
    public static final String APPLICATION_BLOOD = "applicationBloodHashMap";
    public static final String DATA_COLUMN_INFO_PAGE = "dataColumnInfoHashMap";
    public static final String QUERY_BLOODLINE_RELATIONINFO = "queryBloodlineRelationInfoMap";


    /**
     * @author chenfei
     * @date 2024/6/6 10:23
     * @Description 全局数据血缘关系缓存KEY
     */
    public static final String KEY_RELATION_LINK = "relationLinks";

    /**
     * 锁定节点的相关map
     */
    public static final String LOCK_NODE_PAGE = "lockNodePage";


    /**
     * 字段血缘的缓存key值
     */
    public static final String LINEAGE_COLUMN_INFO = "lineageColumnInfoHashMap";


    /**
     * 表分类的相关缓存的key值
     */
    public static final String TABLE_CLASSIFY_MAP = "tableClassifyHashMap";

    /**
     *  导入文件的后缀信息
     */
    public static final String XLSX="xlsx";
    public static final String XLS="xls";

    /**
     * 阿里的平台
     */
    public static final String ODPS="odps";
    /**
     * 华为平台
     */
    public static final String HIVE="hive";
    public static final String HIVE_HUAWEI="hive-huawei";
    //CDH
    public static final String HIVE_CDH="hive-cdh";

    public static final String PROCESS_PLAT_FORM_TYPE="processPlatFormType";
    public static final String DATA_PLAT_FORM_VERSION="dataPlatFormVersion";
    public static final String DATA_PLAT_FORM_TYPE="dataPlatFormType";

    public static final String V3="v3";
    public static final String V2="v2";


    /**
     * 直接/间接
     */
    public static final String DIRECT="直接";
    public static final String IN_DIRECT="间接";



    public static final String KEY_CONN = "@@";


    /**
     * 超时时间的毫秒数  30分钟
     */
    public static final long TIME_OUT = 1800000;


    public static final String ADS = "ads";
    public static final String HBASE = "hbase";


    public static final String GENERATE_FILE = "生成文件";


    public static final String STATUS="status";
    public static final String MESSAGE="message";
    public static final String DATA="data";
    public static final String REQUEST_UUID="requestUuid";


}
