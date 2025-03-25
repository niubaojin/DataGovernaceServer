package com.synway.datarelation.pojo.datawork.v3;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.synway.datarelation.util.DateDeserializer;
import com.synway.datarelation.util.DateSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.Date;
import java.util.List;

/**
 * 节点的返回信息
 */
public class NodeEntity {
    private Long appId;
    private Long baseLineId;
    // bizId 当是业务流程时，同一个bizId属于同一个业务流程
    private Integer bizId;
    @JsonSerialize(using= DateSerializer.class, include= JsonSerialize.Inclusion.NON_NULL)
    @JsonDeserialize(using= DateDeserializer.class)
    private Date createTime; // 创建时间
    private String createUser; // 创建人
    private String cronExpress; //定时触发器
    private Integer cycType; // 周期类型
    private Integer dependentType; // 依赖关系类型
    @JsonSerialize(using= DateSerializer.class, include= JsonSerialize.Inclusion.NON_NULL)
    @JsonDeserialize(using= DateDeserializer.class)
    private Date deployDate; // 发布时间
    private String description; //
    private Integer dqcType;
    @JsonSerialize(using= DateSerializer.class, include= JsonSerialize.Inclusion.NON_NULL)
    @JsonDeserialize(using= DateDeserializer.class)
    private Date endEffectDate;
    private Integer envType;
    private String execName;
    private Integer executeType;  //
    private Long fileId;     // 代码文件ID
    private Integer fileVersion; // 代码文件版本号
    private Long flowId;
    @JsonSerialize(using= DateSerializer.class, include= JsonSerialize.Inclusion.NON_NULL)
    @JsonDeserialize(using= DateDeserializer.class)
    private Date modifyTime; // 最新修改时间
    private String modifyUser; // 最新修改人

    private Integer multiInstCheckType; // 多实例检测标识
    private Integer multiKillType; // 多实例kill
    private String nodeFrom;
    private Long nodeId;
    private String nodeName;
    private Integer nodeType;
    private String odpsProjectName; // 原来的package_id
    private String owner;
    private Integer prgType; // 执行代码类型，如odps_sql等
    private Integer priority; // 优先级
    @JsonSerialize(using= DateSerializer.class, include= JsonSerialize.Inclusion.NON_NULL)
    @JsonDeserialize(using= DateDeserializer.class)
    private Date prodModifyTime;
    private Boolean rerunAble;
    private Long resGroupId; // 任务实例所属资源组ID
    private String source;
    @JsonSerialize(using= DateSerializer.class, include= JsonSerialize.Inclusion.NON_NULL)
    @JsonDeserialize(using= DateDeserializer.class)
    private Date startEffectDate;
//    private List<RelationNode> inputs;  //输入对象
//    private List<RelationNode> outputs;
    private String inputs;  //输入对象
    private String outputs;
    // 20200514 确定节点之间的连接关系
    private List<NodeRelation> parentNodeRelations=null;
    private List<NodeRelation> childNodeRelations=null;
    // 20210119 prgType是98的，为旧工作流的flowId
    private Long relatedFlowId;
    private Long nextNodeId;
    private String nextNodeName;


    public Integer getBizId() {
        return bizId;
    }

    public void setBizId(Integer bizId) {
        this.bizId = bizId;
    }

    public List<NodeRelation> getParentNodeRelations() {
        return parentNodeRelations;
    }

    public void setParentNodeRelations(List<NodeRelation> parentNodeRelations) {
        this.parentNodeRelations = parentNodeRelations;
    }

    public List<NodeRelation> getChildNodeRelations() {
        return childNodeRelations;
    }

