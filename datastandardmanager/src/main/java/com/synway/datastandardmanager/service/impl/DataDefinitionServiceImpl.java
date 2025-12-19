package com.synway.datastandardmanager.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.synway.datastandardmanager.entity.dto.DataDefinitionDTO;
import com.synway.datastandardmanager.entity.pojo.*;
import com.synway.datastandardmanager.entity.vo.*;
import com.synway.datastandardmanager.enums.ErrorCodeEnum;
import com.synway.datastandardmanager.mapper.*;
import com.synway.datastandardmanager.entity.vo.warehouse.FieldInfo;
import com.synway.datastandardmanager.entity.vo.warehouse.TableSimilarInfo;
import com.synway.datastandardmanager.service.DataDefinitionService;
import com.synway.datastandardmanager.util.RestTemplateHandle;
import com.synway.datastandardmanager.util.SelectUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Collator;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Slf4j
public class DataDefinitionServiceImpl implements DataDefinitionService {

    @Autowired
    private RestTemplateHandle restTemplateHandle;

    @Resource
    private ObjectMapper objectMapper;
    @Resource
    private ObjectFieldMapper objectFieldMapper;
    @Resource
    private SynlteFieldMapper synlteFieldMapper;
    @Resource
    private FieldCodeMapper fieldCodeMapper;
    @Resource
    private StandardizeObjectRelationMapper standardizeObjectRelationMapper;
    @Resource
    private StandardizeObjectfieldRelMapper standardizeObjectfieldRelMapper;


    @Override
    public PageVO searchDataDefinitionTable(DataDefinitionDTO parameter) {
        PageVO pageVO = new PageVO<>();
        try {
            log.info(">>>>>>开始查询数据定义管理的数据");
            if (parameter.getSort() == null || parameter.getSort().isEmpty()) {
                parameter.setSort("modDate");
            }
            if (parameter.getSortOrder() == null || parameter.getSortOrder().isEmpty()) {
                parameter.setSortOrder("desc");
            }
            PageHelper.startPage(parameter.getPageIndex(), parameter.getPageSize());
            List<DataDefinitionVO> dataDefinitionList = objectMapper.searchDataDefinitionTable(parameter);
            if (dataDefinitionList != null && !dataDefinitionList.isEmpty()) {
                int num = 1;
                for (DataDefinitionVO data : dataDefinitionList) {
                    data.setRecno(num++);
                }
            } else {
                return pageVO.emptyResult();
            }
            PageInfo<DataDefinitionVO> pageInfo = new PageInfo<>(dataDefinitionList);
            pageVO.setPageNum(parameter.getPageIndex());
            pageVO.setPageSize(parameter.getPageSize());
            pageVO.setTotal(pageInfo.getTotal());
            pageVO.setRows(pageInfo.getList());
        } catch (Exception e) {
            log.error(">>>>>>获取数据定义数据列表出错：", e);
        }
        return pageVO;
    }

