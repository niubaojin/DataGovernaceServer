package com.synway.datarelation.pojo.databloodline;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.synway.datarelation.pojo.common.TreeNode;


import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 点击锁定之后需要存储的缓存信息
 * @author wangdongwei
 * @date 2021/5/11 18:36
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NodePageCache implements Serializable {

    /**
     * 左侧树的相关信息
     */
    private List<TreeNode> treeNodeList;

    /**
     * 页面上的所有节点信息
     */
    private DataBloodlineNode dataBloodlineNode;

    /**
     * 输入框的内容
     */
    private String queryInfo;

    /**
     * 模糊还是精确查询
     */
    @NotNull
    private  String queryType="LIKE";

    /**
     * 左侧树查询的类型
     */
    @NotNull
    private String nodeQueryType;

    /**
     * 点击锁定节点的nodeId
     */
    @NotNull
    private String lockNodeId;

    /**
     * 页面上的唯一uuid，用于区分不同的窗口
     *
     */
    @NotNull
    private String pageId;


    /**
     * 左侧树点击的节点信息
     */
    private TreeNode clickTreeData;

    /**
     * 数据插入缓存中的时间  前端不需要传
     */
    private long insertDate;

    public long getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(long insertDate) {
        this.insertDate = insertDate;
    }

    public TreeNode getClickTreeData() {
        return clickTreeData;
    }

    public void setClickTreeData(TreeNode clickTreeData) {
        this.clickTreeData = clickTreeData;
    }

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public List<TreeNode> getTreeNodeList() {
        return treeNodeList;
    }

    public void setTreeNodeList(List<TreeNode> treeNodeList) {
        this.treeNodeList = treeNodeList;
    }

    public DataBloodlineNode getDataBloodlineNode() {
        return dataBloodlineNode;
    }

    public void setDataBloodlineNode(DataBloodlineNode dataBloodlineNode) {
        this.dataBloodlineNode = dataBloodlineNode;
    }

    public String getQueryInfo() {
        return queryInfo;
    }

    public void setQueryInfo(String queryInfo) {
        this.queryInfo = queryInfo;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public String getNodeQueryType() {
        return nodeQueryType;
    }

    public void setNodeQueryType(String nodeQueryType) {
        this.nodeQueryType = nodeQueryType;
    }

    public String getLockNodeId() {
        return lockNodeId;
    }

    public void setLockNodeId(String lockNodeId) {
        this.lockNodeId = lockNodeId;
    }
}
