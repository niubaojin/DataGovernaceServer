package com.synway.datastandardmanager.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.synway.common.bean.ServerResponse;
import com.synway.datastandardmanager.config.HashLock;
import com.synway.datastandardmanager.constant.Common;
import com.synway.datastandardmanager.dao.master.*;
import com.synway.datastandardmanager.exceptionhandler.ErrorCode;
import com.synway.datastandardmanager.exceptionhandler.SystemException;
import com.synway.datastandardmanager.interceptor.AuthorizedUserUtils;
import com.synway.datastandardmanager.pojo.*;
import com.synway.datastandardmanager.pojo.dataDefinitionManagement.*;
import com.synway.datastandardmanager.pojo.enums.ManufacturerName;
import com.synway.datastandardmanager.pojo.enums.ObjectStateType;
import com.synway.datastandardmanager.pojo.enums.StoreType;
import com.synway.datastandardmanager.pojo.synltefield.SynlteFieldObject;
import com.synway.datastandardmanager.pojo.warehouse.DataSimilarParameter;
import com.synway.datastandardmanager.pojo.warehouse.FieldInfo;
import com.synway.datastandardmanager.pojo.warehouse.TableSimilarInfo;
import com.synway.datastandardmanager.service.DataDefinitionService;
import com.synway.datastandardmanager.service.ResourceManageService;
import com.synway.datastandardmanager.util.DateUtil;
import com.synway.datastandardmanager.util.ExceptionUtil;
import com.synway.datastandardmanager.util.RestTemplateHandle;
import com.synway.datastandardmanager.util.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.constraints.Size;
import java.text.Collator;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


@Service
@Slf4j
public class DataDefinitionServiceImpl implements DataDefinitionService {

    private static HashLock<String> HASH_LOCK = new HashLock<>();


    @Autowired
    private DataDefinitionDao dataDefinitionDao;

    @Autowired
    private SynlteFieldDao synlteFieldDao;

    @Autowired
    private ElementCodeSetManageDao elementCodeSetManageDao;

    @Autowired
    private ResourceManageDao resourceManageDao;

    @Autowired
    private ConcurrentHashMap<String, Boolean> switchHashMap;

    @Autowired
    private ResourceManageService resourceManageServiceImpl;

    @Autowired
    private ResourceManageAddDao resourceManageAddDao;

    @Autowired
    private RestTemplateHandle restTemplateHandle;


    @Override
    public Map<String, Object> searchDataDefinitionTable(DataDefinitionParameter parameter) {
        // 搜索内容如果存在空值，将其变为 null值
        if (StringUtils.isBlank(parameter.getSearchText())) {
            parameter.setSearchText(null);
        }
//        if (StringUtils.isBlank(parameter.getSearchType())) {
//            parameter.setSearchType(null);
//        }
        if (parameter.getSort() == null || parameter.getSort().isEmpty()) {
            parameter.setSort("modDate");
        }
        if (parameter.getSortOrder() == null || parameter.getSortOrder().isEmpty()){
            parameter.setSortOrder("desc");
        }

        Page<DataDefinitionPojo> page = PageHelper.startPage(parameter.getPageIndex(), parameter.getPageSize());

        List<DataDefinitionPojo> dataDefinitionList = dataDefinitionDao.searchDataDefinitionTable(parameter);

        if (dataDefinitionList != null && !dataDefinitionList.isEmpty()) {
            int num = 1;
            for (DataDefinitionPojo data : dataDefinitionList) {
                data.setRecno(num++);
            }
        } else {
            dataDefinitionList = new ArrayList<>();
        }

        PageInfo<DataDefinitionPojo> pageInfo = new PageInfo<>(dataDefinitionList);
        Map<String, Object> map = new HashMap<>();
        map.put("total", pageInfo.getTotal());
        map.put("rows", pageInfo.getList());

        return map;
    }

