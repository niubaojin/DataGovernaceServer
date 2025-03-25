package com.synway.datastandardmanager.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.synway.datastandardmanager.dao.master.ResourceManageAddDao;
import com.synway.datastandardmanager.dao.master.ResourceManageDao;
import com.synway.datastandardmanager.dao.master.StandardResourceManageDao;
import com.synway.datastandardmanager.interceptor.AuthorizedUserUtils;
import com.synway.datastandardmanager.pojo.*;
import com.synway.datastandardmanager.pojo.approvalInfo.ApprovalInfoParams;
import com.synway.datastandardmanager.pojo.enums.DataAcquisitionMethodType;
import com.synway.datastandardmanager.pojo.enums.DataResourceLocation;
import com.synway.datastandardmanager.pojo.enums.SynlteFieldType;
import com.synway.datastandardmanager.pojo.sourcedata.PublicDataInfo;
import com.synway.datastandardmanager.pojo.warehouse.DetectedTable;
import com.synway.datastandardmanager.pojo.warehouse.FieldInfo;
import com.synway.datastandardmanager.service.LabelsManageService;
import com.synway.datastandardmanager.service.ResourceManageAddService;
import com.synway.datastandardmanager.util.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@Service
public class ResourceManageAddServiceImpl implements ResourceManageAddService {
    private static Logger logger = LoggerFactory.getLogger(ResourceManageAddServiceImpl.class);
    @Autowired
    private ResourceManageAddDao resourceManageAddDao;
    @Autowired
    private ResourceManageDao resourceManageDao;
    @Autowired
    private StandardResourceManageDao standardResourceManageDao;
    @Autowired
    private RestTemplateHandle restTemplateHandle;
    @Autowired
    ConcurrentHashMap<String, Boolean> switchHashMap;

    /**
     * 获取所有的来源数据源（表）的信息，并将结果拼接成 select 格式
     *
     * @return List<PageSelectOneValue>
     */
//    @Override
//    public List<PageSelectOneValue> getAllDataSourceLocation() {
//        List<PageSelectOneValue> allResultList = null;
//        try{
//            String allClassifyInterfaceString = restTemplate.getForObject("http://DATARESOURCE/classify-interface/interface/getAllData",String.class);
//
//        }catch (Exception e){
//            logger.error("获取来源数据源信息报错"+ ExceptionUtil.getExceptionTrace(e));
//        }
//        allResultList = new ArrayList<PageSelectOneValue>();
//        PageSelectOneValue pageSelectOneValue1 = new PageSelectOneValue("uuid11111","czrkjbxx");
//        PageSelectOneValue pageSelectOneValue2 = new PageSelectOneValue("uuid22222","zhhhhhhh");
//        allResultList.add(pageSelectOneValue1);
//        allResultList.add(pageSelectOneValue2);
//        logger.info("获取到的数据为"+ JSONObject.toJSONString(allResultList));
//        return allResultList;
//    }
//
//    @Override
//    public List<PageSelectOneValue> getAllStorageDataSourceSelect() {
//        List<PageSelectOneValue> allResultList = null;
//        try{
//            String allClassifyInterfaceString = restTemplate.getForObject("http://DATARESOURCE/classify-interface/interface/getAllData",String.class);
//
//        }catch (Exception e){
//            logger.error("获取来源数据源信息报错"+ ExceptionUtil.getExceptionTrace(e));
//        }
//        allResultList = new ArrayList<PageSelectOneValue>();
//        PageSelectOneValue pageSelectOneValue1 = new PageSelectOneValue("数据源1","数据源1");
//        PageSelectOneValue pageSelectOneValue2 = new PageSelectOneValue("数据源2","数据源2");
//        allResultList.add(pageSelectOneValue1);
//        allResultList.add(pageSelectOneValue2);
//        logger.info("获取到的数据为"+ JSONObject.toJSONString(allResultList));
//        return allResultList;
//    }
//
//    @Override
//    public List<String> initManufacturer(){
//        return ManufacturerName.getAllManufacturerName();
//    }
//
//    @Override
//    public List<Sys> initProtocolNum(){//查询object表的data_source字段
//        List<Sys> dataList = resourceManageAddDao.getAllProtocolNum();
//        return dataList;
//    }
    @Override
    public String checkAndGetTableID(String sourceID) {
        List<String> list = new ArrayList<String>();
        //根据源协议，查找目标协议
        List<InputObjectCreate> result = standardResourceManageDao.getAllInputObjectRelation(null);
        InputObjectCreate targetObject = null;
        for (InputObjectCreate inputObjectCreate : result) {
            if (inputObjectCreate.getInputObjEngName().equalsIgnoreCase(sourceID)) {
                targetObject = inputObjectCreate;
                break;
            }
        }
        return targetObject != null ? targetObject.getOutObjEngName() : null;
    }

