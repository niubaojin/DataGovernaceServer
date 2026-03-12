package com.synway.property.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.synway.property.dao.DataMonitorDao;
import com.synway.property.dao.DataStorageMonitorDao;
import com.synway.property.dao.FoucsOrganizationDao;
import com.synway.property.enums.MainClassifyExp;
import com.synway.property.interceptor.AuthorizedUserUtils;
import com.synway.property.pojo.*;
import com.synway.property.pojo.formorganizationindex.ClassifyInfo;
import com.synway.property.pojo.formorganizationindex.ClassifyInfoTree;
import com.synway.property.service.FoucsOrganizationService;
import com.synway.property.util.CacheManager;
import com.synway.property.util.ExceptionUtil;
import com.synway.common.bean.ServerResponse;
import com.synway.property.util.RestTemplateHandle;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * @author 数据接入
 */
@Service
public class FoucsOrganizationServiceImpl implements FoucsOrganizationService {
    private static Logger logger = LoggerFactory.getLogger(FoucsOrganizationServiceImpl.class);
    @Autowired
    private FoucsOrganizationDao foucsOrganizationDao;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RestTemplateHandle restTemplateHandle;

    @Autowired
    private DataStorageMonitorDao dataStorageMonitorDao;

    @Autowired
    private Environment environment;

    @Autowired
    private DataMonitorDao dataMonitorDao;

    @Autowired
    private CacheManager cacheManager;

    @Resource
    private Environment env;

    /**
     * 获取所有的重点组织信息
     *
     * @return
     */
    @Override
    public ServerResponse<List<FoucsOrganizationFull>> getAllFoucsOrganization() {
        ServerResponse<List<FoucsOrganizationFull>> serverResponse = null;
        try {
            List<FoucsOrganizationFull> foucsOrganizationFullList = foucsOrganizationDao.getAllFoucsOrganizationDao();
            List<FoucsOrganizationFull> foucsOrganizationFullListRemove = new ArrayList<>();
            for (FoucsOrganizationFull oneFoucsOrganizationFull : foucsOrganizationFullList) {
                String mainClassifyCH = oneFoucsOrganizationFull.getMainClassifyCH();
                String mainClassifyCode = MainClassifyExp.getEnglishName(MainClassifyExp.class, mainClassifyCH);
                oneFoucsOrganizationFull.setMainClassifyCode(mainClassifyCode);
                Long allRecordsSum = Long.valueOf(oneFoucsOrganizationFull.getAllRecordsSum());
                Long yesterdayRecordsSum = Long.valueOf(oneFoucsOrganizationFull.getYesterdayRecordsSum());
                if (allRecordsSum > Long.parseLong("100000000")) {
                    oneFoucsOrganizationFull.setAllRecordsSum(String.format("%.2f",allRecordsSum*1.0 / 100000000));
                    oneFoucsOrganizationFull.setDataUnit("亿");
                } else if (allRecordsSum > Long.parseLong("10000")) {
                    oneFoucsOrganizationFull.setAllRecordsSum(String.format("%.2f",allRecordsSum*1.0 / 10000));
                    oneFoucsOrganizationFull.setDataUnit("万");
                } else {
                    oneFoucsOrganizationFull.setAllRecordsSum(String.valueOf(allRecordsSum));
                    oneFoucsOrganizationFull.setDataUnit("");
                }
                if (yesterdayRecordsSum > Long.parseLong("100000000")) {
                    oneFoucsOrganizationFull.setYesterdayRecordsSum(String.format("%.2f",yesterdayRecordsSum*1.0 / 100000000));
                    oneFoucsOrganizationFull.setYesterdayUnit("亿");
                } else if (yesterdayRecordsSum > Long.parseLong("10000")) {
                    oneFoucsOrganizationFull.setYesterdayRecordsSum(String.format("%.2f",yesterdayRecordsSum*1.0 / 10000));
                    oneFoucsOrganizationFull.setYesterdayUnit("万");
                } else {
                    oneFoucsOrganizationFull.setYesterdayRecordsSum(String.valueOf(yesterdayRecordsSum));
                    oneFoucsOrganizationFull.setYesterdayUnit("");
                }
                if ("数据组织分类".equals(oneFoucsOrganizationFull.getMainClassifyCH()) && oneFoucsOrganizationFull.getPrimarycodeid().contains("GACODE000404")){
                    foucsOrganizationFullListRemove.add(oneFoucsOrganizationFull);
                }
                String sjzzflCodeId = env.getProperty("sjzzflCodeId");
                if ("数据来源分类".equals(oneFoucsOrganizationFull.getMainClassifyCH()) && oneFoucsOrganizationFull.getPrimarycodeid().contains(sjzzflCodeId)){
                    foucsOrganizationFullListRemove.add(oneFoucsOrganizationFull);
                }
            }
            for (int i=0; i<foucsOrganizationFullListRemove.size(); i++){
                foucsOrganizationFullList.remove(foucsOrganizationFullListRemove.get(i));
            }
            serverResponse = ServerResponse.asSucessResponse(foucsOrganizationFullList);
        } catch (Exception e) {
            logger.error("查询所有的重点组织信息报错" + ExceptionUtil.getExceptionTrace(e));
            serverResponse = ServerResponse.asErrorResponse("查询所有的重点组织信息报错");
        }

        return serverResponse;
    }

