package com.synway.datastandardmanager.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 字段类型工具类
 */
public class FieldTypeUtil {

    private static final Map<String, String> oracle_odps = new HashMap<>();
    private static final Map<String, String> oracle_ads = new HashMap<>();
    private static final Map<String, String> oracle_mysql = new HashMap<>();
    private static final Map<String, String> oracle_oracle = new HashMap<>();
    private static final Map<String, String> oracle_presto = new HashMap<>();
    private static final Map<String, String> oracle_hive = new HashMap<>();
    private static final Map<String, String> oracle_datahub = new HashMap<>();
    //  所有非正常
    private static final Map<String, String> all_standardize = new HashMap<>();
    private static final Map<String, String> oracle_clickhouse = new HashMap<>();

    /**
     * 初始化类型映射Map
     */
    static {
        /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>odps>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
        oracle_odps.put("integer", "bigint");
        oracle_odps.put("long", "bigint");
        oracle_odps.put("float", "double");
        oracle_odps.put("double", "double");
        oracle_odps.put("string", "string");
        oracle_odps.put("date", "bigint");
        oracle_odps.put("timestamp", "timestamp");
        oracle_odps.put("datetime", "bigint");
        oracle_odps.put("boolean", "boolean");
        oracle_odps.put("tinyint", "tinyint");
        oracle_odps.put("smallint", "smallint");
        oracle_odps.put("int", "int");
        oracle_odps.put("bigint", "bigint");
        oracle_odps.put("varchar", "varchar");
        oracle_odps.put("decimal", "decimal");
        oracle_odps.put("binary", "binary");

        /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>ads>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
        oracle_ads.put("integer", "bigint");
        oracle_ads.put("float", "float");
        oracle_ads.put("string", "varchar");
        oracle_ads.put("date", "bigint");
        oracle_ads.put("timestamp", "timestamp");
        oracle_ads.put("datetime", "bigint");
        oracle_ads.put("boolean", "boolean");
        oracle_ads.put("tinyint", "tinyint");
        oracle_ads.put("smallint", "smallint");
        oracle_ads.put("int", "int");
        oracle_ads.put("bigint", "bigint");
        oracle_ads.put("double", "double");
        oracle_ads.put("varchar", "varchar");
        oracle_ads.put("multivalue", "multivalue");
        oracle_ads.put("blob", "varchar");

        /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>oracle>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
        oracle_oracle.put("integer", "number");
        oracle_oracle.put("float", "float");
        oracle_oracle.put("string", "varchar2");
        oracle_oracle.put("date", "date");
        oracle_oracle.put("datetime", "date");
        oracle_oracle.put("blob", "blob");

        /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>mysql>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
        oracle_mysql.put("", "");
        oracle_mysql.put("", "");

        /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>oracle的相关字段类型数据>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
        all_standardize.put("varchar2", "string");
        all_standardize.put("float", "float");
        all_standardize.put("date", "bigint");
        all_standardize.put("nchar", "string");
        all_standardize.put("nvarchar2", "string");
        all_standardize.put("long", "long");
        all_standardize.put("raw", "integer");
        all_standardize.put("number", "integer");
        all_standardize.put("integer", "integer");
        all_standardize.put("real", "integer");
        // mysql
        all_standardize.put("int", "integer");
        all_standardize.put("smallint", "integer");
        all_standardize.put("tinyint", "integer");
        all_standardize.put("bigint", "long");
        all_standardize.put("mediumint", "integer");
        all_standardize.put("decimal", "float");
        all_standardize.put("numeric", "float");
//        all_standardize.put("float","float");
        all_standardize.put("double", "double");
        all_standardize.put("bit", "long");
        all_standardize.put("date", "date");
        all_standardize.put("datetime", "datetime");
        //  以下两个不一定正确
        all_standardize.put("time", "string");
        all_standardize.put("year", "string");
        all_standardize.put("char", "string");
        all_standardize.put("varchar", "string");
        all_standardize.put("timestamp", "string");
        all_standardize.put("text", "string");
        all_standardize.put("string", "string");
        // odps
        all_standardize.put("longtext", "string");

        /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>presto对应的映射关系>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
        oracle_presto.put("long", "bigint");
        oracle_presto.put("double", "double");
        oracle_presto.put("string", "varchar");
        oracle_presto.put("date", "bigint");
        oracle_presto.put("datetime", "bigint");
        oracle_presto.put("integer", "bigint");
        oracle_presto.put("float", "double");

        /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>hive>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
        oracle_hive.put("long", "bigint");
        oracle_hive.put("double", "double");
        oracle_hive.put("string", "string");
        oracle_hive.put("date", "bigint");
        oracle_hive.put("datetime", "bigint");
        oracle_hive.put("integer", "bigint");
        oracle_hive.put("float", "double");

        /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>datahub>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
        oracle_datahub.put("long", "bigint");
        oracle_datahub.put("double", "double");
        oracle_datahub.put("string", "string");
        oracle_datahub.put("date", "string");
        oracle_datahub.put("datetime", "string");
        oracle_datahub.put("integer", "bigint");
        oracle_datahub.put("float", "double");

        /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>clickhouse>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
        oracle_clickhouse.put("long", "Int64");
        oracle_clickhouse.put("double", "Float64");
        oracle_clickhouse.put("string", "String");
        oracle_clickhouse.put("date", "Date");
        oracle_clickhouse.put("datetime", "DateTime");
        oracle_clickhouse.put("integer", "Int64");
        oracle_clickhouse.put("float", "Float64");
    }

    /**
     * 根据读写库类型返回相应Map映射
     *
     * @param orginDbType  来源库类型
     * @param targetDbType 目标库类型
     */
    public static Map<String, String> getFieldTypeMap(String orginDbType, String targetDbType) throws Exception {
        String name = orginDbType + "_" + targetDbType;
        if ("oracle_odps".equalsIgnoreCase(name)) {
            return oracle_odps;
        } else if ("oracle_ads".equalsIgnoreCase(name) || "oracle_adb".equalsIgnoreCase(name)) {
            return oracle_ads;
        } else if ("all_standardize".equalsIgnoreCase(name)) {
            return all_standardize;
        } else if ("standardize_hbase-cdh".equalsIgnoreCase(name)) {
            return oracle_presto;
        } else if ("standardize_hbase-huawei".equalsIgnoreCase(name)) {
            return oracle_presto;
        } else if ("standardize_hive-cdh".equalsIgnoreCase(name)) {
            return oracle_hive;
        } else if ("standardize_hive-huawei".equalsIgnoreCase(name)) {
            return oracle_hive;
        } else if ("standardize_datahub".equalsIgnoreCase(name)) {
            return oracle_datahub;
        } else if ("standardize_odps".equalsIgnoreCase(name)) {
            return oracle_odps;
        } else if ("standardize_ads".equalsIgnoreCase(name)) {
            return oracle_ads;
        } else if ("standardize_clickhouse".equalsIgnoreCase(name)) {
            return oracle_clickhouse;
        } else if ("oracle_oracle".equalsIgnoreCase(name)) {
            return oracle_oracle;
        } else {
            throw new NullPointerException("没有找到对应类型名【" + name + "】的集合");
        }
    }

}
