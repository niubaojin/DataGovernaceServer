package com.synway.datastandardmanager.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.synway.common.bean.ServerResponse;
import com.synway.common.exception.SystemException;
import com.synway.datastandardmanager.constants.Common;
import com.synway.datastandardmanager.entity.dto.ObjectManageDTO;
import com.synway.datastandardmanager.entity.pojo.*;
import com.synway.datastandardmanager.entity.vo.*;
import com.synway.datastandardmanager.entity.vo.dataprocess.MetaInfoDetail;
import com.synway.datastandardmanager.entity.vo.dataprocess.PushMetaInfo;
import com.synway.datastandardmanager.entity.vo.dataprocess.StandardFieldJson;
import com.synway.datastandardmanager.entity.vo.warehouse.DataResource;
import com.synway.datastandardmanager.entity.vo.warehouse.DetectedTable;
import com.synway.datastandardmanager.entity.vo.warehouse.FieldInfo;
import com.synway.datastandardmanager.entity.vo.warehouse.ProjectInfo;
import com.synway.datastandardmanager.enums.*;
import com.synway.datastandardmanager.mapper.*;
import com.synway.datastandardmanager.service.DataSetStandardService;
import com.synway.datastandardmanager.util.DateUtil;
import com.synway.datastandardmanager.util.RestTemplateHandle;
import com.synway.datastandardmanager.util.SelectUtil;
import com.synway.datastandardmanager.util.UUIDUtil;
import com.synway.datastandardmanager.valid.Resubmit;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
public class DataSetStandardServiceImpl implements DataSetStandardService {

    @Resource
    private ObjectMapper objectMapper;
    @Resource
    private ObjectFieldMapper objectFieldMapper;
    @Resource
    private ObjectHisMapper objectHisMapper;
    @Resource
    private ObjectFieldHisMapper objectFieldHisMapper;
    @Resource
    private ObjectVersionMapper objectVersionMapper;
    @Resource
    private FieldDeterminerMapper fieldDeterminerMapper;
    @Resource
    private SynlteFieldMapper synlteFieldMapper;
    @Resource
    private EntityElementMapper entityElementMapper;
    @Resource
    private StandardizeObjectMapper standardizeObjectMapper;
    @Resource
    private StandardizeObjectRelationMapper standardizeObjectRelationMapper;
    @Resource
    StandardizeObjectfieldRelMapper standardizeObjectfieldRelMapper;
    @Resource
    private StandardizeOutputObjectMapper standardizeOutputObjectMapper;
    @Resource
    private StandardizeInputObjectMapper standardizeInputObjectMapper;
    @Resource
    private StandardizeInputObjectRelateMapper standardizeInputObjectRelateMapper;
    @Resource
    private FieldCodeMapper fieldCodeMapper;
    @Resource
    private FieldCodeValMapper fieldCodeValMapper;
    @Resource
    private DsmSourceInfoMapper dsmSourceInfoMapper;
    @Resource
    private DgnCommonSettingMapper dgnCommonSettingMapper;
    @Resource
    private DsmStandardTableCreatedMapper dsmStandardTableCreatedMapper;

    @Autowired
    RestTemplateHandle restTemplateHandle;
    @Autowired
    private Environment env;


    @Override
    public List<ObjectFieldEntity> queryObjectFieldListByTableId(String tableId) {
        // 先根据 tableId获取对应的objectId
        try {
            if (StringUtils.isEmpty(tableId)) {
                log.error(">>>>>>tableId为空");
                return new ArrayList<>();
            }
            ObjectEntity object = SelectUtil.getObjectEntityByTableId(objectMapper, tableId);
            if (object == null) {
                return new ArrayList<>();
            }
            //objectFieldList
            LambdaQueryWrapper<ObjectFieldEntity> queryWrapper = Wrappers.lambdaQuery();
            queryWrapper.eq(ObjectFieldEntity::getObjectId, object.getObjectId());
            queryWrapper.orderByAsc(ObjectFieldEntity::getRecno);
            List<ObjectFieldEntity> objectFieldList = objectFieldMapper.selectList(queryWrapper);
            //fieldDeterminerList
            LambdaQueryWrapper<FieldDeterminerEntity> queryWrapperFD = Wrappers.lambdaQuery();
            queryWrapperFD.eq(FieldDeterminerEntity::getDeterminerStateNum, "05");
            List<FieldDeterminerEntity> fieldDeterminerEntities = fieldDeterminerMapper.selectList(queryWrapperFD);
            //entityElementList
            LambdaQueryWrapper<EntityElementEntity> queryWrapperEE = Wrappers.lambdaQuery();
            queryWrapperEE.eq(EntityElementEntity::getCreateMode, 1);
            List<EntityElementEntity> entityElements = entityElementMapper.selectList(queryWrapperEE);
            //objectFieldList（codeid、codetext）
            List<ObjectFieldEntity> codeClassList = synlteFieldMapper.queryCodeClassList();
            Map<String, List<ObjectFieldEntity>> codeClassMap = codeClassList.stream().collect(Collectors.groupingBy(ObjectFieldEntity::getFieldId));

            for (ObjectFieldEntity objectField : objectFieldList) {
                // 限定词名称
                fieldDeterminerEntities.stream().forEach(fd -> {
                    if (fd.getDeterminerId().equalsIgnoreCase(objectField.getDeterminerId())) {
                        objectField.setDeterminerName(fd.getDchinseName());
                    }
                });
                String fieldId = objectField.getFieldId().indexOf("_") != -1 ? objectField.getFieldId().split("_")[1] : objectField.getFieldId();
                List<KeyValueVO> synlteFields = synlteFieldMapper.getGadsjFieldByText(null, null, fieldId);
                if (synlteFields.size() != 0) {
                    KeyValueVO value = synlteFields.get(0);
                    objectField.setSynlteFieldMemo(value.getMemo());
                    objectField.setLabel(value.getLabel());
                }
                // 安全分级中文名称
                if (StringUtils.isNotBlank(objectField.getZdmgdfldm())) {
                    objectField.setSecurityLevelCh(KeyStrEnum.getValueByKeyAndType("2_" + objectField.getZdmgdfldm(), Common.DATASECURITYLEVEL));
                }
                if (StringUtils.isEmpty(objectField.getFieldId()) || objectField.getFieldId().contains("unknown_")) {
                    objectField.setFieldId("");
                }
                // 是否参与MD5运算
                if (objectField.getMd5Index() != null && objectField.getMd5Index() != 0) {
                    objectField.setMd5IndexStatus(true);
                }
                // 字段分类中文名 字段分类代码值 敏感度分类代码值
                if (!StringUtils.isEmpty(objectField.getFieldId())) {
                    //20210913 通过标准字段fieldId字段获取对应的数据要素名称
                    EntityElementEntity entity = entityElements.stream().filter(d -> d.getElementCode().equalsIgnoreCase(objectField.getFieldId())).findFirst().orElse(new EntityElementEntity());
                    if (entity.getElementChname() != null) {
                        objectField.setElementName(entity.getElementChname());
                    }
                    // 获取表字段信息的字段分类信息 synltefield.FIELD_CLASS 这个字段里面
                    List<ObjectFieldEntity> codeList = codeClassMap.get(objectField.getFieldId());
                    if (codeList != null && !codeList.isEmpty()) {
                        ObjectFieldEntity entity1 = codeList.get(0);
                        if (entity1 == null) {
                            continue;
                        }
                        objectField.setCodeText(entity1.getCodeText() != null ? entity1.getCodeText() : "");
                        objectField.setCodeid(entity1.getCodeid() != null ? entity1.getCodeid() : "");
                        objectField.setFieldClassId(entity1.getFieldClassId() != null ? entity1.getFieldClassId() : "");
                        objectField.setFieldClassCh(entity1.getFieldClassCh() != null ? entity1.getFieldClassCh() : "");
                        objectField.setSameWordType(entity1.getSameWordType() != null ? entity1.getSameWordType() : "");
                    }
                }
                if (StringUtils.isNotBlank(objectField.getFieldClassId())) {
                    objectField.setFieldClassCh(SynlteFieldClassEnum.getValueById(objectField.getFieldClassId()));
                }
                if (objectField.getPkRecno() != null && objectField.getPkRecno() != 0) {
                    objectField.setPkRecnoStatus(true);
                }
            }
            return objectFieldList;
        } catch (Exception e) {
            log.error(">>>>>>根据tableId获取字段定义信息报错：", e);
        }
        return new ArrayList<>();
    }

    @Override
    public ObjectEntity queryObjectDetail(String tableId) {
        ObjectEntity objectInfo = new ObjectEntity();
        try {
            objectInfo = SelectUtil.getObjectEntityByTableId(objectMapper, tableId);
            if (objectInfo == null) {
                return new ObjectEntity();
            }
            objectInfo.setStorageTableStatus(KeyIntEnum.getValueByKeyAndType(objectInfo.getObjectState(), Common.OBJECT_STATE));
            objectInfo.setStorageDataMode(KeyIntEnum.getValueByKeyAndType(objectInfo.getStoreType(), Common.STORETYPE));
            objectInfo.setDataTypeVo(KeyIntEnum.getValueByKeyAndType(objectInfo.getDataType(), Common.OBJECT_DATATYPE));
            if (!StringUtils.isEmpty(objectInfo.getDataLevel())) {
                objectInfo.setDataLevelCh(KeyStrEnum.getValueByKeyAndType("1_" + objectInfo.getStoreType(), Common.DATASECURITYLEVEL));
            }
            //数据分级
            if (objectInfo.getDataLevel() != null && objectInfo.getDataLevel().length() == 1) {
                objectInfo.setDataLevel("0" + objectInfo.getDataLevel());
            }
            //根据二级去码表回填一级
            if (objectInfo.getCodeTextTd() == null) {
                log.info(">>>>>>源应用系统名称（DATA_SOUCE）为空");
            } else {
                FieldCodeEntity fieldCodeVal = fieldCodeMapper.selectOneSysName(objectInfo.getCodeTextTd());
                objectInfo.setParentCodeTextId(fieldCodeVal.getCodeId());
            }
            // 获取输入和输出的对应关系
            List<InputObjectCreateVO> inputObjectAll = standardizeOutputObjectMapper.getAllInputObject(tableId);
            // 厂商存储方式 存储的数据源
            if (inputObjectAll.size() > 0) {
                Integer outOobjSource = inputObjectAll.get(0).getOutOobjSource();
                objectInfo.setOwnerFactoryCode(outOobjSource.toString());
                objectInfo.setOwnerFactory(KeyIntEnum.getValueByKeyAndType(outOobjSource, Common.MANUFACTURER_NAME));
            } else {
                objectInfo.setOwnerFactoryCode("0");
                objectInfo.setOwnerFactory("全部");
            }
            // TODO 存储数据源信息，根据 codeTextTd的值获取对应的中文翻译
            if (StringUtils.isNotEmpty(objectInfo.getCodeTextTd())) {
                FieldCodeValEntity fieldCodeVal = getFieldCodeVal(objectInfo.getCodeTextTd());
                if (fieldCodeVal.getValText() != null) {
                    objectInfo.setCodeTextCh(fieldCodeVal.getValText());
                } else {
                    objectInfo.setCodeTextCh("错误协议代码");
                }
            }
            // 获取这个tableid在数据组织、数据来源的分级分类信息。
            ObjectEntity classifyOne = objectMapper.getClassifyByTableid(tableId);
            if (classifyOne != null) {
                //处理组织分类中文信息
                String orgClassify = classifyOne.getOrganizationClassify();
                if (StringUtils.isNotBlank(orgClassify) && orgClassify.endsWith("/")) {
                    //处理了只有1级分类时(业务要素索引库和其它)
                    objectInfo.setOrganizationClassify(orgClassify.substring(0, orgClassify.length() - 2));
                } else {
                    if (orgClassify.contains("原始库")) {
                        //如果是原始库，则直接赋值3级
                        objectInfo.setOrganizationClassify(orgClassify);
                    } else {
                        //非原始库，只赋值1级和2级
                        objectInfo.setOrganizationClassify(orgClassify.split("/")[1] + "/" + orgClassify.split("/")[2]);
                    }
                }
                //处理来源分类中文信息
                objectInfo.setSourceClassify(classifyOne.getSourceClassify());

                //回填组织分类和来源分类的id值
                String classIds = classifyOne.getClassIds();
                if (classIds.contains(",")) {
                    List<String> finalClassIdList = new ArrayList<>();
                    List<String> classIdList = Arrays.asList(classIds.split(","));
                    String organizationClassify = Common.DATA_ORGANIZATION_CODE;
                    for (int i = 0; i < classIdList.size(); i++) {
                        if (i < classIdList.size() - 1) {
                            //除原始库外，其它组织分类primary和first存储的一样，所以跳出
                            if (classIdList.get(i).equalsIgnoreCase(classIdList.get(i + 1))) {
                                continue;
                            }
                        }
                        organizationClassify = organizationClassify + classIdList.get(i);
                        finalClassIdList.add(organizationClassify);
                    }
                    //拼接后的classId
                    String realClassIds = StringUtils.join(finalClassIdList, ",");
                    objectInfo.setClassIds(realClassIds);
                } else {
                    objectInfo.setClassIds(Common.DATA_ORGANIZATION_CODE + classIds);
                }

                //赋值来源分类
                String sourceClassIds = classifyOne.getSourceClassIds();
                if (sourceClassIds.contains(",")) {
                    List<String> finalSourceIdList = new ArrayList<>();
                    String sourceClassCode = Common.DATA_SOURCE_CODE;
                    List<String> sourceIdList = Arrays.asList(sourceClassIds.split(","));
                    for (String data : sourceIdList) {
                        sourceClassCode = sourceClassCode + data;
                        finalSourceIdList.add(sourceClassCode);
                    }
                    //拼接后的classId
                    objectInfo.setSourceClassIds(StringUtils.join(finalSourceIdList, ","));
                } else {
                    objectInfo.setClassIds(Common.DATA_SOURCE_CODE + sourceClassIds);
                }
            } else {
                objectInfo.setOrganizationClassify("未知/未知");
                objectInfo.setSourceClassify("未知/未知");
            }
        } catch (Exception e) {
            log.error(">>>>>>根据tableId获取object表详细信息报错：", e);
        }
        return objectInfo;
    }

    public FieldCodeValEntity getFieldCodeVal(String valValue) {
        LambdaQueryWrapper<FieldCodeValEntity> queryWrapperFC = Wrappers.lambdaQuery();
        queryWrapperFC.like(FieldCodeValEntity::getCodeId, "JZCODE0024");
        queryWrapperFC.eq(FieldCodeValEntity::getValValue, valValue);
        return fieldCodeValMapper.selectOne(queryWrapperFC);
    }

    @Override
    public String deleteObjectField(Long objectId, String fieldName) {
        try {
            log.info(String.format(">>>>>>开始删除objectId：%s的字段columnName：%s", objectId, fieldName));
            synchronized (this) {
                if (StringUtils.isEmpty(fieldName)) {
                    log.error("columnName值为空，删除失败");
                    return Common.DEL_FAIL;
                }
                LambdaQueryWrapper<ObjectFieldEntity> queryWrapper = Wrappers.lambdaQuery();
                queryWrapper.eq(ObjectFieldEntity::getObjectId, objectId);
                queryWrapper.eq(ObjectFieldEntity::getColumnName, fieldName);
                ObjectFieldEntity objectField = objectFieldMapper.selectOne(queryWrapper);
                if (objectField.getColumnName() == null) {
                    log.error("该字段在表中不存在，删除失败");
                    return Common.DEL_FAIL;
                }
                // 获取10位数字的uuid
                String uuidStr = UUIDUtil.getUUID().substring(0, 10);
                LambdaUpdateWrapper<ObjectFieldEntity> updateWrapper = Wrappers.lambdaUpdate();
                updateWrapper.set(ObjectFieldEntity::getDeleted, 1).set(ObjectFieldEntity::getFieldId, objectField.getFieldId() + "-" + uuidStr).set(ObjectFieldEntity::getColumnName, objectField.getColumnName() + "-" + uuidStr).set(ObjectFieldEntity::getRecno, -1).set(ObjectFieldEntity::getStandardRecno, -1).eq(ObjectFieldEntity::getObjectId, objectId).eq(ObjectFieldEntity::getColumnName, fieldName).eq(ObjectFieldEntity::getDeleted, 0);
                int updateCount = objectFieldMapper.update(updateWrapper);
                if (updateCount > 0) {
                    log.info(">>>>>>该字段在表中删除成功");
                    return Common.DEL_SUCCESS;
                } else {
                    log.error(">>>>>>该字段在表中删除失败");
                    return Common.DEL_FAIL;
                }
            }
        } catch (Exception e) {
            log.error(">>>>>>该字段在表中删除失败：", e);
            return Common.DEL_FAIL;
        }
    }

