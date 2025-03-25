package com.synway.governace.service.largeScreen;

import com.alibaba.fastjson.JSONObject;
import com.synway.governace.pojo.largeScreen.PropertyLargeDetailed;
import com.synway.governace.pojo.largeScreen.PropertyLargeScreenData;
import com.synway.governace.pojo.largeScreen.ProvinceCity;
import com.synway.governace.pojo.largeScreen.ningbo.PropertyLargeScreenNB;

import java.util.List;

/**
 *  数据资产大屏的相关方法
 * @author wangdongwei
 */
public interface XJPropertyLargeScreenService {

    /**
     * 新疆资产大屏定时任务
     */
    void scheduledTaskXJ();

    /**
     * 新疆大屏数据（固定数据）
     * @return
     */
    JSONObject getPropertyLargeScreenDataForXJ();

}
