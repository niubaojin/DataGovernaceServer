package com.synway.datastandardmanager.controller;

import com.synway.common.bean.ServerResponse;
import com.synway.datastandardmanager.entity.pojo.SameWordEntity;
import com.synway.datastandardmanager.service.DataSemanticManageService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 数据语义的相关接口
 *
 * @author wangdongwei
 * @ClassName SemanticTableManageController
 * @description 数据语义的相关接口
 */
@RestController
public class DataSemanticManageController {

    @Autowired()
    private DataSemanticManageService service;


    /**
     * 数据语义管理页面表数据
     *
     * @param sameId   语义id
     * @param word     语义英文名称
     * @param wordName 语义中文名称
     */
//    @IgnoreSecurity
    @RequestMapping("/semanticTableManage")
    @ResponseBody
    public ServerResponse<List<SameWordEntity>> semanticTableManage(String sameId, String wordName, String word) {
        return ServerResponse.asSucessResponse(service.findByCondition(sameId, wordName, word));
    }

    /**
     * 下载(导出)数据数据语义excel文件
     *
     * @param response
     * @param sameId        主键id
     * @param wordName      语义中文名
     * @param word          语义英文名
     * @param elementObject 主体类型
     */
//    @IgnoreSecurity
    @RequestMapping(value = "semanticTableExport", produces = "application/json;charset=utf-8")
    @ResponseBody()
    public void semanticTableExport(HttpServletResponse response,
                                    @RequestParam("sameId") String sameId,
                                    @RequestParam("wordName") String wordName,
                                    @RequestParam("word") String word,
                                    @RequestParam("elementObject") String elementObject) {
        service.semanticTableExport(response, sameId, wordName, word, elementObject);
    }

    /**
     * 添加新的语义信息
     */
//    @IgnoreSecurity
    @RequestMapping(value = "/addOneSemanticManage", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> addOneSemanticManage(@RequestBody @Valid SameWordEntity sameword) {
        String message = service.addOneSemanticManage(sameword);
        return ServerResponse.asSucessResponse(message, message);
    }

    /**
     * 删除一条语义信息
     */
//    @IgnoreSecurity
    @RequestMapping(value = "/delOneSemanticManage", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> delOneSemanticManage(@RequestBody SameWordEntity delSameword) {
        String message = service.delOneSemanticManage(delSameword);
        return ServerResponse.asSucessResponse(message, message);
    }

    /**
     * 批量删除语义信息
     *
     * @param delSameword 数据语义中文名称列表
     */
//    @IgnoreSecurity
    @RequestMapping(value = "/delAllSemanticManage", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> delAllSemanticManage(@RequestBody List<SameWordEntity> delSameword) {
        String message = service.delAllSemanticManage(delSameword);
        return ServerResponse.asSucessResponse(message, message);
    }

    /**
     * 数据语义管理页面 关联元素集数据
     */
//    @IgnoreSecurity
    @RequestMapping(value = "/getMetadataDefineTableBySameid")
    @ResponseBody
    public ServerResponse getMetadataDefineTableBySameid(int pageIndex, int pageSize, String sameId) {
        return ServerResponse.asSucessResponse(service.getMetadataDefineTableBySameid(pageIndex, pageSize, sameId));
    }

    /**
     * 数据语义管理页面 关联元素集数据
     */
//    @IgnoreSecurity
    @RequestMapping(value = "/uploadSemanticFile", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> uploadSemanticFile(@RequestParam("semanticFile") MultipartFile semanticFile) {
        String message = service.uploadSemanticFile(semanticFile);
        return ServerResponse.asSucessResponse(message, message);
    }

    //下载语义表管理的模板文件
//    @IgnoreSecurity
    @RequestMapping(value = "/downSemanticTableTemplateFile", produces = "application/json;charset=utf-8")
    @ResponseBody()
    public void downSemanticTableTemplateFile(HttpServletResponse response) {
        service.downSemanticTableTemplateFile(response);
    }

}
