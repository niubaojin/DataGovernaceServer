package com.synway.datastandardmanager.controller;

import com.synway.common.bean.ServerResponse;
import com.synway.datastandardmanager.entity.dto.DataDefinitionDTO;
import com.synway.datastandardmanager.entity.pojo.StandardizeObjectfieldRelEntity;
import com.synway.datastandardmanager.entity.vo.KeyValueVO;
import com.synway.datastandardmanager.entity.vo.ObjectRelationManageVO;
import com.synway.datastandardmanager.entity.vo.PageVO;
import com.synway.datastandardmanager.service.DataDefinitionService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 数据定义管理
 *
 * @author obito
 * @version 1.0
 * @date
 */
@RestController
@Slf4j
@RequestMapping("/dataDefinition")
@Validated
public class DataDefinitionController {

    @Autowired
    private DataDefinitionService dataDefinitionService;


    /**
     * 数据定义管理页面数据 后端分页
     *
     * @param dto
     */
    @RequestMapping(value = "/searchDataDefinitionTable")
    public ServerResponse<PageVO> searchDataDefinitionTable(@RequestBody @Valid DataDefinitionDTO dto) {
        return ServerResponse.asSucessResponse(dataDefinitionService.searchDataDefinitionTable(dto));
    }

    /**
     * 根据数据元内部标识符查询数据元的codeId获取字典值
     *
     * @param gadsjFieldId
     * @return
     */
    @RequestMapping(value = "/getDictionaryName")
    public ServerResponse<String> getDictionaryName(@RequestParam("gadsjFieldId") String gadsjFieldId) {
        String dictionaryName = dataDefinitionService.getDictionaryNameById(gadsjFieldId);
        return ServerResponse.asSucessResponse(dictionaryName, dictionaryName);
    }

    /**
     * 获取探查分析推荐标准数据集
     *
     * @param dto 调用仓库所需参数
     */
    @RequestMapping(value = "/getDataSetDetectSimilarResult")
    public ServerResponse<List<KeyValueVO>> getDatasetDetectSimilarResult(@RequestBody DataDefinitionDTO dto) throws Exception {
        return ServerResponse.asSucessResponse(dataDefinitionService.getDataSetDetectSimilarResult(dto));
    }

    /**
     * 关键字搜索全部数据集信息
     *
     * @param searchText 关键字内容
     */
    @RequestMapping(value = "/searchAllDataSetStandard")
    public ServerResponse<List<KeyValueVO>> searchAllDataSetStandard(String searchText) {
        return ServerResponse.asSucessResponse(dataDefinitionService.searchAllDataStandard(searchText));
    }

    /**
     * 根据数据集id获取数据集对标信息
     *
     * @param tableId 数据集id
     */
    @RequestMapping(value = "/getDataSetMapping")
    public ServerResponse<ObjectRelationManageVO> getDataSetMapping(@RequestParam("tableId") String tableId) throws Exception {
        return ServerResponse.asSucessResponse(dataDefinitionService.getDataSetMapping(tableId));
    }

    /**
     * 数据集对标(数据项进行gadsjFieldId匹配后，给前端返回整个数据集对标信息)
     *
     * @param objectRelationManage 数据集id
     */
    @RequestMapping(value = "/getObjectRelation")
    public ServerResponse<ObjectRelationManageVO> getObjectRelation(@RequestBody @Valid ObjectRelationManageVO objectRelationManage) {
        return ServerResponse.asSucessResponse(dataDefinitionService.getObjectRelation(objectRelationManage));
    }

    /**
     * 原始标准库数据字段下拉框
     * 模糊搜素字段名称(字段描述)
     *
     * @param searchText 关键字
     * @param tableId    标准协议
     */
    @RequestMapping("/getColumnNameList")
    public ServerResponse<List<StandardizeObjectfieldRelEntity>> getColumnNameList(String searchText, @RequestParam("tableId") String tableId) {
        return ServerResponse.asSucessResponse(dataDefinitionService.getColumnNameList(searchText, tableId));
    }

    /**
     * 探查推荐映射/数据元映射/序号映射
     *
     * @param objectRelationManage 数据集对标参数
     */
    @RequestMapping("/dataFieldMapping")
    public ServerResponse<ObjectRelationManageVO> dataFieldMapping(@RequestBody ObjectRelationManageVO objectRelationManage) {
        return ServerResponse.asSucessResponse(dataDefinitionService.dataFieldMapping(objectRelationManage));
    }

}
