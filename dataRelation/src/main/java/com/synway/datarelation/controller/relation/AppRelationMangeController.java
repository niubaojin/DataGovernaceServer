package com.synway.datarelation.controller.relation;

import com.alibaba.fastjson.JSONObject;
import com.synway.common.bean.ServerResponse;
import com.synway.datarelation.constant.Common;
import com.synway.datarelation.pojo.common.ApplicationFilterQuery;
import com.synway.datarelation.pojo.common.ApplicationFilterResult;
import com.synway.datarelation.pojo.common.BloodManagementQuery;
import com.synway.datarelation.pojo.databloodline.ApplicationSystemTable;
import com.synway.datarelation.pojo.databloodline.ApplicationTableManage;
import com.synway.datarelation.pojo.databloodline.SummaryData;
import com.synway.datarelation.service.datablood.ApplicationSystemNodeService;
import com.synway.datarelation.service.datablood.CacheManageDataBloodlineService;
import com.synway.datarelation.util.ImportHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 应用血缘管理的相关接口数据
 */
@Controller
@RequestMapping(value = "/app")
public class AppRelationMangeController {
    private static Logger logger = LoggerFactory.getLogger(AppRelationMangeController.class);

    @Autowired
    private ApplicationSystemNodeService applicationSystemNodeService;
    @Autowired
    private CacheManageDataBloodlineService cacheManageDataBloodlineService;

    /**
     * 正则表达式 是否包含中文
     */
    private static final Pattern PATTERN_REG = Pattern.compile("[\u4e00-\u9fa5]");
    /**
     * 获取 应用系统个数以及 涉及表的个数
     * @return
     */
    @RequestMapping(value="/searchSummaryData")
    @ResponseBody
    public ServerResponse<SummaryData> searchSummaryData(){

        try{
            SummaryData summaryData = applicationSystemNodeService.searchSummaryData();
            return ServerResponse.asSucessResponse(summaryData);
        }catch (Exception e){
            logger.error("获取到应用系统个数以及涉及表报错：", e );
            return ServerResponse.asErrorResponse("获取到应用系统个数以及涉及表报错："+e.getMessage());
        }
    }


    /**
     * 获取应用血缘管理的搜索信息
     * @param bloodManagementQuery  查询参数
     * @return
     */
    @RequestMapping(value="/searchApplicationBloodTable")
    @ResponseBody
    public ServerResponse<ApplicationTableManage> searchApplicationBloodTable(@RequestBody BloodManagementQuery bloodManagementQuery){
        try{
            logger.info("搜索应用血缘管理的查询参数为"+ JSONObject.toJSONString(bloodManagementQuery));
            ApplicationTableManage applicationSystemTable = applicationSystemNodeService.searchApplicationBloodTable(bloodManagementQuery);
            return ServerResponse.asSucessResponse(applicationSystemTable);
        }catch (Exception e){
            logger.error("获取到应用血缘展示页面报错：", e);
            return ServerResponse.asErrorResponse("获取到应用血缘展示页面报错："+e.getMessage());
        }
    }

    /**
     * 添加一条新的应用血缘数据
     * @param applicationSystemTable
     * @return
     */
    @RequestMapping(value="/addApplicationBlood")
    @ResponseBody
    public ServerResponse<String> addApplicationBlood(@RequestBody ApplicationSystemTable applicationSystemTable){
        try{
            // 物理表名不能包含中文
            if(PATTERN_REG.matcher(applicationSystemTable.getTableNameEn()).find()){
                throw new NullPointerException("物理表名中不能包含中文信息");
            }
            boolean flag = applicationSystemNodeService.addApplicationBlood(applicationSystemTable);
            if(flag){
                return ServerResponse.asSucessResponse("添加数据成功","添加数据成功");
            }else{
                return ServerResponse.asSucessResponse("添加数据失败","添加数据失败");
            }

        }catch (Exception e){
            logger.error("添加一条应用血缘的数据报错：", e);
            return ServerResponse.asErrorResponse("添加应用血缘的数据报错："+e.getMessage());
        }
    }


    /**
     * 删除一条应用血缘数据
     * @param applicationSystemTable
     * @return
     */
    @RequestMapping(value="/deleteApplicationBlood")
    @ResponseBody
    public ServerResponse<String> deleteApplicationBlood(@RequestBody ApplicationSystemTable applicationSystemTable){
        try{
            boolean flag = applicationSystemNodeService.deleteApplicationBlood(applicationSystemTable,true);
            if(flag){
                return ServerResponse.asSucessResponse("删除数据成功","删除数据成功");
            }else{
                return ServerResponse.asSucessResponse("删除数据失败","删除数据失败");
            }
        }catch (Exception e){
            logger.error("删除应用血缘的数据报错：", e);
            return ServerResponse.asErrorResponse("删除应用血缘的数据报错："+e.getMessage());
        }
    }

    /**
     * 删除多条应用血缘数据
     * @param applicationSystemTableList
     * @return
     */
    @RequestMapping(value="/deleteApplicationBloodList")
    @ResponseBody
    public ServerResponse<String> deleteApplicationBloodList(@RequestBody List<ApplicationSystemTable> applicationSystemTableList){
        try{
            if(applicationSystemTableList == null || applicationSystemTableList.isEmpty()){
                return ServerResponse.asErrorResponse("删除数据失败,传入参数为空");
            }
            int okNumber = 0;
            int errorNumber = 0;
            for(ApplicationSystemTable applicationSystemTable:applicationSystemTableList){
                boolean flag = applicationSystemNodeService.deleteApplicationBlood(applicationSystemTable,false);
                if (flag) {
                    okNumber++;
                } else {
                    errorNumber++;
                }
            }
            cacheManageDataBloodlineService.getApplicationDataBloodCache();
            return ServerResponse.asSucessResponse("删除成功的数据量为："+okNumber+" 删除失败的数据量为："+errorNumber);
        }catch (Exception e){
            logger.error("删除应用血缘的数据报错：", e);
            return ServerResponse.asErrorResponse("删除应用血缘的数据报错："+e.getMessage());
        }
    }

