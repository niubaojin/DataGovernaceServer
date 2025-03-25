package com.synway.datarelation.pojo.modelmonitor;

/**
 * @author
 * @date 2019/4/24 11:19
 */
public class NodeInsQueryParam {

    private String projectName;
    private String flowName;
    private String searchText; //  节点名称或 displayname
    private String instanceIds; //  多个实例 ID,以逗号分隔
    private Integer dagType; // DAG 类型
    private String statuses;//多状态 多个状态,以逗号分隔
    private String instanceTypes;//任务类型：0：  正常  1：一次性任务  2:表示暂停的节点实例  3：空跑
    private Integer nodeType;//节点类型
    private String owner; //  负责人，统一使用工号
    private String bizdate; //  业务日期,格式为 yyyy-MM-dd HH:mi:ss
    private String bizBeginHour;//格式为 yyyy-MM-dd HH:mi:ss
    private String bizEndHour;//格式为 yyyy-MM-dd HH:mi:ss
    private String createTime;//格式为 yyyy-MM-dd HH:mi:ss（查找出设置的createTime当天的数据，并降序排序，如果没有这个参数是查所有）
    private Long dagId;  //dag 实例  ID
    private int start;//分页条件
    private int limit;//分页条件

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public Integer getNodeType() {
        return nodeType;
    }

    public void setNodeType(Integer nodeType) {
        this.nodeType = nodeType;
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

    public Long getDagId() {
        return dagId;
    }

    public void setDagId(Long dagId) {
        this.dagId = dagId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public String getInstanceIds() {
        return instanceIds;
    }

    public void setInstanceIds(String instanceIds) {
        this.instanceIds = instanceIds;
    }

    public Integer getDagType() {
        return dagType;
    }

    public void setDagType(Integer dagType) {
        this.dagType = dagType;
    }

    public String getStatuses() {
        return statuses;
    }

    public void setStatuses(String statuses) {
        this.statuses = statuses;
    }

    public String getInstanceTypes() {
        return instanceTypes;
    }

    public void setInstanceTypes(String instanceTypes) {
        this.instanceTypes = instanceTypes;
    }
}
