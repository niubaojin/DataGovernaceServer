package com.synway.property.pojo;

import java.util.List;

/**
 * @author 数据接入
 */
public class TreeNode {
	private String label;
	private String id;
	private int showIcon;
	private List<TreeNode> children;
	private String parent;
	private int level;
	private String grandpar;
	private int sortLevel;

	public int getSortLevel() { return sortLevel; }

	public void setSortLevel(int sortLevel) { this.sortLevel = sortLevel; }

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getShowIcon() {
		return showIcon;
	}

	public void setShowIcon(int showIcon) {
		this.showIcon = showIcon;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getGrandpar() {
		return grandpar;
	}

	public void setGrandpar(String grandpar) {
		this.grandpar = grandpar;
	}
}
