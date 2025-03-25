package com.synway.datarelation.service.datablood.impl;

import com.alibaba.fastjson.JSONObject;
import com.synway.datarelation.constant.Common;
import com.synway.datarelation.dao.datablood.DataBloodlineDao;
import com.synway.datarelation.constant.Constant;
import com.synway.datarelation.pojo.databloodline.ApplicationSystem;
import com.synway.datarelation.pojo.databloodline.DataBloodlineNode;
import com.synway.datarelation.pojo.databloodline.QueryDataBloodlineTable;
import com.synway.datarelation.pojo.databloodline.RelationshipNode;
import com.synway.datarelation.pojo.databloodline.impactanalysis.*;
import com.synway.datarelation.service.datablood.*;
import com.synway.common.exception.ExceptionUtil;
import com.synway.datarelation.util.RestTemplateHandle;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletResponse;
import java.text.Collator;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author wangdongwei
 * @ClassName ImpactAnalysisServiceImpl
 * @description TODO
 * @date 2020/12/3 11:17
 */
@Service
public class ImpactAnalysisServiceImpl implements ImpactAnalysisService {
    private Logger logger = LoggerFactory.getLogger(ImpactAnalysisServiceImpl.class);

    @Autowired
    private DataBloodlineDao dataBloodlineDao;


    @Autowired
    private CacheManageDataBloodlineService cacheManageDataBloodlineServiceImpl;

    @Autowired
    DataBloodlineLinkService dataBloodlineLinkServiceImpl;

    @Autowired
    private ConcurrentHashMap<String,String> parameterMap;


    @Autowired
    DataBloodlineService dataBloodlineServiceImpl;

    @Autowired
    RestTemplateHandle restTemplateHandle;

