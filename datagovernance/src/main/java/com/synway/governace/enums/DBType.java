package com.synway.governace.enums;


/**
 * @author 数据接入
 */

public enum DBType {
	/**
	 * ODPS
	 */
	ODPS("1","ODPS"),
	/**
	 * ADS hc_db
	 */
	HC("2","ADS"),
	/**
	 * ADS hp_db
	 */
	HQ("3","ADS");
	public String index;
	public String name;
	
	private DBType(String index,String name) {
		this.index=index;
		this.name=name;
	}
	public static void main(String[] args) {
		System.out.println(DBType.getDBTypeByIndex("1"));
	}
	
	public static String getDBTypeByIndex(String index){
		for (DBType element : values()) {
			if(element.index.equals(index)){
				return element.name;
			}
		}
		return null;
	}
	public static String getIndexByName(String name){
		for (DBType element : values()) {
			if(element.name.equals(name)){
				return element.index;
			}
		}
		return null;
	}
	
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
