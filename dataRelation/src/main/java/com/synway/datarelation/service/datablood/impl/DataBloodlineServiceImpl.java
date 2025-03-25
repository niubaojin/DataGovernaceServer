package com.synway.datarelation.service.datablood.impl;

import com.alibaba.fastjson.JSONObject;
import com.synway.common.bean.ServerResponse;
import com.synway.datarelation.constant.*;
import com.synway.datarelation.dao.datablood.DataBloodlineDao;
import com.synway.datarelation.pojo.common.*;
import com.synway.datarelation.pojo.common.melon.RelationLinksCache;
import com.synway.datarelation.pojo.databloodline.*;
import com.synway.datarelation.service.common.CommonService;
import com.synway.datarelation.service.common.StandardTableService;
import com.synway.datarelation.service.datablood.*;
import com.synway.datarelation.util.DataBloodlineUtils;
import com.synway.common.exception.ExceptionUtil;
import com.synway.datarelation.util.RestTemplateHandle;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

/**
 * @author wangdongwei
 */
@Service
public class DataBloodlineServiceImpl implements DataBloodlineService {
    private Logger logger = LoggerFactory.getLogger(DataBloodlineServiceImpl.class);
    @Autowired
    DataBloodlineDao dataBloodlineDao;
    @Autowired
    RestTemplateHandle restTemplateHandle;
    @Autowired
    FieldBloodlineService fieldBloodlineServiceImpl;
    @Autowired
    DataBloodlineLinkService dataBloodlineLinkServiceImpl;
    @Autowired
    ApplicationSystemNodeService applicationSystemNodeServiceImpl;
    @Autowired
    private CacheManageDataBloodlineService cacheManageDataBloodlineServiceImpl;
    @Autowired
    private NodeTreeQueryService nodeTreeQueryServiceImpl;
    @Autowired
    private NodeQueryTableCountServiceImpl nodeQueryTableCountServiceImpl;

    @Autowired
    private ConcurrentHashMap<String,String> synlteObjectIdMap;

    @Autowired
    private CommonService commonServiceImpl;

    @Autowired
    private Environment environment;
    @Autowired
    private StandardTableService standardTableServiceImpl;


    @Override
    public ServerResponse<List<TreeNode>> queryDataBloodlineTable(DataBloodlineQueryParams dataBloodlineQueryParams)
        throws Exception{
        logger.info("搜索按钮点击之后的查询条件为："+ JSONObject.toJSONString(dataBloodlineQueryParams));
        ServerResponse<List<TreeNode>> serverResponse = null;
//        List<QueryDataBloodlineTable> allDataList = new ArrayList<>();
        List<TreeNode> treeNodeList = new ArrayList<>();
        String message = "";
        try{
            TreeNode treeNode = nodeTreeQueryServiceImpl.getTreeNodeInfo(dataBloodlineQueryParams);
            treeNodeList.add(treeNode);
            message = treeNode.getErrorMessage();
        }catch (Exception e){
            logger.error("查询左侧树报错"+ExceptionUtil.getExceptionTrace(e));
            message +=e.getMessage();
        }
        serverResponse = ServerResponse.asSucessResponse(message,treeNodeList);
        return serverResponse;
    }

    @Override
    public DataBloodlineNode getOneBloodlineNodeLink(QueryDataBloodlineTable queryData, String showType, String queryNodeId, Boolean nodeShow) throws Exception {
        // 20200406 当插叙一个节点之后，所有流程的节点都要查询出来
        logger.info("查询的参数为："+JSONObject.toJSONString(queryData)+ " showType:"+showType+" queryNodeId:"+queryNodeId);
        DataBloodlineNode dataBloodlineNode = new DataBloodlineNode();
        switch (queryData.getDataType()){
            case Constant.DATAWARE:
                // 数据仓库
                break;
            case Constant.ACCESS:
                //  数据接入接口查询的
                dataBloodlineNode = getAccessProcessDataBlood(queryData,showType,Constant.ACCESS,queryNodeId,nodeShow);
                break;
            case Constant.STANDARD:
                //  标准化那边
                dataBloodlineNode = getAccessProcessDataBlood(queryData,showType,Constant.STANDARD,queryNodeId,nodeShow);
                break;
            case Constant.DATAPROCESS:
                //  数据加工
                dataBloodlineNode = getDataProcessLink(queryData,showType,queryNodeId);
                break;
            case Constant.OPERATINGSYSTEM:
                // 查询应用血缘的相关信息
                // 20200427 应用血缘的返回结果发生变化，需要展示一级模块 二级模块等信息
                // 20210701 需要再查询数据处理那边ads的节点信息，如果存在，则进行连接
                dataBloodlineNode = applicationSystemNodeServiceImpl.getApplicationSystemNodeLink(queryData ,showType,nodeShow);
                break;
            default:
                break;
        }
        // 查询数据加工血缘的表数据量
        if(dataBloodlineNode !=null && dataBloodlineNode.getNodes() != null &&
                !dataBloodlineNode.getNodes().isEmpty()){
            // 20210701 数据处理的项目名修改需要查询  synlte.object_store_info 其中 IMPORT_FLAG=1 就是
            nodeQueryTableCountServiceImpl.getStandTableProjectName(dataBloodlineNode);
            // 查询表的数据量
            nodeQueryTableCountServiceImpl.getTableCountByName(dataBloodlineNode);
            // 对表名进行排序和去重
            dataBloodlineNode.setNodeNameList(DataBloodlineUtils.getTrackQueryDataByNode(dataBloodlineNode.getNodes()));
        }

        logger.info("---------------节点信息返回结束-----------------");
        return dataBloodlineNode;
    }


    /**
     *  获取数据血缘的信息 根据查询的信息查询
     *  20201210 轨迹搜索中需要用到所有的表英文名
     * @param queryData
     * @return
     * @throws Exception
     */
    public DataBloodlineNode getDataProcessLink(QueryDataBloodlineTable queryData,String showType,String queryNodeId) throws Exception {
        Map<String,Integer> tableExitMap = new HashMap<>();
        // 存储所有的数据加工血缘关系
        DataBloodlineNode dataBloodlineNode = new DataBloodlineNode();
        List<DataBloodlineNode.BloodNode> nodeList = new ArrayList<>();
        List<DataBloodlineNode.Edges> edgesList = new ArrayList<>();
        // 节点的层级数字
        int flagNum = 0;
        if(showType.equalsIgnoreCase("")){
            showType = Constant.MAIN;
        }
        // 20200418 修改 ，先将所有的数据处理查询到map中，使用全局map
        // 先判断缓存中数据是否存在，如果不存在，则查询缓存数据


        if(cacheManageDataBloodlineServiceImpl.getAllDataProcessBloodCache().getChildKeyData() == null ||
                cacheManageDataBloodlineServiceImpl.getAllDataProcessBloodCache().getChildKeyData().size() == 0){
            logger.info("数据加工血缘缓存中数据不存在，开始查询缓存数据");
            cacheManageDataBloodlineServiceImpl.getDataProcessDataBloodCache();
        }
        // 20210915 如果tableId存在 并且 tableNameEn 为空 则需要反查一下 将表英文名查询出来
        if(StringUtils.isBlank(queryData.getTableNameEn()) && StringUtils.isNotBlank(queryData.getTableId())){
            queryData.setTableNameEn(standardTableServiceImpl.getTableNameById(queryData.getTableId()));
        }
        String resultStr = getRightOneBloodlineNodeLink(queryData.getTableNameEn(),
                showType,StringUtils.isEmpty(queryData.getTableId())?"":queryData.getTableId(),tableExitMap,showType,
                nodeList,edgesList,flagNum,queryNodeId,queryData.getPageId(),queryData.getQueryId());

        logger.info("查询结果"+resultStr);
        try{
            edgesList= new ArrayList<>(edgesList.stream()
                    .filter(d -> d != null && !StringUtils.equalsIgnoreCase(d.getSource(),d.getTarget()))
                    .collect(toMap(d -> (d.getSource()+
                                    "|"+d.getTarget()+"|"+d.getEdgeType()+"|"+d.getVisible()),
                            e->e,(exists, replacement)-> exists)).values());
        }catch (Exception e){
            e.printStackTrace();
        }
        dataBloodlineNode.setEdges(edgesList);
        dataBloodlineNode.setNodes(nodeList);
        tableExitMap.clear();
        logger.info("------------查询数据加工数据开始正常返回-------");
        return dataBloodlineNode;
    }

