package com.synway.governace.pojo;
/**
 * 首页
 * @author admin
 *
 */
public class HomePage {
	private String className;//大类
	private double dataCount;//数据总量
	private long dataCountHomePage;//数据总量
	private long tableCountInOneLevel;//表总量
	private long spaceUsageInOneLevel;//占用空间
	private double incremental;;//日增量
	private String statisticTimeInOneLevel;//统计时间
	private String inceptTimeInOneLevel;//入库时间
	
	public long getDataCountHomePage() {
		return dataCountHomePage;
	}
	public void setDataCountHomePage(long dataCountHomePage) {
		this.dataCountHomePage = dataCountHomePage;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public double getDataCount() {
		return dataCount;
	}
	public void setDataCount(double dataCount) {
		this.dataCount = dataCount;
	}
	public long getTableCountInOneLevel() {
		return tableCountInOneLevel;
	}
	public void setTableCountInOneLevel(long tableCountInOneLevel) {
		this.tableCountInOneLevel = tableCountInOneLevel;
	}
	public long getSpaceUsageInOneLevel() {
		return spaceUsageInOneLevel;
	}
	public void setSpaceUsageInOneLevel(long spaceUsageInOneLevel) {
		this.spaceUsageInOneLevel = spaceUsageInOneLevel;
	}
	public double getIncremental() {
		return incremental;
	}
	public void setIncremental(double incremental) {
		this.incremental = incremental;
	}
	public String getStatisticTimeInOneLevel() {
		return statisticTimeInOneLevel;
	}
	public void setStatisticTimeInOneLevel(String statisticTimeInOneLevel) {
		this.statisticTimeInOneLevel = statisticTimeInOneLevel;
	}
	public String getInceptTimeInOneLevel() {
		return inceptTimeInOneLevel;
	}
	public void setInceptTimeInOneLevel(String inceptTimeInOneLevel) {
		this.inceptTimeInOneLevel = inceptTimeInOneLevel;
	}
	
	
}
