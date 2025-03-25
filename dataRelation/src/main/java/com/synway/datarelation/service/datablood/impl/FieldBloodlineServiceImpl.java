package com.synway.datarelation.service.datablood.impl;

import com.alibaba.fastjson.JSONObject;
import com.hazelcast.core.HazelcastInstance;
import com.synway.datarelation.constant.Common;
import com.synway.datarelation.dao.datablood.FieldBloodlineDao;
import com.synway.datarelation.dao.datablood.LineageColumnParsingDao;
import com.synway.datarelation.constant.Constant;
import com.synway.datarelation.pojo.common.DataColumnCache;
import com.synway.datarelation.pojo.common.DataProcessBloodCache;
import com.synway.datarelation.pojo.databloodline.*;
import com.synway.datarelation.pojo.dataresource.FieldInfo;
import com.synway.datarelation.pojo.lineage.ColumnLineDb;
import com.synway.datarelation.service.runnable.AdsColumnQueryRunnable;
import com.synway.datarelation.service.runnable.OdpsColumnQueryRunnable;
import com.synway.datarelation.service.runnable.HuaWeiColumnQueryRunnable;
import com.synway.datarelation.util.ThreadPool;
import com.synway.datarelation.service.datablood.CacheManageDataBloodlineService;
import com.synway.datarelation.service.datablood.DataBloodlineLinkService;
import com.synway.datarelation.service.datablood.FieldBloodlineService;
import com.synway.datarelation.util.AdsClient;
import com.synway.common.exception.ExceptionUtil;
import com.synway.datarelation.util.RestTemplateHandle;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * @author wangdongwei
 */
@Service
public class FieldBloodlineServiceImpl implements FieldBloodlineService {
    private Logger logger = LoggerFactory.getLogger(FieldBloodlineServiceImpl.class);
    @Autowired
    ThreadPool threadPool;
    @Autowired
    Environment env;
    @Autowired
    AdsClient adsClient;
    @Autowired private FieldBloodlineDao fieldBloodlineDao;
    @Autowired
    private CacheManageDataBloodlineService cacheManageDataBloodlineServiceImpl;
    @Autowired
    private RestTemplateHandle restTemplateHandle;
    @Autowired
    private LineageColumnParsingDao lineageColumnParsingDao;
    @Autowired
    private DataBloodlineServiceImpl dataBloodlineServiceImpl;
    @Autowired
    DataBloodlineLinkService dataBloodlineLinkServiceImpl;
    private final HazelcastInstance hazelcastInstance;
    @Autowired
    public FieldBloodlineServiceImpl(HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
    }

    @Override
    public String submitTaskQueryColumn(QueryBloodlineRelationInfo queryData) {
        logger.info("开始从数据库中查询表字段信息");
        if(StringUtils.isEmpty(queryData.getTargetProjectName())
                ||StringUtils.isEmpty(queryData.getTargetTableName())){
            logger.info("表名为空，不能查询字段信息");
            return "表名为空，不能查询字段信息";
        }
        String mapKey = queryData.getTargetProjectName()+"."+queryData.getTargetTableName()+
                "|"+queryData.getTargetEngName()+"|"+queryData.getTargetSysId();
        ConcurrentMap<String, QueryBloodlineRelationInfo> concurrentHashMap = hazelcastInstance.getMap(Common.QUERY_BLOODLINE_RELATIONINFO);
        if(concurrentHashMap.containsKey(mapKey.toUpperCase())){
            logger.error(mapKey.toUpperCase()+"已经在map中存在，不需要重新查询");
            return "";
        }else{
            concurrentHashMap.put(mapKey.toUpperCase() , queryData);
        }
        logger.info("线程池中存在的查询任务信息为："+JSONObject.toJSONString(concurrentHashMap.keySet()));
        QueryDataBloodlineTable queryTable = new QueryDataBloodlineTable();
        queryTable.setDataType(queryData.getTargetDBEngName());
        queryTable.setTableNameEn((queryData.getTargetProjectName()+"."+queryData.getTargetTableName()).toUpperCase());
        queryTable.setTableId(queryData.getTargetEngName());
        queryTable.setTargetCode(queryData.getTargetSysId());
        logger.info("查询参数为："+JSONObject.toJSONString(queryTable));
        if(queryData.getTargetDBEngName().equalsIgnoreCase("odps")){
            threadPool.submit(new OdpsColumnQueryRunnable(queryTable,fieldBloodlineDao,concurrentHashMap));
            logger.info("odps查询结束");
            return "odps查询结束";
        }else if(queryData.getTargetDBEngName().toLowerCase().contains("ads")){
            logger.info("ads查询字段信息");
            threadPool.submit(new AdsColumnQueryRunnable(queryTable,adsClient,fieldBloodlineDao,concurrentHashMap));
            return "ads的查询字段信息结束";
        }else if("hive".equalsIgnoreCase(queryData.getTargetDBEngName())){
            logger.info("hive查询字段信息");
            threadPool.submit(new HuaWeiColumnQueryRunnable(queryTable,fieldBloodlineDao,concurrentHashMap,"hive",restTemplateHandle));

            return "hive的查询字段信息结束";
        }else if("hbase".equalsIgnoreCase(queryData.getTargetDBEngName())){
            logger.info("hbase查询字段信息");
            threadPool.submit(new HuaWeiColumnQueryRunnable(queryTable,fieldBloodlineDao,concurrentHashMap,"hbase",restTemplateHandle));
            return "hbase的查询字段信息结束";
        }else{
            logger.info("从标准化中获取到的节点类型不属于ODPS/ADS,请配置为正确的参数");
            return "从标准化中获取到的节点类型不属于ODPS/ADS,请配置为正确的参数";
        }
    }

