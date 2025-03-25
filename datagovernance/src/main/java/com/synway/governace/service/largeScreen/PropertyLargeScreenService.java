package com.synway.governace.service.largeScreen;

import com.alibaba.fastjson.JSONObject;
import com.synway.governace.pojo.largeScreen.PropertyLargeDetailed;
import com.synway.governace.pojo.largeScreen.PropertyLargeScreenData;
import com.synway.governace.pojo.largeScreen.ningbo.PropertyLargeScreenNB;
import com.synway.governace.pojo.largeScreen.ProvinceCity;

import java.util.List;

/**
 *  数据资产大屏的相关方法
 * @author wangdongwei
 */
public interface PropertyLargeScreenService {

    /**
     * 定时任务
     */
    void scheduledTask();
    /**
     * 获取省市的名称
     * @return
     */
    ProvinceCity getProvinceCityName() throws Exception;
    /**
     * 获取 原始库资产/资源库资产/主题库资产/对外共享 详细的二级分类信息
     * @param moduleName
     * @param searchName
     * @return
     */
    List<PropertyLargeDetailed>  getPropertyLargeDetailed(String moduleName, String searchName);
    /**
     * 页面上获取到的所有数据
     * @return
     */
    PropertyLargeScreenData getPropertyLargeScreenDataPage();


    /**
     * 新疆资产大屏定时任务
     */
//    void scheduledTaskXJ();

    /**
     * 新疆大屏数据（固定数据）
     * @return
     */
//    JSONObject getPropertyLargeScreenDataForXJ();

    /**
     * 获取宁波资产大屏数据
     * @return
     */
//    PropertyLargeScreenNB getPropertyLargeScreenDataForNB();
//    JSONObject getG6MockData();
}
