package com.synway.datastandardmanager.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.synway.datastandardmanager.constants.Common;
import com.synway.datastandardmanager.entity.dto.DataSetManageDTO;
import com.synway.datastandardmanager.entity.pojo.*;
import com.synway.datastandardmanager.entity.vo.*;
import com.synway.datastandardmanager.entity.vo.warehouse.DataResource;
import com.synway.datastandardmanager.entity.vo.warehouse.FieldInfo;
import com.synway.datastandardmanager.enums.KeyIntEnum;
import com.synway.datastandardmanager.enums.SynlteFieldTypeEnum;
import com.synway.datastandardmanager.mapper.*;
import com.synway.datastandardmanager.service.DataSetManageService;
import com.synway.datastandardmanager.util.*;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.Collator;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@Slf4j
@Service
public class DataSetManageServiceImpl implements DataSetManageService {

    @Resource
    private ObjectMapper objectMapper;
    @Resource
    private ObjectFieldMapper objectFieldMapper;
    @Resource
    private ObjectStoreInfoMapper objectStoreInfoMapper;
    @Resource
    private FieldCodeValMapper fieldCodeValMapper;
    @Resource
    private StandardizeInputObjectRelateMapper standardizeInputObjectRelateMapper;
    @Resource
    private SourceInfoMapper sourceInfoMapper;
    @Resource
    private SourceFieldInfoMapper sourceFieldInfoMapper;
    @Resource
    private SynlteFieldMapper synlteFieldMapper;
    @Resource
    private FieldDeterminerMapper fieldDeterminerMapper;
    @Resource
    private PublicDataInfoMapper publicDataInfoMapper;
    @Resource
    private AllCodeDataMapper allCodeDataMapper;

    @Autowired
    private RestTemplateHandle restTemplateHandle;

    @Autowired()
    private Environment env;

    @Override
    public DataSetManageVO searchSummaryTable(DataSetManageDTO dataSetManageDTO) {
        DataSetManageVO dataSetManageVO = new DataSetManageVO();
        try {
            log.info(">>>>>>数据集列表查询参数为：" + JSONObject.toJSONString(dataSetManageDTO));
            if (StringUtils.isNotBlank(dataSetManageDTO.getSort())) {
                dataSetManageDTO.setSort(dataSetManageDTO.getSort().toUpperCase());
            }
            if ("OBJECTSTATESTR".equalsIgnoreCase(dataSetManageDTO.getSort())) {
                dataSetManageDTO.setSort("OBJECTSTATE");
            }
            // 组织分类的列表
            List<String> organizationClassList = new ArrayList<>();
            // 来源分类的列表
            List<String> sourceClassList = new ArrayList<>();
            // Object的id的列表
            List<Integer> objectIdClassList = new ArrayList<>();
            // 资源状态的列表
            List<String> useTageValueList = new ArrayList<>();
            // 数据标签的列表
            List<String> labelClassList1 = new ArrayList<>();
            List<String> labelClassList2 = new ArrayList<>();
            List<String> labelClassList3 = new ArrayList<>();
            List<String> labelClassList4 = new ArrayList<>();
            List<String> labelClassList5 = new ArrayList<>();
            List<String> labelClassList6 = new ArrayList<>();

            // 解析过滤列表
            injectFilterItem(dataSetManageDTO, organizationClassList, sourceClassList, objectIdClassList, useTageValueList,
                    labelClassList1, labelClassList2, labelClassList3, labelClassList4, labelClassList5, labelClassList6);

            List<String> queryCodeList = new ArrayList<>();
            if (dataSetManageDTO.getSearchCode().contains(",")) {
                queryCodeList = Arrays.asList(dataSetManageDTO.getSearchCode().toLowerCase().split(","));
                dataSetManageDTO.setSearchCode(null);
            }
            // 查询数据
            List<DataSetTableInfoVO> listAll = objectMapper.queryDataSetManageList(dataSetManageDTO, organizationClassList, sourceClassList,
                    labelClassList1, labelClassList2, labelClassList3, labelClassList4, labelClassList5, labelClassList6, useTageValueList, objectIdClassList);
            List<DataSetTableInfoVO> filterSummaryObjectTable = filterSummaryObjectTable(listAll, dataSetManageDTO, queryCodeList);

            // 筛选数据
            List<DataSetTableInfoVO> list = getSummaryObjectTableFilter(filterSummaryObjectTable, dataSetManageDTO, queryCodeList);

            // 获取页面上所有需要的筛选值
            setAllFilterData(listAll, dataSetManageVO);

            dataSetManageVO.setTableCount(list.size());
            dataSetManageVO.setSummaryObjectTable(list);
        } catch (Exception e) {
            log.error(">>>>>>获取数据集列表出错：", e);
        }
        return dataSetManageVO;
    }

    public void injectFilterItem(DataSetManageDTO dataSetManageDTO,
                                 List<String> organizationClassList,
                                 List<String> sourceClassList,
                                 List<Integer> objectIdClassList,
                                 List<String> useTageValueList,
                                 List<String> labelClassList1,
                                 List<String> labelClassList2,
                                 List<String> labelClassList3,
                                 List<String> labelClassList4,
                                 List<String> labelClassList5,
                                 List<String> labelClassList6) {
        if (dataSetManageDTO.getClassifyTags() != null) {
            dataSetManageDTO.getClassifyTags().forEach(receiveTag -> {
                if (Common.DATA_ORGANIZATION_CLASSIFY.equalsIgnoreCase(receiveTag.getMainClassify())) {
                    if (StringUtils.isBlank(receiveTag.getSecondaryClassifyCh())) {
                        organizationClassList.add(receiveTag.getPrimaryClassifyCh());
                        organizationClassList.add(receiveTag.getPrimaryClassifyCh() + "->" + receiveTag.getPrimaryClassifyCh());
                        organizationClassList.add(receiveTag.getPrimaryClassifyCh() + "->" + receiveTag.getPrimaryClassifyCh() + "->" + receiveTag.getPrimaryClassifyCh());
                    } else {
                        if (receiveTag.getThreeClassifyCh() == null) {
                            if (receiveTag.getPrimaryClassifyCh().equalsIgnoreCase("主题库")) {
                                organizationClassList.add(receiveTag.getPrimaryClassifyCh() + "->" + receiveTag.getSecondaryClassifyCh() + "->" + receiveTag.getSecondaryClassifyCh());
                            } else {
                                organizationClassList.add(receiveTag.getPrimaryClassifyCh() + "->" + receiveTag.getPrimaryClassifyCh() + "->" + receiveTag.getSecondaryClassifyCh());
                            }
                        } else {
                            organizationClassList.add(receiveTag.getPrimaryClassifyCh() + "->" + receiveTag.getSecondaryClassifyCh() + "->" + receiveTag.getThreeClassifyCh());
                        }
                    }
                } else if (Common.DATA_LABEL_CLASSIFY.equalsIgnoreCase(receiveTag.getMainClassify())) {
                    if (receiveTag.getPrimaryClassifyCh().equalsIgnoreCase("标签一")) {
                        labelClassList1.add(receiveTag.getSecondaryClassifyCh());
                    } else if (receiveTag.getPrimaryClassifyCh().equalsIgnoreCase("标签二")) {
                        labelClassList2.add(receiveTag.getSecondaryClassifyCh());
                    } else if (receiveTag.getPrimaryClassifyCh().equalsIgnoreCase("标签三")) {
                        labelClassList3.add(receiveTag.getSecondaryClassifyCh());
                    } else if (receiveTag.getPrimaryClassifyCh().equalsIgnoreCase("标签四")) {
                        labelClassList4.add(receiveTag.getSecondaryClassifyCh());
                    } else if (receiveTag.getPrimaryClassifyCh().equalsIgnoreCase("标签五")) {
                        labelClassList5.add(receiveTag.getSecondaryClassifyCh());
                    } else if (receiveTag.getPrimaryClassifyCh().equalsIgnoreCase("标签六")) {
                        labelClassList6.add(receiveTag.getSecondaryClassifyCh());
                    }
                } else if (Common.DATA_ELEMENT_CLASSIFY.equalsIgnoreCase(receiveTag.getMainClassify())) {
                    //根据数据要素的标识符查出所对应的ObjectId列表
                    LambdaQueryWrapper<ObjectFieldEntity> wrapper = Wrappers.lambdaQuery();
                    wrapper.eq(ObjectFieldEntity::getFieldId, receiveTag.getSecondaryClassifyCh());
                    List<ObjectFieldEntity> objectFieldEntities = objectFieldMapper.selectList(wrapper);
                    List<Integer> idList = objectFieldEntities.stream().map(ObjectFieldEntity::getObjectId).collect(Collectors.toList());
                    objectIdClassList.addAll(idList);
                } else {
                    if (StringUtils.isBlank(receiveTag.getSecondaryClassifyCh())) {
                        sourceClassList.add(receiveTag.getPrimaryClassifyCh());
                        sourceClassList.add(receiveTag.getPrimaryClassifyCh() + "->" + receiveTag.getPrimaryClassifyCh());
                        sourceClassList.add(receiveTag.getPrimaryClassifyCh() + "->" + receiveTag.getPrimaryClassifyCh() + "->" + receiveTag.getPrimaryClassifyCh());
                    } else {
                        sourceClassList.add(receiveTag.getPrimaryClassifyCh() + "->" + receiveTag.getSecondaryClassifyCh());
                    }
                }
            });
        }
        if (dataSetManageDTO.getUsingTags() != null) {
            dataSetManageDTO.getUsingTags().forEach(useTageValue -> {
                if (Common.YI_FA_BU.equals(useTageValue.getLabel())) {
                    useTageValueList.add("1");
                }
                if (Common.WEI_FA_BU.equals(useTageValue.getLabel())) {
                    useTageValueList.add("0");
                }
                if ("停用".equals(useTageValue.getLabel())) {
                    useTageValueList.add("-1");
                }
            });
        }
    }

