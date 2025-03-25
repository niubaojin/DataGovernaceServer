package com.synway.datastandardmanager.pojo;

import java.util.List;

/**
 *  标签表的数据对象信息
 */
public class TagData {
    private String labelCode;
    private String labelName;
    private String labelLevel;
    private String classId;
    private String parentId;
    private String labelLevelName;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getLabelLevelName() {
        return labelLevelName;
    }

    public void setLabelLevelName(String labelLevelName) {
        this.labelLevelName = labelLevelName;
    }

    public String getLabelCode() {
        return labelCode;
    }

    public void setLabelCode(String labelCode) {
        this.labelCode = labelCode;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getLabelLevel() {
        return labelLevel;
    }

    public void setLabelLevel(String labelLevel) {
        this.labelLevel = labelLevel;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public List<TagData> getBodyTag1() {
        return bodyTag1;
    }

    public void setBodyTag1(List<TagData> bodyTag1) {
        this.bodyTag1 = bodyTag1;
    }

    public List<TagData> getElementTag2() {
        return elementTag2;
    }

    public void setElementTag2(List<TagData> elementTag2) {
        this.elementTag2 = elementTag2;
    }

    public List<TagData> getObjectDescTag3() {
        return objectDescTag3;
    }

    public void setObjectDescTag3(List<TagData> objectDescTag3) {
        this.objectDescTag3 = objectDescTag3;
    }

    public List<TagData> getBehaviorTag4() {
        return behaviorTag4;
    }

    public void setBehaviorTag4(List<TagData> behaviorTag4) {
        this.behaviorTag4 = behaviorTag4;
    }

    public List<TagData> getRelationShipTag5() {
        return relationShipTag5;
    }

    public void setRelationShipTag5(List<TagData> relationShipTag5) {
        this.relationShipTag5 = relationShipTag5;
    }

    public List<TagData> getLocationTag6() {
        return locationTag6;
    }

    public void setLocationTag6(List<TagData> locationTag6) {
        this.locationTag6 = locationTag6;
    }

    private List<TagData> bodyTag1;
    //
    private List<TagData> elementTag2;
    //
    private List<TagData> objectDescTag3;
    // 行为标签
    private List<TagData> behaviorTag4;
    // 关联关系标签
    private List<TagData> relationShipTag5;
    // 位置标签
    private List<TagData> locationTag6;
}
