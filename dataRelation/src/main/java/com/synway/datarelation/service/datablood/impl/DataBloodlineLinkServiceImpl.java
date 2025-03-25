package com.synway.datarelation.service.datablood.impl;

import com.alibaba.fastjson.JSONObject;
import com.synway.datarelation.dao.datablood.DataBloodlineDao;
import com.synway.datarelation.constant.BloodlineNodeType;
import com.synway.datarelation.constant.Constant;
import com.synway.datarelation.constant.ManufacturerName;
import com.synway.datarelation.pojo.databloodline.*;
import com.synway.datarelation.service.datablood.CacheManageDataBloodlineService;
import com.synway.datarelation.service.datablood.DataBloodlineLinkService;
import com.synway.datarelation.service.datablood.DataBloodlineService;
import com.synway.datarelation.service.datablood.FieldBloodlineService;
import com.synway.common.exception.ExceptionUtil;
import com.synway.datarelation.util.RestTemplateHandle;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.stream.Collectors.toMap;

/**
 * @author wangdongwei
 */
@Service
public class DataBloodlineLinkServiceImpl implements DataBloodlineLinkService {
    private Logger logger = LoggerFactory.getLogger(DataBloodlineLinkServiceImpl.class);
    @Autowired
    DataBloodlineService dataBloodlineServiceImpl;
    @Autowired
    DataBloodlineDao dataBloodlineDao;
    @Autowired
    CacheManageDataBloodlineService cacheManageDataBloodlineServiceImpl;
    @Autowired
    RestTemplateHandle restTemplateHandle;
    @Autowired
    ConcurrentHashMap<String,String> synlteObjectIdMap;
    @Autowired
    FieldBloodlineService fieldBloodlineServiceImpl;
    /**
     * 查询上一个流程和下一个流程的所有节点信息
     * @param bloodNode
     * @return
     */
    @Override
    public DataBloodlineNode getNextProcessByNode(DataBloodlineNode.BloodNode bloodNode, String queryType, Boolean nodeShow, int step, String pageId, String queryId) {
        DataBloodlineNode dataBloodlineNode = new DataBloodlineNode();
        String queryNodeId = "";
        try{
            logger.info("查询节点的上一个流程/下一个流程的节点信息为："+ JSONObject.toJSONString(bloodNode));
            List<DataBloodlineNode.BloodNode> listNode = new ArrayList<>();
            List<DataBloodlineNode.Edges> edgesList = new ArrayList<>();
            QueryDataBloodlineTable queryData = new QueryDataBloodlineTable();
            queryData.setDataBaseId(bloodNode.getDataBaseId());
            queryData.setSourceCodeCh(bloodNode.getSourceCodeCh());
            queryData.setSourceId(bloodNode.getSourceId());
            queryData.setTableId(bloodNode.getTableId());
            queryData.setTableNameCh(bloodNode.getTableNameCh());
            queryData.setTableNameEn(bloodNode.getTableNameEn());
            queryData.setTargetCodeCh(bloodNode.getTargetCodeCh());
            queryData.setPageId(pageId);
            queryData.setQueryId(queryId);
            // 如果tableId存在，且表名为空 则通过tableId和 所属系统代码 查询object表中表英文名
            if(StringUtils.isNotEmpty(bloodNode.getTableId()) && (StringUtils.isEmpty(bloodNode.getTableNameEn())
                    ||bloodNode.getTableNameEn().equalsIgnoreCase(bloodNode.getTableProject()+".") )){
                String tableNameEn = dataBloodlineDao.getTableNameByTableId(bloodNode.getTableId(),bloodNode.getTargetCode());
                queryData.setTableNameEn(tableNameEn);
            }
            queryNodeId = bloodNode.getId();
            int pageNum = BloodlineNodeType.getNumByCode(bloodNode.getDataType());
            if(queryType.equalsIgnoreCase(Constant.MAIN) || queryType.equalsIgnoreCase(Constant.LEFT)){
                String queryDataType = "";
                if(step > 1){
                    queryDataType = BloodlineNodeType.getCodeByNum(pageNum - step);
                }else{
                    queryDataType = BloodlineNodeType.getCodeByNum(pageNum -1 );
                }
                if(StringUtils.isEmpty(queryDataType)){
                    logger.info("是流程中是第一个节点信息，不需要查询");
                }else{
                    queryData.setDataType(queryDataType);
                    String message = "【"+BloodlineNodeType.getNameByCode(queryDataType)+"】类型的节点信息  ";
                    logger.info(message);
                    DataBloodlineNode dataBloodlineNodeLeft = dataBloodlineServiceImpl.getOneBloodlineNodeLink(queryData,"left",queryNodeId,nodeShow);
                    if(dataBloodlineNodeLeft != null
                            && dataBloodlineNodeLeft.getNodes()!= null
                            && dataBloodlineNodeLeft.getNodes().size() > 0){
                        listNode.addAll(dataBloodlineNodeLeft.getNodes());
                        edgesList.addAll(dataBloodlineNodeLeft.getEdges());
                    }
                }
            }
            if(queryType.equalsIgnoreCase(Constant.MAIN) || queryType.equalsIgnoreCase(Constant.RIGHT)){
                String queryDataType = "";
                if(step > 1){
                    queryDataType = BloodlineNodeType.getCodeByNum(pageNum+step);
                }else{
                    queryDataType = BloodlineNodeType.getCodeByNum(pageNum+(queryType.equalsIgnoreCase(Constant.LEFT)?-1:1));
                }
                String message = "【"+BloodlineNodeType.getNameByCode(queryDataType)+"】类型的节点信息 ";
                logger.info(message);
                if(StringUtils.isEmpty(queryDataType)){
                    logger.info("是流程中是最后一个节点信息，不需要查询");
                }else{
                    queryData.setDataType(queryDataType);
                    DataBloodlineNode dataBloodlineNodeRight = dataBloodlineServiceImpl.getOneBloodlineNodeLink(queryData,"right",queryNodeId,nodeShow);
                    if(dataBloodlineNodeRight != null
                            && dataBloodlineNodeRight.getNodes()!= null
                            && dataBloodlineNodeRight.getNodes().size() > 0){
                        listNode.addAll(dataBloodlineNodeRight.getNodes());
                        edgesList.addAll(dataBloodlineNodeRight.getEdges());
                    }
                }
            }
            dataBloodlineNode.setNodes(listNode);
            dataBloodlineNode.setEdges(edgesList);
        }catch (Exception e){
            dataBloodlineNode = null;
            logger.error("获取上一个/下一个流程的信息报错"+ExceptionUtil.getExceptionTrace(e));
        }
        return dataBloodlineNode;
    }


