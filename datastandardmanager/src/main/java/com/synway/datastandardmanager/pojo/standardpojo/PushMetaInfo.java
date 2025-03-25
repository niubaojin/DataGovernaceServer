package com.synway.datastandardmanager.pojo.standardpojo;

import java.io.Serializable;
import java.util.List;

public class PushMetaInfo implements Serializable {

    private int type;

    private String sys;

    private String syscnname;

    private String sysengname;

    private String protocolengname;

    private String protocolcnname;

    private String source;

    private String tablename;

    private int db;

    private List<MetaInfoDetail> field;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSys() {
        return sys;
    }

    public void setSys(String sys) {
        this.sys = sys;
    }

    public String getSyscnname() {
        return syscnname;
    }

    public void setSyscnname(String syscnname) {
        this.syscnname = syscnname;
    }

    public String getProtocolengname() {
        return protocolengname;
    }

    public void setProtocolengname(String protocolengname) {
        this.protocolengname = protocolengname;
    }

    public String getProtocolcnname() {
        return protocolcnname;
    }

    public void setProtocolcnname(String protocolcnname) {
        this.protocolcnname = protocolcnname;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public int getDb() {
        return db;
    }

    public void setDb(int db) {
        this.db = db;
    }

    public List<MetaInfoDetail> getField() {
        return field;
    }

    public void setField(List<MetaInfoDetail> field) {
        this.field = field;
    }

    public String getSysengname() {
        return sysengname;
    }

    public void setSysengname(String sysengname) {
        this.sysengname = sysengname;
    }
}
