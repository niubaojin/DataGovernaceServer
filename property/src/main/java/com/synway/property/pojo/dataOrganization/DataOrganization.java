package com.synway.property.pojo.dataOrganization;

import java.util.Objects;

/**
 * @author majia
 * @version 1.0
 * @date 2020/10/16 17:08
 */
public class DataOrganization {

    /**
     * 标准id
     */
    private String objectId;

    /**
     * 中文表名
     */
    private String tableNameCh;
    /**
     * 资源标识
     */
    private String resourceIdentify;
    /**
     * 物理表名
     */
    private String tableNameEn;
    /**
     * 存储位置
     */
    private String storageLocation;
    /**
     * 资源所属数据中心
     */
    private String dataCenter;
    /**
     * 数据量
     */
    private String dataNum;
    /**
     * 来源厂商
     */
    private String manufacturer;
    /**
     * 事权单位
     */
    private String authority;

    private String resourceId;

    private String registerState;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DataOrganization)) {
            return false;
        }
        DataOrganization that = (DataOrganization) o;
        return getResourceIdentify().equals(that.getResourceIdentify());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getResourceIdentify());
    }

    public String getRegisterState() {
        return registerState;
    }

    public void setRegisterState(String registerState) {
        this.registerState = registerState;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getTableNameCh() {
        return tableNameCh;
    }

    public void setTableNameCh(String tableNameCh) {
        this.tableNameCh = tableNameCh;
    }

    public String getResourceIdentify() {
        return resourceIdentify;
    }

    public void setResourceIdentify(String resourceIdentify) {
        this.resourceIdentify = resourceIdentify;
    }

    public String getTableNameEn() {
        return tableNameEn;
    }

    public void setTableNameEn(String tableNameEn) {
        this.tableNameEn = tableNameEn;
    }

    public String getStorageLocation() {
        return storageLocation;
    }

    public void setStorageLocation(String storageLocation) {
        this.storageLocation = storageLocation;
    }

    public String getDataCenter() {
        return dataCenter;
    }

    public void setDataCenter(String dataCenter) {
        this.dataCenter = dataCenter;
    }

    public String getDataNum() {
        return dataNum;
    }

    public void setDataNum(String dataNum) {
        this.dataNum = dataNum;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getResourceId() { return resourceId; }

    public void setResourceId(String resourceId) { this.resourceId = resourceId; }

    @Override
    public String toString() {
        return "DataOrganization{" +
                "objectId='" + objectId + '\'' +
                ", tableNameCh='" + tableNameCh + '\'' +
                ", resourceIdentify='" + resourceIdentify + '\'' +
                ", tableNameEn='" + tableNameEn + '\'' +
                ", storageLocation='" + storageLocation + '\'' +
                ", dataCenter='" + dataCenter + '\'' +
                ", dataNum='" + dataNum + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", authority='" + authority + '\'' +
                '}';
    }
}
