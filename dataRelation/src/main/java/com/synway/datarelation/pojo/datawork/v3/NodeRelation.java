package com.synway.datarelation.pojo.datawork.v3;

public class NodeRelation {
    private Long parentNodeId;
    private Long childNodeId;
    private Long childFlowId;

    public Long getParentNodeId() {
        return parentNodeId;
    }

    public void setParentNodeId(Long parentNodeId) {
        this.parentNodeId = parentNodeId;
    }

    public Long getChildNodeId() {
        return childNodeId;
    }

    public void setChildNodeId(Long childNodeId) {
        this.childNodeId = childNodeId;
    }

    public Long getChildFlowId() {
        return childFlowId;
    }

    public void setChildFlowId(Long childFlowId) {
        this.childFlowId = childFlowId;
    }
}
