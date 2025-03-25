package com.synway.datarelation.service.datablood.impl;

import com.alibaba.fastjson.JSONObject;
import com.hazelcast.core.HazelcastInstance;
import com.synway.common.exception.ExceptionUtil;
import com.synway.datarelation.constant.Common;
import com.synway.datarelation.dao.datablood.DataBloodlineDao;
import com.synway.datarelation.dao.datablood.FieldBloodlineDao;
import com.synway.datarelation.dao.datablood.LineageColumnParsingDao;
import com.synway.datarelation.constant.Constant;
import com.synway.datarelation.pojo.common.DataColumnCache;
import com.synway.datarelation.pojo.common.DataProcessBloodCache;
import com.synway.datarelation.pojo.common.melon.RelationLinksCache;
import com.synway.datarelation.pojo.databloodline.*;
import com.synway.datarelation.pojo.lineage.ColumnLineDb;
import com.synway.datarelation.service.datablood.CacheManageDataBloodlineService;
import com.synway.datarelation.util.RestTemplateHandle;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

/**
 * 缓存的相关代码
 * 20210317 使用Hazelcast 分布式缓存
 * 缓存字段血缘与数据血缘
 * @author wangdongwei
 */
@Service
public class CacheManageDataBloodlineImpl implements CacheManageDataBloodlineService {
    private Logger logger = LoggerFactory.getLogger(CacheManageDataBloodlineImpl.class);
    @Autowired
    DataBloodlineDao dataBloodlineDao;
    @Autowired
    FieldBloodlineDao fieldBloodlineDao;
    @Autowired
    RestTemplateHandle restTemplateHandle;
    @Autowired
    private LineageColumnParsingDao lineageColumnParsingDao;

    @Autowired
    private ConcurrentHashMap<String,String> synlteObjectIdMap;

    @Autowired
    private ConcurrentHashMap<String,List<ObjectStoreColumn>> storeInfoMap;



    private final HazelcastInstance hazelcastInstance;
    @Autowired
    public CacheManageDataBloodlineImpl(HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
    }

    /**
     * 获取所有接入血缘的信息，并将数据写入到缓存中
     */
    @Override
    public void getDataAcessDataBloodCache(){
        logger.info("获取所有接入血缘的信息，并将数据写入到缓存中");
        DataProcessBloodCache dataProcessBloodCache = new DataProcessBloodCache();
        try{
            List<QueryBloodlineRelationInfo> dataList = restTemplateHandle.queryAllAccessRelationInfo();
            // sourceSysId 里面存储了中文，但是 sourceChiName 里面存储了代码 造成查询出错
            if(dataList != null && !dataList.isEmpty()){
                for(QueryBloodlineRelationInfo data:dataList){
                    if(StringUtils.isNumeric(data.getSourceChiName()) && !StringUtils.isNumeric(data.getSourceSysId())){
                        String id = ""+data.getSourceChiName();
                        data.setSourceChiName(data.getSourceSysId());
                        data.setSourceSysId(id);
                    }
                }
                dataProcessBloodCache.setAllDataAccessRelationInfo(dataList);
                dataProcessBloodCache.setType(Constant.ACCESS);
                dataProcessBloodCache.setInsertTime(new Date());
                ConcurrentMap<String,DataProcessBloodCache> hazelcastInstanceMap = hazelcastInstance.getMap(Common.DATA_PROCESS_BLOOD);
                hazelcastInstanceMap.put(Constant.ACCESS,dataProcessBloodCache);
                logger.info("查询接入血缘关系结束，写入缓存成功，接入血缘缓存数据量为："+dataList.size());
            }else{
                logger.info("接入血缘数据库中的数据量为0，无法更新缓存中的数据");
                logger.error("接入血缘数据库中的数据量为0，无法更新缓存中的数据");
            }
        }catch (Exception e){
            logger.error("查询接入血缘报错，请查询日志"+ExceptionUtil.getExceptionTrace(e));
        }
    }

    @Override
    public void getStandardRelationCache() {
        DataBloodlineQueryParams queryParams = new DataBloodlineQueryParams();
        RelationInfoRestTemolate dataList = restTemplateHandle.queryStandardRelationInfo(queryParams);
        if(dataList != null && dataList.getReqInfo() != null && dataList.getReqInfo().size() > 0){
            DataProcessBloodCache dataProcessBloodCache = new DataProcessBloodCache();
            dataProcessBloodCache.setAllDataAccessRelationInfo(dataList.getReqInfo());
            dataProcessBloodCache.setType(Constant.STANDARD);
            dataProcessBloodCache.setInsertTime(new Date());
            ConcurrentMap<String,DataProcessBloodCache> hazelcastInstanceMap = hazelcastInstance.getMap(Common.DATA_PROCESS_BLOOD);
            hazelcastInstanceMap.put(Constant.STANDARD,dataProcessBloodCache);
        }
    }

