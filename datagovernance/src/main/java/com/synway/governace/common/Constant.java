package com.synway.governace.common;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author WW
 */
public class Constant {

    // 数据大屏
	public static final String ERROR_NUMBER = "问题数据";
	public static final String GOVERNANCE_FINISHED = "治理完成数据";
	public static final String INCEPT_DATA = "本地接入";
	public static final String DISTRIBUTE_DATA = "总队下发";
	public static final String KEEP_UPDATE_DATA = "在更数据";
	public static final String STOP_UPDATE_DATA = "停更数据";
	public static final String HANDLE_SOURCE_DATA = "源协议数据";

	// 审批中心
	public static final String CHARACTER_COMMA = ",";
	public static final String EXECUTE_STATUS_SUCCESS = "成功";
	public static final String EXECUTE_STATUS_FAIL = "失败";
	public static final String STRING_ONE = "1";
	public static final String STRING_TWO = "2";
	public static final String STRING_THREE = "3";
	public static final String STRING_FOUR = "4";

    /**
     * 非结构化数据库的类型 unstructuredShow
     */
    public static final List<String> UNSTRUCTURED = Collections.singletonList("OSS");

    /**
     *  实时库的表类型  ads\hbase\ck
     */
    public static final List<String> REALTIME_BS = Arrays.asList("ADS","HBASE","CLICKHOUSE","ES");

    /**
     *  实时库的表类型  ODPS\Hive
     */
    public static final List<String> OFFLINE_BS = Arrays.asList("ODPS","HIVE","HDFS");

    /**
     *  省市的名称
     */
    public static final String XINJIANG = "新疆";
    public static final String DIAN_XIN = "电信";
    public static final String YI_DONG = "移动";
    public static final String LIAN_TONG = "联通";
    public static final String DIAN_WEI = "电围";
    public static final String DIAN_CHA = "电查";
    public static final String FEN_GUANG = "分光";
    public static final List<String> LABEL_LIST = Arrays.asList("公安执法","电信","互联网","行业","物联网");
	public static final String ODPS = "odps";
    public static final String ADS = "ads";

}
