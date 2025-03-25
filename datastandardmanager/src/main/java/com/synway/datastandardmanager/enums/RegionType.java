package com.synway.datastandardmanager.enums;

public enum  RegionType {
    DAY("day", 0,"每天一个分区"),
    FIXED("fixed", 1,"固定分区"),
    DAY_SECONDARY("day_secondary", 2,"一天多个分区"),
    MULTI_DAY("multi_day", 3,"多天一个分区"),
    NOT_AVAILABLE("not_available", -1,"无效分区");

    private String name;
    private int code;
    private String desc;
    private RegionType(String name, int code, String desc) {
        this.desc = desc;
        this.code = code;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
