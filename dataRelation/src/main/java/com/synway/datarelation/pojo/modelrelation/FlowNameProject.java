package com.synway.datarelation.pojo.modelrelation;

/**
 * 存储工作流名称 id 与
 */
public class FlowNameProject {
    // 所属项目名称
    private String project_name;
    // 所属项目id
    private String project_id;
    // 工作流id
    private String id;
    // 工作流名称
    private String flow_name;
    //具体类型（1-节点，2-工作流，3-资源）
    private String object_type;

    private String insert_time;

    @Override
    public String toString() {
        return "FlowNameProject{" +
                "project_name='" + project_name + '\'' +
                ", project_id='" + project_id + '\'' +
                ", id='" + id + '\'' +
                ", flow_name='" + flow_name + '\'' +
                ", object_type='" + object_type + '\'' +
                ", insert_time='" + insert_time + '\'' +
                '}';
    }

    public String getInsert_time() {
        return insert_time;
    }

    public void setInsert_time(String insert_time) {
        this.insert_time = insert_time;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return flow_name;
    }

    public void setName(String flow_name) {
        this.flow_name = flow_name;
    }

    public String getOBJECT_TYPE() {
        return object_type;
    }

    public void setOBJECT_TYPE(String OBJECT_TYPE) {
        this.object_type = OBJECT_TYPE;
    }
}
