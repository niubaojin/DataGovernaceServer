package com.synway.property.pojo;

import java.util.List;

/**
 * 通过一级分类来获取对应的总记录数和存储数
 * 按照 数据组织，数据资源来源，数据资源分类
 * @author majia
 */
public class NumStorageByPrimaryClassify {
    private String cateGoryName;
    private List<String> primaryClassifyList;
    //  单位 亿
    private List<Double> recordsNumber;
    //  单位  GB
    private List<Double> storageSize;
    // 单位
    private String recordsUnit;
    private  String storageUnit;

    @Override
    public String toString() {
        return "NumStorageByPrimaryClassify{" +
                "cateGoryName='" + cateGoryName + '\'' +
                ", primaryClassifyList=" + primaryClassifyList +
                ", recordsNumber=" + recordsNumber +
                ", storageSize=" + storageSize +
                ", recordsUnit='" + recordsUnit + '\'' +
                ", storageUnit='" + storageUnit + '\'' +
                '}';
    }

    public String getRecordsUnit() {
        return recordsUnit;
    }

    public void setRecordsUnit(String recordsUnit) {
        this.recordsUnit = recordsUnit;
    }

    public String getStorageUnit() {
        return storageUnit;
    }

    public void setStorageUnit(String storageUnit) {
        this.storageUnit = storageUnit;
    }

    public String getCateGoryName() {
        return cateGoryName;
    }

    public void setCateGoryName(String cateGoryName) {
        this.cateGoryName = cateGoryName;
    }

    public List<String> getPrimaryClassifyList() {
        return primaryClassifyList;
    }

    public void setPrimaryClassifyList(List<String> primaryClassifyList) {
        this.primaryClassifyList = primaryClassifyList;
    }

    public List<Double> getRecordsNumber() {
        return recordsNumber;
    }

    public void setRecordsNumber(List<Double> recordsNumber) {
        this.recordsNumber = recordsNumber;
    }

    public List<Double> getStorageSize() {
        return storageSize;
    }

    public void setStorageSize(List<Double> storageSize) {
        this.storageSize = storageSize;
    }
}