    /**
     *  查询字段血缘有很多种情况，如果是数据来源的节点，则直接查询表COLUMN_BLOODLINE_RELATION，
     *  如果存在 数据库类型/项目名/表名，则先判断全局map中是否还有这个节点任务，有的话表示这个节点正在从ODPS/ADS
     *  查询表的结构信息，不再显示字段血缘，跳出报错信息，说正在查询字段信息，如果已经查询结束，则从表COLUMN_BLOODLINE
     *  中查询字段信息，如果不存在，则直接查询 表COLUMN_BLOODLINE_RELATION
     *  这个id是 节点的id信息 id里面的信息包括 targetId|targetCode|targetTableName
     * @param node
     * @return  返回的信息是字段的展示信息
     */
    @Override
    public List<QueryColumnTable> getAllColumnByIdsService(DataBloodlineNode.BloodNode node) throws Exception{
        // 如果是空的表示不会去大数据平台查询
        List<FieldColumn> fieldColumnList = fieldBloodlineDao.getAllColumnAli();
        Map<String,List<FieldColumn>> fieldColumnMap = fieldColumnList.stream().collect(Collectors.groupingBy(FieldColumn::getNodeId));

        List<QueryColumnTable> queryColumnTable = cacheManageDataBloodlineServiceImpl.getAllColumnByIdsToCache(node,fieldColumnMap);

        logger.info("返回的结果为："+JSONObject.toJSONString(queryColumnTable));
        return queryColumnTable;
    }

    /**
     * 根据指定的字段名获取字段的血缘关系
     * @param oneColumn
     * @return
     */
    @Override
    public ColumnBloodlineNode getFieldRelationNode(QueryColumnTable oneColumn) throws Exception{
        // 点击节点的id信息
        String mainNodeId = oneColumn.getNodeId();
        // 因为节点的连接关系存储的是字符串，所以需要转一次
        List<QueryColumnTable.Edges> edgesList = JSONObject.parseArray(oneColumn.getEdgeListStr(),QueryColumnTable.Edges.class);
        List<ColumnBloodlineNode.ColumnNode> nodes = new ArrayList<>();
        List<ColumnBloodlineNode.ColumnEdges> edges = new ArrayList<>();
        //  查询出 标准化这边的 所有字段信息

//        List<ColumnRelationDB> columnDbList = fieldBloodlineDao.getAllColumnRelationDBByType(oneColumn.getBloodNode().getDataType());
        // 获取指定pageId下的所有字段信息
        Map<String,List<ColumnRelationDB>> columnDbMap = new HashMap<>();
        String pageKey = "";
        ConcurrentMap<String, DataColumnCache> dataColumnInfoHashMap = hazelcastInstance.getMap(Common.DATA_COLUMN_INFO_PAGE);

        DataColumnCache dataColumnCache = dataColumnInfoHashMap.getOrDefault(oneColumn.getBloodNode().getPageId().toLowerCase(),null);
        DataColumnCache dataColumnOldCache = dataColumnInfoHashMap.getOrDefault((oneColumn.getBloodNode().getPageId()+"_"+oneColumn.getBloodNode().getQueryId()).toLowerCase(),null);
        Instant current = Clock.system(ZoneId.of("Asia/Shanghai")).instant();
        if(dataColumnCache == null && dataColumnOldCache == null){
            throw new Exception("缓存中字段信息已经清除，请关闭该页面并重新查询节点的血缘关系");
        }else if(dataColumnCache != null && dataColumnCache.getQueryId().equalsIgnoreCase(oneColumn.getBloodNode().getQueryId())){
            columnDbMap = dataColumnCache.getColumnDBMap();
            dataColumnCache.setQueryDate(current);
            pageKey = oneColumn.getBloodNode().getPageId().toLowerCase();
            dataColumnInfoHashMap.put(pageKey,dataColumnCache);
        }else if(dataColumnOldCache != null && dataColumnOldCache.getQueryId().equalsIgnoreCase(oneColumn.getBloodNode().getQueryId())){
            columnDbMap = dataColumnOldCache.getColumnDBMap();
            dataColumnOldCache.setQueryDate(current);
            pageKey = (oneColumn.getBloodNode().getPageId()+"_"+oneColumn.getBloodNode().getQueryId()).toLowerCase();
            dataColumnInfoHashMap.put(pageKey,dataColumnOldCache);
        }else{
            throw new Exception("缓存中字段信息已经清除，请关闭该页面并重新查询节点的血缘关系");
        }
//        Map<String,List<ColumnRelationDB>> columnDbMap = columnDbList.stream().collect(Collectors.groupingBy(ColumnRelationDB::getNodeId));
        // 查询出从大数据平台表的字段信息  根据字段之间的关系 适当增删字段
        // 注意 这个里面的nodeid 没有 数据仓库类型以及taskUuid
        List<FieldColumn> fieldColumnList = fieldBloodlineDao.getAllColumnAli();
        Map<String,List<FieldColumn>> fieldColumnMap = fieldColumnList.stream().collect(Collectors.groupingBy( d -> d.getNodeId().toLowerCase()));
        Map<String,Integer> idCountMap = new HashMap<>();

        for(QueryColumnTable.Edges edge:edgesList){
            if(edge.getSource().equalsIgnoreCase(mainNodeId)||edge.getTarget().equalsIgnoreCase(mainNodeId)) {
                // 则查询 sourceId   如果在map中为0，则表示没有查询该节点的信息
                parsingFieldRelation(edgesList, mainNodeId, oneColumn.getTargetColumnName(), Constant.RIGHT,
                        nodes, edges, idCountMap, columnDbMap, fieldColumnMap,pageKey);
                // 开始从右往左查询，把这个节点当成右节点
                parsingFieldRelation(edgesList, mainNodeId, oneColumn.getTargetColumnName(), Constant.LEFT,
                        nodes, edges, idCountMap, columnDbMap, fieldColumnMap,pageKey);
            }
        }
        ColumnBloodlineNode columnBloodlineNode = new ColumnBloodlineNode();
        columnBloodlineNode.setEdges(edges);
        columnBloodlineNode.setNodes(nodes);
        logger.info("字段血缘查询到的数据为："+JSONObject.toJSONString(columnBloodlineNode));
        return columnBloodlineNode;
    }

