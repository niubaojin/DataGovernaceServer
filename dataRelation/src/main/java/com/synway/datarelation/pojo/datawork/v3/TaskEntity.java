package com.synway.datarelation.pojo.datawork.v3;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.synway.datarelation.util.DateDeserializer;
import com.synway.datarelation.util.DateSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.Date;
import java.util.Objects;

/**
 * 任务的实例信息
 * 对应阿里大数据平台(DataWorks)api版本号为 3.7
 */
public class TaskEntity{
    private Long id; //历史任务自增ID
    private Long taskId; // 任务实例ID
    private Long nodeId; // 任务实例对应的节点定义ID
    private Long dagId; // 任务实例所属的流程实例ID
    private Integer inGroupId; // 该周期是当天的第几个周期，小时任务
    private Integer taskType; // 任务类型：0： 正常 1：一次性任务 2:表示暂停的节点实例 3：空跑
    private Integer dagType; // DAG类型：1: routing 2: smoke 3: completeData 4:onceTime
    // 序列化代码时用到自定义的代码 getter方法
    @JsonSerialize(using= DateSerializer.class, include= JsonSerialize.Inclusion.NON_NULL)
    // 反序列化代码时用到自定义的代码  setter方法
    @JsonDeserialize(using= DateDeserializer.class)
    private Date dueTime; // 任务定时时间
    private Integer status; // 任务状态
    private Long opSeq; // 操作序列号
    private Integer opCode; // 操作命令
    private String owner; // 负责人，统一使用工号
    @JsonSerialize(using= DateSerializer.class, include= JsonSerialize.Inclusion.NON_NULL)
    @JsonDeserialize(using= DateDeserializer.class)
    private Date bizdate; // 业务日期
    @JsonSerialize(using= DateSerializer.class, include= JsonSerialize.Inclusion.NON_NULL)
    @JsonDeserialize(using= DateDeserializer.class)
    private Date gmtdate; // 处理日期
    private String gateway; // 任务运行的gateway机器
    private String gwProcessId; // 任务运行的gateway进程号
    private String gwLogFile; // 任务运行的gateway日志
    @JsonSerialize(using= DateSerializer.class, include= JsonSerialize.Inclusion.NON_NULL)
    @JsonDeserialize(using= DateDeserializer.class)
    private Date alisaReturnTime; // alisa的回调序列号
    private String prgName; // 执行代码路径
    private Integer prgType; // 执行代码类型，如odps_sql等
    private Integer priority; // 优先级
    private Integer weight; // 权重
    private String execName; // 执行脚本路径
    private Long fileId; // 代码文件ID
    private Integer fileVersion; // 代码文件版本号
    private String odpsProjectName; // 原来的package_id
    private String paraValue; // 参数信息
    private String gwLogLocalFile; // 任务运行的gateway本地日志
    private String resGroupIdentifier; // 资源组识别符
    private Long resGroupId; // 任务实例所属资源组ID
    private Long baseLineId; // 任务实例所属基线ID
    private Long appId; // 任务实例所属应用ID
    private String appName; // 应用名
    @JsonSerialize(using= DateSerializer.class, include= JsonSerialize.Inclusion.NON_NULL)
    @JsonDeserialize(using= DateDeserializer.class)
    private Date beginWaitTimeTime; // 变成等待时间状态的时间
    @JsonSerialize(using= DateSerializer.class, include= JsonSerialize.Inclusion.NON_NULL)
    @JsonDeserialize(using= DateDeserializer.class)
    private Date beginWaitResTime; // 变成等待资源状态的时间
    @JsonSerialize(using= DateSerializer.class, include= JsonSerialize.Inclusion.NON_NULL)
    @JsonDeserialize(using= DateDeserializer.class)
    private Date beginRunningTime; // 变成运行中状态的时间
    @JsonSerialize(using= DateSerializer.class, include= JsonSerialize.Inclusion.NON_NULL)
    @JsonDeserialize(using= DateDeserializer.class)
    private Date finishTime; // 任务结束时间
    @JsonSerialize(using= DateSerializer.class, include= JsonSerialize.Inclusion.NON_NULL)
    @JsonDeserialize(using= DateDeserializer.class)
    private Date createTime; // 创建时间
    private String createUser; // 创建人
    @JsonSerialize(using= DateSerializer.class, include= JsonSerialize.Inclusion.NON_NULL)
    @JsonDeserialize(using= DateDeserializer.class)
    private Date modifyTime; // 最新修改时间
    private String modifyUser; // 最新修改人
    private Integer multiInstCheckType; // 多实例检测标识
    private Integer multiKillType; // 多实例kill
    private Integer cycType; // 周期类型
    @JsonSerialize(using= DateSerializer.class, include= JsonSerialize.Inclusion.NON_NULL)
    @JsonDeserialize(using= DateDeserializer.class)
    private Date cycTime; // 周期时间
    private Integer roleType; // 节点角色类型，如叶节点、非叶结点
    private Integer dependentType; // 依赖关系类型
    @JsonSerialize(using= DateSerializer.class, include= JsonSerialize.Inclusion.NON_NULL)
    @JsonDeserialize(using= DateDeserializer.class)
    private Date deployDate; // 发布时间
    private String nodeName; // 节点名称
    private Integer rerunTimes; // 重试次数
    @JsonSerialize(using= DateSerializer.class, include= JsonSerialize.Inclusion.NON_NULL)
    @JsonDeserialize(using= DateDeserializer.class)
    private Date nodeModifyTime; // 节点的修改时间
    @JsonSerialize(using= DateSerializer.class, include= JsonSerialize.Inclusion.NON_NULL)
    @JsonDeserialize(using= DateDeserializer.class)
    private Date refreshTime;
    @JsonSerialize(using= DateSerializer.class, include= JsonSerialize.Inclusion.NON_NULL)
    @JsonDeserialize(using= DateDeserializer.class)
    private Date delayExecTime;
    private Boolean rerunAble;
    private Integer isRunOver;//表示实例是否运行过
    private Long bizId;
    private String parentTaskRelations; // 父关系
    private String childTaskRelations; // 子关系
    private String parentOutputTabMetas; //依赖表的Meta产出信息
    private String outputTabMetas; // 本实例输出表的Meta产出信息
    private String cpuConsumption;
    private String memoryConsumption;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public Long getDagId() {
        return dagId;
    }

