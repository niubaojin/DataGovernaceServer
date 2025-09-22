package com.synway.datastandardmanager.service.impl;

import com.synway.datastandardmanager.constant.Common;
import com.synway.datastandardmanager.dao.master.ObjectStoreInfoDao;
import com.synway.datastandardmanager.dao.master.SummaryTableDao;
import com.synway.datastandardmanager.dao.master.SynlteFieldDao;
import com.synway.datastandardmanager.pojo.FilterObject;
import com.synway.datastandardmanager.pojo.PageSelectOneValue;
import com.synway.datastandardmanager.pojo.enums.ObjectStateType;
import com.synway.datastandardmanager.pojo.summaryobjectpage.*;
import com.synway.datastandardmanager.service.SummaryTableService;
import com.synway.datastandardmanager.util.DateUtil;
import com.synway.datastandardmanager.util.ExceptionUtil;
import com.synway.datastandardmanager.util.RestTemplateHandle;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.Collator;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toMap;

/**
 * @author wangdongwei
 * @ClassName SummaryTableServiceImpl
 * @description 数据集管理页面的服务层
 * @date 2020/11/28 15:49
 */
@Service
public class SummaryTableServiceImpl implements SummaryTableService {
    private static Logger logger = LoggerFactory.getLogger(SummaryTableServiceImpl.class);

    @Autowired
    private SummaryTableDao summaryTableDao;

    @Autowired
    private SynlteFieldDao synlteFieldDao;

    @Autowired
    private RestTemplateHandle restTemplateHandle;

    @Autowired
    private ObjectStoreInfoDao objectStoreInfoDao;

