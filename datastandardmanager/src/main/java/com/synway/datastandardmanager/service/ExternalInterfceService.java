package com.synway.datastandardmanager.service;


import com.alibaba.fastjson.JSONObject;
import com.synway.datastandardmanager.pojo.ExternalInterfce.TreeNodeVue;
import com.synway.datastandardmanager.pojo.ObjectPojo;
import com.synway.datastandardmanager.pojo.ObjectPojoTable;
import com.synway.datastandardmanager.pojo.PageSelectOneValue;

import java.util.List;
import java.util.Map;

/**
 * 对外的接口统一的service层
 * @author wangdongwei
 */
public interface ExternalInterfceService {

    /**
     *  数据仓库那边调用的接口
     * @param sourceId
     * @param sourceCode
     * @param sourceFirmCode
     * @return
     */
    List<PageSelectOneValue>  getStandardOutTableIdBySourceIdService(String sourceId,
                                                                     String sourceCode,
                                                                     String sourceFirmCode);

    /**
     *
     * @param dataId
     * @return
     */
    Integer findStandByDataId(String dataId);


    /**
     *  质量检测功能需要根据 sourceId 查询对应的 标准表信息
     * @param sourceId
     * @return
     */
    List<Map<String,String>> getStandardTableBySourceId(String sourceId);


    /**
     * 根据userID查询这个id下的标准信息，如果id为 null 说明是管理员权限，获取所有的标准表信息
     * @param userId
     * @return
     * @throws Exception
     */
    List<ObjectPojo> getObjectByUserId(Integer userId);
}
