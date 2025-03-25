package com.synway.datastandardmanager.pojo;

import com.alibaba.fastjson.JSONObject;
import com.synway.datastandardmanager.entity.BuildTableInfoVo;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 存储建表的所有字段信息
 *  包括 odps/ads/hive/ads
 */
public class BuildTableManage {
    public static final String ODPS="odps";
    public static final String ADS="ads";
    public static final String HIVE="hive";
    public static final String HBASE="hbase";
    public static final String DATAHUB="datahub";
    public static final String CLICK_HOUSE="clickhouse";
    //  存储数据库的类型
    private String dbType;
    // 存储的表名
    private String tableName;
    // 所有的建表信息都存在在这里
    private BuildTableInfoVo buildTableInfoVo;
    /**
     * 20210924 该字段弃用
     */
    private JSONObject allObjectList;
    // 审批信息标识
    private String approvalId;
    // tableId值
    private String tableId;
    //状态(0:初始化;1:审批中;2:退回;3:终止)
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public BuildTableInfoVo getBuildTableInfoVo() {
        return buildTableInfoVo;
    }

    public void setBuildTableInfoVo(BuildTableInfoVo buildTableInfoVo) {
        this.buildTableInfoVo = buildTableInfoVo;
    }

    public JSONObject getAllObjectList() {
        return allObjectList;
    }

    public void setAllObjectList(JSONObject allObjectList) {
        this.allObjectList = allObjectList;
    }
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public String getApprovalId() {
        return approvalId;
    }

    public void setApprovalId(String approvalId) {
        this.approvalId = approvalId;
    }
}
