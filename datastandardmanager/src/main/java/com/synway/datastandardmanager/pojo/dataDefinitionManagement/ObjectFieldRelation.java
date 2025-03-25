package com.synway.datastandardmanager.pojo.dataDefinitionManagement;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
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
public class ObjectFieldRelation implements Serializable {
    private static final long serialVersionUID = 4298712464236641687L;

    /**
     * 数据集 id
     */
    private String setId;

    /**
     * 数据项 id
     */
    private String id;

    /**
     * 字段序号值
     */
    private Integer recno;

    /**
     * 字段名称
     */
    private String columnName;

    //英文名
    private String fieldName;

    /**
     * 数据元
     */
    private String gadsjFieldId;

    /**
     * 引用数据字典id
     */
    private String dictionaryRefId;

    /**
     * 引用数据字典
     */
    private String dictionaryRef;

    /**
     * 数据字典ID
     */
    private String dictionaryContentId;

    /**
     * 数据字典内容
     */
    private String dictionaryContent;

    /**
     * 数据集 id 当object_id原始汇聚，是：-1，否：关联的原始汇聚的id
     */
    private String parentId;

    /**
     * 字段名称
     */
    private String parentColumnName;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @DateTimeFormat(pattern ="yyyy-MM-dd")
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private List<ObjectFieldRelation> objectFieldRelationMapping;

    /**
     * 字段中文名称
     */
    private String fieldChineseName;
}
