package com.synway.datastandardmanager.controller;

import com.synway.common.bean.ServerResponse;
import com.synway.datastandardmanager.entity.dto.BatchOperationDTO;
import com.synway.datastandardmanager.enums.ErrorCodeEnum;
import com.synway.datastandardmanager.service.BatchOperationService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * 批量操作的相关后台程序
 *
 * @author wangdongwei
 * @date 2021/7/15 9:25
 */
@RestController
@Slf4j
@RequestMapping("/batchOperation")
public class BatchOperationController {

    @Autowired
    private BatchOperationService batchOperationServiceImpl;

    /**
     * 批量更新表的使用状态
     */
    @RequestMapping(value = "/objectStatusEdit")
    public ServerResponse<String> objectStatusEdit(@RequestBody @Valid BatchOperationDTO dto, BindingResult bindingResult) throws Exception {
        // 传入参数核验
        if (bindingResult.hasErrors()) {
            throw new Exception(String.format("%s：%s", ErrorCodeEnum.CHECK_PARAMETER_ERROR, Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage()));
        }
        String msg = batchOperationServiceImpl.objectStatusEdit(dto);
        return ServerResponse.asSucessResponse(msg, msg);
    }


    /**
     * 批量设置框，对获取方式、组织分类、来源分类、资源标签等分类属性进行批量修改。
     */
    @RequestMapping(value = "/objectClassifyEdit")
    public ServerResponse<String> objectClassifyEdit(@RequestBody @Valid BatchOperationDTO dto, BindingResult bindingResult) throws Exception {
        // 传入参数核验
        if (bindingResult.hasErrors()) {
            throw new Exception(String.format("%s：%s", ErrorCodeEnum.CHECK_PARAMETER_ERROR, Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage()));
        }
        String msg = batchOperationServiceImpl.objectClassifyEdit(dto);
        return ServerResponse.asSucessResponse(msg, msg);
    }

    /**
     * 数据定义详情批量操作
     *
     * @param editPojo
     * @param bindingResult
     */
    @RequestMapping(value = "/objectFieldEdit")
    public ServerResponse<String> objectFieldEdit(@RequestBody @Valid BatchOperationDTO editPojo, BindingResult bindingResult) throws Exception {
        // 传入参数核验
        if (bindingResult.hasErrors()) {
            throw new Exception(String.format("%s：%s", ErrorCodeEnum.CHECK_PARAMETER_ERROR, Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage()));
        }
        String msg = batchOperationServiceImpl.objectFieldEdit(editPojo);
        return ServerResponse.asSucessResponse(msg, msg);
    }

}
