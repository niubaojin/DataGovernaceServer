package com.synway.datastandardmanager.controller;


import com.synway.common.bean.ServerResponse;
import com.synway.datastandardmanager.entity.dto.StandardDictionaryDTO;
import com.synway.datastandardmanager.entity.pojo.StandardizeOriginalDFEntity;
import com.synway.datastandardmanager.entity.pojo.StandardizeOriginalDictEntity;
import com.synway.datastandardmanager.entity.vo.KeyValueVO;
import com.synway.datastandardmanager.entity.vo.SelectFieldVO;
import com.synway.datastandardmanager.entity.vo.TreeNodeValueVO;
import com.synway.datastandardmanager.service.OriginalDictionaryService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 原始字典代码集管理
 *
 * @author nbj
 * @@date 2025年8月19日17:06:48
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/originalDictionary")
public class OriginalDictionaryController {

    @Autowired
    private OriginalDictionaryService service;

    /**
     * 获取原始字典管理左侧树
     */
    @RequestMapping(value = "/getLeftTreeInfo")
    public ServerResponse<List<TreeNodeValueVO>> getLeftTreeInfo() {
        return ServerResponse.asSucessResponse(service.getLeftTreeInfo());
    }

    /**
     * 添加/修改原始字典
     */
    @RequestMapping(value = "/addOrUpdateOneOriginalDictionary")
    public ServerResponse<String> addOrUpdateOneOriginalDictionary(@RequestBody @Valid StandardizeOriginalDictEntity standardizeOriginalDict) {
        String message = service.addOrUpdateOneOriginalDictionary(standardizeOriginalDict);
        return ServerResponse.asSucessResponse(message, message);
    }

    /**
     * 根据原始字典id和名称删除原始字典
     *
     * @param id             字典id
     * @param dictionaryName 字典名称
     */
    @RequestMapping(value = "/deleteOneOriginalDictionary")
    public ServerResponse<String> deleteOneOriginalDictionary(@RequestParam("id") String id,
                                                              @RequestParam("dictionaryName") String dictionaryName) {
        String message = service.deleteOneOriginalDictionary(id, dictionaryName);
        return ServerResponse.asSucessResponse(message, message);
    }

    /**
     * 根据原始字典id和名称查询原始字典
     *
     * @param id             字典id
     * @param dictionaryName 字典名称
     * @return
     */
    @RequestMapping(value = "/searchDictionaryByIdAndName")
    public ServerResponse<StandardizeOriginalDictEntity> searchDictionaryByIdAndName(@RequestParam("id") String id,
                                                                                     @RequestParam("dictionaryName") String dictionaryName) {
        return ServerResponse.asSucessResponse(service.searchDictionaryByIdAndName(id, dictionaryName));
    }

    /**
     * 关键字搜索对应标准下拉框
     *
     * @param searchText 关键字搜索
     */
    @RequestMapping(value = "/searchDictionaryListInfo")
    public ServerResponse<List<SelectFieldVO>> searchDictionaryListInfo(String searchText) {
        return ServerResponse.asSucessResponse(service.searchStandardDictionaryListInfo(searchText));
    }

    /**
     * 根据codeId获取对应的标准代码列表
     *
     * @param codeId 字典codeId
     */
    @RequestMapping(value = "/searchDictionaryValueListByCodeId")
    public ServerResponse<List<KeyValueVO>> searchDictionaryValueListByCodeId(@RequestParam("codeId") String codeId) {
        return ServerResponse.asSucessResponse(service.searchDictionaryValueListByCodeId(codeId));
    }

    /**
     * 下载原始字典项信息：未选中任何记录时导出列表中全部记录；有选中记录时导出选中记录
     *
     * @param data：列表中的数据
     */
    @RequestMapping(value = "/downloadDictionaryFieldExcel", produces = "application/json;charset=utf-8")
    public void downloadDictionaryFieldExcel(HttpServletResponse response, @RequestBody StandardDictionaryDTO data) {
        service.downloadDictionaryFieldExcel(response, data, "原始字典管理", new StandardizeOriginalDFEntity());
    }

    /**
     * 下载原始字典项信息
     */
    @RequestMapping(value = "/downloadDictionaryExcelTemplate", produces = "application/json;charset=utf-8")
    public void downloadDictionaryExcelTemplate(HttpServletResponse response) {
        service.downloadDictionaryExcelTemplate(response, "原始字典模板");
    }

    /**
     * 导入原始字典项数据
     *
     * @param file
     * @param id   公共数据项组id
     */
    @RequestMapping(value = "/importDictionaryFieldExcel")
    public ServerResponse<List<StandardizeOriginalDFEntity>> importDictionaryFieldExcel(@RequestParam("file") MultipartFile file, @RequestParam("id") String id) {
        return ServerResponse.asSucessResponse(service.importDictionaryFieldExcel(file, id));
    }

    /**
     * 仓库探查分析中字段探查原始字典的下拉内容
     */
    @RequestMapping(value = "/getOriginalDictionaryNameList")
    public ServerResponse<List<KeyValueVO>> getOriginalDictionaryNameList() {
        return ServerResponse.asSucessResponse(service.getOriginalDictionaryNameList());
    }

}