    /**
     *  汇总表格信息的获取
     *  1：先从classify_interface_all_date表中获取基本信息
     *  2：查询数据仓库的接口，查询这些表在大数据平台的建表情况
     *  3：补漏，查询表 standard_table_created中已建表(好像不需要)
     * @param summaryQueryParams  查询的参数
     * @return
     */
    @Override
    public SummaryTable searchSummaryTable(SummaryQueryParams summaryQueryParams) throws Exception {
        SummaryTable summaryTable = new SummaryTable();
        // 1：先从classify_interface_all_date表中获取基本信息
        if(StringUtils.isNotBlank(summaryQueryParams.getSort())){
            summaryQueryParams.setSort(summaryQueryParams.getSort().toUpperCase());
        }
        if("OBJECTSTATESTR".equalsIgnoreCase(summaryQueryParams.getSort())){
            summaryQueryParams.setSort("OBJECTSTATE");
        }

        Map<String,Long> tableCountMap = new HashMap<>();
        try{
            //从object_store_info 表中关联已建表信息
            List<String> AllTableIdList = objectStoreInfoDao.searchAllTableId();
            tableCountMap = AllTableIdList.stream().collect(Collectors.groupingBy(d->d,Collectors.counting()));
        }catch (Exception e){
            logger.error("从数据仓库中获取已建表信息报错"+ ExceptionUtil.getExceptionTrace(e));
        }
        // 2： 根据几个筛选条件来筛选相关数据，先筛选出所有的分类的条件
        // 组织分类的列表
        List<String> dataOrganizationClassifyList = new ArrayList<>();
        // 来源分类的列表
        List<String> dataSourceClassifyList = new ArrayList<>();
        // 数据标签的列表
        List<String> dataLabelClassifyList1 = new ArrayList<>();
        List<String> dataLabelClassifyList2 = new ArrayList<>();
        List<String> dataLabelClassifyList3 = new ArrayList<>();
        List<String> dataLabelClassifyList4 = new ArrayList<>();
        List<String> dataLabelClassifyList5 = new ArrayList<>();
        List<String> dataLabelClassifyList6 = new ArrayList<>();
        // Object的id的列表
        List<String> dataObjectClassifyList = new ArrayList<>();
        if(summaryQueryParams.getClassifyTags() != null){
            summaryQueryParams.getClassifyTags().forEach(receiveTag ->{
                if(Common.DATA_ORGANIZATION_CLASSIFY.equalsIgnoreCase(receiveTag.getMainClassify())){
                    if(StringUtils.isBlank(receiveTag.getSecondaryClassifyCh())){
                        dataOrganizationClassifyList.add(receiveTag.getPrimaryClassifyCh());
                        dataOrganizationClassifyList.add(receiveTag.getPrimaryClassifyCh() + "->" + receiveTag.getPrimaryClassifyCh());
                        dataOrganizationClassifyList.add(receiveTag.getPrimaryClassifyCh() + "->" + receiveTag.getPrimaryClassifyCh() + "->" + receiveTag.getPrimaryClassifyCh());
                    }else{
//                        if(receiveTag.getPrimaryClassifyCh().equalsIgnoreCase("原始库") || receiveTag.getPrimaryClassifyCh().equalsIgnoreCase("主题库")){
//                            if (receiveTag.getPrimaryClassifyCh().equalsIgnoreCase("主题库") && receiveTag.getThreeClassifyCh() == null){
//                                dataOrganizationClassifyList.add(receiveTag.getPrimaryClassifyCh()+"->"+receiveTag.getSecondaryClassifyCh()+"->"+receiveTag.getSecondaryClassifyCh());
//                            }else {
//                                dataOrganizationClassifyList.add(receiveTag.getPrimaryClassifyCh()+"->"+receiveTag.getSecondaryClassifyCh()+"->"+receiveTag.getThreeClassifyCh());
//                            }
//                        }else {
//                            dataOrganizationClassifyList.add(receiveTag.getPrimaryClassifyCh() + "->"+receiveTag.getSecondaryClassifyCh());
//                        }
                        if (receiveTag.getThreeClassifyCh() == null){
                            if (receiveTag.getPrimaryClassifyCh().equalsIgnoreCase("主题库")){
                                dataOrganizationClassifyList.add(receiveTag.getPrimaryClassifyCh()+"->"+receiveTag.getSecondaryClassifyCh()+"->"+receiveTag.getSecondaryClassifyCh());
                            }else {
                                dataOrganizationClassifyList.add(receiveTag.getPrimaryClassifyCh()+"->"+receiveTag.getPrimaryClassifyCh()+"->"+receiveTag.getSecondaryClassifyCh());
                            }
                        }else {
                            dataOrganizationClassifyList.add(receiveTag.getPrimaryClassifyCh()+"->"+receiveTag.getSecondaryClassifyCh()+"->"+receiveTag.getThreeClassifyCh());
                        }
                    }
                }else if(Common.DATA_LABEL_CLASSIFY.equalsIgnoreCase(receiveTag.getMainClassify())){
                    if (receiveTag.getPrimaryClassifyCh().equalsIgnoreCase("标签一")){
                        dataLabelClassifyList1.add(receiveTag.getSecondaryClassifyCh());
                    }else if(receiveTag.getPrimaryClassifyCh().equalsIgnoreCase("标签二")){
                        dataLabelClassifyList2.add(receiveTag.getSecondaryClassifyCh());
                    }else if(receiveTag.getPrimaryClassifyCh().equalsIgnoreCase("标签三")){
                        dataLabelClassifyList3.add(receiveTag.getSecondaryClassifyCh());
                    }else if(receiveTag.getPrimaryClassifyCh().equalsIgnoreCase("标签四")){
                        dataLabelClassifyList4.add(receiveTag.getSecondaryClassifyCh());
                    }else if(receiveTag.getPrimaryClassifyCh().equalsIgnoreCase("标签五")){
                        dataLabelClassifyList5.add(receiveTag.getSecondaryClassifyCh());
                    }else if(receiveTag.getPrimaryClassifyCh().equalsIgnoreCase("标签六")){
                        dataLabelClassifyList6.add(receiveTag.getSecondaryClassifyCh());
                    }
                }else if(Common.DATA_ELEMENT_CLASSIFY.equalsIgnoreCase(receiveTag.getMainClassify())){
                    //根据数据要素的标识符查出所对应的ObjectId列表
                    List<String> idList = synlteFieldDao.searchObjectIdByFieldId(receiveTag.getSecondaryClassifyCh());
                    dataObjectClassifyList.addAll(idList);
                }else{
                    if(StringUtils.isBlank(receiveTag.getSecondaryClassifyCh())){
                        dataSourceClassifyList.add(receiveTag.getPrimaryClassifyCh());
                        dataSourceClassifyList.add(receiveTag.getPrimaryClassifyCh() + "->" + receiveTag.getPrimaryClassifyCh());
                        dataSourceClassifyList.add(receiveTag.getPrimaryClassifyCh() + "->" + receiveTag.getPrimaryClassifyCh() + "->" + receiveTag.getPrimaryClassifyCh());
                    }else{
                        dataSourceClassifyList.add(receiveTag.getPrimaryClassifyCh()+"->"+receiveTag.getSecondaryClassifyCh());
                    }
                }
            });
        }
        // 资源状态的列表
        List<String> useTageValueList = new ArrayList<>();
        if(summaryQueryParams.getUsingTags() != null){
            summaryQueryParams.getUsingTags().forEach( useTageValue ->{
                if(Common.YI_FA_BU.equals(useTageValue.getLabel())){
                    useTageValueList.add("1");
                }
                if (Common.WEI_FA_BU.equals(useTageValue.getLabel())){
                    useTageValueList.add("0");
                }
                if ("停用".equals(useTageValue.getLabel())){
                    useTageValueList.add("-1");
                }
            });
        }

        // 这里可能回造成数据库瓶颈，压测的时候只能 15个并发，所有数据库中的筛选和排序使用java程序获取（改为数据库过滤）
        List<SummaryObjectTable> listAll = summaryTableDao.searchSummaryTableByParams(summaryQueryParams,
                                                                                        dataOrganizationClassifyList,
                                                                                        dataSourceClassifyList,
                                                                                        dataLabelClassifyList1,
                                                                                        dataLabelClassifyList2,
                                                                                        dataLabelClassifyList3,
                                                                                        dataLabelClassifyList4,
                                                                                        dataLabelClassifyList5,
                                                                                        dataLabelClassifyList6,
                                                                                        useTageValueList,
                                                                                        dataObjectClassifyList);
        List<SummaryObjectTable> filterSummaryObjectTable = filterSummaryObjectTable(listAll,summaryQueryParams);
        if(filterSummaryObjectTable.isEmpty()){
            logger.error("从数据库中获取到的数据为空");
            summaryTable.setTableCount(0);
            return summaryTable;
        }

        //  3： 筛选数据
        List<SummaryObjectTable> list = getSummaryObjectTableFilter(filterSummaryObjectTable,tableCountMap,summaryQueryParams);

        // 4: 获取页面上所有需要的筛选值
        setAllFilterData(listAll,summaryTable);

        summaryTable.setTableCount(list.size());
        summaryTable.setSummaryObjectTable(list);
        return summaryTable;
    }


