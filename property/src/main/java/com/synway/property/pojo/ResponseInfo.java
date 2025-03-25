package com.synway.property.pojo;

/**
 * inceptCenter项目向RaskSchedule项目发送请求的返回结果
 * @author Chen KaiWei
 */
public class ResponseInfo {

    public static String ERROR = "ERROR";
    public static String OK = "OK";

    private String result = ERROR;
    private String message = null;
    private String data = null;
    private int status;

    public ResponseInfo(){
    }

    public ResponseInfo(String value ){
        this.result = value;
    }

    public ResponseInfo(String value, String message ){
        this.result = value;
        this.message = message;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setResult(Boolean result) {
        this.result = result?OK:ERROR;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }



}