    /**
     * 数据筛选 根据 时间  tableid 名称
     */
    private List<DataSetTableInfoVO> filterSummaryObjectTable(List<DataSetTableInfoVO> dataSetTableInfoVOS, DataSetManageDTO dto, List<String> queryCodeList) {
        List<DataSetTableInfoVO> list = dataSetTableInfoVOS.parallelStream().filter(d -> {
            try {
                if (StringUtils.isNotBlank(dto.getTableId()) && !StringUtils.equalsIgnoreCase(dto.getTableId(), d.getTableId())) {
                    return false;
                }
                String dbTime = "";
                if (StringUtils.equalsIgnoreCase(dto.getTimeTypeSummary(), DataSetManageDTO.CREATE_TIME_TYPE)) {
                    dbTime = d.getCreateTime();
                } else if (StringUtils.equalsIgnoreCase(dto.getTimeTypeSummary(), DataSetManageDTO.UPDATE_TIME_TYPE)) {
                    dbTime = d.getUpdateTime();
                }
                if (StringUtils.isNotBlank(dto.getStartTimeText())) {
                    if (StringUtils.isBlank(dbTime)) {
                        return false;
                    } else if (DateUtil.parseDate(dbTime, DateUtil.DEFAULT_PATTERN_DATE_SIMPLE).before(
                            DateUtil.parseDate(dto.getStartTimeText(), DateUtil.DEFAULT_PATTERN_DATE_SIMPLE))) {
                        return false;
                    }
                }
                if (StringUtils.isNotBlank(dto.getEndTimeText())) {
                    if (StringUtils.isBlank(dbTime)) {
                        return false;
                    } else if (DateUtil.parseDate(dbTime, DateUtil.DEFAULT_PATTERN_DATE_SIMPLE)
                            .after(DateUtil.parseDate(dto.getEndTimeText(), DateUtil.DEFAULT_PATTERN_DATE_SIMPLE))) {
                        return false;
                    }
                }
                return true;
            } catch (Exception e) {
                log.error(">>>>>>数据筛选报错：", e);
                return false;
            }
        }).collect(Collectors.toList());
        return list;
    }

    /**
     * 根据筛选条件筛选相关数据
     */
    private List<DataSetTableInfoVO> getSummaryObjectTableFilter(List<DataSetTableInfoVO> dataSetTableInfoVOS, DataSetManageDTO dto, List<String> queryCodeList) {
        List<DataSetTableInfoVO> list = new ArrayList<>();

        //TODO 建表工具默认设为0
        //从object_store_info 表中关联已建表信息（从仓库获取）
        Map<String, Long> tableCountMap = new HashMap<>();
        List<String> AllTableIdList = objectStoreInfoMapper.searchAllTableId();
        tableCountMap = AllTableIdList.stream().collect(Collectors.groupingBy(d -> d, Collectors.counting()));

        for (DataSetTableInfoVO dataSetTableInfoVO : dataSetTableInfoVOS) {
            dataSetTableInfoVO.setCreatedTables(tableCountMap.getOrDefault(dataSetTableInfoVO.getTableId().toUpperCase(), Long.valueOf("0")));
            dataSetTableInfoVO.setObjectStateStr(KeyIntEnum.getValueByKeyAndType(Integer.parseInt(dataSetTableInfoVO.getObjectState()), Common.OBJECT_STATE));
            // 此时，需要使用几个筛选的列表来筛选数据；过滤数据，包含'->'的除原始库的组织分类，需要重新赋值
            if (dataSetTableInfoVO.getDataOrganizationClassify().contains("->")) {
                String dataOrganizationClassify = dataSetTableInfoVO.getDataOrganizationClassify();
                String[] dataOrganizationClassifySplit = dataOrganizationClassify.split("->");
                if (!(dataOrganizationClassifySplit[0].equalsIgnoreCase("原始库") ||
                        (dataOrganizationClassifySplit[0].equalsIgnoreCase("主题库")
                                && !dataOrganizationClassifySplit[0].equalsIgnoreCase(dataOrganizationClassifySplit[1])
                                && StringUtils.isNotBlank(dataOrganizationClassifySplit[1])))
                        && !Common.WEI_ZHI.equalsIgnoreCase(dataOrganizationClassifySplit[0])) {
                    //除原始库和未知外，其它组织分类需要展示：1级—>2级
                    dataSetTableInfoVO.setDataOrganizationClassify(dataOrganizationClassifySplit[0] + "->" + dataOrganizationClassifySplit[2]);
                } else if (Common.WEI_ZHI.equalsIgnoreCase(dataOrganizationClassifySplit[0])) {
                    //未知时，只需要展示：1级
                    dataSetTableInfoVO.setDataOrganizationClassify(dataOrganizationClassifySplit[0]);
                }
            }
            if (tableDataFilter(dataSetTableInfoVO, dto)) {
                list.add(dataSetTableInfoVO);
            }
        }
        if (!queryCodeList.isEmpty()) {
            list = list.stream().filter(data -> {
                if ((data.getTableId() != null && queryCodeList.contains(data.getTableId().toLowerCase()))
                        || (data.getTableName() != null && queryCodeList.contains(data.getTableName().toLowerCase()))
                        || (data.getObjectName() != null && queryCodeList.contains(data.getObjectName().toLowerCase()))) {
                    return true;
                }
                return false;
            }).collect(toList());
        }
        // 排序 如果是按照已建表的数量来进行排序，则需要在这里进行排序
        if (Common.CREATED_TABLES.equalsIgnoreCase(dto.getSort()) && Common.ASC.equalsIgnoreCase(dto.getSortOrder())) {
            list.sort(Comparator.comparingLong(DataSetTableInfoVO::getCreatedTables));
        } else if (Common.CREATED_TABLES.equalsIgnoreCase(dto.getSort()) && Common.DESC.equalsIgnoreCase(dto.getSortOrder())) {
            list.sort(Comparator.comparingLong(DataSetTableInfoVO::getCreatedTables).reversed());
        }

        int startNum = 1;
        for (DataSetTableInfoVO summaryObjectTable : list) {
            summaryObjectTable.setRecno(String.valueOf(startNum));
            startNum++;
        }
        return list;
    }

