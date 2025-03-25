package com.synway.property.pojo.datastoragemonitor;

import java.io.Serializable;
import java.util.Objects;

/**
 * 获取需要监控的实时表名
 * @author majia
 */
public class NeedAddRealTimeTable implements Serializable {
    private String dataBaseType;
    private String projectName;
    private String tableNameEN;
    private String tableNameCH;
    private String partitioned;//是否为分区
    private String dataCenterId;
    private String dataCenterName;
    private String dataSourceId;
    private String dataSourceName;
    private String isStandard;
    private String isAddMonitor;
    private String isDelete;
    private String partitionColumn;
    private String tableUuid;

    @Override
    public String toString() {
        return "NeedAddRealTimeTable{" +
                "dataBaseType='" + dataBaseType + '\'' +
                ", projectName='" + projectName + '\'' +
                ", tableNameEN='" + tableNameEN + '\'' +
                ", tableNameCH='" + tableNameCH + '\'' +
                ", partitioned='" + partitioned + '\'' +
                ", dataCenterId='" + dataCenterId + '\'' +
                ", dataCenterName='" + dataCenterName + '\'' +
                ", dataSourceId='" + dataSourceId + '\'' +
                ", dataSourceName='" + dataSourceName + '\'' +
                ", isStandard='" + isStandard + '\'' +
                ", isAddMonitor='" + isAddMonitor + '\'' +
                ", isDelete='" + isDelete + '\'' +
                ", partitionColumn='" + partitionColumn + '\'' +
                ", tableUuid='" + tableUuid + '\'' +
                '}';
    }

    public String getTableUuid() {
        return tableUuid;
    }

    public void setTableUuid(String tableUuid) {
        this.tableUuid = tableUuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NeedAddRealTimeTable)) {
            return false;
        }
        NeedAddRealTimeTable that = (NeedAddRealTimeTable) o;
        return getProjectName().equals(that.getProjectName()) &&
                getTableNameEN().equals(that.getTableNameEN()) &&
                getDataSourceId().equals(that.getDataSourceId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProjectName(), getTableNameEN(), getDataSourceId());
    }

    public String getIsAddMonitor() {
        return isAddMonitor;
    }

    public void setIsAddMonitor(String isAddMonitor) {
        this.isAddMonitor = isAddMonitor;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public String getPartitionColumn() {
        return partitionColumn;
    }

    public void setPartitionColumn(String partitionColumn) {
        this.partitionColumn = partitionColumn;
    }

    public String getIsStandard() {
        return isStandard;
    }

    public void setIsStandard(String isStandard) {
        this.isStandard = isStandard;
    }

    public String getDataCenterId() {
        return dataCenterId;
    }

    public void setDataCenterId(String dataCenterId) {
        this.dataCenterId = dataCenterId;
    }

    public String getDataCenterName() {
        return dataCenterName;
    }

    public void setDataCenterName(String dataCenterName) {
        this.dataCenterName = dataCenterName;
    }

    public String getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(String dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    public String getPartitioned() {
        return partitioned;
    }

    public void setPartitioned(String partitioned) {
        this.partitioned = partitioned;
    }

    public String getDataBaseType() {
        return dataBaseType;
    }

    public void setDataBaseType(String dataBaseType) {
        this.dataBaseType = dataBaseType;
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

    public String getTableNameCH() {
        return tableNameCH;
    }

    public void setTableNameCH(String tableNameCH) {
        this.tableNameCH = tableNameCH;
    }
}
