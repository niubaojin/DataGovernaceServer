package com.synway.datarelation.pojo.datawork;

public class TaskRunAnalyze {
    // 任务实例ID
    private String taskId;

    // 历史任务自增ID
    private String id;

    // 源表类型
    private String sourceType= "";
    // 源表名
    private String sourceTableName= "";
    // 源表存储所在的项目名
    private String sourceTableProject= "";
    // 目标表类型
    private String targetType= "";
    // 目标表名
    private String targetTableName= "";
    // 目标项目名
    private String targetTableProject= "";
    //任务总计耗时(精确到秒)
    private String taskTotalTime = "";
    //任务平均流量
    private String taskAverageTraffic = "";
    //记录写入速度
    private String recordWritingSpeed= "";
    // 读出记录总数
    private Integer readRecords= 0;
    // 读写失败总数
    private Integer readFailureRecords = 0;
    // 任务启动时刻
    private String taskStartTime = "";
    // 任务结束时刻
    private String taskEndTime = "";

    //同步状态  6：成功  5：失败
    private Integer synchronizationStatus = 5;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskStartTime() {
        return taskStartTime;
    }

    public void setTaskStartTime(String taskStartTime) {
        this.taskStartTime = taskStartTime;
    }

    public String getTaskEndTime() {
        return taskEndTime;
    }

    public void setTaskEndTime(String taskEndTime) {
        this.taskEndTime = taskEndTime;
    }

    public Integer getSynchronizationStatus() {
        return synchronizationStatus;
    }

    public void setSynchronizationStatus(Integer synchronizationStatus) {
        this.synchronizationStatus = synchronizationStatus;
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

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public String getTargetTableName() {
        return targetTableName;
    }

    public void setTargetTableName(String targetTableName) {
        this.targetTableName = targetTableName;
    }

    public String getTargetTableProject() {
        return targetTableProject;
    }

    public void setTargetTableProject(String targetTableProject) {
        this.targetTableProject = targetTableProject;
    }

    public String getSourceTableName() {
        return sourceTableName;
    }

    public void setSourceTableName(String sourceTableName) {
        this.sourceTableName = sourceTableName;
    }

    public String getSourceTableProject() {
        return sourceTableProject;
    }

    public void setSourceTableProject(String sourceTableProject) {
        this.sourceTableProject = sourceTableProject;
    }
}
