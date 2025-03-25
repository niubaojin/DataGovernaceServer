package com.synway.datarelation.pojo.monitor;

/**
 * @author majia
 * @version 1.0
 * @date 2020/12/17 13:20
 */
public class DagRunQueryParameters {
    // 查询类型为SEARCH_TASK_RUNLOG
    private String executeMethod = "SEARCH_DAG_BY_ID";
    private String dagId;

    public String getExecuteMethod() {
        return executeMethod;
    }

    public void setExecuteMethod(String executeMethod) {
        this.executeMethod = executeMethod;
    }

    public String getDagId() {
        return dagId;
    }

    public void setDagId(String dagId) {
        this.dagId = dagId;
    }
}
