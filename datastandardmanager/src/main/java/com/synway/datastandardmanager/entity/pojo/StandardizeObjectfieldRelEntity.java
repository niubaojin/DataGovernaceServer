package com.synway.datastandardmanager.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@TableName("STANDARDIZE_OBJECTFIELD_REL")
public class StandardizeObjectfieldRelEntity {

    //数据集id
    @TableField("SET_ID")
    private String setId;

    //数据项id
    @TableField("ID")
    private String id;

    //字段序号值
    @TableField("RECNO")
    private Integer recno;

    //字段名称
    @TableField("COLUMN_NAME")
    private String columnName;

    //英文名
    @TableField(exist = false)
    private String fieldName;

    //数据元
    @TableField("GADSJ_FIELDID")
    private String gadsjFieldId;

    //引用数据字典id
    @TableField("DICTIONARY_REF_ID")
    private String dictionaryRefId;

    //引用数据字典
    @TableField("DICTIONARY_REF")
    private String dictionaryRef;

    //数据字典ID
    @TableField("DICTIONARY_CONTENT_ID")
    private String dictionaryContentId;

    //数据字典内容
    @TableField("DICTIONARY_CONTENT")
    private String dictionaryContent;

    //数据集 id,当object_id原始汇聚，是：-1，否：关联的原始汇聚标的object_id
    @TableField("PARENT_ID")
    private String parentId;

    //对标字段名称
    @TableField("PARENT_COLUMN_NAME")
    private String parentColumnName;

    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @TableField("CREATE_TIME")
    private Date createTime;

    //更新时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("UPDATE_TIME")
    private Date updateTime;

    //字段中文名称
    @TableField("FIELDCHINESENAME")
    private String fieldChineseName;

    @TableField(exist = false)
    private List<StandardizeObjectfieldRelEntity> objectFieldRelationMapping;

}