    public void setChildNodeRelations(List<NodeRelation> childNodeRelations) {
        this.childNodeRelations = childNodeRelations;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public Long getBaseLineId() {
        return baseLineId;
    }

    public void setBaseLineId(Long baseLineId) {
        this.baseLineId = baseLineId;
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

    public String getCronExpress() {
        return cronExpress;
    }

    public void setCronExpress(String cronExpress) {
        this.cronExpress = cronExpress;
    }

    public Integer getCycType() {
        return cycType;
    }

    public void setCycType(Integer cycType) {
        this.cycType = cycType;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDqcType() {
        return dqcType;
    }

    public void setDqcType(Integer dqcType) {
        this.dqcType = dqcType;
    }

    public Date getEndEffectDate() {
        return endEffectDate;
    }

    public void setEndEffectDate(Date endEffectDate) {
        this.endEffectDate = endEffectDate;
    }

    public Integer getEnvType() {
        return envType;
    }

    public void setEnvType(Integer envType) {
        this.envType = envType;
    }

    public String getExecName() {
        return execName;
    }

    public void setExecName(String execName) {
        this.execName = execName;
    }

    public Integer getExecuteType() {
        return executeType;
    }

    public void setExecuteType(Integer executeType) {
        this.executeType = executeType;
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

    public Long getFlowId() {
        return flowId;
    }

    public void setFlowId(Long flowId) {
        this.flowId = flowId;
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

    public String getNodeFrom() {
        return nodeFrom;
    }

    public void setNodeFrom(String nodeFrom) {
        this.nodeFrom = nodeFrom;
    }

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public Integer getNodeType() {
        return nodeType;
    }

    public void setNodeType(Integer nodeType) {
        this.nodeType = nodeType;
    }

    public String getOdpsProjectName() {
        return odpsProjectName;
    }

    public void setOdpsProjectName(String odpsProjectName) {
        this.odpsProjectName = odpsProjectName;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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

    public Date getProdModifyTime() {
        return prodModifyTime;
    }

    public void setProdModifyTime(Date prodModifyTime) {
        this.prodModifyTime = prodModifyTime;
    }

    public Boolean getRerunAble() {
        return rerunAble;
    }

    public void setRerunAble(Boolean rerunAble) {
        this.rerunAble = rerunAble;
    }

    public Long getResGroupId() {
        return resGroupId;
    }

    public void setResGroupId(Long resGroupId) {
        this.resGroupId = resGroupId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Date getStartEffectDate() {
        return startEffectDate;
    }

    public void setStartEffectDate(Date startEffectDate) {
        this.startEffectDate = startEffectDate;
    }

    public String getInputs() {
        return inputs;
    }

    public void setInputs(String inputs) {
        this.inputs = inputs;
    }

    public String getOutputs() {
        return outputs;
    }

    public void setOutputs(String outputs) {
        this.outputs = outputs;
    }

    public Long getRelatedFlowId() {
        return relatedFlowId;
    }

    public void setRelatedFlowId(Long relatedFlowId) {
        this.relatedFlowId = relatedFlowId;
    }

    public Long getNextNodeId() {
        return nextNodeId;
    }

    public void setNextNodeId(Long nextNodeId) {
        this.nextNodeId = nextNodeId;
    }

    public String getNextNodeName() {
        return nextNodeName;
    }

    public void setNextNodeName(String nextNodeName) {
        this.nextNodeName = nextNodeName;
    }

    @Override
    public String toString() {
        return "NodeEntity{" +
                "appId=" + appId +
                ", baseLineId=" + baseLineId +
                ", bizId=" + bizId +
                ", createTime=" + createTime +
                ", createUser='" + createUser + '\'' +
                ", cronExpress='" + cronExpress + '\'' +
                ", cycType=" + cycType +
                ", dependentType=" + dependentType +
                ", deployDate=" + deployDate +
                ", description='" + description + '\'' +
                ", dqcType=" + dqcType +
                ", endEffectDate=" + endEffectDate +
                ", envType=" + envType +
                ", execName='" + execName + '\'' +
                ", executeType=" + executeType +
                ", fileId=" + fileId +
                ", fileVersion=" + fileVersion +
                ", flowId=" + flowId +
                ", modifyTime=" + modifyTime +
                ", modifyUser='" + modifyUser + '\'' +
                ", multiInstCheckType=" + multiInstCheckType +
                ", multiKillType=" + multiKillType +
                ", nodeFrom='" + nodeFrom + '\'' +
                ", nodeId=" + nodeId +
                ", nodeName='" + nodeName + '\'' +
                ", nodeType=" + nodeType +
                ", odpsProjectName='" + odpsProjectName + '\'' +
                ", owner='" + owner + '\'' +
                ", prgType=" + prgType +
                ", priority=" + priority +
                ", prodModifyTime=" + prodModifyTime +
                ", rerunAble=" + rerunAble +
                ", resGroupId=" + resGroupId +
                ", source='" + source + '\'' +
                ", startEffectDate=" + startEffectDate +
                ", inputs='" + inputs + '\'' +
                ", outputs='" + outputs + '\'' +
                ", parentNodeRelations=" + parentNodeRelations +
                ", childNodeRelations=" + childNodeRelations +
                ", relatedFlowId=" + relatedFlowId +
                ", nextNodeId=" + nextNodeId +
                ", nextNodeName='" + nextNodeName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NodeEntity that = (NodeEntity) o;

        return nodeId != null ? nodeId.equals(that.nodeId) : that.nodeId == null;
    }

    @Override
    public int hashCode() {
        return nodeId != null ? nodeId.hashCode() : 0;
    }
}
