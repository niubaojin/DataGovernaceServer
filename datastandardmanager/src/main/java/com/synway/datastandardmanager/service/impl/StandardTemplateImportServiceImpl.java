package com.synway.datastandardmanager.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.synway.common.bean.ServerResponse;
import com.synway.datastandardmanager.constant.Common;
import com.synway.datastandardmanager.dao.master.*;
import com.synway.datastandardmanager.dao.standard.ResourceManageAddColumnDao;
import com.synway.datastandardmanager.exceptionhandler.ErrorCode;
import com.synway.datastandardmanager.exceptionhandler.SystemException;
import com.synway.datastandardmanager.listener.ExcelListener;
import com.synway.datastandardmanager.pojo.*;
import com.synway.datastandardmanager.pojo.enums.*;
import com.synway.datastandardmanager.pojo.standardtemplateexcel.ObjectFieldSheet;
import com.synway.datastandardmanager.pojo.standardtemplateexcel.ObjectTableSheet;
import com.synway.datastandardmanager.pojo.standardtemplateexcel.TableColumnSheet;
import com.synway.datastandardmanager.pojo.synlteelement.SynlteElementVO;
import com.synway.datastandardmanager.pojo.synltefield.SynlteFieldClassEnum;
import com.synway.datastandardmanager.service.ResourceManageAddService;
import com.synway.datastandardmanager.service.ResourceManageService;
import com.synway.datastandardmanager.service.StandardTemplateImportService;
import com.synway.datastandardmanager.util.*;
import org.apache.catalina.connector.ClientAbortException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author wangdongwei
 * @ClassName StandardTemplateImportServiceImpl
 * @description excel 导入导出的相关操作
 * @date 2020/12/8 15:33
 */
@Service
public class StandardTemplateImportServiceImpl implements StandardTemplateImportService {
    private Logger logger = LoggerFactory.getLogger(StandardTemplateImportServiceImpl.class);

    @Autowired
    private ResourceManageService resourceManageService;

    @Autowired
    private ResourceManageAddService resourceManageAddServiceImpl;

    @Autowired
    private StandardResourceManageDao standardResourceManageDao;

    @Autowired
    private FieldCodeValDao fieldCodeValDao;

    @Autowired
    private ResourceManageAddColumnDao resourceManageAddColumnDao;

    @Autowired
    private ResourceManageDao resourceManageDao;

    @Autowired
    private SynlteFieldDao synlteFieldDao;

    @Autowired
    private SynlteElementDao elementDao;

    @Autowired()
    private Environment env;

    private static Lock lock = new ReentrantLock();


    @Override
    public void downloadObjectInfoExcel(HttpServletResponse response, String name,List<String> tableIdList) throws IOException {
        // 设置响应头
        if (tableIdList.size() > 1){
            response.setHeader("Content-Disposition","attachment;fileName=" + URLEncoder.encode(name+".zip", "UTF-8"));
            response.setContentType("application/zip");
            response.setCharacterEncoding("utf-8");
        }else {
            response.setHeader("Content-Disposition","attachment;fileName=" + URLEncoder.encode(name+".xls", "UTF-8"));
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
        }
        ServletOutputStream outputStream = response.getOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
        try{
            logger.info("=====开始导出标准信息=====");
            String[] objectTitles = { "表名","标准表名","源数据名称","对应的数据项描述","表描述","MD_ID取值","主键","过滤列",
                    "分区列","二级分区列","聚集列","加载方式","支持操作类型","ADS(HBASE保存时间)","HDFS源文件保存数","预估数据量","其他要求说明"};

            String[] objectName = new String[]{"tableName","tableId","sourceId",
                    "objectMemo","objectName","md5Index","pkRecno","","partitionRecno","secondPartitionRecno","clustRecno",
                    "","","","","",""};

            String[] standardTableInfoTitles = new String[]{"objectid","tablename","hivetablename","tableid","sourceid","OBJECTNAME","DATA_SOURCE","SJZYLYLXVALUE","SJZYLYLXEJVALUE","SECRETLEVEL",
                    "SJZZYJFLVALLUE","SJZZEJFLVALUE","SJZYBQ1","SJZYBQ2","SJZYBQ3","SJZYBQ4","CREATETIME","ISACTIVETABLE","standard_type","RELATE_TABLENAME","OBJECTMEMO","ElLEMENTNAME","OBJECTSTATE","CREATOR","UPDATER"};

            String[] standardTableInfoName = new String[]{"objectId","tableName","hiveTableName","objectId","sourceId","objectName","dataSource","SJZYLYLXVALUE","","dataLevel","SJZZYJFLVALLUE","SJZZEJFLVALUE",
                    "sjzybq1","sjzybq2","sjzybq3","sjzybq4","createTime","isActiveTable","standardType","relateTableName","objectMemo","ellementName","objectState","creator","updater"};

            String[] columnTitles = {"字段名","元素编码","映射部标内部标识符","映射部标数据元限定词","字段类型","必填","索引","序列","中文名称",
                    "字段分类","安全级别","是否可以布控","备注","私有协议","是否MD5","标准中的索引","添加时间","标准中的源名","标准中的顺序","显示","近线显示"};

            String[] objectFieldName = {"columnName","fieldId","gadsjFieldId","determinerId","fieldType","needValueStr","isIndexStr","recno","fieldChineseName",
                    "fieldClassId","securityLevel","isContorlStr","memo","isPrivate","md5Index","tableIndex","version","","standardRecno","","oraShowStr"};

            //如果大于1条则导出zip文件，1条信息则是excel文件
            if(tableIdList.size() > 1){
                // object
                List<ObjectPojo> objectPojos = resourceManageDao.selectObjectPojoByTableIds(tableIdList);
                List<InputObjectCreate> allInpuObjectList = standardResourceManageDao.getAllInputObjects(tableIdList);
                Map<String, List<InputObjectCreate>> allInpuObjectListMap = allInpuObjectList.stream().collect(Collectors.groupingBy(InputObjectCreate::getOutObjEngName));
                List<FieldCodeVal> fieldCodeVals = resourceManageDao.selectOneSysNames();
                List<ObjectPojoTable> classifyOnes = resourceManageDao.getClassifyByTableids();
                // objectfield
                List<ObjectField> objectFieldLists = resourceManageDao.selectObjectFieldByObjectIds();
                Map<Long, List<ObjectField>> objectFieldMap = objectFieldLists.stream().collect(Collectors.groupingBy(ObjectField::getObjectId));
                List<PageSelectOneValue> synlteFieldInfos = synlteFieldDao.getGadsjFieldByTexts();
                Map<String, List<PageSelectOneValue>> synlteFieldInfoMap = synlteFieldInfos.stream().collect(Collectors.groupingBy(PageSelectOneValue::getValue));
                List<SynlteElementVO> elementNames = elementDao.searchElementNameByIds();
                //  获取 表字段信息的 字段分类信息  synltefield. FIELD_CLASS 这个字段里面
                List<Synltefield> list = resourceManageDao.getCodeTextAndCodeidByObjectFields();

                //将要导出的标准信息都回填
                for (int i = 0; i < tableIdList.size(); i++){
                    String tableId = tableIdList.get(i);
                    ObjectPojo objectInfo = objectPojos.stream().filter(d -> d.getTableId().equalsIgnoreCase(tableId)).findFirst().orElse(new ObjectPojo());
                    // 获取输入和输出的对应关系
                    List<InputObjectCreate> inputObjectCreate = new ArrayList<>();
                    for (String outObjEngName : allInpuObjectListMap.keySet()){
                        if (outObjEngName.equalsIgnoreCase(tableId)){
                            inputObjectCreate = allInpuObjectListMap.get(outObjEngName);
                        }
                    }
                    // fieldCodeVal
                    FieldCodeVal fieldCodeVal = new FieldCodeVal();
                    if (objectInfo.getTableId() != null && objectInfo.getDataSource() != null){
                        fieldCodeVal = fieldCodeVals.stream().filter(d -> d.getValValue().equalsIgnoreCase(objectInfo.getDataSource())).findFirst().orElse(new FieldCodeVal());
                    }
                    // 字段信息
                    ObjectPojoTable classifyOne = classifyOnes.stream().filter(d -> d.getTableId().equalsIgnoreCase(tableId)).findFirst().orElse(new ObjectPojoTable());
                    List<ObjectField> objectFieldList1 = new ArrayList<>();
                    for (Long objectId : objectFieldMap.keySet()){
                        if (objectInfo.getObjectId() != null && objectInfo.getObjectId() == objectId){
                            objectFieldList1 = objectFieldMap.get(objectId);
                        }
                    }
                    // 标准表信息
//                    ObjectPojoTable objectPojoInfo = resourceManageService.selectObjectPojoByTableId(tableId);
                    ObjectPojoTable objectPojoInfo = selectObjectPojoByTableIdNew(objectInfo, inputObjectCreate, fieldCodeVal, classifyOne);
                    //查出标准字段信息
//                    List<ObjectField> objectFieldList = resourceManageService.selectObjectFieldByObjectId(tableId);
                    List<ObjectField> objectFieldList = selectObjectFieldByObjectIdNew(objectInfo, objectFieldList1, synlteFieldInfoMap, elementNames, list);

                    // 其他信息注入
                    ExportObjectInfo exportObjectInfo = getExportObjectInfo(objectPojoInfo, objectFieldList);
                    List<ObjectField> objectFields = exportObjectInfo.getObjectFieldInfo();
                    for(ObjectField objectField : objectFields){
                        // 字段类型和长度合起来
                        String fieldTypeNew = SynlteFieldType.getSynlteFieldType(Integer.valueOf(objectField.getFieldType())) + "(" + objectField.getFieldLen() + ")";
                        objectField.setFieldType(fieldTypeNew);
                    }
                    //生成workbook
                    HSSFWorkbook workbook = ExcelHelper.exportHorizontalExcelZip(new ExportObjectInfo(), exportObjectInfo, objectInfo, objectFields, objectTitles, standardTableInfoTitles, columnTitles,
                            "表说明","标准表信息", "表字段", objectName,standardTableInfoName, objectFieldName);
                    String tableNameCh = exportObjectInfo.getObjectName().replace("/", "、");
                    String xlsName = String.format("%s@%s.xls", exportObjectInfo.getTableName(), tableNameCh);
                    try {
                        //压缩文件中放入excel文件
                        zipOutputStream.putNextEntry(new ZipEntry(xlsName));
                        workbook.write(zipOutputStream);
                        zipOutputStream.closeEntry();
                        workbook.close();
                    }catch (IOException e){
                        logger.error("文件处理失败：{}", xlsName, e);
                    }
                }
//                zipOutputStream.finish();
            }else{
                String tableId = tableIdList.get(0);
                //根据tableId查询标准信息
                ObjectPojoTable objectPojoInfo = resourceManageService.selectObjectPojoByTableId(tableId);
                // 标准表信息
                ObjectPojo objectInfo = resourceManageDao.selectObjectPojoByTableId(tableId);
                //查出标准字段信息
                List<ObjectField> objectFieldList = resourceManageService.selectObjectFieldByObjectId(tableId);
                ExportObjectInfo exportObjectInfo = getExportObjectInfo(objectPojoInfo, objectFieldList);
                //表信息
                List<ExportObjectInfo> objects = new ArrayList<>();
                //字段信息
                List<ObjectField> objectFields = new ArrayList<>();

                //添加到list中
                objects.add(exportObjectInfo);
                //字段信息
                List<ObjectField> objectFieldInfo = exportObjectInfo.getObjectFieldInfo();
                for(ObjectField data:objectFieldInfo){
//                    //回填字段类型
//                    data.setFieldType(SynlteFieldType.getSynlteFieldType(Integer.valueOf(data.getFieldType())));
                    // 字段类型和长度合起来
                    String fieldTypeNew = SynlteFieldType.getSynlteFieldType(Integer.valueOf(data.getFieldType())) + "(" + data.getFieldLen() + ")";
                    data.setFieldType(fieldTypeNew);
                }
                objectFields.addAll(objectFieldInfo);
                //生成excel文件
                logger.info(">>>>>>开始导出");
                ExcelHelper.exportHorizontalExcel(new ExportObjectInfo(),objects,objectInfo,objectFields,objectTitles,standardTableInfoTitles,columnTitles,
                        "表说明","标准表信息","表字段", objectName,standardTableInfoName,objectFieldName,outputStream);
                logger.info(">>>>>>导出结束");
            }
        }catch (ClientAbortException e){
            logger.error("客户端中断下载：{}", e);
        }catch (Exception e){
            if (!response.isCommitted()){
                response.sendError(500, "压缩失败");
            }
            logger.error("下载标准信息报错:\n", e);
        }finally {
            zipOutputStream.finish();
        }
    }