    /**
     * @author chenfei
     * @date 2024/6/6 15:07
     * @Description 加工血缘
     */
    @Override
    public void getDataProcessDataBloodCache() {
        logger.info("开始查询所有的加工血缘数据，然后将数据写入到缓存中");
        // 先查询数据，查询之后再清除缓存
        DataProcessBloodCache dataProcessBloodCache = new DataProcessBloodCache();
        try{
            // 20201225 所有的血缘关系都使用自己解析的数据
            List<RelationshipNode> relationshipNodeList = dataBloodlineDao.getAllDataProcessList();
            if(relationshipNodeList != null && !relationshipNodeList.isEmpty()){
                Map<String, List<RelationshipNode>> parentMap =  relationshipNodeList.stream().filter(d -> (StringUtils.isNotEmpty(d.getParentTN())
                        && StringUtils.isNotEmpty(d.getChildrenTN()))).collect(Collectors.groupingBy(RelationshipNode::getParentTN));
                Map<String, List<RelationshipNode>> childMap = relationshipNodeList.stream().filter(d -> (StringUtils.isNotEmpty(d.getParentTN()) &&
                        StringUtils.isNotEmpty(d.getChildrenTN()))).collect(Collectors.groupingBy(RelationshipNode::getChildrenTN));
                dataProcessBloodCache.setChildKeyData(childMap);
                dataProcessBloodCache.setParentKeyData(parentMap);
                dataProcessBloodCache.setInsertTime(new Date());
                dataProcessBloodCache.setType(Constant.DATAPROCESS);
                // 20210317 使用分布式的 map数据
                ConcurrentMap<String,DataProcessBloodCache> hazelcastInstanceMap = hazelcastInstance.getMap(Common.DATA_PROCESS_BLOOD);
                hazelcastInstanceMap.put(Constant.DATAPROCESS,dataProcessBloodCache);
                logger.info("加工血缘的数据量为"+hazelcastInstanceMap.get(Constant.DATAPROCESS).getChildKeyData().size());
                logger.info("更新数据加工血缘缓存成功");
            }else{
                logger.info("加工血缘数据库中的数据量为0，无法更新缓存中的数据");
                logger.error("加工血缘数据库中的数据量为0，无法更新缓存中的数据");
            }
        }catch (Exception e){
            logger.info("更新数据加工血缘存储失败,清除缓存，请查看日志信息"+ ExceptionUtil.getExceptionTrace(e));
        }
    }

    /**
     * 修改应用血缘的缓存  20200427 因为不需要直接查询所有的信息，所以不需要使用应用缓存信息
     */
    @Override
    public void getApplicationDataBloodCache(){
        // 添加缓存
        try{
            logger.info("开始获取应用血缘的缓存信息");
            List<ApplicationSystem> applicationSystemList = dataBloodlineDao.getApplicationSystemByTableName(
                    "",3,"like");
            ConcurrentMap<String,List<ApplicationSystem>> applicationMap = hazelcastInstance.getMap(Common.APPLICATION_BLOOD);
            if(applicationSystemList!= null){
                Map<String ,List<ApplicationSystem>> map = applicationSystemList.stream().filter(d -> StringUtils.isNotEmpty(d.getTableNameEn())).collect(
                        Collectors.groupingBy(d -> (d.getTableNameEn()).toUpperCase())
                );
                // 20210317 使用分布式的 map数据
                applicationMap.clear();
                applicationMap.putAll(map);
            }
            logger.info("获取应用血缘的缓存信息成功，数据量为："+applicationMap.keySet().size());
        }catch (Exception e){
            logger.error("查询应用缓存报错"+ExceptionUtil.getExceptionTrace(e));
        }
    }

