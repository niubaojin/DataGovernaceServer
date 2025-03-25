package com.synway.datastandardmanager.controller;


import com.alibaba.fastjson.JSONObject;
import com.synway.datastandardmanager.annotation.Resubmit;
import com.synway.datastandardmanager.dao.master.ResourceManageAddDao;
import com.synway.datastandardmanager.pojo.*;
import com.synway.datastandardmanager.pojo.ExternalInterfce.TableAllInfo;
import com.synway.datastandardmanager.pojo.approvalInfo.ApprovalInfoParams;
import com.synway.datastandardmanager.pojo.enums.ManufacturerName;
import com.synway.datastandardmanager.pojo.warehouse.DetectedTable;
import com.synway.datastandardmanager.pojo.warehouse.FieldInfo;
import com.synway.datastandardmanager.pojo.warehouse.ProjectInfo;
import com.synway.datastandardmanager.service.ResourceManageService;
import com.synway.datastandardmanager.util.ExceptionUtil;
import com.synway.datastandardmanager.util.RestTemplateHandle;
import com.synway.common.bean.ServerResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Controller
public class ResourceManageController {
    private Logger logger = LoggerFactory.getLogger(ResourceManageController.class);

    @Autowired
    ResourceManageService resourceManageServiceImpl;
    @Autowired()
    private Environment env;

    @Autowired
    RestTemplateHandle restTemplateHandle;

    @Autowired
    private ResourceManageAddDao resourceManageAddDao;

    @Autowired
    ConcurrentHashMap<String, Boolean> switchHashMap;


    @RequestMapping(value = "/resourceManageObjectField")
    @ResponseBody
    public ServerResponse<List<ObjectField>> resourceManageObjectField(@RequestParam("tableId") String tableId) {
        return ServerResponse.asSucessResponse(resourceManageServiceImpl.selectObjectFieldByObjectId(tableId));
    }


    /**
     * 字段定义的搜索框 搜索按钮
     *
     * @param searchInput 输入的内容
     * @param searchType
     * @param tableId     表id
     * @return
     */
    @RequestMapping(value = "/searchResourceManageObjectField")
    @ResponseBody
    public ServerResponse<Object> searchResourceManageObjectField(String searchInput, String searchType, String tableId) {
        logger.info("传入的参数为：searchInput:" + searchInput + " searchType:" + searchType + " tableId:" + tableId);
        return resourceManageServiceImpl.searchResourceManageObjectField(searchInput, searchType, tableId);
    }


    /**
     * 根据表的tableId号获取最新的数据信息
     * 20191014发生修改
     */
    @RequestMapping(value = "/resourceManageObjectDetail", produces = "application/json;charset=utf-8")
    @ResponseBody
    public ServerResponse<Object> resourceManageObjectDetail(String tableId) {
        ObjectPojoTable objectPojoTable = resourceManageServiceImpl.selectObjectPojoByTableId(tableId);
        return ServerResponse.asSucessResponse(objectPojoTable);
    }

    /**
     * 根据objectId和fieldName删除标准字段
     *
     * @param objectId  标准id
     * @param fieldName 字段英文名
     */
    @RequestMapping(value = "/deleteObjectField")
    @ResponseBody
    public ServerResponse<String> deleteObjectField(Long objectId, String fieldName) {
        logger.info("开始删除objectId：" + objectId + "的字段columnName：" + fieldName);
        ServerResponse<String> result = resourceManageServiceImpl.deleteObjectField(objectId, fieldName);
        logger.info("===================删除结束===================");
        return result;
    }

    /**
     * 获取来源关系的相关信息
     *
     * @param tableId 标准协议编码
     * @return
     */
    @RequestMapping("/sourceRelationShipDataGet")
    @ResponseBody
    public ServerResponse<List<SourceRelationShip>> sourceRelationShipDataGet(String tableId) {
        List<SourceRelationShip> sourceRelationShipList = null;
        try {
            sourceRelationShipList = resourceManageServiceImpl.getSourceRelationShip(tableId);
        } catch (Exception e) {
            logger.error("调用刷新接口报错" + ExceptionUtil.getExceptionTrace(e));
        }
        return ServerResponse.asSucessResponse(sourceRelationShipList);
    }

    //------------------------ 模态框的相关代码--------------------

    /**
     * 新增来源关系中根据大类的id号获取一级分类信息
     *
     * @param mainValue '1':组织分类 '2':来源分类   '3'：资源分类
     * @return
     */
    @RequestMapping("/getFirstClassModeByMain")
    @ResponseBody
    public ServerResponse getFirstClassModeByMain(String mainValue) {
        List<PageSelectOneValue> pageSelectOneValueList = null;
        try {
            pageSelectOneValueList = resourceManageServiceImpl.getFirstClassModeByMainService(mainValue);
            return ServerResponse.asSucessResponse(pageSelectOneValueList);
        } catch (Exception e) {
            logger.error("根据大类的id号获取一级分类信息", e);
            return ServerResponse.asErrorResponse("获取组织分类异常："+e.getMessage());
        }
    }