    /**
     *  获取指定表的所有字段信息
     * @param clickTableName  点击的查询表名
     * @return
     */
    @Override
    public List<QueryColumnTable> getAllProcessColumnById(String dbType , String clickTableName) {
        List<QueryColumnTable> resultList = new ArrayList<>();
        // 1: 从数据仓库中获取
        try{
            List<FieldInfo> tableFields = restTemplateHandle.getLocalTableStructinfo(dbType,clickTableName);
            for(FieldInfo tableField:tableFields){
                // 1.8 之后该字段中不再有 是否为分区字段的标识
                if(!tableField.getIsPartition()){
                    String columnName = tableField.getFieldName();
                    String columnNameCh = tableField.getComments();
                    QueryColumnTable queryColumnTable = new QueryColumnTable();
                    queryColumnTable.setTargetColumnName(columnName);
                    queryColumnTable.setTargetColumnChiName(columnNameCh);
                    queryColumnTable.setShowColumnName(StringUtils.isNotBlank(columnNameCh)?columnName +" -> "+ columnNameCh:columnName);
                    resultList.add(queryColumnTable);
                }
            }
        }catch (Exception e){
            logger.error("获取字段信息报错"+ ExceptionUtil.getExceptionTrace(e));
        }
        if(resultList.size() > 0){
            return resultList;
        }
        // 2：先从数据库中表 m_node_in_out_column 查询 output_column_name 对应的字段信息
        List<String> columnResult = lineageColumnParsingDao.getDbColumnByTable(clickTableName);
        for(String columnName:columnResult){
            QueryColumnTable queryColumnTable = new QueryColumnTable();
            queryColumnTable.setShowColumnName(columnName);
            queryColumnTable.setTargetColumnName(columnName);
            queryColumnTable.setDataType(Constant.DATAPROCESS);
            resultList.add(queryColumnTable);
        }
        // 3： 如果还没有 则从 m_node_in_out_column 查询 input_column_name 对应的字段信息
        if(resultList.size() == 0){
            List<ColumnLineDb> list = cacheManageDataBloodlineServiceImpl.getLineageColumnInfoByType(Constant.DATAPROCESS);
            list.stream().filter(d -> {
                if(StringUtils.isNotBlank(d.getInputTableName()) &&
                        d.getInputTableName().equalsIgnoreCase(clickTableName)){
                    return true;
                }else if(StringUtils.isEmpty(d.getInputColumnName())){
                    return false;
                }else{
                    return false;
                }
            }).map(ColumnLineDb::getInputColumnName).distinct().forEach(column ->{
                QueryColumnTable queryColumnTable = new QueryColumnTable();
                queryColumnTable.setShowColumnName(column);
                queryColumnTable.setTargetColumnName(column);
                queryColumnTable.setDataType(Constant.DATAPROCESS);
                resultList.add(queryColumnTable);
            });
        }

        return resultList;
    }

