package com.synway.datarelation.pojo.databloodline;


import java.io.Serializable;
import java.util.List;

/**
 * 从 根据协议名英文名（jz_xx）、协议中文名、表英文名(不包含项目名)、查询，返回对于匹配数据的血缘关系
 */
public class RelationInfoRestTemolate  implements Serializable{
    private String reqRet;   //  ok 表示成功
    private String error;
    private List<QueryBloodlineRelationInfo> QueryBloodlineRelationInfos;

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
        return QueryBloodlineRelationInfos;
    }

    public void setReqInfo(List<QueryBloodlineRelationInfo> reqInfo) {
        this.QueryBloodlineRelationInfos = reqInfo;
    }
}
