package com.synway.datastandardmanager.controller;
import com.alibaba.fastjson.JSONObject;
import com.synway.common.bean.ServerResponse;
import com.synway.datastandardmanager.exceptionhandler.ErrorCode;
import com.synway.datastandardmanager.exceptionhandler.SystemException;
import com.synway.datastandardmanager.pojo.batchoperation.ObjectClassifyEditPojo;
import com.synway.datastandardmanager.pojo.batchoperation.ObjectFieldEditPojo;
import com.synway.datastandardmanager.pojo.batchoperation.ObjectStatusEditPojo;
import com.synway.datastandardmanager.service.BatchOperationService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * 批量操作的相关后台程序
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
     * @return
     */
    @RequestMapping(value="/objectStatusEdit")
    public ServerResponse<String> objectStatusEdit(@RequestBody @Valid ObjectStatusEditPojo objectStatusEditPojo,
                                                   BindingResult bindingResult){
        // 传入参数核验
        if(bindingResult.hasErrors()){
            throw SystemException.asSystemException(ErrorCode.CHECK_PARAMETER_ERROR,
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        log.info("修改参数为：{}", JSONObject.toJSONString(objectStatusEditPojo));
        String flag = batchOperationServiceImpl.objectStatusEdit(objectStatusEditPojo);
        return ServerResponse.asSucessResponse(flag,flag);
    }


    /**
     * 批量设置框，对获取方式、组织分类、来源分类、资源标签等分类属性进行批量修改。
     * @return
     */
    @RequestMapping(value="/objectClassifyEdit")
    public ServerResponse<String> objectClassifyEdit(@RequestBody @Valid ObjectClassifyEditPojo editPojo,
                                                   BindingResult bindingResult){
        // 传入参数核验
        if(bindingResult.hasErrors()){
            throw SystemException.asSystemException(ErrorCode.CHECK_PARAMETER_ERROR,
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        log.info("修改参数为：{}", JSONObject.toJSONString(editPojo));
        String flag = batchOperationServiceImpl.objectClassifyEdit(editPojo);
        return ServerResponse.asSucessResponse(flag,flag);
    }

    /**
     * 数据定义详情批量操作
     * @param editPojo
     * @param bindingResult
     * @return
     */
    @RequestMapping(value="/objectFieldEdit")
    public ServerResponse<String> objectFieldEdit(@RequestBody @Valid ObjectFieldEditPojo editPojo,
                                                     BindingResult bindingResult){
        // 传入参数核验
        if(bindingResult.hasErrors()){
            throw SystemException.asSystemException(ErrorCode.CHECK_PARAMETER_ERROR,
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        log.info("修改参数为：{}", JSONObject.toJSONString(editPojo));
        String flag = batchOperationServiceImpl.objectFieldEdit(editPojo);
        return ServerResponse.asSucessResponse(flag,flag);
    }


}
