package com.synway.property.pojo;

/**
 * @author 数据接入
 */
public class DBOperatorMonitor {
	
	private String id;  //主键  
	private String dbType; //数据库类型    
	private String projectName;//项目名  
	private int    sqlTyle; //sql类型          0:未解析出来  1:select查询   2:insert插入   3:count计数   4:delete删出   5:update修改   6:create建表   7:drop删表 
	private String executiveState;//执行状态
	private String sql;  //sql语句
	private String startTime;  //开始时间
	private String endTime;  // 结束时间
	private String excuteTime;  //执行时长
	private String ownerName; // 所属账户名
	private String monitorTime; //监控日期  
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDbType() {
		return dbType;
	}
	public void setDbType(String dbType) {
		this.dbType = dbType;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public int getSqlTyle() {
		return sqlTyle;
	}
	public void setSqlTyle(int sqlTyle) {
		this.sqlTyle = sqlTyle;
	}
	public String getExecutiveState() {
		return executiveState;
	}
	public void setExecutiveState(String executiveState) {
		this.executiveState = executiveState;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getExcuteTime() {
		return excuteTime;
	}
	public void setExcuteTime(String excuteTime) {
		this.excuteTime = excuteTime;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getMonitorTime() {
		return monitorTime;
	}
	public void setMonitorTime(String monitorTime) {
		this.monitorTime = monitorTime;
	}
	
	
}
