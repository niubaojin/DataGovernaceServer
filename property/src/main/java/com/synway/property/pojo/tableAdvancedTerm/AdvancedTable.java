package com.synway.property.pojo.tableAdvancedTerm;

import java.util.Objects;

/**
 * 高级检索中符合筛选条件的数据信息
 * @author majia
 * @version 1.0
 * @date 2020/11/9 9:24
 */
public class AdvancedTable {

    /**
     * objectId
     */
    private String objectId;

    /**
     * 用于匹配中文名
     */
    private String fieldId;

    /**
     * 表中文名
     */
    private String tableNameCh;

    /**
     * 表英文名
     */
    private String tableNameEn;

    /**
     * 元素集中文名称
     */
    private String elementChSets;

    /**
     * 语义类型,语义类型筛选用
     */
    private String semanticType;

    /**
     * 中文关键字，元素集筛选用
     */
    private String elementSetCh;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getTableNameCh() {
        return tableNameCh;
    }

    public void setTableNameCh(String tableNameCh) {
        this.tableNameCh = tableNameCh;
    }

    public String getTableNameEn() {
        return tableNameEn;
    }

    public void setTableNameEn(String tableNameEn) {
        this.tableNameEn = tableNameEn;
    }

    public String getElementChSets() {
        return elementChSets;
    }

    public void setElementChSets(String elementChSets) {
        this.elementChSets = elementChSets;
    }

    public String getSemanticType() {
        return semanticType;
    }

    public void setSemanticType(String semanticType) {
        this.semanticType = semanticType;
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public String getElementSetCh() {
        return elementSetCh;
    }

    public void setElementSetCh(String elementSetCh) {
        this.elementSetCh = elementSetCh;
    }

    @Override
    public String toString() {
        return "AdvancedTable{" +
                "objectId='" + objectId + '\'' +
                ", fieldId='" + fieldId + '\'' +
                ", tableNameCh='" + tableNameCh + '\'' +
                ", tableNameEn='" + tableNameEn + '\'' +
                ", elementChSets='" + elementChSets + '\'' +
                ", semanticType='" + semanticType + '\'' +
                ", elementSetCh='" + elementSetCh + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdvancedTable)) {
            return false;
        }
        AdvancedTable table = (AdvancedTable) o;
        return Objects.equals(getObjectId(), table.getObjectId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getObjectId());
    }
}
