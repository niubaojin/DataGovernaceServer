package com.synway.datastandardmanager.pojo;

import com.synway.datastandardmanager.pojo.warehouse.FieldInfo;
import com.synway.datastandardmanager.pojo.warehouse.PartitionField;
import com.synway.datastandardmanager.pojo.warehouse.PartitionInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TableInfo implements Serializable {
    //探查ID,新增无，更新有
    private String detectId;
    //项目空间或ftp父目录
    private String projectName;
    //表名或ftp当前目录/当前文件名
    private String tableNameEN;
    //表中文名
    private String tableNameCN;
    //0：视图（默认） 1：表
    private int tableType;
    //生命周期，目前只有odps、ads、hive用
    private int lifeCycle;
    //分区表 0：非   1：是
    private int isPartitioned;
    //当前存储数据记录数(条)
    private long numRows;
    //当前存储数据物理大小(kb)
    private long physicalSize;
    //最后表结构修改时间
    private String lastDDLTime;
    //最后数据操作时间
    private String lastDMLTime;
    //备注
    private String comments;
    //记录产生时间
    private String createTime;
    //字段信息
    private List<FieldInfo> fieldInfos = new ArrayList<>();
    //String1:列名 String2:分区级别，如odps一级、二级分区
    private List<PartitionField> partitionFields;
    //分区信息
    private List<PartitionInfo> partitionInfos;
    //样例数据
    private List<Map<String,String>> exampleData;

    public String getDetectId() {
        return detectId;
    }

    public void setDetectId(String detectId) {
        this.detectId = detectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getTableNameEN() {
        return tableNameEN;
    }

    public void setTableNameEN(String tableNameEN) {
        this.tableNameEN = tableNameEN;
    }

    public String getTableNameCN() {
        return tableNameCN;
    }

    public void setTableNameCN(String tableNameCN) {
        this.tableNameCN = tableNameCN;
    }

    public int getTableType() {
        return tableType;
    }

    public void setTableType(int tableType) {
        this.tableType = tableType;
    }

    public int getLifeCycle() {
        return lifeCycle;
    }

    public void setLifeCycle(int lifeCycle) {
        this.lifeCycle = lifeCycle;
    }

    public int getIsPartitioned() {
        return isPartitioned;
    }

    public void setIsPartitioned(int isPartitioned) {
        this.isPartitioned = isPartitioned;
    }

    public long getPhysicalSize() {
        return physicalSize;
    }

    public void setPhysicalSize(long physicalSize) {
        this.physicalSize = physicalSize;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLastDDLTime() {
        return lastDDLTime;
    }

    public void setLastDDLTime(String lastDDLTime) {
        this.lastDDLTime = lastDDLTime;
    }

    public String getLastDMLTime() {
        return lastDMLTime;
    }

    public void setLastDMLTime(String lastDMLTime) {
        this.lastDMLTime = lastDMLTime;
    }

    public List<FieldInfo> getFieldInfos() {
        return fieldInfos;
    }

    public void setFieldInfos(List<FieldInfo> fieldInfos) {
        this.fieldInfos = fieldInfos;
    }

    public List<Map<String, String>> getExampleData() {
        return exampleData;
    }

    public void setExampleData(List<Map<String, String>> exampleData) {
        this.exampleData = exampleData;
    }

    public long getNumRows() {
        return numRows;
    }

    public void setNumRows(long numRows) {
        this.numRows = numRows;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public List<PartitionField> getPartitionFields() {
        return partitionFields;
    }

    public void setPartitionFields(List<PartitionField> partitionFields) {
        this.partitionFields = partitionFields;
    }

    public List<PartitionInfo> getPartitionInfos() {
        return partitionInfos;
    }

    public void setPartitionInfos(List<PartitionInfo> partitionInfos) {
        this.partitionInfos = partitionInfos;
    }

}
