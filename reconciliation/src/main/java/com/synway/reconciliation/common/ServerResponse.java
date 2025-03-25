package com.synway.reconciliation.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;

/**
 * 响应实体类
 * @author ym
 */
@JsonSerialize()
public class ServerResponse<T> implements Serializable{
	private static final long serialVersionUID = 1L;
	private int status;
	private String message;
	private T result;
	
	private ServerResponse(int status){
		this.status=status;
	}
	
	public ServerResponse(int status, T result){
		this.status=status;
		this.result = result;
	}
	
	private ServerResponse(int status, String message, T result){
		this.status=status;
		this.message = message;
		this.result = result;
	}
	
	public ServerResponse(int status, String message){
		this.status=status;
		this.message = message;
	}
	@JsonIgnore
	public boolean isSuccess(){
		return this.status==ResponseCode.SUCCESS.getCode();
	}
	
	public int getStatus(){
		return status;
	}
	
	public T getResult(){
		return result;
	}
	
	public String getMessage(){
		return message;
	}
	
	public static <T> ServerResponse<T> createBySuccess(){
		return new ServerResponse<T>(ResponseCode.SUCCESS.getCode()); 
	}
	
	public static <T> ServerResponse<T> createBySuccessMessage(String msg){
		return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),msg); 
	}
	
	public static <T> ServerResponse<T> createBySuccess(T data){
		return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),data); 
	}
	
	public static <T> ServerResponse<T> createBySuccess(String msg,T data){
		return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),msg,data); 
	}
	
	public static <T> ServerResponse<T> createByError(){
		return new ServerResponse<T>(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDesc()); 
	}
	
	public static <T> ServerResponse<T> createByErrorMessage(String errorMsg){
		return new ServerResponse<T>(ResponseCode.ERROR.getCode(),errorMsg); 
	}
	
	public static <T> ServerResponse<T> createByErrorCodeMessage(int code,String errorMsg){
		return new ServerResponse<T>(code,errorMsg); 
	}
	
	
	
	
}
