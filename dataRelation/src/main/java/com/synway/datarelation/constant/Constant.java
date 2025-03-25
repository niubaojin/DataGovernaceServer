package com.synway.datarelation.constant;

import java.util.Arrays;
import java.util.List;

/**
 * @author wangdongwei
 */
public class Constant {
    // 节点类型数据
    public static final String STANDARD = "datastandard";
    public static final String ACCESS = "dataaccess";
    public static final String DATAWARE = "dataware";
    public static final String DATAPROCESS = "dataprocess";
    public static final String OPERATINGSYSTEM = "operatingsystem";

    public static final String LEFT = "left";
    public static final String RIGHT = "RIGHT";
    public static final String MAIN = "main";

    public static final Integer NODE_SHOW_NUM = 3;

    public static final String ODPS = "odps";
    public static final String HIVE = "hive";
    public static final String ADS = "ads";


    public static final String PARENT_PROCESS_SPLIT = "parent@@";
    public static final String CHILD_PROCESS_SPLIT = "child@@";

    public static final String ALI_YUN ="aliyun";
    public static final String HUA_WEI_YUN = "huaweiyun";

    public static final String DF_WORK = "dfwork";
    public static final String DATA_WORK2 = "datawork2";
    public static final String DATA_WORK3 = "datawork3";

    /**
     *  预处理的平台 目前只需要这些
     */
    public static final List<String> DATA_BASE_TYPE_LIST = Arrays.asList(new String[]{"ADS-HC","ADS-HP","ODPS","HIVE","HBASE"});

    /**
     * 数据流向
     */
    public static final String DATA_FLOW ="dataFlow";
    /**
     * 加工流向
     */
    public static final String PROCESS_FLOW ="processFlow";

    public static final String ALL_FILTER ="全部";

    public static final List<String> DATA_TYPE_LIST = Arrays.asList("ads","clickhouse","odps","hive","hbase","hive-cdh","hive-huawei");



}
