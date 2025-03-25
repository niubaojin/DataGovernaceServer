package com.synway.dataoperations.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.synway.common.exception.ExceptionUtil;
import com.synway.dataoperations.dao.HistoryDataMonitorDao;
import com.synway.dataoperations.pojo.AlarmMessage;
import com.synway.dataoperations.pojo.ClassifyInfoTree;
import com.synway.dataoperations.pojo.historyDataMonitor.*;
import com.synway.dataoperations.service.AlarmMessageService;
import com.synway.dataoperations.service.HistoryDataMonitorService;
import com.synway.dataoperations.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author nbj
 * @description 历史数据监测
 * @date 2022年5月30日11:02:03
 */
@Service
public class HistoryDataMonitorServiceImpl implements HistoryDataMonitorService {
    private static Logger logger = LoggerFactory.getLogger(HistoryDataMonitorServiceImpl.class);

    @Autowired
    HistoryDataMonitorDao historyDataMonitorDao;

    @Autowired
    AlarmMessageService alarmMessageService;

    @Override
    public ReturnResultHDM getHDMCommon() {
        // 返回结果
        ReturnResultHDM returnResultHDM = new ReturnResultHDM();
        // 左侧树
        List<ClassifyInfoTree> leftTree = new ArrayList<>();

        // 昨日数据量
        BigDecimal yesterdaySum = new BigDecimal(0);
        // 昨日数据种类
        int yesterdayDataCategory = 0;
        // 昨日数据环比
        String yesterdayLinkRelativeRatio = "0%";
        // 昨日抖动率
        String yesterdayFluctuateRate = "0%";

        try {
            // 日期
            String today = DateUtil.parseDateString(DateUtil.formatDate(DateUtil.addDay(new Date(), 0)));
            String daysAgo1 = DateUtil.parseDateString(DateUtil.formatDate(DateUtil.addDay(new Date(), -1)));
            String daysAgo2 = DateUtil.parseDateString(DateUtil.formatDate(DateUtil.addDay(new Date(), -2)));
            String daysAgo7 = DateUtil.parseDateString(DateUtil.formatDate(DateUtil.addDay(new Date(), -7)));
//            String today = "20220429";
//            String daysAgo1 = "20220428";
//            String daysAgo2 = "20210818";
//            String daysAgo7 = "20210818";
            // 左侧树
            ClassifyInfoTree leftTreeTemp = new ClassifyInfoTree();
            List<ClassifyInfoTree> leftTreeList = getLeftTree();
            leftTreeTemp.setLabel("全部数据");
            leftTreeTemp.setValue("全部数据");
            leftTreeTemp.setChildren(leftTreeList);
            leftTree.add(leftTreeTemp);
            // 昨日数据量
            yesterdaySum = historyDataMonitorDao.getDataNum(daysAgo1, today);
            BigDecimal dayAgo2Num = historyDataMonitorDao.getDataNum(daysAgo2, daysAgo1);     // 前天数据量
            BigDecimal dayAgo7Sum = historyDataMonitorDao.getDataNum(daysAgo7, today);        // 最近七天数据量
            // 昨日数据种类
            yesterdayDataCategory = historyDataMonitorDao.getYesterdayDataCategory(daysAgo1);
            // 昨日数据环比
            if (dayAgo2Num.compareTo(BigDecimal.ZERO) != 0){
                BigDecimal mom = yesterdaySum.subtract(dayAgo2Num).divide(dayAgo2Num,4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)).setScale(2);
                yesterdayLinkRelativeRatio = mom + "%";
            }
            // 昨日数据抖动率
            BigDecimal average7 = dayAgo7Sum.divide(BigDecimal.valueOf(7),RoundingMode.HALF_UP);
            if (average7.compareTo(BigDecimal.ZERO) != 0){
                BigDecimal fluctuateRate = yesterdaySum.subtract(average7).divide(average7,4,RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)).setScale(2);
                yesterdayFluctuateRate = fluctuateRate + "%";
            }
            returnResultHDM.setLeftTree(leftTree);
            returnResultHDM.setYesterdaySum(yesterdaySum);
            returnResultHDM.setYesterdayDataCategory(yesterdayDataCategory);
            returnResultHDM.setYesterdayLinkRelativeRatio(yesterdayLinkRelativeRatio);
            returnResultHDM.setYesterdayFluctuateRate(yesterdayFluctuateRate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnResultHDM;
    }