    public void setDagId(Long dagId) {
        this.dagId = dagId;
    }

    public Integer getInGroupId() {
        return inGroupId;
    }

    public void setInGroupId(Integer inGroupId) {
        this.inGroupId = inGroupId;
    }

    public Integer getTaskType() {
        return taskType;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    public Integer getDagType() {
        return dagType;
    }

    public void setDagType(Integer dagType) {
        this.dagType = dagType;
    }

    public Date getDueTime() {
        return dueTime;
    }

    public void setDueTime(Date dueTime) {
        this.dueTime = dueTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getOpSeq() {
        return opSeq;
    }

    public void setOpSeq(Long opSeq) {
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

    public Date getBizdate() {
        return bizdate;
    }

    public void setBizdate(Date bizdate) {
        this.bizdate = bizdate;
    }

    public Date getGmtdate() {
        return gmtdate;
    }

    public void setGmtdate(Date gmtdate) {
        this.gmtdate = gmtdate;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getGwProcessId() {
        return gwProcessId;
    }

    public void setGwProcessId(String gwProcessId) {
        this.gwProcessId = gwProcessId;
    }

    public String getGwLogFile() {
        return gwLogFile;
    }

    public void setGwLogFile(String gwLogFile) {
        this.gwLogFile = gwLogFile;
    }

    public Date getAlisaReturnTime() {
        return alisaReturnTime;
    }

    public void setAlisaReturnTime(Date alisaReturnTime) {
        this.alisaReturnTime = alisaReturnTime;
    }

    public String getPrgName() {
        return prgName;
    }

    public void setPrgName(String prgName) {
        this.prgName = prgName;
    }

    public Integer getPrgType() {
        return prgType;
    }

    public void setPrgType(Integer prgType) {
        this.prgType = prgType;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getExecName() {
        return execName;
    }

    public void setExecName(String execName) {
        this.execName = execName;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public Integer getFileVersion() {
        return fileVersion;
    }

    public void setFileVersion(Integer fileVersion) {
        this.fileVersion = fileVersion;
    }

    public String getOdpsProjectName() {
        return odpsProjectName;
    }

    public void setOdpsProjectName(String odpsProjectName) {
        this.odpsProjectName = odpsProjectName;
    }

    public String getParaValue() {
        return paraValue;
    }

    public void setParaValue(String paraValue) {
        this.paraValue = paraValue;
    }

    public String getGwLogLocalFile() {
        return gwLogLocalFile;
    }

    public void setGwLogLocalFile(String gwLogLocalFile) {
        this.gwLogLocalFile = gwLogLocalFile;
    }

    public String getResGroupIdentifier() {
        return resGroupIdentifier;
    }

    public void setResGroupIdentifier(String resGroupIdentifier) {
        this.resGroupIdentifier = resGroupIdentifier;
    }

    public Long getResGroupId() {
        return resGroupId;
    }

    public void setResGroupId(Long resGroupId) {
        this.resGroupId = resGroupId;
    }

    public Long getBaseLineId() {
        return baseLineId;
    }

    public void setBaseLineId(Long baseLineId) {
        this.baseLineId = baseLineId;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Date getBeginWaitTimeTime() {
        return beginWaitTimeTime;
    }

    public void setBeginWaitTimeTime(Date beginWaitTimeTime) {
        this.beginWaitTimeTime = beginWaitTimeTime;
    }

    public Date getBeginWaitResTime() {
        return beginWaitResTime;
    }

    public void setBeginWaitResTime(Date beginWaitResTime) {
        this.beginWaitResTime = beginWaitResTime;
    }

    public Date getBeginRunningTime() {
        return beginRunningTime;
    }

    public void setBeginRunningTime(Date beginRunningTime) {
        this.beginRunningTime = beginRunningTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public Integer getMultiInstCheckType() {
        return multiInstCheckType;
    }

    public void setMultiInstCheckType(Integer multiInstCheckType) {
        this.multiInstCheckType = multiInstCheckType;
    }

    public Integer getMultiKillType() {
        return multiKillType;
    }

    public void setMultiKillType(Integer multiKillType) {
        this.multiKillType = multiKillType;
    }

    public Integer getCycType() {
        return cycType;
    }

    public void setCycType(Integer cycType) {
        this.cycType = cycType;
    }

    public Date getCycTime() {
        return cycTime;
    }

    public void setCycTime(Date cycTime) {
        this.cycTime = cycTime;
    }

    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }

    public Integer getDependentType() {
        return dependentType;
    }

    public void setDependentType(Integer dependentType) {
        this.dependentType = dependentType;
    }

    public Date getDeployDate() {
        return deployDate;
    }

    public void setDeployDate(Date deployDate) {
        this.deployDate = deployDate;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public Integer getRerunTimes() {
        return rerunTimes;
    }

    public void setRerunTimes(Integer rerunTimes) {
        this.rerunTimes = rerunTimes;
    }

    public Date getNodeModifyTime() {
        return nodeModifyTime;
    }

    public void setNodeModifyTime(Date nodeModifyTime) {
        this.nodeModifyTime = nodeModifyTime;
    }

    public Date getRefreshTime() {
        return refreshTime;
    }

    public void setRefreshTime(Date refreshTime) {
        this.refreshTime = refreshTime;
    }

    public Date getDelayExecTime() {
        return delayExecTime;
    }

    public void setDelayExecTime(Date delayExecTime) {
        this.delayExecTime = delayExecTime;
    }

    public Boolean getRerunAble() {
        return rerunAble;
    }

    public void setRerunAble(Boolean rerunAble) {
        this.rerunAble = rerunAble;
    }

    public Integer getIsRunOver() {
        return isRunOver;
    }

    public void setIsRunOver(Integer isRunOver) {
        this.isRunOver = isRunOver;
    }

    public Long getBizId() {
        return bizId;
    }

    public void setBizId(Long bizId) {
        this.bizId = bizId;
    }

    public String getParentTaskRelations() {
        return parentTaskRelations;
    }

    public void setParentTaskRelations(String parentTaskRelations) {
        this.parentTaskRelations = parentTaskRelations;
    }

    public String getChildTaskRelations() {
        return childTaskRelations;
    }

    public void setChildTaskRelations(String childTaskRelations) {
        this.childTaskRelations = childTaskRelations;
    }

    public String getParentOutputTabMetas() {
        return parentOutputTabMetas;
    }

    public void setParentOutputTabMetas(String parentOutputTabMetas) {
        this.parentOutputTabMetas = parentOutputTabMetas;
    }

    public String getOutputTabMetas() {
        return outputTabMetas;
    }

    public void setOutputTabMetas(String outputTabMetas) {
        this.outputTabMetas = outputTabMetas;
    }

    public String getCpuConsumption() {
        return cpuConsumption;
    }

    public void setCpuConsumption(String cpuConsumption) {
        this.cpuConsumption = cpuConsumption;
    }

    public String getMemoryConsumption() {
        return memoryConsumption;
    }

    public void setMemoryConsumption(String memoryConsumption) {
        this.memoryConsumption = memoryConsumption;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TaskEntity that = (TaskEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(taskId, that.taskId) &&
                Objects.equals(nodeId, that.nodeId) &&
                Objects.equals(dagId, that.dagId) &&
                Objects.equals(inGroupId, that.inGroupId) &&
                Objects.equals(taskType, that.taskType) &&
                Objects.equals(dagType, that.dagType) &&
                Objects.equals(dueTime, that.dueTime) &&
                Objects.equals(status, that.status) &&
                Objects.equals(opSeq, that.opSeq) &&
                Objects.equals(opCode, that.opCode) &&
                Objects.equals(owner, that.owner) &&
                Objects.equals(bizdate, that.bizdate) &&
                Objects.equals(gmtdate, that.gmtdate) &&
                Objects.equals(gateway, that.gateway) &&
                Objects.equals(gwProcessId, that.gwProcessId) &&
                Objects.equals(gwLogFile, that.gwLogFile) &&
                Objects.equals(alisaReturnTime, that.alisaReturnTime) &&
                Objects.equals(prgName, that.prgName) &&
                Objects.equals(prgType, that.prgType) &&
                Objects.equals(priority, that.priority) &&
                Objects.equals(weight, that.weight) &&
                Objects.equals(execName, that.execName) &&
                Objects.equals(fileId, that.fileId) &&
                Objects.equals(fileVersion, that.fileVersion) &&
                Objects.equals(odpsProjectName, that.odpsProjectName) &&
                Objects.equals(paraValue, that.paraValue) &&
                Objects.equals(gwLogLocalFile, that.gwLogLocalFile) &&
                Objects.equals(resGroupIdentifier, that.resGroupIdentifier) &&
                Objects.equals(resGroupId, that.resGroupId) &&
                Objects.equals(baseLineId, that.baseLineId) &&
                Objects.equals(appId, that.appId) &&
                Objects.equals(appName, that.appName) &&
                Objects.equals(beginWaitTimeTime, that.beginWaitTimeTime) &&
                Objects.equals(beginWaitResTime, that.beginWaitResTime) &&
                Objects.equals(beginRunningTime, that.beginRunningTime) &&
                Objects.equals(finishTime, that.finishTime) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(createUser, that.createUser) &&
                Objects.equals(modifyTime, that.modifyTime) &&
                Objects.equals(modifyUser, that.modifyUser) &&
                Objects.equals(multiInstCheckType, that.multiInstCheckType) &&
                Objects.equals(multiKillType, that.multiKillType) &&
                Objects.equals(cycType, that.cycType) &&
                Objects.equals(cycTime, that.cycTime) &&
                Objects.equals(roleType, that.roleType) &&
                Objects.equals(dependentType, that.dependentType) &&
                Objects.equals(deployDate, that.deployDate) &&
                Objects.equals(nodeName, that.nodeName) &&
                Objects.equals(rerunTimes, that.rerunTimes) &&
                Objects.equals(nodeModifyTime, that.nodeModifyTime) &&
                Objects.equals(refreshTime, that.refreshTime) &&
                Objects.equals(delayExecTime, that.delayExecTime) &&
                Objects.equals(rerunAble, that.rerunAble) &&
                Objects.equals(isRunOver, that.isRunOver) &&
                Objects.equals(bizId, that.bizId) &&
                Objects.equals(parentTaskRelations, that.parentTaskRelations) &&
                Objects.equals(childTaskRelations, that.childTaskRelations) &&
                Objects.equals(parentOutputTabMetas, that.parentOutputTabMetas) &&
                Objects.equals(outputTabMetas, that.outputTabMetas);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, taskId, nodeId, dagId, inGroupId, taskType, dagType, dueTime, status, opSeq, opCode, owner, bizdate, gmtdate, gateway, gwProcessId, gwLogFile, alisaReturnTime, prgName, prgType, priority, weight, execName, fileId, fileVersion, odpsProjectName, paraValue, gwLogLocalFile, resGroupIdentifier, resGroupId, baseLineId, appId, appName, beginWaitTimeTime, beginWaitResTime, beginRunningTime, finishTime, createTime, createUser, modifyTime, modifyUser, multiInstCheckType, multiKillType, cycType, cycTime, roleType, dependentType, deployDate, nodeName, rerunTimes, nodeModifyTime, refreshTime, delayExecTime, rerunAble, isRunOver, bizId, parentTaskRelations, childTaskRelations, parentOutputTabMetas, outputTabMetas);
    }

    @Override
    public String toString() {
        return "TaskEntity{" +
                "id=" + id +
                ", taskId=" + taskId +
                ", nodeId=" + nodeId +
                ", dagId=" + dagId +
                ", inGroupId=" + inGroupId +
                ", taskType=" + taskType +
                ", dagType=" + dagType +
                ", dueTime=" + dueTime +
                ", status=" + status +
                ", opSeq=" + opSeq +
                ", opCode=" + opCode +
                ", owner='" + owner + '\'' +
                ", bizdate=" + bizdate +
                ", gmtdate=" + gmtdate +
                ", gateway='" + gateway + '\'' +
                ", gwProcessId='" + gwProcessId + '\'' +
                ", gwLogFile='" + gwLogFile + '\'' +
                ", alisaReturnTime=" + alisaReturnTime +
                ", prgName='" + prgName + '\'' +
                ", prgType=" + prgType +
                ", priority=" + priority +
                ", weight=" + weight +
                ", execName='" + execName + '\'' +
                ", fileId=" + fileId +
                ", fileVersion=" + fileVersion +
                ", odpsProjectName='" + odpsProjectName + '\'' +
                ", paraValue='" + paraValue + '\'' +
                ", gwLogLocalFile='" + gwLogLocalFile + '\'' +
                ", resGroupIdentifier='" + resGroupIdentifier + '\'' +
                ", resGroupId=" + resGroupId +
                ", baseLineId=" + baseLineId +
                ", appId=" + appId +
                ", appName='" + appName + '\'' +
                ", beginWaitTimeTime=" + beginWaitTimeTime +
                ", beginWaitResTime=" + beginWaitResTime +
                ", beginRunningTime=" + beginRunningTime +
                ", finishTime=" + finishTime +
                ", createTime=" + createTime +
                ", createUser='" + createUser + '\'' +
                ", modifyTime=" + modifyTime +
                ", modifyUser='" + modifyUser + '\'' +
                ", multiInstCheckType=" + multiInstCheckType +
                ", multiKillType=" + multiKillType +
                ", cycType=" + cycType +
                ", cycTime=" + cycTime +
                ", roleType=" + roleType +
                ", dependentType=" + dependentType +
                ", deployDate=" + deployDate +
                ", nodeName='" + nodeName + '\'' +
                ", rerunTimes=" + rerunTimes +
                ", nodeModifyTime=" + nodeModifyTime +
                ", refreshTime=" + refreshTime +
                ", delayExecTime=" + delayExecTime +
                ", rerunAble=" + rerunAble +
                ", isRunOver=" + isRunOver +
                ", bizId=" + bizId +
                ", parentTaskRelations='" + parentTaskRelations + '\'' +
                ", childTaskRelations='" + childTaskRelations + '\'' +
                ", parentOutputTabMetas='" + parentOutputTabMetas + '\'' +
                ", outputTabMetas='" + outputTabMetas + '\'' +
                '}';
    }

    //    private List<TaskRelationDto> parentTaskRelations; // 父关系
//    private List<TaskRelationDto> childTaskRelations; // 子关系
//    private List<MetaTable> parentOutputTabMetas; //依赖表的Meta产出信息
//    private List<MetaTable> outputTabMetas; // 本实例输出表的Meta产出信息
}
