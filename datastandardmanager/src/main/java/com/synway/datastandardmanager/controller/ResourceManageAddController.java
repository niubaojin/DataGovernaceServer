package com.synway.datastandardmanager.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.synway.datastandardmanager.constant.Common;
import com.synway.datastandardmanager.dao.master.ResourceManageAddDao;
import com.synway.datastandardmanager.dao.master.ResourceManageDao;
import com.synway.datastandardmanager.pojo.*;
import com.synway.datastandardmanager.scheduler.SwitchFlagQuery;
import com.synway.datastandardmanager.service.DubboMessageService;
import com.synway.datastandardmanager.service.ResourceManageAddService;
import com.synway.datastandardmanager.util.ExceptionUtil;
import com.synway.common.bean.ServerResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@Controller
//@RequestMapping("/dataStandardManager")
public class ResourceManageAddController {
    private static Logger LOGGER = LoggerFactory.getLogger(ResourceManageAddController.class);

    @Autowired
    private ResourceManageDao resourceManageDao;

    @Autowired ResourceManageAddService resourceManageAddService;

    @Autowired
    DubboMessageService dubboMessageServiceImpl;
    @Autowired private ResourceManageAddDao resourceManageAddDao;

    @Autowired
    ConcurrentHashMap<String,String> parameterMap;

    @Autowired
    private SwitchFlagQuery switchFlagQuery;


//    @RequestMapping("/resourceManageAdd")
//    public String dataOperationMonitor(){
//        return "resourceManageAdd.html";
//    }

//    @RequestMapping("/getdataSourceLocationSelect")
//    @ResponseBody
//    public List<PageSelectOneValue> getdataSourceLocationSelect(){
//        LOGGER.info("======开始获取来源数据源所有信息============");
//        return resourceManageAddService.getAllDataSourceLocation();
//    }
//    @RequestMapping("/getStorageDataSourceSelect")
//    @ResponseBody
//    public List<PageSelectOneValue> getStorageDataSourceSelect(){
//        LOGGER.info("======开始获获取存储数据源所有信息============");
//        return resourceManageAddService.getAllStorageDataSourceSelect();
//    }
//
//    @RequestMapping("/initManufacturer")
//    @ResponseBody
//    public ServerResponse initManufacturer(){//初始化厂商
//        LOGGER.info("======初始化厂商下拉框============");
//        List<String> resultList = resourceManageAddService.initManufacturer();
//        return ServerResponse.asSucessResponse(resultList);
//    }
//
//    @RequestMapping("/initProtocolNum")
//    @ResponseBody
//    public ServerResponse initProtocolNum(HttpServletResponse response){//初始化协议编码
//        LOGGER.info("======初始化厂商下拉框============");
//        List<Sys> resultList = resourceManageAddService.initProtocolNum();
//        return ServerResponse.asSucessResponse(resultList);
//    }

    @RequestMapping("/checkAndGetTableID")
    @ResponseBody
    public ServerResponse checkAndGetTableID(String sourceID){//检测是否已经有目标协议
        String tableID = resourceManageAddService.checkAndGetTableID(sourceID);
        return ServerResponse.asSucessResponse(tableID,tableID);
    }

    /**
     * 导入原始数据项框中的数据
     * @param  sourceProtocol
     * @param tableName
     * @param sourceSystem
     * @param sourceFirm
     * @param tableId
     * @return
     */
    @RequestMapping("/initSourceFieldTable")
    @ResponseBody
    public ServerResponse<List<SourceFieldInfo>> initSourceFieldTable(String sourceProtocol, String tableName, String sourceSystem,
                                                      String sourceFirm,String tableId){
        LOGGER.info("======源字段列表============");
        List<SourceFieldInfo> result = resourceManageAddService.initSourceFieldTable(sourceProtocol,tableName,sourceSystem,sourceFirm,tableId);
        return ServerResponse.asSucessResponse(result);
    }

//    @RequestMapping("/initStandardFieldTable")
//    @ResponseBody public List<ObjectField> initStandardFieldTable(String tableID){//标准的字段:sourceID或者是targetID
//        LOGGER.info("======标准的字段============");
//        List<ObjectField> resultList = resourceManageAddService.initStandardFieldTable(tableID);
//        return resultList;
//    }

//    @RequestMapping("/deleteRelationRecord")
//    @ResponseBody public ServerResponse deleteRelationRecord(String relationRecordID) {
//        resourceManageAddService.deleteRelationRecord(relationRecordID);
//        return ServerResponse.asSucessResponse(relationRecordID);
//    }

//    @RequestMapping("/saveSrcField")
//    @ResponseBody public ServerResponse saveSrcField(@RequestBody JSONObject jsonObject) {
//        try {
//            JSONArray jsonArray = jsonObject.getJSONArray("sourceFieldInfo");
//            String tableID = jsonObject.getString("tableID");
//            List<SourceFieldInfo> sourceFieldInfos = jsonArray.toJavaList(SourceFieldInfo.class);
//            resourceManageAddService.saveSrcField(sourceFieldInfos, tableID);
//            return ServerResponse.asSucessResponse("导入源字段成功！","导入源字段成功！");
//        }catch (Exception e){
//            LOGGER.error(ExceptionUtil.getExceptionTrace(e));
//            return ServerResponse.asErrorResponse("导入源字段失败,错误信息:"+ExceptionUtil.getExceptionTrace(e));
//        }
//    }

