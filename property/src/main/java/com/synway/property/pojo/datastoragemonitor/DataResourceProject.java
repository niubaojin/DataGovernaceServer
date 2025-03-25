package com.synway.property.pojo.datastoragemonitor;

import java.util.Objects;

/**
 * @author majia
 * @version 1.0
 * @date 2021/3/31 16:17
 */
public class DataResourceProject {

    private String tableType;
    private String projectName;

    public String getTableType() {
        return tableType;
    }

    public DataResourceProject setTableType(String tableType) {
        this.tableType = tableType;
        return this;
    }

    public String getProjectName() {
        return projectName;
    }

    public DataResourceProject setProjectName(String projectName) {
        this.projectName = projectName;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DataResourceProject)) {
            return false;
        }
        DataResourceProject project = (DataResourceProject) o;
        return Objects.equals(getTableType(), project.getTableType()) &&
                Objects.equals(getProjectName(), project.getProjectName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTableType(), getProjectName());
    }
}
