package com.synway.datastandardmanager.pojo.buildtable;


import com.synway.datastandardmanager.constant.Common;
import org.apache.commons.lang3.StringUtils;

/**
 * Object_Store_Info表的StoreType对应信息
 * @author obito
 *
 */
public enum ObjectStoreInfoStoreType {

    ODPS("odps"),
    HC("hc"),
    HP("hp"),
    HBASEHUAWEI("hbase-huawei"),
    HIVEHUAWEI("hive-huawei"),
    ES("elasticsearch"),
    CLICKHOUSE("clickhouse"),
    LIBRA("libra"),
    TRS("trs"),
    ORACLE("oracle"),
    HBASECDH("hbase-cdh"),
    HIVECDH("hive-cdh"),
    DATAHUB("dataHub"),
    KAFKA("kafka"),
    MQ("mq"),
    REDIS("redis"),
    GBASE("gbase"),
    ADB_HC("adb-hc"),
    FTP("ftp"),
    ADB_HP("adb-hp"),
    WZ("未知");

    private String value = null;

    ObjectStoreInfoStoreType(String value){
        this.value=value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static String getStoreType(int value){
        if(value==1){
            return ObjectStoreInfoStoreType.ODPS.getValue();
        }else if (value==2) {
            return ObjectStoreInfoStoreType.HC.getValue();
        }else if (value==3) {
            return ObjectStoreInfoStoreType.HP.getValue();
        }else if (value==4) {
            return ObjectStoreInfoStoreType.HBASEHUAWEI.getValue();
        }else if (value==5) {
            return ObjectStoreInfoStoreType.HIVEHUAWEI.getValue();
        }else if (value==6) {
            return ObjectStoreInfoStoreType.ES.getValue();
        }else if (value==7) {
            return ObjectStoreInfoStoreType.CLICKHOUSE.getValue();
        }else if(value==8){
            return ObjectStoreInfoStoreType.LIBRA.getValue();
        }else if(value==9){
            return ObjectStoreInfoStoreType.TRS.getValue();
        }else if(value==10){
            return ObjectStoreInfoStoreType.ORACLE.getValue();
        }else if(value==11){
            return ObjectStoreInfoStoreType.HBASECDH.getValue();
        }else if(value==12){
            return ObjectStoreInfoStoreType.HIVECDH.getValue();
        }else if(value==13){
            return ObjectStoreInfoStoreType.DATAHUB.getValue();
        }else if(value==14){
            return ObjectStoreInfoStoreType.KAFKA.getValue();
        }else if(value==15){
            return ObjectStoreInfoStoreType.MQ.getValue();
        }else if(value==16){
            return ObjectStoreInfoStoreType.REDIS.getValue();
        }else if(value==17){
            return ObjectStoreInfoStoreType.GBASE.getValue();
        }else if(value==18){
            return ObjectStoreInfoStoreType.ADB_HC.getValue();
        }else if(value==19){
            return ObjectStoreInfoStoreType.FTP.getValue();
        }else if(value==20){
            return ObjectStoreInfoStoreType.ADB_HP.getValue();
        }else {
            return ObjectStoreInfoStoreType.WZ.getValue();
        }
    }

    // 根据传入的具体字段中文类型获取数字类型
    public static int getStoreNumType(String value,String projectName){
        if(value.equalsIgnoreCase(ObjectStoreInfoStoreType.ODPS.getValue())){
            return 1;
        }else if (value.equalsIgnoreCase(ObjectStoreInfoStoreType.HC.getValue()) && StringUtils.containsIgnoreCase(projectName, Common.HC)) {
            return 2;
        }else if (value.equalsIgnoreCase(ObjectStoreInfoStoreType.HP.getValue()) && StringUtils.containsIgnoreCase(projectName, Common.HP)) {
            return 3;
        }else if (value.equalsIgnoreCase(ObjectStoreInfoStoreType.HBASEHUAWEI.getValue())) {
            return 4;
        }else if (value.equalsIgnoreCase(ObjectStoreInfoStoreType.HIVEHUAWEI.getValue())) {
            return 5;
        }else if (value.equalsIgnoreCase(ObjectStoreInfoStoreType.ES.getValue())) {
            return 6;
        }else if (value.equalsIgnoreCase(ObjectStoreInfoStoreType.CLICKHOUSE.getValue())) {
            return 7;
        }else if(value.equalsIgnoreCase(ObjectStoreInfoStoreType.LIBRA.getValue())){
            return 8;
        }else if(value.equalsIgnoreCase(ObjectStoreInfoStoreType.TRS.getValue())){
            return 9;
        }else if(value.equalsIgnoreCase(ObjectStoreInfoStoreType.ORACLE.getValue())){
            return 10;
        }else if(value.equalsIgnoreCase(ObjectStoreInfoStoreType.HBASECDH.getValue())){
            return 11;
        }else if(value.equalsIgnoreCase(ObjectStoreInfoStoreType.HIVECDH.getValue())){
            return 12;
        }else if(value.equalsIgnoreCase(ObjectStoreInfoStoreType.DATAHUB.getValue())){
            return 13;
        }else if(value.equalsIgnoreCase(ObjectStoreInfoStoreType.KAFKA.getValue())){
            return 14;
        }else if(value.equalsIgnoreCase(ObjectStoreInfoStoreType.MQ.getValue())){
            return 15;
        }else if(value.equalsIgnoreCase(ObjectStoreInfoStoreType.REDIS.getValue())){
            return 16;
        }else if(value.equalsIgnoreCase(ObjectStoreInfoStoreType.GBASE.getValue())){
            return 17;
        }else if(value.equalsIgnoreCase(ObjectStoreInfoStoreType.ADB_HC.getValue())){
            return 18;
        }else if(value.equalsIgnoreCase(ObjectStoreInfoStoreType.FTP.getValue())){
            return 19;
        }else if(value.equalsIgnoreCase(ObjectStoreInfoStoreType.ADB_HP.getValue())){
            return 20;
        }
        return -1;
    }
}