    /**
     * 判断这行数据是否需要给页面上展示
     *
     * @return true:表示需要在页面山展示，false:不需要在页面上展示
     */
    private boolean tableDataFilter(DataSetTableInfoVO dataSetTableInfoVO, DataSetManageDTO dto) {
        boolean flag = true;
        // 应用系统的筛选内容  这个是应用系统中文名
        if (dto.getDataSourceFilter() != null && !dto.getDataSourceFilter().isEmpty()) {
            flag = dto.getDataSourceFilter().contains(dataSetTableInfoVO.getDataSourceCh());
            if (!flag) {
                return false;
            }
        }
        // 数据状态的筛选  中文
        if (dto.getObjectStatesFilter() != null && !dto.getObjectStatesFilter().isEmpty()) {
            flag = dto.getObjectStatesFilter().contains(dataSetTableInfoVO.getObjectStateStr());
            if (!flag) {
                return false;
            }
        }
        // 创建人的筛选  存在空值
        if (dto.getCreatorFilter() != null && !dto.getCreatorFilter().isEmpty()) {
            flag = dto.getCreatorFilter().contains(dataSetTableInfoVO.getCreator());
            if (!flag) {
                return false;
            }
        }
        // 修改人的筛选  存在空值
        if (dto.getUpdaterFilter() != null && !dto.getUpdaterFilter().isEmpty()) {
            flag = dto.getUpdaterFilter().contains(dataSetTableInfoVO.getUpdater());
            if (!flag) {
                return false;
            }
        }
        //  已建物理表的相关筛选
        if (StringUtils.isNotBlank(dto.getCreateTableTotal())) {
            if (DataSetManageDTO.EQUAL.equals(dto.getCreateTableCondition())) {
                return Long.parseLong(dto.getCreateTableTotal()) == dataSetTableInfoVO.getCreatedTables();
            } else if (DataSetManageDTO.MORE_THAN.equals(dto.getCreateTableCondition())) {
                return Long.parseLong(dto.getCreateTableTotal()) < dataSetTableInfoVO.getCreatedTables();
            } else if (DataSetManageDTO.LESS_THAN.equals(dto.getCreateTableCondition())) {
                return Long.parseLong(dto.getCreateTableTotal()) > dataSetTableInfoVO.getCreatedTables();
            }
        }
        return true;
    }

    /**
     * 获取所有的筛选内容信息
     */
    private void setAllFilterData(List<DataSetTableInfoVO> listAll, DataSetManageVO dataSetManageVO) {
        // 1:获取创建人的筛选值
        for (DataSetTableInfoVO dataSetTableInfoVO : listAll) {
            dataSetTableInfoVO.setObjectStateStr(KeyIntEnum.getValueByKeyAndType(Integer.parseInt(dataSetTableInfoVO.getObjectState()), Common.OBJECT_STATE));
        }
        List<KeyValueVO> creatorFilter = new ArrayList<>();
        listAll.stream().map(DataSetTableInfoVO::getCreator).distinct().forEach(d -> {
                    if (StringUtils.isNotBlank(d)) {
                        creatorFilter.add(new KeyValueVO(d, d));
                    }
                }
        );
        // 2:获取修改人的筛选值
        List<KeyValueVO> updaterFilter = new ArrayList<>();
        listAll.stream().map(DataSetTableInfoVO::getUpdater).distinct().forEach(d -> {
                    if (StringUtils.isNotBlank(d)) {
                        updaterFilter.add(new KeyValueVO(d, d));
                    }
                }
        );
        // 3: 数据状态的筛选
        List<KeyValueVO> objectStatesFilter = new ArrayList<>();
        listAll.stream().map(DataSetTableInfoVO::getObjectStateStr).distinct().forEach(d -> {
                    if (StringUtils.isNotBlank(d)) {
                        objectStatesFilter.add(new KeyValueVO(d, d));
                    }
                }
        );
        // 应用系统的筛选
        List<KeyValueVO> dataSourceFilter = new ArrayList<>();
        listAll.stream().map(DataSetTableInfoVO::getDataSourceCh).distinct().forEach(d -> {
                    if (StringUtils.isNotBlank(d)) {
                        dataSourceFilter.add(new KeyValueVO(d, d));
                    }
                }
        );
        dataSourceFilter.sort((s1, s2) -> Collator.getInstance(java.util.Locale.CHINA).compare(s1.getLabel(), s2.getLabel()));
        creatorFilter.sort((s1, s2) -> Collator.getInstance(java.util.Locale.CHINA).compare(s1.getLabel(), s2.getLabel()));
        updaterFilter.sort((s1, s2) -> Collator.getInstance(java.util.Locale.CHINA).compare(s1.getLabel(), s2.getLabel()));
        objectStatesFilter.sort((s1, s2) -> Collator.getInstance(java.util.Locale.CHINA).compare(s1.getLabel(), s2.getLabel()));
        dataSetManageVO.setCreatorFilter(creatorFilter);
        dataSetManageVO.setUpdaterFilter(updaterFilter);
        dataSetManageVO.setObjectStatesFilter(objectStatesFilter);
        dataSetManageVO.setDataSourceFilter(dataSourceFilter);
    }

    @Override
    public List<KeyValueVO> getPrimaryClassifyData(String mainClassify) {
        List<KeyValueVO> oneResultList = new ArrayList<>();
        try {
            log.info(String.format(">>>>>>开始查询【%s】对应的一级分类信息", mainClassify));
            if (StringUtils.isBlank(mainClassify)) {
                log.error(String.format(">>>>>>查询的参数条件mainClassify【%s】为空", mainClassify));
                throw new NullPointerException(String.format("查询的参数条件mainClassify【%s】为空", mainClassify));
            }
            if (Common.DATA_ORGANIZATION_CLASSIFY.equalsIgnoreCase(mainClassify) || Common.DATA_SOURCE_CLASSIFY.equalsIgnoreCase(mainClassify)) {
                /* 数据组织分类 */
                String sjzzflCodeId = env.getProperty("sjzzflCodeId");
                oneResultList = fieldCodeValMapper.getPrimaryClassifyData(mainClassify.toLowerCase(), sjzzflCodeId);
            } else {
                log.error(">>>>>>查询的参数条件mainClassify不正确");
                throw new NullPointerException(String.format("查询的参数条件mainClassify【%s】为空", mainClassify));
            }
        } catch (Exception e) {
            log.error(">>>>>>查询一级分类信息报错：", e);
        }
        return oneResultList;
    }

    @Override
    public List<KeyValueVO> getSecondaryClassifyData(String mainClassify, String primaryClassifyCode) {
        List<KeyValueVO> keyValueVOList = null;
        try {
            log.info(String.format(">>>>>>开始查询主类别为【%s】和一级分类为【%s】对应的二级分类信息", mainClassify, primaryClassifyCode));
            //二级码表值
            String secondCodeId = "";
            if (StringUtils.isBlank(mainClassify)) {
                log.error(String.format(">>>>>>查询的参数条件mainClassify【%s】为空", mainClassify));
                throw new NullPointerException(String.format(">>>>>>查询的参数条件mainClassify【%s】为空", mainClassify));
            }
            if (StringUtils.isBlank(primaryClassifyCode)) {
                log.error(String.format(">>>>>>查询的参数条件primaryClassifyCode【%s】为空", primaryClassifyCode));
                throw new NullPointerException(String.format(">>>>>>查询的参数条件primaryClassifyCode【%s】为空", primaryClassifyCode));
            }
            if (!Common.DATA_ORGANIZATION_CLASSIFY.equalsIgnoreCase(mainClassify) && !Common.DATA_SOURCE_CLASSIFY.equalsIgnoreCase(mainClassify)) {
                log.error(String.format(">>>>>>查询的条件mainClassify【%s】，参数错误", mainClassify));
                throw new NullPointerException(String.format(">>>>>>查询的条件mainClassify【%s】，参数错误", mainClassify));
            }
            if (Common.DATA_SOURCE_CLASSIFY.equalsIgnoreCase(mainClassify) && !primaryClassifyCode.equalsIgnoreCase("未知")) {
                secondCodeId = primaryClassifyCode.split("GACODE000404")[1];
            }
            keyValueVOList = fieldCodeValMapper.getSecondaryClassifyData(mainClassify.toLowerCase(), primaryClassifyCode.toLowerCase(), secondCodeId);
            if (keyValueVOList != null
                    && keyValueVOList.size() == 1
                    && Common.WEI_ZHI.equals(keyValueVOList.get(0).getValue())
                    && !Common.WEI_ZHI.equals(primaryClassifyCode)) {
                keyValueVOList = null;
            }
        } catch (Exception e) {
            log.error(">>>>>>查询二级分类信息报错：", e);
        }
        return keyValueVOList;
    }

