package com.synway.datastandardmanager.pojo.warehouse;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author
 */
public class MappingSimilarInfo implements Serializable {
    private String resId;
    private String projectName;
    private String tableNameEn;
    private String protocolCode;
    private String sourceFields;
    private List<FieldInfo> sourceFieldList;
    private String targetFields;
    private List<FieldInfo> targetFieldList;
    private String sourceTargetMapping;
    private String sourceName;
    private String targetName;
    private Date updateTime;


    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getTableNameEn() {
        return tableNameEn;
    }

    public void setTableNameEn(String tableNameEn) {
        this.tableNameEn = tableNameEn;
    }

    public String getProtocolCode() {
        return protocolCode;
    }

    public void setProtocolCode(String protocolCode) {
        this.protocolCode = protocolCode;
    }

    public String getSourceFields() {
        return sourceFields;
    }

    public void setSourceFields(String sourceFields) {
        this.sourceFields = sourceFields;
    }

    public List<FieldInfo> getSourceFieldList() {
        return JSONObject.parseArray(sourceFields, FieldInfo.class);
    }

    public void setSourceFieldList(List<FieldInfo> sourceFieldList) {
        this.sourceFieldList = sourceFieldList;
    }

    public String getTargetFields() {
        return targetFields;
    }

    public void setTargetFields(String targetFields) {
        this.targetFields = targetFields;
    }

    public List<FieldInfo> getTargetFieldList() {
        return JSONObject.parseArray(targetFields, FieldInfo.class);
    }

    public void setTargetFieldList(List<FieldInfo> targetFieldList) {
        this.targetFieldList = targetFieldList;
    }

    public String getSourceTargetMapping() {
        return sourceTargetMapping;
    }

    public void setSourceTargetMapping(String sourceTargetMapping) {
        this.sourceTargetMapping = sourceTargetMapping;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}

