package com.synway.datastandardmanager.pojo;

import java.util.List;

/**
 * 从数据仓库接口获取到的原始数据的详细信息
 */
public class  DataResourceRawInformation {
    //表中文名
    private String tableNameCh;

    /**
     * object表中真实存在的表名
     */
    private String tableNameObject;

    // objectId获取
    private String objectId;

    /**
     * 数据仓库id（数据源Id）
     */
    private String dataBaseId;

    //数据名称(表英文名)
    private String dataSourceName;

    //来源协议编码
    private String sourceId;

    //来源单位中文名
    private String sourceFirmCh;

    //来源单位代码
    private String sourceFirmCode;

    //所属应用系统中文名
    private String sourceProtocolCh;

    //所属应用系统代码
    private String  sourceProtocolCode;

    //业务含义描述
    private String memo;

    // 来源表名
    private String sourceTableName="";

    // 2020518 添加字段回填数据仓库那边的 数据来源分类信息
    private String dataResourceOrigin="";
    private String dataOrganizationStr = "";

    /**
     * 数据组织分类和数据来源分类的 id值 用 / 分隔
     */
    private String dataResourceOriginIds="";
    private String dataOrganizationIds = "";

    //数据安全分级
    private String dataLevel= "";

    //项目空间
    private String project="";

    //数据中心id
    private String centerId;

    //数据中心名称
    private String centerName;

    //数据源名称
    private String resName;

    //数据源类型
    private String resType;

    public String getResType() {
        return resType;
    }

    public void setResType(String resType) {
        this.resType = resType;
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

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public String getTableNameCh() {
        return tableNameCh;
    }

    public void setTableNameCh(String tableNameCh) {
        this.tableNameCh = tableNameCh;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getDataLevel() {
        return dataLevel;
    }

    public void setDataLevel(String dataLevel) {
        this.dataLevel = dataLevel;
    }

    public String getDataResourceOriginIds() {
        return dataResourceOriginIds;
    }

    public void setDataResourceOriginIds(String dataResourceOriginIds) {
        this.dataResourceOriginIds = dataResourceOriginIds;
    }

    public String getDataOrganizationIds() {
        return dataOrganizationIds;
    }

    public void setDataOrganizationIds(String dataOrganizationIds) {
        this.dataOrganizationIds = dataOrganizationIds;
    }

    public String getDataOrganizationStr() {
        return dataOrganizationStr;
    }

    public void setDataOrganizationStr(String dataOrganizationStr) {
        this.dataOrganizationStr = dataOrganizationStr;
    }

    public String getDataResourceOrigin() {
        return dataResourceOrigin;
    }

    public void setDataResourceOrigin(String dataResourceOrigin) {
        this.dataResourceOrigin = dataResourceOrigin;
    }

    //  原始字段的相关信息
    private List<FieldColumn> FieldList;

    public static class FieldColumn{
        // 字段名
        private String fieldName;
        // 字段中文注释
        private String fieldDescription;
        // 字段类型 （不包括长度）
        private String fieldType;
        //字段类型(长度)
        private String iFieldType;
        // 字段长度
        private String fieldLength;
        // 字段是否为空
        private String isNonnull;
        // 是否为主键
        private String isPrimarykey;

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public String getFieldDescription() {
            return fieldDescription;
        }

        public void setFieldDescription(String fieldDescription) {
            this.fieldDescription = fieldDescription;
        }

        public String getFieldType() {
            return fieldType;
        }

        public void setFieldType(String fieldType) {
            this.fieldType = fieldType;
        }

        public String getiFieldType() {
            return iFieldType;
        }

        public void setiFieldType(String iFieldType) {
            this.iFieldType = iFieldType;
        }

        public String getFieldLength() {
            return fieldLength;
        }

        public void setFieldLength(String fieldLength) {
            this.fieldLength = fieldLength;
        }

        public String getIsNonnull() {
            return isNonnull;
        }

        public void setIsNonnull(String isNonnull) {
            this.isNonnull = isNonnull;
        }

        public String getIsPrimarykey() {
            return isPrimarykey;
        }

        public void setIsPrimarykey(String isPrimarykey) {
            this.isPrimarykey = isPrimarykey;
        }
    }

    public String getTableNameObject() {
        return tableNameObject;
    }

    public void setTableNameObject(String tableNameObject) {
        this.tableNameObject = tableNameObject;
    }

    public String getSourceTableName() {
        return sourceTableName;
    }

    public void setSourceTableName(String sourceTableName) {
        this.sourceTableName = sourceTableName;
    }

    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceFirmCh() {
        return sourceFirmCh;
    }

    public void setSourceFirmCh(String sourceFirmCh) {
        this.sourceFirmCh = sourceFirmCh;
    }

    public String getSourceFirmCode() {
        return sourceFirmCode;
    }

    public void setSourceFirmCode(String sourceFirmCode) {
        this.sourceFirmCode = sourceFirmCode;
    }

    public String getSourceProtocolCh() {
        return sourceProtocolCh;
    }

    public void setSourceProtocolCh(String sourceProtocolCh) {
        this.sourceProtocolCh = sourceProtocolCh;
    }

    public String getSourceProtocolCode() {
        return sourceProtocolCode;
    }

    public void setSourceProtocolCode(String sourceProtocolCode) {
        this.sourceProtocolCode = sourceProtocolCode;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public List<FieldColumn> getFieldList() {
        return FieldList;
    }

    public void setFieldList(List<FieldColumn> fieldList) {
        FieldList = fieldList;
    }

    public String getDataBaseId() {
        return dataBaseId;
    }

    public void setDataBaseId(String dataBaseId) {
        this.dataBaseId = dataBaseId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}
