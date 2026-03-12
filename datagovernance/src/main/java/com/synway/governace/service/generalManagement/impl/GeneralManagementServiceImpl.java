package com.synway.governace.service.generalManagement.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.synway.common.bean.ServerResponse;
import com.synway.governace.dao.DgnCommonSettingDao;
import com.synway.governace.dao.GeneralManagementDao;
import com.synway.governace.entity.dto.DgnCommonSettingDTO;
import com.synway.governace.entity.pojo.DgnCommonSettingEntity;
import com.synway.governace.entity.vo.DgnCommonSettingVO;
import com.synway.governace.enums.BillAlarmSettingEnum;
import com.synway.governace.enums.OperateLogFailReasonEnum;
import com.synway.governace.enums.OperateLogHandleTypeEnum;
import com.synway.governace.pojo.generalManagement.*;
import com.synway.governace.pojo.largeScreen.DataResource;
import com.synway.governace.service.generalManagement.GeneralManagementService;
import com.synway.governace.service.operateLog.OperateLogServiceImpl;
import com.synway.governace.util.ExcelHelper;
import com.synway.governace.util.RestTemplateHandle;
import com.synway.governace.util.UUIDUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GeneralManagementServiceImpl implements GeneralManagementService {

    @Autowired
    GeneralManagementDao generalManagementDao;
    @Autowired
    private RestTemplateHandle restTemplateHandle;

    @Resource
    private DgnCommonSettingDao dgnCommonSettingDao;
    @Resource
    private OperateLogServiceImpl operateLogServiceImpl;

    @Override
    public void saveOrUpdateGeneralSetting(DgnCommonSettingDTO dto) {
        try {
            if(!ObjectUtils.isEmpty(dto)){
                DgnCommonSettingEntity dgnCommonSetting = new DgnCommonSettingEntity();
                dgnCommonSetting.setId(UUIDUtil.getUUID());
                dgnCommonSetting.setParentId(dto.getParentId());
                dgnCommonSetting.setName(dto.getName());
                dgnCommonSetting.setIsActive(dto.getIsActive());
                dgnCommonSetting.setTreeType(dto.getTreeType());
                dgnCommonSetting.setThresholdValue(JSONObject.toJSONString(dto.getThresholdValue()));
                log.info(String.format(">>>>>>开始保存[%s]的配置：%s", dto.getName(), JSONObject.toJSONString(dto)));
                updateGeneralSetting(dgnCommonSetting);
                operateLogServiceImpl.updateGeneralSettingSuccessLog(OperateLogHandleTypeEnum.ALTER, "通用配置", dto.getName());
                log.info(String.format(">>>>>>[%s]配置保存成功：", dto.getName(), JSONObject.toJSONString(dto)));
            }
        }catch (Exception e){
            log.error(">>>>>>保存通用配置数据出错:", e);
            operateLogServiceImpl.updateGeneralSettingFailLog(OperateLogHandleTypeEnum.ALTER, OperateLogFailReasonEnum.YYXTFM, "通用配置", dto.getName());
        }
    }

    public void updateGeneralSetting(DgnCommonSettingEntity setting){
        LambdaQueryWrapper<DgnCommonSettingEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(DgnCommonSettingEntity::getParentId, setting.getParentId());
        if (dgnCommonSettingDao.selectCount(queryWrapper) > 0){
            LambdaUpdateWrapper<DgnCommonSettingEntity> updateWrapper = Wrappers.lambdaUpdate();
            updateWrapper.eq(DgnCommonSettingEntity::getParentId, setting.getParentId());
            updateWrapper.set(DgnCommonSettingEntity::getThresholdValue, setting.getThresholdValue());
            dgnCommonSettingDao.update(updateWrapper);
        }else {
            dgnCommonSettingDao.insert(setting);
        }
    }

    @Override
    public ServerResponse getGeneralSetting(DgnCommonSettingDTO dto) {
        try {
            log.info(">>>>>>获取通用配置参数：{}", JSONObject.toJSONString(dto));
            LambdaUpdateWrapper<DgnCommonSettingEntity> wrapper = Wrappers.lambdaUpdate();
            wrapper.eq(DgnCommonSettingEntity::getParentId, dto.getParentId());
            DgnCommonSettingEntity entity = dgnCommonSettingDao.selectOne(wrapper);

            DgnCommonSettingVO vo = new DgnCommonSettingVO();
            if (entity == null){
                return ServerResponse.asSucessResponse(vo);
            }
            vo.setName(entity.getName());
            vo.setParentId(entity.getParentId());
            vo.setIsActive(entity.getIsActive());
            vo.setTreeType(entity.getTreeType());
            vo.setId(entity.getId());
            vo.setThresholdValue(JSON.parseObject(entity.getThresholdValue()));
            return ServerResponse.asSucessResponse(vo);
        }catch (Exception e){
            log.error(">>>>>>获取通用配置数据出错：", e);
            return ServerResponse.asErrorResponse("获取通用配置数据出错");
        }
    }

    @Override
    public ServerResponse editPushSetting(AlarmPushSetting alarmPushSetting) {
        if (!ObjectUtils.isEmpty(alarmPushSetting)){
            String id = alarmPushSetting.getId();
            if (StringUtils.isNotBlank(id)){
                DgnCommonSettingEntity thresholdConfigSetting = new DgnCommonSettingEntity();
                String thresholdValue = JSONObject.toJSONString(alarmPushSetting);
                thresholdConfigSetting.setId(id);
                thresholdConfigSetting.setThresholdValue(thresholdValue);

                LambdaUpdateWrapper<DgnCommonSettingEntity> wrapper = Wrappers.lambdaUpdate();
                wrapper.eq(DgnCommonSettingEntity::getParentId, "alarmPush");
                wrapper.eq(DgnCommonSettingEntity::getId, thresholdConfigSetting.getId());
                wrapper.set(DgnCommonSettingEntity::getThresholdValue, thresholdConfigSetting.getThresholdValue());
                dgnCommonSettingDao.update(wrapper);
            }else {
                return ServerResponse.asErrorResponse("ID为空");
            }
        }
        return ServerResponse.asSucessResponse("保存数据成功");
    }

    @Override
    public void delPushSetting(DgnCommonSettingEntity setting) {
        if (!ObjectUtils.isEmpty(setting)){
            String parentId = setting.getParentId();
            String[] ids = setting.getId().split(",");
            generalManagementDao.delPushSetting(parentId,ids);
        }
    }

    @Override
    public void saveOrUpdateReconciliationAlarmSetting(List<DgnCommonSettingEntity> settings) {
        if(!CollectionUtils.isEmpty(settings)){
            // 删除旧配置
            generalManagementDao.deleteReconciliationAlarmSetting(settings.get(0).getParentId());
            // 插入新配置
            for(DgnCommonSettingEntity setting:settings){
//                setting.setId(DateUtil.formatDateTime(new Date(),DateUtil.DEFAULT_PATTERN_DATETIME_SIMPLE_FULL));
                setting.setId(UUIDUtil.getUUID());
                log.info("保存对账数据为数据为：" + JSONObject.toJSONString(setting));
                generalManagementDao.insertReconciliationAlarmSetting(setting);
            }
        }
    }

    @Override
    public BillAlarmSetting getReconciliationAlarmSetting(DgnCommonSettingEntity setting) {
        String parentId = setting.getParentId();
        List<DgnCommonSettingEntity> list = new ArrayList<>();
        if (StringUtils.isNotBlank(parentId)) {
            // 获取配置信息
            LambdaQueryWrapper<DgnCommonSettingEntity> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(DgnCommonSettingEntity::getParentId, setting.getParentId());
            list = dgnCommonSettingDao.selectList(wrapper);
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

    private List<BillAlarmSetting> handleBillAlarmSetting(List<DgnCommonSettingEntity> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        List<BillAlarmSetting> settingList = new ArrayList<>();
        Map<String, List<DgnCommonSettingEntity>> listMap = list.stream().collect(Collectors.groupingBy(DgnCommonSettingEntity::getParentId));
        for (String key : listMap.keySet()) {
            List<DgnCommonSettingEntity> thresholdConfigList = listMap.get(key);
            BillAlarmSetting setting = new BillAlarmSetting();
            for (DgnCommonSettingEntity config : thresholdConfigList) {
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
            log.warn("数据源ID:{}  项目名称:{} 无法找到对应的数据源信息", resId, projectName);
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
