package com.synway.datastandardmanager.controller;

import com.synway.common.bean.ServerResponse;
import com.synway.datastandardmanager.constants.Common;
import com.synway.datastandardmanager.entity.pojo.StandardizePublicDataEntity;
import com.synway.datastandardmanager.entity.pojo.StandardizePublicDataFieldEntity;
import com.synway.datastandardmanager.entity.vo.KeyValueVO;
import com.synway.datastandardmanager.enums.ErrorCodeEnum;
import com.synway.datastandardmanager.service.PublicDataService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Validated
@RestController
@RequestMapping("/publicData")
public class PublicDataController {

    @Autowired
    private PublicDataService publicDataService;


    /**
     * 新增/更新公共数据项
     */
    @RequestMapping(value = "/addOrUpdateOnePublicData")
    public ServerResponse<String> addOrUpdateOnePublicData(@RequestBody @Valid StandardizePublicDataEntity publicData, BindingResult bindingResult) throws Exception {
        // 传入参数核验
        if (bindingResult.hasErrors()) {
            throw new Exception(String.format("%s：%s", ErrorCodeEnum.CHECK_PARAMETER_ERROR, Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage()));
        }
        String result = publicDataService.addOrUpdateOnePublicData(publicData);
        if (result.equalsIgnoreCase(Common.UPDATE_FAIL)){
            return ServerResponse.asErrorResponse(Common.UPDATE_FAIL);
        }else {
            return ServerResponse.asSucessResponse(result, result);
        }
    }

    /**
     * 根据id和组织名称删除数据项分组
     */
    @RequestMapping(value = "/deleteOnePublicDataGroup")
    public ServerResponse<String> deleteOnePublicDataGroup(@RequestParam("id") @NotBlank String id,
                                                           @RequestParam("groupName") @NotBlank String groupName) {
        String result = publicDataService.deleteOnePublicDataGroup(id, groupName);
        return ServerResponse.asSucessResponse(result, result);
    }

    /**
     * 根据关键字搜索数据项中文名称
     *
     * @param searchName 搜索内容
     */
    @RequestMapping(value = "/searchFieldChineseList")
    public ServerResponse<List<KeyValueVO>> searchFieldChineseList(String searchName) {
        return ServerResponse.asSucessResponse(publicDataService.searchFieldChineseList(searchName));
    }

    /**
     * 根据数据项组名称查询数据项信息
     *
     * @param groupName 组织名称
     */
    @RequestMapping(value = "/searchPublicDataByGroupName")
    public ServerResponse<StandardizePublicDataEntity> searchPublicDataByGroupName(@RequestParam("groupName") @NotBlank String groupName) {
        return ServerResponse.asSucessResponse(publicDataService.searchPublicDataByGroupName(groupName));
    }

    /**
     * 查询数据项分组名称列表
     */
    @RequestMapping(value = "/searchGroupName")
    public ServerResponse<List<KeyValueVO>> searchGroupName() {
        return ServerResponse.asSucessResponse(publicDataService.searchGroupNameList());
    }

    /**
     * 下载公共数据项信息
     * 未选中任何记录时导出列表中全部记录；有选中记录时导出选中记录
     *
     * @param response
     * @param data     列表中的数据
     */
    @RequestMapping(value = "/downloadPublicDataExcel", produces = "text/html;charset=utf-8")
    public void downloadPublicDataExcel(HttpServletResponse response, @RequestBody List<StandardizePublicDataFieldEntity> data) {
        publicDataService.downloadPublicDataFieldExcel(response, data, new StandardizePublicDataFieldEntity());
    }

    /**
     * 导入数据要素类型数据
     *
     * @param file
     * @param id   公共数据项组id
     */
    @RequestMapping(value = "/importPublicDataFieldExcel")
    public ServerResponse<List<StandardizePublicDataFieldEntity>> importPublicDataFieldExcel(@RequestParam("file") MultipartFile file, @RequestParam("id") String id) {
        return ServerResponse.asSucessResponse(publicDataService.importPublicDataFieldExcel(file, id));
    }

}
