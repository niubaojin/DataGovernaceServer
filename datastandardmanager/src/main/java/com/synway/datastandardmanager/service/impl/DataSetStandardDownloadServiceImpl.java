package com.synway.datastandardmanager.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.synway.common.exception.ExceptionUtil;
import com.synway.common.exception.SystemException;
import com.synway.datastandardmanager.constants.Common;
import com.synway.datastandardmanager.entity.pojo.*;
import com.synway.datastandardmanager.entity.vo.ExportObjectInfoVO;
import com.synway.datastandardmanager.entity.vo.InputObjectCreateVO;
import com.synway.datastandardmanager.entity.vo.KeyValueVO;
import com.synway.datastandardmanager.entity.vo.createTable.BuildTableInfoVO;
import com.synway.datastandardmanager.entity.vo.createTable.CkColumnVO;
import com.synway.datastandardmanager.entity.vo.importDownload.ObjectFieldSheetVO;
import com.synway.datastandardmanager.entity.vo.importDownload.ObjectTableSheetVO;
import com.synway.datastandardmanager.entity.vo.importDownload.TableColumnSheetVO;
import com.synway.datastandardmanager.enums.*;
import com.synway.datastandardmanager.listener.ExcelListener;
import com.synway.datastandardmanager.mapper.*;
import com.synway.datastandardmanager.service.CreateTableService;
import com.synway.datastandardmanager.service.DataSetStandardDownloadService;
import com.synway.datastandardmanager.service.DataSetStandardService;
import com.synway.datastandardmanager.util.*;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.ClientAbortException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
@Service
public class DataSetStandardDownloadServiceImpl implements DataSetStandardDownloadService {

    @Resource
    private ObjectMapper objectMapper;
    @Resource
    private ObjectFieldMapper objectFieldMapper;
    @Resource
    private ObjectVersionMapper objectVersionMapper;
    @Resource
    private ObjectHisMapper objectHisMapper;
    @Resource
    private FieldCodeMapper fieldCodeMapper;
    @Resource
    private SynlteFieldMapper synlteFieldMapper;
    @Resource
    private EntityElementMapper entityElementMapper;
    @Resource
    private StandardizeOutputObjectMapper standardizeOutputObjectMapper;
    @Resource
    private DgnCommonSettingMapper dgnCommonSettingMapper;

    @Autowired
    private DataSetStandardService dataSetStandardService;
    @Autowired
    private CreateTableService createTableService;

    @Autowired
    private Environment env;

    @Override
    public String importObjectInfoExcel(MultipartFile[] file, Integer objectSheetPage, Integer objectFieldSheetPage) {
        //先过滤文件中不是excel文件
        List<MultipartFile> excelFile = new ArrayList<>();
        for (MultipartFile data : file) {
            String fileName = data.getOriginalFilename();
            String excel = fileName.substring(fileName.indexOf(".") + 1);
            if ("xls".equalsIgnoreCase(excel) || "xlsx".equalsIgnoreCase(excel)) {
                excelFile.add(data);
            }
        }
        log.info(">>>>>>开始导入标准信息");
        ExcelListener<ObjectTableSheetVO> listener = new ExcelListener<>();
        ExcelListener<TableColumnSheetVO> fieldListener = new ExcelListener<>();
        List<ObjectTableSheetVO> list = new ArrayList<>();
        List<TableColumnSheetVO> columnList = new ArrayList<>();
        Integer objectId = null;
        try {
            for (MultipartFile excelData : excelFile) {
                list = EasyExcelUtil.readObjectExcelSheetUtil(excelData, new ObjectTableSheetVO(), listener, objectSheetPage);
                Iterator iterator = list.iterator();
                while (iterator.hasNext()) {
                    ObjectTableSheetVO objectTableSheet = (ObjectTableSheetVO) iterator.next();
                    objectId = objectTableSheet.getObjectId();
                    Boolean flag = checkObjectInfo(objectTableSheet);
                    if (flag) {
                        //查询此标准信息在库中是否存在
                        log.info(">>>>>>开始查询此标准在object表里是否存在，objectId：" + objectTableSheet.getObjectId());
                        LambdaQueryWrapper<ObjectEntity> wrapper = Wrappers.lambdaQuery();
                        wrapper.eq(ObjectEntity::getObjectId, objectTableSheet.getObjectId());
                        if (objectMapper.selectCount(wrapper) == 0) {
                            //数据集标准不存在直接插入
                            ObjectEntity addObjectInfo = injectObject(objectTableSheet);
                            if (objectMapper.insert(addObjectInfo) != 1) {
                                throw SystemException.asSystemException(ErrorCodeEnum.INSERT_ERROR, "数据集信息[" + addObjectInfo.getObjectName() + "]插入失败");
                            }
//                            // 导入数据成功后，将信息插入或更新到用户权限表 USER_AUTHORITY 中
//                            ObjectManageDTO standardObjectManage = new ObjectManageDTO();
//                            standardObjectManage.setObjectPojoTable(addObjectInfo);
//                            standardObjectManage.setObjectId(String.valueOf(addObjectInfo.getObjectId()));
//                            standardObjectManage.setTableId(addObjectInfo.getTableId());
//                            resourceManageAddServiceImpl.addUserAuthorityData(standardObjectManage);
                            iterator.remove();
                        } else {
                            //标准已经存在，比较两者的小版本日期，导入的标准较新则将旧的导入到版本库，存储新的
                            ObjectEntity oldObjectInfo = SelectUtil.getObjectEntityByObjectId(objectMapper, objectId);
                            ObjectEntity objectInfo = injectObject(objectTableSheet);
                            if (objectMapper.updateObjectByObjectId(objectInfo) != 1) {
                                throw SystemException.asSystemException(ErrorCodeEnum.INSERT_ERROR, "数据集信息[" + objectInfo.getObjectName() + "]更新失败");
                            }
                            //将旧的标准信息存储到历史表
                            ObjectVersionEntity objectVersion = createObjectVersion(oldObjectInfo);
                            if (objectVersionMapper.insert(objectVersion) != 1) {
                                throw SystemException.asSystemException(ErrorCodeEnum.INSERT_ERROR, "标准版本信息[" + objectVersion.getTableName() + "]插入失败");
                            }
                            // 将标准历史信息保存至标准历史表中
                            ObjectHisEntity objectHisEntity = injectObjectHis(oldObjectInfo, objectVersion.getObjectidVersion());
                            if (objectHisMapper.insert(objectHisEntity) != 1) {
                                throw SystemException.asSystemException(ErrorCodeEnum.INSERT_ERROR, "标准历史库[" + oldObjectInfo.getRealTablename() + "]插入失败");
                            }
                            iterator.remove();
                        }
                    }
                    //标准导入成功后导入字段信息
                    columnList = EasyExcelUtil.readExcelSheetUtil(excelData, new TableColumnSheetVO(), fieldListener, objectFieldSheetPage);
                    Iterator fieldIterator = columnList.iterator();
                    while (fieldIterator.hasNext()) {
                        TableColumnSheetVO tableColumnSheet = (TableColumnSheetVO) fieldIterator.next();
                        tableColumnSheet.setObjectId(objectId);
                        ObjectFieldEntity objectFieldStandard = objectFieldDataBackFill(tableColumnSheet, objectId);
                        log.info(String.format(">>>>>>开始查询此标准在objectfield表里是否存在，objectId：%s,columnName：%s", objectTableSheet.getObjectId(), objectFieldStandard.getColumnName()));
                        LambdaQueryWrapper<ObjectFieldEntity> wrapper = Wrappers.lambdaQuery();
                        wrapper.eq(ObjectFieldEntity::getObjectId, objectFieldStandard.getObjectId());
                        wrapper.eq(ObjectFieldEntity::getColumnName, objectFieldStandard.getColumnName());
                        if (objectFieldMapper.selectCount(wrapper) == 1) {
                            objectFieldMapper.updateObjectField(objectFieldStandard);
                        } else {
                            objectFieldMapper.insert(objectFieldStandard);
                        }
                        fieldIterator.remove();
                    }
                }
            }

        } catch (Exception e) {
            throw new NullPointerException("导入文件报错:\n" + ExceptionUtil.getExceptionTrace(e));
        }
        return Common.IMPORT_SUCCESS;
    }

