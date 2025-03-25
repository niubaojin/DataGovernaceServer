package com.synway.datarelation.pojo.dataresource;

import java.io.Serializable;
import java.util.List;

public class ProjectInfo implements Serializable {
    private String projectName;
    private String comment;
    private List<TableInfo> tableInfos;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<TableInfo> getTableInfos() {
        return tableInfos;
    }

    public void setTableInfos(List<TableInfo> tableInfos) {
        this.tableInfos = tableInfos;
    }




    @Override
    public String toString() {
        return "ProjectInfo{" +
                "projectName='" + projectName + '\'' +
                ", comment='" + comment + '\'' +
                ", tableInfos=" + tableInfos +
                '}';
    }

}