    /**
     * 获取指定节点的所有数据血缘的链路信息   把查询节点当成父节点
     *    从父节点到子节点 然后以子节点为父节点再次查询子节点
     * @param queryTableName   查询的表名信息
     * @param queryType    right
     * @return
     * @throws Exception
     */
    public String getRightOneBloodlineNodeLink(String queryTableName, String queryType,
                                                           String queryTableId,Map<String,Integer> tableMap,String showType,
                                                          List<DataBloodlineNode.BloodNode> nodeList,List<DataBloodlineNode.Edges> edgesList,
                                              int flagNum,String queryNodeId,String pageId,String queryId) {
        logger.info("开始查询数据加工的表血缘信息");
        // 如果是tableId，则需要
        if(StringUtils.isEmpty(queryTableName)){
            logger.error("查询参数中英文表名不能为空");
            return "查询参数中英文表名不能为空";
        }
        // 先判断tableId是否存在，如果不存在
        // 如果是从右往左查询，则只从右往左查询
        Map<String ,List<RelationshipNode>> allDataProcessMap = new HashMap<>();
        List<RelationshipNode> allDataProcessList = cacheManageDataBloodlineServiceImpl.getCheckRelationshipNodeList(showType,queryTableName);
        if(allDataProcessList == null|| allDataProcessList.isEmpty()){
            logger.error("查询到的所有数据加工血缘关系数据量为0");
            return "查询到的所有数据加工血缘关系数据量为0";
        }
        RelationshipNode oneRelationNode = allDataProcessList.get(0);
        if((showType.equalsIgnoreCase(Constant.MAIN) &&(
                queryTableName.contains(".")?oneRelationNode.getChildrenTN().equalsIgnoreCase(queryTableName):
                        oneRelationNode.getChildrenTN().toLowerCase().contains(queryTableName.toLowerCase())))){
            allDataProcessMap = allDataProcessList.stream().filter(d ->
                    (StringUtils.isNotEmpty(d.getParentTN()))
            ).collect(Collectors.groupingBy(
                    d->(d.getChildrenTN()+"&& "+d.getChildrenTableId()+"&& "+d.getChildrenTableNameCh()+"&& "+d.getFlowName()
                            +"&& "+d.getNodeName() + "&&  "+ d.getDataType())));
        }else if(showType.equalsIgnoreCase(Constant.LEFT)){
            allDataProcessMap = allDataProcessList.stream().filter(d ->
                    (StringUtils.isNotEmpty(d.getParentTN()))
            ).collect(Collectors.groupingBy(
                    d->(d.getChildrenTN()+"&& "+d.getChildrenTableId()+"&& "+d.getChildrenTableNameCh()+"&& "+d.getFlowName()
                            +"&& "+d.getNodeName() + "&&  "+ d.getDataType() )));
        }else{
            allDataProcessMap = allDataProcessList.stream().filter(d ->
                    StringUtils.isNotEmpty(d.getParentTN()) && d.getParentTableId().equalsIgnoreCase(queryTableId)
            ).collect(Collectors.groupingBy(
                    d->(d.getParentTN()+"&& "+d.getParentTableId()+"&& "+d.getParentTableNameCh() +"&& "+d.getFlowName()
                            +"&& "+d.getNodeName() + "&&  "+ d.getDataType() )));
        }
        // 存储子节点的列表
        flagNum ++;
        Map<String, List<RelationshipNode>> finalAllDataProcessMap = allDataProcessMap;
        int finalFlagNum = flagNum;
        allDataProcessMap.keySet().parallelStream().forEach(parentNodeName ->{
            String[] aa = parentNodeName.split("&&",-1);
            if(tableMap.getOrDefault(aa[0].trim().toUpperCase(),0) == 0){
                DataBloodlineNode.BloodNode mainNode = new DataBloodlineNode.BloodNode();
                mainNode.setDataType(Constant.DATAPROCESS);
                mainNode.setTypeName(BloodlineNodeType.getNameByCode(Constant.DATAPROCESS));
                mainNode.setTableId(aa[1].trim());
                mainNode.setTableIdCh(aa[2].trim());
                mainNode.setTableNameEn(aa[0].trim());
                mainNode.setTableNameCh(aa[2].trim());
                mainNode.setId((Constant.DATAPROCESS+"_"+aa[0].trim()).toLowerCase());
                mainNode.setDataQueryType(showType);
                mainNode.setFlagNum(finalFlagNum);
                String nodeType = aa[5].trim();
                mainNode.setDataBaseType(commonServiceImpl.getTableType(nodeType));
                // 确定节点的展示信息
                if(StringUtils.isNotBlank(mainNode.getTableNameCh())){
                    mainNode.setNodeName(mainNode.getTableNameCh().replaceAll("\\."," "));
                }else{
                    mainNode.setNodeName(mainNode.getTableNameEn());
                }
                if(finalFlagNum > Constant.NODE_SHOW_NUM){
                    mainNode.setVisible(true);
                }
                //20210308 新增表的所属分类信息
                String classifyStr = cacheManageDataBloodlineServiceImpl.getTableClassifyCache(aa[0].trim(),aa[1].trim());
                if(StringUtils.isNotBlank(classifyStr)){
                    mainNode.setClassifyFilter(DataBloodlineNode.BloodNode.ORGANIZATION_CLASSIFY);
                    mainNode.setOrganizationClassifyName(classifyStr);
                }
                nodeList.add(mainNode);
                if(StringUtils.isNotEmpty(queryNodeId)){
                    if(showType.equalsIgnoreCase(Constant.LEFT)){
                        edgesList.add(new DataBloodlineNode.Edges(mainNode.getId(),queryNodeId,true,Constant.DATAPROCESS,
                                aa[3].trim(),aa[4].trim()));
                    }else{
                        edgesList.add(new DataBloodlineNode.Edges(queryNodeId,mainNode.getId(),true,Constant.DATAPROCESS,
                                aa[3].trim(),aa[4].trim()));
                    }
                }
                // 查询下一个流程的数据 即应用血缘的消息
                // 如果想查询应用血缘的信息 则只要这个节点是 同步任务的节点即可
                boolean flag = false;
                try{
                    String childTableName = finalAllDataProcessMap.get(parentNodeName).get(0).getChildrenTN();
                    flag = childTableName.equalsIgnoreCase(mainNode.getTableNameEn());
                }catch (Exception e){
                    flag = false;
                }
                if((mainNode.getTableNameEn().toLowerCase().contains("hc_db.")
                        ||mainNode.getTableNameEn().toLowerCase().contains("hp_db.")
                        || "23".equals(nodeType))
                        && flag) {
                    QueryDataBloodlineTable queryDataInfo = new QueryDataBloodlineTable();
                    queryDataInfo.setTableNameEn(mainNode.getTableNameEn());
                    queryDataInfo.setPageId(pageId);
                    queryDataInfo.setQueryId(queryId);
                    DataBloodlineNode bloodNodeNext = dataBloodlineLinkServiceImpl.getNextProcessOneNode(queryDataInfo,Constant.RIGHT,1,Constant.DATAPROCESS);
                    if(bloodNodeNext!=null && bloodNodeNext.getNodes()!=null&& bloodNodeNext.getNodes().size() >0){
                        nodeList.addAll(bloodNodeNext.getNodes());
                        for(DataBloodlineNode.BloodNode bloodNode :bloodNodeNext.getNodes()){
                            edgesList.add(new DataBloodlineNode.Edges(mainNode.getId(),bloodNode.getId().toLowerCase(),true,Constant.DATAPROCESS));
                        }
                    }
                }
                tableMap.put(aa[0].trim().toUpperCase(),1);
                logger.info("主节点的信息为："+JSONObject.toJSONString(aa));
                int nodeNumOld = nodeList.size();
                int nodeNumNew = 0;
                if(showType.equalsIgnoreCase(Constant.LEFT)){
                    getLeftOneBloodlineNodeLink(mainNode.getTableNameEn(),mainNode.getId(),
                            Constant.LEFT,queryTableId,tableMap,showType ,nodeList , edgesList,1,pageId,queryId);
                    nodeNumNew = nodeList.size();
                }else if(queryType.equalsIgnoreCase(Constant.MAIN)){
                    int leftNum = 0;
                    getLeftOneBloodlineNodeLink(queryTableName,mainNode.getId(),
                            Constant.LEFT,queryTableId,tableMap,showType ,nodeList , edgesList,leftNum,pageId,queryId);
                    nodeNumNew = nodeList.size();
                }
                if(nodeNumOld == nodeNumNew){
                    QueryDataBloodlineTable queryDataInfo = new QueryDataBloodlineTable();
                    queryDataInfo.setTableNameEn(mainNode.getTableNameEn());
                    queryDataInfo.setPageId(pageId);
                    queryDataInfo.setQueryId(queryId);
                    DataBloodlineNode bloodNodeAccess = dataBloodlineLinkServiceImpl.getNextProcessOneNode(queryDataInfo,Constant.LEFT,2,Constant.DATAPROCESS);
                    if(bloodNodeAccess != null && bloodNodeAccess.getNodes() != null
                            && bloodNodeAccess.getNodes().size() > 0){
                        edgesList.addAll(bloodNodeAccess.getEdges());
                        nodeList.addAll(bloodNodeAccess.getNodes());
                        // 还需要连接 数据加工和数据接入的节点信息
                        for(DataBloodlineNode.BloodNode bloodNodeAccessChild:bloodNodeAccess.getNodes()){
                            if(bloodNodeAccessChild.getTableNameEn().equalsIgnoreCase(mainNode.getTableNameEn())){
                                edgesList.add(new DataBloodlineNode.Edges(bloodNodeAccessChild.getId(),
                                        mainNode.getId(),true,Constant.ACCESS));
                            }
                        }
                    }
                    // 20210227 查询数据处理那边的节点信息
                    QueryDataBloodlineTable queryDataInfo1 = new QueryDataBloodlineTable();
                    queryDataInfo1.setTableNameEn(mainNode.getTableNameEn().contains(".")?mainNode.getTableNameEn().split("\\.")[1]
                            :mainNode.getTableNameEn());
                    queryDataInfo1.setPageId(pageId);
                    queryDataInfo1.setQueryId(queryId);
                    DataBloodlineNode bloodNodeNext = dataBloodlineLinkServiceImpl.getNextProcessOneNode(queryDataInfo1,
                            Constant.LEFT,1,Constant.DATAPROCESS);
                    if(bloodNodeNext!=null && bloodNodeNext.getNodes()!=null&& bloodNodeNext.getNodes().size() >0){
                        nodeList.addAll(bloodNodeNext.getNodes());
                        for(DataBloodlineNode.BloodNode bloodNode :bloodNodeNext.getNodes()){
                            edgesList.add(new DataBloodlineNode.Edges(bloodNode.getId().toLowerCase(),mainNode.getId(),
                                    true,Constant.STANDARD));
                        }
                    }
                }
            }else{
                if(StringUtils.isNotEmpty(queryNodeId)){
                    if(showType.equalsIgnoreCase(Constant.LEFT)){
                        edgesList.add(new DataBloodlineNode.Edges((Constant.DATAPROCESS+"_"+aa[0].trim()).toLowerCase(),queryNodeId,true,Constant.DATAPROCESS,
                                aa[3].trim(),aa[4].trim()));
                    }else{
                        edgesList.add(new DataBloodlineNode.Edges(queryNodeId,(Constant.DATAPROCESS+"_"+aa[0].trim()).toLowerCase(),true,Constant.DATAPROCESS,
                                aa[3].trim(),aa[4].trim()));
                    }
                }
            }
            // 获取对应的数据
            List<RelationshipNode> oneRelationshipNode = finalAllDataProcessMap.get(parentNodeName);
            for(RelationshipNode childRelationshipNode:oneRelationshipNode){
                // 以该节点为父节点获取子节点的相关信息  往右查
                if(tableMap.getOrDefault(childRelationshipNode.getChildrenTN().trim().toUpperCase(),0) == 0){
                    tableMap.put(childRelationshipNode.getChildrenTN().trim().toUpperCase(),1);
                    DataBloodlineNode.BloodNode childNode = new DataBloodlineNode.BloodNode();
                    childNode.setDataType(Constant.DATAPROCESS);
                    childNode.setTypeName(BloodlineNodeType.getNameByCode(Constant.DATAPROCESS));
                    childNode.setTableId(childRelationshipNode.getChildrenTableId().trim());
                    childNode.setTableIdCh(childRelationshipNode.getChildrenTableNameCh().trim());
                    childNode.setTableNameEn(childRelationshipNode.getChildrenTN().trim());
                    childNode.setTableNameCh(childRelationshipNode.getChildrenTableNameCh().trim());
                    childNode.setId((Constant.DATAPROCESS+"_"+childRelationshipNode.getChildrenTN().trim()).toLowerCase());
                    childNode.setDataQueryType("");
                    childNode.setFlagNum(finalFlagNum);
                    String nodeTypeChild = childRelationshipNode.getDataType();
                    childNode.setDataBaseType(commonServiceImpl.getTableType(nodeTypeChild));

                    // 确定节点的展示信息
                    if(StringUtils.isNotBlank(childNode.getTableNameCh())){
                        childNode.setNodeName(childNode.getTableNameCh().replaceAll("\\."," "));
                    }else{
                        childNode.setNodeName(childNode.getTableNameEn());
                    }
                    //20210308 新增表的所属分类信息
                    String classifyStr = cacheManageDataBloodlineServiceImpl.getTableClassifyCache(childNode.getTableNameEn()
                            ,childNode.getTableId());
                    if(StringUtils.isNotBlank(classifyStr)){
                        childNode.setClassifyFilter(DataBloodlineNode.BloodNode.ORGANIZATION_CLASSIFY);
                        childNode.setOrganizationClassifyName(classifyStr);
                    }
                    nodeList.add(childNode);
                    // 隐藏的代码去除掉
                    childNode.setVisible(true);
                    edgesList.add(new DataBloodlineNode.Edges((Constant.DATAPROCESS+"_"+aa[0].trim()).toLowerCase(),childNode.getId().toLowerCase(),
                            true,Constant.DATAPROCESS,childRelationshipNode.getFlowName(),childRelationshipNode.getNodeName()));
                    // 运行下一个流程的节点信息
                    //  如果该节点是 ads的节点，则不查询上一个流程的节点信息
                    // 20200429 不需要查询上一个流程/下一个流程的数据 产品需求
                    DataBloodlineNode bloodNodeNext = null;
                    if(childNode.getTableNameEn().toLowerCase().contains("hc_db.") ||childNode.getTableNameEn().toLowerCase().contains("hp_db.")
                            || "23".equals(nodeTypeChild)) {
                        QueryDataBloodlineTable queryDataInfo = new QueryDataBloodlineTable();
                        queryDataInfo.setTableNameEn(childNode.getTableNameEn());
                        queryDataInfo.setPageId(pageId);
                        queryDataInfo.setQueryId(queryId);
                        bloodNodeNext = dataBloodlineLinkServiceImpl.getNextProcessOneNode(queryDataInfo,Constant.RIGHT,1,Constant.DATAPROCESS);
                        if(bloodNodeNext!=null && bloodNodeNext.getNodes()!=null&& bloodNodeNext.getNodes().size() >0){
                            nodeList.addAll(bloodNodeNext.getNodes());
                            for(DataBloodlineNode.BloodNode bloodNode :bloodNodeNext.getNodes()){
                                edgesList.add(new DataBloodlineNode.Edges(childNode.getId(),bloodNode.getId().toLowerCase(),true,Constant.DATAPROCESS));
                            }
                        }
                    }else{
                        String queryStr = getRightOneBloodlineNodeLink(childRelationshipNode.getChildrenTN().trim() ,
                                Constant.RIGHT,childRelationshipNode.getChildrenTableId().trim(),tableMap,showType,nodeList,edgesList, finalFlagNum,childNode.getId(),pageId,queryId);
                        logger.info("查询结果为："+queryStr);
                    }

                }else{
                    edgesList.add(new DataBloodlineNode.Edges((Constant.DATAPROCESS+"_"+aa[0].trim()).toLowerCase(),
                            (Constant.DATAPROCESS+"_"+childRelationshipNode.getChildrenTN().trim()).toLowerCase(),
                            true,Constant.DATAPROCESS,childRelationshipNode.getFlowName(),childRelationshipNode.getNodeName()));
                }
            }
        });
        for(String parentNodeName:allDataProcessMap.keySet()){

        }
        return "从左往右查数据加工血缘查询结束";
    }


