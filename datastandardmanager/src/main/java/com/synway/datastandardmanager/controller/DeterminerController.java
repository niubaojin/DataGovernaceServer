package com.synway.datastandardmanager.controller;

import com.synway.common.bean.ServerResponse;
import com.synway.common.exception.SystemException;
import com.synway.datastandardmanager.entity.dto.DeterminerDTO;
import com.synway.datastandardmanager.entity.pojo.FieldDeterminerEntity;
import com.synway.datastandardmanager.entity.vo.FieldDeterminerFilterVO;
import com.synway.datastandardmanager.entity.vo.KeyValueVO;
import com.synway.datastandardmanager.enums.ErrorCodeEnum;
import com.synway.datastandardmanager.service.DeterminerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * 限定词管理的相关接口
 *
 * @author nbj
 * @date 2025年8月4日09:22:22
 */
@Validated
@RestController
@RequestMapping("/fieldDeterminer")
public class DeterminerController {

    @Autowired
    private DeterminerService determinerService;

    /**
     * 查询限定词的相关信息  使用前端分页
     */
    @RequestMapping(value = "/searchTable")
    public ServerResponse<List<FieldDeterminerEntity>> getFieldDeterminerTable(@RequestBody DeterminerDTO dto) {
        return ServerResponse.asSucessResponse(determinerService.getFieldDeterminerTable(dto));
    }

    /**
     * 获取筛选值
     */
    @RequestMapping(value = "/getFilterObject")
    public ServerResponse<FieldDeterminerFilterVO> getFilterObject() {
        return ServerResponse.asSucessResponse(determinerService.getFilterObject());
    }

    /**
     * 新增限定词
     */
    @RequestMapping(value = "/addOneData")
    public ServerResponse<String> addOneData(@RequestBody @Valid FieldDeterminerEntity data, BindingResult bindingResult) {
        // 传入参数核验
        if (bindingResult.hasErrors()) {
            throw SystemException.asSystemException(ErrorCodeEnum.CHECK_PARAMETER_ERROR,
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        String result = determinerService.addOneData(data);
        return ServerResponse.asSucessResponse(result, result);
    }

    /**
     * 编辑限定词
     */
    @RequestMapping(value = "/upOneData")
    public ServerResponse<String> upOneData(@RequestBody @Valid FieldDeterminerEntity data, BindingResult bindingResult) {
        // 传入参数核验
        if (bindingResult.hasErrors()) {
            throw SystemException.asSystemException(ErrorCodeEnum.CHECK_PARAMETER_ERROR,
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        String result = determinerService.upOneData(data);
        return ServerResponse.asSucessResponse(result, result);
    }

    /**
     * 启用/停用 限定词
     *
     * @param id      限定词内部编码
     * @param state   01：新建  07：停用禁用
     * @param modDate 该条记录的修改时间 与数据库对应不上会造成更新失败
     */
    @RequestMapping(value = "/updateDeterminerState")
    public ServerResponse<String> updateDeterminerState(@RequestParam("determinerId") @NotBlank String id,
                                                        @RequestParam("state") String state,
                                                        @RequestParam("modDate") @NotBlank String modDate) {
        String result = determinerService.updateDeterminerState(id, state, modDate);
        return ServerResponse.asSucessResponse(result, result);
    }

    /**
     * 根据中文名获取标识符
     */
    @RequestMapping(value = "/getDNameByChinese")
    public ServerResponse<String> getDNameByChines(@RequestParam("chineseName") @NotBlank String chineseName) {
        String result = determinerService.getDNameByChines(chineseName);
        return ServerResponse.asSucessResponse(result, result);
    }

    /**
     * 获取默认的限定词内部编码信息
     */
    @RequestMapping(value = "/getDeterminerId")
    public ServerResponse<String> getDeterminerId() {
        String result = determinerService.getDeterminerId();
        return ServerResponse.asSucessResponse(result, result);
    }

    /**
     * 获取限定词中文名称下拉框
     */
    @RequestMapping(value = "/searchDeterminerNameList")
    public ServerResponse<List<KeyValueVO>> searchDeterminerNameList(String searchName){
        return ServerResponse.asSucessResponse(determinerService.searchDeterminerNameList(searchName));
    }

}
