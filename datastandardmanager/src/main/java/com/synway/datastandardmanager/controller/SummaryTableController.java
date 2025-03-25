package com.synway.datastandardmanager.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.synway.datastandardmanager.interceptor.CurrentUser;
import com.synway.datastandardmanager.interceptor.IgnoreSecurity;
import com.synway.datastandardmanager.pojo.AuthorizedUser;
import com.synway.datastandardmanager.pojo.PageSelectOneValue;
import com.synway.datastandardmanager.pojo.summaryobjectpage.CommonMap;
import com.synway.datastandardmanager.pojo.summaryobjectpage.SummaryObjectTable;
import com.synway.datastandardmanager.pojo.summaryobjectpage.SummaryQueryParams;
import com.synway.datastandardmanager.pojo.summaryobjectpage.SummaryTable;
import com.synway.datastandardmanager.service.SummaryTableService;
import com.synway.datastandardmanager.util.ExceptionUtil;
import com.synway.datastandardmanager.util.RestTemplateHandle;
import com.synway.common.bean.ServerResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.*;

/**
 * 数据集管理页面的相关接口
 *
 * @author wangdongwei
 * @ClassName SummaryTableController
 * @description 数据集管理的相关请求接口
 * @date 2020/11/27 15:58
 */
@Controller
public class SummaryTableController {
    private static Logger logger = LoggerFactory.getLogger(SummaryTableController.class);
    @Autowired
    private SummaryTableService summaryTableServiceImpl;
    @Autowired
    private RestTemplateHandle restTemplateHandle;


