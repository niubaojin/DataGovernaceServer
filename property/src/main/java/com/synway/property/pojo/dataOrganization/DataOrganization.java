package com.synway.property.pojo.dataOrganization;

import lombok.Data;

import java.util.Objects;

/**
 * @author majia
 * @version 1.0
 * @date 2020/10/16 17:08
 */
@Data
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
        return getResourceIdentify().equals(that.getResourceIdentify()) && getStorageLocation().equals(that.getStorageLocation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getResourceIdentify(), getStorageLocation());
    }

}
