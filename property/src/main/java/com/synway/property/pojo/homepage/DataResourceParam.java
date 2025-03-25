package com.synway.property.pojo.homepage;

/**
 * @author majia
 */
public class DataResourceParam {
    private String centerName;
    private String dataName;
    private String tableNameEN;
    private String tableNameCN;
    private String projectName;
    private String dataType;
    private String isPartitioned;
    private String tableId;
    private String isStandard;

    @Override
    public String toString() {
        return "DataResourceParam{" +
                "centerName='" + centerName + '\'' +
                ", dataName='" + dataName + '\'' +
                ", tableNameEN='" + tableNameEN + '\'' +
                ", tableNameCN='" + tableNameCN + '\'' +
                ", projectName='" + projectName + '\'' +
                ", dataType='" + dataType + '\'' +
                ", isPartitioned='" + isPartitioned + '\'' +
                ", tableId='" + tableId + '\'' +
                ", isStandard='" + isStandard + '\'' +
                '}';
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getTableNameEN() {
        return tableNameEN;
    }

    public void setTableNameEN(String tableNameEN) {
        this.tableNameEN = tableNameEN;
    }

    public String getIsPartitioned() {
        return isPartitioned;
    }

    public void setIsPartitioned(String isPartitioned) {
        this.isPartitioned = isPartitioned;
    }

    public String getIsStandard() {
        return isStandard;
    }

    public void setIsStandard(String isStandard) {
        this.isStandard = isStandard;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getTableNameCN() {
        return tableNameCN;
    }

    public void setTableNameCN(String tableNameCN) {
        this.tableNameCN = tableNameCN;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }
}
