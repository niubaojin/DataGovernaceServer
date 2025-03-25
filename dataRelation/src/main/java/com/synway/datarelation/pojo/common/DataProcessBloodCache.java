package com.synway.datarelation.pojo.common;



import com.synway.datarelation.pojo.databloodline.QueryBloodlineRelationInfo;
import com.synway.datarelation.pojo.databloodline.RelationshipNode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 数据加工血缘的所有数据 使用缓存处理
 */
public class DataProcessBloodCache   implements Serializable {

    /**
     * key值是小写
     */
    private Map<String, List<RelationshipNode>> childKeyData;
    private Map<String, List<RelationshipNode>> parentKeyData;
    // 存储从接入血缘查询的信息
    private List<QueryBloodlineRelationInfo> allDataAccessRelationInfo;
    private String type;
    private Date insertTime;

    public List<QueryBloodlineRelationInfo> getAllDataAccessRelationInfo() {
        return allDataAccessRelationInfo;
    }

    public void setAllDataAccessRelationInfo(List<QueryBloodlineRelationInfo> allDataAccessRelationInfo) {
        this.allDataAccessRelationInfo = allDataAccessRelationInfo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, List<RelationshipNode>> getChildKeyData() {
        return childKeyData;
    }

    public void setChildKeyData(Map<String, List<RelationshipNode>> childKeyData) {
        this.childKeyData = childKeyData;
    }

    public Map<String, List<RelationshipNode>> getParentKeyData() {
        return parentKeyData;
    }

    public void setParentKeyData(Map<String, List<RelationshipNode>> parentKeyData) {
        this.parentKeyData = parentKeyData;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
}
