package com.synway.governace.pojo.process;

/**
 * 审批申请信息
 * @author ywj
 */
public class ApprovalInfo {
    private String approvalId;
    private String moduleName;
    private String operationName;
    private String applicationInfo;
    private String callbackData;
    private String callbackUrl;
    private String status;
    private String moduleId;
    private String operatorId;
    private String taskId;
    private String processinstanceid;
    private String viewUrl;
    private String executeResult;

    public String getApprovalId() {
        return approvalId;
    }

    public void setApprovalId(String approvalId) {
        this.approvalId = approvalId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public String getApplicationInfo() {
        return applicationInfo;
    }

    public void setApplicationInfo(String applicationInfo) {
        this.applicationInfo = applicationInfo;
    }

    public String getCallbackData() {
        return callbackData;
    }

    public void setCallbackData(String callbackData) {
        this.callbackData = callbackData;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getProcessinstanceid() {
        return processinstanceid;
    }

    public void setProcessinstanceid(String processinstanceid) {
        this.processinstanceid = processinstanceid;
    }

    public String getViewUrl() {
        return viewUrl;
    }

    public void setViewUrl(String viewUrl) {
        this.viewUrl = viewUrl;
    }

    public String getExecuteResult() {
        return executeResult;
    }

    public void setExecuteResult(String executeResult) {
        this.executeResult = executeResult;
    }

    @Override
    public String toString() {
        return "ApprovalInfo{" +
                "approvalId='" + approvalId + '\'' +
                ", moduleName='" + moduleName + '\'' +
                ", operationName='" + operationName + '\'' +
                ", applicationInfo='" + applicationInfo + '\'' +
                ", callbackData='" + callbackData + '\'' +
                ", callbackUrl='" + callbackUrl + '\'' +
                ", status='" + status + '\'' +
                ", moduleId='" + moduleId + '\'' +
                ", operatorId='" + operatorId + '\'' +
                ", taskId='" + taskId + '\'' +
                ", processinstanceid='" + processinstanceid + '\'' +
                ", viewUrl='" + viewUrl + '\'' +
                ", executeResult='" + executeResult + '\'' +
                '}';
    }
}