    //    @Transactional
    @Override
    public List<SourceFieldInfo> initSourceFieldTable(String sourceProtocol, String tableName, String sourceSystem, String sourceFirm, String tableId) {
        List<SourceFieldInfo> resultList = new ArrayList<SourceFieldInfo>();
        //检查，如果已经有记录，则返回数据库中已经保存的内容
        SourceInfo sourceInfo = resourceManageAddDao.findSourceInfo(sourceProtocol, tableName, sourceSystem, sourceFirm);

        if (sourceInfo != null) {
            resultList = resourceManageAddDao.findSourceFieldInfoBySourceInfoID(sourceInfo.getId());
            resultList.stream().forEach(d -> {
                d.setColumnName(d.getFieldName());
                d.setFieldChineseName(d.getFieldDescription());
                d.setFieldLen(d.getFieldLength());
                if (StringUtils.isNotBlank(d.getFieldType())){
                    int fieldTypeCode = SynlteFieldType.getSynlteNumType(d.getFieldType().toLowerCase());
                    d.setFieldTypeCode(String.valueOf(fieldTypeCode));
                }
            });
            if (!resultList.isEmpty()) {
                return resultList.stream().sorted(comparing(d ->  d.getRecno())).collect(toList());
            }
        } else {
            //还没有数据的时候，就需要入库dwParams
            sourceInfo = SourceInfo.getInstance(sourceProtocol, tableName, sourceSystem, sourceFirm);
            resourceManageAddDao.insertSourceInfo(sourceInfo);
        }
        if (!StringUtils.isBlank(tableId)) {
            //如果不是流程的，也不是已经保存过的，那么就拿协议当源协议，到objectField中查找字段
            List<ObjectField> list = resourceManageDao.queryObjectField(tableId, null, null, null, null);
            for (ObjectField objectField : list) {
                String fieldTypeStr = "";
                try {
                    if (StringUtils.isNotEmpty(objectField.getFieldtypes())) {
                        fieldTypeStr = objectField.getFieldtypes();
                    } else {
                        // 将数字转换成 字段类型
                        fieldTypeStr = SynlteFieldType.getSynlteFieldType(Integer.valueOf(objectField.getFieldType()));
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
                SourceFieldInfo sourceFieldInfo = SourceFieldInfo.getInstance(sourceInfo.getId(),objectField.getRecno(), tableId, objectField.getFieldName(),
                        objectField.getFieldDescribe(), fieldTypeStr, String.valueOf(objectField.getFieldLen()),
                        objectField.getNeedv(), objectField.getIsIndex() == 0 ? "否" : "是", "否",
                        Integer.valueOf(objectField.getRecno()), objectField.getFieldId(), objectField.getFieldId(),
                        objectField.getGadsjFieldId(), objectField.getDeterminerId(), objectField.getElementId(),
                        objectField.getFieldChineseName(), String.valueOf(objectField.getFieldLen()));
                sourceFieldInfo.setColumnName(objectField.getColumnName());
                resultList.add(sourceFieldInfo);
            }
        }

        if (!resultList.isEmpty()) {
            resourceManageAddDao.insertSourceFieldInfo(resultList);
        }
        return resultList;
    }

    public String pickUpDWTableInfoData(String DWTableInfo) {
        Pattern pattern = Pattern.compile("\\[\\s{0,}\\{.+\\}\\s{0,}\\]");
        Matcher matcher = pattern.matcher(DWTableInfo);
        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }

    @Override
    public List<ObjectField> initStandardFieldTable(String tableID) {
        //根据tableID，查找出标准表结构
        List<ObjectField> list = resourceManageDao.queryObjectField(tableID, null, null, null, null);
        logger.info(list.toString());
        return list;
    }


    @Override
    public void saveSrcField(List<SourceFieldInfo> sourceFieldInfos, String tableID) {
        logger.error(sourceFieldInfos.toString());

        //插入之前顺序先好
        String objectID = resourceManageAddDao.getObjectIDByTableID(tableID);
//        Integer maxSeq = resourceManageAddDao.standardizeObjecttFieldWeb();
        Integer maxSeq = resourceManageAddDao.standardizeObjecttFieldWeb(objectID);
        Map<String, SourceFieldInfo> map = new HashMap<>();//用于过滤已经添加的字段
        for (SourceFieldInfo sourceFieldInfo : sourceFieldInfos) {
            try {
                String fieldType = sourceFieldInfo.getFieldType();
                Map<String, String> dbMapper = FieldTypeUtil.getFieldTypeMap("all", "standardize");
                if (fieldType == null) {
                    sourceFieldInfo.setiFieldType("-1");
                } else {
                    if (fieldType.contains("(")) {
                        fieldType = fieldType.split("[(]")[0];
                    }
                    String targetFieldType = dbMapper.get(fieldType.toLowerCase());
                    if (targetFieldType == null) {
                        sourceFieldInfo.setiFieldType("-1");
                    } else {
                        int typeNum = SynlteFieldType.getSynlteNumType(targetFieldType);
                        sourceFieldInfo.setiFieldType(String.valueOf(typeNum));
                    }
                }
                logger.info(fieldType);
                sourceFieldInfo.setSeq(++maxSeq);
                sourceFieldInfo.setTableId(objectID);
                sourceFieldInfo.setFieldId("unknown_" + String.valueOf(sourceFieldInfo.getSeq()));
                if (!StringUtils.isEmpty(sourceFieldInfo.getFieldName())) {
                    map.put(sourceFieldInfo.getFieldName().toUpperCase(), sourceFieldInfo);
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }

        if (!StringUtils.isBlank(objectID)) {//如果已经有了，则过滤已经添加的字段，避免重复
            List<ObjectField> objectFields = resourceManageDao.selectObjectFieldByObjectId(Long.valueOf(objectID));
            for (ObjectField objectField : objectFields) {
                logger.info(objectField.getColumnName());
                if (objectField.getColumnName() != null) {
                    SourceFieldInfo sourceFieldInfo = map.get(objectField.getColumnName().toUpperCase());
                    if (StringUtils.isEmpty(objectField.getColumnName())) {
                        continue;
                    }
                    if (map.get(objectField.getColumnName().toUpperCase()) != null) {
                        sourceFieldInfos.remove(sourceFieldInfo);
                    }
                } else {
                    logger.info("存在空字段");
                    SourceFieldInfo sourceFieldInfo = map.get(objectField.getFieldName().toUpperCase());
                    if (StringUtils.isEmpty(objectField.getFieldName())) {
                        continue;
                    }
                    if (map.get(objectField.getFieldName().toUpperCase()) != null) {
                        sourceFieldInfos.remove(sourceFieldInfo);
                    }
                }

            }
        }

        //将列表中的数据插入到objectfieldextend
        if (!sourceFieldInfos.isEmpty()) {
            resourceManageAddDao.insertObjectfieldextend(sourceFieldInfos);
        } else {
            logger.warn("导入源字段时，检测到字段都已经存在标准表中");
        }

    }

    /**
     * @param sourceProtocol
     * @param sourceSystem
     * @param sourceFirm
     * @param tableName
     * @param tableId
     * @param centerId
     * @return
     */
    @Override
    public String addTableColumnByEtl(String sourceProtocol, String sourceSystem, String sourceFirm,
                                      String tableName,String dataName, String tableId, String centerId,String centerName,
                                      String project, String resourceId) {
        //  根据数据仓库id和表名从数据仓库中调用接口查询字段信息
//        ServerResponse<String> serverResponse = null;
        String serverResponse;
        try {
            if(StringUtils.isBlank(sourceProtocol) && StringUtils.isBlank(sourceSystem) && StringUtils.isBlank(sourceFirm)
                && StringUtils.isBlank(tableName) && StringUtils.isBlank(tableId) && StringUtils.isBlank(centerId)
                && StringUtils.isBlank(project) && StringUtils.isBlank(resourceId)){
                return "来源信息为空";
            }
            if (StringUtils.isNotBlank(tableName) && tableName.contains(".") && !project.contains("/")) {
                tableName = tableName.split("[.]")[1];
            }

            // 20200818 获取的字段信息变成这个接口 因为新增了元素编码的字段
            List<FieldInfo> responseFieldList = restTemplateHandle.requestGetTableStructure(resourceId, project, tableName);
            logger.info("查询到的字段数量为：" + responseFieldList.size());

            List<SourceFieldInfo> resultList = new ArrayList<>();
            SourceInfo sourceInfo = resourceManageAddDao.findSourceInfo(sourceProtocol.toUpperCase(), tableName, sourceSystem, sourceFirm);
            if (sourceInfo == null) {
                //还没有数据的时候，就需要入库dwParams
//                sourceInfo = SourceInfo.getInstance(sourceProtocol.toUpperCase(), tableName, sourceSystem, sourceFirm);
//                resourceManageAddDao.insertSourceInfo(sourceInfo);
                sourceInfo = SourceInfo.getInstance(sourceProtocol.toUpperCase(), tableName,sourceSystem,sourceFirm,
                        dataName, resourceId, project, centerName, centerId);
                resourceManageAddDao.addSourceInfo(sourceInfo);
            }
            if (responseFieldList != null) {
                int i = 0;
                for (FieldInfo tableField : responseFieldList) {
                    if (StringUtils.isEmpty(String.valueOf(tableField.getLength())) && tableField.getType().contains("(")) {
                        tableField.setLength(Integer.parseInt(tableField.getType().split("\\(")[1].split("\\)")[0]));
                        tableField.setType(tableField.getType().split("\\(")[0]);
                    } else if (StringUtils.isEmpty(String.valueOf(tableField.getLength()))) {
                        tableField.setLength(0);
                    }
                    // 如果字段名或备注为空，则回填数据元名称
                    String tableNameCh = StringUtils.isNotBlank(tableField.getComments()) ? tableField.getComments() : tableField.getSynFieldName();
                    SourceFieldInfo sourceFieldInfo = SourceFieldInfo.getInstance(sourceInfo.getId(),tableField.getNo(), tableId, tableField.getFieldName(),
                            tableNameCh, tableField.getType(), String.valueOf(tableField.getLength()),
                            String.valueOf(tableField.getNullAble()), String.valueOf(tableField.getIsPrimaryKey()),
                            StringUtils.isEmpty(String.valueOf(tableField.getIsForeignKey())) ? "否" : String.valueOf(tableField.getIsForeignKey()),
                            i, String.valueOf(i), tableField.getSynFieldId(), tableField.getGadsjFieldId(),
                            tableField.getDeterminerId(), tableField.getElementId(), tableField.getComments(), String.valueOf(tableField.getLength()));
                    i = i + 1;
                    String columnLen = String.valueOf(tableField.getLength());
                    if (StringUtils.isNotEmpty(columnLen)) {
                        if (columnLen.length() > 6) {
                            sourceFieldInfo.setFieldLength("999999");
                        }
                    }
                    sourceFieldInfo.setFieldSourceType(1);
                    resultList.add(sourceFieldInfo);
                }
                if (resultList.size() > 0) {
                    //先删除字段表中指定的数据，然后再插入  根据 tableid来删除
                    logger.info("开始删除Source_Field_Info中字段信息");
                    resourceManageAddDao.deleteSourceFieldInfoBySourceInfoID(sourceInfo.getId());
                    logger.info("开始向表Source_Field_Info中插入字段信息");
                    resourceManageAddDao.insertSourceFieldInfo(resultList);
                } else {
                    logger.info("查询到的字段信息为空，不能进行插入操作");
                }
            }
            serverResponse = "字段信息插入成功";
        } catch (Exception e) {
            logger.error("添加字段信息报错" + ExceptionUtil.getExceptionTrace(e));
            serverResponse = "从数据仓库中获取字段信息报错";
        }
        return serverResponse;
    }

    /**
     * 添加需要导入的源字段信息
     * 20200817 多了一个元素编码 当存在这个数据时，从数据库中获取相关的数据，
     * 然后查询出标准字段信息，替换成对应的标准字段
     * 20210413  现场提供的需求
     * 1：如果标准字段长度>255，此处采用标准字段长度，否则将长度默认为255
     * 2：使用原始数据的业务中文，如果为空则使用标准字段的中文名
     *
     * @param sourceFieldInfos 需要添加的源字段信息
     * @param objectID         objectID
     * @param objectFieldList  页面上已经存在的字段信息
     * @return
     */
    @Override
    public List<ObjectField> getSourceFieldColumnListService(List<SourceFieldInfo> sourceFieldInfos, String objectID, List<ObjectField> objectFieldList) {
        //  查询数据库中的  最大字段顺序
        int maxRecnoInt = 0;
        int oldRecnoInt = 0;
        List<ObjectField> needInsertList = new ArrayList<>();
        if (!objectFieldList.isEmpty()) {
            Optional<Integer> maxRecno = objectFieldList.stream().map(ObjectField::getRecno).distinct().collect(toList()).stream().reduce(Integer::max);
            if (maxRecno.isPresent()) {
                maxRecnoInt = maxRecno.get() + 1;
                oldRecnoInt = maxRecno.get() + 1;
            }
        }
        logger.info("最大字段顺序为: " + maxRecnoInt);
        Map<String, ObjectField> map = new HashMap<>();//用于过滤已经添加的字段
        for (SourceFieldInfo sourceFieldInfo : sourceFieldInfos) {
            ObjectField objectField = null;
            try {
                String fieldType = sourceFieldInfo.getFieldType();
                Map<String, String> dbMapper = FieldTypeUtil.getFieldTypeMap("all", "standardize");
                if (fieldType == null) {
                    sourceFieldInfo.setiFieldType("-1");
                } else {
                    if (fieldType.contains("(")) {
                        fieldType = fieldType.split("[(]")[0];
                    }
                    String targetFieldType = dbMapper.get(fieldType.toLowerCase());
                    if (targetFieldType == null) {
                        sourceFieldInfo.setiFieldType("-1");
                    } else {
                        int typeNum = SynlteFieldType.getSynlteNumType(targetFieldType);
                        sourceFieldInfo.setiFieldType(String.valueOf(typeNum));
                    }
                }
                if (StringUtils.isNotBlank(sourceFieldInfo.getFieldName())) {
                    // 如果 fieldCode 里面的值不为空，则从数据库中查询对应的标准字段信息，
                    // 如果没有报错，则使用这个数据，如果报错，则还是使用原先的数据
                    objectField = JSONObject.parseObject(JSONObject.toJSONString(sourceFieldInfo), ObjectField.class);
                    objectField.setRecno(maxRecnoInt);
                    objectField.setUpdateStatus(Byte.valueOf("1"));
                    objectField.setFieldType(sourceFieldInfo.getiFieldType());
                    if (StringUtils.isBlank(sourceFieldInfo.getFieldLength()) ||
                            sourceFieldInfo.getFieldLength().equalsIgnoreCase("null")) {
                        objectField.setFieldLen(0);
                    } else if (NumberUtils.isNumber(sourceFieldInfo.getFieldLength())
                            && !sourceFieldInfo.getFieldLength().contains(".")) {
                        objectField.setFieldLen(Integer.parseInt(sourceFieldInfo.getFieldLength()));
                    } else if (NumberUtils.isNumber(sourceFieldInfo.getFieldLength())
                            && sourceFieldInfo.getFieldLength().contains(".")) {
                        objectField.setFieldLen(new BigDecimal(sourceFieldInfo.getFieldLength()).intValue());
                    } else {
                        objectField.setFieldLen(0);
                    }
                    // 以下该值的默认值不能为 null
                    objectField.setClustRecnoStatus(false);
                    objectField.setPkRecnoStatus(false);
                    objectField.setMd5IndexStatus(false);
                    // 如果为true，表示是从数据库中获取的标准字段
                    boolean flag = getStandardFieldByFieldCode(sourceFieldInfo.getFieldCode(), objectField, sourceFieldInfo.getFieldName()
                            , sourceFieldInfo.getFieldDescription());
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
                            objectField.setOdpsPattition("1");
                        }
                    }
                    try {
                        objectField.setNeedValue(Integer.parseInt(sourceFieldInfo.getIsNonnull()));
                        objectField.setIsIndex(Integer.parseInt(sourceFieldInfo.getIsNonnull()));
                    } catch (Exception e) {
                        logger.error(ExceptionUtil.getExceptionTrace(e));
                    }
                    maxRecnoInt++;
                    map.put(sourceFieldInfo.getFieldName().toUpperCase(), objectField);
                    needInsertList.add(objectField);
                }
            } catch (Exception e) {
                needInsertList.add(objectField);
                logger.error("拼接需要添加的字段信息报错" + ExceptionUtil.getExceptionTrace(e));
            }
        }
        for (ObjectField objectPageField : objectFieldList) {
            logger.info("页面中的字段信息为：" + JSONObject.toJSONString(objectPageField));
            if (objectPageField.getColumnName() != null) {
                ObjectField sourceFieldObject = map.get(objectPageField.getColumnName().toUpperCase());
                if (StringUtils.isEmpty(objectPageField.getColumnName())) {
                    continue;
                }
                if (map.get(objectPageField.getColumnName().toUpperCase()) != null) {
                    needInsertList.remove(sourceFieldObject);
                }
            } else {
                logger.info("存在空字段");
                ObjectField sourceFieldObject = map.get(objectPageField.getColumnName().toUpperCase());
                if (StringUtils.isEmpty(objectPageField.getColumnName())) {
                    continue;
                }
                if (map.get(objectPageField.getColumnName().toUpperCase()) != null) {
                    needInsertList.remove(sourceFieldObject);
                }
            }
        }
        for (ObjectField objectField : needInsertList) {
            objectField.setRecno(oldRecnoInt);
            oldRecnoInt++;
        }
        logger.info("需要插入的字段信息为： " + JSONObject.toJSONString(needInsertList));
        return needInsertList;
    }

    /**
     * 20210401
     *
     * @param fieldCode
     * @param objectField
     * @param columnName
     * @param fieldDescription
     * @return
     */
    private boolean getStandardFieldByFieldCode(String fieldCode, ObjectField objectField,
                                                String columnName, String fieldDescription) {
        boolean flag = false;
        try {
            if (StringUtils.isBlank(fieldCode)) {
                throw new NullPointerException("元素编码为空，不需要查询标准字段");
            }
            Synltefield synltefield = resourceManageDao.getStandardFieldByFieldCode(fieldCode);
            objectField.setFieldId(synltefield.getFieldid());
            objectField.setFieldName(synltefield.getFieldname());
            if (StringUtils.isNotBlank(fieldDescription) &&
                    !StringUtils.equalsIgnoreCase(fieldDescription, "null") &&
                    !StringUtils.equalsIgnoreCase(fieldDescription, synltefield.getFieldchinesename())) {
                objectField.setFieldChineseName(fieldDescription);
            } else {
                objectField.setFieldChineseName(synltefield.getFieldchinesename());
            }
            objectField.setFieldDescribe(synltefield.getFielddescribe());
            // 这种表示都为原始库数据
            objectField.setFieldSourceType(1);
            int columnLen = Integer.parseInt(String.valueOf(synltefield.getFieldlen()));
            int originalFieldLen = objectField.getFieldLen();
            objectField.setFieldLen(Math.max(columnLen, originalFieldLen));
            objectField.setColumnName(columnName);
            if (StringUtils.isEmpty(objectField.getFieldType()) || "-1".equals(objectField.getFieldType())) {
                objectField.setFieldType(String.valueOf(synltefield.getFieldtype()));
            }
            // 新增字段分类的信息
            //  获取 表字段信息的 字段分类信息  synltefield. FIELD_CLASS 这个字段里面
            List<Map<String, String>> list = resourceManageDao.getCodeTextAndCodeidByObjectField(objectField.getFieldId());
            if (!list.isEmpty()) {
                Map<String, String> map = list.get(0);
                if (map != null) {
                    String codeText = map.getOrDefault("codeText", "");
                    String codeId = map.getOrDefault("codeId", "");
                    String fieldClass = map.getOrDefault("fieldClass", "");
                    String fieldClassCh = map.getOrDefault("fieldClassCh", "");
                    String sameWordType = map.getOrDefault("sameWordType", "");
                    objectField.setCodeText(StringUtils.isBlank(codeText) ? "" : codeText);
                    objectField.setCodeid(StringUtils.isBlank(codeId) ? "" : codeId);
                    objectField.setFieldClassId(StringUtils.isBlank(fieldClass) ? "" : fieldClass);
                    objectField.setFieldClassCh(StringUtils.isBlank(fieldClassCh) ? "" : fieldClassCh);
                    objectField.setSameWordType(StringUtils.isBlank(sameWordType) ? "" : sameWordType);
                }
            }
            objectField.setSensitivityLevel(StringUtils.isBlank(synltefield.getSecretClass()) ? "" : synltefield.getSecretClass());
            objectField.setSensitivityLevelCh(StringUtils.isBlank(synltefield.getSecretClassCh()) ? "" : synltefield.getSecretClassCh());
            flag = true;
        } catch (Exception e) {
            logger.error("元素编码为" + fieldCode + " 错误原因" + e.getMessage());
            flag = false;
        }
        return flag;

    }


    /**
     * 获取二级分类的所有信息()
     *
     * @param mainClassify      1：数据组织  2：数据来源  3：数据资源(去除)
     * @param primaryClassifyCh "" ：全部分类目前只有数据组织这个主分类  其它为具体的中文名称
     * @return
     */
    @Override
    public List<LayuiClassifyPojo> getSecondaryClassLayuiService(String mainClassify, String primaryClassifyCh) {
        logger.info("查询的参数为， mainClassify：" + mainClassify + " primaryClassifyCh：" + primaryClassifyCh);
        mainClassify = "数据组织分类";
        List<StandardTableRelation> standardTableRelations = resourceManageAddDao.getClassifyObject(mainClassify, primaryClassifyCh);
        List<LayuiClassifyPojo> layuiClassifyPojoList = new ArrayList<>();
        List<String> secondaryChList = standardTableRelations.stream().filter(d -> StringUtils.isNotEmpty(d.getSecondaryClassifyCh()))
                .map(StandardTableRelation::getSecondaryClassifyCh).distinct().collect(toList());
        Map<String, List<StandardTableRelation>> stringListMap = standardTableRelations.stream().filter(d -> StringUtils.isNotEmpty(d.getSecondaryClassifyCh()))
                .collect(Collectors.groupingBy(StandardTableRelation::getSecondaryClassifyCh));
        for (String secondaryifyCh : secondaryChList) {
            LayuiClassifyPojo layuiClassifyPojo = new LayuiClassifyPojo();
            layuiClassifyPojo.setValue(secondaryifyCh);
            layuiClassifyPojo.setLabel(secondaryifyCh);
            List<StandardTableRelation> childrenList = stringListMap.get(secondaryifyCh);
            List<LayuiClassifyPojo> ChildrenLayuiClassifyList = new ArrayList<>();
            for (StandardTableRelation standardTableRelation : childrenList) {
                if (StringUtils.isNotEmpty(standardTableRelation.getThreeClassifyCh())) {
                    LayuiClassifyPojo layuiClassifyChildrenPojo = new LayuiClassifyPojo();
                    layuiClassifyChildrenPojo.setLabel(standardTableRelation.getThreeClassifyCh());
                    layuiClassifyChildrenPojo.setValue(standardTableRelation.getThreeClassifyCh());
                    layuiClassifyChildrenPojo.setChildren(new ArrayList<>());
                    ChildrenLayuiClassifyList.add(layuiClassifyChildrenPojo);
                }
            }
            layuiClassifyPojo.setChildren(ChildrenLayuiClassifyList);
            layuiClassifyPojoList.add(layuiClassifyPojo);
        }
        logger.info("查询到的结果为：" + JSONObject.toJSONString(layuiClassifyPojoList));
        return layuiClassifyPojoList;
    }

    @Override
    public String getEnFlagByChnType(String organizationClassifys,
                                     String sourceClassifys,
                                     String dataSourceName,
                                     Boolean flag) throws Exception {
        StringBuffer sbf = new StringBuffer();
        String[] organizations = organizationClassifys.split("/");
        String[] sourceList = sourceClassifys.split("/");
        // 先获取大类的数据
        String mainClass = "";
        if (organizations[0].equalsIgnoreCase("业务要素索引库")){
            mainClass = "ywys";
        }else if(organizations[0].equalsIgnoreCase("其他")){
            mainClass = "other";
        }else {
            mainClass = getPyFirst(organizations[0]);
        }
        sbf.append(mainClass).append("_");
        // 之后获取第二级的数据
        if (flag) {
            if (StringUtils.isNotEmpty(sourceClassifys)) {
                String secondClass = "";
                if (sourceList.length > 1) {
                    secondClass = resourceManageAddDao.findCheckTableNameImformationThree(sourceList[1], 2);
                } else {
                    secondClass = resourceManageAddDao.findCheckTableNameImformationThree(sourceList[0], 1);
                }
                if (sourceList.length == 1 || StringUtils.isNumeric(sourceClassifys)){
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
        return sbf.toString();
    }

    public String getPyFirst(String data) throws Exception {
        StringBuilder pinYin = new StringBuilder();
        if (StringUtils.isNotBlank(data)) {
            for (char c : data.toCharArray()){
                final String firstSpell = PinYinUtil.getFirstSpell(String.valueOf(c));
                if (StringUtils.isEmpty(firstSpell)){
                    pinYin.append(c);
                }else {
                    pinYin.append(firstSpell);
                }
            }
        }
        return pinYin.toString();
    }

    @Override
    public int checTableNamekIsExit(String realTableName, String objectId) {
        int flag;
        if (null == objectId || "".equals(objectId)) {
            List list = resourceManageAddDao.checTableNamekIsExit(realTableName);
            if (list.size() == 0) {
                flag = 0;
            } else {
                flag = 1;
            }
        } else {
            String tableNameObjectId = resourceManageAddDao.findObjectId(realTableName);
            if (null == tableNameObjectId) {
                flag = 0;
            } else if (objectId.equals(tableNameObjectId)) {
                flag = 0;
            } else {
                flag = 1;
            }
        }
        return flag;
    }

    /**
     * 从数据组织中添加的新的来源
     *
     * @param addTableName
     * @return
     */
    @Override
    public DataResourceRawInformation getOrganizationRelationByTableName(String addTableName) {
        DataResourceRawInformation oneObject = resourceManageAddDao.getOrganizationRelationDao(addTableName);
        return oneObject;
    }

    @Override
    public List<LayuiClassifyPojo> getAllClassifyLayuiService(String mainClassifyCh) {
        //  获取所有的分级分类信息
        List<StandardTableRelation> standardTableRelations = resourceManageAddDao.getAllClassify(mainClassifyCh, "");
        List<LayuiClassifyPojo> layuiClassifyPojoList = new ArrayList<>();
        List<String> primaryChList = standardTableRelations.stream().filter(d -> StringUtils.isNotEmpty(d.getPrimaryClassifyCh()))
                .map(d -> (d.getPrimaryClassifyId() + "&&" + d.getPrimaryClassifyCh())).distinct().collect(toList());
        Map<String, List<StandardTableRelation>> primaryListMap = standardTableRelations.stream().filter(d -> StringUtils.isNotEmpty(d.getPrimaryClassifyCh()))
                .collect(Collectors.groupingBy(d -> (d.getPrimaryClassifyId() + "&&" + d.getPrimaryClassifyCh())));
        for (String primaryClassify : primaryChList) {
            // 一级分类
            LayuiClassifyPojo primaryLayuiClassifyPojo = new LayuiClassifyPojo();
            primaryLayuiClassifyPojo.setValue(primaryClassify.split("&&")[0]);
            primaryLayuiClassifyPojo.setLabel(primaryClassify.split("&&")[1]);
            List<StandardTableRelation> secondaryClassifyList = primaryListMap.get(primaryClassify);
            List<LayuiClassifyPojo> secondaryLayuiClassifyList = new ArrayList<>();
            List<String> secondaryList = secondaryClassifyList.stream().filter(d -> StringUtils.isNotEmpty(d.getSecondaryClassifyCh()))
                    .map(d -> (d.getSecondaryClassifyId() + "&&" + d.getSecondaryClassifyCh())).distinct().collect(toList());
            Map<String, List<StandardTableRelation>> secondaryListMap = secondaryClassifyList.stream().filter(d -> StringUtils.isNotEmpty(d.getSecondaryClassifyCh()))
                    .collect(Collectors.groupingBy(d -> (d.getSecondaryClassifyId() + "&&" + d.getSecondaryClassifyCh())));
            //  二级分类
            for (String secondaryCh : secondaryList) {
                LayuiClassifyPojo secondaryLayuiClassifyPojo = new LayuiClassifyPojo();
                secondaryLayuiClassifyPojo.setValue(secondaryCh.split("&&")[0]);
                secondaryLayuiClassifyPojo.setLabel(secondaryCh.split("&&")[1]);
                //三级分类
                List<StandardTableRelation> threeClassifyList = secondaryListMap.get(secondaryCh);
                List<LayuiClassifyPojo> threeLayuiClassifyList = new ArrayList<>();
                for (StandardTableRelation standardTableRelation : threeClassifyList) {
                    if (StringUtils.isNotEmpty(standardTableRelation.getThreeClassifyCh())) {
                        LayuiClassifyPojo threelayuiClassifyPojo = new LayuiClassifyPojo();
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
//        logger.info("查询到的数据为："+JSONObject.toJSONString(layuiClassifyPojoList));
        return layuiClassifyPojoList;
    }

    /**
     * 从数据仓库中查询数据来源的信息
     *
     * @param dataId    数据仓库的 ID
     * @param tableName 数据仓库的表名
     * @return
     */
    @Override
    public PublicDataInfo getResourceTableDataService(String dataId, String project, String tableName) throws Exception {
        PublicDataInfo publicDataInfo = new PublicDataInfo();
        DetectedTable resourceTable = restTemplateHandle.getTableDetectInfo(dataId, project, tableName);

        // 获取 数据源来源的相关信息
        String classifyStr = resourceTable.getSourceClassify();
        // 数据来源二级分类 从数据库中获取对应的代码值
        if (StringUtils.isNotEmpty(classifyStr)) {
//            String oneClassifyCnStr = classifyStr.split("/")[0];
//            String twoClassifyCnStr = classifyStr.split("/")[1];
//            String oneClassifyStr = resourceManageAddDao.getCodeIdByName(oneClassifyCnStr);
//            String twoClassifyStr = "";
            String oneClassify = "";
            String twoClassify = "";
            PageSelectOneValue dataResourceClass = null;
            try {
                dataResourceClass = resourceManageAddDao.getDataRDataResourceOrigin(classifyStr);
                String dataResourceClassIds = dataResourceClass.getValue();
                twoClassify = dataResourceClassIds.split("/")[1];
            } catch (Exception e) {
                logger.error(ExceptionUtil.getExceptionTrace(e));
            }
            publicDataInfo.setSJZYLYLXVALUE(twoClassify);
//            publicDataInfo.setSJZYLYLX(StringUtils.isEmpty(twoClassifyStr)?"":twoClassifyStr);
//            publicDataInfo.setSJZYLYLX_CN(StringUtils.isEmpty(twoClassifyCnStr)?"":twoClassifyCnStr);
//            publicDataInfo.setSJZYLYYJLX_CN(StringUtils.isEmpty(oneClassifyCnStr)?"":oneClassifyCnStr);
//            publicDataInfo.setSJZZEJFL(StringUtils.isEmpty(oneClassifyStr)?"":oneClassifyStr);
        } else {
            String oneClassifyStr = "";
            try {
                oneClassifyStr = resourceManageAddDao.getCodeIdByName(classifyStr);
            } catch (Exception e) {
                logger.error(ExceptionUtil.getExceptionTrace(e));
            }
            publicDataInfo.setSJZYLYLXVALUE(oneClassifyStr);
            publicDataInfo.setSJZYLYLX(oneClassifyStr);
            publicDataInfo.setSJZYLYLX_CN(classifyStr);
            publicDataInfo.setSJZYLYYJLX_CN("");
        }
        //  源应用系统名称   电子围栏系统
        publicDataInfo.setYYYXTMC(StringUtils.isEmpty(resourceTable.getAppName()) ? "" : resourceTable.getAppName());
        //   源应用系统编号  334
        publicDataInfo.setYYYXTMM(StringUtils.isEmpty(resourceTable.getAppCode()) ? "" : resourceTable.getAppCode());
        //    源应用系统建设公司  全部
        publicDataInfo.setYYYXTJSGS(StringUtils.isEmpty(resourceTable.getAppBuildUnit()) ? "" : resourceTable.getAppBuildUnit());
        //                GA_SOURCE_01001_650100_00004
        publicDataInfo.setSOURCEID(StringUtils.isEmpty(resourceTable.getSourceCode()) ? "" : resourceTable.getSourceCode());
        //  数据资源事权单位名称   浙江省公安厅/测试组
        publicDataInfo.setSJZYSQDWMC(StringUtils.isEmpty(resourceTable.getRoutineUnit()) ? "" : resourceTable.getRoutineUnit());
        //  数据资源事权单位_事权单位代码
        publicDataInfo.setSJZYSQDW_SQDWDM(StringUtils.isEmpty(resourceTable.getRoutineCode()) ? "" : resourceTable.getRoutineCode());
        // 事权单位联系人    名称
        publicDataInfo.setSQDWLXR(StringUtils.isEmpty(resourceTable.getRoutineLinkMan()) ? "" : resourceTable.getRoutineLinkMan());
        // 事权单位联系电话   电话
        publicDataInfo.setSQDWLXDH(StringUtils.isEmpty(resourceTable.getRoutineTel()) ? "" : resourceTable.getRoutineTel());
        //   数据获取方式 01侦控 02管控 03管理 04公开
        publicDataInfo.setSJHQFS(StringUtils.isEmpty(resourceTable.getInceptType()) ? "" : resourceTable.getInceptType());
        publicDataInfo.setSJHQFS_CH(DataAcquisitionMethodType.getValueById(resourceTable.getInceptType()));
        //   数据资源位置 01部 02省 03市 04 网站
        DecimalFormat format = new DecimalFormat("00");
        publicDataInfo.setSJZYWZ(format.format(Long.parseLong(StringUtils.isEmpty(resourceTable.getResPostion()) ? "0" : resourceTable.getResPostion())));
//        publicDataInfo.setSJZYWZ(StringUtils.isEmpty(resourceTable.getSecretLevel())?"":resourceTable.getSecretLevel());
        publicDataInfo.setSJZYWZ_CH(DataResourceLocation.getValueById(publicDataInfo.getSJZYWZ()));
        // 数据资源存储分中心
        publicDataInfo.setSJZYCCFZX(StringUtils.isEmpty(resourceTable.getPostionDesc()) ? "" : resourceTable.getPostionDesc());
        //   数据资源管理单位名称
        publicDataInfo.setSJZYGLMC(StringUtils.isEmpty(resourceTable.getManageUnit()) ? "" : resourceTable.getManageUnit());
        //  数据资源管理单位_公安机关机构代码  SJZYGLDW_GAJGJGDM
        publicDataInfo.setSJZYGLDW_GAJGJGDM(StringUtils.isEmpty(resourceTable.getManageUnitCode()) ? "" : resourceTable.getManageUnitCode());

        /**
         * 20210126 添加的信息 数据接入的信息
         */
//        AccessServiceInformation access = null;
        try {

//                access = JSONObject.parseObject(
//                        resourceTable.getAccessServiceInformation(),AccessServiceInformation.class);
            // 接入服务方
            publicDataInfo.setJRFWF(StringUtils.isEmpty(resourceTable.getInceptUnit()) ? "" : resourceTable.getInceptUnit());
            // 提供接入方式
            publicDataInfo.setTGJRFS(StringUtils.isEmpty(resourceTable.getProvideType()) ? "" : resourceTable.getProvideType());
            // 接入服务人
            publicDataInfo.setJRFWR(StringUtils.isEmpty(resourceTable.getInceptor()) ? "" : resourceTable.getInceptor());
            // 接入服务人联系方式
            publicDataInfo.setJRFWLXDH(StringUtils.isEmpty(resourceTable.getInceptTel()) ? "" : resourceTable.getInceptTel());
            // 接入说明
            publicDataInfo.setSJJRSM(StringUtils.isEmpty(resourceTable.getInceptDesc()) ? "" : resourceTable.getInceptDesc());
            // 格式描述
            publicDataInfo.setGSMS(StringUtils.isEmpty(resourceTable.getDataFormat()) ? "" : resourceTable.getDataFormat());

        } catch (Exception e) {
            logger.error("数据仓库中没有配置数据接入的相关信息" + ExceptionUtil.getExceptionTrace(e));
        }
        publicDataInfo.setYYYXTGLDW(StringUtils.isEmpty(resourceTable.getAppManageUnit()) ? "" : resourceTable.getAppManageUnit());
        // 数据资源更新周期  20210510 该值使用的是 数据仓库那边的  数据更新周期
        publicDataInfo.setSJZYGXZQ(StringUtils.isEmpty(resourceTable.getUpdateCycle()) ? "" : resourceTable.getUpdateCycle());

        /**
         * 20210510 新增 扩充数据来源信息 扩充数据来源信息
         *  需求文档上 3.3.2.11
         *  来源描述、数据存储规模、数据量规模、数据更新方式、数据更新周期、数据更新时间点。
         */
        try {
//            if(access != null){
            // 数据量规模  dataNumberScale存储的是单位   totalRecord存储的是数据量 数据量如果写的是非数字，则改成0
            if (NumberUtils.isNumber(resourceTable.getUpdateCycle())) {
                publicDataInfo.setDataNumberScale(StringUtils.isBlank(resourceTable.getPhysicalUnit()) ?
                        resourceTable.getPhysicalSize() + "MB" : resourceTable.getPhysicalSize() + resourceTable.getPhysicalUnit());
            } else {
                publicDataInfo.setDataNumberScale(StringUtils.isBlank(resourceTable.getPhysicalUnit()) ?
                        "0MB" : "0" + resourceTable.getPhysicalUnit());
            }
            // 数据存储规模，需要2个参数合并  dataStoreScale存储的是单位  totalStore存储的是数据量
            if (NumberUtils.isNumber(String.valueOf(resourceTable.getIncRecordCounts()))) {
                publicDataInfo.setDataStoreScale(StringUtils.isBlank(resourceTable.getIncRecordUnit()) ?
                        resourceTable.getIncRecordCounts() + "条" : resourceTable.getIncRecordCounts() + resourceTable.getIncRecordUnit());
            } else {
                publicDataInfo.setDataStoreScale(StringUtils.isBlank(resourceTable.getIncRecordUnit()) ? "0条" : "0" + resourceTable.getIncRecordUnit());
            }
            // 数据资源更新周期  20210510 该值使用的是 数据仓库那边的   updateCycle  数据更新周期
            publicDataInfo.setSJZYGXZQ(StringUtils.isBlank(resourceTable.getUpdateCycle()) ? "" : resourceTable.getUpdateCycle());
            publicDataInfo.setUpdateType(StringUtils.isBlank(resourceTable.getUpdateType()) ? "" : resourceTable.getUpdateType());

//            }else{
//                logger.error("数据仓库返回的接口中数据规模信息为空，不能匹配对应的数据");
//            }
            publicDataInfo.setAvgStore(StringUtils.isBlank(resourceTable.getResType()) ? "" : "数据库类型:" + resourceTable.getResType() + "数据源名称:" + resourceTable.getResName());
            publicDataInfo.setAvgRecord(StringUtils.isBlank(resourceTable.getUpdateDesc()) ? "" : resourceTable.getUpdateDesc());
        } catch (Exception e) {
            logger.error("扩充数据来源信息的接口报错" + ExceptionUtil.getExceptionTrace(e));
        }
        logger.info("返回的数据为: " + JSON.toJSONString(publicDataInfo));
        return publicDataInfo;
    }

    /**
     * 根据页面上的sourceId 来获取到自增id值，来获取最新的sourceid信息
     * 附带验证功能
     *
     * @param sourceId           页面上的sourceId信息
     * @param dataSourceClassify 数据来源分类的代码
     * @param code               6位本地行政代码
     * @return
     */
    @Override
    public String getNewSourceIdById(String sourceId, String dataSourceClassify, String code) throws Exception {
        //查询public_data_info表中所有的sourceId
        List<String> allSourceIdList = resourceManageAddDao.getAllSourceId();
        String[] sourceList = dataSourceClassify.split("\\/");
        String secondClass = "";
        String tableIdStr = "GA_SOURCE_";
        if (sourceList.length > 1) {
            secondClass = resourceManageAddDao.findCheckTableNameImformationThree(sourceList[1], 2);
        } else {
            secondClass = resourceManageAddDao.findCheckTableNameImformationThree(sourceList[0], 1);
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
        Boolean switchFlag = switchHashMap.getOrDefault("approvalInfo", false);
        List<ApprovalInfoParams> approvalInfoParams = null;
        if (switchFlag) {
            approvalInfoParams = restTemplateHandle.queryApprovalInfoForModule("1", "standardTable");
        }
        if (approvalInfoParams != null && approvalInfoParams.isEmpty()) {
            OptionalInt standardObjectManagesSize = approvalInfoParams.stream().filter(d -> StringUtils.isNotEmpty(d.getCallbackData()))
                    .mapToInt(d -> {
                        try {
                            StandardObjectManage objectManage = JSONObject.parseObject(d.getCallbackData(), StandardObjectManage.class);
                            // tableId 的值为   GA_RESOURCE_5位数据来源代码_6位行政区划_5位流水号
                            String sourceIdPublic = objectManage.getPublicDataInfo().getSOURCEID();
                            if (StringUtils.isBlank(sourceIdPublic) || sourceIdPublic == null) {
                                return -1;
                            }
                            String[] sourceIds = sourceIdPublic.split("\\_");
                            if (sourceIds.length == 5 && (sourceIds[2] + "_" + sourceIds[3]).equalsIgnoreCase(classMesaage)) {
                                if (StringUtils.isNumeric(sourceIds[4])) {
                                    return Integer.valueOf(sourceIds[4]);
                                } else {
                                    return -1;
                                }
                            } else {
                                return -1;
                            }
                        } catch (Exception e) {
                            logger.error("" + ExceptionUtil.getExceptionTrace(e));
                            return -1;
                        }
                    }).filter(d -> d != -1).max();
            int approvalMaxId = Math.max(standardObjectManagesSize.orElse(80000), 80000);
            DecimalFormat format = new DecimalFormat("00000");
            tableIdStr = tableIdStr + "_" + format.format(approvalMaxId > dataBaseMaxId ? (approvalMaxId + 1) : (dataBaseMaxId + 1));
        } else {
            DecimalFormat format = new DecimalFormat("00000");
            tableIdStr = tableIdStr + "_" + format.format(dataBaseMaxId + 1);
        }
        return tableIdStr;
    }

    @Override
    public String addUserAuthorityData(StandardObjectManage standardObjectManage) throws Exception {

        LoginUser authorizedUser = AuthorizedUserUtils.getInstance().getAuthor();
        if (authorizedUser == null) {
            logger.info("用户信息为空，不需要进行权限控制");
            return "";
        }
        if (StringUtils.isNotBlank(standardObjectManage.getObjectId())) {
            logger.info("修改标准信息，不需要更改表的用户权限");
            return "";
        }
        UserAuthority userAuthority = new UserAuthority();
        // 如果是已经存在，则更新 如果不存在，则插入
        userAuthority.setId(standardObjectManage.getTableId().toUpperCase());
        userAuthority.setCmnName(standardObjectManage.getObjectPojoTable().getDataSourceName());
        userAuthority.setUserId(String.valueOf(authorizedUser.getUserId()));
        userAuthority.setUserName(authorizedUser.getUserName());
        userAuthority.setOrganId(authorizedUser.getOrganId());
        userAuthority.setOrganName(authorizedUser.getOrganName());
//        ValidatorUtil.checkObjectValidator(userAuthority);
        int tableCount = resourceManageAddDao.checkUserAuthorityExit(userAuthority.getId(), userAuthority.getUserId());
        if (tableCount >= 1) {
            logger.info(String.format("开始更新权限表user_authority...\nuserName:%s\nuserId:%s\norganId:%s\ntableId:%s\n",
                    userAuthority.getUserName(),userAuthority.getUserId(),userAuthority.getOrganId(),userAuthority.getId()));
            resourceManageAddDao.updateUserAuthority(userAuthority);
        } else {
            logger.info(String.format("开始插入权限表user_authority...\nuserName:%s\nuserId:%s\norganId:%s\ntableId:%s\n",
                    userAuthority.getUserName(),userAuthority.getUserId(),userAuthority.getOrganId(),userAuthority.getId()));
            resourceManageAddDao.insertUserAuthority(userAuthority);
        }
        return "标准表" + userAuthority.getId() + "插入权限表中成功";
    }

    @Override
    public JSONArray getAllFieldClassList() {
        List<FieldCodeVal> list = resourceManageAddDao.getAllFieldClassList();
        if (list == null || list.isEmpty()) {
            throw new NullPointerException("表synlte.fieldcodeval中字段分类的码表信息为空");
        }
        JSONArray jsonArray = convert2Tree(list, "GACODE000414", new JSONArray());
        jsonArray = JsonUtil.delEmptyChildren(jsonArray);
        return jsonArray;
    }

    /**
     * 递归转换树形json数据
     * @param tables
     * @param codeId
     * @param array
     * @return
     */
    public JSONArray convert2Tree(List<FieldCodeVal> tables, String codeId, JSONArray array) {
        for (FieldCodeVal table:tables) {
            if (table.getCodeId().equalsIgnoreCase(codeId)){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("value",table.getValValue());
                jsonObject.put("label",table.getValText());
                jsonObject.put("children",convert2Tree(tables,table.getCodeValId(), new JSONArray()));
                array.add(jsonObject);
            }
        }
        return array;
    }

    @Override
    public String updateFieldClass(ObjectFieldStandard objectField) {
        try {
            if (StringUtils.isBlank(objectField.getFieldId())) {
                return "元素编码为空，不需要修改";
            }
            if (StringUtils.isBlank(objectField.getFieldClassId())) {
                logger.error(objectField.getFieldId() + "分类信息为空");
                return "分类信息为空，不需要修改";
            }
            if (!StringUtils.isNumeric(objectField.getFieldClassId())) {
                logger.error(objectField.getFieldId() + "分类信息为：" + objectField.getFieldClassId() + "不是数字，不能进行修改");
                return objectField.getFieldId() + "分类信息为：" + objectField.getFieldClassId() + "不是数字，不能进行修改";
            }
            int updateNum = resourceManageAddDao.updateFieldClassByFieldId(objectField);
            logger.info("更新的条数为" + updateNum);
            return "更新的条数为" + updateNum;
        } catch (Exception e) {
            logger.error("更新分类信息报错" + ExceptionUtil.getExceptionTrace(e));
            return "更新失败：" + e.getMessage();
        }

    }

}
