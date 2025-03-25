package com.synway.property.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.synway.property.common.UrlConstants;
import com.synway.property.dao.AlarmSettingDao;
import com.synway.property.enums.SysCodeEnum;
import com.synway.property.pojo.alarmsetting.AlarmMessage;
import com.synway.property.service.AlarmSettingService;
import com.synway.property.util.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @ClassName AlarmSettingServiceImpl
 * @Descroption
 * @Author majia
 * @Date 2020/4/27 10:37
 * @Version 1.0
 **/
@Service
public class AlarmSettingServiceImpl implements AlarmSettingService {
    private static final Logger logger = LoggerFactory.getLogger(AlarmSettingServiceImpl.class);

    @Autowired
    private AlarmSettingDao settingDao;

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public void sendAlarmMsg(){
        try {
            List<AlarmMessage> alarmMessages = settingDao.getAssetsAlarmMsgs();
//            //DEBUG
//            AlarmMessage alarmMessage = new AlarmMessage();
//            alarmMessage.setAlarmtime("2022-03-07 12:13:23");
//            alarmMessage.setAlarmcontent("dfthjdthjetkljheltje");
//            alarmMessage.setTableNameCh("tablename");
//            alarmMessage.setTableNameEn("tablename");
//            alarmMessage.setTableId("jz_resource_001");
//            alarmMessage.setLevelName("一般");
//            alarmMessages.add(alarmMessage);
            alarmMessages.stream().forEach(d->{
                d.setAlarmsystem("数据工厂");
                d.setAlarmmodule("数据资产");
                d.setAlarmflag(0);
                int alarmFlag = Integer.parseInt(SysCodeEnum.getCodeByNameAndType(d.getLevelName(),"ALARMLEVEL"));
                d.setLevel(alarmFlag);
            });
            if (alarmMessages.size()>0){
                String jsonString = JSONArray.toJSONString(alarmMessages);
                JSONObject jsonObject = restTemplate.postForObject(UrlConstants.DATAOPERATIONS_BASEURL + "/pushAlarmInfo",jsonString,JSONObject.class);
                if (jsonObject.getInteger("status").equals(1)){
                    logger.info("发送告警信息至数据运维成功");
                }
            }
        }catch (Exception e){
            logger.error("发送告警信息至数据运维失败\n" + ExceptionUtil.getExceptionTrace(e));
        }
    }

}
