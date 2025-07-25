package com.synway.property.pojo;

import lombok.Data;

import java.util.List;

/**
 * 通过一级分类来获取对应的总记录数和存储数
 * 按照 数据组织，数据资源来源，数据资源分类
 * @author majia
 */
@Data
public class NumStorageByPrimaryClassify {
    private String cateGoryName;
    private List<String> primaryClassifyList;
    // 单位：亿
    private List<Double> recordsNumber;
    // 单位：GB
    private List<Double> storageSize;
    // 单位：张
    private List<Integer> tableNum;
    // 单位
    private String recordsUnit;
    private String storageUnit;
    private String tableNumUnit;

}
