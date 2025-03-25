package com.synway.datarelation.pojo.monitor.page;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * 工作流监控每一行对应的数据
 * @author 数据接入
 */
public class NormalBusinessInfo implements Serializable {
    private long appId;
    private long flowId;
    private int bizId;
    private String name;
    private String nodeName;
    private Date bizDate;
    private String projectName;
    private Date startTime;
    private Date finishTime;
    //执行状态
    private String status;
    //cpu消耗峰值
    private String cpuConsumption;
    //内存消耗峰值
    private String memoryConsumption;
    //执行时长
    private long runningTime;
    //任务id
    private String taskIds;
    //节点id
    private String nodeIds;

    public String getNodeName() {
        return nodeName;
    }

    public NormalBusinessInfo setNodeName(String nodeName) {
        this.nodeName = nodeName;
        return this;
    }

    public long getAppId() {
        return appId;
    }

    public NormalBusinessInfo setAppId(long appId) {
        this.appId = appId;
        return this;
    }

    public long getFlowId() {
        return flowId;
    }

    public NormalBusinessInfo setFlowId(long flowId) {
        this.flowId = flowId;
        return this;
    }

    public int getBizId() {
        return bizId;
    }

    public NormalBusinessInfo setBizId(int bizId) {
        this.bizId = bizId;
        return this;
    }

    public String getName() {
        return name;
    }

    public NormalBusinessInfo setName(String name) {
        this.name = name;
        return this;
    }

    public Date getBizDate() {
        return bizDate;
    }

    public NormalBusinessInfo setBizDate(Date bizDate) {
        this.bizDate = bizDate;
        return this;
    }

    public String getProjectName() {
        return projectName;
    }

    public NormalBusinessInfo setProjectName(String projectName) {
        this.projectName = projectName;
        return this;
    }

    public Date getStartTime() {
        return startTime;
    }

    public NormalBusinessInfo setStartTime(Date startTime) {
        this.startTime = startTime;
        return this;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public NormalBusinessInfo setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public NormalBusinessInfo setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getCpuConsumption() {
        return cpuConsumption;
    }

    public NormalBusinessInfo setCpuConsumption(String cpuConsumption) {
        this.cpuConsumption = cpuConsumption;
        return this;
    }

    public String getMemoryConsumption() {
        return memoryConsumption;
    }

    public NormalBusinessInfo setMemoryConsumption(String memoryConsumption) {
        this.memoryConsumption = memoryConsumption;
        return this;
    }

    public long getRunningTime() {
        return runningTime;
    }

    public NormalBusinessInfo setRunningTime(long runningTime) {
        this.runningTime = runningTime;
        return this;
    }

    public String getTaskIds() {
        return taskIds;
    }

    public NormalBusinessInfo setTaskIds(String taskIds) {
        this.taskIds = taskIds;
        return this;
    }

    public String getNodeIds() {
        return nodeIds;
    }

    public NormalBusinessInfo setNodeIds(String nodeIds) {
        this.nodeIds = nodeIds;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NormalBusinessInfo that = (NormalBusinessInfo) o;
        return appId == that.appId &&
                flowId == that.flowId &&
                bizId == that.bizId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(appId, flowId, bizId);
    }
}
