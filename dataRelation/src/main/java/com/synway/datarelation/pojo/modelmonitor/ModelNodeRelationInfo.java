package com.synway.datarelation.pojo.modelmonitor;

/**
 * @author
 * @date 2019/4/24 9:19
 */
public class ModelNodeRelationInfo {


    private String projectName; //依赖的 Node 所属 Flow 的项目名
    private String flowName; //依赖的 Node 所属 Flow 名
    private String nodeName; //依赖的 Node 名
    private Integer relationType; //  依赖方式，参见 DependentType 类,只有普通，自定义
    private Integer projectId;//  项目 ID

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

    public Integer getRelationType() {
        return relationType;
    }

    public void setRelationType(Integer relationType) {
        this.relationType = relationType;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}
