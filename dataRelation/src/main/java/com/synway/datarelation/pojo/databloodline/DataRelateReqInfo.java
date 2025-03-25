package com.synway.datarelation.pojo.databloodline;

import java.util.List;

public class DataRelateReqInfo{

    /**ok、error*/
    private String reqRet;
    /**异常信息*/
    private String error;
    private List<QueryBloodlineRelationInfo> reqInfo;

    public String getReqRet() {
        return reqRet;
    }

    public void setReqRet(String reqRet) {
        this.reqRet = reqRet;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<QueryBloodlineRelationInfo> getReqInfo() {
        return reqInfo;
    }

    public void setReqInfo(List<QueryBloodlineRelationInfo> reqInfo) {
        this.reqInfo = reqInfo;
    }
}


