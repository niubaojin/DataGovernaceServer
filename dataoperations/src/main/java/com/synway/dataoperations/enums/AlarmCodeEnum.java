package com.synway.dataoperations.enums;

public enum AlarmCodeEnum {
    alarmFlagZero(0,"产生","ALARMFLAG"),
    alarmFlagOne(1,"消除","ALARMFLAG"),
    alarmFlagTwo(2,"事件","ALARMFLAG"),
    alarmFlagThree(3,"重启或倒换","ALARMFLAG"),
    alarmFlagFour(4,"异常连接","ALARMFLAG"),
    alarmLevelZero(0,"一般","ALARMLEVEL"),
    alarmLevelOne(1,"警告","ALARMLEVEL"),
    alarmLevelTwo(2,"严重","ALARMLEVEL"),
    alarmLevelThree(3,"紧急","ALARMLEVEL"),

    // 操作日志类型
    operatorType0(0, "登录", "OPERATORTYPE"),
    operatorType1(1, "查询", "OPERATORTYPE"),
    operatorType2(2, "新增", "OPERATORTYPE"),
    operatorType3(3, "修改", "OPERATORTYPE"),
    operatorType4(4, "删除", "OPERATORTYPE"),
    operatorType5(5, "登出", "OPERATORTYPE"),
    operatorType6(6, "导出", "OPERATORTYPE");

    private int code;
    private String name;
    private String type;

    AlarmCodeEnum(int code, String name, String type){
        this.code = code;
        this.name = name;
        this.type = type;
    }

    public static String getNameByCodeAndType(int code, String type){
        String returnStr = null;
        for (AlarmCodeEnum alarmCodeEnum : AlarmCodeEnum.class.getEnumConstants()){
            if (alarmCodeEnum.getCode() == code && alarmCodeEnum.getType().equals(type)){
                returnStr = alarmCodeEnum.getName();
                break;
            }
        }
        return returnStr;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
