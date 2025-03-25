package com.synway.property.pojo.organizationdetail;

/**
 * @ClassName ResourceRegisterInfo
 * @Author majia
 * @Date 2020/5/1 19:28
 * @Version 1.0
 **/
public class ResourceRegisterInfo {

    private String isFormal;//表状态
    private String applicatName;//负责人
    private String updateType;//更新周期
    private String partitionType;//更新方式

    public String getIsFormal() {
        return isFormal;
    }

    public void setIsFormal(String isFormal) {
        this.isFormal = isFormal;
    }

    public String getApplicatName() {
        return applicatName;
    }

    public void setApplicatName(String applicatName) {
        this.applicatName = applicatName;
    }

    public String getUpdateType() {
        return updateType;
    }

    public void setUpdateType(String updateType) {
        this.updateType = updateType;
    }

    public String getPartitionType() {
        return partitionType;
    }

    public void setPartitionType(String partitionType) {
        this.partitionType = partitionType;
    }
}
