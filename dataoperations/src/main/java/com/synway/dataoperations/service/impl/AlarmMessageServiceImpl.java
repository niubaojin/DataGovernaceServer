package com.synway.dataoperations.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.synway.common.exception.ExceptionUtil;
import com.synway.dataoperations.dao.AlarmMessageDao;
import com.synway.dataoperations.dao.DAOHelper;
import com.synway.dataoperations.enums.AlarmCodeEnum;
import com.synway.dataoperations.interceptor.AuthorizedUserUtils;
import com.synway.dataoperations.pojo.AlarmMessage;
import com.synway.dataoperations.pojo.AlarmPushSetting;
import com.synway.dataoperations.pojo.LoginUser;
import com.synway.dataoperations.pojo.OperatorLog;
import com.synway.dataoperations.service.AlarmMessageService;
import com.synway.dataoperations.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class AlarmMessageServiceImpl implements AlarmMessageService {
    public static final Logger logger = LoggerFactory.getLogger(AlarmMessageServiceImpl.class);

    @Autowired
    private AlarmMessageDao dao;

    @Override
    public void insertAlarmMessage(String jsonString){
        try {
            // 添加主键
            List<AlarmMessage> alarmMessages = new ArrayList<>();
            if (jsonString.contains("[{")){
                alarmMessages = (List<AlarmMessage>) JSONObject.parseArray(jsonString,AlarmMessage.class);
            }else {
                AlarmMessage jsonObject = JSONObject.parseObject(jsonString,AlarmMessage.class);
                alarmMessages.add(jsonObject);
            }
            alarmMessages.stream().forEach(d->{
                // 生成uuid用来做主键
                String id = UUID.randomUUID().toString().replace("-","");
                d.setID(id);
                // 根据代码回填名称
                String alarmflagName = AlarmCodeEnum.getNameByCodeAndType(d.getAlarmflag(), "ALARMFLAG");
                String alarmLevelName = AlarmCodeEnum.getNameByCodeAndType(d.getLevel(), "ALARMLEVEL");
                d.setAlarmflagName(alarmflagName);
                d.setLevelName(alarmLevelName);
            });
            DAOHelper.insertDelList(alarmMessages, dao, "insertAlarmMessage", 200);
            logger.info("告警信息入库成功");
        }catch (Exception e){
            logger.error("告警信息入库失败:\n" + ExceptionUtil.getExceptionTrace(e));
        }
    }

    @Override
    public List<AlarmPushSetting> getAlarmSettings() {
        List<AlarmPushSetting> settings = new ArrayList<>();
        try {
            String[] jsonStrings = dao.getAlarmPushSetting();
            for (int i = 0; i<jsonStrings.length; i++){
                AlarmPushSetting setting = null;
                String jsonString = jsonStrings[i];
                setting = JSONObject.parseObject(jsonString, AlarmPushSetting.class);
                settings.add(setting);
            }
        } catch (Exception e) {
            logger.error(ExceptionUtil.getExceptionTrace(e));
        }
        return settings;
    }

    @Transactional(rollbackFor = Exception.class)
    public void insertOperatorLogs(List<OperatorLog> operatorLogs) throws Exception{
        //获取用户名、身份证等信息
        LoginUser loginUser = AuthorizedUserUtils.getInstance().getAuthor();
        operatorLogs.stream().parallel().forEach(e->{
            try {
                e.setUserId(loginUser.getIdCard());
                e.setUserName(loginUser.getUserName());
                e.setUserNum(loginUser.getUserId());
                e.setOrganization(loginUser.getOrganName());
                e.setOrganizationId(loginUser.getOrganId());
                dao.insertOperatorLog(e);
            } catch (Exception ex) {
                logger.error("操作日志[{}]入库失败，失败原因:",e.toString(),ex);
                throw ex;
            }
        });
    }

    @Override
    public PageInfo<OperatorLog> getOperatorLogList( Integer currentPage , Integer pageSize ,
                            String sortName , String sortOrder ,
                            String opeModule , String opeType , String opePerson ,
                            String opeBeginTime, String opeEndTime ) {

        Page<OperatorLog> page = PageHelper.startPage(currentPage, pageSize);
        if (StringUtils.isBlank(sortName)) {
            sortName = "create_Time";
            sortOrder = "desc";
        }
        if (StringUtils.isNotBlank(sortOrder)) {
            page.setOrderBy(sortName + "\t" + sortOrder);
        }
        Date beginTime = null;
        Date endTime = null;
        //前端页面操作开始时间和结束时间会一起提供
        if (StringUtils.isNotBlank(opeBeginTime) && StringUtils.isNotBlank(opeEndTime)) {
            beginTime = DateUtil.parseDate(opeBeginTime);
            endTime = DateUtil.parseDate(opeEndTime);
        }
        List<OperatorLog> list = dao.getOperatorLogList(opeModule, opeType, opePerson, beginTime, endTime, null);
        return new PageInfo<>(list);
    }


}
