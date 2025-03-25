package com.synway.governace.controller.systemConfig;

import com.synway.common.bean.ServerResponse;
import com.synway.governace.pojo.*;
import com.synway.governace.service.systemConfig.ThresholdConfigService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

@Controller
@RequestMapping()
public class ThresholdConfigController {
    private Logger logger = Logger.getLogger(ThresholdConfigController.class);

    @Autowired
    private ThresholdConfigService thresholdConfigService;

    /**
     * 获取初始化配置信息
     */
    @RequestMapping(value="/getThresholdConfigInitInfo",produces="application/json;charset=utf-8")
    @ResponseBody
    public ServerResponse<List<ThresholdConfig>> getThresholdConfigInitInfo(){
        try {
            List<ThresholdConfig> list = thresholdConfigService.getThresholdConfigInitInfo();
            return ServerResponse.asSucessResponse(list);
        } catch (Exception e) {
            logger.info("获取初始化配置信息失败", e);
            return ServerResponse.asSucessResponse("获取初始化配置信息出错:" + e.getMessage());
        }
    }
}