    @Override
    public List<SourceRelationShipVO> getSourceRelationShip(String tableId) {
        List<SourceRelationShipVO> sourceRelationShipList = new ArrayList<>();
        try {
            ObjectEntity object = SelectUtil.getObjectEntityByTableId(objectMapper, tableId);
            if (object == null) {
                return sourceRelationShipList;
            }
            String objectStateType = KeyIntEnum.getValueByKeyAndType(object.getObjectState(), Common.OBJECT_STATE);
            // 获取输入和输出的对应关系
            List<InputObjectCreateVO> allInpuObjectList = standardizeInputObjectRelateMapper.getAllInputObjectRelation(tableId);
            for (InputObjectCreateVO inputObjectCreate : allInpuObjectList) {
                SourceRelationShipVO sourceRelationShip = new SourceRelationShipVO();
                sourceRelationShip.setDataSourceName(inputObjectCreate.getInputObjChiName());
                sourceRelationShip.setRealTableName(inputObjectCreate.getSourceTableName());
                sourceRelationShip.setSourceFirm(KeyIntEnum.getValueByKeyAndType(inputObjectCreate.getInputIobjSource(), Common.MANUFACTURER_NAME));
                sourceRelationShip.setSourceProtocol(inputObjectCreate.getInputObjEngName());
                sourceRelationShip.setSourceId(inputObjectCreate.getInputObjEngName());
                sourceRelationShip.setTableId(inputObjectCreate.getTableId());
                sourceRelationShip.setDataId(inputObjectCreate.getDataId());
                sourceRelationShip.setCenterId(inputObjectCreate.getCenterId());
                sourceRelationShip.setDataName(inputObjectCreate.getInputObjChiName());
                // 获取来源系统英文名
                FieldCodeValEntity fieldCodeVal = getFieldCodeVal(String.valueOf(inputObjectCreate.getInputSysId()));
                sourceRelationShip.setSourceProtocolCh(fieldCodeVal.getValText());
                sourceRelationShip.setSourceSystem(String.valueOf(inputObjectCreate.getInputSysId()));
                sourceRelationShip.setStorageTableStatus(objectStateType);
                //回填数据中心中文和数据源中文
                LambdaQueryWrapper<DsmSourceInfoEntity> queryWrapperSI = Wrappers.lambdaQuery();
                queryWrapperSI.eq(DsmSourceInfoEntity::getSourceProtocol, sourceRelationShip.getSourceProtocol());
                queryWrapperSI.eq(DsmSourceInfoEntity::getTableName, sourceRelationShip.getRealTableName());
                queryWrapperSI.eq(DsmSourceInfoEntity::getSourceSystem, sourceRelationShip.getSourceSystem());
                DsmSourceInfoEntity sourceInfo = dsmSourceInfoMapper.selectOne(queryWrapperSI);
                DataResource dataResource = restTemplateHandle.getResourceById(sourceRelationShip.getDataId());
                sourceRelationShip.setCenterId(dataResource.getCenterId());
                sourceRelationShip.setCenterName(dataResource.getCenterName());
                sourceRelationShip.setProject(sourceInfo == null ? "" : sourceInfo.getProjectName());
                sourceRelationShip.setDataIdCh(dataResource.getResName());
                sourceRelationShip.setResType(dataResource.getResType());
                sourceRelationShip.setResName(dataResource.getResName());
                sourceRelationShipList.add(sourceRelationShip);
            }
        } catch (Exception e) {
            log.error(">>>>>>获取来源关系报错：", e);
        }
        return sourceRelationShipList;
    }

    // mainValue '1':组织分类 '2':来源分类   '3'：资源分类
    @Override
    public List<KeyValueVO> getFirstClassModeByMain(String mainValue) {
        List<KeyValueVO> keyValueVOList = new ArrayList<>();
        try {
            if (mainValue.equalsIgnoreCase("1") || mainValue.equalsIgnoreCase("2") || mainValue.equalsIgnoreCase("3")) {
                keyValueVOList = objectMapper.getFirstClassModeByMain(mainValue);
                log.info(">>>>>>返回的结果为：" + JSONObject.toJSONString(keyValueVOList));
            }
        } catch (Exception e) {
            log.error(">>>>>>根据大类的id号获取一级分类信息出错：", e);
        }
        return keyValueVOList;
    }

    @Override
    public List<KeyValueVO> getSecondaryClassModeByFirst(String mainValue, String firstClassValue) {
        List<KeyValueVO> keyValueVOList = new ArrayList<>();
        try {
            if (mainValue.equalsIgnoreCase("1") || mainValue.equalsIgnoreCase("2") || mainValue.equalsIgnoreCase("3")) {
                keyValueVOList = objectMapper.getSecondaryClassModeByFirst(mainValue, firstClassValue);
                keyValueVOList.add(new KeyValueVO("", "全部分类"));
            }
        } catch (Exception e) {
            log.error(">>>>>>获取二级分类信息出错：", e);
        }
        return keyValueVOList;
    }

    @Override
    public String deleteSourceRelation(List<SourceRelationShipVO> delSourceRelation, String outputDataId) {
        try {
            log.info(String.format(">>>>>>开始删除表ID为：%s的来源关系:%s", outputDataId, JSONObject.toJSONString(delSourceRelation)));
            // 根据outputDataId 获取 输出协议GUID
            String outputGuid = standardizeOutputObjectMapper.getOutputGuidByTableId(outputDataId);
            if (outputGuid == null) {
                log.error(String.format(">>>>>>表：%s，没有找到对应的GUID", outputDataId));
                return Common.DEL_FAIL;
            }
            for (SourceRelationShipVO oneSourceRelationShip : delSourceRelation) {
                // 来源系统
                String inputDataId = oneSourceRelationShip.getSourceSystem();
                // 来源数据协议
                String inputSourceCode = oneSourceRelationShip.getSourceProtocol();
                // 来源厂商  为 0 1 2 等数字
                int inputSourceFirm = KeyIntEnum.getKeyByNameAndType(oneSourceRelationShip.getSourceFirm(), Common.MANUFACTURER_NAME);
                log.info(String.format("inputDataId：%s\ninputSourceCode：%s\ninputSourceFirm：%d", inputDataId, inputSourceCode, inputSourceFirm));
                // 从探查跳转数据定义保存报错问题修复：inputDataId、inputSourceCode参数交换
                String inputGuid = standardizeInputObjectMapper.getInputGuidById(inputSourceCode, inputDataId, inputSourceFirm);
                log.info(String.format(">>>>>>开始删除输出GUID为：%s，输入GUID为：%s在STANDARDIZE_INPUTOBJECTRELATE表中关系", outputGuid, inputGuid));
                // 删除STANDARDIZE_INPUTOBJECTRELATE 数据
                LambdaQueryWrapper<StandardizeInputObjectRelateEntity> queryWrapper = Wrappers.lambdaQuery();
                queryWrapper.eq(StandardizeInputObjectRelateEntity::getIobjGuid, inputGuid);
                queryWrapper.eq(StandardizeInputObjectRelateEntity::getOobjGuid, outputGuid);
                standardizeInputObjectRelateMapper.delete(queryWrapper);
                // 删除standardize_inputObject 数据
                LambdaQueryWrapper<StandardizeInputObjectEntity> queryWrapperSIO = Wrappers.lambdaQuery();
                queryWrapperSIO.eq(StandardizeInputObjectEntity::getIobjGuid, inputGuid);
                standardizeInputObjectMapper.deleteById(queryWrapperSIO);
            }
            return Common.DEL_SUCCESS;
        } catch (Exception e) {
            log.error(">>>>>>删除来源关系报错：", e);
            return Common.DEL_FAIL;
        }
    }

    @Override
    public List<SelectFieldVO> createAddColumnModel(String type, String condition) {
        List<SelectFieldVO> selectFieldVOList = new ArrayList<>();
        try {
            log.info(String.format(">>>>>>查询的字段信息为,type：%s condition：%s", type, condition));
            LambdaQueryWrapper<SynlteFieldEntity> queryWrapper = Wrappers.lambdaQuery();
            queryWrapper.isNotNull(SynlteFieldEntity::getFieldId);
            queryWrapper.isNotNull(SynlteFieldEntity::getGadsjFieldId);
            queryWrapper.eq(SynlteFieldEntity::getStatusNum, "05");
            queryWrapper.ne(SynlteFieldEntity::getFieldStandard, "99");
            if (type.equalsIgnoreCase("fieldId")) {
                queryWrapper.and(wrapper -> wrapper.eq(SynlteFieldEntity::getFieldId, condition).or().eq(SynlteFieldEntity::getFieldChineseName, condition));
                queryWrapper.like(SynlteFieldEntity::getFieldId, condition);
            } else if (type.equalsIgnoreCase("columnName")) {
                queryWrapper.and(wrapper -> wrapper.eq(SynlteFieldEntity::getFieldName, condition).or().eq(SynlteFieldEntity::getFieldChineseName, condition));
                queryWrapper.like(SynlteFieldEntity::getFieldId, condition);
            } else {
                log.error(">>>>>>不支持查询字段条件类型：" + type);
                return selectFieldVOList;
            }
            List<SynlteFieldEntity> synlteFieldEntities = synlteFieldMapper.selectList(queryWrapper);
            synlteFieldEntities.stream().filter(d -> d.getFieldId().substring(0, 3).equalsIgnoreCase("WBZ")).collect(Collectors.toList());
            for (SynlteFieldEntity synlteField : synlteFieldEntities) {
                SelectFieldVO selectFieldVO = new SelectFieldVO();
                if (type.equalsIgnoreCase("fieldId")) {
                    selectFieldVO.setId(synlteField.getFieldId());
                    selectFieldVO.setValue(synlteField.getFieldId());
                    selectFieldVO.setFieldId(synlteField.getFieldId());
                    selectFieldVO.setName(synlteField.getFieldChineseName());
                }
                if (type.equalsIgnoreCase("columnName")) {
                    selectFieldVO.setId(synlteField.getFieldName());
                    selectFieldVO.setValue(synlteField.getFieldName());
                    selectFieldVO.setFieldId(synlteField.getFieldId());
                    selectFieldVO.setName(synlteField.getFieldChineseName());
                }
                selectFieldVOList.add(selectFieldVO);
            }
        } catch (Exception e) {
            log.error(">>>>>>查询的字段信息出错：", e);
        }
        return selectFieldVOList;
    }

    @Override
    public SynlteFieldEntity getAddColumnByInput(String type, String inputValue) {
        SynlteFieldEntity synlteField = new SynlteFieldEntity();
        try {
            log.info(">>>>>>查询的字段信息为,type：%s inputValue：%s", type, inputValue);
            LambdaQueryWrapper<SynlteFieldEntity> queryWrapper = Wrappers.lambdaQuery();
            if (type.equalsIgnoreCase("fieldId")) {
                queryWrapper.eq(SynlteFieldEntity::getFieldId, inputValue.trim());
            } else if (type.equalsIgnoreCase("columnName")) {
                queryWrapper.eq(SynlteFieldEntity::getFieldName, inputValue.trim());
            } else {
                log.error(String.format(">>>>>>传入的type参数值：%s错误", type));
                return synlteField;
            }
            synlteField = synlteFieldMapper.selectOne(queryWrapper);
            synlteField.setFieldtypeName(SynlteFieldTypeEnum.getSynlteFieldType(synlteField.getFieldType()));
            // 20210601 需要获取到分类信息
            List<ObjectFieldEntity> codeClassList = synlteFieldMapper.queryCodeClassList();
            Map<String, List<ObjectFieldEntity>> codeClassMap = codeClassList.stream().collect(Collectors.groupingBy(ObjectFieldEntity::getFieldId));
            for (String key : codeClassMap.keySet()) {
                if (key.equalsIgnoreCase(synlteField.getFieldId())) {
                    List<ObjectFieldEntity> fieldEntities = codeClassMap.get(key);
                    ObjectFieldEntity objectField = fieldEntities.get(0);
                    synlteField.setFieldClass(objectField.getFieldClassId());
                    synlteField.setFieldClassCh(objectField.getFieldClassCh());
                }
            }
        } catch (Exception e) {
            log.error(">>>>>>获取Synltefield表中指定的数据报错：", e);
        }
        return synlteField;
    }

    @Override
    public void checkTableIdSourceIdIsExists(ObjectManageDTO objectManageDTO, boolean switchFlag) {
        try {
            // 新增标准协议，存在正在审批的情况，还需要从这个接口查询数据
            if (StringUtils.isEmpty(objectManageDTO.getTableId()) || StringUtils.isEmpty(objectManageDTO.getObjectPojoTable().getTableId())) {
                throw new Exception("tableId为空，不能保存该标准表信息");
            }
            ObjectEntity objectEntity = SelectUtil.getObjectEntityByTableId(objectMapper, objectManageDTO.getTableId());
            if (StringUtils.isEmpty(objectManageDTO.getObjectId())) {
                if (objectEntity.getObjectId() != null) {
                    // 如果tableid重复，则tableid流水号自增1
                    String newTableId = getNewTableId(objectManageDTO.getTableId());
                    objectManageDTO.setTableId(newTableId);
                    objectManageDTO.getObjectPojoTable().setTableId(newTableId);
                    checkTableIdSourceIdIsExists(objectManageDTO, switchFlag);
                }
            } else {
                // 编辑标准表
                if (objectEntity.getObjectId() != null) {
                    if (!objectManageDTO.getObjectId().equalsIgnoreCase(objectEntity.getObjectId().toString())) {
                        throw new Exception("该tableId已经被使用，请更换tableId值");
                    }
                }
            }
        } catch (Exception e) {
            log.error(">>>>>>检查tableId是否为唯一出错：", e);
        }
    }

    @Override
    @Transactional
    public boolean saveResourceFieldRelation(ObjectManageDTO objectManageDTO) {
        // 标准表数据信息
        try {
            ObjectEntity objectEntity = objectManageDTO.getObjectPojoTable();
            ObjectEntity objectEntityOld = SelectUtil.getObjectEntityByTableId(objectMapper, objectEntity.getTableId());
            // 如果有objectid，检查这个对应的编辑时间是否大于等于数据库中的时间，不是，说明是旧数据，不能编辑
            if (objectEntity.getObjectId() != null && objectEntity.getUpdateTime() != null && objectEntityOld.getUpdateTime() != null) {
                if (objectEntityOld.getUpdateTime().compareTo(objectEntity.getUpdateTime()) > 0) {
                    String updateTime = DateUtil.formatDateTime(objectEntityOld.getUpdateTime(), DateUtil.DEFAULT_PATTERN_DATETIME);
                    throw new Exception(String.format("标准协议[%s]已经在%s时被修改，本次修改失败，请刷新数据", objectEntity.getTableId(), updateTime));
                }
            }
            // 保存object、standardize_object、standardize_outputobject、object_version相关信息
            if (saveObjectInfo(objectManageDTO) == false) return false;

            // 保存objectfield相关信息
            saveObjectFieldInfo(objectManageDTO, objectEntity, objectEntityOld.getObjectId());

            // 保存standardize_object_relation、standardize_objectfield_rel相关信息（仅限于原始汇聚类数据库才保存数据集对标内容）
            saveObjectRelation(objectManageDTO, objectEntity, objectEntityOld);

            // 保存sourceInfo相关信息（来源关系的数据，将原始来源关系存入sourceInfo表中）
            saveSourceInfo(objectManageDTO);

            // 保存standardize_object、standardize_inputobject、standardize_outputobject、standardize_inputobjectrelate相关信息
            saveStandardizeInfo(objectManageDTO, objectEntity);

            return true;
        } catch (Exception e) {
            log.error(">>>>>>保存标准信息报错：", e);
            return false;
        }
    }