    @Override
    public String getDictionaryNameById(String gadsjFieldId) {
        log.info(">>>>>>传递的参数为:{}", gadsjFieldId);
        LambdaQueryWrapper<SynlteFieldEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SynlteFieldEntity::getFieldId, gadsjFieldId);
        SynlteFieldEntity synlteField = synlteFieldMapper.selectOne(wrapper);
        return fieldCodeMapper.searchFieldCodeByCodeId(synlteField.getCodeId());
    }

    @Override
    public List<ValueLabelVO> searchAllDataStandard(String searchText) {
        //查询全部的数据集标准信息
        LambdaQueryWrapper<ObjectEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ObjectEntity::getObjectState, 1);
        if (StringUtils.isNotBlank(searchText)) {
            wrapper.apply("lower(objectname) like lower({0})", "%" + searchText.toLowerCase() + "%");
        }
        List<ObjectEntity> objectEntities = objectMapper.selectList(wrapper);
        List<ValueLabelVO> resultList = new ArrayList<>();
        if (objectEntities.size() > 0) {
            for (ObjectEntity data : objectEntities) {
                resultList.add(new ValueLabelVO(data.getTableId(), data.getDataSourceName(), data.getObjectId().toString()));
            }
        } else {
            return resultList;
        }
        //根据中文名排序
        resultList = resultList.stream().sorted((s1, s2) -> Collator.getInstance(Locale.CHINA).compare(s2.getLabel(), s1.getLabel()))
                .collect(Collectors.toList());
        return resultList;
    }

    @Override
    public List<ValueLabelVO> getDataSetDetectSimilarResult(DataDefinitionDTO dto) throws Exception {
        log.info(">>>>>>开始获取探查分析推荐标准数据集");
        if (StringUtils.isBlank(dto.getResId()) && StringUtils.isBlank(dto.getProjectName())
                && StringUtils.isBlank(dto.getTableNameEn())) {
            throw new Exception(String.format("%s：%s", ErrorCodeEnum.DATA_IS_NULL, "获取探查分析推荐标准数据集参数为空"));
        }
        List<TableSimilarInfo> dataSimilarInfo = restTemplateHandle.getDataSimilarInfo(dto);
        if (dataSimilarInfo.size() == 0) {
            return new ArrayList<>();
        }
        List<ValueLabelVO> resultList = new ArrayList<>();
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMinimumFractionDigits(2);
        dataSimilarInfo.stream().forEach(d -> {
            ValueLabelVO dataSimilarElement = new ValueLabelVO(d.getProtocolCode(), d.getObjectName(), nf.format(d.getScore()));
            resultList.add(dataSimilarElement);
        });
        return resultList;
    }

    @Override
    public ObjectRelationManageVO getDataSetMapping(String tableId) throws Exception {
        log.info(">>>>>>开始查询数据集对标信息，传递的参数为:{}", tableId);
        ObjectRelationManageVO objectRelationManage = new ObjectRelationManageVO();
        //根据tableId查询objectId
        ObjectEntity object = SelectUtil.getObjectEntityByTableId(objectMapper, tableId);
        //查询到原始汇聚的数据集信息
        if (object.getObjectId() == null) {
            throw new Exception(String.format("%s：%s", ErrorCodeEnum.DATA_IS_NULL, "未知异常，请联系相关人员"));
        }
        LambdaQueryWrapper<StandardizeObjectRelationEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(StandardizeObjectRelationEntity::getObjectId, object.getObjectId());
        StandardizeObjectRelationEntity originalObjectRelation = standardizeObjectRelationMapper.selectOne(wrapper);
        if (originalObjectRelation == null || StringUtils.isBlank(originalObjectRelation.getId())) {
            return objectRelationManage;
        }

        //回填原始汇聚的数据集信息
        objectRelationManage.setOriginalId(originalObjectRelation.getId());
        objectRelationManage.setOriginalObjectId(originalObjectRelation.getObjectId());
        objectRelationManage.setOriginalObjectName(originalObjectRelation.getObjectName());
        objectRelationManage.setOriginalTableId(originalObjectRelation.getTableId());
        objectRelationManage.setOriginalParentId("-1");

        //根据原始汇聚的id去查对应的标准层的数据集
        StandardizeObjectRelationEntity standardObjectRelation = standardizeObjectRelationMapper.selectSORByParentId(originalObjectRelation.getId());
        if (standardObjectRelation == null || (StringUtils.isBlank(standardObjectRelation.getId())
                && !originalObjectRelation.getId().equalsIgnoreCase(standardObjectRelation.getParentId()))) {
            return objectRelationManage;
        }
        objectRelationManage.setStandardObjectId(standardObjectRelation.getObjectId());
        objectRelationManage.setStandardObjectName(standardObjectRelation.getObjectName());
        objectRelationManage.setStandardParentId(standardObjectRelation.getParentId());
        objectRelationManage.setStandardTableId(standardObjectRelation.getTableId());

        //objectFieldList
        ObjectEntity objectStd = SelectUtil.getObjectEntityByTableId(objectMapper, standardObjectRelation.getTableId());
        LambdaQueryWrapper<ObjectFieldEntity> wrapperOF = Wrappers.lambdaQuery();
        wrapperOF.eq(ObjectFieldEntity::getDeleted, 0);
        wrapperOF.eq(ObjectFieldEntity::getObjectId, objectStd.getObjectId());
        wrapperOF.orderByAsc(ObjectFieldEntity::getRecno);
        List<ObjectFieldEntity> objectFieldList = objectFieldMapper.selectList(wrapperOF);
//        //fieldDeterminerList
//        LambdaQueryWrapper<FieldDeterminerEntity> wrapperFD = Wrappers.lambdaQuery();
//        wrapperFD.eq(FieldDeterminerEntity::getDeterminerStateNum, "05");
//        List<FieldDeterminerEntity> fieldDeterminerEntities = fieldDeterminerMapper.selectList(wrapperFD);
//        for (ObjectFieldEntity objectField : objectFieldList) {
//            // 限定词名称
//            fieldDeterminerEntities.stream().forEach(fd -> {
//                if (fd.getDeterminerId().equalsIgnoreCase(objectField.getDeterminerId())) {
//                    objectField.setDeterminerName(fd.getDchinseName());
//                }
//            });
//        }
        objectRelationManage.setStandardFieldCount(objectFieldList.size());

        //获取原始汇聚下的数据项信息
        LambdaQueryWrapper<StandardizeObjectfieldRelEntity> wrapperSOFR = Wrappers.lambdaQuery();
        wrapperSOFR.eq(StandardizeObjectfieldRelEntity::getSetId, originalObjectRelation.getId());
        List<StandardizeObjectfieldRelEntity> originalObjectFieldRelations = standardizeObjectfieldRelMapper.selectList(wrapperSOFR);
        //获取标准层下的数据项信息
        wrapperSOFR = Wrappers.lambdaQuery();
        wrapperSOFR.eq(StandardizeObjectfieldRelEntity::getSetId, standardObjectRelation.getId());
        List<StandardizeObjectfieldRelEntity> standardObjectFieldRelations = standardizeObjectfieldRelMapper.selectList(wrapperSOFR);

        originalObjectFieldRelations.stream().forEach(d -> {
            List<StandardizeObjectfieldRelEntity> fieldMapping = new ArrayList<>();
            standardObjectFieldRelations.stream().forEach(e -> {
                if ("-1".equalsIgnoreCase(d.getParentId()) && d.getId().equalsIgnoreCase(e.getParentId())) {
                    fieldMapping.add(e);
                }
            });
            d.setObjectFieldRelationMapping(fieldMapping);
        });
        objectRelationManage.setObjectFieldRelation(originalObjectFieldRelations);
        return objectRelationManage;
    }


    @Override
    public ObjectRelationManageVO getObjectRelation(ObjectRelationManageVO data) {
        log.info(">>>>>>开始数据集对标");
        ObjectRelationManageVO objectRelationManage = new ObjectRelationManageVO();
        objectRelationManage.setOriginalObjectName(data.getOriginalObjectName());
        objectRelationManage.setOriginalTableId(data.getOriginalTableId());
        ObjectEntity object = SelectUtil.getObjectEntityByTableId(objectMapper, data.getStandardTableId());
        objectRelationManage.setStandardObjectId(object.getObjectId());
        objectRelationManage.setStandardObjectName(data.getStandardObjectName());
        objectRelationManage.setStandardTableId(data.getStandardTableId());
        List<StandardizeObjectfieldRelEntity> originalObjectFieldList = data.getObjectFieldRelation();
        log.info(">>>>>>探查映射");
        if ("detect".equalsIgnoreCase(data.getType())) {
            //创建调用仓库接口的参数
            DataDefinitionDTO dto = new DataDefinitionDTO();
            dto.setResId(data.getResId());
            dto.setProjectName(data.getProjectName());
            dto.setTableNameEn(data.getTableNameEn());
            //获取全部的相似度探查结果
            List<TableSimilarInfo> dataSimilarListInfo = restTemplateHandle.getDataSimilarInfo(dto);
            if (dataSimilarListInfo.size() != 0 && !dataSimilarListInfo.isEmpty()) {
                //筛选出相同tableId的探查结果
                TableSimilarInfo tableSimilarInfo = dataSimilarListInfo.stream().filter(d -> data.getStandardTableId().equalsIgnoreCase(d.getProtocolCode())).findFirst().get();
                //获取映射字段信息
                List<SourceFieldMapping> sourceFieldMappingList = JSONObject.parseArray(tableSimilarInfo.getMappingSimilarInfo().getSourceTargetMapping(), SourceFieldMapping.class);
                List<FieldInfo> targetFieldList = tableSimilarInfo.getMappingSimilarInfo().getTargetFieldList();
                originalObjectFieldList.stream().forEach(e -> {
                    List<StandardizeObjectfieldRelEntity> objectFieldRelationMapping = new ArrayList<>();
                    sourceFieldMappingList.stream().forEach(d -> {
                        if (e.getFieldName().equalsIgnoreCase(d.getSourceField())) {
                            FieldInfo fieldInfo = targetFieldList.stream().filter(n -> n.getFieldName().equalsIgnoreCase(d.getTargetField())).findFirst().get();
                            StandardizeObjectfieldRelEntity mappingField = getObjectFieldRelationBySourceField(e, fieldInfo);
                            objectFieldRelationMapping.add(mappingField);
                        }
                        e.setObjectFieldRelationMapping(objectFieldRelationMapping);
                    });
                    if (e.getObjectFieldRelationMapping().size() == 0) {
                        e.getObjectFieldRelationMapping().add(new StandardizeObjectfieldRelEntity());
                    }
                });
                objectRelationManage.setStandardFieldCount(targetFieldList.size());
            }
            objectRelationManage.setObjectFieldRelation(originalObjectFieldList);
            return objectRelationManage;
        }
        log.info("数据元映射");
        if ("gadsjField".equalsIgnoreCase(data.getType())) {
            LambdaQueryWrapper<ObjectFieldEntity> wrapperOF = Wrappers.lambdaQuery();
            wrapperOF.eq(ObjectFieldEntity::getDeleted, 0);
            wrapperOF.eq(ObjectFieldEntity::getObjectId, object.getObjectId());
            wrapperOF.orderByAsc(ObjectFieldEntity::getRecno);
            List<ObjectFieldEntity> standardObjectField = objectFieldMapper.selectList(wrapperOF);
            if (standardObjectField.size() != 0) {
                List<StandardizeObjectfieldRelEntity> list = new ArrayList<>();
                standardObjectField.stream().forEach(objectField -> {
                    StandardizeObjectfieldRelEntity entity = new StandardizeObjectfieldRelEntity();
                    entity.setRecno(objectField.getRecno());
                    entity.setColumnName(objectField.getColumnName());
                    entity.setFieldChineseName(objectField.getFieldChineseName());
                    entity.setGadsjFieldId(objectField.getGadsjFieldId());
                    entity.setCreateTime(objectField.getCreateTime());
                    entity.setUpdateTime(objectField.getUpdateTime());
                    list.add(entity);
                });
                //将gadsjFieldId相同的字段进行对标
                originalObjectFieldList.stream().forEach(d -> {
                    list.stream().forEach(e -> {
                        if (StringUtils.isNotBlank(e.getGadsjFieldId()) && d.getGadsjFieldId().equalsIgnoreCase(e.getGadsjFieldId())) {
                            List<StandardizeObjectfieldRelEntity> addList = new ArrayList<>();
                            addList.add(e);
                            d.setObjectFieldRelationMapping(addList);
                        } else {
                            List<StandardizeObjectfieldRelEntity> objectFieldRelationList = new ArrayList<>();
                            StandardizeObjectfieldRelEntity objectFieldRelation = new StandardizeObjectfieldRelEntity();
                            objectFieldRelation.setCreateTime(new Date());
                            objectFieldRelationList.add(objectFieldRelation);
                            d.setObjectFieldRelationMapping(objectFieldRelationList);
                        }
                    });

                });
                objectRelationManage.setObjectFieldRelation(originalObjectFieldList);
                objectRelationManage.setStandardFieldCount(standardObjectField.size());
            }
            return objectRelationManage;
        }
        return objectRelationManage;
    }

    @Override
    public List<StandardizeObjectfieldRelEntity> getColumnNameList(String searchText, String tableId) {
        List<StandardizeObjectfieldRelEntity> objectFieldArrayList = new ArrayList<>();
        ObjectEntity object = SelectUtil.getObjectEntityByTableId(objectMapper, tableId);
        if (object.getObjectId() != null) {
            LambdaQueryWrapper<ObjectFieldEntity> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(ObjectFieldEntity::getObjectId, object.getObjectId());
            if (StringUtils.isNotBlank(searchText)) {
                wrapper.nested(wrapper2 -> {
                    wrapper2.apply("lower(COLUMNNAME) like lower({0})", "%" + searchText.toLowerCase() + "%");
                    wrapper2.or().apply("lower(FIELDCHINEENAME) like lower({0})", "%" + searchText.toLowerCase() + "%");
                });
            }
            List<ObjectFieldEntity> objectFieldEntities = objectFieldMapper.selectList(wrapper);
            for (ObjectFieldEntity data : objectFieldEntities) {
                StandardizeObjectfieldRelEntity objectFieldRelation = new StandardizeObjectfieldRelEntity();
                if (StringUtils.isNotBlank(data.getGadsjFieldId())) {
                    LambdaQueryWrapper<SynlteFieldEntity> wrapperSF = Wrappers.lambdaQuery();
                    wrapperSF.eq(SynlteFieldEntity::getFieldId, data.getGadsjFieldId());
                    SynlteFieldEntity synlteField = synlteFieldMapper.selectOne(wrapperSF);
                    if (synlteField != null) {
                        String codeId = synlteField.getCodeId();
                        String dictionaryName = fieldCodeMapper.searchFieldCodeByCodeId(codeId);
                        objectFieldRelation.setDictionaryRefId(codeId);
                        objectFieldRelation.setDictionaryRef(dictionaryName);
                    }
                }
                objectFieldRelation.setRecno(data.getRecno());
                objectFieldRelation.setColumnName(data.getColumnName());
                objectFieldRelation.setFieldChineseName(data.getFieldChineseName());
                objectFieldRelation.setGadsjFieldId(data.getGadsjFieldId());
                objectFieldArrayList.add(objectFieldRelation);
            }
            //根据中文名排序
            objectFieldArrayList = objectFieldArrayList.stream().filter(d -> StringUtils.isNotBlank(d.getFieldChineseName())).
                    distinct().sorted((s1, s2) -> Collator.getInstance(Locale.CHINA)
                            .compare(s2.getFieldChineseName(), s1.getFieldChineseName()))
                    .collect(Collectors.toList());
        }
        return objectFieldArrayList;
    }

    @Override
    public ObjectRelationManageVO dataFieldMapping(ObjectRelationManageVO param) {
        log.info("开始数据集对标字段映射");
        ObjectRelationManageVO objectRelationManage = new ObjectRelationManageVO();
        //获取标准的id
        ObjectEntity object = SelectUtil.getObjectEntityByTableId(objectMapper, param.getStandardTableId());
        //创建调用仓库接口的参数
        DataDefinitionDTO dto = new DataDefinitionDTO();
        dto.setResId(param.getResId());
        dto.setProjectName(param.getProjectName());
        dto.setTableNameEn(param.getTableNameEn());
        //获取全部的相似度探查结果
        List<TableSimilarInfo> dataSimilarListInfo = restTemplateHandle.getDataSimilarInfo(dto);

        List<SourceFieldMapping> sourceFieldMappingList = null;
        List<FieldInfo> targetFieldList = null;
        List<ObjectFieldEntity> objectFieldList = null;
        //当仓库返回的接口有数据时,直接用仓库的数据
        if (dataSimilarListInfo.size() != 0 && !dataSimilarListInfo.isEmpty()) {
            //筛选出相同tableId的探查结果
            TableSimilarInfo tableSimilarInfo = dataSimilarListInfo.stream().filter(d -> param.getStandardTableId().equalsIgnoreCase(d.getProtocolCode())).findFirst().get();
            //获取映射字段信息
            sourceFieldMappingList = JSONObject.parseArray(tableSimilarInfo.getMappingSimilarInfo().getSourceTargetMapping(), SourceFieldMapping.class);
            targetFieldList = tableSimilarInfo.getMappingSimilarInfo().getTargetFieldList();
        } else {
            LambdaQueryWrapper<ObjectFieldEntity> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(ObjectFieldEntity::getDeleted, 0);
            wrapper.eq(ObjectFieldEntity::getObjectId, object.getObjectId());
            wrapper.orderByAsc(ObjectFieldEntity::getRecno);
            objectFieldList = objectFieldMapper.selectList(wrapper);
        }

        //返回值填充
        objectRelationManage.setOriginalId(StringUtils.isNotBlank(param.getOriginalId()) ? param.getOriginalId() : null);
        objectRelationManage.setOriginalObjectId(param.getOriginalObjectId() != null ? param.getOriginalObjectId() : null);
        objectRelationManage.setOriginalObjectName(param.getOriginalObjectName());
        objectRelationManage.setOriginalTableId(param.getOriginalTableId());
        objectRelationManage.setOriginalParentId("-1");
        objectRelationManage.setStandardObjectId(object.getObjectId());
        objectRelationManage.setStandardObjectName(param.getStandardObjectName());
        objectRelationManage.setStandardTableId(param.getStandardTableId());
        List<StandardizeObjectfieldRelEntity> originalObjectFieldList = param.getObjectFieldRelation();
        if ("detect".equalsIgnoreCase(param.getType())) {
            log.info("探查推荐字段映射");
            //字段映射
            if (dataSimilarListInfo.size() != 0) {
                List<FieldInfo> finalTargetFieldList = targetFieldList;
                List<SourceFieldMapping> finalSourceFieldMappingList = sourceFieldMappingList;
                originalObjectFieldList.stream().forEach(e -> {
                    List<StandardizeObjectfieldRelEntity> objectFieldRelationMapping = new ArrayList<>();
                    finalSourceFieldMappingList.stream().forEach(d -> {
                        if (e.getFieldName().equalsIgnoreCase(d.getSourceField())) {
                            FieldInfo fieldInfo = finalTargetFieldList.stream().filter(n -> n.getFieldName().equalsIgnoreCase(d.getTargetField())).findFirst().get();
                            StandardizeObjectfieldRelEntity mappingField = getObjectFieldRelationBySourceField(e, fieldInfo);
                            objectFieldRelationMapping.add(mappingField);
                        }
                        e.setObjectFieldRelationMapping(objectFieldRelationMapping);
                    });
                    if (e.getObjectFieldRelationMapping().size() == 0) {
                        e.getObjectFieldRelationMapping().add(new StandardizeObjectfieldRelEntity());
                    }
                });
                objectRelationManage.setStandardFieldCount(targetFieldList.size());
            } else {
                originalObjectFieldList.stream().forEach(d -> {
                    if (d.getObjectFieldRelationMapping() == null) {
                        List<StandardizeObjectfieldRelEntity> objectFieldMapping = new ArrayList<>();
                        objectFieldMapping.add(new StandardizeObjectfieldRelEntity());
                        d.setObjectFieldRelationMapping(objectFieldMapping);
                    }
                });
                objectRelationManage.setStandardFieldCount(objectFieldList.size());
            }
            objectRelationManage.setObjectFieldRelation(originalObjectFieldList);
            return objectRelationManage;
        }
        if ("gadsjField".equalsIgnoreCase(param.getType())) {
            log.info("数据元映射");
            if (dataSimilarListInfo.size() != 0) {
                List<FieldInfo> finalTargetFieldList1 = targetFieldList;
                originalObjectFieldList.stream().forEach(d -> {
                    List<StandardizeObjectfieldRelEntity> objectFieldRelations = new ArrayList<>();
                    finalTargetFieldList1.stream().forEach(e -> {
                        if (StringUtils.isNotBlank(d.getGadsjFieldId()) && StringUtils.isNotBlank(e.getSynFieldId()) &&
                                d.getGadsjFieldId().equalsIgnoreCase(e.getSynFieldId())) {
                            StandardizeObjectfieldRelEntity mappingField = getObjectFieldRelationBySourceField(d, e);
                            objectFieldRelations.add(mappingField);
                        }
                    });
                    d.setObjectFieldRelationMapping(objectFieldRelations);
                    if (d.getObjectFieldRelationMapping().size() == 0) {
                        d.getObjectFieldRelationMapping().add(new StandardizeObjectfieldRelEntity());
                    }
                });
            } else {
                List<ObjectFieldEntity> finalObjectFieldList = objectFieldList;
                originalObjectFieldList.stream().forEach(d -> {
                    List<StandardizeObjectfieldRelEntity> objectFieldRelations = new ArrayList<>();
                    finalObjectFieldList.stream().forEach(e -> {
                        if (StringUtils.isNotBlank(d.getGadsjFieldId()) && StringUtils.isNotBlank(e.getGadsjFieldId()) &&
                                d.getGadsjFieldId().equalsIgnoreCase(e.getGadsjFieldId())) {
                            StandardizeObjectfieldRelEntity mappingField = getObjectFieldRelationByStandField(d, e);
                            objectFieldRelations.add(mappingField);
                        }
                    });
                    d.setObjectFieldRelationMapping(objectFieldRelations);
                    if (d.getObjectFieldRelationMapping().size() == 0) {
                        d.getObjectFieldRelationMapping().add(new StandardizeObjectfieldRelEntity());
                    }
                });
            }
            if (dataSimilarListInfo.size() != 0) {
                objectRelationManage.setStandardFieldCount(targetFieldList.size());
            } else {
                objectRelationManage.setStandardFieldCount(objectFieldList.size());
            }
            objectRelationManage.setObjectFieldRelation(originalObjectFieldList);
            return objectRelationManage;
        }
        if ("recno".equalsIgnoreCase(param.getType())) {
            log.info("序号映射");
            if (dataSimilarListInfo.size() != 0) {
                List<FieldInfo> finalTargetFieldList2 = targetFieldList;
                originalObjectFieldList.stream().forEach(d -> {
                    List<StandardizeObjectfieldRelEntity> objectFieldRelations = new ArrayList<>();
                    finalTargetFieldList2.stream().forEach(e -> {
                        if (d.getRecno().equals(e.getNo())) {
                            StandardizeObjectfieldRelEntity mappingField = getObjectFieldRelationBySourceField(d, e);
                            objectFieldRelations.add(mappingField);
                        }
                    });
                    d.setObjectFieldRelationMapping(objectFieldRelations);
                    if (d.getObjectFieldRelationMapping().size() == 0) {
                        d.getObjectFieldRelationMapping().add(new StandardizeObjectfieldRelEntity());
                    }
                });
            } else {
                List<ObjectFieldEntity> finalObjectFieldList1 = objectFieldList;
                originalObjectFieldList.stream().forEach(d -> {
                    List<StandardizeObjectfieldRelEntity> objectFieldRelations = new ArrayList<>();
                    finalObjectFieldList1.stream().forEach(e -> {
                        if (d.getRecno().equals(e.getRecno())) {
                            StandardizeObjectfieldRelEntity mappingField = getObjectFieldRelationByStandField(d, e);
                            objectFieldRelations.add(mappingField);
                        }
                    });
                    d.setObjectFieldRelationMapping(objectFieldRelations);
                    if (d.getObjectFieldRelationMapping().size() == 0) {
                        d.getObjectFieldRelationMapping().add(new StandardizeObjectfieldRelEntity());
                    }
                });
            }
            if (dataSimilarListInfo.size() != 0) {
                objectRelationManage.setStandardFieldCount(targetFieldList.size());
            } else {
                objectRelationManage.setStandardFieldCount(objectFieldList.size());
            }
            objectRelationManage.setObjectFieldRelation(originalObjectFieldList);
            return objectRelationManage;
        }
        return objectRelationManage;
    }

    /**
     * 创建数据集对标字段项信息
     *
     * @param objectFieldRelation 原始字段项
     * @param fieldInfo
     */
    private StandardizeObjectfieldRelEntity getObjectFieldRelationBySourceField(StandardizeObjectfieldRelEntity objectFieldRelation, FieldInfo fieldInfo) {
        StandardizeObjectfieldRelEntity mappingField = new StandardizeObjectfieldRelEntity();
        try {
            if (objectFieldRelation == null || fieldInfo == null) {
                throw new Exception(String.format("%s：%s", ErrorCodeEnum.DATA_IS_NULL, "创建数据集对标字段项的参数为空"));
            }
            mappingField.setRecno(fieldInfo.getNo());
            mappingField.setColumnName(fieldInfo.getFieldName());
            mappingField.setGadsjFieldId(StringUtils.isNotBlank(fieldInfo.getSynFieldId()) ? fieldInfo.getSynFieldId() : null);
            mappingField.setDictionaryRef(StringUtils.isNotBlank(fieldInfo.getDataDxnry()) ? fieldInfo.getDataDxnry() : null);
            mappingField.setDictionaryRefId(StringUtils.isNotBlank(fieldInfo.getDataDxnryId()) ? fieldInfo.getDataDxnryId() : null);
            mappingField.setParentColumnName(objectFieldRelation.getColumnName());
            mappingField.setFieldChineseName(StringUtils.isNotBlank(fieldInfo.getComments()) ? fieldInfo.getComments() : null);
        } catch (Exception e) {
            log.error(">>>>>>创建数据集对标字段项信息失败：", e);
        }
        return mappingField;
    }

    private StandardizeObjectfieldRelEntity getObjectFieldRelationByStandField(StandardizeObjectfieldRelEntity objectFieldRelation, ObjectFieldEntity objectField) {
        StandardizeObjectfieldRelEntity mappingField = new StandardizeObjectfieldRelEntity();
        try {
            if (objectFieldRelation == null || objectField == null) {
                throw new Exception(String.format("%s：%s", ErrorCodeEnum.DATA_IS_NULL, "创建数据集对标字段项的参数为空"));
            }
            mappingField.setRecno(objectField.getRecno());
            mappingField.setColumnName(objectField.getFieldName());
            mappingField.setGadsjFieldId(StringUtils.isNotBlank(objectField.getGadsjFieldId()) ? objectField.getGadsjFieldId() : null);
            if (StringUtils.isNotBlank(objectField.getGadsjFieldId())) {
                LambdaQueryWrapper<SynlteFieldEntity> wrapper = Wrappers.lambdaQuery();
                wrapper.eq(SynlteFieldEntity::getFieldId, objectField.getGadsjFieldId());
                SynlteFieldEntity synlteField = synlteFieldMapper.selectOne(wrapper);
                if (synlteField != null) {
                    String codeId = synlteField.getCodeId();
                    if (StringUtils.isNotBlank(codeId)) {
                        String dictionaryName = fieldCodeMapper.searchFieldCodeByCodeId(codeId);
                        mappingField.setDictionaryRef(dictionaryName);
                        mappingField.setDictionaryRefId(codeId);
                    }
                }
            }
            mappingField.setParentColumnName(objectFieldRelation.getColumnName());
            mappingField.setFieldChineseName(StringUtils.isNotBlank(objectField.getFieldChineseName()) ? objectField.getFieldChineseName() : null);
        }catch (Exception e){
            log.error(">>>>>>getObjectFieldRelationByStandField失败：", e);
        }
        return mappingField;
    }

}

