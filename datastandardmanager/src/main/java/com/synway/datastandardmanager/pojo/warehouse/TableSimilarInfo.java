package com.synway.datastandardmanager.pojo.warehouse;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author
 */
@Data
public class TableSimilarInfo implements Serializable {
    private String resId;
    private String projectName;
    private String tableNameEn;
    private String protocolCode;
    private String objectName;
    private double score;
    private String matchRate;
    private String sourceMatchRate;
    private String standardMatchRate;
    private String matchInfo;
    private Date updateTime;
    private MappingSimilarInfo mappingSimilarInfo;
    /**
     * 探查ID
     */
    private String detectedId;
    /**
     * 源数据名称
     */
    private String sourceName;

    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }

    public String getDetectedId() {
        return detectedId;
    }

    public void setDetectedId(String detectedId) {
        this.detectedId = detectedId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
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

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getMatchRate() {
        return matchRate;
    }

    public void setMatchRate(String matchRate) {
        this.matchRate = matchRate;
    }

    public String getSourceMatchRate() {
        return sourceMatchRate;
    }

    public void setSourceMatchRate(String sourceMatchRate) {
        this.sourceMatchRate = sourceMatchRate;
    }

    public String getStandardMatchRate() {
        return standardMatchRate;
    }

    public void setStandardMatchRate(String standardMatchRate) {
        this.standardMatchRate = standardMatchRate;
    }

    public String getMatchInfo() {
        return matchInfo;
    }

    public void setMatchInfo(String matchInfo) {
        this.matchInfo = matchInfo;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