    @Override
    public List<KeyValueVO> getThreeClassifyData(String primaryClassifyCode, String secondClassifyCode) {
        List<KeyValueVO> keyValueVOList = null;
        try {
            log.info("开始查询原始库的三级分类:三级分类码值为:" + primaryClassifyCode + "三级分类码值为:" + secondClassifyCode);
            keyValueVOList = fieldCodeValMapper.getThreeClassifyData(primaryClassifyCode + secondClassifyCode, secondClassifyCode);
            if (keyValueVOList != null && keyValueVOList.size() == 1
                    && Common.WEI_ZHI.equals(keyValueVOList.get(0).getValue())
                    && !Common.WEI_ZHI.equals(primaryClassifyCode)) {
                keyValueVOList = null;
            }
        } catch (Exception e) {
            log.error(">>>>>>查询三级分类信息报错：", e);
        }
        return keyValueVOList;
    }

    @Override
    public List<KeyValueVO> getResourceStatus() {
        List<KeyValueVO> keyValueVOList = new ArrayList<>();
        try {
            keyValueVOList = objectMapper.getResourceStatus();
            if (CollectionUtils.isEmpty(keyValueVOList)) {
                keyValueVOList.add(new KeyValueVO("已发布", "已发布(0)"));
                keyValueVOList.add(new KeyValueVO("未发布", "未发布(0)"));
                keyValueVOList.add(new KeyValueVO("停用", "停用(0)"));
            }
        } catch (Exception e) {
            log.error(">>>>>>查询资源状况信息报错：", e);
        }
        return keyValueVOList;
    }