    /**
     * 数据筛选 根据 时间  tableid 名称
     * @param listAll
     * @param summaryQueryParams
     * @return
     */
    private List<SummaryObjectTable> filterSummaryObjectTable(List<SummaryObjectTable> listAll,SummaryQueryParams summaryQueryParams){
        List<SummaryObjectTable> list = listAll.parallelStream().filter(d->{
            try{
                if(StringUtils.isNotBlank(summaryQueryParams.getTableId())&&
                        !StringUtils.equalsIgnoreCase(summaryQueryParams.getTableId(),d.getTableId())){
                    return false;
                }
                String dbTime = "";
                if(StringUtils.equalsIgnoreCase(summaryQueryParams.getTimeTypeSummary(),SummaryQueryParams.CREATE_TIME_TYPE)){
                    dbTime = d.getCreateTime();
                }else if(StringUtils.equalsIgnoreCase(summaryQueryParams.getTimeTypeSummary(),SummaryQueryParams.UPDATE_TIME_TYPE)){
                    dbTime = d.getUpdateTime();
                }
                if( StringUtils.isNotBlank(summaryQueryParams.getStartTimeText())){
                    if(StringUtils.isBlank(dbTime)){
                        return false;
                    }else if(DateUtil.parseDate(dbTime,DateUtil.DEFAULT_PATTERN_DATE_SIMPLE).before(
                            DateUtil.parseDate(summaryQueryParams.getStartTimeText(),DateUtil.DEFAULT_PATTERN_DATE_SIMPLE))){
                        return false;
                    }
                }
                if(StringUtils.isNotBlank(summaryQueryParams.getEndTimeText())){
                    if(StringUtils.isBlank(dbTime)){
                        return false;
                    }else if(DateUtil.parseDate(dbTime,DateUtil.DEFAULT_PATTERN_DATE_SIMPLE).after(
                            DateUtil.parseDate(summaryQueryParams.getEndTimeText(),DateUtil.DEFAULT_PATTERN_DATE_SIMPLE))){
                        return false;
                    }
                }
//                if(StringUtils.isNotBlank(summaryQueryParams.getSearchCode())){
//                    return StringUtils.containsIgnoreCase(d.getTableName(),summaryQueryParams.getSearchCode())
//                            || StringUtils.containsIgnoreCase(d.getTableId(),summaryQueryParams.getSearchCode())
//                            || StringUtils.containsIgnoreCase(d.getObjectName(),summaryQueryParams.getSearchCode());
//                }
                return true;
            }catch (Exception e){
                logger.error("数据筛选报错"+e.getMessage());
                return false;
            }
        }).collect(Collectors.toList());
        return list;
    }



