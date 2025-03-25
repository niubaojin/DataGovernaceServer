package com.synway.datastandardmanager.pojo.unitManagement;

import com.synway.datastandardmanager.pojo.synltefield.SynlteFieldStatusEnum;
import org.apache.commons.lang3.StringUtils;

public enum UnitOrganizationEnum {
    /**
     * 已经创建数据元并提交。提交新的数据需求和对现行数据元的修改建议都从本状态开始。
     */
    YS("1","第一级"),
    /**
     * 经过数据元注册机构形式审查后，等待技术审查
     */
    CA("2","第二级");

    private String id;
    private String value;

    UnitOrganizationEnum(String id, String value){
        this.id = id;
        this.value = value;
    }

    public static String getValueById(String id){
        if(StringUtils.isBlank(id)){
            return "";
        }
        for (UnitOrganizationEnum element : values()) {
            if(element.id.equalsIgnoreCase(id)){
                return element.value;
            }
        }
        return id;
    }
}
