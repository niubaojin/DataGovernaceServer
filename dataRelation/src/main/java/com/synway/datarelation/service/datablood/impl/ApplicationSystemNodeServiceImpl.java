package com.synway.datarelation.service.datablood.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.synway.datarelation.constant.BloodlineNodeType;
import com.synway.datarelation.constant.Common;
import com.synway.datarelation.constant.Constant;
import com.synway.datarelation.dao.datablood.DataBloodlineDao;
import com.synway.datarelation.pojo.common.*;
import com.synway.datarelation.pojo.databloodline.*;
import com.synway.datarelation.service.datablood.ApplicationSystemNodeService;
import com.synway.datarelation.service.datablood.CacheManageDataBloodlineService;
import com.synway.datarelation.service.datablood.DataBloodlineLinkService;
import com.synway.datarelation.service.datablood.DataBloodlineService;
import com.synway.datarelation.util.ExcelHelper;
import com.synway.common.exception.ExceptionUtil;
import com.synway.datarelation.util.StringUtilNew;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.Collator;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wangdongwei
 */
@Service
public class ApplicationSystemNodeServiceImpl implements ApplicationSystemNodeService {
    private Logger logger = LoggerFactory.getLogger(ApplicationSystemNodeServiceImpl.class);
    @Autowired
    DataBloodlineDao dataBloodlineDao;
    @Autowired
    DataBloodlineService dataBloodlineServiceImpl;
    @Autowired
    DataBloodlineLinkService dataBloodlineLinkServiceImpl;
    @Autowired
    private CacheManageDataBloodlineService cacheManageDataBloodlineServiceImpl;
    @Autowired
    private Environment environment;

    /**
     *   获取应用血缘的信息
     * @param clearFlag
     * @return
     */
    @Override
    public TreeNode getApplicationSystemNodeQuery(DataBloodlineQueryParams dataBloodlineQueryParams , int nodeId , Boolean clearFlag) {
        logger.info("查询参数为 dataBloodlineQueryParams:"+ JSONObject.toJSONString(dataBloodlineQueryParams)+" nodeId:"+nodeId);
        TreeNode threeTreeNode = new TreeNode();
        try{
            List<ApplicationSystem> applicationSystemList = dataBloodlineDao.getApplicationSystemByTableName(
                    dataBloodlineQueryParams.getQueryinfo(),4,
                    StringUtils.isNotBlank(dataBloodlineQueryParams.getQuerytype())?
                            dataBloodlineQueryParams.getQuerytype().toLowerCase():"like");
            // 解析相关的数据
            if(applicationSystemList ==null){
               throw new Exception("从数据库中获取到得数据为空");
            }
            for(ApplicationSystem applicationSystem:applicationSystemList){
                if(StringUtils.isNotEmpty(applicationSystem.getSubModuleName())){
                    List<String> modulesList = new ArrayList<>();
                    String[] subModules= applicationSystem.getSubModuleName().split("/");
                    for(String module:subModules){
                        if(StringUtils.isNotEmpty(module)){
                            modulesList.add(module);
                        }else{
                            break;
                        }
                    }
                    applicationSystem.setSubModuleNameLists(modulesList);
                }
            }
            // 获取大类下的所有节点信息
            threeTreeNode.setNodeId(1);
            List<TreeNode> threeChildTreeNode = new ArrayList<>();
            Map<String,List<ApplicationSystem>> applicationSystemMap = applicationSystemList.stream().filter(d -> StringUtils.isNotEmpty(d.getApplicationName())).
                    collect(Collectors.groupingBy(ApplicationSystem::getApplicationName));
            for(String applicationName:applicationSystemMap.keySet()){
                // 根据主模块来编写左侧tree的标签信息
                TreeNode childTreeNode = new TreeNode();
                childTreeNode.setNodeId(Math.abs(UUID.nameUUIDFromBytes(applicationName.getBytes()).hashCode()));
                childTreeNode.setLabel(applicationName+"("+applicationSystemMap.get(applicationName).size()+")");
                childTreeNode.setValue(applicationName);
                childTreeNode.setApplicationName(applicationName);
                childTreeNode.setType("module");
                childTreeNode.setNum(0);
                QueryDataBloodlineTable queryDataBloodlineTable = new QueryDataBloodlineTable();
                queryDataBloodlineTable.setDataType(Constant.OPERATINGSYSTEM);
                queryDataBloodlineTable.setApplicationName(applicationName);
                queryDataBloodlineTable.setSubModuleName("");
                queryDataBloodlineTable.setTableNameEn("");
                childTreeNode.setQueryDataBloodlineTable(queryDataBloodlineTable);
                List<TreeNode> treeNodeList = getChildModuleNode(applicationSystemMap.get(applicationName),0,nodeId,applicationName);

                childTreeNode.setChildren(treeNodeList);
                threeChildTreeNode.add(childTreeNode);
            }
            threeTreeNode.setLabel("应用血缘("+applicationSystemList.size()+")");
            threeTreeNode.setChildren(threeChildTreeNode);
            // 模糊查询出节点的信息  20200427 不需要缓存数据
            try{
                if(threeChildTreeNode.size() > 0){
                    // 添加缓存
                    cacheManageDataBloodlineServiceImpl.getApplicationDataBloodCache();
                }
            }catch (Exception e){
                logger.error("清除缓存中数据报错"+ ExceptionUtil.getExceptionTrace(e));
            }
        }catch (Exception e){
            threeTreeNode.setLabel("应用系统(0)");
            threeTreeNode.setErrorMessage("获取应用系统血缘报错"+e.getMessage());
            logger.error("获取应用系统血缘报错"+ExceptionUtil.getExceptionTrace(e));
        }
        return threeTreeNode;
    }


