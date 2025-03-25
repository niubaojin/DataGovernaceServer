package com.synway.datastandardmanager.pojo;

import com.synway.datastandardmanager.pojo.enums.SynlteFieldType;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class SourceFieldInfo {
    private String sourceInfoID ="";
    //序号
    private int recno;
    private String columnName;
    private String tableId=""         ;
    private String resourceId=""      ;
    private String fieldName=""       ;
    private String fieldDescription="";
    private String fieldType=""       ;
    private String fieldTypeCode=""   ;
    private String iFieldType=""     ;
    private String fieldLength=""     ;
    private String isNonnull="0"       ;
    private String isDeleted=""       ;
    private String isPrimarykey="0"    ;
    private String isForeignKey="0"    ;
    private String insertTime=""      ;
    private String updateTime=""      ;
    private String databaseUrl=""     ;
    private String schemeId=""        ;
    private String haveRelate="";
    private int seq;
    //  设置 fieldId 在数据库中该值不能为空
    private String fieldId = "";
    //  判断该字段是否从数据仓库获取，如果是从数据仓库获取
    //   是否属于原始库
    private Integer fieldSourceType = 0;
    //  20200817  新增相似度分析之后的元素编码信息
    private String fieldCode ="";

    //数据元内部标识符
    private String gadsjFieldId="";

    private String determinerId;
    private String determinerName;

    //数据要素Id
    private String elementId;

    //数据项中文名
    private String fieldChineseName;

    //字段长度
    private String fieldLen;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public int getRecno() {
        return recno;
    }

    public void setRecno(int recno) {
        this.recno = recno;
    }

    public SourceFieldInfo(){}
    public SourceFieldInfo(String sourceInfoID,int id,String tableId,String fieldName,String fieldDescription,
                           String fieldType,String fieldLength,String isNonnull,
                           String isPrimarykey,String isForeignKey,int seq,String fieldId,String fieldCode,
                           String gadsjFieldId,String determinerId,String elementId,String fieldChineseName,String fieldLen){
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
        this.elementId =elementId;
        this.fieldChineseName = fieldChineseName;
        this.fieldLen = fieldLen;
    }

    public static SourceFieldInfo getInstance(String sourceInfoID,int id,String tableId,String fieldName,String fieldDescription,
                                              String fieldType,String fieldLength,String isNonnull,String isPrimarykey,
                                              String isForeignKey,int seq,String fieldId,String fieldCode,String gadsjFieldId,
                                              String determinerId,String elementId,String fieldChineseName,String fieldLen){
        SourceFieldInfo sourceFieldInfo = new SourceFieldInfo(sourceInfoID,id,tableId,fieldName,fieldDescription,fieldType,
                fieldLength,isNonnull,isPrimarykey,isForeignKey,seq,fieldId,fieldCode,gadsjFieldId,determinerId,elementId,
                fieldChineseName,fieldLen);
        return sourceFieldInfo;
    }

    public String getFieldLen() {
        return fieldLen;
    }

    public void setFieldLen(String fieldLen) {
        this.fieldLen = fieldLen;
    }

    public String getFieldChineseName() {
        return fieldChineseName;
    }

    public void setFieldChineseName(String fieldChineseName) {
        this.fieldChineseName = fieldChineseName;
    }

    public String getElementId() {
        return elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    public String getDeterminerId() {
        return determinerId;
    }

    public void setDeterminerId(String determinerId) {
        this.determinerId = determinerId;
    }

    public String getDeterminerName() {
        return determinerName;
    }

    public void setDeterminerName(String determinerName) {
        this.determinerName = determinerName;
    }

    public String getGadsjFieldId() {
        return gadsjFieldId;
    }

    public void setGadsjFieldId(String gadsjFieldId) {
        this.gadsjFieldId = gadsjFieldId;
    }

    public String getFieldCode() {
        return fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public Integer getFieldSourceType() {
        return fieldSourceType;
    }

    public void setFieldSourceType(Integer fieldSourceType) {
        this.fieldSourceType = fieldSourceType;
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public String getiFieldType() {
        return iFieldType;
    }

    public void setiFieldType(String iFieldType) {
        this.iFieldType = iFieldType;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public int getSeq() {
        return seq;
    }

    public void setHaveRelate(String haveRelate) {
        this.haveRelate = haveRelate;
    }

    public String getHaveRelate() {
        return haveRelate;
    }

    public void setSourceInfoID(String sourceInfoID) {
        this.sourceInfoID = sourceInfoID;
    }

    public String getSourceInfoID() {
        return sourceInfoID;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldDescription() {
        return fieldDescription;
    }

    public void setFieldDescription(String fieldDescription) {
        this.fieldDescription = fieldDescription;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldTypeCode() {
        return fieldTypeCode;
    }

    public void setFieldTypeCode(String fieldTypeCode) {
        this.fieldTypeCode = fieldTypeCode;
    }

    public String getFieldLength() {
        return fieldLength;
    }

    public void setFieldLength(String fieldLength) {
        this.fieldLength = fieldLength;
    }

    public String getIsNonnull() {
        if(this.isNonnull == null){
            return "0";
        }else if("是".equals(this.isNonnull)){
            return "1";
        }else if("否".equals(this.isNonnull)){
            return "0";
        }else if(StringUtils.isNumeric(this.isNonnull)){
            return this.isNonnull;
        }else{
            return "0";
        }
    }

    public void setIsNonnull(String isNonnull) {
        this.isNonnull = isNonnull;
    }

    public String getIsDeleted() {
        if("是".equals(this.isDeleted)){
            return "1";
        }else if("否".equals(this.isDeleted)){
            return "0";
        }else if(StringUtils.isNumeric(this.isDeleted)){
            return this.isDeleted;
        }else{
            return "0";
        }
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getIsPrimarykey() {
        if("是".equalsIgnoreCase(this.isPrimarykey)){
            return "1";
        }else if("否".equalsIgnoreCase(this.isPrimarykey)){
            return "0";
        }else if(StringUtils.isNumeric(this.isPrimarykey)){
            return this.isPrimarykey;
        }else{
            return "0";
        }
    }

    public void setIsPrimarykey(String isPrimarykey) {
        this.isPrimarykey = isPrimarykey;
    }

    public String getIsForeignKey() {
        if("是".equalsIgnoreCase(this.isForeignKey)){
            return "1";
        }else if("否".equalsIgnoreCase(this.isForeignKey)){
            return "0";
        }else if(StringUtils.isNumeric(this.isForeignKey)){
            return this.isForeignKey;
        }else{
            return "0";
        }
    }

    public void setIsForeignKey(String isForeignKey) {
        this.isForeignKey = isForeignKey;
    }

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public void setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    public String getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