    /**
     * 根据大类id和一级分类的名称获取二级级分类信息
     *
     * @param mainValue       '1':组织分类 '2':来源分类   '3'：资源分类
     * @param firstClassValue
     * @return
     */
    @RequestMapping("/getSecondaryClassModeByFirst")
    @ResponseBody
    public ServerResponse<List<PageSelectOneValue>> getSecondaryClassModeByFirst(String mainValue, String firstClassValue) {
        try {
            List<PageSelectOneValue> pageSelectOneValueList = resourceManageServiceImpl.getSecondaryClassModeByFirstService(mainValue, firstClassValue);
            return ServerResponse.asSucessResponse(pageSelectOneValueList);
        } catch (Exception e) {
            logger.error("获取二级分类信息报错：", e);
            return ServerResponse.asErrorResponse("获取二级分类信息报错：" + e.getMessage());
        }
    }

    /**
     * http://192.168.71.96:8043/DataResource/getAllTableName 获取数据源下所有表
     * 参数:getAllTableName(String dataId, String queryName,boolean isFresh)
     * dataId（上个返回结构二级Id）queryName 传"" ,isFresh 传false
     */
    @RequestMapping("/getAllTableNameByDataBaseId")
    @ResponseBody
    public ServerResponse<List<PageSelectOneValue>> getAllTableName(String dataId, String projectName) {
        List<PageSelectOneValue> resultPageList = new ArrayList<>();
        try {
            DetectedTable[] result = restTemplateHandle.
                    requestGetAllTableName(dataId, projectName);
            for (DetectedTable oneZtreeNode : result) {
                PageSelectOneValue oneValue = new PageSelectOneValue(oneZtreeNode.getTableNameEN(), oneZtreeNode.getTableNameCN());
                resultPageList.add(oneValue);
            }
            return ServerResponse.asSucessResponse(resultPageList);
        } catch (Exception e) {
            logger.error("获取表名的提示信息报错",  e);
            return ServerResponse.asErrorResponse("获取表名的提示信息报错"+e.getMessage());
        }
    }

    /**
     * 添加新的数据来源关系，当从organizational中获取时，先在标准表中找到这个表名对应的相关信息，然后插入到数据库中
     * 如果是从 database中获取，从数据仓库接口获取对应的数据
     *
     * @param tableName 传入的表名
     * @param addType   添加方式  organizational：组织资产   database：数据仓库获取(来源数据)
     * @return
     */
    @RequestMapping("/addSourceRelationByTableName")
    @ResponseBody
    public ServerResponse<String> addSourceRelationByTableName(String tableName, String addType,
                                                               String outputTableId, String sourceSystem,
                                                               String sourceFirm,String dataId) {
        ServerResponse<String> resultMessage = null;
        try {
            System.out.println(tableName);
            logger.info(tableName);
            resultMessage = resourceManageServiceImpl.addSourceRelationByTableNameService(
                    tableName, addType, outputTableId, sourceSystem, sourceFirm, "",dataId, new ObjectPojoTable());
        } catch (Exception e) {
            logger.error("添加新的来源关系报错" + ExceptionUtil.getExceptionTrace(e));
            resultMessage = ServerResponse.asErrorResponse("添加新的来源关系报错" + e.getMessage());
        }
        return resultMessage;
    }