    @Override
    public String getDictionaryNameById(String gadsjFieldId) {
        log.info("开始根据数据元内部标识符查询对应的字典值");
        SynlteFieldObject synlteFieldObject = synlteFieldDao.searchSynlteFieldById(gadsjFieldId);
        log.info("数据元信息为:{}",synlteFieldObject);
        String codeId = synlteFieldObject.getCodeId();
        String dictionaryName = elementCodeSetManageDao.searchFieldCodeByCodeId(codeId);
        return dictionaryName;
    }

    @Override
    public List<PageSelectOneValue> searchAllDataStandard(String searchText) {
        if(StringUtils.isBlank(searchText)){
            searchText = null;
        }
        //查询全部的数据集标准信息
        List<PageSelectOneValue> resultList = resourceManageDao.searchAllDataStandard(searchText);
        if(resultList == null || resultList.isEmpty()){
            return new ArrayList<>();
        }
        //根据中文名排序
//        resultList = resultList.stream().sorted((s1, s2) -> Collator.getInstance(Locale.CHINA)
//                .compare(s2.getLabel(), s1.getLabel()))
//                .limit(100).collect(Collectors.toList());
        resultList = resultList.stream().sorted((s1, s2) -> Collator.getInstance(Locale.CHINA)
                .compare(s2.getLabel(), s1.getLabel()))
                .collect(Collectors.toList());

        return resultList;
    }

    @Override
    public List<PageSelectOneValue> getDataSetDetectSimilarResult(DataSimilarParameter dataSimilarParameter) {
        log.info("======开始获取探查分析推荐标准数据集======");
        if(StringUtils.isBlank(dataSimilarParameter.getResId()) && StringUtils.isBlank(dataSimilarParameter.getProjectName())
                && StringUtils.isBlank(dataSimilarParameter.getTableNameEn())){
            throw SystemException.asSystemException(ErrorCode.DATA_IS_NULL, "获取探查分析推荐标准数据集参数为空");
        }
        List<TableSimilarInfo> dataSimilarInfo = restTemplateHandle.getDataSimilarInfo(dataSimilarParameter);
        if(dataSimilarInfo.size() == 0){
            return new ArrayList<>();
        }
        List<PageSelectOneValue> resultList = new ArrayList<>();
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMinimumFractionDigits(2);
        dataSimilarInfo.stream().forEach( d ->{
            PageSelectOneValue dataSimilarElement = new PageSelectOneValue(d.getProtocolCode(),d.getObjectName(),nf.format(d.getScore()));
            resultList.add(dataSimilarElement);
        });
        return resultList;
    }

