package com.synway.datarelation.pojo.datawork;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 返回页面查询出来的数据
 */
public class DataSynchronizationReportPageReturn implements Serializable {
    // 页面查询出来的结果
    private Map<String,Object> pageInfoMap;
    // 源表存储位置的 filter列表
    private List<FilterObject> sourceTypeShowList;
    // 源表所在库的 filter列表
    private List<FilterObject> sourceTableProjectShowList;
    // 目标表存储位置的 filter列表
    private List<FilterObject> targetTypeShowList;
    // 目标表所在库的 filter列表
    private List<FilterObject> targetTableProjectShowList;

    public static class FilterObject{
        private String text;
        private String value;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public Map<String, Object> getPageInfoMap() {
        return pageInfoMap;
    }

    public void setPageInfoMap(Map<String, Object> pageInfoMap) {
        this.pageInfoMap = pageInfoMap;
    }

    public List<FilterObject> getSourceTypeShowList() {
        return sourceTypeShowList;
    }

    public void setSourceTypeShowList(List<FilterObject> sourceTypeShowList) {
        this.sourceTypeShowList = sourceTypeShowList;
    }

    public List<FilterObject> getSourceTableProjectShowList() {
        return sourceTableProjectShowList;
    }

    public void setSourceTableProjectShowList(List<FilterObject> sourceTableProjectShowList) {
        this.sourceTableProjectShowList = sourceTableProjectShowList;
    }

    public List<FilterObject> getTargetTypeShowList() {
        return targetTypeShowList;
    }

    public void setTargetTypeShowList(List<FilterObject> targetTypeShowList) {
        this.targetTypeShowList = targetTypeShowList;
    }

    public List<FilterObject> getTargetTableProjectShowList() {
        return targetTableProjectShowList;
    }

    public void setTargetTableProjectShowList(List<FilterObject> targetTableProjectShowList) {
        this.targetTableProjectShowList = targetTableProjectShowList;
    }
}
