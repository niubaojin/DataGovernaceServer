package com.synway.datastandardmanager.pojo.buildtable;

import java.io.Serializable;

/**
 * 建表相关的实体类
 * @author
 * @date 2019/1/11 10:31
 */
public class CreateTableVo implements Serializable {

    private String message;
    private Boolean switchFlag;

    private String url;

    public Boolean getSwitchFlag() {
        return switchFlag;
    }

    public void setSwitchFlag(Boolean switchFlag) {
        this.switchFlag = switchFlag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