    @Override
    public ServerResponse<List<ClassifyInformation>> getClassifyInformationTableService(String mainClassifyCh,
                                                                                        String primaryClassifyCh) {
        ServerResponse<List<ClassifyInformation>> serverResponse = null;
        try {
            List<ClassifyInformation> classifyInformationList = foucsOrganizationDao.getClassifyInformationTableDao(mainClassifyCh, primaryClassifyCh);
            List<ClassifyInformation> classifyInformationListRemove = new ArrayList<>();
            if(classifyInformationList.size()>1){
                String sjzzflCodeId = env.getProperty("sjzzflCodeId");
                for(int i=0; i<classifyInformationList.size();i++){
                    ClassifyInformation classifyInformation = classifyInformationList.get(i);
                    if (("数据组织分类".equals(classifyInformation.getMainClassifyCH()) || "原始库".equals(classifyInformation.getMainClassifyCH()))
                            && (classifyInformation.getPrimaryClassifyCode().contains("GACODE000404")
                            || (classifyInformation.getPrimaryClassifyCH().equals(classifyInformation.getSecondaryClassifyCH())
                            && !primaryClassifyCh.equals("业务要素索引库") && !primaryClassifyCh.equals("其他")))){
                        classifyInformationListRemove.add(classifyInformationList.get(i));
                    }
                    if (("数据来源分类".equals(classifyInformation.getMainClassifyCH()) && classifyInformation.getPrimaryClassifyCode().contains(sjzzflCodeId))
                            || ("数据来源分类".equals(classifyInformation.getMainClassifyCH()) && classifyInformation.getPrimaryClassifyCH().equals(classifyInformation.getSecondaryClassifyCH()))){
                        classifyInformationListRemove.add(classifyInformationList.get(i));
                    }
                }
            }
            if(classifyInformationListRemove.size()>=1){
                for(int i=0; i<classifyInformationListRemove.size();i++){
                    ClassifyInformation classifyInformation = classifyInformationListRemove.get(i);
                    classifyInformationList.remove(classifyInformation);
                }
            }
            // 获取已经添加的一级分类名称,二级分类名称
            List<ClassifyInformation> classifyInformationListNew = new ArrayList<>();

            List<String> addClassifyNameList = foucsOrganizationDao.getAddClassifyNameList();
            if (addClassifyNameList != null) {
                if(classifyInformationList.size()==0){
                    ClassifyInformation oneClassifyInformation = new ClassifyInformation();
                    oneClassifyInformation.setPrimaryClassifyCH(primaryClassifyCh);
                    oneClassifyInformation.setMainClassifyCH(mainClassifyCh);
                    if (addClassifyNameList.contains(primaryClassifyCh)) {
                        oneClassifyInformation.setAlreadyInserted(true);
                    } else {
                        oneClassifyInformation.setAlreadyInserted(false);
                    }
                    classifyInformationListNew.add(oneClassifyInformation);
                }else {
                    for (ClassifyInformation oneClassifyInformation : classifyInformationList) {
                        String secondaryClassifyCH = oneClassifyInformation.getPrimaryClassifyCH() + "_" + oneClassifyInformation.getSecondaryClassifyCH();
                        if (addClassifyNameList.contains(secondaryClassifyCH)) {
                            oneClassifyInformation.setAlreadyInserted(true);
                        } else {
                            oneClassifyInformation.setAlreadyInserted(false);
                        }
                        classifyInformationListNew.add(oneClassifyInformation);
                    }
                }
            }
            serverResponse = ServerResponse.asSucessResponse(classifyInformationListNew);
        } catch (Exception e) {
            logger.error("查询分类信息报错" + ExceptionUtil.getExceptionTrace(e));
            serverResponse = ServerResponse.asErrorResponse("查询分类信息报错");
        }
        return serverResponse;
    }

