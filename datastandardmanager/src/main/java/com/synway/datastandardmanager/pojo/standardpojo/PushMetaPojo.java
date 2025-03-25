package com.synway.datastandardmanager.pojo.standardpojo;

import java.io.Serializable;
import java.util.List;

public class PushMetaPojo implements Serializable {

    private String userId;

    private String userName;


    private List<PushMetaInfo> pushMetaInfoList;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<PushMetaInfo> getPushMetaInfoList() {
        return pushMetaInfoList;
    }

    public void setPushMetaInfoList(List<PushMetaInfo> pushMetaInfoList) {
        this.pushMetaInfoList = pushMetaInfoList;
    }
}