    /**
     * excel文件模板下载
     * @param response
     */
    @RequestMapping(value="/downloadTemplateExcel",produces="application/json;charset=utf-8")
    @ResponseBody()
    public void downloadTemplateExcel(HttpServletResponse response) {
        logger.info("=======开始下载标准化模板excel文档=======");
        applicationSystemNodeService.downloadTemplateExcel(response);
        logger.info("=======开始下载标准化模板excel文档结束========");

    }


    /**
     * 列表中数据导出
     * @param response
     * @param applicationSystemTableList 表格中的数据
     */
    @RequestMapping(value="/exportTableExcel",produces="application/json;charset=utf-8")
    @ResponseBody()
    public void exportTableExcel(HttpServletResponse response,@RequestBody List<ApplicationSystemTable> applicationSystemTableList) {
        logger.info("=======开始下载应用血缘数据管理excel文档=======");
        applicationSystemNodeService.exportTableExcel(response,applicationSystemTableList);
        logger.info("=======下载应用血缘数据管理excel文档结束========");

    }

    /**
     * 获取提示信息 为空时显示全部
     * @param searchValue
     * @return
     */
    @RequestMapping("queryConditionSuggestion")
    @ResponseBody()
    public ServerResponse<List<String>> queryConditionSuggestion(String searchValue){
        logger.info("searchValue:"+searchValue);
        try{
            List<String> result = applicationSystemNodeService.queryConditionSuggestion(searchValue);
            return ServerResponse.asSucessResponse(result);
        }catch (Exception e){
            logger.error("获取提示信息报错", e);
            return ServerResponse.asErrorResponse("获取提示信息报错"+e.getMessage());
        }
    }


    /**
     * 新增应用血缘管理中 系统名称  一级模块 二级模块点击后的筛选值
     * @param applicationFilterQuery
     * @return
     */
    @RequestMapping("queryApplicationFilterQuery")
    @ResponseBody()
    public ServerResponse<ApplicationFilterResult> queryApplicationFilterQuery(@RequestBody ApplicationFilterQuery applicationFilterQuery){
        try{
            ApplicationFilterResult applicationFilterResult = applicationSystemNodeService.queryApplicationFilterQuery(applicationFilterQuery);
            return ServerResponse.asSucessResponse(applicationFilterResult);
        }catch (Exception e){
            logger.error("获取应用血缘管理报错", e);
            return ServerResponse.asErrorResponse("获取应用血缘管理报错"+e.getMessage());
        }
    }

    /**
     * 根据表英文名获取表中文名信息
     * 可能存在返回的数据为空 说明没找到中文名
     * @param tableName
     * @return
     */
    @RequestMapping("queryTableChByEn")
    @ResponseBody()
    public ServerResponse<String> queryTableChByEn(String tableName){
        try{
            if(StringUtils.isBlank(tableName)){
                return ServerResponse.asErrorResponse("查询参数为空");
            }
            String result = applicationSystemNodeService.queryTableChByEn(tableName);
            return ServerResponse.asSucessResponse(result);
        }catch (Exception e){
            logger.error("获取表中文名报错", e);
            return ServerResponse.asErrorResponse("获取表中文名报错"+e.getMessage());
        }
    }


    /**
     * 上传文件保存应用血缘信息
     * 20210318 使用easyExcel来读写数据
     * @param file
     * @return
     */
    @RequestMapping(value="/importApplicationSystemExcel")
    @ResponseBody
    public ServerResponse importApplicationSystemExcel(@RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        List errorList = null;
        if (StringUtils.containsIgnoreCase(fileName, Common.XLS) || StringUtils.containsIgnoreCase(fileName, Common.XLSX)) {
            List<Map> list = ImportHelper.importExcel3(file);
            logger.info("从excel中获取到的数据为{}", list);
            if(list.size() == 0){
                return ServerResponse.asErrorResponse("excel文件中数据量为0或者该excel文件非模板文件");
            }
            errorList = applicationSystemNodeService.importApplicationSystemExcel(list);
        } else {
            return ServerResponse.asErrorResponse("目前只支持Excel文件导入");
        }
        if(errorList.size() >0){
            logger.info("导入失败的数据为："+JSONObject.toJSONString(errorList));
            return ServerResponse.asSucessResponse("存在格式不正确的数据",errorList);
        }else{
            logger.info("应用血缘导入成功");
            return ServerResponse.asSucessResponse(errorList);
        }
    }

    /**
     * 导入失败的文件
     * @param param
     * @param response
     */
    @RequestMapping(value = "/exportApplicationSystemError", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public void exportTableRegisterErrorMessage(@RequestBody List<Map<String,String>> param, HttpServletResponse response) {
        try {
            applicationSystemNodeService.exportApplicationSystemError(param, response);
        } catch (Exception e) {
            logger.info(String.format("表登记信息导入失败信息导出错误"));
        }
    }

}
