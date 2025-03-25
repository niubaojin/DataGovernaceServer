package com.synway.datarelation.pojo.databloodline;

/**
 *
 * 返回项目名/节点名/工作流名称
 * @author wangdongwei
 * @ClassName QueryNodeParams
 * @description TODO
 * @date 2020/12/23 15:43
 */
public class QueryNodeParams {
    private String projectName;
    private String flowName;
    private String nodeName;
    private int fileVersion;

    public int getFileVersion() {
        return fileVersion;
    }

    public void setFileVersion(int fileVersion) {
        this.fileVersion = fileVersion;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }
}
