package com.synway.datarelation.pojo.dfwork;


/**
 *    数据血缘节点信息查询接口
 */
public class NodeInfoParam {

    // 节点主键
    private String nodeId;

    // 节点类型(仅支持sql(10)、sjtb(23)) ，以nodeId为主，nodeId为空时，nodeType方可生效；
    private String nodeType;

    //  节点版本号，例：1
    private String version;

    // 分页条件起始页，默认1
    private int start;

    // 分页条件每页条数，默认10
    private int limit;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

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
}
