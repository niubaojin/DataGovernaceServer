package com.synway.datarelation.pojo.databloodline;


import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

public class RelationshipNode implements Serializable {
	private RelationshipNode preNode;//parentTableName
	private List<RelationshipNode> nextNode;//childrenTableName
	@NotNull
	private String dataType;   // 节点类型
    private String tableId;        // 目标 tableId  数据处理展示  数据加工展示
    private String tableNameEn = "";   //  表项目名.表英文名  数据加工展示  本节点的展示数据
    private String parentTN = "";
    private String childrenTN = "";
    private String parentTableId= "";
    private String childrenTableId = "";
    private String parentTableNameCh = "";
    private String childrenTableNameCh = "";
    // 工作流中节点名称
    private String nodeName = "";
    private String flowName = "";

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public String getParentTableId() {
        return parentTableId;
    }

    public void setParentTableId(String parentTableId) {
        this.parentTableId = parentTableId;
    }

    public String getChildrenTableId() {
        return childrenTableId;
    }

    public void setChildrenTableId(String childrenTableId) {
        this.childrenTableId = childrenTableId;
    }

    public String getParentTableNameCh() {
        return parentTableNameCh;
    }

    public void setParentTableNameCh(String parentTableNameCh) {
        this.parentTableNameCh = parentTableNameCh;
    }

    public String getChildrenTableNameCh() {
        return childrenTableNameCh;
    }

    public void setChildrenTableNameCh(String childrenTableNameCh) {
        this.childrenTableNameCh = childrenTableNameCh;
    }

    public String getParentTN() {
        return parentTN;
    }

    public void setParentTN(String parentTN) {
        this.parentTN = parentTN;
    }

    public String getChildrenTN() {
        return childrenTN;
    }

    public void setChildrenTN(String childrenTN) {
        this.childrenTN = childrenTN;
    }

    public RelationshipNode(){
		
	}
	public RelationshipNode(RelationshipNode preNode, String tableNameEn,
                            List<RelationshipNode> nextNode , String dataType,
                            String tableId, String parentTN,
                            String childrenTN){
		this.preNode=preNode;
        this.tableNameEn = tableNameEn;
		this.nextNode=nextNode;
		this.dataType = dataType;
        this.tableId = tableId;
        this.parentTN = parentTN;
        this.childrenTN = childrenTN;
	}

    public RelationshipNode getPreNode() {
        return preNode;
    }

    public void setPreNode(RelationshipNode preNode) {
        this.preNode = preNode;
    }

    public List<RelationshipNode> getNextNode() {
        return nextNode;
    }

    public void setNextNode(List<RelationshipNode> nextNode) {
        this.nextNode = nextNode;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getTableNameEn() {
        return tableNameEn;
    }

    public void setTableNameEn(String tableNameEn) {
        this.tableNameEn = tableNameEn;
    }
}