    public void saveStandardizeInfo(ObjectManageDTO objectManageDTO, ObjectEntity objectEntity) {
        List<SourceRelationShipVO> sourceRelationShipListPage = objectManageDTO.getSourceRelationShipList();
        List<String> souceSystemProtocolList = sourceRelationShipListPage.stream().filter(e -> (StringUtils.isNotEmpty(e.getSourceSystem()) && StringUtils.isNotEmpty(e.getSourceProtocol()))).distinct().map(e -> (e.getSourceSystem().toUpperCase() + "&&" + e.getSourceProtocol())).collect(toList());
        if (souceSystemProtocolList.size() != 0) {
            // 1：先获取数据库中已经存在的来源关系
            List<SourceRelationShipVO> sourceRelationShipOld = getSourceRelationShip(objectEntity.getTableId());
            // 2：判断哪些是不存在的
            List<SourceRelationShipVO> delSourceRelation = new ArrayList<>();
            for (SourceRelationShipVO sourceRelationShip : sourceRelationShipOld) {
                String name = sourceRelationShip.getSourceSystem().toUpperCase() + "&&" + sourceRelationShip.getSourceProtocol();
                if (!souceSystemProtocolList.contains(name)) {
                    delSourceRelation.add(sourceRelationShip);
                }
            }
            // 3：删除需要删除的来源关系
            if (!delSourceRelation.isEmpty()) {
                deleteSourceRelation(delSourceRelation, objectEntity.getTableId());
            }
            // 4：添加新的来源关系
            List<String> souceSystemProtocolListOld = new ArrayList<>();
            if (sourceRelationShipOld != null) {
                souceSystemProtocolListOld = sourceRelationShipOld.stream().filter(e -> (StringUtils.isNotEmpty(e.getSourceSystem()) && StringUtils.isNotEmpty(e.getSourceProtocol()))).distinct().map(e -> (e.getSourceSystem().toUpperCase() + "&&" + e.getSourceProtocol())).collect(toList());
            }
            for (SourceRelationShipVO pageSource : sourceRelationShipListPage) {
                String name = pageSource.getSourceSystem().toUpperCase() + "&&" + pageSource.getSourceProtocol();
                if (!souceSystemProtocolListOld.contains(name)) {
                    pageSource.setRealTableName(objectEntity.getRealTablename());
                    if (pageSource.getAddType().equalsIgnoreCase(SourceRelationShipVO.ORGANIZATIONAL)) {
                        addSourceRelationByTableName(pageSource.getRealTableName(), pageSource.getSourceId(), pageSource.getSourceSystem(), pageSource.getSourceFirm(), StringUtils.isNotBlank(pageSource.getTableNameCN()) ? pageSource.getTableNameCN() : pageSource.getDataName(), pageSource.getResType(), objectEntity);
                    } else {
                        // 20200603：getRealTableName 换成中文名 getDataSourceName
                        addSourceRelationByEtlMessage(pageSource.getSourceId(), pageSource.getSourceSystem(), pageSource.getSourceFirm(), pageSource.getRealTableName(), objectEntity.getTableId(), pageSource.getTableId(), pageSource.getDataId(), pageSource.getCenterId(), StringUtils.isNotBlank(pageSource.getTableNameCN()) ? pageSource.getTableNameCN() : pageSource.getDataName());
                    }
                }
            }
        }
    }

    public void saveSourceInfo(ObjectManageDTO objectManageDTO) {
        List<SourceRelationShipVO> sourceRelationShipListPage = objectManageDTO.getSourceRelationShipList();
        if (sourceRelationShipListPage != null && sourceRelationShipListPage.size() != 0) {
            SourceRelationShipVO realSourceRelationShip = sourceRelationShipListPage.get(0);
            LambdaQueryWrapper<DsmSourceInfoEntity> queryWrapper = Wrappers.lambdaQuery();
            queryWrapper.eq(DsmSourceInfoEntity::getSourceProtocol, realSourceRelationShip.getSourceProtocol());
            queryWrapper.eq(DsmSourceInfoEntity::getTableName, realSourceRelationShip.getDataSourceName());
            queryWrapper.eq(DsmSourceInfoEntity::getDataId, realSourceRelationShip.getResourceId());
            dsmSourceInfoMapper.selectCount(queryWrapper);
            if (dsmSourceInfoMapper.selectCount(queryWrapper) == 0) {
                DsmSourceInfoEntity sourceInfo = new DsmSourceInfoEntity();
                sourceInfo.setSourceProtocol(realSourceRelationShip.getSourceProtocol());
                sourceInfo.setTableName(realSourceRelationShip.getRealTableName());
                sourceInfo.setSourceSystem(realSourceRelationShip.getSourceSystem());
                sourceInfo.setSourceFirm(realSourceRelationShip.getSourceFirm());
                sourceInfo.setDataName(realSourceRelationShip.getDataName());
                sourceInfo.setDataId(realSourceRelationShip.getResourceId());
                sourceInfo.setProjectName(realSourceRelationShip.getProject());
                sourceInfo.setCenterName(realSourceRelationShip.getCenterName());
                sourceInfo.setCenterId(realSourceRelationShip.getCenterId());
                sourceInfo.setCreateTime(new Date());
                sourceInfo.setUpdateTime(new Date());
                dsmSourceInfoMapper.insert(sourceInfo);
            }
        }
    }

    /**
     * 从数据仓库中获取
     *
     * @param sourceProtocol 表id JZ_RESOURCE11E4312
     * @param sourceSystem   来源系统  3G分光这些的代码值
     * @param sourceFirm     来源厂商中文名  全部/三汇这些
     * @param tableName      表名 表英文名（中文名这些）
     * @param tableId        输出协议的表Id
     * @return
     */
    public void addSourceRelationByEtlMessage(String sourceProtocol, String sourceSystem, String sourceFirm, String tableName, String tableId, String dataCenterTableId, String dataCenterDataId, String centerId, String objMemo) {
        try {
            // 先判断来源厂商的数字转码是否正常，如果正常，继续
            int sourceFirmNum = KeyIntEnum.getKeyByNameAndType(sourceFirm, Common.MANUFACTURER_NAME);
            if (sourceFirmNum == 400) {
                log.error(String.format(">>>>>>来源厂商：%s的中文名称错误，没有找到正确的厂商", sourceFirm));
                return;
            }
            // 先在standardize_inputobject表中查找这个来源协议是否存在，如果存在，直接添加来源关系，没有先创建
            String objGuid = standardizeObjectMapper.getObjGuidByTreeParam(sourceProtocol, sourceSystem, sourceFirmNum);
            if (StringUtils.isEmpty(objGuid)) {
                // 在里面插入来源信息 STANDARDIZE_OBJECT
                StandardizeObjectEntity entity = injectStandardizeObject(sourceSystem, sourceProtocol, tableName, sourceFirmNum, dataCenterDataId, dataCenterTableId, centerId, objMemo);
                standardizeObjectMapper.insert(entity);
            }
            String inputObjGuid = standardizeInputObjectMapper.getInputGuidById(sourceProtocol, sourceSystem, sourceFirmNum);
            String objGuidNew = standardizeObjectMapper.getObjGuidByTreeParam(sourceProtocol, sourceSystem, sourceFirmNum);
            if (StringUtils.isEmpty(inputObjGuid)) {
                //不存在，则插入一条数据
                StandardizeInputObjectEntity inputObject = new StandardizeInputObjectEntity();
                inputObjGuid = objGuidNew;
                inputObject.setObjGuid(objGuidNew);
                inputObject.setIobjType(0);
                inputObject.setIobjStatus(1);
                inputObject.setIobjSource(sourceFirmNum);
                standardizeInputObjectMapper.insert(inputObject);
            }
            // 在关系表中，判断 这个来源关系是否存在，如果存在，不用管，如果不存在，则添加对应的值
            int numCount = standardizeInputObjectRelateMapper.getStdInputObjRelCount(inputObjGuid, tableId);
            if (numCount == 0) {
                String oobjGuid = standardizeOutputObjectMapper.getOObjGuidByTableId(tableId);
                if (StringUtils.isEmpty(oobjGuid)) {
                    // 在数据库中插入一条数据并重新获取该值
                    StandardizeObjectEntity entity = injectStandardizeObject("144", tableId, tableName, 0, dataCenterDataId, dataCenterTableId, centerId, objMemo);
                    standardizeObjectMapper.insert(entity);
                    // 获取最新的objectid值，获取输出协议的 objectGuid
                    oobjGuid = standardizeOutputObjectMapper.getOObjGuidByTableId(tableId);
                    // 获取object表里面的objt_guid之后在STANDARDIZE_outputOBJECT插入到数据中
                    String objGuidInsert = standardizeObjectMapper.getOutputGuidNotInInput(tableId);
                    standardizeOutputObjectMapper.insert(injectStdOutputObj(objGuidInsert, 1, 1, 0));
                }
                StandardizeInputObjectRelateEntity relateEntity = new StandardizeInputObjectRelateEntity();
                relateEntity.setIobjGuid(inputObjGuid);
                relateEntity.setOobjGuid(oobjGuid);
                relateEntity.setIorMemo("");
                relateEntity.setIorStatus(1);
                relateEntity.setIorSource(sourceFirmNum);
                standardizeInputObjectRelateMapper.insert(relateEntity);
            }
        } catch (Exception e) {
            log.error(">>>>>>添加新的来源关系报错：", e);
        }
    }

    public StandardizeOutputObjectEntity injectStdOutputObj(String objGuid, Integer oobjType, Integer oobjStatus, Integer oobjSource) {
        StandardizeOutputObjectEntity outputObject = new StandardizeOutputObjectEntity();
        outputObject.setObjGuid(objGuid);
        String xml = "<ReserveName><ResourceNameInFile></ResourceNameInFile><ResourceNameNoPrefixInFile></ResourceNameNoPrefixInFile><ResourceNameInPath></ResourceNameInPath></ReserveName>";
        outputObject.setOobjReserveNameXml(xml);
        outputObject.setOobjType(oobjType);
        outputObject.setOobjStatus(oobjStatus);
        outputObject.setOobjSource(oobjSource);
        return outputObject;
    }

    public StandardizeObjectEntity injectStandardizeObject(String sysId, String objEngName, String objChiName, Integer sysSource, String dataId, String TableId, String centerId, String objMemo) {
        StandardizeObjectEntity entity = new StandardizeObjectEntity();
        entity.setSysId(sysId);
        entity.setObjEngName(objEngName);
        entity.setObjChiName(objChiName);
        entity.setSysSource(sysSource);
        entity.setDataId(dataId);
        entity.setTableId(TableId);
        entity.setCenterId(centerId);
        entity.setObjMemo(objMemo);
        return entity;
    }

    /**
     * 添加新的数据来源关系 当从organizational中获取时，先在标准表中找到这个表名对应的相关信息，然后插入到数据库中
     * 如果是从 database中获取，从数据仓库接口获取对应的数据
     *
     * @param tableName 表名
     * @return
     */
    public void addSourceRelationByTableName(String tableName, String outputTableId, String sourceSystem, String sourceFirm, String objMemo, String dataId, ObjectEntity objectPojoTable) {
        try {
            int sourceFirmNum = KeyIntEnum.getKeyByNameAndType(sourceFirm, Common.MANUFACTURER_NAME);
            if (sourceFirmNum == 400) {
                log.error(String.format(">>>>>>来源厂商：%s的中文名称错误，没有找到正确的厂商", sourceFirm));
                return;
            }
            // 表名可以是中文表名和英文名，先根据表名找到对应的数据
            InputObjectCreateVO inputObjectCreate = standardizeInputObjectMapper.getSourceRelationByTableName(objectPojoTable.getRealTablename());
            if (inputObjectCreate.inputSysId == null || inputObjectCreate.inputIobjSource == null || StringUtils.isEmpty(inputObjectCreate.objGuid)) {
                // 没有查到来源信息 表示输入的表名错误,则将信息插入到对应的数据中，先根据tablename获取分类表中的tableId
                // 先插入 standardObject表中 然后再往 input的表中插入数据
                String objGuid = standardizeObjectMapper.getObjGuidByTreeParam(outputTableId, sourceSystem, sourceFirmNum);
                String objGuidNew = UUIDUtil.getUUID();
                if (StringUtils.isEmpty(objGuid)) {
                    StandardizeObjectEntity entity = injectStandardizeObject(sourceSystem, outputTableId, tableName, sourceFirmNum, dataId, "", "", objMemo);
                    entity.setObjGuid(objGuidNew);
                    standardizeObjectMapper.insert(entity);
                }
                // 判断STANDARDIZE.INPUTOBJECT表是否存在，存在不用管，不存在插入
                String inputObjGuid = standardizeInputObjectMapper.getInputGuidById(outputTableId, sourceSystem, sourceFirmNum);
                if (StringUtils.isEmpty(inputObjGuid)) {
                    StandardizeInputObjectEntity inputObject = new StandardizeInputObjectEntity();
                    objGuid = StringUtils.isEmpty(objGuid) ? objGuidNew : objGuid;
                    inputObject.setObjGuid(objGuid);
                    inputObject.setIobjType(0);
                    inputObject.setIobjStatus(1);
                    inputObject.setIobjSource(sourceFirmNum);
                    standardizeInputObjectMapper.insert(inputObject);
                }
            }
            // 查询正确，开始将数据插入到来源关系表中
            String outputGuid = standardizeOutputObjectMapper.getOObjGuidByTableId(objectPojoTable.getTableId());
            if (StringUtils.isEmpty(outputGuid)) {
                log.error(">>>>>>没有在standardize_outputobject表中找到" + objectPojoTable.getTableId() + "的相关信息");
                //输出的协议表没有 先在object的表中插入数据
                StandardizeObjectEntity entity = injectStandardizeObject("144", objectPojoTable.getTableId(), tableName, 0, dataId, "", "", objMemo);
                standardizeObjectMapper.insert(entity);

                // 获取最新的objectid值
                String objGuidInsert = standardizeObjectMapper.getOutputGuidNotInInput(objectPojoTable.getTableId());
                // 获取object表里面的objt_guid之后在STANDARDIZE_outputOBJECT插入到数据中
                standardizeOutputObjectMapper.insert(injectStdOutputObj(objGuidInsert, 1, 1, 0));
            }
            outputGuid = standardizeOutputObjectMapper.getOObjGuidByTableId(objectPojoTable.getTableId());
            // 需要先判断 在 输入阶段-对象（协议）关系表中需要插入的数据是否已经存在，如果已经存在
            // 如果存在 判断是否是状态被改成禁用 ，如果是，将状态改成启用，如果不是，插入该条数据
            // 样例数据 {"USEDCOUNT":1,"DISABLEUSEDCOUNT":0}
            inputObjectCreate = standardizeInputObjectMapper.getSourceRelationByTableName(objectPojoTable.getRealTablename());
            if (inputObjectCreate.inputSysId == null || inputObjectCreate.inputIobjSource == null || StringUtils.isEmpty(inputObjectCreate.objGuid)) {
                return;
            }
            log.info(">>>>>>查询的数据为：" + JSONObject.toJSONString(inputObjectCreate));
            CommonVO queryIsExist = standardizeInputObjectRelateMapper.queryStdInputObjRelExist(outputGuid, inputObjectCreate.objGuid == null ? "null" : inputObjectCreate.objGuid, inputObjectCreate.inputIobjSource == null ? 0 : inputObjectCreate.inputIobjSource);
            int usedCount = queryIsExist.getUsedCount();
            int disableUsedCount = queryIsExist.getDisableUsedCount();
            // 如果都为0，表示在数据库中没有该条数据
            if (usedCount == 0 && disableUsedCount == 0) {
                // 拼接成需要插入的数据 然后将数据插入到表中
                String objGuid = inputObjectCreate.objGuid;
                Integer iorSource = inputObjectCreate.inputIobjSource;
                log.info(String.format(">>>>>>要插入的数据为\nIOBJ_GUID:%s\nOOBJ_GUID:%s\nIOR_MEMO: ''\nIOR_STATUS:1 IOR_SOURCE:%s", objGuid, outputGuid, iorSource));
                StandardizeInputObjectRelateEntity relateEntity = new StandardizeInputObjectRelateEntity();
                relateEntity.setIobjGuid(objGuid);
                relateEntity.setOobjGuid(outputGuid);
                relateEntity.setIorMemo("");
                relateEntity.setIorStatus(1);
                relateEntity.setIorSource(iorSource);
                standardizeInputObjectRelateMapper.insert(relateEntity);
            } else if (usedCount == 0 && disableUsedCount > 0) {
                // 表示数据库中有该条数据，但是状态是禁用状态，需要将状态改成启用
                LambdaUpdateWrapper<StandardizeInputObjectRelateEntity> updateWrapper = Wrappers.lambdaUpdate();
                updateWrapper.set(StandardizeInputObjectRelateEntity::getIorStatus, 1).eq(StandardizeInputObjectRelateEntity::getIobjGuid, inputObjectCreate.objGuid).eq(StandardizeInputObjectRelateEntity::getOobjGuid, outputGuid).eq(StandardizeInputObjectRelateEntity::getIorSource, inputObjectCreate.inputIobjSource);
                standardizeInputObjectRelateMapper.update(updateWrapper);
            }
        } catch (Exception e) {
            log.error(">>>>>>添加新的来源关系报错：", e);
        }
    }