    /**
     * 迭代获取应用血缘关系的模块信息
     * @param childModulList  子模块对应的所有列表
     * @param num  查询第几个子模块
     * @param nodeId  node的节点id
     * @return
     */
   private List<TreeNode> getChildModuleNode(List<ApplicationSystem> childModulList,int num,int nodeId,String applicationName){
       List<TreeNode> threeChildTreeNode = new ArrayList<>();
       final int numNew = num;
       // 还一直存在子模块标签的信息
       Map<String,List<ApplicationSystem>> childModuleMap = childModulList.stream().filter(d -> d.getSubModuleNameLists().size() > numNew).collect(
               Collectors.groupingBy(d->(d.getSubModuleNameLists().get(numNew))));
       // 这个表示是最后一层，直接是节点层的数据
       List<ApplicationSystem> lastNodule = childModulList.stream().filter(d -> d.getSubModuleNameLists().size() == numNew).collect(Collectors.toList());
       num = num+1;
       for(String subModuleName : childModuleMap.keySet()){
           nodeId = nodeId+1;
           ApplicationSystem oneData = childModuleMap.get(subModuleName).get(0);
           TreeNode childTreeNode = new TreeNode();
           childTreeNode.setLabel(subModuleName+"("+childModuleMap.get(subModuleName).size()+")");
           childTreeNode.setType("module");
           childTreeNode.setApplicationName(applicationName);
           childTreeNode.setSubModuleName(StringUtils.join(oneData.getSubModuleNameLists().subList(0,num),"/"));
           childTreeNode.setNum(num);
           childTreeNode.setNodeId(Math.abs(UUID.nameUUIDFromBytes((applicationName +childTreeNode.getSubModuleName()).getBytes()).hashCode()));
           QueryDataBloodlineTable queryDataBloodlineTable = new QueryDataBloodlineTable();
           queryDataBloodlineTable.setDataType(Constant.OPERATINGSYSTEM);
           queryDataBloodlineTable.setApplicationName(applicationName);
           queryDataBloodlineTable.setSubModuleName(StringUtils.join(oneData.getSubModuleNameLists().subList(0,num),"/"));
           queryDataBloodlineTable.setTableNameEn("");
           childTreeNode.setQueryDataBloodlineTable(queryDataBloodlineTable);
           List<TreeNode> treeNodeList = getChildModuleNode(childModuleMap.get(subModuleName), num, nodeId,applicationName);
           childTreeNode.setChildren(treeNodeList);
           threeChildTreeNode.add(childTreeNode);
       }
       for(ApplicationSystem applicationSystem:lastNodule){
           TreeNode lastChildNode = new TreeNode();
           nodeId = nodeId+1;
           // 此时表示已经到达子模块最后的位置，此时展示的节点信息是 具体的表
           QueryDataBloodlineTable queryDataBloodlineTable = new QueryDataBloodlineTable();
           queryDataBloodlineTable.setDataType(Constant.OPERATINGSYSTEM);
           queryDataBloodlineTable.setTableNameCh(applicationSystem.getTableNameCh());
           String showName = StringUtils.isNotEmpty(applicationSystem.getTableNameCh().trim())?applicationSystem.getTableNameCh():applicationSystem.getProject()+"."+applicationSystem.getTableNameEn();
           queryDataBloodlineTable.setTableShowData(showName);
           lastChildNode.setLabel(showName.toLowerCase());
           queryDataBloodlineTable.setApplicationName(applicationSystem.getApplicationName());
           queryDataBloodlineTable.setSubModuleName(applicationSystem.getSubModuleName());
           queryDataBloodlineTable.setTableNameEn(applicationSystem.getTableNameEn());
           lastChildNode.setQueryDataBloodlineTable(queryDataBloodlineTable);
           lastChildNode.setType("node");
           lastChildNode.setApplicationName(applicationName);
           lastChildNode.setSubModuleName(applicationSystem.getSubModuleName());
           lastChildNode.setNodeId(Math.abs(UUID.nameUUIDFromBytes((applicationName+applicationSystem.getSubModuleName()
                   +showName.toLowerCase()).getBytes()).hashCode()));
           threeChildTreeNode.add(lastChildNode);
       }
       return threeChildTreeNode;
   }