    @Override
    public ObjectRelationManage getDataSetMapping(String tableId) {
        ObjectRelationManage objectRelationManage = new ObjectRelationManage();
        //根据tableId查询objectId
        String objectId = resourceManageAddDao.getObjectIDByTableID(tableId);
        log.info("查询出的objectId为:{}",objectId);
        //查询到原始汇聚的数据集信息
        if(StringUtils.isBlank(objectId)){
            throw SystemException.asSystemException(ErrorCode.DATA_IS_NULL, "未知异常，请联系相关人员");
        }
        ObjectRelation originalObjectRelation = dataDefinitionDao.searchObjectRelationByObjectId(objectId);
        if(originalObjectRelation == null || StringUtils.isBlank(originalObjectRelation.getId())){
            return objectRelationManage;
        }
        //回填原始汇聚的数据集信息
        objectRelationManage.setOriginalId(originalObjectRelation.getId());
        objectRelationManage.setOriginalObjectId(originalObjectRelation.getObjectId());
        objectRelationManage.setOriginalObjectName(originalObjectRelation.getObjectName());
        objectRelationManage.setOriginalTableId(originalObjectRelation.getTableId());
        objectRelationManage.setOriginalParentId("-1");
        //根据原始汇聚的id去查对应的标准层的数据集
        ObjectRelation standardObjectRelation = dataDefinitionDao.getStandardObjectRelationByParentId(originalObjectRelation.getId());
        if(standardObjectRelation == null || (StringUtils.isBlank(standardObjectRelation.getId())
                && !originalObjectRelation.getId().equalsIgnoreCase(standardObjectRelation.getParentId())) ){
//            throw SystemException.asSystemException(ErrorCode.DATA_IS_NULL, "该数据集的对标标准层查询失败");
            return objectRelationManage;
        }
        objectRelationManage.setStandardObjectId(standardObjectRelation.getObjectId());
        objectRelationManage.setStandardObjectName(standardObjectRelation.getObjectName());
        objectRelationManage.setStandardParentId(standardObjectRelation.getParentId());
        objectRelationManage.setStandardTableId(standardObjectRelation.getTableId());
        String standObjectId = resourceManageAddDao.getObjectIDByTableID(standardObjectRelation.getTableId());
        List<ObjectField> standFieldList = resourceManageDao.selectObjectFieldByObjectId(Long.valueOf(standObjectId));
        objectRelationManage.setStandardFieldCount(standFieldList.size());
        //获取原始汇聚下的数据项信息
        List<ObjectFieldRelation> originalObjectFieldRelations = dataDefinitionDao.searchObjectFieldById(originalObjectRelation.getId());
        //获取标准层下的数据项信息
        List<ObjectFieldRelation> standardObjectFieldRelations = dataDefinitionDao.searchObjectFieldById(standardObjectRelation.getId());

        originalObjectFieldRelations.stream().forEach( d-> {
            List<ObjectFieldRelation> fieldMapping = new ArrayList<>();
            standardObjectFieldRelations.stream().forEach(e ->{
                if("-1".equalsIgnoreCase(d.getParentId()) && d.getId().equalsIgnoreCase(e.getParentId())){
                    fieldMapping.add(e);
                }
            });
            d.setObjectFieldRelationMapping(fieldMapping);
        });
        objectRelationManage.setObjectFieldRelation(originalObjectFieldRelations);

        return objectRelationManage;
    }


