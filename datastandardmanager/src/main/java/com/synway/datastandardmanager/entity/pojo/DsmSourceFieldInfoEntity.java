package com.synway.datastandardmanager.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 来源字段信息表
 */
@Data
@TableName("DSM_SOURCE_FIELD_INFO")
public class DsmSourceFieldInfoEntity {

    //表id
    @TableField("ID")
    private String recno;

    //SOURCEINFOID表的ID
    @TableField("SOURCEINFOID")
    private String sourceInfoID;

    //表英文名
    @TableField("TABLEID")
    private String tableId;

    @TableField("RESOURCEID")
    private String resourceId;

    //字段英文名
    @TableField("FIELDNAME")
    private String fieldName;

    //字段中文名（描述）
    @TableField("FIELDDESCRIPTION")
    private String fieldDescription;

    //字段类型
    @TableField("FIELDTYPE")
    private String fieldType;

    //字段长度
    @TableField("FIELDLENGTH")
    private String fieldLength;

    //是否为空
    @TableField("ISNONNULL")
    private String isNonnull;

    //是否已经删除
    @TableField("ISDELETED")
    private String isDeleted;

    //是否为主键
    @TableField("ISPRIMARYKEY")
    private String isPrimarykey;

    //是否为外键
    @TableField("ISFOREIGNKEY")
    private String isForeignKey;

    //插入时间
    @TableField("INSERTTIME")
    private String insertTime;

    //更新时间
    @TableField("UPDATETIME")
    private String updateTime;

    //数据库的URL
    @TableField("DATABASEURL")
    private String databaseUrl;

    //数据库/项目名
    @TableField("SCHEMEID")
    private String schemeId;

    //1：是 0：否 默认值：0
    @TableField("FIELDSOURCETYPE")
    private Integer fieldSourceType;

    //相似度分析获取到的元素编码 add20200818
    @TableField("FIELDCODE")
    private String fieldCode;

    //数据元内部标识符
    @TableField("GADSJFIELDID")
    private String gadsjFieldId;

    //限定词id
    @TableField("DETERMINERID")
    private String determinerId;

    //数据要素id
    @TableField("ELEMENTID")
    private String elementId;

    @TableField(exist = false)
    private String determinerName;

    @TableField(exist = false)
    private String columnName;

    @TableField(exist = false)
    private String fieldChineseName;

    @TableField(exist = false)
    private String fieldLen;

    @TableField(exist = false)
    private String fieldTypeCode;


    public DsmSourceFieldInfoEntity(){};
    public DsmSourceFieldInfoEntity(String sourceInfoID, int id, String tableId, String fieldName, String fieldDescription,
                                    String fieldType, String fieldLength, String isNonnull,
                                    String isPrimarykey, String isForeignKey, String fieldCode,
                                    String gadsjFieldId, String determinerId, String elementId, String fieldChineseName, String fieldLen) {
        this.sourceInfoID = sourceInfoID;
        this.recno = String.valueOf(id);
        this.tableId = tableId;
        this.fieldName = fieldName;
        this.fieldDescription = fieldDescription;
        this.fieldType = fieldType;
        this.fieldLength = fieldLength;
        this.isNonnull = isNonnull;
        this.isPrimarykey = isPrimarykey;
        this.isForeignKey = isForeignKey;
        this.fieldCode = fieldCode;
        this.gadsjFieldId = gadsjFieldId;
        this.determinerId = determinerId;
        this.elementId = elementId;
        this.fieldChineseName = fieldChineseName;
        this.fieldLen = fieldLen;
    }

}
