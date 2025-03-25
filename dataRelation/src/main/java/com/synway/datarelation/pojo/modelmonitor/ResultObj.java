package com.synway.datarelation.pojo.modelmonitor;

/**
 * @author
 * @date 2019/4/25 8:54
 */
public class ResultObj {

    private String requestId;//请求的 id
    private String returnCode;//0 表示调用成功
    private String returnMessage;//返回执行的详细信息
    private String returnValue; //
    private Integer count; //返回结果数

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    public String getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(String returnValue) {
        this.returnValue = returnValue;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