    /**
     * 获取所有的筛选内容信息
     * @param listAll
     * @param summaryTable
     */
    private void setAllFilterData(List<SummaryObjectTable> listAll,SummaryTable summaryTable){
        // 1:获取创建人的筛选值
        for(SummaryObjectTable summaryObjectTable:listAll){
            summaryObjectTable.setObjectStateStr(ObjectStateType.getObjectStateType(Integer.parseInt(summaryObjectTable.getObjectState())));
        }
        List<FilterObject> creatorFilter = new ArrayList<>();
        listAll.stream().map(SummaryObjectTable::getCreator).distinct().forEach(d->
                creatorFilter.add(new FilterObject(d,d))
        );
        // 2:获取修改人的筛选值
        List<FilterObject> updaterFilter = new ArrayList<>();
        listAll.stream().map(SummaryObjectTable::getUpdater).distinct().forEach(d->
                updaterFilter.add(new FilterObject(d,d))
        );
        // 3: 数据状态的筛选
        List<FilterObject> objectStatesFilter = new ArrayList<>();
        listAll.stream().map(SummaryObjectTable::getObjectStateStr).distinct().forEach(d->{
                if (StringUtils.isNotBlank(d)){
                    objectStatesFilter.add(new FilterObject(d,d));
                }
            }
        );
        // 应用系统的筛选
        List<FilterObject> dataSourceFilter = new ArrayList<>();
        listAll.stream().map(SummaryObjectTable::getDataSourceCh).distinct().forEach(d->{
                if (StringUtils.isNotBlank(d)){
                    dataSourceFilter.add(new FilterObject(d,d));
                }
            }
        );
        dataSourceFilter.sort((s1,s2) -> Collator.getInstance(java.util.Locale.CHINA).compare(s1.getText(),s2.getText()));
        creatorFilter.sort((s1,s2) -> Collator.getInstance(java.util.Locale.CHINA).compare(s1.getText(),s2.getText()));
        updaterFilter.sort((s1,s2) -> Collator.getInstance(java.util.Locale.CHINA).compare(s1.getText(),s2.getText()));
        objectStatesFilter.sort((s1,s2) -> Collator.getInstance(java.util.Locale.CHINA).compare(s1.getText(),s2.getText()));
        summaryTable.setCreatorFilter(creatorFilter);
        summaryTable.setUpdaterFilter(updaterFilter);
        summaryTable.setObjectStatesFilter(objectStatesFilter);
        summaryTable.setDataSourceFilter(dataSourceFilter);
    }


