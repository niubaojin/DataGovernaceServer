package com.synway.datastandardmanager.controller;

import com.alibaba.fastjson.JSONObject;
import com.synway.datastandardmanager.exceptionhandler.ErrorCode;
import com.synway.datastandardmanager.exceptionhandler.SystemException;
import com.synway.datastandardmanager.interceptor.IgnoreSecurity;
import com.synway.datastandardmanager.pojo.FilterObject;
import com.synway.datastandardmanager.pojo.LayuiClassifyPojo;
import com.synway.datastandardmanager.pojo.labelmanage.LabelTreeNodeVue;
import com.synway.datastandardmanager.pojo.synlteelement.*;
import com.synway.datastandardmanager.service.SynlteElementService;
import com.synway.datastandardmanager.util.ExceptionUtil;
import com.synway.common.bean.ServerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *数据要素管理
 * @author obito
 * @version 1.0
 * @date
 */
@RestController
@Slf4j
@RequestMapping("/synlteElement")
@Validated
public class SynlteElementController {

    @Autowired
    private SynlteElementService elementService;

    /**
     * 查询数据要素的相关信息  使用前端分页
     * @param parameter
     * @return
     */
    @RequestMapping(value = "/searchTable")
    public ServerResponse<List<SynlteElementVO>> getElementTable(@RequestBody SynlteElementParameter parameter){
        log.info("查询数据要素的请求参数为{}", JSONObject.toJSONString(parameter));
        List<SynlteElementVO> lists = elementService.searchAllElement(parameter);
        log.info("========查询数据要素结束=========");
        return ServerResponse.asSucessResponse(lists);
    }

    /**
     * 新增数据要素
     * @param data
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/addOneData")
    public ServerResponse<String> addOneData(@RequestBody @Valid SynlteElement data, BindingResult bindingResult){
        log.info("数据要素添加的内容为{}",JSONObject.toJSONString(data));
        // 传入参数核验
        if(bindingResult.hasErrors()){
            throw SystemException.asSystemException(ErrorCode.CHECK_PARAMETER_ERROR,
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        String result = elementService.addOneData(data);
        log.info("========添加数据要素结束=========");
        return ServerResponse.asSucessResponse(result,result);
    }

    /**
     * 编辑数据要素
     * @param data
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/upOneData")
    public ServerResponse<String> upOneData(@RequestBody @Valid SynlteElementVO data,
                                            BindingResult bindingResult){
        log.info("数据要素编辑的内容为{}", JSONObject.toJSONString(data));
        // 传入参数核验
        if(bindingResult.hasErrors()){
            throw SystemException.asSystemException(ErrorCode.CHECK_PARAMETER_ERROR,
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        String result = elementService.upOneData(data);
        log.info("========编辑数据要素结束=========");
        return ServerResponse.asSucessResponse(result,result);
    }

    /**
     * 根据数据要素标识符删除该要素
     * @param elementCode
     * @return
     */
    @RequestMapping(value = "/deleteElementByCode")
    public ServerResponse<String> deleteOneData(@RequestParam("elementCode") @NotBlank String elementCode){
        String count = elementService.deleteElementByCode(elementCode);
        return ServerResponse.asSucessResponse(count,count);
    }

    /**
     * 检查标准里是否使用了该要素
     * @param elementCode 要素标识符
     * @return true 可以删除 false 不可删除
     */
    @RequestMapping(value = "/checkIsDelete")
    public ServerResponse<Boolean> checkIsDelete(@RequestParam("elementCode") @NotBlank String elementCode){
        Boolean isDelete = elementService.checkIsRelevance(elementCode);
        return ServerResponse.asSucessResponse(isDelete);
    }

    /**
     * 生成方式 第一行下拉框数据获取
     * @param searchName 搜索下拉框
     * @return
     */
    @RequestMapping(value = "/searchSynlteField")
    public ServerResponse<List<SelectField>> searchSynlteField(String searchName){
        List<SelectField> fieldList = elementService.searchSynlteField(searchName);
        return ServerResponse.asSucessResponse(fieldList);
    }

    /**
     * 生成方式 第二行下拉框数据获取
     * @param searchName
     * @return
     */
    @RequestMapping(value = "/searchSecondSynlteField")
    public ServerResponse<List<SelectField>> searchSecondSynlteField(String searchName){
        List<SelectField> elementSynlteFields = elementService.searchSecondField(searchName);
        return ServerResponse.asSucessResponse(elementSynlteFields);
    }

    /**
     * 生成方式 第二行第二个下拉框 根据codeId获取对应数据码集值
     * @param codeId
     * @return
     */
    @RequestMapping(value = "/searchAnotherField")
    public ServerResponse<List<SelectField>> searchAnotherField(@RequestParam("codeId") String codeId){
        List<SelectField> list = elementService.searchAnotherField(codeId);
        return ServerResponse.asSucessResponse(list);
    }

