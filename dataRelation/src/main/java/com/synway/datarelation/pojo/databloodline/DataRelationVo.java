package com.synway.datarelation.pojo.databloodline;

import java.util.List;
import java.util.Map;

public class DataRelationVo {
    private List<OdpsFiled> odpsFiledList;//字段信息
    private String tableName;//表名
    private boolean existFlag;//数据是否存在表示
    private List<String> fieldNames;//字段
    private List<Map<String,String>> data;//数据值
    private String id;//主键
    private String methodIndex;//查询标识
    private String dataStr;//数据的json字符串
    private List<OdpsFiled> selectList;//全部字段信息
    private  List<OdpsFiled> selectedList;//选中的字段信息


    public List<OdpsFiled> getSelectedList() {
        return selectedList;
    }

    public void setSelectedList(List<OdpsFiled> selectedList) {
        this.selectedList = selectedList;
    }

    public List<OdpsFiled> getSelectList() {
        return selectList;
    }

    public void setSelectList(List<OdpsFiled> selectList) {
        this.selectList = selectList;
    }

    public String getDataStr() {
        return dataStr;
    }

    public void setDataStr(String dataStr) {
        this.dataStr = dataStr;
    }

    public String getMethodIndex() {
        return methodIndex;
    }

    public void setMethodIndex(String methodIndex) {
        this.methodIndex = methodIndex;
    }

    public List<OdpsFiled> getOdpsFiledList() {
        return odpsFiledList;
    }

    public void setOdpsFiledList(List<OdpsFiled> odpsFiledList) {
        this.odpsFiledList = odpsFiledList;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public boolean isExistFlag() {
        return existFlag;
    }

    public void setExistFlag(boolean existFlag) {
        this.existFlag = existFlag;
    }

    public List<String> getFieldNames() {
        return fieldNames;
    }

    public void setFieldNames(List<String> fieldNames) {
        this.fieldNames = fieldNames;
    }

    public List<Map<String, String>> getData() {
        return data;
    }

    public void setData(List<Map<String, String>> data) {
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
