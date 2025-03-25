package com.synway.datastandardmanager.enums;

public enum  EsType {
    //索引方式单独配置，0每天一个索引，1总共一个索引  2每天一个索引 3多天 一个索引
    DAY("day", 0,"每天一个索引"),
    FIXED("fixed", 1,"总共一个索引"),
//    DAY_SECONDARY("day_secondary", 2),
    MULTI_DAY("multi_day", 3,"多天 一个索引"),
    NOT_AVAILABLE("not_available", -1,"无效索引");
    private String name;
    private int type;
    private String descriptor;
    private EsType(String name,int type, String descriptor) {
        this.descriptor = descriptor;
        this.type = type;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public String getDescriptor() {
        return descriptor;
    }
}