    @Resubmit
    public void saveObjectRelation(ObjectManageDTO objectManageDTO, ObjectEntity objectEntity, ObjectEntity objectEntityOld) {
        if (objectManageDTO.getDataRelationMapping() == null
                || objectManageDTO.getObjectRelationManage() == null
                || StringUtils.isNotBlank(objectManageDTO.getObjectRelationManage().getStandardObjectName())) {
            return;
        }
        ObjectRelationManageVO objectRelationManage = objectManageDTO.getObjectRelationManage();
        //如果原始汇聚层数据集的id为空则是新增的信息
        if (StringUtils.isBlank(objectRelationManage.getOriginalId())) {
            String originalId = UUIDUtil.getUUID();
            String standardId = UUIDUtil.getUUID();
            //生成原始汇聚的数据集，插入数据库
            StandardizeObjectRelationEntity originalObject = new StandardizeObjectRelationEntity();
            originalObject.setId(originalId);
            originalObject.setObjectId(objectEntity.getObjectId());
            originalObject.setObjectName(objectRelationManage.getOriginalObjectName());
            originalObject.setTableId(objectRelationManage.getOriginalTableId());
            originalObject.setParentId("-1");
            originalObject.setCreateTime(new Date());
            originalObject.setUpdateTime(new Date());
            log.info(">>>>>>原始汇聚数据集信息为:", JSONObject.toJSONString(originalObject));
            if (StringUtils.isNotBlank(originalObject.getObjectName()) && StringUtils.isNotBlank(originalObject.getTableId())) {
                insertOrUpdateStdObjRel(originalObject);
            }
            //标准的数据集信息
            StandardizeObjectRelationEntity standardObject = new StandardizeObjectRelationEntity();
            standardObject.setId(standardId);
            standardObject.setObjectId(objectEntity.getObjectId());
            standardObject.setObjectName(objectRelationManage.getStandardObjectName());
            standardObject.setTableId(objectRelationManage.getStandardTableId());
            //关联的原始汇聚标的id
            standardObject.setParentId(originalId);
            log.info(">>>>>>标准的数据集信息为:", JSONObject.toJSONString(standardObject));
            insertOrUpdateStdObjRel(standardObject);

            List<StandardizeObjectfieldRelEntity> originalObjectFieldList = new ArrayList<>();
            List<StandardizeObjectfieldRelEntity> standardObjectFieldList = new ArrayList<>();
            int i = 1;
            for (StandardizeObjectfieldRelEntity data : objectRelationManage.getObjectFieldRelation()) {
                String id = UUIDUtil.getUUID();
                data.setRecno(i++);
                data.setSetId(originalId);
                data.setId(id);
                data.setParentId("-1");
                originalObjectFieldList.add(data);
                List<StandardizeObjectfieldRelEntity> objectFieldRelationMapping = data.getObjectFieldRelationMapping();
                if (!objectFieldRelationMapping.isEmpty()) {
                    objectFieldRelationMapping.stream().forEach(e -> {
                        e.setId(UUIDUtil.getUUID());
                        e.setSetId(standardId);
                        e.setParentId(id);
                        e.setParentColumnName(data.getColumnName());
                        standardObjectFieldList.add(e);
                    });
                }
            }
            if (!originalObjectFieldList.isEmpty() && !standardObjectFieldList.isEmpty()) {
                standardizeObjectfieldRelMapper.insertList(originalObjectFieldList);
                standardizeObjectfieldRelMapper.insertList(standardObjectFieldList);
            }
        } else {
            //此时是更新数据集与数据项信息，拿到之前关联的数据集
            StandardizeObjectRelationEntity standardObjectRelation = standardizeObjectRelationMapper.selectSORByParentId(objectRelationManage.getOriginalId());
            String standObjectUUId = UUIDUtil.getUUID();
            int insertOrUpdateStdObjRelCount;
            if ((StringUtils.isBlank(objectRelationManage.getStandardParentId()) || !(objectRelationManage.getStandardParentId().equalsIgnoreCase(objectRelationManage.getOriginalId())))) {
                LambdaQueryWrapper<StandardizeObjectRelationEntity> queryWrapper = Wrappers.lambdaQuery();
                queryWrapper.eq(StandardizeObjectRelationEntity::getId, standardObjectRelation.getId());
                queryWrapper.eq(StandardizeObjectRelationEntity::getObjectId, standardObjectRelation.getObjectId());
                standardizeObjectRelationMapper.delete(queryWrapper);
                StandardizeObjectRelationEntity standObject = new StandardizeObjectRelationEntity();
                standObject.setId(standObjectUUId);
                standObject.setTableId(objectRelationManage.getStandardTableId());
                standObject.setObjectName(objectRelationManage.getStandardObjectName());
                standObject.setObjectId(objectEntityOld.getObjectId());
                standObject.setParentId(objectRelationManage.getOriginalId());
                //插入更新后的标准数据集
                insertOrUpdateStdObjRelCount = insertOrUpdateStdObjRel(standObject);
                log.info(">>>>>>插入STANDARDIZE_OBJECT_RELATION数据集条数为:{}", insertOrUpdateStdObjRelCount);
            } else {
                insertOrUpdateStdObjRelCount = 0;
            }
            //更新数据集对标字段信息
            List<StandardizeObjectfieldRelEntity> originalObjectFieldList = objectRelationManage.getObjectFieldRelation();
            if (!originalObjectFieldList.isEmpty() && originalObjectFieldList.size() != 0) {
                for (StandardizeObjectfieldRelEntity data : originalObjectFieldList) {
                    data.setSetId(objectRelationManage.getOriginalId());
                    LambdaQueryWrapper<StandardizeObjectfieldRelEntity> queryWrapper = Wrappers.lambdaQuery();
                    queryWrapper.eq(StandardizeObjectfieldRelEntity::getSetId, data.getSetId());
                    queryWrapper.eq(StandardizeObjectfieldRelEntity::getColumnName, data.getColumnName());
                    long columnCount = standardizeObjectfieldRelMapper.selectCount(queryWrapper);
                    String originalFieldId = UUIDUtil.getUUID();
                    if (columnCount >= 1) {
                        //有数据则是更新
                        standardizeObjectfieldRelMapper.updateStdObjectfieldRel(data);
                    } else {
                        data.setId(originalFieldId);
                        data.setParentId("-1");
                        standardizeObjectfieldRelMapper.insert(data);
                    }
                    List<StandardizeObjectfieldRelEntity> objectFieldRelationMapping = data.getObjectFieldRelationMapping();
                    objectFieldRelationMapping.stream().forEach(d -> {
                        d.setId(UUIDUtil.getUUID());
                        d.setParentId(columnCount >= 1 ? data.getId() : originalFieldId);
                        d.setParentColumnName(data.getColumnName());
                        if (insertOrUpdateStdObjRelCount == 0) {
                            d.setSetId(standardObjectRelation.getId());
                        } else {
                            d.setSetId(standObjectUUId);
                        }
                    });
                    standardizeObjectfieldRelMapper.insertList(objectFieldRelationMapping);
                }
            }
        }
    }

    public int insertOrUpdateStdObjRel(StandardizeObjectRelationEntity standObject){
        LambdaQueryWrapper<StandardizeObjectRelationEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(StandardizeObjectRelationEntity::getObjectId, standObject.getObjectId());
        if (standardizeObjectRelationMapper.selectCount(wrapper) > 0){
            return standardizeObjectRelationMapper.updateStdObjRel(standObject);
        }else {
            return standardizeObjectRelationMapper.insert(standObject);
        }
    }

    public void saveObjectFieldInfo(ObjectManageDTO objectManageDTO, ObjectEntity objectEntity, Integer objectID) throws Exception {
        log.info(">>>>>>保存字段定义的内容...");
        // 获取tableId对应的 objectId值，然后再重新给objectId赋值
        // 先通过对比，发现哪些表字段信息是被删除了的，然后再删除对比出来的字段信息
        // 1：先获取数据库中所有字段信息
        List<ObjectFieldEntity> objectFieldListOld = queryObjectFieldListByTableId(objectEntity.getTableId());
        // 2：页面上字段信息
        List<ObjectFieldEntity> objectFieldListPage = objectManageDTO.getObjectFieldList();
        if (objectFieldListPage == null || objectFieldListPage.isEmpty()) {
            return;
        }
        // 字段名称列表
        List<String> columnListPage = objectFieldListPage.stream().filter(e -> StringUtils.isNotEmpty(e.getColumnName())).map(e -> e.getColumnName().toUpperCase()).distinct().collect(Collectors.toList());
        // 3：存储需要删除的字段信息
        if (objectFieldListOld != null) {
            for (ObjectFieldEntity objectField : objectFieldListOld) {
                if (StringUtils.isNotEmpty(objectField.getColumnName()) && !columnListPage.contains(objectField.getColumnName().toUpperCase())) {
                    // 4:调用方法删除字段信息
                    deleteObjectField(Long.valueOf(objectID), objectField.getColumnName());
                }
            }
        }
        // 20210623：字段顺序、是否为聚集列、是否为主键列、是否参与MD5运算，按照页面的顺序来更新对应的数字
        setColumnRecon(objectFieldListPage);
        List<ObjectFieldEntity> realObjectFieldList = new ArrayList<>();
        //处理更新的表字段
        List<ObjectFieldEntity> objectFieldsUpdate = objectFieldListPage.stream().filter(e -> e.getUpdateStatus() != 1).collect(toList());
        if (objectFieldsUpdate != null && objectFieldsUpdate.size() > 0) {
            //2、重复的fieldId计数
            Map<String, Long> fieldIdCount = objectFieldsUpdate.stream().collect(Collectors.groupingBy(ObjectFieldEntity::getFieldId, Collectors.counting()));
            //3、筛出有重复的fieldId的列表
            List<String> fieldIdList = fieldIdCount.keySet().stream().filter(key -> fieldIdCount.get(key) > 1).collect(toList());
            if (!fieldIdList.isEmpty()) {
                throw new Exception("FieldId重复：" + fieldIdList);
            }
            //4、将旧字段添加到新的列表中，旧的字段fieldId保持不变
            for (ObjectFieldEntity data : objectFieldsUpdate) {
                data.setObjectId(objectID);
                realObjectFieldList.add(data);
            }
        }
        //处理新增的表字段
        List<ObjectFieldEntity> objectFieldsAdd = objectFieldListPage.stream().filter(e -> e.getUpdateStatus() == 1).collect(toList());
        if (!objectFieldsAdd.isEmpty()) {
            //赋值完fieldId后，遍历是否有相同的fieldId
            objectFieldsAdd.stream().forEach(d -> {
                objectFieldsAdd.stream().forEach(e -> {
                    if (!d.getColumnName().equalsIgnoreCase(e.getColumnName()) && d.getFieldId().equalsIgnoreCase(e.getFieldId())) {
                        throw new RuntimeException("字段中有相同的数据项唯一编码，无法保存");
                    }
                });
            });
            //4.拿新字段与旧字段比较是否有重复的fieldId
            for (ObjectFieldEntity data : realObjectFieldList) {
                objectFieldsAdd.stream().forEach(e -> {
                    if (data.getFieldId().equalsIgnoreCase(e.getFieldId())) {
                        throw new RuntimeException(e.getColumnName() + "字段中有重复的元素编码，无法保存");
                    }
                });
            }
            //5.将新增的字段添加到需要更新或是筛选的列表中
            for (ObjectFieldEntity data : objectFieldsAdd) {
                data.setObjectId(objectID);
                data.setNeedValue(0);
                realObjectFieldList.add(data);
            }
        }
        //判断fieldId是否有重复的，无重复的之后再存储
        int recno = 1;
        JSONObject versionsJson = getStdVersions();
        String version_0 = versionsJson.getString("synlteFieldVersions");
        LambdaQueryWrapper<ObjectFieldEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(ObjectFieldEntity::getObjectId, realObjectFieldList.get(0).getObjectId());
        List<ObjectFieldEntity> objectFieldOld = objectFieldMapper.selectList(queryWrapper);
        for (ObjectFieldEntity data : realObjectFieldList) {
            data.setUpdater(objectEntity.getUpdater());
            data.setCreator(objectEntity.getUpdater());
            realObjectFieldList.stream().forEach(d -> {
                if (StringUtils.isNoneEmpty(d.getColumnName()) && StringUtils.isNotEmpty(d.getFieldId())) {
                    if (!d.getColumnName().equalsIgnoreCase(data.getColumnName())) {
                        if (d.getFieldId().equalsIgnoreCase(data.getFieldId())) {
                            throw new RuntimeException(data.getColumnName() + "该字段在表已有重复的元素编码(fieldId),无法保存");
                        }
                    }
                }
            });
            saveObjectField(data, recno, version_0, objectFieldOld);
            recno++;
            // 如果字段的分类信息不为空，则更新字段的相关信息
            updateSynlteFieldClass(data);
        }
    }

    public void updateSynlteFieldClass(ObjectFieldEntity objectField) {
        if (StringUtils.isBlank(objectField.getFieldId()) || StringUtils.isBlank(objectField.getFieldClassId()) || !StringUtils.isNumeric(objectField.getFieldClassId())) {
            return;
        }
        LambdaUpdateWrapper<SynlteFieldEntity> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.set(SynlteFieldEntity::getFieldClass, objectField.getFieldClassId()).eq(SynlteFieldEntity::getFieldId, objectField.getFieldId());
        synlteFieldMapper.update(updateWrapper);
        log.info(String.format("synltefield.fieldid：%s，更新的字段为：%s", objectField.getFieldId(), objectField.getFieldClassId()));
    }