    // 在导入来源关系的数据中，如果是从数据仓库中获取 并且不是流程中，则需要调用该接口
    @RequestMapping("/addTableColumnByEtl")
    @ResponseBody
    public ServerResponse<String> addTableColumnByEtl(String sourceProtocol,
                                                      String sourceSystem,
                                                      String sourceFirm,
                                                      String tableName,
                                                      String dataName,
                                                      String tableId,
                                                      String centerId,
                                                      String centerName,
                                                      String project,
                                                      String resourceId){
        LOGGER.info("获取字段信息查询的参数为：sourceProtocol："+sourceProtocol+" sourceSystem:"+sourceSystem+
                " sourceFirm:"+sourceFirm+" tableName:"+tableName+" tableId:"+tableId+ " resourceId:"+resourceId);
        String returnMesg= resourceManageAddService.addTableColumnByEtl(sourceProtocol,sourceSystem,
                sourceFirm,tableName,dataName,tableId,centerId,centerName,project,resourceId);
        return ServerResponse.asSucessResponse(returnMesg,returnMesg);
    }


    @RequestMapping("/getSourceFieldColumnList")
    @ResponseBody
    public ServerResponse<List<ObjectField>> getSourceFieldColumnList(@RequestBody JSONObject jsonObject) {
        ServerResponse<List<ObjectField>> serverResponse = null;
        try{
            LOGGER.info("传入的参数为："+JSONObject.toJSONString(jsonObject));
            JSONArray jsonArray = jsonObject.getJSONArray("sourceFieldInfo");
            String objectID = jsonObject.getString("objectID");
            List<SourceFieldInfo> sourceFieldInfos = jsonArray.toJavaList(SourceFieldInfo.class);
            JSONArray pageJsonArray = jsonObject.getJSONArray("pageColumnList");
            List<ObjectField> objectFieldList = pageJsonArray.toJavaList(ObjectField.class);
            List<ObjectField> needAddobjectFieldList = resourceManageAddService.getSourceFieldColumnListService(
                    sourceFieldInfos, objectID,objectFieldList);
            serverResponse = ServerResponse.asSucessResponse(needAddobjectFieldList);
        }catch (Exception e){
            serverResponse = ServerResponse.asErrorResponse("导入源字段信息报错"+ e.getMessage());
            LOGGER.error("导入源字段信息报错"+ExceptionUtil.getExceptionTrace(e));
        }
        return  serverResponse;
    }


    /**
     * 二级分类的layui的数据
     */
    @RequestMapping("/getSecondaryClassLayui")
    @ResponseBody
    public ServerResponse<List<LayuiClassifyPojo>> getSecondaryClassLayui(@RequestParam("mainClassify")String mainClassify,
                                         @RequestParam("primaryClassifyCh")String primaryClassifyCh){
        LOGGER.info("传入的参数为，mainClassify："+mainClassify+" primaryClassifyCh："+ primaryClassifyCh);
        ServerResponse<List<LayuiClassifyPojo>> serverResponse = null;
        try{
            List<LayuiClassifyPojo> result = resourceManageAddService.getSecondaryClassLayuiService(mainClassify , primaryClassifyCh);
            serverResponse = ServerResponse.asSucessResponse(result);
        }catch (Exception e){
            LOGGER.error("获取二级分类信息报错"+ExceptionUtil.getExceptionTrace(e));
            serverResponse = ServerResponse.asErrorResponse("获取二级分类信息报错"+e.getMessage());
        }
        return serverResponse;
    }

