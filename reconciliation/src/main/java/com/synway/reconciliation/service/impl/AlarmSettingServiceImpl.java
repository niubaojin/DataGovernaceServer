package com.synway.reconciliation.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.synway.reconciliation.dao.AlarmSettingDao;
import com.synway.reconciliation.pojo.*;
import com.synway.reconciliation.service.AlarmSettingService;
import com.synway.reconciliation.util.DateUtil;
import com.synway.reconciliation.util.ExcelHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 异常告警设置实现类
 * @author ywj
 */
@Service
public class AlarmSettingServiceImpl implements AlarmSettingService {

    @Autowired
    AlarmSettingDao reconciliationAlarmDao;

    @Value("${billExpireMaxDay}")
    private String billExpireMaxDay;

    @Override
    public String saveOrUpdateReconciliationAlarmSetting(String settingJsonStr) {
        List<ThresholdConfig> configList = JSON.parseArray(settingJsonStr, ThresholdConfig.class);
        if(!CollectionUtils.isEmpty(configList)){
            // 删除旧配置
            reconciliationAlarmDao.deleteReconciliationAlarmSetting(configList.get(0).getParentId());
            // 插入新配置
            for(ThresholdConfig config:configList){
                config.setId(DateUtil.formatDateTime(new Date(),DateUtil.DEFAULT_PATTERN_DATETIME_SIMPLE_FULL));
                reconciliationAlarmDao.insertReconciliationAlarmSetting(config);
            }
            return configList.get(0).getId();
        }
        return null;
    }

    @Override
    public BillAlarmSetting getReconciliationAlarmSetting(String parentId) {
        List<ThresholdConfig> list = new ArrayList<>();
        if (StringUtils.isNotBlank(parentId)) {
            // 获取配置信息
            list = reconciliationAlarmDao.getReconciliationAlarmSettingByParentId(parentId);
        } else {
            String[] names = {BillAlarmSettingEnum.acceptStr.getName(), BillAlarmSettingEnum.storageStr.getName(),
                    BillAlarmSettingEnum.standardStr.getName(), BillAlarmSettingEnum.errorExpireDay.getName(),
                    BillAlarmSettingEnum.alarmExpireDay.getName(), BillAlarmSettingEnum.billExpireDay.getName(),
                    BillAlarmSettingEnum.autoOffDay.getName(), BillAlarmSettingEnum.settingTache.getName(),
                    BillAlarmSettingEnum.acceptTimingReconValue.getName(),BillAlarmSettingEnum.storageTimingReconValue.getName(),
                    BillAlarmSettingEnum.cleanReconAnalysisValue.getName()};
            list = reconciliationAlarmDao.getReconciliationAlarmSettingByName(names);
        }
        List<BillAlarmSetting> settingList = handleBillAlarmSetting(list);
        return !CollectionUtils.isEmpty(settingList) ? settingList.get(0) : null;
    }

    @Override
    public List<BillAlarmSetting> getReconciliationAlarmPrivateSetting() {
        List<ThresholdConfig> list = new ArrayList<>();
        String[] names = {BillAlarmSettingEnum.acceptStr.getName(), BillAlarmSettingEnum.storageStr.getName(),
                BillAlarmSettingEnum.standardStr.getName(), BillAlarmSettingEnum.errorExpireDay.getName(),
                BillAlarmSettingEnum.alarmExpireDay.getName(), BillAlarmSettingEnum.billExpireDay.getName(),
                BillAlarmSettingEnum.autoOffDay.getName(), BillAlarmSettingEnum.settingTache.getName(),
                BillAlarmSettingEnum.acceptTimingReconValue.getName(),BillAlarmSettingEnum.storageTimingReconValue.getName(),
                BillAlarmSettingEnum.cleanReconAnalysisValue.getName()};
        list = reconciliationAlarmDao.getPrivateReconciliationAlarmSettingByName(names);
        List<BillAlarmSetting> setting = handleBillAlarmSetting(list);
        return setting;
    }

    private List<BillAlarmSetting> handleBillAlarmSetting(List<ThresholdConfig> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        List<BillAlarmSetting> settingList = new ArrayList<>();
        Map<String, List<ThresholdConfig>> listMap = list.stream().collect(Collectors.groupingBy(ThresholdConfig::getParentId));
        for (String key : listMap.keySet()) {
            List<ThresholdConfig> thresholdConfigList = listMap.get(key);
            BillAlarmSetting setting = new BillAlarmSetting();
            for (ThresholdConfig config : thresholdConfigList) {
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
            }
            setting.setParentId(key);
            setting.setBillExpireMaxDay(billExpireMaxDay);
            settingList.add(setting);
        }
        return settingList;
    }

    @Override
    public PageInfo getAlarmPhoneNumberList(GetListRequest getListRequest) {
        Page page=  PageHelper.startPage(getListRequest.getPageNum(),getListRequest.getPageSize());
        List<AlarmPhoneNumber> lists = new ArrayList<AlarmPhoneNumber>();
        lists = reconciliationAlarmDao.getAlarmPhoneNumberList(getListRequest);
        PageInfo<AlarmPhoneNumber> pageInfo = new PageInfo<AlarmPhoneNumber>(lists);
        return pageInfo;
    }

    @Override
    public String saveOrUpdateAlarmPhoneNumber(AlarmPhoneNumber alarmPhoneNumber) {
        AlarmPhoneNumber number = null;
        boolean addFlag = false;
        // 新增
        if(StringUtils.isNotBlank(alarmPhoneNumber.getId())){
            number = reconciliationAlarmDao.getAlarmPhoneNumberById(alarmPhoneNumber.getId());
        }
        if(null == number){
            number = new AlarmPhoneNumber();
            addFlag = true;
        }
        // 属性值复制
        BeanUtils.copyProperties(alarmPhoneNumber, number);
        if(addFlag){
            number.setId(UUID.randomUUID().toString().replace("-",""));
            reconciliationAlarmDao.insertAlarmPhoneNumber(number);
        }else{
            reconciliationAlarmDao.updateAlarmPhoneNumber(number);
        }
        return number.getId();
    }

    @Override
    public int deleteAlarmPhoneNumber(String deleteIds) {
        if (StringUtils.isNotBlank(deleteIds)) {
            String[] idArray = deleteIds.split(",");
            return reconciliationAlarmDao.deleteAlarmPhoneNumber(idArray);
        }
        return 0;
    }
}
