package com.synway.datarelation.dao.datablood;


import com.synway.datarelation.dao.BaseDAO;
import com.synway.datarelation.pojo.common.BloodManagementQuery;
import com.synway.datarelation.pojo.databloodline.*;
import com.synway.datarelation.pojo.monitor.node.MaxVersionNodeId;
import com.synway.datarelation.pojo.modelrelation.DataBloodlineInOutTable;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wangdongwei
 */
@Mapper
@Repository
public interface DataBloodlineDao extends BaseDAO {

    /**
     * 查询数据加工的相关信息
     * @param queryStr
     * @param queryType
     * @return
     */
    List<QueryDataBloodlineTable> getDataProcessTableSearch(@Param("queryStr") String queryStr,
                                                            @Param("queryType") String queryType);

    /**
     * 如果是阿里的v2版本 则查询表 SYNDT_IN_OUT_TABLE_ANALYZE
     * @param queryStr
     * @return
     */
    List<QueryDataBloodlineTable> getDataProcessTableSearchV2(@Param("queryStr") String queryStr);

    /**
     *  查询数据加工血缘的信息
     * @param queryTableName
     * @param queryType   1：查询带项目名的表名  2：查询不带项目名的表名
     * @return
     */
    List<RelationshipNode> getDataProcessTableRelation(@Param("queryTableName") String queryTableName,
                                                       @Param("queryType") int queryType);

    /**
     *  从右往左查询的
     * @param queryTableName
     * @param queryType
     * @return
     */
    List<RelationshipNode> getLeftDataProcessTableRelation(@Param("queryTableName") String queryTableName,
                                                           @Param("queryType") int queryType);


    /**
     * 查询出 应用-血缘关系的信息
     * @param tableName
     * @param queryType  1:表示为模糊查询  2：表示精确查询
     * @param pageType like：模糊查询 exact：精确查询
     * @return
     */
    List<ApplicationSystem> getApplicationSystemByTableName(@Param("tableName") String tableName,
                                                            @Param("queryType") int queryType,
                                                            @Param("pageType") String pageType);

    /**
     * 向数据库中插入 应用血缘的信息
     * @param applicationSystem
     * @return
     */
    int insertApplicationSystem(ApplicationSystem applicationSystem);

    /**
     * 判断该条记录是否已经存在
     * @param applicationSystem
     * @return
     */
    int getApplicationSystemCount(ApplicationSystem applicationSystem);


    /**
     *  从字段关系表中删除指定的数据
     * @param nodeId
     * @param type
     * @return
     */
    int deleteColumnRelation(@Param("nodeId") String nodeId,
                             @Param("type") String type);

    /**
     * 删除字段血缘信息
     * @param delList
     * @return
     */
    int deleteColumnRelationByList(@Param("rcList") List<String> delList);

    /**
     * 插入字段血缘信息
     * @param columnRelationDBList
     * @return
     */
    int insertColumnRelation(@Param("rcList") List<ColumnRelationDB> columnRelationDBList);

    /**
     * 删除字段的关系
     * @param id
     * @return
     */
    int deleteNodeColumnMessage(@Param("id") String id);

    int deleteNodeColumnMessageByList(@Param("rcList") List<String> delList);

    int insertNodeColumnMessage(@Param("rcList") List<NodeColumnRelation> list);

    /**
     * 根据 tableId和所属应用系统查询具体的表名
     * @param tableId
     * @param tableCode
     * @return
     */
    String getTableNameByTableId(@Param("tableId") String tableId, @Param("tableCode") String tableCode);

    /**
     *  缓存的数据
     * @return
     */
    List<RelationshipNode> getAllDataProcessList();

    List<RelationshipNode> getAllDataProcessListV2();


    /**
     * 插入数据
     * @param list
     */
    void insertInOutTables(@Param("list") List<DataBloodlineInOutTable> list);

    List<MaxVersionNodeId> getMaxVersionNode();

    int delMaxVersionInOutTable(@Param("list") List<MaxVersionNodeId> paramList);

    int deleteInOutTables();



    int deleteInOutTablesByType(@Param("nodeType")String nodeType);



    int getTableRelationCount();


    // 根据tableid获取objectid的值
    String getObjectIdbyTableId(@Param("tableId")String tableId);


    /**
     * 获取应用系统个数/涉及表个数
     * @return
     */
    SummaryData searchSummaryData();


    /**
     *  获取应用血缘的相关信息
     * @param bloodManagementQuery
     * @return
     */
    List<ApplicationSystemTable> searchApplicationBloodTable(BloodManagementQuery bloodManagementQuery);

    /**
     * 筛选的内容
     * @return
     */
    List<ApplicationSystemTable> searchAllData();


    /**
     * 删除应用血缘数据
     * @param applicationSystem
     * @return
     */
    int deleteApplicationSystem(ApplicationSystem applicationSystem);


    /**
     * 搜索提示框
     * @param searchValue
     * @return
     */
    List<String> queryConditionSuggestion(@Param("searchValue") String searchValue);


    /**
     *  获取对应的数据用于 v2版本sql代码的获取
     * @param  nodeType 10:sql节点  23：同步任务节点
     * @return
     */
    List<QueryNodeParams> getQueryNodeParams(@Param("nodeType")String nodeType);

    /**
     * 获取 m_node_in_out_table 表的数据量
     * @return
     */
    int queryInOutTableCount();

    /**
     * 获取所有标准表的分类信息
     * @return
     */
    List<TableClassify> queryAllTableClassify();

    /**
     * 根据表英文名获取表中文名
     * @return
     */
    String getTableNameChByEn(@Param("tableName")String tableName);


    /**
     * 判断应用血缘管理数据数据量
     * @param applicationSystem
     * @return
     */
    int getApplicationTableCount(ApplicationSystem applicationSystem);

    /**
     * 获取所有的应用血缘一级模块的数据量
     * @return
     */
    int getApplicationNum();
}
