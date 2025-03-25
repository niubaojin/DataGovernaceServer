package com.synway.datastandardmanager.controller;

import com.synway.datastandardmanager.interceptor.IgnoreSecurity;
import com.synway.datastandardmanager.pojo.FilterObject;
import com.synway.datastandardmanager.pojo.LayuiClassifyPojo;
import com.synway.datastandardmanager.pojo.PageSelectOneValue;
import com.synway.datastandardmanager.pojo.labelmanage.*;
import com.synway.datastandardmanager.pojo.summaryobjectpage.SummaryObjectTable;
import com.synway.datastandardmanager.service.LabelsManageService;
import com.synway.datastandardmanager.util.TemplateExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.synway.common.bean.ServerResponse;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 标签管理的相关接口 ，需求在 3.2.9.1 资源标签管理
 * 1:获取左侧树的相关接口 左侧树型控件展现标签类型名称和当前层级包含资源标签数量统计值
 * 2: 列表展现标签信息包括 列表加载标签记录按照层级正序、修订时间倒叙默认排序。
 * @author wdw
 * @version 1.0
 * @date 2021/6/8 17:03
 */
@RequestMapping("/labelManage")
@RestController
@Slf4j
@Validated
public class LabelsManageController {

    @Autowired
    private LabelsManageService labelsManageServiceImpl;


    /**
     * 获取左侧树的相关信息
     * @return
     */
    @RequestMapping("/getLabelTreeData")
    public ServerResponse<List<LabelTreeNodeVue>> getLabelTreeData(){
        List<LabelTreeNodeVue> list = labelsManageServiceImpl.getLabelTreeData();
        return ServerResponse.asSucessResponse(list);
    }

    /**
     * 数据定义页面筛选条件信息
     * @return
     */
    @RequestMapping("/getLabelTotal")
    public ServerResponse<List<LayuiClassifyPojo>> getLabelTotalList(){
        List<LayuiClassifyPojo> list = labelsManageServiceImpl.getLabelTotalList();
        return ServerResponse.asSucessResponse(list);
    }

    /**
     * 获取所有的标签信息
     * @return
     */
    @RequestMapping(value = "/getLabelManageData",method = RequestMethod.POST)
    public ServerResponse<LabelManagePage> getLabelManageData(@RequestBody QueryParames queryParames){
        LabelManagePage data = labelsManageServiceImpl.getAllLabelManageData(queryParames);
        return ServerResponse.asSucessResponse(data);
    }


