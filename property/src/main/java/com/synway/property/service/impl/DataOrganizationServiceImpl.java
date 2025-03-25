package com.synway.property.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.synway.property.common.UrlConstants;
import com.synway.property.dao.DataMonitorDao;
import com.synway.property.dao.DataOrganizationDao;
import com.synway.property.dao.DataStorageMonitorDao;
import com.synway.property.enums.SysCodeEnum;
import com.synway.property.pojo.dataOrganization.DataOrganization;
import com.synway.property.pojo.dataOrganization.ReturnResult;
import com.synway.property.pojo.formorganizationindex.ClassifyInfo;
import com.synway.property.pojo.formorganizationindex.ClassifyInfoTree;
import com.synway.property.pojo.formorganizationindex.PublicDataInfo;
import com.synway.property.service.DataOrganizationService;
import com.synway.property.util.CacheManager;
import com.synway.property.util.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author majia
 * @version 1.0
 * @date 2020/10/15 13:48
 */
@Service
public class DataOrganizationServiceImpl implements DataOrganizationService {
    private static Logger logger = LoggerFactory.getLogger(DataOrganizationServiceImpl.class);

    @Autowired
    private DataOrganizationDao dao;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DataMonitorDao dataMonitorDao;

    @Autowired
    DataStorageMonitorDao dataStorageMonitorDao;

    @Override
    public List getPageSecondaryClassify(String dataOrganizationType) {
        List retrunList = dao.getPageSecondaryClassify(dataOrganizationType);
        return retrunList;
    }

