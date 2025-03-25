package com.synway.datarelation.pojo.datawork.v3;


/**
 * 查询任务的请求参数
 * 对应阿里大数据平台(DataWorks)api版本号为 3.7
 */
public class TaskQueryParameters {
    private String executeMethod ="SEARCH";  //查询类型为SEARCH
    private Long appId ;  //任务实例所属应用ID
    private String appIds ;  //多值逗号分隔
    private Long taskId ;  //任务实例ID
    private Long nodeId ;  //任务实例对应的节点定义ID
    private Long inGroupId ;  //实例的周期号
    private String nodeIds ;  //节点id列表，逗号分隔
    private Long dagId ;  //任务实例所属的流程实例ID
    private Integer taskType ;  //任务类型：0：正常 1：一次性任务 2:表示暂停的节点实例 3：空跑
    private String taskTypes ;  //任务类型，多值逗号分隔
    private String taskStatuses ;  //任务状态，多值逗号分隔
    private Integer dagType ;  //DAG类型：1: routing 2: smoke 3: completeData 4:onceTime
    private Integer prgType ;  //
    private Integer status ;  //任务状态
    private String owner ;  //负责人，统一使用工号
    private String bizdate ;  //业务日期
    private String odpsInstanceId ;  //odpsInstanceId
    private String bizBeginHour ;  //
    private String bizEndHour ;  //
    private String beginBizDate ;  //
    private String endBizDate ;  //
    private String searchText ;  //前端搜索的字符，包括搜索节点ID和节点名字，支持后模糊
    private Long baseLineId ;  //
    private String createTime ;  //
    private Boolean isDetail ;  //
    private Long opSeq ;  //
    private Integer depth ;  //
    private Long historyId ;  //查询任务日记的参数
    private Long startId ;  //历史任务的起始ID(mosad), 历史任务使用
    private Long endId ;  //历史任务的结束ID(mosad), 历史任务使用
    private Integer costTime ;  //耗时:单位秒
    private String startRunTimeFrom ;  //分时间段查询开始运行时间
    private String  startRunTimeTo;  //
    private String finishRunTimeFrom ;  //分时间段查询完成时间
    private String finishRunTimeTo ;  //
    private String createTimeFrom ;  //
    private String createTimeTo ;  //
    private String prgTypes ;  //
    private String taskIds ;  //
    private Boolean rerunAble ;  //
    private String dagName ;  //
    private String orderBy ;  //
    private Long bizId ;  //
    private Long solId ;  //
    private String gmtdate ;  //
    private String createUser ;  //
    private String modifyUser ;  //
    private String modifyTime ;  //
    private String deployDate ;  //
    private Integer repeatable ;  //是否可重跑
    private Integer pageStart ;  //
    private Integer pageSize ;  //


    public String getExecuteMethod() {
        return executeMethod;
    }

