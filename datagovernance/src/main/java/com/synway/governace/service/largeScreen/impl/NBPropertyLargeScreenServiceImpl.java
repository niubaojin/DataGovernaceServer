package com.synway.governace.service.largeScreen.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.synway.governace.dao.PropertyLargeScreenDao;
import com.synway.governace.pojo.ReconInfo;
import com.synway.governace.pojo.largeScreen.PropertyLargeDbData;
import com.synway.governace.pojo.largeScreen.TotalDataProperty;
import com.synway.governace.pojo.largeScreen.UseHeatProperty;
import com.synway.governace.pojo.largeScreen.ningbo.*;
import com.synway.governace.service.largeScreen.NBPropertyLargeScreenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author chenfei
 * @Data 2024/7/1 14:24
 * @Description 新疆大屏
 */
@Service
public class NBPropertyLargeScreenServiceImpl implements NBPropertyLargeScreenService {
    private Logger logger = LoggerFactory.getLogger(NBPropertyLargeScreenServiceImpl.class);
    @Autowired
    private PropertyLargeScreenDao propertyLargeScreenDao;


    @Override
    public PropertyLargeScreenNB getPropertyLargeScreenDataForNB(){
        /**
         * 获取所有的定时任务统计的数据
         * 0：平台资产（沿用之前统计的数据）
         * 101:数据对账
         * 102:数据血缘
         * 103:数据热度
         * 104:数据接入量
         * 105:数据告警
         * 106:数据资源
         * 107:对外服务
         */
        List<PropertyLargeDbData> list = propertyLargeScreenDao.getPropertyLargeScreenDataPage();
        PropertyLargeScreenNB propertyLargeScreenNB = new PropertyLargeScreenNB();
        for(PropertyLargeDbData data:list){
            switch (data.getType()){
                case 0:
                    // 平台资产
                    TotalDataProperty totalDataProperty = JSONObject.parseObject(data.getData(),TotalDataProperty.class);
                    propertyLargeScreenNB.setPlatformAssets(totalDataProperty);
                    break;
                case 101:
                    // 数据对账
                    ReconInfo reconInfo = JSONObject.parseObject(data.getData(),ReconInfo.class);
                    propertyLargeScreenNB.setDataRecon(reconInfo);
                    break;
                case 102:
                    // 数据血缘
                    AllBloodCount allBloodCount = JSONObject.parseObject(data.getData(), AllBloodCount.class);
                    propertyLargeScreenNB.setDataBlood(allBloodCount);
                    break;
                case 103:
                    // 数据热度
                    List<UseHeatProperty> dataUseHot = JSONObject.parseArray(data.getData(),UseHeatProperty.class);
                    propertyLargeScreenNB.setDataUseHots(dataUseHot);
                    break;
                case 104:
                    // 数据接入量
                    List<PropertyStatisticsChart> dataAccessSums = JSONObject.parseArray(data.getData(),PropertyStatisticsChart.class);
                    propertyLargeScreenNB.setDataAccessSums(dataAccessSums);
                    break;
                case 105:
                    // 数据接入量
                    List<PropertyStatistics> dataAlarms = JSONObject.parseArray(data.getData(), PropertyStatistics.class);
                    propertyLargeScreenNB.setDataAlarms(dataAlarms);
                    break;
                case 106:
                    // 数据资源
                    List<PropertyStatistics> dataResources = JSONObject.parseArray(data.getData(),PropertyStatistics.class);
                    propertyLargeScreenNB.setDataResources(dataResources);
                    break;
                case 107:
                    // 对外服务
                    ExternalServerData externalServerData = JSONObject.parseObject(data.getData(), ExternalServerData.class);
                    propertyLargeScreenNB.setExternalServer(externalServerData);
                    break;
                default:
                    break;
            }
        }
//        // 告警数据实时获取（先用固定数据：from毛加楠）
//        List<PropertyStatistics> dataAlarms = propertyLargeScreenDao.getAlarmDataSum(0);
//        if (dataAlarms == null || dataAlarms.size() == 0){
//            dataAlarms = propertyLargeScreenDao.getAlarmDataSum(1);
//        }
//        propertyLargeScreenNB.setDataAlarms(dataAlarms);
        return propertyLargeScreenNB;
    }

    @Override
    public JSONObject getG6MockData(){
        JSONObject jsonObject = new JSONObject();
        /**
         * 获取所有的定时任务统计的数据
         * 200:数据全流程图：headData
         * 201:数据全流程图：mockData
         */
        List<PropertyLargeDbData> list = propertyLargeScreenDao.getPropertyLargeScreenDataPage();
        for(PropertyLargeDbData data:list){
            switch (data.getType()){
                case 200:
                    // headData
                    JSONArray headData = JSONObject.parseArray(data.getData());
                    jsonObject.put("headData", headData);
                    break;
                case 201:
                    // mockData
                    JSONObject mockData = JSONObject.parseObject(data.getData());
                    jsonObject.put("mockData", mockData);
                    break;
                default:
                    break;
            }
        }
        return jsonObject;
    }

}
