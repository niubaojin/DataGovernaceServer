package com.synway.governace.controller.navbar;

import com.synway.common.bean.ServerResponse;
import com.synway.governace.service.navbar.NavBarService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/navbar")
public class NavBarController {
    private Logger logger = Logger.getLogger(NavBarController.class);

    @Autowired
    private NavBarService navBarService;

    @RequestMapping("/getNavStatusByName")
    @ResponseBody
    public ServerResponse getNavStatusByName(@RequestParam("name")String name){
        boolean status = false;
        try{
            status = navBarService.getNavStatusByName(name);
        }catch (Exception e) {
            logger.error("获取Nav["+name+"]状态失败", e);
            return ServerResponse.asSucessResponse("获取Nav["+name+"]状态失败");
        }
        return ServerResponse.asSucessResponse(status);
    }

}