    /**
     * 获取所有已添加的重点组织监控
     * @return
     */
    @Override
    public ServerResponse<List<ClassifyInformation>> getAllClassifyInformationTableService() {
        ServerResponse<List<ClassifyInformation>> serverResponse = null;
        try {
            String sjzzflCodeId = env.getProperty("sjzzflCodeId");
            List<ClassifyInformation> classifyInformationList = foucsOrganizationDao.getAllClassifyInformationTableDao(sjzzflCodeId);
            // 获取所有已经添加的一级分类名称,二级分类名称
            serverResponse = ServerResponse.asSucessResponse(classifyInformationList);
        } catch (Exception e) {
            logger.error("查询所有分类信息报错" + ExceptionUtil.getExceptionTrace(e));
            serverResponse = ServerResponse.asErrorResponse("查询所有分类信息报错");
        }
        return serverResponse;
    }

    /**
     * @param insertDataList
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServerResponse<String> insertClassifyInformationTableService(List<ClassifyInformation> insertDataList) {
        ServerResponse<String> serverResponse = null;
        try {
            LoginUser authorizedUser = AuthorizedUserUtils.getInstance().getAuthor();
//            List<UserAuthorityData> userAuthorityDataList = new ArrayList<>();
//            //注入用户信息
//            insertDataList.stream().map(item->{
//                //构建权限表数据
//                UserAuthorityData userAuthorityData = new UserAuthorityData();
//                userAuthorityData.setId(UUID.randomUUID().toString());
//                userAuthorityData.setCmnMemo("中文名" + "_" + item.getPrimaryClassifyCH()); //避免空值
//                userAuthorityData.setModuleCode("ZCGL_ZDZZ");
//                userAuthorityData.setModuleName("首页重点组织");
//                userAuthorityData.setIsCreate("1");
//                userAuthorityData.setUserName(authorizedUser.getUserName());
//                userAuthorityData.setCnmName("中文名" + "_" + item.getSecondaryClassifyCH());  //避免空值
//                userAuthorityData.setUserId(authorizedUser.getUserId());
//                userAuthorityData.setCreateTime(new Date());
//                userAuthorityDataList.add(userAuthorityData);
//
//                item.setUserId(authorizedUser.getUserId());
//                item.setUserAuthorityId(userAuthorityData.getId());
//                item.setUserName(authorizedUser.getUserName());
//                item.setIsAdmin(authorizedUser.getIsAdmin().toString());
//                return item;
//            }).collect(Collectors.toList());

            String message = "";
            logger.info("开始删除重点组织监控表中的所有数据");
            Integer delNum = foucsOrganizationDao.delAllClassifyInformationTableDao();
//            Integer delAuthNum = foucsOrganizationDao.delUserAuthorityDataListDao(authorizedUser.getUserId());
//            logger.info("删除权限表数据" + delAuthNum + "条");
            message = "删除了【 " + delNum + " 】条重点组织监控表中的数据";
            logger.info(message);
            logger.info("开始插入需要监控的重点组织信息");
            if (insertDataList.size() > 0) {
                Integer insertNum = foucsOrganizationDao.insertClassifyInformationTableDao(insertDataList);
                message += "数据成功插入" + insertNum + "条";
            } else {
                message += "数据成功插入0条";
            }
//            if (userAuthorityDataList.size()>0){
//                Integer insertAuthNum = foucsOrganizationDao.insertUserAuthorityDataListDao(userAuthorityDataList);
//                logger.info("成功插入权限表数据" + insertAuthNum + "条");
//            }
            serverResponse = ServerResponse.asSucessResponse(message);
        } catch (Exception e) {
            logger.error("将插入需要监控的重点组织信息报错" + ExceptionUtil.getExceptionTrace(e));
            String message = "数据插入报错";
            serverResponse = ServerResponse.asErrorResponse(message);
        }
        return serverResponse;
    }

    /**
     * 获取昨日使用热度的top8
     *
     * @return
     */
    @Override
    public JSONArray getDayUseHeatService() {
        try {
            return restTemplateHandle.getTableHots(8);
        } catch (Exception e) {
            logger.error("查询使用热度top8报错：", e);
            return new JSONArray();
        }
    }

