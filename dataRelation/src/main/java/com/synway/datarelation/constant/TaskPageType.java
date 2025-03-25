package com.synway.datarelation.constant;

/**
 * 同步工作流监控在不同大平台上的 实现
 * @author wangdongwei
 */

public enum TaskPageType {
    // 华为云的查询接口
    HUAWEI_YUN("com.synway.datarelation.service.sync.impl.DfWorkTaskPage"),
    // 阿里云V2的查询接口
    ALI_YUN_V2("com.synway.datarelation.service.sync.impl.DataWorkV2TaskPage"),
    // 阿里云V3的查询接口
    ALI_YUN_V3("com.synway.datarelation.service.sync.impl.DataWorkV3TaskPage");

    private String cla;

    TaskPageType(String cla){
        this.cla = cla;
    }


    /**
     * 根据类型名称返回对应的执行类路径，如果没有找到就返回默认的执行类
     * @param type
     * @return
     */
    public static String getCla(String type,String version){
        if(Constant.HUA_WEI_YUN.equalsIgnoreCase(type)){
            return HUAWEI_YUN.cla;
        }else if((Constant.ALI_YUN+"_2").equalsIgnoreCase(type+"_"+version)){
            return ALI_YUN_V2.cla;
        }else if((Constant.ALI_YUN+"_3").equalsIgnoreCase(type+"_"+version)){
            return ALI_YUN_V3.cla;
        }else{
            return ALI_YUN_V3.cla;
        }

    }


}
