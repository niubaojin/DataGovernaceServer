package com.synway.datastandardmanager.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 数据标准表字段建表信息
 * @author nbj
 * @date 2025年5月12日17:41:46
 */
@Data
@TableName("SYNLTE.OBJECT_STORE_FIELDINFO")
public class ObjectStoreFieldInfoEntity {
    //表主键
    @TableId(type = IdType.NONE)
    @TableField("ID")
    private String id;

    //表存储信息ID
    @TableField("TABLEINFOID")
    private String tableInfoId;

    //数据元唯一编码
    @TableField("FIELDID")
    private String fieldId;

    //建表字段英文称
    @TableField("COLUMNNAME")
    private String columnName;

    //建表字段中文名称
    @TableField("FIELDCHINEENAME")
    private String fieldChineeName;

    //建表字段类型
    @TableField("FIELDTYPE")
    private Integer fieldType;

    private String fieldTypeCh;

    //建表字段长度
    @TableField("FIELDLEN")
    private Integer fieldLen;

    //建表字段默认值
    @TableField("DEFAULTVALUE")
    private String defaultValue;

    //建表字段索引
    @TableField("TABLEINDEX")
    private Integer tableIndex;

    //字段索引类型
    @TableField("INDEXTYPE")
    private Integer indexType;

    //是否必填项
    @TableField("NEEDVALUE")
    private Integer needValue;

    //字段顺序
    @TableField("RECNO")
    private Integer recno;

    //
    @TableField("PARTITION_RECNO")
    private Integer partitionRecno;

    //
    @TableField("CLUST_RECNO")
    private String clustRecno;

    //
    @TableField("COLUMNFIELDTYPE")
    private String columnFieldType;

    //
    @TableField("COLUMNFIELDLEN")
    private Integer columnFieldLen;

    //
    @TableField("COLUMNDEFAULTVALUE")
    private String columnDefaultValue;

    //
    @TableField("FIELDDECIMALLEN")
    private Integer fieldDecimalLen;

    // 对标字段
    @TableField(exist = false)
    private String toStandardField;
}
