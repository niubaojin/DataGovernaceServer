package com.synway.datarelation.pojo.databloodline;

/**
 * @author wangdongwei
 * @ClassName ProcessColumnTask
 * @description 字段血缘解析要用到的几个实体类
 * @date 2020/9/18 12:08
 */
public class ProcessColumnTask {
    // hive节点上的sql代码
    private String sqlCode;
    private String dbType;
    private String projectName;
    private String nodeId;

    public String getSqlCode() {
        return sqlCode;
    }

    public void setSqlCode(String sqlCode) {
        this.sqlCode = sqlCode;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }
}
