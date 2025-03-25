package com.synway.datastandardmanager.pojo.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @author wangdongwei
 * @ClassName DataResourceLocation
 * @description  1部 2省 3市 4 网站
 * @date 2020/9/10 18:46
 */
public enum DataResourceLocation {
    BU("01","部"),
    SHENG("02","省"),
    SHI("03","市"),
    WZ("04","网站");
    private String id;//
    private String value;//

    DataResourceLocation(String id, String value){
        this.id = id;
        this.value = value;
    }

    public static String getValueById(String id){
        if(StringUtils.isEmpty(id)){
            return "";
        }
        for (DataResourceLocation element : values()) {
            if(element.id.equals(id)){
                return element.value;
            }
        }
        return "";
    }
}
