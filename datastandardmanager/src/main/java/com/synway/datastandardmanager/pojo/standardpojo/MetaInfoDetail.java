package com.synway.datastandardmanager.pojo.standardpojo;

import java.io.Serializable;

public class MetaInfoDetail implements Serializable {

    private String key;

    private String fieldno;

    private String engname;

    private String cnname;

    private String dbname;

    private int len;

    private int fieldtype;

    private int partition;

    private int type;

    private int notNull;    // 字段是否必填，0:非必填,1:必填,2:ADS必填


    public int getNotNull() { return notNull; }

    public void setNotNull(int notNull) { this.notNull = notNull; }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getFieldno() {
        return fieldno;
    }

    public void setFieldno(String fieldno) {
        this.fieldno = fieldno;
    }

    public String getEngname() {
        return engname;
    }

    public void setEngname(String engname) {
        this.engname = engname;
    }

    public String getCnname() {
        return cnname;
    }

    public void setCnname(String cnname) {
        this.cnname = cnname;
    }

    public String getDbname() {
        return dbname;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public int getFieldtype() {
        return fieldtype;
    }

    public void setFieldtype(int fieldtype) {
        this.fieldtype = fieldtype;
    }

    public int getPartition() {
        return partition;
    }

    public void setPartition(int partition) {
        this.partition = partition;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