    public void saveObjectField(ObjectFieldEntity objectField, int recno, String version_0, List<ObjectFieldEntity> objectFieldOld) throws Exception {
        //对于 是否为聚集列 是否为主键列 是否参与MD5运算 如果为 true则需要先在数据库中找到
        if (objectField.getColumnName().equalsIgnoreCase("")) {
            log.error(">>>>>>建表字段为空，不能进行保存或修改操作：", JSONObject.toJSONString(objectField));
            return;
        }
        if (objectField.getIsPrivate() == null) {
            objectField.setIsPrivate(0);
        }
        if (objectField.getClustRecno() == null) {
            objectField.setClustRecno(0);
        }
        if (objectField.getPkRecno() == null) {
            objectField.setPkRecno(0);
        } else {
            objectField.setPkRecno(recno);
        }
        if (objectField.getMd5Index() == null) {
            objectField.setMd5Index(0);
        } else {
            objectField.setMd5Index(recno);
        }
        if (objectField.getIsPrivate() == null) {
            objectField.setIsPrivate(1);
        }
        if (objectField.getProType() == null) {
            objectField.setProType(1);
        }
        // 确定版本日期 VERSION 精确到天 20191023
        int version = Integer.parseInt(DateUtil.formatDate(new Date(), DateUtil.DEFAULT_PATTERN_DATE_SIMPLE));
        objectField.setVersion(version);
        log.info(">>>>>>插入/更新的字段信息为：{}", JSONObject.toJSONString(objectField));

        // 先判断在这张表中输入的fieldId是否已经存在，如果存在，返回报错信息，提示页面
        long fieldNeedCount = objectFieldOld.stream().filter(data -> data.getColumnName().equalsIgnoreCase(objectField.getColumnName())).count();
        if (fieldNeedCount == 1) {
            // 如果为1，表示属于更新操作，更新数据；20210818 新增需求，将之前的数据存储到历史表中
            ObjectFieldHisEntity objectFieldHis = new ObjectFieldHisEntity();
            objectField.setCreateTime(objectField.getCreateTime() != null ? objectField.getCreateTime() : new Date());
            objectField.setUpdateTime(objectField.getUpdateTime() != null ? objectField.getUpdateTime() : new Date());
            BeanUtils.copyProperties(objectFieldHis, objectField);
            objectFieldHis.setObjectIdVersion(UUIDUtil.getUUID());
            objectFieldHis.setColumnNameState(0);
            objectFieldHis.setCreator(objectField.getUpdater());
            objectFieldHis.setVersion0(version_0 != null ? version_0 : "1.0");
            objectFieldHis.setMemo(objectField.getMemo() == null ? "" : objectField.getMemo());
            if (objectFieldHis.getPartitionRecno() == null) {
                objectFieldHis.setPartitionRecno(0);
            }
            //更新字段
            objectFieldMapper.updateObjectField(objectField);
            //存储标准字段历史信息
            objectFieldHisMapper.insert(objectFieldHis);
        } else if (fieldNeedCount == 0) {
            objectField.setVersion0(version_0 != null ? version_0 : "");
            objectFieldMapper.insert(objectField);
        } else {
            log.error(">>>>>>该字段在表中已经存在多个，请添加别的字段信息");
        }

    }

    public void setColumnRecon(List<ObjectFieldEntity> list) {
        try {
            // 是否为聚集列，如果不是：填入空字符串，是：则按照
            AtomicInteger clustRecno = new AtomicInteger(1);
            // 是否为主键，PkRecnoStatus
            AtomicInteger pkRecno = new AtomicInteger(1);
            // 是否参与MD5运算
            AtomicInteger md5Index = new AtomicInteger(1);
            //表中字段顺序
            AtomicInteger recno = new AtomicInteger(1);
            AtomicBoolean flag = new AtomicBoolean(false);
            list.stream().sorted(Comparator.comparingInt(ObjectFieldEntity::getRecno)).forEach(d -> {
                //1：不做任何修改，ClustRecnoStatus 为 null 则 clustRecno 存在数字且不为0 表示是有聚集列
                //2：做了修改，以ClustRecnoStatus为准 true表示存在、false表示不存在
                if (d.getUpdateStatus() != 0) {
                    flag.set(true);
                }
                if (d.getClustRecno() != null && d.getClustRecno() != 0 && d.getClustRecnoStatus() == null && flag.get()) {
                    d.setClustRecno(clustRecno.get());
                    clustRecno.getAndIncrement();
                    d.setUpdateStatus(1);
                } else if (d.getClustRecnoStatus() != null && d.getClustRecnoStatus()) {
                    d.setClustRecno(clustRecno.get());
                    clustRecno.getAndIncrement();
                    d.setUpdateStatus(1);
                } else {
                    d.setClustRecno(0);
                }
                if (d.getPkRecno() != null && d.getPkRecno() != 0 && d.getPkRecnoStatus() == null && flag.get()) {
                    d.setPkRecno(pkRecno.get());
                    pkRecno.getAndIncrement();
                    d.setUpdateStatus(1);
                } else if (d.getPkRecnoStatus() != null && d.getPkRecnoStatus()) {
                    d.setPkRecno(pkRecno.get());
                    pkRecno.getAndIncrement();
                    d.setUpdateStatus(1);
                } else {
                    d.setPkRecno(0);
                }
                if (d.getMd5Index() != null && d.getMd5Index() != 0 && d.getMd5IndexStatus() == null && flag.get()) {
                    d.setMd5Index(md5Index.get());
                    md5Index.getAndIncrement();
                    d.setUpdateStatus(1);
                } else if (d.getMd5IndexStatus() != null && d.getMd5IndexStatus()) {
                    d.setMd5Index(md5Index.get());
                    md5Index.getAndIncrement();
                    d.setUpdateStatus(1);
                } else {
                    d.setMd5Index(0);
                }
                if (d.getRecno() != recno.get()) {
                    d.setRecno(recno.get());
                    d.setStandardRecno(recno.get());
                    d.setUpdateStatus(1);
                }
                recno.getAndIncrement();
            });
        } catch (Exception e) {
            log.error(">>>>>>处理字段顺序报错：", e);
        }
    }

    /**
     * 保存修改后的数据信息，当objectid为空时、表示新增，当objectid不为空时、为修改。
     * 新增需求，当objectID存在时，可以修改tableid，此时需要做判断
     * 20200225：因为分级分类表变成tableorg该表是根据表名来做关联的，表名可以做修改，所以先要获取到修改之前的表名
     * 如果表名发生变化，则也要修改表名
     *
     * @param objectManageDTO
     */
    public Boolean saveObjectInfo(ObjectManageDTO objectManageDTO) {
        boolean saveObjectFlag = true;
        try {
            log.info(">>>>>>开始插入标准信息：synlte.object");
            ObjectEntity objectEntity = objectManageDTO.getObjectPojoTable();
            // 目标协议对应厂商
            int factory = KeyIntEnum.getKeyByNameAndType(objectEntity.getOwnerFactory(), Common.MANUFACTURER_NAME);
            if (factory == 400) {
                factory = 0;
            }
            //查询大版本和设置小版本
            JSONObject versionsJson = getStdVersions();
            String versions = versionsJson.getString("objectVersions");

            // 注入标准表objectPojoTable其他信息
            injectObjectPojoTable(objectEntity, factory, versions);

            // 判断tableId是修改的数据还是新增的数据，如果有objectid，先根据objectId判断tableid是否相同，
            // 如果相同，表示tableid没有修改，如果不同，表示tableid发生了修改，需要更改该tableid，
            // 如果修改后的tableid重复返回报错信息
            LambdaQueryWrapper<ObjectEntity> queryWrapperObject = Wrappers.lambdaQuery();
            queryWrapperObject.eq(ObjectEntity::getTableId, objectEntity.getTableId());
            List<ObjectEntity> objectEntities = objectMapper.selectList(queryWrapperObject);
            Integer objectId = objectEntity.getObjectId();
            if (objectId != null) {
                LambdaQueryWrapper<ObjectEntity> wrapper = Wrappers.lambdaQuery();
                wrapper.eq(ObjectEntity::getObjectId, objectId);
                String tableId = objectMapper.selectOne(wrapper).getTableId();
                if (StringUtils.isEmpty(tableId)) {
                    saveObjectFlag = false;
                    log.error(">>>>>>根据objectId获取到的tableid为空");
                    throw new Exception("根据objectId获取到的tableid为空");
                }
                log.info(">>>>>>数据库中找到的tableId值为：" + tableId);
                if (!objectEntity.getTableId().equalsIgnoreCase(tableId)) {
                    // 如果不相等，表示修改了tableid信息，先判断修改后的id信息是否重复，如果重复，表示不能修改该值
                    if (objectEntities.size() > 0 && objectEntities.get(0).getTableId() != null) {
                        saveObjectFlag = false;
                        log.error(String.format(">>>>>>该tableId%s在数据库中已经存在，不能修改为该值", objectEntity.getTableId()));
                        throw new Exception(String.format(">>>>>>该tableId%s在数据库中已经存在，不能修改为该值", objectEntity.getTableId()));
                    }
                    LambdaUpdateWrapper<ObjectEntity> updateWrapper = Wrappers.lambdaUpdate();
                    updateWrapper.set(ObjectEntity::getTableId, objectEntity.getTableId()).eq(ObjectEntity::getTableId, tableId);
                    int updateCount = objectMapper.update(updateWrapper);
                    log.info(String.format(">>>>>>将数据库中的tableid：%s修改为：%s", tableId, objectEntity.getTableId()));
                    // 判断在standardize_outputobject表中的数据是否已经存在，如果存在，先获取到obj_guid，然后根据该值获取到更新tableid
                    String objGuidString = standardizeOutputObjectMapper.getOutPutObjGuidByTableId(tableId, objectEntity.getCodeTextTd(), factory);
                    if (!StringUtils.isEmpty(objGuidString)) {
                        LambdaUpdateWrapper<StandardizeObjectEntity> updateWrapper1 = Wrappers.lambdaUpdate();
                        updateWrapper1.set(StandardizeObjectEntity::getObjEngName, objectEntity.getTableId()).eq(StandardizeObjectEntity::getObjGuid, objGuidString);
                        standardizeObjectMapper.update(updateWrapper1);
                    }
                    if (updateCount >= 1) {
                        objectManageDTO.setOperateType(3);
                        log.info("tableid修改成功");
                    } else {
                        saveObjectFlag = false;
                        log.error("tableid修改失败");
                        throw new Exception("tableId数据修改失败，请排查原因");
                    }
                }
            }
            //------判断这个目标表是否存在，如果存在更新
            int numCount = objectEntities.size();
            if (objectId != null && numCount > 1) {
                // 直接删除现有的信息
                saveObjectFlag = false;
                log.error(">>>>>>在表中同一个tableId有多个数据，数据库中数据报错");
                throw new Exception("在表中同一个tableId有多个数据，数据库中数据报错");
            }
            //------更新/新增object相关信息
            if (objectId == null && numCount == 1) {
                if (!objectEntity.getFlow()) {
                    saveObjectFlag = false;
                    log.error(">>>>>>该tableId已经存在，不能重复新增");
                    throw new Exception(">>>>>>该tableId已经存在，不能重复新增");
                } else {
                    // 流程中的判断
                    if (StringUtils.isNotEmpty(objectEntity.getRealTablename())) {
                        // 如果真实表名和tableId对应得上，则表示可以更新该数据，否则依然报错
                        int objectCount = objectEntities.stream().filter(d -> d.getRealTablename().equalsIgnoreCase(objectEntity.getRealTablename())).collect(toList()).size();
                        if (objectCount > 0) {
                            objectMapper.updateObjectByTableId(objectEntity);
                            objectManageDTO.setOperateType(3);
                            log.info(String.format(">>>>>>object表更新完成：\n%s", JSONObject.toJSONString(objectEntity)));
                        } else {
                            saveObjectFlag = false;
                            log.error(">>>>>>该tableId在object表中已经存在，并且存储的表名不是该表名，新增失败");
                            throw new Exception("该tableId在object表中已经存在，并且存储的表名不是该表名，新增失败");
                        }
                    } else {
                        saveObjectFlag = false;
                        log.error("该tableId已经存在，不能重复新增");
                        throw new Exception("该tableId已经存在，不能重复新增");
                    }
                }
            } else if (objectId == null && numCount == 0) {
                // 当objectid为空时，表示这个表不存在，先查找最大的objectId，然后添加这个值
                Integer objectIdOld = objectMapper.getMaxObjectId();
                int objectIdNew = 1000001;
                if (objectIdOld != null && objectIdOld >= 1000001) {
                    objectIdNew = Integer.valueOf(objectIdOld) + 1;
                }
                objectEntity.setObjectId(objectIdNew);
                if (StringUtils.isBlank(objectEntity.getSourceId())) {
                    objectEntity.setSourceId(objectEntity.getTableId());
                }
                // 新增object数据
                objectMapper.insert(objectEntity);
                objectManageDTO.setOperateType(2);
                log.info(String.format(">>>>>>数据标准表名管理object新增完成：\n%s", JSONObject.toJSONString(objectEntity)));
            } else {
                LambdaQueryWrapper<ObjectEntity> wrapper = Wrappers.lambdaQuery();
                wrapper.eq(ObjectEntity::getObjectId, objectId);
                ObjectEntity objectEntityOld = objectMapper.selectOne(wrapper);
                boolean isUpdate = checkIsUpdate(objectEntityOld, objectEntity);
                if (isUpdate) {
                    saveObjectFlag = false;
                    log.info(">>>>>>Object表未做改动，更新失败");
                    throw new Exception("Object表未做改动，更新失败");
                } else {
                    objectMapper.updateObjectByObjectId(objectEntity);
                    objectManageDTO.setOperateType(3);
                    log.info(String.format(">>>>>>数据标准表名管理表object更新完成：\n%s", JSONObject.toJSONString(objectEntity)));

                    // 更新成功，生成版本库记录
                    String uuid = UUIDUtil.getUUID();
                    ObjectVersionEntity objectVersion = injectObjectVersion(objectEntity, objectEntityOld, versions, uuid);
                    objectVersionMapper.insert(objectVersion);
                    log.info(String.format(">>>>>>版本信息表object_version新增完成：\n%s", JSONObject.toJSONString(objectVersion)));

                    // 将标准历史信息保存至标准历史表中
                    ObjectHisEntity objectHisEntity = injectObjectHis(objectEntityOld, uuid);
                    objectHisMapper.insert(objectHisEntity);
                    log.info(String.format(">>>>>>数据标准表备份表object_history新增完成：\n%s", JSONObject.toJSONString(objectHisEntity)));

                    // 修改dp_table_organization_assets表中的分级分类数据
                    updateAssetsClassify(objectEntity);
                }
            }
            // ------定义的输出表，将数据插入到曾瑞比组的standardize_object、standardize_outputobject表中
            int objectCount = standardizeObjectMapper.getStandardizeObjectCount(objectEntity.getTableId());
            if (objectCount == 0) {
                List<SourceRelationShipVO> sourceRelationShipList = objectManageDTO.getSourceRelationShipList();
                SourceRelationShipVO sourceRelationShip = sourceRelationShipList.size() > 0 ? sourceRelationShipList.get(0) : null;
                // 先判断在standardize_object中是否存在，如果存在，不管，如果不存在，则插入
                String objGuid = standardizeObjectMapper.getObjGuid(objectEntity.getTableId(), objectEntity.getCodeTextTd());
                if (StringUtils.isEmpty(objGuid) && sourceRelationShip != null) {
                    StandardizeObjectEntity standardizeObject = injectStdObject(sourceRelationShip, objectEntity);
                    standardizeObjectMapper.insert(standardizeObject);
                    log.info(String.format(">>>>>>对象（协议）表standardize_object新增完成：\n%s", JSONObject.toJSONString(standardizeObject)));
                }
                String objGuidNew = standardizeObjectMapper.getObjGuid(objectEntity.getTableId(), objectEntity.getCodeTextTd());
                if (StringUtils.isNotEmpty(objGuidNew)) {
                    standardizeOutputObjectMapper.insert(injectStdOutputObj(objGuidNew, 1, 1, Integer.valueOf(objectEntity.getOwnerFactoryCode())));
                    log.info(">>>>>>输出对象（协议）表standardize_outputobject新增完成");
                }
            } else if (objectCount == 1) {
                String objGuid = standardizeObjectMapper.getOutputGuidNotInInput(objectEntity.getTableId());
                LambdaUpdateWrapper<StandardizeObjectEntity> updateWrapper = Wrappers.lambdaUpdate();
                updateWrapper.set(StandardizeObjectEntity::getSysId, objectEntity.getCodeTextTd()).set(StandardizeObjectEntity::getObjChiName, objectEntity.getDataSourceName()).set(StandardizeObjectEntity::getSysSource, objectEntity.getOwnerFactoryCode()).eq(StandardizeObjectEntity::getObjGuid, objGuid);
                int updateCount = standardizeObjectMapper.update(updateWrapper);
                log.info(">>>>>>对象（协议）表standardize_object更新完成：{}", updateCount);
            } else {
                log.error(">>>>>>standardize_outputobject有问题,存在多个相同的数据");
            }
        } catch (Exception e) {
            saveObjectFlag = false;
            log.error(">>>>>>保存object相关数据报错：\n{}", e);
        }
        return saveObjectFlag;
    }

