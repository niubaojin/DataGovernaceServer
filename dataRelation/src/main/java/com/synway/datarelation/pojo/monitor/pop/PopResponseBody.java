package com.synway.datarelation.pojo.monitor.pop;

/**
 * @author majia
 * @version 1.0
 * @date 2021/1/20 14:11
 */
public class PopResponseBody {

    private String ReturnCode;
    private String ReturnErrorSolution;
    private String ReturnMessage;
    private String RequestId;
    private String ReturnValue;
    private String success;

    public String getReturnCode() {
        return ReturnCode;
    }

    public void setReturnCode(String returnCode) {
        this.ReturnCode = returnCode;
    }

    public String getReturnErrorSolution() {
        return ReturnErrorSolution;
    }

    public void setReturnErrorSolution(String returnErrorSolution) {
        this.ReturnErrorSolution = returnErrorSolution;
    }

    public String getReturnMessage() {
        return ReturnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.ReturnMessage = returnMessage;
    }

    public String getRequestId() {
        return RequestId;
    }

    public void setRequestId(String requestId) {
        this.RequestId = requestId;
    }

    public String getReturnValue() {
        return ReturnValue;
    }

    public void setReturnValue(String returnValue) {
        this.ReturnValue = returnValue;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
