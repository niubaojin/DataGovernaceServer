package com.synway.datarelation.pojo.datawork.v3;

/**
 * 存储节点的代码信息
 */
public class NodeCode {
    // 节点的id值
    private Long nodeId;
    // 节点的代码信息
    private String nodeCode="";
    private String nodeType="10";
    //版本号
    private Integer fileVersion;

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeCode() {
        return nodeCode;
    }

    public void setNodeCode(String nodeCode) {
        this.nodeCode = nodeCode;
    }

    public Integer getFileVersion() {
        return fileVersion;
    }

    public void setFileVersion(Integer fileVersion) {
        this.fileVersion = fileVersion;
    }
}
