package com.synway.datastandardmanager.pojo;

import java.util.List;

public class TreeNode {
	private String text;
	private String icon;
	private long nodeId;
	private List<TreeNode> nodes;
	private String color;
	private List<Object> tabs;
	

	public List<Object> getTabs() {
		return tabs;
	}
	public void setTabs(List<Object> tabs) {
		this.tabs = tabs;
	}
	public long getNodeId() {
		return nodeId;
	}
	public void setNodeId(long nodeId) {
		this.nodeId = nodeId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public List<TreeNode> getNodes() {
		return nodes;
	}
	public void setNodes(List<TreeNode> nodes) {
		this.nodes = nodes;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
	
}
