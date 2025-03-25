package com.synway.datastandardmanager.pojo.buildtable;


import com.synway.datastandardmanager.pojo.ObjectField;

import java.io.Serializable;
import java.util.List;

/**
 *  在presto上建表需要用到的参数
 */
public class PrestoObjectTable {

    //
    private String tableNameCH;
    private String primaryOrganizationCh;
    private String secondaryOrganizationCh;
    // 20200302 新增数据组织三级分类
    private String firstOrganizationCh;
    private String primaryDatasourceCh;
    private String secondaryDatasourceCh;
    //  数据源ID
    private String dataId;

    private Integer objectId;

    private String objectName;

    private String objectMemo;

    private String tableId;

    /**
     * hbase下存储的表名称
     */
    private String tableName;

    /**
     * 定义该类型是否生效 0未生效 1生效
     */
    private Short objectFlag;

    /**
     * 02e0016：数据来源名称
     */
    private String dataSource;

    private Integer objectRowkey;

    /**
     * hbase分区类型，0为按天分区，1为按固定分区，2为按天进行二次分区 3多天 一分区
     */
    private Byte regionType;

    /**
     * hbase多天一分区则表示天数，如果是一天多分区，则表示一天的分区数，一天一分区和固定分区该值不生效
     */
    private Short regionCount;

    /**
     * rowkeyfield表示组成datarowkey的业务字段所在列的列名
     */
    private String rowkeyField;

    /**
     * es索引类型
     */
    private Byte esSplitType;

    /**
     * es多天一索引天数，如果是按天建索引，该值不生效
     */
    private Short esSplitCount;

    /**
     * es索引分片数
     */
    private Short esShards;

    /**
     * geohash是否冗余
     */
    private Boolean geoRedundant;

    /**
     * 只查询es source，如果该值为1 则字段必须存在source中
     */
    private Boolean esSourceOnly;

    /**
     * 表空间
     */
    private String schemaName;
    private String schema;

    /**
     * 是否禁用: 0否 1是
     */
    private Boolean disabled;

    /**
     * 表的生命周期
     */
    private Integer lifecycle;

    private String prestoMemo;

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getFirstOrganizationCh() {
        return firstOrganizationCh;
    }

    public void setFirstOrganizationCh(String firstOrganizationCh) {
        this.firstOrganizationCh = firstOrganizationCh;
    }

    public String getTableNameCH() {
        return tableNameCH;
    }

    public void setTableNameCH(String tableNameCH) {
        this.tableNameCH = tableNameCH;
    }

    public String getPrimaryOrganizationCh() {
        return primaryOrganizationCh;
    }

    public void setPrimaryOrganizationCh(String primaryOrganizationCh) {
        this.primaryOrganizationCh = primaryOrganizationCh;
    }

    public String getSecondaryOrganizationCh() {
        return secondaryOrganizationCh;
    }

    public void setSecondaryOrganizationCh(String secondaryOrganizationCh) {
        this.secondaryOrganizationCh = secondaryOrganizationCh;
    }

    public String getPrimaryDatasourceCh() {
        return primaryDatasourceCh;
    }

    public void setPrimaryDatasourceCh(String primaryDatasourceCh) {
        this.primaryDatasourceCh = primaryDatasourceCh;
    }

    public String getSecondaryDatasourceCh() {
        return secondaryDatasourceCh;
    }

    public void setSecondaryDatasourceCh(String secondaryDatasourceCh) {
        this.secondaryDatasourceCh = secondaryDatasourceCh;
    }

    //存储的表字段信息
    private List<ObjectField> columnData = null;


    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public Integer getObjectId() {
        return objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getObjectMemo() {
        return objectMemo;
    }

    public void setObjectMemo(String objectMemo) {
        this.objectMemo = objectMemo;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Short getObjectFlag() {
        return objectFlag;
    }

    public void setObjectFlag(Short objectFlag) {
        this.objectFlag = objectFlag;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public Integer getObjectRowkey() {
        return objectRowkey;
    }

    public void setObjectRowkey(Integer objectRowkey) {
        this.objectRowkey = objectRowkey;
    }

    public Byte getRegionType() {
        return regionType;
    }

    public void setRegionType(Byte regionType) {
        this.regionType = regionType;
    }

    public Short getRegionCount() {
        return regionCount;
    }

    public void setRegionCount(Short regionCount) {
        this.regionCount = regionCount;
    }

    public String getRowkeyField() {
        return rowkeyField;
    }

    public void setRowkeyField(String rowkeyField) {
        this.rowkeyField = rowkeyField;
    }

    public Byte getEsSplitType() {
        return esSplitType;
    }

    public void setEsSplitType(Byte esSplitType) {
        this.esSplitType = esSplitType;
    }

    public Short getEsSplitCount() {
        return esSplitCount;
    }

    public void setEsSplitCount(Short esSplitCount) {
        this.esSplitCount = esSplitCount;
    }

    public Short getEsShards() {
        return esShards;
    }

    public void setEsShards(Short esShards) {
        this.esShards = esShards;
    }

    public Boolean getGeoRedundant() {
        return geoRedundant;
    }

    public void setGeoRedundant(Boolean geoRedundant) {
        this.geoRedundant = geoRedundant;
    }

    public Boolean getEsSourceOnly() {
        return esSourceOnly;
    }

    public void setEsSourceOnly(Boolean esSourceOnly) {
        this.esSourceOnly = esSourceOnly;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public Integer getLifecycle() {
        return lifecycle;
    }

    public void setLifecycle(Integer lifecycle) {
        this.lifecycle = lifecycle;
    }

    public String getPrestoMemo() {
        return prestoMemo;
    }

    public void setPrestoMemo(String prestoMemo) {
        this.prestoMemo = prestoMemo;
    }

    public List<ObjectField> getColumnData() {
        return columnData;
    }

    public void setColumnData(List<ObjectField> columnData) {
        this.columnData = columnData;
    }
}
