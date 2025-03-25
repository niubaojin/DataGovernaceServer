package com.synway.datarelation.pojo.databloodline;

/**
 * odps表字段信息
 */
public class OdpsFiled {
    private String fieldName;//字段名称
    private String fieldValue;//字段内容
    private String fieldType;//字段类型
    private String fieldFormat;//字段格式

    public String getFieldFormat() {
        return fieldFormat;
    }

    public void setFieldFormat(String fieldFormat) {
        this.fieldFormat = fieldFormat;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }
}
