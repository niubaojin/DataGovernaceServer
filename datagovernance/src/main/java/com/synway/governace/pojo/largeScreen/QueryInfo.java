package com.synway.governace.pojo.largeScreen;

/**
 * 查询条件
 *
 * @author ywj
 * @date 2020/7/23 14:26
 */
public class QueryInfo {
    // 数据id
    private String dataId;
    // 数据时间
    private String dataTime;
    // 开始时间
    private String startTime;
    // 结束时间
    private String endTime;

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