    @Override
    public ServerResponse<DataRankingTop> getFullDataRankingService() {
        ServerResponse<DataRankingTop> serverResponse = null;
        DataRankingTop dataRankingTop = new DataRankingTop();
        try {
            List<String> dataNameList = new ArrayList<>();
            List<Double> recordsNumberList = new ArrayList<>();
            int todayAssetsCount = dataStorageMonitorDao.getTodayAssetsCount();
            int daysAgo = todayAssetsCount<100?1:0;
            List<Map<String, Object>> resultList = foucsOrganizationDao.getFullDataRankingDao(daysAgo);
            logger.info("全量排行数据为：" + JSONObject.toJSONString(resultList));
            Collections.reverse(resultList);
            getTableNameCh(dataNameList, recordsNumberList, resultList);
            dataRankingTop.setDataNameList(dataNameList);
            dataRankingTop.setRecordsNumberList(recordsNumberList);
            serverResponse = ServerResponse.asSucessResponse(dataRankingTop);
            logger.info("查询到的数据为" + dataRankingTop.toString());
        } catch (Exception e) {
            logger.error("全量数据TOP5报错" + ExceptionUtil.getExceptionTrace(e));
            serverResponse = ServerResponse.asErrorResponse("全量数据TOP5报错");
        }
        return serverResponse;
    }

    @Override
    public ServerResponse<DataRankingTop> getIncrementalDataRankingService() {
        ServerResponse<DataRankingTop> serverResponse = null;
        DataRankingTop dataRankingTop = new DataRankingTop();
        try {
            List<String> dataNameList = new ArrayList<>();
            List<Double> recordsNumberList = new ArrayList<>();
//            List<Map<String, Object>> resultList = foucsOrganizationDao.getIncrementalDataRankingDao(0);
            List<Map<String, Object>> resultList;
            // 统计资产表今日数据量如果为0，则获取昨天数据
            int todayAssetsCount = dataStorageMonitorDao.getTodayAssetsCount();
            int daysAgo = todayAssetsCount<100?1:0;
            resultList = foucsOrganizationDao.getIncrementalDataRankingDao(daysAgo);
            Collections.reverse(resultList);
            logger.info("增量数据排行查询到的数据为：" + JSONObject.toJSONString(resultList));
            getTableNameCh(dataNameList, recordsNumberList, resultList);
            dataRankingTop.setDataNameList(dataNameList);
            dataRankingTop.setRecordsNumberList(recordsNumberList);
            serverResponse = ServerResponse.asSucessResponse(dataRankingTop);
        } catch (Exception e) {
            logger.error("增量数据TOP5报错" + ExceptionUtil.getExceptionTrace(e));
            serverResponse = ServerResponse.asErrorResponse("增量数据TOP5报错");
        }
        return serverResponse;
    }

    private void getTableNameCh(List<String> dataNameList, List recordsNumberList, List<Map<String, Object>> resultList) {
        boolean isHailiang = cacheManager.getValue("dsType").toString().equalsIgnoreCase("hailiang");
        resultList.forEach(oneMap-> {
            String tableName = String.valueOf(isHailiang ? oneMap.get("tablenamezh") : oneMap.get("TABLENAMEZH"));
            double tableCount = 0L;
            if ( (isHailiang ? oneMap.get("total") : oneMap.get("TOTAL")) != null) {
                tableCount = Double.valueOf(isHailiang ? oneMap.get("total").toString() : oneMap.get("TOTAL").toString());
            }
            dataNameList.add(tableName);
            recordsNumberList.add(tableCount);
        });
    }

