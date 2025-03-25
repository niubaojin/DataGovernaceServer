package com.synway.datarelation.pojo.modelrelation;

/**
 * 模型节点之间的连接关系
 */
public class ModelLinkData {

    // 父节点
    private String parentnode;
    //　子节点
    private  String childnode;

    @Override
    public String toString() {
        return "ModelLinkData{" +
                "parentnode='" + parentnode + '\'' +
                ", childnode='" + childnode + '\'' +
                '}';
    }

    public String getParentnode() {
        return parentnode;
    }

    public void setParentnode(String parentnode) {
        this.parentnode = parentnode;
    }

    public String getChildnode() {
        return childnode;
    }

    public void setChildnode(String childnode) {
        this.childnode = childnode;
    }
}
