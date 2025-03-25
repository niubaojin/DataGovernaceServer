package com.synway.datarelation.pojo.monitor.dag;


import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.deser.std.DateDeserializer;
import org.codehaus.jackson.map.ser.std.DateSerializer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author majia
 * @version 1.0
 * @date 2020/12/17 13:58
 */
public class DagEntity {
    // 流程实例ID
    private Long dagId;
    // 流程实例状态
    private Integer status;
    @JsonSerialize(using = DateSerializer.class, include = JsonSerialize.Inclusion. NON_NULL)
    @JsonDeserialize(using = DateDeserializer.class)
    // 处理日期
    private Date gmtdate;
    @JsonSerialize(using = DateSerializer.class, include = JsonSerialize.Inclusion. NON_NULL)
    @JsonDeserialize(using = DateDeserializer.class)
    // DAG实例的启动日期，用于查询
    private Date startDate;
    @JsonSerialize(using = DateSerializer.class, include = JsonSerialize.Inclusion. NON_NULL)
    @JsonDeserialize(using = DateDeserializer.class)
    // 启动时间
    private Date startTime;
    @JsonSerialize(using = DateSerializer.class, include = JsonSerialize.Inclusion. NON_NULL)
    @JsonDeserialize(using = DateDeserializer.class)
    // 结束时间
    private Date finishTime;
    // 根节点实例ID
    private String rootTaskIds;
    // 所属应用ID
    private Long appId;
    // 父流程实例ID
    private Long parentDagId;
    // 子流程实例ID
    private Long childDagId;
    @JsonSerialize(using = DateSerializer.class, include = JsonSerialize.Inclusion. NON_NULL)
    @JsonDeserialize(using = DateDeserializer.class)
    // 创建时间
    private Date createTime;
    // 创建人
    private String createUser;
    @JsonSerialize(using = DateSerializer.class, include = JsonSerialize.Inclusion. NON_NULL)
    @JsonDeserialize(using = DateDeserializer.class)
    // 最新修改时间
    private Date modifyTime;
    // 最新修改人
    private String modifyUser;
    // 1:initiated 2: creating 3:success 4: failure
    private Integer createStatus;
    private Long opSeq;
    private Integer dagNum;
    // 是否挂靠日常实例
    private Boolean isDependDaily;

    private String flowName;
    private String flowPara;
    private String ownerAccountId;
    private Long flowId;
    private String cpuConsumption;
    private String memoryConsumption;



    ////////////////////////////////分割线：以下创建临时工作流所需属 性//////////////////////////////////////////

    // 流程实例名称
    private String name;
    // 流程实例类型
    private Integer type;
    @JsonSerialize(using = DateSerializer.class, include = JsonSerialize.Inclusion. NON_NULL)
    @JsonDeserialize(using = DateDeserializer.class)
    // 业务日期 一次性任务传该值
    private Date bizdate;
    // 根节点定义ID
    private Long rootNodeId;
    // 包含的节 点定义列表
    private List<Long> includeNodeIds = new ArrayList<>();
    // 未包含 的节点定义列表
    private List<Long> excludeNodeIds = new ArrayList<>();
    // 包含的节 点定义列表
    private String includeNodeIdsStr;
    // 未包含 的节点定义列表
    private String excludeNodeIdsStr;
    @JsonSerialize(using = DateSerializer.class, include = JsonSerialize.Inclusion. NON_NULL)
    @JsonDeserialize(using = DateDeserializer.class)
    // 补数据起始业务日期
    private Date startBizDate;
    @JsonSerialize(using = DateSerializer.class, include = JsonSerialize.Inclusion. NON_NULL)
    @JsonDeserialize(using = DateDeserializer.class)
    // 补数据结束业务日期
    private Date endBizDate;
    // 小时任务的开始时间 13:04:04
    private String bizBeginTime;
    // 小时任务的结束时间 14:04:04
    private String bizEndTime;
    // 补数据的DAG是否可以同时并行运行
    private Boolean isParallel;


    public Long getDagId() {
        return dagId;
    }