    private void runNextNodeMessage(DataBloodlineNode.BloodNode node,String queryType,
                                    List<DataBloodlineNode.BloodNode> nodeList,List<DataBloodlineNode.Edges> edgesList,
                                    Boolean nodeShow,String pageId,String queryId){
        if(StringUtils.isNotEmpty(node.getTableId())){
            // 开始查询上一个流程或者下一个流程的数据
            DataBloodlineNode dataBloodlineNodeChild = dataBloodlineLinkServiceImpl.getNextProcessByNode(node,queryType,nodeShow,1,pageId,queryId);
            if(dataBloodlineNodeChild != null && dataBloodlineNodeChild.getNodes() != null
                    && dataBloodlineNodeChild.getNodes().size() >0){
                nodeList.addAll(dataBloodlineNodeChild.getNodes());
                edgesList.addAll(dataBloodlineNodeChild.getEdges());
            }
        }else if(!queryType.equalsIgnoreCase(Constant.LEFT)){
            // 开始查询上一个流程或者下一个流程的数据
            DataBloodlineNode dataBloodlineNodeChild1 = dataBloodlineLinkServiceImpl.getNextProcessByNode(node,Constant.RIGHT,nodeShow,1,pageId,queryId);
            if(dataBloodlineNodeChild1 != null && dataBloodlineNodeChild1.getNodes() != null
                    && dataBloodlineNodeChild1.getNodes().size() >0){
                nodeList.addAll(dataBloodlineNodeChild1.getNodes());
                edgesList.addAll(dataBloodlineNodeChild1.getEdges());
            }
        }else{
            logger.info("不需要查询上一个流程/下一个流程的节点信息");
        }
    }


    /**
     *  从右往左查询数据加工血缘信息   把查询节点当成子节点 然后再根据子节点查询父节点，再把父节点当成子节点再查询该节点的父节点
     * @param queryTableName
     * @param queryType
     * @param queryTableId
     * @param tableMap
     * @param showType
     * @param nodeList
     * @param edgesList
     * @return
     */
    public String getLeftOneBloodlineNodeLink(String queryTableName, String rootStr, String queryType,
                                                                String queryTableId,Map<String,Integer> tableMap,String showType,
                                                               List<DataBloodlineNode.BloodNode> nodeList,List<DataBloodlineNode.Edges> edgesList,int leftNum
            ,String pageId,String queryId){
        logger.info("开始查询"+queryTableName+"右往左查询数据血缘的节点信息");
        // 从右往左查询
        if(StringUtils.isEmpty(queryTableName)){
            logger.error("查询参数中英文表名不能为空");
            return "查询参数中英文表名不能为空";
        }
        List<RelationshipNode> allDataProcessList = cacheManageDataBloodlineServiceImpl.getCheckRelationshipNodeList(queryType,queryTableName);
//        if(queryTableName.contains(".")){
//            allDataProcessList = dataBloodlineDao.getLeftDataProcessTableRelation(queryTableName,1);
//        }else{
//            allDataProcessList = dataBloodlineDao.getLeftDataProcessTableRelation(queryTableName,2);
//        }
        if(allDataProcessList == null || allDataProcessList.size() == 0 ){
            logger.error("查询到的所有数据加工血缘关系数据量为0");
            return "查询到的所有数据加工血缘关系数据量为0";
        }
        Map<String ,List<RelationshipNode>> allDataProcessMap = allDataProcessList.stream().filter(d ->
                StringUtils.isNotEmpty(d.getParentTN())
        ).collect(Collectors.groupingBy(
                d->(d.getParentTN()+"&& "+d.getParentTableId()+"&& "+d.getParentTableNameCh()+"&& "+d.getFlowName()
                        +"&& "+d.getNodeName()) +"&& "+d.getDataType()));
        leftNum = leftNum +1;
        int finalLeftNum = leftNum;
        allDataProcessMap.keySet().parallelStream().forEach(nodeName ->{
            String[] aa = nodeName.split("&&");
            if(tableMap.getOrDefault(aa[0].trim().toUpperCase(),0) == 0){
                tableMap.put(aa[0].trim().toUpperCase(),1);
                DataBloodlineNode.BloodNode leftChildNode = new DataBloodlineNode.BloodNode();
                leftChildNode.setDataType(Constant.DATAPROCESS);
                leftChildNode.setTypeName(BloodlineNodeType.getNameByCode(Constant.DATAPROCESS));
                leftChildNode.setDataQueryType("");
                leftChildNode.setTableId(aa[1].trim());
                leftChildNode.setTableIdCh(aa[2].trim());
                leftChildNode.setTableNameEn(aa[0].trim());
                leftChildNode.setTableNameCh(aa[2].trim());
                leftChildNode.setDataBaseType(commonServiceImpl.getTableType(aa[5].trim()));

                leftChildNode.setId((Constant.DATAPROCESS+"_"+aa[0].trim()).toLowerCase());
                leftChildNode.setVisible(true);
                // 确定节点的展示信息
                if(StringUtils.isNotBlank(leftChildNode.getTableNameCh())){
                    leftChildNode.setNodeName(leftChildNode.getTableNameCh().replaceAll("\\."," "));
                }else{
                    leftChildNode.setNodeName(leftChildNode.getTableNameEn());
                }
                //20210308 新增表的所属分类信息
                String classifyStr = cacheManageDataBloodlineServiceImpl.getTableClassifyCache(aa[0].trim()
                        ,aa[1].trim());
                if(StringUtils.isNotBlank(classifyStr)){
                    leftChildNode.setClassifyFilter(DataBloodlineNode.BloodNode.ORGANIZATION_CLASSIFY);
                    leftChildNode.setOrganizationClassifyName(classifyStr);
                }
                nodeList.add(leftChildNode);
                edgesList.add(new DataBloodlineNode.Edges((Constant.DATAPROCESS+"_"+aa[0].trim()).toLowerCase() ,rootStr.toLowerCase(),true,Constant.DATAPROCESS,
                        aa[3].trim(),aa[4].trim()));
                int nodeOldNum = nodeList.size();
                getLeftOneBloodlineNodeLink(leftChildNode.getTableNameEn() ,(Constant.DATAPROCESS+"_"+aa[0].trim()).toUpperCase(),
                        Constant.LEFT,queryTableId,tableMap,showType,nodeList,edgesList, finalLeftNum,pageId,queryId);
                int nodeNewNum = nodeList.size();
                // 20200429 不需要查询上一个流程/下一个流程的数据 产品需求
                if(nodeOldNum == nodeNewNum){
                    // 查询接入那边的血缘关系
                    QueryDataBloodlineTable queryDataInfo = new QueryDataBloodlineTable();
                    queryDataInfo.setTableNameEn(leftChildNode.getTableNameEn());
                    queryDataInfo.setPageId(pageId);
                    queryDataInfo.setQueryId(queryId);
                    DataBloodlineNode bloodNodeAccess = dataBloodlineLinkServiceImpl.getNextProcessOneNode(queryDataInfo,Constant.LEFT,2,Constant.DATAPROCESS);
                    if(bloodNodeAccess != null && bloodNodeAccess.getNodes() != null
                            && bloodNodeAccess.getNodes().size() > 0){
                        edgesList.addAll(bloodNodeAccess.getEdges());
                        nodeList.addAll(bloodNodeAccess.getNodes());
                    }
                    // 20210227 查询数据处理那边的节点信息
                    QueryDataBloodlineTable queryDataInfo1 = new QueryDataBloodlineTable();
                    // 如果包含了项目名，则需要把项目名去除掉，再查询相关数据
                    queryDataInfo1.setTableNameEn(leftChildNode.getTableNameEn().contains(".")?
                            leftChildNode.getTableNameEn().split("\\.")[1]:leftChildNode.getTableNameEn());
                    queryDataInfo1.setPageId(pageId);
                    queryDataInfo1.setQueryId(queryId);
                    DataBloodlineNode bloodNodeNext = dataBloodlineLinkServiceImpl.getNextProcessOneNode(queryDataInfo1,Constant.LEFT,1,Constant.DATAPROCESS);
                    if(bloodNodeNext!=null && bloodNodeNext.getNodes()!=null&& bloodNodeNext.getNodes().size() >0){
                        nodeList.addAll(bloodNodeNext.getNodes());
                        for(DataBloodlineNode.BloodNode bloodNode :bloodNodeNext.getNodes()){
                            edgesList.add(new DataBloodlineNode.Edges(bloodNode.getId().toLowerCase(),leftChildNode.getId(),true,Constant.STANDARD));
                        }
                    }
                }
            }else{
                // 20210222 如果已经存在，则需要在加上边
                edgesList.add(new DataBloodlineNode.Edges((Constant.DATAPROCESS+"_"+aa[0].trim()).toLowerCase() ,rootStr.toLowerCase(),true,Constant.DATAPROCESS,
                        aa[3].trim(),aa[4].trim()));
            }
        });
        return "数据加工血缘查询成功";
    }


