package com.synway.datarelation.service.datablood;


import com.synway.datarelation.pojo.databloodline.*;

import java.util.List;

/**
 *  字段血缘的相关接口
 * @author wangdongwei
 */
public interface FieldBloodlineService {
    /**
     *  当查询标准化那边的节点时，需要到ODPS/ads中查询表的字段信息
     *   因为同一个标准表在odps/ads的字段信息可能不同
     * @param queryData
     * @return
     */
    public String submitTaskQueryColumn(QueryBloodlineRelationInfo queryData);

    /**
     *
     *  查询字段血缘有很多种情况，如果是数据来源的节点，则直接查询表COLUMN_BLOODLINE_RELATION，
     *  如果存在 数据库类型/项目名/表名，则先判断全局map中是否还有这个节点任务，有的话表示这个节点正在从ODPS/ADS
     *  查询表的结构信息，不再显示字段血缘，跳出报错信息，说正在查询字段信息，如果已经查询结束，则从表COLUMN_BLOODLINE
     *  中查询字段信息，如果不存在，则直接查询 表COLUMN_BLOODLINE_RELATION
     *  这个id是 节点的id信息 id里面的信息包括 targetId|targetCode|targetTableName
     * @param ids
     * @return  返回的信息是字段的展示信息
     * @throws Exception
     */
    List<QueryColumnTable> getAllColumnByIdsService(DataBloodlineNode.BloodNode ids) throws Exception;

    /**
     * 根据指定的字段名获取字段的血缘关系
     * @param oneColumn
     * @return
     */
    ColumnBloodlineNode getFieldRelationNode(QueryColumnTable oneColumn) throws Exception;

    /**
     * 获取指定表的所有字段信息
     * @param dbType
     * @param clickTableName
     * @return
     */
    List<QueryColumnTable> getAllProcessColumnById(String dbType , String clickTableName);

    /**
     * 根据指定的字段名获取字段的血缘关系
     * @param edgeIds  页面上节点之间的边关系
     * @param nodeIds   页面上数据加工类型的节点
     * @param queryColumnTable   点击的节点信息
     * @param clickTableName  点击的表信息
     * @param columnName    点击的字段信息
     * @return
     */
    ColumnBloodlineNode getDataProcessColumnLink(List<QueryColumnTable.Edges> edgeIds,
                                                 List<String> nodeIds,
                                                 QueryColumnTable queryColumnTable,
                                                 String clickTableName,
                                                 String columnName);


    /**
     * 当只有一个前置节点时，则需要将这整个流程的字段血缘获取出来，将其放入到缓存中
     * @param list  从数据处理获取到的数据
     * @param queryData  页面查询的参数
     */
    void insertColumnRelationToCache(List<QueryBloodlineRelationInfo> list,
                                      QueryDataBloodlineTable queryData);

}