    /**
     * 标准字段信息回填
     *
     * @param tableColumnSheet 导入的字段信息
     * @param objectId         标准id
     * @return
     */
    private ObjectFieldEntity objectFieldDataBackFill(TableColumnSheetVO tableColumnSheet, Integer objectId) {

        ObjectFieldEntity objectField = new ObjectFieldEntity();
        objectField.setObjectId(objectId);
        objectField.setRecno(tableColumnSheet.getRecno());
        objectField.setFieldId(tableColumnSheet.getFieldId());
        objectField.setFieldName(tableColumnSheet.getFieldName());
        objectField.setFieldChineseName(tableColumnSheet.getFieldChineseName());
        objectField.setFieldClassId(tableColumnSheet.getFieldClassification());
        objectField.setZdmgdfldm(tableColumnSheet.getSecurityLevel());
        objectField.setFieldDescribe(tableColumnSheet.getMemo());
        //字段类型
        String fieldType = tableColumnSheet.getFieldType().substring(0, tableColumnSheet.getFieldType().indexOf("("));
        String fieldLength = tableColumnSheet.getFieldType().substring(tableColumnSheet.getFieldType().indexOf("(") + 1, tableColumnSheet.getFieldType().indexOf(")"));
        objectField.setFieldType(KeyIntEnum.getKeyByNameAndType(fieldType, Common.BUILDTABLEFIELD));
        objectField.setFieldLen(Integer.valueOf(fieldLength));
        objectField.setIndexType(0);
        objectField.setTableIndex(StringUtils.isNotBlank(tableColumnSheet.getTableIndex()) ? Integer.valueOf(tableColumnSheet.getTableIndex()) : 0);
        objectField.setIsIndex("是".equalsIgnoreCase(tableColumnSheet.getIsIndexCh()) ? 1 : 0);
        objectField.setNeedValue("是".equalsIgnoreCase(tableColumnSheet.getNeedValueCh()) ? 1 : 0);
        objectField.setIsContorl("是".equalsIgnoreCase(tableColumnSheet.getIsContorlCh()) ? 1 : 0);
        objectField.setColumnName(tableColumnSheet.getFieldName());
        objectField.setMemo(tableColumnSheet.getMemo());
        objectField.setIsQuery("是".equalsIgnoreCase(tableColumnSheet.getIsIndexCh()) ? 1 : 0);
        objectField.setColumnNameState(1);
        objectField.setMd5Index(tableColumnSheet.getMd5Index() != null ? tableColumnSheet.getMd5Index() : 0);
        String todayStr = DateUtil.formatDateTime(new Date(), DateUtil.DEFAULT_PATTERN_DATE_SIMPLE);
        objectField.setVersion(tableColumnSheet.getVersion() != null ? tableColumnSheet.getVersion() : Integer.parseInt(todayStr));
        //查询赋值大版本
        String version = dgnCommonSettingMapper.searchVersion();
        String versions = JSONObject.parseObject(version).getString("objectVersions");
        objectField.setVersion0(versions);
        objectField.setIsPrivate("是".equalsIgnoreCase(tableColumnSheet.getIsPrivateCh()) ? 1 : 0);
        objectField.setStandardRecno(tableColumnSheet.getStandardRecno() != null ? tableColumnSheet.getStandardRecno() : 0);
        objectField.setPkRecno(tableColumnSheet.getPkRecno() != null ? tableColumnSheet.getPkRecno() : 0);
        objectField.setPartitionRecno(tableColumnSheet.getPartitionRecno() != null ? tableColumnSheet.getPartitionRecno() : 0);
        objectField.setClustRecno(tableColumnSheet.getClustRecno() != null ? tableColumnSheet.getClustRecno() : 0);
        objectField.setOraShow("是".equalsIgnoreCase(tableColumnSheet.getOraShowCh()) ? 1 : 0);
        objectField.setDeterminerId(tableColumnSheet.getDeterminerId());
        objectField.setGadsjFieldId(tableColumnSheet.getGadsjFieldId());

        return objectField;
    }

    private ObjectHisEntity injectObjectHis(ObjectEntity objectEntity, String uuid) {
        ObjectHisEntity objectHisEntity = new ObjectHisEntity();
        objectHisEntity.setObjectIdVersion(uuid);
        objectHisEntity.setObjectId(objectEntity.getObjectId());
        objectHisEntity.setObjectName(objectEntity.getObjectName());
        objectHisEntity.setObjectState(objectEntity.getObjectState());
        objectHisEntity.setTableId(objectEntity.getTableId());
        objectHisEntity.setTableName(objectEntity.getTableName());
        objectHisEntity.setStoreType(objectEntity.getStoreType());
        objectHisEntity.setDataSource(objectEntity.getDataSource());
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
        objectHisEntity.setVersion(objectEntity.getVersion() != null ? objectEntity.getVersion() : version);
        objectHisEntity.setVersions(StringUtils.isNotBlank(objectEntity.getVersions()) ? objectEntity.getVersions() : "1.0");
        objectHisEntity.setSecretLevel(Integer.valueOf(objectEntity.getSecretLevel()));
        objectHisEntity.setStandardType(0);
        objectHisEntity.setSjzybq1(objectEntity.getSjzybq1());
        objectHisEntity.setSjzybq2(objectEntity.getSjzybq2());
        objectHisEntity.setSjzybq3(objectEntity.getSjzybq3());
        objectHisEntity.setSjzybq4(objectEntity.getSjzybq4());
        objectHisEntity.setSjzybq5(objectEntity.getSjzybq5());
        objectHisEntity.setSjzybq6(objectEntity.getSjzybq6());
        objectHisEntity.setSjzzyjflvallue(objectEntity.getSjzzyjflVallue());
        objectHisEntity.setSjzzejflvalue(objectEntity.getSjzzejflValue());
        objectHisEntity.setSjzylylxvalue(objectEntity.getSjzylylxValue());
        objectHisEntity.setObjectState(-1);
        objectHisEntity.setObjectStateVo("停用");

        return objectHisEntity;
    }

    /**
     * 生成标准版本信息
     *
     * @param objectPojoTable 标准信息
     * @return
     */
    private ObjectVersionEntity createObjectVersion(ObjectEntity objectPojoTable) {
        ObjectVersionEntity objectVersion = new ObjectVersionEntity();
        String uuid = UUIDUtil.getUUID();
        objectVersion.setObjectidVersion(uuid);
        objectVersion.setObjectId(String.valueOf(objectPojoTable.getObjectId()));
        objectVersion.setTableName(objectPojoTable.getRealTablename());
        if (StringUtils.isBlank(objectPojoTable.getObjectMemo())) {
            objectVersion.setMemo("");
        } else {
            objectVersion.setMemo(objectPojoTable.getObjectMemo());
        }
        String todayStr = DateUtil.formatDateTime(new Date(), DateUtil.DEFAULT_PATTERN_DATE_SIMPLE);
        objectVersion.setVersion(Integer.valueOf(todayStr));
        objectVersion.setVersions(StringUtils.isBlank(objectPojoTable.getVersions()) ? "1.0" : objectPojoTable.getVersions());
        objectVersion.setAuthor(StringUtils.isBlank(objectPojoTable.getCreator()) ? "1.0" : objectPojoTable.getCreator());
        return objectVersion;
    }

    /**
     * 标准信息回填
     *
     * @param objectTableSheet 导入的标准信息
     * @return
     */
    private ObjectEntity injectObject(ObjectTableSheetVO objectTableSheet) throws ParseException {
        ObjectEntity addObjectInfo = new ObjectEntity();
        addObjectInfo.setObjectId(objectTableSheet.getObjectId());
        addObjectInfo.setObjectName(objectTableSheet.getObjectName());
        addObjectInfo.setObjectStateVo(String.valueOf(objectTableSheet.getObjectState()));
        addObjectInfo.setTableId(objectTableSheet.getTableId());
        addObjectInfo.setRealTablename(objectTableSheet.getTableName());
        addObjectInfo.setDataSource(objectTableSheet.getDataSource());
        addObjectInfo.setSourceId(objectTableSheet.getSourceId());
        addObjectInfo.setObjectMemo(objectTableSheet.getObjectMemo());
        addObjectInfo.setIsActiveTable(objectTableSheet.getIsActiveTable() != null ? objectTableSheet.getIsActiveTable() : 0);
        addObjectInfo.setDataType(0);
        addObjectInfo.setStoreTypeVo("0");
        // 2022年11月29日15:39:45 导入时version换成当前日期
        String todayStr = DateUtil.formatDateTime(new Date(), DateUtil.DEFAULT_PATTERN_DATE_SIMPLE);
        addObjectInfo.setVersion(Integer.valueOf(todayStr));
        //查询大版本和设置小版本
        String version = dgnCommonSettingMapper.searchVersion();
        String versions = JSONObject.parseObject(version).getString("objectVersions");
        addObjectInfo.setVersions(versions);

        addObjectInfo.setSjzybq1(objectTableSheet.getSjzybq1());
        addObjectInfo.setSjzybq2(objectTableSheet.getSjzybq2());
        addObjectInfo.setSjzybq3(objectTableSheet.getSjzybq3());
        addObjectInfo.setSjzybq4(objectTableSheet.getSjzybq4());
        addObjectInfo.setSjzzyjflVallue(objectTableSheet.getSjzzyjflValue());
        addObjectInfo.setSjzzejflValue(objectTableSheet.getSjzzejflValue());
        //来源分类存2级
        addObjectInfo.setSjzylylxValue(objectTableSheet.getSjzylylxValue());
        addObjectInfo.setStandardType(objectTableSheet.getStandardType());
        // 创建时间、更新时间
        addObjectInfo.setUpdateTime(new Date());
        if (StringUtils.isNotBlank(objectTableSheet.getCreateTime())) {
            Date createTime = DateUtil.parseDateTime(objectTableSheet.getCreateTime());
            addObjectInfo.setCreateTime(createTime);
        } else {
            addObjectInfo.setCreateTime(new Date());
        }
        // 数据分级
        if (StringUtils.isNotBlank(objectTableSheet.getSecretLevel())) {
            addObjectInfo.setSecretLevel(objectTableSheet.getSecretLevel());
        }
        addObjectInfo.setCreator(objectTableSheet.getCreator());
        addObjectInfo.setUpdater(objectTableSheet.getUpdater());
        return addObjectInfo;
    }

