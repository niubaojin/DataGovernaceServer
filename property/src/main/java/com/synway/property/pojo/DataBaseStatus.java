package com.synway.property.pojo;

import java.io.Serializable;

/**
 * 数据组织监控页面中 数据库状况的实体类
 *
 * @author majia
 * @date 2020/06/02
 */
public class DataBaseStatus implements Serializable {
    private String odpsLiveRote;
    // odps物理存储 GB
    private Long odpsPhysicalStorage;
    // odps记录数  条
    private Long odpsRecordsCount;
    // ads物理存储
    private Long adsPhysicalStorage;
    // ads记录数  条
    private Long adsRecordsCount;

    @Override
    public String toString() {
        return "DataBaseStatus{" +
                "odpsLiveRote='" + odpsLiveRote + '\'' +
                ", odpsPhysicalStorage=" + odpsPhysicalStorage +
                ", odpsRecordsCount=" + odpsRecordsCount +
                ", adsPhysicalStorage=" + adsPhysicalStorage +
                ", adsRecordsCount=" + adsRecordsCount +
                '}';
    }

    public String getOdpsLiveRote() {
        return odpsLiveRote;
    }

    public void setOdpsLiveRote(String odpsLiveRote) {
        this.odpsLiveRote = odpsLiveRote;
    }

    public Long getOdpsPhysicalStorage() {
        return odpsPhysicalStorage;
    }

    public void setOdpsPhysicalStorage(Long odpsPhysicalStorage) {
        this.odpsPhysicalStorage = odpsPhysicalStorage;
    }

    public Long getOdpsRecordsCount() {
        return odpsRecordsCount;
    }

    public void setOdpsRecordsCount(Long odpsRecordsCount) {
        this.odpsRecordsCount = odpsRecordsCount;
    }

    public Long getAdsPhysicalStorage() {
        return adsPhysicalStorage;
    }

    public void setAdsPhysicalStorage(Long adsPhysicalStorage) {
        this.adsPhysicalStorage = adsPhysicalStorage;
    }

    public Long getAdsRecordsCount() {
        return adsRecordsCount;
    }

    public void setAdsRecordsCount(Long adsRecordsCount) {
        this.adsRecordsCount = adsRecordsCount;
    }
}
