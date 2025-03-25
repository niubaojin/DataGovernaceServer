package com.synway.datarelation.pojo.modelmonitor;


import com.synway.datarelation.util.DateUtil;

import java.util.List;
import java.util.Objects;

/**
 * @author
 * @date 2019/4/24 9:00
 */
public class ModelNodeInfo {

    private String nodeName;//  节点名称
    private String nodeDisplayName;//  节点展示名称
    private int runType;//  节点类型，参见 RunType 类
    private int fileId;//  代码编号
    private int fileVersion;//  代码版本号
    private String filePath;//  代码在压缩包的相对路径
    private int nodeType;//  执行代码类型  6:shell  10:sql  11:mr  23:同步任务  98:工作流  99:虚节点
    private String description;//  描述信息
    private String paraValue; //  参数信息
    private String startEffectDate;//  允许调度的起始日期
    private String endEffectDate;//  允许调度的终止日期
    private String cronExpress;// cron 表达式
    private String owner;//  负责人账号
    private String flowName;//流程名
    private String projectName;//项目名
    private int dependentType;//  依赖类型，参见 DependentType 类
    private String source;//  来源
    private String connection; //  连接串
    private String createTime;//  创建时间
    private String createUser; //  创建人
    private String modifyTime;//  最新修改时间
    private String modifyUser;//  最新修改人
    private int priority;//  优先级
    private String resourceGroupIdentifier;//  节点所属资源组标识
    private int baselineId;//  节点所属基线编号
    private int cycleType;//  周期类型，参见 CycleType 类
    private List<ModelNodeRelationInfo> childRelations;//父节点信息
    private List<ModelNodeRelationInfo> parentRelations;//子节点信息
    private String dqcDescription;
    private int dqcType;
    private String isFlowType;//true 表节点是 Flow 类型,false 是节点类型

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeDisplayName() {
        return nodeDisplayName;
    }

    public void setNodeDisplayName(String nodeDisplayName) {
        this.nodeDisplayName = nodeDisplayName;
    }

    public int getRunType() {
        return runType;
    }

    public void setRunType(int runType) {
        this.runType = runType;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public int getFileVersion() {
        return fileVersion;
    }

    public void setFileVersion(int fileVersion) {
        this.fileVersion = fileVersion;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getNodeType() {
        return nodeType;
    }

    public void setNodeType(int nodeType) {
        this.nodeType = nodeType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParaValue() {
        return paraValue;
    }

    public void setParaValue(String paraValue) {
        this.paraValue = paraValue;
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

    public String getCronExpress() {
        return cronExpress;
    }

    public void setCronExpress(String cronExpress) {
        this.cronExpress = cronExpress;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getDependentType() {
        return dependentType;
    }

    public void setDependentType(int dependentType) {
        this.dependentType = dependentType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
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

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getResourceGroupIdentifier() {
        return resourceGroupIdentifier;
    }

    public void setResourceGroupIdentifier(String resourceGroupIdentifier) {
        this.resourceGroupIdentifier = resourceGroupIdentifier;
    }

    public int getBaselineId() {
        return baselineId;
    }

    public void setBaselineId(int baselineId) {
        this.baselineId = baselineId;
    }

    public int getCycleType() {
        return cycleType;
    }

    public void setCycleType(int cycleType) {
        this.cycleType = cycleType;
    }

    public List<ModelNodeRelationInfo> getChildRelations() {
        return childRelations;
    }

    public void setChildRelations(List<ModelNodeRelationInfo> childRelations) {
        this.childRelations = childRelations;
    }

    public List<ModelNodeRelationInfo> getParentRelations() {
        return parentRelations;
    }

    public void setParentRelations(List<ModelNodeRelationInfo> parentRelations) {
        this.parentRelations = parentRelations;
    }

    public String getDqcDescription() {
        return dqcDescription;
    }

    public void setDqcDescription(String dqcDescription) {
        this.dqcDescription = dqcDescription;
    }

    public int getDqcType() {
        return dqcType;
    }

    public void setDqcType(int dqcType) {
        this.dqcType = dqcType;
    }

    public String getIsFlowType() {
        return isFlowType;
    }

    public void setIsFlowType(String isFlowType) {
        this.isFlowType = isFlowType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ModelNodeInfo that = (ModelNodeInfo) o;
        boolean createTimeSame = false;
        try {
            createTimeSame = DateUtil.parseDate(createTime,DateUtil.DEFAULT_PATTERN_DATETIME)
                    .compareTo(DateUtil.parseDate(that.createTime,DateUtil.DEFAULT_PATTERN_DATETIME))==0;
        }catch (Exception e){
            return false;
        }
        return Objects.equals(nodeName, that.nodeName) &&
                Objects.equals(projectName, that.projectName) &&
                Objects.equals(flowName, that.flowName) && createTimeSame;
    }

    @Override
    public int hashCode() {

        return Objects.hash(nodeName, nodeDisplayName, runType, fileId, fileVersion, filePath, nodeType, description, paraValue, startEffectDate, endEffectDate, cronExpress, owner, flowName, projectName, dependentType, source, connection, createTime, createUser, modifyTime, modifyUser, priority, resourceGroupIdentifier, baselineId, cycleType, childRelations, parentRelations, dqcDescription, dqcType, isFlowType);
    }
}