    /**
     * 从数据接入接口中查询接入节点的信息
     *  从标准化管理的接口 查询标准化那边的节点信息
     * @param queryData  查询参数
     * @param showType   left/right 展示的节点
     * @param nodeType   是从 数据接入/数据标准化 接口查询
     * @param nodeShow   节点是否显示
     * @return
     */
    private DataBloodlineNode getAccessProcessDataBlood(QueryDataBloodlineTable queryData,String showType
            ,String nodeType,String queryNodeId,Boolean nodeShow){
        //  数据接入
        DataBloodlineNode dataBloodlineNode = new DataBloodlineNode();
        DataBloodlineQueryParams dataBloodlineQueryParams = new DataBloodlineQueryParams();
        try{
            logger.info("开始从数据接入接口查询节点信息"+JSONObject.toJSONString(queryData));
            if(StringUtils.isNotEmpty(queryData.getTableId())){
                dataBloodlineQueryParams.setQueryinfo(queryData.getTableId());
            }else if(StringUtils.isNotEmpty(queryData.getTableNameEn())){
                String[] list = queryData.getTableNameEn().split("\\.");
                if(list.length >1){
                    dataBloodlineQueryParams.setQueryinfo(list[1]);
                }else{
                    dataBloodlineQueryParams.setQueryinfo(list[0]);
                }
            }else if(StringUtils.isNotEmpty(queryData.getTableNameCh())){
                dataBloodlineQueryParams.setQueryinfo(queryData.getTableNameCh());
            } else if(StringUtils.isNotEmpty(queryData.getSourceId())){
                dataBloodlineQueryParams.setQueryinfo(queryData.getSourceId());
            }else{
                logger.error("在查询数据接入的节点信息时请求参数为空，不能从后台查询数据");
                return null;
            }
            dataBloodlineQueryParams.setQuerytype(DataBloodlineQueryParams.EXACT);
            RelationInfoRestTemolate relationInfoRestTemolate = null;
            if(nodeType.equalsIgnoreCase(Constant.ACCESS)){
                if(StringUtils.isNotEmpty(queryData.getSourceCode())){
                    dataBloodlineQueryParams.setQueryinfo(dataBloodlineQueryParams.getQueryinfo()+"|"+queryData.getSourceCode());
                }
                relationInfoRestTemolate = cacheManageDataBloodlineServiceImpl.getAccessRelationInfo(dataBloodlineQueryParams);
            }else{
                relationInfoRestTemolate = restTemplateHandle.queryStandardRelationInfo(dataBloodlineQueryParams);
            }
            if(relationInfoRestTemolate.getReqRet().equalsIgnoreCase("ok")){
                logger.info("数据查询成功，开始解析数据");
                List<QueryBloodlineRelationInfo> result = relationInfoRestTemolate.getReqInfo();
                //  标准化程序 将列表列表类型的数据解析成血缘关系的节点信息
                if(nodeType.equalsIgnoreCase(Constant.ACCESS)){
                    // 单个节点的所有字段信息 把字段存储在map中，之后统一插入到数据库中 key为nodeid
                    Map<String , List<ColumnRelationDB>> columnMap = new HashMap<>();
                    // 字段的对应关系
                    Map<String , List<NodeColumnRelation>> relationColumnMap = new HashMap<>();
                    dataBloodlineNode =  parsingAccessReturnJson(result , dataBloodlineQueryParams.getQueryinfo(),showType,queryNodeId,columnMap,
                            relationColumnMap,nodeShow,queryData.getPageId(),queryData.getQueryId());
                    try{
                        // 将字段信息插入到缓存中
                        dataBloodlineLinkServiceImpl.insertNodeColumnAccessStandard(queryData.getPageId(),queryData.getQueryId(),columnMap,relationColumnMap);
                    }catch (Exception e){
                        logger.error("将数据接入那边的字段数据插入到数据库中报错"+ExceptionUtil.getExceptionTrace(e));
                    }
                }else{
                    // 单个节点的所有字段信息 把字段存储在map中，之后统一插入到数据库中 key为nodeid
                    Map<String , List<ColumnRelationDB>> columnMap = new HashMap<>();
                    // 字段的对应关系
                    Map<String , List<NodeColumnRelation>> relationColumnMap = new HashMap<>();
                    dataBloodlineNode = parsingStandardReturnJson(result , dataBloodlineQueryParams.getQueryinfo(),showType,queryNodeId,columnMap,
                            relationColumnMap,nodeShow,queryData.getPageId(),queryData.getQueryId(),queryData.getTableNameEn());
                    // 开始将字段信息写入到数据库中
                    try{
                        for(QueryBloodlineRelationInfo oneData:result){
                            // 根据目标表名去数据库中查询字段信息
                            fieldBloodlineServiceImpl.submitTaskQueryColumn(oneData);
                        }
                        dataBloodlineLinkServiceImpl.insertNodeColumnAccessStandard(queryData.getPageId(),queryData.getQueryId(),columnMap,relationColumnMap);
                    }catch (Exception e){
                        logger.error("将字段数据插入到数据库中报错"+ExceptionUtil.getExceptionTrace(e));
                    }
                    logger.info("将字段数据写入到数据库中结束");
                }
            }else{
                logger.error("数据接口报错："+relationInfoRestTemolate.getError());
                dataBloodlineNode = null;
            }

        }catch (Exception e){
            logger.error("从数据接口查询节点信息报错"+ExceptionUtil.getExceptionTrace(e));
        }
        logger.info("返回的数据成功");
        return dataBloodlineNode;
    }


