package com.synway.datastandardmanager.controller;

import com.alibaba.fastjson.JSONObject;
import com.synway.common.bean.ServerResponse;
import com.synway.datastandardmanager.entity.dto.ObjectManageDTO;
import com.synway.datastandardmanager.entity.pojo.ObjectFieldEntity;
import com.synway.datastandardmanager.entity.pojo.SynlteFieldEntity;
import com.synway.datastandardmanager.entity.vo.*;
import com.synway.datastandardmanager.entity.vo.dataprocess.StandardFieldJson;
import com.synway.datastandardmanager.entity.vo.warehouse.DetectedTable;
import com.synway.datastandardmanager.service.DataSetStandardService;
import com.synway.datastandardmanager.valid.Resubmit;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据集标准页面
 *
 * @author nbj
 * @date 2025年6月25日11:02:48
 */
@Slf4j
@RestController
public class DataSetStandardController {

    @Autowired
    private DataSetStandardService dataSetStandardService;

    @Autowired()
    private Environment env;


    /**
     * 根据表tableID获取表字段信息
     *
     * @param tableId 表id
     */
    @RequestMapping(value = "/resourceManageObjectField")
    @ResponseBody
    public ServerResponse<List<ObjectFieldEntity>> queryObjectFieldList(@RequestParam("tableId") String tableId) {
        return ServerResponse.asSucessResponse(dataSetStandardService.queryObjectFieldListByTableId(tableId));
    }

    /**
     * 根据表的tableId号获取最新的数据信息，20191014发生修改
     */
    @RequestMapping(value = "/resourceManageObjectDetail", produces = "application/json;charset=utf-8")
    @ResponseBody
    public ServerResponse<Object> queryObjectDetail(String tableId) {
        return ServerResponse.asSucessResponse(dataSetStandardService.queryObjectDetail(tableId));
    }

    /**
     * 根据objectId和fieldName删除标准表字段
     *
     * @param objectId  标准id
     * @param fieldName 字段英文名
     */
    @RequestMapping(value = "/deleteObjectField")
    @ResponseBody
    public ServerResponse<String> deleteObjectField(Long objectId, String fieldName) {
        return ServerResponse.asSucessResponse(dataSetStandardService.deleteObjectField(objectId, fieldName));
    }

    /**
     * 获取来源关系的相关信息
     *
     * @param tableId 标准协议编码
     */
    @RequestMapping("/sourceRelationShipDataGet")
    @ResponseBody
    public ServerResponse<List<SourceRelationShipVO>> sourceRelationShipDataGet(String tableId) {
        return ServerResponse.asSucessResponse(dataSetStandardService.getSourceRelationShip(tableId));
    }

    /**
     * 新增来源关系中根据大类的id号获取一级分类信息
     *
     * @param mainValue '1':组织分类，'2':来源分类，'3'：资源分类
     */
    @RequestMapping("/getFirstClassModeByMain")
    @ResponseBody
    public ServerResponse<List<KeyValueVO>> getFirstClassModeByMain(String mainValue) {
        return ServerResponse.asSucessResponse(dataSetStandardService.getFirstClassModeByMain(mainValue));
    }

    /**
     * 根据大类id和一级分类的名称获取二级级分类信息
     *
     * @param mainValue       '1':组织分类 '2':来源分类   '3'：资源分类
     * @param firstClassValue
     */
    @RequestMapping("/getSecondaryClassModeByFirst")
    @ResponseBody
    public ServerResponse<List<KeyValueVO>> getSecondaryClassModeByFirst(String mainValue, String firstClassValue) {
        return ServerResponse.asSucessResponse(dataSetStandardService.getSecondaryClassModeByFirst(mainValue, firstClassValue));
    }

