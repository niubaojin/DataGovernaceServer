package com.synway.datarelation.pojo.lineage;



import java.io.Serializable;
import java.util.List;

public class ColumnLineageTree implements Serializable {
    private ColumnLineageNode root = new ColumnLineageNode("root");    //树的根节点
//    public int identifying = 1;  //用于记录树上的节点
    public int index = 0;		//用于遍历树的指针路过节点的个数
    //获取根节点
    public ColumnLineageNode getRoot(){
        return this.root;
    }
    //添加方法重载
    public void add(String parentId,ColumnName data,String nodeId){
        this.add(parentId,data,this.getRoot().nodes,nodeId);
    }

    //添加
    public void add(String parentId,ColumnName data,List<ColumnLineageNode> list,String nodeId){
        if("root".equalsIgnoreCase(parentId)){	//如果父节点Id为0
            ColumnLineageNode newNode = new ColumnLineageNode(nodeId,data);
            this.root.nodes.add(newNode);
        }else {  //判空
            if(list.size()==0){
                return;
            }
            for(ColumnLineageNode item:list){
                if(item.getId().equalsIgnoreCase(parentId)){  //找到父节点
                    ColumnLineageNode newNode = new ColumnLineageNode(nodeId , data );
                    item.nodes.add(newNode); //节点添加
                    break;
                }else {
                    add(parentId,data,item.nodes,nodeId);
                }
            }
        }

    }

    public void setData(String id,ColumnName columnName){
        this.setData(this.getRoot().nodes,id,columnName);
    }

    public void setData(List<ColumnLineageNode> list,String id,ColumnName columnName){
        for(int i = 0; i<list.size();i++) {
            if(list.get(i).getId().equalsIgnoreCase(id)){
                list.get(i).setData(columnName);
            }else{
                setData(list.get(i).nodes,id,columnName);
            }
        }
    }


    //遍历方法的重载
    public ColumnName get(String id){
        ColumnName columnName = this.get(this.getRoot().nodes,id);
        return columnName;
    }

    //循环Tree
    public ColumnName get(List<ColumnLineageNode> list,String id){
        for(ColumnLineageNode item:list){
            if(item.getId().equalsIgnoreCase(id)){
                return item.getData();
            }else {
                ColumnName columnName = get(item.nodes,id);
                if(columnName != null){
                    return columnName;
                }
            }
        }
        return null;
    }
}
