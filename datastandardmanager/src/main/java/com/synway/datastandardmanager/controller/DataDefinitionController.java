package com.synway.datastandardmanager.controller;


import com.synway.common.bean.ServerResponse;
import com.synway.datastandardmanager.pojo.PageSelectOneValue;
import com.synway.datastandardmanager.pojo.dataDefinitionManagement.*;
import com.synway.datastandardmanager.pojo.warehouse.DataSimilarParameter;
import com.synway.datastandardmanager.service.DataDefinitionService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 数据定义管理
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
     * @param dataDefinitionParameter
     * @return
     */
    @RequestMapping(value = "/searchDataDefinitionTable")
    public ServerResponse<Map<String,Object>> searchDataDefinitionTable(@RequestBody @Valid DataDefinitionParameter dataDefinitionParameter){
        log.info("开始查询数据定义管理的数据");
        Map<String, Object> dataDefinitionMap = dataDefinitionService.searchDataDefinitionTable(dataDefinitionParameter);
        log.info("数据定义管理数据查询结束:{}",dataDefinitionMap);
        return ServerResponse.asSucessResponse(dataDefinitionMap);
    }

    /**
     * 根据数据元内部标识符查询数据元的codeId获取字典值
     * @param gadsjFieldId
     * @return
     */
    @RequestMapping(value = "/getDictionaryName")
    public ServerResponse<String> getDictionaryName(@RequestParam("gadsjFieldId")String gadsjFieldId){
        log.info("传递的参数为:{}",gadsjFieldId);
        String dictionaryName = dataDefinitionService.getDictionaryNameById(gadsjFieldId);
        log.info("查出的字典名称为:{}",dictionaryName);
        return ServerResponse.asSucessResponse(dictionaryName,dictionaryName);
    }

    /**
     * 获取探查分析推荐标准数据集
     * @param dataSimilarParameter 调用仓库所需参数
     * @return
     */
    @RequestMapping(value = "/getDataSetDetectSimilarResult")
    public ServerResponse<List<PageSelectOneValue>> getDatasetDetectSimilarResult(@RequestBody DataSimilarParameter dataSimilarParameter){
        List<PageSelectOneValue> datasetDetectSimilarResult = dataDefinitionService.getDataSetDetectSimilarResult(dataSimilarParameter);
        return ServerResponse.asSucessResponse(datasetDetectSimilarResult);
    }

    /**
     * 关键字搜索全部数据集信息
     * @param searchText 关键字内容
     * @return
     */
    @RequestMapping(value = "/searchAllDataSetStandard")
    public ServerResponse<List<PageSelectOneValue>> searchAllDataSetStandard(String searchText){
        log.info("开始查询全部的数据集标准信息，传递的参数为:{}",searchText);
        List<PageSelectOneValue> dataSetList = dataDefinitionService.searchAllDataStandard(searchText);
        log.info("数据集标准信息查询结束,条数为:{}",dataSetList.size());
        return ServerResponse.asSucessResponse(dataSetList);
    }

    /**
     * 根据数据集id获取数据集对标信息
     * @param tableId 数据集id
     * @return
     */
    @RequestMapping(value = "/getDataSetMapping")
    public ServerResponse<ObjectRelationManage> getDataSetMapping(@RequestParam("tableId") String tableId){
        log.info("开始查询数据集对标信息，传递的参数为:{}",tableId);
        ObjectRelationManage dataSetMapping = dataDefinitionService.getDataSetMapping(tableId);
        log.info("查询的数据集对标信息为:{}",dataSetMapping);
        return ServerResponse.asSucessResponse(dataSetMapping);
    }

    /**
     * 数据集对标(数据项进行gadsjFieldId匹配后，给前端返回整个数据集对标信息)
     * @param objectRelationManage 数据集id
     * @return
     */
    @RequestMapping(value = "/getObjectRelation")
    public ServerResponse<ObjectRelationManage> getObjectRelation(@RequestBody @Valid ObjectRelationManage objectRelationManage){
        log.info("开始数据对标");
        ObjectRelationManage objectRelation = dataDefinitionService.getObjectRelation(objectRelationManage);
        log.info("数据对标结束,返回的数据为:{}",objectRelation);
        return ServerResponse.asSucessResponse(objectRelation);
    }

    /**
     * 原始标准库数据字段下拉框
     * 模糊搜素字段名称(字段描述)
     * @param searchText 关键字
     * @param tableId 标准协议
     * @return
     */
    @RequestMapping("/getColumnNameList")
    public ServerResponse<List<ObjectFieldRelation>> getColumnNameList(String searchText, @RequestParam("tableId")String tableId){
        List<ObjectFieldRelation> columnNameList = dataDefinitionService.getColumnNameList(searchText,tableId);
        return ServerResponse.asSucessResponse(columnNameList);
    }

    /**
     * 探查推荐映射/数据元映射/序号映射
     * @param objectRelationManage 数据集对标参数
     * @return
     */
    @RequestMapping("/dataFieldMapping")
    public ServerResponse<ObjectRelationManage> dataFieldMapping(@RequestBody ObjectRelationManage objectRelationManage){
        ObjectRelationManage objectFieldRelation = dataDefinitionService.dataFieldMapping(objectRelationManage);
        return ServerResponse.asSucessResponse(objectFieldRelation);
    }


}
