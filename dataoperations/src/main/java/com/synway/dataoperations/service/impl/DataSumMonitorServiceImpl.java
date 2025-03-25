package com.synway.dataoperations.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.synway.dataoperations.dao.DataSumMonitorDao;
import com.synway.dataoperations.pojo.AlarmMessage;
import com.synway.dataoperations.pojo.ClassifyInfoTree;
import com.synway.dataoperations.pojo.dataSizeMonitor.*;
import com.synway.dataoperations.service.AlarmMessageService;
import com.synway.dataoperations.service.DataSumMonitorService;
import com.synway.dataoperations.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class DataSumMonitorServiceImpl implements DataSumMonitorService {
    private static Logger logger = LoggerFactory.getLogger(DataSumMonitorServiceImpl.class);

    @Autowired
    DataSumMonitorDao dataSumMonitorDao;

    @Autowired
    AlarmMessageService alarmMessageService;

    @Override
    public TreeAndCard getTreeAndCardData(String searchName) {
        TreeAndCard treeAndCard = new TreeAndCard();
        // 左侧树
        List<SjgcDataSummary> leftTrees = dataSumMonitorDao.getDSMLeftTree(searchName);
        List<ClassifyInfoTree> leftTreeList = getLeftTree(leftTrees);
        if (StringUtils.isNotBlank(searchName)){
            treeAndCard.setLeftTree(leftTreeList);
            return treeAndCard;
        }else {
            treeAndCard.setLeftTree(leftTreeList);
        }
        // 卡片数据
        List<SjgcDataSummary> dataTypes = dataSumMonitorDao.getDSMDataType();
        treeAndCard.setTodayDataClassSum(dataTypes.size());
        List<SjgcDataSummary> getCardDatas = dataSumMonitorDao.getCardData();
        if (getCardDatas.size()>0){
            getCardDatas.stream().forEach(item ->{
                if (item.getDataType() != null){
                    if (item.getDataType().equals("1")){
                        treeAndCard.setTodayAccessSum(item.getOutSuccessSum());
                        treeAndCard.setTodayAccessAbnormalSum(item.getOutErrorSum());
                    }else if (item.getDataType().equals("2")){
                        treeAndCard.setTodayHandleSum(item.getOutSuccessSum());
                        treeAndCard.setTodayHandleAbnormalSum(item.getOutErrorSum());
                    }else if (item.getDataType().equals("3")){
                        treeAndCard.setTodayInsertSum(item.getOutSuccessSum());
                        treeAndCard.setTodayInsertAbnormalSum(item.getOutErrorSum());
                    }
                }
            });
        }
        return treeAndCard;
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
    public ReturnResultDSM getChartData(RequestParameterDSM requestParameter) {
        ReturnResultDSM resultDSM = new ReturnResultDSM();
        List<DataSumChart> dataSumCharts = new ArrayList<>();
        List<DataSumChart> dataSumChartsAbnormal = new ArrayList<>();
        DataSumTable dataSumTable = new DataSumTable();
        DataSumTable dataSumTableAbnormal = new DataSumTable();

        injectParm(requestParameter, dataSumCharts,dataSumChartsAbnormal, dataSumTable, dataSumTableAbnormal);

        resultDSM.setDataSumCharts(dataSumCharts);
        resultDSM.setDataSumChartsAbnormal(dataSumChartsAbnormal);
        resultDSM.setDataSumTable(dataSumTable);
        resultDSM.setDataSumTableAbnormal(dataSumTableAbnormal);

        return resultDSM;
    }
    public void injectParm(RequestParameterDSM requestParameter, List<DataSumChart> dataSumCharts, List<DataSumChart> dataSumChartsAbnormal, DataSumTable dataSumTable, DataSumTable dataSumTableAbnormal){
        AtomicLong accessSum = new AtomicLong();
        AtomicLong handleSum = new AtomicLong();
        AtomicLong accessSumAbnormal = new AtomicLong();
        AtomicLong handleSumAbnormal = new AtomicLong();
        List<StorageLocationDataSize> storageLocationDataSizes = new ArrayList<>();
        List<StorageLocationDataSize> storageLocationDataSizesAbnormal = new ArrayList<>();
        // 折线图数据（接入、处理环节）
        List<SjgcDataSummary> chartDatas = dataSumMonitorDao.getChartData(requestParameter.getTableId());
        // 折线图数据（入库环节）
        List<SjgcDataSummary> chartDatas3 = dataSumMonitorDao.getChartData3(requestParameter.getTableId());

        String todayStr = DateUtil.formatDate(new Date(),"yyyyMMdd");
        String nowHourStr = DateUtil.formatDate(new Date(),"HH");
        String yestodayStr = DateUtil.formatDate(DateUtil.addDay(new Date(), -1),"yyyyMMdd");
        String dataSumTime = requestParameter.getDataSumTime();
        String dataSumTimeAbn = requestParameter.getDataSumTimeAbnormal();

        Map<String,List<SjgcDataSummary>> listDataSummary = chartDatas.stream().collect(Collectors.groupingBy(SjgcDataSummary::getDataType));
        for (String key : listDataSummary.keySet()){
            List<SjgcDataSummary> sjgcDataSummaries = listDataSummary.get(key);
            if (key.equals("入库") || key.equals("集成")){
                continue;
            }
            DataSumChart dataSumChart = new DataSumChart();
            DataSumChart dataSumChartAbnormal = new DataSumChart();
            List<String> dataSums = new ArrayList<>();
            List<String> dataSumHours = new ArrayList<>();
            List<String> dataSumsAbn = new ArrayList<>();
            List<String> dataSumAbnHours = new ArrayList<>();
            initDataSumAndHourList(dataSumTime,nowHourStr,dataSumHours,dataSums);
            initDataSumAndHourList(dataSumTimeAbn,nowHourStr,dataSumAbnHours,dataSumsAbn);
            dataSumChart.setDataType(key);
            dataSumChart.setDataSums(dataSums);
            dataSumChart.setPushHours(dataSumHours);
            dataSumChartAbnormal.setDataType(key);
            dataSumChartAbnormal.setDataSums(dataSumsAbn);
            dataSumChartAbnormal.setPushHours(dataSumAbnHours);
            if (key.equalsIgnoreCase("接入")){
                setDataSumChart(sjgcDataSummaries,dataSumChart,dataSumChartAbnormal,accessSum,accessSumAbnormal,requestParameter);
            }else {
                setDataSumChart(sjgcDataSummaries,dataSumChart,dataSumChartAbnormal,handleSum,handleSumAbnormal,requestParameter);
            }
            dataSumCharts.add(dataSumChart);
            dataSumChartsAbnormal.add(dataSumChartAbnormal);
        }
        Map<String,List<SjgcDataSummary>> listDataSummary3 = chartDatas3.stream().collect(Collectors.groupingBy(SjgcDataSummary::getStorageLocation));
        for (String key : listDataSummary3.keySet()){
            List<SjgcDataSummary> sjgcDataSummaries = listDataSummary3.get(key);
            String storageLocation = "入库(" + key + ")";
            DataSumChart dataSumChart = new DataSumChart();
            DataSumChart dataSumChartAbnormal = new DataSumChart();
            List<String> dataSums = new ArrayList<>();
            List<String> dataSumHours = new ArrayList<>();
            List<String> dataSumsAbn = new ArrayList<>();
            List<String> dataSumAbnHours = new ArrayList<>();
            initDataSumAndHourList(dataSumTime,nowHourStr,dataSumHours,dataSums);
            initDataSumAndHourList(dataSumTimeAbn,nowHourStr,dataSumAbnHours,dataSumsAbn);
            dataSumChart.setDataType(storageLocation);
            dataSumChart.setDataSums(dataSums);
            dataSumChart.setPushHours(dataSumHours);
            dataSumChartAbnormal.setDataType(storageLocation);
            dataSumChartAbnormal.setDataSums(dataSumsAbn);
            dataSumChartAbnormal.setPushHours(dataSumAbnHours);

            setDataSumChart(sjgcDataSummaries,dataSumChart,dataSumChartAbnormal,new AtomicLong(),new AtomicLong(),requestParameter);
            dataSumCharts.add(dataSumChart);
            dataSumChartsAbnormal.add(dataSumChartAbnormal);

            /*表格数据*/
            long insertSum = 0l;
            long insertSumAbnormal = 0l;
            StorageLocationDataSize storageLocationDataSize = new StorageLocationDataSize();
            StorageLocationDataSize storageLocationDataSizeAbnormal = new StorageLocationDataSize();
            List<SjgcDataSummary> sjgcDataSummarys = listDataSummary3.get(key);
            for (SjgcDataSummary sjgcDataSummary : sjgcDataSummarys){
                boolean createTimeIsToday = sjgcDataSummary.getCreateTime().equalsIgnoreCase(todayStr);
                if (dataSumTime.equalsIgnoreCase("today")){
                    if (createTimeIsToday){
                        insertSum += Long.parseLong(sjgcDataSummary.getOutSuccessSum());
                    }
                }else {
                    if ( !(sjgcDataSummary.getCreateTime().equals(yestodayStr) && Integer.parseInt(sjgcDataSummary.getPushHour()) < Integer.parseInt(nowHourStr)) ){
                        insertSum += Long.parseLong(sjgcDataSummary.getOutSuccessSum());
                    }
                }
                if (dataSumTimeAbn.equalsIgnoreCase("today")){
                    if (createTimeIsToday){
                        insertSumAbnormal += Long.parseLong(sjgcDataSummary.getOutErrorSum());
                    }
                }else {
                    if ( !(sjgcDataSummary.getCreateTime().equals(yestodayStr) && Integer.parseInt(sjgcDataSummary.getPushHour()) < Integer.parseInt(nowHourStr)) ){
                        insertSumAbnormal += Long.parseLong(sjgcDataSummary.getOutErrorSum());
                    }
                }
            }
            storageLocationDataSize.setStorageLocation(key);
            storageLocationDataSize.setInsertSum(String.valueOf(insertSum));
            storageLocationDataSizeAbnormal.setStorageLocation(key);
            storageLocationDataSizeAbnormal.setInsertSum(String.valueOf(insertSumAbnormal));
            storageLocationDataSizes.add(storageLocationDataSize);
            storageLocationDataSizesAbnormal.add(storageLocationDataSizeAbnormal);
        }

        // 表格数据合并
        List<StorageLocationDataSize> storageLocationDataSizesRel = new ArrayList<>();
        List<StorageLocationDataSize> storageLocationDataSizesAbnormalRel = new ArrayList<>();
        Map<String,List<StorageLocationDataSize>> storageLocations = storageLocationDataSizes.stream().collect(Collectors.groupingBy(StorageLocationDataSize::getStorageLocation));
        for (String key : storageLocations.keySet()){
            long insertSumTemp = 0l;
            StorageLocationDataSize storageLocation = new StorageLocationDataSize();
            List<StorageLocationDataSize> storages = storageLocations.get(key);
            for (StorageLocationDataSize storage : storages){
                insertSumTemp += Long.parseLong(storage.getInsertSum());
            }
            storageLocation.setStorageLocation(key);
            storageLocation.setInsertSum(String.valueOf(insertSumTemp));
            storageLocationDataSizesRel.add(storageLocation);
        }
        Map<String,List<StorageLocationDataSize>> storageLocationsAbn = storageLocationDataSizesAbnormal.stream().collect(Collectors.groupingBy(StorageLocationDataSize::getStorageLocation));
        for (String key : storageLocationsAbn.keySet()){
            long insertSumTemp = 0l;
            StorageLocationDataSize storageLocation = new StorageLocationDataSize();
            List<StorageLocationDataSize> storages = storageLocationsAbn.get(key);
            for (StorageLocationDataSize storage : storages){
                insertSumTemp += Long.parseLong(storage.getInsertSum());
            }
            storageLocation.setStorageLocation(key);
            storageLocation.setInsertSum(String.valueOf(insertSumTemp));
            storageLocationDataSizesAbnormalRel.add(storageLocation);
        }

        if (chartDatas.size() > 0){
            dataSumTable.setTableId(chartDatas.get(0).getOutProtocol() != null ? chartDatas.get(0).getOutProtocol() : null);
            dataSumTable.setTableNameEn(chartDatas.get(0).getOutTableNameEn() !=null ? chartDatas.get(0).getOutTableNameEn() : null);
            dataSumTable.setTableNameCh(chartDatas.get(0).getOutTableNameCh() !=null ? chartDatas.get(0).getOutTableNameCh() : null);
            dataSumTable.setAccessSum(accessSum.toString());
            dataSumTable.setHandlerSum(handleSum.toString());
            dataSumTable.setInsertSumList(storageLocationDataSizesRel);
            dataSumTableAbnormal.setTableId(chartDatas.get(0).getOutProtocol() !=null ? chartDatas.get(0).getOutProtocol() : null);
            dataSumTableAbnormal.setTableNameEn(chartDatas.get(0).getOutTableNameEn() !=null ? chartDatas.get(0).getOutTableNameEn() : null);
            dataSumTableAbnormal.setTableNameCh(chartDatas.get(0).getOutTableNameCh() !=null ? chartDatas.get(0).getOutTableNameCh() : null);
            dataSumTableAbnormal.setAccessSum(accessSumAbnormal.toString());
            dataSumTableAbnormal.setHandlerSum(handleSumAbnormal.toString());
            dataSumTableAbnormal.setInsertSumList(storageLocationDataSizesAbnormalRel);
        }
    }

    private void initDataSumAndHourList(String dataSumTime, String nowHourStr, List<String> dataSumHours, List<String> dataSums){
        if (dataSumTime.equals("today")){
            for (int i = 0; i<= Integer.parseInt(nowHourStr); i++){
                dataSumHours.add(String.valueOf(i));
                dataSums.add("0");
            }
        }else {
            for (int i = Integer.parseInt(nowHourStr) + 1; i<= 23; i++){
                dataSumHours.add(String.valueOf(i));
                dataSums.add("0");
            }
            for (int i = 0; i<= Integer.parseInt(nowHourStr); i++){
                dataSumHours.add(String.valueOf(i));
                dataSums.add("0");
            }
        }
    }

    public void setDataSumChart(List<SjgcDataSummary> sjgcDataSummaries,
                                DataSumChart dataSumChart,
                                DataSumChart dataSumChartAbnormal,
                                AtomicLong accessSum,
                                AtomicLong accessSumAbnormal,
                                RequestParameterDSM requestParameter){
        String dataSumTime = requestParameter.getDataSumTime();
        String dataSumTimeAbn = requestParameter.getDataSumTimeAbnormal();
        String todayStr = DateUtil.formatDate(new Date(),"yyyyMMdd");
        String hourStr = DateUtil.formatDate(new Date(),"HH");
        String yestodayStr = DateUtil.formatDate(DateUtil.addDay(new Date(), -1),"yyyyMMdd");

        sjgcDataSummaries.stream().forEach(chartData ->{
            boolean createTimeIsToday = chartData.getCreateTime().equalsIgnoreCase(todayStr);
            if (dataSumTime.equalsIgnoreCase("today")){
                if (createTimeIsToday){
                    if (!(Integer.parseInt(chartData.getPushHour()) > Integer.parseInt(hourStr))){
                        int index = Integer.parseInt(chartData.getPushHour());
                        dataSumChart.getDataSums().set(index,StringUtils.isNotBlank(chartData.getOutSuccessSum()) ? chartData.getOutSuccessSum() : "0");
                        accessSum.addAndGet(Long.parseLong(chartData.getOutSuccessSum()));
                    }
                }
            }else {
                if ( !(chartData.getCreateTime().equals(yestodayStr) && Integer.parseInt(chartData.getPushHour()) < Integer.parseInt(hourStr)) ) {
                    if (!(Integer.parseInt(chartData.getPushHour()) > Integer.parseInt(hourStr))){
                        int index = 24 - Integer.parseInt(hourStr) -1 + Integer.parseInt(chartData.getPushHour());
                        dataSumChart.getDataSums().set(index, StringUtils.isNotBlank(chartData.getOutSuccessSum()) ? chartData.getOutSuccessSum() : "0");
                        accessSum.addAndGet(Long.parseLong(chartData.getOutSuccessSum()));
                    }
                }
            }
            if (dataSumTimeAbn.equalsIgnoreCase("today")){
                if (createTimeIsToday){
                    if (!(Integer.parseInt(chartData.getPushHour()) > Integer.parseInt(hourStr))){
                        int index = Integer.parseInt(chartData.getPushHour());
                        dataSumChartAbnormal.getDataSums().set(index,StringUtils.isNotBlank(chartData.getOutErrorSum()) ? chartData.getOutErrorSum() : "0");
                        accessSumAbnormal.addAndGet(Long.parseLong(chartData.getOutErrorSum()));
                    }
                }
            }else {
                if ( !(chartData.getCreateTime().equals(yestodayStr) && Integer.parseInt(chartData.getPushHour()) < Integer.parseInt(hourStr)) ){
                    if (!(Integer.parseInt(chartData.getPushHour()) > Integer.parseInt(hourStr))){
                        int index = 24 - Integer.parseInt(hourStr) -1 + Integer.parseInt(chartData.getPushHour());
                        dataSumChartAbnormal.getDataSums().set(index,StringUtils.isNotBlank(chartData.getOutErrorSum()) ? chartData.getOutErrorSum() : "0");
                        accessSumAbnormal.addAndGet(Long.parseLong(chartData.getOutErrorSum()));
                    }
                }
            }
        });
    }

    @Override
    public void sendAlarmMsg() {
        // 告警数据列表
        List<AlarmMessage> alarmMessages = new ArrayList<>();
        // 获取告警中心配置
        List<ThresholdConfigSetting> thresholdConfigSettings = dataSumMonitorDao.getDataVolumeAlarmSetting();
        List<SjgcDataSummary> sjgcDataSummaries = dataSumMonitorDao.getSjgcDataSummarys(0);
        if (thresholdConfigSettings.size() == 0 || sjgcDataSummaries.size() == 0){
            logger.info("数据量监控配置信息或数据量监控数据量为空...");
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
            logger.info("发送数据量监控告警信息至告警中心成功");
        }
    }

    // 生成告警信息
    public void injectAlarmInfo(DataVolumeMonitorSetting setting, List<SjgcDataSummary> sjgcDataSummaries, List<AlarmMessage> alarmMessages){
        String nowHourStr = DateUtil.formatDate(new Date(),"HH");
        long acceptErrorSum = 0;    // 输出异常条数
        long acceptSuccessSum = 0;  // 输出成功条数
        long processErrorSum = 0;   // 输出异常条数
        long processSuccessSum = 0; // 输出成功条数
        long storageErrorSum = 0;   // 输出异常条数
        long storageSuccessSum = 0; // 输出成功条数
        for (SjgcDataSummary item : sjgcDataSummaries){
            if (item.getDataType().equals("接入")){
                acceptErrorSum += Long.parseLong(item.getOutErrorSum());
                acceptSuccessSum += Long.parseLong(item.getOutSuccessSum());
            }
            if (item.getDataType().equals("处理")){
                processErrorSum += Long.parseLong(item.getOutErrorSum());
                processSuccessSum += Long.parseLong(item.getOutSuccessSum());
            }
            if (item.getDataType().equals("入库")){
                storageErrorSum += Long.parseLong(item.getOutErrorSum());
                storageSuccessSum += Long.parseLong(item.getOutSuccessSum());
            }
        }
        SjgcDataSummary sjgcDataSummary = sjgcDataSummaries.get(0);
        if (setting.isAcceptCheck()){
            if (acceptErrorSum > Long.parseLong(setting.getAbnormalDataAlarmNum())){
                AlarmMessage alarmMessage = getPublicAlarmMsg(sjgcDataSummary);
                alarmMessage.setLevel(Integer.parseInt(setting.getAbnormalDataAlarmLevel()));
                String alarmcontent = String.format("消息：%s(接入)，异常数据量%s条；告警原因：异常数据阈值%s，数据异常",alarmMessage.getTableNameCh(),acceptErrorSum,setting.getAbnormalDataAlarmNum());
                alarmMessage.setAlarmcontent(alarmcontent);
                alarmMessages.add(alarmMessage);
            }
            if (nowHourStr.equals("23") && acceptSuccessSum < Long.parseLong(setting.getDataVolumeAlarmSum())){
                AlarmMessage alarmMessage = getPublicAlarmMsg(sjgcDataSummary);
                alarmMessage.setLevel(Integer.parseInt(setting.getDataVolumeAlarmLevel()));
                String alarmcontent = String.format("消息：%s(接入)，当天数据量%s条；告警原因：流量阈值为%s，数据异常",alarmMessage.getTableNameCh(),acceptSuccessSum,setting.getDataVolumeAlarmSum());
                alarmMessage.setAlarmcontent(alarmcontent);
                alarmMessages.add(alarmMessage);
            }
        }
        if (setting.isProcessCheck()){
            if (processErrorSum > Integer.parseInt(setting.getAbnormalDataAlarmNum())){
                AlarmMessage alarmMessage = getPublicAlarmMsg(sjgcDataSummary);
                alarmMessage.setLevel(Integer.parseInt(setting.getAbnormalDataAlarmLevel()));
                String alarmcontent = String.format("消息：%s(处理)，异常数据量%s条；告警原因：异常数据阈值%s，数据异常",alarmMessage.getTableNameCh(),processErrorSum,setting.getAbnormalDataAlarmNum());
                alarmMessage.setAlarmcontent(alarmcontent);
                alarmMessages.add(alarmMessage);
            }
            if (nowHourStr.equals("23") && processSuccessSum < Integer.parseInt(setting.getDataVolumeAlarmSum())){
                AlarmMessage alarmMessage = getPublicAlarmMsg(sjgcDataSummary);
                alarmMessage.setLevel(Integer.parseInt(setting.getDataVolumeAlarmLevel()));
                String alarmcontent = String.format("消息：%s(处理)，当天数据量%s条；告警原因：流量阈值为%s，数据异常",alarmMessage.getTableNameCh(),acceptSuccessSum,setting.getDataVolumeAlarmSum());
                alarmMessage.setAlarmcontent(alarmcontent);
                alarmMessages.add(alarmMessage);
            }
        }
        if (setting.isStorageCheck()){
            if (storageErrorSum > Integer.parseInt(setting.getAbnormalDataAlarmNum())){
                AlarmMessage alarmMessage = getPublicAlarmMsg(sjgcDataSummary);
                alarmMessage.setLevel(Integer.parseInt(setting.getAbnormalDataAlarmLevel()));
                String alarmcontent = String.format("消息：%s(入库)，异常数据量%s条；告警原因：异常数据阈值%s，数据异常",alarmMessage.getTableNameCh(),storageErrorSum,setting.getAbnormalDataAlarmNum());
                alarmMessage.setAlarmcontent(alarmcontent);
                alarmMessages.add(alarmMessage);
            }
            if (nowHourStr.equals("23") && storageSuccessSum < Integer.parseInt(setting.getDataVolumeAlarmSum())){
                AlarmMessage alarmMessage = getPublicAlarmMsg(sjgcDataSummary);
                alarmMessage.setLevel(Integer.parseInt(setting.getDataVolumeAlarmLevel()));
                String alarmcontent = String.format("消息：%s(入库)，当天数据量%s条；告警原因：流量阈值为%s，数据异常",alarmMessage.getTableNameCh(),storageSuccessSum,setting.getDataVolumeAlarmSum());
                alarmMessage.setAlarmcontent(alarmcontent);
                alarmMessages.add(alarmMessage);
            }
        }
    }

    // 获取告警信息公共数据
    public AlarmMessage getPublicAlarmMsg(SjgcDataSummary sjgcDataSummary){
        AlarmMessage alarmMessage = new AlarmMessage();
        alarmMessage.setAlarmsystem("数据工厂");
        alarmMessage.setAlarmmodule("数据量监测");
        alarmMessage.setAlarmflag(0);
        alarmMessage.setTableNameCh(sjgcDataSummary.getOutTableNameCh());
        alarmMessage.setTableNameEn(sjgcDataSummary.getOutTableNameEn());
        alarmMessage.setAlarmtime(DateUtil.formatDateTime(new Date()));
        return alarmMessage;
    }

}
