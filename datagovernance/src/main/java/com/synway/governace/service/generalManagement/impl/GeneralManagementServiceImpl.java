package com.synway.governace.service.generalManagement.impl;

import com.alibaba.fastjson.JSONObject;
import com.synway.common.bean.ServerResponse;
import com.synway.governace.dao.GeneralManagementDao;
import com.synway.governace.enums.BillAlarmSettingEnum;
import com.synway.governace.pojo.generalManagement.*;
import com.synway.governace.pojo.largeScreen.DataResource;
import com.synway.governace.service.generalManagement.GeneralManagementService;
import com.synway.governace.util.ExcelHelper;
import com.synway.governace.util.RestTemplateHandle;
import com.synway.governace.util.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GeneralManagementServiceImpl implements GeneralManagementService {
    private static Logger logger = LoggerFactory.getLogger(GeneralManagementServiceImpl.class);

    @Autowired
    GeneralManagementDao generalManagementDao;

    @Autowired
    private RestTemplateHandle restTemplateHandle;

    @Override
    public void saveOrUpdateGeneralSetting(ThresholdConfigSetting setting) {
        if(!ObjectUtils.isEmpty(setting)){
            // 设置主键id
            setting.setId(UUIDUtil.getUUID());
            if (setting.getParentId().equalsIgnoreCase("dataProperty")){
                PropertyAlarmSetting thresholdValue = setting.getThresholdValueProperty();
                thresholdValue.setFluctuateDays("7");
                setting.setThresholdValue(JSONObject.toJSONString(thresholdValue));
                logger.info("保存资产数据的数据为：" + JSONObject.toJSONString(setting));
                generalManagementDao.saveOrUpdateGeneralSetting(setting);
            }else if (setting.getParentId().equalsIgnoreCase("dataQuality")){
                QualityAlarmSetting thresholdValue = setting.getThresholdValueQuality();
                setting.setThresholdValue(JSONObject.toJSONString(thresholdValue));
                logger.info("保存质量数据的数据为：" + JSONObject.toJSONString(setting));
                generalManagementDao.saveOrUpdateGeneralSetting(setting);
            }else if (setting.getParentId().equalsIgnoreCase("dataVolumeMonitor")){
                if (StringUtils.isBlank(setting.getName())) return;
                DataVolumeMonitorSetting thresholdValue = setting.getDataVolumeMonitorSetting();
                setting.setThresholdValue(JSONObject.toJSONString(thresholdValue));
                logger.info("保存数据量配置的数据为：" + JSONObject.toJSONString(setting));
                generalManagementDao.saveOrUpdateGeneralSettingDataVolume(setting);
            }else if (setting.getParentId().equalsIgnoreCase("alarmPush")){
                // 校验重复数据
                List<ThresholdConfigSetting> thresholdConfigSettings = generalManagementDao.getGeneralSetting(setting.getParentId());
                for (ThresholdConfigSetting thresholdConfigSetting:thresholdConfigSettings){
                    String thresholdValue = thresholdConfigSetting.getThresholdValue();
                    String id = thresholdConfigSetting.getId();
                    AlarmPushSetting alarmPushSetting = new AlarmPushSetting();
                    if (!thresholdValue.equalsIgnoreCase("null") && !thresholdValue.equalsIgnoreCase("")){
                        alarmPushSetting = JSONObject.parseObject(thresholdValue, AlarmPushSetting.class);
                        if (alarmPushSetting.getPushMode().equalsIgnoreCase(setting.getThresholdValueAlarmPush().getPushMode())){
                            setting.setId(id);
                        }
                    }
                }
                AlarmPushSetting thresholdValue = setting.getThresholdValueAlarmPush();
                setting.setThresholdValue(JSONObject.toJSONString(thresholdValue));
                logger.info("保存告警推送的数据为：" + JSONObject.toJSONString(setting));
                generalManagementDao.addPushSetting(setting);
            }else if (setting.getParentId().equalsIgnoreCase("dataPiled")){
                // 校验重复数据
                List<ThresholdConfigSetting> thresholdConfigSettings = generalManagementDao.getGeneralSetting(setting.getParentId());
                thresholdConfigSettings.stream().forEach(item ->{
                    if (item.getName().equals(setting.getName())){
                        setting.setId(item.getId());
                        return;
                    }
                });
                DataPiledSetting thresholdValue = setting.getDataPiledSetting();
                setting.setThresholdValue(JSONObject.toJSONString(thresholdValue));
                logger.info("保存数据堆积配置的的数据为：" + JSONObject.toJSONString(setting));
                generalManagementDao.addPushSetting(setting);
            }
        }
    }

    @Override
    public ServerResponse getGeneralSetting(ThresholdConfigSetting setting) {
        ServerResponse serverResponse = null;
        String parentId = setting.getParentId();
        List<ThresholdConfigSetting> thresholdConfigSettings = generalManagementDao.getGeneralSetting(parentId);
        if (parentId.equalsIgnoreCase("dataProperty")){
            String thresholdValue = thresholdConfigSettings.get(0).getThresholdValue();
            PropertyAlarmSetting propertyAlarmSetting = JSONObject.parseObject(thresholdValue, PropertyAlarmSetting.class);
            return ServerResponse.asSucessResponse(propertyAlarmSetting);
        }else if (parentId.equalsIgnoreCase("dataQuality")){
            String thresholdValue = thresholdConfigSettings.get(0).getThresholdValue();
            QualityAlarmSetting qualityAlarmSetting = JSONObject.parseObject(thresholdValue, QualityAlarmSetting.class);
            return ServerResponse.asSucessResponse(qualityAlarmSetting);
        }else if (parentId.equalsIgnoreCase("alarmPush")){
            List<AlarmPushSetting> alarmPushSettings = new ArrayList<>();
            for (ThresholdConfigSetting thresholdConfigSetting:thresholdConfigSettings){
                String thresholdValue = thresholdConfigSetting.getThresholdValue();
                String id = thresholdConfigSetting.getId();
                AlarmPushSetting alarmPushSetting = new AlarmPushSetting();
                if (!thresholdValue.equalsIgnoreCase("null") && !thresholdValue.equalsIgnoreCase("")){
                    alarmPushSetting = JSONObject.parseObject(thresholdValue, AlarmPushSetting.class);
                }
                alarmPushSetting.setId(id);
                alarmPushSettings.add(alarmPushSetting);
            }
            return ServerResponse.asSucessResponse(alarmPushSettings);
        }else if (parentId.equalsIgnoreCase("dataPiled")){
            List<DataPiledSetting> dataPiledSettings = new ArrayList<>();
            thresholdConfigSettings.stream().forEach(item ->{
                String thresholdValue = item.getThresholdValue();
                if(StringUtils.isNotBlank(thresholdValue)){
                    DataPiledSetting dataPiledSetting = JSONObject.parseObject(thresholdValue, DataPiledSetting.class);
                    dataPiledSetting.setId(item.getId());
                    dataPiledSettings.add(dataPiledSetting);
                }
            });
            return ServerResponse.asSucessResponse(dataPiledSettings);
        }else if (parentId.equalsIgnoreCase("dataVolumeMonitor")){
            List<ThresholdConfigSetting> thresholdConfigSettings1 = generalManagementDao.getGeneralSettingDataVolume(parentId, setting.getName());
            if (thresholdConfigSettings1 == null || thresholdConfigSettings1.size() == 0){
                logger.info("获取到的dataVolumeMonitor配置为空");
                return ServerResponse.asErrorResponse("获取到的dataVolumeMonitor配置为空");
            }
            String thresholdValue = thresholdConfigSettings1.get(0).getThresholdValue();
            DataVolumeMonitorSetting dataVolumeMonitorSetting = JSONObject.parseObject(thresholdValue, DataVolumeMonitorSetting.class);
            return ServerResponse.asSucessResponse(dataVolumeMonitorSetting);
        }else {
            return serverResponse;
        }
    }

    @Override
    public ServerResponse editPushSetting(AlarmPushSetting alarmPushSetting) {
        if (!ObjectUtils.isEmpty(alarmPushSetting)){
            String id = alarmPushSetting.getId();
            if (StringUtils.isNotBlank(id)){
                ThresholdConfigSetting thresholdConfigSetting = new ThresholdConfigSetting();
                String thresholdValue = JSONObject.toJSONString(alarmPushSetting);
                thresholdConfigSetting.setId(id);
                thresholdConfigSetting.setThresholdValue(thresholdValue);
                generalManagementDao.editPushSetting(thresholdConfigSetting);
            }else {
                return ServerResponse.asErrorResponse("ID为空");
            }
        }
        return ServerResponse.asSucessResponse("保存数据成功");
    }

    @Override
    public void delPushSetting(ThresholdConfigSetting setting) {
        if (!ObjectUtils.isEmpty(setting)){
            String parentId = setting.getParentId();
            String[] ids = setting.getId().split(",");
            generalManagementDao.delPushSetting(parentId,ids);
        }
    }

    @Override
    public void saveOrUpdateReconciliationAlarmSetting(List<ThresholdConfigSetting> settings) {
        if(!CollectionUtils.isEmpty(settings)){
            // 删除旧配置
            generalManagementDao.deleteReconciliationAlarmSetting(settings.get(0).getParentId());
            // 插入新配置
            for(ThresholdConfigSetting setting:settings){
//                setting.setId(DateUtil.formatDateTime(new Date(),DateUtil.DEFAULT_PATTERN_DATETIME_SIMPLE_FULL));
                setting.setId(UUIDUtil.getUUID());
                logger.info("保存对账数据为数据为：" + JSONObject.toJSONString(setting));
                generalManagementDao.insertReconciliationAlarmSetting(setting);
            }
        }
    }

    @Override
    public BillAlarmSetting getReconciliationAlarmSetting(ThresholdConfigSetting setting) {
        String parentId = setting.getParentId();
        List<ThresholdConfigSetting> list = new ArrayList<>();
        if (StringUtils.isNotBlank(parentId)) {
            // 获取配置信息
            list = generalManagementDao.getReconciliationAlarmSettingByParentId(parentId);
        } else {
            String[] names = {BillAlarmSettingEnum.acceptStr.getName(), BillAlarmSettingEnum.storageStr.getName(),
                    BillAlarmSettingEnum.standardStr.getName(), BillAlarmSettingEnum.errorExpireDay.getName(),
                    BillAlarmSettingEnum.alarmExpireDay.getName(), BillAlarmSettingEnum.billExpireDay.getName(),
                    BillAlarmSettingEnum.autoOffDay.getName(), BillAlarmSettingEnum.settingTache.getName(),
                    BillAlarmSettingEnum.acceptTimingReconValue.getName(), BillAlarmSettingEnum.storageTimingReconValue.getName(),
                    BillAlarmSettingEnum.cleanReconAnalysisValue.getName()};
            list = generalManagementDao.getReconciliationAlarmSettingByName(names);
        }
        List<BillAlarmSetting> settingList = handleBillAlarmSetting(list);
        return !CollectionUtils.isEmpty(settingList) ? settingList.get(0) : null;
    }

    private List<BillAlarmSetting> handleBillAlarmSetting(List<ThresholdConfigSetting> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        List<BillAlarmSetting> settingList = new ArrayList<>();
        Map<String, List<ThresholdConfigSetting>> listMap = list.stream().collect(Collectors.groupingBy(ThresholdConfigSetting::getParentId));
        for (String key : listMap.keySet()) {
            List<ThresholdConfigSetting> thresholdConfigList = listMap.get(key);
            BillAlarmSetting setting = new BillAlarmSetting();
            for (ThresholdConfigSetting config : thresholdConfigList) {
                // 配置信息映射
                ExcelHelper.setFieldValueByName(BillAlarmSettingEnum.getCodeByName(config.getName()), config.getThresholdValue(), setting);
                // 开启的设置
                if (!StringUtils.equals("1", config.getIsActive())) {
                    continue;
                }
                if (StringUtils.equals(BillAlarmSettingEnum.acceptStr.getName(), config.getName())) {
                    setting.setSamePeriodType(StringUtils.isBlank(setting.getSamePeriodType()) ? "1," : setting.getSamePeriodType() + "1,");
                } else if (StringUtils.equals(BillAlarmSettingEnum.standardStr.getName(), config.getName())) {
                    setting.setChainRatioType(StringUtils.isBlank(setting.getChainRatioType()) ? "2," : setting.getChainRatioType() + "2,");
                } else if (StringUtils.equals(BillAlarmSettingEnum.storageStr.getName(), config.getName())) {
                    setting.setChainRatioType(StringUtils.isBlank(setting.getChainRatioType()) ? "3," : setting.getChainRatioType() + "3,");
                }
                setting.setId(config.getId());
            }
            setting.setParentId(key);
            setting.setBillExpireMaxDay("360");
            settingList.add(setting);
        }
        return settingList;
    }

    /**
     * 获取数据中心列表
     */
    @Override
    public List<FontOption> getAllDataCenter(){
        List<DataResource> dataCenters = restTemplateHandle.getDataCenter("0");
        List<FontOption> fontOptionList = new ArrayList<>();
        for (DataResource dataCenter : dataCenters) {
            FontOption fontOption = new FontOption();
            fontOption.setValue(dataCenter.getCenterId());
            fontOption.setLabel(dataCenter.getCenterName());
            fontOptionList.add(fontOption);
        }
        return fontOptionList;
    }

    @Override
    public List<DataResource> getDataResourceByDataCenterId(String dataCenterId) {
        return restTemplateHandle.getDataResourceByCenterId(dataCenterId, "0");
    }

    @Override
    public List<FontOption> getDataResourceByDataCenterIdAndType(String dataCenterId, String dataResourceType) {
        List<DataResource> dataResources = restTemplateHandle.getDataResourceByCenterIdAndType(dataCenterId, dataResourceType, "0");
        List<FontOption> fontOptionList = new ArrayList<>();
        for (DataResource dataCenter : dataResources) {
            FontOption fontOption = new FontOption();
            fontOption.setValue(dataCenter.getResId());
            fontOption.setLabel(dataCenter.getResName());
            fontOptionList.add(fontOption);
        }
        return fontOptionList;
    }

    @Override
    public DataResource getDataResourceById(String resId) {
        return restTemplateHandle.getResourceById(resId);
    }

    @Override
    public List<FontOption> getProjectList(String resId) {
        return restTemplateHandle.getProjectList(resId);
    }

    @Override
    public List<FontOption> getTableNamesByDataResourceIdAndProjectName(String resId, String projectName) {
        List<FontOption> fontOptions = new ArrayList<>();
        List<DetectedTable> tableInfos = restTemplateHandle.getTablesIncludeDetectedInfo(resId, projectName);
        DataResource dataResource = restTemplateHandle.getResourceById(resId);
        if(dataResource == null){
            logger.warn("数据源ID:{}  项目名称:{} 无法找到对应的数据源信息", resId, projectName);
            return fontOptions;
        }
        if(tableInfos == null){
            return fontOptions;
        }

        String resType = dataResource.getResType();
        for (DetectedTable tableInfo : tableInfos) {
            FontOption fontOption = new FontOption();
            fontOption.setLabel(tableInfo.getTableNameCN());
            fontOption.setValue(tableInfo.getTableNameEN());
            if(tableInfo.getTableType() == 3 && ("ftp".equals(resType) || "sftp".equals(resType) || "nfs".equals(resType) || "samba".equals(resType))){
                fontOption.setValue(tableInfo.getProjectName() + "→_→" + tableInfo.getTableNameEN());
            }
            fontOptions.add(fontOption);
            if(fontOptions.size() == 2000){
                break;
            }
        }
        return fontOptions;
    }

}
