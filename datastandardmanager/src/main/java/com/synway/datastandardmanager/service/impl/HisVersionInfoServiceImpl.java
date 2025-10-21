package com.synway.datastandardmanager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.synway.datastandardmanager.constants.Common;
import com.synway.datastandardmanager.entity.dto.HisVersionInfoDTO;
import com.synway.datastandardmanager.entity.dto.ObjectManageDTO;
import com.synway.datastandardmanager.entity.pojo.*;
import com.synway.datastandardmanager.entity.vo.FilterListVO;
import com.synway.datastandardmanager.entity.vo.InputObjectCreateVO;
import com.synway.datastandardmanager.entity.vo.KeyValueVO;
import com.synway.datastandardmanager.enums.KeyIntEnum;
import com.synway.datastandardmanager.enums.KeyStrEnum;
import com.synway.datastandardmanager.mapper.*;
import com.synway.datastandardmanager.service.HisVersionInfoService;
import com.synway.datastandardmanager.util.DateUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.text.Collator;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class HisVersionInfoServiceImpl implements HisVersionInfoService {

    @Resource
    private ObjectVersionMapper objectVersionMapper;
    @Resource
    private ObjectMapper objectMapper;
    @Resource
    private ObjectHisMapper objectHisMapper;
    @Resource
    private ObjectFieldHisMapper objectFieldHisMapper;
    @Resource
    private SynlteFieldVersionMapper synlteFieldVersionMapper;
    @Resource
    private SynlteFieldHisMapper synltefieldHisMapper;
    @Resource
    private FieldDeterminerVersionMapper fieldDeterminerVersionMapper;
    @Resource
    private FieldDeterminerHisMapper fieldDeterminerHisMapper;
    @Resource
    private StandardizeOutputObjectMapper standardizeOutputObjectMapper;
    @Resource
    private FieldCodeValMapper fieldCodeValMapper;

    @Override
    public List<ObjectVersionEntity> searchAllObjectVersion(HisVersionInfoDTO dto) {
        ArrayList<ObjectVersionEntity> tableList = new ArrayList<>();
        try {
            log.info(">>>>>>查询标准版本表参数为{}", dto);
            List objectVersionList = objectVersionMapper.searchObjectVersionTable(dto);
            //过滤起始日期 截止日期的数据
            List<Object> filterObjectVersionList = filterVersionList(objectVersionList, dto, "version");
            List<ObjectVersionEntity> list = filterObjectVersionList.stream().map(d -> (ObjectVersionEntity) d).collect(Collectors.toList());
            int num = 1;
            for (ObjectVersionEntity data : list) {
                data.setNum(num++);
                tableList.add(data);
            }
            if (tableList.isEmpty()) {
                log.error("从数据库中获取到的数据为空");
            }
        } catch (Exception e) {
            log.error(">>>>>>查询标准版本出错：", e);
        }
        return tableList;
    }

    /**
     * 过滤数据起始日期 截止日期的数据
     */
    private List<Object> filterVersionList(List<Object> list, HisVersionInfoDTO parameter,
                                           String filterName) {
        list = list.parallelStream().filter(d -> {
            try {
                Field field = d.getClass().getDeclaredField(filterName);
                field.setAccessible(true);
                Integer dbTime = (Integer) field.get(d);
                String dbTimeStr = String.valueOf(dbTime);
                if (StringUtils.isNotBlank(parameter.getStartTimeText())) {
                    if (StringUtils.isBlank(dbTimeStr)) {
                        return false;
                    } else if (DateUtil.parseDate(dbTimeStr, DateUtil.DEFAULT_PATTERN_DATE_SIMPLE).before(DateUtil.parseDate(parameter.getStartTimeText(), DateUtil.DEFAULT_PATTERN_DATE_SIMPLE))) {
                        return false;
                    }
                }
                if (StringUtils.isNotBlank(parameter.getEndTimeText())) {
                    if (StringUtils.isBlank(dbTime.toString())) {
                        return false;
                    } else if (DateUtil.parseDate(dbTimeStr, DateUtil.DEFAULT_PATTERN_DATE_SIMPLE).after(DateUtil.parseDate(parameter.getEndTimeText(), DateUtil.DEFAULT_PATTERN_DATE_SIMPLE))) {
                        return false;
                    }
                }
                return true;
            } catch (Exception e) {
                log.error(">>>>>>数据筛选报错" + e.getMessage());
                return false;
            }
        }).collect(Collectors.toList());
        return list;
    }

    @Override
    public List<SynlteFieldVersionEntity> searchAllSynlteFieldVersion(HisVersionInfoDTO dto) {
        ArrayList<SynlteFieldVersionEntity> tableList = new ArrayList<>();
        try {
            log.info(">>>>>>查询数据元版本表参数为{}", dto);
            List synlteFieldVersionList = synlteFieldVersionMapper.searchSynlteFieldVersionTable(dto);
            //过滤起始日期 截止日期的数据
            List<Object> filterSynlteFieldVersionList = filterVersionList(synlteFieldVersionList, dto, "version");
            List<SynlteFieldVersionEntity> list = filterSynlteFieldVersionList.stream().map(d -> (SynlteFieldVersionEntity) d).collect(Collectors.toList());
            int num = 1;
            for (SynlteFieldVersionEntity data : list) {
                data.setNum(num++);
                tableList.add(data);
            }
        } catch (Exception e) {
            log.error(">>>>>>查询数据元版本出错：", e);
        }
        return tableList;
    }

    @Override
    public List<FieldDeterminerVersionEntity> searchAllFieldDeterminerVersion(HisVersionInfoDTO dto) {
        ArrayList<FieldDeterminerVersionEntity> tableList = new ArrayList<>();
        try {
            log.info(">>>>>>查询限定词版本表参数为：{}", dto);
            List determinerVersionTable = fieldDeterminerVersionMapper.searchFieldDeterminerVersionTable(dto);
            //过滤起始日期 截止日期的数据
            List<Object> filterVersionList = filterVersionList(determinerVersionTable, dto, "version");
            List<FieldDeterminerVersionEntity> list = filterVersionList.stream().map(d -> (FieldDeterminerVersionEntity) d).collect(Collectors.toList());
            int num = 1;
            for (FieldDeterminerVersionEntity data : list) {
                data.setNum(num++);
                tableList.add(data);
            }
        } catch (Exception e) {
            log.error(">>>>>>查询限定词版本出错：", e);
        }
        return tableList;
    }

    @Override
    public ObjectManageDTO searchOldObject(ObjectVersionEntity objectVersion) {
        ObjectManageDTO objectManageDTO = new ObjectManageDTO();
        try {
            log.info(">>>>>>查询标准历史信息的参数是{}", objectVersion);
            LambdaQueryWrapper<ObjectHisEntity> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(ObjectHisEntity::getObjectId, objectVersion.getObjectId());
            wrapper.eq(ObjectHisEntity::getUpdateTime, objectVersion.getUpdateTime());
            ObjectHisEntity objectPojo = objectHisMapper.selectOne(wrapper);
            if (objectPojo == null) {
                throw new NullPointerException("查询出的数据有误或是参数异常");
            }
            //翻译表状态、表所在数据库的类型、表类型、数据分级
            objectPojo.setObjectStateVo(KeyIntEnum.getValueByKeyAndType(objectPojo.getObjectState(), Common.OBJECT_STATE));
            objectPojo.setStoreTypeVo(KeyIntEnum.getValueByKeyAndType(objectPojo.getStoreType(), Common.STORETYPE));
            objectPojo.setDataTypeVo(KeyIntEnum.getValueByKeyAndType(objectPojo.getDataType(), Common.OBJECT_DATATYPE));
            if (objectPojo.getSecretLevel() != null) {
                objectPojo.setSecretLevelVo(KeyStrEnum.getValueByKeyAndType("1_" + objectPojo.getSecretLevel(), Common.DATASECURITYLEVEL));
            }

            // 拼接对应的数据
            ObjectEntity objectEntity = new ObjectEntity();
            injectObjectEntity(objectEntity, objectPojo);

            //根据objectId 查询所对应的objectField信息
            LambdaQueryWrapper<ObjectFieldHisEntity> wrapperOFS = Wrappers.lambdaQuery();
            wrapperOFS.eq(ObjectFieldHisEntity::getObjectId, objectEntity.getObjectId());
            List<ObjectFieldHisEntity> objectFieldList = objectFieldHisMapper.selectList(wrapperOFS);
            List<ObjectFieldEntity> objectFieldEntities = new ArrayList<>();
            objectFieldList.stream().forEach(data -> {
                ObjectFieldEntity objectField = new ObjectFieldEntity();
                try {
                    BeanUtils.copyProperties(objectField, data);
                } catch (Exception e) {
                    log.error(">>>>>>ObjectFieldHisEntity复制到ObjectFieldEntity失败：", e);
                }
                objectFieldEntities.add(objectField);
            });

            objectManageDTO.setObjectId(String.valueOf(objectEntity.getObjectId()));
            objectManageDTO.setTableId(objectEntity.getTableId());
            objectManageDTO.setObjectPojoTable(objectEntity);
            objectManageDTO.setObjectFieldList(objectFieldEntities);
        } catch (Exception e) {
            log.error(">>>>>>获取对象详细信息报错：", e);
        }
        return objectManageDTO;
    }

    public void injectObjectEntity(ObjectEntity objectEntity, ObjectHisEntity objectHisEntity) {
        // 拼接对应的数据 object 这个表里面存在的数据
        // objectId
        objectEntity.setObjectId(objectHisEntity.getObjectId());
        // 数据名
        objectEntity.setObjectName(objectHisEntity.getObjectName());
        // 真实表名
        objectEntity.setRealTablename(objectHisEntity.getTableName());
        objectEntity.setDataSource(objectHisEntity.getDataSource());
        objectEntity.setTableId(objectHisEntity.getTableId());
        // 存储表状态
        objectEntity.setObjectStateVo(objectHisEntity.getObjectStateVo());
        // 存储方式
        objectEntity.setStoreTypeVo(objectHisEntity.getStoreTypeVo());
        //更新表类型 20200507 majia添加
        objectEntity.setIsActiveTable(objectHisEntity.getIsActiveTable());
        // 源表ID  sourceId 的值 20191118号新增需求
        objectEntity.setSourceId(objectHisEntity.getSourceId());
        // 注释的字段信息
        objectEntity.setObjectMemo(objectHisEntity.getObjectMemo());
        //数据分级
        if (objectHisEntity.getSecretLevel() != null) {
            objectEntity.setSecretLevel(String.valueOf(objectHisEntity.getSecretLevel()));
        }
        if (!StringUtils.isEmpty(objectHisEntity.getSecretLevelVo()) && objectHisEntity.getSecretLevelVo() != null) {
            objectEntity.setSecretLevelCh(objectHisEntity.getSecretLevelVo());
        }
        // 厂商 存储方式 存储的数据源
        List<String> outOobjSourceCodeList = new ArrayList<>();
        List<String> outOobjSourceList = new ArrayList<>();
        // 获取输入和输出的对应关系
        List<InputObjectCreateVO> allInpuObjectList = standardizeOutputObjectMapper.getAllInputObject(objectHisEntity.getTableId());
        for (InputObjectCreateVO inputObjectCreate : allInpuObjectList) {
            Integer outOobjSource = inputObjectCreate.getOutOobjSource();
            outOobjSourceList.add(KeyIntEnum.getValueByKeyAndType(outOobjSource, Common.MANUFACTURER_NAME));
            outOobjSourceCodeList.add(String.valueOf(outOobjSource));
        }
        if (outOobjSourceCodeList.size() >= 1) {
            objectEntity.setOwnerFactoryCode(outOobjSourceCodeList.get(0));
            objectEntity.setOwnerFactory(outOobjSourceList.get(0));
        } else {
            objectEntity.setOwnerFactoryCode("0");
            objectEntity.setOwnerFactory("全部");
        }
        //  根据 codeTextTd的值获取对应的中文翻译
        if (StringUtils.isNotBlank(objectEntity.getDataSource())) {
            LambdaQueryWrapper<FieldCodeValEntity> queryWrapperFC = Wrappers.lambdaQuery();
            queryWrapperFC.like(FieldCodeValEntity::getCodeId, "JZCODE0024");
            queryWrapperFC.eq(FieldCodeValEntity::getValValue, objectEntity.getDataSource());
            FieldCodeValEntity fieldCodeVal = fieldCodeValMapper.selectOne(queryWrapperFC);
            if (fieldCodeVal.getValText() != null) {
                objectEntity.setDataSourceCh("错误协议代码");
            } else {
                objectEntity.setDataSourceCh(fieldCodeVal.getValText());
            }
        }
        //  获取这个tableId在 数据组织,数据来源的分级分类信息。
        ObjectEntity classifyOne = objectMapper.getClassifyByTableid(objectEntity.getTableId());
        if (classifyOne != null) {
            objectEntity.setOrganizationClassify(classifyOne.getOrganizationClassify());
            objectEntity.setSourceClassify(classifyOne.getSourceClassify());
            objectEntity.setClassIds(classifyOne.getClassIds());
            objectEntity.setSourceClassIds(classifyOne.getSourceClassIds());
        } else {
            objectEntity.setOrganizationClassify("未知/未知");
            objectEntity.setSourceClassify("未知/未知");
            objectEntity.setClassIds("");
            objectEntity.setSourceClassIds("");
        }
        if (objectHisEntity.getCreateTime() != null) {
            objectEntity.setCreateTime(objectHisEntity.getCreateTime());
        }
        if (!StringUtils.isEmpty(objectHisEntity.getCreator())) {
            objectEntity.setCreator(objectHisEntity.getCreator());
        }
        if (!StringUtils.isEmpty(objectHisEntity.getUpdater())) {
            objectEntity.setUpdater(objectHisEntity.getUpdater());
        }
    }

    @Override
    public SynlteFieldHisEntity searchOldSynlteField(SynlteFieldVersionEntity synlteFieldVersion) {
        SynlteFieldHisEntity oldSynlteField = new SynlteFieldHisEntity();
        try {
            log.info(">>>>>>查询数据元历史信息");
            //查询数据元历史信息
            LambdaQueryWrapper<SynlteFieldHisEntity> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(SynlteFieldHisEntity::getFieldId, synlteFieldVersion.getFieldId());
            wrapper.eq(SynlteFieldHisEntity::getUpdateTime, synlteFieldVersion.getUpdateTime());
            oldSynlteField = synltefieldHisMapper.selectOne(wrapper);
        } catch (Exception e) {
            log.error(">>>>>>查询数据元历史信息出错：", e);
        }
        return oldSynlteField;
    }

    @Override
    public FieldDeterminerHisEntity searchOldFieldDeterminer(FieldDeterminerVersionEntity fieldDeterminerVersion) {
        FieldDeterminerHisEntity oldFieldDeterminer = new FieldDeterminerHisEntity();
        try {
            log.info(">>>>>>查询限定词历史信息");
            LambdaQueryWrapper<FieldDeterminerHisEntity> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(FieldDeterminerHisEntity::getDeterminerId, fieldDeterminerVersion.getDeterminerId());
            wrapper.eq(FieldDeterminerHisEntity::getModDate, fieldDeterminerVersion.getUpdateTime());
            oldFieldDeterminer = fieldDeterminerHisMapper.selectOne(wrapper);
            oldFieldDeterminer.setDeterminerStateCh(KeyStrEnum.getValueByKeyAndType(oldFieldDeterminer.getDeterminerState(), Common.DETERMINER_ENUM));
            oldFieldDeterminer.setDeterminerTypeCh(KeyStrEnum.getValueByKeyAndType("2_" + oldFieldDeterminer.getDeterminerType(), Common.DETERMINER_ENUM));
        } catch (Exception e) {
            log.error(">>>>>>查询限定词历史信息出错：", e);
        }
        return oldFieldDeterminer;
    }

    @Override
    public FilterListVO searchVersionAndAuthor(String table) {
        FilterListVO filterList = new FilterListVO();
        try {
            if (StringUtils.isBlank(table)) {
                log.error(">>>>>>传递的表名参数为空");
                return filterList;
            }
            log.info("开始查询过滤信息:");
            List<KeyValueVO> versionsList = objectVersionMapper.searchVersionsList(table);
            versionsList.sort((s1, s2) -> Collator.getInstance(java.util.Locale.CHINA).compare(s1.getLabel(), s2.getLabel()));
            List<KeyValueVO> authorList = objectVersionMapper.searchAuthorList(table);
            authorList.sort((s1, s2) -> Collator.getInstance(java.util.Locale.CHINA).compare(s1.getLabel(), s2.getLabel()));
            filterList.setVersionsList(versionsList);
            filterList.setAuthorList(authorList);
        } catch (Exception e) {
            log.error(">>>>>>大版本号和修订人的筛选值查询出错：", e);
        }
        return filterList;
    }

}
