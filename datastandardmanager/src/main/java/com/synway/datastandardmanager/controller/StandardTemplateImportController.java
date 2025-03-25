package com.synway.datastandardmanager.controller;

import com.alibaba.fastjson.JSONArray;
import com.synway.common.bean.ServerResponse;
import com.synway.datastandardmanager.pojo.standardtemplateexcel.ObjectFieldSheet;
import com.synway.datastandardmanager.service.StandardTemplateImportService;
import com.synway.datastandardmanager.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import java.util.Date;
import java.util.List;

/**
 * @author wangdongwei
 * @ClassName StandardTemplateImportController
 * @description 标准文件的导入 以及模板文件的导出
 * @date 2020/12/8 15:21
 */
@Controller
public class StandardTemplateImportController {
    private Logger logger = LoggerFactory.getLogger(StandardTemplateImportController.class);
    @Autowired
    private StandardTemplateImportService standardTemplateImportServiceImpl;

    /**
     * 导入标准文件
     *
     * @param file 文件
     * @param objectSheetPage 标准信息页脚
     * @param objectFieldSheetPage 标准字段页脚
     */
    @RequestMapping(value="/importStandardExcel")
    @ResponseBody
    public ServerResponse<String> importStandardExcel(@RequestParam("file") MultipartFile[] file,@RequestParam Integer objectSheetPage,
                                                      @RequestParam Integer objectFieldSheetPage){
        logger.info("======开始导入标准信息=====");
        String importMessage = standardTemplateImportServiceImpl.importObjectInfoExcel(file,objectSheetPage,objectFieldSheetPage);
        logger.info("======导入标准信息结束=====");
        return ServerResponse.asSucessResponse(importMessage,importMessage);
    }

    /**
     * 数据标准导出(表信息和字段导出)
     * @param response
     * @param tableIdList 标准协议id列表
     * @return
     */
    @RequestMapping(value = "/downloadObjectInfo",produces="application/json;charset=utf-8")
    @ResponseBody
    public void downloadObjectInfoExcel(HttpServletResponse response,
                                        @RequestBody @Valid List<String> tableIdList){
        standardTemplateImportServiceImpl.downloadObjectInfoExcel(response,"数据标准导出"+
                DateUtil.formatDateTime(new Date(),DateUtil.DEFAULT_PATTERN_DATE_SIMPLE),tableIdList);

    }

    /**
     * 数据标准导出sql文件(表信息和字段导出)
     * @param response
     * @param tableIdList 标准协议id列表
     * @return
     */
    @RequestMapping(value="/downloadObjectInfoSql",produces="application/json;charset=utf-8")
    @ResponseBody
    public void downloadObjectInfoSql(HttpServletResponse response,
                                      @RequestBody @Valid List<String> tableIdList){
        logger.info("开始导出标准信息的sql语句");
        standardTemplateImportServiceImpl.downloadObjectInfoSql(response,"数据标准导出"+
                DateUtil.formatDateTime(new Date(),DateUtil.DEFAULT_PATTERN_DATE_SIMPLE),tableIdList);
        logger.info("导出标准信息的sql语句结束");

    }

//
//    /**
//     * 下载标准excel的模板文档
//     * @param response
//     */
//    @RequestMapping(value="/downloadStandardTemplateExcel",produces="application/json;charset=utf-8")
//    @ResponseBody()
//    public void downloadTemplateExcel(HttpServletResponse response) {
//        logger.info("=======开始下载标准化模板excel文档=======");
//        standardTemplateImportServiceImpl.downloadTemplateExcel(response);
//        logger.info("=======开始下载标准化模板excel文档结束========");
//
//    }

    /**
     * 字段模板excel文件下载
     * @param response
     */
    @RequestMapping(value="/downloadFieldsTempExcel")
    @ResponseBody()
    public void downloadFieldsTempExcel(HttpServletResponse response) {
        logger.info("=======开始字段模板excel文档=======");
        standardTemplateImportServiceImpl.downloadFieldTempExcel(response);
        logger.info("=======开始字段模板excel文档结束========");
    }

    /**
     * 解析Excel导入字段信息
     * @param multipartFile 导入文件
     * @param type  0：全部导入(fieldsInfo空)，1：字段数据fieldsInfo空  2：字段对标信息（fieldsInfo必填）
     * @param fieldsInfo    List<FieldInfo> 对象JSONArray字符串
     * @return
     */
    @PostMapping(value = "/aynsisFileInfo")
    @ResponseBody
    public ServerResponse<List<ObjectFieldSheet>> analysisFieldExcel(@RequestBody MultipartFile multipartFile, int type, String fieldsInfo){
        if (multipartFile==null){
            return ServerResponse.asErrorResponse("文件上传失败");
        }
        List<ObjectFieldSheet> list = null;
        if (StringUtils.isNotBlank(fieldsInfo)){
            list = JSONArray.parseArray(fieldsInfo,ObjectFieldSheet.class);
        }
        List<ObjectFieldSheet> newFieldInfos = standardTemplateImportServiceImpl.analysisFieldExcel(multipartFile,list,type);
        return ServerResponse.asSucessResponse(newFieldInfos);
    }


}
