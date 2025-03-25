//package com.synway.governace.pojo.process;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.databind.annotation.JsonSerialize;
//
//import java.io.Serializable;
//
//@JsonSerialize()
//public class ProcessServerResponse<T> implements Serializable{
//
//	/**
//	 *
//	 */
//	private static final long serialVersionUID = 1L;
//    /**
//     * 1:成功  0：失败
//     */
//	private int status;
//    /**
//     * 报错原因
//     */
//	private String message;
//    /**
//     * 具体的数据
//     */
//	private T result;
//
//	private ProcessServerResponse(int status){
//		this.status=status;
//	}
//
//	public ProcessServerResponse(int status, T result){
//		this.status=status;
//		this.result = result;
//	}
//
//	private ProcessServerResponse(int status, String message, T result){
//		this.status=status;
//		this.message = message;
//		this.result = result;
//	}
//
//	public ProcessServerResponse(int status, String message){
//		this.status=status;
//		this.message = message;
//	}
//	@JsonIgnore
//	public boolean isSuccess(){
//		return this.status== ProcessResponseCode.SUCCESS.getCode();
//	}
//
//	public int getStatus(){
//		return status;
//	}
//
//	public T getResult(){
//		return result;
//	}
//
//	public String getMessage(){
//		return message;
//	}
//
//	public static <T> ProcessServerResponse<T> createBySuccess(){
//		return new ProcessServerResponse<T>(ProcessResponseCode.SUCCESS.getCode());
//	}
//
//	public static <T> ProcessServerResponse<T> createBySuccessMessage(String msg){
//		return new ProcessServerResponse<T>(ProcessResponseCode.SUCCESS.getCode(),msg);
//	}
//
//	public static <T> ProcessServerResponse<T> createBySuccess(T data){
//		return new ProcessServerResponse<T>(ProcessResponseCode.SUCCESS.getCode(),data);
//	}
//
//	public static <T> ProcessServerResponse<T> createBySuccess(String msg,T data){
//		return new ProcessServerResponse<T>(ProcessResponseCode.SUCCESS.getCode(),msg,data);
//	}
//
//	public static <T> ProcessServerResponse<T> createByError(){
//		return new ProcessServerResponse<T>(ProcessResponseCode.ERROR.getCode(),ProcessResponseCode.ERROR.getDesc());
//	}
//
//	public static <T> ProcessServerResponse<T> createByErrorMessage(String errorMsg){
//		return new ProcessServerResponse<T>(ProcessResponseCode.ERROR.getCode(),errorMsg);
//	}
//
//	public static <T> ProcessServerResponse<T> createByErrorCodeMessage(int code,String errorMsg){
//		return new ProcessServerResponse<T>(code,errorMsg);
//	}
//
//
//
//}
