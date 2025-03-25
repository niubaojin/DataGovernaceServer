package com.synway.datastandardmanager.message;


import com.synway.datastandardmanager.pojo.DataProcess.DataProcess;

/**
 *  数据发送 保存的接口
 */
public interface RecordService {

    public Boolean sendMessage(DataProcess dataProcess, String userId);
}