    /**
     * 获取添加重点组织中模态框中左侧的选择框
     */
    @Override
    public ServerResponse<List<ClassifyInfoTree>> getClassTreeList() {
        ServerResponse<List<ClassifyInfoTree>> serverResponse = null;
        try {
            /*获取分类信息*/
            List<ClassifyInfo> classifyInfos = dataMonitorDao.getClassInfo("");
            List<ClassifyInfoTree> classInfoJsonZZ = null,classInfoJsonLY = null,classInfoJsonAll=new ArrayList<>();
            ClassifyInfoTree classifyInfoTreeZZ = new ClassifyInfoTree(),classifyInfoTreeLY = new ClassifyInfoTree();
            String sjzzflCodeId = env.getProperty("sjzzflCodeId");
            classInfoJsonZZ = convert2Tree(classifyInfos, sjzzflCodeId, new LinkedList<>());
            classInfoJsonLY = convert2Tree(classifyInfos,"GACODE000404",new LinkedList<>());
            classifyInfoTreeZZ.setLabel("数据组织分类");
            classifyInfoTreeZZ.setChildren(classInfoJsonZZ);
            classifyInfoTreeLY.setLabel("数据来源分类");
            classifyInfoTreeLY.setChildren(classInfoJsonLY);
            classInfoJsonAll.add(0,classifyInfoTreeZZ);
            classInfoJsonAll.add(1,classifyInfoTreeLY);
            for(ClassifyInfoTree one:classInfoJsonAll){
                for(ClassifyInfoTree two: one.getChildren()){
                    if (two.getLabel().equals("原始库")){
                        for(ClassifyInfoTree three:two.getChildren()){
                            three.setChildren(null);
                        }
                    }else {
                        two.setChildren(null);
                    }
                }
            }
            serverResponse = ServerResponse.asSucessResponse(classInfoJsonAll);

//            Map<String, List<ClassTreeData>> result = new HashMap<>();
//            List<Map<String, String>> classTreeMessageList = foucsOrganizationDao.getClassTreeListDao();
//            for (Map<String, String> classTreeMessage : classTreeMessageList) {
//                String mainClassCh = classTreeMessage.getOrDefault("MAINCLASS_CH", "");
//                String primaryCh = classTreeMessage.getOrDefault("PRIMARY_CH", "").replace("分类","");
//                if (result.containsKey(mainClassCh)) {
//                    ClassTreeData oneClassTree = new ClassTreeData();
//                    oneClassTree.setLabel(primaryCh);
//                    result.get(mainClassCh).add(oneClassTree);
//                } else {
//                    List<ClassTreeData> oneList = new ArrayList<>();
//                    ClassTreeData oneClassTree = new ClassTreeData();
//                    oneClassTree.setLabel(primaryCh);
//                    oneList.add(oneClassTree);
//                    result.put(mainClassCh, oneList);
//                }
//            }
//            List<ClassTreeData> allClassTreeDataList = new ArrayList<>();
//            for (String key : result.keySet()) {
//                ClassTreeData oneClassTreeData = new ClassTreeData();
//                oneClassTreeData.setLabel(key);
//                oneClassTreeData.setChildren(result.get(key));
//                allClassTreeDataList.add(oneClassTreeData);
//            }
//            serverResponse = ServerResponse.asSucessResponse(allClassTreeDataList);

            logger.info("查询到的数据为：" + JSONObject.toJSONString(serverResponse));
        } catch (Exception e) {
            logger.error("获取左侧的tree框报错" + ExceptionUtil.getExceptionTrace(e));
            serverResponse = ServerResponse.asErrorResponse("获取左侧的tree框报错");
        }
        return serverResponse;
    }

    /**
     * 递归转换树形json数据
     * @return
     */
    public List<ClassifyInfoTree> convert2Tree(List<ClassifyInfo> tables, String codeId, List<ClassifyInfoTree> array) {
        for (ClassifyInfo table:tables) {
            if(StringUtils.isBlank(table.getCodeIdPar())){
                continue;
            }
            if (table.getCodeIdPar().equalsIgnoreCase(codeId)){
                ClassifyInfoTree classifyInfoTree = new ClassifyInfoTree();
                classifyInfoTree.setValue(table.getCodeId());
                classifyInfoTree.setLabel(table.getCodeText());
                classifyInfoTree.setChildren(convert2Tree(tables,table.getCodeId(),new LinkedList<>()));
                array.add(classifyInfoTree);
            }
        }
        return array;
    }

}