    /**
     *  开始查询数据加工血缘中的指定表的影响因素
     *  想法是在数据血缘中重新查询 这个表的血缘关系 将相关信息写入到对象中
     *  20210303 1、汇总信息增加“间接设计工作流”和“间接设计应用系统”统计信息，分别统计层级>1的工作流数量和层级>1的应用系统数量。
     *  上/下游表： 增加“生产阶段”、“协议名”、“数据日增量”显示
     *  工作流详情 ：增加“层级”显示，直接参与加工的工作流作的层级为1级，下游表N级表参与加工的工作流作的层级为N+1级。
     * @param tableName
     * @return
     * @throws Exception
     */
    @Override
    public ImpactAnalysis searchImpactAnalysisByTableName(String tableName) throws Exception {
        ImpactAnalysis impactAnalysis = new ImpactAnalysis();
        Map<String,Integer> tableExitMap = new HashMap<>();


        // 先判断缓存中数据是否存在，如果不存在，则查询缓存数据
        if(cacheManageDataBloodlineServiceImpl.getAllDataProcessBloodCache().getChildKeyData() == null
                || cacheManageDataBloodlineServiceImpl.getAllDataProcessBloodCache().getChildKeyData().size() == 0){
            logger.info("数据加工血缘缓存中数据不存在，开始查询缓存数据");
            cacheManageDataBloodlineServiceImpl.getDataProcessDataBloodCache();
        }
        List<StreamTableLevel> downStreamTableLevel = new ArrayList<>();
        List<WorkFlowInformation> workFlowInformation = new ArrayList<>();
//        List<String> nodeNameList = new ArrayList<>();
        List<StreamTableLevel> upStreamTableLevel = new ArrayList<>();
        List<ApplicationBloodline> applicationBloodlines = new ArrayList<>();

        //1: 开始查询表应用血缘的相关信息 直接从应用血缘的缓存中查询  这个是直接应用血缘信息，
        // 20210303 还需要查询间接应用血缘信息 查询下游表之后获取应用血缘的信息
        List<ApplicationBloodline> oldApplicationBloodlines = getApplicationSystemList(tableName,1);

        String dataPlatFormType = parameterMap.getOrDefault(Common.DATA_PLAT_FORM_TYPE,"");
        String dataBaseType =  Constant.HUA_WEI_YUN.equalsIgnoreCase(dataPlatFormType)?"hive":"odps";
        // 2: 以这个表为主节点查询下游表的相关信息，以这个表为输入节点查询第一层的工作流信息以及它是否存在应用血缘的信息
        // 20210303 不再只查询第一层的工作流信息
        queryImpactAnalysisCache(tableName,workFlowInformation,downStreamTableLevel,0,Constant.RIGHT,
                tableExitMap,dataBaseType,applicationBloodlines);
        // 3: 以这个表为主节点查询上游表的相关信息，以这个表为输出节点查询第一层的工作流信息
        queryImpactAnalysisCache(tableName,workFlowInformation,upStreamTableLevel,0,Constant.LEFT,
                tableExitMap,dataBaseType,null);
        int num = 1;
        for(ApplicationBloodline applicationSystem :applicationBloodlines){
            applicationSystem.setRecno(num);
            num++;
        }
        // 获取到objectId的值
        getObjectIdByTableId(downStreamTableLevel);
        getObjectIdByTableId(upStreamTableLevel);

        //20210304  查询这张表的日增数据量，如果是全量表，则查询表数据总量，如果是分区表，则查询昨日分区的数据量
        // 调用马佳的相关接口 查询这些数据，然后将数据写入到列表中
        setTableCountByProperty(downStreamTableLevel);
        setTableCountByProperty(upStreamTableLevel);

        // 4: 相关汇总信息
        AnalysisResults analysisResults = new AnalysisResults();
        OptionalInt downLevel = downStreamTableLevel.stream().mapToInt(StreamTableLevel::getLevels).max();
        OptionalInt upLevel = upStreamTableLevel.stream().mapToInt(StreamTableLevel::getLevels).max();
        analysisResults.setDownStreamLevels(downLevel.isPresent()?downLevel.getAsInt():0);
        analysisResults.setUpStreamLevels(upLevel.isPresent()?upLevel.getAsInt():0);
        analysisResults.setDownStreamTables(downStreamTableLevel.size());
        analysisResults.setUpStreamTables(upStreamTableLevel.size());
        // 应用系统的数据量
        // 存储直接涉及的应用系统的信息
        oldApplicationBloodlines.removeIf(d -> {
            return applicationBloodlines.stream().anyMatch(d1 -> d.hashCode() == d.hashCode());
        });
        int involvingWorkflowsCount = oldApplicationBloodlines.size();
        analysisResults.setInvolvingApplicationSystems(involvingWorkflowsCount);
        analysisResults.setIndirectApplicationSystems((applicationBloodlines.size() - involvingWorkflowsCount));
        applicationBloodlines.addAll(oldApplicationBloodlines);
        // 工作流的相关信息 需要筛选出第一层的工作流信息 然后获取数据量
        long involvingWorkflowCount = workFlowInformation.stream().filter(d -> 1 == d.getLevels()).count();
        analysisResults.setInvolvingWorkflows((int)involvingWorkflowCount);
        // 间接的工作流数据量
        analysisResults.setIndirectWorkflows(workFlowInformation.size() - analysisResults.getInvolvingWorkflows());
        impactAnalysis.setAnalysisResults(analysisResults);


        // 5: 具体的上下游表信息 应用血缘的信息
        impactAnalysis.setDownStreamTableLevel(downStreamTableLevel);
        impactAnalysis.setUpStreamTableLevel(upStreamTableLevel);
        impactAnalysis.setApplicationBloodline(applicationBloodlines);
        impactAnalysis.setWorkFlowInformation(workFlowInformation);
        // 6:  根据工作流名称，来查询项目名和创建时间，目前是先全部查询出来，然后再map缓存一下
        //  20201203 创建时间去除掉了 不想加上这个
//        try{
//            // 有可能会不存在这个表，所以需要单独加个try防止整体崩溃掉
//            // 根据工作流名称 来获取
//            String version = env.getProperty("dataPlatFormVersion","");
//            String handleClaStr = DataBaseType.getCla(dataPlatFormType,version);
//            DataInterfaceQuery dataInterfaceQuery = (DataInterfaceQuery)Class.forName(handleClaStr).newInstance();
//
//
//        }catch (Exception e){
//            logger.error("查询工作流的相关信息报错"+ExceptionUtil.getExceptionTrace(e));
//        }




        // 7:表格中需要筛选内容，需要填写相关数据  需要进行排序
        AnalysisTableFilter analysisTableFilter = new AnalysisTableFilter();

        if(applicationBloodlines != null){
            List<String> applicationNameFilter = applicationBloodlines.stream().map(ApplicationBloodline::getApplicationName)
                    .distinct().sorted((s1, s2) -> Collator.getInstance(Locale.CHINA).compare(s1, s2)).collect(Collectors.toList());
            analysisTableFilter.setApplicationNameFilter(applicationNameFilter);
        }
        List<Integer> downLevesFilter = downStreamTableLevel.stream().map(StreamTableLevel::getLevels).distinct().collect(Collectors.toList());
        analysisTableFilter.setDownLevesFilter(downLevesFilter);
        List<String> downTableProjectsFilter = downStreamTableLevel.stream().map(StreamTableLevel::getProjectName)
                .distinct().sorted((s1, s2) -> Collator.getInstance(Locale.CHINA).compare(s1, s2)).collect(Collectors.toList());
        analysisTableFilter.setDownTableProjectsFilter(downTableProjectsFilter);

        List<Integer> upLevesFilter = upStreamTableLevel.stream().map(StreamTableLevel::getLevels).distinct().collect(Collectors.toList());
        analysisTableFilter.setUpLevesFilter(upLevesFilter);
        List<String> upTableProjectsFilter = upStreamTableLevel.stream().map(StreamTableLevel::getProjectName)
                .distinct().sorted((s1, s2) -> Collator.getInstance(Locale.CHINA).compare(s1, s2)).collect(Collectors.toList());
        analysisTableFilter.setUpTableProjectsFilter(upTableProjectsFilter);

        List<String>  workflowProjectFilter = workFlowInformation.stream().map(WorkFlowInformation::getProjectName)
                .distinct().sorted((s1, s2) -> Collator.getInstance(Locale.CHINA).compare(s1, s2)).collect(Collectors.toList());
        analysisTableFilter.setWorkflowProjectFilter(workflowProjectFilter);

        // 生产阶段的筛选信息
        List<String> upStageFilter =  upStreamTableLevel.stream().map(StreamTableLevel::getStage)
                .distinct().sorted((s1, s2) -> Collator.getInstance(Locale.CHINA).compare(s1, s2)).collect(Collectors.toList());
        analysisTableFilter.setUpStageFilter(upStageFilter);

        List<String> downStageFilter =  downStreamTableLevel.stream().map(StreamTableLevel::getStage)
                .distinct().sorted((s1, s2) -> Collator.getInstance(Locale.CHINA).compare(s1, s2)).collect(Collectors.toList());
        analysisTableFilter.setDownStageFilter(downStageFilter);

        impactAnalysis.setAnalysisTableFilter(analysisTableFilter);
        logger.info("查询表"+tableName+"到的影响因素信息的数据为："+JSONObject.toJSONString(impactAnalysis));

        // 8: 清除中间数据
        tableExitMap.clear();
        return impactAnalysis;
    }