    /**
     * 删除选定的来源关系
     */
    @RequestMapping(value = "/deleteSourceRelation", method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse<String> deleteSourceRelation(@RequestBody JSONObject jsonObject) {
        List<SourceRelationShipVO> delSourceRelation = jsonObject.getJSONArray("delSourceRelation").toJavaList(SourceRelationShipVO.class);
        String outputDataId = jsonObject.getString("outputDataId");
        return ServerResponse.asSucessResponse(dataSetStandardService.deleteSourceRelation(delSourceRelation, outputDataId));
    }

    /**
     * 创建添加字段模态框中的搜索查询框
     *
     * @param type      类型 fieldId：搜索元素编码  columnName：表字段名
     * @param condition 搜索查询框
     */
    @RequestMapping(value = "/createAddColumnModel")
    @ResponseBody
    public ServerResponse<List<SelectFieldVO>> createAddColumnModel(String type, String condition) {
        return ServerResponse.asSucessResponse(dataSetStandardService.createAddColumnModel(type, condition));
    }

    /**
     * 创建添加字段模态框中的搜索查询框，新建标准字段时，标准列名改变或者唯一标识改变会调改接口
     *
     * @param type       类型 fieldId：搜索元素编码  columnName：表字段名
     * @param inputValue 输入框中填入的值
     */
    @RequestMapping(value = "/getAddColumnByInput")
    @ResponseBody
    public ServerResponse<SynlteFieldEntity> getAddColumnByInput(String type, String inputValue) {
        if (StringUtils.isEmpty(inputValue)) {
            return ServerResponse.asSucessResponse("请输入查询的标准列名");
        }
        return ServerResponse.asSucessResponse(dataSetStandardService.getAddColumnByInput(type, inputValue));
    }

    /**
     * 保存标准信息
     * 20200225 新增需求，一次性保存 数据信息/字段定义/来源关系 三种数据
     * 20220201 保存数据定义/字段定义/数据集对标 三种信息
     */
    @RequestMapping(value = "/saveResourceManageTable")
    @ResponseBody
    @Resubmit(delaySeconds = 1)
    public ServerResponse<DataSetStandardVO> saveResourceManageTable(@RequestBody @Valid ObjectManageDTO objectManageDTO) throws Exception {
        // 验证tableId和sourceId的唯一性，如果objectId为空，则判断tableId是否已经存在，如果objectId不为空，也要验证
        dataSetStandardService.checkTableIdSourceIdIsExists(objectManageDTO, false);
        if (dataSetStandardService.saveResourceFieldRelation(objectManageDTO)) {
            // 向标准化程序提交修改之后的数据
            String pushStdResult = "";
            String isPushMetaInfo = env.getProperty("isPushMetaInfo");
            if (isPushMetaInfo.equalsIgnoreCase("true")) {
                boolean pushStdStatus = dataSetStandardService.pushMetaInfo(objectManageDTO.getTableId());
                pushStdResult = pushStdStatus ? "，推送标准化成功" : "，推送标准化失败:具体原因请查看日志";
            }
            DataSetStandardVO dataSetStandardVO = new DataSetStandardVO();
            dataSetStandardVO.setMessage("标准表数据保存成功" + pushStdResult);
            dataSetStandardVO.setTableId(objectManageDTO.getTableId());
            return ServerResponse.asSucessResponse(dataSetStandardVO);
        } else {
            return ServerResponse.asErrorResponse("标准表数据保存失败");
        }
    }

    /**
     * 根据表英文名、项目空间、数据源ID 来获取表结构信息
     */
    @RequestMapping(value = "/getTableInfoForStandard")
    @ResponseBody
    public ServerResponse<DetectedTable> getTableInfoForStandard(String resId, String project, String tableName) {
        DetectedTable detectedTableInfo = dataSetStandardService.getDetectedTableInfo(resId, project, tableName);
        return ServerResponse.asSucessResponse(detectedTableInfo);
    }

    /**
     * 获取源应用系统名称下拉列表
     */
    @RequestMapping(value = "/getSysToSelect")
    @ResponseBody
    public ServerResponse<List<ValueLabelVO>> getSysToSelect() {
        return ServerResponse.asSucessResponse(dataSetStandardService.getAllSysList());
    }

    @RequestMapping(value = "/getAllStandardFieldJson")
    @ResponseBody
    public ServerResponse<StandardFieldJson> getAllStandardFieldJson(String tableId) {
        return ServerResponse.asSucessResponse(dataSetStandardService.getAllStandardFieldJson(tableId));
    }

    /**
     * 根据dataId获取数据仓库那边的数据
     * 数据仓库那边跳转过来，带上一个tableid，然后这边反查，获取查询到的值
     */
    @RequestMapping(value = "/getDataResourceInformation")
    @ResponseBody
    public ServerResponse<DataResourceRawInformationVO> getDataResourceInformation(String dataId, String project, String tableName) {
        return ServerResponse.asSucessResponse(dataSetStandardService.getDataResourceInformation(dataId, project, tableName));
    }

    /**
     * 创建表名的搜索提示框，其中：二级分类可能存在 其它数据／其它
     */
    @RequestMapping(value = "/createObjectTableSuggest")
    @ResponseBody
    public ServerResponse<List<String>> createObjectTableSuggest(@RequestParam("mainValue") String mainValue,
                                                                 @RequestParam("firstValue") String firstValue,
                                                                 @RequestParam("secondaryValue") String secondaryValue,
                                                                 @RequestParam("condition") String condition) {
        return ServerResponse.asSucessResponse(dataSetStandardService.createObjectTableSuggest(mainValue, firstValue, secondaryValue, condition));
    }

    @RequestMapping(value = "/getResourceManageByApprovalId")
    @ResponseBody
    public ServerResponse<JSONObject> getResourceManageByApprovalIdController(@RequestParam("approvalId") String approvalId) {
        log.info(">>>>>>传入的参数为，approvalId：" + approvalId);
        return ServerResponse.asSucessResponse(new JSONObject());
    }

    /**
     * 通过dataId从数据仓库中获取项目名，审批通过的项目名
     */
    @RequestMapping(value = "/getSchemaApproved")
    @ResponseBody
    public ServerResponse<List<String>> getSchemaApproved(String dataId) {
        return ServerResponse.asSucessResponse(dataSetStandardService.getSchemaApproved(dataId));
    }

    /**
     * 新增来源关系时，选择数据仓库，获取已探查英文表名接口
     *
     * @param resId       数据源ID
     * @param projectName 项目空间名
     * @param type        数据源类型
     */
    @RequestMapping(value = "/getDetectedTablesNameInfo")
    @ResponseBody
    public ServerResponse<List<KeyValueVO>> getDetectedTablesNameInfo(String resId, String projectName, String type) {
        return ServerResponse.asSucessResponse(dataSetStandardService.getDetectedTablesNameInfo(resId, projectName, type));
    }

    @RequestMapping(value = "/getIsExitsFiledMessage")
    @ResponseBody
    public ServerResponse<String> getIsExitsFiledMessage(String fieldId, String fieldName) {
        return ServerResponse.asSucessResponse(dataSetStandardService.getIsExitsFiledMessage(fieldId, fieldName));
    }

    /**
     * 数据组织分类选择选择原始库之后 获取对应的tableId值
     * 规则为：GA_RESOURCE_5位数据来源代码_6位行政区划_5位流水号
     *
     * @param dataSourceClassify 数据来源分类的中文值
     * @param code               数据协议的6位行政区划代码
     */
    @RequestMapping(value = "/getOrganizationTableId")
    @ResponseBody
    public ServerResponse<String> getOrganizationTableId(String dataSourceClassify, String code) {
        String message = dataSetStandardService.getOrganizationTableId(dataSourceClassify, code);
        return ServerResponse.asSucessResponse(message, message);
    }

    /**
     * 获取默认行政区划代码
     */
    @RequestMapping(value = "/getDefaultXZQH")
    @ResponseBody
    public ServerResponse<String> getDefaultXZQH() {
        String message = dataSetStandardService.getDefaultXZQH();
        return ServerResponse.asSucessResponse(message, message);
    }

    /**
     * 获取数据中心名
     */
    @RequestMapping(value = "/getDataCenter")
    @ResponseBody
//    @IgnoreSecurity
    public ServerResponse<List<KeyValueVO>> getDataCenter() {
        return ServerResponse.asSucessResponse(dataSetStandardService.getDataCenter());
    }

    /**
     * 根据数据中心id获取所有的数据源信息
     *
     * @param centerId 数据中心id
     */
    @RequestMapping(value = "/getDataResourceNameByCenterId")
    @ResponseBody
//    @IgnoreSecurity
    public ServerResponse<List<KeyValueVO>> getDataResourceNameByCenterId(@RequestParam("centerId") String centerId,
                                                                          @RequestParam("type") String type) {
        return ServerResponse.asSucessResponse(dataSetStandardService.getDataResourceNameByCenterId(centerId, type));
    }

    /**
     * 根据数据源类型过滤数据源
     *
     * @param type 数据源类型
     * @return
     */
    @RequestMapping(value = "/getDataResourceNameByType")
    @ResponseBody
//    @IgnoreSecurity
    public ServerResponse<List<KeyValueVO>> getDataResourceNameByType(@RequestParam("type") String type) {
        return ServerResponse.asSucessResponse(dataSetStandardService.getDataResourceNameByType(type));
    }

    @RequestMapping(value = "/searchObjectBySourceId")
    @ResponseBody
    public ServerResponse<Long> searchObjectBySourceId(String tableId) {
        return ServerResponse.asSucessResponse(dataSetStandardService.searchObjectBySourceId(tableId));
    }

    /**
     * 查看表是否已经存在
     * 1：从table_organization_assets统计表中查询该表是否已经存在（该表可能在数据库中不存在）
     * 2：从STANDARD_TABLE_CREATED中查询该表是否已经存在
     */
    @RequestMapping("/queryTableIsExist")
    @ResponseBody
    public ServerResponse<Boolean> queryTableIsExist(@RequestParam("tableName") String tableName) {
        return ServerResponse.asSucessResponse(dataSetStandardService.queryTableIsExist(tableName));
    }

}