    /**
     * 检查标准信息是否合规
     *
     * @param objectTableSheet 导入的标准信息
     */
    private Boolean checkObjectInfo(ObjectTableSheetVO objectTableSheet) {
        if (objectTableSheet.getObjectId() == null && StringUtils.isBlank(objectTableSheet.getTableId())) {
            return false;
        }
        return true;
    }

    @Override
    public void downloadObjectInfoExcel(HttpServletResponse response, List<String> tableIdList) throws IOException {
        ServletOutputStream outputStream = response.getOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
        try {
            // 设置响应头
            String name = "数据标准导出" + DateUtil.formatDateTime(new Date(), DateUtil.DEFAULT_PATTERN_DATE_SIMPLE);
            if (tableIdList.size() > 1) {
                response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(name + ".zip", "UTF-8"));
                response.setContentType("application/zip");
                response.setCharacterEncoding("utf-8");
            } else {
                response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(name + ".xls", "UTF-8"));
                response.setContentType("application/vnd.ms-excel");
                response.setCharacterEncoding("utf-8");
            }
            log.info(">>>>>>开始导出标准信息");
            String[] objectTitles = {"表名", "标准表名", "源数据名称", "对应的数据项描述", "表描述", "MD_ID取值", "主键", "过滤列",
                    "分区列", "二级分区列", "聚集列", "加载方式", "支持操作类型", "ADS(HBASE保存时间)", "HDFS源文件保存数", "预估数据量", "其他要求说明"};

            String[] objectName = new String[]{"tableName", "tableId", "sourceId",
                    "objectMemo", "objectName", "md5Index", "pkRecno", "", "partitionRecno", "secondPartitionRecno", "clustRecno",
                    "", "", "", "", "", ""};

            String[] standardTableInfoTitles = new String[]{"objectid", "tablename", "hivetablename", "tableid", "sourceid", "OBJECTNAME", "DATA_SOURCE", "SJZYLYLXVALUE", "SJZYLYLXEJVALUE", "SECRETLEVEL",
                    "SJZZYJFLVALLUE", "SJZZEJFLVALUE", "SJZYBQ1", "SJZYBQ2", "SJZYBQ3", "SJZYBQ4", "CREATETIME", "ISACTIVETABLE", "standard_type", "RELATE_TABLENAME", "OBJECTMEMO", "ElLEMENTNAME", "OBJECTSTATE", "CREATOR", "UPDATER"};

            String[] standardTableInfoName = new String[]{"objectId", "tableName", "hiveTableName", "objectId", "sourceId", "objectName", "dataSource", "SJZYLYLXVALUE", "", "dataLevel", "SJZZYJFLVALLUE", "SJZZEJFLVALUE",
                    "sjzybq1", "sjzybq2", "sjzybq3", "sjzybq4", "createTime", "isActiveTable", "standardType", "relateTableName", "objectMemo", "ellementName", "objectState", "creator", "updater"};

            String[] columnTitles = {"字段名", "元素编码", "映射部标内部标识符", "映射部标数据元限定词", "字段类型", "必填", "索引", "序列", "中文名称",
                    "字段分类", "安全级别", "是否可以布控", "备注", "私有协议", "是否MD5", "标准中的索引", "添加时间", "标准中的源名", "标准中的顺序", "显示", "近线显示"};

            String[] objectFieldName = {"columnName", "fieldId", "gadsjFieldId", "determinerId", "fieldType", "needValueStr", "isIndexStr", "recno", "fieldChineseName",
                    "fieldClassId", "securityLevel", "isContorlStr", "memo", "isPrivate", "md5Index", "tableIndex", "version", "", "standardRecno", "", "oraShowStr"};

