package com.synway.datastandardmanager.pojo.dataDefinitionManagement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 数据集对标卡片
 * @author obito
 * @version 1.0
 * @date 2022/03/07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataSetMark implements Serializable {
    private static final long serialVersionUID = 42124467657568722L;

    /**
     * 标准id
     */
    private Long objectId;

    /**
     * 数据名称
     */
    private String dataName;

    /**
     * 标准编码
     */
    private String tableId;

    /**
     * 数据项数
     */
    private Integer objectFieldCount;

    /**
     * 数据项信息
     */
    private List<ObjectFieldRelation> objectFieldRelationList;
}
