package com.synway.property.pojo.organizationdetail;

import java.math.BigDecimal;

/**
 * @author majia
 */
public class DataStatistics {
    private String tableNameEn;
    private String dataTime;
    private BigDecimal totalCount;
    private BigDecimal incrementCount;
    private BigDecimal tableAllSize;    // 数据总大小
    private BigDecimal average7;        // 前7天均值
    private BigDecimal fluctuateRate;   // 数据抖动率
    private String isNormal;            // 波动是否正常（1：正常，2：异常）
    private String situationInfo;       // 态势信息

    public BigDecimal getTableAllSize() {
        return tableAllSize;
    }

    public void setTableAllSize(BigDecimal tableAllSize) {
        this.tableAllSize = tableAllSize;
    }

    public String getIsNormal() {
        return isNormal;
    }

    public void setIsNormal(String isNormal) {
        this.isNormal = isNormal;
    }

    public String getSituationInfo() {
        return situationInfo;
    }

    public void setSituationInfo(String situationInfo) {
        this.situationInfo = situationInfo;
    }

    public BigDecimal getAverage7() {
        return average7;
    }

    public void setAverage7(BigDecimal average7) {
        this.average7 = average7;
    }

    public BigDecimal getFluctuateRate() {
        return fluctuateRate;
    }

    public void setFluctuateRate(BigDecimal fluctuateRate) {
        this.fluctuateRate = fluctuateRate;
    }

    public String getTableNameEn() {
        return tableNameEn;
    }

    public void setTableNameEn(String tableNameEn) {
        this.tableNameEn = tableNameEn;
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public BigDecimal getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(BigDecimal totalCount) {
        this.totalCount = totalCount;
    }

    public BigDecimal getIncrementCount() {
        return incrementCount;
    }

    public void setIncrementCount(BigDecimal incrementCount) {
        this.incrementCount = incrementCount;
    }
}
