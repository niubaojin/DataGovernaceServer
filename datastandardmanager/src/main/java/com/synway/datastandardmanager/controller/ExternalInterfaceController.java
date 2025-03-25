package com.synway.datastandardmanager.controller;


import com.alibaba.fastjson.JSONObject;
import com.synway.common.bean.ServerResponse;
import com.synway.datastandardmanager.entity.BuildTableInfoVo;
import com.synway.datastandardmanager.interceptor.AuthorizedUserUtils;
import com.synway.datastandardmanager.interceptor.IgnoreSecurity;
import com.synway.datastandardmanager.pojo.*;
import com.synway.datastandardmanager.pojo.ExternalInterfce.GetTreeReq;
import com.synway.datastandardmanager.pojo.ExternalInterfce.RegisterTableInfo;
import com.synway.datastandardmanager.pojo.ExternalInterfce.TreeNodeVue;
import com.synway.datastandardmanager.service.DbManageService;
import com.synway.datastandardmanager.service.ExternalInterfceService;
import com.synway.datastandardmanager.service.ObjectStoreInfoService;
import com.synway.datastandardmanager.service.ResourceManageService;
import com.synway.datastandardmanager.util.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * 标准管理对外的接口
 * 因为有url请求拦截，而对外的接口是不需要在cookie上配置用户信息，所以之后的所有
 * 新加的接口需要加上 externalInterface/接口名  或者 加上这个  @IgnoreSecurity 注解
 * @author wangdongwei
 */
@Controller
@Validated
public class ExternalInterfaceController {
    private Logger logger = LoggerFactory.getLogger(ExternalInterfaceController.class);
    @Autowired
    ResourceManageService resourceManageServiceImpl;
    @Autowired
    ObjectStoreInfoService objectStoreInfoService;
    @Autowired
    private DbManageService dbManageService;
    @Autowired()private Environment env;
    @Autowired private ExternalInterfceService externalInterfceServiceImpl;


    /**
     * 提供给数据对账分类树的接口
     * @param req  参数接口 查询分类树
     * @return
     */
//    @IgnoreSecurity
    @RequestMapping(value = "/getTableOrganizationTree")
    @ResponseBody
    public ServerResponse<List<TreeNodeVue>> getTableOrganizationTree(@RequestBody GetTreeReq req){
        ServerResponse<List<TreeNodeVue>> serverResponse = null;
        logger.info("开始获取分级分类的相关信息，精确到表");
        try{
//            GetTreeReq req = new GetTreeReq();
            List<TreeNodeVue> treeNodeList = resourceManageServiceImpl.externalgetTableOrganizationTree(req,true,true,false);
            serverResponse = ServerResponse.asSucessResponse(treeNodeList);
        }catch (Exception e){
            serverResponse = ServerResponse.asErrorResponse("程序报错，查看标准管理程序"+e.getMessage());
            logger.error("获取分级分类的相关信息报错"+ ExceptionUtil.getExceptionTrace(e));
        }
        return serverResponse;
    }


