package com.synway.property.pojo.DataProcess;

import java.io.Serializable;
import java.util.List;

/**
 *  根据指定用户userid查询，返回用户信息用户所在机构信息用户所属角色信息
 *
 * @author wdw
 */
public class OrganUser implements Serializable{
    // 是否查询成功
    private Boolean isSuccess = false;
    // 返回的数据
    private List<OrganData> data;

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public List<OrganData> getData() {
        return data;
    }

    public void setData(List<OrganData> data) {
        this.data = data;
    }
}
