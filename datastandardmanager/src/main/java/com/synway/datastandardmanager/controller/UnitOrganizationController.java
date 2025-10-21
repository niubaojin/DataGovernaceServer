package com.synway.datastandardmanager.controller;

import com.synway.common.bean.ServerResponse;
import com.synway.datastandardmanager.entity.dto.UnitOrganizationDTO;
import com.synway.datastandardmanager.entity.pojo.StandardizeUnitManageEntity;
import com.synway.datastandardmanager.entity.vo.KeyValueVO;
import com.synway.datastandardmanager.entity.vo.PageVO;
import com.synway.datastandardmanager.entity.vo.UnitOrganizationTreeVO;
import com.synway.datastandardmanager.entity.vo.ValueLabelVO;
import com.synway.datastandardmanager.service.UnitOrganizationService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 机构管理的相关功能
 *
 * @author obito
 * @date 2022/3/14 11:07
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/unitOrganization")
public class UnitOrganizationController {

    @Autowired
    private UnitOrganizationService unitOrganizationService;

    /**
     * 获取左侧树信息
     */
    @RequestMapping(value = "/searchLeftTree")
    public ServerResponse<List<UnitOrganizationTreeVO>> searchLeftTree() {
        return ServerResponse.asSucessResponse(unitOrganizationService.getLeftTree());
    }

    /**
     * 获取单位机构管理表格信息
     *
     * @param dto 单位机构参数
     */
    @RequestMapping(value = "/searchUnitOrganizationTable")
    public ServerResponse<PageVO> searchUnitOrganizationTable(@RequestBody @Valid UnitOrganizationDTO dto) {
        return ServerResponse.asSucessResponse(unitOrganizationService.searchUnitOrganizationTable(dto));
    }

    /**
     * 增加单位机构信息
     *
     * @param standardizeUnitManage 单位机构信息
     */
    @RequestMapping(value = "/addOneUnitOrganization")
    public ServerResponse<String> addOneUnitOrganization(@RequestBody StandardizeUnitManageEntity standardizeUnitManage) {
        String addMessage = unitOrganizationService.addOneUnitOrganization(standardizeUnitManage);
        return ServerResponse.asSucessResponse(addMessage, addMessage);
    }

    /**
     * 删除指定单位机构代码信息
     *
     * @param unitCode 单位机构代码(id)
     * @return
     */
    @RequestMapping(value = "/deleteOneUnitOrganization")
    public ServerResponse<String> deleteOneUnitOrganization(@RequestParam("unitCode") String unitCode) {
        String deleteMessage = unitOrganizationService.deleteOneUnitOrganization(unitCode);
        return ServerResponse.asSucessResponse(deleteMessage, deleteMessage);
    }

    /**
     * 更新单位机构信息
     *
     * @param unitManageEntity 单位机构信息
     */
    @RequestMapping(value = "/updateOneUnitOrganization")
    public ServerResponse<String> updateOneUnitOrganization(@RequestBody StandardizeUnitManageEntity unitManageEntity) {
        String updateMessage = unitOrganizationService.updateOneUnitOrganization(unitManageEntity);
        return ServerResponse.asSucessResponse(updateMessage, updateMessage);
    }

    /**
     * 获取所属地区信息
     */
    @RequestMapping(value = "/getAreaInfo")
    public ServerResponse<List<ValueLabelVO>> getAreaInfo() {
        return ServerResponse.asSucessResponse(unitOrganizationService.getAreaInfo());
    }

    /**
     * 下载单位机构信息，未选中任何记录时导出列表中全部记录；有选中记录时导出选中记录
     *
     * @param unitManageEntities 列表中的数据
     */
    @RequestMapping(value = "/downloadUnitOrganization", produces = "application/json;charset=utf-8")
    public void downloadUnitOrganization(HttpServletResponse response, @RequestBody List<StandardizeUnitManageEntity> unitManageEntities) {
        unitOrganizationService.downloadUnitOrganization(response, unitManageEntities, "单位机构管理表", new StandardizeUnitManageEntity());
    }

    /**
     * 获取表格的筛选内容
     */
    @RequestMapping(value = "/getFilterObject")
    public ServerResponse<List<KeyValueVO>> getFilterObject() {
        return ServerResponse.asSucessResponse(unitOrganizationService.getFilterObject());
    }

}
