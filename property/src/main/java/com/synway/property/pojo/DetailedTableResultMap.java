package com.synway.property.pojo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author majia
 */
public class DetailedTableResultMap  implements Serializable {
    private List<Map<String,String>> projectList;
    private List<Map<String,String>> productStageList;
    private List<Map<String,String>> updatePeriodList;
    private List<DetailedTableByClassify> data;

    private long total;
    public long getTotal() { return total; }
    public void setTotal(long total) { this.total = total; }

    public List<Map<String, String>> getUpdatePeriodList() {
        return updatePeriodList;
    }

    public void setUpdatePeriodList(List<Map<String, String>> updatePeriodList) {
        this.updatePeriodList = updatePeriodList;
    }

    public List<Map<String, String>> getProductStageList() {
        return productStageList;
    }

    public void setProductStageList(List<Map<String, String>> productStageList) {
        this.productStageList = productStageList;
    }

    public List<DetailedTableByClassify> getData() {
        return data;
    }

    public void setData(List<DetailedTableByClassify> data) {
        this.data = data;
    }

    public List<Map<String,String>> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<Map<String,String>> projectList) {
        this.projectList = projectList;
    }
}
