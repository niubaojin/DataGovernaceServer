package com.synway.governace.pojo.largeScreen;

import java.util.Date;

/**
 * 资产大屏的数据库存储类
 * @author wangdongwei
 * @date 2021/4/30 15:59
 */
public class PropertyLargeDbData {
    private String id;
    private String data;
    private Date createTime;
    private int type;
    private int saveFlag ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSaveFlag() {
        return saveFlag;
    }

    public void setSaveFlag(int saveFlag) {
        this.saveFlag = saveFlag;
    }
}
