package com.synway.datarelation.pojo.monitor.detail;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 返回页面查询出来的数据
 * @author majia
 */
public class TaskReportPageReturn implements Serializable {
    // 页面查询出来的结果
    private Map<String,Object> pageInfoMap;
    // 项目名称的 filter列表
    private List<FilterObject> projectNameShowList;
    // 业务流程的 filter列表
    private List<FilterObject> businessShowList;
    // 执行状态的 filter列表
    private List<FilterObject> statusShowList;
    // 节点类型的filter列表
    private List<FilterObject> prgTypeShowList;

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

    public List<FilterObject> getProjectNameShowList() {
        return projectNameShowList;
    }

    public void setProjectNameShowList(List<FilterObject> projectNameShowList) {
        this.projectNameShowList = projectNameShowList;
    }

    public List<FilterObject> getBusinessShowList() {
        return businessShowList;
    }

    public void setBusinessShowList(List<FilterObject> businessShowList) {
        this.businessShowList = businessShowList;
    }

    public List<FilterObject> getStatusShowList() {
        return statusShowList;
    }

    public void setStatusShowList(List<FilterObject> statusShowList) {
        this.statusShowList = statusShowList;
    }

    public List<FilterObject> getPrgTypeShowList() {
        return prgTypeShowList;
    }

    public void setPrgTypeShowList(List<FilterObject> prgTypeShowList) {
        this.prgTypeShowList = prgTypeShowList;
    }
}