    /**
     * 将字段关系插入到数据库中
     *  如果queryType 是 left 表示这个是 数据来源  如果是 right 则表示是数据目标
     * @param oneRelation
     */
    private void insertColumnRelation(String queryType, String nodeId,
                                      QueryBloodlineRelationInfo oneRelation,String type,
                                      Map<String , List<ColumnRelationDB>> map,String nodeName){
        List<ColumnRelationDB> columnRelationDBList = new ArrayList<>();
        try{
            if(StringUtils.isNotEmpty(nodeId)){
                if(oneRelation.getFieldsRelation() != null && oneRelation.getFieldsRelation().size() > 0){
                    //先删除数据库中已经存在的数据
//                    int delNum = dataBloodlineDao.deleteColumnRelation(nodeId, type);
//                    logger.info("从数据库中删除的数据量为"+delNum);
                    for(QueryBloodlineRelationInfo.ColumnRelation columnRelation :oneRelation.getFieldsRelation()){
                        ColumnRelationDB columnRelationDB = new ColumnRelationDB();
                        if(queryType.equalsIgnoreCase(Constant.LEFT)){
                            columnRelationDB.setTableName("");
                            columnRelationDB.setTableNameCh("");
                            columnRelationDB.setType(type);
                            columnRelationDB.setColumnChiName(columnRelation.getSourceColumnChiName());
                            columnRelationDB.setColumnName(columnRelation.getSourceColumnName());
                            columnRelationDB.setFactoryId(oneRelation.getSourceSupplierId());
                            columnRelationDB.setId(UUID.randomUUID().toString());
                            columnRelationDB.setNodeId(nodeId);
                            columnRelationDB.setTableCode(oneRelation.getSourceSysId());
                            columnRelationDB.setTableId(oneRelation.getSourceEngName());
                            columnRelationDB.setTableIdCh(oneRelation.getSourceChiName());
                            columnRelationDB.setNodeName(nodeName);
                        }else{
                            columnRelationDB.setTableName(oneRelation.getTargetTableName());
                            columnRelationDB.setType(type);
                            columnRelationDB.setColumnChiName(columnRelation.getTargetColumnChiName());
                            columnRelationDB.setColumnName(columnRelation.getTargetColumnName());
                            columnRelationDB.setFactoryId("");
                            columnRelationDB.setId(UUID.randomUUID().toString());
                            columnRelationDB.setNodeId(nodeId);
                            columnRelationDB.setTableCode(oneRelation.getTargetSysId());
                            columnRelationDB.setTableId(oneRelation.getTargetEngName());
                            columnRelationDB.setTableNameCh(oneRelation.getTargetChiName());
                            columnRelationDB.setTableIdCh(oneRelation.getTargetChiName());
                            columnRelationDB.setDbEngName(oneRelation.getTargetDBEngName());
                            columnRelationDB.setTaskUuid(oneRelation.getTaskUUID());
                            columnRelationDB.setNodeName(nodeName);
                        }
                        columnRelationDBList.add(columnRelationDB);
                    }
//                    DAOHelper.insertList(columnRelationDBList,dataBloodlineDao,"insertColumnRelation");
                }
                if(map.getOrDefault(nodeId,null) == null){
                    map.put(nodeId,columnRelationDBList);
                }else{
                    List<ColumnRelationDB> nodeIdList = map.get(nodeId);
                    nodeIdList.addAll(columnRelationDBList);
                    map.put(nodeId,nodeIdList);
                }
            }
        }catch (Exception e){
            logger.error("插入数据报错"+ExceptionUtil.getExceptionTrace(e));
        }
    }


    /**
     * 解析标准化程序的返回结果
     * 标准化那边来源存在多个 sourceCode 多个sourceId 目标表存在多种存储的数据库类型
     * 以及根据sourceId对应的不同的 targetId
     * 输出表名可能存在空的情况
     * @param dataList   接口中的返回结果
     * @param queryStr   查询参数 主要用于确认主节点的信息
     * @param showType   为空时表示是查询根节点为 main  其它为 left/right(弃用)
     * @return
     */
    public DataBloodlineNode parsingStandardReturnJson(List<QueryBloodlineRelationInfo> dataList,
                                                        String queryStr,
                                                        String showType,
                                                        String queryNodeId,
                                                        Map<String , List<ColumnRelationDB>> oneNodeColumnMap,
                                                        Map<String , List<NodeColumnRelation>> relationColumnMap,
                                                        Boolean nodeShow,String pageId,String queryId,
                                                       String mainTableNameEn){
        logger.info("开始解析从标准化那边获取到的接口数据，生成页面需要展示节点");
        //  可能存在多个目标表，所以所有数据都存到child里面  根据 输出平台的英文名称 来判断
        //
        DataBloodlineNode dataBloodlineNode = new DataBloodlineNode();
        List<DataBloodlineNode.BloodNode> bloodNodeList = new ArrayList<>();
        List<DataBloodlineNode.Edges> edgesList = new ArrayList<>();
        Map<String ,Integer> allDataMap = new HashMap<>();
        Map<String , List<String>> sourceMap = new HashMap<>();
        try{
            for(QueryBloodlineRelationInfo queryBloodlineRelationInfo:dataList){
                String sourceId = (queryBloodlineRelationInfo.getSourceEngName()+"|"+queryBloodlineRelationInfo.getSourceSysId()).toUpperCase();
                String targetId = (queryBloodlineRelationInfo.getTargetEngName()+"|"+queryBloodlineRelationInfo.getTargetSysId()).toUpperCase();
                String nodeId = (queryBloodlineRelationInfo.getTargetEngName()+"|"+queryBloodlineRelationInfo.getTargetSysId()+"|"
                        + queryBloodlineRelationInfo.getTargetProjectName()+"."+queryBloodlineRelationInfo.getTargetTableName()+"|"
                        +queryBloodlineRelationInfo.getTargetDBEngName()).toUpperCase();
                List<String> nodeIds = sourceMap.getOrDefault(targetId,new ArrayList<String>());
                nodeIds.add(nodeId);
                sourceMap.put(targetId.toUpperCase(),nodeIds);
            }
            dataList.parallelStream().forEach(queryBloodlineRelationInfo ->{
                String sourceNodeId = (queryBloodlineRelationInfo.getSourceEngName()+"|"+queryBloodlineRelationInfo.getSourceSysId()+"|"
                        +queryBloodlineRelationInfo.getSourceSupplierId()).toUpperCase();
                // 目标表的 节点id值还需要加上 输出平台的英文名称和任务id
                String targetNodeId = (queryBloodlineRelationInfo.getTargetEngName()+"|"+queryBloodlineRelationInfo.getTargetSysId()+"|"
                        + queryBloodlineRelationInfo.getTargetProjectName()+"."+queryBloodlineRelationInfo.getTargetTableName()+"|"+
                        queryBloodlineRelationInfo.getTargetDBEngName()).toUpperCase();
                DataBloodlineNode.BloodNode bloodNode = new DataBloodlineNode.BloodNode();
                if(allDataMap.getOrDefault(sourceNodeId,0) == 0){
                    bloodNode.setSourceId(queryBloodlineRelationInfo.getSourceEngName());
                    bloodNode.setSourceIdCh(StringUtils.isNotBlank(queryBloodlineRelationInfo.getSourceChiName())?
                            queryBloodlineRelationInfo.getSourceChiName():"");
                    bloodNode.setSourceCode(queryBloodlineRelationInfo.getSourceSysId());
                    bloodNode.setSourceCodeCh(queryBloodlineRelationInfo.getSourceSysChiName());
                    bloodNode.setSourceFactoryId(queryBloodlineRelationInfo.getSourceSupplierId());
                    bloodNode.setSourceFactoryCh(ManufacturerName.getNameByIndex(Integer.parseInt(queryBloodlineRelationInfo.getSourceSupplierId())));
                    bloodNode.setDataType(Constant.STANDARD);
                    bloodNode.setTypeName(BloodlineNodeType.getNameByCode(Constant.STANDARD));
                    bloodNode.setId(sourceNodeId.toLowerCase());
                    bloodNode.setTaskId(queryBloodlineRelationInfo.getTaskUUID());
                    bloodNode.setVisible(nodeShow);
                    bloodNode.setObjectId(synlteObjectIdMap.getOrDefault(bloodNode.getSourceId().toLowerCase(),""));
                    // 查询出数据接入那边的节点信息
                    //  20200503 修改 查询数据接入那边所有的信息 输出的不是表名 而是协议名
                    if(!showType.equalsIgnoreCase(Constant.RIGHT)){
                        getStandNodeLeftNodes(bloodNode,pageId,queryId,bloodNodeList,edgesList);
                    }
                    //   节点展示的名称
                    if(StringUtils.isNotEmpty(queryBloodlineRelationInfo.getSourceChiName())){
                        bloodNode.setNodeName(queryBloodlineRelationInfo.getSourceChiName());
                    }else{
                        bloodNode.setNodeName(bloodNode.getSourceId()+"/"+bloodNode.getSourceCodeCh());
                    }
                    // 将字段信息写入到数据库中
                    insertColumnRelation(Constant.LEFT,sourceNodeId,queryBloodlineRelationInfo,Constant.STANDARD,oneNodeColumnMap,bloodNode.getNodeName());
                    // 将节点插入到数组中
                    bloodNodeList.add(bloodNode);
                    allDataMap.put(sourceNodeId ,1);
                    if(bloodNode.getSourceId().equalsIgnoreCase(queryStr)&&
                            !queryBloodlineRelationInfo.getSourceEngName().equalsIgnoreCase(queryBloodlineRelationInfo.getTargetEngName())){
                        bloodNode.setDataQueryType(Constant.MAIN);
                        if(StringUtils.isNotEmpty(queryNodeId)){
                            if(showType.equalsIgnoreCase(Constant.LEFT)){
                                if(bloodNode.getTargetDBEngName().equalsIgnoreCase(Constant.ODPS) ||
                                        bloodNode.getTargetDBEngName().equalsIgnoreCase(Constant.HIVE) ){
                                    addEdgesList(edgesList,bloodNode.getId().toLowerCase() ,queryNodeId.toLowerCase() ,Constant.STANDARD);
                                }
                            }else{
                                addEdgesList(edgesList,queryNodeId.toLowerCase() ,  bloodNode.getId().toLowerCase(),Constant.STANDARD);
                            }
                        }
                    }
                }

                if(allDataMap.getOrDefault(targetNodeId,0) == 0){
                    DataBloodlineNode.BloodNode rightbloodNode = new DataBloodlineNode.BloodNode();
                    rightbloodNode.setDataType(Constant.STANDARD);
                    rightbloodNode.setTypeName(BloodlineNodeType.getNameByCode(Constant.STANDARD));
                    rightbloodNode.setId(targetNodeId.toLowerCase());
                    rightbloodNode.setTableId(queryBloodlineRelationInfo.getTargetEngName().toUpperCase());
                    rightbloodNode.setTargetCode(queryBloodlineRelationInfo.getTargetSysId());
                    rightbloodNode.setTargetCodeCh(queryBloodlineRelationInfo.getTargetSysChiName());
                    rightbloodNode.setTableNameEn((queryBloodlineRelationInfo.getTargetProjectName()+"."+
                            queryBloodlineRelationInfo.getTargetTableName()).toUpperCase());
                    rightbloodNode.setTableNameCh(StringUtils.isNotBlank(queryBloodlineRelationInfo.getTargetChiName())?
                            queryBloodlineRelationInfo.getTargetChiName():"");
                    rightbloodNode.setTableProject(queryBloodlineRelationInfo.getTargetProjectName());
                    rightbloodNode.setOutputDatabaseType(queryBloodlineRelationInfo.getTargetDBEngName());
                    rightbloodNode.setTaskId(queryBloodlineRelationInfo.getTaskUUID());
                    rightbloodNode.setTargetDBEngName(queryBloodlineRelationInfo.getTargetDBEngName());
                    rightbloodNode.setVisible(nodeShow);
                    rightbloodNode.setObjectId(synlteObjectIdMap.getOrDefault(rightbloodNode.getTableId().toLowerCase(),""));
                    rightbloodNode.setTableIdCh(StringUtils.isNotBlank(queryBloodlineRelationInfo.getTargetChiName())?
                            queryBloodlineRelationInfo.getTargetChiName():"");
                    if(StringUtils.isNotEmpty(queryBloodlineRelationInfo.getTargetChiName())){
                        if(StringUtils.isNotEmpty(queryBloodlineRelationInfo.getTargetSysChiName())){
                            rightbloodNode.setNodeName(queryBloodlineRelationInfo.getTargetChiName()+"|"+queryBloodlineRelationInfo.getTargetSysChiName());
                        }else{
                            rightbloodNode.setNodeName(queryBloodlineRelationInfo.getTargetChiName());
                        }
                    }else if(StringUtils.isNotEmpty(queryBloodlineRelationInfo.getTargetTableName())){
                        rightbloodNode.setNodeName(rightbloodNode.getTableNameEn());
                    }else{
                        if(StringUtils.isNotEmpty(queryBloodlineRelationInfo.getTargetSysChiName())){
                            rightbloodNode.setNodeName(rightbloodNode.getTableId()+"|"+queryBloodlineRelationInfo.getTargetSysChiName());
                        }else{
                            rightbloodNode.setNodeName(rightbloodNode.getTableId());
                        }
                    }
                    insertColumnRelation(Constant.RIGHT,targetNodeId,queryBloodlineRelationInfo,Constant.STANDARD,oneNodeColumnMap,rightbloodNode.getNodeName());
                    bloodNodeList.add(rightbloodNode);
                    allDataMap.put(targetNodeId ,1);
                    if(rightbloodNode.getTableId().equalsIgnoreCase(queryStr) || rightbloodNode.getTableNameEn().equalsIgnoreCase(queryStr)
                            || rightbloodNode.getTableNameCh().equalsIgnoreCase(queryStr)){
                        if(StringUtils.isNotBlank(mainTableNameEn) &&
                                StringUtils.equalsIgnoreCase(rightbloodNode.getTableNameEn(),mainTableNameEn)){
                            rightbloodNode.setDataQueryType(Constant.MAIN);
                        }
                        if(StringUtils.isNotEmpty(queryNodeId)){
                            if(showType.equalsIgnoreCase(Constant.LEFT)){
                                if(rightbloodNode.getTargetDBEngName().equalsIgnoreCase(Constant.ODPS)||
                                        bloodNode.getTargetDBEngName().equalsIgnoreCase(Constant.HIVE) ){
                                    addEdgesList(edgesList,rightbloodNode.getId().toLowerCase() ,queryNodeId.toLowerCase() ,Constant.STANDARD);
                                }
                            }else{
                                addEdgesList(edgesList,queryNodeId.toLowerCase() ,  rightbloodNode.getId().toLowerCase(),Constant.STANDARD);
                            }
                        }
                    }
                    // 查询输出节点的下一个流程,只查询ODPS类型的
                    // 这个是查询下一个流程的信息  目前只能是没有子节点，才能
                    // 20200502 修改需求  目前查询另一个接口 来查询下一个流程第一个节点信息
                    // 20210701 ads的数据需要查询应用血缘的信息
                    getStandNodeRightNodes(queryBloodlineRelationInfo,showType,rightbloodNode,pageId,queryId,bloodNodeList,edgesList);
                }
                // 判断是否把边的信息插入到数据库中
                if(queryBloodlineRelationInfo.getSourceSupplierId().equals("0")||
                        queryBloodlineRelationInfo.getSourceSupplierId().equals("5")){
                    List<String> ids = sourceMap.getOrDefault((queryBloodlineRelationInfo.getSourceEngName()+"|"+queryBloodlineRelationInfo.getSourceSysId()).toUpperCase(),null);
                    if(ids != null){
                        boolean ownFlag= false;
                        for(String id:ids){
                            List<String> lists = Arrays.asList(id.split("\\|"));
                            String dbEngName = "";
                            if(lists.size() >3){
                                dbEngName = Arrays.asList(id.split("\\|")).get(3);
                            }
                            if(StringUtils.isNotEmpty(dbEngName) &&
                                    dbEngName.equalsIgnoreCase(queryBloodlineRelationInfo.getTargetDBEngName())
                                    && !id.equalsIgnoreCase(targetNodeId)){
                                boolean addFlag = addEdgesList(edgesList,id.toLowerCase() , targetNodeId.toLowerCase(),Constant.STANDARD);
                                // 将字段对应关系写入到数据库中
                                inserColumnRelation(id,targetNodeId,queryBloodlineRelationInfo,relationColumnMap);
                                ownFlag = true;
                                // 如果选择这个节点当做前置节点，则需要删除之前插入的节点信息
                                if(StringUtils.isNotEmpty(bloodNode.getSourceId()) && addFlag){
                                    bloodNodeList.remove(bloodNode);
                                    allDataMap.put(sourceNodeId ,0);
                                }
                            }
                        }
                        if(!ownFlag){
                            // 将字段对应关系写入到数据库中
                            inserColumnRelation(sourceNodeId,targetNodeId,queryBloodlineRelationInfo,relationColumnMap);
                            addEdgesList(edgesList,sourceNodeId.toLowerCase() , targetNodeId.toLowerCase(),Constant.STANDARD);
                        }
                    }else{
                        // 将字段对应关系写入到数据库中
                        inserColumnRelation(sourceNodeId,targetNodeId,queryBloodlineRelationInfo,relationColumnMap);
                        addEdgesList(edgesList,sourceNodeId.toLowerCase() , targetNodeId.toLowerCase(),Constant.STANDARD);
                    }
                }else{
                    // 将字段对应关系写入到数据库中
                    inserColumnRelation(sourceNodeId,targetNodeId,queryBloodlineRelationInfo,relationColumnMap);
                    addEdgesList(edgesList,sourceNodeId.toLowerCase() , targetNodeId.toLowerCase(),Constant.STANDARD);
                }
            });
        }catch (Exception e){
            logger.error("解析标准化返回结果报错"+ExceptionUtil.getExceptionTrace(e));
        }
        // 20210329 需要做数据处理的节点信息  存储这个节点的前置节点信息，共2个信息 showname 和id信息
        if(!bloodNodeList.isEmpty() && !edgesList.isEmpty()){
            bloodNodeList.forEach(d ->{
                if(StringUtils.equalsIgnoreCase(Constant.STANDARD,d.getDataType())){
                    // 获取该节点的后置数据处理节点
                    d.setPreDataProcessList(getPreDataProcessList(d.getId(),edgesList,bloodNodeList));
                }
            });
        }

        dataBloodlineNode.setNodes(bloodNodeList);
        dataBloodlineNode.setEdges(edgesList);
        return dataBloodlineNode;
    }


