package com.synway.datarelation.pojo.modelmonitor;

/**
 * @author
 * @date 2019/4/24 10:18
 */
public class ModelNodeInsRelationInfo {

    private String childDagId;
    private String childInsId;
    private String createTime;
    private String createUser;
    private String modifyTime;
    private String modifyUser;
    private String parentInsId;
    private String relationType;

    public String getChildDagId() {
        return childDagId;
    }

    public void setChildDagId(String childDagId) {
        this.childDagId = childDagId;
    }

    public String getChildInsId() {
        return childInsId;
    }

    public void setChildInsId(String childInsId) {
        this.childInsId = childInsId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public String getParentInsId() {
        return parentInsId;
    }

    public void setParentInsId(String parentInsId) {
        this.parentInsId = parentInsId;
    }

    public String getRelationType() {
        return relationType;
    }

    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }
}
