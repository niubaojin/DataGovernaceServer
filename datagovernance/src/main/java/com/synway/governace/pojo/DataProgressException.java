package com.synway.governace.pojo;

import java.io.Serializable;

public class DataProgressException implements Serializable {
     private String className;
     private String subClass;
     private String tableId;
     private String logicJudge;
     private String thresHold;
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getSubClass() {
		return subClass;
	}
	public void setSubClass(String subClass) {
		this.subClass = subClass;
	}
	public String getTableId() {
		return tableId;
	}
	public void setTableId(String tableId) {
		this.tableId = tableId;
	}
	public String getLogicJudge() {
		return logicJudge;
	}
	public void setLogicJudge(String logicJudge) {
		this.logicJudge = logicJudge;
	}
	public String getThresHold() {
		return thresHold;
	}
	public void setThresHold(String thresHold) {
		this.thresHold = thresHold;
	}
     
}
