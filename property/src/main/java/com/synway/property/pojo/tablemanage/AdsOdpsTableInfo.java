package com.synway.property.pojo.tablemanage;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author 数据接入
 */
public class AdsOdpsTableInfo {
    private String id;
    private String tableCreater;//负责人(表创建人)
    private String tableName;//表名
    private String tableNameZH;
    private String createTime;//表创建时间
    private String productLine;//表所属产品线
    private String responsiblePersonOfProduct;//产品负责人(中文名)
    private String lastDDLModifiedTime;//最后DDL修改时间
    private String stage;//所属平台
    private String projectName;//项目名
    private String projectNameZH;//项目中文名
    private String memo;//描述
    private String responsiblepersonID;//负责人ID
    private long physicalSize;//物理存储大小
    private long life;//生命周期
    private boolean partitioned;//是否为分区
    private String lastDataModifiedTime;//获取数据最后修改时间
    private String power;
    private List<AdsOdpsTableDetailInfo> adsOdpsTableDetailInfoList;//表信息


    public void setProjectNameZH(String projectNameZH) {
        this.projectNameZH = projectNameZH;
    }

    public String getProjectNameZH() {
        return projectNameZH;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getPower() {
        return power;
    }

    public boolean isPartitioned() {
        return partitioned;
    }

    public void setPartitioned(boolean partitioned) {
        this.partitioned = partitioned;
    }

    public String getLastDataModifiedTime() {
        return lastDataModifiedTime;
    }

    public void setLastDataModifiedTime(String lastDataModifiedTime) {
        this.lastDataModifiedTime = lastDataModifiedTime;
    }

    public void setLife(long life) {
        this.life = life;
    }

    public long getLife() {
        return life;
    }

    public long getPhysicalSize() {
        return physicalSize;
    }

    public void setPhysicalSize(long physicalSize) {
        this.physicalSize = physicalSize;
    }

    public AdsOdpsTableInfo(){}

    public AdsOdpsTableInfo(String id, String tableCreater, String tableName, String tableNameZH, String createTime, String productLine, String responsiblePersonOfProduct, String lastDDLModifiedTime, String stage, String projectName, String memo, String responsiblepersonID) {
        this.id = id;
        this.tableCreater = tableCreater;
        this.tableName = tableName;
        this.tableNameZH = tableNameZH;
        this.createTime = createTime;
        this.productLine = productLine;
        this.responsiblePersonOfProduct = responsiblePersonOfProduct;
        this.lastDDLModifiedTime = lastDDLModifiedTime;
        this.stage = stage;
        this.projectName = projectName;
        this.memo = memo;
        this.responsiblepersonID = responsiblepersonID;
    }

    //getter and setter...
    public void setTableNameZH(String tableNameZH) {this.tableNameZH = tableNameZH;}
    public String getTableNameZH() {return tableNameZH;}
    public void setResponsiblepersonID(String responsiblepersonID) {this.responsiblepersonID = responsiblepersonID;}
    public String getResponsiblepersonID() {return responsiblepersonID;}
    public void setAdsOdpsTableDetailInfoList(List<AdsOdpsTableDetailInfo> adsOdpsTableDetailInfoList) {this.adsOdpsTableDetailInfoList = adsOdpsTableDetailInfoList;}
    public List<AdsOdpsTableDetailInfo> getAdsOdpsTableDetailInfoList() {return adsOdpsTableDetailInfoList;}
    public void setId(String id) {this.id = id;}
    public String getId() {return id;}
    public String getMemo() {return memo;}
    public String getProjectName() {return projectName;}
    public void setMemo(String memo) {this.memo = memo;}
    public void setProjectName(String projectName) {this.projectName = projectName;}
    public void setStage(String stage) {this.stage = stage;}
    public String getStage() {return stage;}
    public void setTableCreater(String tableCreater) {this.tableCreater = tableCreater;}
    public String getTableCreater() {return tableCreater;}
    public String getTableName() {return tableName;}
    public void setTableName(String tableName) {this.tableName = tableName;}
    public String getCreateTime() {return createTime;}
    public void setCreateTime(String createTime) {this.createTime = createTime;}
    public String getProductLine() {return productLine;}
    public void setProductLine(String productLine) {this.productLine = productLine;}
    public String getResponsiblePersonOfProduct() {return responsiblePersonOfProduct;}
    public void setResponsiblePersonOfProduct(String responsiblePersonOfProduct) {this.responsiblePersonOfProduct = responsiblePersonOfProduct;}
    public String getLastDDLModifiedTime() {return lastDDLModifiedTime;}
    public void setLastDDLModifiedTime(String lastDDLModifiedTime) {this.lastDDLModifiedTime = lastDDLModifiedTime;}

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
