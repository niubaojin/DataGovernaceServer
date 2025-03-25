package com.synway.datarelation.pojo.dfwork;

/**
 *  节点信息的返回数据
 */
public class NodeInfoReturnValue {
    // 工作流名称
    private String flowName;
    //  工作流ID
    private String nodeId;
    // 节点名称
    private String nodeName;
    //  节点类型
    private String nodeType;
    //  项目名
    private String projectName;
    //  sql代码
//    当节点类型为sjtb时：
//            "sqlCode":{"sourceConType":"hiveserver2","sourceConnect":"odps_first.syntag_springfestival_in","targetConType":"postgres","targetConnect":"ads_hc_db.syntag_springfestival_in"}
//    sourceConType： 源数据库类型；
//    sourceConnect：源数据库表信息，XXX.aaa ，XXX表示 数据库名称，aaa表示 表名称,
//    当数据库查询不在的时候，即aaa表示 表名称；
//    targetConType：目标数据库类型；
//    targetConnect：目标数据库表信息，XXX.aaa ，XXX表示 数据库名称，aaa表示 表名称,
    private String sqlCode;


    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getSqlCode() {
        return sqlCode;
    }

    public void setSqlCode(String sqlCode) {
        this.sqlCode = sqlCode;
    }
}