    // 获取页面左侧树数据
    public List<ClassifyInfoTree> getLeftTree(){
        List<ClassifyInfoTree> leftTree = new ArrayList<>();
        List<EventType> eventTypesAll = historyDataMonitorDao.getLeftTree();
        Map<String,List<EventType>> listMap = eventTypesAll.stream().collect(Collectors.groupingBy(EventType::getDataType));
        for (String key : listMap.keySet()){
            List<EventType> eventTypes1 = listMap.get(key);
            ClassifyInfoTree classifyInfoTree1 = new ClassifyInfoTree();
            classifyInfoTree1.setLabel(key);
            classifyInfoTree1.setValue(key);
            List<ClassifyInfoTree> classifyInfoTrees1 = new ArrayList<>();
            for (int i = 0; i<eventTypes1.size(); i++){
                ClassifyInfoTree classifyInfoTree2 = new ClassifyInfoTree();
                classifyInfoTree2.setValue(eventTypes1.get(i).getTableNameCh());
                classifyInfoTree2.setLabel(eventTypes1.get(i).getTableNameCh());
                classifyInfoTree2.setChildren(new LinkedList<>());
                classifyInfoTrees1.add(classifyInfoTree2);
            }
            classifyInfoTree1.setChildren(classifyInfoTrees1);
            leftTree.add(classifyInfoTree1);
        }
        return leftTree;
    }

    @Override
    public ReturnResultHDM getHDMData(RequestParameterHDM requestParameterHDM){
        // 返回结果
        ReturnResultHDM returnResultHDM = new ReturnResultHDM();
        try {
            // 查询参数
            String searchName = "";
            if (!requestParameterHDM.getSearchName().equalsIgnoreCase("全部数据")){
                searchName = requestParameterHDM.getSearchName();
            }
            String areaName = requestParameterHDM.getAreaName().equalsIgnoreCase("全部") ||
                              requestParameterHDM.getAreaName().equalsIgnoreCase("全部数据") ? "" : requestParameterHDM.getAreaName();
            String operatorName = requestParameterHDM.getOperatorName().equalsIgnoreCase("全部") ||
                                  requestParameterHDM.getOperatorName().equalsIgnoreCase("全部数据") ? "" : requestParameterHDM.getOperatorName();
            String networkTypeName = requestParameterHDM.getNetworkTypeName().equalsIgnoreCase("全部") ||
                                     requestParameterHDM.getNetworkTypeName().equalsIgnoreCase("全部数据") ? "" : requestParameterHDM.getNetworkTypeName();
            String minDt = DateUtil.parseDateString(DateUtil.formatDate(DateUtil.addDay(new Date(), -1)));
            String maxDt = DateUtil.parseDateString(DateUtil.formatDate(DateUtil.addDay(new Date(), 0)));
            if (!requestParameterHDM.getDt().isEmpty()){
                minDt = requestParameterHDM.getDt();
            }
            // 区域下拉菜单
            JSONArray areaMenu = getMenu("citycode",minDt, maxDt,searchName,areaName,operatorName,networkTypeName);
            returnResultHDM.setAreaMenu(areaMenu);
            // 运行商下拉菜单
            JSONArray operatorMenu = getMenu("operator_net",minDt, maxDt,searchName,areaName,operatorName,networkTypeName);
            returnResultHDM.setOperatorMenu(operatorMenu);
            // 网络类型下拉菜单
            JSONArray networkTypeMenu = getMenu("data_source",minDt, maxDt,searchName,areaName,operatorName,networkTypeName);
            returnResultHDM.setNetworkTypeMenu(networkTypeMenu);

            // 获取数据量趋势
            String yesterday = DateUtil.parseDateString(DateUtil.formatDate(DateUtil.addDay(new Date(), -1)));
            if (yesterday.equalsIgnoreCase(minDt)){
                String minDtTemp = DateUtil.parseDateString(DateUtil.formatDate(DateUtil.addDay(new Date(), -7)));
                List<DataCountTrend> dataCountTrends = getDataCountTrend(minDtTemp, maxDt,searchName,areaName,operatorName,networkTypeName);
                returnResultHDM.setDataCountTrends(dataCountTrends);
            }else {
                List<DataCountTrend> dataCountTrends = getDataCountTrend(minDt, maxDt,searchName,areaName,operatorName,networkTypeName);
                returnResultHDM.setDataCountTrends(dataCountTrends);
            }
            // 获取数据区域分布
            DataAreaCount cityCountTrends = getDataArea(minDt, maxDt,searchName,areaName,operatorName,networkTypeName);
            returnResultHDM.setDataAreaCount(cityCountTrends);

            // 获取数据种类、事件种类
            List<DataClass> dataClass = getDataOrEventClass("dataclass",minDt, maxDt,searchName,areaName,operatorName,networkTypeName);
            List<DataClass> eventClass = getDataOrEventClass("eventclass",minDt, maxDt,searchName,areaName,operatorName,networkTypeName);
            List<DataClass> appClass = getDataOrEventClass("appclass",minDt, maxDt,searchName,areaName,operatorName,networkTypeName);
            returnResultHDM.setDataClass(dataClass);
            returnResultHDM.setEventClass(eventClass);
            returnResultHDM.setAppClass(appClass);
        }catch (Exception e){
            logger.error("获取历史数据监测-图表数据出错：\n" + ExceptionUtil.getExceptionTrace(e));
        }
        return returnResultHDM;
    }