    /**
     *  查询数据接入缓存中的数据
     * @param dataBloodlineQueryParams  只查询指定接入血缘关系的信息
     * @return
     */
    @Override
    public RelationInfoRestTemolate getAccessRelationInfo(DataBloodlineQueryParams dataBloodlineQueryParams){
        logger.info("开始查询指定接入血缘关系的信息"+JSONObject.toJSONString(dataBloodlineQueryParams));
        RelationInfoRestTemolate relationInfoRestTemolate = new RelationInfoRestTemolate();
        List<QueryBloodlineRelationInfo> resultData = new ArrayList<>();
        try{
            // 使用分布式缓存
            ConcurrentMap<String,DataProcessBloodCache> hazelcastInstanceMap = hazelcastInstance.getMap(Common.DATA_PROCESS_BLOOD);
            DataProcessBloodCache dataProcessBloodCache = hazelcastInstanceMap.getOrDefault(Constant.ACCESS,null);
            if(dataProcessBloodCache != null){
                List<QueryBloodlineRelationInfo>  queryBloodlineRelationInfoList = dataProcessBloodCache.getAllDataAccessRelationInfo();
                if(queryBloodlineRelationInfoList != null){
                    if("like".equalsIgnoreCase(dataBloodlineQueryParams.getQuerytype())){
                        for(QueryBloodlineRelationInfo queryBloodlineRelationInfo:queryBloodlineRelationInfoList){
                            if(queryBloodlineRelationInfo.getTargetEngName().toLowerCase().contains(dataBloodlineQueryParams.getQueryinfo().toLowerCase()) ||
                                    queryBloodlineRelationInfo.getSourceEngName().toLowerCase().contains(dataBloodlineQueryParams.getQueryinfo().toLowerCase()) ||
                                    queryBloodlineRelationInfo.getSourceChiName().toLowerCase().contains(dataBloodlineQueryParams.getQueryinfo().toLowerCase())){
                                resultData.add(queryBloodlineRelationInfo);
                            }
                        }
                    }else{
                        if(dataBloodlineQueryParams.getQueryinfo().contains("|")){
                            String sourceCode = dataBloodlineQueryParams.getQueryinfo().split("\\|")[1];
                            String queryInfo = dataBloodlineQueryParams.getQueryinfo().split("\\|")[0];
                            for(QueryBloodlineRelationInfo queryBloodlineRelationInfo:queryBloodlineRelationInfoList){
                                if((queryInfo.equalsIgnoreCase(queryBloodlineRelationInfo.getTargetEngName())
                                        ||queryInfo.equalsIgnoreCase(queryBloodlineRelationInfo.getSourceEngName())
                                        ||queryInfo.equalsIgnoreCase(queryBloodlineRelationInfo.getSourceChiName()))
                                  && sourceCode.equalsIgnoreCase(queryBloodlineRelationInfo.getSourceSysId())){
                                    resultData.add(queryBloodlineRelationInfo);
                                }
                            }
                        }else{
                            for(QueryBloodlineRelationInfo queryBloodlineRelationInfo:queryBloodlineRelationInfoList){
                                if(dataBloodlineQueryParams.getQueryinfo().equalsIgnoreCase(queryBloodlineRelationInfo.getTargetEngName())
                                        ||dataBloodlineQueryParams.getQueryinfo().equalsIgnoreCase(queryBloodlineRelationInfo.getSourceEngName())
                                        ||dataBloodlineQueryParams.getQueryinfo().equalsIgnoreCase(queryBloodlineRelationInfo.getSourceChiName())){
                                    resultData.add(queryBloodlineRelationInfo);
                                }
                            }
                        }
                    }
                }
            }
            relationInfoRestTemolate.setReqRet("ok");
        }catch (Exception e){
            relationInfoRestTemolate.setReqRet("error");
            logger.error("获取报错"+ ExceptionUtil.getExceptionTrace(e));
        }
        relationInfoRestTemolate.setReqInfo(resultData);
        return relationInfoRestTemolate;
    }