    /**
     *  获取objectid的值
     * @param streamTableLevelList
     */
    private void getObjectIdByTableId(List<StreamTableLevel> streamTableLevelList){
        try{
            for(StreamTableLevel streamTableLevel :streamTableLevelList){
                if(StringUtils.isNotBlank(streamTableLevel.getTableId())){
                    String objectId = dataBloodlineDao.getObjectIdbyTableId(streamTableLevel.getTableId());
                    streamTableLevel.setObjectId(objectId);
                }
            }
        }catch (Exception e){
            logger.error("获取objectId报错"+ExceptionUtil.getExceptionTrace(e));
        }

    }

    /**
     * 根据表名获取应用血缘信息
     * @param tableName  表英文名
     * @return  返回这张表的所有应用血缘信息
     */
    public List<ApplicationBloodline> getApplicationSystemList(String tableName,int levels){
        List<ApplicationBloodline> applicationBloodlines = new ArrayList<>();
        String[] tableNameEns = tableName.split("\\.");
        List<ApplicationSystem> applicationSystemList = cacheManageDataBloodlineServiceImpl.getApplicationSystemCache(
                tableNameEns.length ==2?tableNameEns[1].toUpperCase():tableNameEns[0]);
        int recno = 1;
        if(applicationSystemList != null && !applicationSystemList.isEmpty()){
            for(ApplicationSystem bloodNode:applicationSystemList){
                ApplicationBloodline applicationBloodline = new ApplicationBloodline();
                applicationBloodline.setApplicationName(bloodNode.getApplicationName());
                //  一人多号分析(同人分析)//
                String[] subModuleNames = bloodNode.getSubModuleName().split("/");
                if(subModuleNames.length >1){
                    applicationBloodline.setSubModuleName(subModuleNames[0]);
                    applicationBloodline.setLowestModuleName(subModuleNames[1]);
                }else if(subModuleNames.length == 1){
                    applicationBloodline.setSubModuleName(subModuleNames[0]);
                    applicationBloodline.setLowestModuleName("");
                }else{
                    applicationBloodline.setSubModuleName("");
                    applicationBloodline.setLowestModuleName("");
                }
                applicationBloodline.setRecno(recno);
                applicationBloodline.setLevels(levels);
                applicationBloodline.setLevelCh(levels == 1?Common.DIRECT:Common.IN_DIRECT);
                applicationBloodlines.add(applicationBloodline);
                recno ++;
            }
        }
        return applicationBloodlines;
    }