    /**
     *  20200423 数据不再插入到数据库中 插入到缓存
     * @param columnMap
     * @param relationColumnMap
     * @throws Exception
     */
    @Override
    public void insertNodeColumnAccessStandard(String pageId, String queryId, Map<String, List<ColumnRelationDB>> columnMap, Map<String, List<NodeColumnRelation>> relationColumnMap) throws Exception{
        // 将字段信息插入到数据库中
        if (pageId == null){
            logger.error("获取不到字段信息");
            return;
        }
        logger.info("字段信息为："+ JSONObject.toJSONString(columnMap.keySet()));
        // 将单个节点的字段信息插入到数据库中
        Map<String,List<ColumnRelationDB>> allColumnDataMap = new HashMap<>();
        for(String nodeId:columnMap.keySet()){
            List<ColumnRelationDB> columnRelationDBList = columnMap.get(nodeId);
            columnRelationDBList = new ArrayList<>(columnRelationDBList.stream().filter(d ->StringUtils.isNotEmpty(d.getColumnName()))
                    .collect(toMap(ColumnRelationDB::getColumnName, e->e,(exists, replacement)-> exists)).values());
            if(columnRelationDBList.size() > 0){
//                                int delNum = dataBloodlineDao.deleteColumnRelation(nodeId, Constant.STANDARD);
                allColumnDataMap.put(nodeId,columnRelationDBList);
            }
//            delColumnList.add(nodeId);
        }
//        logger.info("开始删除COLUMN_BLOODLINE_relation表中的数据");
//        if(delColumnList.size() > 0){
//            dataBloodlineDao.deleteColumnRelationByList(delColumnList);
//        }
//        logger.info("开始插入单个节点的字段信息");
//        if(allColumnDataList.size() > 0 ){
//            DAOHelper.insertList(allColumnDataList,dataBloodlineDao,"insertColumnRelation");
//        }
        // 插入节点的字段关系信息
//        logger.info("开始插入节点的字段关系信息");
        List<NodeColumnRelation> allRelationList = new ArrayList<>();
//        List<String> nodeColumnRelationList = new ArrayList<>();
        for(String id: relationColumnMap.keySet()){
            List<NodeColumnRelation> relations = relationColumnMap.get(id);
            if(relations.size() > 0){
                allRelationList.addAll(relations);
            }
//            nodeColumnRelationList.add(id);
        }
//        logger.info("从数据库表 TABLE_COLUMN_BLOODLINE中删除数据");
//        if(nodeColumnRelationList.size() > 0){
//            dataBloodlineDao.deleteNodeColumnMessageByList(nodeColumnRelationList);
//        }
        if(allRelationList.size() > 0 ){
            allRelationList = new ArrayList<>(allRelationList.stream().filter(d -> StringUtils.isNotEmpty(d.getSourceColumnName())
                    &&StringUtils.isNotEmpty(d.getTargetColumnName()))
                    .collect(toMap(d ->(d.getId()+d.getSourceColumnName()+d.getTargetColumnName()), e->e,(exists, replacement)-> exists)).values());
//            DAOHelper.insertList(allRelationList,dataBloodlineDao,"insertNodeColumnMessage");
        }
        // 调用插入缓存的方法
        cacheManageDataBloodlineServiceImpl.insertNodeColumnToCache(pageId,queryId,allColumnDataMap,allRelationList);

    }


