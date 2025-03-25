package com.synway.datarelation.pojo.databloodline;

import java.io.Serializable;

/**
 *
 * 表组织分类信息的相关信息
 * @author wangdongwei
 * @date 2021/3/8 9:47
 */
public class TableClassify implements Serializable {

    private static final long serialVersionUID = 1L;

    private String tableNameEn;

    private String tableId;

    /**
     * 组织分类标注  原始库/业务库/资源库/主题库/其它
     */
    private String organizationClassifyName;
    private String objectId;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getTableNameEn() {
        return tableNameEn;
    }

    public void setTableNameEn(String tableNameEn) {
        this.tableNameEn = tableNameEn;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getOrganizationClassifyName() {
        return organizationClassifyName;
    }

    public void setOrganizationClassifyName(String organizationClassifyName) {
        this.organizationClassifyName = organizationClassifyName;
    }
}
