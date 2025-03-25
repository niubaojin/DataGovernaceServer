package com.synway.datarelation.service.datablood;

import com.synway.datarelation.pojo.common.DataProcessBloodCache;
import com.synway.datarelation.pojo.common.melon.RelationLinksCache;
import com.synway.datarelation.pojo.databloodline.*;
import com.synway.datarelation.pojo.lineage.ColumnLineDb;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/**
 *
 * @author wangdongwei
 */
public interface CacheManageDataBloodlineService {

    /**
     *获取所有接入血缘的信息，并将数据写入到缓存中
     */
    public void getDataAcessDataBloodCache();

    void getStandardRelationCache();

    /**
     * 查询出加工血缘的所有缓存数据,将数据写入到缓存中
     */
    public void getDataProcessDataBloodCache();
    /**
     *   获取数据加工中字段血缘的缓存信息，方便查询时快速获取
     */
    public void getDataProcessColumnCache();
    /**
     *修改应用血缘的缓存
     * 20200427 因为不需要直接查询所有的信息，所以不需要使用应用缓存信息
     */
    public void getApplicationDataBloodCache();
    /**
     * 获取所有的标准表的分类信息
     */
    public void getAllTableClassifyCache();

    /**
     * 查询数据接入缓存中的数据
     * @param dataBloodlineQueryParams  只查询指定接入血缘关系的信息
     * @return
     */
    public RelationInfoRestTemolate getAccessRelationInfo(DataBloodlineQueryParams dataBloodlineQueryParams);


    /**
     *
     * 查询数据加工血缘中缓存的数据
     * @param queryType  查询类型  left right main
     * @param queryTableName  查询名称 表名
     * @return
     */
    public List<RelationshipNode> getCheckRelationshipNodeList(String queryType, String queryTableName);
    /**
     * 将字段信息插入到缓存中
     * @param pageId
     * @param queryId
     * @param columnMap
     * @param relationColumnList
     */
    public void insertNodeColumnToCache(String pageId, String queryId, Map<String, List<ColumnRelationDB>> columnMap, List<NodeColumnRelation> relationColumnList);

    /**
     *
     * 在缓存中存储 查询指点节点下所有的字段信息
     * @param node
     * @param fieldColumnMap   从阿里平台获取
     * @return
     * @throws Exception
     */
    public List<QueryColumnTable> getAllColumnByIdsToCache(DataBloodlineNode.BloodNode node, Map<String, List<FieldColumn>> fieldColumnMap) throws Exception;

    /**
     *
     * 获取字段血缘关系的source target相关的关系
     * @param nodeSource  节点来源关系
     * @param nodeTarget  节点的输出关系
     * @param columnName   字段名称
     * @param queryType   往左查还是往右查
     * @param flag       是否    0 表示 不以从大数据平台获取的字段信息为准
     * @param fieldColumnList    大数据平台所有的字段信息
     * @param pageKey   页面缓存的key值
     * @return
     */
    public List<String> getColumnNameListByCache(String nodeSource, String nodeTarget, String columnName, String queryType,
                                                 String flag, List<FieldColumn> fieldColumnList, String pageKey);
    /**
     * 根据子节点的表名获取对应的链路信息
     * 如果查询的表名包含了项目名，则直接查询对应的缓存
     * 如果没有包含项目名，则需要截取掉项目名，然后再获取 目前只能是有同步任务的输出表
     * @param tableNameEn
     * @return
     */
    List<RelationshipNode> getProcessNodeByChild(String tableNameEn);
    /**
     * 根据表名来获取指定的分类信息
     * @param tableName
     * @param tableId
     * @return
     */
    public String getTableClassifyCache(String tableName,String tableId);


    /**
     * 根据表获取应用血缘信息的
     * @param tableName
     * @return
     */
    List<ApplicationSystem> getApplicationSystemCache(String tableName);


    /**
     * 获取所有的应用血缘信息
     * @return
     */
    ConcurrentMap<String,List<ApplicationSystem>> getAllApplicationSystemCache();


    /**
     * 获取所有的加工血缘信息
     * @return
     */
    DataProcessBloodCache getAllDataProcessBloodCache();


    /**
     * 获取接入血缘信息
     * @return
     */
    DataProcessBloodCache getAllAccessBloodCache();


    /**
     * 获取字段血缘
     * @param type  dataprocess：数据加工
     * @return
     */
    List<ColumnLineDb> getLineageColumnInfoByType(String type);


    /**
     * 将 tableId和 objectId对应的关系插入到缓存中
     */
    void getSynlteObjectId();

    /**
     * 获取已建表的相关信息
     */
    void getObjectStoreInfo();


    RelationLinksCache getAllRelationLinks(DataBloodlineQueryParams dataBloodlineQueryParams);

    RelationLinksCache getAllRelationLinks();
}
