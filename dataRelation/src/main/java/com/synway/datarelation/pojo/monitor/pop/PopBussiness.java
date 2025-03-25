package com.synway.datarelation.pojo.monitor.pop;

/**
 * @author majia
 * @version 1.0
 * @date 2021/1/20 14:17
 */
public class PopBussiness {

    private String BizId;
    private String BizName;
    private String BizKey;
    private String AppId;
    private String TenantId;

    public String getBizId() {
        return BizId;
    }

    public void setBizId(String bizId) {
        this.BizId = bizId;
    }

    public String getBizName() {
        return BizName;
    }

    public void setBizName(String bizName) {
        this.BizName = bizName;
    }

    public String getBizKey() {
        return BizKey;
    }

    public void setBizKey(String bizKey) {
        this.BizKey = bizKey;
    }

    public String getAppId() {
        return AppId;
    }

    public void setAppId(String appId) {
        this.AppId = appId;
    }

    public String getTenantId() {
        return TenantId;
    }

    public void setTenantId(String tenantId) {
        this.TenantId = tenantId;
    }
}