    public JSONObject getStdVersions() {
        LambdaQueryWrapper<DgnCommonSettingEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(DgnCommonSettingEntity::getName, "标准版本管理设置");
        DgnCommonSettingEntity dgnCommonSetting = dgnCommonSettingMapper.selectOne(queryWrapper);
        return dgnCommonSetting.getThresholdValue() != null ? (JSONObject) JSON.parse(dgnCommonSetting.getThresholdValue()) : new JSONObject();
    }

    /**
     * 修改dp_table_organization_assets表中的分级分类数据：目前修改数据组织分类和数据来源分类信息
     *
     * @param objectEntity
     */
    public void updateAssetsClassify(ObjectEntity objectEntity) {
        log.info(">>>>>>开始修改dp_table_organization_assets表中分类信息");
        // 数据组织分类
        String sourceClassifyCh = objectEntity.getSourceClassify();
        // 数据来源分类
        String organizationClassifyCh = objectEntity.getOrganizationClassify();
        String oneSourceClassifyCh = "";
        String twoSourceClassifyCh = "";
        String oneOrganizationClassifyCh = "";
        String twoOrganizationClassifyCh = "";
        String threeOrganizationClassifyCh = "";
        if (sourceClassifyCh.split("/").length == 2) {
            oneSourceClassifyCh = sourceClassifyCh.split("/")[0];
            twoSourceClassifyCh = sourceClassifyCh.split("/")[1];
        } else if (sourceClassifyCh.split("/").length == 1) {
            oneSourceClassifyCh = sourceClassifyCh.split("/")[0];
            twoSourceClassifyCh = "";
        } else {
            oneSourceClassifyCh = "未知";
            twoSourceClassifyCh = "未知";
        }
        if (organizationClassifyCh.contains("业务要素索引库")) {
            oneOrganizationClassifyCh = organizationClassifyCh;
            twoOrganizationClassifyCh = "";
        } else if (organizationClassifyCh.contains("其它")) {
            oneOrganizationClassifyCh = organizationClassifyCh;
            twoOrganizationClassifyCh = "";
        } else if (organizationClassifyCh.split("/").length == 3) {
            oneOrganizationClassifyCh = organizationClassifyCh.split("/")[0];
            twoOrganizationClassifyCh = organizationClassifyCh.split("/")[1];
            threeOrganizationClassifyCh = organizationClassifyCh.split("/")[2];
        } else if (organizationClassifyCh.split("/").length == 2) {
            oneOrganizationClassifyCh = organizationClassifyCh.split("/")[0];
            twoOrganizationClassifyCh = organizationClassifyCh.split("/")[1];
        } else if (organizationClassifyCh.split("/").length == 1) {
            oneOrganizationClassifyCh = organizationClassifyCh.split("/")[0];
            twoOrganizationClassifyCh = "";
        } else {
            oneOrganizationClassifyCh = "未知";
            twoOrganizationClassifyCh = "未知";
        }
        String oldTableName = objectEntity.getRealTablename();
        objectMapper.updateAssetsClassify(oneSourceClassifyCh, twoSourceClassifyCh, oneOrganizationClassifyCh, twoOrganizationClassifyCh, threeOrganizationClassifyCh, oldTableName);
        objectMapper.updateAssetsTempClassify(oneSourceClassifyCh, twoSourceClassifyCh, oneOrganizationClassifyCh, twoOrganizationClassifyCh, threeOrganizationClassifyCh, oldTableName);
    }

    private ObjectVersionEntity injectObjectVersion(ObjectEntity objectEntity, ObjectEntity objectEntityOld, String versions, String uuid) {
        ObjectVersionEntity objectVersion = new ObjectVersionEntity();
        objectVersion.setObjectidVersion(uuid);
        objectVersion.setObjectId(String.valueOf(objectEntity.getObjectId()));
        if (!objectEntity.getRealTablename().isEmpty()) {
            objectVersion.setTableName(objectEntity.getRealTablename());
        }
        StringBuffer stringBuffer = new StringBuffer();
        if (!objectEntityOld.getDataSourceName().equals(objectEntity.getDataSourceName())) {
            stringBuffer.append("数据中文名属性,");
        }
        if (!(objectEntityOld.getDataType().equals(objectEntity.getDataType()))) {
            stringBuffer.append("数据组织分类属性,");
        }
        String sjzylylxValue = objectEntity.getSjzylylxValue() == null ? "" : objectEntity.getSjzylylxValue();
        String sjzylylxValueOld = objectEntityOld.getSjzylylxValue() == null ? "" : objectEntityOld.getSjzylylxValue();
        if (!(sjzylylxValue.equalsIgnoreCase(sjzylylxValueOld))) {
            stringBuffer.append("数据来源分类属性,");
        }
        if (!(objectEntityOld.getRealTablename().equals(objectEntity.getRealTablename()))) {
            stringBuffer.append("物理表名属性,");
        }
        if (!(objectEntityOld.getObjectState().equals(objectEntity.getObjectState()))) {
            stringBuffer.append("存储表状态属性,");
        }
        if (!(objectEntityOld.getObjectMemo() != null && objectEntityOld.getObjectMemo().equals(objectEntity.getObjectMemo()))) {
            stringBuffer.append("数据描述属性,");
        }
        if (!(objectEntityOld.getDataLevel().equals(objectEntity.getDataLevel()))) {
            stringBuffer.append("数据分级属性,");
        }
        if (objectEntityOld.getSjzybq1() != null && objectEntity.getSjzybq1() != null && !objectEntityOld.getSjzybq1().equalsIgnoreCase(objectEntity.getSjzybq1())) {
            stringBuffer.append("资源标签一,");
        }
        if (objectEntityOld.getSjzybq2() != null && objectEntity.getSjzybq2() != null && !objectEntityOld.getSjzybq2().equalsIgnoreCase(objectEntity.getSjzybq2())) {
            stringBuffer.append("资源标签二,");
        }
        if (objectEntityOld.getSjzybq3() != null && objectEntity.getSjzybq3() != null && !objectEntityOld.getSjzybq3().equalsIgnoreCase(objectEntity.getSjzybq3())) {
            stringBuffer.append("资源标签三,");
        }
        if (objectEntityOld.getSjzybq4() != null && objectEntity.getSjzybq4() != null && !objectEntityOld.getSjzybq4().equalsIgnoreCase(objectEntity.getSjzybq4())) {
            stringBuffer.append("资源标签四,");
        }
        if (objectEntityOld.getSjzybq5() != null && objectEntity.getSjzybq5() != null && !objectEntityOld.getSjzybq5().equalsIgnoreCase(objectEntity.getSjzybq5())) {
            stringBuffer.append("资源标签五,");
        }
        if (objectEntityOld.getSjzybq6() != null && objectEntity.getSjzybq6() != null && !objectEntityOld.getSjzybq6().equalsIgnoreCase(objectEntity.getSjzybq6())) {
            stringBuffer.append("资源标签六,");
        }
        if (stringBuffer.length() != 0) {
            StringBuffer memo = new StringBuffer("修改了：").append(stringBuffer);
            if (memo.indexOf(",", memo.length() - 1) != -1) {
                objectVersion.setMemo(memo.substring(0, memo.length() - 1));
            }
        } else {
            objectVersion.setMemo("未修改内容");
        }
        objectVersion.setVersion(objectEntity.getVersion());
        objectVersion.setVersions(StringUtils.isNotBlank(versions) ? versions : "1.0");
        objectVersion.setAuthor(objectEntity.getUpdater());
        objectVersion.setUpdateTime(new Date());

        return objectVersion;
    }

    private ObjectHisEntity injectObjectHis(ObjectEntity objectEntity, String uuid) {
        ObjectHisEntity objectHisEntity = new ObjectHisEntity();
        objectHisEntity.setObjectIdVersion(uuid);
        objectHisEntity.setObjectId(objectEntity.getObjectId());
        objectHisEntity.setObjectName(objectEntity.getDataSourceName());
        objectHisEntity.setObjectState(objectEntity.getObjectState());
        objectHisEntity.setTableId(objectEntity.getTableId());
        objectHisEntity.setTableName(objectEntity.getRealTablename());
        objectHisEntity.setStoreType(objectEntity.getStoreType());
        objectHisEntity.setCodeTextTd(objectEntity.getCodeTextTd());
        objectHisEntity.setDbSource(objectEntity.getDbSource());
        objectHisEntity.setSourceId(objectEntity.getSourceId());
        objectHisEntity.setObjectMemo(objectEntity.getObjectMemo());
        objectHisEntity.setObjectFlag(objectEntity.getObjectFlag());
        objectHisEntity.setHiveTableName(objectEntity.getHiveTableName());
        objectHisEntity.setDataType(objectEntity.getDataType());
        objectHisEntity.setRelateTableName(objectEntity.getRelateTableName());
        objectHisEntity.setIsActiveTable(objectEntity.getIsActiveTable());
        objectHisEntity.setUpdateTime(objectEntity.getUpdateTime());
        objectHisEntity.setCreator(objectEntity.getCreator());
        objectHisEntity.setUpdater(objectEntity.getUpdater());
        Integer version = Integer.valueOf(DateUtil.formatDateTime(new Date(), DateUtil.DEFAULT_PATTERN_DATE_SIMPLE));
        objectHisEntity.setVersion(objectEntity.getVersion() != null ? objectEntity.getVersion(): version);
        objectHisEntity.setVersions(StringUtils.isNotBlank(objectEntity.getVersions()) ? objectEntity.getVersions() : "1.0");
        objectHisEntity.setDataLevel(Integer.valueOf(objectEntity.getDataLevel()));
        objectHisEntity.setStandardType(objectEntity.getStandardType());
        objectHisEntity.setSjzybq1(objectEntity.getSjzybq1());
        objectHisEntity.setSjzybq2(objectEntity.getSjzybq2());
        objectHisEntity.setSjzybq3(objectEntity.getSjzybq3());
        objectHisEntity.setSjzybq4(objectEntity.getSjzybq4());
        objectHisEntity.setSjzybq5(objectEntity.getSjzybq5());
        objectHisEntity.setSjzybq6(objectEntity.getSjzybq6());
        objectHisEntity.setSjzzyjflvallue(objectEntity.getSjzzyjflVallue());
        objectHisEntity.setSjzzejflvalue(objectEntity.getSjzzejflValue());
        objectHisEntity.setSjzylylxvalue(objectEntity.getSjzylylxValue());

        return objectHisEntity;
    }

    private StandardizeObjectEntity injectStdObject(SourceRelationShipVO sourceRelationShip, ObjectEntity objectEntity) {
        StandardizeObjectEntity standardizeObject = new StandardizeObjectEntity();
        String sysId = objectEntity.getCodeTextTd() != null ? objectEntity.getCodeTextTd() : sourceRelationShip.getSourceSystem();
        String tableId = objectEntity.getSourceId() != null ? objectEntity.getSourceId() : sourceRelationShip.getSourceProtocol();
        String tableNameEn = sourceRelationShip.getTableNameEN() != null ? sourceRelationShip.getTableNameEN() : sourceRelationShip.getRealTableName();
        Integer ownerFactory = objectEntity.getOwnerFactory() != null ? Integer.valueOf(objectEntity.getOwnerFactory()) : 0;
        String dataId = sourceRelationShip.getDataId() != null ? sourceRelationShip.getDataId() : "dataId";
        String tableNameCh = sourceRelationShip.getTableNameCN() != null ? sourceRelationShip.getTableNameCN() : sourceRelationShip.getDataSourceName();
        standardizeObject.setSysId(sysId);
        standardizeObject.setObjEngName(tableId);
        standardizeObject.setObjChiName(tableNameEn);
        standardizeObject.setSysSource(ownerFactory);
        standardizeObject.setDataId(dataId);
        standardizeObject.setCenterId(sourceRelationShip.getCenterId());
        standardizeObject.setObjMemo(tableNameCh);

        return standardizeObject;
    }

    private boolean checkIsUpdate(ObjectEntity objectEntityOld, ObjectEntity objectEntity) {
        boolean isUpdate = false;
        if (objectEntityOld.getDataSourceName().equalsIgnoreCase(objectEntity.getDataSourceName())
                && objectEntityOld.getDataType() == objectEntity.getDataType()
                && objectEntityOld.getRealTablename().equalsIgnoreCase(objectEntity.getRealTablename())
                && objectEntityOld.getIsActiveTable().equals(objectEntity.getIsActiveTable())
                && objectEntityOld.getObjectState().equals(objectEntity.getObjectState())
                && objectEntityOld.getDataLevel().equalsIgnoreCase(objectEntity.getDataLevel())
                && objectEntityOld.getSjzybq1() != null && objectEntityOld.getSjzybq1().equalsIgnoreCase(objectEntity.getSjzybq1())
                && objectEntityOld.getSjzybq2() != null && objectEntityOld.getSjzybq2().equalsIgnoreCase(objectEntity.getSjzybq2())
                && objectEntityOld.getSjzybq3() != null && objectEntityOld.getSjzybq3().equalsIgnoreCase(objectEntity.getSjzybq3())
                && objectEntityOld.getSjzybq4() != null && objectEntityOld.getSjzybq4().equalsIgnoreCase(objectEntity.getSjzybq4())
                && objectEntityOld.getSjzybq5() != null && objectEntityOld.getSjzybq5().equalsIgnoreCase(objectEntity.getSjzybq5())
                && objectEntityOld.getSjzybq6() != null && objectEntityOld.getSjzybq6().equalsIgnoreCase(objectEntity.getSjzybq6())) {
            isUpdate = true;
        }
        if (objectEntityOld.getObjectMemo() != null && objectEntityOld.getObjectMemo() != "") {
            if (objectEntityOld.getObjectMemo().equalsIgnoreCase(objectEntity.getObjectMemo())) {
                isUpdate = isUpdate && true;
            }
        }
        return isUpdate;
    }