    /**
     * 根据指定的字段名获取字段的血缘关系
     * @param edgeIds  页面上节点之间的边关系
     * @param nodeIds   页面上数据加工类型的节点
     * @param queryColumnTable   点击的节点信息
     * @param clickTableName  点击的表信息
     * @param columnName    点击的字段信息
     * @return
     */
    @Override
    public ColumnBloodlineNode getDataProcessColumnLink(List<QueryColumnTable.Edges> edgeIds,
                                                        List<String> nodeIds,
                                                        QueryColumnTable queryColumnTable,
                                                        String clickTableName,
                                                        String columnName) {

        ColumnBloodlineNode columnBloodlineNode = new ColumnBloodlineNode();
        List<ColumnBloodlineNode.ColumnNode> nodes = new ArrayList<>();
        List<ColumnBloodlineNode.ColumnEdges> edges = new ArrayList<>();
        logger.info("开始查询数据加工字段血缘信息");
        // 开始查询主节点的字段信息
        ColumnBloodlineNode.ColumnNode columnNodeMain= new ColumnBloodlineNode.ColumnNode();
        columnNodeMain.setId(queryColumnTable.getBloodNode().getId()+"||"+columnName);
        columnNodeMain.setColumnName(columnName);
        if(StringUtils.isNotBlank(queryColumnTable.getTargetColumnChiName())){
            columnNodeMain.setColumnNameCh(queryColumnTable.getTargetColumnChiName());
        }
        // 这个是表血缘上的节点名称
        columnNodeMain.setNodeName(queryColumnTable.getBloodNode().getNodeName());
        columnNodeMain.setTableNameEn(clickTableName);
        columnNodeMain.setTableNameCh(queryColumnTable.getBloodNode().getTableNameCh());
        columnNodeMain.setDataType(Constant.MAIN);
        if(StringUtils.isNotBlank(queryColumnTable.getBloodNode().getTableId())){
            columnNodeMain.setTableId(queryColumnTable.getBloodNode().getTableId());
            columnNodeMain.setTableIdCh(queryColumnTable.getBloodNode().getTableIdCh());
        }
        nodes.add(columnNodeMain);
        //字段血缘的map信息 ，先查询出来，因为从缓存中获取这些数据需要0.3秒，影响性能
        List<ColumnLineDb> dataColumnList = cacheManageDataBloodlineServiceImpl.getLineageColumnInfoByType(Constant.DATAPROCESS);
        // 以下是表血缘信息
        DataProcessBloodCache dataProcessBloodCache = cacheManageDataBloodlineServiceImpl.getAllDataProcessBloodCache();
        for(QueryColumnTable.Edges edges1 :edgeIds){
            // 这个表示 点击的表是输入表
            if(edges1.getSource().equalsIgnoreCase(queryColumnTable.getBloodNode().getId())){
                // 向右边查询数据加工血缘的字段信息
                parsingProcessColumnRelation(edgeIds,queryColumnTable.getBloodNode().getId(),
                        columnNodeMain.getId(),
                        columnName,nodes,edges,nodeIds,Constant.RIGHT,
                        dataColumnList,dataProcessBloodCache);
            }else if(edges1.getTarget().equalsIgnoreCase(queryColumnTable.getBloodNode().getId())){
                parsingProcessColumnRelation(edgeIds,queryColumnTable.getBloodNode().getId()
                        ,columnNodeMain.getId(),
                        columnName,nodes,edges,nodeIds,Constant.LEFT,
                        dataColumnList,dataProcessBloodCache);
            }
        }
        columnBloodlineNode.setNodes(nodes);
        columnBloodlineNode.setEdges(edges);
        return columnBloodlineNode;
    }

    /**
     *  获取单个节点的时候
     * @param list  从数据处理获取到的数据
     * @param queryData  页面查询的参数
     */
    @Override
    public void insertColumnRelationToCache(List<QueryBloodlineRelationInfo> list, QueryDataBloodlineTable queryData) {
        // 需要将从数据处理获取到的节点血缘关系获取一遍，生成字段血缘的相关规则，按照之前的内容插入到缓存中
        // 单个节点的所有字段信息 把字段存储在map中，之后统一插入到数据库中 key为nodeid
        try{
            Map<String , List<ColumnRelationDB>> columnMap = new HashMap<>();
            // 字段的对应关系
            Map<String , List<NodeColumnRelation>> relationColumnMap = new HashMap<>();
            dataBloodlineServiceImpl.parsingStandardReturnJson(list,null,Constant.LEFT,null
                    ,columnMap,relationColumnMap,true,queryData.getPageId(),queryData.getQueryId(),queryData.getTableNameEn());
            dataBloodlineLinkServiceImpl.insertNodeColumnAccessStandard(queryData.getPageId(),queryData.getQueryId(),columnMap,relationColumnMap);
        }catch (Exception e){
            logger.error(ExceptionUtil.getExceptionTrace(e));
        }

    }

