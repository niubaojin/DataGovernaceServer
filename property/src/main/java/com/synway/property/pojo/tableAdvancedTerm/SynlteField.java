package com.synway.property.pojo.tableAdvancedTerm;

/**
 * SYNLTE.SYNLTEFIELD
 * @author majia
 * @version 1.0
 * @date 2020/11/9 13:12
 */
public class SynlteField {

    private String fieldId;
    private String fieldChineseName;
    private String sameName;

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public String getFieldChineseName() {
        return fieldChineseName;
    }

    public void setFieldChineseName(String fieldChineseName) {
        this.fieldChineseName = fieldChineseName;
    }

    public String getSameName() {
        return sameName;
    }

    public void setSameName(String sameName) {
        this.sameName = sameName;
    }

    @Override
    public String toString() {
        return "SynlteField{" +
                "fieldId='" + fieldId + '\'' +
                ", fieldChineseName='" + fieldChineseName + '\'' +
                ", sameId='" + sameName + '\'' +
                '}';
    }
}