    @Override
    public void downloadExcel(HttpServletResponse response, AnalysisExportData analysisExportData)
            throws Exception{


    }


    /**
     *  生成excel文件的表头
     * @param
     * @return
     */
    @Override
    public void downloadExcel(AnalysisExportData analysisExportData, List<String> tableNameCh,
                              List<List<String>> list, List<Object> dataList){
        List<Object> pageData = analysisExportData.getDataList();
        switch(analysisExportData.getDataType().toLowerCase()){
            case AnalysisExportData.DOWN_STREAM_TABLE:
                tableNameCh.add("下游表");
                list.add(Collections.singletonList("序号"));
                list.add(Collections.singletonList("层级"));
                list.add(Collections.singletonList("生产阶段"));
                list.add(Collections.singletonList("平台类型"));
                list.add(Collections.singletonList("项目名"));
                list.add(Collections.singletonList("英文名"));
                list.add(Collections.singletonList("中文名"));
                list.add(Collections.singletonList("表协议ID"));
                list.add(Collections.singletonList("日增数据量"));
                list.add(Collections.singletonList("OBJECTID"));
                if(pageData== null || pageData.isEmpty()){
                    break;
                }
                for(Object data:pageData){
                    StreamTableLevel streamTableLevel = JSONObject.parseObject(JSONObject.toJSONString(data),StreamTableLevel.class);
                    dataList.add(streamTableLevel);
                }
                break;
            case AnalysisExportData.UP_STREAM_TABLE:
                tableNameCh.add("上游表");
                list.add(Collections.singletonList("序号"));
                list.add(Collections.singletonList("层级"));
                list.add(Collections.singletonList("生产阶段"));
                list.add(Collections.singletonList("平台类型"));
                list.add(Collections.singletonList("项目名"));
                list.add(Collections.singletonList("英文名"));
                list.add(Collections.singletonList("中文名"));
                list.add(Collections.singletonList("表协议ID"));
                list.add(Collections.singletonList("日增数据量"));
                list.add(Collections.singletonList("OBJECTID"));
                if(pageData== null || pageData.isEmpty()){
                    break;
                }
                for(Object data:pageData){
                    StreamTableLevel streamTableLevel = JSONObject.parseObject(JSONObject.toJSONString(data),StreamTableLevel.class);
                    dataList.add(streamTableLevel);
                }
                break;
            case AnalysisExportData.WORK_FLOW_INFORMATION:
                tableNameCh.add("工作流");
                list.add(Collections.singletonList("序号"));
                list.add(Collections.singletonList("关系层级"));
                list.add(Collections.singletonList("关系类型"));
                list.add(Collections.singletonList("项目名"));
                list.add(Collections.singletonList("工作流名"));
                if(pageData== null || pageData.isEmpty()){
                    break;
                }
                for(Object data:pageData){
                    WorkFlowInformation workFlowInformation = JSONObject.parseObject(JSONObject.toJSONString(data),WorkFlowInformation.class);
                    dataList.add(workFlowInformation);
                }
                break;
            case AnalysisExportData.APPLICATION_BLOODLINE:
                tableNameCh.add("应用系统");
                list.add(Collections.singletonList("序号"));
                list.add(Collections.singletonList("关系层级"));
                list.add(Collections.singletonList("关系类型"));
                list.add(Collections.singletonList("应用系统名称"));
                list.add(Collections.singletonList("功能模块名称"));
                list.add(Collections.singletonList("功能点名称"));
                if(pageData== null || pageData.isEmpty()){
                    break;
                }
                for(Object data:pageData){
                    ApplicationBloodline applicationBloodline = JSONObject.parseObject(JSONObject.toJSONString(data),ApplicationBloodline.class);
                    dataList.add(applicationBloodline);
                }
                break;
            default:
                logger.info("dataType数据为："+analysisExportData.getDataType()+"没有编写表头的方法");
                tableNameCh.add("dataType数据为："+analysisExportData.getDataType()+"没有编写表头的方法");
                break;
        }
    }


