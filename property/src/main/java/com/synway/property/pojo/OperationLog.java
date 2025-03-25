package com.synway.property.pojo;

import java.util.Date;

/**
 * @author 数据接入
 */
public class OperationLog {

	private String id;
	private String logName;
	private Date   logTime;
	private long dataCount;
	
	public long getDataCount() {
		return dataCount;
	}
	public void setDataCount(long dataCount) {
		this.dataCount = dataCount;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLogName() {
		return logName;
	}
	public void setLogName(String logName) {
		this.logName = logName;
	}
	public Date getLogTime() {
		return logTime;
	}
	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}
	@Override
	public String toString() {
		return "OperationLog [id=" + id + ", logName=" + logName + ", logTime="
				+ logTime + ", dataCount=" + dataCount + ", getDataCount()="
				+ getDataCount() + ", getId()=" + getId() + ", getLogName()="
				+ getLogName() + ", getLogTime()=" + getLogTime()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	
	
}
