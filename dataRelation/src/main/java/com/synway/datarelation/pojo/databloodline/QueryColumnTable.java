package com.synway.datarelation.pojo.databloodline;

import java.io.Serializable;
import java.util.List;

/**
 * 字段血缘的
 */
public class QueryColumnTable implements Serializable{
    // 左侧表的展示字段名称
    private String showColumnName;
    private String targetColumnName;
    private String targetColumnChiName;
    private String nodeId;
    private DataBloodlineNode.BloodNode bloodNode;
    private List<Edges> edgeList;
    private String edgeListStr;

    // 字段的类型  数据加工类型(dataprocess)/数据处理类型(datastandard)
    private String dataType="datastandard";


    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getEdgeListStr() {
        return edgeListStr;
    }

    public void setEdgeListStr(String edgeListStr) {
        this.edgeListStr = edgeListStr;
    }

    public QueryColumnTable(){

    }
    public static class Edges implements Serializable{
        // 节点之间连线的关系 是id对应的连接关系
        private String source="";
        private String target="";
        public   Edges(String source ,String target ){
            this.source = source;
            this.target = target;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getTarget() {
            return target;
        }

        public void setTarget(String target) {
            this.target = target;
        }
    }

    public List<Edges> getEdgeList() {
        return edgeList;
    }

    public void setEdgeList(List<Edges> edgeList) {
        this.edgeList = edgeList;
    }

    public DataBloodlineNode.BloodNode getBloodNode() {
        return bloodNode;
    }

    public void setBloodNode(DataBloodlineNode.BloodNode bloodNode) {
        this.bloodNode = bloodNode;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getShowColumnName() {
        return showColumnName;
    }

    public void setShowColumnName(String showColumnName) {
        this.showColumnName = showColumnName;
    }

    public String getTargetColumnName() {
        return targetColumnName;
    }

    public void setTargetColumnName(String targetColumnName) {
        this.targetColumnName = targetColumnName;
    }

    public String getTargetColumnChiName() {
        return targetColumnChiName;
    }

    public void setTargetColumnChiName(String targetColumnChiName) {
        this.targetColumnChiName = targetColumnChiName;
    }
}
