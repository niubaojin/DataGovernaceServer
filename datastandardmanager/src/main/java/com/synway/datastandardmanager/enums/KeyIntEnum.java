package com.synway.datastandardmanager.enums;

import com.synway.datastandardmanager.constants.Common;

/**
 * 公共枚举类：key类型为Integer
 *
 * @author nbj
 * @date 2025年6月26日14:03:20
 */
public enum KeyIntEnum {

    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>标准表object，ObjectStateType标准状态>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
    FORMAL_USE(1, "已发布", Common.OBJECT_STATE),
    TEMP_USE(0, "未发布", Common.OBJECT_STATE),
    ABANDON(-1, "停用", Common.OBJECT_STATE),

    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Object表的dataType对应信息>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
    SOURCE_DATA(0, "源数据", Common.OBJECT_DATATYPE),
    BASE_DATA(1, "基础数据", Common.OBJECT_DATATYPE),
    REPOSITORY(2, "资源数据(知识库)", Common.OBJECT_DATATYPE),
    BEHAVIOR_LOG_LIB(3, "资源数据（行为日志库）", Common.OBJECT_DATATYPE),
    CUSTOM_TABLE_TYPE(4, "自定义表类型", Common.OBJECT_DATATYPE),
    SPECIAL_LIB(5, "专题库", Common.OBJECT_DATATYPE),
    LABEL_DATA(901, "标签数据", Common.OBJECT_DATATYPE),
    NEARLY_LINE_QUERY_DATA(902, "近线查询数据", Common.OBJECT_DATATYPE),

    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>厂商名称>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
    ALL(0, "全部", Common.MANUFACTURER_NAME),      //Source_ALL
    PT(1, "普天", Common.MANUFACTURER_NAME),       //Source_PT
    HZ(2, "汇智", Common.MANUFACTURER_NAME),       //Source_HZ
    SS(3, "三所", Common.MANUFACTURER_NAME),       //Source_SS
    FH(4, "烽火", Common.MANUFACTURER_NAME),       //Source_FH
    SH(5, "三汇", Common.MANUFACTURER_NAME),       //Source_SH
    RA(6, "锐安", Common.MANUFACTURER_NAME),       //Source_RA
    BZX(7, "部中心", Common.MANUFACTURER_NAME),     //Source_BZX
    BZXMQ(8, "部中心mq", Common.MANUFACTURER_NAME), //Source_BZXMQ
    HK(9, "海康", Common.MANUFACTURER_NAME),        //Source_HK

    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>平台类型>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
    HABSE(0, "hbase", Common.STORETYPE),
    ORACLE(1, "oracle", Common.STORETYPE),
    SQL(2, "sql", Common.STORETYPE),
    ADS(3, "ads", Common.STORETYPE),

    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>数据处理字段类型>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
    STRING(0 ,"2", Common.DATAPROCESS),         //字符型(string)
    NUMERIC(1,"0", Common.DATAPROCESS),         //数值型(numeric)
    DATE(2 , "3", Common.DATAPROCESS),
    DATETIME(3 ,"4", Common.DATAPROCESS),
    FLOAT(8,"1", Common.DATAPROCESS),
    DOUBLE(9,"7", Common.DATAPROCESS),

    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>建表信息管理字段类型>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
    INTEGER(0,"integer", Common.BUILDTABLEFIELD),
    FLOAT1(1,"float", Common.BUILDTABLEFIELD),
    STRING1(2,"string", Common.BUILDTABLEFIELD),
    DATE1(3,"date", Common.BUILDTABLEFIELD),
    DATETIME1(4,"datetime", Common.BUILDTABLEFIELD),
    BLOB(5,"blob", Common.BUILDTABLEFIELD),
    LONG(6,"long", Common.BUILDTABLEFIELD),
    DOUBLE1(7,"double", Common.BUILDTABLEFIELD),

    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>数据平台类型对应的数字>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
    ODPS(1, "odps", Common.DATASTORETYPE),
    ADS_HC(2, "ads-hc", Common.DATASTORETYPE),
    ADS_HP(3, "ads-hp", Common.DATASTORETYPE),
    HBASEHUAWEI(4, "hbase-huawei", Common.DATASTORETYPE),
    HIVEHUAWEI(5, "hive-huawei", Common.DATASTORETYPE),
    ES(6, "elasticsearch", Common.DATASTORETYPE),
    CLICKHOUSE(7, "clickhouse", Common.DATASTORETYPE),
    LIBRA(8, "libra", Common.DATASTORETYPE),
    TRS(9, "TRS", Common.DATASTORETYPE),
    ORACLE1(10, "ORACLE", Common.DATASTORETYPE),
    HBASECDH(11, "hbase-cdh", Common.DATASTORETYPE),
    HIVECDH(12, "hive-cdh", Common.DATASTORETYPE),
    DATAHUB(13, "datahub", Common.DATASTORETYPE),
    KAFKA(14, "kafka", Common.DATASTORETYPE),
    MQ(15, "mq", Common.DATASTORETYPE),
    REDIS(16, "redis", Common.DATASTORETYPE),
    GBASE(17, "gbase", Common.DATASTORETYPE),
    ADB_HC(18, "adb-hc", Common.DATASTORETYPE),
    FTP(19, "ftp", Common.DATASTORETYPE),
    ADB_HP(20, "adb-hp", Common.DATASTORETYPE);

    /*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>end>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/


    private Integer key;
    private String value;
    private String type;

    KeyIntEnum(Integer key, String value, String type) {
        this.key = key;
        this.value = value;
        this.type = type;
    }

    public static Integer getKeyByNameAndType(String value, String type) {
        Integer returnInt = null;
        for (KeyIntEnum exp : KeyIntEnum.class.getEnumConstants()) {
            if (exp.getValue().equalsIgnoreCase(value) && exp.getType().equals(type)) {
                returnInt = exp.getKey();
                break;
            }
        }
        return returnInt;
    }

    public static String getValueByKeyAndType(Integer key, String type) {
        String returnStr = null;
        for (KeyIntEnum exp : KeyIntEnum.class.getEnumConstants()) {
            if (exp.getKey().equals(key) && exp.getType().equals(type)) {
                returnStr = exp.getValue();
                break;
            }
        }
        return returnStr;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(String id) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