    /**
     *  根据筛选条件筛选相关数据
     * @param filterSummaryObjectTable
     * @param tableCountMap
     * @param summaryQueryParams
     * @return
     */
    private List<SummaryObjectTable> getSummaryObjectTableFilter(List<SummaryObjectTable> filterSummaryObjectTable, Map<String,Long> tableCountMap, SummaryQueryParams summaryQueryParams){
        List<SummaryObjectTable> list = new ArrayList<>();
        for(SummaryObjectTable summaryObjectTable:filterSummaryObjectTable){
            summaryObjectTable.setCreatedTables(tableCountMap.getOrDefault(summaryObjectTable.getTableId().toUpperCase(),Long.valueOf("0")));
            if(StringUtils.isBlank(summaryObjectTable.getObjectState())){
                summaryObjectTable.setObjectState("-1");
            }
            summaryObjectTable.setObjectStateStr(ObjectStateType.getObjectStateType(Integer.parseInt(summaryObjectTable.getObjectState())));
            // 此时，需要使用几个筛选的列表来筛选数据
            //过滤数据，包含'->'的除原始库的组织分类，需要重新赋值
            if(summaryObjectTable.getDataOrganizationClassify().contains("->")){
                String dataOrganizationClassify = summaryObjectTable.getDataOrganizationClassify();
                String[] dataOrganizationClassifySplit = dataOrganizationClassify.split("->");
                if(!(dataOrganizationClassifySplit[0].equalsIgnoreCase("原始库") ||
                        (dataOrganizationClassifySplit[0].equalsIgnoreCase("主题库")
                        && !dataOrganizationClassifySplit[0].equalsIgnoreCase(dataOrganizationClassifySplit[1])
                        && StringUtils.isNotBlank(dataOrganizationClassifySplit[1])) )
                    && !Common.WEI_ZHI.equalsIgnoreCase(dataOrganizationClassifySplit[0])){
                    //除原始库和未知外，其它组织分类需要展示：1级—>2级
                    summaryObjectTable.setDataOrganizationClassify(dataOrganizationClassifySplit[0]+"->"+dataOrganizationClassifySplit[2]);
                }else if(Common.WEI_ZHI.equalsIgnoreCase(dataOrganizationClassifySplit[0])){
                    //未知时，只需要展示：1级
                    summaryObjectTable.setDataOrganizationClassify(dataOrganizationClassifySplit[0]);
                }
            }
            if(tableDataFilter(summaryObjectTable,summaryQueryParams)){
                list.add(summaryObjectTable);
            }
        }
        // 排序 如果是按照已建表的数量来进行排序，则需要在这里进行排序
        if (Common.CREATED_TABLES.equalsIgnoreCase(summaryQueryParams.getSort()) &&
                Common.ASC.equalsIgnoreCase(summaryQueryParams.getSortOrder())){
            list.sort(Comparator.comparingLong(SummaryObjectTable::getCreatedTables));
        }else if(Common.CREATED_TABLES.equalsIgnoreCase(summaryQueryParams.getSort()) &&
                Common.DESC.equalsIgnoreCase(summaryQueryParams.getSortOrder())){
            list.sort(Comparator.comparingLong(SummaryObjectTable::getCreatedTables).reversed());
        }
        // 序号的排序问题
        // 5: 获取 数据处理规则的数据量 和 质量检测规则的数据量 然后再给相关数据赋值
        // 质量检测规则的key值是 根据 tableId,  数据处理规则的key值是 tableId+sysCode
        //1.9 去除这个
//        Map<String,Long> mapKnowledge = new HashMap<>(12);
//        Map<String,Long> mapProcess = new HashMap<>(12);
//        setAllRuleCount(list,mapKnowledge,mapProcess);

        int startNum = 1;
        for(SummaryObjectTable summaryObjectTable:list){
            summaryObjectTable.setRecno(String.valueOf(startNum));
            // 根据2个map值筛选出相关的数据量信息
//            summaryObjectTable.setKnowledgeConfigCount(
//                    mapKnowledge.getOrDefault(summaryObjectTable.getTableId().toLowerCase(),0L));
//            summaryObjectTable.setProcessRuleCount(
//                    mapProcess.getOrDefault((summaryObjectTable.getTableId()+summaryObjectTable.getDataSourceCode()).toLowerCase()
//                            ,0L));
            startNum++;
        }

        return list;
    }

