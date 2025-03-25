package com.synway.property.pojo;

/**
 * @author majia
 */
public class DataStoreAndUseHeat {
	private String id;
	private String lastDate;//日期，默认显示昨日的
	private String tableType;//1 odps,2 hc,3 hp
	private String className;//大类
	private String subclass;//小类
	private String tableNameEN;//表英文名
	private String tableNameZH;//表中文名
	private int lifeCycle;//表生命周期
	private long tableAllCount;//总数据量(万条)
	private long tableSize;//总存储大小
	private long tableUseCount;//表使用总次数
	private long tableUseCountOfDay;//表日使用次数
	private long tableUseCountOfWeek;//表周使用次数
	private long tableUseCountOfMonth;//表月使用次数
	private String tableProject;//项目名称
	private String tableCreatedTime;//表创建时间
	private String tableLastMetaModifiedTime;//表最后DDL时间TABLELASTMETAMODIFIEDTIME
	private String tableLastDataModifiedTime;//表数据最后修改时间
	private String partitiondate;//表分区日期
	private String partitionCount;//表分区数据行数

	public String getTableProject() {
		return tableProject;
	}

	public void setTableProject(String tableProject) {
		this.tableProject = tableProject;
	}

	public String getTableCreatedTime() {
		return tableCreatedTime;
	}

	public void setTableCreatedTime(String tableCreatedTime) {
		this.tableCreatedTime = tableCreatedTime;
	}

	public String getTableLastMetaModifiedTime() {
		return tableLastMetaModifiedTime;
	}

	public void setTableLastMetaModifiedTime(String tableLastMetaModifiedTime) {
		this.tableLastMetaModifiedTime = tableLastMetaModifiedTime;
	}

	public String getTableLastDataModifiedTime() {
		return tableLastDataModifiedTime;
	}

	public void setTableLastDataModifiedTime(String tableLastDataModifiedTime) {
		this.tableLastDataModifiedTime = tableLastDataModifiedTime;
	}

	public String getPartitiondate() {
		return partitiondate;
	}

	public void setPartitiondate(String partitiondate) {
		this.partitiondate = partitiondate;
	}

	public String getPartitionCount() {
		return partitionCount;
	}

	public void setPartitionCount(String partitionCount) {
		this.partitionCount = partitionCount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id=id;
	}

	public String getLastDate() {
		return lastDate;
	}

	public void setLastDate(String lastDate) {
		if(lastDate!=null && !lastDate.isEmpty() && lastDate.length()==8){
			this.lastDate=String.format("%s-%s-%s", lastDate.substring(0, 4),lastDate.substring(4,6),lastDate.substring(6, 8));
			return;
		}
		this.lastDate = lastDate;
	}

	public String getTableType() {
		return tableType;
	}
	public void setTableType(String tableType) {//设置"1"为ODPS，2,3为ADS
		this.tableType = tableType;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getSubclass() {
		return subclass;
	}
	public void setSubclass(String subclass) {
		this.subclass = subclass;
	}
	public String getTableNameEN() {
		return tableNameEN;
	}
	public void setTableNameEN(String tableNameEN) {
		this.tableNameEN = tableNameEN;
	}
	public String getTableNameZH() {
		return tableNameZH;
	}
	public void setTableNameZH(String tableNameZH) {
		this.tableNameZH = tableNameZH;
	}
	public int getLifeCycle() {
		return lifeCycle;
	}
	public void setLifeCycle(int lifeCycle) {
		this.lifeCycle = lifeCycle;
	}


	public long getTableAllCount() {
		return tableAllCount;
	}

	public void setTableAllCount(long tableAllCount) {
		this.tableAllCount = tableAllCount;
	}

	public long getTableSize() {
		return tableSize;
	}

	public void setTableSize(long tableSize) {
		this.tableSize = tableSize;
	}

	public long getTableUseCount() {
		return tableUseCount;
	}

	public void setTableUseCount(long tableUseCount) {
		this.tableUseCount = tableUseCount;
	}

	public long getTableUseCountOfDay() {
		return tableUseCountOfDay;
	}

	public void setTableUseCountOfDay(long tableUseCountOfDay) {
		this.tableUseCountOfDay = tableUseCountOfDay;
	}

	public long getTableUseCountOfWeek() {
		return tableUseCountOfWeek;
	}

	public void setTableUseCountOfWeek(long tableUseCountOfWeek) {
		this.tableUseCountOfWeek = tableUseCountOfWeek;
	}

	public long getTableUseCountOfMonth() {
		return tableUseCountOfMonth;
	}

	public void setTableUseCountOfMonth(long tableUseCountOfMonth) {
		this.tableUseCountOfMonth = tableUseCountOfMonth;
	}

	public void setTableSize(Integer tableSize) {
		this.tableSize = tableSize;
	}

	@Override
	public String toString() {
		return "DataStoreAndUseHeat [lastDate=" + lastDate + ", tableType="
				+ tableType + ", className=" + className + ", subclass="
				+ subclass + ", tableNameEN=" + tableNameEN + ", tableNameZH="
				+ tableNameZH + ", lifeCycle=" + lifeCycle + ", tableAllCount="
				+ tableAllCount + ", tableSize=" + tableSize
				+ ", tableUseCount=" + tableUseCount + ", tableUseCountOfDay="
				+ tableUseCountOfDay + ", tableUseCountOfWeek="
				+ tableUseCountOfWeek + ", tableUseCountOfMonth="
				+ tableUseCountOfMonth + "]";
	}
}