            //如果大于1条则导出zip文件，1条信息则是excel文件
            if (tableIdList.size() > 1) {
                LambdaQueryWrapper<ObjectEntity> wrapper = Wrappers.lambdaQuery();
                wrapper.in(ObjectEntity::getTableId, tableIdList);
                // object
                List<ObjectEntity> objectPojos = objectMapper.selectList(wrapper);
                List<InputObjectCreateVO> allInpuObjectList = standardizeOutputObjectMapper.getAllInputObjects(tableIdList);
                Map<String, List<InputObjectCreateVO>> allInpuObjectListMap = allInpuObjectList.stream().collect(Collectors.groupingBy(InputObjectCreateVO::getOutObjEngName));
                List<FieldCodeEntity> fieldCodeVals = fieldCodeMapper.selectOneSysNames();
                List<ObjectEntity> classifyOnes = objectMapper.getClassifyByTableids();
                // objectfield
                LambdaQueryWrapper<ObjectFieldEntity> wrapperOF = Wrappers.lambdaQuery();
                wrapperOF.eq(ObjectFieldEntity::getDeleted, 0);
                List<ObjectFieldEntity> objectFieldLists = objectFieldMapper.selectList(wrapperOF);
                Map<Integer, List<ObjectFieldEntity>> objectFieldMap = objectFieldLists.stream().collect(Collectors.groupingBy(ObjectFieldEntity::getObjectId));
                List<KeyValueVO> synlteFieldInfos = synlteFieldMapper.getGadsjFieldByTexts();
                Map<String, List<KeyValueVO>> synlteFieldInfoMap = synlteFieldInfos.stream().collect(Collectors.groupingBy(KeyValueVO::getValue));
                LambdaQueryWrapper<EntityElementEntity> wrapperEE = Wrappers.lambdaQuery();
                wrapperEE.eq(EntityElementEntity::getCreateMode, 1);
                List<EntityElementEntity> elementNames = entityElementMapper.selectList(wrapperEE);
                //  获取 表字段信息的 字段分类信息  synltefield. FIELD_CLASS 这个字段里面
                List<ObjectFieldEntity> list = synlteFieldMapper.queryCodeClassList();
                //将要导出的标准信息都回填
                for (int i = 0; i < tableIdList.size(); i++) {
                    String tableId = tableIdList.get(i);
                    ObjectEntity objectInfo = objectPojos.stream().filter(d -> d.getTableId().equalsIgnoreCase(tableId)).findFirst().orElse(new ObjectEntity());
                    // 获取输入和输出的对应关系
                    List<InputObjectCreateVO> inputObjectCreate = new ArrayList<>();
                    for (String outObjEngName : allInpuObjectListMap.keySet()) {
                        if (outObjEngName.equalsIgnoreCase(tableId)) {
                            inputObjectCreate = allInpuObjectListMap.get(outObjEngName);
                        }
                    }
                    // fieldCodeVal
                    FieldCodeEntity fieldCodeVal = new FieldCodeEntity();
                    if (objectInfo.getTableId() != null && objectInfo.getDataSource() != null) {
                        fieldCodeVal = fieldCodeVals.stream().filter(d -> d.getValValue().equalsIgnoreCase(objectInfo.getDataSource())).findFirst().orElse(new FieldCodeEntity());
                    }
                    // 字段信息
                    ObjectEntity classifyOne = classifyOnes.stream().filter(d -> d.getTableId().equalsIgnoreCase(tableId)).findFirst().orElse(new ObjectEntity());
                    List<ObjectFieldEntity> objectFieldList1 = new ArrayList<>();
                    for (Integer objectId : objectFieldMap.keySet()) {
                        if (objectInfo.getObjectId() != null && objectInfo.getObjectId() == objectId) {
                            objectFieldList1 = objectFieldMap.get(objectId);
                        }
                    }
                    // 标准表信息
                    ObjectEntity objectPojoInfo = selectObjectPojoByTableIdNew(objectInfo, inputObjectCreate, fieldCodeVal, classifyOne);
                    //查出标准字段信息
                    List<ObjectFieldEntity> objectFieldList = selectObjectFieldByObjectIdNew(objectInfo, objectFieldList1, synlteFieldInfoMap, elementNames, list);

                    // 其他信息注入
                    ExportObjectInfoVO exportObjectInfo = getExportObjectInfo(objectPojoInfo, objectFieldList);
                    List<ObjectFieldEntity> objectFields = exportObjectInfo.getObjectFieldInfo();
                    for (ObjectFieldEntity objectField : objectFields) {
                        // 字段类型和长度合起来
                        String fieldTypeNew = SynlteFieldTypeEnum.getSynlteFieldType(Integer.valueOf(objectField.getFieldType())) + "(" + objectField.getFieldLen() + ")";
                        objectField.setFieldTypeCh(fieldTypeNew);
                    }
                    //生成workbook
                    HSSFWorkbook workbook = ExcelHelper.exportHorizontalExcelZip(new ExportObjectInfoVO(), exportObjectInfo, objectInfo, objectFields, objectTitles, standardTableInfoTitles, columnTitles,
                            "表说明", "标准表信息", "表字段", objectName, standardTableInfoName, objectFieldName);
                    String tableNameCh = exportObjectInfo.getObjectName().replace("/", "、");
                    String xlsName = String.format("%s@%s.xls", exportObjectInfo.getTableName(), tableNameCh);
                    try {
                        //压缩文件中放入excel文件
                        zipOutputStream.putNextEntry(new ZipEntry(xlsName));
                        workbook.write(zipOutputStream);
                        zipOutputStream.closeEntry();
                        workbook.close();
                    } catch (IOException e) {
                        log.error("文件处理失败：{}", xlsName, e);
                    }
                }
//                zipOutputStream.finish();
            } else {
                String tableId = tableIdList.get(0);
                //根据tableId查询标准信息
                ObjectEntity objectPojoInfo = dataSetStandardService.queryObjectDetail(tableId);
                // 标准表信息
                ObjectEntity objectInfo = SelectUtil.getObjectEntityByTableId(objectMapper, tableId);
                //查出标准字段信息
                List<ObjectFieldEntity> objectFieldList = dataSetStandardService.queryObjectFieldListByTableId(tableId);
                ExportObjectInfoVO exportObjectInfo = getExportObjectInfo(objectPojoInfo, objectFieldList);
                //表信息
                List<ExportObjectInfoVO> objects = new ArrayList<>();
                //字段信息
                List<ObjectFieldEntity> objectFields = new ArrayList<>();
                //添加到list中
                objects.add(exportObjectInfo);
                //字段信息
                List<ObjectFieldEntity> objectFieldInfo = exportObjectInfo.getObjectFieldInfo();
                for (ObjectFieldEntity data : objectFieldInfo) {
                    // 字段类型和长度合起来
                    String fieldTypeNew = SynlteFieldTypeEnum.getSynlteFieldType(Integer.valueOf(data.getFieldType())) + "(" + data.getFieldLen() + ")";
                    data.setFieldTypeCh(fieldTypeNew);
                }
                objectFields.addAll(objectFieldInfo);
                //生成excel文件
                log.info(">>>>>>开始导出");
                ExcelHelper.exportHorizontalExcel(new ExportObjectInfoVO(), objects, objectInfo, objectFields, objectTitles, standardTableInfoTitles, columnTitles,
                        "表说明", "标准表信息", "表字段", objectName, standardTableInfoName, objectFieldName, outputStream);
                log.info(">>>>>>导出结束");
            }
        } catch (ClientAbortException e) {
            log.error("客户端中断下载：{}", e);
        } catch (Exception e) {
            if (!response.isCommitted()) {
                response.sendError(500, "压缩失败");
            }
            log.error("下载标准信息报错:\n", e);
        } finally {
            zipOutputStream.finish();
        }
    }

    /**
     * 获取导出的标准对象
     *
     * @param objectPojoTable 数据库中的标准信息
     * @param objectFieldList 数据库中的标准字段信息
     */
    private ExportObjectInfoVO getExportObjectInfo(ObjectEntity objectPojoTable, List<ObjectFieldEntity> objectFieldList) {
        ArrayList<String> md5IndexList = new ArrayList<>();
        ArrayList<String> pkRecnoList = new ArrayList<>();
        ArrayList<String> partitionRecnoList = new ArrayList<>();
        ArrayList<String> secondPartitionRecno = new ArrayList<>();
        ArrayList<String> clustRecnoList = new ArrayList<>();
        ExportObjectInfoVO exportObjectInfo = new ExportObjectInfoVO();
        exportObjectInfo.setObjectId(String.valueOf(objectPojoTable.getObjectId()));
        exportObjectInfo.setObjectName(objectPojoTable.getObjectName());
        exportObjectInfo.setTableName(objectPojoTable.getRealTablename());
        exportObjectInfo.setTableId(objectPojoTable.getTableId());
        exportObjectInfo.setSourceId(objectPojoTable.getSourceId());
        exportObjectInfo.setObjectMemo(objectPojoTable.getObjectMemo());
        if (objectFieldList != null && objectFieldList.size() > 0) {
            objectFieldList.stream().forEach(d -> {
                if (d.getMd5Index() != null && d.getMd5Index() != 0) {
                    md5IndexList.add(d.getFieldName());
                }
                if (d.getPkRecno() != null && d.getPkRecno() != 0) {
                    pkRecnoList.add(d.getFieldName());
                }
                if (d.getPartitionRecno() != null && d.getPartitionRecno() == 1) {
                    partitionRecnoList.add(d.getFieldName());
                }
                if (d.getPartitionRecno() != null && d.getPartitionRecno() == 2) {
                    secondPartitionRecno.add(d.getFieldName());
                }
                if (d.getClustRecno() != null && d.getClustRecno() != 0) {
                    clustRecnoList.add(d.getFieldName());
                }
                if (d.getNeedValue() == 0) {
                    d.setNeedValueStr("否");
                } else if (d.getNeedValue() == 1) {
                    d.setNeedValueStr("是");
                }
                if (d.getIsIndex() == 0) {
                    d.setIsIndexStr("否");
                } else if (d.getIsIndex() == 1) {
                    d.setIsIndexStr("是");
                }
                if (d.getIsContorl() == 0) {
                    d.setIsContorlStr("否");
                } else if (d.getIsContorl() == 1) {
                    d.setIsContorlStr("是");
                }
//                if (d.getIsPrivate().equalsIgnoreCase("0")){
//                    d.setIsPrivate("否");
//                }else if (d.getIsPrivate().equalsIgnoreCase("1")){
//                    d.setIsPrivate("是");
//                }
                if (d.getOraShow() == 0) {
                    d.setOraShowStr("否");
                } else if (d.getOraShow() == 1) {
                    d.setOraShowStr("是");
                }
            });
        }
        exportObjectInfo.setMd5Index(StringUtils.join(md5IndexList, ","));
        exportObjectInfo.setPkRecno(StringUtils.join(pkRecnoList, ","));
        exportObjectInfo.setPartitionRecno(StringUtils.join(partitionRecnoList, ","));
        exportObjectInfo.setSecondPartitionRecno(StringUtils.join(secondPartitionRecno, ","));
        exportObjectInfo.setClustRecno(StringUtils.join(clustRecnoList, ","));
        if (objectPojoTable.getOrganizationClassify() != null) {
            String firstOrganization = objectPojoTable.getOrganizationClassify().split("/")[0];
            String secondOrganization = objectPojoTable.getOrganizationClassify().split("/")[1];
            exportObjectInfo.setParOrganizationClassify(firstOrganization);
            exportObjectInfo.setSecondOrganizationClassify(secondOrganization);
        }
        exportObjectInfo.setObjectFieldInfo(objectFieldList);
        return exportObjectInfo;
    }

    public List<ObjectFieldEntity> selectObjectFieldByObjectIdNew(ObjectEntity objectFieldDemo,
                                                                  List<ObjectFieldEntity> objectFieldList,
                                                                  Map<String, List<KeyValueVO>> synlteFieldInfoMap,
                                                                  List<EntityElementEntity> elementVOList,
                                                                  List<ObjectFieldEntity> list) {
        // 先根据 tableId获取对应的objectId
        try {
            if (objectFieldDemo == null || objectFieldList == null || objectFieldList.size() == 0) {
                return new ArrayList<>();
            }
            // 获取代码中文名
            for (ObjectFieldEntity objectField : objectFieldList) {
                String fieldId = objectField.getFieldId();
                if (objectField.getFieldId().indexOf("_") != -1) {
                    fieldId = objectField.getFieldId().split("_")[1];
                }
                List<KeyValueVO> synlteFieldInfo = new ArrayList<>();
                for (String fieldId1 : synlteFieldInfoMap.keySet()) {
                    if (fieldId1.equalsIgnoreCase(fieldId)) {
                        synlteFieldInfo = synlteFieldInfoMap.get(fieldId);
                    }
                }
                if (synlteFieldInfo.size() > 0) {
                    KeyValueVO value = synlteFieldInfo.get(0);
                    objectField.setSynlteFieldMemo(value.getMemo());
                    objectField.setLabel(value.getLabel());
                }

                if (StringUtils.isEmpty(objectField.getZdmgdfldm())) {
                    objectField.setZdmgdfldm("");
                    objectField.setSecurityLevelCh("");
                } else {
                    objectField.setSecurityLevelCh(KeyStrEnum.getValueByKeyAndType("2_" + objectField.getZdmgdfldm(), Common.DATASECURITYLEVEL));
                }
                // 判断
                if (StringUtils.isEmpty(objectField.getFieldId()) || objectField.getFieldId().contains("unknown_")) {
                    objectField.setFieldId("");
                }
                if (objectField.getMd5Index() != null && objectField.getMd5Index() != 0) {
                    objectField.setMd5IndexStatus(true);
                }
                if (StringUtils.isEmpty(objectField.getFieldId())) {
                    objectField.setCodeText("");
                    objectField.setCodeid("");
                    objectField.setFieldClassId("");
                    objectField.setFieldClassCh("");
                    objectField.setSameWordType("");
                } else {
                    //20210913 通过标准字段fieldId字段获取对应的数据要素名称
                    EntityElementEntity elementVO = elementVOList.stream().filter(d -> d.getElementCode().equalsIgnoreCase(objectField.getFieldId())).findFirst().orElse(new EntityElementEntity());
                    objectField.setElementName(elementVO.getElementChname() != null ? elementVO.getElementChname() : "");
                    //  获取 表字段信息的 字段分类信息  synltefield. FIELD_CLASS 这个字段里面
                    if (list.size() > 0) {
                        ObjectFieldEntity synltefield = list.stream().filter(d -> d.getFieldId().equalsIgnoreCase(objectField.getFieldId())).findFirst().orElse(new ObjectFieldEntity());
                        objectField.setCodeText(synltefield.getCodeText() != null ? synltefield.getCodeText() : "");
                        objectField.setCodeid(synltefield.getCodeid() != null ? synltefield.getCodeid() : "");
                        objectField.setFieldClassId(synltefield.getFieldClassId() != null ? synltefield.getFieldClassId() : "");
                        objectField.setFieldClassCh(synltefield.getFieldClassCh() != null ? synltefield.getFieldClassCh() : "");
                        objectField.setSameWordType(synltefield.getSameWordType() != null ? synltefield.getSameWordType() : "");
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
        return null;
    }

    public ObjectEntity selectObjectPojoByTableIdNew(ObjectEntity objectInfo,
                                                     List<InputObjectCreateVO> allInpuObjectList,
                                                     FieldCodeEntity fieldCodeVal,
                                                     ObjectEntity classifyOne) {
        ObjectEntity oneObjectPojoTable = new ObjectEntity();
        try {
            if (objectInfo == null) {
                return oneObjectPojoTable;
            }
            if (!StringUtils.isEmpty(objectInfo.getSecretLevel())) {
                objectInfo.setSecretLevelCh(KeyStrEnum.getKeyByNameAndType("1_" + objectInfo.getSecretLevel(), Common.DATASECURITYLEVEL));
            }
            // 拼接对应的数据 object 这个表里面存在的数据
            // 序号
            oneObjectPojoTable.setObjectId(objectInfo.getObjectId());
            // 数据名
            oneObjectPojoTable.setObjectName(objectInfo.getObjectName());
            // 真实表名
            oneObjectPojoTable.setRealTablename(objectInfo.getTableName());

            //源应用系统名称二级
            oneObjectPojoTable.setDataSource(String.valueOf(objectInfo.getDataSource()));
            //根据二级去码表回填一级
            if (objectInfo.getDataSource() == null) {
                log.info("源应用系统名称（DATA_SOUCE）为空");
            } else {
                oneObjectPojoTable.setDataSourceOne(fieldCodeVal.getCodeId());
            }

            oneObjectPojoTable.setTableId(objectInfo.getTableId());
            // 存储表状态
            if (objectInfo.getObjectState() != null) {
                oneObjectPojoTable.setObjectStateVo(KeyIntEnum.getValueByKeyAndType(objectInfo.getObjectState(), Common.OBJECT_STATE));
            }
            // 存储方式
            if (objectInfo.getStoreType() != null) {
                oneObjectPojoTable.setStoreTypeVo(KeyIntEnum.getValueByKeyAndType(objectInfo.getStoreType(), Common.STORETYPE));
            }
            //更新表类型 20200507 majia添加
            if (objectInfo.getIsActiveTable() != null) {
                oneObjectPojoTable.setIsActiveTable(objectInfo.getIsActiveTable());
            }
            // 厂商 存储方式 存储的数据源
            List<String> outOobjSourceCodeList = new ArrayList<>();
            // 源表ID  sourceId 的值 20191118号新增需求
            oneObjectPojoTable.setSourceId(objectInfo.getSourceId());
            // 注释的字段信息
            oneObjectPojoTable.setObjectMemo(objectInfo.getObjectMemo());
            //数据分级
            if (!StringUtils.isEmpty(objectInfo.getSecretLevel()) && objectInfo.getSecretLevel() != null) {
                if (objectInfo.getSecretLevel().length() == 1) {
                    oneObjectPojoTable.setSecretLevel("0" + objectInfo.getSecretLevel());
                } else {
                    oneObjectPojoTable.setSecretLevel(objectInfo.getSecretLevel());
                }
            }
            if (!StringUtils.isEmpty(objectInfo.getSecretLevelCh()) && objectInfo.getSecretLevelCh() != null) {
                oneObjectPojoTable.setSecretLevelCh(objectInfo.getSecretLevelCh());
            }
            if (objectInfo.getVersion() != null) {
                oneObjectPojoTable.setVersion(objectInfo.getVersion());
            }
            //资源标签
            if (StringUtils.isNotBlank(objectInfo.getSjzybq1())) {
                oneObjectPojoTable.setSjzybq1(objectInfo.getSjzybq1());
            }
            if (StringUtils.isNotBlank(objectInfo.getSjzybq2())) {
                oneObjectPojoTable.setSjzybq2(objectInfo.getSjzybq2());
            }
            if (StringUtils.isNotBlank(objectInfo.getSjzybq3())) {
                oneObjectPojoTable.setSjzybq3(objectInfo.getSjzybq3());
            }
            if (StringUtils.isNotBlank(objectInfo.getSjzybq4())) {
                oneObjectPojoTable.setSjzybq4(objectInfo.getSjzybq4());
            }
            if (StringUtils.isNotBlank(objectInfo.getSjzybq5())) {
                oneObjectPojoTable.setSjzybq5(objectInfo.getSjzybq5());
            }
            if (StringUtils.isNotBlank(objectInfo.getSjzybq6())) {
                oneObjectPojoTable.setSjzybq6(objectInfo.getSjzybq6());
            }

            List<String> outOobjSourceList = new ArrayList<>();
            for (InputObjectCreateVO inputObjectCreate : allInpuObjectList) {
                Integer sysId = inputObjectCreate.getOutSysId();
                Integer outOobjSource = inputObjectCreate.getOutOobjSource();
                outOobjSourceList.add(KeyIntEnum.getValueByKeyAndType(outOobjSource, Common.MANUFACTURER_NAME));
                outOobjSourceCodeList.add(String.valueOf(outOobjSource));
            }
            if (outOobjSourceCodeList.size() >= 1) {
                oneObjectPojoTable.setOwnerFactoryCode(outOobjSourceCodeList.get(0));
                oneObjectPojoTable.setOwnerFactory(outOobjSourceList.get(0));
            } else {
                oneObjectPojoTable.setOwnerFactoryCode("0");
                oneObjectPojoTable.setOwnerFactory("全部");
            }
            // TODO 存储数据源信息
            // 根据 codeTextTd的值获取对应的中文翻译
            if (StringUtils.isEmpty(oneObjectPojoTable.getDataSource())) {
                oneObjectPojoTable.setDataSourceCh("");
            } else {
                if (StringUtils.isEmpty(fieldCodeVal.getValText())) {
                    oneObjectPojoTable.setDataSourceCh("错误协议代码");
                } else {
                    oneObjectPojoTable.setDataSourceCh(fieldCodeVal.getValText());
                }
            }
            //  获取这个tableid在 数据组织 ， 数据来源的分级分类信息。
            if (classifyOne != null) {
                //处理组织分类中文信息
                String orgClassify = classifyOne.getOrganizationClassify();
                if (StringUtils.isNotBlank(orgClassify) && orgClassify.endsWith("/")) {
                    //处理了只有1级分类时(业务要素索引库和其它)
                    oneObjectPojoTable.setOrganizationClassify(orgClassify.substring(0, orgClassify.length() - 2));
                } else {
                    if (orgClassify.contains("原始库")) {
                        //如果是原始库，则直接赋值3级
                        oneObjectPojoTable.setOrganizationClassify(orgClassify);
                    } else {
                        //非原始库，只赋值1级和2级
                        oneObjectPojoTable.setOrganizationClassify(orgClassify.split("/")[1] + "/" + orgClassify.split("/")[2]);
                    }
                }
                //处理来源分类中文信息
                oneObjectPojoTable.setSourceClassify(classifyOne.getSourceClassify());
                //回填组织分类和来源分类的id值
                String classIds = classifyOne.getClassIds();
                if (classIds.contains(",")) {
                    String[] classIdSplit = classIds.split(",");
                    List<String> classIdList = Arrays.asList(classIdSplit);
                    List<String> finalClassIdList = new ArrayList<>();

                    String organizationClassify = Common.DATA_ORGANIZATION_CODE;
                    for (int i = 0; i < classIdList.size(); i++) {
                        if (i < classIdList.size() - 1) {
                            //除原始库外，其它组织分类primary和first存储的一样，所以跳出
                            if (classIdList.get(i).equalsIgnoreCase(classIdList.get(i + 1))) {
                                continue;
                            }
                        }
                        String s = classIdList.get(i);
                        organizationClassify = organizationClassify + s;
                        finalClassIdList.add(organizationClassify);
                    }
                    //拼接后的classId
                    String realClassIds = StringUtils.join(finalClassIdList, ",");
                    oneObjectPojoTable.setClassIds(realClassIds);
                } else {
                    String realClassIds = Common.DATA_ORGANIZATION_CODE + classIds;
                    oneObjectPojoTable.setClassIds(realClassIds);
                }

                //赋值来源分类
                String sourceClassIds = classifyOne.getSourceClassIds();
                if (sourceClassIds.contains(",")) {
                    String[] sourceIdSplit = sourceClassIds.split(",");
                    List<String> sourceIdList = Arrays.asList(sourceIdSplit);
                    List<String> finalSourceIdList = new ArrayList<>();
                    String sourceClassCode = Common.DATA_SOURCE_CODE;
                    for (String data : sourceIdList) {
                        sourceClassCode = sourceClassCode + data;
//						String finalSourceId = Common.DATA_SOURCE_CODE+data;
                        finalSourceIdList.add(sourceClassCode);
                    }
                    //拼接后的classId
                    String realSourceIds = StringUtils.join(finalSourceIdList, ",");
                    oneObjectPojoTable.setSourceClassIds(realSourceIds);
                } else {
                    String realSourceIds = Common.DATA_SOURCE_CODE + sourceClassIds;
                    oneObjectPojoTable.setClassIds(realSourceIds);
                }
//                oneObjectPojoTable.setClassIds(classifyOne.getClassIds());
//                oneObjectPojoTable.setSourceClassIds(classifyOne.getSourceClassIds());
            } else {
                oneObjectPojoTable.setOrganizationClassify("未知/未知");
                oneObjectPojoTable.setSourceClassify("未知/未知");
                oneObjectPojoTable.setClassIds("");
                oneObjectPojoTable.setSourceClassIds("");
            }
            oneObjectPojoTable.setCreateTime(objectInfo.getCreateTime());
            oneObjectPojoTable.setUpdateTime(objectInfo.getUpdateTime());
            oneObjectPojoTable.setCreator(objectInfo.getCreator());
            oneObjectPojoTable.setUpdater(objectInfo.getUpdater());
        } catch (Exception e) {
            log.error(">>>>>>根据tableId获取对象详细信息报错：", e);
        }
        return oneObjectPojoTable;
    }

    @Override
    public void downloadObjectInfoSql(HttpServletResponse response, List<String> tableIdList) {
        log.info(">>>>>>开始导出标准信息的sql语句");
        BufferedOutputStream buff = null;
        ServletOutputStream outStr = null;
        //list>1导出压缩文件，等于1条导出sql文件
        if (tableIdList.size() > 1) {
            try {
                String name = "数据标准导出" + DateUtil.formatDateTime(new Date(), DateUtil.DEFAULT_PATTERN_DATE_SIMPLE);
                response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(name + ".zip", "UTF-8"));
                response.setContentType("application/octet-stream");
                response.setCharacterEncoding("utf-8");
                ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());


                LambdaQueryWrapper<ObjectEntity> wrapper = Wrappers.lambdaQuery();
                wrapper.in(ObjectEntity::getTableId, tableIdList);
                // object
                List<ObjectEntity> objectPojos = objectMapper.selectList(wrapper);
                // objectfield
                LambdaQueryWrapper<ObjectFieldEntity> wrapperOF = Wrappers.lambdaQuery();
                wrapperOF.eq(ObjectFieldEntity::getDeleted, 0);
                List<ObjectFieldEntity> objectFieldLists = objectFieldMapper.selectList(wrapperOF);
                Map<Integer, List<ObjectFieldEntity>> objectFieldMap = objectFieldLists.stream().collect(Collectors.groupingBy(ObjectFieldEntity::getObjectId));
                List<KeyValueVO> synlteFieldInfos = synlteFieldMapper.getGadsjFieldByTexts();
                Map<String, List<KeyValueVO>> synlteFieldInfoMap = synlteFieldInfos.stream().collect(Collectors.groupingBy(KeyValueVO::getValue));
                LambdaQueryWrapper<EntityElementEntity> wrapperEE = Wrappers.lambdaQuery();
                wrapperEE.eq(EntityElementEntity::getCreateMode, 1);
                List<EntityElementEntity> elementNames = entityElementMapper.selectList(wrapperEE);
                //  获取 表字段信息的 字段分类信息  synltefield. FIELD_CLASS 这个字段里面
                List<ObjectFieldEntity> list = synlteFieldMapper.queryCodeClassList();

                //大于1条，导出zip文件
                for (String tableId : tableIdList) {
                    ObjectEntity objectInfo = objectPojos.stream().filter(d -> d.getTableId().equalsIgnoreCase(tableId)).findFirst().orElse(new ObjectEntity());
                    List<ObjectFieldEntity> objectFieldList1 = new ArrayList<>();
                    for (Integer objectId : objectFieldMap.keySet()) {
                        if (objectInfo.getObjectId() != null && objectInfo.getObjectId() == objectId) {
                            objectFieldList1 = objectFieldMap.get(objectId);
                        }
                    }
                    List<ObjectFieldEntity> objectFieldList = selectObjectFieldByObjectIdNew(objectInfo, objectFieldList1, synlteFieldInfoMap, elementNames, list);
                    String tableNameCh = objectInfo.getObjectName().replace("/", "、");
                    zipOutputStream.putNextEntry(new ZipEntry(String.format("%s@%s.sql", objectInfo.getTableName(), tableNameCh)));
                    //生成object的sql
                    StringBuffer sql = jointSql(objectInfo);
                    //生成objectField的sql
                    for (ObjectFieldEntity objectField : objectFieldList) {
                        sql.append(jointFieldSql(objectField));
                    }
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bos.write(sql.toString().getBytes("UTF-8"));
                    bos.writeTo(zipOutputStream);
                    bos.flush();
                    zipOutputStream.flush();
                    zipOutputStream.closeEntry();
                    IOUtils.closeQuietly(bos);
                }
                zipOutputStream.finish();
            } catch (Exception e) {
                throw SystemException.asSystemException(ErrorCodeEnum.DOWNLOAD_ERROR, "下载文件失败");
            }
        } else if (tableIdList.size() == 1) {
            //设置响应头
            setServletResponseText(response);
            String tableId = tableIdList.get(0);
            //通过tableId获取标准信息
            ObjectEntity objectInfo = SelectUtil.getObjectEntityByTableId(objectMapper, tableId);
            StringBuffer sql = jointSql(objectInfo);
            //通过tableId获取字段信息
            List<ObjectFieldEntity> objectFieldList = dataSetStandardService.queryObjectFieldListByTableId(tableId);
            for (ObjectFieldEntity data : objectFieldList) {
                sql.append(jointFieldSql(data));
            }
            try {
                outStr = response.getOutputStream();
                buff = new BufferedOutputStream(outStr);
                buff.write(sql.toString().getBytes("UTF-8"));
                buff.flush();
            } catch (Exception e) {
                throw SystemException.asSystemException(ErrorCodeEnum.DOWNLOAD_ERROR, "下载文件失败");
            } finally {
                try {
                    buff.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    outStr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 设置响应头
     *
     * @param response 响应对象
     */
    private HttpServletResponse setServletResponseText(HttpServletResponse response) {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''");
        return response;
    }

    /**
     * 拼接标准插入sql语句
     *
     * @param data 标准信息对象
     * @return
     */
    private StringBuffer jointSql(ObjectEntity data) {
        String databaseType = env.getProperty("database.type");
        StringBuffer baseSql = new StringBuffer();
        if (Common.DAMENG.equalsIgnoreCase(databaseType)) {
            baseSql = new StringBuffer("insert into synlte.\"OBJECT\"(OBJECTID,OBJECTNAME,OBJECTMEMO,OBJECTSTATE,TABLEID,TABLENAME,DATATYPE," +
                    "STORETYPE,DATA_SOURCE,SECRETLEVEL,RELATE_TABLENAME,SOURCEID,ISACTIVETABLE,DELETED,SJZYBQ1,SJZYBQ2,SJZYBQ3," +
                    "SJZYBQ4,SJZYBQ5,SJZYLYLXVALUE,SJZZYJFLVALLUE,SJZZEJFLVALUE,CREATETIME,UPDATETIME,CREATOR,UPDATER," +
                    "VERSION,\"VERSIONS\",STANDARD_TYPE)values(");
        } else {
            baseSql = new StringBuffer("insert into synlte.object(OBJECTID,OBJECTNAME,OBJECTMEMO,OBJECTSTATE,TABLEID,TABLENAME,DATATYPE," +
                    "STORETYPE,DATA_SOURCE,SECRETLEVEL,RELATE_TABLENAME,SOURCEID,ISACTIVETABLE,DELETED,SJZYBQ1,SJZYBQ2,SJZYBQ3," +
                    "SJZYBQ4,SJZYBQ5,SJZYLYLXVALUE,SJZZYJFLVALLUE,SJZZEJFLVALUE,CREATETIME,UPDATETIME,CREATOR,UPDATER," +
                    "VERSION,VERSIONS,STANDARD_TYPE)values(");
        }
//        StringBuffer baseSql = new StringBuffer("insert into synlte.object(OBJECTID,OBJECTNAME,OBJECTMEMO,OBJECTSTATE,TABLEID,TABLENAME,DATATYPE," +
//                "STORETYPE,DATA_SOURCE,SECRETLEVEL,RELATE_TABLENAME,SOURCEID,ISACTIVETABLE,DELETED,SJZYBQ1,SJZYBQ2,SJZYBQ3," +
//                "SJZYBQ4,SJZYBQ5,SJZYLYLXVALUE,SJZZYJFLVALLUE,SJZZEJFLVALUE,CREATETIME,UPDATETIME,CREATOR,UPDATER," +
//                "VERSION,VERSIONS,STANDARD_TYPE)values(");
        baseSql.append("'" + data.getObjectId() + "'," + "'" + data.getObjectName() + "',");
        baseSql.append(StringUtils.isNotBlank(data.getObjectMemo()) ? "'" + data.getObjectMemo() + "'," : "'',");
        baseSql.append("'" + data.getObjectState() + "',");
        baseSql.append(StringUtils.isNotBlank(data.getTableId()) ? "'" + data.getTableId() + "'," : "'',");
        baseSql.append(StringUtils.isNotBlank(data.getTableName()) ? "'" + data.getTableName() + "'," : "'',");
        baseSql.append(data.getDataType() != null ? "'" + data.getDataType() + "'," : "null,");
        baseSql.append(data.getStoreType() != null ? "'" + data.getStoreType() + "'," : "null,");
        baseSql.append(StringUtils.isNotBlank(data.getDataSource()) ? "'" + data.getDataSource() + "'," : "'',");
        baseSql.append(StringUtils.isNotBlank(data.getSecretLevel()) ? "'" + data.getSecretLevel() + "'," : "'',");
        baseSql.append("'OBJECTFIELD',");
        baseSql.append(StringUtils.isNotBlank(data.getSourceId()) ? "'" + data.getSourceId() + "'," : "'',");
        baseSql.append(data.getIsActiveTable() != null ? "'" + data.getIsActiveTable() + "'," : "null,");
        baseSql.append("'0',");
        baseSql.append(StringUtils.isNotBlank(data.getSjzybq1()) ? "'" + data.getSjzybq1() + "'," : "'',");
        baseSql.append(StringUtils.isNotBlank(data.getSjzybq2()) ? "'" + data.getSjzybq2() + "'," : "'',");
        baseSql.append(StringUtils.isNotBlank(data.getSjzybq3()) ? "'" + data.getSjzybq3() + "'," : "'',");
        baseSql.append(StringUtils.isNotBlank(data.getSjzybq4()) ? "'" + data.getSjzybq4() + "'," : "'',");
        baseSql.append(StringUtils.isNotBlank(data.getSjzybq5()) ? "'" + data.getSjzybq5() + "'," : "'',");
        baseSql.append(StringUtils.isNotBlank(data.getSjzylylxValue()) ? "'" + data.getSjzylylxValue() + "'," : "'',");
        baseSql.append(StringUtils.isNotBlank(data.getSjzzyjflVallue()) ? "'" + data.getSjzzyjflVallue() + "'," : "'',");
        baseSql.append(StringUtils.isNotBlank(data.getSjzzejflValue()) ? "'" + data.getSjzzejflValue() + "'," : "'',");
        baseSql.append("sysdate," + "sysdate,");
        baseSql.append(StringUtils.isNotBlank(data.getCreator()) ? "'" + data.getCreator() + "'," : "'',");
        baseSql.append(StringUtils.isNotBlank(data.getUpdater()) ? "'" + data.getCreator() + "'," : "'',");
        baseSql.append(data.getVersion() != null ? "'" + data.getVersion() + "'," : "null,");
        baseSql.append(StringUtils.isNotBlank(data.getVersions()) ? "'" + data.getVersions() + "'," : "'',");
        baseSql.append(data.getStandardType() != null ? "'" + data.getStandardType() + "');" : "null);");
        baseSql.append("\n" + "commit;" + "\n");

        return baseSql;
    }

    /**
     * 拼接字段（objectField）sql
     *
     * @param data 字段信息
     */
    private StringBuffer jointFieldSql(ObjectFieldEntity data) {
        String databaseType = env.getProperty("database.type");
        StringBuffer baseSql = new StringBuffer();
        if (Common.DAMENG.equalsIgnoreCase(databaseType)) {
            baseSql = new StringBuffer("insert into synlte.objectField(OBJECTID,RECNO,FIELDID,FIELDNAME," +
                    "FIELDCHINEENAME,FIELDDESCRIBE,FIELDTYPE,FIELDLEN,DEFAULTVALUE,INDEXTYPE,ISINDEX,NEEDVALUE,ISCONTORL," +
                    "DELETED,COLUMNNAME,MEMO,TABLEINDEX,ISQUERY,COLUMNNAME_STATE,MD5_INDEX,\n" +
                    "\"VERSIONS\",ISPRIVATE,STANDARD_RECNO,PK_RECNO,PARTITION_RECNO,CLUST_RECNO,ORA_SHOW,\n" +
                    "ODPS_PATTITION,PRO_TYPE,FIELDSOURCETYPE,DETERMINERID,GADSJ_FIELDID)values(");
        } else {
            baseSql = new StringBuffer("insert into synlte.objectField(OBJECTID,RECNO,FIELDID,FIELDNAME," +
                    "FIELDCHINEENAME,FIELDDESCRIBE,FIELDTYPE,FIELDLEN,DEFAULTVALUE,INDEXTYPE,ISINDEX,NEEDVALUE,ISCONTORL," +
                    "DELETED,COLUMNNAME,MEMO,TABLEINDEX,ISQUERY,COLUMNNAME_STATE,MD5_INDEX,\n" +
                    "VERSIONS,ISPRIVATE,STANDARD_RECNO,PK_RECNO,PARTITION_RECNO,CLUST_RECNO,ORA_SHOW,\n" +
                    "ODPS_PATTITION,PRO_TYPE,FIELDSOURCETYPE,DETERMINERID,GADSJ_FIELDID)values(");
        }
//        StringBuffer baseSql = new StringBuffer("insert into synlte.objectField(OBJECTID,RECNO,FIELDID,FIELDNAME," +
//                "FIELDCHINEENAME,FIELDDESCRIBE,FIELDTYPE,FIELDLEN,DEFAULTVALUE,INDEXTYPE,ISINDEX,NEEDVALUE,ISCONTORL," +
//                "DELETED,COLUMNNAME,MEMO,TABLEINDEX,ISQUERY,COLUMNNAME_STATE,MD5_INDEX,\n" +
//                "VERSIONS,ISPRIVATE,STANDARD_RECNO,PK_RECNO,PARTITION_RECNO,CLUST_RECNO,ORA_SHOW,\n" +
//                "ODPS_PATTITION,PRO_TYPE,FIELDSOURCETYPE,DETERMINERID,GADSJ_FIELDID)values(");
        baseSql.append("'" + data.getObjectId() + "'," + "'" + data.getRecno() + "'," + "'" + data.getFieldId() + "'," + "'" + data.getFieldName() + "',");
        baseSql.append(StringUtils.isNotBlank(data.getFieldChineseName()) ? "'" + data.getFieldChineseName() + "'," : "'',");
        baseSql.append(StringUtils.isNotBlank(data.getFieldDescribe()) ? "'" + data.getFieldDescribe() + "'," : "'',");
        baseSql.append(data.getFieldType() != null ? "'" + data.getFieldType() + "'," : "'',");
        baseSql.append(data.getFieldLen() != null ? "'" + data.getFieldLen() + "'," : "null,");
        baseSql.append(StringUtils.isNotBlank(data.getDefaultValue()) ? "'" + data.getDefaultValue() + "'," : "'',");
        baseSql.append(data.getIndexType() != null ? "'" + data.getIndexType() + "'," : "null,");
        baseSql.append(data.getIsIndex() != null ? "'" + data.getIsIndex() + "'," : "null,");
        baseSql.append(data.getNeedValue() != null ? "'" + data.getNeedValue() + "'," : "null,");
        baseSql.append(data.getIsContorl() != null ? "'" + data.getIsContorl() + "'," : "null,");
        baseSql.append("'0',");
        baseSql.append(StringUtils.isNotBlank(data.getColumnName()) ? "'" + data.getColumnName() + "'," : "'',");
        baseSql.append(StringUtils.isNotBlank(data.getMemo()) ? "'" + data.getMemo() + "'," : "'',");
        baseSql.append(data.getTableIndex() != null ? "'" + data.getTableIndex() + "'," : "'',");
        baseSql.append(data.getIsQuery() != null ? "'" + data.getIsQuery() + "'," : "null,");
        baseSql.append(data.getColumnNameState() != null ? "'" + data.getColumnNameState() + "'," : "null,");
        baseSql.append(data.getMd5Index() != null ? "'" + data.getMd5Index() + "'," : "null,");
        baseSql.append(data.getVersions() != null ? "'" + data.getVersions() + "'," : "null,");
        baseSql.append(data.getIsPrivate() != null ? "'" + data.getIsPrivate() + "'," : "'',");
        baseSql.append(data.getStandardRecno() != null ? "'" + data.getStandardRecno() + "'," : "null,");
        baseSql.append(data.getPkRecno() != null ? "'" + data.getPkRecno() + "'," : "null,");
        baseSql.append(data.getPartitionRecno() != null ? "'" + data.getPartitionRecno() + "'," : "null,");
        baseSql.append(data.getClustRecno() != null ? "'" + data.getClustRecno() + "'," : "null,");
        baseSql.append(data.getOraShow() != null ? "'" + data.getOraShow() + "'," : "null,");
        baseSql.append(data.getOdpsPattition() != null ? "'" + data.getOdpsPattition() + "'," : "'',");
        baseSql.append(data.getProType() != null ? "'" + data.getProType() + "'," : "'',");
        baseSql.append(StringUtils.isNotBlank(data.getFieldDescribe()) ? "'" + data.getFieldDescribe() + "'," : "'',");
        baseSql.append(StringUtils.isNotBlank(data.getDeterminerId()) ? "'" + data.getDeterminerId() + "'," : "'',");
        baseSql.append(StringUtils.isNotBlank(data.getGadsjFieldId()) ? "'" + data.getGadsjFieldId() + "');" : "'');");
        baseSql.append("\n" + "commit;" + "\n");

        return baseSql;
    }

    @Override
    public void downloadFieldTempExcel(HttpServletResponse response) {
        InputStream in = null;
        ServletOutputStream outputStream = null;
        try {
            log.info(">>>>>>开始字段模板excel文档");
            outputStream = response.getOutputStream();
            ApplicationHome app = new ApplicationHome(getClass());
            String execlFile = app.getDir().getAbsolutePath() + "/static/tempfile/src_tab_structure_tmp.xlsx";
            log.info("excel文件的路径是：{}", execlFile);
            File xlsFile = new File(execlFile);
            if (xlsFile.exists()) {
                response.setContentType("application/vnd.ms-excel");
                response.setCharacterEncoding("utf-8");
                String fileName = URLEncoder.encode("源数据物理结构模板", "UTF-8") + ".xlsx";
                response.setHeader("Content-disposition",
                        "attachment;filename*=utf-8''" + fileName);
                in = new BufferedInputStream(new FileInputStream(execlFile));
                int len;
                byte[] bytes = new byte[1024];
                while ((len = in.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, len);
                }
                outputStream.flush();
            } else {
                throw new Exception("下载模板失败：系统中无源数据物理结构模板文件");
            }
        } catch (SystemException e) {
            log.error(ExceptionUtil.getExceptionTrace(e));
            throw e;
        } catch (Exception e1) {
            log.error("导出模板失败：\n" + ExceptionUtil.getExceptionTrace(e1));
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    log.error("关闭输出流失败");
                    e.printStackTrace();
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.error("关闭输入流失败");
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public List<ObjectFieldSheetVO> analysisFieldExcel(MultipartFile multipartFile) {
        List<ObjectFieldSheetVO> fieldInfos = new ArrayList<>();
        if (multipartFile == null) {
            return fieldInfos;
        }
        try {
            ExcelListener<ObjectTableSheetVO> listener = new ExcelListener<>();
            fieldInfos = EasyExcelUtil.readObjectFieldExcelSheetUtil(multipartFile, new ObjectFieldSheetVO(), listener, 0);
            for (int i = 0; i < fieldInfos.size(); i++) {
                // 序号
                fieldInfos.get(i).setRecno(i);
                // 是否必填
                String neddValue = fieldInfos.get(i).getNeedValueStr();
                fieldInfos.get(i).setNeedValue(StringUtils.isNotBlank(neddValue) && neddValue.toUpperCase().equalsIgnoreCase("Y") ? 1 : 0);
                // 字段类型
                String fieldType = String.valueOf(SynlteFieldTypeEnum.getSynlteNumType(fieldInfos.get(i).getFieldType()));
                fieldInfos.get(i).setFieldType(fieldType);
                // 根据数据元编码获取标准信息
                SynlteFieldEntity synltefield = dataSetStandardService.getAddColumnByInput("fieldId", fieldInfos.get(i).getFieldId());
                if (synltefield != null) {
                    if (StringUtils.isBlank(fieldInfos.get(i).getFieldType()) && synltefield.getFieldType() != null) {
                        fieldInfos.get(i).setFieldType(String.valueOf(synltefield.getFieldType()));
                    }
                    if (fieldInfos.get(i).getFieldLen() == null && synltefield.getFieldLen() != 0) {
                        fieldInfos.get(i).setFieldLen(synltefield.getFieldLen().intValue());
                    }
                    fieldInfos.get(i).setFieldName(synltefield.getFieldName());         // 标准列名
                    fieldInfos.get(i).setMemo(synltefield.getMemo());                   // 备注
                    fieldInfos.get(i).setSameWordType(synltefield.getSameId());         // 语义类型
                    fieldInfos.get(i).setFieldClassCh(synltefield.getFieldClassCh());   // 字段分类
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fieldInfos;
    }

    @Override
    public void downloadCreateTableSql(HttpServletResponse response, List<String> tableIdList, String dsType) {
//        Common.DATA_TYPE_LIST
        if (StringUtils.isBlank(dsType)) {
            log.error(">>>>>>数据库类型为空，不能生成sql");
        }
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        try {
            log.info(">>>>>>开始导出标准建表sql语句");
            if (tableIdList.size() > 1) {
                String name = String.format("建表语句导出%s", DateUtil.formatDateTime(new Date(), DateUtil.DEFAULT_PATTERN_DATE_SIMPLE));
                response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(name + ".zip", "UTF-8"));
                ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());

                LambdaQueryWrapper<ObjectEntity> wrapper = Wrappers.lambdaQuery();
                wrapper.in(ObjectEntity::getTableId, tableIdList);
                List<ObjectEntity> objectEntities = objectMapper.selectList(wrapper);
                Map<String, List<ObjectEntity>> tableIdListMap = objectEntities.stream().collect(Collectors.groupingBy(ObjectEntity::getTableId));

                for (String tableId : tableIdList) {
                    List<ObjectEntity> list = tableIdListMap.get(tableId);
                    BuildTableInfoVO buildTableInfoVO = injectBuildTableInfoVO(list.get(0), dsType);
                    zipOutputStream.putNextEntry(new ZipEntry(String.format("%s(%s).sql", buildTableInfoVO.getTableName(), dsType)));
                    //生成建表语句
                    String creatSql = createTableService.showCreateTableSql(buildTableInfoVO);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bos.write(creatSql.toString().getBytes("UTF-8"));
                    bos.writeTo(zipOutputStream);
                    bos.flush();
                    zipOutputStream.flush();
                    zipOutputStream.closeEntry();
                    IOUtils.closeQuietly(bos);
                }
                zipOutputStream.finish();
            } else {
                ObjectEntity objectEntity = SelectUtil.getObjectEntityByTableId(objectMapper, tableIdList.get(0));
                BuildTableInfoVO buildTableInfoVO = injectBuildTableInfoVO(objectEntity, dsType);
                String creatSql = createTableService.showCreateTableSql(buildTableInfoVO);
                String name = String.format("%s(%s)", buildTableInfoVO.getTableName(), dsType);
                response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(name + ".sql", "UTF-8"));

                ServletOutputStream outStr = response.getOutputStream();
                BufferedOutputStream buff = new BufferedOutputStream(outStr);
                buff.write(creatSql.toString().getBytes("UTF-8"));
                buff.flush();
                buff.close();
                outStr.close();
            }
        } catch (Exception e) {
            log.error(">>>>>>导出标准建表sql语句出错：", e);
        }
    }

    public BuildTableInfoVO injectBuildTableInfoVO(ObjectEntity objectEntity, String dsType) {
        BuildTableInfoVO buildTableInfoVO = new BuildTableInfoVO();
        List<ObjectFieldEntity> columnList = dataSetStandardService.queryObjectFieldListByTableId(objectEntity.getTableId());
        // 表字段类型对应
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("dataBaseType", dsType);
        jsonObject.put("columnData", columnList);
        columnList = createTableService.columnCorrespondClick(jsonObject);
        buildTableInfoVO.setColumnData(columnList);
        buildTableInfoVO.setTableName(objectEntity.getTableName());
        buildTableInfoVO.setTableNameCH(objectEntity.getObjectName());
        buildTableInfoVO.setDsType(dsType);
        switch (dsType.toUpperCase()) {
            case "ADS":
                for (ObjectFieldEntity objectField : columnList) {
                    if (objectField.getPkRecno() != null && objectField.getPkRecno() >= 1) {
                        buildTableInfoVO.setPartitionFirst(objectField.getColumnName());
                        buildTableInfoVO.setPartitionFirstNum("512");
                        break;
                    }
                }
                for (ObjectFieldEntity objectField : columnList) {
                    if (objectField.getPkRecno() != null
                            && objectField.getPkRecno() >= 1
                            && objectField.getPartitionRecno() != null
                            && objectField.getPartitionRecno() == 2 && objectField.getColumnName().equalsIgnoreCase("dt")) {
                        buildTableInfoVO.setPartitionSecond(objectField.getColumnName());
                        buildTableInfoVO.setPartitionSecondNum("365");
                        break;
                    }
                }
                break;
            case "ODPS":
                buildTableInfoVO.setIsPartition(0);
                buildTableInfoVO.setPartitionType(1);
                buildTableInfoVO.setLifeCycle(36500);
                break;
            case "HIVE-CDH":
                buildTableInfoVO.setIsPartition(0);
                buildTableInfoVO.setPartitionType(1);
                buildTableInfoVO.setLifeCycle(36500);
                break;
            case "HIVE-HUAWEI":
                buildTableInfoVO.setIsPartition(0);
                buildTableInfoVO.setPartitionType(1);
                buildTableInfoVO.setLifeCycle(36500);
                break;
            case "CLICKHOUSE":
                List<CkColumnVO> ckColumnVOS = new ArrayList<>();
                CkColumnVO ckColumnVO = new CkColumnVO();
                ckColumnVO.setColumnName(columnList.get(0).getColumnName());
                ckColumnVO.setPartitionType("column");
                ckColumnVOS.add(ckColumnVO);
                buildTableInfoVO.setCkOrderByColumn(ckColumnVOS);
                buildTableInfoVO.setCkPartitionColumn(ckColumnVOS);

                buildTableInfoVO.setLifeCycle(36500);
                break;
            case "ORACLE":
                break;
            default:
        }
        return buildTableInfoVO;
    }

}
