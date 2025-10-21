package com.synway.datastandardmanager.controller;

import com.alibaba.fastjson.JSONObject;
import com.synway.common.bean.ServerResponse;
import com.synway.common.exception.SystemException;
import com.synway.datastandardmanager.entity.dto.EntityElementDTO;
import com.synway.datastandardmanager.entity.pojo.EntityElementEntity;
import com.synway.datastandardmanager.entity.vo.*;
import com.synway.datastandardmanager.enums.ErrorCodeEnum;
import com.synway.datastandardmanager.service.DataElementService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 数据要素管理
 *
 * @author nbj
 * @date 2025年5月13日10:41:45
 */
@Slf4j
@RestController
@RequestMapping("/synlteElement")
public class DataElementController {

    @Autowired
    private DataElementService elementService;

    /**
     * 查询数据要素的相关信息：使用前端分页
     *
     * @param elementDTO
     * @return
     */
    @RequestMapping(value = "/searchTable")
    public ServerResponse<List<EntityElementVO>> getElementTable(@RequestBody EntityElementDTO elementDTO) {
        log.info("查询数据要素的请求参数为：{}", JSONObject.toJSONString(elementDTO));
        return ServerResponse.asSucessResponse(elementService.getDataElementList(elementDTO));
    }

    /**
     * 新增数据要素
     *
     * @param elementDTO
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/addOneData")
    public ServerResponse addOneData(@RequestBody @Valid EntityElementEntity elementDTO, BindingResult bindingResult) {
        // 传入参数核验
        if (bindingResult.hasErrors()) {
            throw SystemException.asSystemException(ErrorCodeEnum.CHECK_PARAMETER_ERROR,
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return ServerResponse.asSucessResponse(elementService.addOneData(elementDTO));
    }

    /**
     * 更新数据要素
     *
     * @param elementDTO
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/upOneData")
    public ServerResponse upOneData(@RequestBody @Valid EntityElementEntity elementDTO, BindingResult bindingResult) {
        // 传入参数核验
        if (bindingResult.hasErrors()) {
            throw SystemException.asSystemException(ErrorCodeEnum.CHECK_PARAMETER_ERROR,
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return ServerResponse.asSucessResponse(elementService.upOneData(elementDTO));
    }

    /**
     * 根据数据要素标识符删除该要素
     *
     * @param elementCode
     * @return
     */
    @RequestMapping(value = "/deleteElementByCode")
    public ServerResponse<String> deleteOneData(@RequestParam("elementCode") @NotBlank String elementCode) {
        return ServerResponse.asSucessResponse(elementService.deleteElementByCode(elementCode));
    }

    /**
     * 检查标准里是否使用了该要素
     *
     * @param elementCode 要素标识符
     * @return true 可以删除 false 不可删除
     */
    @RequestMapping(value = "/checkIsDelete")
    public ServerResponse<Boolean> checkIsDelete(@RequestParam("elementCode") @NotBlank String elementCode) {
        return ServerResponse.asSucessResponse(elementService.checkIsDelete(elementCode));
    }

    /**
     * 生成方式 第一行下拉框数据获取
     *
     * @param searchName 搜索下拉框
     * @return
     */
    @RequestMapping(value = "/searchSynlteField")
    public ServerResponse<List<SelectFieldVO>> searchSynlteField(@RequestParam("searchName") String searchName) {
        return ServerResponse.asSucessResponse(elementService.searchSynlteField(searchName));
    }

    /**
     * 生成方式 第二行下拉框数据获取
     *
     * @param searchName
     * @return
     */
    @RequestMapping(value = "/searchSecondSynlteField")
    public ServerResponse<List<SelectFieldVO>> searchSecondSynlteField(@RequestParam("searchName") String searchName) {
        return ServerResponse.asSucessResponse(elementService.searchSecondField(searchName));
    }

    /**
     * 生成方式 第二行第二个下拉框 根据codeId获取对应数据码集值
     *
     * @param codeId
     * @return
     */
    @RequestMapping(value = "/searchAnotherField")
    public ServerResponse<List<SelectFieldVO>> searchAnotherField(@RequestParam("codeId") String codeId) {
        return ServerResponse.asSucessResponse(elementService.searchAnotherField(codeId));
    }

    /**
     * 数据元素主体 树信息
     *
     * @return
     */
//    @IgnoreSecurity
    @RequestMapping(value = "/treeInfo")
    public ServerResponse<List<TreeNodeValueVO>> treeInfo() {
        return ServerResponse.asSucessResponse(elementService.searchElementObject());
    }

