package com.synway.datastandardmanager.pojo;


/**
 * ztree插件的简单数据格式
 */
public class ZtreeNode {
    // 节点的id号
    private String id;
    //节点的父节点id号  如果为root，表示该节点为父节点
    private String pId;
    // 该节点的名称
    private String name;
    // 该节点是否为表节点 true：表示是最底层的表节点
    private Boolean tableNodeFlag = false;


    @Override
    public String toString() {
        return "ZtreeNode{" +
                "id='" + id + '\'' +
                ", pId='" + pId + '\'' +
                ", name='" + name + '\'' +
                ", tableNodeFlag=" + tableNodeFlag +
                '}';
    }

    public Boolean getTableNodeFlag() {
        return tableNodeFlag;
    }

    public void setTableNodeFlag(Boolean tableNodeFlag) {
        this.tableNodeFlag = tableNodeFlag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