    /**
     * 查询数据加工血缘中缓存的数据
     * @param queryType  查询类型  left right main
     * @param queryTableName  查询名称 表名
     * @return
     */
    @Override
    public  List<RelationshipNode> getCheckRelationshipNodeList(String queryType, String queryTableName){
        List<RelationshipNode> allData = new ArrayList<>();
        try{
            //  left 表示是从应用血缘这边查询过来的数据 ,则从child节点反查数据
            if(queryType.equalsIgnoreCase(Constant.MAIN) || queryType.equalsIgnoreCase(Constant.RIGHT)){
                // 使用分布式缓存
                ConcurrentMap<String,DataProcessBloodCache> dataProcessBloodHashMap = hazelcastInstance.getMap(Common.DATA_PROCESS_BLOOD);
                Map<String, List<RelationshipNode>> parentKeyMap = new HashMap<>();
                if(dataProcessBloodHashMap.get(Constant.DATAPROCESS) != null){
                    parentKeyMap = dataProcessBloodHashMap.get(Constant.DATAPROCESS).getParentKeyData();
                }
                if(queryTableName!=null && queryTableName.contains(".")){
                    for(String key :parentKeyMap.keySet()){
                        if(key.equalsIgnoreCase(queryTableName)){
                            allData.addAll(parentKeyMap.getOrDefault(key,new ArrayList<>()));
                            break;
                        }
                    }
                }else{
                    for(String key :parentKeyMap.keySet()){
                        String newKey = key.split("\\.").length>1?key.split("\\.")[1]:key.split("\\.")[0];
                        if(newKey.equalsIgnoreCase(queryTableName)){
                            allData.addAll(parentKeyMap.getOrDefault(key,new ArrayList<>()));
                            break;
                        }
                    }
                }
            }
            if(queryType.equalsIgnoreCase(Constant.MAIN) && allData.size() >0){
                allData = new ArrayList<>(allData.stream().collect(toMap(d -> (d.getChildrenTN()+"_"+d.getParentTN()+"_"+d.getFlowName()+"_"+d.getNodeName()),
                        e->e,(exists, replacement)-> exists)).values());
                return allData;
            }
            if(queryType.equalsIgnoreCase(Constant.MAIN) || queryType.equalsIgnoreCase(Constant.LEFT)){
                // 使用分布式缓存
                ConcurrentMap<String,DataProcessBloodCache> dataProcessBloodHashMap = hazelcastInstance.getMap(Common.DATA_PROCESS_BLOOD);
                DataProcessBloodCache dataProcessBloodCache = dataProcessBloodHashMap.get(Constant.DATAPROCESS);
                Map<String, List<RelationshipNode>> childKeyMap = new HashMap<>();
                if(dataProcessBloodCache != null){
                    childKeyMap = dataProcessBloodCache.getChildKeyData();
                }
                if(queryTableName!=null && queryTableName.contains(".")){
                    for(String key :childKeyMap.keySet()){
                        if(key.equalsIgnoreCase(queryTableName)){
                            allData.addAll(childKeyMap.getOrDefault(key,new ArrayList<>()));
                            break;
                        }
                    }
                }else{
                    for(String key :childKeyMap.keySet()){
                        String newKey = key.split("\\.").length>1?key.split("\\.")[1]:key.split("\\.")[0];
                        if(newKey.equalsIgnoreCase(queryTableName)){
                            allData.addAll(childKeyMap.getOrDefault(key,new ArrayList<>()));
                            break;
                        }
                    }
                }
            }
            // 清除数据中的重复项
            allData = new ArrayList<>(allData.stream().collect(toMap(d -> (d.getChildrenTN()+"_"+d.getParentTN()+"_"+d.getFlowName()+"_"+d.getNodeName()),
                    e->e,(exists, replacement)-> exists)).values());
        }catch(Exception e){
            logger.error("查询缓存中数据报错"+ExceptionUtil.getExceptionTrace(e));
            return allData;
        }
        return allData;
    }



