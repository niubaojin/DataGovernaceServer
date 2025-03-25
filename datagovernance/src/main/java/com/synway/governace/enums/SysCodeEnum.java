package com.synway.governace.enums;

/**
 * 系统配置枚举
 *
 * @author ywj
 * @date 2020/7/23 10:13
 */
public enum SysCodeEnum {
    standardDefined("BZDY", "标准定义", "MODULETYPE"),
    standard("SJDY", "数据定义", "STANDARDTYPE"),
    metadata("YSBM", "元素编码", "STANDARDTYPE"),
    semantic("YYLX", "语义类型", "STANDARDTYPE"),
    nationalCode("GBZD", "国标字典", "STANDARDTYPE"),
    origin("origin", "原始库", "ORGANIZATIONTYPE"),
    resource("resource", "资源库", "ORGANIZATIONTYPE"),
    theme("theme", "主题库", "ORGANIZATIONTYPE"),
    business("business", "业务库", "ORGANIZATIONTYPE"),
    knowledge("knowledge", "知识库", "ORGANIZATIONTYPE"),
    index("index", "业务要素索引库", "ORGANIZATIONTYPE"),
    register("register", "注册", "APPROVALMODULETYPE"),
    dataDefinition("dataDefinition", "数据探查", "APPROVALMODULETYPE"),

    // 操作日志类型
    operatorType0("0", "登录", "OPERATORTYPE"),
    operatorType1("1", "查询", "OPERATORTYPE"),
    operatorType2("2", "新增", "OPERATORTYPE"),
    operatorType3("3", "修改", "OPERATORTYPE"),
    operatorType4("4", "删除", "OPERATORTYPE"),
    operatorType5("5", "登出", "OPERATORTYPE"),
    operatorType6("6", "导出", "OPERATORTYPE"),
    operatorType7("7", "注册", "OPERATORTYPE"),
    operatorType8("8", "建表", "OPERATORTYPE");

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
