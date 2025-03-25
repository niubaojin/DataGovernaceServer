package com.synway.property.pojo.approvalinfo;
/**
 *
 *
 * @author majia
 * @date 2020/06/02
 */
public class ApprovalStartInfo {

    private String approvalId;
    private String operatorId;
    private String operatorName;
    private String operatorOrganId;
    private String moduleName;
    private String operationName;

    @Override
    public String toString() {
        return "ApprovalStartInfo{" +
                "approvalId='" + approvalId + '\'' +
                ", operatorId='" + operatorId + '\'' +
                ", operatorName='" + operatorName + '\'' +
                ", operatorOrganId='" + operatorOrganId + '\'' +
                ", moduleName='" + moduleName + '\'' +
                ", operationName='" + operationName + '\'' +
                '}';
    }

    public String getApprovalId() {
        return approvalId;
    }

    public void setApprovalId(String approvalId) {
        this.approvalId = approvalId;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperatorOrganId() {
        return operatorOrganId;
    }

    public void setOperatorOrganId(String operatorOrganId) {
        this.operatorOrganId = operatorOrganId;
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
}
