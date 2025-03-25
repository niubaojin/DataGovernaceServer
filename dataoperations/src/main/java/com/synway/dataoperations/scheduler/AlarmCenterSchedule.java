package com.synway.dataoperations.scheduler;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.synway.common.exception.ExceptionUtil;
import com.synway.dataoperations.dao.AlarmMessageDao;
import com.synway.dataoperations.pojo.OperatorLog;
import com.synway.dataoperations.util.CryptoUtils;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jcajce.provider.digest.SM3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * @description 告警中心及治理跟踪数据定时任务
 */
@Service
public class AlarmCenterSchedule {
    private static Logger logger = LoggerFactory.getLogger(AlarmCenterSchedule.class);

    @Autowired()
    private Environment env;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired()
    private AlarmMessageDao alarmMessageDao;

    // 定时清理告警中心过期数据
    @Scheduled(cron = "${deleteOverTimeAlarmData}")
    public void deleteOverTimeAlarmData() {
        try {
            String days = env.getProperty("alarmDataStoreDays");
            logger.info("开始清理告警中心过期数据");
            if (StringUtils.isBlank(days)){
                days = "90";
                alarmMessageDao.deleteOverTimeAlarmData(Integer.valueOf(days));
            }else {
                alarmMessageDao.deleteOverTimeAlarmData(Integer.valueOf(days));
            }
            logger.info("清理告警中心过期数据完成");
        } catch (Exception e) {
            logger.error("定时清理告警中心过期数据报错：\n" + ExceptionUtil.getExceptionTrace(e));
        }
    }

    // 定时清理治理跟踪数据
    @Scheduled(cron = "${deleteOverTimeDGFData}")
    public void deleteOverTimeDGFData() {
        try {
            String days = env.getProperty("DGFDataStoreDays");
            logger.info("开始治理跟踪过期数据");
            if (StringUtils.isBlank(days)){
                days = "90";
                alarmMessageDao.deleteOverTimeDGFData(Integer.valueOf(days));
            }else {
                alarmMessageDao.deleteOverTimeDGFData(Integer.valueOf(days));
            }
            logger.info("清理治理跟踪过期数据完成");
        } catch (Exception e) {
            logger.error("定时清理治理跟踪过期数据报错：\n" + ExceptionUtil.getExceptionTrace(e));
        }
    }

    //定时推送 金华接口
    @Scheduled(cron = "${jinhua.sendOperatorLog}")
    public void sendOperatorLog() {
        Boolean ifSendOperatorLog = env.getProperty("jinhua.ifSendOperatorLog",Boolean.class);
        if(ifSendOperatorLog==null || ifSendOperatorLog ==Boolean.FALSE){
            logger.info("无需推送操作日志到金华日志系统");
            return;
        }

        logger.info("开始推送操作日志");
        //查询数据库，将没有推送过的数据查询（分页查询）
        for (int pageNum = 1,pageSize=50; ; pageNum++) {

            Page<OperatorLog> page = PageHelper.startPage(pageNum, pageSize);
            page.setOrderBy("create_time desc nulls last");
            List<OperatorLog> operatorLogs = alarmMessageDao.getOperatorLogList(StringUtils.EMPTY,StringUtils.EMPTY,
                    StringUtils.EMPTY,null,null,Boolean.FALSE);

            handleSend(operatorLogs);

            PageInfo<OperatorLog> pageInfo = new PageInfo<>(operatorLogs);
            if(pageInfo.isIsLastPage()) {
                break;
            }

        }

        //逐个推送，推送完后入库，如果推送失败，本次略过，下次继续推
        logger.info("操作日志推送完毕");
    }

    @Transactional(rollbackFor = Exception.class)
    public void handleSend ( List<OperatorLog> operatorLogs ) {
        LinkedHashMap<String,Object> map = new LinkedHashMap<String,Object>();
        map.put("sysId",env.getProperty("jinhua.sysId"));
        map.put("sendId", UUID.randomUUID().toString().replaceAll("-",StringUtils.EMPTY));
        map.put("logType","1");
        map.put("subLogType","101");
        map.put("appSecret ", env.getProperty("jinhua.appSecret"));

        List<Map<String,Object>> logContents = new ArrayList<>();
        for ( OperatorLog log : operatorLogs ) {
            Map<String,Object> logMap = BeanUtil.beanToMap(log);
            logContents.add(logMap);
        }

        map.put("logContents",logContents);
        map.put("checkSum",CryptoUtils.encode(map));
        //发送接口
//        String result = restTemplate.postForObject(env.getProperty("jinhua.url"),map,String.class);
        String result = CryptoUtils.httpsRequest(env.getProperty("jinhua.url"),map,String.class);
        logger.info("调用接口时成功，返回结果为:"+result);
        //更新数据库
        alarmMessageDao.updateOperatorLogs(operatorLogs);
    }


}
