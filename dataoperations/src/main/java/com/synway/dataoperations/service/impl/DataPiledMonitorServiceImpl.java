package com.synway.dataoperations.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.synway.common.exception.ExceptionUtil;
import com.synway.dataoperations.constant.Common;
import com.synway.dataoperations.dao.DataPiledMonitorDao;
import com.synway.dataoperations.pojo.AlarmMessage;
import com.synway.dataoperations.pojo.ClassifyInfoTree;
import com.synway.dataoperations.pojo.dataPiledMonitor.*;
import com.synway.dataoperations.service.AlarmMessageService;
import com.synway.dataoperations.service.DataPiledMonitorService;
import com.synway.dataoperations.util.DateUtil;
import com.synway.dataoperations.util.KafkaUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DataPiledMonitorServiceImpl implements DataPiledMonitorService {
    private static Logger logger = LoggerFactory.getLogger(DataPiledMonitorServiceImpl.class);

    @Autowired
    DataPiledMonitorDao dataPiledMonitorDao;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    AlarmMessageService alarmMessageService;

    @Override
    public TreeAndCartPiled getTreeAndCardData(String searchName, String dataType) {
        TreeAndCartPiled treeAndCartPiled = new TreeAndCartPiled();
        List<DataPiledSetting> dataPiledSettings = dataPiledMonitorDao.getDataPiledMonitors(0);
        Map<String, List<DataPiledSetting>> listMap = dataPiledSettings.stream().collect(Collectors.groupingBy(DataPiledSetting::getPushHour));
        List<DataPiledSetting> dataPiledSettingsHour = new ArrayList<>();
        String hourStrNow = DateUtil.formatDate(new Date(),"HH");
        String hourStrLast = DateUtil.formatDate(DateUtil.addHour(new Date(), -1),"HH");
        dataPiledSettingsHour = listMap.get(hourStrNow);
        if (dataPiledSettingsHour == null || dataPiledSettingsHour.size() == 0){
            dataPiledSettingsHour = listMap.get(hourStrLast);
        }

        // 左侧树数据
        if (StringUtils.isNotBlank(searchName)){
            dataPiledSettingsHour = dataPiledSettingsHour.stream().filter(d -> d.getDataName().equalsIgnoreCase(searchName)).collect(Collectors.toList());
            List<ClassifyInfoTree> leftTreeList = getLeftTree(dataPiledSettingsHour);
            treeAndCartPiled.setLeftTree(leftTreeList);
            return treeAndCartPiled;
        }else {
            List<ClassifyInfoTree> leftTreeList = getLeftTree(dataPiledSettingsHour);
            treeAndCartPiled.setLeftTree(leftTreeList);
        }

        // 卡片数据
        Integer normalDataClassSum = 0;
        Integer piledDataClassSum = 0;
        Integer abnormalDataClassSum = 0;
        if (dataPiledSettingsHour == null || dataPiledSettingsHour.size() == 0){
            return treeAndCartPiled;
        }
        for (DataPiledSetting dataPiledSetting : dataPiledSettingsHour){
            if (dataPiledSetting.getDataType().equalsIgnoreCase("1")){
                normalDataClassSum += 1;
            }
            if (dataPiledSetting.getDataType().equalsIgnoreCase("2")){
                piledDataClassSum += 1;
            }
            if (dataPiledSetting.getDataType().equalsIgnoreCase("3")){
                abnormalDataClassSum += 1;
            }
        }
        treeAndCartPiled.setDataClassSum(dataPiledSettingsHour.size());
        treeAndCartPiled.setNormalDataClassSum(normalDataClassSum);
        treeAndCartPiled.setPiledDataClassSum(piledDataClassSum);
        treeAndCartPiled.setAbnormalDataClassSum(abnormalDataClassSum);

        // 点击卡片
        if (StringUtils.isNotBlank(dataType)){
            switch (dataType){
                case "1":
                    dataPiledSettingsHour = dataPiledSettingsHour.stream().filter(d -> d.getDataType().equalsIgnoreCase("1")).collect(Collectors.toList());
                    break;
                case "2":
                    dataPiledSettingsHour = dataPiledSettingsHour.stream().filter(d -> d.getDataType().equalsIgnoreCase("2")).collect(Collectors.toList());
                    break;
                case "3":
                    dataPiledSettingsHour = dataPiledSettingsHour.stream().filter(d -> d.getDataType().equalsIgnoreCase("3")).collect(Collectors.toList());
                    break;
                default:
            }
            List<ClassifyInfoTree> leftTreeList = getLeftTree(dataPiledSettingsHour);
            treeAndCartPiled.setLeftTree(leftTreeList);
            return treeAndCartPiled;
        }
        return treeAndCartPiled;
    }

    @Override
    public ReturnResultDPM getChartDataDPM(RequestParameterDPM requestParameterDPM) {
        ReturnResultDPM resultDPM = new ReturnResultDPM();
        List<DataPiledSetting> dataPiledSettings = dataPiledMonitorDao.getDataPiledMonitors(1);

        String todayStr = DateUtil.formatDate(new Date(),"yyyyMMdd");
        String yesTodayStr = DateUtil.formatDate(DateUtil.addDay(new Date(), -1),"yyyyMMdd");
        String nowHourStr = DateUtil.formatDate(new Date(),"HH");
        String hourStrLast = DateUtil.formatDate(DateUtil.addHour(new Date(), -1),"HH");

        // 根据数据名称过滤
        dataPiledSettings = dataPiledSettings.stream().filter(d -> d.getDataName().equalsIgnoreCase(requestParameterDPM.getDataName())).collect(Collectors.toList());
        // 今日数据类型列表
        List<DataPiledSetting> dataPiledSettingsToday = dataPiledSettings.stream().filter(d -> d.getInsertTime().equalsIgnoreCase(todayStr)).collect(Collectors.toList());
        // 昨日数据类型列表
        List<DataPiledSetting> dataPiledSettingsYestoday = dataPiledSettings.stream().filter(d -> d.getInsertTime().equalsIgnoreCase(yesTodayStr)).collect(Collectors.toList());

        // 折线图数据
        DataPiledChart dataPiledChart = new DataPiledChart();           // 折线图
        String dataPiledTime = requestParameterDPM.getDataPiledTime();  // 时间选择
        List<String> dataPiledRates = new ArrayList<>();                // 抖动率
        List<String> dataPiledHours = new ArrayList<>();                // 推送数据时点
        initDataPiledAndHourList(dataPiledTime, nowHourStr, dataPiledHours, dataPiledRates);
        dataPiledChart.setPiledRate(dataPiledRates);
        dataPiledChart.setPushHours(dataPiledHours);
        if (!dataPiledTime.equalsIgnoreCase("today")){
            for (DataPiledSetting setting : dataPiledSettingsYestoday){
                if (Integer.parseInt(setting.getPushHour()) > Integer.parseInt(nowHourStr)){
                    int index = Integer.parseInt(setting.getPushHour()) - Integer.parseInt(nowHourStr) -1;
                    dataPiledChart.getPiledRate().set(index, setting.getPiledRate());
                }
            }
        }
        for (DataPiledSetting setting : dataPiledSettingsToday){
            if (dataPiledTime.equalsIgnoreCase("today")){
                if (Integer.parseInt(setting.getPushHour()) <= Integer.parseInt(nowHourStr)){
                    int index = Integer.parseInt(setting.getPushHour());
                    dataPiledChart.getPiledRate().set(index, setting.getPiledRate());
                }
            }else {
                if (Integer.parseInt(setting.getPushHour()) <= Integer.parseInt(nowHourStr)) {
                    int index = 23 - Integer.parseInt(nowHourStr) + Integer.parseInt(setting.getPushHour());
                    dataPiledChart.getPiledRate().set(index, setting.getPiledRate());
                }
            }
        }
        // 表格数据
        DataPiledSetting dataPiledSetting = null;
        for (DataPiledSetting dataPiledSetting1 : dataPiledSettingsToday){
            if (dataPiledSetting1.getPushHour().equalsIgnoreCase(nowHourStr)){
                dataPiledSetting = dataPiledSetting1;
            }
        }
        if (dataPiledSetting == null || StringUtils.isBlank(dataPiledSetting.getDataName())){
            for (DataPiledSetting dataPiledSetting1 : dataPiledSettingsToday){
                if (dataPiledSetting1.getPushHour().equalsIgnoreCase(hourStrLast)){
                    dataPiledSetting = dataPiledSetting1;
                }
            }
        }
        // 表格注入阈值数据
        DataPiledSetting settingData = new DataPiledSetting();
        List<DataPiledSetting> settings = getSettingForApi();
        if (settings.size() > 0){
            for (DataPiledSetting setting : settings){
                if(setting.getDataName().equalsIgnoreCase(requestParameterDPM.getDataName())){
                    settingData = setting;
                    break;
                }
            }
        }
        dataPiledSetting.setYiban(settingData.getYiban());
        dataPiledSetting.setJinggao(settingData.getJinggao());
        dataPiledSetting.setYanzhong(settingData.getYanzhong());
        resultDPM.setDataPiledChart(dataPiledChart);
        resultDPM.setDataPiledSetting(dataPiledSetting);
        return resultDPM;
    }

    private void initDataPiledAndHourList(String dataPiledTime, String nowHourStr, List<String> dataPiledHours, List<String> dataPiledRates){
        if (dataPiledTime.equals("today")){
            for (int i = 0; i<= Integer.parseInt(nowHourStr); i++){
                dataPiledHours.add(String.valueOf(i));
                dataPiledRates.add("0");
            }
        }else {
            for (int i = Integer.parseInt(nowHourStr) + 1; i<= 23; i++){
                dataPiledHours.add(String.valueOf(i));
                dataPiledRates.add("0");
            }
            for (int i = 0; i<= Integer.parseInt(nowHourStr); i++){
                dataPiledHours.add(String.valueOf(i));
                dataPiledRates.add("0");
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void getDataPiledMonitor() {
        List<DataPiledSetting> dataPiledSettings = getSettingForApi();
        if (dataPiledSettings.size() == 0){
            logger.info("从数据堆积设置获取到的数据为0！");
            return;
        }
        String hourStr = DateUtil.formatDate(new Date(),"HH");
        for (DataPiledSetting dataPiledSetting : dataPiledSettings) {
            if (!dataPiledSetting.getIsEnable().equalsIgnoreCase("true")){
                logger.info("数据名称：[{}]没有启用，略过...");
                continue;
            }
            ConnectInfo connectInfo = JSONObject.parseObject(dataPiledSetting.getConnectInfo(), ConnectInfo.class);
            connectInfo.setTopic(dataPiledSetting.getConsumTopic());
            connectInfo.setGroup(dataPiledSetting.getConsumGroup());
            // 获取kafkaConsumer
            KafkaConsumer kafkaConsumer = KafkaUtil.connect(connectInfo);
            try {
                if (kafkaConsumer == null){
                    logger.info("ip=={},topic=={},group=={},dataName=={},获取kafkaConsumer为空...",connectInfo.getZookeeperIp(),connectInfo.getTopic(),connectInfo.getGroup(),dataPiledSetting.getDataName());
                    dataPiledSetting.setPushHour(hourStr);
                    dataPiledSetting.setOffset(0);
                    dataPiledSetting.setLogSize(0);
                    dataPiledSetting.setLag(0);
                    dataPiledSetting.setPiledRate("0");
                    dataPiledSetting.setDataType("3");
                    continue;
                }
                // 获取主题的分区信息
                List<PartitionInfo> partitionInfos = getMetaInfo(kafkaConsumer, connectInfo.getTopic());
                if (partitionInfos.size() == 0){
                    logger.info("ip=={},topic=={},group=={},dataName=={},获取partitionInfos为空...",connectInfo.getZookeeperIp(),connectInfo.getTopic(),connectInfo.getGroup(),dataPiledSetting.getDataName());
                    dataPiledSetting.setPushHour(hourStr);
                    dataPiledSetting.setOffset(0);
                    dataPiledSetting.setLogSize(0);
                    dataPiledSetting.setLag(0);
                    dataPiledSetting.setPiledRate("0");
                    dataPiledSetting.setDataType("3");
                    continue;
                }
                List<TopicPartition> topicPartitions = new ArrayList<>();
                partitionInfos.stream().forEach(item -> {
                    topicPartitions.add(new TopicPartition(item.topic(), item.partition()));
                });

                Map<Integer, Long> endOffsetMap = new HashMap();       // logSize
                Map<Integer, Long> commitOffsetMap = new HashMap();    // offset
                Map<TopicPartition, Long> topicPartitionLongMap = kafkaConsumer.endOffsets(topicPartitions);
                for (TopicPartition topicPartition : topicPartitionLongMap.keySet()) {
                    // logSize
                    endOffsetMap.put(topicPartition.partition(), topicPartitionLongMap.get(topicPartition));
                    // offset
                    OffsetAndMetadata offsetAndMetadata = kafkaConsumer.committed(topicPartition);
                    if (offsetAndMetadata == null) {
                        commitOffsetMap.put(topicPartition.partition(), (long) 0);
                    } else {
                        commitOffsetMap.put(topicPartition.partition(), offsetAndMetadata.offset());
                    }
                }
                long lagSum = 0l;       // 累加lag
                long offsetSum = 0l;    // 累加offset
                long logSizeSum = 0l;   // 累加logSize
                if (endOffsetMap.size() == commitOffsetMap.size()){
                    for (Object data: endOffsetMap.keySet()){
                        long endOffset = endOffsetMap.get(data);
                        long commitOffset = commitOffsetMap.get(data);
                        long diffOffset = endOffset - commitOffset;
                        lagSum += diffOffset;
                        offsetSum += commitOffset;
                        logSizeSum += endOffset;
                    }
                }
                // 堆积率
                String piledRate = "0";
                BigDecimal lagValue = BigDecimal.valueOf(lagSum);
                BigDecimal yibanValue = BigDecimal.valueOf(Integer.parseInt(dataPiledSetting.getYiban()));
                if (yibanValue.compareTo(BigDecimal.ZERO) != 0 && lagValue.compareTo(yibanValue) == 1){
                    BigDecimal fluctuateRate = lagValue.subtract(yibanValue).divide(yibanValue,4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)).setScale(2);
                    piledRate = fluctuateRate + "";
                    dataPiledSetting.setDataType("2");
                }else {
                    dataPiledSetting.setDataType("1");
                }
                dataPiledSetting.setOffset(offsetSum);
                dataPiledSetting.setLogSize(logSizeSum);
                dataPiledSetting.setLag(lagSum);
                dataPiledSetting.setPiledRate(piledRate);
                dataPiledSetting.setPushHour(hourStr);
            }catch (Exception e){
                logger.error("处理【{}】时失败：\n{}",dataPiledSetting.getDataName(),ExceptionUtil.getExceptionTrace(e));
            }finally {
                kafkaConsumer.close();
            }
        }
        // 数据入库
        dataPiledSettings.stream().forEach(dataPiledSetting -> {
            dataPiledMonitorDao.insertDataPiledMonitor(dataPiledSetting);
        });
        logger.info("数据堆积监控入库完成");
        // 删除历史数据
        int delSum = dataPiledMonitorDao.delDataPiledMonitors();
        logger.info("删除数据堆积监控历史数据：{}",delSum);
        // 告警数据推送至告警中心
        sendAlarmMsg(dataPiledSettings);
        logger.info("本次调度结束......");
    }

    public void sendAlarmMsg(List<DataPiledSetting> dataPiledSettings){
        List<AlarmMessage> alarmMessages = new ArrayList<>();   // 告警数据列表
        dataPiledSettings.stream().forEach(d ->{
            if (d.getDataType().equalsIgnoreCase("2")){
                AlarmMessage alarmMessage = new AlarmMessage();
                alarmMessage.setAlarmsystem("数据工厂");
                alarmMessage.setAlarmmodule("数据堆积监测");
                alarmMessage.setAlarmflag(0);
                int alarmFlag = 0;
                String alarmName = "一般";
                if (d.getLag() > Integer.parseInt(d.getJinggao())){
                    alarmFlag = 1;
                    alarmName = "警告";
                }
                if (d.getLag() > Integer.parseInt(d.getYanzhong())){
                    alarmFlag = 2;
                    alarmName = "严重";
                }
                alarmMessage.setLevel(alarmFlag);
                alarmMessage.setLevelName(alarmName);
                alarmMessage.setTableNameCh(d.getDataName());
                alarmMessage.setTableNameEn(d.getConsumTopic());
                alarmMessage.setAlarmtime(DateUtil.formatDateTime(new Date()));
                String alarmcontent = String.format("消息：%s，数据堆积值是%s，堆积率为%s%s；告警原因：超过堆积阈值%s",d.getDataName(),d.getLag(),d.getPiledRate(),"%",d.getYiban());
                alarmMessage.setAlarmcontent(alarmcontent);
                alarmMessages.add(alarmMessage);
            }
        });
        if (alarmMessages.size() > 0){
            String jsonString = JSONArray.toJSONString(alarmMessages);
            alarmMessageService.insertAlarmMessage(jsonString);
            logger.info("发送告警信息至告警中心成功");
        }
    }

    public List<DataPiledSetting> getSettingForApi(){
        JSONObject param = new JSONObject();
        param.put("parentId","dataPiled");
        String url = Common.DATAGOVERNANCE_BASEURL + "/configManage/generalManagement/getGeneralSetting";
        String jsonObject = restTemplate.postForObject(url, param, String.class);
        JSONObject result = JSONObject.parseObject(jsonObject);

        List<DataPiledSetting> dataPiledSettings = new ArrayList<>();
        if (result.getIntValue("status") == 1){
            dataPiledSettings = result.getJSONArray("data").toJavaList(DataPiledSetting.class);
            return dataPiledSettings;
        }
        return dataPiledSettings;
    }

    public List<PartitionInfo> getMetaInfo(KafkaConsumer kafkaConsumer, String topic) {
        try {
            List<PartitionInfo> list = kafkaConsumer.partitionsFor(topic);
            if (CollectionUtils.isNotEmpty(list)) {
                return list;
            }
        } catch (Exception e) {
            logger.error("获取主题信息报错:\n" + ExceptionUtil.getExceptionTrace(e));
            return new ArrayList<>();
        }
        return new ArrayList<>();
    }

    // 页面左侧树数据
    public List<ClassifyInfoTree> getLeftTree(List<DataPiledSetting> dataPiledSettings){
        List<ClassifyInfoTree> leftTree = new ArrayList<>();
        if (dataPiledSettings == null){
            return leftTree;
        }
        ClassifyInfoTree classifyInfoTree1 = new ClassifyInfoTree();
        classifyInfoTree1.setLabel("kafka");
        classifyInfoTree1.setValue("kafka");
        List<ClassifyInfoTree> classifyInfoTrees1 = new ArrayList<>();
        for (int i = 0; i<dataPiledSettings.size(); i++){
            if (dataPiledSettings.get(i).getDataName() != null){
                ClassifyInfoTree classifyInfoTree2 = new ClassifyInfoTree();
                classifyInfoTree2.setValue(dataPiledSettings.get(i).getDataName());
                classifyInfoTree2.setLabel(dataPiledSettings.get(i).getDataName());
                classifyInfoTree2.setChildren(new LinkedList<>());
                classifyInfoTrees1.add(classifyInfoTree2);
            }
        }
        classifyInfoTree1.setChildren(classifyInfoTrees1);
        leftTree.add(classifyInfoTree1);
        return leftTree;
    }

}