    /**
     * 提供给数据组织资产的接口，只精确到最后一个分类
     *  左侧组织树-数据组织资产
     * @param req   参数接口 查询分类树
     * @return
     */
//    @IgnoreSecurity
    @RequestMapping(value = "/getTableOrganizationTreeForOrgPage")
    @ResponseBody
    public ServerResponse<List<TreeNodeVue>> getTableOrganizationTreeForOrgPage(@RequestBody GetTreeReq req){
        ServerResponse<List<TreeNodeVue>> serverResponse = null;
        logger.info("开始获取分级分类的相关信息，只精确到最后一级分级分类");
        try{
            List<TreeNodeVue> treeNodeList = resourceManageServiceImpl.externalgetTableOrganizationTree(req,false,true,true);
            serverResponse = ServerResponse.asSucessResponse(treeNodeList);
        }catch (Exception e){
            serverResponse = ServerResponse.asErrorResponse("程序报错，查看标准管理程序"+e.getMessage());
            logger.error("获取分级分类的相关信息报错"+ ExceptionUtil.getExceptionTrace(e));
        }
        return serverResponse;
    }


//    /**
//     * 提供给流程审批的接口，用于创建object信息
//     * @param reqStr  StandardObjectManage类的字符串
//     * @return
//     */
////    @IgnoreSecurity
//    @RequestMapping(value = "/createObjectByApprovalInfo")
//    @ResponseBody
//    public ServerResponse<String> createObjectByApprovalInfo(@RequestBody String reqStr){
//        ServerResponse<String> serverResponse = null;
//        logger.info("流程审批调用的参数为："+reqStr);
//        try{
//            StandardObjectManage standardObjectManage = JSONObject.parseObject(reqStr,StandardObjectManage.class);
//            if("4".equals(standardObjectManage.getStatus())){
//                serverResponse = ServerResponse.asSucessResponse("流程是中止状态，不能创建标准表","流程是中止状态，不能创建标准表");
//                return serverResponse;
//            }
//            String updateMessage = resourceManageServiceImpl.updateObjectStatus(standardObjectManage.getTableId());
//            ServerResponse.asSucessResponse(updateMessage);
//            if(standardObjectManage.getUser() != null){
//                AuthorizedUserUtils.getInstance().setAuthor(standardObjectManage.getUser());
//            }
//            String returnMsg = resourceManageServiceImpl.saveResourceFieldRelationService(standardObjectManage);
//            serverResponse = ServerResponse.asSucessResponse(returnMsg,returnMsg);
//            // 向 标准化程序提交修改之后的数据
//            ServerResponse<String> serverResponseReturn = resourceManageServiceImpl.pushMetaInfo(standardObjectManage.getTableId());
//            logger.info("向标准化push数据的结果为："+ JSONObject.toJSONString(serverResponseReturn));
//            // 还有保存源协议
////            resourceManageServiceImpl.pushSourceMetaInfo(standardObjectManage.getSourceRelationShipList());
//        }catch (Exception e){
//            logger.error("更新object表报错："+ExceptionUtil.getExceptionTrace(e));
//            serverResponse = ServerResponse.asErrorResponse("创建object表报错："+e.getMessage());
//        }
//        logger.info("流程审批更新object表状态结束");
//        return serverResponse;
//    }


    /**
     * 提供给流程审批的接口，用于建表 目前支持 odps/ads/hive/hbase
     * @param reqStr
     * @return
     */
//    @IgnoreSecurity
    @RequestMapping(value = "/buildTableApprovalInfo")
    @ResponseBody
    public ServerResponse<String> buildTableApprovalInfo(@RequestBody String reqStr) {
        ServerResponse<String> serverResponse = null;
        logger.info("流程审批调用建表的接口为：" + reqStr);
        try{
            BuildTableManage buildTableManage = JSONObject.parseObject(reqStr,BuildTableManage.class);
            if("4".equals(buildTableManage.getStatus())){
                logger.error("流程的返回状态是中止状态，禁止建表");
                return ServerResponse.asSucessResponse("流程的返回状态是中止状态，禁止建表","流程的返回状态是中止状态，禁止建表");
            }
            String dbType = buildTableManage.getDbType();
            if(dbType.equalsIgnoreCase(BuildTableManage.ADS) ||
                    dbType.equalsIgnoreCase(BuildTableManage.ODPS)||
                        BuildTableManage.DATAHUB.equalsIgnoreCase(dbType)){
                String message = dbManageService.buildAdsOrOdpsTable(buildTableManage.getBuildTableInfoVo());
                serverResponse = ServerResponse.asSucessResponse(message,message);
            }else if(dbType.equalsIgnoreCase(BuildTableManage.HIVE) ||
                    dbType.equalsIgnoreCase(BuildTableManage.HBASE)||
                    StringUtils.equalsIgnoreCase(dbType,BuildTableManage.CLICK_HOUSE)){
                String message;
                if(buildTableManage.getBuildTableInfoVo() == null){
                    BuildTableInfoVo buildTableInfoVo = JSONObject.parseObject(
                            JSONObject.toJSONString(buildTableManage.getAllObjectList()),BuildTableInfoVo.class);
                    message = dbManageService.createHuaWeiTableService(buildTableInfoVo);
                }else{
                    message = dbManageService.createHuaWeiTableService(buildTableManage.getBuildTableInfoVo());
                }
                serverResponse = ServerResponse.asSucessResponse(message,message);
            }else{
                String message = "数据库类型为"+dbType+"没有编写对应的建表方式";
                serverResponse = ServerResponse.asErrorResponse(message);
            }
            logger.info("调用流程审批建表接口结束");
        }catch (Exception e){
            logger.error("调用流程审批建表接口报错"+ExceptionUtil.getExceptionTrace(e));
            serverResponse = ServerResponse.asErrorResponse("调用流程审批建表接口报错"+e.getMessage());
        }
        return serverResponse;
    }

