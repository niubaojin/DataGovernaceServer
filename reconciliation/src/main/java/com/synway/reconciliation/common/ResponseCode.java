package com.synway.reconciliation.common;

/**
 * 响应状态码
 * @author ym
 */

public enum ResponseCode {
	
	SUCCESS(1,"SUCCESS"),
	ERROR(0,"ERROR"),
	ILLEGAL_ARGUMENT(2,"ILLEGAL_ARGUMENT");
	private final int code;
	private final String desc;
	
	ResponseCode(int code, String desc){
		this.code=code;
		this.desc=desc;
	}
	
	public int getCode(){
		return code;
	}
	
	public String getDesc(){
		return desc;
	}

}