    public ObjectPojoTable selectObjectPojoByTableIdNew(ObjectPojo objectInfo,
                                                        List<InputObjectCreate> allInpuObjectList,
                                                        FieldCodeVal fieldCodeVal,
                                                        ObjectPojoTable classifyOne) {
        ObjectPojoTable oneObjectPojoTable = new ObjectPojoTable();
        try {
            if (objectInfo == null) {
                return oneObjectPojoTable;
            }
            if (!StringUtils.isEmpty(objectInfo.getDataLevel())) {
                objectInfo.setDataLevelVo(ObjectSecurityLevelType.getValueById("1_" + objectInfo.getDataLevel()));
            }
            // 拼接对应的数据 object 这个表里面存在的数据
            // 序号
            oneObjectPojoTable.setObjectId(String.valueOf(objectInfo.getObjectId()));
            // 数据名
            oneObjectPojoTable.setDataSourceName(objectInfo.getObjectName());
            // 真实表名
            oneObjectPojoTable.setRealTablename(objectInfo.getTableName());

            //源应用系统名称二级
            oneObjectPojoTable.setCodeTextTd(String.valueOf(objectInfo.getDataSource()));
            //根据二级去码表回填一级
            if (objectInfo.getDataSource() == null){
                logger.info("源应用系统名称（DATA_SOUCE）为空");
            }else {
                oneObjectPojoTable.setParentCodeTextId(fieldCodeVal.getCodeId());
            }

            oneObjectPojoTable.setTableId(objectInfo.getTableId());
            // 存储表状态
            if (objectInfo.getObjectState() != null){
                oneObjectPojoTable.setStorageTableStatus(ObjectStateType.getObjectStateType(objectInfo.getObjectState()));
            }
            // 存储方式
            if (objectInfo.getStoreType() != null){
                oneObjectPojoTable.setStorageDataMode(StoreType.getStoreType(objectInfo.getStoreType()));
            }
            //更新表类型 20200507 majia添加
            if (objectInfo.getIsActiveTable() != null){
                oneObjectPojoTable.setIsActiveTable(objectInfo.getIsActiveTable());
            }
            // 厂商 存储方式 存储的数据源
            List<String> outOobjSourceCodeList = new ArrayList<>();
            // 源表ID  sourceId 的值 20191118号新增需求
            oneObjectPojoTable.setSourceId(objectInfo.getSourceId());
            // 注释的字段信息
            oneObjectPojoTable.setObjectMemo(objectInfo.getObjectMemo());
            //数据分级
            if (!StringUtils.isEmpty(objectInfo.getDataLevel()) && objectInfo.getDataLevel() != null) {
                if (objectInfo.getDataLevel().length()==1){
                    oneObjectPojoTable.setDataLevel("0" + objectInfo.getDataLevel());
                }else {
                    oneObjectPojoTable.setDataLevel(objectInfo.getDataLevel());
                }
            }
            if (!StringUtils.isEmpty(objectInfo.getDataLevelVo()) && objectInfo.getDataLevelVo() != null) {
                oneObjectPojoTable.setDataLevelCh(objectInfo.getDataLevelVo());
            }
            if(objectInfo.getVersion() != null){
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
            if(StringUtils.isNotBlank(objectInfo.getSjzybq6())){
                oneObjectPojoTable.setSjzybq6(objectInfo.getSjzybq6());
            }


            List<String> outOobjSourceList = new ArrayList<>();
            for (InputObjectCreate inputObjectCreate : allInpuObjectList) {
                Integer sysId = inputObjectCreate.getOutSysId();
                Integer outOobjSource = inputObjectCreate.getOutOobjSource();
                outOobjSourceList.add(ManufacturerName.getNameByIndex(outOobjSource));
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
            //  根据 codeTextTd的值获取对应的中文翻译
            if (StringUtils.isEmpty(oneObjectPojoTable.getCodeTextTd())) {
                oneObjectPojoTable.setCodeTextCh("");
            } else {
                if (StringUtils.isEmpty(fieldCodeVal.getValText())) {
                    oneObjectPojoTable.setCodeTextCh("错误协议代码");
                } else {
                    oneObjectPojoTable.setCodeTextCh(fieldCodeVal.getValText());
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
                oneObjectPojoTable.setOrganizationClassify("中间表/中间表");
                oneObjectPojoTable.setSourceClassify("中间表/中间表");
                oneObjectPojoTable.setClassIds("");
                oneObjectPojoTable.setSourceClassIds("");
            }
            oneObjectPojoTable.setCreateTime(objectInfo.getCreateTime());
            oneObjectPojoTable.setUpdateTime(objectInfo.getUpdateTimeStr());
            oneObjectPojoTable.setCreator(objectInfo.getCreator());
            oneObjectPojoTable.setUpdater(objectInfo.getUpdater());
        } catch (Exception e) {
            logger.error("根据tableId获取对象详细信息报错" + ExceptionUtil.getExceptionTrace(e));
        }
        return oneObjectPojoTable;
    }

    public List<ObjectField> selectObjectFieldByObjectIdNew(ObjectPojo objectFieldDemo,
                                                            List<ObjectField> objectFieldList,
                                                            Map<String, List<PageSelectOneValue>> synlteFieldInfoMap,
                                                            List<SynlteElementVO> elementVOList,
                                                            List<Synltefield> list) {
        // 先根据 tableId获取对应的objectId
        try {
            if (objectFieldDemo == null || objectFieldList == null || objectFieldList.size() == 0) {
                return new ArrayList<>();
            }
            // 获取代码中文名
            for (ObjectField objectField : objectFieldList) {
                String fieldId = objectField.getFieldId();
                if(objectField.getFieldId().indexOf("_") != -1){
                    fieldId = objectField.getFieldId().split("_")[1];
                }
                List<PageSelectOneValue> synlteFieldInfo = new ArrayList<>();
                for (String fieldId1 : synlteFieldInfoMap.keySet()){
                    if (fieldId1.equalsIgnoreCase(fieldId)){
                        synlteFieldInfo = synlteFieldInfoMap.get(fieldId);
                    }
                }
                if (synlteFieldInfo.size() > 0){
                    PageSelectOneValue value = synlteFieldInfo.get(0);
                    objectField.setSynlteFieldMemo(value.getMemo());
                    objectField.setLabel(value.getLabel());
                }

                if (StringUtils.isEmpty(objectField.getSecurityLevel())) {
                    objectField.setSecurityLevel("");
                    objectField.setSecurityLevelCh("");
                } else {
                    objectField.setSecurityLevelCh(ObjectSecurityLevelType.getValueById("2_" + objectField.getSecurityLevel()));
                }
                // 判断
                if (StringUtils.isEmpty(objectField.getFieldId()) || objectField.getFieldId().contains("unknown_")) {
                    objectField.setFieldId("");
                }
                if(objectField.getMd5Index() != null && objectField.getMd5Index() != 0){
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
                    SynlteElementVO elementVO = elementVOList.stream().filter(d -> d.getElementCode().equalsIgnoreCase(objectField.getFieldId())).findFirst().orElse(new SynlteElementVO());
                    objectField.setElementName(elementVO.getElementChname() != null ? elementVO.getElementChname() : "");
                    //  获取 表字段信息的 字段分类信息  synltefield. FIELD_CLASS 这个字段里面
                    if (list.size() > 0) {
                        Synltefield synltefield = list.stream().filter(d -> d.getFieldid().equalsIgnoreCase(objectField.getFieldId())).findFirst().orElse(new Synltefield());
                        objectField.setCodeText(synltefield.getCodeText() != null ? synltefield.getCodeText() : "");
                        objectField.setCodeid(synltefield.getCodeid() != null ? synltefield.getCodeid() : "");
                        objectField.setFieldClassId(synltefield.getFieldClass() != null ? synltefield.getFieldClass() : "");
                        objectField.setFieldClassCh(synltefield.getFieldClassCh() != null ? synltefield.getFieldClassCh() : "");
                        objectField.setSameWordType(synltefield.getWordName() != null ? synltefield.getWordName() : "");
                    }
                }
                if (StringUtils.isNotBlank(objectField.getFieldClassId())){
                    objectField.setFieldClassCh(SynlteFieldClassEnum.getValueById(objectField.getFieldClassId()));
                }
                if (objectField.getPkRecno() != null && objectField.getPkRecno() != 0) {
                    objectField.setPkRecnoStatus(true);
                }
            }
            return objectFieldList;
        } catch (Exception e) {
            logger.error("根据tableId获取字段定义信息报错" + ExceptionUtil.getExceptionTrace(e));
        }
        return null;
    }

    @Override
    public void downloadObjectInfoSql(HttpServletResponse response,String name, List<String> tableIdList) {
        BufferedOutputStream buff = null;
        ServletOutputStream outStr = null;
        //list>1导出压缩文件，等于1条导出sql文件
        if(tableIdList.size() > 1){
            try{
                response.setHeader("Content-Disposition","attachment;fileName=" + URLEncoder.encode(name+".zip", "UTF-8"));
                response.setContentType("application/octet-stream");
                response.setCharacterEncoding("utf-8");
                ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());

                // object
                List<ObjectPojo> objectPojos = resourceManageDao.selectObjectPojoByTableIds(tableIdList);
                // objectfield
                List<ObjectField> objectFieldLists = resourceManageDao.selectObjectFieldByObjectIds();
                Map<Long, List<ObjectField>> objectFieldMap = objectFieldLists.stream().collect(Collectors.groupingBy(ObjectField::getObjectId));
                List<PageSelectOneValue> synlteFieldInfos = synlteFieldDao.getGadsjFieldByTexts();
                Map<String, List<PageSelectOneValue>> synlteFieldInfoMap = synlteFieldInfos.stream().collect(Collectors.groupingBy(PageSelectOneValue::getValue));
                List<SynlteElementVO> elementNames = elementDao.searchElementNameByIds();
                //  获取 表字段信息的 字段分类信息  synltefield. FIELD_CLASS 这个字段里面
                List<Synltefield> list = resourceManageDao.getCodeTextAndCodeidByObjectFields();

                //大于1条，导出zip文件
                for(String tableId:tableIdList){
                    ObjectPojo objectInfo = objectPojos.stream().filter(d -> d.getTableId().equalsIgnoreCase(tableId)).findFirst().orElse(new ObjectPojo());
                    List<ObjectField> objectFieldList1 = new ArrayList<>();
                    for (Long objectId : objectFieldMap.keySet()){
                        if (objectInfo.getObjectId() != null && objectInfo.getObjectId() == objectId){
                            objectFieldList1 = objectFieldMap.get(objectId);
                        }
                    }
                    List<ObjectField> objectFieldList = selectObjectFieldByObjectIdNew(objectInfo, objectFieldList1, synlteFieldInfoMap, elementNames, list);
                    String tableNameCh = objectInfo.getObjectName().replace("/", "、");
                    zipOutputStream.putNextEntry(new ZipEntry(String.format("%s@%s.sql", objectInfo.getTableName(), tableNameCh)));
                    //生成object的sql
                    StringBuffer sql = jointSql(objectInfo);
                    //生成objectField的sql
                    for(ObjectField objectField:objectFieldList){
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
            }catch (Exception e){
                throw SystemException.asSystemException(ErrorCode.DOWNLOAD_ERROR, "下载文件失败");
            }
        }else if(tableIdList.size() == 1){
            //设置响应头
            setServletResponseText(response);
            String tableId = tableIdList.get(0);
            //通过tableId获取标准信息
            ObjectPojo objectInfo = resourceManageDao.selectObjectPojoByTableId(tableId);
            StringBuffer sql = jointSql(objectInfo);
            //通过tableId获取字段信息
            List<ObjectField> objectFieldList = resourceManageService.selectObjectFieldByObjectId(tableId);
            for(ObjectField data : objectFieldList){
                sql.append(jointFieldSql(data));
            }
            try{
                outStr = response.getOutputStream();
                buff = new BufferedOutputStream(outStr);
                buff.write(sql.toString().getBytes("UTF-8"));
                buff.flush();
            }catch (Exception e){
                throw SystemException.asSystemException(ErrorCode.DOWNLOAD_ERROR, "下载文件失败");
            }finally {
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String importObjectInfoExcel(MultipartFile[] file,Integer objectSheetPage,Integer objectFieldSheetPage) {
        //先过滤文件中不是excel文件
        List<MultipartFile> excelFile = new ArrayList<>();
        for(MultipartFile data : file){
            String fileName = data.getOriginalFilename();
            String excel = fileName.substring(fileName.indexOf(".") + 1);
            if("xls".equalsIgnoreCase(excel) || "xlsx".equalsIgnoreCase(excel)){
                excelFile.add(data);
            }
        }
        logger.info("======开始导入标准信息======");
        ExcelListener<ObjectTableSheet> listener = new ExcelListener<>();
        ExcelListener<TableColumnSheet> fieldListener = new ExcelListener<>();
        List<ObjectTableSheet>  list = new ArrayList<>();
        List<TableColumnSheet> columnList = new ArrayList<>();
        Long objectId = null;
        lock.lock();
        try{
            for(MultipartFile excelData:excelFile){
                list = EasyExcelUtil.readObjectExcelSheetUtil(excelData, new ObjectTableSheet(), listener, objectSheetPage);
                Iterator iterator = list.iterator();
                while (iterator.hasNext()) {
                    ObjectTableSheet objectTableSheet = (ObjectTableSheet) iterator.next();
                    objectId = objectTableSheet.getObjectId();
                    Boolean flag = checkObjectInfo(objectTableSheet);
                    if (flag) {
                        //查询此标准信息在库中是否存在
                        logger.info("开始查询此标准在object表里是否存在，objectId：" + objectTableSheet.getObjectId());
                        int objectCount = standardResourceManageDao.getObjectCountByObjectId(objectTableSheet.getObjectId());
                        if (objectCount == 0) {
                            //数据集标准不存在直接插入
                            ObjectPojoTable addObjectInfo = objectPojoDataBackFill(objectTableSheet);
                            int addObjectCount = standardResourceManageDao.addObjectMessageDao(addObjectInfo);
                            if (addObjectCount != 1) {
                                throw SystemException.asSystemException(ErrorCode.INSERT_ERROR, "数据集信息[" + addObjectInfo.getDataSourceName() + "]插入失败");
                            }
                            // 导入数据成功后，将信息插入或更新到用户权限表 USER_AUTHORITY 中
                            StandardObjectManage standardObjectManage = new StandardObjectManage();
                            standardObjectManage.setObjectPojoTable(addObjectInfo);
                            standardObjectManage.setObjectId(addObjectInfo.getObjectId());
                            standardObjectManage.setTableId(addObjectInfo.getTableId());
                            resourceManageAddServiceImpl.addUserAuthorityData(standardObjectManage);
                            iterator.remove();
                        } else {
                            //标准已经存在，比较两者的小版本日期，导入的标准较新则将旧的导入到版本库，存储新的
                            ObjectPojoTable oldObjectInfo = standardResourceManageDao.searchOneData(String.valueOf(objectTableSheet.getObjectId()));
                            // 2022年11月29日15:19:12 导入的时候去掉version字段
//                            if (oldObjectInfo.getVersion() != null && oldObjectInfo.getVersion() >= objectTableSheet.getVersion()) {
                            if ( false ) {
                                logger.info("标准版本没有高于数据库中的版本，不予保存");
                                iterator.remove();
                            } else {
                                ObjectPojoTable objectInfo = objectPojoDataBackFill(objectTableSheet);
                                int updateObjectCount = standardResourceManageDao.updateObjectMessageDao(objectInfo);
                                if (updateObjectCount != 1) {
                                    throw SystemException.asSystemException(ErrorCode.INSERT_ERROR, "数据集信息[" + objectInfo.getDataSourceName() + "]更新失败");
                                }
                                //将旧的标准信息存储到历史表
                                ObjectVersion objectVersion = createObjectVersion(oldObjectInfo);
                                int addVersionNum = standardResourceManageDao.saveOneDataVersion(objectVersion);
                                if (addVersionNum != 1) {
                                    throw SystemException.asSystemException(ErrorCode.INSERT_ERROR, "标准版本信息[" + objectVersion.getTableName() + "]插入失败");
                                }

                                oldObjectInfo.setObjectVersion(objectVersion.getObjectVersion());
                                String todayStr = DateUtil.formatDateTime(new Date(), DateUtil.DEFAULT_PATTERN_DATE_SIMPLE);
                                oldObjectInfo.setVersion(Integer.valueOf(todayStr));
                                //standardType默认为0
                                oldObjectInfo.setStandardType(0);
                                oldObjectInfo.setStorageTableStatus(String.valueOf(ObjectStateType.getObjectStateStatus("停用")));
                                oldObjectInfo.setVersions(StringUtils.isBlank(oldObjectInfo.getVersions())?"1.0":oldObjectInfo.getVersions());
                                int addObjectHistoryCount = standardResourceManageDao.saveOldData(oldObjectInfo);
                                if (addObjectHistoryCount != 1) {
                                    throw SystemException.asSystemException(ErrorCode.INSERT_ERROR, "标准历史库[" + oldObjectInfo.getRealTablename() + "]插入失败");
                                }
                                iterator.remove();
                            }
                        }
                    }
                    //标准导入成功后导入字段信息
                    columnList = EasyExcelUtil.readExcelSheetUtil(excelData, new TableColumnSheet(), fieldListener, objectFieldSheetPage);
                    Iterator fieldIterator = columnList.iterator();
                    while (fieldIterator.hasNext()) {
                        TableColumnSheet tableColumnSheet = (TableColumnSheet) fieldIterator.next();
                        tableColumnSheet.setObjectId(objectId);
                        ObjectFieldStandard objectFieldStandard = objectFieldDataBackFill(tableColumnSheet, objectId);
                        logger.info(String.format("开始查询此标准在objectfield表里是否存在，objectId：%s,columnName：%s", objectTableSheet.getObjectId(),objectFieldStandard.getColumnName()));
                        int fieldExistFlag = resourceManageAddColumnDao.getcolumnCountByFieldId(objectFieldStandard.getObjectId(), objectFieldStandard.getColumnName());
                        if (fieldExistFlag == 1) {
                            //更新
                            int updateFieldCount = resourceManageAddColumnDao.updateObjectField(objectFieldStandard);
                            if (updateFieldCount == 1) {
                                logger.info(objectFieldStandard.getColumnName() + "字段更新成功");
                            } else {
                                logger.info(objectFieldStandard.getColumnName() + "字段更新失败");
                            }
                        } else {
                            int addFieldCount = resourceManageAddColumnDao.addObjectField(objectFieldStandard);
                            if (addFieldCount == 1) {
                                logger.info(objectFieldStandard.getColumnName() + "字段插入成功");
                            } else {
                                logger.info(objectFieldStandard.getColumnName() + "字段更新失败");
                            }
                        }
                        fieldIterator.remove();
                    }
                }
            }

        }catch (Exception e){
            throw new NullPointerException("导入文件报错:\n" + ExceptionUtil.getExceptionTrace(e));
        }finally {
            lock.unlock();
        }
        return "导入成功";
    }

    /**
     * 获取导出的标准对象
     * @param objectPojoTable 数据库中的标准信息
     * @param objectFieldList 数据库中的标准字段信息
     * @return
     */
    private ExportObjectInfo getExportObjectInfo(ObjectPojoTable objectPojoTable,List<ObjectField> objectFieldList){
        ArrayList<String> md5IndexList = new ArrayList<>();
        ArrayList<String> pkRecnoList = new ArrayList<>();
        ArrayList<String> partitionRecnoList = new ArrayList<>();
        ArrayList<String> secondPartitionRecno = new ArrayList<>();
        ArrayList<String> clustRecnoList = new ArrayList<>();
        ExportObjectInfo exportObjectInfo = new ExportObjectInfo();
        exportObjectInfo.setObjectId(objectPojoTable.getObjectId());
        exportObjectInfo.setObjectName(objectPojoTable.getDataSourceName());
        exportObjectInfo.setTableName(objectPojoTable.getRealTablename());
        exportObjectInfo.setTableId(objectPojoTable.getTableId());
        exportObjectInfo.setSourceId(objectPojoTable.getSourceId());
        exportObjectInfo.setObjectMemo(objectPojoTable.getObjectMemo());
        if (objectFieldList != null && objectFieldList.size() > 0){
            objectFieldList.stream().forEach(d -> {
                if(d.getMd5Index() != null && d.getMd5Index() != 0){
                    md5IndexList.add(d.getFieldName());
                }
                if(d.getPkRecno() != null && d.getPkRecno() != 0){
                    pkRecnoList.add(d.getFieldName());
                }
                if(d.getPartitionRecno() != null && d.getPartitionRecno() ==1 ){
                    partitionRecnoList.add(d.getFieldName());
                }
                if(d.getPartitionRecno() != null && d.getPartitionRecno() == 2){
                    secondPartitionRecno.add(d.getFieldName());
                }
                if(d.getClustRecno() != null && d.getClustRecno() != 0 ){
                    clustRecnoList.add(d.getFieldName());
                }
                if (d.getNeedValue() == 0){
                    d.setNeedValueStr("否");
                }else if (d.getNeedValue() == 1){
                    d.setNeedValueStr("是");
                }
                if (d.getIsIndex() == 0){
                    d.setIsIndexStr("否");
                }else if (d.getIsIndex() == 1){
                    d.setIsIndexStr("是");
                }
                if (d.getIsContorl() == 0){
                    d.setIsContorlStr("否");
                }else if (d.getIsContorl() == 1){
                    d.setIsContorlStr("是");
                }
                if (d.getIsPrivate().equalsIgnoreCase("0")){
                    d.setIsPrivate("否");
                }else if (d.getIsPrivate().equalsIgnoreCase("1")){
                    d.setIsPrivate("是");
                }
                if (d.getOraShow() == 0){
                    d.setOraShowStr("否");
                }else if (d.getOraShow() == 1){
                    d.setOraShowStr("是");
                }
            });
        }
        exportObjectInfo.setMd5Index(StringUtils.join(md5IndexList,","));
        exportObjectInfo.setPkRecno(StringUtils.join(pkRecnoList,","));
        exportObjectInfo.setPartitionRecno(StringUtils.join(partitionRecnoList,","));
        exportObjectInfo.setSecondPartitionRecno(StringUtils.join(secondPartitionRecno,","));
        exportObjectInfo.setClustRecno(StringUtils.join(clustRecnoList,","));
        if (objectPojoTable.getOrganizationClassify() != null){
            String firstOrganization = objectPojoTable.getOrganizationClassify().split("/")[0];
            String secondOrganization = objectPojoTable.getOrganizationClassify().split("/")[1];
            exportObjectInfo.setParOrganizationClassify(firstOrganization);
            exportObjectInfo.setSecondOrganizationClassify(secondOrganization);
        }
        exportObjectInfo.setObjectFieldInfo(objectFieldList);
        return exportObjectInfo;
    }

    /**
     * 检查标准信息是否合规
     * @param objectTableSheet 导入的标准信息
     * @return
     */
    private Boolean checkObjectInfo(ObjectTableSheet objectTableSheet) {
        if(objectTableSheet.getObjectId() == null && StringUtils.isBlank(objectTableSheet.getTableId())){
            return false;
        }
        return true;
    }

    /**
     * 标准信息回填
     * @param objectTableSheet 导入的标准信息
     * @return
     */
    private ObjectPojoTable objectPojoDataBackFill(ObjectTableSheet objectTableSheet) throws ParseException {
        ObjectPojoTable addObjectInfo = new ObjectPojoTable();
        addObjectInfo.setObjectId(String.valueOf(objectTableSheet.getObjectId()));
        addObjectInfo.setDataSourceName(objectTableSheet.getObjectName());
        addObjectInfo.setStorageTableStatus(String.valueOf(objectTableSheet.getObjectState()));
        addObjectInfo.setTableId(objectTableSheet.getTableId());
        addObjectInfo.setRealTablename(objectTableSheet.getTableName());
        addObjectInfo.setCodeTextTd(objectTableSheet.getDataSource());
        addObjectInfo.setSourceId(objectTableSheet.getSourceId());
        addObjectInfo.setObjectMemo(objectTableSheet.getObjectMemo());
        addObjectInfo.setIsActiveTable(objectTableSheet.getIsActiveTable() !=null?objectTableSheet.getIsActiveTable():0);
        //dataType给默认值
        addObjectInfo.setDataType(0);
        //storeType给默认值
        addObjectInfo.setStorageDataMode("0");
        // 2022年11月29日15:39:45 导入时version换成当前日期
//        addObjectInfo.setVersion(objectTableSheet.getVersion());
        String todayStr = DateUtil.formatDateTime(new Date(), DateUtil.DEFAULT_PATTERN_DATE_SIMPLE);
        addObjectInfo.setVersion(Integer.valueOf(todayStr));
        //查询大版本和设置小版本
        String searchVersion = fieldCodeValDao.searchVersion();
        JSONObject parse = (JSONObject) JSON.parse(searchVersion);
        String versions = parse.getString("objectVersions");
        addObjectInfo.setVersions(versions);

        addObjectInfo.setSjzybq1(objectTableSheet.getSjzybq1());
        addObjectInfo.setSjzybq2(objectTableSheet.getSjzybq2());
        addObjectInfo.setSjzybq3(objectTableSheet.getSjzybq3());
        addObjectInfo.setSjzybq4(objectTableSheet.getSjzybq4());
        addObjectInfo.setSJZZYJFLVALLUE(objectTableSheet.getSjzzyjflValue());
        addObjectInfo.setSJZZEJFLVALUE(objectTableSheet.getSjzzejflValue());
        //来源分类存2级
        addObjectInfo.setSJZYLYLXVALUE(objectTableSheet.getSjzylylxValue());
        addObjectInfo.setStandardType(objectTableSheet.getStandardType());
        // 创建时间、更新时间
        Date createTime = new Date();
        addObjectInfo.setUpdateTimeRel(createTime);
        if (StringUtils.isNotBlank(objectTableSheet.getCreateTime())){
            createTime = DateUtil.parseDateTime(objectTableSheet.getCreateTime());
        }
        addObjectInfo.setCreateTimeRel(createTime);
        // 数据分级
        if (StringUtils.isNotBlank(objectTableSheet.getSecretLevel())){
            addObjectInfo.setDataLevel(objectTableSheet.getSecretLevel());
        }
        addObjectInfo.setCreator(objectTableSheet.getCreator());
        addObjectInfo.setUpdater(objectTableSheet.getUpdater());
        return addObjectInfo;
    }

    /**
     * 标准字段信息回填
     * @param tableColumnSheet 导入的字段信息
     * @param objectId 标准id
     * @return
     */
    private ObjectFieldStandard objectFieldDataBackFill(TableColumnSheet tableColumnSheet,Long objectId){

        ObjectFieldStandard objectField = new ObjectFieldStandard();
        objectField.setObjectId(objectId);
        objectField.setRecno(tableColumnSheet.getRecno());
        objectField.setFieldId(tableColumnSheet.getFieldId());
        objectField.setFieldName(tableColumnSheet.getFieldName());
        objectField.setFieldChineseName(tableColumnSheet.getFieldChineseName());
        objectField.setFieldClassId(tableColumnSheet.getFieldClassification());
        objectField.setSecurityLevel(tableColumnSheet.getSecurityLevel());
        objectField.setFieldDescribe(tableColumnSheet.getMemo());
        //字段类型
        String fieldType = tableColumnSheet.getFieldType().substring(0, tableColumnSheet.getFieldType().indexOf("("));
        String fieldLength = tableColumnSheet.getFieldType().substring(tableColumnSheet.getFieldType().indexOf("(")+1,
                tableColumnSheet.getFieldType().indexOf(")"));
        objectField.setFieldType(FieldType.getFieldTypeNum(fieldType));
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
        objectField.setMd5Index(tableColumnSheet.getMd5Index() != null?tableColumnSheet.getMd5Index():0);
        String todayStr = DateUtil.formatDateTime(new Date(), DateUtil.DEFAULT_PATTERN_DATE_SIMPLE);
        objectField.setVersion(tableColumnSheet.getVersion() != null ? tableColumnSheet.getVersion(): Integer.parseInt(todayStr));
        //查询赋值大版本
        String searchVersion = fieldCodeValDao.searchVersion();
        JSONObject parse = (JSONObject) JSON.parse(searchVersion);
        String versions = parse.getString("objectVersions");
        objectField.setVersion0(versions);
        objectField.setIsPrivate("是".equalsIgnoreCase(tableColumnSheet.getIsPrivateCh()) ? 1 : 0);
        objectField.setStandardRecno(tableColumnSheet.getStandardRecno() != null? tableColumnSheet.getStandardRecno():0);
        objectField.setPkRecno(tableColumnSheet.getPkRecno() != null?tableColumnSheet.getPkRecno():0);
        objectField.setPartitionRecno(tableColumnSheet.getPartitionRecno() != null?tableColumnSheet.getPartitionRecno():0);
        objectField.setClustRecno(tableColumnSheet.getClustRecno() != null? tableColumnSheet.getClustRecno():0);
        objectField.setOraShow("是".equalsIgnoreCase(tableColumnSheet.getOraShowCh()) ? 1 : 0);
        objectField.setDeterminerId(tableColumnSheet.getDeterminerId());
        objectField.setGadsjFieldId(tableColumnSheet.getGadsjFieldId());

        return objectField;
    }

    /**
     * 生成标准版本信息
     * @param objectPojoTable 标准信息
     * @return
     */
    private ObjectVersion createObjectVersion(ObjectPojoTable objectPojoTable){
        ObjectVersion objectVersion = new ObjectVersion();
        String uuid = UUIDUtil.getUUID();
        objectVersion.setObjectVersion(uuid);
        objectVersion.setObjectId(objectPojoTable.getObjectId());
        objectVersion.setTableName(objectPojoTable.getRealTablename());
        if (StringUtils.isBlank(objectPojoTable.getObjectMemo())){
            objectVersion.setMemo(" ");
        }else {
            objectVersion.setMemo(objectPojoTable.getObjectMemo());
        }
        String todayStr = DateUtil.formatDateTime(new Date(), DateUtil.DEFAULT_PATTERN_DATE_SIMPLE);

        objectVersion.setVersion(Integer.valueOf(todayStr));
        objectVersion.setVersions(StringUtils.isBlank(objectPojoTable.getVersions())?"1.0":objectPojoTable.getVersions());
        objectVersion.setAuthor(StringUtils.isBlank(objectPojoTable.getCreator())?"1.0":objectPojoTable.getCreator());
        return objectVersion;
    }


    /**
     * 拼接标准插入sql语句
     * @param data 标准信息对象
     * @return
     */
    private StringBuffer jointSql(ObjectPojo data) {
        String databaseType = env.getProperty("database.type");
        StringBuffer baseSql = new StringBuffer();
        if(Common.DAMENG.equalsIgnoreCase(databaseType)){
            baseSql = new StringBuffer("insert into synlte.\"OBJECT\"(OBJECTID,OBJECTNAME,OBJECTMEMO,OBJECTSTATE,TABLEID,TABLENAME,DATATYPE," +
                    "STORETYPE,DATA_SOURCE,SECRETLEVEL,RELATE_TABLENAME,SOURCEID,ISACTIVETABLE,DELETED,SJZYBQ1,SJZYBQ2,SJZYBQ3," +
                    "SJZYBQ4,SJZYBQ5,SJZYLYLXVALUE,SJZZYJFLVALLUE,SJZZEJFLVALUE,CREATETIME,UPDATETIME,CREATOR,UPDATER," +
                    "VERSION,\"VERSIONS\",STANDARD_TYPE)values(");
        }else {
            baseSql = new StringBuffer("insert into synlte.object(OBJECTID,OBJECTNAME,OBJECTMEMO,OBJECTSTATE,TABLEID,TABLENAME,DATATYPE," +
                    "STORETYPE,DATA_SOURCE,SECRETLEVEL,RELATE_TABLENAME,SOURCEID,ISACTIVETABLE,DELETED,SJZYBQ1,SJZYBQ2,SJZYBQ3," +
                    "SJZYBQ4,SJZYBQ5,SJZYLYLXVALUE,SJZZYJFLVALLUE,SJZZEJFLVALUE,CREATETIME,UPDATETIME,CREATOR,UPDATER," +
                    "VERSION,VERSIONS,STANDARD_TYPE)values(");
        }
//        StringBuffer baseSql = new StringBuffer("insert into synlte.object(OBJECTID,OBJECTNAME,OBJECTMEMO,OBJECTSTATE,TABLEID,TABLENAME,DATATYPE," +
//                "STORETYPE,DATA_SOURCE,SECRETLEVEL,RELATE_TABLENAME,SOURCEID,ISACTIVETABLE,DELETED,SJZYBQ1,SJZYBQ2,SJZYBQ3," +
//                "SJZYBQ4,SJZYBQ5,SJZYLYLXVALUE,SJZZYJFLVALLUE,SJZZEJFLVALUE,CREATETIME,UPDATETIME,CREATOR,UPDATER," +
//                "VERSION,VERSIONS,STANDARD_TYPE)values(");
        baseSql.append("'"+data.getObjectId()+"',"+"'"+data.getObjectName()+"',");
        baseSql.append(StringUtils.isNotBlank(data.getObjectMemo())?"'"+data.getObjectMemo()+"',":"'',");
        baseSql.append("'"+data.getObjectState()+"',");
        baseSql.append(StringUtils.isNotBlank(data.getTableId())?"'"+data.getTableId()+"',":"'',");
        baseSql.append(StringUtils.isNotBlank(data.getTableName())?"'"+data.getTableName()+"',":"'',");
        baseSql.append(data.getDataType() != null?"'"+data.getDataType()+"',":"null,");
        baseSql.append(data.getStoreType() != null? "'"+data.getStoreType()+"',":"null,");
        baseSql.append(StringUtils.isNotBlank(data.getDataSource())?"'"+data.getDataSource()+"',":"'',");
        baseSql.append(StringUtils.isNotBlank(data.getDataLevel())?"'"+data.getDataLevel()+"',":"'',");
        baseSql.append("'OBJECTFIELD',");
        baseSql.append(StringUtils.isNotBlank(data.getSourceId())?"'"+data.getSourceId()+"',":"'',");
        baseSql.append(data.getIsActiveTable() != null?"'"+data.getIsActiveTable()+"',":"null,");
        baseSql.append("'0',");
        baseSql.append(StringUtils.isNotBlank(data.getSjzybq1())?"'"+data.getSjzybq1()+"',":"'',");
        baseSql.append(StringUtils.isNotBlank(data.getSjzybq2())?"'"+data.getSjzybq2()+"',":"'',");
        baseSql.append(StringUtils.isNotBlank(data.getSjzybq3())?"'"+data.getSjzybq3()+"',":"'',");
        baseSql.append(StringUtils.isNotBlank(data.getSjzybq4())?"'"+data.getSjzybq4()+"',":"'',");
        baseSql.append(StringUtils.isNotBlank(data.getSjzybq5())?"'"+data.getSjzybq5()+"',":"'',");
        baseSql.append(StringUtils.isNotBlank(data.getSJZYLYLXVALUE())?"'"+data.getSJZYLYLXVALUE()+"',":"'',");
        baseSql.append(StringUtils.isNotBlank(data.getSJZZYJFLVALLUE())?"'"+data.getSJZZYJFLVALLUE()+"',":"'',");
        baseSql.append(StringUtils.isNotBlank(data.getSJZZEJFLVALUE())?"'"+data.getSJZZEJFLVALUE()+"',":"'',");
        baseSql.append("sysdate,"+"sysdate,");
        baseSql.append(StringUtils.isNotBlank(data.getCreator())?"'"+data.getCreator()+"',":"'',");
        baseSql.append(StringUtils.isNotBlank(data.getUpdater())?"'"+data.getCreator()+"',":"'',");
        baseSql.append(data.getVersion()!=null?"'"+data.getVersion()+"',":"null,");
        baseSql.append(StringUtils.isNotBlank(data.getVersions())?"'"+data.getVersions()+"',":"'',");
        baseSql.append(data.getStandardType()!=null?"'"+data.getStandardType()+"');":"null);");
        baseSql.append("\n"+"commit;"+"\n");

        return baseSql;
    }

    /**
     * 拼接字段（objectField）sql
     * @param data 字段信息
     * @return
     */
    private StringBuffer jointFieldSql(ObjectField data){
        String databaseType = env.getProperty("database.type");
        StringBuffer baseSql = new StringBuffer();
        if(Common.DAMENG.equalsIgnoreCase(databaseType)){
            baseSql = new StringBuffer("insert into synlte.objectField(OBJECTID,RECNO,FIELDID,FIELDNAME," +
                    "FIELDCHINEENAME,FIELDDESCRIBE,FIELDTYPE,FIELDLEN,DEFAULTVALUE,INDEXTYPE,ISINDEX,NEEDVALUE,ISCONTORL," +
                    "DELETED,COLUMNNAME,MEMO,TABLEINDEX,ISQUERY,COLUMNNAME_STATE,MD5_INDEX,\n" +
                    "\"VERSIONS\",ISPRIVATE,STANDARD_RECNO,PK_RECNO,PARTITION_RECNO,CLUST_RECNO,ORA_SHOW,\n" +
                    "ODPS_PATTITION,PRO_TYPE,FIELDSOURCETYPE,DETERMINERID,GADSJ_FIELDID)values(");
        }else {
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
        baseSql.append("'"+data.getObjectId()+"',"+"'"+data.getRecno()+"',"+"'"+data.getFieldId()+"',"+"'"+data.getFieldName()+"',");
        baseSql.append(StringUtils.isNotBlank(data.getFieldChineseName())?"'"+data.getFieldChineseName()+"',":"'',");
        baseSql.append(StringUtils.isNotBlank(data.getFieldDescribe())?"'"+data.getFieldDescribe()+"',":"'',");
        baseSql.append(StringUtils.isNotBlank(data.getFieldType())?"'"+data.getFieldType()+"',":"'',");
        baseSql.append(data.getFieldLen()!=null?"'"+data.getFieldLen()+"',":"null,");
        baseSql.append(StringUtils.isNotBlank(data.getDefaultValue())?"'"+data.getDefaultValue()+"',":"'',");
        baseSql.append(data.getIndexType()!=null?"'"+data.getIndexType()+"',":"null,");
        baseSql.append(data.getIsIndex()!=null?"'"+data.getIsIndex()+"',":"null,");
        baseSql.append(data.getNeedValue() != null?"'"+data.getNeedValue()+"',":"null,");
        baseSql.append(data.getIsContorl() != null?"'"+data.getIsContorl()+"',":"null,");
        baseSql.append("'0',");
        baseSql.append(StringUtils.isNotBlank(data.getColumnName())?"'"+data.getColumnName()+"',":"'',");
        baseSql.append(StringUtils.isNotBlank(data.getMemo())?"'"+data.getMemo()+"',":"'',");
        baseSql.append(data.getTableIndex()!=null?"'"+data.getTableIndex()+"',":"'',");
        baseSql.append(data.getIsQuery()!=null?"'"+data.getIsQuery()+"',":"null,");
        baseSql.append(data.getColumnNameState()!=null?"'"+data.getColumnNameState()+"',":"null,");
        baseSql.append(data.getMd5Index()!=null?"'"+data.getMd5Index()+"',":"null,");
        baseSql.append(data.getVersions()!=null?"'"+data.getVersions()+"',":"null,");
        baseSql.append(StringUtils.isNotBlank(data.getIsPrivate())?"'"+data.getIsPrivate()+"',":"'',");
        baseSql.append(data.getStandardRecno()!=null?"'"+data.getStandardRecno()+"',":"null,");
        baseSql.append(data.getPkRecno()!=null?"'"+data.getPkRecno()+"',":"null,");
        baseSql.append(data.getPartitionRecno()!=null?"'"+data.getPartitionRecno()+"',":"null,");
        baseSql.append(data.getClustRecno()!=null?"'"+data.getClustRecno()+"',":"null,");
        baseSql.append(data.getOraShow()!=null?"'"+data.getOraShow()+"',":"null,");
        baseSql.append(StringUtils.isNotBlank(data.getOdpsPattition())?"'"+data.getOdpsPattition()+"',":"'',");
        baseSql.append(StringUtils.isNotBlank(data.getProType())?"'"+data.getProType()+"',":"'',");
        baseSql.append(StringUtils.isNotBlank(data.getFieldDescribe())?"'"+data.getFieldDescribe()+"',":"'',");
        baseSql.append(StringUtils.isNotBlank(data.getDeterminerId())?"'"+data.getDeterminerId()+"',":"'',");
        baseSql.append(StringUtils.isNotBlank(data.getGadsjFieldId())?"'"+data.getGadsjFieldId()+"');":"'');");
        baseSql.append("\n"+"commit;"+"\n");

        return baseSql;
    }

    /**
     * 设置响应头
     * @param response 响应对象
     * @return
     */
    private HttpServletResponse setServletResponseText(HttpServletResponse response){
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition",
                "attachment;filename*=utf-8''" );
        return response;
    }

//    /**
//     * 导出模板文件
//     * @param response
//     */
//    @Override
//    public void downloadTemplateExcel(HttpServletResponse response) {
//        ExcelWriter excelWriter = null;
//        try{
//            response.setContentType("application/vnd.ms-excel");
//            response.setCharacterEncoding("utf-8");
//            String fileName = URLEncoder.encode("JZ_RESOURCE_XXXX@XXXX@JZ_SOUCE_XXXX@v"
//                    + DateUtil.formatDate(new Date(),DateUtil.DEFAULT_PATTERN_DATE_SIMPLE),"UTF-8")+".xlsx";
//            response.setHeader("Content-disposition","attachment;filename*=utf-8''"
//                    +fileName);
//            excelWriter = EasyExcel.write(response.getOutputStream()).build();
//            WriteSheet writeSheetTwo = EasyExcel.writerSheet(0,"修订说明").head(RevisionDescription.class)
//                    .build();
//            excelWriter.write(null,writeSheetTwo);
//            // 表说明的相关信息 不是规范的表格 是相关注释说明
//            WriteSheet writeSheetThree = EasyExcel.writerSheet(1,"表说明")
//                    .build();
//            excelWriter.write(getTableDescriptionList(),writeSheetThree);
//            // 表字段 的信息
//            WriteSheet writeSheetOne = EasyExcel.writerSheet(2,"表字段").head(TableColumnSheet.class)
//                    .build();
//            excelWriter.write(null,writeSheetOne);
//
//        }catch (Exception e){
//            logger.error("导出标准化的模板文件报错"+ ExceptionUtil.getExceptionTrace(e));
//        }finally {
//            if(excelWriter != null){
//                excelWriter.finish();
//            }
//        }
//    }

//    /**
//     * 获取表说明中相关的模板数据
//     * @return
//     */
//    @Deprecated
//    private List<List<Object>> getTableDescriptionList(){
//        List<List<Object>> resultList = new ArrayList<>();
//        resultList.add(Collections.singletonList("表名"));
//        resultList.add(Collections.singletonList("标准表名"));
//        resultList.add(Collections.singletonList("源数据名"));
//        resultList.add(Collections.singletonList("对应的数据项描述"));
//        resultList.add(Collections.singletonList("表描述"));
//        resultList.add(Collections.singletonList("MD_ID取值"));
//        resultList.add(Collections.singletonList("主键"));
//        resultList.add(Collections.singletonList("过滤列"));
//        resultList.add(Collections.singletonList("分区列"));
//        resultList.add(Collections.singletonList("二级分区列"));
//        resultList.add(Collections.singletonList("聚集列"));
//        resultList.add(Collections.singletonList("加载方式"));
//        resultList.add(Collections.singletonList("支持操作类型"));
//        resultList.add(Collections.singletonList("ADS(HBASE保存时间)"));
//        resultList.add(Collections.singletonList("HDFS源文件保存数"));
//        resultList.add(Collections.singletonList("预估数据量"));
//        resultList.add(Collections.singletonList("其他要求说明"));
//        return resultList;
//    }

    @Override
    public void downloadFieldTempExcel(HttpServletResponse response) {
        InputStream in = null;
        ServletOutputStream outputStream=null;
        try{
            outputStream=response.getOutputStream();
            ApplicationHome app = new ApplicationHome(getClass());
            String execlFile = app.getDir().getAbsolutePath() + "/static/tempfile/src_tab_structure_tmp.xlsx";
            logger.info("excel文件的路径是：{}",execlFile);
            File xlsFile = new File(execlFile);
            if (xlsFile.exists()){
                response.setContentType("application/vnd.ms-excel");
                response.setCharacterEncoding("utf-8");
                String fileName = URLEncoder.encode("源数据物理结构模板","UTF-8")+".xlsx";
                response.setHeader("Content-disposition",
                        "attachment;filename*=utf-8''" +fileName);
                in = new BufferedInputStream(new FileInputStream(execlFile));
                int len;
                byte[] bytes = new byte[1024];
                while ((len = in.read(bytes)) != -1){
                    outputStream.write(bytes,0,len);
                }
                outputStream.flush();
            }else {
                throw new Exception("下载模板失败：系统中无源数据物理结构模板文件");
            }
        }catch (SystemException e){
            logger.error(ExceptionUtil.getExceptionTrace(e));
            throw e;
        } catch (Exception e1){
            logger.error("导出模板失败：\n"+ ExceptionUtil.getExceptionTrace(e1));
        }finally {
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    logger.error("关闭输出流失败");
                    e.printStackTrace();
                }
            }
            if(in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error("关闭输入流失败");
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public List<ObjectFieldSheet> analysisFieldExcel(MultipartFile multipartFile, List<ObjectFieldSheet> fieldInfoList, int type){
        List<ObjectFieldSheet> fieldInfos = null;
        ExcelListener<ObjectTableSheet> listener = new ExcelListener<>();
        try {
            fieldInfos = EasyExcelUtil.readObjectFieldExcelSheetUtil(multipartFile, new ObjectFieldSheet(), listener, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i=0; i<fieldInfos.size();i++){
            // 序号
            fieldInfos.get(i).setRecno(i);
            // 是否必填
            String neddValue = fieldInfos.get(i).getNeedValueStr();
            fieldInfos.get(i).setNeedValue(StringUtils.isNotBlank(neddValue) && neddValue.toUpperCase().equalsIgnoreCase("Y") ? 1 : 0);
            // 字段类型
            String fieldType = String.valueOf(SynlteFieldType.getSynlteNumType(fieldInfos.get(i).getFieldType()));
            fieldInfos.get(i).setFieldType(fieldType);
            // 根据数据元编码获取标准信息
            ServerResponse<Synltefield> synltefieldServerResponse = resourceManageService.getAddColumnByInputService("fieldId",fieldInfos.get(i).getFieldId());
            if (synltefieldServerResponse.getData() != null){
                Synltefield synltefield = synltefieldServerResponse.getData();
                synltefield.getColumnname();
                if (StringUtils.isBlank(fieldInfos.get(i).getFieldType()) && synltefield.getFieldtype() != null){
                    fieldInfos.get(i).setFieldType(String.valueOf(synltefield.getFieldtype()));
                }
                if (fieldInfos.get(i).getFieldLen() == null && synltefield.getFieldlen() !=0){
                    fieldInfos.get(i).setFieldLen(synltefield.getFieldlen().intValue());
                }
                fieldInfos.get(i).setFieldName(synltefield.getFieldname());         // 标准列名
                fieldInfos.get(i).setMemo(synltefield.getMemo());                   // 备注
                fieldInfos.get(i).setSameWordType(synltefield.getSameid());         // 语义类型
                fieldInfos.get(i).setFieldClassCh(synltefield.getFieldClassCh());   // 字段分类

            }
        }

        return fieldInfos;
    }

}
