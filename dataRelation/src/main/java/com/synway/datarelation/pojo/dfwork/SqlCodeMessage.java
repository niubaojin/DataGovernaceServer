package com.synway.datarelation.pojo.dfwork;

import java.io.Serializable;

public class SqlCodeMessage implements Serializable{
    // 源数据库类型
    private String sourceConType =" ";
    //   源数据库表信息，XXX.aaa ，XXX表示 数据库名称，aaa表示 表名称, 当数据库查询不在的时候，即aaa表示 表名称；
    private String sourceConnect = " ";
    //  目标数据库类型
    private String targetConType =" ";
    //  目标数据库表信息，XXX.aaa ，XXX表示 数据库名称，aaa表示 表名称, 当数据库查询不在的时候，即aaa表示 表名称
    private String targetConnect =" ";

    public String getSourceConType() {
        return sourceConType;
    }

    public void setSourceConType(String sourceConType) {
        this.sourceConType = sourceConType;
    }

    public String getSourceConnect() {
        return sourceConnect;
    }

    public void setSourceConnect(String sourceConnect) {
        this.sourceConnect = sourceConnect;
    }

    public String getTargetConType() {
        return targetConType;
    }

    public void setTargetConType(String targetConType) {
        this.targetConType = targetConType;
    }

    public String getTargetConnect() {
        return targetConnect;
    }

    public void setTargetConnect(String targetConnect) {
        this.targetConnect = targetConnect;
    }
}