    /**
     *  向右查询这张表的影响因素的
     *  20210303 需要获取间接的应用血缘信息，需要相关的血缘信息
     * @param tableName  需要查询的表名
     * @param workFlowInformationList  存储的工作流信息 只有工作流信息，其它的需要去匹配
     * @param streamTableLevel 上游表/下级表的存储信息
     * @param flagNum  层级 当是0时表示为查询节点
     * @param queryType left:查询上游表的相关信息，right：查询下游表的相关信息
     * @param  tableMap  防止出现无限迭代的情况
     * @param  applicationBloodlines 如果为null 则不需要获取对应的应用血缘信息
     */
    @Override
    public String queryImpactAnalysisCache(String tableName,
                                           List<WorkFlowInformation> workFlowInformationList,
                                           List<StreamTableLevel> streamTableLevel,
                                           int flagNum ,
                                           String queryType,
                                           Map<String,Integer> tableMap,
                                           String dataBaseType,
                                           List<ApplicationBloodline> applicationBloodlines) throws Exception{
        tableMap.put(tableName.toLowerCase(),1);
        List<RelationshipNode> allDataProcessList = cacheManageDataBloodlineServiceImpl.getCheckRelationshipNodeList(queryType
                ,tableName);
        if(allDataProcessList.isEmpty()){
            logger.info("查询到表"+tableName+"的子节点为空");
            return "";
        }
        Map<String ,List<RelationshipNode>> allDataProcessMap = new HashMap<>();
        if(queryType.equalsIgnoreCase(Constant.LEFT)){
            allDataProcessMap = allDataProcessList.stream().filter(d ->
                    (StringUtils.isNotEmpty(d.getParentTN()))
            ).collect(Collectors.groupingBy(
                    d->(d.getChildrenTN()+"&& "+d.getChildrenTableId()+"&& "+d.getChildrenTableNameCh()+"&& "+d.getFlowName()
                            +"&& "+d.getNodeName() + "&&  "+ d.getDataType() )));
        }else{
            allDataProcessMap = allDataProcessList.stream().filter(d ->
                    StringUtils.isNotEmpty(d.getParentTN())
            ).collect(Collectors.groupingBy(
                    d->(d.getParentTN()+"&& "+d.getParentTableId()+"&& "+d.getParentTableNameCh() +"&& "+d.getFlowName()
                            +"&& "+d.getNodeName() + "&&  "+ d.getDataType() )));
        }
        flagNum ++;
        for(String parentNodeName:allDataProcessMap.keySet()){
            // 父节点不需要存储，直接拿子节点的表名
            List<RelationshipNode> oneRelationshipNode = allDataProcessMap.get(parentNodeName);
            for(RelationshipNode relationshipNode:oneRelationshipNode){
                String tableNameKey = Constant.RIGHT.equals(queryType)?
                        relationshipNode.getChildrenTN().trim().toLowerCase():relationshipNode.getParentTN().trim().toLowerCase();
                String tabNameChKey = Constant.RIGHT.equals(queryType)?
                        relationshipNode.getChildrenTableNameCh().trim():relationshipNode.getParentTableNameCh().trim();
                String tableId = Constant.RIGHT.equals(queryType)?
                        relationshipNode.getChildrenTableId():relationshipNode.getParentTableId();
                if(tableMap.getOrDefault(tableNameKey,0) != 0){
                    continue;
                }
                // 先存储上游/下游表的相关信息
                StreamTableLevel streamTableLevel1 = new StreamTableLevel();
                String[] tableNameEn = tableNameKey.split("\\.");
                if(tableNameEn.length == 2){
                    streamTableLevel1.setTableNameEn(tableNameEn[1]);
                    streamTableLevel1.setProjectName(tableNameEn[0]);
                }else{
                    streamTableLevel1.setTableNameEn(tableNameEn[0]);
                }
                streamTableLevel1.setLevels(flagNum);
                streamTableLevel1.setDataBaseType(dataBaseType);
                streamTableLevel1.setTableNameCh(tabNameChKey);
                streamTableLevel1.setRecno(streamTableLevel.size() +1);
                streamTableLevel1.setTableId(tableId);
                // 生产阶段
                streamTableLevel1.setStage(StreamTableLevel.DATAPROCESS);
                streamTableLevel.add(streamTableLevel1);

                // 表示这个是主节点上面的，需要存储工作流信息
                // 20210303  不再是只获取一个层级的工作流信息
                // 注释掉  flagNum == 1
                String[] nodeNameList = relationshipNode.getNodeName().split("\\.");
                WorkFlowInformation workFlowInformation = new WorkFlowInformation();
                if(nodeNameList.length == 2){
                    workFlowInformation.setProjectName(nodeNameList[0]);
                    workFlowInformation.setWorkFlowName(nodeNameList[1]);
                }else{
                    workFlowInformation.setProjectName("");
                    workFlowInformation.setWorkFlowName(nodeNameList[nodeNameList.length -1]);
                }
                workFlowInformation.setRecno(workFlowInformationList.size()+1);
                workFlowInformation.setLevels(flagNum);
                workFlowInformation.setLevelCh(flagNum == 1?Common.DIRECT:Common.IN_DIRECT);
                workFlowInformationList.add(workFlowInformation);

                int flagNumNew = flagNum;
                int tableOldSize = streamTableLevel.size();
                queryImpactAnalysisCache(tableNameKey,workFlowInformationList,streamTableLevel,
                        flagNumNew,queryType, tableMap,dataBaseType,applicationBloodlines);
                if(tableOldSize == streamTableLevel.size() ){
                    if(Constant.RIGHT.equalsIgnoreCase(queryType)&&
                            applicationBloodlines!= null){
                        // 这个表示是查询应用血缘的信息
                        List<ApplicationBloodline> data = getApplicationSystemList(tableNameKey,flagNumNew+1);
                        applicationBloodlines.addAll(data);
                    }else if(Constant.LEFT.equalsIgnoreCase(queryType)){
                        // 这个是查询左侧 数据处理的血缘信息，只需要那整条线的数据
                        QueryDataBloodlineTable queryData = new QueryDataBloodlineTable();
                        queryData.setTableNameEn(streamTableLevel1.getTableNameEn());
                        queryData.setDataType(Constant.STANDARD);
                        // 查询数据处理那边的接口
                        DataBloodlineNode bloodNode = dataBloodlineServiceImpl.getOneBloodlineNodeLink(queryData,
                                Constant.LEFT,"",true);
                        // 获取相关数据之后，再去除掉不需要的数据
                        if(bloodNode == null || bloodNode.getNodes().isEmpty() || bloodNode.getEdges().isEmpty()){
                            return null;
                        }else{
                            // 根据tableNameKey 来获取相关的节点id值，然后再根据这个值来获取整条流程上的节点信息
                            Optional<DataBloodlineNode.BloodNode> data = bloodNode.getNodes().stream().filter(d -> tableNameKey.equalsIgnoreCase(d.getTableNameEn())).findFirst();
                            if(data.isPresent()){
                                Set<String> dataExitsList = new HashSet<>();
                                queryDatastandardImpactAnalysis(streamTableLevel,bloodNode,data.get().getId(),flagNumNew + 1,dataExitsList);
                                dataExitsList.clear();
                            }
                        }
                    }
                }
            }
        }
        return null;
    }


