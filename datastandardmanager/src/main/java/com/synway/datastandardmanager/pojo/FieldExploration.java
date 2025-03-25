package com.synway.datastandardmanager.pojo;

import java.util.Date;

public class FieldExploration {

    // 字段序号
    private String fieldId;

    // 字段英文名
    private String fieldName;

    // 数据项语义
    private String fieldIdentity;

    // 字段中文
    private String fieldNameCN;

    // 元素代码
    private String fieldCode;

    // 外键信息
    private String fieldForeignKey;

    // 空值率
    private String nullPercent;

    // 值域
    private String valueRange;

    //相似度
    private double score;

    //相似字段信息
    private String similitude;

    // 更新时间
    private Date updateTime;

    private String tableId;

    private String data_Id;

    public String getData_Id() {
        return data_Id;
    }

    public void setData_Id(String data_Id) {
        this.data_Id = data_Id;
    }

    public String getSimilitude() {
        return similitude;
    }

    public void setSimilitude(String similitude) {
        this.similitude = similitude;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

//    private String standardCode;
//
    private String fieldCodeId;
//
//    public String getStandardCode() {
//        return standardCode;
//    }
//
//    public void setStandardCode(String standardCode) {
//        this.standardCode = standardCode;
//    }

    public String getFieldCodeId() {
        return fieldCodeId;
    }

    public void setFieldCodeId(String fieldCodeId) {
        this.fieldCodeId = fieldCodeId;
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldIdentity() {
        return fieldIdentity;
    }

    public void setFieldIdentity(String fieldIdentity) {
        this.fieldIdentity = fieldIdentity;
    }

    public String getFieldCode() {
        return fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public String getFieldForeignKey() {
        return fieldForeignKey;
    }

    public void setFieldForeignKey(String fieldForeignKey) {
        this.fieldForeignKey = fieldForeignKey;
    }

    public String getNullPercent() {
        return nullPercent;
    }

    public void setNullPercent(String nullPercent) {
        this.nullPercent = nullPercent;
    }

    public String getValueRange() {
        return valueRange;
    }

    public void setValueRange(String valueRange) {
        this.valueRange = valueRange;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getFieldNameCN() {
        return fieldNameCN;
    }

    public void setFieldNameCN(String fieldNameCN) {
        this.fieldNameCN = fieldNameCN;
    }
}