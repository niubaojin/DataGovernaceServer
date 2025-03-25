package com.synway.datastandardmanager.pojo.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @author wangdongwei
 * @ClassName DataAcquisitionMethodType
 * @description 数据获取方法的枚举类
 * @date 2020/9/10 18:39
 */
public enum DataAcquisitionMethodType {
    ZK("01","侦控"),
    GK("02","管控"),
    GL("03","管理"),
    GKA("04","公开");
    private String id;//
    private String value;//

    DataAcquisitionMethodType(String id, String value){
        this.id = id;
        this.value = value;
    }

    public static String getValueById(String id){
        if(StringUtils.isEmpty(id)){
            return "";
        }
        for (DataAcquisitionMethodType element : values()) {
            if(element.id.equals(id)){
                return element.value;
            }
        }
        return "";
    }
}