    /**
     * 根据分级分类信息以及数据名 回填对应的表名
     * @param organizationClassifys
     * @param sourceClassifys
     * @param dataSourceName
     * @return
     */
     @RequestMapping("/getEnFlagByChnType")
     @ResponseBody
     public ServerResponse<String>   getEnFlagByChnType(@RequestParam("organizationClassifys")String organizationClassifys,
                                                        @RequestParam("sourceClassifys")String sourceClassifys,
                                                        @RequestParam("dataSourceName")String dataSourceName,
                                                        @RequestParam("flag")Boolean flag){
           LOGGER.info("传入的参数为，organizationClassifys: "+organizationClassifys+" sourceClassifys:"+
                   sourceClassifys+" dataSourceName:"+dataSourceName+" flag:"+flag);
           ServerResponse<String> serverResponse = null;
           try{
               String  tableName =   resourceManageAddService.getEnFlagByChnType(organizationClassifys,sourceClassifys,dataSourceName,flag);
               serverResponse = ServerResponse.asSucessResponse(tableName,tableName);
           }catch(Exception e){
               LOGGER.error("获取组织标准为表名检测使用出错"+ExceptionUtil.getExceptionTrace(e));
               serverResponse = ServerResponse.asErrorResponse("获取组织标准为表名检测使用出错"+e.getMessage());
           }
           return serverResponse;
     }

    /**
     * 如果objectId 等于null  直接判断该表名是否已经存在了，存在则提示，表命名已经存在了
     * 如果objectId 不等于null 先判断表名的objectId 和传进来的objectId 是否一致。
     * 如果一致，可以给他保存
     * 如果不一致（说明这个表名已经存在了），则不给他保存
     * **/

    @RequestMapping("/checTableNamekIsExit")
    @ResponseBody
    public ServerResponse<Object>   checTableNamekIsExit(String realTableName,String objectId){
        LOGGER.info("传入的参数为，realTableName "+realTableName+"  objectId"+ objectId);
        ServerResponse<Object> serverResponse = null;
        try{
            int i =  resourceManageAddService.checTableNamekIsExit(realTableName,objectId);
            serverResponse = ServerResponse.asSucessResponse(i);
        }catch(Exception e){
            LOGGER.error("判断该表名是否已经存在了出错"+ExceptionUtil.getExceptionTrace(e));
            serverResponse = ServerResponse.asErrorResponse("判断该表名是否已经存在了出错"+e.getMessage());
        }
        return serverResponse;
    }


    /**
     *
     */
    @RequestMapping("/getOrganizationRelationByTableName")
    @ResponseBody
    public ServerResponse<DataResourceRawInformation> getOrganizationRelationByTableName(@RequestParam("addTableName")String addTableName){
        ServerResponse<DataResourceRawInformation> serverResponse = null;
        try{
            LOGGER.info("查询的参数addTableName为:"+addTableName);
            DataResourceRawInformation dataResourceRawInformation = resourceManageAddService.getOrganizationRelationByTableName(addTableName);
            serverResponse = ServerResponse.asSucessResponse(dataResourceRawInformation);
        }catch (Exception e){
            LOGGER.error("获取增加来源信息报错"+ExceptionUtil.getExceptionTrace(e));
        }
        return serverResponse;
    }


    /**
     * 数据信息中  数据组织 layui插件需要的数据
     * @param mainClassifyCh
     * @return
     */
    @RequestMapping("/getAllClassifyLayui")
    @ResponseBody
    public ServerResponse<List<LayuiClassifyPojo>> getAllClassifyLayui(
            @RequestParam("mainClassifyCh")String mainClassifyCh){
        LOGGER.info("开始查询所有符合layui插件的分级分类信息");
        ServerResponse<List<LayuiClassifyPojo>> serverResponse = null;
        try{
            List<LayuiClassifyPojo> result = resourceManageAddService.getAllClassifyLayuiService(mainClassifyCh);
            serverResponse = ServerResponse.asSucessResponse(result);
        }catch (Exception e){
            LOGGER.error("获取分级分类信息报错"+ExceptionUtil.getExceptionTrace(e));
            serverResponse = ServerResponse.asErrorResponse("获取分级分类信息报错"+e.getMessage());
        }
        return serverResponse;
    }


