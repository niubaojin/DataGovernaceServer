package com.synway.property.pojo.organizationdetail;

import java.util.Date;

/**
 * @author majia
 */
public class TableField {
    // 字段id
    private String id;

    // 表id
    private String tableId;

    // 数据源id
    private String resourceId;

    // 字段名称
    private String fieldName;

    // 字段描述
    private String fieldDescription;

    // 字段类型
    private String fieldType;

    // 字段长度
    private String fieldLength;

    // 是否非空
    private String isNonnull;

    // 是否删除
    private String isDeleted;

    // 是否主键
    private String isPrimarykey;

    // 是否外键
    private String isForeignKey;

    // 创建时间
    private Date insertTime;

    // 更新时间
    private Date updateTime;

    //数据库链接地址
    private String databaseUrl;

    //用户名
    private String schemeId;

    //是否是分区字段
    private String isPartition;

    //分区字段的级数

    private String partitionLevel;

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public void setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    public String getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldDescription() {
        return fieldDescription;
    }

    public void setFieldDescription(String fieldDescription) {
        this.fieldDescription = fieldDescription;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldLength() {
        return fieldLength;
    }

    public void setFieldLength(String fieldLength) {
        this.fieldLength = fieldLength;
    }

    public String getIsNonnull() {
        return isNonnull;
    }

    public void setIsNonnull(String isNonnull) {
        this.isNonnull = isNonnull;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getIsPrimarykey() {
        return isPrimarykey;
    }

    public void setIsPrimarykey(String isPrimarykey) {
        this.isPrimarykey = isPrimarykey;
    }

    public String getIsForeignKey() {
        return isForeignKey;
    }

    public void setIsForeignKey(String isForeignKey) {
        this.isForeignKey = isForeignKey;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getIsPartition() {
        return isPartition;
    }

    public void setIsPartition(String isPartition) {
        this.isPartition = isPartition;
    }

    public String getPartitionLevel() {
        return partitionLevel;
    }

    public void setPartitionLevel(String partitionLevel) {
        this.partitionLevel = partitionLevel;
    }
}
