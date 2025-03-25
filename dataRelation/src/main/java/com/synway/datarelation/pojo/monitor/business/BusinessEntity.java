package com.synway.datarelation.pojo.monitor.business;

import java.util.Objects;

/**
 * @author majia
 * @version 1.0
 * @date 2021/1/19 16:34
 */
public class BusinessEntity {

    private Long appId;
    private Long flowId;
    private Integer bizId;
    private String bizName;
    private String projectName;
    private String nodeIds;


    public Long getAppId() {
        return appId;
    }

    public BusinessEntity setAppId(Long appId) {
        this.appId = appId;
        return this;
    }

    public Long getFlowId() {
        return flowId;
    }

    public BusinessEntity setFlowId(Long flowId) {
        this.flowId = flowId;
        return this;
    }

    public Integer getBizId() {
        return bizId;
    }

    public BusinessEntity setBizId(Integer bizId) {
        this.bizId = bizId;
        return this;
    }

    public String getBizName() {
        return bizName;
    }

    public BusinessEntity setBizName(String bizName) {
        this.bizName = bizName;
        return this;
    }

    public String getProjectName() {
        return projectName;
    }

    public BusinessEntity setProjectName(String projectName) {
        this.projectName = projectName;
        return this;
    }

    public String getNodeIds() {
        return nodeIds;
    }

    public BusinessEntity setNodeIds(String nodeIds) {
        this.nodeIds = nodeIds;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BusinessEntity)) {
            return false;
        }
        BusinessEntity that = (BusinessEntity) o;
        return Objects.equals(getAppId(), that.getAppId()) &&
                Objects.equals(getFlowId(), that.getFlowId()) &&
                Objects.equals(getBizId(), that.getBizId()) &&
                Objects.equals(getBizName(), that.getBizName()) &&
                Objects.equals(getProjectName(), that.getProjectName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAppId(), getFlowId(), getBizId(), getBizName(), getProjectName());
    }

    @Override
    public String toString() {
        return "BusinessEntity{" +
                "appId=" + appId +
                ", flowId=" + flowId +
                ", bizId=" + bizId +
                ", bizName='" + bizName + '\'' +
                ", projectName='" + projectName + '\'' +
                ", nodeIds='" + nodeIds + '\'' +
                '}';
    }
}

