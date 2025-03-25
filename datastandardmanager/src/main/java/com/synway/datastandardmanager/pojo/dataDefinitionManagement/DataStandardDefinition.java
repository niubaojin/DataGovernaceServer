package com.synway.datastandardmanager.pojo.dataDefinitionManagement;

import com.synway.datastandardmanager.pojo.ObjectField;
import com.synway.datastandardmanager.pojo.ObjectPojoTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 数据标准定义的所有信息，包括(数据集定义、数据项定义、数据集对标)
 * @author obito
 * @version 1.0
 * @date 2022/02/11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataStandardDefinition implements Serializable {
    private static final long serialVersionUID = 4271264157123475L;

    /**
     *数据集定义信息
     */
    private ObjectPojoTable objectPojoTable;

    /**
     *数据项定义信息
     */
    private List<ObjectField> objectFieldList;

    //20220111 是否有数据集对标内容
    private Boolean dataRelationMapping;

    //20220111 数据项对标内容
    private ObjectRelationManage objectRelationManage;

    //状态(0:初始化;1:审批中;2:退回;3:终止)
    private String status;

}
