package com.synway.datastandardmanager.pojo.sourcedata;

/**
 * @author wangdongwei
 * @ClassName OtherModuleManageCreated
 * @description 表 MODULE_CREATED_OBJECT 的实体类
 * @date 2020/9/17 16:11
 */
public class OtherModuleManageCreated {
    private String tableId;
    // MODULE_NAME
    private String moduleName;
    // OBJECTID
    private String objectId;

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}
