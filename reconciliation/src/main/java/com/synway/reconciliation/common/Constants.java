package com.synway.reconciliation.common;

/**
 * 常量
 * @author ym
 */
public class Constants {

    public static final String BILL_FILE_SUFFIX = ".txt";

    public static final String DONE_BILL_FILE_SUFFIX = ".done";

    /**
     * 自定义总队行政区划编码
     */
    public static final String MAIN_DIVISION_CODE = "999999";

    /**
     * 数据库类型：Oracle
     */
    public static final String ORACLE = "ORACLE";
    public static final String POSTGRESQL = "POSTGRESQL";
    public static final String HAILIANG = "HAILIANG";

    /**
     * 数据库类型：达梦
     */
    public static final String DAMENG = "DAMENG";

    /**
     * 数据库类型：金仓
     */
    public static final String KINGBASE = "KINGBASEES";

    /**
     * 字符：.
     */
    public static final String CHARACTER_POINT = ".";

    /**
     * 字符：,
     */
    public static final String CHARACTER_COMMA = ",";

    /**
     * 字符：%
     */
    public static final String CHARACTER_PERCENT = "%";

    /**
     * 字符：3
     */
    public static final String THREE = "3";

    /**
     * 数字：3
     */
    public static final int NUM_THREE = 3;

    public static final int DEFAULT_LIMIT = 100;

    public static final String USER_ID= "userId";
    public static final String USER_NAME = "userName";
    public static final String IS_ADMIN = "isAdmin";
    public static final String SELECT = "select";

    public static final String DATA_PROCESS = "数据处理";

    public static final String FINGERPRINT_TYPE_MD5 = "MD5";
    public static final String FINGERPRINT_TYPE_SHA1 = "SHA1";

    /**
     * 消息中间件类型：rocketmq
     */
    public static final String ROCKETMQ = "rocketmq";

    /**
     * 消息中间件类型：kafka
     */
    public static final String KAFKA = "kafka";

    /**
     * 接收账单类型：api
     */
    public static final String API = "api";

    /**
     * hazelcastLock
     */

    public static final String UPDATE_DS_DETECTED_TABLE = "updateDsDetectedTable";
    public static final String ISSUE_RECON_STATISTICS = "issueReconStatistics";
    public static final String SEND_TASK_INFO = "sendTaskInfo";
    public static final String CONSUMER_TASK_INFO = "consumerTaskInfo";
    public static final String CONSUMER_ISSUE_BILL = "consumerIssueBill";
    public static final String UPDATE_DATA_NAME_CACHE = "updateDataNameCache";

    public static final String SUMMARIZE_BILL = "summarizeBill";
    public static final String DATA_RECON_BY_INSTANCE = "dataReconByInstance";
    public static final String BUILD_INVENTORY_BILL = "buildInventoryBill";
    public static final String BILL_DATA_STATISTICS = "billDataStatistics";
    public static final String BILL_DATA_ANALYSIS = "billDataAnalysis";



    /**
     * 对账数据中文名以及分区
     */
    public static final String SOURCE_TABLE_INFO = "sourceTableInfo";
    public static final String ISSUE_DAY_STATISTICS = "issueDayStatistics";
    public static final String ISSUE_BASE_TIME_MAP = "issueBaseTimeMap";
    public static final String TASK_INFO_MAP = "taskInfoMap";
    public static final String TASK_INFO_TAG_MAP = "taskInfoTagMap";
    public static final String BILL_RELATE_INFO = "billRelateInfo";
    public static final String ADMINISTRATIVE_DIVISION_CODE = "AdministrativeDivisionCode";


    /**
     * 六种账单类型 对应文件夹名
     */
    public static final String ACCESS_PROVIDER = "access_provider";
    public static final String ACCESS_ACCEPTOR = "access_acceptor";
    public static final String STANDARD_PROVIDER = "standard_provider";
    public static final String STANDARD_ACCEPTOR = "standard_acceptor";
    public static final String STORAGE_PROVIDER = "storage_provider";
    public static final String STORAGE_ACCEPTOR = "storage_acceptor";

    /**
     * 六张账单类型 对应beanName
     */
    public static final String ACCESS_PROVIDER_WRITER = ACCESS_PROVIDER + "_writer";
    public static final String ACCESS_ACCEPTOR_WRITER = ACCESS_ACCEPTOR + "_writer";
    public static final String STANDARD_PROVIDER_WRITER = STANDARD_PROVIDER + "_writer";
    public static final String STANDARD_ACCEPTOR_WRITER = STANDARD_ACCEPTOR + "_writer";
    public static final String STORAGE_PROVIDER_WRITER = STORAGE_PROVIDER + "_writer";
    public static final String STORAGE_ACCEPTOR_WRITER = STORAGE_ACCEPTOR + "_writer";

    /**
     * API接收账单定时任务hazelLock
     */
    public static final String CONSUMER_BILL_SCHEDULE = "consumerBillSchedule";
    public static final String HANDEL_HISTORY_BILL_DATA = "handelHistoryBillData";
    public static final String CLEAN_BILL_FILE = "cleanBillFile";
}
