package com.synway.datastandardmanager.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * @author obito
 * @description 单位机构左侧树pojo
 */
@Data
public class UnitOrganizationTreeVO implements Serializable {

    private static final long serialVersionUID = 1545314213512443611L;

    private String id;
    private String unitName;
    private Integer unitLevel;
    private Integer unitType;
    private String parentId;
    private List<UnitOrganizationTreeVO> childrenUnit;

}
