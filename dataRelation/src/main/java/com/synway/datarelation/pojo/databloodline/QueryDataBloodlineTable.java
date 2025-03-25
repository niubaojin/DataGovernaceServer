package com.synway.datarelation.pojo.databloodline;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 *  左侧搜索框查询出来的数据　左侧的table展示数据
 */
public class QueryDataBloodlineTable implements Serializable {
    @NotNull
    private String tableShowData="";  //表格中查询到的展示数据　　数据接入 —> 卡口数据
    @NotNull
    private String dataType;   // 节点类型
    private String dataBaseId="";  //  数据仓库对应的 id值   数据探查展示
    private String sourceId="";    // 源协议英文名  数据接入展示
    private String sourceCodeCh="";    // 源系统代号中文名  数据接入展示
    private String sourceCode="";    // 源系统代号中文名  数据接入展示
    private String tableId="";        // 目标 tableId  数据处理展示  数据加工展示
    private String targetCodeCh="";  // 目标 系统代号中文名  数据处理展示
    private String targetCode="";  // 目标 系统代号中文名  数据处理展示  数据加工展示
    private String tableNameEn="";   //  表项目名.表英文名  数据加工展示
    private String tableNameCh="";   //  表中文名  数据加工展示
    private String applicationName="";
    private String subModuleName="";

    // 20200423 增加2个页面查询参数
    private String pageId;
    private String queryId;
    private String inputQueryStr="";
    private String queryType="";

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    //  如果是点击节点之后再查询的，会填上以下两个值
    public String getInputQueryStr() {
        return inputQueryStr;
    }

    public void setInputQueryStr(String inputQueryStr) {
        this.inputQueryStr = inputQueryStr;
    }

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public String getQueryId() {
        return queryId;
    }

    public void setQueryId(String queryId) {
        this.queryId = queryId;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getSubModuleName() {
        return subModuleName;
    }

    public void setSubModuleName(String subModuleName) {
        this.subModuleName = subModuleName;
    }

    public String getTableNameCh() {
        return tableNameCh;
    }

    public void setTableNameCh(String tableNameCh) {
        this.tableNameCh = tableNameCh;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String getTargetCode() {
        return targetCode;
    }

    public void setTargetCode(String targetCode) {
        this.targetCode = targetCode;
    }

    public String getTableShowData() {
        return tableShowData;
    }

    public void setTableShowData(String tableShowData) {
        this.tableShowData = tableShowData;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDataBaseId() {
        return dataBaseId;
    }

    public void setDataBaseId(String dataBaseId) {
        this.dataBaseId = dataBaseId;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceCodeCh() {
        return sourceCodeCh;
    }

    public void setSourceCodeCh(String sourceCodeCh) {
        this.sourceCodeCh = sourceCodeCh;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getTargetCodeCh() {
        return targetCodeCh;
    }

    public void setTargetCodeCh(String targetCodeCh) {
        this.targetCodeCh = targetCodeCh;
    }

    public String getTableNameEn() {
        return tableNameEn;
    }

    public void setTableNameEn(String tableNameEn) {
        this.tableNameEn = tableNameEn;
    }

    @Override
    public String toString() {
        return "QueryDataBloodlineTable{" +
                "tableShowData='" + tableShowData + '\'' +
                ", dataType='" + dataType + '\'' +
                ", dataBaseId='" + dataBaseId + '\'' +
                ", sourceId='" + sourceId + '\'' +
                ", sourceCodeCh='" + sourceCodeCh + '\'' +
                ", sourceCode='" + sourceCode + '\'' +
                ", tableId='" + tableId + '\'' +
                ", targetCodeCh='" + targetCodeCh + '\'' +
                ", targetCode='" + targetCode + '\'' +
                ", tableNameEn='" + tableNameEn + '\'' +
                ", tableNameCh='" + tableNameCh + '\'' +
                ", applicationName='" + applicationName + '\'' +
                ", subModuleName='" + subModuleName + '\'' +
                ", pageId='" + pageId + '\'' +
                ", queryId='" + queryId + '\'' +
                ", inputQueryStr='" + inputQueryStr + '\'' +
                '}';
    }
}
