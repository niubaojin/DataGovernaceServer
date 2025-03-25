package com.synway.datarelation.pojo.monitor.detail;

import java.io.Serializable;
import java.util.Date;

/**
 * @author majia
 * @version 1.0
 * @date 2020/12/16 13:16
 */
public class NormalTaskInfo implements Serializable {
    //任务id
    private String taskId;
    //项目名
    private String projectName;
    //节点名称
    private String nodeName;
    //业务流程名称
    private String businessName;
    //节点类型
    private String prgType;
    //开始时间
    private Date startTime;
    //结束时间
    private Date finishTime;
    //执行时长
    private Long runningTime;
    //输出表个数
    private Integer outTableNum;
    //最后输出记录数
    private Long outRecordsNum;
    //执行状态
    private String status;
    //耗用cpu
    private String cpuConsumption;
    //耗用内存
    private String memoryConsumption;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getPrgType() {
        return prgType;
    }

    public void setPrgType(String prgType) {
        this.prgType = prgType;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Long getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(Long runningTime) {
        this.runningTime = runningTime;
    }

    public Integer getOutTableNum() {
        return outTableNum;
    }

    public void setOutTableNum(Integer outTableNum) {
        this.outTableNum = outTableNum;
    }

    public Long getOutRecordsNum() {
        return outRecordsNum;
    }

    public void setOutRecordsNum(Long outRecordsNum) {
        this.outRecordsNum = outRecordsNum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCpuConsumption() {
        return cpuConsumption;
    }

    public void setCpuConsumption(String cpuConsumption) {
        this.cpuConsumption = cpuConsumption;
    }

    public String getMemoryConsumption() {
        return memoryConsumption;
    }

    public void setMemoryConsumption(String memoryConsumption) {
        this.memoryConsumption = memoryConsumption;
    }
}
