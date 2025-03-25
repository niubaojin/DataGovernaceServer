package com.synway.datarelation.service.common;

import java.util.List;
import java.util.Map;

/**
 *
 * @author wangdongwei
 */
public interface CommonService {

    /**
     * 设置本地仓库类型的接口 aliyun / huaweiyun
     * @throws Exception
     */
    void getDataResourceVersion()  throws Exception;

    /**
     * 获取表所属的数据库类型 ads/odps/hive/hbase
     * @param nodeType  10 23
     * @return
     */
    String getTableType(String nodeType);

    /**
     *  获取缓存中的 dataId信息
     * @param projectName
     * @return
     */
    List<String> getDataIdList(String projectName);
}
