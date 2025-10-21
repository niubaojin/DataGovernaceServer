package com.synway.datastandardmanager.controller;

import com.synway.common.bean.ServerResponse;
import com.synway.datastandardmanager.entity.dto.CommonDTO;
import com.synway.datastandardmanager.entity.vo.importDownload.ObjectFieldSheetVO;
import com.synway.datastandardmanager.service.DataSetStandardDownloadService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author wangdongwei
 * @ClassName StandardTemplateImportController
 * @description 标准文件的导入导出，及sql文件的导出
 * @date 2020/12/8 15:21
 */
@RestController
public class DataSetStandardDownloadController {

    @Autowired
    private DataSetStandardDownloadService service;

    /**
     * 导入标准文件
     *
     * @param file                 文件
     * @param objectSheetPage      标准信息页脚
     * @param objectFieldSheetPage 标准字段页脚
     */
    @RequestMapping(value = "/importStandardExcel")
    @ResponseBody
    public ServerResponse<String> importStandardExcel(@RequestParam("file") MultipartFile[] file,
                                                      @RequestParam Integer objectSheetPage,
                                                      @RequestParam Integer objectFieldSheetPage) {
        String importMessage = service.importObjectInfoExcel(file, objectSheetPage, objectFieldSheetPage);
        return ServerResponse.asSucessResponse(importMessage, importMessage);
    }

    /**
     * 数据标准导出(表信息和字段导出)
     *
     * @param tableIdList 标准协议id列表
     */
    @RequestMapping(value = "/downloadObjectInfo", produces = "application/json;charset=utf-8")
    @ResponseBody
    public void downloadObjectInfoExcel(HttpServletResponse response,
                                        @RequestBody @Valid List<String> tableIdList) throws IOException {
        service.downloadObjectInfoExcel(response, tableIdList);
    }

    /**
     * 数据标准导出sql文件(表信息和字段导出)
     *
     * @param tableIdList 标准协议id列表
     */
    @RequestMapping(value = "/downloadObjectInfoSql", produces = "application/json;charset=utf-8")
    @ResponseBody
    public void downloadObjectInfoSql(HttpServletResponse response, @RequestBody List<String> tableIdList) {
        service.downloadObjectInfoSql(response, tableIdList);
    }

    /**
     * 字段模板excel文件下载
     */
    @RequestMapping(value = "/downloadFieldsTempExcel")
    @ResponseBody()
    public void downloadFieldsTempExcel(HttpServletResponse response) {
        service.downloadFieldTempExcel(response);
    }

    /**
     * 解析Excel导入字段信息
     *
     * @param multipartFile 导入文件
     * @param type          0：全部导入(fieldsInfo空)，1：字段数据fieldsInfo空  2：字段对标信息（fieldsInfo必填）
     * @param fieldsInfo    List<FieldInfo> 对象JSONArray字符串
     * @return
     */
    @PostMapping(value = "/aynsisFileInfo")
    @ResponseBody
    public ServerResponse<List<ObjectFieldSheetVO>> analysisFieldExcel(@RequestBody MultipartFile multipartFile, int type, String fieldsInfo) {
        return ServerResponse.asSucessResponse(service.analysisFieldExcel(multipartFile));
    }

    /**
     * 导出标准表建表语句
     * @author nbj
     * @date 2025年9月4日15:26:05
     */
    @RequestMapping(value = "/downloadCreateTableSql", produces = "application/json;charset=utf-8")
    @ResponseBody
    public void downloadCreateTableSql(HttpServletResponse response, @RequestBody CommonDTO dto) {
        String dsType = dto.getDsType();
        List<String> tableIdList = dto.getTableIdList();
        service.downloadCreateTableSql(response, tableIdList, dsType);
    }


}
