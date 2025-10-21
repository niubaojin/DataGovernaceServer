package com.synway.datastandardmanager.controller;

import com.synway.common.bean.ServerResponse;
import com.synway.datastandardmanager.entity.dto.ObjectManageDTO;
import com.synway.datastandardmanager.service.UserAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 权限控制相关
 *
 * @author nbj
 * @date 2025年8月6日14:11:14
 */
@RestController
@RequestMapping("/authority")
public class UserAuthorityController {

    @Autowired
    private UserAuthorityService userAuthorityService;

    @RequestMapping(value = "/addUserAuthorityData")
    public ServerResponse<String> addUserAuthorityData(@RequestBody ObjectManageDTO objectManageDTO) {
        return ServerResponse.asSucessResponse(userAuthorityService.addUserAuthorityData(objectManageDTO));
    }

}