    /**
     * 回填组织分类和来源分类的码值
     *
     * @param objectEntity
     */
    private void injectObjectPojoTable(ObjectEntity objectEntity, int factory, String versions) {
        // 如果是hive平台 则保存的 storageDataMode 的值为 hbase 如果是odps平台 则是 ads
        objectEntity.setStorageDataMode(getResType());
        objectEntity.setStoreType(KeyIntEnum.getKeyByNameAndType(objectEntity.getStorageDataMode(), Common.STORETYPE));
        objectEntity.setUpdateTime(new Date());
        if (objectEntity.getCreateTime() == null) {
            objectEntity.setCreateTime(new Date());
        }
        objectEntity.setOwnerFactory(String.valueOf(factory));
        if (objectEntity.getObjectId() != null && StringUtils.isBlank(objectEntity.getStorageTableStatus())) {
            objectEntity.setObjectState(KeyIntEnum.getKeyByNameAndType("未发布", Common.OBJECT_STATE));
        } else {
            objectEntity.setObjectState(KeyIntEnum.getKeyByNameAndType(objectEntity.getStorageTableStatus(), Common.OBJECT_STATE));
        }
        if (objectEntity.getIsActiveTable() == null) {
            objectEntity.setIsActiveTable(1);
        }
        if (objectEntity.getStandardType() == null) {
            objectEntity.setStandardType(0);
        }
        String todayStr = DateUtil.formatDateTime(new Date(), DateUtil.DEFAULT_PATTERN_DATE_SIMPLE);
        objectEntity.setVersion(Integer.valueOf(todayStr));
        objectEntity.setVersions(StringUtils.isNotBlank(versions) ? versions : "1.0");

        // 数据组织分类的中文名 如果classIds不为空，则直接用classIds，否则用中文查询代码值
        String classIds = objectEntity.getClassIds();
        String classIds1 = "";  // 1级
        String classIds2 = "";  // 2级
        String classIds3 = "";  // 3级
        if (classIds.contains(",")) {
            // 一、二、三级的组织分类数组
            String[] list = classIds.split(",");
            String organizationClassifyPar = objectEntity.getOrganizationClassify().split("/")[0];
            if ("原始库".equals(organizationClassifyPar) || "主题库".equals(organizationClassifyPar)) {
                classIds1 = list[0].split(Common.DATA_ORGANIZATION_CODE)[1];
                classIds2 = list[1];
                if (list.length > 2) {
                    classIds3 = list[2].split(classIds2)[1];
                    if (StringUtils.isNotBlank(classIds1) && StringUtils.isNotBlank(classIds3)) {
                        objectEntity.setSjzzyjflVallue(classIds1);
                        objectEntity.setSjzzejflValue(classIds3);
                    }
                } else {
                    classIds3 = list[1].split(list[0])[1];
                    if (StringUtils.isNotBlank(classIds1) && StringUtils.isNotBlank(classIds3)) {
                        objectEntity.setSjzzyjflVallue(classIds1);
                        objectEntity.setSjzzejflValue(classIds3);
                    }
                }
            } else {
                // 非原始库时
                if (list.length != 0) {
                    classIds1 = list[0].split(Common.DATA_ORGANIZATION_CODE)[1];
                    classIds2 = list[1].split(list[0])[1];
                    objectEntity.setSjzzyjflVallue(StringUtils.isBlank(classIds1) ? "" : classIds1);
                    objectEntity.setSjzzejflValue(StringUtils.isBlank(classIds2) ? "" : classIds2);
                }
            }
        } else {
            // 当组织分类为其他的时候
            objectEntity.setSjzzyjflVallue(classIds.split(Common.DATA_ORGANIZATION_CODE)[1]);
        }
        // 数据来源分类的中文名
        String sourceClassIds = objectEntity.getSourceClassIds();
        if (StringUtils.isNotEmpty(sourceClassIds)) {
            List<String> sourceIdList = Arrays.asList(sourceClassIds.split(","));
            String sourceIds = sourceIdList.get(sourceIdList.size() - 1).split(sourceIdList.get(0))[1];
            objectEntity.setSjzylylxValue(sourceIds);
        }
        // 20200309 新增了 datatype这个参数
        // 原始库->0:源数据，资源库->2: 资源数据(知识库)，主题库->5:专题库，知识库->5:专题库，业务库->3：资源数据（行为日志库），业务要素索引库->无定义
        String primaryDatasourceCh = objectEntity.getOrganizationClassify().split("/")[0];
        if (primaryDatasourceCh.equalsIgnoreCase("原始库") || primaryDatasourceCh.equalsIgnoreCase("其他")) {
            objectEntity.setDataType(0);
        } else if (primaryDatasourceCh.equalsIgnoreCase("资源库")) {
            objectEntity.setDataType(2);
        } else if (primaryDatasourceCh.equalsIgnoreCase("主题库") || primaryDatasourceCh.equalsIgnoreCase("知识库")) {
            objectEntity.setDataType(5);
        } else if (primaryDatasourceCh.equalsIgnoreCase("业务库")) {
            objectEntity.setDataType(3);
        } else if (primaryDatasourceCh.equalsIgnoreCase("业务要素索引库")) {
            objectEntity.setDataType(4);
        } else {
            objectEntity.setDataType(0);
        }
        // 字段差别
        objectEntity.setDbSource("JZ_RESOURCE");
        objectEntity.setObjectFlag(1);
        objectEntity.setHdDbSource("JZ_RESOURCE");
        objectEntity.setHiveTableName(objectEntity.getRealTablename());
        objectEntity.setRelateTableName("OBJECTFIELD");
        objectEntity.setDeleted(0);
    }

    public String getResType() {
        String resType = Common.ALI_YUN;
        if (env.getProperty("isCreatTableTool").equalsIgnoreCase("true")) {
            return resType;
        }
        log.info(">>>>>>开始从数据仓库中获取本地仓库的数据库的类型");
        List<DataResource> dataResources = restTemplateHandle.getDataCenterVersion("2", "0");
        if (dataResources == null || dataResources.isEmpty()) {
            log.error(">>>>>>从数据仓库中获取到本地仓的数据为空");
            return resType;
        }
        // 可能odps、hive同时存在，如果出现odps就以odps为准，之后再找其它的
        List<String> resTypes = dataResources.stream().map(dataResource -> dataResource.getResType().toLowerCase()).distinct().collect(Collectors.toList());
        if (resTypes.contains(Common.HIVEHUAWEI) || resTypes.contains(Common.HIVECDH)) {
            resType = Common.HUA_WEI_YUN;
        } else if (resTypes.contains(Common.ODPS)) {
            resType = Common.ALI_YUN;
        } else {
            resType = Common.ALI_YUN;
        }
        log.info(">>>>>>获取到本地仓库的数据库类型为：" + JSONObject.toJSONString(resType));
        return resType;
    }

    /**
     * tableId自增1
     *
     * @return
     */
    public String getNewTableId(String oldTableId) {
        String[] tableIdArr = oldTableId.split("_");
        Integer tableidNum = Integer.parseInt(tableIdArr[tableIdArr.length - 1]);
        String tableidNumMax = String.format("%05d", tableidNum + 1);
        tableIdArr[tableIdArr.length - 1] = tableidNumMax;
        return String.join("_", tableIdArr);
    }

    @Override
    public boolean pushMetaInfo(String tableId) {
        boolean pushStatus = true;
        try {
            StandardFieldJson standardFieldJson = getAllStandardFieldJson(tableId);
            if (standardFieldJson == null) {
                return false;
            }
            PushMetaInfo pushMetaInfo = new PushMetaInfo();
            pushMetaInfo.setDb(standardFieldJson.getDb());
            List<MetaInfoDetail> field = new ArrayList<>();
            for (StandardFieldJson.StandardField standardField : standardFieldJson.getField()) {
                MetaInfoDetail metaInfoDetail = new MetaInfoDetail();
                metaInfoDetail.setCnname(standardField.getCnname());
                metaInfoDetail.setDbname(standardField.getDbname());
                metaInfoDetail.setEngname(standardField.getEngname());
                metaInfoDetail.setFieldno(String.valueOf(standardField.getFieldno()));
                metaInfoDetail.setFieldtype(standardField.getFieldtype());
                metaInfoDetail.setKey(standardField.getKey());
                metaInfoDetail.setLen(standardField.getLen());
                metaInfoDetail.setPartition(standardField.getPartition());
                metaInfoDetail.setType(standardField.getType());
                metaInfoDetail.setNotNull(standardField.getNotNull());
                field.add(metaInfoDetail);
            }
            pushMetaInfo.setField(field);
            pushMetaInfo.setProtocolcnname(standardFieldJson.getProtocolcnname());
            pushMetaInfo.setProtocolengname(standardFieldJson.getProtocolengname());
            pushMetaInfo.setSource(standardFieldJson.getSource());
            pushMetaInfo.setSys(standardFieldJson.getSys());
            pushMetaInfo.setSysengname(standardFieldJson.getSys());
            pushMetaInfo.setSyscnname(standardFieldJson.getSyscnname());
            pushMetaInfo.setTablename(standardFieldJson.getTablename());
            pushMetaInfo.setType(standardFieldJson.getType());
            List<PushMetaInfo> list = new ArrayList<>();
            list.add(pushMetaInfo);
            log.info(">>>>>>向标准化push传入参数：\n" + JSONObject.toJSONString(list));
            JSONObject responseToPage = restTemplateHandle.requestPushMetaInfo(list);
            log.info(">>>>>>向标准化push数据的结果为：\n" + JSONObject.toJSONString(responseToPage));
            if (responseToPage != null && responseToPage.getString("status").equalsIgnoreCase("OK")) {
                pushStatus = true;
            } else {
                pushStatus = false;
            }
        } catch (Exception e) {
            log.error(">>>>>>向标准化push失败：", e);
            return false;
        }
        return pushStatus;
    }

    @Override
    public DetectedTable getDetectedTableInfo(String resId, String project, String tableName) {
        if (StringUtils.isBlank(resId) && StringUtils.isBlank(project) && StringUtils.isBlank(tableName)) {
            throw SystemException.asSystemException(ErrorCodeEnum.CHECK_PARAMETER_ERROR, "调用仓库接口的参数为空");
        }
        DetectedTable tableDetectInfo = null;
        try {
            tableDetectInfo = restTemplateHandle.getTableDetectInfo(resId, project, tableName);
            //根据二级去码表回填一级
            FieldCodeEntity fieldCodeVal = fieldCodeMapper.selectOneSysName(tableDetectInfo.getAppCode());
            tableDetectInfo.setParentAppCode(fieldCodeVal.getCodeId());
            log.info(">>>>>>从数据仓库获取表的探查信息为：\n" + JSONObject.toJSONString(tableDetectInfo));
        } catch (Exception e) {
            log.error(">>>>>>从数据仓库获取表的探查信息报错：", e);
        }
        return tableDetectInfo;
    }

    @Override
    public List<ValueLabelVO> getAllSysList() {
        try {
            //查询源应用系统
            List<FieldCodeEntity> fieldCodes = fieldCodeMapper.selectOneSysNames();
            //筛选出源应用系统一级总共的名称列表
            List<String> primaryChList = fieldCodes.stream().filter(d -> StringUtils.isNotEmpty(d.getCodeText())).map(d -> (d.getCodeId() + "&&" + d.getCodeText())).distinct().collect(toList());
            //根据出源应用系统一级分组总共有多少数据
            Map<String, List<FieldCodeEntity>> primaryListMap = fieldCodes.stream().filter(d -> StringUtils.isNotEmpty(d.getCodeText())).collect(Collectors.groupingBy(d -> (d.getCodeId() + "&&" + d.getCodeText())));

            List<ValueLabelVO> valueLabelVOList = new ArrayList<>();
            for (String data : primaryChList) {
                ValueLabelVO valueLabelVO = new ValueLabelVO();
                valueLabelVO.setValue(data.split("&&")[0]);
                valueLabelVO.setLabel(data.split("&&")[1]);
                //获取当前源应用系统下的数据
                List<FieldCodeEntity> childrenList = primaryListMap.get(data);
                List<ValueLabelVO> children = new ArrayList<>();
                //源应用系统二级数据
                List<String> secondaryList = childrenList.stream().filter(d -> StringUtils.isNotEmpty(d.getValText())).map(d -> (d.getValValue() + "&&" + d.getValText())).distinct().collect(toList());
                for (String childrenData : secondaryList) {
                    ValueLabelVO valueLabelVO2 = new ValueLabelVO();
                    valueLabelVO2.setValue(childrenData.split("&&")[0]);
                    valueLabelVO2.setLabel(childrenData.split("&&")[1]);
                    children.add(valueLabelVO2);
                }
                valueLabelVO.setChildren(children);
                valueLabelVOList.add(valueLabelVO);
            }
            return valueLabelVOList;
        } catch (Exception e) {
            log.error(">>>>>>获取源应用系统名称下拉列表出错：", e);
            return new ArrayList<>();
        }
    }

    @Override
    public StandardFieldJson getAllStandardFieldJson(String tableId) {
        StandardFieldJson standardFieldJson = new StandardFieldJson();
        try {
            List<StandardFieldJson.StandardField> standardFields = new ArrayList<>();
            // 现根据objectId获取对应的所有字段信息
            List<ObjectFieldEntity> objectFieldEntities = queryObjectFieldListByTableId(tableId);
            if (objectFieldEntities != null && !objectFieldEntities.isEmpty()) {
                for (ObjectFieldEntity oneColumn : objectFieldEntities) {
                    StandardFieldJson.StandardField oneColumnStandard = new StandardFieldJson.StandardField();
                    if (StringUtils.isEmpty(oneColumn.getFieldId())) {
                        oneColumnStandard.setKey(oneColumn.getColumnName());
                    } else {
                        oneColumnStandard.setKey(oneColumn.getFieldId());
                    }
                    oneColumnStandard.setFieldno(oneColumn.getRecno());
                    if (StringUtils.isEmpty(oneColumn.getFieldName())) {
                        oneColumnStandard.setEngname(oneColumn.getColumnName());
                    } else {
                        oneColumnStandard.setEngname(oneColumn.getFieldName());
                    }
                    oneColumnStandard.setDbname(oneColumn.getColumnName());
                    oneColumnStandard.setCnname(oneColumn.getFieldChineseName() == null ? "" : oneColumn.getFieldChineseName());
                    oneColumnStandard.setLen(oneColumn.getFieldLen());
                    if (oneColumn.getFieldType() == null || oneColumn.getFieldType() == -1) {
                        oneColumnStandard.setFieldtype(0);
                    } else {
                        int fieldType = KeyIntEnum.getKeyByNameAndType(String.valueOf(oneColumn.getFieldType()), Common.DATAPROCESS);
                        oneColumnStandard.setFieldtype(fieldType);
                    }
                    oneColumnStandard.setPartition(oneColumn.getPartitionRecno() == null ? 0 : oneColumn.getPartitionRecno());
                    oneColumnStandard.setNotNull(oneColumn.getNeedValue());
                    standardFields.add(oneColumnStandard);
                }
            }
            standardFieldJson.setField(standardFields);
            // 然后tableId获取对应的 详细的 数据信息
            ObjectEntity objectEntity = queryObjectDetail(tableId);
            standardFieldJson.setType(1);
            standardFieldJson.setSys(objectEntity.getCodeTextTd());
            standardFieldJson.setSyscnname(objectEntity.getCodeTextCh());
            standardFieldJson.setProtocolengname(objectEntity.getTableId());
            standardFieldJson.setProtocolcnname(objectEntity.getDataSourceName());
            standardFieldJson.setSource(objectEntity.getOwnerFactoryCode());
            standardFieldJson.setTablename(objectEntity.getRealTablename());
            standardFieldJson.setDb(0);
        } catch (Exception e) {
            log.error("获取标准化字段信息出错：", e);
        }
        return standardFieldJson;
    }

