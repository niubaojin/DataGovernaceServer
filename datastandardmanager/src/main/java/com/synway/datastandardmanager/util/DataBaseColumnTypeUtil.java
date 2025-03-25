package com.synway.datastandardmanager.util;



import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 存储数据库类型的 目前有ODPS/ADS
 */
public class DataBaseColumnTypeUtil {
    private static final  Map<String,String> ODPS_COLUMN = new HashMap<>();
    private static final  Map<String,String> ADS_COLUMN = new HashMap<>();
    private static final  Map<String,String> ORACLE_COLUMN = new HashMap<>();
    private static final  Map<String,String> MYSQL_COLUMN = new HashMap<>();
    private static final  Map<String,String> HIVE_COLUMN = new HashMap<>();
    private static final  Map<String,String> PRESTO_COLUMN = new HashMap<>();
    private static final  Map<String,String> DATAHUB_COLUMN = new HashMap<>();
    private static final  Map<String,String> CLICKHOUSE_COLUMN = new HashMap<>();

    static {
        ODPS_COLUMN.put("bigint","bigint");
        ODPS_COLUMN.put("string","string");
        ODPS_COLUMN.put("double","double");
        ODPS_COLUMN.put("boolean","boolean");
        ODPS_COLUMN.put("datetime","datetime");
        ODPS_COLUMN.put("decimal","decimal");

        ADS_COLUMN.put("bigint","bigint");
        ADS_COLUMN.put("boolean","boolean");
        ADS_COLUMN.put("tinyint","tinyint");
        ADS_COLUMN.put("smallint","smallint");
        ADS_COLUMN.put("int","int");
        ADS_COLUMN.put("double","double");
        ADS_COLUMN.put("float","float");
        ADS_COLUMN.put("varchar","varchar");
        ADS_COLUMN.put("date","date");
        ADS_COLUMN.put("timestamp","timestamp");
        ADS_COLUMN.put("multivalue","multivalue");

        //  oracle的所有数据库类型
        ORACLE_COLUMN.put("varchar2","varchar2");
        ORACLE_COLUMN.put("number","number");
        ORACLE_COLUMN.put("float","float");
        ORACLE_COLUMN.put("date","date");
        ORACLE_COLUMN.put("blob","blob");
//        ORACLE_COLUMN.put("nvarchar2","nvarchar2");
//        ORACLE_COLUMN.put("raw","raw");
//        ORACLE_COLUMN.put("char","char");
//        ORACLE_COLUMN.put("nchar","nchar");
//        ORACLE_COLUMN.put("integer","integer");
//        ORACLE_COLUMN.put("timestamp","timestamp");
//        ORACLE_COLUMN.put("datetime","datetime");
//        ORACLE_COLUMN.put("boolean","boolean");

        //  mysql的所有数据库类型
        MYSQL_COLUMN.put("TINYINT","TINYINT");
        MYSQL_COLUMN.put("SMALLINT","SMALLINT");
        MYSQL_COLUMN.put("MEDIUMINT","MEDIUMINT");
        MYSQL_COLUMN.put("INTEGER","INTEGER");
        MYSQL_COLUMN.put("BIGINT","BIGINT");
        MYSQL_COLUMN.put("DECIMAL","DECIMAL");
        MYSQL_COLUMN.put("FLOAT","FLOAT");
        MYSQL_COLUMN.put("DOUBLE","DOUBLE");
        MYSQL_COLUMN.put("DATE","DATE");
        MYSQL_COLUMN.put("TIME","TIME");
        MYSQL_COLUMN.put("YEAR","YEAR");
        MYSQL_COLUMN.put("DATETIME","DATETIME");
        MYSQL_COLUMN.put("TIMESTAMP","TIMESTAMP");
        MYSQL_COLUMN.put("CHAR","CHAR");
        MYSQL_COLUMN.put("VARCHAR","VARCHAR");
        MYSQL_COLUMN.put("TINYTEXT","TINYTEXT");
        MYSQL_COLUMN.put("TEXT","TEXT");
        MYSQL_COLUMN.put("MEDIUMTEXT","MEDIUMTEXT");
        MYSQL_COLUMN.put("LONGTEXT","LONGTEXT");
        MYSQL_COLUMN.put("ENUM","ENUM");
        MYSQL_COLUMN.put("BINARY","BINARY");
        MYSQL_COLUMN.put("VARBINARY","VARBINARY");
        MYSQL_COLUMN.put("BLOB","BLOB");
        MYSQL_COLUMN.put("BOOLEAN","BOOLEAN");

        // hive的所有数据库类型
        HIVE_COLUMN.put("tinyint","tinyint");
        HIVE_COLUMN.put("smallint","smallint");
        HIVE_COLUMN.put("int","int");
        HIVE_COLUMN.put("bigint","bigint");
        HIVE_COLUMN.put("boolean","boolean");
        HIVE_COLUMN.put("float","float");
        HIVE_COLUMN.put("double","double");
        HIVE_COLUMN.put("string","string");

        // presto （hbase、es，同ads的hc）
        PRESTO_COLUMN.put("boolean","boolean");
        PRESTO_COLUMN.put("bigint","bigint");
        PRESTO_COLUMN.put("double","double");
        PRESTO_COLUMN.put("varchar","varchar");
        PRESTO_COLUMN.put("varbinary","varbinary");
        PRESTO_COLUMN.put("json","json");
        // 日历日期（年，月，日）
        PRESTO_COLUMN.put("date","date");
        // 一天中的时间
        PRESTO_COLUMN.put("time","time");
        // 包括日期和时间
        PRESTO_COLUMN.put("timestamp","timestamp");

        // DATAHUB_COLUMN
        DATAHUB_COLUMN.put("bigint","bigint");
        DATAHUB_COLUMN.put("double","double");
        DATAHUB_COLUMN.put("boolean","boolean");
        DATAHUB_COLUMN.put("timestamp","timestamp");
        DATAHUB_COLUMN.put("string","string");

        // CLICKHOUSE_COLUMN
        CLICKHOUSE_COLUMN.put("Int8","Int8");
        CLICKHOUSE_COLUMN.put("Int16","Int16");
        CLICKHOUSE_COLUMN.put("Int32","Int32");
        CLICKHOUSE_COLUMN.put("Int64","Int64");
        CLICKHOUSE_COLUMN.put("UInt8","UInt8");
        CLICKHOUSE_COLUMN.put("UInt16","UInt16");
        CLICKHOUSE_COLUMN.put("UInt32","UInt32");
        CLICKHOUSE_COLUMN.put("UInt64","UInt64");
        CLICKHOUSE_COLUMN.put("Float32","Float32");
        CLICKHOUSE_COLUMN.put("Float64","Float64");
        CLICKHOUSE_COLUMN.put("String","String");
        CLICKHOUSE_COLUMN.put("Date","Date");
        CLICKHOUSE_COLUMN.put("DateTime","DateTime");
    }

    public static Map<String,String> getFieldTypeMap(String dataBaseType)throws Exception{
        if("ODPS".equalsIgnoreCase(dataBaseType)){
            return ODPS_COLUMN;
        }else if("ADS".equalsIgnoreCase(dataBaseType)){
            return ADS_COLUMN;
        }else if("ORACLE".equalsIgnoreCase(dataBaseType)){
            return ORACLE_COLUMN;
        } else if("MYSQL".equalsIgnoreCase(dataBaseType)){
            return MYSQL_COLUMN;
        } else if(StringUtils.containsIgnoreCase(dataBaseType,"Hive")){
            return HIVE_COLUMN;
        }else if(StringUtils.containsIgnoreCase(dataBaseType,"HBase")){
            return PRESTO_COLUMN;
        } else if("DATAHUB".equalsIgnoreCase(dataBaseType)){
            return DATAHUB_COLUMN;
        }else if("CLICKHOUSE".equalsIgnoreCase(dataBaseType)){
            return CLICKHOUSE_COLUMN;
        }else{
            throw new NullPointerException("没有找到对应类型名【"+dataBaseType+"】的集合");
        }
    }
}
