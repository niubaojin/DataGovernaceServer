package com.synway.datastandardmanager.pojo;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * 样例数据对应的结构
 */
public class ExampleDataColumn {
    private List<Map<String,Object>> columnList;
    private List<Map<String,Object>> exampleDataList;

    @Override
    public String toString() {
        return "ExampleDataColumn{" +
                "columnList=" + JSONObject.toJSONString(columnList )+
                ", exampleDataList=" + JSONObject.toJSONString(exampleDataList) +
                '}';
    }

    public List<Map<String,Object>> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<Map<String,Object>> columnList) {
        this.columnList = columnList;
    }

    public List<Map<String, Object>> getExampleDataList() {
        return exampleDataList;
    }

    public void setExampleDataList(List<Map<String, Object>> exampleDataList) {
        this.exampleDataList = exampleDataList;
    }
}
