package com.synway.datastandardmanager.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.synway.common.bean.ServerResponse;
import com.synway.datastandardmanager.entity.dto.DataSetManageDTO;
import com.synway.datastandardmanager.entity.pojo.ObjectFieldEntity;
import com.synway.datastandardmanager.entity.pojo.DsmSourceFieldInfoEntity;
import com.synway.datastandardmanager.entity.vo.DataResourceRawInformationVO;
import com.synway.datastandardmanager.entity.vo.DataSetManageVO;
import com.synway.datastandardmanager.entity.vo.ValueLabelVO;
import com.synway.datastandardmanager.entity.vo.ValueLabelChildrenVO;
import com.synway.datastandardmanager.service.DataSetManageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 数据集管理页面的相关接口
 *
 * @author wangdongwei
 * @date 2020/11/27 15:58
 */
@RestController
public class DataSetManageController {

    @Autowired
    private DataSetManageService service;

    /**
     * 数据集管理页面，查询汇总表的相关数据：使用前端分页
     */
    @RequestMapping(value = "/searchSummaryTable", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<DataSetManageVO> searchSummaryTable(@RequestBody @Valid DataSetManageDTO dataSetManageDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ServerResponse.asErrorResponse(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return ServerResponse.asSucessResponse(service.searchSummaryTable(dataSetManageDTO));
    }

    /**
     * 查询主分类的相关信息
     *
     * @param mainClassify 二个主分类的英文信息 dataOrganizationClassify/dataSourceClassify
     * @return 对应的所有一级分类信息  null：查询失败
     */
    @RequestMapping("/getPrimaryClassifyData")
    @ResponseBody
    public ServerResponse<List<ValueLabelVO>> getPrimaryClassifyData(@RequestParam("mainClassify") String mainClassify) {
        return ServerResponse.asSucessResponse(service.getPrimaryClassifyData(mainClassify));
    }


    /**
     * 根据选择的数据资源目录的二大分类之一 和 一级分类信息 来获取二级分类信息
     * 2020/2/21 一级分类标识改变，获取二级分类改变
     *
     * @param mainClassify        二个主分类的英文信息 dataOrganizationClassify/dataSourceClassify
     * @param primaryClassifyCode 一级分类的代码值
     * @return 对应的所有一级分类信息
     */
    @RequestMapping("/getSecondaryClassifyData")
    @ResponseBody
    public ServerResponse<List<ValueLabelVO>> getSecondaryClassifyData(@RequestParam("mainClassify") String mainClassify,
                                                                       @RequestParam("primaryClassifyCode") String primaryClassifyCode) {
        // 其实 我也很好奇 为什么只能查询一个，但是既然马佳大佬这么写 那我就只查询一个吧
        return ServerResponse.asSucessResponse(service.getSecondaryClassifyData(mainClassify, primaryClassifyCode));
    }

    /**
     * 根据选择的数据组织分类的二级信息查询三级信息
     * 2021/11/16
     *
     * @param primaryClassifyCode 二级分类码值
     * @param secondClassifyCode  三级分类的代码值
     * @return 对应的所有三级分类信息
     */
    @RequestMapping("/getThreeClassifyData")
    @ResponseBody
    public ServerResponse<List<ValueLabelVO>> getThreeClassifyData(@RequestParam("primaryClassifyCode") String primaryClassifyCode,
                                                                   @RequestParam("secondClassifyCode") String secondClassifyCode) {
        return ServerResponse.asSucessResponse(service.getThreeClassifyData(primaryClassifyCode, secondClassifyCode));
    }


    /**
     * 获取资源状况  启用(122) 停用(22)
     */
    @RequestMapping("/getResourceStatus")
    @ResponseBody
    public ServerResponse<List<ValueLabelVO>> getResourceStatus() {
        return ServerResponse.asSucessResponse(service.getResourceStatus());
    }

    /**
     * 获取提示信息，为空时显示全部；支持数据名、真实表名、资源标识模糊匹配
     */
    @RequestMapping("/queryConditionSuggestion")
    @ResponseBody()
    public ServerResponse<List<String>> queryConditionSuggestion(String searchValue) {
        return ServerResponse.asSucessResponse(service.queryConditionSuggestion(searchValue));
    }

    /**
     * 获取字段分类的码表信息
     *
     * @return
     */
//    @IgnoreSecurity
    @RequestMapping("/getAllFieldClassList")
    @ResponseBody
    public ServerResponse<JSONArray> getAllFieldClassList() {
        return ServerResponse.asSucessResponse(service.getAllFieldClassList());
    }

    @RequestMapping("/checkAndGetTableID")
    @ResponseBody
    public ServerResponse checkAndGetTableID(String sourceID) {
        //检测是否已经有目标协议
        String tableID = service.checkAndGetTableID(sourceID);
        return ServerResponse.asSucessResponse(tableID, tableID);
    }

    /**
     * 导入原始数据项框中的数据
     */
    @RequestMapping("/initSourceFieldTable")
    @ResponseBody
    public ServerResponse<List<DsmSourceFieldInfoEntity>> initSourceFieldTable(String sourceProtocol, String tableName, String sourceSystem,
                                                                               String sourceFirm, String tableId) {
        List<DsmSourceFieldInfoEntity> result = service.initSourceFieldTable(sourceProtocol, tableName, sourceSystem, sourceFirm, tableId);
        return ServerResponse.asSucessResponse(result);
    }

    /**
     * 在导入来源关系的数据中，如果是从数据仓库中获取 并且不是流程中，则需要调用该接口
     */
    @RequestMapping("/addTableColumnByEtl")
    @ResponseBody
    public ServerResponse<String> addTableColumnByEtl(String sourceProtocol,
                                                      String sourceSystem,
                                                      String sourceFirm,
                                                      String tableName,
                                                      String dataName,
                                                      String tableId,
                                                      String centerId,
                                                      String centerName,
                                                      String project,
                                                      String resourceId) {
        String returnMesg = service.addTableColumnByEtl(sourceProtocol, sourceSystem, sourceFirm, tableName, dataName, tableId, centerId, centerName, project, resourceId);
        return ServerResponse.asSucessResponse(returnMesg, returnMesg);
    }

    @RequestMapping("/getSourceFieldColumnList")
    @ResponseBody
    public ServerResponse<List<ObjectFieldEntity>> getSourceFieldColumnList(@RequestBody JSONObject jsonObject) {
        return ServerResponse.asSucessResponse(service.getSourceFieldColumnList(jsonObject));
    }

    /**
     * 二级分类的layui的数据
     */
    @RequestMapping("/getSecondaryClassLayui")
    @ResponseBody
    public ServerResponse<List<ValueLabelChildrenVO>> getSecondaryClassLayui(@RequestParam("mainClassify") String mainClassify,
                                                                             @RequestParam("primaryClassifyCh") String primaryClassifyCh) {
        return ServerResponse.asSucessResponse(service.getSecondaryClassLayuiService(mainClassify, primaryClassifyCh));
    }

    /**
     * 根据分级分类信息以及数据名 回填对应的表名
     */
    @RequestMapping("/getEnFlagByChnType")
    @ResponseBody
    public ServerResponse<String> getEnFlagByChnType(@RequestParam("organizationClassifys") String organizationClassifys,
                                                     @RequestParam("sourceClassifys") String sourceClassifys,
                                                     @RequestParam("dataSourceName") String dataSourceName,
                                                     @RequestParam("flag") Boolean flag) {
        String tableName = service.getEnFlagByChnType(organizationClassifys, sourceClassifys, dataSourceName, flag);
        return ServerResponse.asSucessResponse(tableName, tableName);
    }

    /**
     * 如果objectId 等于null  直接判断该表名是否已经存在了，存在则提示，表命名已经存在了
     * 如果objectId 不等于null 先判断表名的objectId 和传进来的objectId 是否一致。
     * 如果一致，可以给他保存，如果不一致（说明这个表名已经存在了），则不给他保存
     **/
    @RequestMapping("/checTableNamekIsExit")
    @ResponseBody
    public ServerResponse<Object> checTableNamekIsExit(String realTableName, String objectId) {
        return ServerResponse.asSucessResponse(service.checTableNamekIsExit(realTableName, objectId));
    }

    @RequestMapping("/getOrganizationRelationByTableName")
    @ResponseBody
    public ServerResponse<DataResourceRawInformationVO> getOrganizationRelationByTableName(@RequestParam("addTableName") String addTableName) {
        return ServerResponse.asSucessResponse(service.getOrganizationRelationByTableName(addTableName));
    }

    /**
     * 数据信息中，数据组织layui插件需要的数据
     */
    @RequestMapping("/getAllClassifyLayui")
    @ResponseBody
    public ServerResponse<List<ValueLabelChildrenVO>> getAllClassifyLayui(@RequestParam("mainClassifyCh") String mainClassifyCh) {
        return ServerResponse.asSucessResponse(service.getAllClassifyLayuiService(mainClassifyCh));
    }

    @RequestMapping("/getDataBaseType")
    @ResponseBody
    public ServerResponse getDataBaseType() {
        String dbType = service.getDatabaseType();
        return ServerResponse.asSucessResponse(dbType, dbType);
    }

    /**
     * 根据数据来源分类的id值获取对应的代码数据值
     *
     * @param classifyIds 逗号分隔，JZCODEGASJZZFL010101,JZCODEGASJZZFL010101001
     */
    @RequestMapping("/getCodeNameByClassifyId")
    @ResponseBody
    public ServerResponse<String> getCodeNameByClassifyId(@RequestParam("classifyIds") String classifyIds) {
        return ServerResponse.asSucessResponse(service.getCodeNameByClassifyId(classifyIds));
    }

    /**
     * 根据页面上的sourceId 来获取到自增id值，来获取最新的sourceid信息
     * 附带验证功能
     *
     * @param sourceId           页面上的sourceId信息
     * @param dataSourceClassify 数据来源分类的代码
     * @param code               6位本地行政代码
     * @return 如果为空 表示这个sourceid不是标准协议信息 如果不为空，表示已经获取了最新的sourceid信息
     */
    @RequestMapping("/getNewSourceIdById")
    @ResponseBody
    public String getNewSourceIdById(String sourceId, String dataSourceClassify, String code) {
        return service.getNewSourceIdById(sourceId, dataSourceClassify, code);
    }

    /**
     * 获取数据分级的码表信息
     */
    @RequestMapping("/searchSecurityLevel")
    @ResponseBody
    public ServerResponse<List<ValueLabelVO>> searchSecurityLevel() {
        return ServerResponse.asSucessResponse(service.searchSecurityLevel());
    }


    /**
     * 获取字段安全分级列表
     */
    @RequestMapping("/searchFieldSecurityLevelList")
    @ResponseBody
    public ServerResponse<List<ValueLabelVO>> searchFieldSecurityLevelList(@RequestParam("codeId") String codeId) {
        return ServerResponse.asSucessResponse(service.searchFieldSecurityLevelList(codeId));
    }


}
