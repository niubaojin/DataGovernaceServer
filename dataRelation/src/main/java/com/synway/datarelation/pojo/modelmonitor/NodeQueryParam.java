package com.synway.datarelation.pojo.modelmonitor;

/**
 * 查询节点的参数对象
 * @author
 * @date 2019/4/24 11:17
 */
public class NodeQueryParam {

    private String runTypes; //  节点运行类型,多个以逗号分隔
    private String nodeTypes; //  节点类型,多个以逗号分隔
    private String projectName;//精确匹配
    private String flowName; //精确匹配
    private String owner; //  节点负责人
    private String modifyTime;//节点发布日期,格式 yyyy-mm-dd
    private String searchText;//节点名称,模糊查询,nodename,displayname
    private Boolean includeRelation;//返回的结果中,是否包含依赖关系（true:如果有层级则返回层级数据，false：如果有层级，不返回层级数据）
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

    public String getRunTypes() {
        return runTypes;
    }

    public void setRunTypes(String runTypes) {
        this.runTypes = runTypes;
    }

    public String getNodeTypes() {
        return nodeTypes;
    }

    public void setNodeTypes(String nodeTypes) {
        this.nodeTypes = nodeTypes;
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public Boolean getIncludeRelation() {
        return includeRelation;
    }

    public void setIncludeRelation(Boolean includeRelation) {
        this.includeRelation = includeRelation;
    }
}