    @Override
    public List<PageSelectOneValue> getPrimaryClassifyData(String mainClassify) throws Exception{
        List<PageSelectOneValue> oneResultList;
        if(StringUtils.isBlank(mainClassify)){
            logger.error("查询的参数条件mainClassify【"+mainClassify+"】为空，不能进行查询");
            throw new NullPointerException("查询的参数条件mainClassify【"+mainClassify+"】为空，不能进行查询");
        }
        if(Common.DATA_ORGANIZATION_CLASSIFY.equalsIgnoreCase(mainClassify) ||
                Common.DATA_SOURCE_CLASSIFY.equalsIgnoreCase(mainClassify)){
            /* 数据组织分类 */
            oneResultList = summaryTableDao.getPrimaryClassifyData(mainClassify.toLowerCase());
        }else {
            logger.error("查询的参数条件mainClassify不正确，不能进行查询");
            throw new NullPointerException("查询的参数条件mainClassify【" + mainClassify + "】不正确，不能进行查询");
        }
        return oneResultList;
    }

    /**
     *
     * @param mainClassify  主类名称
     * @param primaryClassifyCode   一级分类
     * @return
     * @throws Exception
     */
    @Override
    public List<PageSelectOneValue> getSecondaryClassifyData(String mainClassify, String primaryClassifyCode) throws Exception {
        List<PageSelectOneValue> oneResultList =null;
        //二级码表值
        String secondCodeId = "";
        if(StringUtils.isBlank(mainClassify)){
            logger.error("查询的参数条件mainClassify【"+mainClassify+"】为空，不能进行查询");
            throw new NullPointerException("查询的参数条件mainClassify【"+mainClassify+"】为空，不能进行查询");
        }
        if(StringUtils.isBlank(primaryClassifyCode)){
            logger.error("查询的参数条件primaryClassifyCode【"+primaryClassifyCode+"】为空，不能进行查询");
            throw new NullPointerException("查询的参数条件primaryClassifyCode【"+primaryClassifyCode+"】为空，不能进行查询");
        }
        if(!Common.DATA_ORGANIZATION_CLASSIFY.equalsIgnoreCase(mainClassify) &&
                !Common.DATA_SOURCE_CLASSIFY.equalsIgnoreCase(mainClassify)){
            logger.error("查询的参数条件mainClassify【"+mainClassify+"】不为dataOrganizationClassify，dataSourceClassify");
            throw new NullPointerException("查询的参数条件mainClassify【"+mainClassify+"】不为dataOrganizationClassify，dataSourceClassify");
        }
        if(Common.DATA_SOURCE_CLASSIFY.equalsIgnoreCase(mainClassify) && !primaryClassifyCode.equalsIgnoreCase("中间表")){
            secondCodeId = primaryClassifyCode.split("GACODE000404")[1];
        }
        oneResultList = summaryTableDao.getSecondaryClassifyData(mainClassify.toLowerCase(),primaryClassifyCode.toLowerCase(),secondCodeId);
        if(oneResultList!=null && oneResultList.size() == 1
                && Common.WEI_ZHI.equals(oneResultList.get(0).getValue())
                && !Common.WEI_ZHI.equals(primaryClassifyCode)){
            oneResultList = null;
        }
        return oneResultList;
    }

    /**
     * 目前只有数据组织分类的原始库有三级分类
     */
    @Override
    public List<PageSelectOneValue> getThreeClassifyData(String primaryClassifyCode, String secondClassifyCode) throws Exception {
        List<PageSelectOneValue> oneResultList =null;
        String mainClassify = "dataorganizationclassify";
        oneResultList = summaryTableDao.getThreeClassifyData(primaryClassifyCode+secondClassifyCode, secondClassifyCode);
        if(oneResultList!=null && oneResultList.size() == 1
                && Common.WEI_ZHI.equals(oneResultList.get(0).getValue())
                && !Common.WEI_ZHI.equals(primaryClassifyCode)){
            oneResultList = null;
        }
        return oneResultList;
    }



