package com.synway.datastandardmanager.controller;

import com.synway.common.bean.ServerResponse;
import com.synway.datastandardmanager.interceptor.IgnoreSecurity;
import com.synway.datastandardmanager.pojo.PageSelectOneValue;
import com.synway.datastandardmanager.pojo.fielddeterminermanage.FieldDeterminer;
import com.synway.datastandardmanager.service.FieldCodeValService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 * 通用Controller，提供需要的接口
 * @author obito
 * @version 1.0
 * @date
 */
@RestController
@Slf4j
@RequestMapping("/commonData")
public class CommonController {

    @Autowired
    private FieldCodeValService fieldCodeValService;

    /**
     * 查询数据厂商
     * @return
     */
    @RequestMapping("/searchValtextInfo")
    public ServerResponse<List<PageSelectOneValue>> searchValtext(){
        log.info("开始查询厂商信息");
        List<PageSelectOneValue> valtextInfoList = fieldCodeValService.searchValtext();
        log.info("查询的厂商数据为:"+valtextInfoList);
        return ServerResponse.asSucessResponse(valtextInfoList);
    }

}
