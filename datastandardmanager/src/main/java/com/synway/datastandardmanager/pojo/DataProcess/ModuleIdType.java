package com.synway.datastandardmanager.pojo.DataProcess;

/**
 *  子系统代码或模块代码对应的代码值
 *  用于 数据历程中  模块代码
 */
public enum ModuleIdType {
    SJCK("SJCK","数据仓库"),
    SJTC("SJTC","数据探查"),
    SJJR("SJJR","数据接入"),
    BZGL("BZGL","标准管理"),
    ZLJC("ZLJC","数据质检"),
    SJDZ("SJDZ","数据对账"),
    ZCGL("ZCGL","资产管理"),
    SJXY("SJXY","数据血缘"),
    JCDJ("JCDJ","家产登记"),
    YWGL("YWGL","运维管理");

    private String moduleId;
    private String moduleName;


    ModuleIdType(String moduleId,String moduleName){
        this.moduleId = moduleId;
        this.moduleName = moduleName;
    }

    public static String getIndexByName(String name){
        for (ModuleIdType element : values()) {
            if(element.moduleName == name ){
                return element.moduleId;
            }
        }
        return "";
    }

}
