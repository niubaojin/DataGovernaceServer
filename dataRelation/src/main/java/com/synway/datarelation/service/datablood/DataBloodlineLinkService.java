package com.synway.datarelation.service.datablood;


import com.synway.datarelation.pojo.databloodline.ColumnRelationDB;
import com.synway.datarelation.pojo.databloodline.DataBloodlineNode;
import com.synway.datarelation.pojo.databloodline.NodeColumnRelation;
import com.synway.datarelation.pojo.databloodline.QueryDataBloodlineTable;

import java.util.List;
import java.util.Map;

/**
 *  节点的流程
 * @author wangdongwei
 */
public interface DataBloodlineLinkService {

    /**
     *   刚开始只是查询一个节点的信息，然后调用该方法查询上一个流程和下一个流程的
     *   节点信息
     * @param bloodNode
     * @param queryType
     * @param nodeShow
     * @param step
     * @param pageId
     * @param queryId
     * @return
     */
    public DataBloodlineNode getNextProcessByNode(DataBloodlineNode.BloodNode bloodNode, String queryType, Boolean nodeShow, int step, String pageId, String queryId);


    /**
     * 将字段信息插入到数据库中
     * @param pageId
     * @param queryId
     * @param columnMap
     * @param relationColumnMap
     * @throws Exception
     */
    public void insertNodeColumnAccessStandard(String pageId, String queryId, Map<String, List<ColumnRelationDB>> columnMap, Map<String, List<NodeColumnRelation>> relationColumnMap) throws Exception;


    /**
     * 获取上一个流程/下一个流程的对应关系的节点，只需要一个
     * @param queryData   查询的参数 实体类
     * @param queryType   查询的方向 往左还是往右
     * @param queryNum    1表示相隔一个流程 2表示相隔2个流程
     * @param nodeType   节点类型
     * @return
     */
    public DataBloodlineNode getNextProcessOneNode(QueryDataBloodlineTable queryData, String queryType, int queryNum, String nodeType);

}
