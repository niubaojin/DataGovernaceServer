package com.synway.datastandardmanager.entity.vo.warehouse;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
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
    private List<Map<String, String>> exampleData;

}
