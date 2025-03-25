package com.synway.property.controller;

import com.synway.property.service.AlarmSettingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName AlarmSettingController
 * @Descroption 异常告警设置
 * @Author majia
 * @Date 2020/4/27 10:31
 * @Version 1.0
 **/
@RequestMapping("/dataOrganizationMonitoring/AlarmSetting")
@Controller
public class AlarmSettingController {

    @Autowired
    private AlarmSettingService service;

    private static Logger logger = LoggerFactory.getLogger(AlarmSettingController.class);

    /**
     * @description 发送表数据异常信息至告警中心
     * @return
     */
    @Scheduled(cron = "${sendAlarmMsg}")
    public void sendAlarmMsg(){
        logger.info("开始发送告警信息");
        service.sendAlarmMsg();
        logger.info("发送告警信息结束");
    }
}
