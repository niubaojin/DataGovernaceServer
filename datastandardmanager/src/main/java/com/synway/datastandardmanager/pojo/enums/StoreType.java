package com.synway.datastandardmanager.pojo.enums;

public enum StoreType {
	HABSE("hbase"),
	ORACLE("oracle"),
	SQL("sql"),
	ADS("ads");
	private String value=null;

	StoreType(String value){
		this.value=value;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public static String getStoreType(int value){
		if(value==0){
			return StoreType.HABSE.getValue();
		}else if (value==1) {
			return StoreType.ORACLE.getValue();
		}else if (value==2) {
			return StoreType.SQL.getValue();
		}else if (value==3) {
			return StoreType.ADS.getValue();
		}
		return null;
	}
	public static int getStoreValue(String value){
		if(value.equalsIgnoreCase(StoreType.HABSE.getValue())){
			return 0;
		}else if (value.equalsIgnoreCase(StoreType.ORACLE.getValue())) {
			return 1;
		}else if (value.equalsIgnoreCase(StoreType.SQL.getValue())) {
			return 2;
		}else if (value.equalsIgnoreCase(StoreType.ADS.getValue())) {
			return 3;
		}
		return 4;
	}
}
