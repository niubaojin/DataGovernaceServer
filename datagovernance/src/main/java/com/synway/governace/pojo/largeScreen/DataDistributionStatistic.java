package com.synway.governace.pojo.largeScreen;

/**
 * 新疆下发数据统计的结果表
 * @author wangdongwei
 * @date 2021/4/29 17:18
 */
public class DataDistributionStatistic {
    /**
     * 表英文名
     */
    private String tableName;
    /**
     * 数据名称
     */
    private String name;
    /**
     * 下发的数据量
     */
    private String totalcount;
    /**
     * 统计时间  yyyyMMdd
     */
    private String statistictime;
    /**
     * 分区时间
     */
    private String partitiontime;
    /**
     * dt
     */
    private String dt;
    /**
     * 地州名称
     */
    private String dz;

    private String totalTypes;

    public String getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(String totalcount) {
        this.totalcount = totalcount;
    }

    public String getTotalTypes() {
        return totalTypes;
    }

    public void setTotalTypes(String totalTypes) {
        this.totalTypes = totalTypes;
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


    public String getStatistictime() {
        return statistictime;
    }

    public void setStatistictime(String statistictime) {
        this.statistictime = statistictime;
    }

    public String getPartitiontime() {
        return partitiontime;
    }

    public void setPartitiontime(String partitiontime) {
        this.partitiontime = partitiontime;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getDz() {
        return dz;
    }

    public void setDz(String dz) {
        this.dz = dz;
    }
}
