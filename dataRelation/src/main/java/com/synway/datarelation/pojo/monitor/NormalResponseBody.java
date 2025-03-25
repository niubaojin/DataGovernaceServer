package com.synway.datarelation.pojo.monitor;

import java.util.List;

/**
 * @author majia
 * @version 1.0
 * @date 2020/12/17 14:48
 */
public class NormalResponseBody<T> {
    // # 请求的ID,用来定位日志,排查问题
    private String requestId;
    // # 0表示调用成功
    private String returnCode;
    //# 返回执行的详细信息
    private String returnMessage;
    // 具体的返回值 具体结果数据
    private List<T> returnValue;
    //不一定都有
    private String successCode;
    //  返回的总数 不一定都有
    private int count;

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

    public List<T> getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(List<T> returnValue) {
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
}
