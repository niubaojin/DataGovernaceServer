package com.synway.property.service.impl;

import com.synway.property.dao.DataMonitorDao;
import com.synway.property.dao.DataOrganizationDao;
import com.synway.property.dao.DataStorageMonitorDao;
import com.synway.property.entity.dto.DataOrganizationDTO;
import com.synway.property.pojo.dataOrganization.DataOrganization;
import com.synway.property.pojo.dataOrganization.ReturnResult;
import com.synway.property.pojo.formorganizationindex.ClassifyInfo;
import com.synway.property.pojo.formorganizationindex.ClassifyInfoTree;
import com.synway.property.pojo.formorganizationindex.PublicDataInfo;
import com.synway.property.service.DataOrganizationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author majia
 * @version 1.0
 * @date 2020/10/15 13:48
 */
@Slf4j
@Service
public class DataOrganizationServiceImpl implements DataOrganizationService {

    @Autowired
    private DataOrganizationDao dao;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DataMonitorDao dataMonitorDao;

    @Autowired
    DataStorageMonitorDao dataStorageMonitorDao;

    @Resource
    private Environment env;

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
        String sjzzflCodeId = env.getProperty("sjzzflCodeId");
        List<ClassifyInfo> classifyInfos = dataMonitorDao.getClassInfo(sjzzflCodeId);
        List<ClassifyInfoTree> classInfoJson = null;
        classInfoJson = convert2Tree(classifyInfos, sjzzflCodeId, new LinkedList<>());
        for (int i = 0; i < classInfoJson.size(); i++) {
            if (dataOrganizationType.equals(classInfoJson.get(i).getLabel())) {
                returnResult = classInfoJson.get(i).getChildren();
                if (dataOrganizationType.equalsIgnoreCase("业务要素索引库")) {
                    ClassifyInfoTree classifyInfoTree = new ClassifyInfoTree();
                    classifyInfoTree.setLabel("业务要素索引库");
                    classifyInfoTree.setValue(sjzzflCodeId + "06");
                    classifyInfoTree.setChildren(new ArrayList<>());
                    returnResult.add(classifyInfoTree);
                }
                if (dataOrganizationType.equals("原始库") || dataOrganizationType.equals("主题库")) {
                    for (int j = 0; j < returnResult.size(); j++) {
                        String EJLabel = returnResult.get(j).getLabel();
                        int EJLabelSum = listMap.get(EJLabel) != null ? listMap.get(EJLabel).size() : 0;
                        String EJnewLabel = EJLabel + "(" + EJLabelSum + ")";
                        returnResult.get(j).setLabel(EJnewLabel);
                        if (returnResult.get(j).getChildren().size() > 0) {
                            for (int k = 0; k < returnResult.get(j).getChildren().size(); k++) {
                                String SJLabel = returnResult.get(j).getChildren().get(k).getLabel();
                                List<PublicDataInfo> ciads2 = listMap.get(EJLabel);
                                if (ciads2 == null) {
                                    String SJnewLabel = SJLabel + "(" + 0 + ")";
                                    returnResult.get(j).getChildren().get(k).setLabel(SJnewLabel);
                                    continue;
                                }
                                Map<String, List<PublicDataInfo>> listMap3 = ciads2.stream().collect(Collectors.groupingBy(PublicDataInfo::getSjzzsjfl));
                                int SJLabelSum = listMap3.get(SJLabel) != null ? listMap3.get(SJLabel).size() : 0;
                                String SJnewLabel = SJLabel + "(" + SJLabelSum + ")";
                                returnResult.get(j).getChildren().get(k).setLabel(SJnewLabel);
                            }
                        }
                    }
                } else {
                    for (int j = 0; j < returnResult.size(); j++) {
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
     *
     * @return
     */
    public List<ClassifyInfoTree> convert2Tree(List<ClassifyInfo> tables, String codeId, List<ClassifyInfoTree> array) {
        for (ClassifyInfo table : tables) {
            if (StringUtils.isBlank(table.getCodeIdPar())) {
                continue;
            }
            if (table.getCodeIdPar().equalsIgnoreCase(codeId)) {
                ClassifyInfoTree classifyInfoTree = new ClassifyInfoTree();
                classifyInfoTree.setValue(table.getCodeId());
                classifyInfoTree.setLabel(table.getCodeText());
                classifyInfoTree.setChildren(convert2Tree(tables, table.getCodeId(), new LinkedList<>()));
                array.add(classifyInfoTree);
            }
        }
        return array;
    }

    @Override
    public List getAllManufacturers() {
        List retrunList = dao.getAllManufacturers();
        retrunList.add(0, "全部厂商");
        return retrunList;
    }

    @Override
    public List getAuthorities() {
        List retrunList = dao.getAuthorities();
        retrunList.add(0, "全部单位");
        return retrunList;
    }

    @Override
    public ReturnResult getDataOrganization(String dataOrganizationType, String classify, String classifyid, String manufacturer, String authority, String search, String dataSet) {
        ReturnResult returnResult = new ReturnResult();
        try {
            // 业务要素索引库特殊处理
            if (dataOrganizationType.equalsIgnoreCase("业务要素索引库")) {
                classify = "";
                classifyid = "";
            }
            // 请求的组织分类级别
            int dataOrgLevel = 1;
            if (StringUtils.isNotBlank(classify)) {
                if (classify.equalsIgnoreCase("原始汇集库") || classify.equalsIgnoreCase("原始标准库")) {
                    dataOrgLevel = 2;
                } else {
                    dataOrgLevel = 3;
                }
            }
            int todayAssetsCount = dataStorageMonitorDao.getTodayAssetsCount();
            int daysAgo = todayAssetsCount < 100 ? 1 : 0;
            String sjzzflCodeId = env.getProperty("sjzzflCodeId");
            DataOrganizationDTO dto = new DataOrganizationDTO(daysAgo, dataOrgLevel, dataOrganizationType, classify, search, manufacturer, authority, classifyid, sjzzflCodeId);
            Set<DataOrganization> assetsSet = dao.getDataOrganizationTableInfo(dto);
            returnResult.setDataOrganizations(assetsSet);
            returnResult.setTableNums(0);
        } catch (Exception e) {
            log.error(">>>>>>查询数据组织失败:", e);
        }
        return returnResult;
    }

}