    // 获取数据种类、事件种类
    public List<DataClass> getDataOrEventClass(String type, String minDt, String maxDt, String searchName, String areaName,String operatorName,String networkTypeName){
        List<DataClass> dataClasses = new ArrayList<>();
        if (type.equalsIgnoreCase("dataclass")){
            dataClasses = historyDataMonitorDao.getDataClass(minDt,maxDt,searchName,areaName,operatorName,networkTypeName);
        }else if (type.equalsIgnoreCase("eventclass")){
            dataClasses = historyDataMonitorDao.getEventClass(minDt,maxDt,searchName,areaName,operatorName,networkTypeName);
        }else if (type.equalsIgnoreCase("appclass")){
            dataClasses = historyDataMonitorDao.getAppClass(minDt,maxDt,searchName,areaName,operatorName,networkTypeName);
        }
        long allCount = 0;
        for (DataClass dataClass:dataClasses){
            allCount = allCount + dataClass.getDataCount();
        }
        BigDecimal finalAllCount = new BigDecimal(allCount);
        dataClasses.parallelStream().forEach(item->{
            item.setRatio("");
            BigDecimal dataCount = new BigDecimal(item.getDataCount());
            if (finalAllCount.compareTo(BigDecimal.ZERO) != 0){
                BigDecimal mom = dataCount.divide(finalAllCount,4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)).setScale(2);
                item.setRatio(mom + "%");
            }
        });
        dataClasses.sort(Comparator.comparingLong(DataClass::getDataCount).reversed());
        return dataClasses;
    }

    // 获取城市数据分布
    public DataAreaCount getDataArea(String minDt, String maxDt, String searchName, String areaName,String operatorName,String networkTypeName){
        DataAreaCount returnResult = new DataAreaCount();

        List<DataCountTrend> dataArea = historyDataMonitorDao.getDataArea(minDt,maxDt,searchName,areaName,operatorName,networkTypeName);
        Map<String,List<DataCountTrend>> map = dataArea.stream().collect(Collectors.groupingBy(DataCountTrend::getCity));
        String[] city = new String[map.size()];
        long[] yiDongCount = new long[map.size()];
        long[] lianTongCount = new long[map.size()];
        long[] dianXinCount = new long[map.size()];
        long[] allCount = new long[map.size()];
        int i = 0;
        for (String key : map.keySet()) {
            city[i] = key;
            long allCountTemp = 0;
            List<DataCountTrend> dataCountTrends = map.get(key);
            for (DataCountTrend dataCountTrend : dataCountTrends){
                String operatorNet = dataCountTrend.getOperatorNet();
                if (operatorNet.equalsIgnoreCase("移动")){
                    allCountTemp += dataCountTrend.getAllCount();
                    yiDongCount[i] = dataCountTrend.getAllCount();
                }
                if (operatorNet.equalsIgnoreCase("联通")){
                    allCountTemp += dataCountTrend.getAllCount();
                    lianTongCount[i] = dataCountTrend.getAllCount();
                }
                if (operatorNet.equalsIgnoreCase("电信")){
                    allCountTemp += dataCountTrend.getAllCount();
                    dianXinCount[i] = dataCountTrend.getAllCount();
                }
            }
            allCount[i] = allCountTemp;
            i++;
        }
        returnResult.setCity(city);
        returnResult.setYiDongCount(yiDongCount);
        returnResult.setLianTongCount(lianTongCount);
        returnResult.setDianXinCount(dianXinCount);
        returnResult.setAllCount(allCount);
        return returnResult;
    }

    // 获取数据量趋势
    public List<DataCountTrend> getDataCountTrend(String minDt, String maxDt, String searchName, String areaName,String operatorName,String networkTypeName){
        List<DataCountTrend> allCount = historyDataMonitorDao.getDataCountTrend("",minDt,maxDt,searchName,areaName,operatorName,networkTypeName);
        List<DataCountTrend> yidongCount = historyDataMonitorDao.getDataCountTrend("移动",minDt,maxDt,searchName,areaName,operatorName,networkTypeName);
        List<DataCountTrend> liantongCount = historyDataMonitorDao.getDataCountTrend("联通",minDt,maxDt,searchName,areaName,operatorName,networkTypeName);
        List<DataCountTrend> dianxinCount = historyDataMonitorDao.getDataCountTrend("电信",minDt,maxDt,searchName,areaName,operatorName,networkTypeName);

        for (DataCountTrend dataCountTrend:allCount){
            String dt = dataCountTrend.getDt();
            for (DataCountTrend yidong : yidongCount){
                String dt1 = yidong.getDt();
                if (dt.equalsIgnoreCase(dt1)){
                    dataCountTrend.setYiDongCount(yidong.getAllCount());
                }
            }
            for (DataCountTrend liantong : liantongCount){
                String dt1 = liantong.getDt();
                if (dt.equalsIgnoreCase(dt1)){
                    dataCountTrend.setLianTongCount(liantong.getAllCount());
                }
            }
            for (DataCountTrend dianxin : dianxinCount){
                String dt1 = dianxin.getDt();
                if (dt.equalsIgnoreCase(dt1)){
                    dataCountTrend.setDianXinCount(dianxin.getAllCount());
                }
            }
        }

        return allCount;
    }

    // 获取下拉菜单
    public JSONArray getMenu(String type, String minDt, String maxD, String searchName, String areaName,String operatorName,String networkTypeName){
        JSONArray array = new JSONArray();
        List<String> strings = historyDataMonitorDao.getMenu(type,minDt,maxD,"","","","");
        strings.add(0,"全部");
        for (String string : strings){
            if (string == null || string.equalsIgnoreCase("-")){
                continue;
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("label",string);
            jsonObject.put("value",string);
            array.add(jsonObject);
        }
        return array;
    }

    @Override
    public void sendAlarmMsg() {
        try {
            String minDt = DateUtil.parseDateString(DateUtil.formatDate(DateUtil.addDay(new Date(), -1)));
            String maxDt = DateUtil.parseDateString(DateUtil.formatDate(DateUtil.addDay(new Date(), 0)));
            // 告警数据列表
            List<AlarmMessage> alarmMessages = historyDataMonitorDao.getHDMMsg(minDt, maxDt);

            // 告警信息入库
            if (alarmMessages.size() > 0){
                alarmMessages.forEach(alarmMessage -> {
                    alarmMessage.setAlarmsystem("数据工厂");
                    alarmMessage.setAlarmmodule("历史数据监测");
                    alarmMessage.setAlarmflag(0);
                    alarmMessage.setLevel(0);
                    alarmMessage.setAlarmtime(DateUtil.formatDateTime(new Date()));
                });
                String jsonString = JSONArray.toJSONString(alarmMessages);
                alarmMessageService.insertAlarmMessage(jsonString);
                logger.info("发送数据抖动告警信息至告警中心成功");
            }
        }catch (Exception e){
            logger.error(ExceptionUtil.getExceptionTrace(e));
        }
    }

//    /**
//     * 递归转换树形json数据
//     * @return
//     */
//    public List<ClassifyInfoTree> convert2Tree(List<ClassifyInfo> tables, String codeId, List<ClassifyInfoTree> array) {
//        for (ClassifyInfo table:tables) {
//            if(StringUtils.isBlank(table.getCodeIdPar())){
//                continue;
//            }
//            if (table.getCodeIdPar().equalsIgnoreCase(codeId)){
//                ClassifyInfoTree classifyInfoTree = new ClassifyInfoTree();
//                classifyInfoTree.setValue(table.getCodeId());
//                classifyInfoTree.setLabel(table.getCodeText());
//                classifyInfoTree.setChildren(convert2Tree(tables,table.getCodeId(),new LinkedList<>()));
//                array.add(classifyInfoTree);
//            }
//        }
//        return array;
//    }

}
