package com.synway.datastandardmanager.pojo.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 组织分类
 * @author obito
 * @version 1.0
 * @date
 */
public enum OrganizationClassification {

    YSK("01","原始库"),
    ZYK("02","资源库"),
    ZTK("03","主题库"),
    ZSK("04","知识库"),
    YWK("05","业务库"),
    YWYSSYK("06","业务要素索引库"),
    QT("99","其他");

    private String id;
    private String value;

    OrganizationClassification(String id, String value){
        this.id = id;
        this.value = value;
    }

    public static String getValueById(String id){
        if(StringUtils.isBlank(id)){
            return "";
        }
        for (OrganizationClassification element : values()) {
            if(element.id.equalsIgnoreCase(id)){
                return element.value;
            }
        }
        return id;
    }


}
