package com.synway.datarelation.pojo.common;


import com.synway.datarelation.pojo.databloodline.QueryDataBloodlineTable;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangdongwei
 */
public class TreeNode implements Serializable {

	private String label;
	private long nodeId;
	private List<TreeNode> children;
	private String errorMessage="";
	private String type="node";  // module  node  如果是 module则表示是应用血缘的模块信息，点击之后按照这个查询
	private String value;  // 模块信息对应的值
	private String applicationName="";
	private String subModuleName="";
	private int num;  // 当是应用血缘时，节点对应模块分类，0表示是大模块
	// 存储具体的节点信息，点击之后用这个参数查询
	private QueryDataBloodlineTable queryDataBloodlineTable;

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getSubModuleName() {
		return subModuleName;
	}

	public void setSubModuleName(String subModuleName) {
		this.subModuleName = subModuleName;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public QueryDataBloodlineTable getQueryDataBloodlineTable() {
		return queryDataBloodlineTable;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void setQueryDataBloodlineTable(QueryDataBloodlineTable queryDataBloodlineTable) {
		this.queryDataBloodlineTable = queryDataBloodlineTable;
	}

	public long getNodeId() {
		return nodeId;
	}
	public void setNodeId(long nodeId) {
		this.nodeId = nodeId;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}
}