    @Override
    public List<PageSelectOneValue> getResourceStatus() throws Exception {
        List<PageSelectOneValue> pageSelectOneValueList = summaryTableDao.getResourceStatus();
        if(CollectionUtils.isEmpty(pageSelectOneValueList)){
            PageSelectOneValue pageSelectOneValue = new PageSelectOneValue("已发布","已发布(0)");
            PageSelectOneValue pageSelectTwoValue = new PageSelectOneValue("未发布","未发布(0)");
            PageSelectOneValue pageSelectThreeValue = new PageSelectOneValue("停用","停用(0)");
            pageSelectOneValueList.add(pageSelectOneValue);
            pageSelectOneValueList.add(pageSelectTwoValue);
            pageSelectOneValueList.add(pageSelectThreeValue);
        }
        return pageSelectOneValueList;
    }


    /**
     * 20210401 新增需求 需要进行排序，去除掉搜索内容后进行排序
     * @param searchValue
     * @return
     */
    @Override
    public List<String> queryConditionSuggestion(String searchValue) {
        if(StringUtils.isBlank(searchValue)){
            searchValue = "";
        }
        List<String> commonMaps = summaryTableDao.queryConditionSuggestion(searchValue);
        final String searchValueOld = searchValue;
        if(commonMaps != null && !commonMaps.isEmpty()){
            commonMaps = commonMaps.stream().filter(StringUtils::isNotBlank).sorted((s1, s2) -> Collator.getInstance(Locale.CHINA)
                    .compare(s1.replaceFirst(searchValueOld,""), s2.replaceFirst(searchValueOld,"")))
                    .collect(Collectors.toList());
        }
        return commonMaps;
    }


