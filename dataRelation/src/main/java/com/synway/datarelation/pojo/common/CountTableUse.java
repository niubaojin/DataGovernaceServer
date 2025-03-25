package com.synway.datarelation.pojo.common;

import java.util.UUID;


/**
 * 数据使用次数的相关实体类
 * @author 数据接入
 */
public class CountTableUse {
    /**
     * 外键，与DBOperatorMonitor主键对应
     *  20210424 这个id值已经没有用处
     */
	private String id;
    /**
     * 表名
     */
	private String tableName;
    /**
     * 使用次数
     */
	private int useCount;
    /**
     * SQL类型：1,"SELECT"；2,"INSERT"；3,"COUNT"
     */
	private int SQLType;
    /**
     * 解析时间：yyyy-MM-dd
     */
	private String DT;
    /**
     * 项目名
     */
	private String projectName;
	
	public CountTableUse(){}
	
	public CountTableUse(String id, String tableName, String projectName, String dt, int sqlType,int useCount){
		this.id=id;
		this.tableName=tableName;
		this.projectName=projectName;
		this.DT=dt;
		this.setSQLType(sqlType);
		this.useCount = useCount;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public int getUseCount() {
		return useCount;
	}
	public void setUseCount(int useCount) {
		this.useCount = useCount;
	}
	public void incrementsCount(int useCount){
		this.useCount++;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public static void main(String[] args) {
		System.out.println(UUID.randomUUID().toString().length());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((projectName == null) ? 0 : projectName.hashCode());
		result = prime * result
				+ ((tableName == null) ? 0 : tableName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		CountTableUse other = (CountTableUse) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (projectName == null) {
			if (other.projectName != null) {
				return false;
			}
		} else if (!projectName.equals(other.projectName)) {
			return false;
		}
		if (tableName == null) {
			if (other.tableName != null) {
				return false;
			}
		} else if (!tableName.equals(other.tableName)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "CountTableUse [id=" + id + ", tableName=" + tableName
				+ ", useCount=" + useCount + ", projectName=" + projectName
				+ "]";
	}
	public String getDT() {
		return DT;
	}
	public void setDT(String dT) {
		DT = dT;
	}

	public int getSQLType() {
		return SQLType;
	}

	public void setSQLType(int sQLType) {
		SQLType = sQLType;
	}
	
	
	
}
