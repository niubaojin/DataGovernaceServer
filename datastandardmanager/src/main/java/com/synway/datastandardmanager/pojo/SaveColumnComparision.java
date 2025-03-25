package com.synway.datastandardmanager.pojo;

import java.util.List;

/**
 * @author wangdongwei
 * @ClassName SaveColumnComparision
 * @description TODO
 * @date 2020/9/24 21:03
 */
public class SaveColumnComparision {

    private List<TableColumnPage> columnList;
    private String tableId;
    private StandardTableCreated createdTableData;
    private boolean createdTableIsPartition;
    // 审批信息标识
    private String approvalId;
    //状态(0:初始化;1:审批中;2:退回;3:终止)
    private String status;
    // 浏览器上的ip地址
    private String localIp;
    private String userId;

    // hbase新增字段时 有其它设置需要，需要存储标准字段的相关信息


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLocalIp() {
        return localIp;
    }

    public void setLocalIp(String localIp) {
        this.localIp = localIp;
    }

    public boolean getCreatedTableIsPartition() {
        return createdTableIsPartition;
    }

    public void setCreatedTableIsPartition(boolean createdTableIsPartition) {
        this.createdTableIsPartition = createdTableIsPartition;
    }

    public String getApprovalId() {
        return approvalId;
    }

    public void setApprovalId(String approvalId) {
        this.approvalId = approvalId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<TableColumnPage> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<TableColumnPage> columnList) {
        this.columnList = columnList;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public StandardTableCreated getCreatedTableData() {
        return createdTableData;
    }

    public void setCreatedTableData(StandardTableCreated createdTableData) {
        this.createdTableData = createdTableData;
    }
}
