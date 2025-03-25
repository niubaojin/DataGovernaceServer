package com.synway.datastandardmanager.service;


import com.synway.datastandardmanager.pojo.sourcedata.BackDataConstruct;

import java.util.List;

/**
 * dubbo 的相关接口
 * @author wangdongwei
 */
public interface DubboMessageService {


    /**
     *  弃用
     * @return
     */
    @Deprecated
    List<BackDataConstruct> getResourceManagementUnit();
}
