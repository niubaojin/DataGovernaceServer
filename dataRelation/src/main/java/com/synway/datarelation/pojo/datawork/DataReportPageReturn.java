package com.synway.datarelation.pojo.datawork;

import java.io.Serializable;

/**
 * 数据同步报表每一行对应的数据
 */
public class DataReportPageReturn implements Serializable {
    // 序号
    private Integer rowNumber;
    // 任务实例ID
    private Long taskId;
    // 历史任务自增ID
    private Long id;
    //工作流节点ID
    private Long nodeId;
    // 工作流节点名称
    private String nodeName;
    // 工作流节点所属的项目名
    private String nodeFlowName;
    // 源表名
    private String sourceTableName;
    // 源表存储位置
    private String sourceType;
    // 源表所在库
    private String sourceTableProject;
    //目标表名
    private String targetTableName;
    //目标表存储位置
    private String targetType;
    //目标表所在库
    private String targetTableProject;
    //同步周期
    private String cronExpress;
    //任务创建时刻
    private String createTime;
    //任务启动时刻
    private String beginRunningTime;
    //任务结束时刻
    private String finishTime;
    //任务运行耗时
    private String taskTotalTime;
    //任务平均流量
    private String taskAverageTraffic;
    //任务写入速度
    private String recordWritingSpeed;
    //读出记录总数
    private Integer readRecords;
    //读写失败总数
    private Integer readFailureRecords;
    // 写入成功总数
    private Integer writeRecords;
    // 同步状态
    private Integer synchronizationStatus;
    // 同步状态
    private String status;

    public String getNodeFlowName() {
        return nodeFlowName;
    }

    public void setNodeFlowName(String nodeFlowName) {
        this.nodeFlowName = nodeFlowName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(Integer rowNumber) {
        this.rowNumber = rowNumber;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getSourceTableName() {
        return sourceTableName;
    }

    public void setSourceTableName(String sourceTableName) {
        this.sourceTableName = sourceTableName;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getSourceTableProject() {
        return sourceTableProject;
    }

    public void setSourceTableProject(String sourceTableProject) {
        this.sourceTableProject = sourceTableProject;
    }

    public String getTargetTableName() {
        return targetTableName;
    }

    public void setTargetTableName(String targetTableName) {
        this.targetTableName = targetTableName;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public String getTargetTableProject() {
        return targetTableProject;
    }

    public void setTargetTableProject(String targetTableProject) {
        this.targetTableProject = targetTableProject;
    }

    public String getCronExpress() {
        return cronExpress;
    }

    public void setCronExpress(String cronExpress) {
        this.cronExpress = cronExpress;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getBeginRunningTime() {
        return beginRunningTime;
    }

    public void setBeginRunningTime(String beginRunningTime) {
        this.beginRunningTime = beginRunningTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getTaskTotalTime() {
        return taskTotalTime;
    }

    public void setTaskTotalTime(String taskTotalTime) {
        this.taskTotalTime = taskTotalTime;
    }

    public String getTaskAverageTraffic() {
        return taskAverageTraffic;
    }

    public void setTaskAverageTraffic(String taskAverageTraffic) {
        this.taskAverageTraffic = taskAverageTraffic;
    }

    public String getRecordWritingSpeed() {
        return recordWritingSpeed;
    }

    public void setRecordWritingSpeed(String recordWritingSpeed) {
        this.recordWritingSpeed = recordWritingSpeed;
    }

    public Integer getReadRecords() {
        return readRecords;
    }

    public void setReadRecords(Integer readRecords) {
        this.readRecords = readRecords;
    }

    public Integer getReadFailureRecords() {
        return readFailureRecords;
    }

    public void setReadFailureRecords(Integer readFailureRecords) {
        this.readFailureRecords = readFailureRecords;
    }

    public Integer getWriteRecords() {
        return writeRecords;
    }

    public void setWriteRecords(Integer writeRecords) {
        this.writeRecords = writeRecords;
    }

    public Integer getSynchronizationStatus() {
        return synchronizationStatus;
    }

    public void setSynchronizationStatus(Integer synchronizationStatus) {
        this.synchronizationStatus = synchronizationStatus;
    }
}