    /**
     *   把字段血缘的相关信息插入到缓存中
     * @param pageId   页面id值
     * @param columnMap 字段信息的map值
     * @param relationColumnList
     * @param queryId
     */
    @Override
    public void insertNodeColumnToCache(String pageId, String queryId, Map<String,List<ColumnRelationDB>> columnMap, List<NodeColumnRelation> relationColumnList){
        try{
            if(StringUtils.isEmpty(pageId)){
                logger.info("页面的pageId为空，无法保存缓存数据");
//                throw new Exception("页面的pageId为空，无法保存缓存数据");
                return;
            }
            if(StringUtils.isEmpty(queryId)){
                logger.info("页面的queryId为空，无法保存缓存数据");
//                throw new Exception("页面的queryId为空，无法保存缓存数据");
                return;
            }
            if(columnMap.size() == 0 && relationColumnList.size() == 0){
                logger.info("字段信息为空，不需要加入缓存");
//                throw new Exception("字段信息为空，不需要加入缓存");
                return;
            }
            synchronized (this){
                Instant current = Clock.system(ZoneId.of("Asia/Shanghai")).instant();
                // 使用分布式缓存
                ConcurrentMap<String, DataColumnCache> dataColumnInfoHashMap = hazelcastInstance.getMap(Common.DATA_COLUMN_INFO_PAGE);
                DataColumnCache dataColumnCache = dataColumnInfoHashMap.getOrDefault(pageId.toLowerCase(),null);
                // 如果是空表示是最新的，还没有插入
                if(dataColumnCache == null){
                    dataColumnCache = new DataColumnCache();
                    dataColumnCache.setPageId(pageId.toLowerCase());
                    dataColumnCache.setQueryDate(current);
                    dataColumnCache.setColumnDBMap(columnMap);
                    dataColumnCache.setQueryId(queryId);
                    dataColumnCache.setNodeColumnRelationList(relationColumnList);
                    dataColumnInfoHashMap.put(pageId.toLowerCase(),dataColumnCache);
                }else{
                    if(StringUtils.isNotEmpty(queryId) && !queryId.equalsIgnoreCase(dataColumnCache.getQueryId())){
                        logger.info("删除旧的数据");
                        if(dataColumnCache.getShowColumnPage()){
                            dataColumnInfoHashMap.put(pageId.toLowerCase()+"_"+dataColumnCache.getQueryId().toLowerCase(),dataColumnCache);
                        }
                        DataColumnCache dataColumnCacheNew = new DataColumnCache();
                        dataColumnCacheNew.setPageId(pageId.toLowerCase());
                        dataColumnCacheNew.setQueryDate(current);
                        dataColumnCacheNew.setColumnDBMap(columnMap);
                        dataColumnCacheNew.setQueryId(queryId);
                        dataColumnCacheNew.setNodeColumnRelationList(relationColumnList);
                        dataColumnInfoHashMap.put(pageId.toLowerCase(),dataColumnCacheNew);
                    }else{
                        Map<String,List<ColumnRelationDB>> oldMap = dataColumnCache.getColumnDBMap();
                        oldMap.putAll(columnMap);
                        dataColumnCache.setColumnDBMap(oldMap);
                        List<NodeColumnRelation> oldRelationList = dataColumnCache.getNodeColumnRelationList();
                        oldRelationList.addAll(relationColumnList);
                        oldRelationList = new ArrayList<>(oldRelationList.stream().filter(d -> StringUtils.isNotEmpty(d.getSourceColumnName())
                                &&StringUtils.isNotEmpty(d.getTargetColumnName()))
                                .collect(toMap(d ->(d.getId()+d.getSourceColumnName()+d.getTargetColumnName()), e->e,(exists, replacement)-> exists)).values());
                        dataColumnCache.setNodeColumnRelationList(oldRelationList);
                        dataColumnCache.setQueryDate(current);
                        dataColumnCache.setQueryId(queryId);
                        dataColumnInfoHashMap.put(pageId.toLowerCase(),dataColumnCache);
                    }
                }
            }
        }catch (Exception e){
            logger.error("保存缓存数据报错"+ExceptionUtil.getExceptionTrace(e));
        }
    }


    /**
     * 在缓存中存储 查询指点节点下所有的字段信息
     * @param node
     * @param fieldColumnMap   从阿里平台获取
     * @return
     * @throws Exception
     */
    @Override
    public List<QueryColumnTable> getAllColumnByIdsToCache(DataBloodlineNode.BloodNode node, Map<String,List<FieldColumn>> fieldColumnMap) throws Exception{
        ConcurrentMap<String, DataColumnCache> dataColumnInfoHashMap = hazelcastInstance.getMap(Common.DATA_COLUMN_INFO_PAGE);
        // 这个是存储正在查询odps任务的节点信息
        ConcurrentMap<String, QueryBloodlineRelationInfo> concurrentHashMap = hazelcastInstance.getMap(Common.QUERY_BLOODLINE_RELATIONINFO);
        String key = node.getTableNameEn()+"|"+node.getTableId()+"|"+node.getTargetCode();
        String nodeId = node.getId();
        String pageId = node.getPageId().toLowerCase();
        List<QueryColumnTable> queryColumnTable = new ArrayList<>();
        DataColumnCache dataColumnCache = dataColumnInfoHashMap.getOrDefault(pageId.toLowerCase(),null);
        if(dataColumnCache == null){
            throw new Exception("缓存中已经不存在该节点的所有字段信息，请重新查询该表的血缘信息");
        }
        dataColumnCache.setShowColumnPage(true);
        dataColumnCache.setSaveTime(720);
        dataColumnInfoHashMap.put(pageId.toLowerCase(),dataColumnCache);
        Map<String, List<ColumnRelationDB>>  columnDBMap  = dataColumnCache.getColumnDBMap();
        // 如果大数据平台的字段信息存在，则需要匹配
        // 页面上所有的nodeId都是小写
        List<ColumnRelationDB> oneNodeColumn = columnDBMap.get(nodeId.toUpperCase());
        if(fieldColumnMap.getOrDefault(key.toUpperCase(),null) == null){

        }else{
            if(concurrentHashMap.getOrDefault(key,null) == null){
                List<String> fieldColumnList = fieldColumnMap.get(key.toUpperCase()).stream().map(o->o.getFieldName().toLowerCase()).distinct().collect(toList());
                // 和从odps查询到得所有字段做匹配，只获取两个列表中的交集
                oneNodeColumn = oneNodeColumn.stream().filter(d -> fieldColumnList.contains(d.getColumnName().toLowerCase())).collect(Collectors.toList());
            }else{
                logger.error("该节点正在从大数据平台中查询字段信息，查询任务运行结束之后才能查询该字段血缘信息");
                throw new Exception("该节点正在从大数据平台中查询字段信息，查询任务运行结束之后才能查询该字段血缘信息");
            }
        }
        for(ColumnRelationDB columnRelationDB:oneNodeColumn){
            QueryColumnTable oneQueryColumnTable = new QueryColumnTable();
            oneQueryColumnTable.setNodeId(nodeId);
            oneQueryColumnTable.setTargetColumnName(columnRelationDB.getColumnName());
            oneQueryColumnTable.setTargetColumnChiName(columnRelationDB.getColumnChiName());
            if(StringUtils.isNotEmpty(columnRelationDB.getColumnChiName())){
                oneQueryColumnTable.setShowColumnName(columnRelationDB.getColumnName() + " -> "+columnRelationDB.getColumnChiName());
            }else{
                oneQueryColumnTable.setShowColumnName(columnRelationDB.getColumnName() );
            }
            queryColumnTable.add(oneQueryColumnTable);
        }
        logger.info("返回的结果为："+JSONObject.toJSONString(queryColumnTable));
        return queryColumnTable;
    }


