package com.synway.datastandardmanager.controller;

import com.synway.common.bean.ServerResponse;
import com.synway.datastandardmanager.config.CacheManager;
import com.synway.datastandardmanager.constants.Common;
import com.synway.datastandardmanager.entity.dto.CommonDTO;
import com.synway.datastandardmanager.entity.dto.CreateTableInfoDTO;
import com.synway.datastandardmanager.entity.dto.ObjectStoreInfoDTO;
import com.synway.datastandardmanager.entity.dto.RefreshCreateTableDTO;
import com.synway.datastandardmanager.entity.pojo.ObjectStoreInfoEntity;
import com.synway.datastandardmanager.entity.vo.BuildTableFilterVO;
import com.synway.datastandardmanager.entity.vo.KeyValueVO;
import com.synway.datastandardmanager.entity.vo.PageVO;
import com.synway.datastandardmanager.service.BuildTableInfoManageService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 建表管理相关接口
 *
 * @author nbj
 * @date 2025年8月8日14:13:40
 */
@Slf4j
@RestController
@RequestMapping
public class BuildTableInfoManageController {

    @Autowired
    private BuildTableInfoManageService service;

    @Resource
    private CacheManager cacheManager;

    /**
     * 更新是否自动入库的字段信息
     */
    @RequestMapping(value = "/editImportFlagSave", method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse<String> editImportFlagSave(@RequestBody CreateTableInfoDTO saveColumnComparision) {
        service.updateColumnToInfo(saveColumnComparision, 2);
        return ServerResponse.asSucessResponse(Common.UPDATE_SUCCESS, Common.UPDATE_SUCCESS);
    }

    /**
     * 刷新已建表信息
     */
    @RequestMapping(value = "/refreshCreateTable", method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse<String> refreshCreateTable(@RequestBody RefreshCreateTableDTO refreshCreateTableDTO) {
        try {
            if (StringUtils.isBlank(refreshCreateTableDTO.getDataCenterId()) && StringUtils.isBlank(refreshCreateTableDTO.getResId())) {
                service.refreshCreateTableAll(refreshCreateTableDTO);
                return ServerResponse.asSucessResponse("刷新成功", "刷新成功");
            } else {
                cacheManager.addOrUpdateCache("refreshCreateTableStatus", true);
                service.refreshCreateTableProject(refreshCreateTableDTO);
                cacheManager.addOrUpdateCache("refreshCreateTableStatus", false);
                return ServerResponse.asSucessResponse("刷新成功", "刷新成功");
            }
        } catch (Exception e) {
            cacheManager.addOrUpdateCache("refreshCreateTableStatus", false);
            log.error(">>>>>>刷新已建表信息报错：", e);
            return ServerResponse.asErrorResponse("刷新失败：" + e.getMessage());
        }
    }

    /**
     * 刷新 已建表信息（单表刷新）
     */
    @RequestMapping(value = "/refreshCreateTableOneTable", method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse<String> refreshCreateTableOneTable(@RequestBody RefreshCreateTableDTO createTableDTO) {
        service.refreshCreateTableOneTable(createTableDTO);
        return ServerResponse.asSucessResponse("刷新成功", "刷新成功");
    }

    @RequestMapping("/getRefreshCreateTableStatus")
    @ResponseBody
    public ServerResponse getRefreshCreateTableStatus() {
        ServerResponse serverResponse;
        try {
            serverResponse = ServerResponse.asSucessResponse(cacheManager.getValue("refreshCreateTableStatus"));
        } catch (Exception e) {
            serverResponse = ServerResponse.asErrorResponse("查询刷新建表信息状态报错：" + e.getMessage());
            log.error(">>>>>>查询刷新建表信息状态报错：", e);
        }
        return serverResponse;
    }

    /**
     * 数据集建表管理页面数据
     */
    @RequestMapping(value = "/searchTableInfo", method = {RequestMethod.POST})
    @ResponseBody
    public ServerResponse<PageVO> searchTableInfo(@RequestBody ObjectStoreInfoDTO objectStoreInfoDTO) {
        return ServerResponse.asSucessResponse(service.searchTableInfo(objectStoreInfoDTO));
    }

    /**
     * 通过数据中心id获取数据源信息
     *
     * @param dataCenterId 数据中心id
     */
    @RequestMapping(value = "/getDataResource", method = {RequestMethod.GET})
    @ResponseBody
    public ServerResponse<List<KeyValueVO>> getDataResource(String dataCenterId, String storeType) {
        return ServerResponse.asSucessResponse(service.getDataResource(dataCenterId, storeType));
    }

    /**
     * 根据数据源Id查找项目空间信息
     */
    @RequestMapping(value = "/getProjectNameList", method = {RequestMethod.GET})
    @ResponseBody
    public ServerResponse<List<String>> getProjectNameList(@RequestParam("resId") String resId) throws Exception {
        return ServerResponse.asSucessResponse(service.getProjectList(resId));
    }

    /**
     * 获取建表信息管理页面的筛选框
     */
    @RequestMapping(value = "/getFilterInfo", method = {RequestMethod.GET})
    @ResponseBody
    public ServerResponse<BuildTableFilterVO> getFilterInfo() {
        return ServerResponse.asSucessResponse(service.getFilterInfo());
    }

    /**
     * 更新是否输出入库状态
     */
    @RequestMapping(value = "/updateImportFlag")
    @ResponseBody
    public ServerResponse updateImportFlag(@RequestBody ObjectStoreInfoEntity objectStoreInfo) {
        service.updateImportFlag(objectStoreInfo);
        return ServerResponse.asSucessResponse("更新输出入库状态成功");
    }

    /**
     * 获取标准数据集数据
     */
    @RequestMapping(value = "/getStandardDataSet")
    @ResponseBody
    public ServerResponse getStandardDataSet() {
        return ServerResponse.asSucessResponse(service.getStandardDataSet());
    }

    /**
     * 获取物理表数据列表
     */
    @RequestMapping(value = "/getDetectedTableList")
    @ResponseBody
    public ServerResponse getDetectedTableList(@RequestParam("resId") String resId, @RequestParam("projectName") String projectName) {
        return ServerResponse.asSucessResponse(service.getDetectedTableList(resId, projectName));
    }

    /**
     * 获取标准表字段信息
     */
    @RequestMapping(value = "/getStandardDataItem")
    @ResponseBody
    public ServerResponse getStandardDataItem(@RequestParam("objectId") String objectId) {
        return ServerResponse.asSucessResponse(service.getStandardDataItem(objectId));
    }

    /**
     * 获取仓库表字段信息
     */
    @RequestMapping(value = "/getDetectedFieldInfo")
    @ResponseBody
    public ServerResponse getDetectedFieldInfo(@RequestBody ObjectStoreInfoEntity objectStoreInfo) {
        return ServerResponse.asSucessResponse(service.getDetectedFieldInfo(objectStoreInfo));
    }

    /**
     * 保存建表信息
     */
    @RequestMapping(value = "/saveCreateTableInfo")
    @ResponseBody
    public ServerResponse saveCreateTableInfo(@RequestBody ObjectStoreInfoEntity objectStoreInfo) {
        return ServerResponse.asSucessResponse(service.saveCreateTableInfo(objectStoreInfo));
    }

    @RequestMapping(value = "/getCreateTableInfo")
    @ResponseBody
    public ServerResponse getCreateTableInfo(@RequestBody ObjectStoreInfoEntity objectStoreInfo) {
        return ServerResponse.asSucessResponse(service.getCreateTableInfo(objectStoreInfo));
    }

    /**
     * 保存建表管理页面的显示字段列表
     *
     * @param showField
     */
    @RequestMapping("/updateBuildTableShowField")
    @ResponseBody
    public ServerResponse updateBuildTableShowField(@RequestBody CommonDTO showField) {
        return ServerResponse.asErrorResponse(service.updateBuildTableShowField(showField));
    }

    @RequestMapping("/getBuildTableShowField")
    @ResponseBody
    public ServerResponse<List<String>> getBuildTableShowField() {
        return ServerResponse.asSucessResponse(service.getBuildTableShowField());
    }

    @RequestMapping("/getStoreTypeList")
    @ResponseBody
    public ServerResponse getStoreTypeList() {
        return ServerResponse.asSucessResponse(service.getStoreTypeList());
    }

    @RequestMapping("/deleteObjectStore")
    @ResponseBody
    public ServerResponse deleteObjectStore(@RequestBody ObjectStoreInfoEntity objectStoreInfo) {
        String msg = service.deleteObjectStore(objectStoreInfo);
        if (msg.equalsIgnoreCase(Common.DEL_SUCCESS)) {
            return ServerResponse.asSucessResponse("true", msg);
        } else if (msg.equalsIgnoreCase(Common.DEL_FAIL)) {
            return ServerResponse.asErrorResponse(msg);
        } else {
            return ServerResponse.asSucessResponse("false", msg);
        }
    }

}