    /**
     *  返回标准管理页面的url
     * @param executeMethod   从哪个模块跳转过来  approvalInfo:表示是从审批流程中跳转过来的
     * @param dataId      对应的数据id，审批流程表示为
     * @param turnIndex   审批流程被驳回重新编辑后跳转url
     * @return
     */
//    @IgnoreSecurity
    @RequestMapping(value = "/getStandardPageByApprovalInfo")
    @ResponseBody
    public ServerResponse<String> getStandardPageByApprovalInfo(
            String executeMethod, String dataId, String turnIndex ,String moduleId){
        ServerResponse<String> serverResponse = null;
        try{
            String url = env.getProperty("nginxIp")
                    +"/governance/resourceManage?executeMethod="+executeMethod+"&dataId="+dataId+"&moduleId="+moduleId;
            if(StringUtils.isNotBlank(turnIndex)){
                url = url + "&turnIndex="+turnIndex;
            }
            serverResponse = ServerResponse.asSucessResponse(url,url);
        }catch (Exception e){
            logger.error("获取页面url报错"+ ExceptionUtil.getExceptionTrace(e));
            serverResponse = ServerResponse.asErrorResponse("获取页面url报错"+e.getMessage());
        }
        return serverResponse;
    }

    /**
     *    数据仓库那边需要根据 sourceId来查询 对应的所有 tableId，以及对应的tableName
     *    来源厂商只有中文 不能代码
     *    20210201 修改成只根据 sourceId
     * @param sourceId  来源协议id
     * @param sourceCode 来源系统代码
     * @param sourceFirmCode  来源厂商代码
     * @return
     */
    @RequestMapping(value = "/getStandardOutTableIdBySourceId")
    @ResponseBody
    public ServerResponse<List<PageSelectOneValue>> getStandardOutTableIdBySourceId(@RequestParam("sourceId") @NotBlank String sourceId,
                                                                                    @RequestParam("sourceCode")@NotBlank String sourceCode,
                                                                                    @RequestParam("sourceFirmCode")@NotBlank String sourceFirmCode){
        ServerResponse<List<PageSelectOneValue>> serverResponse = null;
        try{
            logger.info("查询的参数为，sourceId："+sourceId+ " sourceCode:"+sourceCode +" sourceFirmCode:"+sourceFirmCode);
            if(StringUtils.isEmpty(sourceId) || StringUtils.isEmpty(sourceCode) || StringUtils.isEmpty(sourceFirmCode) ){
                throw new NullPointerException("传入的参数值为空，请先填写sourceId/sourceCode/sourceFirmCode对应的值");
            }
            List<PageSelectOneValue> pageSelectOneValueList = externalInterfceServiceImpl.getStandardOutTableIdBySourceIdService(sourceId ,sourceCode , sourceFirmCode );
            serverResponse = ServerResponse.asSucessResponse(pageSelectOneValueList);
        }catch (Exception e){
            logger.error("查询数据报错"+ExceptionUtil.getExceptionTrace(e));
            serverResponse = ServerResponse.asErrorResponse(e.getMessage());
        }
        return serverResponse;
    }

    /**
     * 数据仓库 通过dataId判断该 数据源信息是否被使用
     * 弃用
     * @param dataId  数据仓库id
     * @return
     */
//    @IgnoreSecurity
    @RequestMapping(value = "/findStandByDataId")
    @ResponseBody
    @Deprecated
    public ServerResponse<Integer> findStandByDataId(@RequestParam("dataId") @NotBlank String dataId){
        ServerResponse<Integer> serverResponse = null;
        try{
            logger.info("数据仓库dataId是否使用查询的参数为，dataId:"+dataId);
            int useCount = externalInterfceServiceImpl.findStandByDataId(dataId);
            serverResponse = ServerResponse.asSucessResponse(useCount);
        }catch (Exception e){
            logger.error("查询数据报错"+ExceptionUtil.getExceptionTrace(e));
            serverResponse = ServerResponse.asErrorResponse("查询数据报错"+e.getMessage());
        }
        return serverResponse;
    }


//    /**
//     * 刷新数据探查信息，当数据仓库中修改时，调用这个接口刷新表 PUBLIC_DATA_INFO中的数据
//     * @param dataId   数据仓库中的参数
//     * @param tableId
//     * @return  是否刷新成功
//     */
//    @IgnoreSecurity
//    @RequestMapping(value = "/refreshDataExploration")
//    @ResponseBody
//    public ServerResponse<String> refreshDataExploration(@RequestParam("dataId") @NotBlank String dataId,
//                                                    @RequestParam("tableId")  @NotBlank String tableId,
//                                                    @RequestParam("sourceId") @NotBlank String sourceId,
//                                                    @RequestParam("project")String project){
//        logger.info("刷新数据探查信息，请求的参数为dataId："+dataId+" tableId:"+tableId +" sourceId:"+sourceId);
//        // 根据sourceId直接 修改 PUBLIC_DATA_INFO 表中的数据
//        ServerResponse<String> serverResponse = null;
//        try{
//            externalInterfceServiceImpl.refreshDataExploration(dataId,tableId,project,sourceId);
//            serverResponse = ServerResponse.asSucessResponse("刷新信息成功");
//        }catch (Exception e){
//            logger.error("刷新信息失败："+ExceptionUtil.getExceptionTrace(e));
//            serverResponse = ServerResponse.asErrorResponse(e.getMessage());
//        }
//        return serverResponse;
//    }


