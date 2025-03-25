package com.synway.datastandardmanager.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.synway.common.bean.ServerResponse;
import com.synway.datastandardmanager.constant.Common;
import com.synway.datastandardmanager.dao.master.ResourceManageAddDao;
import com.synway.datastandardmanager.dao.master.ResourceManageDao;
import com.synway.datastandardmanager.dao.standard.TableOrganizationDao;
import com.synway.datastandardmanager.databaseparse.DataBaseType;
import com.synway.datastandardmanager.databaseparse.TableColumnHandle;
import com.synway.datastandardmanager.entity.BuildTableInfoVo;
import com.synway.datastandardmanager.enums.SysCodeEnum;
import com.synway.datastandardmanager.pojo.ObjectField;
import com.synway.datastandardmanager.pojo.PageSelectOneValue;
import com.synway.datastandardmanager.pojo.StandardTableCreated;
import com.synway.datastandardmanager.pojo.buildtable.CreateTableVo;
import com.synway.datastandardmanager.pojo.enums.SynlteFieldType;
import com.synway.datastandardmanager.scheduler.SwitchFlagQuery;
import com.synway.datastandardmanager.service.DbManageService;
import com.synway.datastandardmanager.service.ObjectStoreInfoService;
import com.synway.datastandardmanager.util.*;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

//import jakarta.validation.constraints.NotNull;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 建表相关的操作
 * @author
 * @date 2019/10/25 9:58
 */
