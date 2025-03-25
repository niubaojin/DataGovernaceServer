package com.synway.datastandardmanager.pojo.dataDefinitionManagement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 数据项对标 数据项pojo
 * @author obito
 * @version 1.0
 * @date 2022/01/11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ObjectRelationManage implements Serializable {
    private static final long serialVersionUID = 4287865435214654668L;

    /**
     * 原始汇聚层数据集
     */
    //表id
    private String originalId;

    //原始汇聚层数据集id
    private Long originalObjectId;

    //原始汇聚层数据名称
    private String originalObjectName;

    //原始汇聚层数据集标准编码
    private String originalTableId;

    //当object_id原始汇聚，是：-1，否：关联的原始汇聚的id
    private String originalParentId;

    /**
     * 标准层数据集
     */
    //标准层数据集id
    private Long standardObjectId;

    //标准数据集数据名称
    private String standardObjectName;

    //标准数据集标准编码
    private String standardTableId;

    //当object_id原始汇聚，是：-1，否：关联的原始汇聚的id
    private String standardParentId;

    /**
     * 标准数据项数
     */
    private Integer standardFieldCount;

    /**
     * 数据项
     */
    private List<ObjectFieldRelation> objectFieldRelation;


    //映射类型:detect gadsjField recno
    private String type;

    //下面三个字段是作为调仓库接口参数使用
    //数据源id
    private String resId;

    //项目空间名称
    private String projectName;

    //表英文名
    private String tableNameEn;
}