    /**
     * 获取上一个流程/下一个流程的对应关系的节点，只需要一个
     * @param queryData   查询的参数
     * @param queryType   查询的方向 往左还是往右
     * @param queryNum    1表示相隔一个流程 2表示相隔2个流程
     * @param nodeType   节点类型
     * @return
     */
    @Override
    public DataBloodlineNode getNextProcessOneNode(QueryDataBloodlineTable queryData , String queryType, int queryNum,String nodeType) {
        DataBloodlineNode bloodNode = null;
        try{
            logger.info("查询下个流程的参数为，queryData:"+JSONObject.toJSONString(queryData)+" queryType:"+queryType+" queryNum:"+queryNum+" nodeType:"+nodeType);
            int pageNum = BloodlineNodeType.getNumByCode(nodeType);
            if(queryType.equalsIgnoreCase(Constant.LEFT)){
                String queryDataType = "";
                if(queryNum > 1){
                    queryDataType = BloodlineNodeType.getCodeByNum(pageNum - queryNum);
                }else{
                    queryDataType = BloodlineNodeType.getCodeByNum(pageNum -1 );
                }
                if(StringUtils.isEmpty(queryDataType)){
                    logger.info("是流程中是第一个节点信息，不需要查询");
                }else{
                    logger.info("开始查询【"+BloodlineNodeType.getNameByCode(queryDataType)+"】类型的节点信息 ");
                    bloodNode = getNextOneBloodInfo(queryData,queryDataType,queryType,nodeType);
                }
            }
            if(queryType.equalsIgnoreCase(Constant.RIGHT)){
                String queryDataType = "";
                if(queryNum > 1){
                    queryDataType = BloodlineNodeType.getCodeByNum(pageNum+queryNum);
                }else{
                    queryDataType = BloodlineNodeType.getCodeByNum(pageNum + 1);
                }
                logger.info("开始查询【"+BloodlineNodeType.getNameByCode(queryDataType)+"】类型的节点信息 ");
                if(StringUtils.isEmpty(queryDataType)){
                    logger.info("是流程中是最后一个节点信息，不需要查询");
                }else{
                    bloodNode = getNextOneBloodInfo(queryData,queryDataType,queryType,nodeType);
                }
            }
        }catch (Exception e){
            logger.error("查询该节点下一个流程是否有数据报错"+ExceptionUtil.getExceptionTrace(e));
        }
        return bloodNode;
    }