@RequestMapping("/dbManager")
@Controller
@Validated
public class DbManagerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DbManagerController.class);

    @Autowired
    private DbManageService dbManageService;

    @Autowired
    private TableOrganizationDao tableOrganizationDao;

    @Autowired
    ConcurrentHashMap<String,Boolean> switchHashMap;

    @Autowired
    private ResourceManageDao resourceManageDao;

    @Autowired
    ConcurrentHashMap<String,String> parameterMap;

    @Autowired
    ResourceManageAddDao resourceManageAddDao;

    /**
     * 阿里云的相关建表接口
     * @param buildTableInfoVo
     * @return
     */
    @RequestMapping("/buildTable")
    @ResponseBody
    public ServerResponse buildTable(@RequestBody BuildTableInfoVo buildTableInfoVo){
        CreateTableVo createTableVo = new CreateTableVo();
        try {
            LOGGER.info("========"+JSON.toJSONString(buildTableInfoVo));
            boolean switchFlag = switchHashMap.getOrDefault("approvalInfo",true);
            if(switchFlag){
                LOGGER.info("从接口查询到需要审批");
                // 20200227 需要向审批流程中发送请求，之后由审批流程中建表
                String url = dbManageService.buildTableToApprovalInfoService(buildTableInfoVo);
                createTableVo.setUrl(url);
                createTableVo.setSwitchFlag(true);
            }else{
                LOGGER.info("从接口查询到不需要审批，直接建表");
                String result = dbManageService.buildAdsOrOdpsTable(buildTableInfoVo);
                createTableVo.setSwitchFlag(false);
                createTableVo.setMessage(result);
            }
            createTableVo.setMessage("创建成功");
            return ServerResponse.asSucessResponse(createTableVo);
        }catch (Exception e){
            LOGGER.error("建表报错：", e);
            return ServerResponse.asErrorResponse("建表报错："+e.getMessage());
        }
    }

    /**
     * 获取指定类型的所有标准字段信息
     * @param dataBaseType 类型
     * @return
     */
    @RequestMapping("/getColumnType")
    @ResponseBody
    public ServerResponse<Object> getColumnType(@NotNull String dataBaseType){
        List<Map<String ,String>> resultList = new ArrayList<>();
        try {
            LOGGER.info("开始获取数据库【"+dataBaseType+"】的字段类型");
            Map<String ,String> columnType = DataBaseColumnTypeUtil.getFieldTypeMap(dataBaseType);
            for(String keyName:columnType.keySet()){
                Map<String,String> oneMap = new HashMap<>();
                oneMap.put("value",keyName);
                oneMap.put("text",columnType.get(keyName));
                resultList.add(oneMap);
            }
            LOGGER.info("获取数据库【"+dataBaseType+"】的字段类型所有数据为："+ JSONObject.toJSONString(resultList));
        }catch (Exception e){
            LOGGER.error("获取数据库【"+dataBaseType+"】报错"+ ExceptionUtil.getExceptionTrace(e));

        }
        return ServerResponse.asSucessResponse(resultList);
    }

    /**
     * 一键切换字段的定义关系
     * @param
     * @return
     */
    @RequestMapping(value = "/columnCorrespondClick" ,method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse<List<ObjectField>> columnCorrespondClick(@RequestBody JSONObject allObjectList){
        ServerResponse<List<ObjectField>> serverResponse = null;
        try {
            LOGGER.info("开始对表字段进行一键映射");
            String dataBaseType = allObjectList.getString("dataBaseType");
            if(StringUtils.isEmpty(dataBaseType)){
                serverResponse = ServerResponse.asErrorResponse("传入的数据库类型为空");
                return serverResponse;
            }
            List<ObjectField> columnList = allObjectList.getJSONArray("columnData").toJavaList(ObjectField.class);
            Map<String,String> resultColumnMap = null;
            if("ODPS".equalsIgnoreCase(dataBaseType)){
                resultColumnMap = FieldTypeUtil.getFieldTypeMap("oracle","odps");
            }else if("ADS".equalsIgnoreCase(dataBaseType)){
                resultColumnMap = FieldTypeUtil.getFieldTypeMap("oracle","ads");
            }else if("HBASE-CDH".equalsIgnoreCase(dataBaseType)){
                resultColumnMap = FieldTypeUtil.getFieldTypeMap("standardize","HBASE-CDH");
            }else if("HBASE-HUAWEI".equalsIgnoreCase(dataBaseType)){
                resultColumnMap = FieldTypeUtil.getFieldTypeMap("standardize","HBASE-HUAWEI");
            }else if("HIVE-CDH".equalsIgnoreCase(dataBaseType)){
                resultColumnMap = FieldTypeUtil.getFieldTypeMap("standardize","HIVE-CDH");
            }else if("HIVE-HUAWEI".equalsIgnoreCase(dataBaseType)){
                resultColumnMap = FieldTypeUtil.getFieldTypeMap("standardize","HIVE-HUAWEI");
            }else if("DATAHUB".equalsIgnoreCase(dataBaseType)){
                resultColumnMap = FieldTypeUtil.getFieldTypeMap("standardize","datahub");
            } else if("CLICKHOUSE".equalsIgnoreCase(dataBaseType)){
                resultColumnMap = FieldTypeUtil.getFieldTypeMap("standardize","CLICKHOUSE");
            } else if ("ORACLE".equalsIgnoreCase(dataBaseType)){
                resultColumnMap = FieldTypeUtil.getFieldTypeMap("oracle","oracle");
            }else{
                serverResponse = ServerResponse.asErrorResponse("传入的数据库类型不是ODPS/ADS/HBASE/hive/datahub/CLICKHOUSE/ORACLE");
                return serverResponse;
            }
            for(ObjectField oneObjectField:columnList){
                String standardColumnType = "";
                if(StringUtils.isEmpty(oneObjectField.getFieldType())){
                    standardColumnType = "";
                }else{
                    standardColumnType = SynlteFieldType.getSynlteFieldType(Integer.parseInt(oneObjectField.getFieldType()));
                }
                if(StringUtils.isNotEmpty(oneObjectField.getCreateColumnType())){
                    continue;
                }
                if(StringUtils.isNotEmpty(standardColumnType)){
                    String createColumnType = resultColumnMap.get(standardColumnType);
                    oneObjectField.setCreateColumnType(createColumnType);
                    oneObjectField.setCreateColumnLen(oneObjectField.getFieldLen());
                }
            }
            serverResponse = ServerResponse.asSucessResponse(columnList);
        }catch (Exception e){
            serverResponse = ServerResponse.asErrorResponse("传入的数据库类型为空");
            LOGGER.error("获取数据库报错"+ ExceptionUtil.getExceptionTrace(e));
        }
        return serverResponse;
    }

    /**
     * 创建建表sql信息
     * @param buildTableInfoVo
     * @return
     */
    @RequestMapping(value = "/showCreateTableSql" ,method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse<String> showCreateTableSql(@RequestBody BuildTableInfoVo buildTableInfoVo){
        ServerResponse<String> serverResponse = null;
        try{
            LOGGER.info("=================开始创建表SQL====================");
            String dataBaseType = buildTableInfoVo.getDsType();
            // 20200927 使用以下内容进行
            String handleClaStr = DataBaseType.getCla(dataBaseType+"_addcolumn");
            TableColumnHandle tableColumnHandle = (TableColumnHandle)Class.forName(handleClaStr).newInstance();
            String sql = tableColumnHandle.getCreateSql(buildTableInfoVo);
            LOGGER.info("=================获取建表sql结束====================");
            serverResponse = ServerResponse.asSucessResponse("",sql);
        }catch (Exception e){
            serverResponse = ServerResponse.asErrorResponse("创建建表SQL报错"+e.getMessage());
            LOGGER.error("创建建表SQL报错"+ ExceptionUtil.getExceptionTrace(e));
        }
        return serverResponse;
    }

    /**
     *  获取已经创建的表信息
     * @param tableId   表协议ID
     * @return
     */
    @RequestMapping(value = "/getAllStandardTableCreatedList" ,method = {RequestMethod.GET})
    @ResponseBody
    public ServerResponse<List<StandardTableCreated>> getAllStandardTableCreatedList(String tableId){
        ServerResponse<List<StandardTableCreated>> serverResponse = null;
        try{
            LOGGER.info("=================开始获取已经创建的表信息====================");
            if(StringUtils.isEmpty(tableId)){
                serverResponse = ServerResponse.asErrorResponse("传入的表协议ID为空，获取已建表信息报错");
                return serverResponse;
            }
            // 先根据tableId获取对应的表名
            List<String> tableNameList = resourceManageDao.getTableNameByTableId(tableId);
            if(tableNameList.isEmpty() || StringUtils.isEmpty(tableNameList.get(0))){
                serverResponse = ServerResponse.asErrorResponse("传入的tableId为"+tableId+",从OBJECT表中获取到的英文表名为空，" + "没有创建该标准表信息");
                return serverResponse;
            }
            String objectId = resourceManageAddDao.getObjectIDByTableID(tableId);
            List<StandardTableCreated> resultAll = tableOrganizationDao.getCreatedTableListByTableId(tableId);
            for (StandardTableCreated standardTableCreated : resultAll){
                String tableType = SysCodeEnum.getNameByCodeAndType(standardTableCreated.getTableBase(), "TABLETYPE");
                standardTableCreated.setTableBase(StringUtils.isNotBlank(tableType) ? tableType : "");
                standardTableCreated.setObjectId(objectId);
            }
            serverResponse = ServerResponse.asSucessResponse(resultAll);
        }catch (Exception e){
            serverResponse = ServerResponse.asErrorResponse("获取已经创建的表信息报错"+e.getMessage());
            LOGGER.error("获取已经创建的表信息报错"+ ExceptionUtil.getExceptionTrace(e));
        }
        return serverResponse;
    }

    /**
     *  presto 建表需要单独写个建表的接口
     * @param buildTableInfoVo
     * @return
     */
    @RequestMapping(value = "/createHuaWeiTable")
    @ResponseBody
    public ServerResponse<Map<String,String>> createHuaWeiTable(@RequestBody BuildTableInfoVo buildTableInfoVo){
        ServerResponse<Map<String,String>> serverResponse = null;
        Map<String,String> returnMap = null;
        try{
            LOGGER.info("==================开始建表==============");
            boolean switchFlag = switchHashMap.getOrDefault("approvalInfo",true);
            if(switchFlag){
                String message =  dbManageService.buildHuaWeiTableToApprovalInfoService(buildTableInfoVo);
                returnMap = new HashMap<>();
                returnMap.put("approvalInfo","true");
                returnMap.put("message",message);
            }else{
                String message = dbManageService.createHuaWeiTableService(buildTableInfoVo);
                returnMap = new HashMap<>();
                returnMap.put("approvalInfo","false");
                returnMap.put("message",message);
            }
            serverResponse = ServerResponse.asSucessResponse(returnMap);
        }catch (Exception e){
            LOGGER.error("创建华为平台相关表报错: "+ExceptionUtil.getExceptionTrace(e));
            serverResponse = ServerResponse.asErrorResponse("创建华为平台相关表报错: "+e.getMessage());
        }
        return serverResponse;
    }

    /**
     * 对于新的需求，所有字段都会先存储在页面中，所以需要添加公共字段之后，再返回需要添加之后的所有字段信息
     */
    @RequestMapping(value = "/getCommonColumn" ,method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse<List<ObjectField>> getCommonColumn(@RequestBody List<ObjectField> allObjectList){
        ServerResponse<List<ObjectField>> serverResponse = null;
        try{
            LOGGER.info("传入的所有字段信息为："+JSONObject.toJSONString(allObjectList));
            List<ObjectField> commonFiledList = dbManageService.getCommonColumnService(allObjectList);
            serverResponse = ServerResponse.asSucessResponse(commonFiledList);
        }catch (Exception e){
            serverResponse = ServerResponse.asErrorResponse("获取公共字段报错"+e.getMessage());
            LOGGER.error("获取公共字段报错"+ExceptionUtil.getExceptionTrace(e));
        }
        return serverResponse;
    }

    /**
     * vue接口获取 建表时的数据库类型
     * @return
     */
    @RequestMapping(value = "/getDataPlatFormTypeVue" )
    @ResponseBody
    public ServerResponse<List<String>> getDataPlatFormTypeVue(){
        ServerResponse<List<String>> serverResponse = null;
        try{
            List<String> responseList = null;
            LOGGER.info("开始查询配置中心的参数dataPlatFormType");
            // 20210626  每次查询都调用这个接口，来获取指定用户的本地仓库类型
//            Map<String,DataResource> map = switchFlagQuery.getLocalMap();
//            responseList = Common.DATA_TYPE_LIST;
            serverResponse = ServerResponse.asSucessResponse(Common.DATA_TYPE_LIST);
        }catch (Exception e){
            serverResponse = ServerResponse.asErrorResponse("获取数据源类型报错，请在数据仓库中配置本地仓类型"+e.getMessage());
            LOGGER.error("获取数据源类型报错，请在数据仓库中配置本地仓类型："+ExceptionUtil.getExceptionTrace(e));
        }
        return serverResponse;
    }

    /**
     * 获取分区类型
     * @return
     */
    @RequestMapping(value = "/getPartitionType" )
    @ResponseBody
    public ServerResponse<List<PageSelectOneValue>> getPartitionType(){
        LOGGER.info("开始获取分区类型");
        List<PageSelectOneValue> partitionTypeList = dbManageService.getPartitionType();
        LOGGER.info("分区类型获取结束");
        return ServerResponse.asSucessResponse(partitionTypeList);
    }


}
