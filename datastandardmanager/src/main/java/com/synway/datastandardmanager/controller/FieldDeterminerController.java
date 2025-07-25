package com.synway.datastandardmanager.controller;

import com.alibaba.fastjson.JSONObject;
import com.synway.common.bean.ServerResponse;
import com.synway.datastandardmanager.exceptionhandler.ErrorCode;
import com.synway.datastandardmanager.exceptionhandler.SystemException;
import com.synway.datastandardmanager.pojo.PageSelectOneValue;
import com.synway.datastandardmanager.pojo.fielddeterminermanage.FieldDeterminer;
import com.synway.datastandardmanager.pojo.fielddeterminermanage.FieldDeterminerParameter;
import com.synway.datastandardmanager.pojo.fielddeterminermanage.FieldDeterminerTable;
import com.synway.datastandardmanager.service.FieldDeterminerService;
import com.synway.datastandardmanager.util.ExceptionUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Objects;

/**
 * 限定词管理的相关接口
 * @author wangdongwei
 * @date 2021/7/16 9:07
 */
@RestController
@Slf4j
@RequestMapping("/fieldDeterminer")
@Validated
public class FieldDeterminerController {
    @Autowired
    private FieldDeterminerService fieldDeterminerServiceImpl;


    /**
     * 查询限定词的相关信息  使用前端分页
     * @param parameter
     * @return
     */
    @RequestMapping(value = "/searchTable")
    public ServerResponse<List<FieldDeterminer>> getFieldDeterminerTable(@RequestBody FieldDeterminerParameter parameter){
        log.info("查询限定词的请求参数为{}", JSONObject.toJSONString(parameter));
        List<FieldDeterminer> fieldDeterminerTable = fieldDeterminerServiceImpl.searchTable(parameter);
        log.info("========查询限定词结束=========");
        return ServerResponse.asSucessResponse(fieldDeterminerTable);
    }


    /**
     * 获取筛选值
     * @return
     */
    @RequestMapping(value = "/getFilterObject")
    public ServerResponse<FieldDeterminerTable> getFilterObject(){
        log.info("查询限定词的相关筛选数据");
        FieldDeterminerTable fieldDeterminerTable = fieldDeterminerServiceImpl.getFilterObject();
        return ServerResponse.asSucessResponse(fieldDeterminerTable);
    }


    /**
     * 新增限定词
     * @param data
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/addOneData")
    public ServerResponse<String> addOneData(@RequestBody @Valid FieldDeterminer data,
                                             BindingResult bindingResult){
        log.info("限定词添加的内容为{}", JSONObject.toJSONString(data));
        // 传入参数核验
        if(bindingResult.hasErrors()){
            throw SystemException.asSystemException(ErrorCode.CHECK_PARAMETER_ERROR,
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        String result = fieldDeterminerServiceImpl.addOneData(data);
        log.info("========添加限定词结束=========");
        return ServerResponse.asSucessResponse(result,result);
    }

    /**
     * 编辑限定词
     * @param data
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/upOneData")
    public ServerResponse<String> upOneData(@RequestBody @Valid FieldDeterminer data,
                                             BindingResult bindingResult){
        log.info("限定词编辑的内容为{}", JSONObject.toJSONString(data));
        // 传入参数核验
        if(bindingResult.hasErrors()){
            throw SystemException.asSystemException(ErrorCode.CHECK_PARAMETER_ERROR,
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        String result = fieldDeterminerServiceImpl.upOneData(data);
        log.info("========编辑限定词结束=========");
        return ServerResponse.asSucessResponse(result,result);
    }

    /**
     * 启用/停用 限定词
     * @param id   限定词内部编码
     * @param state  01：新建  07：停用禁用
     * @param modDate 该条记录的修改时间 与数据库对应不上会造成更新失败
     * @return
     */
    @RequestMapping(value = "/updateDeterminerState")
    public ServerResponse<String> updateDeterminerState(@RequestParam("determinerId") @NotBlank String id,
                                            @RequestParam("state")String state,
                                            @RequestParam("modDate") @NotBlank String modDate){
        log.info("启用/停用限定词的参数条件为determinerId:{} ,state:{} , modDate:{}", id,state,modDate);
        String result = fieldDeterminerServiceImpl.updateDeterminerState(id,state,modDate);
        log.info("========启用/停用限定词结束=========");
        return ServerResponse.asSucessResponse(result,result);
    }

    /**
     * 点击停用，标准字段信息表objectfield是否存在引用关联
     * @param id   DeterminerID 限定词ID
     * @param modDate  修改时间
     * @return   true:不可以更新  false：可以更新
     */
    @RequestMapping(value = "/checkIsDeactivate")
    public ServerResponse<Boolean> checkIsDeactivate(@RequestParam("determinerId") @NotBlank String id,
                                                        @RequestParam("modDate") @NotBlank String modDate){
        log.info("标准字段信息表objectField是否存在引用关联为determinerId:{} ,dName:{} , modDate:{}", id,modDate);
        boolean result = fieldDeterminerServiceImpl.checkIsDeactivate(id,modDate);
        log.info("========标准字段信息表objectField是否存在引用关联结束=========");
        return ServerResponse.asSucessResponse(result);
    }


    /**
     * 根据中文名获取标识符
     * @param chineseName
     * @return
     */
    @RequestMapping(value = "/getDNameByChinese")
    public ServerResponse<String> getDNameByChines(@RequestParam("chineseName") @NotBlank String chineseName){
        String result = fieldDeterminerServiceImpl.getDName(chineseName);
        return ServerResponse.asSucessResponse(result,result);
    }

    /**
     * 获取默认的 限定词内部编码 信息
     * @return
     */
    @RequestMapping(value = "/getDeterminerId")
    public ServerResponse<String> getDeterminerId(){
        String result = fieldDeterminerServiceImpl.getDeterminerId();
        return ServerResponse.asSucessResponse(result,result);
    }

    /**
     * 获取限定词中文名称下拉框
     * @return
     */
    @RequestMapping(value = "/searchDeterminerNameList")
    public ServerResponse<List<PageSelectOneValue>> searchDeterminerNameList(String searchName){
        ServerResponse<List<PageSelectOneValue>> serverResponse = null;
        try{
            List<PageSelectOneValue> list = fieldDeterminerServiceImpl.searchDeterminerNameList(searchName);
            serverResponse = ServerResponse.asSucessResponse(list);
        }catch(Exception e){
            log.error(ExceptionUtil.getExceptionTrace(e));
            serverResponse = ServerResponse.asErrorResponse("获取限定词下拉列表信息报错"+e.getMessage());
        }
        return serverResponse;
    }

}
