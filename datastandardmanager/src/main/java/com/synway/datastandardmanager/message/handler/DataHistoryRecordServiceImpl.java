package com.synway.datastandardmanager.message.handler;

import com.alibaba.fastjson.JSONObject;
import com.synway.datastandardmanager.message.RecordService;
import com.synway.datastandardmanager.pojo.DataProcess.DataProcess;
import com.synway.datastandardmanager.pojo.DataProcess.OrganUser;
import com.synway.datastandardmanager.util.DateUtil;
import com.synway.datastandardmanager.util.ExceptionUtil;
import com.synway.datastandardmanager.util.RestTemplateHandle;
import com.synway.datastandardmanager.util.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据历程的相关方法 将
 */
@Service(value = "DataHistoryRecordServiceImpl")
public class DataHistoryRecordServiceImpl implements RecordService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataHistoryRecordServiceImpl.class);
    @Autowired
    RestTemplateHandle restTemplateHandle;
    @Autowired
    ConcurrentHashMap<String,DataProcess> buildTableCourseHashMap;

    @Autowired()private Environment env;


    @Override
    public Boolean sendMessage(DataProcess dataProcess,String userIds) {
        String uuidDate = UUIDUtil.getUUID()+"_"+ DateUtil.formatDate(new Date(),DateUtil.DEFAULT_PATTERN_DATETIME_SIMPLE_FULL);
        Boolean flag = false;
        OrganUser organUser = null;
        try{
            Integer userId = StringUtils.isEmpty(userIds)?0:Integer.valueOf(userIds);
            dataProcess.setUserId(userId);
            dataProcess = dataProcess ==null?new DataProcess():dataProcess;
            if(StringUtils.isBlank(dataProcess.getIp())){
                dataProcess.setIp(env.getProperty("server.address"," "));
            }
            // 根据 userId 查询dubbo接口获取用户信息 拼接出最后的对象信息
//            organUser = restTemplateHandle.getUserMesageByDubbo(userId);
//            LOGGER.info("从dubbo接口获取到数据为："+JSONObject.toJSONString(organUser));
//            if(organUser != null && organUser.getSuccess() && organUser.getData().size() >0){
//                dataProcess.setAreaId(organUser.getData().get(0).getOrgan().getSymbol12());
//                dataProcess.setDept(organUser.getData().get(0).getOrgan().getOrganName());
//                dataProcess.setOperator(organUser.getData().get(0).getUser().getName());
//                dataProcess.setPoliceno(organUser.getData().get(0).getUser().getPoliceNo());
//            }
            if(StringUtils.isEmpty(dataProcess.getOperateTime())){
                dataProcess.setOperateTime(DateUtil.formatDate(new Date(),DateUtil.DEFAULT_PATTERN_DATETIME));
            }
            Integer num = 1;
            do{
                LOGGER.info(String.format("开始第[%s]建表的信息发送数据历程的保存接口",num));
                flag = restTemplateHandle.sendDataHistoryRecord(dataProcess);
               // 开始将保存之后的数据进行解析，判断是否已经保存成功
                num ++;
            }while(!flag && num <=3);

            LOGGER.info("开始将缓存中暂存的建表信息重新发送到保存的接口");
            for(String key:buildTableCourseHashMap.keySet()){
                LOGGER.info(String.format("将上次的信息[%s]发送到数据历程的保存接口", JSONObject.toJSONString(buildTableCourseHashMap.get(key))));
                DataProcess processNew = buildTableCourseHashMap.get(key);
                if(StringUtils.isEmpty(processNew.getAreaId())){
                    OrganUser organUserNew = restTemplateHandle.getUserMesageByDubbo(userId);
                    LOGGER.info("从dubbo接口获取到数据为："+JSONObject.toJSONString(organUserNew));
                    if(organUserNew != null && organUserNew.getSuccess() && organUserNew.getData().size() >0){
                        processNew.setAreaId(organUserNew.getData().get(0).getOrgan().getSymbol12());
                        processNew.setDept(organUserNew.getData().get(0).getOrgan().getOrganName());
                        processNew.setOperator(organUserNew.getData().get(0).getUser().getName());
                        processNew.setPoliceno(organUserNew.getData().get(0).getUser().getPoliceNo());
                    }
                }
                restTemplateHandle.sendDataHistoryRecord(processNew);
                // 如果成功 则删除缓存中的数据
                buildTableCourseHashMap.remove(key);
            }
            if(!flag){
                buildTableCourseHashMap.put(uuidDate,dataProcess);
            }
        }catch (Exception e){
            LOGGER.error("将建表的信息发送到数据历程过程中出错,将该条记录写入到缓存中，下次再调用"+ ExceptionUtil.getExceptionTrace(e));
            buildTableCourseHashMap.put(uuidDate,dataProcess);
            flag = false;
        }
//        // 异步调用的方法 需要在最上层去获取数据
//        CompletableFuture future = CompletableFuture.supplyAsync(() ->{
//            return false;
//        });
        LOGGER.info("=======================发送数据历程接口结束==============================");
        return flag;
    }
}
