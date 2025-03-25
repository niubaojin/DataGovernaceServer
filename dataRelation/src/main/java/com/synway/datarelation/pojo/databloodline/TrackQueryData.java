package com.synway.datarelation.pojo.databloodline;

import java.io.Serializable;

/**
 * 轨迹查询需要的
 * @author wdw
 * @date 2021/4/8 16:27
 */
public class TrackQueryData implements Serializable {
    /**
     * 节点id
     */
    private String nodeId;

    /**
     * 节点名称
     */
    private String nodeName;

    public TrackQueryData(){

    }

    public TrackQueryData(String nodeId,String nodeName){
        this.nodeId = nodeId;
        this.nodeName = nodeName;
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
}