    /**
     * 删除选定的来源关系
     *
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "/deleteSourceRelation", method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse<String> deleteSourceRelation(@RequestBody JSONObject jsonObject) {
        ServerResponse<String> resultMessage = null;
        try {
            String outputDataId = jsonObject.getString("outputDataId");
            List<SourceRelationShip> delSourceRelation = jsonObject.getJSONArray("delSourceRelation").toJavaList(SourceRelationShip.class);
            logger.info("=============开始删除表ID为：" + outputDataId + "的来源关系:" + JSONObject.toJSONString(delSourceRelation));
            resultMessage = resourceManageServiceImpl.deleteSourceRelationService(delSourceRelation, outputDataId);

        } catch (Exception e) {
            logger.error("删除来源关系报错" + ExceptionUtil.getExceptionTrace(e));
            resultMessage = ServerResponse.asErrorResponse("删除来源关系报错" + e.getMessage());
        }
        return resultMessage;
    }

    /**
     * 创建添加字段模态框中的搜索查询框
     *
     * @param type      类型 fieldId：搜索元素编码  columnName：表字段名
     * @param condition 搜索查询框
     * @return
     */
    @RequestMapping(value = "/createAddColumnModel")
    @ResponseBody
    public ServerResponse<List<OneSuggestValue>> createAddColumnModel(String type, String condition) {
        BootstrapReturnObject resultObject = null;
        try {
            logger.info("查询的字段信息为,type: " + type + " condition: " + condition);
            if (StringUtils.isEmpty(condition)) {
                return ServerResponse.asSucessResponse();
            }
//            resultObject = "{\"message\": \"\",\"value\": [{\"name\": \"测试字段1232121\",\"value\":" +
//					" \"CHU_RU_PING_AN_EDFDDFDDS\",\"id\": 20001}],\"code\": 200,\"redirect\": \"\"}";
            return resourceManageServiceImpl.createAddColumnModelService(type, condition);
        } catch (Exception e) {
            logger.error("建添加字段模态框中的搜索查询框" + ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("查询异常："+ e.getMessage());
        }
    }

    /**
     * 创建添加字段模态框中的搜索查询框
     * 新建标准字段时，标准列名改变或者唯一标识改变会调改接口
     *
     * @param type       类型 fieldId：搜索元素编码  columnName：表字段名
     * @param inputValue 输入框中填入的值
     * @return
     */
    @RequestMapping(value = "/getAddColumnByInput")
    @ResponseBody
    public ServerResponse<Synltefield> getAddColumnByInput(String type, String inputValue) {
        ServerResponse<Synltefield> synltefieldServerResponse = null;
        try {
            logger.info("查询的字段信息为,type: " + type + " inputValue: " + inputValue);
            if (StringUtils.isEmpty(inputValue)){
                return ServerResponse.asSucessResponse("请输入查询的标准列名");
            }
            synltefieldServerResponse = resourceManageServiceImpl.getAddColumnByInputService(type, inputValue);
            logger.info("返回的结果为：" + JSONObject.toJSONString(synltefieldServerResponse));
        } catch (Exception e) {
            logger.error("建添加字段模态框中的搜索查询框" + ExceptionUtil.getExceptionTrace(e));
        }
        return synltefieldServerResponse;
    }

    /**
     * 保存数据信息
     *
     * @return
     * @ 20200225 新增需求，一次性保存 数据信息/字段定义/来源关系 三种数据
     * 20220201 保存数据定义/字段定义/数据集对标 三种信息
     */
    @RequestMapping(value = "/saveResourceManageTable")
    @ResponseBody
    @Resubmit(delaySeconds = 1)
    public ServerResponse<Map<String, String>> saveResourceManageTable(@RequestBody @Valid StandardObjectManage standardObjectManage) throws Exception {
        ServerResponse<Map<String, String>> serverResponse;
        Map<String, String> returnMap = new HashMap<>();

        // 验证 tableId 和sourceId的唯一性, 如果 objectId 是空，则判断 tableId是否已经存在，如果objectId不为空，也要验证
        resourceManageServiceImpl.checkTableIdSourceIdIsExistsService(standardObjectManage, false);
        String returnMsg = resourceManageServiceImpl.saveResourceFieldRelationService(standardObjectManage);
        // 向标准化程序提交修改之后的数据
        String pushResult = "";
        String isPushMetaInfo = env.getProperty("isPushMetaInfo");
        if (StringUtils.isNotBlank(returnMsg) && returnMsg.contains("成功") && isPushMetaInfo.equalsIgnoreCase("true")){
            ServerResponse<String> serverResponseReturn = resourceManageServiceImpl.pushMetaInfo(standardObjectManage.getTableId());
            pushResult = serverResponseReturn.getStatus() == 1 ? "，推送标准化成功" : "，推送标准化失败:具体原因请查看日志";
            logger.info("向标准化push数据的结果为：" + JSONObject.toJSONString(serverResponseReturn));
        }
        returnMap.put("approvalInfo", "false");
        returnMap.put("message", returnMsg + pushResult);
        returnMap.put("tableId", standardObjectManage.getTableId());
        serverResponse = ServerResponse.asSucessResponse(returnMap);
        logger.info("保存标准返回的数据为：" + JSONObject.toJSONString(serverResponse));

        return serverResponse;
    }

    /**
     * 根据表英文名、项目空间、数据源ID 来获取表结构信息
     */
    @RequestMapping(value = "/getTableInfoForStandard")
    @ResponseBody
    public ServerResponse<DetectedTable> getTableInfoForStandard(String resId, String project, String tableName) {
        DetectedTable detectedTableInfo = resourceManageServiceImpl.getDetectedTableInfo(resId, project, tableName);
        return ServerResponse.asSucessResponse(detectedTableInfo);
    }

    /**
     * 获取源应用系统名称下拉列表
     * @return
     */
    @RequestMapping(value = "/getSysToSelect")
    @ResponseBody
    public ServerResponse<List<LayuiClassifyPojo>> getSysToSelect() {
        logger.info("开始获取sys表中的数据");
        ServerResponse<List<LayuiClassifyPojo>> pageSelectOneValueList = null;
        try {
            pageSelectOneValueList = resourceManageServiceImpl.getAllSysList();
        } catch (Exception e) {
            pageSelectOneValueList = ServerResponse.asErrorResponse(e.getMessage());
            logger.error(ExceptionUtil.getExceptionTrace(e));
        }
        logger.info("sys表返回的值为：" + JSONObject.toJSONString(pageSelectOneValueList));
        return pageSelectOneValueList;
    }

    //
    @RequestMapping(value = "/getAllStandardFieldJson")
    @ResponseBody
    public ServerResponse<StandardFieldJson> getAllStandardFieldJson(String tableId) {
        logger.info("获取对应数据传给数据处理流程来使用,tableId为" + tableId);
        ServerResponse<StandardFieldJson> serverResponse = resourceManageServiceImpl.getAllStandardFieldJson(tableId);
        logger.info("获取对应数据传给数据处理流程的数据为：" + JSONObject.toJSONString(serverResponse));
        return serverResponse;
    }

    //  根据dataId获取数据仓库那边的数据
    //  数据仓库那边跳转过来，带上一个tableid，然后这边反查，获取查询到的值
    @RequestMapping(value = "/getDataResourceInformation")
    @ResponseBody
    public ServerResponse<DataResourceRawInformation> getDataResourceInformation(String dataId, String project, String tableName) {
        ServerResponse<DataResourceRawInformation> serverResponse = null;
        try {
            logger.info("需要获取的dataId为：" + dataId + ", project:" + project + ", tableName:" + tableName);
            DetectedTable tableDetectInfo = restTemplateHandle.getTableDetectInfo(dataId, project, tableName);
            logger.info("信息tableDetectInfo为:" + tableDetectInfo);

            logger.info("开始调用仓库的getResourceById获取数据源信息");
            DataResource dataResourceInfo = restTemplateHandle.getResourceById(dataId);
            logger.info("调用仓库getResourceById接口结束,结果为:{}", dataResourceInfo);
            if (dataResourceInfo != null) {
                //回填数据中心名称
                tableDetectInfo.setCenterId(dataResourceInfo.getCenterId());
                tableDetectInfo.setCenterName(dataResourceInfo.getCenterName());
                //回填数据源信息
                tableDetectInfo.setResName(dataResourceInfo.getResName());
                tableDetectInfo.setResType(dataResourceInfo.getResType());
            }

            DataResourceRawInformation dataResourceRawInformation = new DataResourceRawInformation();
            if (tableDetectInfo != null) {
                //获取对应表结构信息
                List<FieldInfo> fieldInfos = restTemplateHandle.requestGetTableStructure(dataId, project, tableName);
                TableAllInfo tableAllInfo = new TableAllInfo();

                tableAllInfo.setResourceTable(tableDetectInfo);

                tableAllInfo.setTableFields(fieldInfos);

                if (tableAllInfo == null) {
                    logger.error("返回的数据为空");
                    serverResponse = ServerResponse.asErrorResponse("从数据仓库返回的数据为空");
                    return serverResponse;
                }
                if (StringUtils.isEmpty(tableAllInfo.getResourceTable().getAppName())) {
                    dataResourceRawInformation.setObjectId("");
                    dataResourceRawInformation.setTableNameObject("");
                } else {
                    String objectID = resourceManageAddDao.getObjectIDByTableID(tableAllInfo.getResourceTable().getSourceCode());
                    if (StringUtils.isNotEmpty(objectID)) {
                        // 根据tableId获取object表中的　objectId值
                        dataResourceRawInformation.setObjectId(objectID);
                        // 如果objectId 存在，则查询出数据库中的真实表名
                        String tableNameObject = resourceManageAddDao.getTableNameByObjectId(objectID);
                        dataResourceRawInformation.setTableNameObject(tableNameObject);
                    } else {
                        dataResourceRawInformation.setObjectId("");
                        dataResourceRawInformation.setTableNameObject("");
                    }
                }
                // 20200518 新增一个字段信息 新增数据源来源
                dataResourceRawInformation.setDataResourceOriginIds(tableAllInfo.getResourceTable().getSourceClassify());
                if (StringUtils.isNotEmpty(dataResourceRawInformation.getDataResourceOriginIds())) {
                    // 如果数据来源不是空，则需要查询数据来源分类的中文信息
                    try {
                        PageSelectOneValue rDataResourceOrigin = resourceManageAddDao.getDataRDataResourceOrigin(dataResourceRawInformation.getDataResourceOriginIds());
                        dataResourceRawInformation.setDataResourceOrigin(rDataResourceOrigin.getLabel());
                        dataResourceRawInformation.setDataResourceOriginIds(rDataResourceOrigin.getValue());

                    } catch (Exception e) {
                        logger.error("来源分类报错" + ExceptionUtil.getExceptionTrace(e));
                    }
                }
                dataResourceRawInformation.setDataSourceName(tableAllInfo.getResourceTable().getTableNameEN());
                dataResourceRawInformation.setSourceId(tableAllInfo.getResourceTable().getSourceCode());
                dataResourceRawInformation.setResName(tableAllInfo.getResourceTable().getResName());
                dataResourceRawInformation.setResType(tableAllInfo.getResourceTable().getResType());
                dataResourceRawInformation.setCenterId(tableAllInfo.getResourceTable().getCenterId());
                dataResourceRawInformation.setCenterName(tableAllInfo.getResourceTable().getCenterName());

                // 20200402 所属单位改成这个字段
                if (StringUtils.isEmpty(tableAllInfo.getResourceTable().getAppManageUnit())) {
                    dataResourceRawInformation.setSourceFirmCh("全部");
                    dataResourceRawInformation.setSourceFirmCode("0");
                } else {
                    dataResourceRawInformation.setSourceFirmCh(tableAllInfo.getResourceTable().getAppManageUnit());
                    dataResourceRawInformation.setSourceFirmCode(String.valueOf(ManufacturerName.getIndexByName((tableAllInfo.getResourceTable().getAppManageUnit()))));
                }
                //  所属应用系统中文名/代码
                dataResourceRawInformation.setSourceProtocolCh(tableAllInfo.getResourceTable().getAppName());
                dataResourceRawInformation.setSourceProtocolCode(tableAllInfo.getResourceTable().getAppCode());
                dataResourceRawInformation.setSourceTableName(tableAllInfo.getResourceTable().getProjectName() + "." + tableAllInfo.getResourceTable().getTableNameEN());
                dataResourceRawInformation.setDataBaseId(dataId);
                dataResourceRawInformation.setProject(tableAllInfo.getResourceTable().getProjectName());
                // 数据安全分级
                dataResourceRawInformation.setDataLevel(tableAllInfo.getResourceTable().getDataLevel());
                dataResourceRawInformation.setTableNameCh(tableAllInfo.getResourceTable().getTableNameCN());

                List<FieldInfo> tableFieldList = tableAllInfo.getTableFields();
                List<DataResourceRawInformation.FieldColumn> columnList = new ArrayList<>();
                if (tableFieldList != null && tableFieldList.size() > 0) {
                    for (FieldInfo tableField : tableFieldList) {
                        DataResourceRawInformation.FieldColumn oneFieldColumn = new DataResourceRawInformation.FieldColumn();
                        oneFieldColumn.setFieldName(tableField.getFieldName());
                        oneFieldColumn.setFieldDescription(tableField.getComments());
                        oneFieldColumn.setFieldType(tableField.getType());
                        oneFieldColumn.setiFieldType("");
                        oneFieldColumn.setFieldLength(String.valueOf(tableField.getLength()));
                        oneFieldColumn.setIsNonnull(String.valueOf(tableField.getNullAble()));
                        oneFieldColumn.setIsPrimarykey(String.valueOf(tableField.getIsPrimaryKey()));
                        columnList.add(oneFieldColumn);
                    }
                    dataResourceRawInformation.setFieldList(columnList);
                } else {
                    dataResourceRawInformation.setFieldList(columnList);
                }
                serverResponse = ServerResponse.asSucessResponse(dataResourceRawInformation);
            } else {
                serverResponse = ServerResponse.asErrorResponse("从数据仓库获取数据报错");
            }

        } catch (Exception e) {
            logger.error(ExceptionUtil.getExceptionTrace(e));
            serverResponse = ServerResponse.asErrorResponse(e.getMessage());
        }
        logger.info("返回的数据为：" + JSONObject.toJSONString(serverResponse));
        return serverResponse;
    }


    /**
     * 转发请求到标准系统
     * @param tableId  目标表tableID
     * @param sourceId 来源表id
     * @return
     */
    @RequestMapping(value = "/requestDataHandle")
    @ResponseBody
    public ServerResponse<String[]> requestDataHandle(String tableId, String sourceId) {
        logger.info("跳转时传入的参数为：" + tableId + sourceId);
        String[] list = new String[2];
        list[0] = env.getProperty("nginxIp") + "/factorygateway/datagovernance/datagovernance/index.html";
        String modelUrlString = resourceManageServiceImpl.getDataHandleModelMessageService(tableId, sourceId);
        list[1] = modelUrlString;
        return ServerResponse.asSucessResponse(list);
    }

    /**
     * 每次修改标准表相关数据之后，都要调用标准化平台的修改接口
     */
    @RequestMapping(value = "/pushMetaInfoController")
    @ResponseBody
    public ServerResponse<String> pushMetaInfoController(String tableId) {
        logger.info("数据提交标准化管理传入的参数为：" + tableId);
        ServerResponse<String> serverResponseReturn = resourceManageServiceImpl.pushMetaInfo(tableId);
        logger.info("返回的数据为：" + JSONObject.toJSONString(serverResponseReturn));
        return serverResponseReturn;
    }

    @RequestMapping(value = "/createObjectTableSuggest")
    @ResponseBody
    public ServerResponse<List<String>> createObjectTableSuggest(@RequestParam("mainValue") String mainValue,
                                                 @RequestParam("firstValue") String firstValue,
                                                 @RequestParam("secondaryValue") String secondaryValue,
                                                 @RequestParam("condition") String condition) {
        List<String> tableNameList = null;
        try {
            logger.info("输入的参数为" + mainValue + " " + firstValue + " " + secondaryValue + " " + condition);
            tableNameList = resourceManageServiceImpl.createObjectTableSuggestService(mainValue, firstValue, secondaryValue, condition);
            return ServerResponse.asSucessResponse(tableNameList);
        } catch (Exception e) {
            logger.error("获取表名的提示信息报错", e);
            return ServerResponse.asErrorResponse("获取表名的提示信息报错:"+e.getMessage());
        }
    }

    @RequestMapping(value = "/getResourceManageByApprovalId")
    @ResponseBody
    public ServerResponse<StandardObjectManage> getResourceManageByApprovalIdController(
            @RequestParam("approvalId") String approvalId) {
        logger.info("传入的参数为，approvalId：" + approvalId);
        ServerResponse<StandardObjectManage> serverResponse = null;
        try {
            JSONObject approvalInfoJson = restTemplateHandle.getApprovalDetail(approvalId);
            logger.info("返回的信息为：" + JSONObject.toJSONString(approvalInfoJson));
            Integer status = approvalInfoJson.getInteger("status");
            if (status == 1) {
                ApprovalInfoParams approvalInfoParams = approvalInfoJson.getObject("result", ApprovalInfoParams.class);
                String callBackStr = approvalInfoParams.getCallbackData();
                StandardObjectManage standardObjectManage = JSONObject.parseObject(callBackStr, StandardObjectManage.class);
                standardObjectManage.setApprovalId(approvalInfoParams.getApprovalId());
                standardObjectManage.setStatus(approvalInfoParams.getStatus());
                serverResponse = ServerResponse.asSucessResponse(standardObjectManage);
            } else {
                serverResponse = ServerResponse.asErrorResponse(approvalInfoJson.getString("message"));
            }
        } catch (Exception e) {
            serverResponse = ServerResponse.asErrorResponse(e.getMessage());
            logger.error(ExceptionUtil.getExceptionTrace(e));
        }
        return serverResponse;
    }

    /**
     * 从数据仓库中获取项目名  审批通过的项目名
     * 通过dataId
     */
    @RequestMapping(value = "/getSchemaApproved")
    @ResponseBody
    public ServerResponse<List<String>> getSchemaApproved(String dataId) {
        ArrayList<String> list = new ArrayList<>();
        try {
            logger.info("查询的参数为：" + dataId);
            List<ProjectInfo> result = restTemplateHandle.getProjectList(dataId);
            for (ProjectInfo data : result) {
                list.add(data.getProjectName());
            }
//            list.add("test");
        } catch (Exception e) {
            ServerResponse.asErrorResponse();
        }
        return ServerResponse.asSucessResponse(list);
    }

    /**
     * 获取表名信息
     *
     * @param dataId
     * @param type   数据源类型
     */
    @RequestMapping(value = "/getTableNameApprovaed")
    @ResponseBody
    public ServerResponse<List<PageSelectOneValue>> getTableNameApprovaed(String dataId, String type) {
        logger.info("查询的参数为：" + dataId);
        List<PageSelectOneValue> result = new ArrayList<>();
        try {
            //获取所有表信息
            List<DetectedTable> resourceTableList = restTemplateHandle.getTableImformationList();
            for (DetectedTable oneResourceTable : resourceTableList) {
                if (oneResourceTable.getResType().equalsIgnoreCase(type)) {
                    PageSelectOneValue oneValue = new PageSelectOneValue(oneResourceTable.getProjectName(), oneResourceTable.getTableNameEN(),
                            oneResourceTable.getResId());
                    result.add(oneValue);
                }
            }
            //过滤前100条记录
            result = result.parallelStream().limit(100).collect(Collectors.toList());
            return ServerResponse.asSucessResponse(result);
        } catch (Exception e) {
            logger.error("获取项目名报错：", e);
            return ServerResponse.asErrorResponse("获取项目名报错：" + e.getMessage());
        }
    }

    /**
     * 新增来源关系时，选择数据仓库，获取已探查英文表名接口
     * @param resId       数据源ID
     * @param projectName 项目空间名
     * @param type        数据源类型
     */
    @RequestMapping(value = "/getDetectedTablesNameInfo")
    @ResponseBody
    public ServerResponse<List<PageSelectOneValue>> getDetectedTablesNameInfo(String resId, String projectName, String type) {
        logger.info("新增来源关系时，查询的参数为:resId={},projectName={},type={}", resId, projectName, type);
        List<PageSelectOneValue> list = new ArrayList<>();
        try {
            //调用仓库接口
            List<DetectedTable> detectedTableList = restTemplateHandle.getDetectedTables(resId, projectName);
            for (DetectedTable oneResourceTable : detectedTableList) {
                //过滤当前数据源类型的已探查表信息
                if (oneResourceTable.getResType().equalsIgnoreCase(type)) {
                    PageSelectOneValue oneValue = new PageSelectOneValue(oneResourceTable.getProjectName(), oneResourceTable.getTableNameEN(),
                            oneResourceTable.getResId());
                    list.add(oneValue);
                }
            }
            logger.debug("获取已探查表信息结束，结果为{}", list);
            return ServerResponse.asSucessResponse(list);
        } catch (Exception e) {
            logger.error("获取已探查表信息出错", e);
            return ServerResponse.asErrorResponse("获取已探查表信息出错:" + e.getMessage());
        }
    }

    @RequestMapping(value = "/getPageUrl")
    @ResponseBody
    public ServerResponse getOrtherPageUrl() {
        String[] list = new String[2];
        try {
            // 20200608  页面统一通过gateway跳转
//			String dataGovernanceWebUrl = env.getProperty("dataGovernanceWebUrl","");
            String dataGovernanceWebUrl = env.getProperty("nginxIp") + "/factorygateway/datagovernance/datagovernance/index.html";
//			String url = "http://"+env.getProperty("client.address","")+"/dataStandardManager/resourceManage";
            String url = env.getProperty("nginxIp") + "/governance/resourceManage";
            list[0] = dataGovernanceWebUrl;
            list[1] = url;
            return ServerResponse.asSucessResponse(list);
        } catch (Exception e) {
            logger.error("接口gePageUrl异常：", e);
            return ServerResponse.asErrorResponse("接口[getPageUrl]异常:"+e.getMessage());
        }
    }

    /**
     *
     */
    @RequestMapping(value = "/getIsExitsFiledMessage")
    @ResponseBody
    public ServerResponse<String> getIsExitsFiledMessage(String fieldId, String fieldName) {
        logger.info("判断是否为标准字段信息 fieldId：" + fieldId + " fieldName:" + fieldName);
        String mesaage = "";
        ServerResponse<String> stringServerResponse = null;
        try {
            mesaage = resourceManageServiceImpl.getIsExitsFiledMessageService(fieldId, fieldName);
            stringServerResponse = ServerResponse.asSucessResponse(mesaage);
        } catch (Exception e) {
            logger.error("判断标准字段是否正确报错：" + ExceptionUtil.getExceptionTrace(e));
            stringServerResponse = ServerResponse.asErrorResponse("判断标准字段是否正确报错：" + e.getMessage());
        }
        return stringServerResponse;
    }


    /**
     * 数据组织分类选择选择原始库之后 获取对应的tableId值
     *
     * @param dataSourceClassify
     * @param code
     * @return
     */
    @RequestMapping(value = "/getOrganizationTableId")
    @ResponseBody
    public ServerResponse<String> getOrganizationTableId(String dataSourceClassify, String code) {
        logger.info("原始库数据获取tableId的信息 dataSourceClassify：" + dataSourceClassify + " 6位行政区划代码:" + code);
        String message = "";
        ServerResponse<String> stringServerResponse = null;
        try {
            message = resourceManageServiceImpl.getOrganizationTableIdService(dataSourceClassify, code);
            stringServerResponse = ServerResponse.asSucessResponse(message, message);
        } catch (Exception e) {
            logger.error("原始库数据获取tableId的信息报错：" + ExceptionUtil.getExceptionTrace(e));
            stringServerResponse = ServerResponse.asErrorResponse("原始库数据获取tableId的信息报错：" + e.getMessage());
        }
        return stringServerResponse;
    }

    /**
     * 获取默认行政区划代码
     * @return
     */
    @RequestMapping(value = "/getDefaultXZQH")
    @ResponseBody
    public ServerResponse<String> getDefaultXZQH() {
        String message = "";
        ServerResponse<String> stringServerResponse = null;
        try {
            message = resourceManageServiceImpl.getDefaultXZQH();
            stringServerResponse = ServerResponse.asSucessResponse(message, message);
        } catch (Exception e) {
            logger.error("获取默认行政区划代码报错：\n" + ExceptionUtil.getExceptionTrace(e));
            stringServerResponse = ServerResponse.asErrorResponse("获取默认行政区划代码报错：" + e.getMessage());
        }
        return stringServerResponse;
    }

    /**
     * 获取资产详情页面的相关url信息
     *
     * @param standardTableCreated
     * @return
     */
    @RequestMapping(value = "/getOrganizationDetailUrl")
    @ResponseBody
    public ServerResponse<String> getOrganizationDetailUrl(StandardTableCreated standardTableCreated) {
        ServerResponse<String> serverResponse = null;
        String registerState = "";
        try {
            logger.info("开始获取注册状态");
            DetectedTable tableDetectInfo = restTemplateHandle.getTableDetectInfo(standardTableCreated.getDataId(),
                    standardTableCreated.getTableProject(),
                    standardTableCreated.getTableName());
            if (tableDetectInfo == null) {
                throw new Exception("从仓库获取表的探查信息出错");
            }
            int isRegistered = tableDetectInfo.getIsRegistered();
            registerState = String.valueOf(isRegistered);
        } catch (Exception e) {
            logger.error(e.getMessage());
            serverResponse = ServerResponse.asErrorResponse(e.getMessage());
        }

        try {
            logger.info("跳转到数据资产详情的数据信息为：\n" + standardTableCreated);
            String url = "";
            String nginxIp = env.getProperty("nginxIp", "");
            if (StringUtils.isEmpty(nginxIp)) {
                throw new NullPointerException("配置中心中参数nginxIp为空");
            }
            if (standardTableCreated.getTableNameCh() == null) {
                standardTableCreated.setTableNameCh("");
            }
            url = nginxIp + "/governance/dataGovernance/dataAssets/organizationDetail?";
            url = url + "tableId=" + standardTableCreated.getTableId()
                    + "&objectId" + standardTableCreated.getObjectId()
                    + "&tableType=" + standardTableCreated.getTableBase() + "&tableNameEn=" + standardTableCreated.getTableName()
                    + "&tableProject=" + standardTableCreated.getTableProject() + "&tableNameCh=" + standardTableCreated.getTableNameCh()
                    + "&objectId=" + standardTableCreated.getObjectId() + "&resourceId=" + standardTableCreated.getDataId();
            serverResponse = ServerResponse.asSucessResponse(registerState, url);
            logger.info("跳转的url为: \n" + url);
        } catch (Exception e) {
            logger.error(e.getMessage());
            serverResponse = ServerResponse.asErrorResponse(e.getMessage());
        }
        return serverResponse;
    }

    /**
     * 获取数据中心名
     *
     * @return
     */
    @RequestMapping(value = "/getDataCenter")
    @ResponseBody
//    @IgnoreSecurity
    public ServerResponse<List<PageSelectOneValue>> getDataCenter() {
        try {
            List<DataResource> dataCenter = restTemplateHandle.getDataCenter("0");
            if (dataCenter == null) {
                logger.error("查询仓库数据中心出错");
            }
            ArrayList<PageSelectOneValue> list = new ArrayList<>();
            for (DataResource data : dataCenter) {
                list.add(new PageSelectOneValue(data.getCenterId(), data.getCenterName()));
            }
            return ServerResponse.asSucessResponse(list);
        } catch (Exception e) {
            logger.error("查询仓库数据中心出错" + ExceptionUtil.getExceptionTrace(e));
        }
        return null;
    }


    /**
     * 根据数据中心id获取所有的数据源信息
     *
     * @param centerId 数据中心id
     * @return
     */
    @RequestMapping(value = "/getDataResourceNameByCenterId")
    @ResponseBody
//    @IgnoreSecurity
    public ServerResponse<List<PageSelectOneValue>> getDataResourceNameByCenterId(@RequestParam("centerId") String centerId,
                                                                                  @RequestParam("type") String type) {
        try {
            if (StringUtils.isBlank(centerId) || StringUtils.isBlank(type)) {
                logger.error("根据数据中心获取数据源时传递的参数为空");
                return ServerResponse.asSucessResponse(new ArrayList<>());
            }
            List<DataResource> dataResourcesList = restTemplateHandle.getDataResourceByCenterId(centerId, "0");
            if (dataResourcesList == null) {
                logger.error("查询仓库数据中心出错");
                return ServerResponse.asErrorResponse();
            }
            //过滤出数据源类型为指定值时的数据源信息
            ArrayList<PageSelectOneValue> list = new ArrayList<>();
            for (DataResource data : dataResourcesList) {
                if (data.getResType().equalsIgnoreCase(type)) {
                    list.add(new PageSelectOneValue(data.getResId(), data.getResName()));
                }
            }
            return ServerResponse.asSucessResponse(list);
        } catch (Exception e) {
            logger.error("查询仓库数据中心出错" + ExceptionUtil.getExceptionTrace(e));
        }
        return ServerResponse.asSucessResponse(new ArrayList<>());
    }


    /**
     * 根据数据源类型过滤数据源
     *
     * @param type 数据源类型
     * @return
     */
    @RequestMapping(value = "/getDataResourceNameByType")
    @ResponseBody
//    @IgnoreSecurity
    public ServerResponse<List<PageSelectOneValue>> getDataResourceNameByType(@RequestParam("type") String type) {
        try {
            List<DataResource> dataResourcesList = restTemplateHandle.getDataCenterVersion("0", "0");
            if (dataResourcesList == null) {
                logger.error("查询仓库数据中心出错");
                return ServerResponse.asErrorResponse();
            }
            ArrayList<PageSelectOneValue> list = new ArrayList<>();
            for (DataResource data : dataResourcesList) {
                if (data.getResType().equalsIgnoreCase(type)) {
                    list.add(new PageSelectOneValue(data.getResId(), data.getResName()));
                }
            }
            return ServerResponse.asSucessResponse(list);
        } catch (Exception e) {
            logger.error("查询仓库数据中心出错" + ExceptionUtil.getExceptionTrace(e));
        }
        return ServerResponse.asSucessResponse(new ArrayList<>());

    }

    @RequestMapping(value = "/searchObjectBySourceId")
    @ResponseBody
    public ServerResponse<Integer> searchObjectCountBySourceId(String tableId) {
        ServerResponse<Integer> serverResponse = null;
        int i = resourceManageAddDao.searchObjectBySourceId(tableId);
//		int i = 0;
        return ServerResponse.asSucessResponse(i);
    }

}