    /**
     * 数据元素新增时主体下拉信息
     *
     * @return
     */
    @RequestMapping(value = "/searchAllElementObjectInfo")
    public ServerResponse<List<SelectFieldVO>> searchAllElementObjectInfo() {
        return ServerResponse.asSucessResponse(elementService.searchAllObject());
    }

    /**
     * 数据要素管理页面 搜索名称 下拉框提示
     *
     * @param searchName
     * @return
     */
    @RequestMapping(value = "/searchElementChname")
    public ServerResponse<List<String>> searchElementChname(@RequestParam("searchName") String searchName) {
        return ServerResponse.asSucessResponse(elementService.searchElementChname(searchName));
    }

    /**
     * 查询数据来源码表值
     */
    @RequestMapping(value = "/searchIsElement")
    public ServerResponse<List<KeyValueVO>> searchIsElement() {
        log.info(">>>>>>查询数据来源码表值信息");
        return ServerResponse.asSucessResponse(elementService.searchIsElement());
    }

    /**
     * 查询语义类型
     */
    @RequestMapping(value = "/searchWordName")
    public ServerResponse<String> searchSameWordName(@RequestParam("sameId") String sameId) {
        return ServerResponse.asSucessResponse(elementService.searchSameWord(sameId));
    }

    /**
     * 数据定义页面数据要素筛选条件
     */
    @RequestMapping(value = "/searchElementFilter")
    public ServerResponse<List<ValueLabelVO>> searchElementToTalFilter() {
        log.info(">>>>>>开始查询【数据要素筛选条件】分类信息");
        ServerResponse<List<ValueLabelVO>> serverResponse = null;
        try {
            List<ValueLabelVO> list = elementService.searchElementTotal();
            serverResponse = ServerResponse.asSucessResponse(list);
            log.info(">>>>>>查询【数据要素筛选条件】分类信息结束");
        } catch (Exception e) {
            serverResponse = ServerResponse.asErrorResponse(e.getMessage());
            log.error(">>>>>>查询【数据要素筛选条件】分类信息报错:", e);
        }
        return serverResponse;
    }


    /**
     * 导出数据要素记录
     * 未选中任何记录时导出列表中全部记录；有选中记录时导出选中记录
     *
     * @param response
     * @param elementList 列表中的数据
     */
    @RequestMapping(value = "/downloadElementTableExcel", produces = "application/json;charset=utf-8")
    public void downloadElementTableExcel(HttpServletResponse response, @RequestBody List<EntityElementEntity> elementList) {
        log.info(">>>>>>开始下载数据要素表的相关信息=======");
        elementService.downloadAllElementTableExcel(response, elementList, "数据要素管理汇总表", new EntityElementEntity());
    }

    /**
     * 导入数据要素类型数据
     *
     * @param file
     */
    @RequestMapping(value = "/importElementTableExcel")
    public ServerResponse<List<EntityElementEntity>> importElementTableExcel(@RequestParam("file") MultipartFile file) {
        log.info(">>>>>>开始导入数据要素的相关信息");
        List<EntityElementEntity> list = elementService.importElementTableExcel(file);
        log.info(">>>>>>导入数据要素的相关信息结束,导入成功的数据量为:{}", list.size());
        return ServerResponse.asSucessResponse(list);
    }

    /**
     * 导入失败的文件
     *
     * @param param
     * @param response
     */
    @RequestMapping(value = "/exportElementErrorMessage", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public void downloadErrorElementTableExcel(@RequestBody List<EntityElementEntity> param, HttpServletResponse response) {
        log.info(">>>>>>开始下载错误数据的相关信息");
        if (param != null && !param.isEmpty()) {
            elementService.downloadErrorElementTableExcel(response, param, "标签上传错误数据", new EntityElementEntity());
        }
    }

    /**
     * 下载数据要素的模板excel文件
     *
     * @param response
     */
    @RequestMapping(value = "/downloadTemplateExcel", produces = "application/json;charset=utf-8")
    public void downloadTemplateExcel(HttpServletResponse response) {
        log.info(">>>>>>开始下载数据要素表的模板信息");
        List<EntityElementEntity> list = new ArrayList<>();
        EntityElementEntity labelManageData = new EntityElementEntity();
        labelManageData.setMemo("备注：注*为必填项，数据要素标识符生成规则为数据元ID，要素标识符不可重复定义");
        list.add(labelManageData);
        elementService.downloadElmentTemplateTableExcel(response, list, "数据要素管理模板", new EntityElementEntity());
    }


}
