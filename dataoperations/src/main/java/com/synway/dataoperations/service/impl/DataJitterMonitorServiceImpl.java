package com.synway.dataoperations.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.synway.dataoperations.dao.DataJitterMonitorDao;
import com.synway.dataoperations.dao.DataSumMonitorDao;
import com.synway.dataoperations.pojo.AlarmMessage;
import com.synway.dataoperations.pojo.ClassifyInfoTree;
import com.synway.dataoperations.pojo.dataJitterMonitor.DataJitterChart;
import com.synway.dataoperations.pojo.dataJitterMonitor.RequestParameterDJM;
import com.synway.dataoperations.pojo.dataJitterMonitor.ReturnResultDJM;
import com.synway.dataoperations.pojo.dataJitterMonitor.TreeAndCardJitter;
import com.synway.dataoperations.pojo.dataSizeMonitor.DataVolumeMonitorSetting;
import com.synway.dataoperations.pojo.dataSizeMonitor.SjgcDataSummary;
import com.synway.dataoperations.pojo.dataSizeMonitor.ThresholdConfigSetting;
import com.synway.dataoperations.service.AlarmMessageService;
import com.synway.dataoperations.service.DataJitterMonitorService;
import com.synway.dataoperations.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DataJitterMonitorServiceImpl implements DataJitterMonitorService {
    private static Logger logger = LoggerFactory.getLogger(DataJitterMonitorServiceImpl.class);

    @Autowired
    DataJitterMonitorDao dataJitterMonitorDao;

    @Autowired
    DataSumMonitorDao dataSumMonitorDao;

    @Autowired
    AlarmMessageService alarmMessageService;

    @Override
    public TreeAndCardJitter getTreeAndCardData(String searchName) {
        TreeAndCardJitter treeAndCardJitter = new TreeAndCardJitter();
        // 左侧树
        List<SjgcDataSummary> leftTrees = dataSumMonitorDao.getDSMLeftTree(searchName);
        List<ClassifyInfoTree> leftTreeList = getLeftTree(leftTrees);
        if (StringUtils.isNotBlank(searchName)){
            treeAndCardJitter.setLeftTree(leftTreeList);
            return treeAndCardJitter;
        }else {
            treeAndCardJitter.setLeftTree(leftTreeList);
        }
        // 卡片数据
        List<SjgcDataSummary> dataTypes = dataSumMonitorDao.getDSMDataType();
        treeAndCardJitter.setTodayDataClassSum(dataTypes.size());
        String hourStr = DateUtil.formatDate(new Date(),"HH");
        List<SjgcDataSummary> cardDatas = dataJitterMonitorDao.getCardDataDJM(hourStr);
        Map<String,List<SjgcDataSummary>> listMap = cardDatas.stream().collect(Collectors.groupingBy(SjgcDataSummary::getDataType));
        for (String key : listMap.keySet()){
            List<SjgcDataSummary> sjgcDataSummaries = listMap.get(key);
            long todayDataSum = 0l;
            String todayFluctuateRate = "";
            TreeAndCardJitter treeAndCardJitterTemp = todayFluctuateRate(sjgcDataSummaries, todayDataSum, todayFluctuateRate);
            if (key.equals("1")){
                treeAndCardJitter.setTodayAccessSum(treeAndCardJitterTemp.getTodayAccessSum());
                treeAndCardJitter.setTodayAccessJitterRate(treeAndCardJitterTemp.getTodayAccessJitterRate());
            }
            if (key.equals("2")){
                treeAndCardJitter.setTodayHandleSum(treeAndCardJitterTemp.getTodayAccessSum());
                treeAndCardJitter.setTodayHandleJitterRate(treeAndCardJitterTemp.getTodayAccessJitterRate());
            }
            if (key.equals("3")){
                treeAndCardJitter.setTodayInsertSum(treeAndCardJitterTemp.getTodayAccessSum());
                treeAndCardJitter.setTodayInsertJitterRate(treeAndCardJitterTemp.getTodayAccessJitterRate());
            }
        }
        return treeAndCardJitter;
    }

    @Override
    public ReturnResultDJM getChartDataDJM(RequestParameterDJM requestParameter) {
        ReturnResultDJM resultDJM = new ReturnResultDJM();              // 返回结果数据
        List<DataJitterChart> dataJitterCharts = new ArrayList<>();     // 折线图数据（接入、处理环节）
        List<DataJitterChart> dataJitterCharts3 = new ArrayList<>();    // 折线图数据（入库环节）
        List<DataJitterChart> dataJitterTables = new ArrayList<>();     // 表格数据
        List<DataJitterChart> dataJitterTables3 = new ArrayList<>();    // 表格数据（入库环节）
        // 折线图数据（接入、处理环节）
        List<SjgcDataSummary> sjgcDataSummaries = dataJitterMonitorDao.getChartDataDJM(requestParameter.getTableId());
        // 折线图数据（入库环节）
        List<SjgcDataSummary> sjgcDataSummaries3 = dataJitterMonitorDao.getChartDataDJM3(requestParameter.getTableId());
        sjgcDataSummaries3.stream().forEach(item ->{item.setDataType("入库(" + item.getStorageLocation() + ")");});

        String dataJitterTime = requestParameter.getDataJitterTime();                   // 请求时间选择（今日/近24小时）
        injectDataJitterChart(dataJitterCharts, sjgcDataSummaries, dataJitterTime);     // 注入折线图数据（接入、处理环节）
        injectDataJitterChart(dataJitterCharts3, sjgcDataSummaries3, dataJitterTime);   // 注入折线图数据（入库环节）
        injectDataJitterTable(dataJitterTables, sjgcDataSummaries, dataJitterTime);     // 注入表格数据（接入、处理环节）
        injectDataJitterTable(dataJitterTables3, sjgcDataSummaries3, dataJitterTime);   // 注入表格数据（入库环节）

        dataJitterCharts.addAll(dataJitterCharts3);
        dataJitterTables.addAll(dataJitterTables3);
        resultDJM.setDataJitterCharts(dataJitterCharts);
        resultDJM.setDataJitterTables(dataJitterTables);

        return resultDJM;
    }

    /*折线图数据*/
    public void injectDataJitterChart(List<DataJitterChart> dataJitterCharts, List<SjgcDataSummary> sjgcDataSummaries, String dataJitterTime){
        // 按日期分类
        Map<String, List<SjgcDataSummary>> listMapCreateTime = sjgcDataSummaries.stream().collect(Collectors.groupingBy(SjgcDataSummary::getCreateTime));
        // 按类型分类
        Map<String, List<SjgcDataSummary>> listMapDataType = sjgcDataSummaries.stream().collect(Collectors.groupingBy(SjgcDataSummary::getDataType));

        String todayStr = DateUtil.formatDate(new Date(),"yyyyMMdd");
        String yesTodayStr = DateUtil.formatDate(DateUtil.addDay(new Date(), -1),"yyyyMMdd");
        String nowHourStr = DateUtil.formatDate(new Date(),"HH");

        // 今日数据类型列表
        Map<String, List<SjgcDataSummary>> listMapDataTypeToday = null;
        for (String key: listMapCreateTime.keySet()){
            List<SjgcDataSummary> sjgcDataSummariesToday = listMapCreateTime.get(key);
            if (key.equals(todayStr)){
                listMapDataTypeToday = sjgcDataSummariesToday.stream().collect(Collectors.groupingBy(SjgcDataSummary::getDataType));
                break;
            }
        }
        // 昨日数据类型列表
        Map<String, List<SjgcDataSummary>> listMapDataTypeYestoday = null;
        for (String key: listMapCreateTime.keySet()){
            if (key.equals(yesTodayStr)){
                List<SjgcDataSummary> sjgcDataSummariesToday = listMapCreateTime.get(key);
                listMapDataTypeYestoday = sjgcDataSummariesToday.stream().collect(Collectors.groupingBy(SjgcDataSummary::getDataType));
                break;
            }
        }

        for (String keyDataType : listMapDataType.keySet()){
            DataJitterChart dataJitterChart = new DataJitterChart();    // 折线图
            List<String> dataJitterRates = new ArrayList<>();           // 抖动率
            List<String> dataJitterHours = new ArrayList<>();           // 推送数据时点
            List<String> dataJitterSums = new ArrayList<>();            // 数据量

            initDataJitterAndHourList(dataJitterTime, nowHourStr, dataJitterHours, dataJitterRates, dataJitterSums);
            dataJitterChart.setDataType(keyDataType);
            dataJitterChart.setJitterRate(dataJitterRates);
            dataJitterChart.setPushHours(dataJitterHours);

            List<SjgcDataSummary> sjgcDataSummariesAccess = listMapDataType.get(keyDataType);
            Map<String, List<SjgcDataSummary>> listMapHours = sjgcDataSummariesAccess.stream().collect(Collectors.groupingBy(SjgcDataSummary::getPushHour));
            for (String keyHour : listMapHours.keySet()){
                List<SjgcDataSummary> sjgcDataSummariesHours = listMapHours.get(keyHour);
                long hourSuccessSum = 0;
                for (int i = 0; i < sjgcDataSummariesHours.size(); i++){
                    hourSuccessSum += Long.parseLong(sjgcDataSummariesHours.get(i).getOutSuccessSum());
                }
                BigDecimal lastDays7DataSum = new BigDecimal(hourSuccessSum);
                BigDecimal average7 = lastDays7DataSum.divide(BigDecimal.valueOf(sjgcDataSummariesHours.size()), RoundingMode.HALF_UP);

                if (listMapDataTypeToday == null || listMapDataTypeToday.get(keyDataType) == null){
                    continue;
                }
                List<SjgcDataSummary> todayDataTypes = listMapDataTypeToday.get(keyDataType);
                List<SjgcDataSummary> yestodayDataTypes = listMapDataTypeYestoday == null ? new ArrayList<>() : listMapDataTypeYestoday.get(keyDataType);
                if (!dataJitterTime.equalsIgnoreCase("today")){
                    for (SjgcDataSummary summary : yestodayDataTypes){
                        if (summary.getPushHour().equals(keyHour)){
                            // 数据抖动率
                            String todayFluctuateRate = "0";
                            if (average7.compareTo(BigDecimal.ZERO) != 0){
                                BigDecimal fluctuateRate = BigDecimal.valueOf(Long.parseLong(summary.getOutSuccessSum())).subtract(average7).divide(average7,4,RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)).setScale(2);
                                todayFluctuateRate = fluctuateRate + "";
                            }
                            if (Integer.parseInt(keyHour) > Integer.parseInt(nowHourStr)){
                                int index = Integer.parseInt(keyHour) - Integer.parseInt(nowHourStr) -1;
                                dataJitterChart.getJitterRate().set(index, todayFluctuateRate);
                            }
                        }
                    }
                }
                for (SjgcDataSummary summary : todayDataTypes){
                    if (summary.getPushHour().equals(keyHour)){
                        // 今日数据抖动率
                        String todayFluctuateRate = "0";
                        if (average7.compareTo(BigDecimal.ZERO) != 0){
                            BigDecimal fluctuateRate = BigDecimal.valueOf(Long.parseLong(summary.getOutSuccessSum())).subtract(average7).divide(average7,4,RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)).setScale(2);
                            todayFluctuateRate = fluctuateRate + "";
                        }
                        if (dataJitterTime.equalsIgnoreCase("today")){
                            if (Integer.parseInt(keyHour) <= Integer.parseInt(nowHourStr)){
                                int index = Integer.parseInt(keyHour);
                                dataJitterChart.getJitterRate().set(index, todayFluctuateRate);
                            }
                        }else {
                            if (Integer.parseInt(keyHour) <= Integer.parseInt(nowHourStr)) {
                                int index = 23 - Integer.parseInt(nowHourStr) + Integer.parseInt(keyHour);
                                dataJitterChart.getJitterRate().set(index, todayFluctuateRate);
                            }
                        }
                    }
                }
            }
            dataJitterCharts.add(dataJitterChart);
        }
    }

    /*表格数据*/
    public void injectDataJitterTable(List<DataJitterChart> dataJitterTables, List<SjgcDataSummary> sjgcDataSummaries, String dataJitterTime){
        // 按日期分类
        Map<String, List<SjgcDataSummary>> listMapCreateTime = sjgcDataSummaries.stream().collect(Collectors.groupingBy(SjgcDataSummary::getCreateTime));
        // 按类型分类
        Map<String, List<SjgcDataSummary>> listMapDataType = sjgcDataSummaries.stream().collect(Collectors.groupingBy(SjgcDataSummary::getDataType));

        String todayStr = DateUtil.formatDate(new Date(),"yyyyMMdd");
        String yesTodayStr = DateUtil.formatDate(DateUtil.addDay(new Date(), -1),"yyyyMMdd");
        String nowHourStr = DateUtil.formatDate(new Date(),"HH");

        // 今日数据类型列表
        Map<String, List<SjgcDataSummary>> listMapDataTypeToday = null;
        for (String key: listMapCreateTime.keySet()){
            if (key.equals(todayStr)){
                List<SjgcDataSummary> sjgcDataSummariesToday = listMapCreateTime.get(key);
                listMapDataTypeToday = sjgcDataSummariesToday.stream().collect(Collectors.groupingBy(SjgcDataSummary::getDataType));
                break;
            }
        }
        // 昨日数据类型列表
        Map<String, List<SjgcDataSummary>> listMapDataTypeYestoday = null;
        for (String key: listMapCreateTime.keySet()){
            if (key.equals(yesTodayStr)){
                List<SjgcDataSummary> sjgcDataSummariesToday = listMapCreateTime.get(key);
                listMapDataTypeYestoday = sjgcDataSummariesToday.stream().collect(Collectors.groupingBy(SjgcDataSummary::getDataType));
                break;
            }
        }

        for (String keyDataType : listMapDataType.keySet()){
            DataJitterChart dataJitterTableSum = new DataJitterChart();     // 表格-接入数据量
            DataJitterChart dataJitterTableAve = new DataJitterChart();     // 表格-7天平均数据量
            DataJitterChart dataJitterTableRate = new DataJitterChart();    // 表格-接入抖动率
            List<String> dataJitterRates = new ArrayList<>();               // 抖动率
            List<String> dataJitterHours = new ArrayList<>();               // 推送数据时点
            List<String> dataJitterSums = new ArrayList<>();                // 数据量
            List<String> dataJitterSumsAve = new ArrayList<>();             // 数据量

            initDataJitterAndHourList(dataJitterTime, nowHourStr, dataJitterHours, dataJitterRates, dataJitterSums);
            initDataJitterAndHourList(dataJitterTime, nowHourStr, new ArrayList<>(), new ArrayList<>(), dataJitterSumsAve);
            dataJitterTableSum.setDataType(keyDataType + "数据量(条)");
            dataJitterTableSum.setDataSums(dataJitterSums);
            dataJitterTableSum.setPushHours(dataJitterHours);

            dataJitterTableAve.setDataType(keyDataType + "7天平均数据量(条)");
            dataJitterTableAve.setDataSums(dataJitterSums);
            dataJitterTableAve.setPushHours(dataJitterHours);

            dataJitterTableRate.setDataType(keyDataType + "抖动率(%)");
            dataJitterTableRate.setDataSums(dataJitterRates);
            dataJitterTableRate.setPushHours(dataJitterHours);

            List<SjgcDataSummary> sjgcDataSummariesAccess = listMapDataType.get(keyDataType);
            Map<String, List<SjgcDataSummary>> listMapHours = sjgcDataSummariesAccess.stream().collect(Collectors.groupingBy(SjgcDataSummary::getPushHour));
            for (String keyHour : listMapHours.keySet()){
                List<SjgcDataSummary> sjgcDataSummariesHours = listMapHours.get(keyHour);
                long hourSuccessSum = 0;
                for (int i = 0; i < sjgcDataSummariesHours.size(); i++){
                    hourSuccessSum += Long.parseLong(sjgcDataSummariesHours.get(i).getOutSuccessSum());
                }
                BigDecimal lastDays7DataSum = new BigDecimal(hourSuccessSum);
                BigDecimal average7 = lastDays7DataSum.divide(BigDecimal.valueOf(sjgcDataSummariesHours.size()), RoundingMode.HALF_UP);

                if (listMapDataTypeToday == null || listMapDataTypeToday.get(keyDataType) == null){
                    continue;
                }
                List<SjgcDataSummary> todayDataTypes = listMapDataTypeToday.get(keyDataType);
                List<SjgcDataSummary> yestodayDataTypes = listMapDataTypeYestoday == null ? new ArrayList<>() : listMapDataTypeYestoday.get(keyDataType);
                if (!dataJitterTime.equalsIgnoreCase("today")){
                    for (SjgcDataSummary summary : yestodayDataTypes){
                        if (summary.getPushHour().equals(keyHour)){
                            // 数据抖动率
                            String todayFluctuateRate = "0";
                            if (average7.compareTo(BigDecimal.ZERO) != 0){
                                BigDecimal fluctuateRate = BigDecimal.valueOf(Long.parseLong(summary.getOutSuccessSum())).subtract(average7).divide(average7,4,RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)).setScale(2);
                                todayFluctuateRate = fluctuateRate + "";
                            }
                            if (Integer.parseInt(keyHour) > Integer.parseInt(nowHourStr)){
                                int index = Integer.parseInt(keyHour) - Integer.parseInt(nowHourStr) -1;
                                dataJitterTableSum.getDataSums().set(index, summary.getOutSuccessSum());
                                dataJitterTableAve.getDataSums().set(index, average7.toString());
                                dataJitterTableRate.getDataSums().set(index, todayFluctuateRate);
                            }
                        }
                    }
                }
                for (SjgcDataSummary summary : todayDataTypes){
                    if (summary.getPushHour().equals(keyHour)){
                        // 今日数据抖动率
                        String todayFluctuateRate = "0";
                        if (average7.compareTo(BigDecimal.ZERO) != 0){
                            BigDecimal fluctuateRate = BigDecimal.valueOf(Long.parseLong(summary.getOutSuccessSum())).subtract(average7).divide(average7,4,RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)).setScale(2);
                            todayFluctuateRate = fluctuateRate + "";
                        }
                        if (dataJitterTime.equalsIgnoreCase("today")){
                            if (Integer.parseInt(keyHour) <= Integer.parseInt(nowHourStr)){
                                int index = Integer.parseInt(keyHour);
                                dataJitterTableSum.getDataSums().set(index, summary.getOutSuccessSum());
                                dataJitterTableAve.getDataSums().set(index, average7.toString());
                                dataJitterTableRate.getDataSums().set(index, todayFluctuateRate);
                            }
                        }else {
                            if (Integer.parseInt(keyHour) <= Integer.parseInt(nowHourStr)){
                                int index = 23 - Integer.parseInt(nowHourStr) + Integer.parseInt(keyHour);
                                dataJitterTableSum.getDataSums().set(index, summary.getOutSuccessSum());
                                dataJitterTableAve.getDataSums().set(index, average7.toString());
                                dataJitterTableRate.getDataSums().set(index, todayFluctuateRate);
                            }
                        }
                    }
                }
            }
            dataJitterTables.add(dataJitterTableSum);
            dataJitterTables.add(dataJitterTableAve);
            dataJitterTables.add(dataJitterTableRate);
        }
    }

    private void initDataJitterAndHourList(String dataJitterTime, String nowHourStr, List<String> dataJitterHours, List<String> dataJitterRates, List<String> dataJitterSums){
        if (dataJitterTime.equals("today")){
            for (int i = 0; i<= Integer.parseInt(nowHourStr); i++){
                dataJitterHours.add(String.valueOf(i));
                dataJitterRates.add("0");
                dataJitterSums.add("0");
            }
        }else {
            for (int i = Integer.parseInt(nowHourStr) + 1; i<= 23; i++){
                dataJitterHours.add(String.valueOf(i));
                dataJitterRates.add("0");
                dataJitterSums.add("0");
            }
            for (int i = 0; i<= Integer.parseInt(nowHourStr); i++){
                dataJitterHours.add(String.valueOf(i));
                dataJitterRates.add("0");
                dataJitterSums.add("0");
            }
        }
    }

    public TreeAndCardJitter todayFluctuateRate(List<SjgcDataSummary> sjgcDataSummaries, long todayDataSum, String todayFluctuateRate){
        TreeAndCardJitter treeAndCardJitterTemp = new TreeAndCardJitter();
        treeAndCardJitterTemp.setTodayAccessSum("0");
        String todayStr = DateUtil.formatDate(new Date(),"yyyyMMdd");
        long lastDays7DataSum = 0l;
        for (SjgcDataSummary sjgcDataSummary : sjgcDataSummaries){
            long outSuccessSum = Long.parseLong(sjgcDataSummary.getOutSuccessSum());
            lastDays7DataSum += outSuccessSum;
            if (sjgcDataSummary.getCreateTime().equals(todayStr)){
                todayDataSum = outSuccessSum;
                treeAndCardJitterTemp.setTodayAccessSum(String.valueOf(todayDataSum));
            }
        }
        // 今日数据抖动率
        BigDecimal average7 = BigDecimal.valueOf(lastDays7DataSum).divide(BigDecimal.valueOf(sjgcDataSummaries.size()), RoundingMode.HALF_UP);
        if (average7.compareTo(BigDecimal.ZERO) != 0){
            BigDecimal fluctuateRate = BigDecimal.valueOf(todayDataSum).subtract(average7).divide(average7,4,RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)).setScale(2);
            todayFluctuateRate = fluctuateRate + "";
        }
        treeAndCardJitterTemp.setTodayAccessJitterRate(todayFluctuateRate);
        return treeAndCardJitterTemp;
    }

    // 页面左侧树数据
    public List<ClassifyInfoTree> getLeftTree(List<SjgcDataSummary> sjgcDataSummaries){
        List<ClassifyInfoTree> leftTree = new ArrayList<>();
        Map<String,List<SjgcDataSummary>> listMap = sjgcDataSummaries.stream().collect(Collectors.groupingBy(SjgcDataSummary::getSjzzsjfl));
        for (String key : listMap.keySet()){
            List<SjgcDataSummary> sjgcDataSummaries1 = listMap.get(key);
            ClassifyInfoTree classifyInfoTree1 = new ClassifyInfoTree();
            classifyInfoTree1.setLabel(key);
            classifyInfoTree1.setValue(key);
            List<ClassifyInfoTree> classifyInfoTrees1 = new ArrayList<>();
            for (int i = 0; i<sjgcDataSummaries1.size(); i++){
                if (!(sjgcDataSummaries1.get(i).getOutTableNameCh() == null && sjgcDataSummaries1.get(i).getOutTableNameEn() == null && sjgcDataSummaries1.get(i).getOutProtocol() == null)){
                    ClassifyInfoTree classifyInfoTree2 = new ClassifyInfoTree();
                    classifyInfoTree2.setValue(sjgcDataSummaries1.get(i).getOutTableNameEn());
                    classifyInfoTree2.setLabel(sjgcDataSummaries1.get(i).getOutTableNameCh());
                    classifyInfoTree2.setTableId(sjgcDataSummaries1.get(i).getOutProtocol());
                    classifyInfoTree2.setChildren(new LinkedList<>());
                    classifyInfoTrees1.add(classifyInfoTree2);
                }
            }
            classifyInfoTree1.setChildren(classifyInfoTrees1);
            leftTree.add(classifyInfoTree1);
        }
        return leftTree;
    }

    @Override
    public void sendAlarmMsg() {
        // 告警数据列表
        List<AlarmMessage> alarmMessages = new ArrayList<>();
        // 获取告警中心配置
        List<ThresholdConfigSetting> thresholdConfigSettings = dataSumMonitorDao.getDataVolumeAlarmSetting();
        // 获取告警数据
        String hourStr = DateUtil.formatDate(new Date(),"HH");
        List<SjgcDataSummary> sjgcDataSummaries = dataJitterMonitorDao.getSjgcDataSummarysByHour(hourStr);
        if (thresholdConfigSettings.size() == 0 || sjgcDataSummaries.size() == 0){
            logger.info("数据抖动监控配置信息或数据抖动监控数据量为空...");
            return;
        }
        Map<String, List<SjgcDataSummary>> mapTableName = sjgcDataSummaries.stream().collect(Collectors.groupingBy(SjgcDataSummary::getOutTableNameEn));
        // 生成告警信息
        thresholdConfigSettings.stream().forEach(d ->{
            DataVolumeMonitorSetting setting = JSON.parseObject(d.getThresholdValue(), DataVolumeMonitorSetting.class);
            if (setting.getSettingTache().size()>0){
                List<SjgcDataSummary> sjgcDataSummariesTableName = mapTableName.get(d.getName());
                injectAlarmInfo(setting, sjgcDataSummariesTableName, alarmMessages);
            }
        });
        // 告警信息入库
        if (alarmMessages.size() > 0){
            String jsonString = JSONArray.toJSONString(alarmMessages);
            alarmMessageService.insertAlarmMessage(jsonString);
            logger.info("发送数据抖动告警信息至告警中心成功");
        }
    }
    public void injectAlarmInfo(DataVolumeMonitorSetting setting, List<SjgcDataSummary> summaryList, List<AlarmMessage> alarmMessages){
        Map<String,List<SjgcDataSummary>> listMap = summaryList.stream().collect(Collectors.groupingBy(SjgcDataSummary::getDataType));
        for (String key : listMap.keySet()){
            List<SjgcDataSummary> sjgcDataSummariesDataType = listMap.get(key);
            long todayDataSum = 0l;
            String fluctuateRate = "";
            TreeAndCardJitter treeAndCardJitter = todayFluctuateRate(sjgcDataSummariesDataType, todayDataSum, fluctuateRate);
            Float fluateRateNow = Float.parseFloat(treeAndCardJitter.getTodayAccessJitterRate());
            SjgcDataSummary sjgcDataSummary = sjgcDataSummariesDataType.get(0);
            if (key.equals("1") && setting.isAcceptCheck()){
                injectAlarmLevelInfo(alarmMessages, setting, sjgcDataSummary, fluateRateNow, "接入");
            }
            if (key.equals("2")){
                injectAlarmLevelInfo(alarmMessages, setting, sjgcDataSummary, fluateRateNow, "处理");
            }
            if (key.equals("3")){
                injectAlarmLevelInfo(alarmMessages, setting, sjgcDataSummary, fluateRateNow, "入库");
            }
        }
    }
    public void injectAlarmLevelInfo(List<AlarmMessage> alarmMessages,
                                     DataVolumeMonitorSetting setting,
                                     SjgcDataSummary sjgcDataSummary,
                                     Float fluateRateNow,
                                     String datatype){
        String yiban = setting.getDataJitterAlarmYiban();
        String jinggao = setting.getDataJitterAlarmJinggao();
        String yanzhong = setting.getDataJitterAlarmYanzhong();
        AlarmMessage alarmMessage = getPublicAlarmMsg(sjgcDataSummary);
        if (yanzhong != null && Math.abs(fluateRateNow) >= Integer.parseInt(yanzhong)){
            String alarmcontent = String.format("消息：%s(%s)，数据抖动率为%s%s；告警原因：抖动阈值上下%s%s，数据异常",alarmMessage.getTableNameCh(),datatype,fluateRateNow,"%",yanzhong,"%");
            alarmMessage.setLevel(2);
            alarmMessage.setAlarmcontent(alarmcontent);
            alarmMessages.add(alarmMessage);
            return;
        }
        if (jinggao != null && Math.abs(fluateRateNow) >= Integer.parseInt(jinggao)){
            alarmMessage.setLevel(1);
            String alarmcontent = String.format("消息：%s(%s)，数据抖动率为%s%s；告警原因：抖动阈值上下%s%s，数据异常",alarmMessage.getTableNameCh(),datatype,fluateRateNow,"%",jinggao,"%");
            alarmMessage.setAlarmcontent(alarmcontent);
            alarmMessages.add(alarmMessage);
            return;
        }
        if (yiban != null && Math.abs(fluateRateNow) >= Integer.parseInt(yiban)){
            alarmMessage.setLevel(0);
            String alarmcontent = String.format("消息：%s(%s)，数据抖动率为%s%s；告警原因：抖动阈值上下%s%s，数据异常",alarmMessage.getTableNameCh(),datatype,fluateRateNow,"%",yiban,"%");
            alarmMessage.setAlarmcontent(alarmcontent);
            alarmMessages.add(alarmMessage);
        }
    }

    // 获取告警信息公共数据
    public AlarmMessage getPublicAlarmMsg(SjgcDataSummary sjgcDataSummary){
        AlarmMessage alarmMessage = new AlarmMessage();
        alarmMessage.setAlarmsystem("数据工厂");
        alarmMessage.setAlarmmodule("数据抖动监测");
        alarmMessage.setAlarmflag(0);
        alarmMessage.setTableNameCh(sjgcDataSummary.getOutTableNameCh());
        alarmMessage.setTableNameEn(sjgcDataSummary.getOutTableNameEn());
        alarmMessage.setAlarmtime(DateUtil.formatDateTime(new Date()));
        return alarmMessage;
    }

}
