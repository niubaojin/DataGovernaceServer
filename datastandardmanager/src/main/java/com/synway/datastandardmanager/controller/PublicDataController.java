package com.synway.datastandardmanager.controller;

import com.alibaba.fastjson.JSONObject;
import com.synway.common.bean.ServerResponse;
import com.synway.datastandardmanager.exceptionhandler.ErrorCode;
import com.synway.datastandardmanager.exceptionhandler.SystemException;
import com.synway.datastandardmanager.pojo.PageSelectOneValue;
import com.synway.datastandardmanager.pojo.publicDataManage.PublicDataField;
import com.synway.datastandardmanager.pojo.publicDataManage.PublicDataPojo;
import com.synway.datastandardmanager.service.PublicDataManageService;
import com.synway.datastandardmanager.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;

/**
 * 公共数据项管理的相关接口
 *
 * @author obito
 * @version 1.0
 */
@RestController
@Slf4j
@RequestMapping("/publicData")
@Validated
public class PublicDataController {

    @Autowired
    private PublicDataManageService publicDataManageService;

    /**
     * 新增/更新公共数据项
     *
     * @param publicDataPojo
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/addOrUpdateOnePublicData")
    public ServerResponse<String> addOrUpdateOnePublicData(@RequestBody @Valid PublicDataPojo publicDataPojo, BindingResult bindingResult) throws Exception {
        log.info("添加的公共数据项内容为{}", JSONObject.toJSONString(publicDataPojo));
        // 传入参数核验
        if (bindingResult.hasErrors()) {
            throw SystemException.asSystemException(ErrorCode.CHECK_PARAMETER_ERROR,
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        String result = publicDataManageService.addPublicData(publicDataPojo);
        log.info("========添加公共数据项结束=========");
        return ServerResponse.asSucessResponse(result, result);
    }

    /**
     * 根据id和组织名称删除数据项分组
     *
     * @param id
     * @param groupName
     * @return
     */
    @RequestMapping(value = "/deleteOnePublicDataGroup")
    public ServerResponse<String> deleteOnePublicDataGroup(@RequestParam("id") @NotBlank String id,
                                                           @RequestParam("groupName") @NotBlank String groupName) {
        log.info("要删除的公共数据项组的名称为{}", groupName);
        String result = publicDataManageService.deleteOnePublicDataGroup(id, groupName);
        log.info("============删除公共数据项组的名称结束=======");
        return ServerResponse.asSucessResponse(result, result);
    }

    /**
     * 根据关键字搜索数据项中文名称
     *
     * @param searchName 搜索内容
     * @return
     */
    @RequestMapping(value = "/searchFieldChineseList")
    public ServerResponse<List<PageSelectOneValue>> searchFieldChineseList(String searchName) {
        ServerResponse<List<PageSelectOneValue>> serverResponse = null;
        try {
            log.info("开始查询数据项中文名称列表");
            List<PageSelectOneValue> list = publicDataManageService.searchFieldChineseList(searchName);
            serverResponse = ServerResponse.asSucessResponse(list);
        } catch (Exception e) {
            log.error(ExceptionUtil.getExceptionTrace(e));
            serverResponse = ServerResponse.asErrorResponse("获取数据元中文名称下拉列表信息报错" + e.getMessage());
        }
        return serverResponse;
    }

    /**
     * 根据数据项组名称查询数据项信息
     *
     * @param groupName 组织名称
     * @return
     */
    @RequestMapping(value = "/searchPublicDataByGroupName")
    public ServerResponse<PublicDataPojo> searchPublicDataByGroupName(@RequestParam("groupName") @NotBlank String groupName) {
        log.info("要查询的公共数据项组的名称为{}", groupName);
        PublicDataPojo publicDataPojo = publicDataManageService.searchPublicDataByGroupName(groupName);
        log.info("查询到的公共数据项组为:{}", publicDataPojo);
        return ServerResponse.asSucessResponse(publicDataPojo);
    }

    /**
     * 查询数据项分组列表
     *
     * @return
     */
    @RequestMapping(value = "/searchGroupName")
    public ServerResponse<List<PageSelectOneValue>> searchGroupName() {
        List<PageSelectOneValue> responseList = publicDataManageService.searchGroupNameList();
        return ServerResponse.asSucessResponse(responseList);
    }

    /**
     * 下载公共数据项信息
     * 未选中任何记录时导出列表中全部记录；有选中记录时导出选中记录
     *
     * @param response
     * @param data     列表中的数据
     */
    @RequestMapping(value = "/downloadPublicDataExcel", produces = "text/html;charset=utf-8")
    public void downloadPublicDataExcel(HttpServletResponse response,
                                        @RequestBody List<PublicDataField> data) {
        log.info("=======开始下载公共数据项的相关信息=======");
        publicDataManageService.downloadPublicDataFieldExcel(response, data, "公共数据项管理表.xlsx", new PublicDataField());
        log.info("=======下载公共数据项的相关信息结束========");
    }

    /**
     * 导入数据要素类型数据
     *
     * @param file
     * @param id   公共数据项组id
     */
    @RequestMapping(value = "/importPublicDataFieldExcel")
    public ServerResponse<List<PublicDataField>> importPublicDataFieldExcel(@RequestParam("file") MultipartFile file, @RequestParam("id") String id) {
        log.info("=======开始导入公共数据项的相关信息=======");
        List<PublicDataField> list = publicDataManageService.importPublicDataFieldExcel(file, id);
        log.info("=======导入公共数据项的相关信息结束,导入成功的数据量为{}========", list.size());
        return ServerResponse.asSucessResponse(list);
    }
}
