package com.synway.datarelation.pojo.datawork.v3;

/**
 * 查询任务实例的运行日志
 */
public class TaskRunQueryParameters {

    // 查询类型为SEARCH_TASK_RUNLOG
    private String executeMethod = "SEARCH_TASK_RUNLOG";
    // 指定taskId或者historyId之一
    private Long taskId;
    private Long historyId;

    public String getExecuteMethod() {
        return executeMethod;
    }

    public void setExecuteMethod(String executeMethod) {
        this.executeMethod = executeMethod;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Long historyId) {
        this.historyId = historyId;
    }
}
