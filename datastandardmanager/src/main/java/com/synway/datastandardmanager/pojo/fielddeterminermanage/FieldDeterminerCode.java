package com.synway.datastandardmanager.pojo.fielddeterminermanage;

import com.synway.datastandardmanager.pojo.enums.DataAcquisitionMethodType;
import org.apache.commons.lang3.StringUtils;

/**
 * 限定词的相关码表
 * @author wangdongwei
 * @date 2021/7/16 15:27
 */
public enum FieldDeterminerCode {

    /**
     * 新建
     */
    XJ("01","新建"),
    /**
     *  发布
     */
    FB("05","发布"),
    /**
     *  废弃
     */
    FQ("07","废弃"),
    /**
     * 公安标准
     */
    GABZ("2_1","是"),
    /**
     *  非标准
     */
    FBZ("2_0","否");

    private String id;
    private String value;

    FieldDeterminerCode(String id, String value){
        this.id = id;
        this.value = value;
    }

    public static String getValueById(String id){
        if(StringUtils.isBlank(id)){
            return "";
        }
        for (FieldDeterminerCode element : values()) {
            if(element.id.equalsIgnoreCase(id)){
                return element.value;
            }
        }
        return id;
    }


}