    /**
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
    @Override
    public List<String> getColumnNameListByCache(String nodeSource, String nodeTarget, String columnName, String queryType,
                                                 String flag, List<FieldColumn> fieldColumnList, String pageKey) {
        ConcurrentMap<String, DataColumnCache> dataColumnInfoHashMap = hazelcastInstance.getMap(Common.DATA_COLUMN_INFO_PAGE);

        DataColumnCache dataColumnCache = dataColumnInfoHashMap.getOrDefault(pageKey,null);
        List<String> lists = new ArrayList<>();
        try{
            if(dataColumnCache != null){
                dataColumnCache.setShowColumnPage(true);
                dataColumnCache.setSaveTime(720);
                dataColumnInfoHashMap.put(pageKey,dataColumnCache);
                List<NodeColumnRelation> nodeColumnRelationList = dataColumnCache.getNodeColumnRelationList();
                if(flag.equals("1")){
                    List<String> fieldColumnNameList = fieldColumnList.stream().map(o->o.getFieldName().toLowerCase()).distinct().collect(toList());
                    if(queryType.equalsIgnoreCase(Constant.LEFT)){
                        nodeColumnRelationList = nodeColumnRelationList.stream().filter( d -> fieldColumnNameList.contains(d.getSourceColumnName().toLowerCase())).collect(Collectors.toList());
                    }else{
                        nodeColumnRelationList = nodeColumnRelationList.stream().filter( d -> fieldColumnNameList.contains(d.getTargetColumnName().toLowerCase())).collect(Collectors.toList());
                    }
                }
                for(NodeColumnRelation nodeColumnRelation:nodeColumnRelationList){
                    if(nodeColumnRelation.getSourceNodeId().equalsIgnoreCase(nodeSource)
                            && nodeColumnRelation.getTargetNodeId().equalsIgnoreCase(nodeTarget)){
                        if(queryType.equalsIgnoreCase(Constant.LEFT)){
                            if(nodeColumnRelation.getTargetColumnName().equalsIgnoreCase(columnName)){
                                lists.add(nodeColumnRelation.getSourceColumnName());
                            }
                        }else{
                            if(nodeColumnRelation.getSourceColumnName().equalsIgnoreCase(columnName)){
                                lists.add(nodeColumnRelation.getTargetColumnName());
                            }
                        }
                    }
                }
            }else{
                throw new Exception("缓存中不存在该值的字段关系，请重新查询");
            }
        }catch (Exception e){
           logger.error("查询字段血缘关系报错"+ExceptionUtil.getExceptionTrace(e));
        }
        return lists;
    }

    /**
     *   获取数据加工中字段血缘的缓存信息，方便查询时快速获取
     */
    @Override
    public void getDataProcessColumnCache() {
        logger.info("开始将数据库表 m_node_in_out_column 中解析的字段血缘信息写入到缓存中");
        List<ColumnLineDb>  list = lineageColumnParsingDao.getAllColumnDb();
        ConcurrentMap<String,  List<ColumnLineDb>> lineageColumnInfoHashMap = hazelcastInstance.getMap(Common.LINEAGE_COLUMN_INFO);
        if(list != null && !list.isEmpty()){
            // 开始删除缓存中的数据
            lineageColumnInfoHashMap.remove(Constant.DATAPROCESS);
            lineageColumnInfoHashMap.put(Constant.DATAPROCESS,list);
            logger.info("数据加工中字段血缘的数据量为"+list.size());
        }else{
            lineageColumnInfoHashMap.put(Constant.DATAPROCESS,new ArrayList<>());
            logger.info("数据库表 m_node_in_out_column 中没有数据，请确定程序是否报错/或者是否在数据仓库中配置本地仓");
            logger.error("数据库表 m_node_in_out_column 中没有数据，请确定程序是否报错/或者是否在数据仓库中配置本地仓");
        }
    }

