package com.synway.governace.pojo.process;

public enum ProcessResponseCode {

	SUCCESS(1,"SUCCESS"),
	ERROR(0,"ERROR"),
	ILLEGAL_ARGUMENT(2,"ILLEGAL_ARGUMENT");
	private final int code;
	private final String desc;

	ProcessResponseCode(int code, String desc){
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
