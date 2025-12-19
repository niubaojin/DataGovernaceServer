package com.synway.datastandardmanager.controller;

import com.synway.common.bean.ServerResponse;
import com.synway.datastandardmanager.entity.dto.LabelsDTO;
import com.synway.datastandardmanager.entity.pojo.LabelsEntity;
import com.synway.datastandardmanager.entity.vo.*;
import com.synway.datastandardmanager.service.ResourceLabelManageService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 标签管理的相关接口 ，需求在 3.2.9.1 资源标签管理
 * 1：获取左侧树的相关接口 左侧树型控件展现标签类型名称和当前层级包含资源标签数量统计值
 * 2：列表展现标签信息包括 列表加载标签记录按照层级正序、修订时间倒叙默认排序。
 *
 * @author wdw
 * @date 2021/6/8 17:03
 */
@RequestMapping("/labelManage")
@RestController
@Slf4j
@Validated
public class ResourceLabelManageController {

    @Autowired
    private ResourceLabelManageService service;


    /**
     * 获取左侧树的相关信息
     */
    @RequestMapping("/getLabelTreeData")
    public ServerResponse<List<TreeNodeValueVO>> getLabelTreeData() {
        return ServerResponse.asSucessResponse(service.getLabelTreeData());
    }

    /**
     * 数据定义页面筛选条件信息
     */
    @RequestMapping("/getLabelTotal")
    public ServerResponse<List<ValueLabelChildrenVO>> getLabelTotalList() {
        return ServerResponse.asSucessResponse(service.getLabelTotalList());
    }

    /**
     * 获取所有的标签信息
     */
    @RequestMapping(value = "/getLabelManageData", method = RequestMethod.POST)
    public ServerResponse<LabelManagePageVO> getLabelManageData(@RequestBody LabelsDTO labelsDTO) {
        return ServerResponse.asSucessResponse(service.getAllLabelManageData(labelsDTO));
    }


    /**
     * 新增标签信息 如果id为空，表示是新增 如果id不为空
     */
    @RequestMapping(value = "/addUpdateLabelManageData", method = RequestMethod.POST)
    public ServerResponse<String> addUpdateLabelManageData(@RequestBody @Valid LabelsEntity labelManageData, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ServerResponse.asErrorResponse(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        String result = service.addUpdateLabelManageData(labelManageData);
        return ServerResponse.asSucessResponse(result, result);
    }


    /**
     * 获取新增编辑里面的标签类型的选择框
     */
    @RequestMapping(value = "/getLabelTypeList")
    public ServerResponse<List<SelectFieldVO>> getLabelTypeList() {
        return ServerResponse.asSucessResponse(service.getLabelTypeList());
    }

    /**
     * 获取新增编辑里面的常用组织分类的选择框
     */
    @RequestMapping(value = "/getClassidTypeList")
    public ServerResponse<List<SelectFieldVO>> getClassidTypeList() {
        return ServerResponse.asSucessResponse(service.getClassidTypeList());
    }

    /**
     * 删除指定的标签信息
     */
    @RequestMapping(value = "/delLabelById")
    public ServerResponse<String> delLabelById(@RequestParam("id") @NotBlank String id, @RequestParam("labelCode") @NotBlank String labelCode) {
        String result = service.delLabelById(id, labelCode);
        return ServerResponse.asSucessResponse(result, result);
    }

    /**
     * 下载汇总表的相关信息；未选中任何记录时导出列表中全部记录；有选中记录时导出选中记录
     *
     * @param summaryObjectTableList 列表中的数据
     */
    @RequestMapping(value = "/downloadLabelsTableExcel", produces = "application/json;charset=utf-8")
    public void downloadLabelsTableExcel(HttpServletResponse response, @RequestBody List<LabelsEntity> summaryObjectTableList) {
        service.downloadLabelsTableExcel(response, summaryObjectTableList, "标签管理汇总表", new LabelsEntity());
    }

    /**
     * 导入标签类型数据
     */
    @RequestMapping(value = "/importLabelsTableExcel")
    public ServerResponse<String> importLabelsTableExcel(@RequestParam("file") MultipartFile file) {
        String importMessage = service.importLabelsTableExcel(file);
        return ServerResponse.asSucessResponse(importMessage, importMessage);
    }

    /**
     * 导入失败的文件
     */
    @RequestMapping(value = "/exportLabelsErrorMessage", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public void exportLabelsErrorMessage(@RequestBody List<LabelsEntity> param, HttpServletResponse response) {
        service.exportLabelsErrorMessage(response, param, "标签上传错误数据", new LabelsEntity());
    }

    /**
     * 下载标签表的模板excel文件
     */
    @RequestMapping(value = "/downloadTemplateExcel", produces = "application/json;charset=utf-8")
    public void downloadTemplateExcel(HttpServletResponse response) {
        List<LabelsEntity> list = new ArrayList<>();
        LabelsEntity labelManageData = new LabelsEntity();
        labelManageData.setMemo("备注：注*为必填项，标签代码在相同的标签类型下不可重复定义，并且标签类型代码只能填写为1/2/3这种代码,常用组织分类代码要使用文本格式，01/02这种代码");
        list.add(labelManageData);
        service.exportLabelsErrorMessage(response, list, "标签管理模板", new LabelsEntity());
    }

    /**
     * 获取资源标签的代码值
     *
     * @param level 标签等级
     */
    @RequestMapping(value = "/getLabelCodeByLevel")
    public ServerResponse<String> getLabelCodeByLevel(@RequestParam("level") int level) {
        String labelCode = service.getLabelCodeByLevel(level);
        return ServerResponse.asSucessResponse(labelCode, labelCode);
    }

    /**
     * 根据 classid获取标签信息
     */
    @RequestMapping(value = "/getLabelSelectByClassId")
    public ServerResponse<LabelManageSelectVO> getLabelManageDataByClassId(@RequestParam("classId") String classId,
                                                                           @RequestParam("labelLevel") Integer labelLevel) {
        return ServerResponse.asSucessResponse(service.getLabelManageDataByClassId(classId, labelLevel));
    }

    /**
     * 保存时根据 code值获取对象信息
     */
    @RequestMapping(value = "/getLabelManageByLabelCode", method = {RequestMethod.POST})
    public ServerResponse<List<LabelsEntity>> getLabelManageByLabelCode(@RequestBody List<String> labelList) {
        return ServerResponse.asSucessResponse(service.getLabelManageByLabelCode(labelList));
    }

    /**
     * 验证同一个分类下（LABELLEVEL）标签代码值是否重复 true:不重复 false:重复
     *
     * @param labelCode  标签代码值
     * @param labelLevel 标签等级
     */
    @RequestMapping(value = "/checkLabelCodeIsExist")
    public ServerResponse<Boolean> checkLabelCodeIsExist(@RequestParam("labelCode") String labelCode,
                                                         @RequestParam("labelLevel") Integer labelLevel) {
        return ServerResponse.asSucessResponse(service.checkLabelCodeIsExist(labelCode, labelLevel));
    }

}
