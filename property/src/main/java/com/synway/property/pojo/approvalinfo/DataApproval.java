package com.synway.property.pojo.approvalinfo;
/**
 *
 *
 * @author majia
 * @date 2020/06/02
 */
public class DataApproval {
    private String tableId;//协议编号
    private String tableNameEn;//英文表名
    private String objectId;//标准表id
    private String tableProject;//项目名称
    private String approvalId;//审批id
    private String approvalStatus;//审批状态
    private String approvalCreateTime;//审批创建时间
    private String approvalUpdateTime;//审批更新时间
    private String approvalType;//提价审批的类型

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getTableNameEn() {
        return tableNameEn;
    }

    public void setTableNameEn(String tableNameEn) {
        this.tableNameEn = tableNameEn;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getApprovalId() {
        return approvalId;
    }

    public void setApprovalId(String approvalId) {
        this.approvalId = approvalId;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getApprovalCreateTime() {
        return approvalCreateTime;
    }

    public void setApprovalCreateTime(String approvalCreateTime) {
        this.approvalCreateTime = approvalCreateTime;
    }

    public String getApprovalUpdateTime() {
        return approvalUpdateTime;
    }

    public void setApprovalUpdateTime(String approvalUpdateTime) {
        this.approvalUpdateTime = approvalUpdateTime;
    }

    public String getApprovalType() {
        return approvalType;
    }

    public void setApprovalType(String approvalType) {
        this.approvalType = approvalType;
    }

    public String getTableProject() {
        return tableProject;
    }

    public void setTableProject(String tableProject) {
        this.tableProject = tableProject;
    }

    @Override
    public String toString() {
        return "DataApproval{" +
                "tableId='" + tableId + '\'' +
                ", tableNameEn='" + tableNameEn + '\'' +
                ", objectId='" + objectId + '\'' +
                ", tableProject='" + tableProject + '\'' +
                ", approvalId='" + approvalId + '\'' +
                ", approvalStatus='" + approvalStatus + '\'' +
                ", approvalCreateTime='" + approvalCreateTime + '\'' +
                ", approvalUpdateTime='" + approvalUpdateTime + '\'' +
                ", approvalType='" + approvalType + '\'' +
                '}';
    }
}
