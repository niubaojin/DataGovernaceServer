package com.synway.datarelation.pojo.datawork.v2;


/**
 * 日志查询的参数
 */
public class LogQueryParameters {
    private Integer nodeInsId; //任务实例的 ID
    private Integer historyInsId; //任务实例历史运行记录的 ID

    public Integer getNodeInsId() {
        return nodeInsId;
    }

    public void setNodeInsId(Integer nodeInsId) {
        this.nodeInsId = nodeInsId;
    }

    public Integer getHistoryInsId() {
        return historyInsId;
    }

    public void setHistoryInsId(Integer historyInsId) {
        this.historyInsId = historyInsId;
    }
}
