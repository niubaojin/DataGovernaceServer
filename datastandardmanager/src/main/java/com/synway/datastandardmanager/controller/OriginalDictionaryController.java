package com.synway.datastandardmanager.controller;


import com.synway.common.bean.ServerResponse;
import com.synway.datastandardmanager.exceptionhandler.ErrorCode;
import com.synway.datastandardmanager.exceptionhandler.SystemException;
import com.synway.datastandardmanager.pojo.OneSuggestValue;
import com.synway.datastandardmanager.pojo.PageSelectOneValue;
import com.synway.datastandardmanager.pojo.labelmanage.LabelTreeNodeVue;
import com.synway.datastandardmanager.pojo.originalDictionary.OriginalDictionaryFieldPojo;
import com.synway.datastandardmanager.pojo.originalDictionary.OriginalDictionaryParameter;
import com.synway.datastandardmanager.pojo.originalDictionary.OriginalDictionaryPojo;
import com.synway.datastandardmanager.service.OriginalDictionaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 原始字典管理的相关接口
 *
 * @author obito
 * @version 1.0
 */
@RestController
@Slf4j
@RequestMapping("/originalDictionary")
@Validated
public class OriginalDictionaryController {

    @Autowired
    private OriginalDictionaryService originalDictionaryService;

    /**
     * 获取原始字典管理左侧树
     *
     * @return
     */
    @RequestMapping(value = "/getLeftTreeInfo")
    public ServerResponse<List<LabelTreeNodeVue>> getLeftTreeInfo() {
        List<LabelTreeNodeVue> leftTreeInfo = originalDictionaryService.getLeftTreeInfo();
        return ServerResponse.asSucessResponse(leftTreeInfo);
    }

    /**
     * 添加/修改原始字典
     *
     * @param originalDictionaryPojo 原始字典pojo
     * @return
     */
    @RequestMapping(value = "/addOrUpdateOneOriginalDictionary")
    public ServerResponse<String> addOrUpdateOneOriginalDictionary(@RequestBody @Valid OriginalDictionaryPojo originalDictionaryPojo,
                                                                   BindingResult bindingResult) throws Exception {
        // 传入参数核验
        if (bindingResult.hasErrors()) {
            throw SystemException.asSystemException(ErrorCode.CHECK_PARAMETER_ERROR,
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        String message = originalDictionaryService.addOrUpdateOneOriginalDictionary(originalDictionaryPojo);
        return ServerResponse.asSucessResponse(message, message);
    }

    /**
     * 根据原始字典id和名称删除原始字典
     *
     * @param id             字典id
     * @param dictionaryName 字典名称
     * @return
     */
    @RequestMapping(value = "/deleteOneOriginalDictionary")
    public ServerResponse<String> deleteOneOriginalDictionary(@RequestParam("id") String id, @RequestParam("dictionaryName") String dictionaryName) {
        String message = originalDictionaryService.deleteOneOriginalDictionary(id, dictionaryName);
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
    public ServerResponse<OriginalDictionaryPojo> searchDictionaryByIdAndName(@RequestParam("id") String id,
                                                                              @RequestParam("dictionaryName") String dictionaryName) {
        OriginalDictionaryPojo originalDictionaryPojo = originalDictionaryService.searchDictionaryByIdAndName(id, dictionaryName);
        return ServerResponse.asSucessResponse(originalDictionaryPojo);
    }

    /**
     * 关键字搜索对应标准下拉框
     *
     * @param searchText 关键字搜索
     * @return
     */
    @RequestMapping(value = "/searchDictionaryListInfo")
    public ServerResponse<List<OneSuggestValue>> searchDictionaryListInfo(String searchText) {
        List<OneSuggestValue> oneSuggestValueList = originalDictionaryService.searchStandardDictionaryListInfo(searchText);
        return ServerResponse.asSucessResponse(oneSuggestValueList);
    }

    /**
     * 根据codeId获取对应的标准代码列表
     *
     * @param codeId 字典codeId
     * @return
     */
    @RequestMapping(value = "/searchDictionaryValueListByCodeId")
    public ServerResponse<List<PageSelectOneValue>> searchDictionaryValueListByCodeId(@RequestParam("codeId") String codeId) {
        List<PageSelectOneValue> resultList = originalDictionaryService.searchDictionaryValueListByCodeId(codeId);
        return ServerResponse.asSucessResponse(resultList);
    }

    /**
     * 下载原始字典项信息
     * 未选中任何记录时导出列表中全部记录；有选中记录时导出选中记录
     *
     * @param response
     * @param data     列表中的数据
     */
    @RequestMapping(value = "/downloadDictionaryFieldExcel", produces = "application/json;charset=utf-8")
    public void downloadDictionaryFieldExcel(HttpServletResponse response,
                                             @RequestBody OriginalDictionaryParameter data) {
        log.info("=======开始下载原始字典的相关信息=======");
        originalDictionaryService.downloadDictionaryFieldExcel(response, data, "原始字典管理", new OriginalDictionaryFieldPojo());
        log.info("=======下载原始字典的相关信息结束========");
    }

    /**
     * 下载原始字典项信息
     *
     * @param response
     */
    @RequestMapping(value = "/downloadDictionaryExcelTemplate",produces = "application/json;charset=utf-8")
    public void downloadDictionaryExcelTemplate(HttpServletResponse response){
        log.info("开始下载原始字典模板文件");
        List<OriginalDictionaryFieldPojo> list = new ArrayList<>();
        originalDictionaryService.downloadDictionaryExcelTemplate(response,list,"原始字典模板",new OriginalDictionaryFieldPojo());
        log.info("下载原始字典模板文件结束");
    }

    /**
     * 导入原始字典项数据
     *
     * @param file
     * @param id   公共数据项组id
     */
    @RequestMapping(value = "/importDictionaryFieldExcel")
    public ServerResponse<List<OriginalDictionaryFieldPojo>> importDictionaryFieldExcel(@RequestParam("file") MultipartFile file, @RequestParam("id") String id) {
        log.info("=======开始导入原始字典项的相关信息=======");
        List<OriginalDictionaryFieldPojo> list = originalDictionaryService.importDictionaryFieldExcel(file, id);
        log.info("=======导入原始字典项的相关信息结束,导入成功的数据量为{}========", list.size());
        return ServerResponse.asSucessResponse(list);
    }

    /**
     * 仓库探查分析中字段探查原始字典的下拉内容
     *
     */
    @RequestMapping(value = "/getOriginalDictionaryNameList")
    public ServerResponse<List<PageSelectOneValue>> getOriginalDictionaryNameList(){
        log.info("获取原始字典的名称下拉信息");
        List<PageSelectOneValue> originalDictionaryNameList = originalDictionaryService.getOriginalDictionaryNameList();
        log.info("获取原始字典的名称下拉信息结束");
        return ServerResponse.asSucessResponse(originalDictionaryNameList);
    }
}
