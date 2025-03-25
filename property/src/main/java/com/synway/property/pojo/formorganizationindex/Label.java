package com.synway.property.pojo.formorganizationindex;


/**
 * 数据资源标签类
 * @author majia
 */
public class Label {
    private String id;
    private String labelName;
    private String labelCode;
    private String labelLevel;
    private String classId;
    private String parentId;
    private String labelLevelName;

    @Override
    public String toString() {
        return "Label{" +
                "id='" + id + '\'' +
                ", labelName='" + labelName + '\'' +
                ", labelCode='" + labelCode + '\'' +
                ", labelLevel='" + labelLevel + '\'' +
                ", classId='" + classId + '\'' +
                ", parentId='" + parentId + '\'' +
                ", labelLevelName='" + labelLevelName + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getLabelCode() {
        return labelCode;
    }

    public void setLabelCode(String labelCode) {
        this.labelCode = labelCode;
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
}
