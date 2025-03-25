package com.synway.governace.service.largeScreen;

import com.alibaba.fastjson.JSONObject;
import com.synway.governace.pojo.largeScreen.ningbo.PropertyLargeScreenNB;

/**
 *  数据资产大屏的相关方法
 * @author wangdongwei
 */
public interface NBPropertyLargeScreenService {


    PropertyLargeScreenNB getPropertyLargeScreenDataForNB();

    JSONObject getG6MockData();
}
