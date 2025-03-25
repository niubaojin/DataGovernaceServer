package com.synway.datastandardmanager.pojo;

import java.util.List;

public class ChildrenNode {
	private String key;
	private String value;
	private List<ObjectPojo> childrenobjects;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public List<ObjectPojo> getChildrenobjects() {
		return childrenobjects;
	}
	public void setChildrenobjects(List<ObjectPojo> childrenobjects) {
		this.childrenobjects = childrenobjects;
	}
	
	
}