    /**
     * 新增标签信息 如果id为空，表示是新增 如果id不为空
     * @param labelManageData
     * @return
     */
    @RequestMapping(value = "/addUpdateLabelManageData",method = RequestMethod.POST)
    public ServerResponse<String> addUpdateLabelManageData(@RequestBody @Valid LabelManageData labelManageData,
                                                              BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return  ServerResponse.asErrorResponse(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        boolean result = labelsManageServiceImpl.addUpdateLabelManageData(labelManageData);
        String flag = result?"成功":"失败";
        return ServerResponse.asSucessResponse(flag,flag);
    }


    /**
     * 获取新增编辑里面的标签类型的选择框
     * @return
     */
    @RequestMapping(value = "/getLabelTypeList")
    public ServerResponse<List<LabelSelect>> getLabelTypeList(){
        List<LabelSelect> list = labelsManageServiceImpl.getLabelTypeList();
        return ServerResponse.asSucessResponse(list);
    }

    /**
     * 获取新增编辑里面的常用组织分类的选择框
     * @return
     */
    @RequestMapping(value = "/getClassidTypeList")
    public ServerResponse<List<LabelSelect>> getClassidTypeList(){
        List<LabelSelect> list = labelsManageServiceImpl.getClassidTypeList();
        return ServerResponse.asSucessResponse(list);
    }


    /**
     * 删除指定的标签信息
     * @param id
     * @param labelCode
     * @return
     */
    @RequestMapping(value = "/delLabelById")
    public ServerResponse<String> delLabelById(@RequestParam("id") @NotBlank String id, @RequestParam("labelCode") @NotBlank String labelCode){
        String result = labelsManageServiceImpl.delLabelById(id,labelCode);
        return ServerResponse.asSucessResponse(result,result);
    }



    /**
     * 下载汇总表的相关信息
     * 未选中任何记录时导出列表中全部记录；有选中记录时导出选中记录
     * @param response
     * @param summaryObjectTableList  列表中的数据
     */
    @RequestMapping(value="/downloadLabelsTableExcel",produces="application/json;charset=utf-8")
    public void downloadLabelsTableExcel(HttpServletResponse response,
                                          @RequestBody List<LabelManageData> summaryObjectTableList) {
        log.info("=======开始下载标签表的相关信息=======");
        labelsManageServiceImpl.downloadLabelsTableExcel(response,summaryObjectTableList,"标签管理汇总表",new LabelManageData());
        log.info("=======下载标签表的相关信息结束========");
    }


    /**
     * 导入标签类型数据
     * @param file
     */
    @RequestMapping(value="/importLabelsTableExcel")
    public ServerResponse<String> importLabelsTableExcel(@RequestParam("file") MultipartFile file) {
        log.info("=======开始导入标签表的相关信息=======");
        String importMessage = labelsManageServiceImpl.importLabelsTableExcel(file);
        log.info("=======导入标签表的相关信息结束========");
        return ServerResponse.asSucessResponse(importMessage,importMessage);
    }


    /**
     * 导入失败的文件
     * @param param
     * @param response
     */
    @RequestMapping(value = "/exportLabelsErrorMessage", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public void exportLabelsErrorMessage(@RequestBody List<LabelManageExcel> param, HttpServletResponse response) {
        log.info("=======开始下载错误数据的相关信息=======");
        if(param != null && !param.isEmpty()){
            labelsManageServiceImpl.downloadLabelsTableExcel(response,param,"标签上传错误数据",new LabelManageExcel());
            log.info("=======下载错误数据相关信息结束========");
        }else{
            log.info("=======下载错误数据为空========");
        }

    }


    /**
     * 下载标签表的模板excel文件
     * @param response
     */
    @RequestMapping(value="/downloadTemplateExcel",produces="application/json;charset=utf-8")
    public void downloadTemplateExcel(HttpServletResponse response) {
        log.info("=======开始下载标签表的模板信息=======");
        List<LabelManageExcel> list = new ArrayList<>();
        LabelManageExcel labelManageData = new LabelManageExcel();
        labelManageData.setMemo("备注：注*为必填项，标签代码在相同的标签类型下不可重复定义，并且标签类型代码只能填写为1/2/3这种代码" +
                ",常用组织分类代码要使用文本格式，01/02这种代码");
        list.add(labelManageData);
        labelsManageServiceImpl.downloadLabelsTableExcel(response,list,"标签管理模板",new LabelManageExcel());
        log.info("=======下载标签表的模板信息结束========");
    }

    /**
     * 获取资源标签的代码值
     * @param level 标签等级
     * @return
     */
    @RequestMapping(value="/getLabelCodeByLevel")
    public ServerResponse<String> getLabelCodeByLevel(@RequestParam("level")int level) {

        String labelCode = labelsManageServiceImpl.getLabelCodeByLevel(level);
        log.info("级别：{}，对应的标签代码为: {}",level,labelCode);
        return ServerResponse.asSucessResponse(labelCode,labelCode);
    }


    /**
     * 根据 classid获取标签信息
     * @param classId
     * @return
     */
    @RequestMapping(value="/getLabelSelectByClassId")
    public ServerResponse<LabelManageSelect> getLabelManageDataByClassId(@RequestParam("classId")String classId,
                                                                                @RequestParam("labelLevel")Integer labelLevel) {
        if(labelLevel == null){
            throw new NullPointerException("labelLevel不能为空");
        }
        List<PageSelectOneValue> data = labelsManageServiceImpl.getLabelManageDataByClassId(classId,labelLevel);
        LabelManageSelect labelManageSelect = new LabelManageSelect();
        labelManageSelect.setLabelLevel(labelLevel);
        labelManageSelect.setList(data);
        return ServerResponse.asSucessResponse(labelManageSelect);
    }


    /**
     * 保存时根据 code值获取对象信息
     * @param labelList
     * @return
     */
    @RequestMapping(value="/getLabelManageByLabelCode",method = {RequestMethod.POST})
    public ServerResponse<List<LabelManageData>> getLabelManageByLabelCode(@RequestBody List<String> labelList) {
        List<LabelManageData> data = labelsManageServiceImpl.getLabelManageByLabelCode(labelList);
        return ServerResponse.asSucessResponse(data);
    }

    /**
     * 验证同一个分类下（LABELLEVEL）标签代码值是否重复 true:不重复 false:重复
     * @param labelCode 标签代码值
     * @param labelLevel 标签等级
     * @return
     */
    @RequestMapping(value = "/checkLabelCodeIsExist")
    public ServerResponse<Boolean> checkLabelCodeIsExist(@RequestParam("labelCode") String labelCode,
                                                         @RequestParam("labelLevel") Integer labelLevel){
        Boolean flag = labelsManageServiceImpl.checkLabelCodeIsExist(labelCode, labelLevel);
        return ServerResponse.asSucessResponse(flag);
    }

}