    /**
     * 从标准化中查询数据接入的节点信息
     * @param bloodNode
     * @param pageId
     * @param queryId
     * @param bloodNodeList
     * @param edgesList
     */
    private void getStandNodeLeftNodes(DataBloodlineNode.BloodNode bloodNode,
                                       String  pageId,String queryId,
                                       List<DataBloodlineNode.BloodNode> bloodNodeList,
                                       List<DataBloodlineNode.Edges> edgesList){
        QueryDataBloodlineTable queryDataInfo = new QueryDataBloodlineTable();
        queryDataInfo.setTableId(bloodNode.getSourceId());
        queryDataInfo.setPageId(pageId);
        queryDataInfo.setQueryId(queryId);
        DataBloodlineNode bloodNodeAccess = dataBloodlineLinkServiceImpl.getNextProcessOneNode(queryDataInfo,Constant.LEFT,1,Constant.STANDARD);
        if(bloodNodeAccess != null && bloodNodeAccess.getNodes() != null
                && bloodNodeAccess.getNodes().size() > 0){
            edgesList.addAll(bloodNodeAccess.getEdges());
            bloodNodeList.addAll(bloodNodeAccess.getNodes());
            // 还需要连接 数据加工和数据接入的节点信息
            for(DataBloodlineNode.BloodNode bloodNodeAccessChild:bloodNodeAccess.getNodes()){
                if(bloodNodeAccessChild.getTableId().equalsIgnoreCase(bloodNode.getSourceId())){
                    edgesList.add(new DataBloodlineNode.Edges(bloodNodeAccessChild.getId(),
                            bloodNode.getId(),true,Constant.ACCESS));
                }
            }
        }
    }