    @Override
    public List<ClassifyInfoTree> getPageSecondaryClassifyTree(String dataOrganizationType) {
        List<ClassifyInfoTree> returnResult = null;
        /*获取CLASSIFY_INTERFACE_ALL_DATE数据*/
        List<PublicDataInfo> ciads = dao.getCIAD();
        Map<String, List<PublicDataInfo>> listMap = ciads.stream().collect(Collectors.groupingBy(PublicDataInfo::getSjzzejfl));
        Map<String, List<PublicDataInfo>> listMap2 = ciads.stream().collect(Collectors.groupingBy(PublicDataInfo::getSjzzsjfl));
        /*获取分类信息*/
        List<ClassifyInfo> classifyInfos = dataMonitorDao.getClassInfo("JZCODEGASJZZFL");
        List<ClassifyInfoTree> classInfoJson = null;
        classInfoJson = convert2Tree(classifyInfos,"JZCODEGASJZZFL",new LinkedList<>());
        for(int i=0; i<classInfoJson.size(); i++){
            if(dataOrganizationType.equals(classInfoJson.get(i).getLabel())){
                returnResult = classInfoJson.get(i).getChildren();
                if (dataOrganizationType.equalsIgnoreCase("业务要素索引库")){
                    ClassifyInfoTree classifyInfoTree = new ClassifyInfoTree();
                    classifyInfoTree.setLabel("业务要素索引库");
                    classifyInfoTree.setValue("JZCODEGASJZZFL06");
                    classifyInfoTree.setChildren(new ArrayList<>());
                    returnResult.add(classifyInfoTree);
                }
                if(dataOrganizationType.equals("原始库") || dataOrganizationType.equals("主题库")){
                    for(int j=0; j<returnResult.size(); j++){
                        String EJLabel = returnResult.get(j).getLabel();
                        int EJLabelSum = listMap.get(EJLabel)!=null?listMap.get(EJLabel).size():0;
                        String EJnewLabel = EJLabel+"(" + EJLabelSum + ")";
                        returnResult.get(j).setLabel(EJnewLabel);
                        if (returnResult.get(j).getChildren().size()>0){
                            for(int k=0; k<returnResult.get(j).getChildren().size(); k++){
                                String SJLabel = returnResult.get(j).getChildren().get(k).getLabel();
                                List<PublicDataInfo> ciads2 = listMap.get(EJLabel);
                                if (ciads2 == null){
                                    String SJnewLabel = SJLabel+"(" + 0 + ")";
                                    returnResult.get(j).getChildren().get(k).setLabel(SJnewLabel);
                                    continue;
                                }
                                Map<String, List<PublicDataInfo>> listMap3 = ciads2.stream().collect(Collectors.groupingBy(PublicDataInfo::getSjzzsjfl));
                                int SJLabelSum = listMap3.get(SJLabel)!=null?listMap3.get(SJLabel).size():0;
                                String SJnewLabel = SJLabel+"(" + SJLabelSum + ")";
                                returnResult.get(j).getChildren().get(k).setLabel(SJnewLabel);
                            }
                        }
                    }
                }else{
                    for(int j=0; j<returnResult.size(); j++) {
                        String EJLabel = returnResult.get(j).getLabel();
                        int EJLabelSum = listMap2.get(EJLabel) != null ? listMap2.get(EJLabel).size() : 0;
                        String EJnewLabel = EJLabel + "(" + EJLabelSum + ")";
                        returnResult.get(j).setLabel(EJnewLabel);
                    }
                }
            }
        }
        return returnResult;
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

    @Override
    public List getAllManufacturers() {
        List retrunList = dao.getAllManufacturers();
        retrunList.add(0,"全部厂商");
        return retrunList;
    }

    @Override
    public List getAuthorities() {
        List retrunList = dao.getAuthorities();
        retrunList.add(0,"全部单位");
        return retrunList;
    }

    @Override
    public ReturnResult getDataOrganization(String dataOrganizationType, String classify, String classifyid, String manufacturer, String authority, String search, String dataSet) {
        ReturnResult returnResult = new ReturnResult();
        // 请求的组织分类级别
        int dataOrgLevel = 1;
        // 业务要素索引库特殊处理
        if (dataOrganizationType.equalsIgnoreCase("业务要素索引库")){
            classify = "";
            classifyid = "";
        }
        // 统计表数量
        Set<DataOrganization> objectTableSet = new HashSet<>();
        if (StringUtils.isBlank(classifyid)){
            dataOrgLevel = 1;
            String dataOCCode = SysCodeEnum.getCodeByNameAndType(dataOrganizationType,"DATAOC");
            objectTableSet = dao.getObjectInfos(1,dataOCCode,search);
        } else {
            String dataOCCode = "";
            if (classifyid.length() > 20){
                dataOrgLevel = 3;
                dataOCCode = classifyid.substring(20,classifyid.length());
                objectTableSet = dao.getObjectInfos(3,dataOCCode,search);
            }else {
                dataOrgLevel = 2;
                dataOCCode = classifyid.substring(16,classifyid.length());
                objectTableSet = dao.getObjectInfos(2,dataOCCode,search);
            }
        }
        int tableNums = objectTableSet.size();
        returnResult.setTableNums(tableNums);

        //资源标识，中文表名，物理表名,OBJECTID
        if (dataSet.equalsIgnoreCase("全部数据集") || dataSet.isEmpty()){
            objectTableSet = dao.getDataOrganizationTable(dataOrganizationType, classify, classifyid, search);
        }
        //查询synlte.public_data_info中的来源厂商和事权单位
        Map<DataOrganization,DataOrganization> dataInfoMap = new HashMap();
        try{
            manufacturer = manufacturer.equalsIgnoreCase("全部厂商") ? "" : manufacturer;
            authority = authority.equalsIgnoreCase("全部单位") ? "" : authority;
            Set<DataOrganization> dataInfoTableSet = dao.getDataInfoTable(manufacturer, authority);
            for (DataOrganization dataOrganization : dataInfoTableSet) {
                dataInfoMap.put(dataOrganization,dataOrganization);
            }
            //若有筛选条件，需取交集
            if (StringUtils.isNotBlank(manufacturer) || StringUtils.isNotBlank(authority)) {
                objectTableSet.retainAll(dataInfoTableSet);
            }
        }catch (Exception e) {
            logger.error("获取来源厂商和事权单位报错\n"+ ExceptionUtil.getExceptionTrace(e));
        }
        //获取存储位置
        Map<DataOrganization,DataOrganization> assetsInfoMap = new HashMap();
        List<JSONObject> allDataResrouce = null;
        try{
            //获取资产数据
            //Set<DataOrganization> assetsSet = dao.getOrganizationAssetsTable(dataOrganizationType,classify,tableType,tableProject);
            int todayAssetsCount = dataStorageMonitorDao.getTodayAssetsCount();
            int daysAgo = todayAssetsCount<100?1:0;
            Set<DataOrganization> assetsSet = dao.getOrganizationAssetsTable(dataOrganizationType,classify, daysAgo, dataOrgLevel);
            for (DataOrganization dataOrganization : assetsSet) {
                assetsInfoMap.put(dataOrganization,dataOrganization);
            }
            //获取所有数据源
//            allDataResrouce = restTemplate.getForObject(TableOrganizationConstant.DATARESOURCE_BASEURL + "/DataResource/getAllDataResource", JSONArray.class);
            String getAllResources = restTemplate.getForObject(UrlConstants.DATARESOURCE_BASEURL + "/dataresource/api/getAllResources", String.class);
            if(StringUtils.isNotBlank(getAllResources) && "1".equals(JSONObject.parseObject(getAllResources).getString("status"))){
                String str = JSONObject.parseObject(getAllResources).getObject("data",String.class);
                allDataResrouce = JSONArray.parseArray(str, JSONObject.class);
            }
        } catch (Exception e) {
            logger.error("获取存储位置报错\n"+ ExceptionUtil.getExceptionTrace(e));
        }
        //注入来源厂商和事权单位, 存储位置和数据量 ，数据中心
        List<JSONObject> finalAllDataResrouce = allDataResrouce;
        objectTableSet.parallelStream().forEach(item -> {
            // 数据中心默认值
            String dataCenterNameTemp = "资源共享平台中心";
            // 默认注册状态
            String registerState = "-1";
            DataOrganization temp1 = dataInfoMap.get(item);
            if(temp1!=null) {
                item.setAuthority(temp1.getAuthority());
                item.setManufacturer(temp1.getManufacturer());
            }
            DataOrganization temp2 = assetsInfoMap.get(item);
            if(temp2!=null) {
                item.setStorageLocation(temp2.getStorageLocation());
                item.setDataNum(temp2.getDataNum());
                if(StringUtils.isNotBlank(temp2.getResourceId()) && finalAllDataResrouce != null){
                    for (int i = 0; i < finalAllDataResrouce.size(); i++){
                        JSONObject map = finalAllDataResrouce.get(i);
                        if(map.get("resId").equals(temp2.getResourceId())){
                            dataCenterNameTemp = (String) map.get("centerName");
                            break;
                        }
                    }
                }
                item.setDataCenter(dataCenterNameTemp);
                item.setRegisterState(temp2.getRegisterState());
                item.setResourceId(temp2.getResourceId());
            } else {
                // 数据中心默认值
                item.setDataCenter(dataCenterNameTemp);
                item.setRegisterState(registerState);
            }
        });
        returnResult.setDataOrganizations(objectTableSet);
        return returnResult;
    }
}
