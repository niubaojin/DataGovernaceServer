package com.synway.datarelation.service.datablood;

import java.util.List;

/**
 *  这个是对外接口 提供给别的应用的接口方法
 * @author wangdongwei
 */
public interface ExternalInterfceService {


    /**
     * 根据 输出表的表id/表名 获取输入协议的相关信息
     * @param type  1：数据处理
     * @param searchName  查询名称
     */
    default public List<String> getStandardBloodByName(String type, String searchName) throws Exception{

        return null;
    }
}