    public void setDagId(Long dagId) {
        this.dagId = dagId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getGmtdate() {
        return gmtdate;
    }

    public void setGmtdate(Date gmtdate) {
        this.gmtdate = gmtdate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public String getRootTaskIds() {
        return rootTaskIds;
    }

    public void setRootTaskIds(String rootTaskIds) {
        this.rootTaskIds = rootTaskIds;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public Long getParentDagId() {
        return parentDagId;
    }

    public void setParentDagId(Long parentDagId) {
        this.parentDagId = parentDagId;
    }

    public Long getChildDagId() {
        return childDagId;
    }

    public void setChildDagId(Long childDagId) {
        this.childDagId = childDagId;
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

    public Integer getCreateStatus() {
        return createStatus;
    }

    public void setCreateStatus(Integer createStatus) {
        this.createStatus = createStatus;
    }

    public Long getOpSeq() {
        return opSeq;
    }

    public void setOpSeq(Long opSeq) {
        this.opSeq = opSeq;
    }

    public Integer getDagNum() {
        return dagNum;
    }

    public void setDagNum(Integer dagNum) {
        this.dagNum = dagNum;
    }

    public Boolean getIsDependDaily() {
        return isDependDaily;
    }

    public void setIsDependDaily(Boolean dependDaily) {
        isDependDaily = dependDaily;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getBizdate() {
        return bizdate;
    }

    public void setBizdate(Date bizdate) {
        this.bizdate = bizdate;
    }

    public Long getRootNodeId() {
        return rootNodeId;
    }

    public void setRootNodeId(Long rootNodeId) {
        this.rootNodeId = rootNodeId;
    }

    public List<Long> getIncludeNodeIds() {
        return includeNodeIds;
    }

    public void setIncludeNodeIds(List<Long> includeNodeIds) {
        this.includeNodeIds = includeNodeIds;
        if(includeNodeIds!=null&&includeNodeIds.size()>0){
            StringBuilder sb = new StringBuilder();
            includeNodeIds.forEach(
                    item-> sb.append(item).append(",")
            );
            sb.deleteCharAt(sb.length()-1);
            if(sb.length()>0){
                this.includeNodeIdsStr = sb.toString();
            }
        }
    }

    public List<Long> getExcludeNodeIds() {
        return excludeNodeIds;
    }

    public void setExcludeNodeIds(List<Long> excludeNodeIds) {
        this.excludeNodeIds = excludeNodeIds;
        if(excludeNodeIds!=null&&excludeNodeIds.size()>0){
            StringBuilder sb = new StringBuilder();
            excludeNodeIds.forEach(
                    item-> sb.append(item).append(",")
            );
            sb.deleteCharAt(sb.length()-1);
            if(sb.length()>0){
                this.excludeNodeIdsStr = sb.toString();
            }
        }
    }

    public Date getStartBizDate() {
        return startBizDate;
    }

    public void setStartBizDate(Date startBizDate) {
        this.startBizDate = startBizDate;
    }

    public Date getEndBizDate() {
        return endBizDate;
    }

    public void setEndBizDate(Date endBizDate) {
        this.endBizDate = endBizDate;
    }

    public String getBizBeginTime() {
        return bizBeginTime;
    }

    public void setBizBeginTime(String bizBeginTime) {
        this.bizBeginTime = bizBeginTime;
    }

    public String getBizEndTime() {
        return bizEndTime;
    }

    public void setBizEndTime(String bizEndTime) {
        this.bizEndTime = bizEndTime;
    }

    public Boolean getIsParallel() {
        return isParallel;
    }

    public void setIsParallel(Boolean parallel) {
        isParallel = parallel;
    }

    public String getIncludeNodeIdsStr() {
        return includeNodeIdsStr;
    }

    public void setIncludeNodeIdsStr(String includeNodeIdsStr) {
        this.includeNodeIdsStr = includeNodeIdsStr;
    }

    public String getExcludeNodeIdsStr() {
        return excludeNodeIdsStr;
    }

    public void setExcludeNodeIdsStr(String excludeNodeIdsStr) {
        this.excludeNodeIdsStr = excludeNodeIdsStr;
    }

    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public String getFlowPara() {
        return flowPara;
    }

    public void setFlowPara(String flowPara) {
        this.flowPara = flowPara;
    }

    public String getOwnerAccountId() {
        return ownerAccountId;
    }

    public void setOwnerAccountId(String ownerAccountId) {
        this.ownerAccountId = ownerAccountId;
    }

    public Long getFlowId() {
        return flowId;
    }

    public void setFlowId(Long flowId) {
        this.flowId = flowId;
    }

    public String getCpuConsumption() {
        return cpuConsumption;
    }

    public DagEntity setCpuConsumption(String cpuConsumption) {
        this.cpuConsumption = cpuConsumption;
        return this;
    }

    public String getMemoryConsumption() {
        return memoryConsumption;
    }

    public DagEntity setMemoryConsumption(String memoryConsumption) {
        this.memoryConsumption = memoryConsumption;
        return this;
    }
}