    /**
     * 查询应用血缘的节点信息
     * @param queryData
     * @param showType
     * @return
     */
    @Override
    public List<DataBloodlineNode.BloodNode> getApplicationSystemNode(QueryDataBloodlineTable queryData, String showType, Boolean nodeShow){
        logger.info("查询应用血缘的信息，查询参数queryData："+JSONObject.toJSONString(queryData)+" showType:"+showType);
        List<DataBloodlineNode.BloodNode> dataBloodlineNodeList = new ArrayList<>();
        if(StringUtils.isNotEmpty(queryData.getTableNameEn())){
            // 添加缓存
            if(cacheManageDataBloodlineServiceImpl.getAllApplicationSystemCache().keySet().size() == 0){
                cacheManageDataBloodlineServiceImpl.getApplicationDataBloodCache();
            }
            List<ApplicationSystem> applicationSystemList = cacheManageDataBloodlineServiceImpl.getApplicationSystemCache(queryData.getTableNameEn());
            logger.info("查询到的应用血缘信息为："+JSONObject.toJSONString(applicationSystemList));
            if(applicationSystemList != null){
                for(ApplicationSystem applicationSystem:applicationSystemList){
                    DataBloodlineNode.BloodNode oneDataNode = new  DataBloodlineNode.BloodNode();
                    oneDataNode.setId(applicationSystem.getApplicationName()+"/"+applicationSystem.getSubModuleName()+"/"+applicationSystem.getProject()+"."+applicationSystem.getTableNameEn());
                    oneDataNode.setDataType(Constant.OPERATINGSYSTEM);
                    oneDataNode.setTypeName(BloodlineNodeType.getNameByCode(Constant.OPERATINGSYSTEM));
                    oneDataNode.setDataQueryType(showType);
                    oneDataNode.setApplicationSystem(applicationSystem.getApplicationName());
                    oneDataNode.setSubModuleName(applicationSystem.getSubModuleName());
                    oneDataNode.setTableNameEn(applicationSystem.getProject()+"."+applicationSystem.getTableNameEn());
                    oneDataNode.setNodeName(applicationSystem.getApplicationName()+"/"+applicationSystem.getSubModuleName());
                    oneDataNode.setVisible(nodeShow);
                    dataBloodlineNodeList.add(oneDataNode);
                }
            }
        }else{
            logger.error("查询参数中没有英文表名，不能查询具体的应用血缘信息");
        }
        logger.info("返回的数据为："+JSONObject.toJSONString(dataBloodlineNodeList));
        return dataBloodlineNodeList;

    }

    /**
     * 查询应用血缘的相关信息 ,目前的需求是 主模块/子模块 点击之后都能查询到对应的应用血缘信息
     * @param queryData
     * @param showType
     * @param nodeShow
     * @return
     */
    @Override
    public DataBloodlineNode getApplicationSystemNodeLink(QueryDataBloodlineTable queryData, String showType, Boolean nodeShow) {
        logger.info("查询应用血缘的信息，查询参数queryData："+JSONObject.toJSONString(queryData)+" showType:"+showType);
        DataBloodlineNode dataBloodlineNode = new DataBloodlineNode();
        List<DataBloodlineNode.BloodNode> allNodeList = new ArrayList<>();
        List<DataBloodlineNode.Edges> allEdgeList = new ArrayList<>();
        if(StringUtils.isEmpty(queryData.getTableNameEn()) && StringUtils.isEmpty(queryData.getApplicationName())
                && StringUtils.isEmpty(queryData.getSubModuleName())) {
            logger.error("查询的参数为空，不能查询应用血缘的信息");
            return dataBloodlineNode;
        }else{
            List<ApplicationSystem> applicationSystemList = dataBloodlineDao.getApplicationSystemByTableName(
                    "",3,"like");
            if(applicationSystemList == null || applicationSystemList.size() == 0){
                logger.error("从数据库中获取到得信息为空，不能查询应用血缘的信息");
                return dataBloodlineNode;
            }
            for(ApplicationSystem applicationSystem:applicationSystemList){
                if(StringUtils.isNotEmpty(applicationSystem.getSubModuleName())){
                    List<String> modulesList = new ArrayList<>();
                    String[] subModules= applicationSystem.getSubModuleName().split("/");
                    for(String module:subModules){
                        if(StringUtils.isNotEmpty(module)){
                            modulesList.add(module);
                        }else{
                            break;
                        }
                    }
                    applicationSystem.setSubModuleNameLists(modulesList);
                }
            }
            Map<String,List<ApplicationSystem>> applicationSystemMap = new HashMap<>();
            if(StringUtils.isNotEmpty(queryData.getInputQueryStr())){
                // 20200506 新增表中文名，需要能用到这个内容
                Boolean chineseBoolean = StringUtilNew.isContainChinese(queryData.getInputQueryStr());
                applicationSystemMap  = applicationSystemList.stream().filter(d -> (
                               (StringUtils.isEmpty(queryData.getApplicationName())?true:d.getApplicationName().equalsIgnoreCase(queryData.getApplicationName()))&&
                                (StringUtils.isNotEmpty(queryData.getSubModuleName())?d.getSubModuleName().contains(queryData.getSubModuleName()):true)
                                && (StringUtils.isNotEmpty(queryData.getTableNameEn())?
                                d.getTableNameEn().equalsIgnoreCase(queryData.getTableNameEn()):true)
                                && (chineseBoolean?(d.getApplicationName().contains(queryData.getInputQueryStr()) || d.getSubModuleName().contains(queryData.getInputQueryStr())
                                     || d.getTableNameCh().contains(queryData.getInputQueryStr()))
                                :(d.getProject()+"."+d.getTableNameEn()).toLowerCase().contains(queryData.getInputQueryStr().toLowerCase()))
                )).collect(Collectors.groupingBy(ApplicationSystem::getApplicationName));
            }else{
                applicationSystemMap  = applicationSystemList.stream().filter(d -> (
                        (StringUtils.isEmpty(queryData.getApplicationName())?true:d.getApplicationName().equalsIgnoreCase(queryData.getApplicationName())) &&
                                (StringUtils.isNotEmpty(queryData.getSubModuleName())?d.getSubModuleName().contains(queryData.getSubModuleName()):true)
                                && (StringUtils.isNotEmpty(queryData.getTableNameEn())?
                                d.getTableNameEn().equalsIgnoreCase(queryData.getTableNameEn()):true)
                )).collect(Collectors.groupingBy(ApplicationSystem::getApplicationName));
            }
            for(String applicationName:applicationSystemMap.keySet()){
                // 主类的节点信息
                DataBloodlineNode.BloodNode bloodNode = new DataBloodlineNode.BloodNode();
                bloodNode.setDataType(Constant.OPERATINGSYSTEM);
                bloodNode.setTypeName(BloodlineNodeType.getNameByCode(Constant.OPERATINGSYSTEM));
                bloodNode.setModuleClassify(applicationName);
                bloodNode.setModuleClassifyNum(0);
                bloodNode.setNodeName(applicationName);
                bloodNode.setId((Constant.OPERATINGSYSTEM+"_"+applicationName+"_0").toLowerCase());
                if(StringUtils.isEmpty(queryData.getSubModuleName())){
                    bloodNode.setDataQueryType(Constant.MAIN);
                }
                getChildApplicationSystemNode(applicationSystemMap.get(applicationName),allNodeList,allEdgeList,0,bloodNode.getId(),applicationName,queryData);
                allNodeList.add(bloodNode);
            }
        }
        dataBloodlineNode.setNodes(allNodeList);
        dataBloodlineNode.setEdges(allEdgeList);
        return dataBloodlineNode;
    }

