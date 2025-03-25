package com.synway.datarelation.pojo.modelmonitor;

import java.util.List;
import java.util.Objects;

/**
 * @author
 * @date 2019/4/24 9:04
 */
public class ModelNodeInsInfo implements Comparable{

    private Integer historyId; //  历史实例 ID
    private Integer instanceId;//  任务实例 ID
    private Integer dagId;//  任务实例所属的流程实例 ID
    private Integer instanceType;//任务类型：0：  正常  1：一次性任务  2:表示暂停的节点实例  3：空跑
    private Integer dagType;// DAG 类型
    private Integer status;//  任务状态
    private Integer opSeq; //  操作序列号
    private Integer opCode;//  操作命令
    private String owner;//  负责人，统一使用工号
    private String bizdate;//  业务日期
    private String gmtdate;//  处理日期
    private Integer nodeType;//  执行代码类型，如 odps_sql 等
    private Integer priority;//  优先级
    private String paraValue; //  参数信息
    private Integer projectId;//  任务实例所属应用 ID
    private String projectName;//  任务实例所属应用名
    private Integer relatedDagId; //  任务关联的工作流实例编号
    private String finishTime;//  任务结束时间
    private String beginWaitTimeTime;//  变成等待时间状态的时间
    private String beginWaitResTime;//  变成等待资源状态的时间
    private String beginRunningTime;//  变成运行中状态的时间
    private String createTime;//  创建时间
    private String createUser;//  创建人
    private String modifyTime;//  最新修改时间
    private String modifyUser;//  最新修改人
    private Integer cycleType;//  周期类型
    private String cycleTime;//  周期时间
    private Integer dependentType;//  依赖关系类型
    private String nodeName;//  节点名称
    private String flowName;//  根节点所属工作流定义
    private Integer inGroupId;//  该周期是当天的第几个周期
    private String resourceGroupIdentifier;//  节点所属资源组标识
    private Integer baselineId;//  任务所属基线编号
    private List<ModelNodeInsRelationInfo> parentNodeRelations;// 父节点实例集合
    private List<ModelNodeInsRelationInfo> childNodeRelations;// 子节点实例集合
    private String dqcType;
    private String source;

    @Override
    public int compareTo(Object o) {

        return 0;
    }

    public Integer getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Integer historyId) {
        this.historyId = historyId;
    }

    public Integer getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(Integer instanceId) {
        this.instanceId = instanceId;
    }

    public Integer getDagId() {
        return dagId;
    }

    public void setDagId(Integer dagId) {
        this.dagId = dagId;
    }

    public Integer getInstanceType() {
        return instanceType;
    }

    public void setInstanceType(Integer instanceType) {
        this.instanceType = instanceType;
    }

    public Integer getDagType() {
        return dagType;
    }

    public void setDagType(Integer dagType) {
        this.dagType = dagType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOpSeq() {
        return opSeq;
    }

    public void setOpSeq(Integer opSeq) {
        this.opSeq = opSeq;
    }

    public Integer getOpCode() {
        return opCode;
    }

    public void setOpCode(Integer opCode) {
        this.opCode = opCode;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getBizdate() {
        return bizdate;
    }

    public void setBizdate(String bizdate) {
        this.bizdate = bizdate;
    }

    public String getGmtdate() {
        return gmtdate;
    }

    public void setGmtdate(String gmtdate) {
        this.gmtdate = gmtdate;
    }

    public Integer getNodeType() {
        return nodeType;
    }

    public void setNodeType(Integer nodeType) {
        this.nodeType = nodeType;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getParaValue() {
        return paraValue;
    }

    public void setParaValue(String paraValue) {
        this.paraValue = paraValue;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getRelatedDagId() {
        return relatedDagId;
    }

    public void setRelatedDagId(Integer relatedDagId) {
        this.relatedDagId = relatedDagId;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getBeginWaitTimeTime() {
        return beginWaitTimeTime;
    }

    public void setBeginWaitTimeTime(String beginWaitTimeTime) {
        this.beginWaitTimeTime = beginWaitTimeTime;
    }

    public String getBeginWaitResTime() {
        return beginWaitResTime;
    }

    public void setBeginWaitResTime(String beginWaitResTime) {
        this.beginWaitResTime = beginWaitResTime;
    }

    public String getBeginRunningTime() {
        return beginRunningTime;
    }

    public void setBeginRunningTime(String beginRunningTime) {
        this.beginRunningTime = beginRunningTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public Integer getCycleType() {
        return cycleType;
    }

    public void setCycleType(Integer cycleType) {
        this.cycleType = cycleType;
    }

    public String getCycleTime() {
        return cycleTime;
    }

    public void setCycleTime(String cycleTime) {
        this.cycleTime = cycleTime;
    }

    public Integer getDependentType() {
        return dependentType;
    }

    public void setDependentType(Integer dependentType) {
        this.dependentType = dependentType;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public Integer getInGroupId() {
        return inGroupId;
    }

    public void setInGroupId(Integer inGroupId) {
        this.inGroupId = inGroupId;
    }

    public String getResourceGroupIdentifier() {
        return resourceGroupIdentifier;
    }

    public void setResourceGroupIdentifier(String resourceGroupIdentifier) {
        this.resourceGroupIdentifier = resourceGroupIdentifier;
    }

    public Integer getBaselineId() {
        return baselineId;
    }

    public void setBaselineId(Integer baselineId) {
        this.baselineId = baselineId;
    }

    public List<ModelNodeInsRelationInfo> getParentNodeRelations() {
        return parentNodeRelations;
    }

    public void setParentNodeRelations(List<ModelNodeInsRelationInfo> parentNodeRelations) {
        this.parentNodeRelations = parentNodeRelations;
    }

    public List<ModelNodeInsRelationInfo> getChildNodeRelations() {
        return childNodeRelations;
    }

    public void setChildNodeRelations(List<ModelNodeInsRelationInfo> childNodeRelations) {
        this.childNodeRelations = childNodeRelations;
    }

    public String getDqcType() {
        return dqcType;
    }

    public void setDqcType(String dqcType) {
        this.dqcType = dqcType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ModelNodeInsInfo that = (ModelNodeInsInfo) o;
        return Objects.equals(projectName, that.projectName) &&
                Objects.equals(nodeName, that.nodeName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(projectName, nodeName);
    }
}
