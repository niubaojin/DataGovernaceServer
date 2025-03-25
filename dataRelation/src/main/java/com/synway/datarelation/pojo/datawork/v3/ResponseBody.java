package com.synway.datarelation.pojo.datawork.v3;


/**
 * 返回体的相关信息
 */
public class ResponseBody {
    private String requestId;// # 请求的ID,用来定位日志,排查问题
    private String returnCode;// # 0表示调用成功
    private String returnMessage;//# 返回执行的详细信息
    private String returnValue;// 具体的返回值 具体结果数据
    private String successCode;//不一定都有
    private int count;//  返回的总数 不一定都有


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

    public String getSuccessCode() {
        return successCode;
    }

    public void setSuccessCode(String successCode) {
        this.successCode = successCode;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


    @Override
    public String toString() {
        return "ResponseBody{" +
                "requestId='" + requestId + '\'' +
                ", returnCode='" + returnCode + '\'' +
                ", returnMessage='" + returnMessage + '\'' +
                ", returnValue='" + returnValue + '\'' +
                ", successCode='" + successCode + '\'' +
                ", count=" + count +
                '}';
    }
}
