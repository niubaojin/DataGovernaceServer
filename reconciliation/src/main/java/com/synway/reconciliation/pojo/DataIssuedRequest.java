package com.synway.reconciliation.pojo;

/**
 * 数据下发统计信息查询条件
 * @author ywj
 */
public class DataIssuedRequest {
    /**
     * 统计时间
     */
    private String statisticTime;
    /**
     * 下发目标
     */
    private String dz;
    /**
     * 数据表名
     */
    private String tableName;
    /**
     * 数据中文名
     */
    private String name;

    public String getStatisticTime() {
        return statisticTime;
    }

    public void setStatisticTime(String statisticTime) {
        this.statisticTime = statisticTime;
    }

    public String getDz() {
        return dz;
    }

    public void setDz(String dz) {
        this.dz = dz;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