    /**
     *  从应用血缘中获取对应的数据加工血缘里面的同步任务的数据
     * @param queryTableName
     * @return
     */
    @Override
    public List<RelationshipNode> getProcessNodeByChild(String queryTableName) {
        List<RelationshipNode> allData = new ArrayList<>();
        ConcurrentMap<String,DataProcessBloodCache> dataProcessBloodHashMap = hazelcastInstance.getMap(Common.DATA_PROCESS_BLOOD);
        DataProcessBloodCache dataProcessBloodCache = dataProcessBloodHashMap.get(Constant.DATAPROCESS);
        if(dataProcessBloodCache == null){
            return allData;
        }
        Map<String, List<RelationshipNode>> childKeyMap = dataProcessBloodCache.getChildKeyData();
        if(queryTableName.contains(".")){
            for(String key :childKeyMap.keySet()){
                if(key.equalsIgnoreCase(queryTableName)){
                    allData.addAll(childKeyMap.getOrDefault(key,new ArrayList<>()));
                    break;
                }
            }
        }else{
            for(String key :childKeyMap.keySet()){
                String newKey = key.split("\\.").length>1?key.split("\\.")[key.split("\\.").length -1]:key.split("\\.")[0];
                if(newKey.equalsIgnoreCase(queryTableName)){
                    // 之后判断这个关系的类型 是否为 同步任务 即 23
                    List<RelationshipNode> relationshipNodes = childKeyMap.getOrDefault(key,new ArrayList<>());
                    //                        if("23".equals(relationshipNode.getDataType())){
                    //
                    for(RelationshipNode relationshipNode:relationshipNodes){
                        if(relationshipNode.getDataType() == null ||
                             "23".equals(relationshipNode.getDataType())){
                            allData.add(relationshipNode);
                            break;
                        }
                    }
//                    allData.addAll(relationshipNodes);
                }
            }
        }
        // 这个有一个需要排序的地方，如果里面包含了2个数据，一个是表名相同一个是有同步任务的表，则有同步任务的表放在第一位
        return allData;
    }


    @Override
    public void getAllTableClassifyCache() {
        try{
            logger.info("开始获取所有标准表的分类信息");
            List<TableClassify> tableClassifyList = dataBloodlineDao.queryAllTableClassify();
            Map<String,List<TableClassify>> map = tableClassifyList.stream().collect(Collectors.groupingBy(
                    d -> d.getTableNameEn().toUpperCase()));
            ConcurrentMap<String,List<TableClassify>> tableClassifyHashMap = hazelcastInstance.getMap(Common.TABLE_CLASSIFY_MAP);
            tableClassifyHashMap.clear();
            tableClassifyHashMap.putAll(map);
        }catch (Exception e){
            logger.error("获取所有的分类信息报错："+ExceptionUtil.getExceptionTrace(e));
        }finally {
            logger.info("获取所有标准表的分类信息定时任务结束");
        }

    }

    @Override
    public String getTableClassifyCache(String tableName, String tableId) {
        try{
            if(StringUtils.isBlank(tableName)){
                return "";
            }
            // 如果存在项目名 则需要去除掉血缘信息
            ConcurrentMap<String,List<TableClassify>> tableClassifyHashMap = hazelcastInstance.getMap(Common.TABLE_CLASSIFY_MAP);
            List<TableClassify> queryData = tableClassifyHashMap.getOrDefault(
                    tableName.contains(".")?tableName.split("\\.")[1].toUpperCase():tableName.toUpperCase(),
                    null);
            if(queryData == null || queryData.size() <1){
                return "";
            }
            if(queryData.size() == 1){
                return queryData.get(0).getOrganizationClassifyName();
            }
            if(StringUtils.isBlank(tableId)){
                return queryData.get(0).getOrganizationClassifyName();
            }
            Optional<TableClassify> data = queryData.stream().filter(d -> StringUtils.equalsIgnoreCase(d.getTableId(),tableId)).findFirst();

            if(data.isPresent()){
                return data.get().getOrganizationClassifyName();
            }else{
                return queryData.get(0).getOrganizationClassifyName();
            }
        }catch (Exception e){
            logger.error("获取分类信息报错"+ExceptionUtil.getExceptionTrace(e));
            return "";
        }
    }


