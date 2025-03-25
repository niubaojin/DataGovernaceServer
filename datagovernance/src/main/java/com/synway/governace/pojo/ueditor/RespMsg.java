package com.synway.governace.pojo.ueditor;

public class RespMsg {
	private Integer status;
	private String msg;
	private Object data;
	
	public static RespMsg build(Integer status,String msg,Object data){
		return new RespMsg(status,msg,data);
	}
	public static RespMsg build(Integer status,String msg){
		return new RespMsg(status,msg,null);
	}
	public static RespMsg ok(Object data){
		return new RespMsg(data);
	}
	public static RespMsg ok(){
		return new RespMsg(null);
	}
	public static RespMsg fail(){
		return new RespMsg(500,"fail",null);
	}
	public RespMsg(){}
	public RespMsg(Integer status,String msg,Object data){
		this.status=status;
		this.msg=msg;
		this.data=data;
	}
	public RespMsg(Object data){
		this.data=data;
		this.status=200;
		this.msg="word导出上传成功...";
	}
	public Integer getStatus() {
		return status;
	}
	public String getMsg() {
		return msg;
	}
	public Object getData() {
		return data;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
}
