package com.synway.governace.pojo.largeScreen;

import java.math.BigDecimal;

/**
 * 统计结果
 *
 * @author ywj
 * @date 2020/7/22 14:56
 */
public class StatisticsResult {
    // 数据名称
    private String dataName;
    // 种类数量
    private BigDecimal typeCount;
    // 数据数量
    private BigDecimal dataCount;
    // 今日更新数
    private BigDecimal todayUpdateCount;
    // 近7天更新数
    private BigDecimal recentUpdateCount;
    // 数据时间
    private String dataTime;
    // 数据id
    private String dataId;

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public BigDecimal getTypeCount() {
        return typeCount;
    }

    public void setTypeCount(BigDecimal typeCount) {
        this.typeCount = typeCount;
    }

    public BigDecimal getDataCount() {
        return dataCount;
    }

    public void setDataCount(BigDecimal dataCount) {
        this.dataCount = dataCount;
    }

    public BigDecimal getTodayUpdateCount() {
        return todayUpdateCount;
    }

    public void setTodayUpdateCount(BigDecimal todayUpdateCount) {
        this.todayUpdateCount = todayUpdateCount;
    }

    public BigDecimal getRecentUpdateCount() {
        return recentUpdateCount;
    }

    public void setRecentUpdateCount(BigDecimal recentUpdateCount) {
        this.recentUpdateCount = recentUpdateCount;
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }
}
