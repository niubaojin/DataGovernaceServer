package com.synway.property.pojo.datastoragemonitor;

import java.util.Date;

public class UserAuthorityData {
    private String id;              // 业务资源ID
    private String cmnMemo;         //业务资源ID对应信息
    private String moduleCode;      //模块编码
    private String moduleName;      //模块名称
    private String isCreate;        //0：非创建人 1：创建人
    private String userName;        //有访问权限的用户名
    private String cnmName;         //业务资源名
    private int userId;          //有访问权限的用户id
    private Date createTime;      //创建时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCmnMemo() {
        return cmnMemo;
    }

    public void setCmnMemo(String cmnMemo) {
        this.cmnMemo = cmnMemo;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getIsCreate() {
        return isCreate;
    }

    public void setIsCreate(String isCreate) {
        this.isCreate = isCreate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCnmName() {
        return cnmName;
    }

    public void setCnmName(String cnmName) {
        this.cnmName = cnmName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
