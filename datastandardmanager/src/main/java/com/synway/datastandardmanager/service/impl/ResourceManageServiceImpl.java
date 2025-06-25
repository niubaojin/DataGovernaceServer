package com.synway.datastandardmanager.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.synway.datastandardmanager.annotation.Resubmit;
import com.synway.datastandardmanager.config.HashLock;
import com.synway.datastandardmanager.config.TransactionUtils;
import com.synway.datastandardmanager.constant.Common;
import com.synway.datastandardmanager.dao.master.*;
import com.synway.datastandardmanager.dao.standard.ResourceManageAddColumnDao;
import com.synway.datastandardmanager.dao.standard.TableOrganizationDao;
import com.synway.datastandardmanager.exceptionhandler.ErrorCode;
import com.synway.datastandardmanager.exceptionhandler.SystemException;
import com.synway.datastandardmanager.interceptor.AuthorizedUserUtils;
import com.synway.datastandardmanager.pojo.*;
import com.synway.datastandardmanager.pojo.ExternalInterfce.GetTreeReq;
import com.synway.datastandardmanager.pojo.ExternalInterfce.TreeNodeVue;
import com.synway.datastandardmanager.pojo.approvalInfo.ApprovalInfoParams;
import com.synway.datastandardmanager.pojo.dataDefinitionManagement.ObjectFieldRelation;
import com.synway.datastandardmanager.pojo.dataDefinitionManagement.ObjectRelation;
import com.synway.datastandardmanager.pojo.dataDefinitionManagement.ObjectRelationManage;
import com.synway.datastandardmanager.pojo.enums.*;
import com.synway.datastandardmanager.pojo.standardpojo.MetaInfoDetail;
import com.synway.datastandardmanager.pojo.standardpojo.PushMetaInfo;
import com.synway.datastandardmanager.pojo.synltefield.SynlteFieldClassEnum;
import com.synway.datastandardmanager.pojo.warehouse.DetectedTable;
import com.synway.datastandardmanager.service.OtherModuleManageService;
import com.synway.datastandardmanager.service.ResourceManageAddService;
import com.synway.datastandardmanager.service.ResourceManageService;
import com.synway.common.bean.ServerResponse;
import com.synway.datastandardmanager.util.*;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class ResourceManageServiceImpl implements ResourceManageService {
    private static Logger log = LoggerFactory.getLogger(ResourceManageServiceImpl.class);
    private static HashLock<String> HASH_LOCK = new HashLock<>();

    @Autowired
    ResourceManageDao resourceManageDao;
    @Autowired
    StandardResourceManageDao standardResourceManageDao;
    @Autowired
    ResourceManageAddColumnDao resourceManageAddColumnDao;
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    RestTemplateHandle restTemplateHandle;

    @Autowired
    private ResourceManageAddDao resourceManageAddDao;

    RestTemplate restTemplate1 = new RestTemplate();
    //	@Value("${urlClassify}")
//	private String urlClassify;
    @Autowired
    private TableOrganizationDao tableOrganizationDao;

    @Autowired
    ConcurrentHashMap<String, Boolean> switchHashMap;

    @Autowired
    private OtherModuleManageService otherModuleManageServiceImpl;

    @Autowired
    ConcurrentHashMap<String, String> parameterMap;
    @Autowired
    private Environment env;
    @Autowired
    private ResourceManageAddService resourceManageAddServiceImpl;

    @Autowired
    private FieldCodeValDao fieldCodeValDao;

    @Autowired
    private SynlteElementDao elementDao;

    @Autowired
    private DataDefinitionDao dataDefinitionDao;

    @Autowired
    private SynlteFieldDao synlteFieldDao;

    @Autowired
    private TransactionUtils transactionUtils;

//    @Override
//    public List<Integer> selectDataTypeByObject() {
//        // TODO Auto-generated method stub
//        return resourceManageDao.selectDataTypeByObject();
//    }
//
//    @Override
//    public List<FieldCodeVal> selectFieldCodeValByDataType(int dataType) {
//        // TODO Auto-generated method stub
//        return resourceManageDao.selectFieldCodeValByDataType(dataType);
//    }
//
//    @Override
//    public List<ObjectPojo> selectObjectPojoByDataTypeAndCodeValId(
//            int dataType, String codeValId) {
//        // TODO Auto-generated method stub
//        return resourceManageDao.selectObjectPojoByDataTypeAndCodeValId(dataType, codeValId);
//    }

    @Override
    public List<ObjectField> selectObjectFieldByObjectId(String tableId) {
        // 先根据 tableId获取对应的objectId
        try {
            if (StringUtils.isEmpty(tableId)) {
                log.error("tableId" + tableId + "为空");
                return new ArrayList<>();
            }
            ObjectPojo objectFieldDemo = resourceManageDao.selectObjectPojoByTableId(tableId);
            if (objectFieldDemo == null) {
                return new ArrayList<>();
            }
            List<ObjectField> objectFieldList = resourceManageDao.selectObjectFieldByObjectId(objectFieldDemo.getObjectId());
            boolean isHailiang = env.getProperty("database.type").equalsIgnoreCase("hailiang");
            // 获取代码中文名
            for (ObjectField objectField : objectFieldList) {
                if(objectField.getFieldId().indexOf("_") != -1){
                    String fieldId = objectField.getFieldId().split("_")[1];
                    List<PageSelectOneValue> synlteFieldInfo = synlteFieldDao.getGadsjFieldByText(null, null, fieldId);
                    if(synlteFieldInfo.size() != 0){
                        PageSelectOneValue value = synlteFieldInfo.get(0);
                        objectField.setSynlteFieldMemo(value.getMemo());
                        objectField.setLabel(value.getLabel());
                    }
                }else {
                    List<PageSelectOneValue> synlteFieldInfo = synlteFieldDao.getGadsjFieldByText(null, null, objectField.getFieldId());
                    if(synlteFieldInfo.size() != 0){
                        PageSelectOneValue value = synlteFieldInfo.get(0);
                        objectField.setSynlteFieldMemo(value.getMemo());
                        objectField.setLabel(value.getLabel());
                    }
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
                String codeText = null;
                String codeId = null;
                String fieldClass = null;
                String fieldClassCh = null;
                String sameWordType = null;
                List<Map<String, String>> list = new ArrayList<>();
                if (StringUtils.isEmpty(objectField.getFieldId())) {
                    objectField.setCodeText("");
                    objectField.setCodeid("");
                    objectField.setFieldClassId("");
                    objectField.setFieldClassCh("");
                    objectField.setSameWordType("");
                } else {
                    //20210913 通过标准字段fieldId字段获取对应的数据要素名称
                    String elementName = elementDao.searchElementNameById(objectField.getFieldId());
                    objectField.setElementName(elementName);

                    //  获取 表字段信息的 字段分类信息  synltefield. FIELD_CLASS 这个字段里面
                    list = resourceManageDao.getCodeTextAndCodeidByObjectField(objectField.getFieldId());
                    if (!list.isEmpty()) {
                        Map<String, String> map = list.get(0);
                        if (map == null) {
                            continue;
                        }
                        codeText = isHailiang ? map.get("codetext") : map.get("codeText");
                        codeId = isHailiang ? map.get("codeid") : map.get("codeId");
                        fieldClass = isHailiang ? map.get("fieldclass") : map.get("fieldClass");
                        fieldClassCh = isHailiang ? map.get("fieldclassch") : map.get("fieldClassCh");
                        sameWordType = isHailiang ? map.get("samewordtype") : map.get("sameWordType");
                        objectField.setCodeText(StringUtils.isEmpty(codeText) ? "" : codeText);
                        objectField.setCodeid(StringUtils.isEmpty(codeId) ? "" : codeId);
                        objectField.setFieldClassId(StringUtils.isEmpty(fieldClass) ? "" : fieldClass);
                        objectField.setFieldClassCh(StringUtils.isEmpty(fieldClassCh) ? "" : fieldClassCh);
                        objectField.setSameWordType(StringUtils.isEmpty(sameWordType) ? "" : sameWordType);
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
            log.error("根据tableId获取字段定义信息报错" + ExceptionUtil.getExceptionTrace(e));
        }
        return null;
    }

    /**
     * 20191010 展示的内容发生大更改 对象详细表需要展示的内容发生变化
     * @param tableId 表id
     * @return
     */
    @Override
    public ObjectPojoTable selectObjectPojoByTableId(String tableId) {
        ObjectPojoTable oneObjectPojoTable = new ObjectPojoTable();
        try {
            ObjectPojo objectInfo = resourceManageDao.selectObjectPojoByTableId(tableId);
            if (objectInfo == null) {
                return oneObjectPojoTable;
            }
            // 获取输入和输出的对应关系
            List<InputObjectCreate> allInpuObjectList = standardResourceManageDao.getAllInputObject(tableId);

            String tableName = objectInfo.getTableName();
            objectInfo.setObjectStateVo(ObjectStateType.getObjectStateType(objectInfo.getObjectState()));
            objectInfo.setStoreTypeVo(StoreType.getStoreType(objectInfo.getStoreType()));
            objectInfo.setDataTypeVo(ObjectDataType.getDataType(objectInfo.getDataType()));
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
                log.info("源应用系统名称（DATA_SOUCE）为空");
            }else {
                FieldCodeVal fieldCodeVal = resourceManageDao.selectOneSysName(objectInfo.getDataSource());
                oneObjectPojoTable.setParentCodeTextId(fieldCodeVal.getCodeId());
            }

            oneObjectPojoTable.setTableId(objectInfo.getTableId());
            // 存储表状态
            oneObjectPojoTable.setStorageTableStatus(objectInfo.getObjectStateVo());
            // 存储方式
            oneObjectPojoTable.setStorageDataMode(objectInfo.getStoreTypeVo());
            //更新表类型 20200507 majia添加
            oneObjectPojoTable.setIsActiveTable(objectInfo.getIsActiveTable());
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
//			oneObjectPojoTable.setOwnerFactoryCode(StringUtils.join(outOobjSourceCodeList , ","));
//			oneObjectPojoTable.setOwnerFactory(StringUtils.join(outOobjSourceList , ","));
//			oneObjectPojoTable.setCodeTextTd(StringUtils.join(outSysIdList , ","));
            // TODO 存储数据源信息
            //  根据 codeTextTd的值获取对应的中文翻译
            if (StringUtils.isEmpty(oneObjectPojoTable.getCodeTextTd())) {
                oneObjectPojoTable.setCodeTextCh("");
            } else {
                String sysChi = resourceManageDao.getSysChiName(oneObjectPojoTable.getCodeTextTd());
                if (StringUtils.isEmpty(sysChi)) {
                    oneObjectPojoTable.setCodeTextCh("错误协议代码");
                } else {
                    oneObjectPojoTable.setCodeTextCh(sysChi);
                }

            }
            //  获取这个tableid在 数据组织 ， 数据来源的分级分类信息。
            ObjectPojoTable classifyOne = resourceManageDao.getClassifyByTableid(tableId);
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
            oneObjectPojoTable.setUpdateTime(objectInfo.getUpdateTimeStr());
            oneObjectPojoTable.setCreator(objectInfo.getCreator());
            oneObjectPojoTable.setUpdater(objectInfo.getUpdater());


            log.info(JSONObject.toJSONString(oneObjectPojoTable));
        } catch (Exception e) {
            log.error("根据tableId获取对象详细信息报错" + ExceptionUtil.getExceptionTrace(e));
        }
        return oneObjectPojoTable;
    }


//    public List<TreeNode> treeNode() {
//        List<Integer> dataTypeList = selectDataTypeByObject();
//        List<TreeNode> treeNodeList = new ArrayList<TreeNode>();
//        for (Integer dataType : dataTypeList) {
//            if (dataType == null) {
//                continue;
//            }
//            TreeNode treeNode1 = new TreeNode();
//            String dataTypeStr = ObjectDataType.getDataType(dataType);
//            treeNode1.setText(dataTypeStr);//获取1级数据
//            //treeNode1.setIcon("glyphicon glyphicon-folder-close");
//            treeNode1.setIcon("");
//            List<FieldCodeVal> fieldCodeValList = selectFieldCodeValByDataType(dataType);
//            List<TreeNode> treeNodeList1 = new ArrayList<TreeNode>();
//            for (FieldCodeVal fieldCodeVal : fieldCodeValList) {
//                TreeNode treeNode2 = new TreeNode();
//                String codeValId = fieldCodeVal.getCodeValId();
//                String valText = fieldCodeVal.getValText();
//                treeNode2.setText(valText);
//                //treeNode2.setIcon("glyphicon glyphicon-folder-close");
//                treeNode2.setIcon("");
//                treeNodeList1.add(treeNode2);
//                List<ObjectPojo> objectPojoList = selectObjectPojoByDataTypeAndCodeValId(dataType, codeValId);
//                List<TreeNode> treeNodeList2 = new ArrayList<TreeNode>();
//                for (ObjectPojo objectPojo : objectPojoList) {
//                    TreeNode treeNode3 = new TreeNode();
//                    List<Object> tabs = new ArrayList<Object>();
//                    String tableId = objectPojo.getTableId();
//                    Long objectId = objectPojo.getObjectId();
//                    String tableName = objectPojo.getTableName();
//                    String tableNameZh = objectPojo.getObjectName();
//                    int objectState = objectPojo.getObjectState();
////					treeNode3.setText(tableNameZh+"("+tableName+")");
//                    treeNode3.setText(tableNameZh);
//                    treeNode3.setNodeId(objectId);
//                    tabs.add(tableId);
//                    tabs.add(objectId);
//                    tabs.add(valText);
//                    tabs.add(tableName);
//                    treeNode3.setTabs(tabs);
//                    //判断表使用情况
//                    if (0 == objectState) {
//                        //treeNode3.setIcon("glyphicon glyphicon-ok-sign");
//                        treeNode3.setColor("#000000");
//                    } else if (1 == objectState) {
//                        //treeNode3.setIcon("glyphicon glyphicon-question-sign");
//                        treeNode3.setColor("F9F900");
//                    } else {
//                        //treeNode3.setIcon("glyphicon glyphicon-remove-sign");
//                        treeNode3.setColor("8E8E8E");
//                    }
//                    treeNodeList2.add(treeNode3);
//                }
//                treeNode2.setNodes(treeNodeList2);
//            }
//            treeNode1.setNodes(treeNodeList1);
//            treeNodeList.add(treeNode1);
//        }
//        return treeNodeList;
//    }

//    @Override
//    public List<TreeNode> queryTreeNode(String objectName) {
//        // TODO Auto-generated method stub
//        List<TreeNode> treeNodeList1 = new ArrayList<TreeNode>();
//        treeNodeList1 = treeNode();
//        //过滤三级
//        for (int i = 0; i < treeNodeList1.size(); i++) {
//            TreeNode t1 = treeNodeList1.get(i);
//            System.out.println("一级：" + t1.getText());
//            List<TreeNode> treeNodeList2 = new ArrayList<TreeNode>();
//            treeNodeList2 = t1.getNodes();
//            for (int j = 0; j < treeNodeList2.size(); j++) {
//                TreeNode t2 = treeNodeList2.get(j);
//                System.out.println("   二级：" + t2.getText());
//                List<TreeNode> treeNodeList3 = new ArrayList<TreeNode>();
//                List<TreeNode> treeNodeList3New = new ArrayList<TreeNode>();
//                treeNodeList3 = t2.getNodes();
//                for (int m = 0; m < treeNodeList3.size(); m++) {
//                    try {
//                        TreeNode t3 = treeNodeList3.get(m);
//                        if (String.valueOf(t3.getTabs().get(3)).toLowerCase().contains(objectName.toLowerCase()) || t3.getText().contains(objectName)) {
////					if(t3.getText().contains(objectName)){
//                            treeNodeList3New.add(t3);
//                            System.out.println("      三级：" + t3.getText());
//                        } else {
//                            if (t1.getText().contains(objectName) || t2.getText().contains(objectName)) {
//                                treeNodeList3New.add(t3);
//                                System.out.println("      三级：" + t3.getText());
//                            }
//                        }
//                    } catch (Exception e) {
//                        log.error(e.getMessage());
//                    }
//                }
//                t2.setNodes(treeNodeList3New);
//            }
//        }
//        //过滤二级
//        for (int i = 0; i < treeNodeList1.size(); i++) {
//            TreeNode t1 = treeNodeList1.get(i);
//            System.out.println("一级：" + t1.getText());
//            List<TreeNode> treeNodeList2 = new ArrayList<TreeNode>();
//            List<TreeNode> treeNodeList2New = new ArrayList<TreeNode>();
//            treeNodeList2 = t1.getNodes();
//            for (int j = 0; j < treeNodeList2.size(); j++) {
//                TreeNode t2 = treeNodeList2.get(j);
//                if (t2.getNodes().size() != 0) {
//                    treeNodeList2New.add(t2);
//                    System.out.println("   二级：" + t2.getText());
//                }
//            }
//            t1.setNodes(treeNodeList2New);
//        }
//        //过滤一级
//        List<TreeNode> treeNodeList1New = new ArrayList<TreeNode>();
//        for (int i = 0; i < treeNodeList1.size(); i++) {
//            TreeNode t1 = treeNodeList1.get(i);
//            if (t1.getNodes().size() != 0) {
//                treeNodeList1New.add(t1);
//                System.out.println("一级：" + t1.getText());
//            }
//
//        }
//
//        return treeNodeList1New;
//    }


//    @Override
//    public List<ObjectField> queryObjectField(String tableId, String fieldId, String fieldNameEn, String fieldNameCh, String isNeed) {
//        List<ObjectField> list = new ArrayList<>();
//        list = resourceManageDao.queryObjectField(tableId, fieldId, fieldNameEn, fieldNameCh, isNeed);
//        return list;
//    }
//
//    @Override
//    public ObjectField showObjectField(String tableId, String fieldId) {
//        // TODO Auto-generated method stub
//        ObjectField objectField = new ObjectField();
//        objectField = resourceManageDao.showObjectField(tableId, fieldId);
//        return objectField;
//    }

    /**
     * 20210623 字段顺序和 各种顺序不再查询数据库 按照页面上的数据来
     *
     * @param objectField
     * @return
     */
    @Override
    public ServerResponse<String> saveObjectField(ObjectFieldStandard objectField,int recno) throws Exception {
        //对于 是否为聚集列 是否为主键列 是否参与MD5运算 如果为 true则需要先在数据库中找到
        if (objectField.getColumnName().equalsIgnoreCase("")) {
            log.error("建表字段为空，不能进行保存或修改操作");
            return ServerResponse.asErrorResponse("建表字段为空，不能进行保存或修改操作");
        }
        if (StringUtils.isBlank(objectField.getFieldDescribe())) {
            objectField.setFieldDescribe(" ");
        }
        if (objectField.getIsPrivate() == null) {
            objectField.setIsPrivate(0);
        }
        if (objectField.getClustRecno() == null ) {
            objectField.setClustRecno(0);
        }
        if (objectField.getPkRecno() == null || objectField.getPkRecno() == 0) {
            objectField.setPkRecno(0);
        }else{
            objectField.setPkRecno(recno);
        }
        if (objectField.getMd5Index() == null || objectField.getMd5Index() == 0 ) {
            objectField.setMd5Index(0);
        }else{
            objectField.setMd5Index(recno);
        }

        String dataType = env.getProperty("database.type", " ");
        // 确定版本日期 VERSION 精确到天 20191023
        int version = Integer.parseInt(DateUtil.formatDate(new Date(), DateUtil.DEFAULT_PATTERN_DATE_SIMPLE));
        objectField.setVersion(version);

        if (objectField.getIsPrivate() == null) {
            //设置默认值为1
            objectField.setIsPrivate(1);
        }

        log.info("更新的字段信息为：" + JSONObject.toJSONString(objectField));
        if (StringUtils.isBlank(objectField.getOdpsPattition()) && Common.DAMENG.equalsIgnoreCase(dataType)) {
            objectField.setOdpsPattition(null);
        } else if (StringUtils.isBlank(objectField.getOdpsPattition())) {
            objectField.setOdpsPattition("");
        }
        if (StringUtils.isBlank(objectField.getProType()) && Common.DAMENG.equalsIgnoreCase(dataType)) {
            objectField.setProType(null);
        } else if (StringUtils.isBlank(objectField.getProType())) {
            objectField.setProType("1");
        }
        // 先判断在这张表中输入的fieldId是否已经存在，如果存在，返回报错信息，提示页面
        int fieldNeedCount = resourceManageAddColumnDao.getcolumnCountByFieldId(objectField.getObjectId(), objectField.getColumnName());
        if (fieldNeedCount == 1) {
            // 如果为1，表示属于更新操作，更新数据
            //20210818 新增需求 并将之前的数据存储到历史表中
            ObjectField copyObjectField = new ObjectField();
            BeanUtils.copyProperties(copyObjectField, objectField);
            copyObjectField.setObjectIdVersion(UUIDUtil.getUUID());
            copyObjectField.setColumnNameState(0);
            String todayStr = DateUtil.formatDateTime(new Date(), DateUtil.DEFAULT_PATTERN_DATE_SIMPLE);
            copyObjectField.setVersions(Integer.valueOf(todayStr));
            copyObjectField.setCreator(objectField.getUpdater());
            //大版本 从版本管理中读取
            String version0 = fieldCodeValDao.searchVersion();
            JSONObject parse = (JSONObject) JSON.parse(version0);
            String versions = parse != null ? parse.getString("synlteFieldVersions") : "1.0";
            copyObjectField.setVersion0(versions);
            copyObjectField.setMemo(objectField.getMemo() == null ? "" : objectField.getMemo());
            if (copyObjectField.getPartitionRecno() == null){
                copyObjectField.setPartitionRecno(0);
            }
            //存储标准字段历史信息
            resourceManageAddColumnDao.saveOldObjectField(copyObjectField);
            //更新字段
            int updateCount = resourceManageAddColumnDao.updateObjectField(objectField);
            if (updateCount == 1) {
                return ServerResponse.asSucessResponse("字段在表objectField中更新成功");
            } else {
                return ServerResponse.asErrorResponse("字段在表objectField中更新失败");
            }
        } else if (fieldNeedCount == 0) {
            log.info("插入的字段信息为：" + JSONObject.toJSONString(objectField));
            //大版本 从版本管理中读取
            String version0 = fieldCodeValDao.searchVersion();
            JSONObject parse = StringUtils.isNotBlank(version0) ? (JSONObject) JSON.parse(version0) : new JSONObject();
            String versions = parse.getString("synlteFieldVersions");
            objectField.setVersion0(StringUtils.isNotBlank(versions) ? versions : "");
            int addCount = resourceManageAddColumnDao.addObjectField(objectField);
            if (addCount == 1) {
                return ServerResponse.asSucessResponse("字段插入到表objectField中成功");
            } else {
                return ServerResponse.asErrorResponse("字段插入到表objectField中失败");
            }
        } else {
            log.error("该字段在表中已经存在多个，请添加别的字段信息");
            return ServerResponse.asErrorResponse("该字段在表中已经存在多个，请添加别的字段信息");
        }

    }

    /**
     * 将 objectField表中指定字段改成被删除状态
     *
     * @param objectId 表id
     * @param
     * @return
     */
    @Override
    public ServerResponse<String> deleteObjectField(Long objectId, String columnName) {
        try {
            synchronized (this) {
                if (StringUtils.isEmpty(columnName)) {
                    log.error("columnName值为空，删除失败");
                    return ServerResponse.asErrorResponse("columnName值为空，删除失败");
                }
                int fieldNeedCount = resourceManageAddColumnDao.getcolumnCountByFieldId(objectId, columnName);
                if (fieldNeedCount == 0) {
                    log.error("该字段在表中不存在，删除失败");
                    return ServerResponse.asErrorResponse("该字段在表中不存在，删除失败");
                }
                // 获取10位数字的uuid
                String uuidStr = UUIDUtil.getUUID().substring(0, 10);
                int updateCount = resourceManageAddColumnDao.deleteObjectField(objectId, columnName, uuidStr);
                if (updateCount > 0) {
                    log.info("该字段在表中删除成功");
                    return ServerResponse.asSucessResponse("该字段在表中删除成功");
                } else {
                    log.error("该字段在表中删除失败");
                    return ServerResponse.asErrorResponse("该字段在表中删除失败");
                }
            }
        } catch (Exception e) {
            log.error(ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("该字段在表中删除失败" + e.getMessage());
        }
    }

//    /**
//     * 20191010 根据部标标准修改需求 左侧tree的修改
//     *
//     * @param name     搜索的名称
//     * @param dataType 数据类型  1：组织分类  2：来源分类  3：资源分类
//     * @return
//     */
//    @Override
//    public List<ZtreeNode> getResourceManageZTreeNodes(String name, int dataType) {
//        List<ZtreeNode> ztreeNodeList = new ArrayList<>();
//        List<Relation> relationList = new ArrayList<>();
//        try {
//            relationList = resourceManageDao.getResourceManageZTreeNodes(null == name ? "" : name, dataType);
//            List<String> parents = relationList.stream().map(o -> o.getParent()).distinct().collect(toList());
//            Map<String, List<Relation>> parentMap = relationList.stream().collect(Collectors.groupingBy(Relation::getParent));
//            for (String parent : parents) {
//                ZtreeNode oneZtreeNode = new ZtreeNode();
//                oneZtreeNode.setId(parent);
//                oneZtreeNode.setpId("root");
//                ztreeNodeList.add(oneZtreeNode);
//                List<Relation> levelTowList = parentMap.get(parent);
//                oneZtreeNode.setName(parent + "(" + String.valueOf(levelTowList.size()) + ")");
//                List<String> childrens = levelTowList.stream().map(o -> o.getChild()).distinct().collect(toList());
//                Map<String, List<Relation>> childMap = levelTowList.stream().collect(Collectors.groupingBy(Relation::getChild));
//                for (String child : childrens) {
//                    ZtreeNode twoZtreeNode = new ZtreeNode();
//                    twoZtreeNode.setId(parent + "|" + child);
//                    twoZtreeNode.setpId(parent);
//                    ztreeNodeList.add(twoZtreeNode);
//                    List<Relation> levelThreeList = childMap.get(child);
//                    twoZtreeNode.setName(child + "(" + String.valueOf(levelThreeList.size()) + ")");
//                    for (Relation r : levelThreeList) {
//                        ZtreeNode threeZtreeNode = new ZtreeNode();
//                        threeZtreeNode.setId(r.getResourceId());
//                        threeZtreeNode.setName(r.getTableName());
//                        threeZtreeNode.setpId(r.getParent() + "|" + r.getChild());
//                        ztreeNodeList.add(threeZtreeNode);
//                    }
//                }
//            }
//
//        } catch (Exception e) {
//            log.error("获取资源分类信息报错" + ExceptionUtil.getExceptionTrace(e));
//        }
//        log.info("返回的结果为" + JSONObject.toJSONString(ztreeNodeList));
//        return ztreeNodeList;
//    }

    /**
     * 展示所有的来源关系
     *
     * @param tableId tableId的信息
     * @return
     */
    @Override
    public List<SourceRelationShip> getSourceRelationShip(String tableId) {
        List<SourceRelationShip> sourceRelationShipList = new ArrayList<>();
        try {
            ObjectPojo objectPojo = resourceManageDao.selectObjectPojoByTableId(tableId);
            if (objectPojo == null) {
                return sourceRelationShipList;
            }
            String objectStateType = ObjectStateType.getObjectStateType(objectPojo.getObjectState());
            // 获取输入和输出的对应关系
            List<InputObjectCreate> allInpuObjectList = standardResourceManageDao.getAllInputObjectRelation(tableId);
            for (InputObjectCreate inputObjectCreate : allInpuObjectList) {
                SourceRelationShip sourceRelationShip = new SourceRelationShip();
                sourceRelationShip.setDataSourceName(inputObjectCreate.getInputObjChiName());
                sourceRelationShip.setRealTableName(inputObjectCreate.getSourceTableName());
                sourceRelationShip.setSourceFirm(ManufacturerName.getNameByIndex(inputObjectCreate.getInputIobjSource()));
                sourceRelationShip.setSourceProtocol(inputObjectCreate.getInputObjEngName());
                sourceRelationShip.setSourceId(inputObjectCreate.getInputObjEngName());
//                sourceRelationShip.setSourceProtocol(String.valueOf(inputObjectCreate.getInputSysId()));
                sourceRelationShip.setTableId(inputObjectCreate.getTableId());
                sourceRelationShip.setDataId(inputObjectCreate.getDataId());
                sourceRelationShip.setCenterId(inputObjectCreate.getCenterId());
                sourceRelationShip.setDataName(inputObjectCreate.getInputObjChiName());
                // 获取来源系统英文名
                String sysChi = resourceManageDao.getSysChiName(String.valueOf(inputObjectCreate.getInputSysId()));
                sourceRelationShip.setSourceProtocolCh(sysChi);
                sourceRelationShip.setSourceSystem(String.valueOf(inputObjectCreate.getInputSysId()));
//                sourceRelationShip.setSourceSystem(inputObjectCreate.getInputObjEngName());
                sourceRelationShip.setStorageTableStatus(objectStateType);

                SourceInfo sourceInfo = resourceManageAddDao.findSourceAllInfo(sourceRelationShip.getSourceProtocol(), sourceRelationShip.getRealTableName(),
                        sourceRelationShip.getSourceSystem());

                //回填数据中心中文和数据源中文
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
            sourceRelationShipList = new ArrayList<>();
            log.error("获取来源关系报错" + ExceptionUtil.getExceptionTrace(e));
        }
        return sourceRelationShipList;
    }

//	------------------------------   增加来源关系的模态框需要的后台程序 -------------------------------------------------

    /**
     * @param mainValue '1':组织分类 '2':来源分类   '3'：资源分类
     * @return
     */
    @Override
    public List<PageSelectOneValue> getFirstClassModeByMainService(String mainValue) {
        List<PageSelectOneValue> pageSelectOneValueList = null;
        try {
            if (mainValue.equalsIgnoreCase("1") ||
                    mainValue.equalsIgnoreCase("2") ||
                    mainValue.equalsIgnoreCase("3")) {
                pageSelectOneValueList = resourceManageDao.getFirstClassModeByMainDao(mainValue);
//				pageSelectOneValueList.add(new PageSelectOneValue("","全部分类"));
                log.info("返回的结果为：" + JSONObject.toJSONString(pageSelectOneValueList));
            } else {
                pageSelectOneValueList = null;
            }
        } catch (Exception e) {
            log.error("根据大类的id号获取一级分类信息" + ExceptionUtil.getExceptionTrace(e));
        }

        return pageSelectOneValueList;
    }

    @Override
    public List<PageSelectOneValue> getSecondaryClassModeByFirstService(String mainValue, String firstClassValue) {
        List<PageSelectOneValue> pageSelectOneValueList = null;
        try {
            if (mainValue.equalsIgnoreCase("1") ||
                    mainValue.equalsIgnoreCase("2") ||
                    mainValue.equalsIgnoreCase("3")) {
                pageSelectOneValueList = resourceManageDao.getSecondaryClassModeByFirstDao(mainValue, firstClassValue);
                pageSelectOneValueList.add(new PageSelectOneValue("", "全部分类"));
                log.info("最后返回的结果为：" + JSONObject.toJSONString(pageSelectOneValueList));
            } else {
                pageSelectOneValueList = null;
            }
        } catch (Exception e) {
            log.error("获取二级分类信息" + ExceptionUtil.getExceptionTrace(e));
        }

        return pageSelectOneValueList;
    }

    /**
     * 添加新的数据来源关系 当从organizational中获取时，先在标准表中找到这个表名对应的相关信息，然后插入到数据库中
     * 如果是从 database中获取，从数据仓库接口获取对应的数据
     *
     * @param tableName 表名
     * @param addType   添加格式
     * @return
     */
    @Override
    public ServerResponse<String> addSourceRelationByTableNameService(String tableName,
                                                                      String addType,
                                                                      String outputTableId,
                                                                      String sourceSystem,
                                                                      String sourceFirm,
                                                                      String objMemo,
                                                                      String dataId,
                                                                      ObjectPojoTable objectPojoTable) {
        ServerResponse<String> stringServerResponse = null;

        try {
            int sourceFirmNum = ManufacturerName.getIndexByName(sourceFirm);
            if (sourceFirmNum == 400) {
                stringServerResponse = ServerResponse.asErrorResponse("来源厂商的中文名称错误" + sourceFirm + "没有找到正确的厂商");
                return stringServerResponse;
            }
//            String tableId = null;
            if (addType.equalsIgnoreCase("organizational")) {
                //  表名可以是中文表名和英文名，先根据表名找到对应的数据
                InputObjectCreate inputObjectCreate = standardResourceManageDao.getSourceRelationByTableName(objectPojoTable.getRealTablename());
                log.info(JSONObject.toJSONString(inputObjectCreate));
                if (inputObjectCreate.inputSysId == null || inputObjectCreate.inputIobjSource == null || StringUtils.isEmpty(inputObjectCreate.objGuid)) {
                    // 没有查到来源信息 表示输入的表名错误,则将信息插入到对应的数据中，先根据tablename获取分类表中的tableId
                    // 先插入 standardObject表中 然后再往 input的表中插入数据
                    String objGuidNew = standardResourceManageDao.getObjGuidByTreeParam(outputTableId, sourceSystem, sourceFirmNum);
                    if (StringUtils.isEmpty(objGuidNew)) {
                        // 在里面插入来源信息 STANDARDIZE_OBJECT
                        int insertCount = standardResourceManageDao.insertStandardizeObjectDao(outputTableId, sourceSystem, sourceFirmNum, tableName, dataId, "", "", objMemo);
                        if (insertCount == 0) {
                            stringServerResponse = ServerResponse.asErrorResponse("向STANDARDIZE_OBJECT表中插入数据失败");
                            return stringServerResponse;
                        }
                    }
                    // 判断  STANDARDIZE.INPUTOBJECT 表是否存在，存在，不用管，不存在插入
                    String inputObjGuid = standardResourceManageDao.getInputGuidById(outputTableId, sourceSystem, sourceFirmNum);
                    objGuidNew = standardResourceManageDao.getObjGuidByTreeParam(outputTableId, sourceSystem, sourceFirmNum);
                    if (StringUtils.isEmpty(inputObjGuid)) {
                        //不存在，则插入一条数据
                        standardResourceManageDao.insertInputObjectDao(objGuidNew, 0, 1, sourceFirmNum);
                    }
                }
                // 查询正确，开始将数据插入到来源关系表中
//                String outputGuid = standardResourceManageDao.getOutputGuidNotInInput(outputTableId);
                String outputGuid = standardResourceManageDao.getOObjGuidByTableId(objectPojoTable.getTableId());
                if (StringUtils.isEmpty(outputGuid)) {
                    log.error("没有在STANDARDIZE_outputOBJECT这个表中找到" + objectPojoTable.getTableId() + "的相关信息");
                    //输出的协议表没有 先在object的表中插入数据
                    standardResourceManageDao.insertStandardizeObjectDao(objectPojoTable.getTableId(), "144", 0, tableName, dataId, "", "", objMemo);
                    //获取最新的objectid值
                    String objGuidInsert = standardResourceManageDao.getOutputGuidNotInInput(objectPojoTable.getTableId());
                    // 获取 object表里面的objt_guid 之后在STANDARDIZE_outputOBJECT 插入到数据中
                    standardResourceManageDao.insertOutputObject(objGuidInsert, "<ReserveName>" +
                            "<ResourceNameInFile></ResourceNameInFile>" +
                            "<ResourceNameNoPrefixInFile></ResourceNameNoPrefixInFile>" +
                            "<ResourceNameInPath></ResourceNameInPath>" +
                            "</ReserveName>", 1, 1, "0");
                }
                outputGuid = standardResourceManageDao.getOObjGuidByTableId(objectPojoTable.getTableId());
                // 需要先判断 在 输入阶段-对象（协议）关系表中需要插入的数据是否已经存在，如果已经存在
                // 如果存在 判断是否是状态被改成禁用 ，如果是，将状态改成启用，如果不是，插入该条数据
                //  样例数据 {"USEDCOUNT":1,"DISABLEUSEDCOUNT":0}
                inputObjectCreate = standardResourceManageDao.getSourceRelationByTableName(objectPojoTable.getRealTablename());
                if (inputObjectCreate.inputSysId == null || inputObjectCreate.inputIobjSource == null || StringUtils.isEmpty(inputObjectCreate.objGuid)) {
                    log.error("查询的数据为：" + JSONObject.toJSONString(inputObjectCreate));
                    stringServerResponse = ServerResponse.asErrorResponse("查询的插入数据为空" + JSONObject.toJSONString(inputObjectCreate));
                }
                log.info("查询的数据为：" + JSONObject.toJSONString(inputObjectCreate));
                Map<String, Integer> queryIsExistMap = standardResourceManageDao.selectDataIsExist(outputGuid,
                        inputObjectCreate.objGuid == null ? "null" : inputObjectCreate.objGuid,
                        inputObjectCreate.inputIobjSource == null ? 0 : inputObjectCreate.inputIobjSource);
                boolean isHailiang = env.getProperty("database.type").equalsIgnoreCase("hailiang");
                int usedCount = Integer.valueOf(String.valueOf(isHailiang ? queryIsExistMap.get("usedcount") : queryIsExistMap.get("USEDCOUNT")));
                int disableUsedCount = Integer.valueOf(String.valueOf(isHailiang ? queryIsExistMap.get("disableusedcount") : queryIsExistMap.get("DISABLEUSEDCOUNT")));
                // 如果都为0，表示在数据库中没有该条数据
                if (usedCount == 0 && disableUsedCount == 0) {
                    // 拼接成需要插入的数据 然后将数据插入到表中
                    log.info("要插入的数据为\nIOBJ_GUID:" + inputObjectCreate.objGuid + "\nOOBJ_GUID:" + outputGuid + "\nIOR_MEMO: ''\nIOR_STATUS:1 IOR_SOURCE:" + inputObjectCreate.inputIobjSource);
                    int insertCount = standardResourceManageDao.insertInputObjectRelate(inputObjectCreate.objGuid, outputGuid, inputObjectCreate.inputIobjSource);
                    Boolean insertFlag = insertCount == 1 ? true : false;
                    log.info("数据的插入状态为:" + insertFlag);
                    if (insertFlag) {
                        stringServerResponse = ServerResponse.asSucessResponse("数据插入到关系表中成功");
                    } else {
                        stringServerResponse = ServerResponse.asErrorResponse("数据插入到关系表中失败");
                    }
                    // 表示已经有对应的关系数据在数据库中，不需要插入，返回报错
                } else if (usedCount > 0 && disableUsedCount == 0) {
                    stringServerResponse = ServerResponse.asSucessResponse("该来源关系已经存在，不用重新插入");

                } else if (usedCount == 0 && disableUsedCount > 0) {
                    // 表示数据库中有该条数据，但是状态是禁用状态，需要将状态改成启用
                    int updateCount = standardResourceManageDao.updateInputObjectRelate(inputObjectCreate.objGuid, outputGuid, inputObjectCreate.inputIobjSource);
                    if (updateCount >= 1) {
                        stringServerResponse = ServerResponse.asSucessResponse("数据插入到关系表中成功");
                    } else {
                        stringServerResponse = ServerResponse.asErrorResponse("数据插入到关系表中失败");
                    }
                } else {
                    // 查询失败，返回报错信息
                    stringServerResponse = ServerResponse.asSucessResponse("查询数据在数据库中情况时报错，返回值为:" + JSONObject.toJSONString(queryIsExistMap));
                }
            } else if (addType.equalsIgnoreCase("database")) {
                // 数据库格式的插入
                stringServerResponse = ServerResponse.asErrorResponse("添加的格式不对，格式名称为" + addType);
            } else {
                stringServerResponse = ServerResponse.asErrorResponse("添加的格式不对，格式名称为" + addType);
            }
        } catch (Exception e) {
            stringServerResponse = ServerResponse.asErrorResponse("添加新的来源关系报错" + e.getMessage());
            log.error("添加新的来源关系报错" + ExceptionUtil.getExceptionTrace(e));
        }

        return stringServerResponse;
    }

    /**
     * 开始删除指定的来源关系
     * 先根据 表协议id获取数据协议表中的输出GUID，
     * 之后根据输入协议的 来源系统 来源数据协议 来源厂商获取来源的 GUID
     * 根据获取到的输出GUID 和输入的GUID来删除指定的
     *
     * @param delSourceRelation 　　指定的来源关系
     * @param outputDataId      表协议ID
     * @return
     */
    @Override
    public ServerResponse<String> deleteSourceRelationService(List<SourceRelationShip> delSourceRelation,
                                                              String outputDataId) {
        ServerResponse<String> serverResponse = null;
        try {
            String inputMessage = "";
            // 根据outputDataId 获取 输出协议GUID
            String outputGuid = standardResourceManageDao.getOutputGuidbYTableId(outputDataId);
            log.info("表" + outputDataId + "对应的GUID为：" + outputGuid);
            if (outputGuid == null) {
                serverResponse = ServerResponse.asErrorResponse("表" + outputDataId + "没有找到对应的GUID");
                return serverResponse;
            }
            for (SourceRelationShip oneSourceRelationShip : delSourceRelation) {
                // 来源系统
                String inputDataId = oneSourceRelationShip.getSourceSystem();
                // 来源数据协议
                String inputSourceCode = oneSourceRelationShip.getSourceProtocol();
                // 来源厂商  为 0 1 2 等数字
                int inputSourceFirm = ManufacturerName.getIndexByName(oneSourceRelationShip.getSourceFirm());
                log.info(inputDataId + " " + inputSourceCode + " " + inputSourceFirm);
                String inputGuid = standardResourceManageDao.getInputGuidById(inputSourceCode, inputDataId, inputSourceFirm);
                log.info("开始删除输出GUID为：" + outputGuid + "输入GUID为：" + inputGuid + "在STANDARDIZE_INPUTOBJECTRELATE表中关系");
                int updateCount = standardResourceManageDao.updateInputObjectRelateDao(outputGuid, inputGuid);
                // 删除standardize_inputObject 这张表的数据
                standardResourceManageDao.deleteStandardInputObjectDao(inputGuid);
                if (updateCount >= 1) {
                    inputMessage = inputMessage + "来源系统[" + inputDataId + "]删除成功\n";
                } else {
                    inputMessage = inputMessage + "来源系统[" + inputDataId + "]删除失败\n";
                }
            }
            serverResponse = ServerResponse.asSucessResponse(inputMessage, inputMessage);
        } catch (Exception e) {
            log.error("开始删除指定的来源关系" + ExceptionUtil.getExceptionTrace(e));
            serverResponse = ServerResponse.asErrorResponse("删除指定的来源关系报错" + e.getMessage());
        }
        return serverResponse;
    }

    @Override
    public ServerResponse<List<OneSuggestValue>> createAddColumnModelService(String type, String condition) {
        List<OneSuggestValue> resultList = null;
        if (type.equalsIgnoreCase("fieldId")) {
            resultList = resourceManageDao.getFieldIdByCondition(condition);
        } else if (type.equalsIgnoreCase("columnName")) {
            resultList = resourceManageDao.getColumnByCondition(condition);
        } else {
            return ServerResponse.asErrorResponse("不支持查询字段条件类型:" + type);
        }
        // 按照优先级排列，先是D开头的科信部表字段，其次是数字开头的技侦部标字段，
        // 再次是SHKX开头的公司私标字段；最后是除去WBZ的其他字段。不要使用WBZ开头的伪标准字段
//        if (resultList != null && resultList.size() > 0) {
//            try{
//                resultList = resultList.stream().sorted(
//                        Comparator.comparing(OneSuggestValue::getFieldId, (x, y) -> {
//                            if (x == null || y == null){
//                                return -1;
//                            }
//                            log.info("x:{}, y：{}", x, y);
//                            if (y.toLowerCase().startsWith("d") && !x.toLowerCase().startsWith("d")) {
//                                return 1;
//                            }
//                            else if (StringUtils.isNumeric(y.substring(0, 1)) && !x.toLowerCase().startsWith("d")) {
//                                return 1;
//                            }
//                            else if (y.toLowerCase().startsWith("shkx") &&
//                                    (!StringUtils.isNumeric(x.substring(0, 1)) && !x.toLowerCase().startsWith("d"))
//                            ) {
//                                return 1;
//                            }else {
//                                return -1;
//                            }
//                        })).collect(toList());
//            }catch (Exception e){
//                log.error("字段排序异常：", e);
//            }
//        }
        return ServerResponse.asSucessResponse(resultList);
    }

    /**
     * 根据指定的数据获取对应的Synltefield 表中字段信息
     *
     * @param type
     * @param inputValue
     * @return
     */
    @Override
    public ServerResponse<Synltefield> getAddColumnByInputService(String type, String inputValue) {
        ServerResponse<Synltefield> synltefieldServerResponse = null;
        try {
            Synltefield oneSynltefield = null;
            if (type.equalsIgnoreCase("fieldId")) {
                oneSynltefield = resourceManageDao.getAddColumnByInputDao("fieldId", inputValue.trim());
            } else if (type.equalsIgnoreCase("columnName")) {
                oneSynltefield = resourceManageDao.getAddColumnByInputDao("columnName", inputValue.trim());
            } else {
                synltefieldServerResponse = ServerResponse.asErrorResponse("传入的type参数值为：" + type + "不正确");
                return synltefieldServerResponse;
            }
            oneSynltefield.setFieldtypeName(SynlteFieldType.getSynlteFieldType(oneSynltefield.getFieldtype()));
            // 20210601 需要获取到分类信息
            List<Map<String, String>> list = resourceManageDao.getCodeTextAndCodeidByObjectField(oneSynltefield.getFieldid());
            boolean isHailiang = env.getProperty("database.type").equalsIgnoreCase("hailiang");
            if (!list.isEmpty()) {
                Map<String, String> map = list.get(0);
                if (map != null) {
                    String fieldClass = isHailiang ? map.get("fieldclass") : map.get("fieldClass");
                    String fieldClassCh = isHailiang ? map.get("fieldclassch") : map.get("fieldClassCh");
                    oneSynltefield.setFieldClass(StringUtils.isBlank(fieldClass) ? "" : fieldClass);
                    oneSynltefield.setFieldClassCh(StringUtils.isBlank(fieldClassCh) ? "" : fieldClassCh);
                }
            }
            synltefieldServerResponse = ServerResponse.asSucessResponse(oneSynltefield);
        } catch (Exception e) {
            log.error("获取Synltefield表中指定的数据报错" + ExceptionUtil.getExceptionTrace(e));
            synltefieldServerResponse = ServerResponse.asErrorResponse("获取" + inputValue +
                    "对应的Synltefield表中字段信息报错" + e.getMessage());
        }
        return synltefieldServerResponse;
    }

    //	@Transactional(rollbackFor = Exception.class)
    @Resubmit
    public ServerResponse<String> saveObjectRelationService(ObjectRelationManage objectRelationManage,ObjectPojoTable objectPojoTable) {
        ServerResponse<String> serverResponse = null;
        try {
            //如果原始汇聚层数据集的id为空则是新增的信息
            if (StringUtils.isBlank(objectRelationManage.getOriginalId())) {
                ObjectRelation originalObject = new ObjectRelation();
                String uuid = UUIDUtil.getUUID();
                //生成原始汇聚的数据集，插入数据库
                log.info("开始生成原始汇聚的数据集信息");
                originalObject.setId(uuid);
                originalObject.setObjectId(Long.parseLong(objectPojoTable.getObjectId()));
                originalObject.setObjectName(objectRelationManage.getOriginalObjectName());
                originalObject.setTableId(objectRelationManage.getOriginalTableId());
                originalObject.setParentId("-1");
                log.info("原始汇聚数据集信息为:{}", originalObject);
                if (StringUtils.isNotBlank(originalObject.getObjectName()) && StringUtils.isNotBlank(originalObject.getTableId())){
                    int addOriginalObjectCount = dataDefinitionDao.insertObjectRelation(originalObject);
                    log.info("插入的原始汇聚的数据集条数为:{}", addOriginalObjectCount);
                }

                ObjectRelation standardObject = new ObjectRelation();
                log.info("开始生成标准的数据集信息");
                String standardId = UUIDUtil.getUUID();
                standardObject.setId(standardId);
//                standardObject.setObjectId(objectRelationManage.getStandardObjectId());
                standardObject.setObjectId(Long.parseLong(objectPojoTable.getObjectId()));
                standardObject.setObjectName(objectRelationManage.getStandardObjectName());
                standardObject.setTableId(objectRelationManage.getStandardTableId());
                //关联的原始汇聚标的id
                standardObject.setParentId(uuid);
                log.info("标准的数据集信息为:{}", standardObject);
                int addStandardObjectCount = dataDefinitionDao.insertObjectRelation(standardObject);
                log.info("插入的标准的数据集条数为:{}", addStandardObjectCount);

                List<ObjectFieldRelation> originalObjectFieldList = new ArrayList<>();
                List<ObjectFieldRelation> standardObjectFieldList = new ArrayList<>();

                int i = 1;
                for (ObjectFieldRelation data : objectRelationManage.getObjectFieldRelation()) {
                    String id = UUIDUtil.getUUID();
                    data.setRecno(i++);
                    data.setSetId(uuid);
                    data.setId(id);
                    data.setParentId("-1");
                    originalObjectFieldList.add(data);
                    List<ObjectFieldRelation> objectFieldRelationMapping = data.getObjectFieldRelationMapping();
                    if (!objectFieldRelationMapping.isEmpty()) {
                        data.getObjectFieldRelationMapping().stream().forEach(e -> {
                            e.setId(UUIDUtil.getUUID());
                            e.setSetId(standardId);
                            e.setParentId(id);
                            e.setParentColumnName(data.getColumnName());
                            standardObjectFieldList.add(e);
                        });
                    }
                }
                if (!originalObjectFieldList.isEmpty() && !standardObjectFieldList.isEmpty()) {
                    int insertOriginalCount = dataDefinitionDao.insertObjectFieldRelationList(originalObjectFieldList);
                    log.info("插入的原始数据项条数为:{}", insertOriginalCount);
                    int insertStandardCount = dataDefinitionDao.insertObjectFieldRelationList(standardObjectFieldList);
                    log.info("插入的标准数据项条数为:{}", insertStandardCount);
                }
                serverResponse = ServerResponse.asSucessResponse("数据集对标内容存储成功");
            } else {
                //此时是更新数据集与数据项信息
                //拿到之前关联的数据集
                ObjectRelation standardObjectRelation = dataDefinitionDao.getStandardObjectRelationByParentId(objectRelationManage.getOriginalId());
                String standObjectUUId = UUIDUtil.getUUID();
                int addStandardObjectCount = 0;
                if (StringUtils.isBlank(objectRelationManage.getStandardParentId())
                        || !(objectRelationManage.getStandardParentId().equalsIgnoreCase(objectRelationManage.getOriginalId()))) {
                    log.info("开始删除标准层的数据项");
                    int i = dataDefinitionDao.deleteObjectRelation(standardObjectRelation.getId(), standardObjectRelation.getObjectId());
                    log.info("删除的数据集信息为:{}", i);
                    ObjectRelation standObject = new ObjectRelation();
                    standObject.setId(standObjectUUId);
                    standObject.setTableId(objectRelationManage.getStandardTableId());
                    standObject.setObjectName(objectRelationManage.getStandardObjectName());
                    //如果史坦察的数据集则没有Objectid的,去数据库查一次更为准确
                    String standObjectId = resourceManageAddDao.getObjectIDByTableID(standardObjectRelation.getTableId());
                    standObject.setObjectId(Long.valueOf(standObjectId));
                    standObject.setParentId(objectRelationManage.getOriginalId());
                    //插入更新后的标准数据集
                    addStandardObjectCount = dataDefinitionDao.insertObjectRelation(standObject);
                    log.info("插入的标准的数据集条数为:{}", addStandardObjectCount);
                }
                //更新数据集对标字段信息
                List<ObjectFieldRelation> originalObjectFieldList = objectRelationManage.getObjectFieldRelation();
                if (!originalObjectFieldList.isEmpty() && originalObjectFieldList.size() != 0) {
                    for (ObjectFieldRelation data : originalObjectFieldList) {
                        data.setSetId(objectRelationManage.getOriginalId());
                        int columnCount = dataDefinitionDao.getColumnCountByIdAndName(data.getSetId(), data.getColumnName());
                        if (columnCount == 1) {
                            //有数据则是更新
                            int updateFieldCount = dataDefinitionDao.updateObjectFieldRelation(data);
                            if (updateFieldCount >= 1) {
                                dataDefinitionDao.deleteStandardFieldRelationByParentId(data.getId());
                                int finalAddStandardObjectCount1 = addStandardObjectCount;
                                List<ObjectFieldRelation> objectFieldRelationMapping = data.getObjectFieldRelationMapping();
                                objectFieldRelationMapping.stream().forEach(d -> {
                                    if (finalAddStandardObjectCount1 == 0) {
                                        d.setSetId(standardObjectRelation.getId());
                                        d.setId(UUIDUtil.getUUID());
                                        d.setParentId(data.getId());
                                        d.setParentColumnName(data.getColumnName());
                                    } else {
                                        d.setSetId(standObjectUUId);
                                        d.setId(UUIDUtil.getUUID());
                                        d.setParentId(data.getId());
                                        d.setParentColumnName(data.getColumnName());
                                    }
                                });
                                int insertStandardCount = dataDefinitionDao.insertObjectFieldRelationList(objectFieldRelationMapping);
                                log.info("数据项信息更新成功");
                            } else {
                                log.info("数据项信息更新失败");
                            }
                        } else {
                            String originalFieldId = UUIDUtil.getUUID();
                            data.setId(originalFieldId);
                            data.setParentId("-1");
                            int insertFieldCount = dataDefinitionDao.insertObjectFieldRelation(data);
                            if (insertFieldCount == 1) {
                                List<ObjectFieldRelation> objectFieldRelationMapping = data.getObjectFieldRelationMapping();
                                int finalAddStandardObjectCount1 = addStandardObjectCount;
                                objectFieldRelationMapping.stream().forEach(d -> {
                                    if (finalAddStandardObjectCount1 == 0) {
                                        d.setSetId(standardObjectRelation.getId());
                                        d.setId(UUIDUtil.getUUID());
                                        d.setParentId(originalFieldId);
                                        d.setParentColumnName(data.getColumnName());
                                    } else {
                                        d.setSetId(standObjectUUId);
                                        d.setId(UUIDUtil.getUUID());
                                        d.setParentId(originalFieldId);
                                        d.setParentColumnName(data.getColumnName());
                                    }
                                });
                                int insertStandardCount = dataDefinitionDao.insertObjectFieldRelationList(objectFieldRelationMapping);
                                log.info("数据项信息更新成功");
                            } else {
                                log.info("数据项信息更新失败");
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            serverResponse = ServerResponse.asErrorResponse(e.getMessage());
            log.error("保存修改后的数据报错" + ExceptionUtil.getExceptionTrace(e));
        }
        return serverResponse;
    }

    /**
     * 保存 修改后的数据信息 当objectid为空时，表示新增 当objectid不为空时，为修改。
     * 新增需求，当objectID存在时，可以修改tableid，此时需要做判断
     * 20200225 因为 分级分类表变成 tableorg 该表是根据表名来做关联的，表名可以做修改，所以先要获取到修改之前的表名，
     * 如果表名发生变化，则也要修改表名
     *
     * @param standardObjectManage
     * @return
     */
    @Override
    public ServerResponse<String> saveResourceManageTableService(StandardObjectManage standardObjectManage) {
        ServerResponse<String> serverResponse = null;
        try {
            log.info("-------开始插入标准信息----");

            List<SourceRelationShip> sourceRelationShipList = standardObjectManage.getSourceRelationShipList();
            SourceRelationShip sourceRelationShip = sourceRelationShipList.size() > 0 ? sourceRelationShipList.get(0) : null;
            ObjectPojoTable objectPojoTable = standardObjectManage.getObjectPojoTable();
            String objectId = objectPojoTable.getObjectId();
            // 目标协议对应厂商
            int factory = ManufacturerName.getIndexByName(objectPojoTable.getOwnerFactory());
            if (factory == 400) {
                factory = 0;
            }
            //查询大版本和设置小版本
            String searchVersion = fieldCodeValDao.searchVersion();
            JSONObject parse = StringUtils.isNotBlank(searchVersion) ? (JSONObject) JSON.parse(searchVersion) : new JSONObject();
            String versions = parse.getString("objectVersions");

            // 注入标准表objectPojoTable其他信息
            injectObjectPojoTable(objectPojoTable, factory, versions);

            // 判断tableId是修改的数据还是新增的数据，如果有objectid，现根据objectId判断tableid是否相同，
            // 如果相同，表示tableid没有修改，如果不同，表示tableid发生了修改，需要更改该tableid，
            // 如果修改后的tableid重复返回报错信息
            if (StringUtils.isNotBlank(objectId)) {
                String dataBaseTableid = standardResourceManageDao.getTableIdByObjectId(objectId);
                if (StringUtils.isEmpty(dataBaseTableid)) {
                    log.error("根据objectId获取到的tableid为空");
                    return ServerResponse.asErrorResponse("根据objectId获取到的tableid为空");
                }
                log.info("数据库中找到的tableId值为：" + dataBaseTableid);
                if (!objectPojoTable.getTableId().equalsIgnoreCase(dataBaseTableid)) {
                    // 如果不相等，表示修改了tableid信息，先判断修改后的id信息是否重复，如果重复，表示不能修改该值
                    int tableIdCount = standardResourceManageDao.getCountObjectByTableId(objectPojoTable.getTableId());
                    if (tableIdCount >= 1) {
                        log.error("该tableId" + objectPojoTable.getTableId() + "在数据库中已经存在，不能修改为该值");
                        return ServerResponse.asErrorResponse("该tableId" + objectPojoTable.getTableId() + "在数据库中已经存在，不能修改为该值");
                    }
                    int updateCount = standardResourceManageDao.updateObjectTableId(dataBaseTableid, objectPojoTable.getTableId());
                    log.info(String.format("将数据库中的tableid：%s修改为：%s", dataBaseTableid, objectPojoTable.getTableId()));
                    //  还需要修改 STANDARDIZE_object表中的数据
                    //  判断在 STANDARDIZE_outputobject表中的数据是否已经存在，如果存在，先获取到 obj_guid,
                    //  然后根据该值获取到更新tableid
                    String ObjGuidString = standardResourceManageDao.getOutPutObjGuidByTableId(dataBaseTableid, objectPojoTable.getCodeTextTd(), factory);
                    if (!StringUtils.isEmpty(ObjGuidString)) {
                        standardResourceManageDao.updateStandardizeObjectByGuid(objectPojoTable.getTableId(), ObjGuidString);
                    }
                    if (updateCount >= 1) {
                        standardObjectManage.setOperateType(3);
                        log.info("tableid修改成功");
                    } else {
                        log.error("tableid修改失败");
                        return ServerResponse.asErrorResponse("tableId数据修改失败，请排查原因");
                    }
                }
            }
            //判断这个目标表是否存在，如果存在更新
            int numCount = standardResourceManageDao.getCountObjectByTableId(objectPojoTable.getTableId());
            if (StringUtils.isEmpty(objectId) && numCount > 1) {
                // 直接删除现有的信息
                log.error("在表中同一个tableId有多个数据，数据库中数据报错");
                return ServerResponse.asErrorResponse("在表中同一个tableId有多个数据，数据库中数据报错");
            }
            // 20200309 新增了 datatype这个参数
            //  原始库->0:源数据  资源库->2: 资源数据(知识库)  主题库->5:专题库
            //知识库->5:专题库  业务库->3：资源数据（行为日志库）  业务要素索引库->无定义
            try {
                String primaryDatasourceCh = objectPojoTable.getOrganizationClassify().split("/")[0];
                if (primaryDatasourceCh.equalsIgnoreCase("原始库") || primaryDatasourceCh.equalsIgnoreCase("其他")) {
                    objectPojoTable.setDataType(0);
                } else if (primaryDatasourceCh.equalsIgnoreCase("资源库")) {
                    objectPojoTable.setDataType(2);
                } else if (primaryDatasourceCh.equalsIgnoreCase("主题库") ||
                        primaryDatasourceCh.equalsIgnoreCase("知识库")) {
                    objectPojoTable.setDataType(5);
                } else if (primaryDatasourceCh.equalsIgnoreCase("业务库")) {
                    objectPojoTable.setDataType(3);
                } else if(primaryDatasourceCh.equalsIgnoreCase("业务要素索引库")){
                    objectPojoTable.setDataType(4);
                } else {
                    objectPojoTable.setDataType(0);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            //
            if (StringUtils.isEmpty(objectId) && numCount == 1) {
                if (!objectPojoTable.getFlow()) {
                    log.error("该tableId已经存在，不能重复新增");
                    return ServerResponse.asErrorResponse("该tableId已经存在，不能重复新增");
                } else {
                    // 流程中的判断
                    if (StringUtils.isNotEmpty(objectPojoTable.getRealTablename())) {
                        // 如果真实表名和tableId对应得上，则表示可以更新该数据，否则依然报错
                        int objectCount = standardResourceManageDao.getCountObjectByIdName(objectPojoTable.getTableId(),
                                objectPojoTable.getRealTablename());
                        if (objectCount >= 1) {
                            int updateCount = standardResourceManageDao.updateObjectMessageByTableId(objectPojoTable);
                            standardObjectManage.setOperateType(3);
                            log.info("Object表更新了" + updateCount + "条");
                        } else {
                            log.error("该tableId在object表中已经存在，并且存储的表名不是该表名，新增失败");
                            return ServerResponse.asErrorResponse("该tableId在object表中已经存在，并且存储的表名不是该表名，新增失败");
                        }
                    } else {
                        log.error("该tableId已经存在，不能重复新增");
                        return ServerResponse.asErrorResponse("该tableId已经存在，不能重复新增");
                    }
                }
            } else if (StringUtils.isEmpty(objectId) && numCount == 0) {
                // 当objectid为空时，表示这个表不存在，先查找最大的objectId，然后添加这个值
                String oldObjectId = standardResourceManageDao.getMaxObjectId();
                int newObjectId = 1000001;
                if (StringUtils.isEmpty(oldObjectId) || Integer.valueOf(oldObjectId) < 1000001) {
                    newObjectId = 1000001;
                } else {
                    newObjectId = Integer.valueOf(oldObjectId) + 1;
                }
                objectPojoTable.setObjectId(String.valueOf(newObjectId));

                if(StringUtils.isBlank(objectPojoTable.getSourceId())){
                    objectPojoTable.setSourceId(objectPojoTable.getTableId());
                }
                //把object表中的数据进行修改
                int inserCount = standardResourceManageDao.addObjectMessageDao(objectPojoTable);
                standardObjectManage.setOperateType(2);
                log.info("Object表插入了" + inserCount + "条");
            } else {
                int num = standardResourceManageDao.checkIsUpdate(objectPojoTable);
                if (num >= 1) {
                    log.info("object表未做改动");
                    return ServerResponse.asSucessResponse("Object表未做改动，更新失败");
                } else {
                    ObjectPojoTable oldObject = standardResourceManageDao.searchOneData(objectPojoTable.getObjectId());
                    int updateCount = standardResourceManageDao.updateObjectMessageDao(objectPojoTable);
                    standardObjectManage.setOperateType(3);
                    log.info("Object表更新了" + updateCount + "条");

                    //更新成功，生成版本库记录
                    ObjectVersion objectVersion = new ObjectVersion();
                    String uuId = UUIDUtil.getUUID();
                    objectVersion.setObjectVersion(uuId);
                    objectVersion.setObjectId(objectPojoTable.getObjectId());
                    if (!objectPojoTable.getRealTablename().isEmpty()) {
                        objectVersion.setTableName(objectPojoTable.getRealTablename());
                    }
                    StringBuffer stringBuffer = new StringBuffer();
                    if (!oldObject.getDataSourceName().equals(objectPojoTable.getDataSourceName())) {
                        stringBuffer.append("数据中文名属性,");
                    }
                    if (!(oldObject.getDataType().equals(objectPojoTable.getDataType()))) {
                        stringBuffer.append("数据组织分类属性,");
                    }
                    String sjzylylxValue = objectPojoTable.getSJZYLYLXVALUE() == null ? "" : objectPojoTable.getSJZYLYLXVALUE();
                    String sjzylylxValueOld = oldObject.getSJZYLYLXVALUE() == null ? "" : oldObject.getSJZYLYLXVALUE();
                    if(!(sjzylylxValue.equalsIgnoreCase(sjzylylxValueOld))){
                        stringBuffer.append("数据来源分类属性,");
                    }
                    if (!(oldObject.getRealTablename().equals(objectPojoTable.getRealTablename()))) {
                        stringBuffer.append("物理表名属性,");
                    }
//					if(!(oldObject.getIsActiveTable().equals(objectPojoTable.getIsActiveTable()))){
//						stringBuffer.append("物理存储类型、");
//					}
                    if (!(oldObject.getStorageTableStatus().equals(objectPojoTable.getStorageTableStatus()))) {
                        stringBuffer.append("存储表状态属性,");
                    }
                    if (!(oldObject.getObjectMemo().equals(objectPojoTable.getObjectMemo()))) {
                        stringBuffer.append("数据描述属性,");
                    }
                    if (!(oldObject.getDataLevel().equals(objectPojoTable.getDataLevel()))) {
                        stringBuffer.append("数据分级属性,");
                    }
                    if((StringUtils.isBlank(oldObject.getSjzybq1()) && StringUtils.isNotBlank(objectPojoTable.getSjzybq1())) ||
                            (StringUtils.isNotBlank(objectPojoTable.getSjzybq1()) &&
                            (!(oldObject.getSjzybq1().equalsIgnoreCase(objectPojoTable.getSjzybq1()))))){
                        stringBuffer.append("资源标签一,");
                    }
                    if((StringUtils.isBlank(oldObject.getSjzybq2()) && StringUtils.isNotBlank(objectPojoTable.getSjzybq2())) ||
                            (StringUtils.isNotBlank(objectPojoTable.getSjzybq2()) &&
                                    (!(oldObject.getSjzybq2().equalsIgnoreCase(objectPojoTable.getSjzybq2()))))){
                        stringBuffer.append("资源标签二,");
                    }
                    if((StringUtils.isBlank(oldObject.getSjzybq3()) && StringUtils.isNotBlank(objectPojoTable.getSjzybq3())) ||
                            (StringUtils.isNotBlank(objectPojoTable.getSjzybq3()) &&
                                    (!(oldObject.getSjzybq3().equalsIgnoreCase(objectPojoTable.getSjzybq3()))))){
                        stringBuffer.append("资源标签三,");
                    }
                    if((StringUtils.isBlank(oldObject.getSjzybq4()) && StringUtils.isNotBlank(objectPojoTable.getSjzybq4())) ||
                            (StringUtils.isNotBlank(objectPojoTable.getSjzybq4()) &&
                                    (!(oldObject.getSjzybq4().equalsIgnoreCase(objectPojoTable.getSjzybq4()))))){
                        stringBuffer.append("资源标签四,");
                    }
                    if((StringUtils.isBlank(oldObject.getSjzybq5()) && StringUtils.isNotBlank(objectPojoTable.getSjzybq5())) ||
                            (StringUtils.isNotBlank(objectPojoTable.getSjzybq5()) &&
                                    (!(oldObject.getSjzybq5().equalsIgnoreCase(objectPojoTable.getSjzybq5()))))){
                        stringBuffer.append("资源标签五,");
                    }
                    if((StringUtils.isBlank(oldObject.getSjzybq6()) && StringUtils.isNotBlank(objectPojoTable.getSjzybq6())) ||
                            (StringUtils.isNotBlank(objectPojoTable.getSjzybq6()) &&
                                    (!(oldObject.getSjzybq6().equalsIgnoreCase(objectPojoTable.getSjzybq6()))))){
                        stringBuffer.append("资源标签六,");
                    }

                    StringBuffer constantStr = null;
                    if(stringBuffer.length() != 0){
                        constantStr = new StringBuffer("修改了");
                        constantStr.append(stringBuffer);
                        if(constantStr.indexOf(",",constantStr.length()-1) != -1){
                            objectVersion.setMemo(constantStr.substring(0,constantStr.length()-1));
                        }
                    }else{
                        constantStr = new StringBuffer("未修改内容");
                        objectVersion.setMemo(constantStr.toString());
                    }

                    objectVersion.setVersion(objectPojoTable.getVersion());
                    objectVersion.setVersions(StringUtils.isNotBlank(versions) ? versions : "1.0");
                    objectVersion.setAuthor(objectPojoTable.getUpdater());
                    Date date = null;
                    String dayStr = null;
                    try {
                        dayStr = DateUtil.formatDateTime(new Date(), DateUtil.DEFAULT_PATTERN_DATETIME);
                        date = DateUtil.parseDate(dayStr, DateUtil.DEFAULT_PATTERN_DATETIME);
                    } catch (Exception e) {
                        log.error("解析时间报错" + ExceptionUtil.getExceptionTrace(e));
                        date = new Date();
                    }
                    objectVersion.setUpdateTime(date);
                    int versionNum = standardResourceManageDao.saveOneDataVersion(objectVersion);
                    log.info("生成Object版本信息:{}", versionNum);

                    //将标准历史信息保存至标准历史表中
                    ObjectPojoTable copyeObject = new ObjectPojoTable();
                    ConvertUtils.register(new DateConverter(null), java.util.Date.class);
                    BeanUtils.copyProperties(copyeObject, oldObject);
                    copyeObject.setObjectVersion(uuId);
                    copyeObject.setStorageTableStatus(String.valueOf(ObjectStateType.getObjectStateStatus("废止")));
                    copyeObject.setUpdateTime(dayStr);
                    copyeObject.setVersion(Integer.valueOf(DateUtil.formatDateTime(new Date(), DateUtil.DEFAULT_PATTERN_DATE_SIMPLE)));
                    copyeObject.setVersions(StringUtils.isNotBlank(versions) ? versions : "1.0");
                    if (copyeObject.getStandardType() == null){
                        copyeObject.setStandardType(0);
                    }
                    if (copyeObject.getDataLevel() == null || StringUtils.isBlank(copyeObject.getDataLevel())){
                        copyeObject.setDataLevel(objectPojoTable.getDataLevel());
                    }
                    log.info("保存的OBJECT_HISTORY参数为：" + JSONObject.toJSONString(copyeObject));
                    standardResourceManageDao.saveOldData(copyeObject);
                }
            }
            // 这些都是定义的输出表
            // 然后将数据插入到 曾瑞比组的 STANDARDIZE_OUTPUTOBJECT   STANDARDIZE_object 表中
//                String OobjGuid = standardResourceManageDao.getOutputGuidbYId(objectPojoTable.getTableId());

            int objectCount = standardResourceManageDao.selectCountStandardizeCount(objectPojoTable.getTableId());
            if (objectCount == 0) {
                // 先判断在STANDARDIZE_object中是否存在，如果存在，不管，如果不存在，则插入
                String objGuid = standardResourceManageDao.getObjGuidByObject(objectPojoTable.getTableId(), objectPojoTable.getCodeTextTd());
                if (StringUtils.isEmpty(objGuid)) {
                    // 在STANDARDIZE_object中插入该值
                    if(sourceRelationShip != null){
                        String tableNameEn = StringUtils.isNotBlank(sourceRelationShip.getTableNameEN()) ? sourceRelationShip.getTableNameEN() : sourceRelationShip.getRealTableName();
                        String tableNameCh = StringUtils.isNotBlank(sourceRelationShip.getTableNameCN()) ? sourceRelationShip.getTableNameCN() : sourceRelationShip.getDataSourceName();
                        String codeTextTd = StringUtils.isNotBlank(objectPojoTable.getCodeTextTd()) ? objectPojoTable.getCodeTextTd() : sourceRelationShip.getSourceSystem();
                        String sourceId = StringUtils.isNotBlank(objectPojoTable.getSourceId()) ? objectPojoTable.getSourceId() : sourceRelationShip.getSourceProtocol();
                        String ownerFactory = StringUtils.isNotBlank(objectPojoTable.getOwnerFactory()) ? objectPojoTable.getOwnerFactory() : "0";
                        String dataId = StringUtils.isNotBlank(sourceRelationShip.getDataId()) ? sourceRelationShip.getDataId() : "dataId";
                        int insertCount1 = standardResourceManageDao.insertObjectDao(codeTextTd,sourceId, tableNameEn,ownerFactory,dataId,
                                sourceRelationShip.getCenterId(),tableNameCh);
                        log.info("STANDARDIZE_object中插入了" + insertCount1 + "条数据");
                    }
                }
                String objGuidNew = standardResourceManageDao.getObjGuidByObject(
                        objectPojoTable.getTableId(), objectPojoTable.getCodeTextTd());
                if (StringUtils.isEmpty(objGuidNew)) {
                    log.info("STANDARDIZE_OUTPUTOBJECT表中插入失败");
                } else {
                    standardResourceManageDao.insertOutputObject(objGuidNew, "<ReserveName>" +
                            "<ResourceNameInFile></ResourceNameInFile>" +
                            "<ResourceNameNoPrefixInFile></ResourceNameNoPrefixInFile>" +
                            "<ResourceNameInPath></ResourceNameInPath>" +
                            "</ReserveName>", 1, 1, objectPojoTable.getOwnerFactory());
                }
            } else if (objectCount == 1) {
                String OobjGuid = standardResourceManageDao.getOutputGuidNotInInput(
                        objectPojoTable.getTableId());
                objectPojoTable.setOobjGuidStr(OobjGuid);
                int updateCount = standardResourceManageDao.updateObjectDao(objectPojoTable);

                log.info("STANDARDIZE_object标准的更新了" + updateCount + "条数据");
            } else {
                log.error("datastand_outputobject有问题,存在多个相同的数据");
            }
            // 先调用分级分类的接口，更改这个tableId对应的分级分类信息
            // @ 20200225 修改分级分类的方式发生变化，不再是调用接口，而是直接修改表 tableorg
            // @ 20200317 新增数据来源分类
            // @ 20200915 分类信息不再插入到表tableorg中 插入到 PUBLIC_DATA_INFO
            // 1.8取消对PUBLIC_DATA_INFO表操作
//				String classMessage = updateInsertClassify(objectPojoTable , oldTableName);
            //  @ 20200319 当修改分级分类信息时  修改  table_organization_assets 表数据

            //20200225 如果objectId已经存在，则获取之前已经存在的表名
            String oldTableName = "";
            if (StringUtils.isNotEmpty(objectId)) {
                oldTableName = standardResourceManageDao.getOldTableNameByObjectIdDao(objectId);
            }
            String finalOldTableName = oldTableName;
            // 异步更新，让保存快一点
            ObjectPojoTable finalObjectPojoTable = objectPojoTable;
            CompletableFuture.runAsync(() -> {
                updateAssetsClassify(finalObjectPojoTable, finalOldTableName);
            });
            serverResponse = ServerResponse.asSucessResponse("数据库中数据更改或增加数据成功\n");
        } catch (Exception e) {
            if (e.getMessage().contains("OBJECTNAME") && e.getMessage().contains("违反唯一约束条件")) {
                serverResponse = ServerResponse.asErrorResponse("数据名在object表中重复");
            } else {
                serverResponse = ServerResponse.asErrorResponse(e.getMessage());
            }
            log.error("保存修改后的数据报错" + ExceptionUtil.getExceptionTrace(e));
        }
        return serverResponse;
    }

    /**
     * 回填组织分类和来源分类的码值
     *
     * @param objectPojoTable
     */
    private void injectObjectPojoTable(ObjectPojoTable objectPojoTable, int factory, String versions) throws ParseException {
        //  如果是hive平台 则保存的 storageDataMode 的值为 hbase 如果是odps平台 则是 ads
        if (Common.HUA_WEI_YUN.equalsIgnoreCase(parameterMap.getOrDefault(Common.DATA_PLAT_FORM_TYPE, Common.HUA_WEI_YUN))) {
            objectPojoTable.setStorageDataMode("hbase");
        } else {
            objectPojoTable.setStorageDataMode("ads");
        }
        if (StringUtils.isBlank(objectPojoTable.getCreateTime())) {
            objectPojoTable.setCreateTime(DateUtil.formatDate(new Date(), DateUtil.DEFAULT_PATTERN_DATETIME));
            objectPojoTable.setCreateTimeRel(new Date());
        }else {
            objectPojoTable.setCreateTimeRel(DateUtil.parseDateTime(objectPojoTable.getCreateTime(),DateUtil.DEFAULT_PATTERN_DATETIME));
        }
        if (StringUtils.isBlank(objectPojoTable.getSourceId())) {
            objectPojoTable.setSourceId(" ");
        }
        if (StringUtils.isBlank(objectPojoTable.getUpdateTime())){
            objectPojoTable.setUpdateTime(DateUtil.formatDate(new Date(), DateUtil.DEFAULT_PATTERN_DATETIME));
            objectPojoTable.setUpdateTimeRel(new Date());
        }else {
            objectPojoTable.setUpdateTimeRel(DateUtil.parseDateTime(objectPojoTable.getUpdateTime(),DateUtil.DEFAULT_PATTERN_DATETIME));
        }
        objectPojoTable.setOwnerFactory(String.valueOf(factory));
        if (StringUtils.isBlank(objectPojoTable.getObjectId()) && StringUtils.isBlank(objectPojoTable.getStorageTableStatus())) {
            objectPojoTable.setStorageTableStatus(String.valueOf(ObjectStateType.getObjectStateStatus("未发布")));
        } else {
            objectPojoTable.setStorageTableStatus(String.valueOf(ObjectStateType.getObjectStateStatus(objectPojoTable.getStorageTableStatus())));
        }
        objectPojoTable.setStorageDataMode(String.valueOf(StoreType.getStoreValue(objectPojoTable.getStorageDataMode())));
        if(objectPojoTable.getIsActiveTable() == null){
            objectPojoTable.setIsActiveTable(1);
        }
        if(objectPojoTable.getStandardType() == null){
            objectPojoTable.setStandardType(0);
        }
        String todayStr = DateUtil.formatDateTime(new Date(), DateUtil.DEFAULT_PATTERN_DATE_SIMPLE);
        objectPojoTable.setVersion(Integer.valueOf(todayStr));
        objectPojoTable.setVersions(StringUtils.isNotBlank(versions) ? versions : "1.0");

        // 数据组织分类的中文名 如果classIds不为空，则直接用classIds，否则用中文查询代码值
        String classIds = objectPojoTable.getClassIds();
        String parClassIds = "";        // 1级
        String secondClassIds = "";     // 2级
        String threeClassIds = "";      // 3级
        if(classIds.contains(",")){
            // 一、二、三级的组织分类数组
            String[] list = classIds.split(",");
            String parOrganiztionClassif = objectPojoTable.getOrganizationClassify().split("/")[0];
            if("原始库".equals(parOrganiztionClassif) || "主题库".equals(parOrganiztionClassif)){
                parClassIds = list[0].split(Common.DATA_ORGANIZATION_CODE)[1];
                secondClassIds = list[1];
                if (list.length > 2){
                    threeClassIds = list[2].split(secondClassIds)[1];
                    if(StringUtils.isNotBlank(parClassIds) && StringUtils.isNotBlank(threeClassIds)){
                        objectPojoTable.setSJZZYJFLVALLUE(parClassIds);
                        objectPojoTable.setSJZZEJFLVALUE(threeClassIds);
                    }
                }else {
                    threeClassIds = list[1].split(list[0])[1];
                    if(StringUtils.isNotBlank(parClassIds) && StringUtils.isNotBlank(threeClassIds)){
                        objectPojoTable.setSJZZYJFLVALLUE(parClassIds);
                        objectPojoTable.setSJZZEJFLVALUE(threeClassIds);
                    }
                }
            }else {
                // 非原始库时
                if (list.length != 0) {
                    parClassIds = list[0].split(Common.DATA_ORGANIZATION_CODE)[1];
                    secondClassIds = list[1].split(list[0])[1];
                    objectPojoTable.setSJZZYJFLVALLUE(StringUtils.isBlank(parClassIds) ? "" : parClassIds);
                    objectPojoTable.setSJZZEJFLVALUE(StringUtils.isBlank(secondClassIds) ? "" : secondClassIds);
                }
            }
        }else {
            // 当组织分类为其他的时候
            objectPojoTable.setSJZZYJFLVALLUE(classIds.split(Common.DATA_ORGANIZATION_CODE)[1]);
        }
        // 数据来源分类的中文名
        String sourceClassIds = objectPojoTable.getSourceClassIds();
        if(StringUtils.isNotEmpty(sourceClassIds)){
            List<String> sourceIdList = Arrays.asList(sourceClassIds.split(","));
            String sourceIds = sourceIdList.get(sourceIdList.size()-1).split(sourceIdList.get(0))[1];
            objectPojoTable.setSJZYLYLXVALUE(sourceIds);
        }
    }

    /**
     * 修改 table_organization_assets表中的分级分类数据
     * 目前修改 数据组织分类 和数据来源分类信息
     *
     * @param objectPojoTable
     * @param oldTableName
     */
    @Async
    public void updateAssetsClassify(ObjectPojoTable objectPojoTable, String oldTableName) {
        try {
            log.info("开始修改table_organization_assets表中分类信息");
            // 数据组织分类
            String sourceClassifyCh = objectPojoTable.getSourceClassify();
            // 数据来源分类
            String organizationClassifyCh = objectPojoTable.getOrganizationClassify();
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
            }else if(organizationClassifyCh.contains("其它")){
                oneOrganizationClassifyCh = organizationClassifyCh;
                twoOrganizationClassifyCh = "";
            }else if(organizationClassifyCh.split("/").length == 3){
                oneOrganizationClassifyCh = organizationClassifyCh.split("/")[0];
                twoOrganizationClassifyCh = organizationClassifyCh.split("/")[1];
                threeOrganizationClassifyCh = organizationClassifyCh.split("/")[2];
            }else if (organizationClassifyCh.split("/").length == 2) {
                oneOrganizationClassifyCh = organizationClassifyCh.split("/")[0];
                twoOrganizationClassifyCh = organizationClassifyCh.split("/")[1];
            } else if (organizationClassifyCh.split("/").length == 1) {
                oneOrganizationClassifyCh = organizationClassifyCh.split("/")[0];
                twoOrganizationClassifyCh = "";
            } else {
                oneOrganizationClassifyCh = "未知";
                twoOrganizationClassifyCh = "未知";
            }
            oldTableName = objectPojoTable.getRealTablename();
            resourceManageAddDao.updateAssetsClassify(oneSourceClassifyCh, twoSourceClassifyCh,
                    oneOrganizationClassifyCh, twoOrganizationClassifyCh,threeOrganizationClassifyCh, oldTableName);
            resourceManageAddDao.updateAssetsTempClassify(oneSourceClassifyCh, twoSourceClassifyCh,
                    oneOrganizationClassifyCh, twoOrganizationClassifyCh,threeOrganizationClassifyCh, oldTableName);
        } catch (Exception e) {
            log.error("修改table_organization_assets表中分类信息报错" + ExceptionUtil.getExceptionTrace(e));
        }
    }

//    /**
//     * 修改 tableOrg表中的分级分类信息
//     *
//     * @param objectPojoTable 数据信息
//     * @param oldTableName
//     * @return
//     */
//    private String updateInsertClassify(ObjectPojoTable objectPojoTable, String oldTableName) {
//        String updateStr = "";
//        try {
//            // 数据组织分类的ids
//            String classIds = objectPojoTable.getClassIds().trim();
//            // 数据来源分类的ids
//            String sourceClassIds = objectPojoTable.getSourceClassIds().trim();
//
//            log.info("先修改对应的表名，如果不同，则修改对应的表名");
//            if (StringUtils.isEmpty(oldTableName)) {
//                oldTableName = objectPojoTable.getRealTablename();
//            }
//            int isExitTable = standardResourceManageDao.getTableorgIsExitDao(oldTableName);
//            if (isExitTable > 0) {
//                int updateCount = standardResourceManageDao.updateTableorgClassifyNameDao(
//                        oldTableName, objectPojoTable.getRealTablename());
//                oldTableName = objectPojoTable.getRealTablename();
//                log.info("表名更新成功");
//            }
//            if (!classIds.equalsIgnoreCase("")) {
//                log.info("开始更新数据组织分类的数据");
//                String[] classifyChList = classIds.split(",");
//                String orgCodevalId = classifyChList[classifyChList.length - 1];
//                log.info("最低一级的分类信息为：" + orgCodevalId);
//                if (isExitTable > 0) {
//                    int updateCount = standardResourceManageDao.updateTableorgClassifyDao(
//                            oldTableName, objectPojoTable.getRealTablename(), objectPojoTable.getDataSourceName(),
//                            orgCodevalId);
//                } else {
//                    int updateCount = standardResourceManageDao.insertTableorgClassifyDao(
//                            objectPojoTable.getRealTablename(), objectPojoTable.getDataSourceName(),
//                            orgCodevalId);
//                }
//                updateStr += "  数据组织分类修改成功";
//            }
//            if (!sourceClassIds.equalsIgnoreCase("")) {
//                log.info("开始更新数据来源分类的数据");
//                isExitTable = standardResourceManageDao.getTableorgIsExitDao(oldTableName);
//                String[] sourceClassIdsList = sourceClassIds.split(",");
//                // 来源分类编码代码
//                String sourceCodevalId = sourceClassIdsList[sourceClassIdsList.length - 1];
//                if (isExitTable > 0) {
//                    int updateCount = standardResourceManageDao.updateTableorgSourceClassifyDao(
//                            oldTableName, objectPojoTable.getRealTablename(), objectPojoTable.getDataSourceName(),
//                            sourceCodevalId);
//                } else {
//                    int updateCount = standardResourceManageDao.insertTableorgSourceClassifyDao(
//                            objectPojoTable.getRealTablename(), objectPojoTable.getDataSourceName(),
//                            sourceCodevalId);
//                }
//                updateStr += "  来源分类修改成功";
//            }
//            // 修改标签库的数据
//            //  标签库不需要了 20200903
////			standardResourceManageDao.updateLabelClassifyDao(oldTableName,objectPojoTable.getLabels());
//
//        } catch (Exception e) {
//            log.error("更新分级分类信息报错" + ExceptionUtil.getExceptionTrace(e));
//            updateStr += "  更新分级分类信息报错" + e.getMessage();
//        }
//        return updateStr;
//    }


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
    @Override
    public ServerResponse<String> addSourceRelationByEtlMessageService(String sourceProtocol, String sourceSystem,
                                                                       String sourceFirm, String tableName, String tableId,
                                                                       String dataCenterTableId, String dataCenterDataId, String centerId, String objMemo, String realTableName) {
        ServerResponse<String> serverResponse = null;
        try {
            // 先判断来源厂商的数字转码是否正常，如果正常，继续
            int sourceFirmNum = ManufacturerName.getIndexByName(sourceFirm);
            if (sourceFirmNum == 400) {
                serverResponse = ServerResponse.asErrorResponse("来源厂商的中文名称错误" + sourceFirm + "没有找到正确的厂商");
                return serverResponse;
            }
            // 先在  STANDARDIZE_INPUTOBJECT 表中查找这个来源协议是否存在，如果存在，直接添加来源关系，没有先创建
            String objGuid = standardResourceManageDao.getObjGuidByTreeParam(sourceProtocol, sourceSystem, sourceFirmNum);
            if (StringUtils.isEmpty(objGuid)) {
                // 在里面插入来源信息 STANDARDIZE_OBJECT
                int insertCount = standardResourceManageDao.insertStandardizeObjectDao(sourceProtocol, sourceSystem,
                        sourceFirmNum, tableName, dataCenterDataId, dataCenterTableId, centerId, objMemo);
                if (insertCount == 0) {
                    serverResponse = ServerResponse.asErrorResponse("向STANDARDIZE_OBJECT表中插入数据失败");
                    return serverResponse;
                }
            }
            String objGuidNew = standardResourceManageDao.getObjGuidByTreeParam(sourceProtocol, sourceSystem, sourceFirmNum);
            // 判断  STANDARDIZE.INPUTOBJECT 表是否存在，存在，不用管，不存在插入
            String inputObjGuid = standardResourceManageDao.getInputGuidById(sourceProtocol, sourceSystem, sourceFirmNum);
            if (StringUtils.isEmpty(inputObjGuid)) {
                //不存在，则插入一条数据
                standardResourceManageDao.insertInputObjectDao(objGuidNew, 0, 1, sourceFirmNum);
            }
            inputObjGuid = standardResourceManageDao.getInputGuidById(sourceProtocol, sourceSystem, sourceFirmNum);
            // 在关系表中，判断 这个来源关系是否存在，如果存在，不用管，如果不存在，则添加对应的值
            int numCount = standardResourceManageDao.getCountSourceRelationByInpuObject(inputObjGuid, tableId);
            if (numCount == 0) {
                String oobjGuid = standardResourceManageDao.getOObjGuidByTableId(tableId);
                if (StringUtils.isEmpty(oobjGuid)) {
                    // 在数据库中插入一条数据并重新获取该值
                    standardResourceManageDao.insertStandardizeObjectDao(tableId, "144", 0,
                            tableName, dataCenterDataId, dataCenterTableId, centerId, objMemo);
                    //获取最新的objectid值  h获取输出协议的 objectGuid
                    String objGuidInsert = standardResourceManageDao.getOutputGuidNotInInput(tableId);
                    // 获取 object表里面的objt_guid
                    standardResourceManageDao.insertOutputObject(objGuidInsert, "<ReserveName>" +
                            "<ResourceNameInFile></ResourceNameInFile>" +
                            "<ResourceNameNoPrefixInFile></ResourceNameNoPrefixInFile>" +
                            "<ResourceNameInPath></ResourceNameInPath>" +
                            "</ReserveName>", 1, 1, "0");

                    oobjGuid = standardResourceManageDao.getOObjGuidByTableId(tableId);
                }
                int insertCount = standardResourceManageDao.insertInputObjectRelate(inputObjGuid, oobjGuid, sourceFirmNum);
                log.info("来源关系数据插入" + insertCount + "数据");
                if (insertCount > 0) {
                    serverResponse = ServerResponse.asSucessResponse("来源关系数据插入成功");
                    return serverResponse;
                } else {
                    serverResponse = ServerResponse.asErrorResponse("来源关系数据插入失败");
                    return serverResponse;
                }
            } else {
                serverResponse = ServerResponse.asErrorResponse("来源关系已经存在，不能重复插入");
                return serverResponse;
            }

        } catch (Exception e) {
            log.error(ExceptionUtil.getExceptionTrace(e));
            serverResponse = ServerResponse.asErrorResponse(e.getMessage());
        }
        return serverResponse;
    }

    /**
     * @param searchInput
     * @param
     * @param tableId
     * @return
     */
    @Override
    public ServerResponse<Object> searchResourceManageObjectField(String searchInput, String searchType, String tableId) {
        // 先根据 tableId获取对应的objectId
        ServerResponse<Object> serverResponse = null;
        try {
            ObjectPojo objectFieldDemo = resourceManageDao.selectObjectPojoByTableId(tableId);
            if (objectFieldDemo == null) {
                serverResponse = ServerResponse.asErrorResponse("表id对应的数据不存在" + tableId);
                return serverResponse;
            }
            List<ObjectField> objectFieldList = resourceManageDao.selectObjectFieldByObjectIdQuery(objectFieldDemo.getObjectId(),
                    searchInput, searchType);
            // 获取代码中文名
            boolean isHailiang = env.getProperty("database.type").equalsIgnoreCase("hailiang");
            for (ObjectField objectField : objectFieldList) {
                String codeText = null;
                String codeId = null;
                List<Map<String, String>> list = new ArrayList<>();
                if (StringUtils.isEmpty(objectField.getFieldId())) {
                    objectField.setCodeText("");
                    objectField.setCodeid("");
                } else {
                    list = resourceManageDao.getCodeTextAndCodeidByObjectField(objectField.getFieldId());
                    if (list.size() != 0) {
                        Map<String, String> map = list.get(0);
                        codeText = isHailiang ? map.get("codetext") : map.get("codeText");
                        codeId = isHailiang ? map.get("codeid") : map.get("codeId");
                        objectField.setCodeText(codeText);
                        objectField.setCodeid(codeId);
                    }
                }
            }
            serverResponse = ServerResponse.asSucessResponse(objectFieldList);

        } catch (Exception e) {
            serverResponse = ServerResponse.asErrorResponse("根据tableId获取字段定义信息报错" + tableId + e.getMessage());
            log.error("根据tableId获取字段定义信息报错" + ExceptionUtil.getExceptionTrace(e));
        }
        return serverResponse;
    }

    @Override
    public ServerResponse<List<LayuiClassifyPojo>> getAllSysList() {
        ServerResponse<List<LayuiClassifyPojo>> serverResponse = null;
        try {
            //查询源应用系统
            List<FieldCodeVal> searchList = resourceManageDao.selectSysObjectSelect();
            List<LayuiClassifyPojo> resultList = new ArrayList<>();
            //筛选出源应用系统一级总共的名称列表
            List<String> primaryChList = searchList.stream().filter(d -> StringUtils.isNotEmpty(d.getCodeText()))
                    .map(d -> (d.getCodeId() + "&&" + d.getCodeText())).distinct().collect(toList());
            //根据出源应用系统一级分组总共有多少数据
            Map<String, List<FieldCodeVal>> primaryListMap = searchList.stream().filter(d -> StringUtils.isNotEmpty(d.getCodeText()))
                    .collect(Collectors.groupingBy(d -> (d.getCodeId() + "&&" + d.getCodeText())));

            for (String data : primaryChList) {
                LayuiClassifyPojo parentPojo = new LayuiClassifyPojo();
                parentPojo.setValue(data.split("&&")[0]);
                parentPojo.setLabel(data.split("&&")[1]);
                //获取当前源应用系统下的数据
                List<FieldCodeVal> childrenList = primaryListMap.get(data);
                List<LayuiClassifyPojo> childrenLayuiList = new ArrayList<>();
                //源应用系统二级数据
                List<String> secondaryList = childrenList.stream().filter(d -> StringUtils.isNotEmpty(d.getValText()))
                        .map(d -> (d.getValValue() + "&&" + d.getValText())).distinct().collect(toList());
//				Map<String ,List<FieldCodeVal>> secondaryListMap = childrenList.stream().filter(d -> StringUtils.isNotEmpty(d.getValText()))
//						.collect(Collectors.groupingBy(d -> (d.getValValue()+"&&"+d.getValText())));
                for (String childrenData : secondaryList) {
                    LayuiClassifyPojo secondaryLayuiClassifyPojo = new LayuiClassifyPojo();
                    secondaryLayuiClassifyPojo.setValue(childrenData.split("&&")[0]);
                    secondaryLayuiClassifyPojo.setLabel(childrenData.split("&&")[1]);
                    childrenLayuiList.add(secondaryLayuiClassifyPojo);
                }
                parentPojo.setChildren(childrenLayuiList);
                resultList.add(parentPojo);
            }

            serverResponse = ServerResponse.asSucessResponse(resultList);
        } catch (Exception e) {
            log.error(ExceptionUtil.getExceptionTrace(e));
            serverResponse = ServerResponse.asErrorResponse(e.getMessage());
        }
        return serverResponse;
    }

    @Override
    public String getDataHandleModelMessageService(String tableId, String sourceId) {
//		String pageUrl = env.getProperty("dataProcessingPage");
//		System.out.println(pageUrl);
        // 20200608 新增页面固定参数
        String pageUrl = env.getProperty("nginxIp") + env.getProperty("dataProcessingPage", "/standardize/emptyRule?");
        System.out.println(pageUrl);
//		ModelAndView modelAndView = new ModelAndView(pageUrl);
//		modelAndView.addObject("identify", null);
        // 先获取输出协议的相关信息
        if (StringUtils.isNotBlank(tableId)) {
            ObjectPojoTable objectPojoTable = selectObjectPojoByTableId(tableId);
            // 输出协议 数据关系来源
//			modelAndView.addObject("outSourceFirmCode", objectPojoTable.getOwnerFactoryCode());
            // 这个没有用
//			modelAndView.addObject("outSourceFirm", objectPojoTable.getOwnerFactory());
//			//这个没哟用
//			modelAndView.addObject("outSourceSystemName",objectPojoTable.getCodeTextCh());
            // 输出协议 目标协议名称
//			modelAndView.addObject("outTableName", tableId);
            //  输出协议 数据协议名
//			modelAndView.addObject("outSourceProtocol", tableId);
            // 输出协议所属系统
//			modelAndView.addObject("outSourceSystem", objectPojoTable.getCodeTextTd());
//			modelAndView.addObject("outSysChiName", objectPojoTable.getCodeTextCh());
            // 20200603  输出协议用中文名称
//            pageUrl = pageUrl+"outTableName="+objectPojoTable.getDataSourceName()+"&outSourceProtocol="
//                    +objectPojoTable.getDataSourceName() +"&outSourceSystem="+
//                    objectPojoTable.getCodeTextTd()+"&outSysChiName="+objectPojoTable.getCodeTextCh();
            pageUrl = pageUrl + "outTableName=" + tableId + "&outSourceProtocol=" + tableId + "&outSourceSystem=" +
                    objectPojoTable.getCodeTextTd() + "&outSysChiName=" + objectPojoTable.getCodeTextCh();
            // 向标准化系统push相关数据
            try {
                pushMetaInfo(tableId);
            } catch (Exception e) {
                log.error("向标准化系统push相关输出协议数据报错：" + ExceptionUtil.getExceptionTrace(e));
            }
        } else {
            pageUrl = pageUrl + "outTableName=&outSourceProtocol=&outSourceSystem=&outSysChiName=";
        }
        if (StringUtils.isNotBlank(sourceId)) {
            List<SourceRelationShip> sourceRelationShipList = getSourceRelationShip(tableId);
            List<SourceRelationShip> sourceRelationShip = sourceRelationShipList.stream()
                    .filter(e -> e.getSourceSystem().equalsIgnoreCase(sourceId)).collect(toList());
            log.info(JSONObject.toJSONString(sourceRelationShip));
//            if(sourceRelationShip.size() == 0){
//				return modelAndView;
//			}
            SourceRelationShip oneSourceRelationShip = sourceRelationShip.get(0);
            // 输入协议所属系统
//			modelAndView.addObject("inSourceSystem",oneSourceRelationShip.getSourceProtocol() );
//			// 输入协议 采集厂商
//			modelAndView.addObject("inSourceFirmCode", ManufacturerName.getIndexByName(oneSourceRelationShip.getSourceFirm()));
//			// 数据协议名
//			modelAndView.addObject("inSourceProtocol", oneSourceRelationShip.getSourceSystem());
//			// 输入协议中文名
//			modelAndView.addObject("inTableName", oneSourceRelationShip.getSourceSystem());
//
//			modelAndView.addObject("inSysChiName", oneSourceRelationShip.getSourceProtocolCh());
            pageUrl = pageUrl + "&inSourceSystem=" + oneSourceRelationShip.getSourceProtocol() + "&inSourceFirmCode=" + ManufacturerName.getIndexByName(oneSourceRelationShip.getSourceFirm())
                    + "&inSourceProtocol=" + oneSourceRelationShip.getSourceSystem() + "&inTableName=" + oneSourceRelationShip.getSourceSystem()
                    + "&inSysChiName=" + oneSourceRelationShip.getSourceProtocolCh();
            try {
                PushMetaInfo pushMetaInfo = new PushMetaInfo();
                pushMetaInfo.setType(0);
                pushMetaInfo.setSys(oneSourceRelationShip.getSourceProtocol());
                pushMetaInfo.setSyscnname(oneSourceRelationShip.getSourceProtocolCh());
                pushMetaInfo.setProtocolengname(oneSourceRelationShip.getSourceSystem());
                pushMetaInfo.setProtocolcnname(oneSourceRelationShip.getDataSourceName());
                pushMetaInfo.setSysengname(oneSourceRelationShip.getSourceProtocol());
                pushMetaInfo.setSource(String.valueOf(ManufacturerName.getIndexByName(oneSourceRelationShip.getSourceFirm())));
                List<PushMetaInfo> list = new ArrayList<>();
                list.add(pushMetaInfo);
                log.info("数据提交标准化管理输入协议的传入参数为：" + JSONObject.toJSONString(list));
                JSONObject responseToPage = restTemplateHandle.requestPushMetaInfo(list);
                log.info("数据提交标准化管理返回参数为：" + JSONObject.toJSONString(responseToPage));
            } catch (Exception e) {
                log.error("向标准化系统push相关输入协议报错：" + ExceptionUtil.getExceptionTrace(e));
            }
        } else {
            pageUrl = pageUrl + "&inSourceSystem=&inSourceFirmCode=&inSourceProtocol=&inTableName=&inSysChiName=";
        }
        return pageUrl;
    }

    @Override
    public ServerResponse<StandardFieldJson> getAllStandardFieldJson(String tableId) {
        ServerResponse<StandardFieldJson> serverResponse = null;
        try {
            StandardFieldJson standardFieldJson = new StandardFieldJson();
            List<StandardFieldJson.StandardField> allColumnToStandardList = new ArrayList<>();
            // 现根据objectId获取对应的所有字段信息
            List<ObjectField> allColumnList = selectObjectFieldByObjectId(tableId);
            if (allColumnList == null) {
                standardFieldJson.setField(null);
            } else {
                for (ObjectField oneColumn : allColumnList) {
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
                    if (StringUtils.isEmpty(oneColumn.getFieldType()) || oneColumn.getFieldType().equals("-1")) {
                        oneColumnStandard.setFieldtype(0);
                    } else {
                        int standardizeNum = StandardizeFieldType.getStandardizeNum(StandardizeFieldType.class, Integer.valueOf(oneColumn.getFieldType()));
                        oneColumnStandard.setFieldtype(standardizeNum);
                    }
                    oneColumnStandard.setPartition(oneColumn.getPartitionRecno()==null ? 0 : oneColumn.getPartitionRecno());
//                    oneColumnStandard.setType(Integer.valueOf(oneColumn.getType()));
                    oneColumnStandard.setNotNull(oneColumn.getNeedValue());
                    allColumnToStandardList.add(oneColumnStandard);
                }
            }
            standardFieldJson.setField(allColumnToStandardList);
            // 然后tableId获取对应的 详细的 数据信息
            ObjectPojoTable oneObjectPojo = selectObjectPojoByTableId(tableId);
            standardFieldJson.setType(1);
            standardFieldJson.setSys(oneObjectPojo.getCodeTextTd());
            standardFieldJson.setSyscnname(oneObjectPojo.getCodeTextCh());
            standardFieldJson.setProtocolengname(oneObjectPojo.getTableId());
            standardFieldJson.setProtocolcnname(oneObjectPojo.getDataSourceName());
            standardFieldJson.setSource(oneObjectPojo.getOwnerFactoryCode());
            standardFieldJson.setTablename(oneObjectPojo.getRealTablename());
            standardFieldJson.setDb(0);
            serverResponse = ServerResponse.asSucessResponse(standardFieldJson);
        } catch (Exception e) {
            serverResponse = ServerResponse.asErrorResponse(e.getMessage());
            log.error(ExceptionUtil.getExceptionTrace(e));
        }
        return serverResponse;
    }

    @Override
    public ServerResponse<String> pushMetaInfo(String tableId) {
        ServerResponse<String> serverResponseReturn = null;
        try {
            ServerResponse<StandardFieldJson> serverResponse = getAllStandardFieldJson(tableId);
            if (serverResponse.getStatus() == 0) {
                serverResponseReturn = ServerResponse.asErrorResponse(serverResponse.getMessage());
                return serverResponseReturn;
            } else {
                StandardFieldJson standardFieldJson = serverResponse.getData();
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
                log.info("数据提交标准化管理传入参数" + JSONObject.toJSONString(list));
                JSONObject responseToPage = restTemplateHandle.requestPushMetaInfo(list);
                log.info("数据提交标准化管理返回参数为：" + JSONObject.toJSONString(responseToPage));
                if (responseToPage != null && responseToPage.getString("status").equalsIgnoreCase("OK")) {
                    serverResponseReturn = ServerResponse.asSucessResponse("数据提交标准化管理成功", "数据提交标准化管理成功");
                } else {
                    if (responseToPage == null) {
                        serverResponseReturn = ServerResponse.asErrorResponse("数据提交标准化管理的接口不存在");
                    } else {
                        serverResponseReturn = ServerResponse.asErrorResponse(responseToPage.getString("error"));
                    }
                }
                return serverResponseReturn;
            }
        } catch (Exception e) {
            log.error("数据提交标准化管理失败" + ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("数据提交标准化管理失败" + e.getMessage());
        }
    }

    @Override
    public void pushSourceMetaInfo(List<SourceRelationShip> sourceRelationShipList) {
        try {
            List<PushMetaInfo> list = new ArrayList<>();
            for (SourceRelationShip sourceRelationShip : sourceRelationShipList) {
                PushMetaInfo pushMetaInfo = new PushMetaInfo();
                pushMetaInfo.setType(0);
                pushMetaInfo.setProtocolcnname(sourceRelationShip.getSourceSystem());
                pushMetaInfo.setProtocolengname(sourceRelationShip.getSourceSystem());
                pushMetaInfo.setSource(sourceRelationShip.getSourceFirm());
                pushMetaInfo.setSys(sourceRelationShip.getSourceProtocol());
                pushMetaInfo.setSysengname(sourceRelationShip.getSourceProtocolCh());
                pushMetaInfo.setSyscnname(sourceRelationShip.getSourceProtocolCh());
                pushMetaInfo.setTablename(sourceRelationShip.getSourceSystem());
                list.add(pushMetaInfo);
            }
            log.info("数据来源提交的传入参数" + JSONObject.toJSONString(list));
            JSONObject responseToPage = restTemplateHandle.requestPushMetaInfo(list);
            log.info("数据提交标准化管理返回参数为：" + JSONObject.toJSONString(responseToPage));
        } catch (Exception e) {
            log.error(ExceptionUtil.getExceptionTrace(e));
        }
    }

//    /**
//     * 获取数据分类信息  分为组织和来源两列
//     *
//     * @param name
//     * @param dataType   1：数据组织  2：数据来源
//     * @param moduleName 第三方模块名称 20200917新增
//     * @return
//     */
//    @Override
//    public List<ZtreeNode> getOrganizationZTreeNodes(String name, int dataType, String moduleName) {
//        List<ZtreeNode> ztreeNodeList = new ArrayList<>();
//        List<StandardTableRelation> standardRelationList = null;
//        try {
//            List<StandardTableRelation> standardRelationList1 = resourceManageDao.getOrganizationZTreeNodes(null == name ? "" : name, dataType);
//            standardRelationList = otherModuleManageServiceImpl.filterZtreeByModuleName(standardRelationList1, moduleName);
//            List<String> parimaryClassList = standardRelationList.stream().map(o -> o.getPrimaryClassifyCh()).distinct().collect(toList());
//            Map<String, List<StandardTableRelation>> parimaryClassMap = standardRelationList.stream().collect(Collectors.groupingBy(StandardTableRelation::getPrimaryClassifyCh));
//            for (String parent : parimaryClassList) {
//                //  一级分类的内容
//                ZtreeNode oneZtreeNode = new ZtreeNode();
//                oneZtreeNode.setId(parent);
//                oneZtreeNode.setpId("root");
//                List<StandardTableRelation> parimaryTowList = parimaryClassMap.get(parent);
//                oneZtreeNode.setName(parent + "(" + String.valueOf(parimaryTowList.size()) + ")");
//                ztreeNodeList.add(oneZtreeNode);
//                List<String> secondaryClassList = parimaryTowList.stream().map(o -> o.getSecondaryClassifyCh()).distinct().collect(toList());
//                Map<String, List<StandardTableRelation>> secondaryClassMap = parimaryTowList.stream().collect(Collectors.groupingBy(StandardTableRelation::getSecondaryClassifyCh));
//                for (String child : secondaryClassList) {
//                    ZtreeNode twoZtreeNode = new ZtreeNode();
//                    twoZtreeNode.setId(parent + "|" + child);
//                    twoZtreeNode.setpId(parent);
//                    List<StandardTableRelation> levelTwoList = secondaryClassMap.get(child);
//                    twoZtreeNode.setName(child + "(" + String.valueOf(levelTwoList.size()) + ")");
//                    if ((child.equalsIgnoreCase("未知") && !parent.equals("未知")) ||
//                            (!parent.equals("未知") && StringUtils.isEmpty(child))) {
//
//                    } else {
//                        ztreeNodeList.add(twoZtreeNode);
//                    }
//                    // 对于三级分类，有些表id存在三级分类，有些表id不存在三级分类，所以需要区分
//                    // 存在三级分类的数据
//                    List<String> threeNotNullClassList = levelTwoList.stream().filter(o -> StringUtils.isNotEmpty(o.getThreeClassifyCh()))
//                            .map(o -> o.getThreeClassifyCh()).distinct().collect(toList());
//                    Map<String, List<StandardTableRelation>> threeClassMap = levelTwoList.stream().filter(o -> StringUtils.isNotEmpty(o.getThreeClassifyCh()))
//                            .collect(Collectors.groupingBy(StandardTableRelation::getThreeClassifyCh));
//                    for (String twoChild : threeNotNullClassList) {
//                        // 三级分类
//                        ZtreeNode threeZtreeNode = new ZtreeNode();
//                        threeZtreeNode.setId(parent + "|" + child + "|" + twoChild);
//                        threeZtreeNode.setpId(parent + "|" + child);
//                        List<StandardTableRelation> levelThreeList = threeClassMap.get(twoChild);
//                        threeZtreeNode.setName(twoChild + "(" + String.valueOf(levelThreeList.size()) + ")");
//                        ztreeNodeList.add(threeZtreeNode);
//                        for (StandardTableRelation s1 : levelThreeList) {
//                            // 以下为 具体的表信息
//                            ZtreeNode tableZtreeNode = new ZtreeNode();
//                            tableZtreeNode.setId(s1.getTableId());
//                            tableZtreeNode.setName(s1.getTableNameCh());
//                            tableZtreeNode.setpId(parent + "|" + child + "|" + twoChild);
//                            tableZtreeNode.setTableNodeFlag(true);
//                            ztreeNodeList.add(tableZtreeNode);
//                        }
//                    }
////					// 存储三级分类是空的数据
//                    for (StandardTableRelation standardTableRelation : levelTwoList) {
//                        if (StringUtils.isEmpty(standardTableRelation.getThreeClassifyCh())) {
//                            ZtreeNode tableZtreeNode = new ZtreeNode();
//                            tableZtreeNode.setId(standardTableRelation.getTableId());
//                            tableZtreeNode.setName(standardTableRelation.getTableNameCh());
//                            tableZtreeNode.setTableNodeFlag(true);
//                            if (!parent.equals("未知") && (child.equalsIgnoreCase("未知")
//                                    || StringUtils.isEmpty(child))) {
//                                tableZtreeNode.setpId(parent);
//                            } else {
//                                tableZtreeNode.setpId(parent + "|" + child);
//                            }
//                            ztreeNodeList.add(tableZtreeNode);
//                        }
//                    }
//                }
//            }
//        } catch (Exception e) {
//            log.error("获取左侧树报错" + ExceptionUtil.getExceptionTrace(e));
//        }
//        log.info("获取左侧树的信息为：" + JSONObject.toJSONString(ztreeNodeList));
//        return ztreeNodeList;
//    }

    /**
     * 提供给vue页面的接口，查询分级分类的tree树
     *
     * @param req
     * @param isQueryTable
     * @return
     */
    @Override
    public List<TreeNodeVue> externalgetTableOrganizationTree(GetTreeReq req, Boolean isQueryTable, Boolean showLabelCount, Boolean showAll) {
        log.info("查询的参数为：" + JSONObject.toJSONString(req) + " 是否精确到表:  " + isQueryTable);
        List<TreeNodeVue> treeNodes = new ArrayList<>();
        List<StandardTableRelation> standardRelationList = new ArrayList<>();
        //  数据组织资产那边需要查询所有的分类 标准和非标准都要
        if (showAll) {
            standardRelationList = resourceManageDao.getOrganizationZTreeNodesAll(req.getNodeName(), req.getType());
        } else {
            standardRelationList = resourceManageDao.getOrganizationZTreeNodes(req.getNodeName(), req.getType());
        }
        List<String> parimaryClassList = standardRelationList.stream().map(o -> o.getPrimaryClassifyCh()).distinct().collect(toList());
        Map<String, List<StandardTableRelation>> parimaryClassMap = standardRelationList.stream().collect(Collectors.groupingBy(StandardTableRelation::getPrimaryClassifyCh));
        for (String parent : parimaryClassList) {
            //  一级分类的内容
            TreeNodeVue levelOne = new TreeNodeVue();
            levelOne.setId(parent);
            List<StandardTableRelation> levelOneList = parimaryClassMap.get(parent);
            if (showLabelCount) {
                levelOne.setLabel(parent + "(" + String.valueOf(levelOneList.size()) + ")");
            } else {
                levelOne.setLabel(parent);
            }
            levelOne.setLevel(1);
            // 一级分类之后的子节点数组
            List<TreeNodeVue> chlidOne = new ArrayList<>();
            List<String> secondaryClassList = levelOneList.stream().filter(d -> StringUtils.isNotEmpty(d.getSecondaryClassifyCh())).map(o -> o.getSecondaryClassifyCh()).distinct().collect(toList());
            Map<String, List<StandardTableRelation>> secondaryClassMap = levelOneList.stream().filter(d -> StringUtils.isNotEmpty(d.getSecondaryClassifyCh())).collect(Collectors.groupingBy(StandardTableRelation::getSecondaryClassifyCh));
            for (String child : secondaryClassList) {
                // 二级分类的数据
                TreeNodeVue levelTwo = new TreeNodeVue();
                levelTwo.setId(child);
                levelTwo.setParent(parent);
                List<StandardTableRelation> levelTwoList = secondaryClassMap.get(child);
                if (showLabelCount) {
                    levelTwo.setLabel(child + "(" + String.valueOf(levelTwoList.size()) + ")");
                } else {
                    levelTwo.setLabel(child);
                }
                levelTwo.setLevel(2);
                List<TreeNodeVue> chlidTwo = new ArrayList<>();
                // 对于三级分类，有些表id存在三级分类，有些表id不存在三级分类，所以需要区分
                // 存在三级分类的数据
                List<String> threeNotNullClassList = levelTwoList.stream().filter(o -> StringUtils.isNotEmpty(o.getThreeClassifyCh()))
                        .map(o -> o.getThreeClassifyCh()).distinct().collect(toList());
                Map<String, List<StandardTableRelation>> threeClassMap = levelTwoList.stream().filter(o -> StringUtils.isNotEmpty(o.getThreeClassifyCh()))
                        .collect(Collectors.groupingBy(StandardTableRelation::getThreeClassifyCh));
                for (String twoChild : threeNotNullClassList) {
                    //  三级分类
                    TreeNodeVue levelThree = new TreeNodeVue();
                    levelThree.setId(twoChild);
                    levelThree.setParent(child);
                    levelThree.setGrandpar(parent);
                    List<StandardTableRelation> levelThreeList = threeClassMap.get(twoChild);
                    if (showLabelCount) {
                        levelThree.setLabel(twoChild + "(" + String.valueOf(levelThreeList.size()) + ")");
                    } else {
                        levelThree.setLabel(twoChild);
                    }
                    levelThree.setLevel(3);
                    List<TreeNodeVue> chlidThree = new ArrayList<>();
                    if (isQueryTable) {
                        for (StandardTableRelation s1 : levelThreeList) {
                            // 以下为 具体的表信息
                            TreeNodeVue tableTreeNode = new TreeNodeVue();
                            tableTreeNode.setId(s1.getTableId());
                            tableTreeNode.setLabel(s1.getTableNameCh());
                            tableTreeNode.setLevel(4);
                            tableTreeNode.setParent(twoChild);
                            chlidThree.add(tableTreeNode);
                        }
                    }
                    levelThree.setChildren(chlidThree);
                    chlidTwo.add(levelThree);
                }
                // 存储三级分类是空的数据
                if (isQueryTable) {
                    for (StandardTableRelation standardTableRelation : levelTwoList) {
                        if (StringUtils.isEmpty(standardTableRelation.getThreeClassifyCh())) {
                            TreeNodeVue levelTwoTable = new TreeNodeVue();
                            levelTwoTable.setId(standardTableRelation.getTableId());
                            levelTwoTable.setLabel(standardTableRelation.getTableNameCh());
                            levelTwoTable.setLevel(3);
                            levelTwoTable.setParent(child);
                            chlidTwo.add(levelTwoTable);
                        }
                    }
                }
                levelTwo.setChildren(chlidTwo);
                chlidOne.add(levelTwo);
            }
            levelOne.setChildren(chlidOne);
            treeNodes.add(levelOne);
        }
        log.info("查询到的数据为：" + JSONObject.toJSONString(treeNodes));
        return treeNodes;
    }

    /**
     * 创建表名的搜索提示框
     * 其中　二级分类可能存在　　其它数据／其它
     *
     * @param mainValue
     * @param firstValue
     * @param secondaryValue
     * @param condition
     * @return
     */
    @Override
    public List<String> createObjectTableSuggestService(String mainValue, String firstValue, String secondaryValue,
                                                        String condition) {
        String threeClassifyValue = "";
        String secondaryClassifyValue = "";
        if (secondaryValue.contains("/")) {
            threeClassifyValue = secondaryValue.split("/")[1];
            secondaryClassifyValue = secondaryValue.split("/")[0];
        } else {
            threeClassifyValue = "";
            secondaryClassifyValue = secondaryValue;
        }
        //  20200224 目前主分类无效 只有数据组织分类一种
        List<String> tableName = resourceManageDao.createObjectTableSuggestDao(mainValue, firstValue, secondaryClassifyValue, threeClassifyValue, condition);
        return tableName;
    }

    /**
     * @param standardObjectManage
     * @return
     */
    @Override
    public String saveResourceFieldRelationService(StandardObjectManage standardObjectManage) throws Exception {
        // 标准表数据信息
        ObjectPojoTable objectPojoTable = standardObjectManage.getObjectPojoTable();
        // 保存数据信息的内容, 20210730 在此处进行加锁操作
        String lockId = StringUtils.isNotBlank(objectPojoTable.getObjectId()) ? objectPojoTable.getObjectId() :
                (StringUtils.isNotBlank(objectPojoTable.getTableId()) ? objectPojoTable.getTableId() : "kong");
        ServerResponse<String> serverResponseOne = null;
        HASH_LOCK.lock(lockId);
        try {
            // 如果有objectid，检查这个对应的编辑时间是否大于等于数据库中的时间，不是，说明是旧数据，不能编辑
            if (StringUtils.isNotBlank(objectPojoTable.getObjectId()) && StringUtils.isNotBlank(objectPojoTable.getUpdateTime())) {
                String updateTimeStrDb = resourceManageAddDao.getUpdateTimeStr(objectPojoTable.getTableId(), objectPojoTable.getUpdateTime());
                if (StringUtils.isNotBlank(updateTimeStrDb)) {
                    throw new Exception("标准协议[" + objectPojoTable.getTableId() + "]已经在" + updateTimeStrDb + "时被修改，本次修改失败，请刷新数据");
                }
            }
            serverResponseOne = saveResourceManageTableService(standardObjectManage);
            if ("Object表未做改动，更新失败".equals(serverResponseOne.getData())) {
                return "Object表未做改动，更新失败";
            }
        } finally {
            HASH_LOCK.unlock(lockId);
        }
        // 保存字段定义的内容
        log.info("保存字段定义的内容...");
        // 获取tableId对应的 objectId值，然后再重新给objectId赋值
        if (serverResponseOne.getStatus() == 1) {
            String objectID = resourceManageAddDao.getObjectIDByTableID(objectPojoTable.getTableId());
            //  先通过对比，发现哪些表字段信息是被删除了的，然后再删除对比出来的字段信息
            // 1: 先获取数据库中所有字段信息
            List<ObjectField> oldObjectFieldList = selectObjectFieldByObjectId(objectPojoTable.getTableId());
            // 2: 页面上字段信息
            List<ObjectField> pageObjectFieldList = standardObjectManage.getObjectFieldList();
            //获取字段名称列表
            List<String> pageColumnList = pageObjectFieldList.stream().filter(e -> StringUtils.isNotEmpty(e.getColumnName()))
                    .map(e -> e.getColumnName().toUpperCase()).distinct().collect(Collectors.toList());

            // 3:存储需要删除的字段信息
            if (oldObjectFieldList != null){
                for (ObjectField objectField : oldObjectFieldList) {
                    if (StringUtils.isNotEmpty(objectField.getColumnName()) &&
                            !pageColumnList.contains(objectField.getColumnName().toUpperCase())) {
                        // 4:调用方法删除字段信息
                        deleteObjectField(Long.valueOf(objectID), objectField.getColumnName());
                    }
                }
            }

            // 20210623  字段顺序 是否为聚集列 是否为主键列 是否参与MD5运算  按照页面的顺序来更新对应的数字
            StandardColumnUtil.setColumnRecon(pageObjectFieldList);
            List<ObjectFieldStandard> realObjectFieldList = new ArrayList<>();

            //遍历列表，查询fieldId是否有重复
            //1.筛选出fieldId已经存在的表字段，再进行内部遍历看是否有重复的fieldId
            List<ObjectField> oldFieldList = pageObjectFieldList.stream().filter(e -> e.getUpdateStatus() != 1).collect(toList());
            if (oldFieldList != null && oldFieldList.size() > 0) {
                //2.重复的fieldId计数
                Map<String, Long> repetitionCount = oldFieldList.stream().collect(Collectors.groupingBy(ObjectField::getFieldId, Collectors.counting()));
                //3.筛出有重复的fieldId的列表
                List<String> repetitionList = repetitionCount.keySet().stream().filter(key -> repetitionCount.get(key) > 1).collect(toList());
                if (!repetitionList.isEmpty()) {
                    throw new Exception("FieldId重复：" + repetitionList.toString());
                }
                //4.将旧字段添加到新的列表中，旧的字段fieldId保持不变
                for (ObjectField data : oldFieldList) {
                    data.setObjectId(Long.valueOf(objectID));
                    ObjectFieldStandard objectFieldStandard = new ObjectFieldStandard();
                    BeanUtils.copyProperties(objectFieldStandard, data);
                    realObjectFieldList.add(objectFieldStandard);
                }
            }

            //处理新增的表字段
            List<ObjectField> addObjectFieldList = pageObjectFieldList.stream().filter(e -> e.getUpdateStatus() == 1).collect(toList());
            if (!addObjectFieldList.isEmpty()) {
//                // fieldId拼接改为由前端完成
//                //新增的数据项当限定词不为空时，fieldid由限定词标识+_+数据元field组合而成
//                addObjectFieldList.stream().forEach(d -> {
//                    if (StringUtils.isNotBlank(d.getDeterminerId())) {
//                        // bug修复
//                        String fieldId = d.getFieldId().split("_")[d.getFieldId().split("_").length -1];
//                        d.setFieldId(d.getDeterminerId() + "_" + fieldId);
//                    }
//                });
                //赋值完fieldId后，遍历是否有相同的fieldId
                addObjectFieldList.stream().forEach(d -> {
                    addObjectFieldList.stream().forEach(e -> {
                        if (!d.getColumnName().equalsIgnoreCase(e.getColumnName()) && d.getFieldId().equalsIgnoreCase(e.getFieldId())) {
                            throw new RuntimeException("字段中有相同的数据项唯一编码，无法保存");
                        }
                    });
                });
                //4.拿新字段与旧字段比较是否有重复的fieldId
                for (ObjectFieldStandard data : realObjectFieldList) {
                    addObjectFieldList.stream().forEach(e -> {
                        if (data.getFieldId().equalsIgnoreCase(e.getFieldId())) {
                            throw new RuntimeException(e.getColumnName() + "字段中有重复的元素编码，无法保存");
                        }
                    });
                }
                //5.将新增的字段添加到需要更新或是筛选的列表中
                for (ObjectField data : addObjectFieldList) {
//					if(data.getUpdateStatus() != 0){
                    data.setObjectId(Long.valueOf(objectID));
                    data.setNeedValue(0);
                    ObjectFieldStandard objectFieldStandard = new ObjectFieldStandard();
                    BeanUtils.copyProperties(objectFieldStandard, data);
                    realObjectFieldList.add(objectFieldStandard);
//					}
                }
            }
            //判断fieldId是否有重复的，无重复的之后再存储
            int recno = 1;
            for (ObjectFieldStandard data : realObjectFieldList) {
                data.setUpdater(objectPojoTable.getUpdater());
                data.setCreator(objectPojoTable.getUpdater());
                realObjectFieldList.stream().forEach(d -> {
                    if (StringUtils.isNoneEmpty(d.getColumnName()) && StringUtils.isNotEmpty(d.getFieldId())){
                        if ( !d.getColumnName().equalsIgnoreCase(data.getColumnName())) {
                            if (d.getFieldId().equalsIgnoreCase(data.getFieldId())) {
                                throw new RuntimeException(data.getColumnName() + "该字段在表已有重复的元素编码(fieldId),无法保存");
                            }
                        }
                    }
                });
                ServerResponse<String> one = saveObjectField(data,recno);
                recno++;
                log.info("objectId为：" + objectID + "的字段保存结果为：" + JSONObject.toJSONString(one));
                // 如果 字段的分类信息不为空，则 更新字段的相关信息
                resourceManageAddServiceImpl.updateFieldClass(data);
            }
        } else {
            throw new NullPointerException("保存数据信息报错：" + serverResponseOne.getMessage());
        }

        if (serverResponseOne.getStatus() == 1
                && standardObjectManage.getDataRelationMapping() != null
                && standardObjectManage.getDataRelationMapping()
                && StringUtils.isNotBlank(standardObjectManage.getObjectRelationManage().getStandardObjectName()) ) {
            //仅限于原始汇聚类数据库才保存数据集对标内容
            saveObjectRelationService(standardObjectManage.getObjectRelationManage(),objectPojoTable);
        }

        // 保存来源关系的数据
        List<SourceRelationShip> pageSourceRelationShipList = standardObjectManage.getSourceRelationShipList();

        //将原始来源关系存入sourceInfo表中
        if(pageSourceRelationShipList.size() != 0){
            SourceRelationShip realSourceRelationShip = pageSourceRelationShipList.get(0);
            int sourceInfoIsExist = resourceManageAddDao.searchSourceInfoIsExist(realSourceRelationShip.getSourceProtocol(), realSourceRelationShip.getDataSourceName(),
                    realSourceRelationShip.getResourceId());
            if(sourceInfoIsExist == 0){
                SourceInfo sourceInfo = SourceInfo.getInstance(realSourceRelationShip.getSourceProtocol(), realSourceRelationShip.getRealTableName(),
                        realSourceRelationShip.getSourceSystem(), realSourceRelationShip.getSourceFirm(), realSourceRelationShip.getDataName(),
                        realSourceRelationShip.getResourceId(), realSourceRelationShip.getProject(), realSourceRelationShip.getCenterName(),
                        realSourceRelationShip.getCenterId());
                resourceManageAddDao.addSourceInfo(sourceInfo);
            }
        }

        List<String> pageRelationStringList = pageSourceRelationShipList.stream().filter(e -> (
                StringUtils.isNotEmpty(e.getSourceSystem()) && StringUtils.isNotEmpty(e.getSourceProtocol()))).distinct().map(e ->
                (e.getSourceSystem().toUpperCase() + "&&" + e.getSourceProtocol()))
                .collect(toList());
        // 1:先获取数据库中已经存在的来源关系
        if(pageRelationStringList.size() != 0){
            List<SourceRelationShip> oldSourceRelationShipList = getSourceRelationShip(objectPojoTable.getTableId());
            List<String> oldRelationStringList = new ArrayList<>();
            if (oldSourceRelationShipList != null) {
                oldRelationStringList = oldSourceRelationShipList.stream().filter(e -> (
                        StringUtils.isNotEmpty(e.getSourceSystem()) && StringUtils.isNotEmpty(e.getSourceProtocol()))).distinct().map(e ->
                        (e.getSourceSystem().toUpperCase() + "&&" + e.getSourceProtocol()))
                        .collect(toList());
            }
            // 2:判断哪些是不存在的
            List<SourceRelationShip> delSourceRelation = new ArrayList<>();
            for (SourceRelationShip sourceRelationShip : oldSourceRelationShipList) {
                String name = sourceRelationShip.getSourceSystem().toUpperCase() + "&&" + sourceRelationShip.getSourceProtocol();
                if (!pageRelationStringList.contains(name)) {
                    delSourceRelation.add(sourceRelationShip);
                }
            }
            //3：删除需要删除的来源关系
            if (!delSourceRelation.isEmpty()) {
                ServerResponse<String> deleteRelationMsg = deleteSourceRelationService(delSourceRelation, objectPojoTable.getTableId());
                if (!deleteRelationMsg.isSuccess()) {
                    throw new Exception("来源关系删除失败，请重新处理来源关系，报错原因：" + serverResponseOne.getMessage());
                }
            } else {
                log.info("不需要删除来源关系");
            }
            // 4：添加新的来源关系
            for (SourceRelationShip pageSource : pageSourceRelationShipList) {
                String name = pageSource.getSourceSystem().toUpperCase() + "&&" + pageSource.getSourceProtocol();
                if (!oldRelationStringList.contains(name)) {
                    pageSource.setRealTableName(objectPojoTable.getRealTablename());
                    // 添加新的来源关系
                    if (pageSource.getAddType().equalsIgnoreCase(SourceRelationShip.ORGANIZATIONAL)) {
                        addSourceRelationByTableNameService(pageSource.getRealTableName(),
                                "organizational",
                                pageSource.getSourceId(),
                                pageSource.getSourceSystem(),
                                pageSource.getSourceFirm(),
                                StringUtils.isNotBlank(pageSource.getTableNameCN()) ? pageSource.getTableNameCN() : pageSource.getDataName(),
                                pageSource.getResType(),
                                objectPojoTable);
                    } else {
                        //  20200603   getRealTableName 换成中文名  getDataSourceName
                        addSourceRelationByEtlMessageService(pageSource.getSourceId(),
                                pageSource.getSourceSystem(),
                                pageSource.getSourceFirm(),
                                pageSource.getRealTableName(),
                                objectPojoTable.getTableId(),
                                pageSource.getTableId(),
                                pageSource.getDataId(),
                                pageSource.getCenterId(),
                                StringUtils.isNotBlank(pageSource.getTableNameCN()) ? pageSource.getTableNameCN() : pageSource.getDataName(),
                                objectPojoTable.getRealTablename());
                    }
                }
            }
        }

        return "标准表数据保存成功";
    }

//    /**
//     * 向流程审批那边发送审批流程  返回一个审批流程中开始的 url路径
//     *
//     * @param standardObjectManage
//     * @return
//     * @throws Exception
//     */
//    @Override
//    public String saveOrUpdateApprovalInfoService(StandardObjectManage standardObjectManage) throws Exception {
//        String approvalId = standardObjectManage.getApprovalId();
//        ApprovalInfoParams approvalInfoParams = new ApprovalInfoParams();
//        approvalInfoParams.setApprovalId(approvalId);
//        if (StringUtils.isBlank(approvalId)) {
//            approvalInfoParams.setOperationName("新增标准表");
//            approvalInfoParams.setModuleName("标准管理");
//        }
//        LoginUser user = AuthorizedUserUtils.getInstance().getAuthor();
//        if (user != null) {
//            standardObjectManage.setUser(user);
//        }
//        approvalInfoParams.setModuleId(ApprovalInfoParams.STANDARD_TABLE);
//        approvalInfoParams.setApplicationInfo("新增数据名为：" + standardObjectManage.getObjectPojoTable().getDataSourceName()
//                + " tableId为：" + standardObjectManage.getObjectPojoTable().getTableId() + "的标准表");
//        approvalInfoParams.setCallbackData(JSONObject.toJSONString(standardObjectManage));
//        approvalInfoParams.setCallbackUrl(env.getProperty("nginxIp") + "/factorygateway/datastandardmanager/dataStandardManager/createObjectByApprovalInfo");
//
//        log.info("往审批流程发送的数据为：" + JSONObject.toJSONString(approvalInfoParams));
//        JSONObject returnObject = restTemplateHandle.saveOrUpdateApprovalInfo(approvalInfoParams);
//
//        log.info("审批流程返回的数据为：" + JSONObject.toJSONString(JSONObject.toJSONString(returnObject)));
//        if (returnObject.getInteger("status") == 1) {
//            // 表示调用审批流程成功
//            approvalId = returnObject.getString("result");
//        } else {
//            throw new Exception(returnObject.getString("message"));
//        }
//        // 审批流程的页面url
//        String approvalInfoHtmlUrl = env.getProperty("nginxIp") + "/factorygateway/datagovernance/datagovernance/process/approval?approvalId=" + approvalId;
//        return approvalInfoHtmlUrl;
//    }

    /**
     * @param fieldId
     * @param fieldName
     * @return
     * @throws Exception
     */
    @Override
    public String getIsExitsFiledMessageService(String fieldId, String fieldName) throws Exception {
        String message = "";
        if (StringUtils.isEmpty(fieldId)) {
            throw new Exception("fieldId值为空，不是标准库中的元素编码");
        }
        if (StringUtils.isEmpty(fieldName)) {
            throw new Exception("fieldName值为空，不是标准库中的标准列名");
        }
        int fieldIdCount = resourceManageAddDao.getCountByFiledId(fieldId);
        int fieldNameCount = resourceManageAddDao.getCountByFiledName(fieldName);
        if (fieldIdCount == 0) {
            message = "元素编码错误： 【" + fieldId + "】不是标准库中的元素编码,不能保存";
            throw new Exception(message);
        }
        if (fieldNameCount == 0) {
            message += "标准列名错误：【" + fieldName + "】不是标准库中的标准列名,不能保存";
            throw new Exception(message);
        }
        log.info(message);
        return message;
    }

    /**
     * 检查tableId是否为唯一的
     *
     * @param standardObjectManage
     * @return
     * @throws Exception
     */
    @Override
    public Boolean checkTableIdSourceIdIsExistsService(StandardObjectManage standardObjectManage, boolean switchFlag) throws Exception {
        try {
            log.info("【检查tableId是否是唯一的数据】");
            // 新增标准协议, 存在正在审批的情况 还需要从这个接口查询数据
            if (StringUtils.isEmpty(standardObjectManage.getTableId()) || StringUtils.isEmpty(standardObjectManage.getObjectPojoTable().getTableId())) {
                throw new Exception("tableId为空，不能保存该标准表信息");
            }
            String objectId = resourceManageAddDao.getObjectIDByTableID(standardObjectManage.getTableId());
            if (StringUtils.isEmpty(standardObjectManage.getObjectId())) {
                if (StringUtils.isNotEmpty(objectId)) {
                    // 如果tableid重复，则tableid流水号自增1
                    String newTableId = getNewTableId(standardObjectManage.getTableId());
                    standardObjectManage.setTableId(newTableId);
                    standardObjectManage.getObjectPojoTable().setTableId(newTableId);
                    checkTableIdSourceIdIsExistsService(standardObjectManage, switchFlag);
//                throw new Exception("tableId已经存在，请更换tableId值");
                }
            } else {
                //编辑标准表
                if (StringUtils.isNotEmpty(objectId)) {
                    if (!standardObjectManage.getObjectId().equalsIgnoreCase(objectId)) {
                        throw new Exception("该tableId已经被使用，请更换tableId值");
                    }
                }
            }

//        // 判断审批中是否存在 同表名/ 同tableId
//        if (switchFlag) {
//            List<ApprovalInfoParams> list = restTemplateHandle.queryApprovalInfoForModule("1", "standardTable");
//            if (list != null && list.size() > 0) {
//                Long countAllTableName = list.stream().filter(d -> StringUtils.isNotEmpty(d.getCallbackData()))
//                        .map(d -> {
//                            try {
//                                StandardObjectManage objectManage = JSONObject.parseObject(d.getCallbackData(), StandardObjectManage.class);
//                                String realTableName = objectManage.getObjectPojoTable().getRealTablename();
//                                if (standardObjectManage.getObjectPojoTable().getRealTablename().equalsIgnoreCase(realTableName)) {
//                                    return objectManage;
//                                } else {
//                                    return null;
//                                }
//                            } catch (Exception e) {
//                                log.error("" + ExceptionUtil.getExceptionTrace(e));
//                                return null;
//                            }
//                        }).filter(Objects::nonNull).count();
//                if (countAllTableName > 0) {
//                    throw new Exception("真实表名对应的标准表正在审批中，请修改真实表名(真实表名重复)");
//                }
//                Long countAllTableId = list.stream().filter(d -> StringUtils.isNotEmpty(d.getCallbackData()))
//                        .map(d -> {
//                            try {
//                                StandardObjectManage objectManage = JSONObject.parseObject(d.getCallbackData(), StandardObjectManage.class);
//                                // tableId 的值为   GA_RESOURCE_5位数据来源代码_6位行政区划_5位流水号
//                                String tableId = objectManage.getTableId();
//                                if (standardObjectManage.getTableId().equalsIgnoreCase(tableId)) {
//                                    return objectManage;
//                                } else {
//                                    return null;
//                                }
//                            } catch (Exception e) {
//                                log.error("" + ExceptionUtil.getExceptionTrace(e));
//                                return null;
//                            }
//                        }).filter(Objects::nonNull).count();
//                if (countAllTableId > 0) {
//                    throw new Exception("tableId对应的标准表正在审批中，请更换对应的tableId(或重新点击保存按钮)");
//                }
//            }
//        }
            // 判断tableId 是否符合要求
            return false;
        }catch (Exception e){
            log.error("检查tableId是否为唯一出错：\n" + ExceptionUtil.getExceptionTrace(e));
            return false;
        }
    }

    /**
     * tableId自增1
     * @return
     */
    public String getNewTableId(String oldTableId){
        String[] tableIdArr = oldTableId.split("_");
        Integer tableidNum = Integer.parseInt(tableIdArr[tableIdArr.length -1]);
        String tableidNumMax = String.format("%05d",tableidNum + 1);
        tableIdArr[tableIdArr.length -1] = tableidNumMax;
        String newTableId = "";
        for (String e : tableIdArr){
            if (StringUtils.isNotBlank(newTableId)){
                newTableId = newTableId + "_" + e;
            }else {
                newTableId = e;
            }
        }
        return newTableId;
    }

    /**
     * 数据组织分类选择选择原始库之后 获取对应的tableId值
     * 规则为   GA_RESOURCE_5位数据来源代码_6位行政区划_5位流水号
     *
     * @param dataSourceClassify 数据来源分类的中文值
     * @param code               数据协议的6位行政区划代码
     * @return
     * @throws Exception
     */
    @Override
    public String getOrganizationTableIdService(String dataSourceClassify, String code) throws Exception {
        // 如果code为null，则获取默认行政区划
        if (StringUtils.isBlank(code)){
            code = getDefaultXZQH();
        }
        // 根据数据来源分类信息（中文） 获取分类的代码值
        String[] sourceList = dataSourceClassify.split("\\/");
        String tableIdStr = "GA_RESOURCE_";
        String secondClass = "";
        // 如果dataSourceClassify为代码则不用查询数据库
        if (sourceList.length == 1 || isNumeric(dataSourceClassify)){
            secondClass = dataSourceClassify;
        } else if (sourceList.length > 1) {
            secondClass = resourceManageAddDao.findCheckTableNameImformationThree(sourceList[1], 2);
        } else {
            secondClass = resourceManageAddDao.findCheckTableNameImformationThree(sourceList[0], 1);
        }
        tableIdStr = tableIdStr + secondClass;
        tableIdStr = tableIdStr + "_" + code;
        // 5位流水号 从 00001 开始
        final String classMesaage = secondClass + "_" + code;
        // 获取数据库中的所有tableId的情况
        List<String> tableIdList = resourceManageAddDao.getAllTableIdDao();
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
        int dataBaseMaxId = maxId.orElse(0);

        Boolean switchFlag = switchHashMap.getOrDefault("approvalInfo", true);
        List<ApprovalInfoParams> approvalInfoParams = null;
        if (switchFlag) {
            approvalInfoParams = restTemplateHandle.queryApprovalInfoForModule("1", "standardTable");
        }
        if (approvalInfoParams != null && approvalInfoParams.size() > 0) {
            OptionalInt standardObjectManagesSize = approvalInfoParams.stream().filter(d -> StringUtils.isNotEmpty(d.getCallbackData()))
                    .mapToInt(d -> {
                        try {
                            StandardObjectManage objectManage = JSONObject.parseObject(d.getCallbackData(), StandardObjectManage.class);
                            // tableId 的值为   GA_RESOURCE_5位数据来源代码_6位行政区划_5位流水号
                            String[] tableIds = objectManage.getTableId().split("\\_");
                            if (tableIds.length == 5 && (tableIds[2] + "_" + tableIds[3]).equalsIgnoreCase(classMesaage)) {
                                if (StringUtils.isNumeric(tableIds[4])) {
                                    return Integer.valueOf(tableIds[4]);
                                } else {
                                    return -1;
                                }
                            } else {
                                return -1;
                            }
                        } catch (Exception e) {
                            log.error("" + ExceptionUtil.getExceptionTrace(e));
                            return -1;
                        }
                    }).filter(d -> d != -1).max();
            int approvalMaxId = standardObjectManagesSize.orElse(0);
            DecimalFormat format = new DecimalFormat("00000");
            tableIdStr = tableIdStr + "_" + format.format(approvalMaxId > dataBaseMaxId ? (approvalMaxId + 1) : (dataBaseMaxId + 1));
        } else {
            DecimalFormat format = new DecimalFormat("00000");
            tableIdStr = tableIdStr + "_" + format.format(dataBaseMaxId + 1);
        }
        System.gc();
        return tableIdStr;
    }

    @Override
    public String getDefaultXZQH(){
        String defaultXZQH = env.getProperty("defaultUnitCode");
        return defaultXZQH;
    }

    public static boolean isNumeric(String str){
        for (int i = 0; i < str.length(); i++){
            if (!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }

    /**
     * 获取跳转到表登记的url信息
     *
     * @param tableName
     * @param tableId
     * @param objectId
     * @return
     * @throws Exception
     */
    @Override
    public String getTableRegisterUrlService(String tableName, String tableId, String objectId) throws Exception {
        // 主要传递以下参数
        // projectName:'',//项目名称  tableNameCh: '',//表中文名  orgCode: '',//最小层级组织分类编码  orgCodeCascader: '',//组织分类级联编码,分隔
        //  sourceCode: '',//最小层级来源分类编码   sourceCodeCascader: '',//来源分类级联编码,分隔  tableName: '',//表英文名
        // 先根据objectId获取对应的数据信息
        ObjectPojoTable objectPojoTable = selectObjectPojoByTableId(tableId);
//        String tableRegisterRootUrl = env.getProperty("tableRegisterUrl");
        // 20200608 页面地址更改
        String tableRegisterRootUrl = env.getProperty("nginxIp") + "/register/tableRegister";

        String[] tableNameList = tableName.split("\\|");
        StringBuffer urlString = new StringBuffer();
        // 获取配置中心中 表登记的url信息   http://10.1.8.101:1000/register/tableRegister&title=表登记管理
        urlString.append(tableRegisterRootUrl);
        // 在阿里云平台中 1表示是odps 2：表示是ads  在华为云平台中 1：hive  2：hbase等其它库
        int dataBaseFlag = 1;
        if (tableNameList.length == 2) {
            if (StringUtils.isEmpty(tableNameList[0])) {
                throw new Exception("表名为【" + tableName + "】中数据仓库类型为空，不能进行表登记操作");
            } else if ("odps".equalsIgnoreCase(tableNameList[0]) || "ads".equalsIgnoreCase(tableNameList[0])) {
                urlString.append("?platformType=阿里平台");
                if ("odps".equalsIgnoreCase(tableNameList[0])) {
                    dataBaseFlag = 1;
                } else {
                    dataBaseFlag = 2;
                }
            } else {
                urlString.append("?platformType=华为平台");
                if ("hive".equalsIgnoreCase(tableNameList[0])) {
                    dataBaseFlag = 1;
                } else {
                    dataBaseFlag = 2;
                }
            }
            if (StringUtils.isEmpty(tableNameList[1]) || ".".equalsIgnoreCase(tableNameList[1]) || !tableNameList[1].contains(".")) {
                throw new Exception("表名为【" + tableName + "】中表名为空，不能进行表登记操作");
            } else {
                String projectName = tableNameList[1].split("\\.")[0];
                String tableNameEn = tableNameList[1].split("\\.")[1];
                urlString.append("&projectName=").append(projectName).append("&tableName=").append(tableNameEn);
            }
            // 数据集群名称
            urlString.append("&clusterName=").append(tableNameList[0].toUpperCase());
        } else {
            throw new Exception("表名为【" + tableName + "】不符合规范，不能进行表登记操作");
        }
        // 表中文名
        urlString.append("&tableNameCh=").append(objectPojoTable.getDataSourceName());
        // 表状态
        if (objectPojoTable.getStorageTableStatus().equalsIgnoreCase("正式使用")) {
            urlString.append("&isFormal=").append("2");
        } else {
            urlString.append("&isFormal=").append("1");
        }
        // 更新方式
        urlString.append("&updateType=").append("1".equals(objectPojoTable.getIsActiveTable()) ? "1" : "2");
        // 是否为标准表
        urlString.append("&isStandard=").append("1");
        // standardCode: '',//协议编码
        urlString.append("&standardCode=").append(tableId);
        //labelOneList: '',//主体标签,分隔
        //  标签的值为 20200903 标签不再需要
//		urlString.append("&labelOneList=").append(objectPojoTable.getBodyTag1Val());
//		//          labelTwoList: '',//要素标签,分隔
//		urlString.append("&labelTwoList=").append(objectPojoTable.getElementTag2Val());
//		//          labelThreeList: '',//对象描述标签,分隔
//		urlString.append("&labelThreeList=").append(objectPojoTable.getObjectDescTag3Val());
//		//          labelFourList: '',//行为标签,分隔
//		urlString.append("&labelFourList=").append(objectPojoTable.getBehaviorTag4Val());
//		//          labelFiveList: '',//关联关系标签,分隔
//		urlString.append("&labelFiveList=").append(objectPojoTable.getRelationShipTag5Val());
//		//          labelSixList: ''//位置标签,分隔
//		urlString.append("&labelSixList=").append(objectPojoTable.getLocationTag6Val());
        // 获取组织分类编码 来源分类的编码 目前是中文，要根据中文转成代码
        ObjectPojoTable classifyOne = resourceManageDao.getClassifyIdByTableid(tableId);
        if (classifyOne != null) {
            //组织分类级联编码,分隔
            urlString.append("&orgCodeCascader=").append(classifyOne.getOrganizationClassify());
            //  来源分类级联编码,分隔
            urlString.append("&sourceCodeCascader=").append(classifyOne.getSourceClassify());
        } else {
            urlString.append("&orgCodeCascader=").append("");
            urlString.append("&sourceCodeCascader=").append("");
        }
        // 分区字段名称，根据objectId 直接查询表里面的分区字段，如果没有为空 如果有多个 则用 英文逗号分隔
        List<String> columnList = resourceManageDao.getColumnNameByObjectId(objectId);
        if (columnList != null && columnList.size() > 0) {
            if (dataBaseFlag == 1 && columnList.contains("DT")) {
                urlString.append("&partitionField=").append("DT");
            } else {
                urlString.append("&partitionField=").append(StringUtils.join(columnList, ","));
            }
        }
        //建表时间，直接查询数据库表 standard_table_created 建表时间存在空格
//        String createTime = resourceManageDao.getCreateTimeByTableName(tableName);
        String createTime = tableOrganizationDao.getcreatedTimeStrByTableName(tableNameList[1]);
        urlString.append("&createTime=").append(createTime);
        // 20200603 拼接 数据中心名和数据源名称
//		String allString = resourceManageDao.getDataCenterNameByTableName(tableName);
        // 20210613 从数据仓库中获取建表信息
        List<DetectedTable> list = restTemplateHandle.getTableImformationList();
        Optional<DetectedTable> data = list.stream().filter(d -> d != null && StringUtils.equalsIgnoreCase(d.getProjectName() + "."
                + d.getTableNameEN(), tableNameList[1])).findFirst();
        if (data.isPresent()) {
            urlString.append("&dataCenter=").append(StringUtils.isBlank(data.get().getCenterName()) ? "" : data.get().getCenterName());
            urlString.append("&dataResource=").append(StringUtils.isBlank(data.get().getResName()) ? "" : data.get().getResName());
        }
        list = null;
        // 调用 dataGovernanceUrl
//		String dataGovernanceUrl = env.getProperty("dataGovernanceWebUrl");
        String dataGovernanceUrl = env.getProperty("nginxIp") + "/factorygateway/datagovernance/datagovernance/index.html";
        if (StringUtils.isEmpty(dataGovernanceUrl)) {
            log.error("配置中心的参数dataGovernanceWebUrl为空，获取跳转页面url报错");
            throw new Exception("配置中心的参数dataGovernanceWebUrl为空，获取跳转页面url报错");
        }
        String tableRegisterUrl = dataGovernanceUrl + "?src=" + URLEncoder.encode(urlString.toString()) + "&title=表登记";
        log.info("调用的url为：" + urlString.toString());
        log.info("总的url为：" + tableRegisterUrl);
        return tableRegisterUrl;
    }

    @Override
    public String updateObjectStatus(String tableId) {
        if (tableId.isEmpty()) {
            log.error("传递的tableId参数为空");
        }
        int num = resourceManageDao.updateObjectStatus(tableId);
        if (num > 0) {
            return "object状态更新成功";
        }
        return "状态更新失败";
    }

    @Override
    public DetectedTable getDetectedTableInfo(String resId, String project, String tableName) {
        if(StringUtils.isBlank(resId) && StringUtils.isBlank(project) && StringUtils.isBlank(tableName)){
            throw SystemException.asSystemException(ErrorCode.CHECK_PARAMETER_ERROR, "调用仓库接口的参数为空");
        }
        DetectedTable tableDetectInfo = null;
        try {
            tableDetectInfo = restTemplateHandle.getTableDetectInfo(resId, project, tableName);
            //根据二级去码表回填一级
            FieldCodeVal fieldCodeVal = resourceManageDao.selectOneSysName(tableDetectInfo.getAppCode());
            tableDetectInfo.setParentAppCode(fieldCodeVal.getCodeId());
            log.info("返回的信息为：" + JSONObject.toJSONString(tableDetectInfo));
        } catch (Exception e) {
            tableDetectInfo = null;
            log.error("从数据仓库调用接口获取表的探查信息报错:" + ExceptionUtil.getExceptionTrace(e));
        }
        return tableDetectInfo;
    }

}