    @Override
    public SummaryData searchSummaryData() {
        SummaryData summaryData = dataBloodlineDao.searchSummaryData();
        return summaryData;
    }

    @Override
    public ApplicationTableManage searchApplicationBloodTable(BloodManagementQuery bloodManagementQuery) {
        ApplicationTableManage applicationTableManage =new ApplicationTableManage();
        if(StringUtils.isNotBlank(bloodManagementQuery.getSort())){
            bloodManagementQuery.setSort(bloodManagementQuery.getSort().toLowerCase());
        }
        List<ApplicationSystemTable> applicationSystemTableList = dataBloodlineDao.searchApplicationBloodTable(bloodManagementQuery);
//        List<ApplicationSystemTable> allApplicationData = dataBloodlineDao.searchAllData();
        // 切分一级/二级/三级模块数据
        List<ApplicationSystemTable> applicationSystemTableList1 = new ArrayList<>();
        for(ApplicationSystemTable applicationSystemTable:applicationSystemTableList){
            // 一级模块
            String oneLevelModule = applicationSystemTable.getOneLevelModule();
            String[] oneLevelModuleList = oneLevelModule.split("/");
            applicationSystemTable.setOneLevelModule(oneLevelModuleList.length >=1?oneLevelModuleList[0]:"");
            applicationSystemTable.setTwoLevelModule(oneLevelModuleList.length >=2?oneLevelModuleList[1]:"");
            applicationSystemTable.setThreeLevelModule(oneLevelModuleList.length >=3?oneLevelModuleList[2]:"");
            // 筛选相关信息
            if(tableDataFilter(bloodManagementQuery,applicationSystemTable)){
                applicationSystemTableList1.add(applicationSystemTable);
            }
        }
        /**
         * 筛选的相关内容
         */
//        for(ApplicationSystemTable applicationSystemTable:allApplicationData){
//            String oneLevelModule = applicationSystemTable.getOneLevelModule();
//            String[] oneLevelModuleList = oneLevelModule.split("/");
//            applicationSystemTable.setOneLevelModule(oneLevelModuleList.length >=1?oneLevelModuleList[0]:"");
//            applicationSystemTable.setTwoLevelModule(oneLevelModuleList.length >=2?oneLevelModuleList[1]:"");
//            applicationSystemTable.setThreeLevelModule(oneLevelModuleList.length >=3?oneLevelModuleList[2]:"");
//        }

        if(Common.SORT_COLUMNS.contains(bloodManagementQuery.getSort()+",")
                && Common.ASC.equalsIgnoreCase(bloodManagementQuery.getSortOrder())){
            applicationSystemTableList1.sort(Comparator.comparing(d ->{
                if(Common.ONE_LEVEL_MODULE.equalsIgnoreCase(bloodManagementQuery.getSort())){
                    return d.getOneLevelModule();
                }else if(Common.TWO_LEVEL_MODULE.equalsIgnoreCase(bloodManagementQuery.getSort())){
                    return d.getTwoLevelModule();
                }else{
                    return d.getThreeLevelModule();
                }
            }));
        }else if(Common.SORT_COLUMNS.contains(bloodManagementQuery.getSort()+",")
                && Common.DESC.equalsIgnoreCase(bloodManagementQuery.getSortOrder())){
            ObjectMapper objectMapper= new ObjectMapper();
            applicationSystemTableList1.sort(Comparator.comparing(d ->{
                if(Common.ONE_LEVEL_MODULE.equalsIgnoreCase(bloodManagementQuery.getSort())){
                    return objectMapper.convertValue(d,ApplicationSystemTable.class).getOneLevelModule();
                }else if(Common.TWO_LEVEL_MODULE.equalsIgnoreCase(bloodManagementQuery.getSort())){
                    return objectMapper.convertValue(d,ApplicationSystemTable.class).getTwoLevelModule();
                }else{
                    return objectMapper.convertValue(d,ApplicationSystemTable.class).getThreeLevelModule();
                }
            }).reversed());
        };
        applicationTableManage.setApplicationSystemTableList(applicationSystemTableList1);
        applicationTableManage.setTableCount(applicationSystemTableList1.size());

        applicationTableManage.setApplicationFilter(applicationSystemTableList1.stream()
                .map(ApplicationSystemTable::getApplicationSystem).
                        distinct().sorted((s1, s2) -> Collator.getInstance(Locale.CHINA).compare(s1, s2)).collect(Collectors.toList()));

        applicationTableManage.setOneLevelModuleFilter(applicationSystemTableList1.stream()
                .map(ApplicationSystemTable::getOneLevelModule).
                distinct().sorted((s1, s2) -> Collator.getInstance(Locale.CHINA).compare(s1, s2)).collect(Collectors.toList()));

        applicationTableManage.setTwoLevelModuleFilter(applicationSystemTableList1.stream()
                .map(ApplicationSystemTable::getTwoLevelModule).
                distinct().sorted((s1, s2) -> Collator.getInstance(Locale.CHINA).compare(s1, s2)).collect(Collectors.toList()));

        applicationTableManage.setThreeLevelModuleFilter(applicationSystemTableList1.stream()
                .map(ApplicationSystemTable::getThreeLevelModule).
                distinct().sorted((s1, s2) -> Collator.getInstance(Locale.CHINA).compare(s1, s2)).collect(Collectors.toList()));
        return applicationTableManage;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean addApplicationBlood(ApplicationSystemTable applicationSystemTable)  throws Exception{
        logger.info("开始添加应用血缘的数据："+ JSONObject.toJSONString(applicationSystemTable));
        ApplicationSystem applicationSystem =new ApplicationSystem();
        applicationSystem.setApplicationName(applicationSystemTable.getApplicationSystem());
        applicationSystem.setDataBaseType("ads");
        applicationSystem.setProject("hc_db");
        String stringBuilder = applicationSystemTable.getOneLevelModule() + "/" + applicationSystemTable.getTwoLevelModule() +
                "/" + applicationSystemTable.getThreeLevelModule();
        applicationSystem.setSubModuleName(stringBuilder.toUpperCase());
        applicationSystem.setTableNameEn(applicationSystemTable.getTableNameEn().toUpperCase());
        applicationSystem.setTableNameCh(applicationSystemTable.getTableNameCh());
        // 判断这些参数是否是已经存在，如果存在，返回报错
        int tableCount = dataBloodlineDao.getApplicationSystemCount(applicationSystem);
        if(tableCount >=1){
            throw new Exception("表中已经存在该条记录");
        }
        int insertNum = dataBloodlineDao.insertApplicationSystem(applicationSystem);
        logger.info("插入数据库的数据量为"+insertNum);
        if(insertNum >= 1){
            cacheManageDataBloodlineServiceImpl.getApplicationDataBloodCache();
            return true;
        }
        return false;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteApplicationBlood(ApplicationSystemTable applicationSystemTable,boolean flag) {
        logger.info("开始删除应用血缘的数据："+ JSONObject.toJSONString(applicationSystemTable));
        ApplicationSystem applicationSystem =new ApplicationSystem();
        applicationSystem.setApplicationName(applicationSystemTable.getApplicationSystem());
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(applicationSystemTable.getOneLevelModule()).append("/").append(applicationSystemTable.getTwoLevelModule())
                .append("/").append(applicationSystemTable.getThreeLevelModule());
        applicationSystem.setSubModuleName(stringBuffer.toString());
        applicationSystem.setTableNameEn(applicationSystemTable.getTableNameEn());
        int deleteNum = dataBloodlineDao.deleteApplicationSystem(applicationSystem);
        if(deleteNum >= 1 && flag){
            cacheManageDataBloodlineServiceImpl.getApplicationDataBloodCache();
            return true;
        }
        return deleteNum >= 1;
    }

    @Override
    public void downloadTemplateExcel(HttpServletResponse response) {
        try{
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("应用-血缘关系模板","UTF-8")+".xlsx";
            response.setHeader("Content-disposition",
                    "attachment;filename*=utf-8''" +fileName);
            EasyExcel.write(response.getOutputStream(),ApplicationSystem.class).autoCloseStream(Boolean.FALSE)
                    .sheet("应用血缘管理").doWrite(null);
            logger.info("导出应用血缘管理模板成功");
        }catch (Exception e){
            logger.error("导出应用-血缘关系模板报错"+ ExceptionUtil.getExceptionTrace(e));
        }
    }

    @Override
    public void exportTableExcel(HttpServletResponse response, List<ApplicationSystemTable> applicationSystemTableList) {
        try{
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("应用血缘关系数据","UTF-8")+".xlsx";
            response.setHeader("Content-disposition",
                    "attachment;filename*=utf-8''" +fileName);
            EasyExcel.write(response.getOutputStream(),ApplicationSystemTable.class).autoCloseStream(Boolean.FALSE)
                    .sheet("应用血缘管理").doWrite(applicationSystemTableList);
            logger.info("导出应用血缘关系数据成功");
        }catch (Exception e){
            logger.error("导出应用-血缘关系数据报错"+ ExceptionUtil.getExceptionTrace(e));
        }
    }

    @Override
    public List<String> queryConditionSuggestion(String searchValue) {
        if(StringUtils.isBlank(searchValue)){
            searchValue = "";
        }
        List<String> commonMaps = dataBloodlineDao.queryConditionSuggestion(searchValue);
        return commonMaps;
    }

    @Override
    public ApplicationFilterResult queryApplicationFilterQuery(ApplicationFilterQuery applicationFilterQuery) {
        List<ApplicationSystemTable> allApplicationData = dataBloodlineDao.searchAllData();
        ApplicationFilterResult applicationFilterResult = new ApplicationFilterResult();
        List<String> applicationFilter = new ArrayList<>();
        List<String> oneLevelModuleFilter = new ArrayList<>();
        List<String> twoLevelModuleFilter = new ArrayList<>();
        List<String> threeLevelModuleFilter = new ArrayList<>();

        if(allApplicationData != null){
            allApplicationData.stream().filter(d -> {
                boolean flag = false;
                String[] oneLevelModuleList = d.getOneLevelModule().split("/");
                if(StringUtils.isNotBlank(applicationFilterQuery.getApplicationFilter())
                        && !(oneLevelModuleList.length >=1 &&
                        StringUtils.equalsIgnoreCase(applicationFilterQuery.getApplicationFilter(),d.getApplicationSystem()))){
                    return false;
                }else{
                    flag = true;
                }
                if(StringUtils.isNotBlank(applicationFilterQuery.getOneLevelModuleFilter())
                        && !(oneLevelModuleList.length >=2 &&
                        StringUtils.equalsIgnoreCase(applicationFilterQuery.getOneLevelModuleFilter(),oneLevelModuleList[0]))){
                    return false;
                }else{
                    flag = true;
                }
                if(StringUtils.isNotBlank(applicationFilterQuery.getTwoLevelModuleFilter())
                        && !(oneLevelModuleList.length >=3 &&
                        StringUtils.equalsIgnoreCase(applicationFilterQuery.getTwoLevelModuleFilter(),oneLevelModuleList[1]))){
                    return false;
                }else{
                    flag = true;
                }
                return flag;
            }).forEach(d ->{
                String[] oneLevelModuleList = d.getOneLevelModule().split("/");
                applicationFilter.add(d.getApplicationSystem());
                oneLevelModuleFilter.add(oneLevelModuleList.length >=1?oneLevelModuleList[0]:"");
                twoLevelModuleFilter.add(oneLevelModuleList.length >=2?oneLevelModuleList[1]:"");
                threeLevelModuleFilter.add(oneLevelModuleList.length >=3?oneLevelModuleList[2]:"");
               }
            );
            applicationFilterResult.setApplicationFilter(applicationFilter.stream()
                    .distinct().sorted((s1, s2) -> Collator.getInstance(Locale.CHINA).compare(s1, s2)).collect(Collectors.toList()));
            applicationFilterResult.setOneLevelModuleFilter(oneLevelModuleFilter.stream()
                    .distinct().sorted((s1, s2) -> Collator.getInstance(Locale.CHINA).compare(s1, s2)).collect(Collectors.toList()));
            applicationFilterResult.setTwoLevelModuleFilter(twoLevelModuleFilter.stream()
                    .distinct().sorted((s1, s2) -> Collator.getInstance(Locale.CHINA).compare(s1, s2)).collect(Collectors.toList()));
            applicationFilterResult.setThreeLevelModuleFilter(threeLevelModuleFilter.stream()
                    .distinct().sorted((s1, s2) -> Collator.getInstance(Locale.CHINA).compare(s1, s2)).collect(Collectors.toList()));
        }else{
            logger.info("数据库中的数据为空");
        }
        return applicationFilterResult;
    }

    @Override
    public String queryTableChByEn(String tableName) {
        // 根据表英文名 在标准表中查询 表中文名
        String tableNameCh = dataBloodlineDao.getTableNameChByEn(tableName);
        return StringUtils.isBlank(tableNameCh)?"":tableNameCh;
    }


    /**
     *
     * @param bloodManagementQuery
     * @param applicationSystemTable
     * @return
     */
    private boolean tableDataFilter(BloodManagementQuery bloodManagementQuery
            ,ApplicationSystemTable applicationSystemTable){
        boolean flag = true;
        if(bloodManagementQuery.getApplicationFilter() != null &&
                !bloodManagementQuery.getApplicationFilter().isEmpty()){
            flag = bloodManagementQuery.getApplicationFilter().contains(applicationSystemTable.getApplicationSystem());
            if(!flag){
                return false;
            }
        }
        if(bloodManagementQuery.getOneLevelModuleFilter() != null &&
                !bloodManagementQuery.getOneLevelModuleFilter().isEmpty()){
            flag = bloodManagementQuery.getOneLevelModuleFilter().contains(applicationSystemTable.getOneLevelModule());
            if(!flag){
                return false;
            }
        }
        if(bloodManagementQuery.getTwoLevelModuleFilter() != null &&
                !bloodManagementQuery.getTwoLevelModuleFilter().isEmpty()){
            flag = bloodManagementQuery.getTwoLevelModuleFilter().contains(applicationSystemTable.getTwoLevelModule());
            if(!flag){
                return false;
            }
        }
        if(bloodManagementQuery.getThreeLevelModuleFilter() != null &&
                !bloodManagementQuery.getThreeLevelModuleFilter().isEmpty()){
            flag = bloodManagementQuery.getThreeLevelModuleFilter().contains(applicationSystemTable.getThreeLevelModule());
            if(!flag){
                return false;
            }
        }
        return flag;
    }


    public void getChildApplicationSystemNode(List<ApplicationSystem> applicationSystemList,List<DataBloodlineNode.BloodNode> allNodeList,
                                              List<DataBloodlineNode.Edges> allEdgeList,int num,String fatherNodeId,String applicationName,QueryDataBloodlineTable queryData){
        final int numNew = num;
        // 还一直存在子模块标签的信息
        Map<String,List<ApplicationSystem>> childModuleMap = applicationSystemList.stream().filter(d -> d.getSubModuleNameLists().size() > numNew).collect(
                Collectors.groupingBy(d->(d.getSubModuleNameLists().get(numNew))));
        // 这个表示是最后一层，直接是节点层的数据
        List<ApplicationSystem> lastNodule = applicationSystemList.stream().filter(d -> d.getSubModuleNameLists().size() == numNew).collect(Collectors.toList());
        num = num+1;
        for(String subModuleName : childModuleMap.keySet()){
            DataBloodlineNode.BloodNode bloodNode = new DataBloodlineNode.BloodNode();
            bloodNode.setDataType(Constant.OPERATINGSYSTEM);
            bloodNode.setTypeName(BloodlineNodeType.getNameByCode(Constant.OPERATINGSYSTEM));
            bloodNode.setModuleClassify(subModuleName);
            bloodNode.setModuleClassifyNum(num);
            bloodNode.setNodeName(subModuleName);
            bloodNode.setId((Constant.OPERATINGSYSTEM+"_"+applicationName+"_"+StringUtils.join(childModuleMap.get(subModuleName).get(0).getSubModuleNameLists().subList(0,num),"/")).toLowerCase());
            if(StringUtils.isNotEmpty(queryData.getSubModuleName()) &&
                    StringUtils.isEmpty(queryData.getTableNameEn()) &&
                    queryData.getSubModuleName().equalsIgnoreCase(StringUtils.join(childModuleMap.get(subModuleName).get(0).getSubModuleNameLists().subList(0,num),"/"))){
                bloodNode.setDataQueryType(Constant.MAIN);
            }
            DataBloodlineNode.Edges oneEdge = new DataBloodlineNode.Edges(bloodNode.getId(),fatherNodeId,true,Constant.OPERATINGSYSTEM);
//            DataBloodlineNode.Edges oneEdge = new DataBloodlineNode.Edges(fatherNodeId,bloodNode.getId(),true,Constant.OPERATINGSYSTEM);
            allEdgeList.add(oneEdge);
            allNodeList.add(bloodNode);
            getChildApplicationSystemNode(childModuleMap.get(subModuleName),allNodeList,allEdgeList,num,bloodNode.getId(),applicationName,queryData);
        }
        // 如果是节点层，则查询上一个流程/下一个流程是否有节点，如果有则展示，如果没有不展示
        for(ApplicationSystem applicationSystem:lastNodule){
            DataBloodlineNode.BloodNode bloodNode = new DataBloodlineNode.BloodNode();
            bloodNode.setDataType(Constant.OPERATINGSYSTEM);
            bloodNode.setModuleClassify("");
            bloodNode.setTypeName(BloodlineNodeType.getNameByCode(Constant.OPERATINGSYSTEM));
            bloodNode.setModuleClassifyNum(-1);
            bloodNode.setTableNameCh(applicationSystem.getTableNameCh());
            bloodNode.setNodeName(StringUtils.isNotEmpty(applicationSystem.getTableNameCh().trim())?applicationSystem.getTableNameCh():(applicationSystem.getProject()+"."+applicationSystem.getTableNameEn()).toLowerCase());
//            bloodNode.setTableNameEn((applicationSystem.getProject()+"."+applicationSystem.getTableNameEn()).toLowerCase());
            bloodNode.setTableNameEn(applicationSystem.getTableNameEn().toLowerCase());
            bloodNode.setSubModuleName(applicationSystem.getSubModuleName());
            bloodNode.setApplicationSystem(applicationSystem.getApplicationName());
            bloodNode.setDataBaseType(applicationSystem.getDataBaseType());
            bloodNode.setId((Constant.OPERATINGSYSTEM+"_"+bloodNode.getTableNameEn()).toLowerCase());
            if(StringUtils.isNotEmpty(queryData.getTableNameEn()) &&
                    queryData.getTableNameEn().equalsIgnoreCase(bloodNode.getTableNameEn())){
                bloodNode.setDataQueryType(Constant.MAIN);
            }
            allNodeList.add(bloodNode);
            DataBloodlineNode.Edges oneEdge = new DataBloodlineNode.Edges(bloodNode.getId(), fatherNodeId ,true,Constant.OPERATINGSYSTEM);
            allEdgeList.add(oneEdge);
            // 获取加工那边的节点信息
            getDataProcessNodes(allNodeList,allEdgeList,bloodNode);
            // 获取数据处理那边的节点信息
            getDataStandardNodes(allNodeList,allEdgeList,bloodNode);
        }
    }


    private void getDataProcessNodes(List<DataBloodlineNode.BloodNode> allNodeList,
                                     List<DataBloodlineNode.Edges> allEdgeList,
                                     DataBloodlineNode.BloodNode bloodNode){
        // 查询下一个流程中数据
        QueryDataBloodlineTable queryDataInfo = new QueryDataBloodlineTable();
        queryDataInfo.setTableNameEn(bloodNode.getTableNameEn());
        DataBloodlineNode bloodNodeNext = dataBloodlineLinkServiceImpl.getNextProcessOneNode(queryDataInfo,Constant.LEFT,1,Constant.OPERATINGSYSTEM);
        if(bloodNodeNext != null && bloodNodeNext.getNodes().size() >0){
            for(DataBloodlineNode.BloodNode bloodNode1:bloodNodeNext.getNodes()){
                DataBloodlineNode.Edges edge = new DataBloodlineNode.Edges(bloodNode1.getId(), bloodNode.getId(),true,Constant.OPERATINGSYSTEM);
                allEdgeList.add(edge);
                allNodeList.add(bloodNode1);
            }
        }
    }

    /**
     * 获取数据处理那边的节点信息
     * @param allNodeList
     * @param allEdgeList
     * @param bloodNode
     */
    private void getDataStandardNodes(List<DataBloodlineNode.BloodNode> allNodeList,
                                     List<DataBloodlineNode.Edges> allEdgeList,
                                      DataBloodlineNode.BloodNode bloodNode){
        if(environment.getProperty("standardConnectOperatingFlag",Boolean.class,false)){
            QueryDataBloodlineTable queryDataInfo = new QueryDataBloodlineTable();
            queryDataInfo.setTableNameEn(bloodNode.getTableNameEn());
            DataBloodlineNode bloodNodeNext = dataBloodlineLinkServiceImpl.getNextProcessOneNode(queryDataInfo,Constant.LEFT,2,Constant.OPERATINGSYSTEM);
            if(bloodNodeNext != null && bloodNodeNext.getNodes()!= null &&
                    !bloodNodeNext.getNodes().isEmpty()){
                for(DataBloodlineNode.BloodNode bloodNode1:bloodNodeNext.getNodes()){
                    DataBloodlineNode.Edges edge = new DataBloodlineNode.Edges(bloodNode1.getId(), bloodNode.getId(),true,Constant.OPERATINGSYSTEM);
                    allEdgeList.add(edge);
                    allNodeList.add(bloodNode1);
                }
            }
        }
    }





    /**
     *  开始将 应用系统血缘的信息导入到数据库中
     *  如果map的key值不对 表示 用的模板文件不对 需要
     *  20210318 模板发生变化
     * @param list
     * @return
     */
    @Override
    public List importApplicationSystemExcel(List<Map> list) {
        List errorList = new ArrayList();
        try{
            for(Map element:list){
                try{
                    ApplicationSystem applicationSystem = new ApplicationSystem();
                    applicationSystem.setApplicationName(String.valueOf(element.get("应用系统名称")).trim());
                    // 一级/二级/三级 模块的相关信息
                    String oneModuleName = String.valueOf(element.get("一级模块")).trim();
                    String twoModuleName = String.valueOf(element.get("二级模块")).trim();
                    String threeModuleName = String.valueOf(element.get("三级模块")).trim();
                    applicationSystem.setOneModuleName(oneModuleName);
                    applicationSystem.setTwoModuleName(twoModuleName);
                    applicationSystem.setThreeModuleName(threeModuleName);
                    applicationSystem.setDataBaseType(String.valueOf(element.get("数据库类型")).trim());
                    applicationSystem.setProject(String.valueOf(element.get("数据库或账户")).trim());
                    applicationSystem.setTableNameEn(String.valueOf(element.get("表名")).trim());
                    applicationSystem.setTableNameCh(String.valueOf(element.get("表中文名")).trim());
                    boolean flag = StringUtils.isNotBlank(applicationSystem.getApplicationName()) &&
                            !StringUtils.equalsIgnoreCase("null",applicationSystem.getApplicationName())&&
                            StringUtils.isNotBlank(applicationSystem.getOneModuleName()) &&
                            !StringUtils.equalsIgnoreCase("null",applicationSystem.getOneModuleName()) &&
                            StringUtils.isNotBlank(applicationSystem.getTableNameEn()) &&
                            !StringUtils.equalsIgnoreCase("null",applicationSystem.getTableNameEn()) &&
                            !Common.PATTERN_REG.matcher(applicationSystem.getTableNameEn()).find();
                    applicationSystem.setSubModuleName(oneModuleName +"/"+ twoModuleName+"/"+threeModuleName);
                    // 判断这条记录是否是已经存在
                    int numFlag = dataBloodlineDao.getApplicationTableCount(applicationSystem);
                    if(numFlag > 0){
                        flag = false;
                    }
                    if(flag){
                        dataBloodlineDao.insertApplicationSystem(applicationSystem);
                    }else{
                        errorList.add(element);
                    }
                }catch (Exception e){
                    errorList.add(element);
                    logger.error("导入一条数据报错"+ExceptionUtil.getExceptionTrace(e));
                }
            }
            logger.info("开始清除缓存中的应用系统数据");
            cacheManageDataBloodlineServiceImpl.getApplicationDataBloodCache();
        }catch (Exception e){
            errorList = list;
            logger.error("导入数据报错"+ExceptionUtil.getExceptionTrace(e));
        }
        return errorList;
    }

    @Override
    public void exportApplicationSystemError(List<Map<String,String>> param, HttpServletResponse response) {
        //表标题
        String[] titles = {"应用系统名称","一级模块","二级模块","三级模块","数据库类型","数据库或账户","表名","表中文名"};
        //列对应字段
        String[] fieldName = {"applicationName","oneModuleName","twoModuleName","threeModuleName","dataBaseType","project","tableNameEn","tableNameCh"};
        //文件名称
        String name = "应用系统未导入信息";
        //响应
        try{
            ServletOutputStream out = response.getOutputStream();
//            response.setContentType("application/binary;charset=UTF-8");
            // 导出excel
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(name + ".xls", "UTF-8"));
            List<Object> listNew = new ArrayList<Object>();
            for(Map<String,String> oneMap : param){
                ApplicationSystem applicationSystem = new ApplicationSystem();
                applicationSystem.setApplicationName(oneMap.getOrDefault("应用系统名称",""));
                applicationSystem.setOneModuleName(oneMap.getOrDefault("一级模块",""));
                applicationSystem.setTwoModuleName(oneMap.getOrDefault("二级模块",""));
                applicationSystem.setThreeModuleName(oneMap.getOrDefault("三级模块",""));
                applicationSystem.setDataBaseType(oneMap.getOrDefault("数据库类型",""));
                applicationSystem.setProject(oneMap.getOrDefault("数据库或账户",""));
                applicationSystem.setTableNameEn(oneMap.getOrDefault("表名",""));
                applicationSystem.setTableNameCh(oneMap.getOrDefault("表中文名",""));
                listNew.add(applicationSystem);
            }
            ExcelHelper.export(new ApplicationSystem(), titles, name, listNew, fieldName, out);
        } catch (Exception e) {
            logger.error("应用系统未导入信息导出失败"+ExceptionUtil.getExceptionTrace(e));
        }
    }




}