    /**
     *  判断这行数据是否需要给页面上展示
     * @param summaryObjectTable  数据
     * @param summaryQueryParams 相关的查询条件
     * @return  true:表示需要在页面山展示 false:不需要在页面上展示
     */
    private boolean tableDataFilter(SummaryObjectTable summaryObjectTable,
                                    SummaryQueryParams summaryQueryParams){
        boolean flag = true;
        // 应用系统的筛选内容  这个是应用系统中文名
        if(summaryQueryParams.getDataSourceFilter()!= null && !summaryQueryParams.getDataSourceFilter().isEmpty()){
            flag = summaryQueryParams.getDataSourceFilter().contains(summaryObjectTable.getDataSourceCh());
            if(!flag) {
                return false;
            }
        }
        // 数据状态的筛选  中文
        if(summaryQueryParams.getObjectStatesFilter()!= null && !summaryQueryParams.getObjectStatesFilter().isEmpty()){
            flag = summaryQueryParams.getObjectStatesFilter().contains(summaryObjectTable.getObjectStateStr());
            if(!flag) {
                return false;
            }
        }
        // 创建人的筛选  存在空值
        if(summaryQueryParams.getCreatorFilter()!= null && !summaryQueryParams.getCreatorFilter().isEmpty()){
            flag = summaryQueryParams.getCreatorFilter().contains(summaryObjectTable.getCreator());
            if(!flag) {
                return false;
            }
        }
        // 修改人的筛选  存在空值
        if(summaryQueryParams.getUpdaterFilter()!= null && !summaryQueryParams.getUpdaterFilter().isEmpty()){
            flag = summaryQueryParams.getUpdaterFilter().contains(summaryObjectTable.getUpdater());
            if(!flag) {
                return false;
            }
        }
        //  已建物理表的相关筛选
        if(StringUtils.isNotBlank(summaryQueryParams.getCreateTableTotal())){
            if(SummaryQueryParams.EQUAL.equals(summaryQueryParams.getCreateTableCondition())){
                return Long.parseLong(summaryQueryParams.getCreateTableTotal()) == summaryObjectTable.getCreatedTables();
            }else if(SummaryQueryParams.MORE_THAN.equals(summaryQueryParams.getCreateTableCondition())){
                return Long.parseLong(summaryQueryParams.getCreateTableTotal()) < summaryObjectTable.getCreatedTables();
            }else if(SummaryQueryParams.LESS_THAN.equals(summaryQueryParams.getCreateTableCondition())){
                return Long.parseLong(summaryQueryParams.getCreateTableTotal()) > summaryObjectTable.getCreatedTables();
            }
        }
        return true;
    }


//    @Override
//    public void downloadSummaryTableExcel(HttpServletResponse response, List<SummaryObjectTable> summaryObjectTableList) {
//        try{
//            response.setContentType("application/vnd.ms-excel");
//            response.setCharacterEncoding("utf-8");
//            String fileName = URLEncoder.encode("数据定义管理汇总表","UTF-8")+".xlsx";
//            response.setHeader("Content-disposition",
//                    "attachment;filename*=utf-8''" +fileName);
////            SummaryTable summaryTable = this.searchSummaryTable(summaryQueryParams);
//            EasyExcel.write(response.getOutputStream(),SummaryObjectTable.class).autoCloseStream(Boolean.FALSE)
//                    .sheet("汇总表").doWrite(summaryObjectTableList);
//
//        }catch (Exception e){
//            logger.error("导出汇总表信息报错"+ ExceptionUtil.getExceptionTrace(e));
//        }
//    }


//    /**
//     * 获取本次查询中协议的所有  数据处理规则和 质量检测的规则数
//     * @param list
//     * @param mapKnowledge
//     * @param mapProcess
//     */
//    private void setAllRuleCount(List<SummaryObjectTable> list,Map<String,Long> mapKnowledge
//            ,Map<String,Long> mapProcess)  {
//        if(list == null || list.isEmpty()){
//            logger.info("查询出来的数据量为空，不需要获取质量规则库的信息");
//            return;
//        }
//        // 使用异步插件 ，同时获取2个插件中的 数据
//        CompletableFuture<Map<String,Long>> futureKnowledge = CompletableFuture.supplyAsync(() -> {
//            Map<String,Long> map = null;
//            try{
//                List<JSONObject> objects = restTemplateHandle.getKnowledgeConfig();
//                // 根据 tableId 这个参数来聚合 获取对应的数据量
//                map = objects.stream().filter(d -> StringUtils.isNotBlank(d.getString(Common.TABLE_ID)))
//                        .collect(Collectors.groupingBy((d -> d.getString(Common.TABLE_ID).toLowerCase()), Collectors.counting()));
//            }catch (Exception e){
//                map = new HashMap<>(1);
//                logger.error("获取质量检测规则的数据量报错"+ExceptionUtil.getExceptionTrace(e));
//            }
//            return map;
//        });
//        CompletableFuture<Map<String,Long>> futureProcess = CompletableFuture.supplyAsync(() -> {
//            Map<String,Long> map = null;
//            try{
//                List<JSONObject> objects = restTemplateHandle.getProcessRuleList();
//                map = objects.stream().filter(d -> (StringUtils.isNotBlank(d.getString(Common.TABLE_ID))
//                        && StringUtils.isNotBlank(d.getString(Common.SYS_CODE))))
//                        .collect(Collectors.groupingBy(d -> (d.getString(Common.TABLE_ID)+d.getString(Common.SYS_CODE)).toLowerCase(),
//                                Collectors.summingLong(d -> d.getLong(Common.TARGET_COUNT))));
//            }catch (Exception e){
//                map = new HashMap<>(1);
//                logger.error("获取数据处理规则的数据量报错"+ExceptionUtil.getExceptionTrace(e));
//            }
//            return map;
//        });
//        try{
//            mapKnowledge.putAll(futureKnowledge.get());
////            mapProcess.putAll(futureProcess.get());
//            logger.info("数据处理规则查询出的数据量为"+mapProcess.size()+ " 质量检测知识库的数据量为"+mapKnowledge.size());
//        }catch (Exception e){
//            logger.error("获取数据过程中报错"+ExceptionUtil.getExceptionTrace(e));
//        }
//    }

}
