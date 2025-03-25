package com.synway.datastandardmanager.pojo.buildtable;

/**
 * @author wangdongwei
 * @ClassName ObjectInfo
 * @description TODO
 * @date 2021/1/28 17:06
 */
public class ObjectInfo {
    private String objectId;                        // 主键id
    private String tableId;                         // 表协议id
    private String tableName;                       // 表英文名称
    private String objectName;                      // 表中文名称
    private String organizationClassificationId;    // 数据组织分类代码
    private String organizationClassificationCh;    // 数据组织分类名称

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getOrganizationClassificationId() {
        return organizationClassificationId;
    }

    public void setOrganizationClassificationId(String organizationClassificationId) {
        this.organizationClassificationId = organizationClassificationId;
    }

    public String getOrganizationClassificationCh() {
        return organizationClassificationCh;
    }

    public void setOrganizationClassificationCh(String organizationClassificationCh) {
        this.organizationClassificationCh = organizationClassificationCh;
    }

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
}
