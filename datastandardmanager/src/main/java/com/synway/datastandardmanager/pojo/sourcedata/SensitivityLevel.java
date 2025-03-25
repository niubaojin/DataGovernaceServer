package com.synway.datastandardmanager.pojo.sourcedata;

/**
 * @author wangdongwei
 * @ClassName SensitivityLevel
 * @description 敏感度分类的相关信息
 * @date 2020/9/14 11:22
 */
public class SensitivityLevel {
    private String tableId;
    private String fieldId;
    private String columnName;
    private String sensitivityId;
    private String sensitivityCh;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getSensitivityId() {
        return sensitivityId;
    }

    public void setSensitivityId(String sensitivityId) {
        this.sensitivityId = sensitivityId;
    }

    public String getSensitivityCh() {
        return sensitivityCh;
    }

    public void setSensitivityCh(String sensitivityCh) {
        this.sensitivityCh = sensitivityCh;
    }
}
