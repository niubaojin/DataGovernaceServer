package com.synway.governace.pojo.largeScreen;

/**
 * 数据接入信息
 *
 * @author ywj
 * @date 2020/7/31 10:33
 */
public class DataInceptInfo {
    // 节点信息
    private String nodeInstanceId;
    // 统计信息json串
    private String statisticStr;
    // sourceId等附加信息json串
    private String extraInfoStr;
    // 数据量
    private long dataCount;
    // 协议id
    private String sourceId;
    // 数据时间
    private String dataTime;
    // datax配置信息json串
    private String jobDataxJson;
    // 数据源id
    private String dataId;

    public String getNodeInstanceId() {
        return nodeInstanceId;
    }

    public void setNodeInstanceId(String nodeInstanceId) {
        this.nodeInstanceId = nodeInstanceId;
    }

    public String getStatisticStr() {
        return statisticStr;
    }

    public void setStatisticStr(String statisticStr) {
        this.statisticStr = statisticStr;
    }

    public String getExtraInfoStr() {
        return extraInfoStr;
    }

    public void setExtraInfoStr(String extraInfoStr) {
        this.extraInfoStr = extraInfoStr;
    }

    public long getDataCount() {
        return dataCount;
    }

    public void setDataCount(long dataCount) {
        this.dataCount = dataCount;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public String getJobDataxJson() {
        return jobDataxJson;
    }

    public void setJobDataxJson(String jobDataxJson) {
        this.jobDataxJson = jobDataxJson;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }
}
