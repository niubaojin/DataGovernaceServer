package com.synway.datarelation.pojo.modelmonitor;

import java.util.Date;

/**
 *  dfwork的返回参数
 */
public class DfWorkModelNodeInsInfo {

    // dag实例id
    private String dagId;
    // 工作流id
    private String flowId;
    // 运行状态
    private String status;
    // 创建人id
    private String createUser;
    // 业务日期
    private String bizdate;
    private Date bizTimeDate;
    // 项目id
    private String projectId;
    // 项目中文名
    private String projectName;
    // 开始执行时间
    private String bizBeginHour;
    private Date bizBeginHourDate;
    // 执行结束时间
    private String bizEndHour;
    private Date bizEndHourDate;
    // 创建时间
    private String createTime;
    private Date createTimeDate;
    // 工作流名称
    private String flowName;
    // 生效日期
    private String startEffectDate;
    // 失效日期
    private String endEffectDate;
    // 0正常，1暂停或者冻结
    private Integer isPause;
    // 0手动调度，1周期调度
    private Integer schedulerType;
    // 调度规则
    private String scheduler;
    // 调度的详细信息
    private String schedulerInfo;
    // 依赖的流程id,多个以逗号分隔
    private String dependProgressId;
    // 实例类型 (如 scheduled__2020-01-18T08:40:00，其中后面是业务日期，根据前缀分为：backfill(补数据实例)，manual(手动实例)，
    // scheduled：周期实例  dagtest：测试实例)
    private String runId;
    private String prgType="98";

    public Date getBizTimeDate() {
        return bizTimeDate;
    }

    public void setBizTimeDate(Date bizTimeDate) {
        this.bizTimeDate = bizTimeDate;
    }

    public Date getBizBeginHourDate() {
        return bizBeginHourDate;
    }

    public void setBizBeginHourDate(Date bizBeginHourDate) {
        this.bizBeginHourDate = bizBeginHourDate;
    }

    public Date getBizEndHourDate() {
        return bizEndHourDate;
    }

    public void setBizEndHourDate(Date bizEndHourDate) {
        this.bizEndHourDate = bizEndHourDate;
    }

    public Date getCreateTimeDate() {
        return createTimeDate;
    }

    public void setCreateTimeDate(Date createTimeDate) {
        this.createTimeDate = createTimeDate;
    }

    public String getPrgType() {
        return prgType;
    }

    public void setPrgType(String prgType) {
        this.prgType = prgType;
    }

    public String getDagId() {
        return dagId;
    }

    public void setDagId(String dagId) {
        this.dagId = dagId;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getBizdate() {
        return bizdate;
    }

    public void setBizdate(String bizdate) {
        this.bizdate = bizdate;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getBizBeginHour() {
        return bizBeginHour;
    }

    public void setBizBeginHour(String bizBeginHour) {
        this.bizBeginHour = bizBeginHour;
    }

    public String getBizEndHour() {
        return bizEndHour;
    }

    public void setBizEndHour(String bizEndHour) {
        this.bizEndHour = bizEndHour;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public String getStartEffectDate() {
        return startEffectDate;
    }

    public void setStartEffectDate(String startEffectDate) {
        this.startEffectDate = startEffectDate;
    }

    public String getEndEffectDate() {
        return endEffectDate;
    }

    public void setEndEffectDate(String endEffectDate) {
        this.endEffectDate = endEffectDate;
    }

    public Integer getIsPause() {
        return isPause;
    }

    public void setIsPause(Integer isPause) {
        this.isPause = isPause;
    }

    public Integer getSchedulerType() {
        return schedulerType;
    }

    public void setSchedulerType(Integer schedulerType) {
        this.schedulerType = schedulerType;
    }

    public String getScheduler() {
        return scheduler;
    }

    public void setScheduler(String scheduler) {
        this.scheduler = scheduler;
    }

    public String getSchedulerInfo() {
        return schedulerInfo;
    }

    public void setSchedulerInfo(String schedulerInfo) {
        this.schedulerInfo = schedulerInfo;
    }

    public String getDependProgressId() {
        return dependProgressId;
    }

    public void setDependProgressId(String dependProgressId) {
        this.dependProgressId = dependProgressId;
    }

    public String getRunId() {
        return runId;
    }

    public void setRunId(String runId) {
        this.runId = runId;
    }
}
