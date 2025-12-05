package com.synway.datastandardmanager.controller;

import com.alibaba.fastjson.JSONObject;
import com.synway.common.bean.ServerResponse;
import com.synway.datastandardmanager.constants.Common;
import com.synway.datastandardmanager.entity.pojo.ObjectFieldEntity;
import com.synway.datastandardmanager.entity.pojo.DsmStandardTableCreatedEntity;
import com.synway.datastandardmanager.entity.vo.KeyValueVO;
import com.synway.datastandardmanager.entity.vo.createTable.BuildTableInfoVO;
import com.synway.datastandardmanager.entity.vo.createTable.CreateTableVO;
import com.synway.datastandardmanager.service.CreateTableService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 建表相关的操作
 *
 * @author
 * @date 2019/10/25 9:58
 */
@RequestMapping("/dbManager")
@RestController
@Validated
public class CreateTableController {

    @Autowired
    private CreateTableService createTableService;

    /**
     * 阿里云的相关建表接口
     */
    @RequestMapping("/buildTable")
    @ResponseBody
    public ServerResponse buildTable(@RequestBody BuildTableInfoVO buildTableInfoVo) {
        return ServerResponse.asSucessResponse(createTableService.buildAdsOrOdpsTable(buildTableInfoVo));
    }

    /**
     * 获取指定类型的所有标准字段信息
     *
     * @param dataBaseType 类型
     */
    @RequestMapping("/getColumnType")
    @ResponseBody
    public ServerResponse<Object> getColumnType(@NotNull String dataBaseType) {
        return ServerResponse.asSucessResponse(createTableService.getColumnType(dataBaseType));
    }

    /**
     * 一键切换字段的定义关系
     */
    @RequestMapping(value = "/columnCorrespondClick", method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse<List<ObjectFieldEntity>> columnCorrespondClick(@RequestBody JSONObject allObjectList) {
        return ServerResponse.asSucessResponse(createTableService.columnCorrespondClick(allObjectList));
    }

    /**
     * 创建建表sql信息
     */
    @RequestMapping(value = "/showCreateTableSql", method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse<String> showCreateTableSql(@RequestBody BuildTableInfoVO buildTableInfoVo) {
        return ServerResponse.asSucessResponse("", createTableService.showCreateTableSql(buildTableInfoVo));
    }

    /**
     * 获取已经创建的表信息
     *
     * @param tableId 表协议ID
     */
    @RequestMapping(value = "/getAllStandardTableCreatedList", method = {RequestMethod.GET})
    @ResponseBody
    public ServerResponse<List<DsmStandardTableCreatedEntity>> getAllStandardTableCreatedList(String tableId) {
        return ServerResponse.asSucessResponse(createTableService.getAllStandardTableCreatedList(tableId));
    }

    /**
     * presto 建表需要单独写个建表的接口
     */
    @RequestMapping(value = "/createHuaWeiTable")
    @ResponseBody
    public ServerResponse<CreateTableVO> createHuaWeiTable(@RequestBody BuildTableInfoVO buildTableInfoVo) {
        return ServerResponse.asSucessResponse(createTableService.createHuaWeiTableService(buildTableInfoVo));
    }

    /**
     * 对于新的需求，所有字段都会先存储在页面中，所以需要添加公共字段之后，再返回需要添加之后的所有字段信息
     */
    @RequestMapping(value = "/getCommonColumn", method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse<List<ObjectFieldEntity>> getCommonColumn(@RequestBody List<ObjectFieldEntity> allObjectList) {
        return ServerResponse.asSucessResponse(createTableService.getCommonColumnService(allObjectList));
    }

    /**
     * vue接口获取 建表时的数据库类型
     */
    @RequestMapping(value = "/getDataPlatFormTypeVue")
    @ResponseBody
    public ServerResponse<List<String>> getDataPlatFormTypeVue() {
        return ServerResponse.asSucessResponse(Common.DATA_TYPE_LIST);
    }

    /**
     * 获取分区类型
     */
    @RequestMapping(value = "/getPartitionType")
    @ResponseBody
    public ServerResponse<List<KeyValueVO>> getPartitionType() {
        return ServerResponse.asSucessResponse(createTableService.getPartitionType());
    }


}