    @Override
    public List<ApplicationSystem> getApplicationSystemCache(String tableName) {
        if(StringUtils.isBlank(tableName)){
            return null;
        }
        ConcurrentMap<String,List<ApplicationSystem>> applicationMap = hazelcastInstance.getMap(Common.APPLICATION_BLOOD);
        return applicationMap.getOrDefault(tableName.toUpperCase(),null);
    }

    @Override
    public ConcurrentMap<String, List<ApplicationSystem>> getAllApplicationSystemCache() {
        return hazelcastInstance.getMap(Common.APPLICATION_BLOOD);
    }

    @Override
    public  DataProcessBloodCache getAllDataProcessBloodCache() {
        ConcurrentMap<String,DataProcessBloodCache> hazelcastInstanceMap = hazelcastInstance.getMap(Common.DATA_PROCESS_BLOOD);
        return hazelcastInstanceMap.getOrDefault(Constant.DATAPROCESS,new DataProcessBloodCache());
    }

    @Override
    public DataProcessBloodCache getAllAccessBloodCache() {
        ConcurrentMap<String,DataProcessBloodCache> hazelcastInstanceMap = hazelcastInstance.getMap(Common.DATA_PROCESS_BLOOD);
        return hazelcastInstanceMap.getOrDefault(Constant.ACCESS,new DataProcessBloodCache());
    }

    @Override
    public List<ColumnLineDb> getLineageColumnInfoByType(String type) {
        ConcurrentMap<String,  List<ColumnLineDb>> lineageColumnInfoHashMap = hazelcastInstance.getMap(Common.LINEAGE_COLUMN_INFO);
        return lineageColumnInfoHashMap.get(type);
    }

    @Override
    public void getSynlteObjectId() {
        try{
            logger.info("开始获取tableId和objectId的关系");
            List<TableClassify> list = fieldBloodlineDao.getAllTableIdObjectId();
            if(list != null && !list.isEmpty()){
                synlteObjectIdMap.clear();
                list.stream().filter(d -> StringUtils.isNotBlank(d.getObjectId())
                        && StringUtils.isNotBlank(d.getTableId())).forEach( d-> {
                    synlteObjectIdMap.put(d.getTableId().toLowerCase(),d.getObjectId());
                });
                logger.info("缓存中存储的tableId和objectId的关系对有{}条", synlteObjectIdMap.size());
            }else{
                logger.info("数据库中表synlte.object的数据量为0");
            }
        }catch (Exception e){
            logger.error(ExceptionUtil.getExceptionTrace(e));
        }

    }

    @Override
    public void getObjectStoreInfo() {
        try{
            logger.info("开始获取synlte.object_store_info表中的数据");
            List<ObjectStoreColumn> list = fieldBloodlineDao.getAllObjectStoreColumn();
            if(list != null && !list.isEmpty()) {
                storeInfoMap.clear();
                Map<String,List<ObjectStoreColumn>> map = list.stream().filter(d ->
                        StringUtils.isNotBlank(d.getTableName())&&
                        d.getImportFlag() == 1 && StringUtils.isNotBlank(d.getProjectName())).collect(
                        Collectors.groupingBy(d ->d.getTableName().toLowerCase())
                );
                storeInfoMap.putAll(map);
                logger.info("synlte.object_store_info表的数据量为"+map.size());
            }
            logger.info("获取synlte.object_store_info表中的数据结束");
        }catch (Exception e){
            logger.error(ExceptionUtil.getExceptionTrace(e));
        }
    }

    @Override
    public RelationLinksCache getAllRelationLinks(DataBloodlineQueryParams dataBloodlineQueryParams){

        return null;
    }

    @Override
    public RelationLinksCache getAllRelationLinks(){
        ConcurrentMap<String, Object> hazelcastInstanceMap = hazelcastInstance.getMap(Common.KEY_RELATION_LINK);
        return (RelationLinksCache) hazelcastInstanceMap.getOrDefault(Common.KEY_RELATION_LINK, new RelationLinksCache());
    }


}
