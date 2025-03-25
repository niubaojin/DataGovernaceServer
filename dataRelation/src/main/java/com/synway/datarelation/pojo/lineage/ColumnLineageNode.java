package com.synway.datarelation.pojo.lineage;

import java.util.ArrayList;
import java.util.List;

public class ColumnLineageNode {

    private String Id;    //节点Id  如果id为root 则为根节点信息
    private ColumnName data; //节点数据
    public List<ColumnLineageNode> nodes = new ArrayList<ColumnLineageNode>(); //多个子节点，利用List实现
    public ColumnLineageNode(String Id){
        this.Id = Id;
    }
    public ColumnLineageNode(String Id,ColumnName data){
        this.Id = Id;
        this.data = data;
    }

    public String getId() {
        return Id;
    }
    public void setId(String id) {
        Id = id;
    }
    public ColumnName getData() {
        return data;
    }
    public void setData(ColumnName data) {
        this.data = data;
    }

    public List<ColumnLineageNode> getNodes() {
        return nodes;
    }

}
