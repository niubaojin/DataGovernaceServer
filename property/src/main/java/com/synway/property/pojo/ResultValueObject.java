package com.synway.property.pojo;


import java.io.Serializable;

/**
 * @author 数据接入
 */
public class ResultValueObject implements Serializable {
    public final static String OK = "200";
    public final static String ERROR = "400";
    // 请求后具体的返回数据
    private Object data;
    //报错信息
    private String errorMessage="";
    //返回状态
    private String status;

    @Override
    public String toString() {
        return "ResultValueObject{" +
                "data=" + data +
                ", errorMessage='" + errorMessage + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
