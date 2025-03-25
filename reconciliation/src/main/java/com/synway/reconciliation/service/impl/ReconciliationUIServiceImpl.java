package com.synway.reconciliation.service.impl;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hazelcast.core.HazelcastInstance;
import com.synway.reconciliation.common.Constants;
import com.synway.reconciliation.common.ErrorCode;
import com.synway.reconciliation.common.SystemException;
import com.synway.reconciliation.dao.InventoryReconDao;
import com.synway.reconciliation.dao.ReconciliationUIDao;
import com.synway.reconciliation.interceptor.AuthorizedUserUtils;
import com.synway.reconciliation.pojo.*;
import com.synway.reconciliation.service.CacheManageService;
import com.synway.reconciliation.service.ReconciliationUIService;
import com.synway.reconciliation.util.CacheManager;
import com.synway.reconciliation.util.DateParamUtil;
import com.synway.reconciliation.util.DateUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class ReconciliationUIServiceImpl implements ReconciliationUIService {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private ReconciliationUIDao reconciliationUIDao;

    @Autowired
    private DruidDataSource oracleDataSource;

    @Autowired
    private InventoryReconDao inventoryReconDao;

    private static Logger logger = LoggerFactory.getLogger(ReconciliationUIServiceImpl.class);

    private final HazelcastInstance hazelcastInstance;

    @Autowired
    public ReconciliationUIServiceImpl(HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
    }

    @Override
    public List<TreeNode> getOrganizationTree(GetListRequest req){
        List<TreeNode> treeNodes = new ArrayList<>();
        List<AccessListResponse> accessList = new ArrayList<>();
        List<StandardListResponse> standardList = new ArrayList<>();
        // 用户权限控制
        AuthorizedUser userInfo = AuthorizedUserUtils.getInstance().getAuthor();
        if(null != userInfo && !userInfo.getIsAdmin()) {
            req.setUserId(userInfo.getUserId());
        }
        if(StringUtils.equals("dataAccess", req.getAccessType()) || StringUtils.equals("dataStorage", req.getAccessType()) || StringUtils.equals("dataIssued", req.getAccessType())){
            if(StringUtils.equals("dataAccess", req.getAccessType())){
                accessList = reconciliationUIDao.getAccessList(req);
            } else if(StringUtils.equals("dataStorage", req.getAccessType())) {
                accessList = reconciliationUIDao.getStorageList(req);
            } else {
                accessList = reconciliationUIDao.getIssuedList(req);
            }
            if(!CollectionUtils.isEmpty(accessList)) {
                List<String> parimaryClassList = 1 == req.getType() ? accessList.stream().map(o -> o.getPrimaryOrganizationCh()).distinct().collect(toList()) : accessList.stream().map(o -> o.getPrimaryDatasourceCh()).distinct().collect(toList());
                Map<String, List<AccessListResponse>> parimaryClassMap = 1 == req.getType() ? accessList.stream().collect(Collectors.groupingBy(AccessListResponse::getPrimaryOrganizationCh)) : accessList.stream().collect(Collectors.groupingBy(AccessListResponse::getPrimaryDatasourceCh));
                for (String parent : parimaryClassList) {
                    //  一级分类的内容
                    TreeNode levelOne = new TreeNode();
                    levelOne.setId(parent);
                    List<AccessListResponse> levelOneList = parimaryClassMap.get(parent);
                    levelOne.setLabel(parent + "(" + String.valueOf(levelOneList.size()) + ")");
                    levelOne.setLevel(1);
                    // 一级分类之后的子节点数组
                    List<TreeNode> chlidOne = new ArrayList<>();
                    if (1 == req.getType() && StringUtils.equals("原始库", parent)) {
                        List<String> firstClassList = levelOneList.stream().map(o -> o.getFirstOrganizationCh()).distinct().collect(toList());
                        Map<String, List<AccessListResponse>> firstClassMap = levelOneList.stream().collect(Collectors.groupingBy(AccessListResponse::getFirstOrganizationCh));
                        for (String child : firstClassList) {
                            // 二级分类的数据
                            TreeNode levelTwo = new TreeNode();
                            levelTwo.setId(child);
                            levelTwo.setParent(parent);
                            List<AccessListResponse> levelTwoList = firstClassMap.get(child);
                            levelTwo.setLabel(child + "(" + String.valueOf(levelTwoList.size()) + ")");
                            levelTwo.setLevel(2);
                            List<TreeNode> chlidTwo = new ArrayList<>();
                            List<String> secondaryClassList = levelTwoList.stream().map(o -> o.getSecondaryOrganizationCh()).distinct().collect(toList());
                            Map<String, List<AccessListResponse>> secondaryClassMap = levelTwoList.stream().collect(Collectors.groupingBy(AccessListResponse::getSecondaryOrganizationCh));
                            for (String childTwo : secondaryClassList) {
                                // 三级分类的数据
                                TreeNode levelThree = new TreeNode();
                                levelThree.setId(childTwo);
                                levelThree.setParent(child);
                                levelThree.setGrandPar(parent);
                                List<AccessListResponse> levelThreeList = secondaryClassMap.get(childTwo);
                                levelThree.setLabel(childTwo + "(" + String.valueOf(levelThreeList.size()) + ")");
                                levelThree.setLevel(3);
                                List<TreeNode> chlidThree = new ArrayList<>();
                                levelThree.setChildren(chlidThree);
                                chlidTwo.add(levelThree);
                            }
                            levelTwo.setChildren(chlidTwo);
                            chlidOne.add(levelTwo);
                        }
                    } else {
                        List<String> secondaryClassList = 1 == req.getType() ? levelOneList.stream().map(o -> o.getSecondaryOrganizationCh()).distinct().collect(toList()) : levelOneList.stream().map(o -> o.getSecondaryDatasourceCh()).distinct().collect(toList());
                        Map<String, List<AccessListResponse>> secondaryClassMap = 1 == req.getType() ? levelOneList.stream().collect(Collectors.groupingBy(AccessListResponse::getSecondaryOrganizationCh)) : levelOneList.stream().collect(Collectors.groupingBy(AccessListResponse::getSecondaryDatasourceCh));
                        for (String child : secondaryClassList) {
                            // 二级分类的数据
                            TreeNode levelTwo = new TreeNode();
                            levelTwo.setId(child);
                            levelTwo.setParent(parent);
                            List<AccessListResponse> levelTwoList = secondaryClassMap.get(child);
                            levelTwo.setLabel(child + "(" + String.valueOf(levelTwoList.size()) + ")");
                            levelTwo.setLevel(2);
                            List<TreeNode> chlidTwo = new ArrayList<>();
                            levelTwo.setChildren(chlidTwo);
                            chlidOne.add(levelTwo);
                        }
                    }
                    levelOne.setChildren(chlidOne);
                    treeNodes.add(levelOne);
                }
            }
        }else{
            if(StringUtils.equals("standardization", req.getAccessType())){
                standardList = reconciliationUIDao.getStandardList(req);
            } else {
                standardList = reconciliationUIDao.getDistributionList(req);
            }
            if(!CollectionUtils.isEmpty(standardList)) {
                List<String> parimaryClassList = 1 == req.getType() ? standardList.stream().map(o -> o.getPrimaryOrganizationCh()).distinct().collect(toList()) : standardList.stream().map(o -> o.getPrimaryDatasourceCh()).distinct().collect(toList());
                Map<String, List<StandardListResponse>> parimaryClassMap = 1 == req.getType() ? standardList.stream().collect(Collectors.groupingBy(StandardListResponse::getPrimaryOrganizationCh)) : standardList.stream().collect(Collectors.groupingBy(StandardListResponse::getPrimaryDatasourceCh));
                for (String parent : parimaryClassList) {
                    //  一级分类的内容
                    TreeNode levelOne = new TreeNode();
                    levelOne.setId(parent);
                    List<StandardListResponse> levelOneList = parimaryClassMap.get(parent);
                    levelOne.setLabel(parent + "(" + String.valueOf(levelOneList.size()) + ")");
                    levelOne.setLevel(1);
                    // 一级分类之后的子节点数组
                    List<TreeNode> chlidOne = new ArrayList<>();
                    if (1 == req.getType() && StringUtils.equals("原始库", parent)) {
                        List<String> firstClassList = levelOneList.stream().map(o -> o.getFirstOrganizationCh()).distinct().collect(toList());
                        Map<String, List<StandardListResponse>> firstClassMap = levelOneList.stream().collect(Collectors.groupingBy(StandardListResponse::getFirstOrganizationCh));
                        for (String child : firstClassList) {
                            // 二级分类的数据
                            TreeNode levelTwo = new TreeNode();
                            levelTwo.setId(child);
                            levelTwo.setParent(parent);
                            List<StandardListResponse> levelTwoList = firstClassMap.get(child);
                            levelTwo.setLabel(child + "(" + String.valueOf(levelTwoList.size()) + ")");
                            levelTwo.setLevel(2);
                            List<TreeNode> chlidTwo = new ArrayList<>();
                            List<String> secondaryClassList = levelTwoList.stream().map(o -> o.getSecondaryOrganizationCh()).distinct().collect(toList());
                            Map<String, List<StandardListResponse>> secondaryClassMap = levelTwoList.stream().collect(Collectors.groupingBy(StandardListResponse::getSecondaryOrganizationCh));
                            for (String childTwo : secondaryClassList) {
                                // 三级分类的数据
                                TreeNode levelThree = new TreeNode();
                                levelThree.setId(childTwo);
                                levelThree.setParent(child);
                                levelThree.setGrandPar(parent);
                                List<StandardListResponse> levelThreeList = secondaryClassMap.get(childTwo);
                                levelThree.setLabel(childTwo + "(" + String.valueOf(levelThreeList.size()) + ")");
                                levelThree.setLevel(3);
                                List<TreeNode> chlidThree = new ArrayList<>();
                                levelThree.setChildren(chlidThree);
                                chlidTwo.add(levelThree);
                            }
                            levelTwo.setChildren(chlidTwo);
                            chlidOne.add(levelTwo);
                        }
                    } else {
                        List<String> secondaryClassList = 1 == req.getType() ? levelOneList.stream().map(o -> o.getSecondaryOrganizationCh()).distinct().collect(toList()) : levelOneList.stream().map(o -> o.getSecondaryDatasourceCh()).distinct().collect(toList());
                        Map<String, List<StandardListResponse>> secondaryClassMap = 1 == req.getType() ? levelOneList.stream().collect(Collectors.groupingBy(StandardListResponse::getSecondaryOrganizationCh)) : levelOneList.stream().collect(Collectors.groupingBy(StandardListResponse::getSecondaryDatasourceCh));
                        for (String child : secondaryClassList) {
                            // 二级分类的数据
                            TreeNode levelTwo = new TreeNode();
                            levelTwo.setId(child);
                            levelTwo.setParent(parent);
                            List<StandardListResponse> levelTwoList = secondaryClassMap.get(child);
                            levelTwo.setLabel(child + "(" + String.valueOf(levelTwoList.size()) + ")");
                            levelTwo.setLevel(2);
                            List<TreeNode> chlidTwo = new ArrayList<>();
                            levelTwo.setChildren(chlidTwo);
                            chlidOne.add(levelTwo);
                        }
                    }
                    levelOne.setChildren(chlidOne);
                    treeNodes.add(levelOne);
                }
            }
        }
        return treeNodes;
    }


    @Override
    public List<TreeNode> getOrganizationTreeForBill(ReconInfoRequest request) throws ParseException {
        List<TreeNode> treeNodes = new ArrayList<>();
        String dataTime = request.getSCSJ_RQSJ();
        Date date = DateUtil.parseDate(dataTime);
        int startTime = DateParamUtil.getDayStartInt(date);
        int endTime = DateParamUtil.getDayEndInt(date);
        request.setStartTime(startTime);
        request.setEndTime(endTime);
        List<OrganizationInfo> dataList = inventoryReconDao.getOrganizationTreeForBill(request);
        if (!CollectionUtils.isEmpty(dataList)) {
            // 1.组织 2.来源
            Map<String, List<OrganizationInfo>> primaryClassMap = 1 == request.getType()
                    ? dataList.stream().collect(Collectors.groupingBy(OrganizationInfo::getPrimaryOrganizationCh))
                    : dataList.stream().collect(Collectors.groupingBy(OrganizationInfo::getPrimaryDatasourceCh));
            //  一级分类的内容
            for (String parent : primaryClassMap.keySet()) {
                TreeNode levelOne = new TreeNode();
                levelOne.setId(parent);
                List<OrganizationInfo> levelOneList = primaryClassMap.get(parent);
                levelOne.setLabel(parent + "(" + levelOneList.size() + ")");
                levelOne.setLevel(1);
                List<String> value = levelOneList.stream().map(OrganizationInfo::getResourceId).distinct().collect(toList());
                levelOne.setValues(value);
                // 一级分类之后的子节点数组
                List<TreeNode> childOne = new ArrayList<>();
                if (1 == request.getType() && StringUtils.equals("原始库", parent)) {
                    // 二级分类的数据 是组织且是原始库的
                    Map<String, List<OrganizationInfo>> firstClassMap = levelOneList.stream().collect(Collectors.groupingBy(OrganizationInfo::getFirstOrganizationCh));
                    for (String child : firstClassMap.keySet()) {
                        TreeNode levelTwo = new TreeNode();
                        levelTwo.setId(child);
                        levelTwo.setParent(parent);
                        List<OrganizationInfo> levelTwoList = firstClassMap.get(child);
                        levelTwo.setLabel(child + "(" + levelTwoList.size() + ")");
                        levelTwo.setLevel(2);
                        value = levelTwoList.stream().map(OrganizationInfo::getResourceId).distinct().collect(toList());
                        levelTwo.setValues(value);
                        List<TreeNode> childTwo = new ArrayList<>();
                        // 三级分类的数据
                        Map<String, List<OrganizationInfo>> secondaryClassMap = levelTwoList.stream().collect(Collectors.groupingBy(OrganizationInfo::getSecondaryOrganizationCh));
                        for (String childSec : secondaryClassMap.keySet()) {
                            TreeNode levelThree = new TreeNode();
                            levelThree.setId(childSec);
                            levelThree.setParent(child);
                            levelThree.setGrandPar(parent);
                            List<OrganizationInfo> levelThreeList = secondaryClassMap.get(childSec);
                            levelThree.setLabel(childSec + "(" + levelThreeList.size() + ")");
                            levelThree.setLevel(3);
                            List<TreeNode> childThree = new ArrayList<>();
                            levelThree.setChildren(childThree);
                            value = levelThreeList.stream().map(OrganizationInfo::getResourceId).distinct().collect(toList());
                            levelThree.setValues(value);
                            childTwo.add(levelThree);
                        }
                        levelTwo.setChildren(childTwo);
                        childOne.add(levelTwo);
                    }
                } else {
                    // 二级分类的数据 非原始库的组织或来源
                    Map<String, List<OrganizationInfo>> secondaryClassMap = 1 == request.getType()
                            ? levelOneList.stream().collect(Collectors.groupingBy(OrganizationInfo::getSecondaryOrganizationCh))
                            : levelOneList.stream().collect(Collectors.groupingBy(OrganizationInfo::getSecondaryDatasourceCh));
                    for (String child : secondaryClassMap.keySet()) {
                        TreeNode levelTwo = new TreeNode();
                        levelTwo.setId(child);
                        levelTwo.setParent(parent);
                        List<OrganizationInfo> levelTwoList = secondaryClassMap.get(child);
                        levelTwo.setLevel(2);
                        levelTwo.setLabel(child + "(" + levelTwoList.size() + ")");
                        value = levelTwoList.stream().map(OrganizationInfo::getResourceId).distinct().collect(toList());
                        levelTwo.setValues(value);
                        List<TreeNode> childTwo = new ArrayList<>();
                        levelTwo.setChildren(childTwo);
                        childOne.add(levelTwo);
                    }
                }
                levelOne.setChildren(childOne);
                treeNodes.add(levelOne);
            }
        }
        return treeNodes;
    }

    private String fatchGroupKey(BillStatisticsHistory info){
        return info.getResourceId()+"#"+info.getDirection()+"#"+info.getTache();
    }

    /**
     * 分页数据封装map
     * @param pageInfo 分页数据
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    private Map<String, Object> putInfoToMap(PageInfo pageInfo, List<String> dataTimeList) {
        Map<String, Object> map = new HashMap<>();
        map.put("total", pageInfo.getTotal());
        map.put("rows", pageInfo.getList());
        map.put("dataTimeList", dataTimeList);
        return map;
    }

    @Override
    public Map<String, Object> getAccessList(GetListRequest getListRequest){
        Page page=  PageHelper.startPage(getListRequest.getPageNum(),getListRequest.getPageSize());
        if(StringUtils.isNotEmpty(getListRequest.getSortName())&&StringUtils.isNotEmpty(getListRequest.getSortOrder())){
            page.setOrderBy(getListRequest.getSortName()+" "+getListRequest.getSortOrder());
        }
        List<AccessListResponse> lists = new ArrayList<AccessListResponse>();
        // 用户权限控制
        AuthorizedUser userInfo = AuthorizedUserUtils.getInstance().getAuthor();
        if(null != userInfo && !userInfo.getIsAdmin()) {
            getListRequest.setUserId(userInfo.getUserId());
        }
        lists = reconciliationUIDao.getAccessList(getListRequest);
        List<BillStatisticsHistory> history = reconciliationUIDao.getStatisticsHistory(1,getListRequest.getStartTime());
        Map<String,List<BillStatisticsHistory>> map = history.stream().collect(Collectors.groupingBy(o->fatchGroupKey(o)));
        for(AccessListResponse accessListResponse : lists){
//            accessListResponse.setAccessAccept(accessListResponse.getAccessAccept().divide(new BigDecimal(10000)).setScale(4));
//            accessListResponse.setAccessProvide(accessListResponse.getAccessProvide().divide(new BigDecimal(10000)).setScale(4));
//            accessListResponse.setThirdpartyProvide(accessListResponse.getThirdpartyProvide().divide(new BigDecimal(10000)).setScale(4));
            accessListResponse.setIsUpdating(2);
            List<BillStatisticsHistory> histories = map.get(accessListResponse.getResourceId()+"#"+accessListResponse.getDataDirection()+"#"+accessListResponse.getTache());
            if(histories != null && histories.size() >0&& histories.get(0).getStatus()==1){
                accessListResponse.setIsUpdating(1);
            }
            if(StringUtils.isBlank(accessListResponse.getBeginTime()) || StringUtils.equals("0", accessListResponse.getBeginTime())){
                accessListResponse.setBeginTime("-");
            } else{
                accessListResponse.setBeginTime(sdf.format(new Date(Long.valueOf(accessListResponse.getBeginTime()))));
            }
//            if(accessListResponse.getThirdpartyProvide().compareTo(accessListResponse.getAccessProvide()) == 0 && accessListResponse.getThirdpartyProvide().compareTo(accessListResponse.getAccessAccept()) ==0)
//                accessListResponse.setStatus(1);
        }
        PageInfo<AccessListResponse> pageInfo = new PageInfo<AccessListResponse>(lists);
        List<String> dataTimeList = reconciliationUIDao.getAccessDataTimeList();
        Map<String, Object> result = putInfoToMap(pageInfo, dataTimeList);
        return result;
    }

    @Override
    public Map<String, Object> getStorageList(GetListRequest getListRequest){
        Page page=  PageHelper.startPage(getListRequest.getPageNum(),getListRequest.getPageSize());
        if(StringUtils.isNotEmpty(getListRequest.getSortName())&&StringUtils.isNotEmpty(getListRequest.getSortOrder())){
            page.setOrderBy(getListRequest.getSortName()+" "+getListRequest.getSortOrder());
        }
        List<AccessListResponse> lists = new ArrayList<AccessListResponse>();
        // 用户权限控制
        AuthorizedUser userInfo = AuthorizedUserUtils.getInstance().getAuthor();
        if(null != userInfo && !userInfo.getIsAdmin()) {
            getListRequest.setUserId(userInfo.getUserId());
        }
        // 数据入库列表数据=数据接入入本地仓库+数据处理入ODPS原始库及业务生产库
        lists = reconciliationUIDao.getStorageList(getListRequest);
        List<BillStatisticsHistory> history = reconciliationUIDao.getStatisticsHistory(1,getListRequest.getStartTime());
        Map<String,List<BillStatisticsHistory>> map = history.stream().collect(Collectors.groupingBy(o->fatchGroupKey(o)));
        for(AccessListResponse accessListResponse : lists){
//            accessListResponse.setAccessAccept(accessListResponse.getAccessAccept().divide(new BigDecimal(10000)).setScale(4));
//            accessListResponse.setAccessProvide(accessListResponse.getAccessProvide().divide(new BigDecimal(10000)).setScale(4));
//            accessListResponse.setThirdpartyProvide(accessListResponse.getThirdpartyProvide().divide(new BigDecimal(10000)).setScale(4));
            accessListResponse.setIsUpdating(2);
            List<BillStatisticsHistory> histories = map.get(accessListResponse.getResourceId()+"#"+accessListResponse.getDataDirection()+"#"+accessListResponse.getTache());
            if(histories != null && histories.size() >0&& histories.get(0).getStatus()==1){
                accessListResponse.setIsUpdating(1);
            }
            if(StringUtils.isBlank(accessListResponse.getBeginTime()) || StringUtils.equals("0", accessListResponse.getBeginTime())){
                accessListResponse.setBeginTime("-");
            } else {
                accessListResponse.setBeginTime(sdf.format(new Date(Long.valueOf(accessListResponse.getBeginTime()))));
            }
//            if(accessListResponse.getThirdpartyProvide().compareTo(accessListResponse.getAccessProvide()) == 0 && accessListResponse.getThirdpartyProvide().compareTo(accessListResponse.getAccessAccept()) ==0)
//                accessListResponse.setStatus(1);
        }
        PageInfo<AccessListResponse> pageInfo = new PageInfo<AccessListResponse>(lists);
        List<String> dataTimeList = reconciliationUIDao.getStorageDataTimeList();
        Map<String, Object> result = putInfoToMap(pageInfo, dataTimeList);
        return result;
    }

    @Override
    public Map<String, Object> getIssuedList(GetListRequest getListRequest) {
        Page page = PageHelper.startPage(getListRequest.getPageNum(), getListRequest.getPageSize());
        if (StringUtils.isNotEmpty(getListRequest.getSortName()) && StringUtils.isNotEmpty(getListRequest.getSortOrder())) {
            page.setOrderBy(getListRequest.getSortName() + " " + getListRequest.getSortOrder());
        }
        List<AccessListResponse> lists = new ArrayList<AccessListResponse>();
        lists = reconciliationUIDao.getIssuedList(getListRequest);
        PageInfo<AccessListResponse> pageInfo = new PageInfo<AccessListResponse>(lists);
        List<String> dataTimeList = reconciliationUIDao.getIssuedDataTimeList();
        Map<String, Object> result = putInfoToMap(pageInfo, dataTimeList);
        return result;
    }

    @Override
    public Map<String, Object> getDistributionList(GetListRequest getListRequest) {
        Page page = PageHelper.startPage(getListRequest.getPageNum(),getListRequest.getPageSize());
        if(StringUtils.isNotEmpty(getListRequest.getSortName())&&StringUtils.isNotEmpty(getListRequest.getSortOrder())){
            page.setOrderBy(getListRequest.getSortName()+" "+getListRequest.getSortOrder());
        }
        List<StandardListResponse> lists = new ArrayList<StandardListResponse>();
        // 用户权限控制
        AuthorizedUser userInfo = AuthorizedUserUtils.getInstance().getAuthor();
        if(null != userInfo && !userInfo.getIsAdmin()) {
            getListRequest.setUserId(userInfo.getUserId());
        }
        // 数据分发列表数据:数据处理入ODPS非原始库业务生产库+数据处理入非ODPS库
        lists = reconciliationUIDao.getDistributionList(getListRequest);
        for(StandardListResponse standardListResponse : lists){
//            standardListResponse.setAccessAccept(standardListResponse.getAccessAccept().divide(new BigDecimal(10000)).setScale(4));
//            standardListResponse.setAccessProvide(standardListResponse.getAccessProvide().divide(new BigDecimal(10000)).setScale(4));
            if(StringUtils.isBlank(standardListResponse.getBeginTime()) || StringUtils.equals("0", standardListResponse.getBeginTime())){
                standardListResponse.setBeginTime("-");
            } else {
                standardListResponse.setBeginTime(sdf.format(new Date(Long.valueOf(standardListResponse.getBeginTime()))));
            }
            standardListResponse.setIsUpdating(2);
        }
        PageInfo<StandardListResponse> pageInfo = new PageInfo<StandardListResponse>(lists);
        List<String> dataTimeList = reconciliationUIDao.getDistributionDataTimeList();
        Map<String, Object> result = putInfoToMap(pageInfo, dataTimeList);
        return result;
    }

    @Override
    public Map<String, Object> getStandardList(GetListRequest getListRequest){
        Page page = PageHelper.startPage(getListRequest.getPageNum(),getListRequest.getPageSize());
        if(StringUtils.isNotEmpty(getListRequest.getSortName())&&StringUtils.isNotEmpty(getListRequest.getSortOrder())){
            page.setOrderBy(getListRequest.getSortName()+" "+getListRequest.getSortOrder());
        }
        List<StandardListResponse> lists = new ArrayList<StandardListResponse>();
        // 用户权限控制
        AuthorizedUser userInfo = AuthorizedUserUtils.getInstance().getAuthor();
        if(null != userInfo && !userInfo.getIsAdmin()) {
            getListRequest.setUserId(userInfo.getUserId());
        }
        lists = reconciliationUIDao.getStandardList(getListRequest);
        for(StandardListResponse standardListResponse : lists){
            if(StringUtils.isBlank(standardListResponse.getBeginTime()) || StringUtils.equals("0", standardListResponse.getBeginTime())){
                standardListResponse.setBeginTime("-");
            } else {
                standardListResponse.setBeginTime(sdf.format(new Date(Long.valueOf(standardListResponse.getBeginTime()))));
            }
            standardListResponse.setIsUpdating(2);
        }
        PageInfo<StandardListResponse> pageInfo = new PageInfo<StandardListResponse>(lists);
        List<String> dataTimeList = reconciliationUIDao.getStandardDataTimeList();
        Map<String, Object> result = putInfoToMap(pageInfo, dataTimeList);
        return result;
    }




    @Override
    public PageInfo<AccessAccessStateListResponse> getAccessAccessStateList(GetListRequest req){
        PageHelper.startPage(req.getPageNum(),req.getPageSize());
        List<AccessAccessStateListResponse> lists = new ArrayList<>();
        lists = reconciliationUIDao.getAccessAccessStateList(req);
        for(AccessAccessStateListResponse accessListResponse : lists){
            if(accessListResponse.getAccessAccept().compareTo(accessListResponse.getAccessProvide()) == 0){
                accessListResponse.setStatus(1);
            }
            if(StringUtils.isBlank(accessListResponse.getBeginNo()) || StringUtils.equals("0", accessListResponse.getBeginNo())){
                accessListResponse.setBeginNo("-");
            } else {
                accessListResponse.setBeginNo(sdf.format(new Date(Long.valueOf(accessListResponse.getBeginNo()))));
            }
        }
        PageInfo<AccessAccessStateListResponse> pageInfo = new PageInfo<AccessAccessStateListResponse>(lists);
        return pageInfo;
    }

    @Override
    public PageInfo<StandardAccessStateListResponse> getStandardStorageStateList(GetListRequest req){
        PageHelper.startPage(req.getPageNum(),req.getPageSize());
        List<StandardAccessStateListResponse> lists = new ArrayList<>();
        if(1 == req.getTache()){
            lists = reconciliationUIDao.getStandardAccessStateList(req);
        } else {
            lists = reconciliationUIDao.getStandardStorageStateList(req);
        }
        for(StandardAccessStateListResponse accessListResponse : lists){
            accessListResponse.setUpdateTime(new Date());
            if(accessListResponse.getAccessAccept().compareTo(accessListResponse.getAccessProvide()) == 0){
                accessListResponse.setStatus(1);
            }
            if(StringUtils.isBlank(accessListResponse.getBeginNo()) || StringUtils.equals("0", accessListResponse.getBeginNo())){
                accessListResponse.setBeginNo("-");
            } else {
                accessListResponse.setBeginNo(sdf.format(new Date(Long.valueOf(accessListResponse.getBeginNo()))));
            }
        }
        PageInfo<StandardAccessStateListResponse> pageInfo = new PageInfo<StandardAccessStateListResponse>(lists);
        return pageInfo;
    }

    @Override
    public StandardListResponse updateStandard(Date startTime, String resourceId , String direction, int tache, String userId){
        StandardListResponse standardListResponse = new StandardListResponse();
        Connection connection = null;
        String id = UUID.randomUUID().toString();
        try{
            BillStatisticsHistory bsh = new BillStatisticsHistory();
            bsh.setId(id);
            bsh.setDataTime(startTime);
            bsh.setDirection(direction);
            bsh.setResourceId(resourceId);
            bsh.setStatus(1);
            bsh.setSystem(1);
            bsh.setTache(tache);
            reconciliationUIDao.insertStatisticsUpdateHistory(bsh);
            Calendar c = Calendar.getInstance();
            c.setTime(startTime);
            List<AccessBillStatistics> totalResult = new ArrayList<>();
            c.add(Calendar.HOUR_OF_DAY, -1);
            for (int i = 0; i < 24; i++) {
                c.add(Calendar.HOUR_OF_DAY, 1);
                String startDateT = getStartT(c.getTime());
                String endDateT = getEndT(c.getTime());
                String startDateY = getStartY(c.getTime());
                List<AccessBillStatistics> result = new ArrayList<>();
                if (tache == 1) {
                    result = reconciliationUIDao.statisticsStandardAccess(startDateT, endDateT, startDateY, direction, resourceId, userId);
                } else {
                    result = reconciliationUIDao.statisticsStandardStorage(startDateT, endDateT, direction, resourceId, userId);
                }
                totalResult.addAll(result);
            }
            String start = sdf.format(startTime);
            String end = start.substring(0,11) +"23:59:59";
            reconciliationUIDao.deleteFromStandardBillStatics(resourceId,direction,tache,start,end,userId);
            connection = oracleDataSource.getConnection();
            if(totalResult.size() >0){
                insertStandardBillStatistics(totalResult,connection);
            }
            if(tache == 1){
                standardListResponse = reconciliationUIDao.getStandardAccessUpdate(start,end,resourceId,direction,userId);
            }else if(tache == 2){
                standardListResponse = reconciliationUIDao.getStandardStorageUpdate(start,end,resourceId,direction,userId);
            }
            if(null != standardListResponse){
                standardListResponse.setUpdateTime(new Date());
                if(StringUtils.isBlank(standardListResponse.getBeginTime()) || StringUtils.equals("0", standardListResponse.getBeginTime())){
                    standardListResponse.setBeginTime("-");
                } else {
                    standardListResponse.setBeginTime(sdf.format(new Date(Long.valueOf(standardListResponse.getBeginTime()))));
                }
                if(standardListResponse.getAccessAccept().compareTo(standardListResponse.getAccessProvide()) == 0){
                    standardListResponse.setStatus(1);
                }
            }
            reconciliationUIDao.updateStatisticsUpdateHistory(id,2);
        }catch (Exception e){
            reconciliationUIDao.updateStatisticsUpdateHistory(id,3);
            try{
                if(connection !=null){
                    connection.rollback();
                }
            }catch (Exception e1){
                throw SystemException.asSystemException(ErrorCode.DB_ROLLBACK_ERROR, e1);
            }
            logger.error(e.toString());
            throw SystemException.asSystemException(ErrorCode.DATA_INSERT_ERROR, e);
        }finally {
            if(connection !=null){
                try {
                    connection.close();
                }catch (Exception e){

                }
            }
        }
        return standardListResponse;
    }

    private boolean insertStandardBillStatistics(List<AccessBillStatistics> list, Connection connection) {
        boolean isExe = false;
        PreparedStatement pstmt = null;
        if (list.size() == 0) {
            return true;
        }
        try {
            String SQLString = "insert into DAC_STANDARD_BILL_STATISTICS(ACCESS_SYSTEM,DATA_SOURCE,RESOURCE_ID,INSIDE_PROVIDE,INSIDE_ACCEPT,TACHE,DIRCTION,DATA_TIME,CREATETIME,START_NO,SYS_CODE,DATA_SOURCE_CODE,TABLE_NAME_EN,USER_ID,USER_NAME,INCEPT_DATA_TIME,OUTSIDE_ACCEPT) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(SQLString);
            int count = 0;
            for (AccessBillStatistics entry : list) {
                pstmt.setString(1, entry.getAccessSystem());
                pstmt.setString(2, entry.getDataSource());
                pstmt.setString(3, entry.getResourceId());
                pstmt.setLong(4, entry.getInsideProvide());
                pstmt.setLong(5, entry.getInsideAccess());
                pstmt.setInt(6, entry.getTache());
                pstmt.setString(7, entry.getDirection());
                pstmt.setTimestamp(8, entry.getDataTime());
                pstmt.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
                pstmt.setString(10, entry.getStartNo());
                pstmt.setString(11, entry.getSysCode());
                pstmt.setString(12, entry.getDataSourceCode());
                pstmt.setString(13, entry.getTableNameEn());
                pstmt.setString(14, entry.getUserId());
                pstmt.setString(15, entry.getUserName());
                pstmt.setString(16, entry.getInceptDataTime());
                pstmt.setLong(17, entry.getOutsideAccept());
                pstmt.addBatch();
                count++;
                //1000条提交一次
                if (count % 1000 == 0) {
                    pstmt.executeBatch();
                }
            }
            pstmt.executeBatch();
            connection.commit();
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (Exception e2) {
                throw SystemException.asSystemException(ErrorCode.DB_ROLLBACK_ERROR, e2);
            }
            isExe = true;
            logger.error(e.toString());
            throw SystemException.asSystemException(ErrorCode.DATA_INSERT_ERROR, e);
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e1) {

                }
            }
        }
        return isExe;
    }

    @Override
    public List<String>getAccessSystemList(int tache){
        if(tache == 1){
            return reconciliationUIDao.getAccessSystemList1();
        }else if(tache == 2){
            return reconciliationUIDao.getAccessSystemList2();
        }else{
            return reconciliationUIDao.getAccessSystemList3();
        }
    }

    @Override
    public DataCount getDataCount(GetDataCountReq req){
        DataCount dataCount = new DataCount();
        int accessStorageCount = reconciliationUIDao.getAccessDataCount(2,req.getStartTime(),req.getEndTime(),req.getDataName());
        int standardCount = reconciliationUIDao.getStandardDataCount(req.getStartTime(),req.getEndTime(),req.getDataName());
        int accessAccessCount =reconciliationUIDao.getAccessDataCount(1,req.getStartTime(),req.getEndTime(),req.getDataName());
        dataCount.setAccessAccessCount(accessAccessCount);
        dataCount.setAccessStorageCount(accessStorageCount);
        dataCount.setStandardCount(standardCount);
        return dataCount;
    }

    @Override
    public AccessListResponse updateAccess(Date startTime,String resourceId ,String direction,int tache, String isIssued, String userId){
        AccessListResponse accessListResponse = new AccessListResponse();
        Connection connection = null;
        String id = UUID.randomUUID().toString();
        try{
            BillStatisticsHistory bsh = new BillStatisticsHistory();
            bsh.setId(id);
            bsh.setDataTime(startTime);
            bsh.setDirection(direction);
            bsh.setResourceId(resourceId);
            bsh.setStatus(1);
            bsh.setSystem(1);
            bsh.setTache(tache);
            reconciliationUIDao.insertStatisticsUpdateHistory(bsh);
            Calendar c = Calendar.getInstance();
            c.setTime(startTime);
            List<AccessBillStatistics> totalResult = new ArrayList<>();
            c.add(Calendar.HOUR_OF_DAY, -1);
            for (int i = 0; i < 24; i++) {
                c.add(Calendar.HOUR_OF_DAY, 1);
                String startDateT = getStartT(c.getTime());
                String endDateT = getEndT(c.getTime());
                String startDateY = getStartY(c.getTime());
                List<AccessBillStatistics> result = new ArrayList<>();
                result = reconciliationUIDao.statisticsAcccessStorage(startDateT, endDateT, startDateY, resourceId, direction, userId);
                if (!direction.equals(Constants.DATA_PROCESS)) {
                    List<AccessBillStatistics> result2 = new ArrayList<>();
                    result2 = reconciliationUIDao.statisticsAcccessStorage(startDateT, endDateT, startDateY, resourceId, Constants.DATA_PROCESS, userId);
                    totalResult.addAll(result2);
                }
                totalResult.addAll(result);
            }
            String start = sdf.format(startTime);
            String end = start.substring(0,11) +"23:59:59";
            reconciliationUIDao.deleteFromAccessBillStatics(resourceId,direction,tache,start,end,userId);
            if(!direction.equals(Constants.DATA_PROCESS))
            {
                reconciliationUIDao.deleteFromAccessBillStatics(resourceId,Constants.DATA_PROCESS,tache,start,end,userId);
            }
            connection = oracleDataSource.getConnection();
            if (totalResult.size() > 0) {
                insertAccessBillStatistics(totalResult, connection);
            }
            accessListResponse =  reconciliationUIDao.getAccessUpdateData(resourceId,direction,tache,start,end,userId);
            if(accessListResponse != null){
//                accessListResponse.setAccessAccept(accessListResponse.getAccessAccept().divide(new BigDecimal(10000)).setScale(4));
//                accessListResponse.setAccessProvide(accessListResponse.getAccessProvide().divide(new BigDecimal(10000)).setScale(4));
//                accessListResponse.setThirdpartyProvide(accessListResponse.getThirdpartyProvide().divide(new BigDecimal(10000)).setScale(4));
                accessListResponse.setIsUpdating(2);
            }
            reconciliationUIDao.updateStatisticsUpdateHistory(id,2);
        }catch (Exception e){
            reconciliationUIDao.updateStatisticsUpdateHistory(id,3);
            try{
                if(connection !=null){
                    connection.rollback();
                }
            }catch (Exception e1){
                throw SystemException.asSystemException(ErrorCode.DB_ROLLBACK_ERROR, e1);
            }
            logger.error(e.toString());
        }finally {
            if(connection !=null){
                try {
                    connection.close();
                }catch (Exception e){

                }
            }
        }
        return accessListResponse;
    }

    public boolean insertAccessBillStatistics(List<AccessBillStatistics> list, Connection connection){
        boolean isExe = false;
        PreparedStatement pstmt=null;
        if (list.size() == 0) {
            return true;
        }
        try {
            String SQLString = "insert into DAC_ACCESS_BILL_STATISTICS(ACCESS_SYSTEM,DATA_SOURCE,RESOURCE_ID,OUTSIDE_PROVIDE,INSIDE_PROVIDE,INSIDE_ACCEPT,TACHE,DIRCTION,DATA_TIME,CREATETIME,OPERATION,START_NO,IS_ISSUED,TABLE_NAME,JOB_NAME,IS_LOCAL,TABLE_NAME_EN,USER_ID,USER_NAME,INCEPT_DATA_TIME) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(SQLString);
            int count = 0;
            for (AccessBillStatistics entry : list) {
                pstmt.setString(1, entry.getAccessSystem());
                pstmt.setString(2, entry.getDataSource());
                pstmt.setString(3, entry.getResourceId());
                pstmt.setLong(4, entry.getOutSideProvide());
                pstmt.setLong(5, entry.getInsideProvide());
                pstmt.setLong(6, entry.getInsideAccess());
                pstmt.setInt(7, entry.getTache());
                pstmt.setString(8, entry.getDirection());
                pstmt.setTimestamp(9, entry.getDataTime());
                pstmt.setTimestamp(10, new Timestamp(System.currentTimeMillis()));
                pstmt.setInt(11, 1);
                pstmt.setString(12, entry.getStartNo());
                pstmt.setString(13, entry.getIsIssued());
                pstmt.setString(14, entry.getTableName());
                pstmt.setString(15, entry.getJobName());
                pstmt.setString(16, StringUtils.defaultIfBlank(entry.getIsLocal(), "0"));
                pstmt.setString(17, entry.getTableNameEn());
                pstmt.setString(18, entry.getUserId());
                pstmt.setString(19, entry.getUserName());
                pstmt.setString(20, entry.getInceptDataTime());
                pstmt.addBatch();
                count++;
                //1000条提交一次
                if (count % 1000 == 0) {
                    pstmt.executeBatch();
                }
            }
            pstmt.executeBatch();
            connection.commit();
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (Exception e2) {
                throw SystemException.asSystemException(ErrorCode.DB_ROLLBACK_ERROR, e2);
            }
            isExe = true;
            logger.error(e.toString());
            throw SystemException.asSystemException(ErrorCode.DATA_INSERT_ERROR, e);
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e1) {

                }
            }
        }
        return isExe;
    }

    private String getStartT(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.SECOND,0);
        c.set(Calendar.MINUTE,0);
        String d = sdf.format(c.getTime());
        return d;
    }

    private String getEndT(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.SECOND,59);
        c.set(Calendar.MINUTE,59);
        String d = sdf.format(c.getTime());
        return d;
    }

    private String getStartY(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.SECOND,0);
        c.set(Calendar.MINUTE,0);
        c.add(Calendar.DAY_OF_YEAR,-1);
        String d = sdf.format(c.getTime());
        return d;
    }

    @Override
    public List<String> getAccessDirection(){
        return reconciliationUIDao.getAccessDirection();
    }

    @Override
    public List<String> getStorageDirection(){
        return reconciliationUIDao.getStorageDirection();
    }

    @Override
    public List<String> getStandardDirection(){
        return reconciliationUIDao.getStandardDirection();
    }

    @Override
    public List<String> getDistributionDirection(){
        return reconciliationUIDao.getDistributionDirection();
    }

    @Override
    public List<TreeNode> getOrganizationTreeForOrgPage(GetTreeReq req){
        List<TreeNode> treeNodes = new ArrayList<>();
        List<Relation> relations = new ArrayList<>();
        List<String> resourceIds = new ArrayList<>();
        if(req.getType() == 1){
            relations = reconciliationUIDao.getOrganizationList(req.getNodeName());
        }else if(req.getType() == 2){
            relations = reconciliationUIDao.getDataSourceList(req.getNodeName());
        }else {
            relations = reconciliationUIDao.getFactorList(req.getNodeName());
        }
        if(StringUtils.isNotEmpty(req.getStartTime())&&StringUtils.isNotEmpty(req.getEndTime())){
            if(req.getTache() == 1){
                resourceIds = reconciliationUIDao.getAccessStatisticResourceIds(req.getStartTime(),req.getEndTime());
            }else{
                resourceIds = reconciliationUIDao.getStandardStatisticResourceIds(req.getStartTime(),req.getEndTime());
            }
        }
        List<String> parents = relations.stream().map(o->o.getParent()).distinct().collect(Collectors.toList());
        Map<String,List<Relation>> parentMap = relations.stream().collect(Collectors.groupingBy(Relation::getParent));
        for(String parent : parents){
            TreeNode levelOne = new TreeNode();
            levelOne.setLabel(parent);
            levelOne.setId(parent);
            levelOne.setLevel(0);
            List<Relation> levelTowList = parentMap.get(parent);
            List<String> childrens = levelTowList.stream().map(o->o.getChild()).distinct().collect(Collectors.toList());
            Map<String,List<Relation>> childMap = levelTowList.stream().collect(Collectors.groupingBy(Relation::getChild));
            List<TreeNode> chlidOne = new ArrayList<>();
//            boolean flag = false;
            for(String child:childrens){
                TreeNode two = new TreeNode();
                two.setLabel(child);
                two.setId(child);
                two.setLevel(1);
                two.setParent(parent);
//                if(flag)
//                    two.setShowIcon(1);
                chlidOne.add(two);
            }
//            if(flag)
//                levelOne.setShowIcon(1);
            levelOne.setChildren(chlidOne);
            treeNodes.add(levelOne);
        }
        return treeNodes;
    }

    @Override
    public PageInfo queryDataDistributionBillInfo(GetReconciliationListRequest req) {
        PageHelper.startPage(req.getPageNum(), req.getPageSize());
        List<ReconciliationListResponse> list = new ArrayList<>();
        if(req.getBillType() == 1){
            list = reconciliationUIDao.queryCheckBillList(req);
        }else{
            list = reconciliationUIDao.queryCancelBillList(req);
        }
        PageInfo<ReconciliationListResponse> pageInfo = new PageInfo<ReconciliationListResponse>(list);
        return pageInfo;
    }

    @Override
    public List<AccessListResponse> getHistoryCompareStatistics(GetListRequest getListRequest) {
        List<AccessListResponse> result = new ArrayList<>();
        if(StringUtils.equals("dataAccess", getListRequest.getAccessType()) || (StringUtils.equals("dataStorage", getListRequest.getAccessType()) && StringUtils.equals("1", getListRequest.getIsLocal()))){
            result = reconciliationUIDao.getAccessHistoryCompareStatistics(getListRequest);
        } else {
            result = reconciliationUIDao.getStandardHistoryCompareStatistics(getListRequest);
        }
        return result;
    }

}
