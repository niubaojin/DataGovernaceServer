package com.synway.property.pojo.organizationdetail;

/**
 * @ClassName DataNum
 * @Descroption
 * @Author majia
 * @Date 2020/5/2 16:24
 * @Version 1.0
 **/
public class DataNum {
    private String tableAllCount;//数据总记录
    private String averageDailyCount;//平均日增记录
    private String tableAllSize;//数据总存储量
    private String averageDailySize;//平均日存储量

    public String getTableAllCount() {
        return tableAllCount;
    }

    public void setTableAllCount(String tableAllCount) {
        this.tableAllCount = tableAllCount;
    }

    public String getAverageDailyCount() {
        return averageDailyCount;
    }

    public void setAverageDailyCount(String averageDailyCount) {
        this.averageDailyCount = averageDailyCount;
    }

    public String getTableAllSize() {
        return tableAllSize;
    }

    public void setTableAllSize(String tableAllSize) {
        this.tableAllSize = tableAllSize;
    }

    public String getAverageDailySize() {
        return averageDailySize;
    }

    public void setAverageDailySize(String averageDailySize) {
        this.averageDailySize = averageDailySize;
    }
}
