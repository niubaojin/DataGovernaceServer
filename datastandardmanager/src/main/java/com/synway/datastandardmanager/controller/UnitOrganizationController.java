package com.synway.datastandardmanager.controller;


import com.synway.common.bean.ServerResponse;
import com.synway.datastandardmanager.exceptionhandler.ErrorCode;
import com.synway.datastandardmanager.exceptionhandler.SystemException;
import com.synway.datastandardmanager.pojo.FilterObject;
import com.synway.datastandardmanager.pojo.LayuiClassifyPojo;
import com.synway.datastandardmanager.pojo.synltefield.SynlteFieldFilter;
import com.synway.datastandardmanager.pojo.unitManagement.UnitOrganizationParameter;
import com.synway.datastandardmanager.pojo.unitManagement.UnitOrganizationPojo;
import com.synway.datastandardmanager.pojo.unitManagement.UnitOrganizationTree;
import com.synway.datastandardmanager.service.UnitOrganizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 机构管理的相关功能
 * @author obito
 * @date 2022/3/14 11:07
 */
@RestController
@Slf4j
@RequestMapping("/unitOrganization")
@Validated
public class UnitOrganizationController {

    @Autowired
    private UnitOrganizationService unitOrganizationService;

    /**
     * 获取左侧树信息
     * @return
     */
    @RequestMapping(value = "/searchLeftTree")
    public ServerResponse<List<UnitOrganizationTree>> searchLeftTree(){
        List<UnitOrganizationTree> leftTree = unitOrganizationService.getLeftTree();
        return ServerResponse.asSucessResponse(leftTree);
    }

    /**
     * 获取单位机构管理表格信息
     * @param unitOrganizationParameter 单位机构参数
     * @return
     */
    @RequestMapping(value = "/searchUnitOrganizationTable")
    public ServerResponse<Map<String,Object>> searchUnitOrganizationTable(@RequestBody @Valid UnitOrganizationParameter unitOrganizationParameter){
        Map<String, Object> resultMap = unitOrganizationService.searchUnitOrganizationTable(unitOrganizationParameter);
        return ServerResponse.asSucessResponse(resultMap);
    }

    /**
     * 增加单位机构信息
     * @param unitOrganizationPojo 单位机构信息
     * @return
     */
    @RequestMapping(value = "/addOneUnitOrganization")
    public ServerResponse<String> addOneUnitOrganization(@RequestBody @Valid UnitOrganizationPojo unitOrganizationPojo,
                                                         BindingResult bindingResult){
        // 传入参数核验
        if(bindingResult.hasErrors()){
            throw SystemException.asSystemException(ErrorCode.CHECK_PARAMETER_ERROR,
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        String addMessage = unitOrganizationService.addOneUnitOrganization(unitOrganizationPojo);
        return ServerResponse.asSucessResponse(addMessage,addMessage);
    }

    /**
     * 删除指定单位机构代码信息
     * @param unitCode 单位机构代码(id)
     * @return
     */
    @RequestMapping(value = "/deleteOneUnitOrganization")
    public ServerResponse<String> deleteOneUnitOrganization(@RequestParam("unitCode")String unitCode){
        String deleteMessage = unitOrganizationService.deleteOneUnitOrganization(unitCode);
        return ServerResponse.asSucessResponse(deleteMessage,deleteMessage);
    }

    /**
     * 更新单位机构信息
     * @param unitOrganizationPojo 单位机构信息
     * @return
     */
    @RequestMapping(value = "/updateOneUnitOrganization")
    public ServerResponse<String> updateOneUnitOrganization(@RequestBody @Valid UnitOrganizationPojo unitOrganizationPojo){
        String updateMessage = unitOrganizationService.updateOneUnitOrganization(unitOrganizationPojo);
        return ServerResponse.asSucessResponse(updateMessage,updateMessage);
    }

    /**
     * 获取所属地区信息
     * @return
     */
    @RequestMapping(value = "/getAreaInfo")
    public ServerResponse<List<LayuiClassifyPojo>> getAreaInfo(){
        List<LayuiClassifyPojo> areaInfo = unitOrganizationService.getAreaInfo();
        return ServerResponse.asSucessResponse(areaInfo);
    }

    /**
     * 下载单位机构信息
     * 未选中任何记录时导出列表中全部记录；有选中记录时导出选中记录
     * @param response
     * @param unitOrganizationList  列表中的数据
     */
    @RequestMapping(value="/downloadUnitOrganization",produces="application/json;charset=utf-8")
    public void downloadUnitOrganization(HttpServletResponse response,
                                         @RequestBody List<UnitOrganizationPojo> unitOrganizationList){
        unitOrganizationService.downloadUnitOrganization(response,unitOrganizationList,"单位机构管理表",new UnitOrganizationPojo());
    }

    /**
     * 获取表格的筛选内容
     * @return
     */
    @RequestMapping(value = "/getFilterObject")
    public ServerResponse<List<FilterObject>> getFilterObject(){
        log.info("查询数据元的表格筛选内容");
        List<FilterObject> data = unitOrganizationService.getFilterObject();
        log.info("========查询数据元的筛选内容结束=========");
        return ServerResponse.asSucessResponse(data);
    }
}