    @Override
    public List<String> queryConditionSuggestion(String searchValue) {
        List<String> commonMaps = new ArrayList<>();
        try {
            log.info(">>>>>>获取提示信息searchValue:" + searchValue);
            commonMaps = objectMapper.queryConditionSuggestion(searchValue);
            final String searchValueOld = searchValue;
            if (commonMaps != null && !commonMaps.isEmpty()) {
                commonMaps = commonMaps.stream().filter(StringUtils::isNotBlank).sorted((s1, s2) -> Collator.getInstance(Locale.CHINA)
                                .compare(s1.replaceFirst(searchValueOld, ""), s2.replaceFirst(searchValueOld, "")))
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            log.error(">>>>>>获取提示信息报错：", e);
        }
        return commonMaps;
    }

    @Override
    public JSONArray getAllFieldClassList() {
        List<FieldCodeValEntity> list = fieldCodeValMapper.getAllFieldClassList();
        if (list == null || list.isEmpty()) {
            throw new NullPointerException("表synlte.fieldcodeval中字段分类的码表信息为空");
        }
        JSONArray jsonArray = JsonUtil.convert2Tree(list, "GACODE000414", new JSONArray());
        return JsonUtil.delEmptyChildren(jsonArray);
    }

    @Override
    public String checkAndGetTableID(String sourceID) {
        //根据源协议，查找目标协议
        List<InputObjectCreateVO> result = standardizeInputObjectRelateMapper.getAllInputObjectRelation(null);
        InputObjectCreateVO targetObject = null;
        for (InputObjectCreateVO inputObjectCreate : result) {
            if (inputObjectCreate.getInputObjEngName().equalsIgnoreCase(sourceID)) {
                targetObject = inputObjectCreate;
                break;
            }
        }
        return targetObject != null ? targetObject.getOutObjEngName() : null;
    }

    @Override
    public List<SourceFieldInfoEntity> initSourceFieldTable(String sourceProtocol, String tableName, String sourceSystem, String sourceFirm, String tableId) {
        List<SourceFieldInfoEntity> resultList = new ArrayList<>();
        try {
            log.info(">>>>>>源字段列表");
            //检查，如果已经有记录，则返回数据库中已经保存的内容
            SourceInfoEntity sourceInfo = sourceInfoMapper.findSourceInfo(sourceProtocol, tableName, sourceSystem, sourceFirm);
            if (sourceInfo != null) {
                LambdaQueryWrapper<SourceFieldInfoEntity> wrapperSFI = Wrappers.lambdaQuery();
                wrapperSFI.eq(SourceFieldInfoEntity::getSourceInfoID, sourceInfo.getId());
                resultList = sourceFieldInfoMapper.selectList(wrapperSFI);

                LambdaQueryWrapper<FieldDeterminerEntity> wrapperFD = Wrappers.lambdaQuery();
                wrapperFD.eq(FieldDeterminerEntity::getDeterminerStateNum, "05");
                List<FieldDeterminerEntity> fieldDeterminerEntities = fieldDeterminerMapper.selectList(wrapperFD);

                resultList.stream().forEach(data -> {
                    data.setColumnName(data.getFieldName());
                    data.setFieldChineseName(data.getFieldDescription());
                    data.setFieldLen(data.getFieldLength());
                    if (StringUtils.isNotBlank(data.getFieldType())) {
                        int fieldTypeCode = SynlteFieldTypeEnum.getSynlteNumType(data.getFieldType().toLowerCase());
                        data.setFieldTypeCode(String.valueOf(fieldTypeCode));
                    }
                    fieldDeterminerEntities.stream().forEach(fieldDeterminer -> {
                        if (data.getDeterminerId() != null && data.getDeterminerId().equalsIgnoreCase(fieldDeterminer.getDeterminerId())) {
                            data.setDeterminerName(data.getDeterminerName());
                        }
                    });
                });
                if (!resultList.isEmpty()) {
                    return resultList.stream().sorted(comparing(d -> d.getRecno())).collect(toList());
                }
            } else {
                //还没有数据的时候，就需要入库dwParams
                sourceInfo = new SourceInfoEntity(sourceProtocol, tableName, sourceSystem, sourceFirm);
                sourceInfoMapper.insertSourceInfo(sourceInfo);
            }
            if (!StringUtils.isBlank(tableId)) {
                //如果不是流程的，也不是已经保存过的，那么就拿协议当源协议，到objectField中查找字段
                ObjectEntity objectEntity = SelectUtil.getObjectEntityByTableId(objectMapper, tableId);
                LambdaQueryWrapper<ObjectFieldEntity> wrapper = Wrappers.lambdaQuery();
                wrapper.eq(ObjectFieldEntity::getObjectId, objectEntity.getObjectId());
                List<ObjectFieldEntity> list = objectFieldMapper.selectList(wrapper);
                for (ObjectFieldEntity objectField : list) {
                    String fieldTypeStr = "";
                    if (StringUtils.isNotEmpty(objectField.getFieldtypes())) {
                        fieldTypeStr = objectField.getFieldtypes();
                    } else {
                        // 将数字转换成 字段类型
                        fieldTypeStr = SynlteFieldTypeEnum.getSynlteFieldType(Integer.valueOf(objectField.getFieldType()));
                    }
                    SourceFieldInfoEntity sourceFieldInfo = new SourceFieldInfoEntity(sourceInfo.getId(),
                            objectField.getRecno(), tableId,
                            objectField.getFieldName(),
                            objectField.getFieldDescribe(), fieldTypeStr, String.valueOf(objectField.getFieldLen()), objectField.getNeedv(),
                            objectField.getIsIndex() == 0 ? "否" : "是", "否",
                            objectField.getFieldId(), objectField.getGadsjFieldId(),
                            objectField.getDeterminerId(), "",
                            objectField.getFieldChineseName(), String.valueOf(objectField.getFieldLen()));
                    sourceFieldInfo.setColumnName(objectField.getColumnName());
                    resultList.add(sourceFieldInfo);
                }
            }
            if (!resultList.isEmpty()) {
                sourceFieldInfoMapper.insertSourceFieldInfo(resultList);
            }
        } catch (Exception e) {
            log.error(">>>>>>获取源字段列表失败：", e);
        }
        return resultList;
    }

    @Override
    public String addTableColumnByEtl(String sourceProtocol, String sourceSystem, String sourceFirm, String tableName, String dataName,
                                      String tableId, String centerId, String centerName, String project, String resourceId) {
        try {
            if (StringUtils.isBlank(sourceProtocol) && StringUtils.isBlank(sourceSystem) && StringUtils.isBlank(sourceFirm)
                    && StringUtils.isBlank(tableName) && StringUtils.isBlank(tableId) && StringUtils.isBlank(centerId)
                    && StringUtils.isBlank(project) && StringUtils.isBlank(resourceId)) {
                return Common.NULL;
            }
            if (StringUtils.isNotBlank(tableName) && tableName.contains(".") && !project.contains("/")) {
                tableName = tableName.split("[.]")[1];
            }
            SourceInfoEntity sourceInfo = sourceInfoMapper.findSourceInfo(sourceProtocol.toUpperCase(), tableName, sourceSystem, sourceFirm);
            if (sourceInfo == null) {
                //还没有数据的时候，就需要入库dwParams
                sourceInfo = new SourceInfoEntity(sourceProtocol.toUpperCase(), tableName, sourceSystem, sourceFirm, dataName, resourceId, project, centerName, centerId);
                sourceInfoMapper.insert(sourceInfo);
            }
            // 20200818 获取的字段信息变成这个接口 因为新增了元素编码的字段
            List<FieldInfo> fieldInfos = restTemplateHandle.requestGetTableStructure(resourceId, project, tableName);
            if (fieldInfos != null) {
                List<SourceFieldInfoEntity> sourceFieldInfoEntities = new ArrayList<>();
                for (FieldInfo tableField : fieldInfos) {
                    if (StringUtils.isEmpty(String.valueOf(tableField.getLength())) && tableField.getType().contains("(")) {
                        tableField.setLength(Integer.parseInt(tableField.getType().split("\\(")[1].split("\\)")[0]));
                        tableField.setType(tableField.getType().split("\\(")[0]);
                    } else if (StringUtils.isEmpty(String.valueOf(tableField.getLength()))) {
                        tableField.setLength(0);
                    }
                    // 如果字段名或备注为空，则回填数据元名称
                    String tableNameCh = StringUtils.isNotBlank(tableField.getComments()) ? tableField.getComments() : tableField.getSynFieldName();
                    SourceFieldInfoEntity sourceFieldInfo = new SourceFieldInfoEntity(sourceInfo.getId(), tableField.getNo(), tableId, tableField.getFieldName(),
                            tableNameCh, tableField.getType(), String.valueOf(tableField.getLength()),
                            String.valueOf(tableField.getNullAble()), String.valueOf(tableField.getIsPrimaryKey()),
                            StringUtils.isEmpty(String.valueOf(tableField.getIsForeignKey())) ? "否" : String.valueOf(tableField.getIsForeignKey()),
                            tableField.getSynFieldId(), tableField.getGadsjFieldId(),
                            tableField.getDeterminerId(), tableField.getElementId(), tableField.getComments(), String.valueOf(tableField.getLength()));
                    String columnLen = String.valueOf(tableField.getLength());
                    if (StringUtils.isNotEmpty(columnLen)) {
                        if (columnLen.length() > 6) {
                            sourceFieldInfo.setFieldLength("999");
                        }
                    }
                    sourceFieldInfo.setFieldSourceType(1);
                    sourceFieldInfoEntities.add(sourceFieldInfo);
                }
                if (sourceFieldInfoEntities.size() > 0) {
                    //先删除字段表中指定的数据，然后再插入  根据 tableid来删除
                    log.info(">>>>>>开始删除Source_Field_Info中字段信息");
                    LambdaQueryWrapper<SourceFieldInfoEntity> wrapper = Wrappers.lambdaQuery();
                    wrapper.eq(SourceFieldInfoEntity::getSourceInfoID, sourceInfo.getId());
                    sourceFieldInfoMapper.delete(wrapper);
                    log.info(">>>>>>开始向表Source_Field_Info中插入字段信息");
                    sourceFieldInfoMapper.insertSourceFieldInfo(sourceFieldInfoEntities);
                }
            }
            return Common.ADD_SUCCESS;
        } catch (Exception e) {
            log.error(">>>>>>添加字段信息报错：", e);
            return Common.ADD_FAIL;
        }
    }

    @Override
    public List<ObjectFieldEntity> getSourceFieldColumnList(JSONObject jsonObject) {
        List<ObjectFieldEntity> needInsertList = new ArrayList<>();
        try {
            log.info(">>>>>>传入的参数为：" + JSONObject.toJSONString(jsonObject));
            JSONArray jsonArray = jsonObject.getJSONArray("sourceFieldInfo");
            List<SourceFieldInfoEntity> sourceFieldInfos = jsonArray.toJavaList(SourceFieldInfoEntity.class);
            JSONArray pageJsonArray = jsonObject.getJSONArray("pageColumnList");
            List<ObjectFieldEntity> objectFieldList = pageJsonArray.toJavaList(ObjectFieldEntity.class);

            // 查询数据库中的最大字段顺序
            int maxRecnoInt = 0;
            int oldRecnoInt = 0;
            if (!objectFieldList.isEmpty()) {
                Optional<Integer> maxRecno = objectFieldList.stream().map(ObjectFieldEntity::getRecno).distinct().collect(toList()).stream().reduce(Integer::max);
                if (maxRecno.isPresent()) {
                    maxRecnoInt = maxRecno.get() + 1;
                    oldRecnoInt = maxRecno.get() + 1;
                }
            }
            log.info(">>>>>>最大字段顺序为: " + maxRecnoInt);
            //用于过滤已经添加的字段
            Map<String, ObjectFieldEntity> map = new HashMap<>();
            for (SourceFieldInfoEntity sourceFieldInfo : sourceFieldInfos) {
                ObjectFieldEntity objectField = new ObjectFieldEntity();
                if (StringUtils.isNotBlank(sourceFieldInfo.getFieldName())) {
                    // 如果 fieldCode 里面的值不为空，则从数据库中查询对应的标准字段信息，
                    // 如果没有报错，则使用这个数据，如果报错，则还是使用原先的数据
                    objectField = JSONObject.parseObject(JSONObject.toJSONString(sourceFieldInfo), ObjectFieldEntity.class);
                    objectField.setRecno(maxRecnoInt);
                    objectField.setUpdateStatus(1);

                    if (StringUtils.isBlank(sourceFieldInfo.getFieldLength()) || sourceFieldInfo.getFieldLength().equalsIgnoreCase("null")) {
                        objectField.setFieldLen(0);
                    } else if (NumberUtils.isNumber(sourceFieldInfo.getFieldLength()) && !sourceFieldInfo.getFieldLength().contains(".")) {
                        objectField.setFieldLen(Integer.parseInt(sourceFieldInfo.getFieldLength()));
                    } else if (NumberUtils.isNumber(sourceFieldInfo.getFieldLength()) && sourceFieldInfo.getFieldLength().contains(".")) {
                        objectField.setFieldLen(new BigDecimal(sourceFieldInfo.getFieldLength()).intValue());
                    } else {
                        objectField.setFieldLen(0);
                    }
                    // 以下该值的默认值不能为 null
                    objectField.setClustRecnoStatus(false);
                    objectField.setPkRecnoStatus(false);
                    objectField.setMd5IndexStatus(false);

                    //字段类型
                    String fieldType = sourceFieldInfo.getFieldType();
                    Map<String, String> dbMapper = FieldTypeUtil.getFieldTypeMap("all", "standardize");
                    if (StringUtils.isBlank(fieldType)) {
                        objectField.setFieldType(-1);
                    } else {
                        if (fieldType.contains("(")) {
                            fieldType = fieldType.split("[(]")[0];
                        }
                        String targetFieldType = dbMapper.get(fieldType.toLowerCase());
                        if (targetFieldType == null) {
                            objectField.setFieldType(-1);
                        } else {
                            int typeNum = SynlteFieldTypeEnum.getSynlteNumType(targetFieldType);
                            objectField.setFieldType(typeNum);
                        }
                    }
                    // 如果为true，表示是从数据库中获取的标准字段
                    boolean flag = getStandardFieldByFieldCode(sourceFieldInfo.getFieldCode(), objectField, sourceFieldInfo.getFieldName(), sourceFieldInfo.getFieldDescription());
                    if (!flag) {
                        objectField.setFieldId("");
                        objectField.setFieldChineseName((StringUtils.isBlank(sourceFieldInfo.getFieldDescription()) ||
                                StringUtils.equalsIgnoreCase(sourceFieldInfo.getFieldDescription(), "null"))
                                ? "" : sourceFieldInfo.getFieldDescription());
                        objectField.setFieldDescribe((StringUtils.isBlank(sourceFieldInfo.getFieldDescription()) ||
                                StringUtils.equalsIgnoreCase(sourceFieldInfo.getFieldDescription(), "null"))
                                ? "" : sourceFieldInfo.getFieldDescription());
                        objectField.setDefaultValue("");
                        objectField.setMemo((StringUtils.isBlank(sourceFieldInfo.getFieldDescription()) ||
                                StringUtils.equalsIgnoreCase(sourceFieldInfo.getFieldDescription(), "null"))
                                ? "" : sourceFieldInfo.getFieldDescription());
                        objectField.setFieldSourceType(sourceFieldInfo.getFieldSourceType());
                        objectField.setColumnName(sourceFieldInfo.getFieldName());
                        objectField.setFieldName("");
                        if ("dt".equalsIgnoreCase(sourceFieldInfo.getFieldName())) {
                            // 如果是dt这个字段，则需要先默认设置为分区字段
                            objectField.setOdpsPattition(1);
                        }
                    }
                    objectField.setNeedValue(Integer.parseInt(sourceFieldInfo.getIsNonnull()));
                    objectField.setIsIndex(Integer.parseInt(sourceFieldInfo.getIsNonnull()));
                    maxRecnoInt++;
                    map.put(sourceFieldInfo.getFieldName().toUpperCase(), objectField);
                    needInsertList.add(objectField);
                }
            }
            for (ObjectFieldEntity objectPageField : objectFieldList) {
                log.info("页面中的字段信息为：" + JSONObject.toJSONString(objectPageField));
                if (objectPageField.getColumnName() != null) {
                    ObjectFieldEntity sourceFieldObject = map.get(objectPageField.getColumnName().toUpperCase());
                    if (StringUtils.isEmpty(objectPageField.getColumnName())) {
                        continue;
                    }
                    if (map.get(objectPageField.getColumnName().toUpperCase()) != null) {
                        needInsertList.remove(sourceFieldObject);
                    }
                } else {
                    log.info("存在空字段");
                    ObjectFieldEntity sourceFieldObject = map.get(objectPageField.getColumnName().toUpperCase());
                    if (StringUtils.isEmpty(objectPageField.getColumnName())) {
                        continue;
                    }
                    if (map.get(objectPageField.getColumnName().toUpperCase()) != null) {
                        needInsertList.remove(sourceFieldObject);
                    }
                }
            }
            for (ObjectFieldEntity objectField : needInsertList) {
                objectField.setRecno(oldRecnoInt);
                oldRecnoInt++;
            }
            log.info(">>>>>>需要插入的字段信息为：" + JSONObject.toJSONString(needInsertList));
        } catch (Exception e) {
            log.error(">>>>>>导入源字段信息报错：", e);
        }
        return needInsertList;
    }

    private boolean getStandardFieldByFieldCode(String fieldCode, ObjectFieldEntity objectField, String columnName, String fieldDescription) {
        try {
            if (StringUtils.isBlank(fieldCode)) {
                throw new NullPointerException("元素编码为空，不需要查询标准字段");
            }
            LambdaQueryWrapper<SynlteFieldEntity> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(SynlteFieldEntity::getFieldId, fieldCode);
            SynlteFieldEntity synltefield = synlteFieldMapper.selectOne(wrapper);
            LambdaQueryWrapper<FieldCodeValEntity> wrapperFC = Wrappers.lambdaQuery();
            wrapperFC.eq(FieldCodeValEntity::getCodeId, "JZCODEGAZDMGD");
            List<FieldCodeValEntity> fieldCodeValEntities = fieldCodeValMapper.selectList(wrapperFC);
            fieldCodeValEntities.stream().forEach(fieldCodeVal -> {
                if (fieldCodeVal.getValValue().equalsIgnoreCase(synltefield.getSecretClass())) {
                    synltefield.setSecretClassCh(fieldCodeVal.getValText());
                }
            });
            objectField.setFieldId(synltefield.getFieldId());
            objectField.setFieldName(synltefield.getFieldName());
            if (StringUtils.isNotBlank(fieldDescription) &&
                    !StringUtils.equalsIgnoreCase(fieldDescription, "null") &&
                    !StringUtils.equalsIgnoreCase(fieldDescription, synltefield.getFieldChineseName())) {
                objectField.setFieldChineseName(fieldDescription);
            } else {
                objectField.setFieldChineseName(synltefield.getFieldChineseName());
            }
            objectField.setFieldDescribe(synltefield.getFieldDescribe());
            // 这种表示都为原始库数据
            objectField.setFieldSourceType(1);
            int columnLen = Integer.parseInt(String.valueOf(synltefield.getFieldLen()));
            int originalFieldLen = objectField.getFieldLen();
            objectField.setFieldLen(Math.max(columnLen, originalFieldLen));
            objectField.setColumnName(columnName);
            if (objectField.getFieldType() == null || objectField.getFieldType() == -1) {
                objectField.setFieldType(synltefield.getFieldType());
            }
            // 新增字段分类的信息；获取表字段信息的字段分类信息，synltefield.FIELD_CLASS 这个字段里面
            List<ObjectFieldEntity> codeClassList = synlteFieldMapper.queryCodeClassList();
            Map<String, List<ObjectFieldEntity>> codeClassMap = codeClassList.stream().collect(Collectors.groupingBy(ObjectFieldEntity::getFieldId));
            List<ObjectFieldEntity> codeList = codeClassMap.get(objectField.getFieldId());
            if (!codeList.isEmpty()) {
                ObjectFieldEntity entity1 = codeList.get(0);
                objectField.setCodeText(entity1.getCodeText() != null ? entity1.getCodeText() : "");
                objectField.setCodeid(entity1.getCodeid() != null ? entity1.getCodeid() : "");
                objectField.setFieldClassId(entity1.getFieldClassId() != null ? entity1.getFieldClassId() : "");
                objectField.setFieldClassCh(entity1.getFieldClassCh() != null ? entity1.getFieldClassCh() : "");
                objectField.setSameWordType(entity1.getSameWordType() != null ? entity1.getSameWordType() : "");
            }
            objectField.setSensitivityLevel(StringUtils.isBlank(synltefield.getSecretClass()) ? "" : synltefield.getSecretClass());
            objectField.setSensitivityLevelCh(StringUtils.isBlank(synltefield.getSecretClassCh()) ? "" : synltefield.getSecretClassCh());
            return true;
        } catch (Exception e) {
            log.error("元素编码为" + fieldCode + " 错误原因" + e.getMessage());
            return false;
        }
    }

    @Override
    public List<ValueLabelVO> getSecondaryClassLayuiService(String mainClassify, String primaryClassifyCh) {
        List<ValueLabelVO> valueLabelVOList = new ArrayList<>();
        try {
            log.info(">>>>>>查询的参数为， mainClassify：" + mainClassify + " primaryClassifyCh：" + primaryClassifyCh);
            mainClassify = "数据组织分类";
            List<StandardTableRelationVO> standardTableRelations = objectMapper.getClassifyObject(mainClassify, primaryClassifyCh);
            List<String> secondaryChList = standardTableRelations.stream().filter(d -> StringUtils.isNotEmpty(d.getSecondaryClassifyCh()))
                    .map(StandardTableRelationVO::getSecondaryClassifyCh).distinct().collect(toList());
            Map<String, List<StandardTableRelationVO>> stringListMap = standardTableRelations.stream().filter(d -> StringUtils.isNotEmpty(d.getSecondaryClassifyCh()))
                    .collect(Collectors.groupingBy(StandardTableRelationVO::getSecondaryClassifyCh));
            for (String secondaryifyCh : secondaryChList) {
                ValueLabelVO layuiClassifyPojo = new ValueLabelVO();
                layuiClassifyPojo.setValue(secondaryifyCh);
                layuiClassifyPojo.setLabel(secondaryifyCh);
                List<StandardTableRelationVO> childrenList = stringListMap.get(secondaryifyCh);
                List<ValueLabelVO> ChildrenLayuiClassifyList = new ArrayList<>();
                for (StandardTableRelationVO standardTableRelation : childrenList) {
                    if (StringUtils.isNotEmpty(standardTableRelation.getThreeClassifyCh())) {
                        ValueLabelVO layuiClassifyChildrenPojo = new ValueLabelVO();
                        layuiClassifyChildrenPojo.setLabel(standardTableRelation.getThreeClassifyCh());
                        layuiClassifyChildrenPojo.setValue(standardTableRelation.getThreeClassifyCh());
                        layuiClassifyChildrenPojo.setChildren(new ArrayList<>());
                        ChildrenLayuiClassifyList.add(layuiClassifyChildrenPojo);
                    }
                }
                layuiClassifyPojo.setChildren(ChildrenLayuiClassifyList);
                valueLabelVOList.add(layuiClassifyPojo);
            }
            log.info(">>>>>>查询到的结果为：" + JSONObject.toJSONString(valueLabelVOList));
        } catch (Exception e) {
            log.error("");
        }
        return valueLabelVOList;
    }

    @Override
    public String getEnFlagByChnType(String organizationClassifys, String sourceClassifys, String dataSourceName, Boolean flag) {
        StringBuffer sbf = new StringBuffer();
        try {
            String[] organizations = organizationClassifys.split("/");
            String[] sourceList = sourceClassifys.split("/");
            // 先获取大类的数据
            String mainClass = "";
            if (organizations[0].equalsIgnoreCase("业务要素索引库")) {
                mainClass = "ywys";
            } else if (organizations[0].equalsIgnoreCase("其他")) {
                mainClass = "other";
            } else {
                mainClass = getPyFirst(organizations[0]);
            }
            sbf.append(mainClass).append("_");
            // 之后获取第二级的数据
            if (flag) {
                if (StringUtils.isNotEmpty(sourceClassifys)) {
                    String secondClass = "";
                    if (sourceList.length > 1) {
                        secondClass = objectMapper.findCheckTableNameImformationThree(sourceList[1], 2);
                    } else {
                        secondClass = objectMapper.findCheckTableNameImformationThree(sourceList[0], 1);
                    }
                    if (sourceList.length == 1 || StringUtils.isNumeric(sourceClassifys)) {
                        secondClass = sourceClassifys;
                    }
                    sbf.append(secondClass).append("_");
                } else {
                    sbf.append("xxx").append("_");
                }
            } else {
                if (organizations.length > 1) {
                    String secondClass = getPyFirst(organizations[1]);
                    sbf.append(secondClass).append("_");
                } else {
                    sbf.append(sbf);
                }
            }
            if (StringUtils.isNotBlank(dataSourceName)) {
                String daSourceNamePy = getPyFirst(dataSourceName);
                sbf.append(daSourceNamePy).append("_");
            } else {
                sbf.append("xxx").append("_");
            }
        } catch (Exception e) {
            log.error(">>>>>>获取组织标准为表名检测使用出错：", e);
        }
        return sbf.toString();
    }

    public String getPyFirst(String data) throws Exception {
        StringBuilder pinYin = new StringBuilder();
        if (StringUtils.isNotBlank(data)) {
            for (char c : data.toCharArray()) {
                final String firstSpell = PinYinUtil.getFirstSpell(String.valueOf(c));
                if (StringUtils.isEmpty(firstSpell)) {
                    pinYin.append(c);
                } else {
                    pinYin.append(firstSpell);
                }
            }
        }
        return pinYin.toString();
    }

    @Override
    public int checTableNamekIsExit(String realTableName, String objectId) {
        try {
            log.info(String.format(">>>>>>传入的参数为，realTableName：%s，objectId：%s", realTableName, objectId));
            LambdaQueryWrapper<ObjectEntity> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(ObjectEntity::getTableName, realTableName);
            ObjectEntity objectEntity = objectMapper.selectOne(wrapper);
            if (StringUtils.isBlank(objectId)) {
                return objectEntity == null ? 0 : 1;
            } else {
                Integer objectIdOld = objectEntity.getObjectId();
                return objectIdOld == null || objectIdOld == Integer.valueOf(objectId) ? 0 : 1;
            }
        } catch (Exception e) {
            log.error(">>>>>>判断该表名是否已经存在出错：", e);
            return 0;
        }
    }

    @Override
    public DataResourceRawInformationVO getOrganizationRelationByTableName(String addTableName) {
        try {
            log.info(">>>>>>查询的参数addTableName为:" + addTableName);
            return objectMapper.getOrganizationRelationDao(addTableName);
        } catch (Exception e) {
            log.error(">>>>>>获取增加来源信息报错：", e);
            return new DataResourceRawInformationVO();
        }
    }

    @Override
    public List<ValueLabelVO> getAllClassifyLayuiService(String mainClassifyCh) {
        List<ValueLabelVO> layuiClassifyPojoList = new ArrayList<>();
        try {
            log.info(">>>>>>开始查询所有符合layui插件的分级分类信息");
            //  获取所有的分级分类信息
            List<StandardTableRelationVO> standardTableRelations = objectMapper.getAllClassify(mainClassifyCh, "");
            List<String> primaryChList = standardTableRelations.stream().filter(d -> StringUtils.isNotEmpty(d.getPrimaryClassifyCh()))
                    .map(d -> (d.getPrimaryClassifyId() + "&&" + d.getPrimaryClassifyCh())).distinct().collect(toList());
            Map<String, List<StandardTableRelationVO>> primaryListMap = standardTableRelations.stream().filter(d -> StringUtils.isNotEmpty(d.getPrimaryClassifyCh()))
                    .collect(Collectors.groupingBy(d -> (d.getPrimaryClassifyId() + "&&" + d.getPrimaryClassifyCh())));
            for (String primaryClassify : primaryChList) {
                // 一级分类
                ValueLabelVO primaryLayuiClassifyPojo = new ValueLabelVO();
                primaryLayuiClassifyPojo.setValue(primaryClassify.split("&&")[0]);
                primaryLayuiClassifyPojo.setLabel(primaryClassify.split("&&")[1]);
                List<StandardTableRelationVO> secondaryClassifyList = primaryListMap.get(primaryClassify);
                List<ValueLabelVO> secondaryLayuiClassifyList = new ArrayList<>();
                List<String> secondaryList = secondaryClassifyList.stream().filter(d -> StringUtils.isNotEmpty(d.getSecondaryClassifyCh()))
                        .map(d -> (d.getSecondaryClassifyId() + "&&" + d.getSecondaryClassifyCh())).distinct().collect(toList());
                Map<String, List<StandardTableRelationVO>> secondaryListMap = secondaryClassifyList.stream().filter(d -> StringUtils.isNotEmpty(d.getSecondaryClassifyCh()))
                        .collect(Collectors.groupingBy(d -> (d.getSecondaryClassifyId() + "&&" + d.getSecondaryClassifyCh())));
                //  二级分类
                for (String secondaryCh : secondaryList) {
                    ValueLabelVO secondaryLayuiClassifyPojo = new ValueLabelVO();
                    secondaryLayuiClassifyPojo.setValue(secondaryCh.split("&&")[0]);
                    secondaryLayuiClassifyPojo.setLabel(secondaryCh.split("&&")[1]);
                    //三级分类
                    List<StandardTableRelationVO> threeClassifyList = secondaryListMap.get(secondaryCh);
                    List<ValueLabelVO> threeLayuiClassifyList = new ArrayList<>();
                    for (StandardTableRelationVO standardTableRelation : threeClassifyList) {
                        if (StringUtils.isNotEmpty(standardTableRelation.getThreeClassifyCh())) {
                            ValueLabelVO threelayuiClassifyPojo = new ValueLabelVO();
                            threelayuiClassifyPojo.setLabel(standardTableRelation.getThreeClassifyCh());
                            threelayuiClassifyPojo.setValue(standardTableRelation.getThreeClassifyId());
                            threelayuiClassifyPojo.setChildren(new ArrayList<>());
                            threeLayuiClassifyList.add(threelayuiClassifyPojo);
                        }
                    }
                    secondaryLayuiClassifyPojo.setChildren(threeLayuiClassifyList);
                    secondaryLayuiClassifyList.add(secondaryLayuiClassifyPojo);
                }
                primaryLayuiClassifyPojo.setChildren(secondaryLayuiClassifyList);
                layuiClassifyPojoList.add(primaryLayuiClassifyPojo);
            }
        } catch (Exception e) {
            log.error(">>>>>>获取分级分类信息报错：", e);
        }
        return layuiClassifyPojoList;
    }

    @Override
    public String getDatabaseType() {
        String resType = Common.ALI_YUN;
        if (env.getProperty("isCreatTableTool").equalsIgnoreCase("true")) {
            return resType;
        }
        try {
            List<DataResource> dataResourcesList = restTemplateHandle.getDataCenterVersion("0", "0");
            if (dataResourcesList == null) {
                return resType;
            }
            for (DataResource data : dataResourcesList) {
                if (data.getResType().equalsIgnoreCase(Common.ODPS)) {
                    resType = Common.ALI_YUN;
                    break;
                } else if (data.getResType().equalsIgnoreCase(Common.HIVECDH)) {
                    resType = Common.HUA_WEI_YUN;
                    break;
                } else if (data.getResType().equalsIgnoreCase(Common.HIVEHUAWEI)) {
                    resType = Common.HUA_WEI_YUN;
                    break;
                }
            }
        } catch (Exception e) {
            log.error(">>>>>>获取大数据库类型异常：", e);
        }
        return resType;
    }

    @Override
    public String getCodeNameByClassifyId(String classifyIds) {
        String codeName = "";
        try {
            if (StringUtils.isEmpty(classifyIds)) {
                return codeName;
            }
            List<String> classifyIdList = Arrays.asList(classifyIds.split(","));
            String oneClassifyId = classifyIdList.get(classifyIdList.size() - 1);
            if (classifyIdList.size() > 1) {
                codeName = objectMapper.getCodeNameByClassifyIdDao(oneClassifyId, 2);
            } else {
                codeName = objectMapper.getCodeNameByClassifyIdDao(oneClassifyId, 1);
            }
        } catch (Exception e) {
            log.error(">>>>>>根据数据来源分类的id值获取对应的代码数据值报错:", e);
        }
        return codeName;
    }

    @Override
    public String getNewSourceIdById(String sourceId, String dataSourceClassify, String code) {
        //查询public_data_info表中所有的sourceId
        LambdaQueryWrapper<PublicDataInfoEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(PublicDataInfoEntity::getZyzt, 1);
        wrapper.isNotNull(PublicDataInfoEntity::getSourceId);
        List<PublicDataInfoEntity> publicDataInfoEntities = publicDataInfoMapper.selectList(wrapper);
        List<String> allSourceIdList = publicDataInfoEntities.stream().map(PublicDataInfoEntity::getSourceId).collect(toList());

        String[] sourceList = dataSourceClassify.split("\\/");
        String secondClass = "";
        String tableIdStr = "GA_SOURCE_";
        if (sourceList.length > 1) {
            secondClass = objectMapper.findCheckTableNameImformationThree(sourceList[1], 2);
        } else {
            secondClass = objectMapper.findCheckTableNameImformationThree(sourceList[0], 1);
        }
        tableIdStr = tableIdStr + secondClass;
        tableIdStr = tableIdStr + "_" + code;
        // 5位流水号 从 80001 开始
        final String classMesaage = secondClass + "_" + code;
        OptionalInt maxId = allSourceIdList.stream().filter(d -> {
            if (StringUtils.isNotEmpty(d)) {
                String[] tableIdListStr = d.split("\\_");
                if (tableIdListStr.length == 5 && (tableIdListStr[2] + "_" + tableIdListStr[3]).equalsIgnoreCase(classMesaage)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }).mapToInt(d -> {
            try {
                String[] tableIdListStr = d.split("\\_");
                return Integer.valueOf(tableIdListStr[4]);
            } catch (Exception e) {
                return -1;
            }
        }).filter(d -> d != -1).max();
        // 获取到数据库中  PUBLIC_DATA_INFO 中
        int dataBaseMaxId = Math.max(maxId.orElse(80000), 80000);
        DecimalFormat format = new DecimalFormat("00000");
        return tableIdStr + "_" + format.format(dataBaseMaxId + 1);
    }

    @Override
    public List<KeyValueVO> searchSecurityLevel() {
        List<KeyValueVO> list = new ArrayList<>();
        try {
            log.info(">>>>>>开始获取数据分级信息");
            list = fieldCodeValMapper.queryLabelValueByCodeId("GACODE000412");
            if (list == null || list.isEmpty()) {
                throw new Exception("表fieldcodeval中没有配置安全分级的码表值，请联系标委会");
            }
        } catch (Exception e) {
            log.error(">>>>>>获取安全分级的码表报错：", e);
        }
        return list;
    }

    @Override
    public List<KeyValueVO> searchFieldSecurityLevelList(String codeId) {
        List<KeyValueVO> list = new ArrayList<>();
        try {
            log.info(">>>>>>开始获取字段定义中的安全分级信息");
            LambdaQueryWrapper<AllCodeDataEntity> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(AllCodeDataEntity::getCodeId, "SECURITYLEVEL");
            if (StringUtils.isNotBlank(codeId)) {
                wrapper.eq(AllCodeDataEntity::getId, codeId);
            }
            List<AllCodeDataEntity> allCodeDataEntities = allCodeDataMapper.selectList(wrapper);
            if (allCodeDataEntities == null || allCodeDataEntities.isEmpty()) {
                return list;
            }
            for (AllCodeDataEntity data : allCodeDataEntities) {
                list.add(new KeyValueVO(data.getCodeValue(), data.getCodeText()));
            }
        } catch (Exception e) {
            log.error(">>>>>>获取字段定义的安全分级的码表报错：", e);
        }
        return list;
    }


}
