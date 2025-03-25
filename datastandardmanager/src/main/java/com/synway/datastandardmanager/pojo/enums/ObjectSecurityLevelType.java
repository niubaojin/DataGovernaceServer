package com.synway.datastandardmanager.pojo.enums;

import com.synway.datastandardmanager.pojo.synlteelement.SynlteElementCode;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * 数据安全分级枚举类
 * @author obito
 * @version 1.0
 * @date
 */
public enum ObjectSecurityLevelType {

    PUBLICITY("1_01","公开"),

    COMMON("1_02","一般"),

    IMPORTANCE("1_03","重要"),

    SPECIAL("1_04","特殊"),

    FIRST_LEVEL("2_01","第一级"),

    SECOND_LEVEL("2_02","第二级"),

    THIRD_LEVEL("2_03","第三级"),

    FOUR_LEVEL("2_04","第四级"),

    FIVE_LEVEL("2_05","第五级"),

    SIX_LEVEL("2_06","第六级"),

    SEVEN_LEVEL("2_07","第七级"),

    EIGHT_LEVEL("2_08","第八级");



    private String id;
    private String value;

    ObjectSecurityLevelType(String id, String value){
        this.id = id;
        this.value = value;
    }
    public static String getValueById(String id){
        if(StringUtils.isBlank(id)){
            return "";
        }
        for (ObjectSecurityLevelType data : values()) {
            if(data.id.equalsIgnoreCase(id)){
                return data.value;
            }
        }
        return id;
    }
}