    private void getStandNodeRightNodes(QueryBloodlineRelationInfo queryBloodlineRelationInfo,
                                       String showType, DataBloodlineNode.BloodNode rightbloodNode,
                                        String pageId,String queryId,
                                        List<DataBloodlineNode.BloodNode> bloodNodeList,
                                        List<DataBloodlineNode.Edges> edgesList){
        DataBloodlineNode bloodNodeProcess = null;
        QueryDataBloodlineTable queryDataInfo = null;
        if(queryBloodlineRelationInfo.getTargetDBEngName().equalsIgnoreCase(Constant.ODPS)||
                queryBloodlineRelationInfo.getTargetDBEngName().equalsIgnoreCase(Constant.HIVE)){
            queryDataInfo = new QueryDataBloodlineTable();
            queryDataInfo.setTableId(rightbloodNode.getTableId());
            queryDataInfo.setPageId(pageId);
            queryDataInfo.setQueryId(queryId);
            // 20210226  需要根据输出的表名进行查询
            if(StringUtils.isNotBlank(queryBloodlineRelationInfo.getTargetTableName())){
                queryDataInfo.setTableNameEn(StringUtils.isNotBlank(queryBloodlineRelationInfo.getTargetProjectName())
                        ?queryBloodlineRelationInfo.getTargetProjectName()+"."+queryBloodlineRelationInfo.getTargetTableName()
                        : DataBaseProject.getTableNameByCode(queryBloodlineRelationInfo.getTargetDBEngName(),
                        queryBloodlineRelationInfo.getTargetTableName()));
            }else{
                queryDataInfo.setTableNameEn("");
            }
            bloodNodeProcess = dataBloodlineLinkServiceImpl.getNextProcessOneNode(queryDataInfo,Constant.RIGHT,1,Constant.STANDARD);
        }else if(StringUtils.containsIgnoreCase(queryBloodlineRelationInfo.getTargetDBEngName(),Constant.ADS)
                && StringUtils.isNotBlank(queryBloodlineRelationInfo.getTargetTableName())
                 && environment.getProperty("standardConnectOperatingFlag",Boolean.class,false) ){
            queryDataInfo = new QueryDataBloodlineTable();
            queryDataInfo.setTableId(rightbloodNode.getTableId());
            queryDataInfo.setPageId(pageId);
            queryDataInfo.setQueryId(queryId);
            queryDataInfo.setTableNameEn(queryBloodlineRelationInfo.getTargetTableName());
            bloodNodeProcess = dataBloodlineLinkServiceImpl.getNextProcessOneNode(queryDataInfo,Constant.RIGHT,2,Constant.STANDARD);
        }
        if(bloodNodeProcess != null &&
                bloodNodeProcess.getNodes() != null
                && bloodNodeProcess.getNodes().size() > 0){
            bloodNodeList.addAll(bloodNodeProcess.getNodes());
            for(DataBloodlineNode.BloodNode node:bloodNodeProcess.getNodes()){
                edgesList.add(new DataBloodlineNode.Edges(rightbloodNode.getId(),node.getId(),true,Constant.STANDARD));
            }
        }
    }


    /**
     * 获取该节点的所有前置节点id信息
     * 20210415 变成后置节点
     * @param id
     * @param edges
     * @param nodes
     * @return
     */
    private List<PreDataProcess> getPreDataProcessList(String id,List<DataBloodlineNode.Edges> edges,
                                                       List<DataBloodlineNode.BloodNode> nodes){
        List<PreDataProcess> list = new ArrayList<>();
        try{
            edges.stream().filter( d-> StringUtils.equalsIgnoreCase(d.getSource(),id)).forEach( d->{
                try{
                    PreDataProcess preDataProcess = new PreDataProcess();
                    preDataProcess.setTableId(d.getTarget().split("\\|")[0]);
                    Optional<DataBloodlineNode.BloodNode> data1 = nodes.stream().filter( d1-> StringUtils.equalsIgnoreCase(
                            d1.getId(),d.getTarget())).findFirst();
                    if(data1.isPresent()){
                        preDataProcess.setShowName(data1.get().getNodeName());
                        preDataProcess.setTableIdCh(StringUtils.isBlank(preDataProcess.getShowName())?"":
                                preDataProcess.getShowName().split("\\|")[0]);
                    }else{
                        preDataProcess.setShowName(d.getTarget());
                        preDataProcess.setTableIdCh("");
                    }
                    // 获取 tableId对应的objectId信息
                    preDataProcess.setObjectId(synlteObjectIdMap.getOrDefault(preDataProcess.getTableId().toLowerCase(),""));

                    list.add(preDataProcess);
                }catch (Exception e){
                    logger.error("获取数据处理的前置节点报错："+ExceptionUtil.getExceptionTrace(e));
                }
            });
        }catch (Exception e){
            logger.error("获取数据处理的前置节点报错："+ExceptionUtil.getExceptionTrace(e));
        }
        return list;
    }


    private Boolean addEdgesList(List<DataBloodlineNode.Edges> edgesList ,String sourceNodeId ,
                              String targetNodeId,String nodeType){
        Boolean insertType = true;
        for(DataBloodlineNode.Edges edges:edgesList){
            if(edges.getSource().equalsIgnoreCase(sourceNodeId) &&
                    edges.getTarget().equalsIgnoreCase(targetNodeId)){
                insertType = false;
            }
        }
        if(insertType){
            edgesList.add(new DataBloodlineNode.Edges(sourceNodeId , targetNodeId,true,nodeType));
        }
        return insertType;
    }

    private void inserColumnRelation(String sourceId,String targetId,
                                     QueryBloodlineRelationInfo oneData,Map<String , List<NodeColumnRelation>> relationColumnMap){
        try{
//            logger.info("开始将字段数据写入到数据库中");
            List<NodeColumnRelation> nodeColumnRelationList = new ArrayList<>();
                //将字段的关系表插入到数据库中
            for(QueryBloodlineRelationInfo.ColumnRelation columnRelation: oneData.getFieldsRelation()){
                NodeColumnRelation nodeColumnRelation = new NodeColumnRelation();
                nodeColumnRelation.setId(UUID.randomUUID().toString());
                nodeColumnRelation.setSourceNodeId(sourceId);
                nodeColumnRelation.setSourceColumnName(columnRelation.getSourceColumnName());
                nodeColumnRelation.setTargetColumnName(columnRelation.getTargetColumnName());
                nodeColumnRelation.setTargetNodeId(targetId);
                nodeColumnRelationList.add(nodeColumnRelation);
            }
            //   根据 sourceId  targetId 删除数据库中指定的数据
            if(nodeColumnRelationList.size() > 0){
                List<NodeColumnRelation>  columnRelationDBList = relationColumnMap.getOrDefault((sourceId+"|"+targetId).toUpperCase(),new ArrayList<>());
                columnRelationDBList.addAll(nodeColumnRelationList);
                relationColumnMap.put((sourceId+"|"+targetId).toUpperCase(),columnRelationDBList);
            }else{
                logger.info("获取到的数据信息为空");
            }
        }catch (Exception e){
            logger.error(ExceptionUtil.getExceptionTrace(e));
        }
    }