    /**
     * 数据集管理页面 查询汇总表的相关数据  使用前端分页
     * @param summaryQueryParams
     * @return
     */
    @RequestMapping(value="/searchSummaryTable",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<SummaryTable> searchSummaryTable(@RequestBody @Valid SummaryQueryParams summaryQueryParams,
                                                           BindingResult bindingResult){
        logger.info("汇总信息的查询参数为："+ JSONObject.toJSONString(summaryQueryParams));
        ServerResponse<SummaryTable> serverResponse = null;
        try{
            if(bindingResult.hasErrors()){
                serverResponse = ServerResponse.asErrorResponse(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
                return serverResponse;
            }
            SummaryTable summaryTable = summaryTableServiceImpl.searchSummaryTable(summaryQueryParams);
            serverResponse = ServerResponse.asSucessResponse(summaryTable);
        }catch (Exception e){
            logger.error("获取汇总表信息报错"+ ExceptionUtil.getExceptionTrace(e));
            serverResponse = ServerResponse.asErrorResponse("获取汇总表信息报错"+e.getMessage());
        }
        return serverResponse;
    }

    /**
     * 查询主分类的相关信息
     * @param mainClassify   二个主分类的英文信息 dataOrganizationClassify/dataSourceClassify
     * @return 对应的所有一级分类信息  null：查询失败
     */
    @RequestMapping("/getPrimaryClassifyData")
    @ResponseBody
    public  ServerResponse<List<PageSelectOneValue>>  getPrimaryClassifyData(
            @RequestParam("mainClassify")String  mainClassify){
        logger.info("开始查询【"+mainClassify+"】对应的一级分类信息");
        ServerResponse<List<PageSelectOneValue>> serverResponse = null;
        try{
            List<PageSelectOneValue> pageSelectOneValues = summaryTableServiceImpl.getPrimaryClassifyData(mainClassify);
            serverResponse = ServerResponse.asSucessResponse(pageSelectOneValues);
            logger.info("getPrimaryClassifyData()方法运行结束，返回结果");
        }catch (Exception e){
            serverResponse = ServerResponse.asErrorResponse("查询一级分类信息报错"+e.getMessage());
            logger.error("查询一级分类信息报错"+ExceptionUtil.getExceptionTrace(e));
        }
        return serverResponse;
    }


    /**
     * 根据选择的数据资源目录的二大分类之一 和 一级分类信息 来获取二级分类信息
     * 2020/2/21 一级分类标识改变，获取二级分类改变
     * @param mainClassify 二个主分类的英文信息 dataOrganizationClassify/dataSourceClassify
     * @param primaryClassifyCode 一级分类的代码值
     * @return 对应的所有一级分类信息
     */
    @RequestMapping("/getSecondaryClassifyData")
    @ResponseBody
    public  ServerResponse<List<PageSelectOneValue>> getSecondaryClassifyData(
            @RequestParam("mainClassify")String  mainClassify,
            @RequestParam("primaryClassifyCode")String  primaryClassifyCode){
        // 其实 我也很好奇 为什么只能查询一个，但是既然马佳大佬这么写 那我就只查询一个吧
        logger.info("开始查询主类别为【"+mainClassify+"】和一级分类为【"+primaryClassifyCode+"】对应的二级分类信息");
        ServerResponse<List<PageSelectOneValue>> serverResponse = null;
        try{
            List<PageSelectOneValue> pageSelectOneValues = summaryTableServiceImpl.getSecondaryClassifyData(
                    mainClassify,primaryClassifyCode);
            serverResponse = ServerResponse.asSucessResponse(pageSelectOneValues);
            logger.info("getSecondaryClassifyData()方法运行结束，返回结果");
        }catch (Exception e){
            serverResponse = ServerResponse.asErrorResponse("查询二级分类信息报错"+e.getMessage());
            logger.error("查询二级分类信息报错"+ExceptionUtil.getExceptionTrace(e));
        }
        return serverResponse;
    }

    /**
     * 根据选择的数据组织分类的二级信息查询三级信息
     * 2021/11/16
     * @param primaryClassifyCode 二级分类码值
     * @param secondClassifyCode 三级分类的代码值
     * @return 对应的所有三级分类信息
     */
    @RequestMapping("/getThreeClassifyData")
    @ResponseBody
    public ServerResponse<List<PageSelectOneValue>> getThreeClassifyData(
            @RequestParam("primaryClassifyCode")String primaryClassifyCode,
            @RequestParam("secondClassifyCode")String secondClassifyCode){
        logger.info("开始查询原始库的三级分类:三级分类码值为:{}"+primaryClassifyCode+"三级分类码值为:{}"+secondClassifyCode);
        ServerResponse<List<PageSelectOneValue>> serverResponse = null;
        try {
            List<PageSelectOneValue> pageSelectOneValues = summaryTableServiceImpl.getThreeClassifyData(primaryClassifyCode,secondClassifyCode);
            serverResponse = ServerResponse.asSucessResponse(pageSelectOneValues);
            logger.info("getThreeClassifyData()方法运行结束，返回结果");
        } catch (Exception e) {
            serverResponse = ServerResponse.asErrorResponse("查询三级分类信息报错"+e.getMessage());
            logger.error("查询三级分类信息报错"+ExceptionUtil.getExceptionTrace(e));
        }
        return serverResponse;
    }


    /**
     *  获取资源状况  启用(122) 停用(22)
     * @return
     */
    @RequestMapping("/getResourceStatus")
    @ResponseBody
    public  ServerResponse<List<PageSelectOneValue>> getResourceStatus(){
        // 其实 我也很好奇 为什么只能查询一个，但是既然马佳大佬这么写 那我就只查询一个吧
        ServerResponse<List<PageSelectOneValue>> serverResponse = null;
        try{
            List<PageSelectOneValue> pageSelectOneValues = summaryTableServiceImpl.getResourceStatus();
            serverResponse = ServerResponse.asSucessResponse(pageSelectOneValues);
        }catch (Exception e){
            serverResponse = ServerResponse.asErrorResponse("查询资源状况信息报错"+e.getMessage());
            logger.error("查询资源状况信息报错"+ExceptionUtil.getExceptionTrace(e));
        }
        return serverResponse;
    }

    /**
     * 获取提示信息 为空时显示全部
     * 支持数据名、真实表名、资源标识模糊匹配
     * @param searchValue
     * @return
     */
    @RequestMapping("queryConditionSuggestion")
    @ResponseBody()
    public ServerResponse<List<String>> queryConditionSuggestion(String searchValue){
        logger.info("searchValue:"+searchValue);
        try{
            List<String> result = summaryTableServiceImpl.queryConditionSuggestion(searchValue);
            return ServerResponse.asSucessResponse(result);
        }catch (Exception e){
            logger.error("获取提示信息报错"+ExceptionUtil.getExceptionTrace(e));
            return ServerResponse.asErrorResponse("获取提示信息报错"+e.getMessage());
        }
    }

    //    /**
//     *  1.9弃用
//     * 下载汇总表的相关信息
//     * 未选中任何记录时导出列表中全部记录；有选中记录时导出选中记录
//     * @param response
//     * @param summaryObjectTableList  列表中的数据
//     */
//    @RequestMapping(value="/downloadSummaryTableExcel")
//    @ResponseBody()
//    public void downloadSummaryTableExcel(HttpServletResponse response,
//                                          @RequestBody List<SummaryObjectTable> summaryObjectTableList) {
//        logger.info("=======开始下载汇总表的相关信息=======");
//        summaryTableServiceImpl.downloadSummaryTableExcel(response,summaryObjectTableList);
//        logger.info("=======下载汇总表的相关信息结束========");
//
//    }

//    /**
//     * 1.9弃用
//     * 根据tableId获取质量检测配置任务是否存在
//     * @param tableId  表协议ID
//     * @return
//     */
//    @RequestMapping("checkDataCommonConfigByTableId")
//    @ResponseBody()
//    public ServerResponse<Boolean> checkDataCommonConfigByTableId(@RequestParam("tableId")String tableId){
//        // /dataQualityManage/internalService/checkDataCommonConfigByTableId
//        try{
//            if(StringUtils.isBlank(tableId)){
//                return ServerResponse.asSucessResponse("tableId参数为空，无法查询数据",false);
//            }
//            logger.info("查询质量检测配置任务信息,tableId:"+tableId);
//            JSONObject object = restTemplateHandle.checkDataCommonConfigByTableId(tableId);
//            if(object == null){
//                logger.error("查询质量检测配置任务信息报错，返回对象为空");
//                return ServerResponse.asSucessResponse("查询质量检测配置任务信息报错，返回对象为空",false);
//            }
//            Integer statusReturn = object.getInteger("status");
//            if(statusReturn == 1){
//                boolean returnMsg = object.getObject("result",boolean.class);
//                return ServerResponse.asSucessResponse(returnMsg);
//            }else{
//                logger.error("查询质量检测配置任务信息报错："+object.getString("msg"));
//                return ServerResponse.asSucessResponse("查询质量检测配置任务信息报错："+object.getString("msg"),false);
//            }
//        }catch (Exception e){
//            logger.error("查询质量检测配置任务信息报错"+ExceptionUtil.getExceptionTrace(e));
//            return ServerResponse.asSucessResponse("查询质量检测配置任务信息报错"+e.getMessage(),false);
//        }
//    }





}
