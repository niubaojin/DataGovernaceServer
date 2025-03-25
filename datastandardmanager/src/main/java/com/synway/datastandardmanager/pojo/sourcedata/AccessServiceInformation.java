package com.synway.datastandardmanager.pojo.sourcedata;

import java.io.Serializable;

/**
 * @author wangdongwei
 * @description 数据仓库那边的接入服务信息
 * @date 2021/1/26 10:52
 */
@Deprecated
public class AccessServiceInformation implements Serializable {
    /**
     * 接入服务方
     */
    private String accessServiceProvider ="";
    /**
     * 接入方式
     */
    private String accessMode="";
    /**
     * 接入服务人
     */
    private String accessServicePersion="";
    /**
     * 接入服务人联系方式
     */
    private String accessServiceProviderPhone="";
    /**
     * 接入说明
     */
    private String accessInstructions="";

    private String accessFormatType ="";

    /**
     * 20210510 新增的内容
     * 数据存储规模、数据量规模、数据更新方式、数据更新周期
     */
    private String dataNumberScale="";
    private String dataStoreScale="";
    private String updateCycle="";
    private String updateType="";

    public String getDataNumberScale() {
        return dataNumberScale;
    }

    public void setDataNumberScale(String dataNumberScale) {
        this.dataNumberScale = dataNumberScale;
    }

    public String getDataStoreScale() {
        return dataStoreScale;
    }

    public void setDataStoreScale(String dataStoreScale) {
        this.dataStoreScale = dataStoreScale;
    }

    public String getUpdateCycle() {
        return updateCycle;
    }

    public void setUpdateCycle(String updateCycle) {
        this.updateCycle = updateCycle;
    }

    public String getUpdateType() {
        return updateType;
    }

    public void setUpdateType(String updateType) {
        this.updateType = updateType;
    }

    public String getAccessFormatType() {
        return accessFormatType;
    }

    public void setAccessFormatType(String accessFormatType) {
        this.accessFormatType = accessFormatType;
    }

    public String getAccessServiceProvider() {
        return accessServiceProvider;
    }

    public void setAccessServiceProvider(String accessServiceProvider) {
        this.accessServiceProvider = accessServiceProvider;
    }

    public String getAccessMode() {
        return accessMode;
    }

    public void setAccessMode(String accessMode) {
        this.accessMode = accessMode;
    }

    public String getAccessServicePersion() {
        return accessServicePersion;
    }

    public void setAccessServicePersion(String accessServicePersion) {
        this.accessServicePersion = accessServicePersion;
    }

    public String getAccessServiceProviderPhone() {
        return accessServiceProviderPhone;
    }

    public void setAccessServiceProviderPhone(String accessServiceProviderPhone) {
        this.accessServiceProviderPhone = accessServiceProviderPhone;
    }

    public String getAccessInstructions() {
        return accessInstructions;
    }

    public void setAccessInstructions(String accessInstructions) {
        this.accessInstructions = accessInstructions;
    }
}