    @RequestMapping("/getDataBaseType")
    @ResponseBody
    public ServerResponse getDataBaseType(){
        LOGGER.info("开始查询大数据库类型的");
        try{
            String dbType;
            Map<String, DataResource> map = switchFlagQuery.getLocalMap();
            if(map.get(Common.ODPS) != null){
                dbType = Common.ALI_YUN;
            }else if(map.get(Common.HIVECDH) != null){
                dbType = Common.HUA_WEI_YUN;
            }else if(map.get(Common.HIVEHUAWEI) != null){
                dbType = Common.HUA_WEI_YUN;
            }else{
                dbType = parameterMap.getOrDefault(Common.DATA_PLAT_FORM_TYPE, Common.HUA_WEI_YUN);
            }
            return ServerResponse.asSucessResponse(dbType, dbType);
        }catch (Exception e){
            LOGGER.error("获取大数据库类型异常：", e);
            return ServerResponse.asErrorResponse("获取大数据库类型异常：" + e.getMessage());
        }
    }

//    /**
//     *  从数据仓库中查询数据来源的信息
//     * @param dataId  数据仓库的 ID
//     * @param tableName 数据仓库的表名
//     * @param project 数据仓库的项目空间
//     * @return
//     */
//    @RequestMapping("/getResourceTableDataUrl")
//    @ResponseBody
//    public ServerResponse<PublicDataInfo> getResourceTableDataUrl(@RequestParam("dataId")String dataId,
//                                                                  @RequestParam("project") String project,
//                                                                  @RequestParam("tableName")String tableName){
//        ServerResponse<PublicDataInfo> serverResponse = null;
//        LOGGER.info("开始从数据仓库中查询数据来源的信息，参数为dataId:"+dataId+" tableName:"+tableName);
//        try{
//            if(StringUtils.isEmpty(dataId)){
//                throw new NullPointerException("dataId的参数为空");
//            }
//            if(StringUtils.isEmpty(tableName)){
//                throw new NullPointerException("tableName的参数为空");
//            }
//            PublicDataInfo publicDataInfo = resourceManageAddService.getResourceTableDataService(dataId,project,tableName);
//            serverResponse = ServerResponse.asSucessResponse(publicDataInfo);
//        }catch (Exception e){
//            LOGGER.error("从数据仓库中查询数据来源的信息报错："+ExceptionUtil.getExceptionTrace(e));
//            serverResponse = ServerResponse.asErrorResponse("从数据仓库中查询数据来源的信息报错: "+e.getMessage());
//        }
//        return serverResponse;
//    }


//    /**
//     * 获取dubbo里面的管理单位
//     * @return
//     */
//    @RequestMapping("/getResourceManagementUnitUrl")
//    @ResponseBody
//    public ServerResponse<List<BackDataConstruct>> getResourceManagementUnitUrl(){
//        ServerResponse<List<BackDataConstruct>> serverResponse = null;
//        try{
//            List<BackDataConstruct> backDataConstructList = dubboMessageServiceImpl.getResourceManagementUnit();
//            serverResponse = ServerResponse.asSucessResponse(backDataConstructList);
//        }catch (Exception e){
//            LOGGER.error("从dubbo中获取数据报错："+ExceptionUtil.getExceptionTrace(e));
//            serverResponse = ServerResponse.asErrorResponse("从dubbo中获取数据报错："+e.getMessage());
//        }
//        return serverResponse;
//    }


    /**
     * 根据 数据来源分类的id值获取 对应的代码数据值
     * @param classifyIds   逗号分隔   JZCODEGASJZZFL010101,JZCODEGASJZZFL010101001
     * @return
     */
    @RequestMapping("/getCodeNameByClassifyId")
    @ResponseBody
    public ServerResponse<String> getCodeNameByClassifyId(@RequestParam("classifyIds")String classifyIds){
        String codeName = "";
        try{
            if(StringUtils.isEmpty(classifyIds)){
                return ServerResponse.asSucessResponse("");
            }
            List<String> classifyIdList = Arrays.asList(classifyIds.split(","));
            String oneClassifyId = classifyIdList.get(classifyIdList.size() - 1);
            if(classifyIdList.size() >1){
                codeName = resourceManageAddDao.getCodeNameByClassifyIdDao(oneClassifyId,2);
            }else{
                codeName = resourceManageAddDao.getCodeNameByClassifyIdDao(oneClassifyId,1);
            }
            return ServerResponse.asSucessResponse(codeName);
        }catch (Exception e){
            LOGGER.error("根据数据来源分类的id值获取对应的代码数据值报错: ", e);
            return ServerResponse.asErrorResponse("查询分类编码失败：" + e.getMessage());
        }
    }


//    /**
//     * 获取数据库中 字段敏感度的相关码表信息
//     * @return
//     */
//    @RequestMapping("/getSensitivityLevelSelect")
//    @ResponseBody
//    public ServerResponse<List<PageSelectOneValue>>  getSensitivityLevelSelect(){
//        LOGGER.info("开始获取字段敏感度的相关信息");
//        ServerResponse<List<PageSelectOneValue>> serverResponse = null;
//        try {
//            List<PageSelectOneValue> pageSelectOneValues = resourceManageAddDao.searchFieldCodeValByCodeId("JZCODEGAZDMGD");
//            if(pageSelectOneValues == null || pageSelectOneValues.isEmpty()){
//                serverResponse = ServerResponse.asErrorResponse("表fieldcodeval中没有配置字段敏感度的码表值，请联系标委会");
//            }else{
//                serverResponse = ServerResponse.asSucessResponse(pageSelectOneValues);
//            }
//        }catch (Exception e){
//            LOGGER.error("获取字段敏感度的码表报错"+ExceptionUtil.getExceptionTrace(e));
//            serverResponse = ServerResponse.asErrorResponse("获取字段敏感度的码表报错: "+e.getMessage());
//        }
//        return serverResponse;
//    }


