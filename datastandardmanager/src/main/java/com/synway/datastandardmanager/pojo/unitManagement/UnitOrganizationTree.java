package com.synway.datastandardmanager.pojo.unitManagement;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


/**
 * @ClassName UnitOrganizationPojo
 * @description  单位机构左侧树pojo
 * @author obito
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitOrganizationTree implements Serializable {

    private static final long serialVersionUID = 1545314213512443611L;

    private String id;

    private String unitName;

    private Integer unitLevel;

    private Integer unitType;

    private String parentId;

    private List<UnitOrganizationTree> childrenUnit;

    @Override
    public String toString() {
        return "UnitOrganizationTree{" +
                "id='" + id + '\'' +
                ", unitName='" + unitName + '\'' +
                ", unitLevel=" + unitLevel +
                ", unitType=" + unitType +
                ", parentId='" + parentId + '\'' +
                '}';
    }
}