    /**
     *
     * @param queryData   查询的节点名称
     * @param queryDataType  查询的节点类型
     * @param queryType      查询的方向 left / right
     * @param oldNodeType
     * @return
     */
    private DataBloodlineNode getNextOneBloodInfo(QueryDataBloodlineTable queryData, String queryDataType
            , String queryType,String oldNodeType) {
        List<DataBloodlineNode.BloodNode> oneBloodList = new ArrayList<>();
        DataBloodlineNode.BloodNode oneBlood = new DataBloodlineNode.BloodNode();
        DataBloodlineNode oneBloodNode = new DataBloodlineNode();
        try {
            switch (queryDataType) {
                case Constant.ACCESS:
                    //  数据接入接口查询的  从缓存中查询
                    // 目前只可能从右到左查， 如果查询的是 tableId，则查询接入血缘的sourceid 并且输出表名为空
                    // 如果查询的是 表名，则 接入血缘的输出表名不能为空
                    queryData.setDataType(Constant.ACCESS);
                    oneBloodNode= dataBloodlineServiceImpl.getOneBloodlineNodeLink(queryData,Constant.LEFT,"",true);
                    break;
                case Constant.DATAPROCESS:
                    //  数据加工 right 如果从左往右查，则给的参数是tableId，先根据tableid查询到表名，然后再查询到具体的数据加工血缘
                    //    left 如果是从右往左查询  查询数据加工具体的信息
                    // 20210324 需要添加 分类信息和表的数据量
                    if(queryType.equalsIgnoreCase(Constant.RIGHT)){
                        // 根据tableId查找到具体的表名
                        if(StringUtils.isNotEmpty(queryData.getTableId()) && (StringUtils.isNotBlank(queryData.getTableNameEn()))){
                            String tableNameEn = dataBloodlineDao.getTableNameByTableId(queryData.getTableId(),queryData.getTargetCode());
                            queryData.setTableNameEn(tableNameEn);
                        }
                        List<RelationshipNode> allDataProcessList = cacheManageDataBloodlineServiceImpl.getCheckRelationshipNodeList(Constant.MAIN,queryData.getTableNameEn());
                        if( allDataProcessList == null || allDataProcessList.size() ==0){
                            oneBloodNode = null;
                        }else{
                            RelationshipNode relationshipNode = allDataProcessList.get(0);
                            oneBlood.setDataType(Constant.DATAPROCESS);
                            oneBlood.setTypeName(BloodlineNodeType.getNameByCode(Constant.DATAPROCESS));
                            if(relationshipNode.getParentTableId().equalsIgnoreCase(queryData.getTableId())){
                                oneBlood.setTableId(relationshipNode.getParentTableId());
                                oneBlood.setTableIdCh(relationshipNode.getParentTableNameCh());
                                oneBlood.setTableNameEn(relationshipNode.getParentTN());
                                oneBlood.setTableNameCh(relationshipNode.getParentTableNameCh());
                                oneBlood.setId((Constant.DATAPROCESS+"_"+oneBlood.getTableNameEn()).toLowerCase());
                            }else{
                                oneBlood.setTableId(relationshipNode.getChildrenTableId());
                                oneBlood.setTableIdCh(relationshipNode.getChildrenTableNameCh());
                                oneBlood.setTableNameEn(relationshipNode.getChildrenTN());
                                oneBlood.setTableNameCh(relationshipNode.getChildrenTableNameCh());
                                oneBlood.setId((Constant.DATAPROCESS+"_"+oneBlood.getTableNameEn()).toLowerCase());
                            }
                            oneBlood.setDataQueryType(Constant.RIGHT);
                            oneBlood.setFlagNum(1);
                            //20210308 新增表的所属分类信息
                            String classifyStr = cacheManageDataBloodlineServiceImpl.getTableClassifyCache(oneBlood.getTableNameEn(),oneBlood.getTableId());
                            if(StringUtils.isNotBlank(classifyStr)){
                                oneBlood.setClassifyFilter(DataBloodlineNode.BloodNode.ORGANIZATION_CLASSIFY);
                                oneBlood.setOrganizationClassifyName(classifyStr);
                            }
                            oneBlood.setClickShowAll(true);
                            oneBlood.setNodeName(StringUtils.isEmpty(oneBlood.getTableNameCh())?oneBlood.getTableNameEn():oneBlood.getTableNameCh());
                            oneBloodList.add(oneBlood);
                            oneBloodNode.setNodes(oneBloodList);
                            logger.info("数据加工流程的节点查询到的数据为"+JSONObject.toJSONString(oneBlood));
                        }
                    }else{
                        //  这个表示是从应用血缘中查询数据加工血缘的相关信息，应用血缘里面没有项目名
                        List<RelationshipNode> relationshipNodeList = cacheManageDataBloodlineServiceImpl.getProcessNodeByChild(queryData.getTableNameEn());
                        if(relationshipNodeList == null || relationshipNodeList.size() == 0){
                            oneBloodNode = null;
                        }else{
                            RelationshipNode relationshipNode = relationshipNodeList.get(0);
                            oneBlood.setDataType(Constant.DATAPROCESS);
                            oneBlood.setTypeName(BloodlineNodeType.getNameByCode(Constant.DATAPROCESS));
                            oneBlood.setTableId(relationshipNode.getChildrenTableId());
                            oneBlood.setTableIdCh(relationshipNode.getChildrenTableNameCh());
                            oneBlood.setTableNameEn(relationshipNode.getChildrenTN());
                            oneBlood.setTableNameCh(relationshipNode.getChildrenTableNameCh());
                            oneBlood.setId((Constant.DATAPROCESS+"_"+oneBlood.getTableNameEn()).toLowerCase());
                            oneBlood.setDataQueryType(Constant.LEFT);
                            oneBlood.setFlagNum(1);
                            oneBlood.setClickShowAll(true);
                            oneBlood.setNodeName(StringUtils.isEmpty(oneBlood.getTableNameCh())?oneBlood.getTableNameEn():oneBlood.getTableNameCh());
                            //20210308 新增表的所属分类信息
                            String classifyStr = cacheManageDataBloodlineServiceImpl.getTableClassifyCache(oneBlood.getTableNameEn(),oneBlood.getTableId());
                            if(StringUtils.isNotBlank(classifyStr)){
                                oneBlood.setClassifyFilter(DataBloodlineNode.BloodNode.ORGANIZATION_CLASSIFY);
                                oneBlood.setOrganizationClassifyName(classifyStr);
                            }
                            oneBloodList.add(oneBlood);
                            oneBloodNode.setNodes(oneBloodList);
                            logger.info("查询到的数据为"+JSONObject.toJSONString(oneBlood));
                        }
                    }
                    break;
                case Constant.DATAWARE:
                    // 数据仓库
                    break;
                case Constant.STANDARD:
                    //  标准化那边 如果queryType是 left 则表示是从数据加工查询过来的数据，则标准化缓存数据这边要是输出平台是odps的
                    // 如果 queryType是 right，则表示是从数据接入查询的数据，则需要查询输入血缘关系的内容
                    // 这个需要直接查询接口
                    DataBloodlineQueryParams dataBloodlineQueryParams = new DataBloodlineQueryParams();
                    dataBloodlineQueryParams.setQueryinfo(queryData.getTableNameEn());
                    dataBloodlineQueryParams.setQuerytype(DataBloodlineQueryParams.EXACT);
                    RelationInfoRestTemolate relationInfoRestTemolate = restTemplateHandle.queryStandardRelationInfo(dataBloodlineQueryParams);
                    List<QueryBloodlineRelationInfo> relationInfoList = relationInfoRestTemolate.getReqInfo();
                    //20210301 不再使用tableid来查询数据处理的相关数据
                    if(relationInfoList != null && !relationInfoList.isEmpty()){
                        Optional<QueryBloodlineRelationInfo> optional = relationInfoList.stream().filter(
                                d->{
                                    if(StringUtils.equalsIgnoreCase(queryType,Constant.LEFT)){
                                        if(StringUtils.equalsIgnoreCase(oldNodeType,Constant.OPERATINGSYSTEM)){
                                            return StringUtils.containsIgnoreCase(d.getTargetDBEngName(),Constant.ADS)
                                                    && StringUtils.equalsIgnoreCase(d.getTargetTableName(),queryData.getTableNameEn());
                                        }else{
                                            return (StringUtils.equalsIgnoreCase(d.getTargetDBEngName(),Constant.ODPS) ||
                                                    StringUtils.equalsIgnoreCase(d.getTargetDBEngName(),Constant.HIVE))&&
                                                    StringUtils.equalsIgnoreCase(d.getTargetTableName(),queryData.getTableNameEn());
                                        }

                                    }else{
                                        return StringUtils.equalsIgnoreCase(d.getSourceEngName(),queryData.getSourceId()) &&
                                                StringUtils.equalsIgnoreCase(d.getSourceSysId(),queryData.getSourceCode());
                                    }
                                }).findFirst();
                        QueryBloodlineRelationInfo oneOptional = null;
                        if(optional.isPresent()){
                            oneOptional = optional.get();
                        }else{
                            throw new NullPointerException("从数据处理获取到符合条件的节点信息为空");
                        }
                        String sourceNodeId = (oneOptional.getSourceEngName()+"|"+oneOptional.getSourceSysId()+"|"
                                +oneOptional.getSourceSupplierId()).toLowerCase();
                        // 目标表的 节点id值还需要加上 输出平台的英文名称和任务id
                        String targetNodeId = (oneOptional.getTargetEngName()+"|"+oneOptional.getTargetSysId()+"|"
                                + oneOptional.getTargetProjectName()+"."+oneOptional.getTargetTableName()+"|"+
                                oneOptional.getTargetDBEngName()).toLowerCase();
                        if(queryType.equalsIgnoreCase(Constant.LEFT)){
                            oneBlood.setDataType(Constant.STANDARD);
                            oneBlood.setTypeName(BloodlineNodeType.getNameByCode(Constant.STANDARD));
                            oneBlood.setId(targetNodeId);
                            oneBlood.setTableId(oneOptional.getTargetEngName().toUpperCase());
                            oneBlood.setTargetCode(oneOptional.getTargetSysId());
                            oneBlood.setTargetCodeCh(oneOptional.getTargetSysChiName());
                            oneBlood.setTableNameEn((oneOptional.getTargetProjectName()+"."+
                                    oneOptional.getTargetTableName()).toUpperCase());
                            oneBlood.setTableNameCh(StringUtils.isNotBlank(oneOptional.getTargetChiName())?
                                    oneOptional.getTargetChiName():"");
                            oneBlood.setTableProject(oneOptional.getTargetProjectName());
                            oneBlood.setOutputDatabaseType(oneOptional.getTargetDBEngName());
                            oneBlood.setTaskId(oneOptional.getTaskUUID());
                            oneBlood.setDataQueryType(Constant.LEFT);
                            oneBlood.setObjectId(synlteObjectIdMap.getOrDefault(oneBlood.getTableId().toLowerCase(),""));
                            oneBlood.setTargetDBEngName(oneOptional.getTargetDBEngName());
                            if(StringUtils.isNotEmpty(oneOptional.getTargetChiName())){
                                if(StringUtils.isNotEmpty(oneOptional.getTargetSysChiName())){
                                    oneBlood.setNodeName(oneOptional.getTargetChiName()+"|"+oneOptional.getTargetSysChiName());
                                }else{
                                    oneBlood.setNodeName(oneOptional.getTargetChiName());
                                }
                            }else if(StringUtils.isNotEmpty(oneOptional.getTargetTableName())){
                                oneBlood.setNodeName(oneBlood.getTableNameEn());
                            }else{
                                if(StringUtils.isNotEmpty(oneOptional.getTargetSysChiName())){
                                    oneBlood.setNodeName(oneBlood.getTableId()+"|"+oneOptional.getTargetSysChiName());
                                }else{
                                    oneBlood.setNodeName(oneBlood.getTableId());
                                }
                            }
                            oneBlood.setTableIdCh(StringUtils.isNotBlank(oneOptional.getTargetChiName())?
                                    oneOptional.getTargetChiName():"");
                            oneBlood.setVisible(true);
                            oneBlood.setClickShowAll(true);
                        }else{
                            oneBlood.setSourceId(oneOptional.getSourceEngName());
                            oneBlood.setSourceCode(oneOptional.getSourceSysId());
                            oneBlood.setSourceCodeCh(oneOptional.getSourceSysChiName());
                            oneBlood.setSourceFactoryId(oneOptional.getSourceSupplierId());
                            oneBlood.setSourceFactoryCh(ManufacturerName.getNameByIndex(Integer.parseInt(oneOptional.getSourceSupplierId())));
                            oneBlood.setDataType(Constant.STANDARD);
                            oneBlood.setDataQueryType(Constant.RIGHT);
                            oneBlood.setTypeName(BloodlineNodeType.getNameByCode(Constant.STANDARD));
                            oneBlood.setId(sourceNodeId);
                            oneBlood.setTaskId(oneOptional.getTaskUUID());
                            oneBlood.setSourceIdCh(oneOptional.getSourceChiName());
                            oneBlood.setSourceIdCh(StringUtils.isNotBlank(oneBlood.getSourceIdCh())?oneBlood.getSourceIdCh():"");
                            oneBlood.setNodeName(StringUtils.isEmpty(oneBlood.getSourceIdCh()) ? oneBlood.getSourceId():oneBlood.getSourceIdCh());
                            oneBlood.setVisible(true);
                            oneBlood.setClickShowAll(true);
                            oneBlood.setObjectId(synlteObjectIdMap.getOrDefault(oneBlood.getSourceId().toLowerCase(),""));
                        }
                        // 获取字段的相关信息
                        fieldBloodlineServiceImpl.insertColumnRelationToCache(relationInfoList,queryData);
                        oneBloodList.add(oneBlood);
                        oneBloodNode.setNodes(oneBloodList);
                        logger.info("查询到的数据为"+JSONObject.toJSONString(oneBlood));
                    }else{
                        oneBloodNode = null;
                    }
                    break;
                case Constant.OPERATINGSYSTEM:
                    // 这个是根据表名查询是否在应用血缘这边有节点信息
                    // 20201106 应用血缘的缓存中表名不再 包含项目名
                    String[] queryTableName = queryData.getTableNameEn().split("\\.");
                    String queryTypeNameEn = queryTableName[queryTableName.length -1].toUpperCase();
                    List<ApplicationSystem> applicationSystemList = cacheManageDataBloodlineServiceImpl.getApplicationSystemCache(queryTypeNameEn);
                    // 只需要出现一个表名，当出现多个标签时，只展示表名
                    if(applicationSystemList!=null && !applicationSystemList.isEmpty()){
                        oneBlood.setDataType(Constant.OPERATINGSYSTEM);
                        oneBlood.setTypeName(BloodlineNodeType.getNameByCode(Constant.OPERATINGSYSTEM));
                        oneBlood.setModuleClassify("");
                        oneBlood.setModuleClassifyNum(-1);
                        if(StringUtils.isNotBlank(applicationSystemList.get(0).getTableNameCh())){
                            oneBlood.setNodeName(applicationSystemList.get(0).getTableNameCh());
                        }else{
                            oneBlood.setNodeName(queryTypeNameEn.toLowerCase());
                        }
                        oneBlood.setTableNameEn(queryTypeNameEn.toLowerCase());
                        oneBlood.setId((Constant.OPERATINGSYSTEM+"_"+oneBlood.getTableNameEn()));
                        oneBlood.setClickShowAll(true);
                        oneBloodList.add(oneBlood);
                        oneBloodNode.setNodes(oneBloodList);
                    }else{
                        oneBloodNode = null;
                    }
                    break;
                default:
                    break;
            }
        }catch (Exception e){
            logger.error("查询下一个流程中单个节点信息报错"+ExceptionUtil.getExceptionTrace(e));
        }
        return oneBloodNode;
    }


}