    /**
     *  根据表英文名获取到数据加工的血缘信息之后，获取到主线上的血缘信息，
     *  但是最左边的那个源协议不需要展示 所以需要去除掉那个内容
     * @param streamTableLevel
     * @param bloodNode
     * @param nodeId
     */
    public String queryDatastandardImpactAnalysis(List<StreamTableLevel> streamTableLevel,
                                                DataBloodlineNode bloodNode,
                                                String nodeId,
                                                int levels,
                                                Set<String> dataExitsList){
        if(bloodNode == null || bloodNode.getNodes().isEmpty() || bloodNode.getEdges().isEmpty()){
            return "";
        }
        bloodNode.getEdges().stream().filter(d -> d.getTarget().equalsIgnoreCase(nodeId))
                .forEach(d ->{
                    Optional<DataBloodlineNode.BloodNode> data = bloodNode.getNodes().stream()
                            .filter(d1-> d1.getId().equalsIgnoreCase(nodeId)).findFirst();
                    if(data.isPresent()){
                        StreamTableLevel streamTableLevel1 = new StreamTableLevel();
                        streamTableLevel1.setStage(StreamTableLevel.STANDARD);
                        streamTableLevel1.setTableNameEn(StringUtils.isBlank(data.get().getTableNameEn())?"":
                                data.get().getTableNameEn().replaceAll(data.get().getTableProject()+".",""));
                        streamTableLevel1.setProjectName(data.get().getTableProject());
                        streamTableLevel1.setTableId(data.get().getTableId());
                        streamTableLevel1.setLevels(levels);
                        streamTableLevel1.setRecno(streamTableLevel.size() +1);
                        streamTableLevel1.setTableNameCh(data.get().getTableNameCh());
                        streamTableLevel1.setDataBaseType(data.get().getDataBaseType());
                        if(!dataExitsList.contains(data.get().getTableId())){
                            streamTableLevel.add(streamTableLevel1);
                        }
                        dataExitsList.add(data.get().getTableId());
                        String sourceId = d.getSource();
                        int levelsNew = levels;
                        levelsNew++;
                        queryDatastandardImpactAnalysis(streamTableLevel,bloodNode,sourceId,levelsNew,dataExitsList);
                    }
        });
        return "";
    }


