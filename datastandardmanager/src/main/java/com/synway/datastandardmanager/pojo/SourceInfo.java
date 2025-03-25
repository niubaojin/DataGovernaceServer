package com.synway.datastandardmanager.pojo;

import com.synway.datastandardmanager.util.UUIDUtil;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

public class SourceInfo {
    private String id;
    public String sourceProtocol ;  //来源协议
    public String tableName;        //数据表名(英文名)
    public String sourceSystem;     //来源系统
    public String sourceFirm;       //来源单位
    //数据名称(中文名)
    private String dataName;

    //数据源
    private String dataId;

    //项目空间
    private String projectName;

    //数据中心中文名
    private String centerName;

    //数据中心id
    private String centerId;

    public Date createTime;
    public Date updateTime;


    public SourceInfo(){}
    public SourceInfo(String sourceProtocol, String tableName, String sourceSystem, String sourceFirm,String dataName,
                      String dataId,String projectName,String centerName,String centerId){
        Date now = new Date();
        this.sourceProtocol = sourceProtocol;
        this.tableName = tableName;
        this.sourceSystem=sourceSystem;
        this.sourceFirm = sourceFirm;
        this.dataName = dataName;
        this.dataId = dataId;
        this.projectName = projectName;
        this.centerName = centerName;
        this.centerId = centerId;
        this.createTime = now;
        this.updateTime = now;
    }

    public SourceInfo(String sourceProtocol, String tableName, String sourceSystem, String sourceFirm){
        Date now = new Date();
        this.sourceProtocol = sourceProtocol;
        this.tableName = tableName;
        this.sourceSystem=sourceSystem;
        this.sourceFirm = sourceFirm;
        this.createTime = now;
        this.updateTime = now;
    }

    public static SourceInfo getInstance(String sourceProtocol, String tableName, String sourceSystem, String sourceFirm){
        SourceInfo sourceInfo = new SourceInfo(sourceProtocol,tableName,sourceSystem,sourceFirm);
        sourceInfo.setId(UUIDUtil.getUUID());
        return sourceInfo;
    }

    public static SourceInfo getInstance(String sourceProtocol, String tableName, String sourceSystem, String sourceFirm,
                                         String dataName,String dataId,String projectName,String centerName,String centerId){
        SourceInfo sourceInfo = new SourceInfo(sourceProtocol,tableName,sourceSystem,sourceFirm,dataName,dataId,projectName,
                centerName,centerId);
        sourceInfo.setId(UUIDUtil.getUUID());
        return sourceInfo;
    }



    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getCenterId() {
        return centerId;
    }

    public void setCenterId(String centerId) {
        this.centerId = centerId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getSourceProtocol() {
        return sourceProtocol;
    }

    public void setSourceProtocol(String sourceProtocol) {
        this.sourceProtocol = sourceProtocol;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getSourceSystem() {
        return sourceSystem;
    }

    public void setSourceSystem(String sourceSystem) {
        this.sourceSystem = sourceSystem;
    }

    public String getSourceFirm() {
        return sourceFirm;
    }

    public void setSourceFirm(String sourceFirm) {
        this.sourceFirm = sourceFirm;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.MULTI_LINE_STYLE);
    }

}
