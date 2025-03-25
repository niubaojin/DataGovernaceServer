package com.synway.datarelation.pojo.datawork.v3;

/**
 * 查询节点的请求参数
 * 对应阿里大数据平台(DataWorks)api版本号为 3.7
 */
public class NodeQueryParameters {

    private String executeMethod ="SEARCH";  //查询类型为SEARCH
    private String nodeName;                //节点名，可选
    private Long cloudUUID;                 // 代码库时相关的节点的ID，可选
    private Long nodeId;                    // 节点的ID，可选
    private Long version;                   // 节点代码的版本号，可选
    private String inputs;                  // 节点的输入，以逗号分隔，可选
    private String outputs;//节点的输出，以逗号分隔，可选
    private Long fileId;//文件ID，可选
    private String nodeIds;//节点ID列表，以逗号分隔，可选
    private String searchText;  //前端搜索的字符，包括搜索节点ID和节点名字，支持后模糊，可选
    private Integer prgType;  //程序类型，可选
    private String prgTypes; // 查询是多个类型时，以逗号隔开，可选
    private Long appId;//应用ID 如果为空，在我的所有应用范围内搜索，可选
    private String owner;//节点负责人，可选
    private String createUser;//节点创建人，可选
    private String modifyUser;//节点修改人，可选
    private String createTime;//创建时间yyyy-MM-dd HH:mm:ss，可选
    private String modifyTime;//修改时间 yyyy-MM-dd HH:mm:ss，可选
    private Long baseLineId;//基线ID，可选
    private String deployDate;//发布时间，可选
    private String orderBy;
    private Boolean isDetail;//是否查询节点的详细信息，可选
    private Integer depth;//遍历的层数，可选
    private Long flowId;//工作流ID
    private Integer nodeType;//0，正常调度任务被日常调度 1，手动任务不会被日常调度 2，暂停任务被日常调度，但启动调度时直接被置为失败 3，空跑任务被日常调度，但启动调度时直接被置为成功
    private Long tenantId;//租户ID，可选
    private String bizDate;//业务业务时间格式[yyyy-MM-dd]，可选
    private String prgName;//程序类型名称，可选
    private Integer isOnline;//是否在线 1：在线 0：下线，可选
    private String modifyStartTime;//修改起始时间，可选
    private String modifyEndTime;//修改截止时间，可选
    private String deployStartingDate;//发布起始时间，在指定一段时间内查询节点，可选
    private String deployDeadlineDate;//发布截止时间，在指定一段时间内查询节点，可选
    private String nodeForm;//节点的来源
    private Boolean rerunAble;//设置是否可重跑，可选
    private Long bizId;//业务流程ID，可选
    private Long solId;//解决方案ID，可选
    private String filePaths;//文件路径，可选
    private Boolean isSmoke;//
    private Long resGroupId;//资源组ID，可选
    private Boolean isDeploy;
    private Integer deployType;
    private Integer pageStart;
    private Integer pageSize;

    public String getExecuteMethod() {
        return executeMethod;
    }

    public void setExecuteMethod(String executeMethod) {
        this.executeMethod = executeMethod;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public Long getCloudUUID() {
        return cloudUUID;
    }

    public void setCloudUUID(Long cloudUUID) {
        this.cloudUUID = cloudUUID;
    }

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
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

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getNodeIds() {
        return nodeIds;
    }

    public void setNodeIds(String nodeIds) {
        this.nodeIds = nodeIds;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public Integer getPrgType() {
        return prgType;
    }

    public void setPrgType(Integer prgType) {
        this.prgType = prgType;
    }

    public String getPrgTypes() {
        return prgTypes;
    }

    public void setPrgTypes(String prgTypes) {
        this.prgTypes = prgTypes;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Long getBaseLineId() {
        return baseLineId;
    }

    public void setBaseLineId(Long baseLineId) {
        this.baseLineId = baseLineId;
    }

    public String getDeployDate() {
        return deployDate;
    }

    public void setDeployDate(String deployDate) {
        this.deployDate = deployDate;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public Boolean getDetail() {
        return isDetail;
    }

    public void setDetail(Boolean detail) {
        isDetail = detail;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public Long getFlowId() {
        return flowId;
    }

    public void setFlowId(Long flowId) {
        this.flowId = flowId;
    }

    public Integer getNodeType() {
        return nodeType;
    }

    public void setNodeType(Integer nodeType) {
        this.nodeType = nodeType;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getBizDate() {
        return bizDate;
    }

    public void setBizDate(String bizDate) {
        this.bizDate = bizDate;
    }

    public String getPrgName() {
        return prgName;
    }

    public void setPrgName(String prgName) {
        this.prgName = prgName;
    }

    public Integer getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Integer isOnline) {
        this.isOnline = isOnline;
    }

    public String getModifyStartTime() {
        return modifyStartTime;
    }

    public void setModifyStartTime(String modifyStartTime) {
        this.modifyStartTime = modifyStartTime;
    }

    public String getModifyEndTime() {
        return modifyEndTime;
    }

    public void setModifyEndTime(String modifyEndTime) {
        this.modifyEndTime = modifyEndTime;
    }

    public String getDeployStartingDate() {
        return deployStartingDate;
    }

    public void setDeployStartingDate(String deployStartingDate) {
        this.deployStartingDate = deployStartingDate;
    }

    public String getDeployDeadlineDate() {
        return deployDeadlineDate;
    }

    public void setDeployDeadlineDate(String deployDeadlineDate) {
        this.deployDeadlineDate = deployDeadlineDate;
    }

    public String getNodeForm() {
        return nodeForm;
    }

    public void setNodeForm(String nodeForm) {
        this.nodeForm = nodeForm;
    }

    public Boolean getRerunAble() {
        return rerunAble;
    }

    public void setRerunAble(Boolean rerunAble) {
        this.rerunAble = rerunAble;
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

    public String getFilePaths() {
        return filePaths;
    }

    public void setFilePaths(String filePaths) {
        this.filePaths = filePaths;
    }

    public Boolean getSmoke() {
        return isSmoke;
    }

    public void setSmoke(Boolean smoke) {
        isSmoke = smoke;
    }

    public Long getResGroupId() {
        return resGroupId;
    }

    public void setResGroupId(Long resGroupId) {
        this.resGroupId = resGroupId;
    }

    public Boolean getDeploy() {
        return isDeploy;
    }

    public void setDeploy(Boolean deploy) {
        isDeploy = deploy;
    }

    public Integer getDeployType() {
        return deployType;
    }

    public void setDeployType(Integer deployType) {
        this.deployType = deployType;
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