    /**
     *  向右迭代获取字段血缘的相关信息
     * @param edgesList  页面传递过来的边的信息
     * @param mainBoodNodeId  数据血缘节点的节点id
     * @param coumnNodeId  字段节点的节点ID
     * @param columnName 字段节点的字段名称
     * @param nodes   存储最后生成的字段节点信息
     * @param edges   存储最后生成的边信息
     * @param nodeIds 用于判断这个边对应的节点是否是页面展示的节点
     * @param type  left/right  向左查询/向右查询
     * @param dataColumnList  字段血缘
     * @param dataProcessBloodCache  表血缘
     */
    private void parsingProcessColumnRelation(List<QueryColumnTable.Edges> edgesList,
                                                   String mainBoodNodeId,
                                                   String coumnNodeId,
                                                   String columnName,
                                                   List<ColumnBloodlineNode.ColumnNode> nodes,
                                                   List<ColumnBloodlineNode.ColumnEdges> edges,
                                                   List<String> nodeIds,
                                                   String type,
                                                   List<ColumnLineDb> dataColumnList,
                                                   DataProcessBloodCache dataProcessBloodCache){
        for(QueryColumnTable.Edges edges1 :edgesList){
            if(Constant.RIGHT.equalsIgnoreCase(type)){
                if(edges1.getSource().equalsIgnoreCase(mainBoodNodeId) && nodeIds.contains(edges1.getTarget())){
                    // 说明这个边的右节点是 数据血缘里面的下一个节点，根据id值从缓存中 获取对应的表名，然后再从 columnLineDbList
                    // 中获取对应的输出字段信息
                    // 1: 先获取表名
                    String leftTableName = mainBoodNodeId.replace(Constant.DATAPROCESS+"_","");
                    List<RelationshipNode> relationshipNodes = dataProcessBloodCache.getParentKeyData().getOrDefault(leftTableName,null);
                    // 理论上只会存在一个
                    if(relationshipNodes != null && relationshipNodes.size() >0){
                        for(RelationshipNode relationshipNode:relationshipNodes){
                            if(relationshipNode.getParentTN().equalsIgnoreCase(leftTableName)
                                &&relationshipNode.getChildrenTN().equalsIgnoreCase(edges1.getTarget().replace(Constant.DATAPROCESS+"_",""))
                                ){
                                String rightTableName = relationshipNode.getChildrenTN();
                                // 2：根据输入输出表名以及输入字段 获取输出字段信息
                                List<String> outPutColumnList = dataColumnList.stream().filter(d ->{
                                    if(d == null || StringUtils.isEmpty(d.getInputTableName()) || StringUtils.isEmpty(d.getOutputTableName())){
                                        return false;
                                    }else if(leftTableName.equalsIgnoreCase(d.getInputTableName())
                                        && rightTableName.equalsIgnoreCase(d.getOutputTableName())
                                          && columnName.equalsIgnoreCase(d.getInputColumnName())){
                                        return true;
                                    }else{
                                        return false;
                                    }
                                }).distinct().map(ColumnLineDb::getOutputColumnName).collect(Collectors.toList());
                                for(String outputColumnName: outPutColumnList){
                                    ColumnBloodlineNode.ColumnNode columnNode = new ColumnBloodlineNode.ColumnNode();
                                    columnNode.setId(Constant.DATAPROCESS+"_"+rightTableName+"||"+outputColumnName);
                                    if(checkIsContail(nodes,columnNode.getId())){
                                        break;
                                    }
                                    columnNode.setColumnName(outputColumnName);
                                    columnNode.setTableIdCh(StringUtils.isNotEmpty(relationshipNode.getChildrenTableId())?
                                            relationshipNode.getChildrenTableNameCh():"");
                                    columnNode.setTableId(relationshipNode.getChildrenTableId());
                                    columnNode.setTableNameCh(relationshipNode.getChildrenTableNameCh());
                                    columnNode.setTableNameEn(relationshipNode.getChildrenTN());
                                    columnNode.setNodeName(StringUtils.isNotEmpty(relationshipNode.getChildrenTableNameCh())?
                                              relationshipNode.getChildrenTableNameCh():relationshipNode.getChildrenTN());
                                    nodes.add(columnNode);
                                    String caseStr = getCaseStrByTableName(leftTableName,columnName,rightTableName,outputColumnName);
                                    ColumnBloodlineNode.ColumnEdges columnEdges =
                                            new ColumnBloodlineNode.ColumnEdges(coumnNodeId,columnNode.getId(),caseStr);
                                    edges.add(columnEdges);
                                    // 继续向右获取字段信息
                                    parsingProcessColumnRelation(edgesList,edges1.getTarget(),columnNode.getId(),outputColumnName,
                                            nodes,edges,nodeIds,Constant.RIGHT,dataColumnList,dataProcessBloodCache);
                                }
                                if(outPutColumnList.size() > 0){
                                    break;
                                }
                            }
                        }
                    }
                }
            }else{
                //  这个是从右往左查
                if(edges1.getTarget().equalsIgnoreCase(mainBoodNodeId) && nodeIds.contains(edges1.getSource())){
                    String rightTableName = mainBoodNodeId.replace(Constant.DATAPROCESS+"_","");
                    List<RelationshipNode> relationshipNodes = dataProcessBloodCache.getChildKeyData().getOrDefault(rightTableName,null);
                    // 理论上只会存在一个
                    if(relationshipNodes != null && relationshipNodes.size() >0){
                        for(RelationshipNode relationshipNode:relationshipNodes){
                            if(relationshipNode.getParentTN().equalsIgnoreCase(edges1.getSource().replace(Constant.DATAPROCESS+"_",""))
                                    &&relationshipNode.getChildrenTN().equalsIgnoreCase(rightTableName)
                                    &&(relationshipNodes.size() <= 1 || !relationshipNode.getNodeName().toLowerCase().contains("dev"))){
                                // 2：根据输入输出表名以及输出字段 获取输入字段信息
                                String leftTableName = relationshipNode.getParentTN();
                                List<String> inPutColumnList = dataColumnList.stream().filter(d ->{
                                    if(d == null || StringUtils.isEmpty(d.getInputTableName()) || StringUtils.isEmpty(d.getOutputTableName())){
                                        return false;
                                    }else if(leftTableName.equalsIgnoreCase(d.getInputTableName())
                                            && rightTableName.equalsIgnoreCase(d.getOutputTableName())
                                            && columnName.equalsIgnoreCase(d.getOutputColumnName())){
                                        return true;
                                    }else{
                                        return false;
                                    }
                                }).distinct().map(ColumnLineDb::getInputColumnName).collect(Collectors.toList());
                                for(String inputColumnName:inPutColumnList){
                                    ColumnBloodlineNode.ColumnNode columnNode = new ColumnBloodlineNode.ColumnNode();
                                    columnNode.setId(Constant.DATAPROCESS+"_"+leftTableName+"||"+inputColumnName);
                                    if(checkIsContail(nodes,columnNode.getId())){
                                        break;
                                    }
                                    columnNode.setColumnName(inputColumnName);
                                    columnNode.setTableIdCh(StringUtils.isNotEmpty(relationshipNode.getParentTableId())?
                                            relationshipNode.getParentTableNameCh():"");
                                    columnNode.setTableId(relationshipNode.getParentTableId());
                                    columnNode.setTableNameCh(relationshipNode.getParentTableNameCh());
                                    columnNode.setTableNameEn(relationshipNode.getParentTN());
                                    columnNode.setNodeName(StringUtils.isNotEmpty(relationshipNode.getParentTableNameCh())?
                                            relationshipNode.getParentTableNameCh():relationshipNode.getParentTN());
                                    nodes.add(columnNode);
                                    String caseStr = getCaseStrByTableName(leftTableName,inputColumnName,rightTableName,columnName);
                                    ColumnBloodlineNode.ColumnEdges columnEdges =
                                            new ColumnBloodlineNode.ColumnEdges(columnNode.getId(), coumnNodeId, caseStr);
                                    edges.add(columnEdges);
                                    // 继续向右获取字段信息
                                    parsingProcessColumnRelation(edgesList,edges1.getSource(),columnNode.getId(),inputColumnName,
                                            nodes,edges,nodeIds,Constant.LEFT,dataColumnList,dataProcessBloodCache);
                                }
                                if(inPutColumnList.size() > 0){
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     *  判断是否已经存在这个id值
     * @param nodes
     * @param id
     * @return
     */
    private Boolean checkIsContail(List<ColumnBloodlineNode.ColumnNode> nodes, String id){
        Long dataCount = nodes.stream().filter(d ->{
            return d.getId().equalsIgnoreCase(id);
        }).count();
        if(dataCount > 0 ){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 获取caseStr的值
     * @return
     */
    private String getCaseStrByTableName(String inputTableName,String inputColumnName,
                                         String outputTableName,String outputColumnName){
        String caseStr = cacheManageDataBloodlineServiceImpl.getLineageColumnInfoByType(Constant.DATAPROCESS).stream().filter(d ->{
            if(inputTableName.equalsIgnoreCase(d.getInputTableName())
                &&inputColumnName.equalsIgnoreCase(d.getInputColumnName())
                  &&outputTableName.equalsIgnoreCase(d.getOutputTableName())
                    &&outputColumnName.equalsIgnoreCase(d.getOutputColumnName())){
                return true;
            }else{
                return false;
            }
        }).map(ColumnLineDb::getCaseStr).findFirst().get();
        return  caseStr;
    }

    /**
     *  迭代获取
     * @param edgesList
     * @param mainNodeId
     * @param type
     */
    private void parsingFieldRelation(List<QueryColumnTable.Edges> edgesList,String mainNodeId,
                                      String columnName,
                                      String type,List<ColumnBloodlineNode.ColumnNode> nodeList,
                                      List<ColumnBloodlineNode.ColumnEdges> edgeList,Map<String,Integer> idCountMap,
                                      Map<String,List<ColumnRelationDB>> columnDbMap,
                                      Map<String,List<FieldColumn>> fieldColumnMap,String pageKey){
        for(QueryColumnTable.Edges edges:edgesList){
            if(type.equalsIgnoreCase(Constant.LEFT)){
                if(!edges.getTarget().equalsIgnoreCase(mainNodeId)){
                    continue;
                }
                //  mainNodeId 是目标id
                if (idCountMap.getOrDefault((mainNodeId + "|" + columnName).toUpperCase(), 0) == 0) {
                    List<ColumnRelationDB> columnRelationDBList = columnDbMap.getOrDefault(mainNodeId.toUpperCase(), null);
                    List<ColumnRelationDB> columnRelationList = columnRelationDBList.stream().filter(d -> d.getColumnName().equalsIgnoreCase(columnName)).collect(Collectors.toList());;
                    if(columnRelationList != null && columnRelationList.size() >0){
                        for(ColumnRelationDB columnRelationDB:columnRelationList){
                            ColumnBloodlineNode.ColumnNode columnNode = new ColumnBloodlineNode.ColumnNode();
                            columnNode.setId((mainNodeId + "|" + columnRelationDB.getColumnName()).toUpperCase());
                            columnNode.setColumnName(columnRelationDB.getColumnName());
                            columnNode.setTableId(columnRelationDB.getTableId());
                            columnNode.setTableNameEn(columnRelationDB.getTableName());
                            columnNode.setTableNameCh(columnRelationDB.getTableNameCh());
                            columnNode.setTableIdCh(columnRelationDB.getTableIdCh());
                            columnNode.setColumnNameCh(columnRelationDB.getColumnChiName());
                            columnNode.setNodeName(columnRelationDB.getNodeName());
                            columnNode.setSourceCode(columnRelationDB.getTableCode());
                            nodeList.add(columnNode);
                            idCountMap.put(columnNode.getId(), 1);
                        }
                    }else{
                        continue;
                    }
                }
                List<String> leftColumnNameList = null;
                //
                String keyStr = StringUtils.join(Arrays.asList(edges.getSource().split("\\|")).subList(0,3),"|");
                if (fieldColumnMap.getOrDefault(keyStr, null) == null) {
                    // 0 表示 不以从大数据平台获取的字段信息为准
                    leftColumnNameList =  cacheManageDataBloodlineServiceImpl.getColumnNameListByCache(edges.getSource(), edges.getTarget(),
                            columnName, "left", "0",null,pageKey);
//                    leftColumnNameList = fieldBloodlineDao.getTableColumnRelation(edges.getSource(), edges.getTarget(), columnName, "left", 0);
                } else {
                    // 1 表示已从大数据平台获取的字段信息为准
                    leftColumnNameList =  cacheManageDataBloodlineServiceImpl.getColumnNameListByCache(edges.getSource(), edges.getTarget(),
                            columnName, "left", "1",fieldColumnMap.get(keyStr),pageKey);
//                    leftColumnNameList = fieldBloodlineDao.getTableColumnRelation(edges.getSource(), edges.getTarget(), columnName, "left", 1);
                }
                if (leftColumnNameList != null && leftColumnNameList.size() >0) {
                    for(String leftColumnName:leftColumnNameList){
                        if (idCountMap.getOrDefault((edges.getSource() + "|" + leftColumnName).toUpperCase(), 0) == 0) {
                            List<ColumnRelationDB> childList = columnDbMap.getOrDefault(edges.getSource().toUpperCase(), null);
                            if (childList!= null && childList.size() > 0) {
                                final String columnNameChild = leftColumnName;
                                List<ColumnRelationDB> childColumnList = childList.stream().filter(d -> d.getColumnName().equalsIgnoreCase(columnNameChild)).collect(Collectors.toList());
                                if (childColumnList !=null && childColumnList.size() > 0) {
                                    for(ColumnRelationDB childColumn:childColumnList){
                                        ColumnBloodlineNode.ColumnNode childColumnNode = new ColumnBloodlineNode.ColumnNode();
                                        childColumnNode.setTableId(childColumn.getTableId());
                                        childColumnNode.setTableIdCh(childColumn.getTableIdCh());
                                        childColumnNode.setTableNameEn(childColumn.getTableName());
                                        childColumnNode.setTableNameCh(childColumn.getTableNameCh());
                                        childColumnNode.setColumnName(leftColumnName);
                                        childColumnNode.setColumnNameCh(childColumn.getColumnChiName());
                                        childColumnNode.setId((edges.getSource() + "|" + leftColumnName).toUpperCase());
                                        childColumnNode.setNodeName(childColumn.getNodeName());
                                        childColumnNode.setSourceCode(childColumn.getTableCode());
                                        nodeList.add(childColumnNode);
                                        idCountMap.put((edges.getSource() + "|" + leftColumnName).toUpperCase(), 1);
                                        edgeList.add(new ColumnBloodlineNode.ColumnEdges(childColumnNode.getId() , (mainNodeId + "|" + columnName).toUpperCase()));
                                        parsingFieldRelation(edgesList, edges.getSource(), leftColumnName, Constant.LEFT,
                                                nodeList, edgeList, idCountMap, columnDbMap, fieldColumnMap,pageKey);
                                    }
                                }
                            }
                        }
                    }

                }
            }else {
                if(!edges.getSource().equalsIgnoreCase(mainNodeId)){
                    continue;
                }
                //  mainNodeId 是来源id
                if (idCountMap.getOrDefault((mainNodeId + "|" + columnName).toUpperCase(), 0) == 0) {
                    List<ColumnRelationDB> columnRelationDBList = columnDbMap.getOrDefault(mainNodeId.toUpperCase(), null);
                    Optional<ColumnRelationDB> columnRelationDBOptional = columnRelationDBList.stream().filter(d -> d.getColumnName().equalsIgnoreCase(columnName)).findFirst();
                    if(!columnRelationDBOptional.equals(Optional.empty())){
                        ColumnRelationDB columnRelationDB = columnRelationDBOptional.get();
                        ColumnBloodlineNode.ColumnNode columnNode = new ColumnBloodlineNode.ColumnNode();
                        columnNode.setId((mainNodeId + "|" + columnRelationDB.getColumnName()).toUpperCase());
                        columnNode.setColumnName(columnRelationDB.getColumnName());
                        columnNode.setTableId(columnRelationDB.getTableId());
                        columnNode.setTableNameEn(columnRelationDB.getTableName());
                        columnNode.setTableNameCh(columnRelationDB.getTableNameCh());
                        columnNode.setTableIdCh(columnRelationDB.getTableIdCh());
                        columnNode.setColumnNameCh(columnRelationDB.getColumnChiName());
                        columnNode.setNodeName(columnRelationDB.getNodeName());
                        columnNode.setSourceCode(columnRelationDB.getTableCode());
                        nodeList.add(columnNode);
                        idCountMap.put(columnNode.getId(), 1);
                    }
                }
                List<String> rightColumnNameList = null;
                String keyStr = "";
                if(Arrays.asList(edges.getTarget().split("\\|")).size() > 3){
                    keyStr = StringUtils.join(Arrays.asList(edges.getTarget().split("\\|")).subList(0,3),"|");
                }else{
                    keyStr = StringUtils.join(Arrays.asList(edges.getTarget().split("\\|")),"|");
                }
                if (fieldColumnMap.getOrDefault(keyStr, null) == null) {
                    // 0 表示 不以从大数据平台获取的字段信息为准
                    rightColumnNameList =  cacheManageDataBloodlineServiceImpl.getColumnNameListByCache(edges.getSource(), edges.getTarget(),
                            columnName, "right", "0",fieldColumnMap.get(keyStr),pageKey);
//                    rightColumnNameList = fieldBloodlineDao.getTableColumnRelation(edges.getSource(), edges.getTarget(), columnName, "right", 0);
                } else {
                    // 1 表示已从大数据平台获取的字段信息为准
                    rightColumnNameList =  cacheManageDataBloodlineServiceImpl.getColumnNameListByCache(edges.getSource(), edges.getTarget(),
                            columnName, "right", "1",fieldColumnMap.get(keyStr),pageKey);
//                    rightColumnNameList = fieldBloodlineDao.getTableColumnRelation(edges.getSource(), edges.getTarget(), columnName, "right", 1);
                }
                if (rightColumnNameList != null && rightColumnNameList.size() >0) {
                    for(String rightColumnName:rightColumnNameList){
                        // 开始从左往右查询，把这个节点当成左节点
                        if (idCountMap.getOrDefault((edges.getTarget() + "|" + rightColumnName).toUpperCase(), 0) == 0) {
                            List<ColumnRelationDB> childList = columnDbMap.getOrDefault(edges.getTarget().toUpperCase(), null);
                            if (childList!= null && childList.size() > 0) {
                                final String columnNameChild = rightColumnName;
                                List<ColumnRelationDB> childColumnList = childList.stream().filter(d -> d.getColumnName().equalsIgnoreCase(columnNameChild)).collect(Collectors.toList());;
                                if (childColumnList!= null &&childColumnList.size() >0) {
                                    for(ColumnRelationDB childColumn :childColumnList){
                                        ColumnBloodlineNode.ColumnNode childColumnNode = new ColumnBloodlineNode.ColumnNode();
                                        childColumnNode.setTableId(childColumn.getTableId());
                                        childColumnNode.setTableIdCh(childColumn.getTableIdCh());
                                        childColumnNode.setTableNameEn(childColumn.getTableName());
                                        childColumnNode.setTableNameCh(childColumn.getTableNameCh());
                                        childColumnNode.setColumnName(rightColumnName);
                                        childColumnNode.setColumnNameCh(childColumn.getColumnChiName());
                                        childColumnNode.setId((edges.getTarget() + "|" + rightColumnName).toUpperCase());
                                        childColumnNode.setNodeName(childColumn.getNodeName());
                                        childColumnNode.setSourceCode(childColumn.getTableCode());
                                        nodeList.add(childColumnNode);
                                        idCountMap.put((edges.getTarget() + "|" + rightColumnName).toUpperCase(), 1);
                                        edgeList.add(new ColumnBloodlineNode.ColumnEdges((mainNodeId + "|" + columnName).toUpperCase(), childColumnNode.getId()));
                                        parsingFieldRelation(edgesList, edges.getTarget(), rightColumnName, Constant.RIGHT,
                                                nodeList, edgeList, idCountMap, columnDbMap, fieldColumnMap,pageKey);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    }

}
