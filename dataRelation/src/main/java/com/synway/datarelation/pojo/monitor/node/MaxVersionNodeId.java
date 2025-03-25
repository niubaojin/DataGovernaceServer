package com.synway.datarelation.pojo.monitor.node;

public class MaxVersionNodeId {

    private long nodeId;
    private int maxVersion;

    public long getNodeId() {
        return nodeId;
    }

    public void setNodeId(long nodeId) {
        this.nodeId = nodeId;
    }

    public int getMaxVersion() {
        return maxVersion;
    }

    public void setMaxVersion(int maxVersion) {
        this.maxVersion = maxVersion;
    }

    @Override
    public String toString() {
        return "MaxVersionNodeId{" +
                "nodeId=" + nodeId +
                ", maxVersion=" + maxVersion +
                '}';
    }
}
