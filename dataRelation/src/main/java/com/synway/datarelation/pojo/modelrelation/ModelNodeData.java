package com.synway.datarelation.pojo.modelrelation;

/**
 * 模型节点的相关信息　实体类
 */
public class ModelNodeData {
    //  工作流节点对应的key
    private String key;
    //  工作流节点对应的  value
    private String text;
    // 'true' 对应的是 true  'false' 对应的是 'false'
    private String isGroup;
    //  在gojs中对应的是 group
    private String groupname;
    // 20200515 新增节点类型
    private String nodeType;

    @Override
    public String toString() {
        return "ModelNodeData{" +
                "key='" + key + '\'' +
                ", text='" + text + '\'' +
                ", isGroup='" + isGroup + '\'' +
                ", groupname='" + groupname + '\'' +
                ", nodeType='" + nodeType + '\'' +
                '}';
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIsGroup() {
        return isGroup;
    }

    public void setIsGroup(String isGroup) {
        this.isGroup = isGroup;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }
}
