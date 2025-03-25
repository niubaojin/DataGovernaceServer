package com.synway.datastandardmanager.pojo.enums;

/**
 *
 * 标准状态
 * @author obito
 * @version 1.0
 * @date
 */
public enum ObjectStateType {
	FORMAL_USE("已发布"),
	TEMP_USE("未发布"),
	ABANDON("废止");
	private String value=null;

	ObjectStateType(String value){
		this.value=value;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public static String getObjectStateType(int value){
		if(value==0){
			return ObjectStateType.TEMP_USE.getValue();
		}else if (value==1) {
			return ObjectStateType.FORMAL_USE.getValue();
		}else if (value==-1) {
			return ObjectStateType.ABANDON.getValue();
		}
		return null;
	}

	public static int getObjectStateStatus(String value){
		if(value.equalsIgnoreCase(ObjectStateType.TEMP_USE.getValue())){
			return 0;
		}else if (value.equalsIgnoreCase(ObjectStateType.FORMAL_USE.getValue())) {
			return 1;
		}else if (value.equalsIgnoreCase(ObjectStateType.ABANDON.getValue())) {
			return -1;
		}
		return -1;
	}
}