    public void setExecuteMethod(String executeMethod) {
        this.executeMethod = executeMethod;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getAppIds() {
        return appIds;
    }

    public void setAppIds(String appIds) {
        this.appIds = appIds;
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

    public Long getInGroupId() {
        return inGroupId;
    }

    public void setInGroupId(Long inGroupId) {
        this.inGroupId = inGroupId;
    }

    public String getNodeIds() {
        return nodeIds;
    }

    public void setNodeIds(String nodeIds) {
        this.nodeIds = nodeIds;
    }

    public Long getDagId() {
        return dagId;
    }

    public void setDagId(Long dagId) {
        this.dagId = dagId;
    }

    public Integer getTaskType() {
        return taskType;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    public String getTaskTypes() {
        return taskTypes;
    }

    public void setTaskTypes(String taskTypes) {
        this.taskTypes = taskTypes;
    }

    public String getTaskStatuses() {
        return taskStatuses;
    }

    public void setTaskStatuses(String taskStatuses) {
        this.taskStatuses = taskStatuses;
    }

    public Integer getDagType() {
        return dagType;
    }

    public void setDagType(Integer dagType) {
        this.dagType = dagType;
    }

    public Integer getPrgType() {
        return prgType;
    }

    public void setPrgType(Integer prgType) {
        this.prgType = prgType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getOdpsInstanceId() {
        return odpsInstanceId;
    }

    public void setOdpsInstanceId(String odpsInstanceId) {
        this.odpsInstanceId = odpsInstanceId;
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

    public String getBeginBizDate() {
        return beginBizDate;
    }

    public void setBeginBizDate(String beginBizDate) {
        this.beginBizDate = beginBizDate;
    }

    public String getEndBizDate() {
        return endBizDate;
    }

    public void setEndBizDate(String endBizDate) {
        this.endBizDate = endBizDate;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public Long getBaseLineId() {
        return baseLineId;
    }

    public void setBaseLineId(Long baseLineId) {
        this.baseLineId = baseLineId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Boolean getDetail() {
        return isDetail;
    }

    public void setDetail(Boolean detail) {
        isDetail = detail;
    }

    public Long getOpSeq() {
        return opSeq;
    }

    public void setOpSeq(Long opSeq) {
        this.opSeq = opSeq;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public Long getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Long historyId) {
        this.historyId = historyId;
    }

    public Long getStartId() {
        return startId;
    }

    public void setStartId(Long startId) {
        this.startId = startId;
    }

    public Long getEndId() {
        return endId;
    }

    public void setEndId(Long endId) {
        this.endId = endId;
    }

    public Integer getCostTime() {
        return costTime;
    }

    public void setCostTime(Integer costTime) {
        this.costTime = costTime;
    }

    public String getStartRunTimeFrom() {
        return startRunTimeFrom;
    }

    public void setStartRunTimeFrom(String startRunTimeFrom) {
        this.startRunTimeFrom = startRunTimeFrom;
    }

    public String getStartRunTimeTo() {
        return startRunTimeTo;
    }

    public void setStartRunTimeTo(String startRunTimeTo) {
        this.startRunTimeTo = startRunTimeTo;
    }

    public String getFinishRunTimeFrom() {
        return finishRunTimeFrom;
    }

    public void setFinishRunTimeFrom(String finishRunTimeFrom) {
        this.finishRunTimeFrom = finishRunTimeFrom;
    }

    public String getFinishRunTimeTo() {
        return finishRunTimeTo;
    }

    public void setFinishRunTimeTo(String finishRunTimeTo) {
        this.finishRunTimeTo = finishRunTimeTo;
    }

    public String getCreateTimeFrom() {
        return createTimeFrom;
    }

    public void setCreateTimeFrom(String createTimeFrom) {
        this.createTimeFrom = createTimeFrom;
    }

    public String getCreateTimeTo() {
        return createTimeTo;
    }

    public void setCreateTimeTo(String createTimeTo) {
        this.createTimeTo = createTimeTo;
    }

    public String getPrgTypes() {
        return prgTypes;
    }

    public void setPrgTypes(String prgTypes) {
        this.prgTypes = prgTypes;
    }

    public String getTaskIds() {
        return taskIds;
    }

    public void setTaskIds(String taskIds) {
        this.taskIds = taskIds;
    }

    public Boolean getRerunAble() {
        return rerunAble;
    }

    public void setRerunAble(Boolean rerunAble) {
        this.rerunAble = rerunAble;
    }

    public String getDagName() {
        return dagName;
    }

    public void setDagName(String dagName) {
        this.dagName = dagName;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public Long getBizId() {
        return bizId;
    }

    public void setBizId(Long bizId) {
        this.bizId = bizId;
    }

    public Long getSolId() {
        return solId;
    }

    public void setSolId(Long solId) {
        this.solId = solId;
    }

    public String getGmtdate() {
        return gmtdate;
    }

    public void setGmtdate(String gmtdate) {
        this.gmtdate = gmtdate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getDeployDate() {
        return deployDate;
    }

    public void setDeployDate(String deployDate) {
        this.deployDate = deployDate;
    }

    public Integer getRepeatable() {
        return repeatable;
    }

    public void setRepeatable(Integer repeatable) {
        this.repeatable = repeatable;
    }

    public Integer getPageStart() {
        return pageStart;
    }

    public void setPageStart(Integer pageStart) {
        this.pageStart = pageStart;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