    /**
     *  查询数据资产那边指定表的所有表数据量，
     * @param streamTableLevelList
     */
    private void setTableCountByProperty(List<StreamTableLevel> streamTableLevelList){
        try{
            if(streamTableLevelList ==null || streamTableLevelList.isEmpty()){
                throw new NullPointerException("传入的数组为空，查询失败");
            }
            List<ReceiveTable> paramList = new ArrayList<>();
            for(StreamTableLevel streamTableLevel:streamTableLevelList){
                ReceiveTable params = new ReceiveTable();
                params.setTableType("");
                params.setProjectName(streamTableLevel.getProjectName());
                params.setTableName(streamTableLevel.getTableNameEn());
                paramList.add(params);
            }
            List<DetailedTableByClassify> data = restTemplateHandle.getDetailedTable(paramList);
            Map<String,List<DetailedTableByClassify>> dataMap = data.stream().collect(Collectors.groupingBy(d ->
                    (d.getTableProject().replaceAll(d.getTableType()+"->","")+"."+d.getTableNameEn()).toUpperCase()));
            for(StreamTableLevel streamTableLevel:streamTableLevelList){
                List<DetailedTableByClassify> queryData = dataMap.getOrDefault((streamTableLevel.getProjectName()+"."
                        +streamTableLevel.getTableNameEn()).toUpperCase(),null);
                if(queryData != null && !queryData.isEmpty()){
                    streamTableLevel.setDailyIncreaseCount(StringUtils.isNotBlank(queryData.get(0).getYesterdayCount())?
                            queryData.get(0).getYesterdayCount()+"万条":"0万条");
                }
            }
        }catch (Exception e){
            logger.error("获取指定表的数据量报错："+ExceptionUtil.getExceptionTrace(e));
        }
    }

}