    /**
     *   数据接入血缘的解析程序
     * @param dataList
     * @param queryStr
     * @param showType
     * @param queryNodeId
     * @param oneNodeColumnMap
     * @param relationColumnMap
     * @param nodeShow
     * @return
     */
    private DataBloodlineNode parsingAccessReturnJson(List<QueryBloodlineRelationInfo> dataList,
                                                        String queryStr,
                                                        String showType,
                                                        String queryNodeId,
                                                        Map<String , List<ColumnRelationDB>> oneNodeColumnMap,
                                                        Map<String , List<NodeColumnRelation>> relationColumnMap,
                                                        Boolean nodeShow,String pageId,String queryId){

        logger.info("开始解析从数据接入那边获取到的接口数据，生成页面需要展示节点");
        //  可能存在多个目标表，所以所有数据都存到child里面  根据 输出平台的英文名称 来判断
        //
        DataBloodlineNode dataBloodlineNode = new DataBloodlineNode();
        List<DataBloodlineNode.BloodNode> bloodNodeList = new ArrayList<>();
        List<DataBloodlineNode.Edges> edgesList = new ArrayList<>();
        Map<String ,Integer> allDataMap = new HashMap<>();
        for(QueryBloodlineRelationInfo queryBloodlineRelationInfo:dataList){
           String sourceNodeId = Constant.ACCESS+"|"+queryBloodlineRelationInfo.getSourceEngName()+"|"+queryBloodlineRelationInfo.getSourceSysId()
                   + queryBloodlineRelationInfo.getSourceSupplierId();
            String targetNodeId = "";
           if(StringUtils.isNotEmpty(queryBloodlineRelationInfo.getTargetTableName())){
               targetNodeId = Constant.ACCESS+"|"+ queryBloodlineRelationInfo.getTargetProjectName()+"|"+queryBloodlineRelationInfo.getTargetTableName();
           }else{
               targetNodeId = Constant.ACCESS+"|"+queryBloodlineRelationInfo.getSourceEngName()+"|"+queryBloodlineRelationInfo.getSourceSysId()+"|0|file";
           }
            if(allDataMap.getOrDefault(sourceNodeId,0) == 0){
                DataBloodlineNode.BloodNode leftBloodNode = new DataBloodlineNode.BloodNode();
                leftBloodNode.setVisible(true);
                leftBloodNode.setDataType(Constant.ACCESS);
                leftBloodNode.setId(sourceNodeId.toLowerCase());
                leftBloodNode.setTypeName(BloodlineNodeType.getNameByCode(Constant.ACCESS));
                leftBloodNode.setSourceId(queryBloodlineRelationInfo.getSourceEngName());
                if(StringUtils.isEmpty(queryBloodlineRelationInfo.getSourceSysId())){
                    continue;
                }
                leftBloodNode.setSourceCode(queryBloodlineRelationInfo.getSourceSysId());
                leftBloodNode.setSourceCodeCh(queryBloodlineRelationInfo.getSourceSysChiName());
                leftBloodNode.setSourceFactoryId(queryBloodlineRelationInfo.getSourceSupplierId());
                leftBloodNode.setSourceIdCh(queryBloodlineRelationInfo.getSourceChiName());
                leftBloodNode.setDataBaseId(queryBloodlineRelationInfo.getSourceDatabaseId());
                leftBloodNode.setDatabaseTableId(queryBloodlineRelationInfo.getSourceDatabaseTableId());
                leftBloodNode.setTaskName(queryBloodlineRelationInfo.getTaskName());
                leftBloodNode.setTaskType(queryBloodlineRelationInfo.getSourceDatabaseType());
                if(StringUtils.isNotEmpty(leftBloodNode.getSourceFactoryId())){
                    leftBloodNode.setSourceFactoryCh(ManufacturerName.getNameByIndex(Integer.parseInt(queryBloodlineRelationInfo.getSourceSupplierId())));
                }
                //   节点展示的名称
                if(StringUtils.isNotEmpty(queryBloodlineRelationInfo.getSourceChiName())){
                    leftBloodNode.setNodeName(queryBloodlineRelationInfo.getSourceChiName());
                }else{
                    leftBloodNode.setNodeName(leftBloodNode.getSourceId()+"/"+leftBloodNode.getSourceCodeCh()+"/"+leftBloodNode.getSourceFactoryCh());
                }
                insertColumnRelation(Constant.LEFT,sourceNodeId.toUpperCase(),queryBloodlineRelationInfo,Constant.ACCESS,oneNodeColumnMap,leftBloodNode.getNodeName());
                if(!showType.equalsIgnoreCase(Constant.LEFT) && (StringUtils.containsIgnoreCase(queryStr,leftBloodNode.getSourceId())
                        || leftBloodNode.getSourceIdCh().equalsIgnoreCase(queryStr))){
                    leftBloodNode.setDataQueryType(Constant.MAIN);
                }
                bloodNodeList.add(leftBloodNode);
                allDataMap.put(sourceNodeId ,1);
            }
            //如果存在表名 则需要右节点展示的是表名信息，如果不存在表名，则对应的是标准文件或普通文件
            if(allDataMap.getOrDefault(targetNodeId,0) == 0){
                if(StringUtils.isNotEmpty(queryBloodlineRelationInfo.getTargetTableName())){
                    DataBloodlineNode.BloodNode rightBloodNode = new DataBloodlineNode.BloodNode();
                    rightBloodNode.setDataType(Constant.ACCESS);
                    rightBloodNode.setVisible(true);
                    rightBloodNode.setId(targetNodeId.toLowerCase());
                    rightBloodNode.setTypeName(BloodlineNodeType.getNameByCode(Constant.ACCESS));
                    rightBloodNode.setTableNameEn(queryBloodlineRelationInfo.getTargetTableName());
                    rightBloodNode.setTableProject(queryBloodlineRelationInfo.getTargetProjectName());
                    rightBloodNode.setNodeName(queryBloodlineRelationInfo.getTargetProjectName()+"."+queryBloodlineRelationInfo.getTargetTableName());
                    // 直接查询数据加工的血缘关系
                    if(rightBloodNode.getTableNameEn().equalsIgnoreCase(queryStr)){
                        if(!showType.equalsIgnoreCase(Constant.LEFT)){
                            rightBloodNode.setDataQueryType(Constant.MAIN);
                        }
                        if(StringUtils.isNotEmpty(queryNodeId)){
                            if(showType.equalsIgnoreCase(Constant.LEFT)){
                                addEdgesList(edgesList,rightBloodNode.getId() ,queryNodeId ,Constant.ACCESS);
                            }else{
                                addEdgesList(edgesList,queryNodeId ,  rightBloodNode.getId(),Constant.ACCESS);
                            }
                        }
                    }
                    rightBloodNode.setDataBaseId(queryBloodlineRelationInfo.getTargetDatabaseId());
                    rightBloodNode.setDatabaseTableId(queryBloodlineRelationInfo.getTargetDatabaseTableId());
                    rightBloodNode.setTaskName(queryBloodlineRelationInfo.getTaskName());
                    rightBloodNode.setTaskType(queryBloodlineRelationInfo.getTargetDatabaseType());
                    bloodNodeList.add(rightBloodNode);
                    // 之后根据表名查询在数据加工的血缘关系
                    // 20200429 不需要查询上一个流程/下一个流程的数据 产品需求
                    if(StringUtils.isNotEmpty(rightBloodNode.getTableNameEn()) && !showType.equalsIgnoreCase(Constant.LEFT)) {
                        // 20200503 修改获取下一个流程的节点  这个查询数据加工那边是否有节点信息
                        QueryDataBloodlineTable queryDataInfo = new QueryDataBloodlineTable();
                        queryDataInfo.setTableNameEn(rightBloodNode.getNodeName());
                        DataBloodlineNode bloodNodeProcess = dataBloodlineLinkServiceImpl.getNextProcessOneNode(queryDataInfo,Constant.RIGHT,2,Constant.ACCESS);
                        if(bloodNodeProcess != null && bloodNodeProcess.getNodes() != null
                                && bloodNodeProcess.getNodes().size() > 0){
                            bloodNodeList.addAll(bloodNodeProcess.getNodes());
                            for(DataBloodlineNode.BloodNode node:bloodNodeProcess.getNodes()){
                                edgesList.add(new DataBloodlineNode.Edges(rightBloodNode.getId(),node.getId(),true,Constant.STANDARD));
                            }
                        }
                    }
                    insertColumnRelation(Constant.RIGHT,targetNodeId.toUpperCase(),queryBloodlineRelationInfo,Constant.ACCESS,oneNodeColumnMap,rightBloodNode.getNodeName());
                    inserColumnRelation(sourceNodeId.toUpperCase(),targetNodeId.toUpperCase(),queryBloodlineRelationInfo,relationColumnMap);
                    addEdgesList(edgesList,sourceNodeId.toLowerCase() ,rightBloodNode.getId() ,Constant.ACCESS);
                }else{
                    DataBloodlineNode.BloodNode rightBloodNode = new DataBloodlineNode.BloodNode();
                    rightBloodNode.setDataType(Constant.ACCESS);
                    rightBloodNode.setVisible(true);
                    rightBloodNode.setId(targetNodeId.toLowerCase());
                    rightBloodNode.setTypeName(BloodlineNodeType.getNameByCode(Constant.ACCESS));
                    rightBloodNode.setTableId(queryBloodlineRelationInfo.getSourceEngName());
                    rightBloodNode.setTableIdCh(queryBloodlineRelationInfo.getSourceChiName());
                    rightBloodNode.setTargetCode(queryBloodlineRelationInfo.getSourceSysId());
                    rightBloodNode.setTargetCodeCh(queryBloodlineRelationInfo.getSourceSysChiName());
                    rightBloodNode.setNodeName(Common.GENERATE_FILE);
                    if(rightBloodNode.getTableId().equalsIgnoreCase(queryStr) || rightBloodNode.getTableIdCh().equalsIgnoreCase(queryStr)){
                        if(!showType.equalsIgnoreCase(Constant.LEFT)){
                            rightBloodNode.setDataQueryType(Constant.MAIN);
                        }
                        if(StringUtils.isNotEmpty(queryNodeId)){
                            if(showType.equalsIgnoreCase(Constant.LEFT)){
                                addEdgesList(edgesList,rightBloodNode.getId() ,queryNodeId ,Constant.ACCESS);
                            }else{
                                addEdgesList(edgesList,queryNodeId ,  rightBloodNode.getId(),Constant.ACCESS);
                            }
                        }
                    }
                    bloodNodeList.add(rightBloodNode);
                    insertColumnRelation(Constant.RIGHT,targetNodeId.toUpperCase(),queryBloodlineRelationInfo,Constant.ACCESS,oneNodeColumnMap,rightBloodNode.getNodeName());
                    inserColumnRelation(sourceNodeId.toUpperCase(),targetNodeId.toUpperCase(),queryBloodlineRelationInfo,relationColumnMap);
                    addEdgesList(edgesList,sourceNodeId.toLowerCase() ,rightBloodNode.getId() ,Constant.ACCESS);
                    // 查询下一个流程的接口 即标准的接口
                    // 20200429 不需要查询上一个流程/下一个流程的数据 产品需求
                    if(StringUtils.isNotEmpty(rightBloodNode.getTableId()) && !showType.equalsIgnoreCase(Constant.LEFT)) {
                        // 20200503 查询标准化那边的接口数据
                        QueryDataBloodlineTable queryDataInfo = new QueryDataBloodlineTable();
                        queryDataInfo.setSourceId(rightBloodNode.getTableId());
                        if(StringUtils.isNotBlank(rightBloodNode.getTableIdCh())){
                            queryDataInfo.setTableNameEn(rightBloodNode.getTableIdCh());
                        }else{
                            queryDataInfo.setTableNameEn(rightBloodNode.getTableId());
                        }
                        queryDataInfo.setSourceCode(rightBloodNode.getTargetCode());
                        DataBloodlineNode bloodNodeNext = dataBloodlineLinkServiceImpl.getNextProcessOneNode(queryDataInfo,Constant.RIGHT,1,Constant.ACCESS);
                        if(bloodNodeNext!=null && bloodNodeNext.getNodes()!=null&& bloodNodeNext.getNodes().size() >0){
                            bloodNodeList.addAll(bloodNodeNext.getNodes());
                            for(DataBloodlineNode.BloodNode bloodNode :bloodNodeNext.getNodes()){
                                edgesList.add(new DataBloodlineNode.Edges(rightBloodNode.getId(),bloodNode.getId(),true,Constant.STANDARD));
                            }
                        }
                    }
                }
            }

        }
        dataBloodlineNode.setEdges(edgesList);
        dataBloodlineNode.setNodes(bloodNodeList);
        return dataBloodlineNode;
    }


    /**
     *
     * @return
     */
    @Override
    public AllBloodCount getAllBloodCount(){
        AllBloodCount allBloodCount = new AllBloodCount();
        try{
            CompletableFuture future = CompletableFuture.supplyAsync(() ->{
                try{
                    DataBloodlineQueryParams queryParams = new DataBloodlineQueryParams();
                    queryParams.setQuerytype("like");
                    queryParams.setQueryinfo("");
                    RelationInfoRestTemolate restTemolate = restTemplateHandle.queryStandardRelationInfo(queryParams);
                    return restTemolate.getReqInfo().stream().map(d-> d.getSourceSysChiName()+d.getSourceEngName()
                            +d.getTargetEngName()+d.getTargetSysId()).distinct().count();
                }catch (Exception e){
                    logger.error("数据大屏获取数据处理那边所有数据量报错"+e.getMessage());
                    return 0;
                }
            });
            // 获取数据应用血缘的 数据量
            int applicationNum = dataBloodlineDao.getApplicationNum();
            allBloodCount.setApplicationCount(applicationNum);
            DataProcessBloodCache accessData = cacheManageDataBloodlineServiceImpl.getAllAccessBloodCache();
            allBloodCount.setAccessCount(accessData == null?0:accessData.getAllDataAccessRelationInfo() == null?0:accessData.getAllDataAccessRelationInfo().size());
            DataProcessBloodCache dataProcessData =  cacheManageDataBloodlineServiceImpl.getAllDataProcessBloodCache();
            allBloodCount.setDataProcessCount(dataProcessData == null?0:dataProcessData.getParentKeyData() == null?
                    0:dataProcessData.getParentKeyData().size());
            allBloodCount.setStandardCount(Long.parseLong(String.valueOf(future.get())));
        }catch (Exception e){
            logger.error("查询数据总量报错"+ExceptionUtil.getExceptionTrace(e));
        }
        return allBloodCount;
    }



}




