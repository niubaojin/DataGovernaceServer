package com.synway.datarelation.constant;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * @author wangdongwei
 * @ClassName DataBaseType
 * @description  当大数据平台数据库类型不同时，对应的查询接口不一样
 * @date 2020/12/2 9:16
 */
public enum DataBaseType {
    HUAWEI_YUN("com.synway.datarelation.service.workflow.impl.DfWorkService"),
    ALI_YUN_V2("com.synway.datarelation.service.workflow.impl.DataWorkV2Service"),
    ALI_YUN_V3("com.synway.datarelation.service.workflow.impl.DataWorkV3Service"),
    DF_WORKS("com.synway.datarelation.service.workflow.impl.DfWorkService"),
    DATA_WORKS_V2("com.synway.datarelation.service.workflow.impl.DataWorkV2Service"),
    DATA_WORKS_V3("com.synway.datarelation.service.workflow.impl.DataWorkV3Service");

    private String cla;

    DataBaseType(String cla){
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
            return "";
        }
    }

    public static String getCla(Map<String,String> parameterMap){
        //cf 新增: 先通过新增参数processType判断，不能确定则通过数据源类型判断
        String processType = parameterMap.getOrDefault(Common.PROCESS_PLAT_FORM_TYPE, "");
        if (StringUtils.isNotBlank(processType)){
            if(Constant.DF_WORK.equalsIgnoreCase(processType)){
                return DF_WORKS.cla;
            }else if (Constant.DATA_WORK2.equalsIgnoreCase(processType)){
                return DATA_WORKS_V2.cla;
            }else if (Constant.DATA_WORK3.equalsIgnoreCase(processType)){
                return DATA_WORKS_V3.cla;
            }
        }

        //cf 兼容历史版本：通过数据源类型判断（wangdongwei）
        String version = parameterMap.getOrDefault(Common.DATA_PLAT_FORM_VERSION, "3");
        String dtaBaseType = parameterMap.getOrDefault(Common.DATA_PLAT_FORM_TYPE, "");
        if(Constant.HUA_WEI_YUN.equalsIgnoreCase(dtaBaseType)){
            return HUAWEI_YUN.cla;
        }else if((Constant.ALI_YUN+"_2").equalsIgnoreCase(dtaBaseType+"_"+version)){
            return ALI_YUN_V2.cla;
        }else if((Constant.ALI_YUN+"_3").equalsIgnoreCase(dtaBaseType+"_"+version)){
            return ALI_YUN_V3.cla;
        }else{
            return "";
        }
    }

}
