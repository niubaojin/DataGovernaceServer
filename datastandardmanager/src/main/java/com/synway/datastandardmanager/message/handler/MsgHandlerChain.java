package com.synway.datastandardmanager.message.handler;

import com.synway.datastandardmanager.message.RecordService;
import com.synway.datastandardmanager.pojo.DataProcess.DataProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * 消息处理类链
 * @author
 * @date
 */
@Service(value = "MsgHandlerChain")
public class MsgHandlerChain implements RecordService {

    @Autowired()@Qualifier("DataHistoryRecordServiceImpl")
    private RecordService dataHistoryRecordServiceImpl;


    @Override
    public Boolean sendMessage(DataProcess dataProcess, String userId) {
        Boolean flag = dataHistoryRecordServiceImpl.sendMessage(dataProcess,userId);
        return flag;
    }
}
