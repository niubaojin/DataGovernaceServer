package com.synway.datarelation.pojo.lineage;

import java.io.Serializable;

/**
 *  数据库中字段血缘的存储实体类
 */
public class ColumnLineDb implements Serializable {
    // 输出表的表名 包括项目名 syndm.table1
    private String outputTableName="";
    // 数据库类型 hive/odps
    private String dbType="";
    // 输出表的字段名
    private String outputColumnName="";

    // 输入表的表名 包括项目名 syndm.table2
    private String inputTableName="";
    // 输入表的字段名
    private String inputColumnName="";
    // 输入字段 -> 输出字段 整个流程的对象信息
    private String processJson="";
    // 第一层字段如果存在 函数 case 等操作 存储具体的操作信息
    // 比如  md5(concat(A.CITY_CODE, A.SRC_IP, B.WB_SPECIALBIZ))
    private String caseStr="";
    // 节点的id值
    private String nodeId="";

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getOutputTableName() {
        return outputTableName;
    }

    public void setOutputTableName(String outputTableName) {
        this.outputTableName = outputTableName;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getOutputColumnName() {
        return outputColumnName;
    }

    public void setOutputColumnName(String outputColumnName) {
        this.outputColumnName = outputColumnName;
    }

    public String getInputTableName() {
        return inputTableName;
    }

    public void setInputTableName(String inputTableName) {
        this.inputTableName = inputTableName;
    }

    public String getInputColumnName() {
        return inputColumnName;
    }

    public void setInputColumnName(String inputColumnName) {
        this.inputColumnName = inputColumnName;
    }

    public String getProcessJson() {
        return processJson;
    }

    public void setProcessJson(String processJson) {
        this.processJson = processJson;
    }

    public String getCaseStr() {
        return caseStr;
    }

    public void setCaseStr(String caseStr) {
        this.caseStr = caseStr;
    }
}
