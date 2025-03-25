package com.synway.property.pojo;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 *
 * 实时数据和全量数据柱状图展示
 * @author majia
 * @date 2020/06/02
 */
public class DataRankingTop {

    private List<String> dataNameList;
    private List<Double> recordsNumberList;

    @Override
    public String toString() {
        return "DataRankingTop{" +
                "dataNameList=" + JSONObject.toJSONString(dataNameList) +
                ", recordsNumberList=" + JSONObject.toJSONString(recordsNumberList) +
                '}';
    }

    public List<String> getDataNameList() {
        return dataNameList;
    }

    public void setDataNameList(List<String> dataNameList) {
        this.dataNameList = dataNameList;
    }

    public List<Double> getRecordsNumberList() {
        return recordsNumberList;
    }

    public void setRecordsNumberList(List<Double> recordsNumberList) {
        this.recordsNumberList = recordsNumberList;
    }
}