    @Override
    public ObjectRelationManage getObjectRelation(ObjectRelationManage data) {
        log.info("开始数据集对标");
        ObjectRelationManage objectRelationManage = new ObjectRelationManage();
        objectRelationManage.setOriginalObjectName(data.getOriginalObjectName());
        objectRelationManage.setOriginalTableId(data.getOriginalTableId());
        String objectId = resourceManageAddDao.getObjectIDByTableID(data.getStandardTableId());
        objectRelationManage.setStandardObjectId(Long.valueOf(objectId));
        objectRelationManage.setStandardObjectName(data.getStandardObjectName());
        objectRelationManage.setStandardTableId(data.getStandardTableId());
        List<ObjectFieldRelation> originalObjectFieldList = data.getObjectFieldRelation();
        log.info("探查映射");
        if("detect".equalsIgnoreCase(data.getType())){
            //创建调用仓库接口的参数
            DataSimilarParameter dataSimilarParameter = new DataSimilarParameter(data.getResId(), data.getProjectName(), data.getTableNameEn());
            //获取全部的相似度探查结果
            List<TableSimilarInfo> dataSimilarListInfo = restTemplateHandle.getDataSimilarInfo(dataSimilarParameter);
            if(dataSimilarListInfo.size() != 0 && !dataSimilarListInfo.isEmpty()){
                //筛选出相同tableId的探查结果
                TableSimilarInfo tableSimilarInfo = dataSimilarListInfo.stream().filter(d -> data.getStandardTableId().equalsIgnoreCase(d.getProtocolCode())).findFirst().get();
                //获取映射字段信息
                List<SourceFieldMapping> sourceFieldMappingList = JSONObject.parseArray(tableSimilarInfo.getMappingSimilarInfo().getSourceTargetMapping(), SourceFieldMapping.class);
                List<FieldInfo> targetFieldList = tableSimilarInfo.getMappingSimilarInfo().getTargetFieldList();
                originalObjectFieldList.stream().forEach(e ->{
                    List<ObjectFieldRelation> objectFieldRelationMapping = new ArrayList<>();
                    sourceFieldMappingList.stream().forEach(d -> {
                        if (e.getFieldName().equalsIgnoreCase(d.getSourceField())) {
                            FieldInfo fieldInfo = targetFieldList.stream().filter(n -> n.getFieldName().equalsIgnoreCase(d.getTargetField())).findFirst().get();
                            ObjectFieldRelation mappingField = getObjectFieldRelationBySourceField(e, fieldInfo);
                            objectFieldRelationMapping.add(mappingField);
                        }
                        e.setObjectFieldRelationMapping(objectFieldRelationMapping);
                    });
                    if(e.getObjectFieldRelationMapping().size() == 0){
                        e.getObjectFieldRelationMapping().add(new ObjectFieldRelation());
                    }
                });
                objectRelationManage.setStandardFieldCount(targetFieldList.size());
            }
            objectRelationManage.setObjectFieldRelation(originalObjectFieldList);
            return objectRelationManage;
        }
        log.info("数据元映射");
        if("gadsjField".equalsIgnoreCase(data.getType())){
            List<ObjectFieldRelation> standardObjectField = dataDefinitionDao.searchFieldByObject(Long.valueOf(objectId));
            if(standardObjectField.size() != 0){
                objectRelationManage.setStandardFieldCount(standardObjectField.size());
                //将gadsjFieldId相同的字段进行对标
                originalObjectFieldList.stream().forEach( d ->{
                    standardObjectField.stream().forEach( e ->{
                        if(StringUtils.isNotBlank(e.getGadsjFieldId()) && d.getGadsjFieldId().equalsIgnoreCase(e.getGadsjFieldId())){
                            List<ObjectFieldRelation> addList = new ArrayList<>();
                            addList.add(e);
                            d.setObjectFieldRelationMapping(addList);
                        }else {
                            List<ObjectFieldRelation> objectFieldRelationList = new ArrayList<>();
                            ObjectFieldRelation objectFieldRelation = new ObjectFieldRelation();
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
    public List<ObjectFieldRelation> getColumnNameList(String searchText,String tableId) {
        log.info("开始获取字段名称下拉框,参数为:{}",searchText);
        String objectId = resourceManageAddDao.getObjectIDByTableID(tableId);
        if(StringUtils.isNotBlank(objectId)){
            List<ObjectFieldRelation> objectFieldArrayList = new ArrayList<>();
            List<ObjectField> resultList = resourceManageDao.getColumnNameList(objectId,searchText);
            for(ObjectField data : resultList){
                log.info("开始根据数据元内部标识符查询对应的字典值");
                if(StringUtils.isNotBlank(data.getGadsjFieldId())){
                    SynlteFieldObject synlteFieldObject = synlteFieldDao.searchSynlteFieldById(data.getGadsjFieldId());
                    log.info("数据元信息为:{}",synlteFieldObject);
                    ObjectFieldRelation objectFieldRelation = new ObjectFieldRelation();
                    if(synlteFieldObject != null){
                        String codeId = synlteFieldObject.getCodeId();
                        String dictionaryName = elementCodeSetManageDao.searchFieldCodeByCodeId(codeId);
                        log.info("查询到的引用字典内容为:{}",dictionaryName);
                        objectFieldRelation.setDictionaryRefId(codeId);
                        objectFieldRelation.setDictionaryRef(dictionaryName);
                    }
                    objectFieldRelation.setRecno(data.getRecno());
                    objectFieldRelation.setColumnName(data.getColumnName());
                    objectFieldRelation.setFieldChineseName(data.getFieldChineseName());
                    objectFieldRelation.setGadsjFieldId(data.getGadsjFieldId());
                    objectFieldArrayList.add(objectFieldRelation);
                }else{
                    ObjectFieldRelation objectFieldRelation = new ObjectFieldRelation();
                    objectFieldRelation.setRecno(data.getRecno());
                    objectFieldRelation.setColumnName(data.getColumnName());
                    objectFieldRelation.setFieldChineseName(data.getFieldChineseName());
                    objectFieldRelation.setGadsjFieldId(data.getGadsjFieldId());
                    objectFieldArrayList.add(objectFieldRelation);
                }
            }
            //根据中文名排序
            objectFieldArrayList = objectFieldArrayList.stream().filter(d->StringUtils.isNotBlank(d.getFieldChineseName())).
                    distinct().sorted((s1, s2) -> Collator.getInstance(Locale.CHINA)
                    .compare(s2.getFieldChineseName(), s1.getFieldChineseName()))
                    .collect(Collectors.toList());
            log.info("字段名称下拉框查询结束,条数为:{}",objectFieldArrayList.size());
            return objectFieldArrayList;
        }else{
            return new ArrayList<>();
        }

    }

    @Override
    public ObjectRelationManage dataFieldMapping(ObjectRelationManage param) {
        log.info("开始数据集对标字段映射");
        ObjectRelationManage objectRelationManage = new ObjectRelationManage();
        //获取标准的id
        String objectId = resourceManageAddDao.getObjectIDByTableID(param.getStandardTableId());
        //创建调用仓库接口的参数
        DataSimilarParameter dataSimilarParameter = new DataSimilarParameter(param.getResId(), param.getProjectName(), param.getTableNameEn());
        //获取全部的相似度探查结果
        List<TableSimilarInfo> dataSimilarListInfo = restTemplateHandle.getDataSimilarInfo(dataSimilarParameter);

        List<SourceFieldMapping> sourceFieldMappingList = null;
        List<FieldInfo> targetFieldList = null;
        List<ObjectField> objectFieldList = null;
        //当仓库返回的接口有数据时,直接用仓库的数据
        if(dataSimilarListInfo.size() != 0 && !dataSimilarListInfo.isEmpty()){
            //筛选出相同tableId的探查结果
            TableSimilarInfo tableSimilarInfo = dataSimilarListInfo.stream().filter(d -> param.getStandardTableId().equalsIgnoreCase(d.getProtocolCode())).findFirst().get();
            //获取映射字段信息
            sourceFieldMappingList = JSONObject.parseArray(tableSimilarInfo.getMappingSimilarInfo().getSourceTargetMapping(), SourceFieldMapping.class);
            targetFieldList = tableSimilarInfo.getMappingSimilarInfo().getTargetFieldList();
        }else{
            objectFieldList = resourceManageDao.selectObjectFieldByObjectId(Long.valueOf(objectId));
        }

        //返回值填充
        objectRelationManage.setOriginalId(StringUtils.isNotBlank(param.getOriginalId())? param.getOriginalId():null);
        objectRelationManage.setOriginalObjectId(param.getOriginalObjectId() != null ? param.getOriginalObjectId():null);
        objectRelationManage.setOriginalObjectName(param.getOriginalObjectName());
        objectRelationManage.setOriginalTableId(param.getOriginalTableId());
        objectRelationManage.setOriginalParentId("-1");
        objectRelationManage.setStandardObjectId(Long.valueOf(objectId));
        objectRelationManage.setStandardObjectName(param.getStandardObjectName());
        objectRelationManage.setStandardTableId(param.getStandardTableId());
        List<ObjectFieldRelation> originalObjectFieldList = param.getObjectFieldRelation();
        if("detect".equalsIgnoreCase(param.getType())){
            log.info("探查推荐字段映射");
            //字段映射
            if(dataSimilarListInfo.size() != 0){
                List<FieldInfo> finalTargetFieldList = targetFieldList;
                List<SourceFieldMapping> finalSourceFieldMappingList = sourceFieldMappingList;
                originalObjectFieldList.stream().forEach(e ->{
                    List<ObjectFieldRelation> objectFieldRelationMapping = new ArrayList<>();
                    finalSourceFieldMappingList.stream().forEach(d -> {
                        if (e.getFieldName().equalsIgnoreCase(d.getSourceField())) {
                            FieldInfo fieldInfo = finalTargetFieldList.stream().filter(n -> n.getFieldName().equalsIgnoreCase(d.getTargetField())).findFirst().get();
                            ObjectFieldRelation mappingField = getObjectFieldRelationBySourceField(e, fieldInfo);
                            objectFieldRelationMapping.add(mappingField);
                        }
                        e.setObjectFieldRelationMapping(objectFieldRelationMapping);
                    });
                    if(e.getObjectFieldRelationMapping().size() == 0){
                        e.getObjectFieldRelationMapping().add(new ObjectFieldRelation());
                    }
                });
                objectRelationManage.setStandardFieldCount(targetFieldList.size());
            }else {
                originalObjectFieldList.stream().forEach(d -> {
                    if(d.getObjectFieldRelationMapping() == null){
                        List<ObjectFieldRelation> objectFieldMapping = new ArrayList<>();
                        objectFieldMapping.add(new ObjectFieldRelation());
                        d.setObjectFieldRelationMapping(objectFieldMapping);
                    }
                });
                objectRelationManage.setStandardFieldCount(objectFieldList.size());
            }
            objectRelationManage.setObjectFieldRelation(originalObjectFieldList);
            return objectRelationManage;
        }
        if("gadsjField".equalsIgnoreCase(param.getType())){
            log.info("数据元映射");
            if(dataSimilarListInfo.size() != 0){
                List<FieldInfo> finalTargetFieldList1 = targetFieldList;
                originalObjectFieldList.stream().forEach(d ->{
                    List<ObjectFieldRelation> objectFieldRelations = new ArrayList<>();
                    finalTargetFieldList1.stream().forEach(e -> {
                        if(StringUtils.isNotBlank(d.getGadsjFieldId()) && StringUtils.isNotBlank(e.getSynFieldId()) &&
                                d.getGadsjFieldId().equalsIgnoreCase(e.getSynFieldId())){
                            ObjectFieldRelation mappingField = getObjectFieldRelationBySourceField(d, e);
                            objectFieldRelations.add(mappingField);
                        }
                    });
                    d.setObjectFieldRelationMapping(objectFieldRelations);
                    if(d.getObjectFieldRelationMapping().size() == 0){
                        d.getObjectFieldRelationMapping().add(new ObjectFieldRelation());
                    }
                });
            }else {
                List<ObjectField> finalObjectFieldList = objectFieldList;
                originalObjectFieldList.stream().forEach(d ->{
                    List<ObjectFieldRelation> objectFieldRelations = new ArrayList<>();
                    finalObjectFieldList.stream().forEach(e ->{
                        if(StringUtils.isNotBlank(d.getGadsjFieldId()) && StringUtils.isNotBlank(e.getGadsjFieldId()) &&
                                d.getGadsjFieldId().equalsIgnoreCase(e.getGadsjFieldId())){
                            ObjectFieldRelation mappingField = getObjectFieldRelationByStandField(d, e);
                            objectFieldRelations.add(mappingField);
                        }
                    });
                    d.setObjectFieldRelationMapping(objectFieldRelations);
                    if(d.getObjectFieldRelationMapping().size() == 0){
                        d.getObjectFieldRelationMapping().add(new ObjectFieldRelation());
                    }
                });
            }
            if(dataSimilarListInfo.size() != 0){
                objectRelationManage.setStandardFieldCount(targetFieldList.size());
            }else {
                objectRelationManage.setStandardFieldCount(objectFieldList.size());
            }
            objectRelationManage.setObjectFieldRelation(originalObjectFieldList);
            return objectRelationManage;
        }
        if("recno".equalsIgnoreCase(param.getType())){
            log.info("序号映射");
            if(dataSimilarListInfo.size() != 0){
                List<FieldInfo> finalTargetFieldList2 = targetFieldList;
                originalObjectFieldList.stream().forEach(d -> {
                    List<ObjectFieldRelation> objectFieldRelations = new ArrayList<>();
                    finalTargetFieldList2.stream().forEach(e -> {
                        if(d.getRecno().equals(e.getNo())){
                            ObjectFieldRelation mappingField = getObjectFieldRelationBySourceField(d, e);
                            objectFieldRelations.add(mappingField);
                        }
                    });
                    d.setObjectFieldRelationMapping(objectFieldRelations);
                    if(d.getObjectFieldRelationMapping().size() == 0){
                        d.getObjectFieldRelationMapping().add(new ObjectFieldRelation());
                    }
                });
            }else {
                List<ObjectField> finalObjectFieldList1 = objectFieldList;
                originalObjectFieldList.stream().forEach(d ->{
                    List<ObjectFieldRelation> objectFieldRelations = new ArrayList<>();
                    finalObjectFieldList1.stream().forEach(e->{
                        if(d.getRecno().equals(e.getRecno())){
                            ObjectFieldRelation mappingField = getObjectFieldRelationByStandField(d, e);
                            objectFieldRelations.add(mappingField);
                        }
                    });
                    d.setObjectFieldRelationMapping(objectFieldRelations);
                    if(d.getObjectFieldRelationMapping().size() == 0){
                        d.getObjectFieldRelationMapping().add(new ObjectFieldRelation());
                    }
                });
            }
            if(dataSimilarListInfo.size() != 0){
                objectRelationManage.setStandardFieldCount(targetFieldList.size());
            }else {
                objectRelationManage.setStandardFieldCount(objectFieldList.size());
            }
            objectRelationManage.setObjectFieldRelation(originalObjectFieldList);
            return objectRelationManage;
        }
        return objectRelationManage;
    }

    /**
     * 创建数据集对标字段项信息
     * @param objectFieldRelation 原始字段项
     * @param fieldInfo
     * @return
     */
    private ObjectFieldRelation getObjectFieldRelationBySourceField(ObjectFieldRelation objectFieldRelation,FieldInfo fieldInfo){
        if(objectFieldRelation == null || fieldInfo == null){
            throw SystemException.asSystemException(ErrorCode.DATA_IS_NULL, "创建数据集对标字段项的参数为空");
        }
        ObjectFieldRelation mappingField = new ObjectFieldRelation();
        mappingField.setRecno(fieldInfo.getNo());
        mappingField.setColumnName(fieldInfo.getFieldName());
        mappingField.setGadsjFieldId(StringUtils.isNotBlank(fieldInfo.getSynFieldId()) ? fieldInfo.getSynFieldId():null);
        mappingField.setDictionaryRef(StringUtils.isNotBlank(fieldInfo.getDataDxnry())? fieldInfo.getDataDxnry():null);
        mappingField.setDictionaryRefId(StringUtils.isNotBlank(fieldInfo.getDataDxnryId())? fieldInfo.getDataDxnryId():null);
        mappingField.setParentColumnName(objectFieldRelation.getColumnName());
        mappingField.setFieldChineseName(StringUtils.isNotBlank(fieldInfo.getComments())? fieldInfo.getComments():null);
        return mappingField;
    }

    private ObjectFieldRelation getObjectFieldRelationByStandField(ObjectFieldRelation objectFieldRelation,ObjectField objectField){
        if(objectFieldRelation == null || objectField == null){
            throw SystemException.asSystemException(ErrorCode.DATA_IS_NULL, "创建数据集对标字段项的参数为空");
        }
        ObjectFieldRelation mappingField = new ObjectFieldRelation();
        mappingField.setRecno(objectField.getRecno());
        mappingField.setColumnName(objectField.getFieldName());
        mappingField.setGadsjFieldId(StringUtils.isNotBlank(objectField.getGadsjFieldId()) ? objectField.getGadsjFieldId():null);
        if(StringUtils.isNotBlank(objectField.getGadsjFieldId())){
            SynlteFieldObject synlteFieldObject = synlteFieldDao.searchSynlteFieldById(objectField.getGadsjFieldId());
            if(synlteFieldObject != null){
                log.info("数据元信息为:{}",synlteFieldObject);
                String codeId = synlteFieldObject.getCodeId();
                if(StringUtils.isNotBlank(codeId)){
                    String dictionaryName = elementCodeSetManageDao.searchFieldCodeByCodeId(codeId);
                    mappingField.setDictionaryRef(dictionaryName);
                    mappingField.setDictionaryRefId(codeId);
                }
            }
        }
        mappingField.setParentColumnName(objectFieldRelation.getColumnName());
        mappingField.setFieldChineseName(StringUtils.isNotBlank(objectField.getFieldChineseName())? objectField.getFieldChineseName():null);
        return mappingField;
    }
}