    /**
     *   根据页面上的sourceId 来获取到自增id值，来获取最新的sourceid信息
     *   附带验证功能
     * @param sourceId   页面上的sourceId信息
     * @param dataSourceClassify   数据来源分类的代码
     * @param code                 6位本地行政代码
     * @return  如果为空 表示这个sourceid不是标准协议信息 如果不为空，表示已经获取了最新的sourceid信息
     */
    @RequestMapping("/getNewSourceIdById")
    @ResponseBody
    public String  getNewSourceIdById(String sourceId,
                                      String dataSourceClassify,
                                      String code){
        try{
            String sourceIdNew = resourceManageAddService.getNewSourceIdById(sourceId,dataSourceClassify,code);
            return sourceIdNew;
        }catch (Exception e){
            LOGGER.error(ExceptionUtil.getExceptionTrace(e));
            return null;
        }
    }


    /**
     * 获取字段分类的码表信息
     * @return
     */
//    @IgnoreSecurity
    @RequestMapping("/getAllFieldClassList")
    @ResponseBody
    public  ServerResponse<List<PageSelectOneValue>> getAllFieldClassList(){
        ServerResponse serverResponse = null;
        try{
            JSONArray list = resourceManageAddService.getAllFieldClassList();
            serverResponse = ServerResponse.asSucessResponse(list);
        }catch (Exception e){
            LOGGER.error(ExceptionUtil.getExceptionTrace(e));
            serverResponse = ServerResponse.asErrorResponse("获取字段分类信息报错"+e.getMessage());
        }
        return serverResponse;
    }


    /**
     * 获取数据分级的码表信息
     * @return
     */
    @RequestMapping("/searchSecurityLevel")
    @ResponseBody
    public ServerResponse<List<PageSelectOneValue>> searchSecurityLevelList(){
        LOGGER.info("开始获取数据分级信息");
        ServerResponse<List<PageSelectOneValue>> serverResponse = null;
        try{
            List<PageSelectOneValue> list = resourceManageDao.searchSecurityLevel();
            if(list == null || list.isEmpty()){
                serverResponse = ServerResponse.asErrorResponse("表fieldcodeval中没有配置安全分级的码表值，请联系标委会");
            }else{
                serverResponse = ServerResponse.asSucessResponse(list);
            }
        }catch (Exception e){
            LOGGER.error("获取安全分级的码表报错"+ExceptionUtil.getExceptionTrace(e));
            serverResponse = ServerResponse.asErrorResponse("获取安全分级的码表报错: "+e.getMessage());
        }
        return serverResponse;
    }


    /**
     * 获取字段安全分级列表
     * @param codeId
     * @return
     */
    @RequestMapping("/searchFieldSecurityLevelList")
    @ResponseBody
    public ServerResponse<List<PageSelectOneValue>> searchFieldSecurityLevelList(@RequestParam("codeId")String codeId){
        LOGGER.info("开始获取字段定义中的安全分级信息");
        ServerResponse<List<PageSelectOneValue>> serverResponse = null;
        try{
            if(StringUtils.isBlank(codeId)){
                codeId = "";
            }
            List<PageSelectOneValue> list = resourceManageDao.searchFieldSecurityLevel(codeId);
            if(list == null || list.isEmpty()){
                return ServerResponse.asSucessResponse(new ArrayList<PageSelectOneValue>());
            }else{
                serverResponse = ServerResponse.asSucessResponse(list);
            }
        }catch (Exception e){
            LOGGER.error("获取字段定义的安全分级的码表报错"+ExceptionUtil.getExceptionTrace(e));
            serverResponse = ServerResponse.asErrorResponse("获取字段定义的安全分级的码表报错: "+e.getMessage());
        }
        return serverResponse;
    }




}
