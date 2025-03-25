package com.synway.governace.pojo.largeScreen;


import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author 数据接入
 */
public class DataResource implements Serializable {

    //    数据源id,唯一
    private String resId;
    //    数据库名称,唯一
    private String resName;
    //    数据库类型
    private String resType;
    //    数据库是否连通
    private int status;
    //    数据库连接参数信息
    private String connectInfo;
    //    数据中心id
    private String centerId;
    //    数据中心名称
    private String centerName;
    //  0:全部（默认） 2：本地    1：非本地
    private int  isLocal;
    //    总存储空间
    private String totalSpace;
    //    已用空间
    private String usedSpace;
    //     审批
    private int approved;
    //    版本号
    private String version;
    //管理人
    private String manager;
    //管理单位
    private String manageUnit;
    //创建人
    private String creater;
    //备注
    private String remark;
    //创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date gmtCreate;
    //最近更新时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date gmtModified;


    /**
     * odps\ads\datahub\hbase\hive\ck专用
     * **/
    public List<String> getProjectNames(){
        if(StringUtils.isBlank(this.connectInfo)){
            return null;
        }
        List<String> projectList = new ArrayList<>();;
        try{
            JSONObject jdbcInfo = JSONObject.parseObject(this.connectInfo);
            if (jdbcInfo==null){
                return null;
            }
            String projects = jdbcInfo.getString("projects");
            if (StringUtils.isBlank(projects)){
                return null;
            }
            List<String> list = Arrays.asList(projects.toLowerCase().split(","));
            if (list != null){
                projectList.addAll(list);
            }
        }catch (Exception e){
//            e.printStackTrace();
        }
        return projectList;
    }

    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }

    public String getResType() {
        return resType;
    }

    public void setResType(String resType) {
        this.resType = resType;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getConnectInfo() {
        return connectInfo;
    }

    public void setConnectInfo(String connectInfo) {
        this.connectInfo = connectInfo;
    }

    public String getCenterId() {
        return centerId;
    }

    public void setCenterId(String centerId) {
        this.centerId = centerId;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public int getIsLocal() {
        return isLocal;
    }

    public void setIsLocal(int isLocal) {
        this.isLocal = isLocal;
    }

    public String getTotalSpace() {
        return totalSpace;
    }

    public void setTotalSpace(String totalSpace) {
        this.totalSpace = totalSpace;
    }

    public String getUsedSpace() {
        return usedSpace;
    }

    public void setUsedSpace(String usedSpace) {
        this.usedSpace = usedSpace;
    }

    public int getApproved() {
        return approved;
    }

    public void setApproved(int approved) {
        this.approved = approved;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getManageUnit() {
        return manageUnit;
    }

    public void setManageUnit(String manageUnit) {
        this.manageUnit = manageUnit;
    }

}