    /**
     *  质量检测功能需要根据 sourceId 查询对应的 标准表信息
     *   [{"tableId":"jz_resource_111"}]
     * @param sourceId  来源id
     * @return
     */
    @RequestMapping(value = "/getStandardTableBySourceId")
    @ResponseBody
    public ServerResponse<List<Map<String,String>>> getStandardTableBySourceId(@RequestParam("sourceId") @NotBlank String sourceId){
        ServerResponse<List<Map<String,String>>> serverResponse = null;
        try{
            logger.info("查询的参数为，sourceId："+sourceId);
            if(StringUtils.isNotBlank(sourceId)){
                throw new NullPointerException("传入的参数值为空，请先填写sourceId对应的值");
            }
            List<Map<String,String>> object = externalInterfceServiceImpl.getStandardTableBySourceId(sourceId);
            serverResponse = ServerResponse.asSucessResponse(object);
        }catch (Exception e){
            logger.error("查询数据报错"+ExceptionUtil.getExceptionTrace(e));
            serverResponse = ServerResponse.asErrorResponse(e.getMessage());
        }
        return serverResponse;
    }


    /**
     *
     * 根据userID查询这个id下的标准信息，如果id为 null 说明是管理员权限，获取所有的标准表信息
     * 质量检测程序使用
     * @param userId   用户的userId值
     * @return
     */
//    @IgnoreSecurity
    @RequestMapping(value = "/getObjectByUserId")
    @ResponseBody
    public ServerResponse<List<ObjectPojo>> getObjectByUserId(@RequestParam("userId")Integer userId) {
        logger.info("开始获取用户id: "+userId+"对应的所有标准表信息");
        List<ObjectPojo> list = externalInterfceServiceImpl.getObjectByUserId(userId);
        return ServerResponse.asSucessResponse(list);
    }

    /**
     * 数据仓库调用 用于查询 dataId是否被启用
     * @param resourceId  数据仓库那边的dataId
     * @return
     */
    @RequestMapping(value = "/findUsedDataresource")
    @ResponseBody
    public ServerResponse<Boolean> findUsedDataresource(@RequestParam("resourceId") @NotBlank String resourceId){
        logger.info("数据仓库dataId是否使用查询的参数为，dataId:"+resourceId);
        int useCount = externalInterfceServiceImpl.findStandByDataId(resourceId);
        return ServerResponse.asSucessResponse(useCount > 0);
    }

    /**
     * 资源注册时查询object_store_info是否存在，存在则返回，不存在则创建后返回
     * @param registerTableInfos 查询信息
     * @return
     */
    @RequestMapping(value = "/searchOrCreateObjectStoreInfo")
    @ResponseBody
//    @IgnoreSecurity
    public ServerResponse<List<RegisterTableInfo>> searchOrCreateObjectStoreInfo(@RequestBody List<RegisterTableInfo> registerTableInfos){
        ServerResponse<String> serverResponse = null;
        logger.info("资源注册时传递的查询参数为{}：",registerTableInfos);
        try{
            logger.info("开始查询或新建object_store_info及表结构信息");
            List<RegisterTableInfo> responseRegisterTableInfo = objectStoreInfoService.searchOrCreateObjectStoreInfo(registerTableInfos);
            logger.info("查询结束，相关信息为{}",responseRegisterTableInfo.toString());
            return ServerResponse.asSucessResponse(responseRegisterTableInfo);
        }catch (Exception e){
            serverResponse = ServerResponse.asErrorResponse("查询或创建object_store_info表报错："+e.getMessage());
            logger.error("查询或创建object_store_info表报错："+ExceptionUtil.getExceptionTrace(e));
        }
        logger.info("流程审批更新object表状态结束");
        return ServerResponse.asSucessResponse(registerTableInfos);

    }


}
