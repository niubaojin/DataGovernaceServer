package com.synway.property.pojo.alarmsetting;

/**
 * @author majia
 */
public class ThresholdConfig {
    private String id;
    private String parentId;
    private String name;
    private String logicalJudgment;
    private String thresholdValue;
    private String treeType;
    private String isActive;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogicalJudgment() {
        return logicalJudgment;
    }

    public void setLogicalJudgment(String logicalJudgment) {
        this.logicalJudgment = logicalJudgment;
    }

    public String getThresholdValue() {
        return thresholdValue;
    }

    public void setThresholdValue(String thresholdValue) {
        this.thresholdValue = thresholdValue;
    }

    public String getTreeType() {
        return treeType;
    }

    public void setTreeType(String treeType) {
        this.treeType = treeType;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
}
