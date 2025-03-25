package com.synway.datastandardmanager.pojo.buildtable;

import java.util.List;

public class AddAdsOdpsTableReq {
    private String dataId;//数据源id
    private String schema;//ads库 odps项目
    private String tableName;//表名
    private String tableNameCn;//表中文名；表备注
    private List<TableColumn> columns;
    private String partitionFirst;//一级分区列(ADS)
    private String partitionFirstNum;//一级分区列数(ADS)
    private String partitionSecond;//二级分区列(ADS)
    private String partitionSecondNum;//二级分区列数(ADS)
    private List<String> clusterColumn;//聚集列(ADS)
    private String isActiveTable;//更新类型（0：batch;1:realtime）(ADS)
    private List<String> partitionColumns;//分区字段（ODPS）
    private String lifeCycle;//生命周期（ODPS/datahub）
    private String topicName; // toplic名称(datahub)
    private String shardCount; // 通道数(datahub)
    private String comment; // 备注信息(datahub)
    private String dataBaseType; // 数据库类型  odps/ads/datahub 这个只是在1.6.0存在 ，之前不存在
    private String topicProjectName; // 项目名(datahub)

    public String getTopicProjectName() {
        return topicProjectName;
    }

    public void setTopicProjectName(String topicProjectName) {
        this.topicProjectName = topicProjectName;
    }

    public String getDataBaseType() {
        return dataBaseType;
    }

    public void setDataBaseType(String dataBaseType) {
        this.dataBaseType = dataBaseType;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getShardCount() {
        return shardCount;
    }

    public void setShardCount(String shardCount) {
        this.shardCount = shardCount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPartitionFirstNum() {
        return partitionFirstNum;
    }

    public void setPartitionFirstNum(String partitionFirstNum) {
        this.partitionFirstNum = partitionFirstNum;
    }

    public String getPartitionSecondNum() {
        return partitionSecondNum;
    }

    public void setPartitionSecondNum(String partitionSecondNum) {
        this.partitionSecondNum = partitionSecondNum;
    }

    public List<String> getClusterColumn() {
        return clusterColumn;
    }

    public void setClusterColumn(List<String> clusterColumn) {
        this.clusterColumn = clusterColumn;
    }

    public String getIsActiveTable() {
        return isActiveTable;
    }

    public void setIsActiveTable(String isActiveTable) {
        this.isActiveTable = isActiveTable;
    }

    public List<String> getPartitionColumns() {
        return partitionColumns;
    }

    public void setPartitionColumns(List<String> partitionColumns) {
        this.partitionColumns = partitionColumns;
    }

    public String getLifeCycle() {
        return lifeCycle;
    }

    public void setLifeCycle(String lifeCycle) {
        this.lifeCycle = lifeCycle;
    }

    public String getPartitionFirst() {
        return partitionFirst;
    }

    public void setPartitionFirst(String partitionFirst) {
        this.partitionFirst = partitionFirst;
    }

    public String getPartitionSecond() {
        return partitionSecond;
    }

    public void setPartitionSecond(String partitionSecond) {
        this.partitionSecond = partitionSecond;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public List<TableColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<TableColumn> columns) {
        this.columns = columns;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableNameCn() {
        return tableNameCn;
    }

    public void setTableNameCn(String tableNameCn) {
        this.tableNameCn = tableNameCn;
    }
}
