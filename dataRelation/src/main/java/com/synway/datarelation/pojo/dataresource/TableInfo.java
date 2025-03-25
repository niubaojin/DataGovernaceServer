package com.synway.datarelation.pojo.dataresource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 在 1.8版本之后 数据仓库表的字段信息使用这个接口
 * @author wangdongwei
 */
public class TableInfo implements Serializable{
    //探查ID,新增无，更新有
    private String detectId;
    //项目空间或ftp父目录
    private String projectName;
    //表/视图英文名/ftp当前目录/当前文件名
    private String tableNameEN;
    //表中文名
    private String tableNameCN;
    //0：未知      1：表 2：视图     11:文件 12：文件夹
    private int tableType;
    //文件系统相关
    //通配符
    private String wildcard;
    //递归子目录
    private int isRecursion;
    //字段分隔符
    private String separator;
    //来源协议编码
    private String sourceCode;
    //数据相关相关
    //当前存储数据记录数(条)
    private long recordCounts;
    //当前存储数据物理大小(kb)
    private long physicalSize;
    //生命周期，目前只有odps、ads、hive用
    private int storeCycle;
    //分区表 0：非   1：分区  2：非分区
    private int isPartitioned;
    //最后表结构修改时间
    private String lastDDLTime;
    //最后数据操作时间
    private String lastDMLTime;
    //记录产生时间
    private String createTime;
    //String1:列名 String2:分区级别，如odps一级、二级分区
    private List<PartitionField> partitionFields;
    //分区信息
    private List<PartitionInfo> partitionInfos;
    //样例数据
    private List<Map<String,String>> exampleData;
    //字段信息
    private List<FieldInfo> fieldInfos = new ArrayList<>();
    //备注
    private String comments;

    //子目录
    private ProjectInfo projectInfo;

    public ProjectInfo getProjectInfo() {
        return projectInfo;
    }

    public void setProjectInfo(ProjectInfo projectInfo) {
        this.projectInfo = projectInfo;
    }

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

    public String getWildcard() {
        return wildcard;
    }

    public void setWildcard(String wildcard) {
        this.wildcard = wildcard;
    }

    public int getIsRecursion() {
        return isRecursion;
    }

    public void setIsRecursion(int isRecursion) {
        this.isRecursion = isRecursion;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public List<FieldInfo> getFieldInfos() {
        return fieldInfos;
    }

    public void setFieldInfos(List<FieldInfo> fieldInfos) {
        this.fieldInfos = fieldInfos;
    }

    public long getRecordCounts() {
        return recordCounts;
    }

    public void setRecordCounts(long recordCounts) {
        this.recordCounts = recordCounts;
    }

    public long getPhysicalSize() {
        return physicalSize;
    }

    public void setPhysicalSize(long physicalSize) {
        this.physicalSize = physicalSize;
    }

    public int getStoreCycle() {
        return storeCycle;
    }

    public void setStoreCycle(int storeCycle) {
        this.storeCycle = storeCycle;
    }

    public int getIsPartitioned() {
        return isPartitioned;
    }

    public void setIsPartitioned(int isPartitioned) {
        this.isPartitioned = isPartitioned;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public List<Map<String, String>> getExampleData() {
        return exampleData;
    }

    public void setExampleData(List<Map<String, String>> exampleData) {
        this.exampleData = exampleData;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "TableInfo{" +
                "detectId='" + detectId + '\'' +
                ", projectName='" + projectName + '\'' +
                ", tableNameEN='" + tableNameEN + '\'' +
                ", tableNameCN='" + tableNameCN + '\'' +
                ", tableType=" + tableType +
                ", wildcard='" + wildcard + '\'' +
                ", isRecursion=" + isRecursion +
                ", separator='" + separator + '\'' +
                ", sourceCode='" + sourceCode + '\'' +
                ", fieldInfos=" + fieldInfos +
                ", recordCounts=" + recordCounts +
                ", physicalSize=" + physicalSize +
                ", storeCycle=" + storeCycle +
                ", isPartitioned=" + isPartitioned +
                ", lastDDLTime='" + lastDDLTime + '\'' +
                ", lastDMLTime='" + lastDMLTime + '\'' +
                ", createTime='" + createTime + '\'' +
                ", partitionFields=" + partitionFields +
                ", partitionInfos=" + partitionInfos +
                ", exampleData=" + exampleData +
                ", comments='" + comments + '\'' +
//                ", nextTableInfos=" + nextTableInfos +
                '}';
    }
}
