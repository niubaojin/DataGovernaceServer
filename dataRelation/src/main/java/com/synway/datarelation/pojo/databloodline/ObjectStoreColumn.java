package com.synway.datarelation.pojo.databloodline;

import java.io.Serializable;

/**
 * 表 OBJECT_STORE_INFO 的相关字段信息
 * @author wangdongwei
 * @date 2021/1/27 15:57
 */
public class ObjectStoreColumn implements Serializable {
    /**
     *标准数据项集编码
     */
    private String tableId;
    /**
     *表英文名称
     */
    private String tableName;
    /**
     *数据项集中文名称
     */
    private String objectName;
    /**
     *平台类型 1：odps  2：hc  3：hp  4：hbase
     * 5：hive  6: ES  7: clickhouse  8: libra 9: TRS
     */
    private int storeType;
    /**
     *表空间名称
     */
    private String projectName;
    /**
     *表空间中文名称
     */
    private String projectNameCh="";
    /**
     *存储描述信息
     */
    private String memo;
    /**
     * 创建者
     */
    private String creater;
    /**
     *创建者代码
     */
    private String createrId;
    /**
     *表创建时间
     */
    private String tableCreateTime;
    /**
     *表结构修改时间
     */
    private String tableModTime;
    /**
     * 是否入库  1：入库程序读取并执行入库操作
     * 0：入库程序不进行入库操作
     */
    private int importFlag = 1;


    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public int getStoreType() {
        return storeType;
    }

    public void setStoreType(int storeType) {
        this.storeType = storeType;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectNameCh() {
        return projectNameCh;
    }

    public void setProjectNameCh(String projectNameCh) {
        this.projectNameCh = projectNameCh;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getCreaterId() {
        return createrId;
    }

    public void setCreaterId(String createrId) {
        this.createrId = createrId;
    }

    public String getTableCreateTime() {
        return tableCreateTime;
    }

    public void setTableCreateTime(String tableCreateTime) {
        this.tableCreateTime = tableCreateTime;
    }

    public String getTableModTime() {
        return tableModTime;
    }

    public void setTableModTime(String tableModTime) {
        this.tableModTime = tableModTime;
    }

    public int getImportFlag() {
        return importFlag;
    }

    public void setImportFlag(int importFlag) {
        this.importFlag = importFlag;
    }
}
