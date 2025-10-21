package com.synway.datastandardmanager.entity.vo;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Data
public class SourceFieldInfoVO {
    private String sourceInfoID = "";
    private int recno;                  //序号
    private String columnName;
    private String tableId = "";
    private String resourceId = "";
    private String fieldName = "";
    private String fieldDescription = "";
    private String fieldType = "";
    private String fieldTypeCode = "";
    private String iFieldType = "";
    private String fieldLength = "";
    private String isNonnull = "0";
    private String isDeleted = "";
    private String isPrimarykey = "0";
    private String isForeignKey = "0";
    private String insertTime = "";
    private String updateTime = "";
    private String databaseUrl = "";
    private String schemeId = "";
    private String haveRelate = "";
    private int seq;
    private String fieldId = "";            // 设置 fieldId 在数据库中该值不能为空
    private Integer fieldSourceType = 0;    // 判断该字段是否从数据仓库获取，如果是从数据仓库获取，是否属于原始库
    private String fieldCode = "";          // 20200817：新增相似度分析之后的元素编码信息
    private String gadsjFieldId = "";       //数据元内部标识符
    private String determinerId;
    private String determinerName;
    private String elementId;               //数据要素Id
    private String fieldChineseName;        //数据项中文名
    private String fieldLen;                //字段长度

    public SourceFieldInfoVO() {
    }

    public SourceFieldInfoVO(String sourceInfoID, int id, String tableId, String fieldName, String fieldDescription,
                             String fieldType, String fieldLength, String isNonnull,
                             String isPrimarykey, String isForeignKey, int seq, String fieldId, String fieldCode,
                             String gadsjFieldId, String determinerId, String elementId, String fieldChineseName, String fieldLen) {
        this.sourceInfoID = sourceInfoID;
        this.recno = id;
        this.tableId = tableId;
        this.fieldName = fieldName;
        this.fieldDescription = fieldDescription;
        this.fieldType = fieldType;
        this.fieldLength = fieldLength;
        this.isNonnull = isNonnull;
        this.isPrimarykey = isPrimarykey;
        this.isForeignKey = isForeignKey;
        this.seq = seq;
        this.fieldId = fieldId;
        this.fieldCode = fieldCode;
        this.gadsjFieldId = gadsjFieldId;
        this.determinerId = determinerId;
        this.elementId = elementId;
        this.fieldChineseName = fieldChineseName;
        this.fieldLen = fieldLen;
    }

    public static SourceFieldInfoVO getInstance(String sourceInfoID, int id, String tableId, String fieldName, String fieldDescription,
                                                String fieldType, String fieldLength, String isNonnull, String isPrimarykey,
                                                String isForeignKey, int seq, String fieldId, String fieldCode, String gadsjFieldId,
                                                String determinerId, String elementId, String fieldChineseName, String fieldLen) {
        SourceFieldInfoVO sourceFieldInfo = new SourceFieldInfoVO(sourceInfoID, id, tableId, fieldName, fieldDescription, fieldType,
                fieldLength, isNonnull, isPrimarykey, isForeignKey, seq, fieldId, fieldCode, gadsjFieldId, determinerId, elementId,
                fieldChineseName, fieldLen);
        return sourceFieldInfo;
    }


    public String getIsNonnull() {
        if (this.isNonnull == null) {
            return "0";
        } else if ("是".equals(this.isNonnull)) {
            return "1";
        } else if ("否".equals(this.isNonnull)) {
            return "0";
        } else if (StringUtils.isNumeric(this.isNonnull)) {
            return this.isNonnull;
        } else {
            return "0";
        }
    }

    public void setIsNonnull(String isNonnull) {
        this.isNonnull = isNonnull;
    }

    public String getIsDeleted() {
        if ("是".equals(this.isDeleted)) {
            return "1";
        } else if ("否".equals(this.isDeleted)) {
            return "0";
        } else if (StringUtils.isNumeric(this.isDeleted)) {
            return this.isDeleted;
        } else {
            return "0";
        }
    }

}
