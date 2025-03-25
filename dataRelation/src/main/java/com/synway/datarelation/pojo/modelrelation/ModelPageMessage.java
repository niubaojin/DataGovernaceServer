package com.synway.datarelation.pojo.modelrelation;

/**
 * 模型血缘页面左上角血缘信息的部分信息
 */
public class ModelPageMessage {

    // 工作流名称
    private String DISPLAY_NAME;
    //创建者
    private String created_by;
    //创建时间
    private String CREATED_ON;
    //最后修改者
    private String last_modified_by;
    //最后修改时间
    private String last_modified_on;
    //  发布状态
    private String deploy_status;
    //  提交状态
    private String commit_status;

    public String getDISPLAY_NAME() {
        return DISPLAY_NAME;
    }

    public void setDISPLAY_NAME(String DISPLAY_NAME) {
        this.DISPLAY_NAME = DISPLAY_NAME;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getCREATED_ON() {
        return CREATED_ON;
    }

    public void setCREATED_ON(String CREATED_ON) {
        this.CREATED_ON = CREATED_ON;
    }

    public String getLast_modified_by() {
        return last_modified_by;
    }

    public void setLast_modified_by(String last_modified_by) {
        this.last_modified_by = last_modified_by;
    }

    public String getLast_modified_on() {
        return last_modified_on;
    }

    public void setLast_modified_on(String last_modified_on) {
        this.last_modified_on = last_modified_on;
    }

    public String getDeploy_status() {
        return deploy_status;
    }

    public void setDeploy_status(String deploy_status) {
        this.deploy_status = deploy_status;
    }

    public String getCommit_status() {
        return commit_status;
    }

    public void setCommit_status(String commit_status) {
        this.commit_status = commit_status;
    }

    @Override
    public String toString() {
        return "ModelPageMessage{" +
                "DISPLAY_NAME='" + DISPLAY_NAME + '\'' +
                ", created_by='" + created_by + '\'' +
                ", CREATED_ON='" + CREATED_ON + '\'' +
                ", last_modified_by='" + last_modified_by + '\'' +
                ", last_modified_on='" + last_modified_on + '\'' +
                ", deploy_status='" + deploy_status + '\'' +
                ", commit_status='" + commit_status + '\'' +
                '}';
    }
}
