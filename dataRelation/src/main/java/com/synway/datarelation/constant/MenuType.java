package com.synway.datarelation.constant;

/**
 *  右键菜单栏的类型数据
 */
public enum MenuType {
    SOURCE_INFORMATION("sourceInformation","来源信息"),
    ACCESS_TASK("accessTask","接入任务"),
    FIELD_BLOODLINE("fieldBloodline","字段血缘"),
    HANDLE_TASK("handleTask","处理任务"),
    ASSET_INFORMATION("assetInformation","资产信息"),
    DATA_RECONCILIATION("dataReconciliation","数据对账"),
    DATA_QUALITY_MESSAGE("dataQualityMessage","质量信息"),
    DATA_STANDARD_MESSAGE("dataStandardMessage","标准信息"),
    ASSOCIATIVE_WORKFLOW("associativeWorkflow","关联工作流");
    private final String code;
    private final String message;

    MenuType(String code , String message){
        this.code = code;
        this.message = message;
    }
    public static String getCodeByName(String name){
        for (MenuType element : values()) {
            if(element.message.equalsIgnoreCase(name)){
                return element.code;
            }
        }
        return "";
    }

    public static String getNameByCode(String code){
        for (MenuType element : values()) {
            if(element.code.equalsIgnoreCase(code)){
                return element.message;
            }
        }
        return "";
    }
}
