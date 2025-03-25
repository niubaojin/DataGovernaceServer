package com.synway.datastandardmanager.pojo.approvalInfo;


/**
 * 流程审批的传参
 */
public class ApprovalInfoParams {
    public static final String STANDARD_TABLE="standardTable";
    public static final String CREATE_TABLE="createTable";
    public static final String ADD_TABLE_COLUMN="addTableColumn";
    private String  approvalId = "";
    private String  moduleId = "standardTable";    //  建表还是createTable，标准表standardTable
    private String moduleName; //模块名称，如标准管理
    private String operationName;//操作名称 ，如建表
    private String applicationInfo;//申请内容
    private String callbackData;//回传表单数据，json格式
    private String callbackUrl;  //流程结束后，回调的业务系统处理方法的地址
    private String status;  // //状态(1:审批中;2:退回)


    @Override
    public String toString() {
        return "ApprovalInfoParams{" +
                "approvalId='" + approvalId + '\'' +
                ", moduleId='" + moduleId + '\'' +
                ", moduleName='" + moduleName + '\'' +
                ", operationName='" + operationName + '\'' +
                ", applicationInfo='" + applicationInfo + '\'' +
                ", callbackData='" + callbackData + '\'' +
                ", callbackUrl='" + callbackUrl + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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
}
