package com.synway.datastandardmanager.controller;

import com.synway.common.bean.ServerResponse;
import com.synway.datastandardmanager.entity.vo.ValueLabelVO;
import com.synway.datastandardmanager.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 通用Controller，提供需要的接口
 * @author nbj
 * @date 2025年8月25日15:19:38
 */
@RestController
@RequestMapping("/commonData")
public class CommonController {

    @Autowired
    private CommonService service;

    /**
     * 查询数据厂商
     */
    @RequestMapping("/searchValtextInfo")
    public ServerResponse<List<ValueLabelVO>> searchValtext() {
        return ServerResponse.asSucessResponse(service.searchValtext());
    }

}
