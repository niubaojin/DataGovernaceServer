package com.synway.property.util;

/**
 * @author 数据接入
 */
public enum ResponseCode {

	/**
	 * 成功代码
	 */
	SUCCESS(1,"SUCCESS"),
	/**
	 * 失败代码
	 */
	ERROR(0,"ERROR"),
	/**
	 * 非法代码
	 */
	ILLEGAL_ARGUMENT(2,"ILLEGAL_ARGUMENT");
	//代码
	private final int code;
	//描述
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
