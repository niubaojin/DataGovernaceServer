package com.synway.property.pojo;

/**
 * 在重点组织中添加需要监控的表的实体类
 *
 * @author 数据接入
 * @date 2020/06/02
 */
public class ClassifyInformation {
    private String mainClassifyCH;
    private String primaryClassifyCode;
    private String primaryClassifyCH;
    private String secondaryClassifyCode;
    private String secondaryClassifyCH;
    private Boolean alreadyInserted;
    private int userId;
    private String userAuthorityId;
    private String userName;
    private String isAdmin;

    @Override
    public String toString() {
        return "ClassifyInformation{" +
                "mainClassifyCH='" + mainClassifyCH + '\'' +
                ", primaryClassifyCode='" + primaryClassifyCode + '\'' +
                ", primaryClassifyCH='" + primaryClassifyCH + '\'' +
                ", secondaryClassifyCode='" + secondaryClassifyCode + '\'' +
                ", secondaryClassifyCH='" + secondaryClassifyCH + '\'' +
                ", alreadyInserted=" + alreadyInserted +
                '}';
    }

    public int getUserId() { return userId; }

    public void setUserId(int userId) { this.userId = userId; }

    public String getUserName() { return userName; }

    public void setUserName(String userName) { this.userName = userName; }

    public Boolean getAlreadyInserted() {
        return alreadyInserted;
    }

    public void setAlreadyInserted(Boolean alreadyInserted) {
        this.alreadyInserted = alreadyInserted;
    }

    public String getPrimaryClassifyCode() {
        return primaryClassifyCode;
    }

    public void setPrimaryClassifyCode(String primaryClassifyCode) {
        this.primaryClassifyCode = primaryClassifyCode;
    }

    public String getSecondaryClassifyCode() {
        return secondaryClassifyCode;
    }

    public void setSecondaryClassifyCode(String secondaryClassifyCode) {
        this.secondaryClassifyCode = secondaryClassifyCode;
    }

    public String getMainClassifyCH() {
        return mainClassifyCH;
    }

    public void setMainClassifyCH(String mainClassifyCH) {
        this.mainClassifyCH = mainClassifyCH;
    }

    public String getPrimaryClassifyCH() {
        return primaryClassifyCH;
    }

    public void setPrimaryClassifyCH(String primaryClassifyCH) {
        this.primaryClassifyCH = primaryClassifyCH;
    }

    public String getSecondaryClassifyCH() {
        return secondaryClassifyCH;
    }

    public void setSecondaryClassifyCH(String secondaryClassifyCH) {
        this.secondaryClassifyCH = secondaryClassifyCH;
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getUserAuthorityId() {
        return userAuthorityId;
    }

    public void setUserAuthorityId(String userAuthorityId) {
        this.userAuthorityId = userAuthorityId;
    }
}