    /**
     * 数据元素主体 树信息
     * @return
     */
    @RequestMapping(value = "/treeInfo")
//    @IgnoreSecurity
    public ServerResponse<List<LabelTreeNodeVue>> treeInfo(){
        List<LabelTreeNodeVue> elementTreeNodeList = elementService.searchElementObject();
        return ServerResponse.asSucessResponse(elementTreeNodeList);
    }

    /**
     * 数据元素新增时主体下拉信息
     * @return
     */
    @RequestMapping(value = "/searchAllElementObjectInfo")
    public ServerResponse<List<SelectField>> searchAllElementObjectInfo(){
        List<SelectField> elementObjectList = elementService.searchAllObject();
        return ServerResponse.asSucessResponse(elementObjectList);
    }

    /**
     * 数据要素管理页面 搜索名称 下拉框提示
     * @param searchName
     * @return
     */
    @RequestMapping(value = "/searchElementChname")
    public ServerResponse<List<String>> searchElementChname(String searchName){
        List<String> nameList = elementService.searchElementChname(searchName);
        return ServerResponse.asSucessResponse(nameList);
    }


    /**
     * 下载汇总表的相关信息
     * 未选中任何记录时导出列表中全部记录；有选中记录时导出选中记录
     * @param response
     * @param elementList  列表中的数据
     */
    @RequestMapping(value="/downloadElementTableExcel",produces="application/json;charset=utf-8")
    public void downloadElementTableExcel(HttpServletResponse response,
                                         @RequestBody List<SynlteElement> elementList){
        log.info("=======开始下载数据要素表的相关信息=======");
        elementService.downloadAllElementTableExcel(response,elementList,"数据要素管理汇总表",new SynlteElement());
        log.info("=======下载数据要素的相关信息结束========");
    }

    /**
     * 导入数据要素类型数据
     * @param file
     */
    @RequestMapping(value="/importElementTableExcel")
    public ServerResponse<List<SynlteElement>> importElementTableExcel(@RequestParam("file") MultipartFile file) {
        log.info("=======开始导入数据要素的相关信息=======");
        List<SynlteElement> list = elementService.importElementTableExcel(file);
        log.info("=======导入数据要素的相关信息结束,导入成功的数据量为{}========",list.size());
        return ServerResponse.asSucessResponse(list);
    }

    /**
     * 导入失败的文件
     * @param param
     * @param response
     */
    @RequestMapping(value = "/exportElementErrorMessage", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public void downloadErrorElementTableExcel(@RequestBody List<SynlteElement> param, HttpServletResponse response) {
        log.info("=======开始下载错误数据的相关信息=======");
        if(param != null && !param.isEmpty()){
            elementService.downloadErrorElementTableExcel(response,param,"标签上传错误数据",new SynlteElement());
            log.info("=======下载错误数据相关信息结束========");
        }else{
            log.info("=======下载错误数据为空========");
        }
    }

    /**
     * 下载数据要素的模板excel文件
     * @param response
     */
    @RequestMapping(value="/downloadTemplateExcel",produces="application/json;charset=utf-8")
    public void downloadTemplateExcel(HttpServletResponse response) {
        log.info("=======开始下载数据要素表的模板信息=======");
        List<SynlteElement> list = new ArrayList<>();
        SynlteElement labelManageData = new SynlteElement();
        labelManageData.setMemo("备注：注*为必填项，数据要素标识符生成规则为数据元ID，要素标识符不可重复定义");
        list.add(labelManageData);
        elementService.downloadElmentTemplateTableExcel(response,list,"数据要素管理模板",new SynlteElement());
        log.info("=======下载数据要素的模板信息结束========");
    }

    /**
     * 查询数据来源码表值
     */
    @RequestMapping(value = "/searchIsElement")
    public ServerResponse<List<FilterObject>> searchIsElement(){
        log.info("=======开始查询数据来源码表值信息=======");
        List<FilterObject> selectFields = elementService.searchIsElement();
        log.info("=======结束查询数据来源码表值信息=======");
        return ServerResponse.asSucessResponse(selectFields);
    }

    /**
     * 查询语义类型
     */
    @RequestMapping(value = "/searchWordName")
    public ServerResponse<String> searchSameWordName(@RequestParam("sameId") String sameId){
        String sameWord = elementService.searchSameWord(sameId);
        return ServerResponse.asSucessResponse(sameWord,sameWord);
    }

    /**
     * 数据定义页面数据要素筛选条件
     */
    @RequestMapping(value = "/searchElementFilter")
    public ServerResponse<List<LayuiClassifyPojo>> searchElementToTalFilter(){
        log.info("开始查询【数据要素筛选条件】分类信息");
        ServerResponse<List<LayuiClassifyPojo>> serverResponse = null;
        try {
            List<LayuiClassifyPojo> list = elementService.searchElementTotal();
            serverResponse = ServerResponse.asSucessResponse(list);
            log.info("查询【数据要素筛选条件】分类信息结束");
        }catch (Exception e){
            serverResponse = ServerResponse.asErrorResponse(e.getMessage());
            log.error("查询【数据要素筛选条件】分类信息报错"+ ExceptionUtil.getExceptionTrace(e));
        }
        return serverResponse;
    }

}