    @Override
    public DataResourceRawInformationVO getDataResourceInformation(String dataId, String project, String tableName) {
        DataResourceRawInformationVO dataResourceRawInformation = new DataResourceRawInformationVO();
        try {
            DetectedTable tableDetectInfo = restTemplateHandle.getTableDetectInfo(dataId, project, tableName);
            DataResource dataResourceInfo = restTemplateHandle.getResourceById(dataId);
            if (dataResourceInfo != null) {
                tableDetectInfo.setCenterId(dataResourceInfo.getCenterId());
                tableDetectInfo.setCenterName(dataResourceInfo.getCenterName());
                tableDetectInfo.setResName(dataResourceInfo.getResName());
                tableDetectInfo.setResType(dataResourceInfo.getResType());
            }
            //获取对应表结构信息
            List<FieldInfo> fieldInfos = restTemplateHandle.requestGetTableStructure(dataId, project, tableName);
            if (tableDetectInfo == null || fieldInfos == null) {
                return dataResourceRawInformation;
            }
            if (StringUtils.isEmpty(tableDetectInfo.getAppName())) {
                dataResourceRawInformation.setObjectId("");
                dataResourceRawInformation.setTableNameObject("");
            } else {
                ObjectEntity objectEntity = SelectUtil.getObjectEntityByTableId(objectMapper, tableDetectInfo.getSourceCode());
                Integer objectID = objectEntity.getObjectId();
                if (objectID != null) {
                    dataResourceRawInformation.setObjectId(String.valueOf(objectID));
                    dataResourceRawInformation.setTableNameObject(objectEntity.getRealTablename());
                } else {
                    dataResourceRawInformation.setObjectId("");
                    dataResourceRawInformation.setTableNameObject("");
                }
            }
            // 20200518 新增一个字段信息 新增数据源来源
            dataResourceRawInformation.setDataResourceOriginIds(tableDetectInfo.getSourceClassify());
            if (StringUtils.isNotEmpty(dataResourceRawInformation.getDataResourceOriginIds())) {
                // 如果数据来源不是空，则需要查询数据来源分类的中文信息
//                KeyValueVO rDataResourceOrigin = objectMapper.getDataResourceOrigin();
//                dataResourceRawInformation.setDataResourceOriginIds(rDataResourceOrigin.getValue());
//                dataResourceRawInformation.setDataResourceOrigin(rDataResourceOrigin.getLabel());
            }

            // 20200402 所属单位改成这个字段
            if (StringUtils.isEmpty(tableDetectInfo.getAppManageUnit())) {
                dataResourceRawInformation.setSourceFirmCh("全部");
                dataResourceRawInformation.setSourceFirmCode("0");
            } else {
                dataResourceRawInformation.setSourceFirmCh(tableDetectInfo.getAppManageUnit());
                int manufacturer = KeyIntEnum.getKeyByNameAndType(tableDetectInfo.getAppManageUnit(), Common.MANUFACTURER_NAME);
                dataResourceRawInformation.setSourceFirmCode(String.valueOf(manufacturer));
            }
            dataResourceRawInformation.setDataSourceName(tableDetectInfo.getTableNameEN());
            dataResourceRawInformation.setSourceId(tableDetectInfo.getSourceCode());
            dataResourceRawInformation.setResName(tableDetectInfo.getResName());
            dataResourceRawInformation.setResType(tableDetectInfo.getResType());
            dataResourceRawInformation.setCenterId(tableDetectInfo.getCenterId());
            dataResourceRawInformation.setCenterName(tableDetectInfo.getCenterName());
            //  所属应用系统中文名/代码
            dataResourceRawInformation.setSourceProtocolCh(tableDetectInfo.getAppName());
            dataResourceRawInformation.setSourceProtocolCode(tableDetectInfo.getAppCode());
            dataResourceRawInformation.setSourceTableName(tableDetectInfo.getProjectName() + "." + tableDetectInfo.getTableNameEN());
            dataResourceRawInformation.setDataBaseId(dataId);
            dataResourceRawInformation.setProject(tableDetectInfo.getProjectName());
            // 数据安全分级
            dataResourceRawInformation.setDataLevel(tableDetectInfo.getDataLevel());
            dataResourceRawInformation.setTableNameCh(tableDetectInfo.getTableNameCN());

            List<DataResourceRawInformationVO.FieldColumn> columnList = new ArrayList<>();
            if (fieldInfos != null && fieldInfos.size() > 0) {
                for (FieldInfo tableField : fieldInfos) {
                    DataResourceRawInformationVO.FieldColumn oneFieldColumn = new DataResourceRawInformationVO.FieldColumn();
                    oneFieldColumn.setFieldName(tableField.getFieldName());
                    oneFieldColumn.setFieldDescription(tableField.getComments());
                    oneFieldColumn.setFieldType(tableField.getType());
                    oneFieldColumn.setIFieldType("");
                    oneFieldColumn.setFieldLength(String.valueOf(tableField.getLength()));
                    oneFieldColumn.setIsNonnull(String.valueOf(tableField.getNullAble()));
                    oneFieldColumn.setIsPrimarykey(String.valueOf(tableField.getIsPrimaryKey()));
                    columnList.add(oneFieldColumn);
                }
                dataResourceRawInformation.setFieldList(columnList);
            } else {
                dataResourceRawInformation.setFieldList(columnList);
            }
        } catch (Exception e) {
            log.error(">>>>>>从数据仓库接口获取原始数据的详细信息报错：", e);
        }
        return dataResourceRawInformation;
    }

    @Override
    public List<String> createObjectTableSuggest(String mainValue, String firstValue, String secondaryValue, String condition) {
        try {
            String threeClassifyValue = "";
            String secondaryClassifyValue = "";
            if (secondaryValue.contains("/")) {
                threeClassifyValue = secondaryValue.split("/")[1];
                secondaryClassifyValue = secondaryValue.split("/")[0];
            } else {
                threeClassifyValue = "";
                secondaryClassifyValue = secondaryValue;
            }
            // 20200224：目前主分类无效，只有数据组织分类一种
            return objectMapper.createObjectTableSuggest(mainValue, firstValue, secondaryClassifyValue, threeClassifyValue, condition);
        } catch (Exception e) {
            log.error(">>>>>>获取表名的提示信息报错：", e);
            return new ArrayList<>();
        }
    }

    @Override
    public List<String> getSchemaApproved(String dataId) {
        ArrayList<String> list = new ArrayList<>();
        try {
            if (env.getProperty("isCreatTableTool").equalsIgnoreCase("true")){
                list.add("test");
                return list;
            }
            List<ProjectInfo> result = restTemplateHandle.getProjectList(dataId);
            if (result == null) {
                list.add("test");
            }
            for (ProjectInfo data : result) {
                list.add(data.getProjectName());
            }
        } catch (Exception e) {
            ServerResponse.asErrorResponse();
        }
        return list;
    }

    @Override
    public List<KeyValueVO> getDetectedTablesNameInfo(String resId, String projectName, String type) {
        List<KeyValueVO> list = new ArrayList<>();
        try {
            log.info(">>>>>>新增来源关系时，查询的参数为:resId={},projectName={},type={}", resId, projectName, type);
            //调用仓库接口
            List<DetectedTable> detectedTableList = restTemplateHandle.getDetectedTables(resId, projectName);
            for (DetectedTable detectedTable : detectedTableList) {
                //过滤当前数据源类型的已探查表信息
                if (detectedTable.getResType().equalsIgnoreCase(type)) {
                    KeyValueVO oneValue = new KeyValueVO(detectedTable.getProjectName(), detectedTable.getTableNameEN(), detectedTable.getResId());
                    list.add(oneValue);
                }
            }
        } catch (Exception e) {
            log.error(">>>>>>获取已探查表信息出错：", e);
        }
        return list;
    }

    @Override
    public String getIsExitsFiledMessage(String fieldId, String fieldName) {
        String message = "";
        try {
            log.info(String.format(">>>>>>判断是否为标准字段信息fieldId：%s，fieldName：%s", fieldId, fieldName));
            if (StringUtils.isEmpty(fieldId)) {
                throw new Exception("fieldId值为空，不是标准库中的元素编码");
            }
            if (StringUtils.isEmpty(fieldName)) {
                throw new Exception("fieldName值为空，不是标准库中的标准列名");
            }
//            queryWrapper.eq(SynlteFieldEntity::getFieldId, fieldId);
//            long fieldIdCount = synlteFieldMapper.selectCount(queryWrapper);
//            if (fieldIdCount == 0) {
//                message = String.format("元素编码错误：【%s】不是标准库中的元素编码,不能保存", fieldId);
//                throw new Exception(message);
//            }
//            queryWrapper = Wrappers.lambdaQuery();
            LambdaQueryWrapper<SynlteFieldEntity> queryWrapper = Wrappers.lambdaQuery();
            queryWrapper.eq(SynlteFieldEntity::getFieldName, fieldName);
            long fieldNameCount = synlteFieldMapper.selectCount(queryWrapper);
            if (fieldNameCount == 0) {
                message += String.format("标准列名错误：【%s】不是标准库中的标准列名,不能保存", fieldName);
                throw new Exception(message);
            }
            log.info(message);
        } catch (Exception e) {
            log.error(">>>>>>判断标准字段是否正确报错：", e);
        }
        return message;
    }

    @Override
    public String getOrganizationTableId(String dataSourceClassify, String code) {
        try {
            log.info(String.format(">>>>>>原始库数据获取tableId的信息dataSourceClassify：%s，6位行政区划代码：%s", dataSourceClassify, code));
            // 如果code为null，则获取默认行政区划
            if (StringUtils.isBlank(code)) {
                code = getDefaultXZQH();
            }
            // 根据数据来源分类信息（中文）获取分类的代码值，如果dataSourceClassify为代码则不用查询数据库
            String[] sourceList = dataSourceClassify.split("\\/");
            String secondClass = "";
            if (sourceList.length == 1 || isNumeric(dataSourceClassify)) {
                secondClass = dataSourceClassify;
            } else if (sourceList.length > 1) {
                secondClass = objectMapper.findCheckTableNameImformationThree(sourceList[1], 2);
            } else {
                secondClass = objectMapper.findCheckTableNameImformationThree(sourceList[0], 1);
            }
            String tableIdStr = String.format("GA_RESOURCE_%s_%s", secondClass, code);
            // 5位流水号从00001开始，获取数据库中的所有tableId的情况
            final String classMesaage = secondClass + "_" + code;
            List<ObjectEntity> objectEntities = objectMapper.selectList(Wrappers.lambdaQuery());
            List<String> tableIdList = objectEntities.stream().map(entity -> entity.getTableId()).distinct().collect(Collectors.toList());
            OptionalInt maxId = tableIdList.stream().filter(d -> {
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
            DecimalFormat format = new DecimalFormat("00000");
            tableIdStr = String.format("%s_%s", tableIdStr, format.format(maxId.orElse(0) + 1));
            return tableIdStr;
        } catch (Exception e) {
            log.error(">>>>>>原始库数据获取tableId的信息报错：", e);
            return "";
        }
    }

    public static boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String getDefaultXZQH() {
        String defaultXzqh = "";
        try {
            defaultXzqh = env.getProperty("defaultUnitCode");
        } catch (Exception e) {
            log.error(">>>>>>获取默认行政区划代码报错：", e);
        }
        return defaultXzqh;
    }

    @Override
    public List<KeyValueVO> getDataCenter() {
        ArrayList<KeyValueVO> list = new ArrayList<>();
        try {
            if (env.getProperty("isCreatTableTool").equalsIgnoreCase("true")){
                list.add(new KeyValueVO("test", "测试"));
                return list;
            }
            List<DataResource> dataCenters = restTemplateHandle.getDataCenter("0");
            if (dataCenters == null || dataCenters.isEmpty()) {
                return list;
            }
            for (DataResource data : dataCenters) {
                list.add(new KeyValueVO(data.getCenterId(), data.getCenterName()));
            }
        } catch (Exception e) {
            log.error(">>>>>>查询仓库数据中心出错：", e);
        }
        return list;
    }

    @Override
    public List<KeyValueVO> getDataResourceNameByCenterId(String centerId, String type) {
        List<KeyValueVO> keyValueVOList = new ArrayList<>();
        try {
            if (env.getProperty("isCreatTableTool").equalsIgnoreCase("true")){
                keyValueVOList.add(new KeyValueVO("test", "测试"));
                return keyValueVOList;
            }
            if (StringUtils.isBlank(centerId) || StringUtils.isBlank(type)) {
                log.error(">>>>>>根据数据中心获取数据源时传递的参数为空");
                return keyValueVOList;
            }
            List<DataResource> dataResourcesList = restTemplateHandle.getDataResourceByCenterId(centerId, "0");
            if (dataResourcesList == null) {
                return keyValueVOList;
            }
            //过滤出数据源类型为指定值时的数据源信息
            for (DataResource data : dataResourcesList) {
                if (data.getResType().equalsIgnoreCase(type)) {
                    keyValueVOList.add(new KeyValueVO(data.getResId(), data.getResName()));
                }
            }
//            //debug
//            if (type.equalsIgnoreCase("adb") || type.equalsIgnoreCase("datahub")){
//                keyValueVOList.add(new KeyValueVO("adb", "测试"));
//            }
        } catch (Exception e) {
            log.error(">>>>>>查询仓库数据中心出错：", e);
        }
        return keyValueVOList;
    }

    @Override
    public List<KeyValueVO> getDataResourceNameByType(String type) {
        List<KeyValueVO> keyValueVOList = new ArrayList<>();
        try {
            List<DataResource> dataResourcesList = restTemplateHandle.getDataCenterVersion("0", "0");
            if (dataResourcesList == null) {
                return keyValueVOList;
            }
            for (DataResource data : dataResourcesList) {
                if (data.getResType().equalsIgnoreCase(type)) {
                    keyValueVOList.add(new KeyValueVO(data.getResId(), data.getResName()));
                }
            }
        } catch (Exception e) {
            log.error(">>>>>>查询仓库数据中心出错：", e);
        }
        return keyValueVOList;
    }

    @Override
    public Long searchObjectBySourceId(String tableId) {
        long tableIdCount = 0;
        try {
            LambdaQueryWrapper<ObjectEntity> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(ObjectEntity::getTableId, tableId);
            tableIdCount = objectMapper.selectCount(wrapper);
        } catch (Exception e) {
            log.error(">>>>>>根据tableId获取object数据量出错：", e);
        }
        return tableIdCount;
    }

    @Override
    public Boolean queryTableIsExist(String tableName) {
        Boolean exitsFlag = false;
        try {
            if (env.getProperty("isCreatTableTool").equalsIgnoreCase("true")){
                return exitsFlag;
            }
            log.info(">>>>>>开始查询表:" + tableName + "是否已经存在");
            if (StringUtils.isBlank(tableName)) {
                return exitsFlag;
            }
            log.info("开始在syndmg_table_all表中查询表是否存在");
            List<DetectedTable> list = restTemplateHandle.getTableImformationList();
            if (list == null) {
                return exitsFlag;
            }
            long countCreated = list.stream().filter(d -> {
                return StringUtils.isNotBlank(d.getTableNameEN())
                        && d.getTableNameEN().equalsIgnoreCase(tableName);
            }).count();
            long assetsCount = Long.bitCount(countCreated);

            LambdaQueryWrapper<DsmStandardTableCreatedEntity> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(DsmStandardTableCreatedEntity::getTableName, tableName);
            assetsCount += dsmStandardTableCreatedMapper.selectCount(wrapper);
            if (assetsCount > 0) {
                exitsFlag = true;
            }
        } catch (Exception e) {
            log.error(">>>>>>获取分级分类信息报错：", e);
        }
        return exitsFlag;
    }

}
