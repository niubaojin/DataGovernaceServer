package com.synway.property.enums;

/**
 * @author 数据接入
 */
public enum SysCodeEnum {
    updateTypeOne("1", "实时更新", "UPDATETYPE"),
    updateTypeTwo("2", "批量表", "UPDATETYPE"),
    partitionTypeOne("1", "按天", "PARTITIONTYPE"),
    partitionTypeTwo("2", "按月", "PARTITIONTYPE"),
    partitionTypeThree("3", "按年", "PARTITIONTYPE"),
    isFormalOne("1", "临时表", "ISFORMAL"),
    isFormalTwo("2", "正式表", "ISFORMAL"),
    isFormalThree("3", "中间表", "ISFORMAL"),
    registerStateOne("1", "已登记未建表", "REGISTERSTATE"),
    registerStateTwo("2", "未登记已建表", "REGISTERSTATE"),
    registerStateThree("3", "已登记已建表", "REGISTERSTATE"),
    productStageOne("01", "标签加工", "PRODUCTSTAGE"),
    productStageTwo("02", "临时使用", "PRODUCTSTAGE"),
    productStageThree("03", "模型加工", "PRODUCTSTAGE"),
    productStageFour("04", "模型开发", "PRODUCTSTAGE"),
    productStageFive("05", "数据加工", "PRODUCTSTAGE"),
    productStageSix("06", "数据接入", "PRODUCTSTAGE"),
    productStageSeven("07", "资源提取", "PRODUCTSTAGE"),
    productStageEight("99", "未分配", "PRODUCTSTAGE"),
    // 平台类型
    tableType1("1", "odps", "TABLETYPE"),
    tableType2("2", "ads", "TABLETYPE"),
    tableType4("4", "HBase-HUAWEI", "TABLETYPE"),
    tableType5("5", "Hive-HUAWEI", "TABLETYPE"),
    tableType6("6", "ES", "TABLETYPE"),
    tableType7("7", "ClickHouse", "TABLETYPE"),
    tableType8("8", "libra", "TABLETYPE"),
    tableType9("9", "TRSHybase", "TABLETYPE"),
    tableType10("10", "Oracle", "TABLETYPE"),
    tableType11("11", "HBase-CDH", "TABLETYPE"),
    tableType12("12", "Hive-CDH", "TABLETYPE"),
    tableType13("13", "Datahub", "TABLETYPE"),
    tableType14("14", "Kafka", "TABLETYPE"),
    tableType15("15", "RocketMq", "TABLETYPE"),
    tableType16("16", "Redis", "TABLETYPE"),
    tableType18("18", "ADB", "TABLETYPE"),
    tableType19("19", "FTP", "TABLETYPE"),
    // 告警级别
    alarmLevelZero("0","一般","ALARMLEVEL"),
    alarmLevelOne("1","警告","ALARMLEVEL"),
    alarmLevelTwo("2","严重","ALARMLEVEL"),
    alarmLevelThree("3","紧急","ALARMLEVEL"),
    // 一级组织分类
    dataOCOne("01","原始库","DATAOC"),
    dataOCTwo("02","资源库","DATAOC"),
    dataOCThree("03","主题库","DATAOC"),
    dataOCFour("04","知识库","DATAOC"),
    dataOCFive("05","业务库","DATAOC"),
    dataOCSix("06","业务要素索引库","DATAOC"),

    // 操作日志类型
    operatorType0("0", "登录", "OPERATORTYPE"),
    operatorType1("1", "查询", "OPERATORTYPE"),
    operatorType2("2", "新增", "OPERATORTYPE"),
    operatorType3("3", "修改", "OPERATORTYPE"),
    operatorType4("4", "删除", "OPERATORTYPE"),
    operatorType5("5", "登出", "OPERATORTYPE"),
    operatorType6("6", "导出", "OPERATORTYPE");

    private String code = null;
    private String name = null;
    private String type = null;

    SysCodeEnum(String code, String name, String type) {
        this.code = code;
        this.name = name;
        this.type = type;
    }

    public static String getCodeByNameAndType(String name, String type) {
        String returnStr = null;
        for (SysCodeEnum exp : SysCodeEnum.class.getEnumConstants()) {
            if (exp.getName().equals(name) && exp.getType().equals(type)) {
                returnStr = exp.getCode();
                break;
            }
        }
        return returnStr;
    }

    public static String getNameByCodeAndType(String code, String type) {
        String returnStr = null;
        for (SysCodeEnum exp : SysCodeEnum.class.getEnumConstants()) {
            if (exp.getCode().equals(code) && exp.getType().equals(type)) {
                returnStr = exp.getName();
                break;
            }
        }
        return returnStr;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

