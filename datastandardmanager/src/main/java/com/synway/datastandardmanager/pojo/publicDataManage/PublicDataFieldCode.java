package com.synway.datastandardmanager.pojo.publicDataManage;

import com.synway.datastandardmanager.pojo.synlteelement.SynlteElementCode;
import org.apache.commons.lang3.StringUtils;

/**
 * @author obito
 * @version 1.0
 * @date
 */
public enum PublicDataFieldCode {
    /**
     * 数据元编码
     */
    YES("1_1","是"),
    /**
     *  数据元+数据字典
     */

    NO("1_0","否");


    private String id;
    private String value;

    PublicDataFieldCode(String id, String value){
        this.id = id;
        this.value = value;
    }

    public static String getValueById(String id){
        if(StringUtils.isBlank(id)){
            return "";
        }
        for (PublicDataFieldCode element : values()) {
            if(element.id.equalsIgnoreCase(id)){
                return element.value;
            }
        }
        return id;
    }
}
