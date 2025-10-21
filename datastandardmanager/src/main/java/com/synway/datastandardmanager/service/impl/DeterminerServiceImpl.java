package com.synway.datastandardmanager.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.synway.datastandardmanager.constants.Common;
import com.synway.datastandardmanager.entity.dto.DeterminerDTO;
import com.synway.datastandardmanager.entity.pojo.FieldDeterminerEntity;
import com.synway.datastandardmanager.entity.pojo.FieldDeterminerHisEntity;
import com.synway.datastandardmanager.entity.pojo.FieldDeterminerVersionEntity;
import com.synway.datastandardmanager.entity.vo.FieldDeterminerFilterVO;
import com.synway.datastandardmanager.entity.vo.KeyValueVO;
import com.synway.datastandardmanager.entity.vo.ValueLabelVO;
import com.synway.datastandardmanager.enums.ErrorCodeEnum;
import com.synway.datastandardmanager.enums.KeyStrEnum;
import com.synway.datastandardmanager.enums.OperateLogHandleTypeEnum;
import com.synway.datastandardmanager.mapper.DgnCommonSettingMapper;
import com.synway.datastandardmanager.mapper.FieldDeterminerHisMapper;
import com.synway.datastandardmanager.mapper.FieldDeterminerMapper;
import com.synway.datastandardmanager.mapper.FieldDeterminerVersionMapper;
import com.synway.datastandardmanager.service.DeterminerService;
import com.synway.datastandardmanager.util.DateUtil;
import com.synway.datastandardmanager.util.PinYinUtil;
import com.synway.datastandardmanager.util.UUIDUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.text.Collator;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DeterminerServiceImpl implements DeterminerService {

    @Resource
    FieldDeterminerMapper fieldDeterminerMapper;
    @Resource
    FieldDeterminerVersionMapper fieldDeterminerVersionMapper;
    @Resource
    FieldDeterminerHisMapper fieldDeterminerHisMapper;
    @Resource
    private DgnCommonSettingMapper dgnCommonSettingMapper;
    @Resource
    private OperateLogServiceImpl operateLogServiceImpl;

    @Override
    public List<FieldDeterminerEntity> getFieldDeterminerTable(DeterminerDTO determinerDTO) {
        log.info(">>>>>>查询限定词的请求参数为：{}", JSONObject.toJSONString(determinerDTO));
        List<FieldDeterminerEntity> list = new ArrayList<>();
        try {
            if (determinerDTO.getSort() == null || determinerDTO.getSort().isEmpty()) {
                determinerDTO.setSort("modDate");
            }
            if (determinerDTO.getSortOrder() == null || determinerDTO.getSortOrder().isEmpty()) {
                determinerDTO.setSortOrder("desc");
            }
            list = fieldDeterminerMapper.getFieldDeterminerTable(determinerDTO);
            if (list != null && !list.isEmpty()) {
                int num = 1;
                for (FieldDeterminerEntity data : list) {
                    // 码表值
                    data.setDeterminerState(KeyStrEnum.getValueByKeyAndType(data.getDeterminerStateNum(), Common.DETERMINER_ENUM));
                    data.setDeterminerType(KeyStrEnum.getValueByKeyAndType("2_" + data.getDeterminerTypeNum(), Common.DETERMINER_ENUM));
                    data.setNum(num++);
                }
            }
        } catch (Exception e) {
            log.error(">>>>>>查询限定词数据列表出错：", e);
        }
        return list;
    }

    @Override
    public FieldDeterminerFilterVO getFilterObject() {
        log.info(">>>>>>查询限定词筛选数据");
        FieldDeterminerFilterVO filterVO = new FieldDeterminerFilterVO();
        try {
            List<FieldDeterminerEntity> list = fieldDeterminerMapper.selectList(Wrappers.lambdaQuery());
            list = list.stream().filter(data -> data.getDeterminerStateNum() != null &&
                                                data.getDeterminerTypeNum() != null &&
                                                data.getVersions() != null &&
                                                data.getRegOrg() != null).collect(Collectors.toList());
            // 进行筛选
            List<ValueLabelVO> determinerStateFilter = new ArrayList<>();
            list.stream().map(FieldDeterminerEntity::getDeterminerStateNum).distinct().forEach(d ->
                    determinerStateFilter.add(new ValueLabelVO(d, KeyStrEnum.getValueByKeyAndType(d, Common.DETERMINER_ENUM)))
            );
            List<ValueLabelVO> determinerTypeFilter = new ArrayList<>();
            list.stream().map(FieldDeterminerEntity::getDeterminerTypeNum).distinct().forEach(d ->
                    determinerTypeFilter.add(new ValueLabelVO(String.valueOf(d), KeyStrEnum.getValueByKeyAndType("2_" + d, Common.DETERMINER_ENUM)))
            );
            List<ValueLabelVO> versionFilter = new ArrayList<>();
            list.stream().map(FieldDeterminerEntity::getVersions).distinct().forEach(d ->
                    versionFilter.add(new ValueLabelVO(d, d))
            );
            List<ValueLabelVO> regOrgFilter = new ArrayList<>();
            list.stream().map(FieldDeterminerEntity::getRegOrg).distinct().forEach(d ->
                    regOrgFilter.add(new ValueLabelVO(d, d))
            );
            // 排序后插入进去
            determinerStateFilter.sort((s1, s2) -> Collator.getInstance(java.util.Locale.CHINA).compare(s1.getLabel(), s2.getLabel()));
            determinerTypeFilter.sort((s1, s2) -> Collator.getInstance(java.util.Locale.CHINA).compare(s1.getLabel(), s2.getLabel()));
            versionFilter.sort((s1, s2) -> Collator.getInstance(java.util.Locale.CHINA).compare(s1.getLabel(), s2.getLabel()));
            regOrgFilter.sort((s1, s2) -> Collator.getInstance(java.util.Locale.CHINA).compare(s1.getLabel(), s2.getLabel()));
            filterVO.setDeterminerStateFilter(determinerStateFilter);
            filterVO.setDeterminerTypeFilter(determinerTypeFilter);
            filterVO.setVersionFilter(versionFilter);
            filterVO.setRegOrgFilter(regOrgFilter);
        } catch (Exception e) {
            log.error(">>>>>>查询限定词筛选数据报错：", e);
        }
        return filterVO;
    }

    @Override
    public String addOneData(FieldDeterminerEntity data) {
        try {
            LambdaQueryWrapper<FieldDeterminerEntity> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(FieldDeterminerEntity::getDeterminerId, data.getDeterminerId());
            if (fieldDeterminerMapper.selectCount(wrapper) > 0) {
                throw new Exception(String.format("%s：内部标识符[%s]重复", ErrorCodeEnum.CHECK_UNION_ERROR, data.getDeterminerId()));
            }
            data.setDname(getDNameByChines(data.getDchinseName()));
            //大版本 从版本管理中读取
            String version = dgnCommonSettingMapper.searchVersion();
            String versions = JSONObject.parseObject(version).getString("fieldDeterminerVersions");
            data.setVersions(versions);
            // 版本发布日期YYYYMMDD，审核时间date：YYYYMMDD
            String todayStr = DateUtil.formatDateTime(new Date(), DateUtil.DEFAULT_PATTERN_DATE_SIMPLE);
            Date date = DateUtil.parseDate(todayStr, DateUtil.DEFAULT_PATTERN_DATE_SIMPLE);
            data.setReleaseDate(Integer.valueOf(todayStr));
            data.setOnDate(date);
            log.info(">>>>>>限定词添加的内容为：{}", JSONObject.toJSONString(data));
            fieldDeterminerMapper.insert(data);
            // 发送操作日志
            operateLogServiceImpl.fieldDeterminerSuccessLog(OperateLogHandleTypeEnum.ADD, "限定词管理", data);
            return Common.ADD_SUCCESS;
        } catch (Exception e) {
            log.error(">>>>>>限定词添加失败：", e);
            return Common.ADD_FAIL;
        }
    }

    @Override
    public String upOneData(FieldDeterminerEntity data) {
        try {
            LambdaQueryWrapper<FieldDeterminerEntity> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(FieldDeterminerEntity::getDeterminerId, data.getDeterminerId());
            FieldDeterminerEntity searchFieldDeterminer = fieldDeterminerMapper.selectOne(wrapper);
            if (searchFieldDeterminer == null || searchFieldDeterminer.getDeterminerId() == null) {
                throw new Exception(String.format("%s：内部标识符[%s]在数据库中对应的数据不存在，更新失败", ErrorCodeEnum.CHECK_PARAMETER_ERROR, data.getDeterminerId()));
            }
            // 判断是否修改限定词属性值，未做任何改动，直接返回，不更新数据库
            searchFieldDeterminer.setDeterminerState(KeyStrEnum.getValueByKeyAndType(searchFieldDeterminer.getDeterminerStateNum(), Common.DETERMINER_ENUM));
            searchFieldDeterminer.setDeterminerType(KeyStrEnum.getValueByKeyAndType("2_" + searchFieldDeterminer.getDeterminerTypeNum(), Common.DETERMINER_ENUM));
            if (data.equals(searchFieldDeterminer)) {
                return Common.NOT_CHANGED;
            }
            if (data.getModDate() == null) {
                throw new Exception(String.format("%s：更新时间字段的值不能为空", ErrorCodeEnum.CHECK_PARAMETER_ERROR));
            }
            // 先判断限定词中文名称是否已经修改，是则重新生成限定词标示符，如果限定词标示符也是空、也要重新获取
            wrapper = Wrappers.lambdaQuery();
            wrapper.eq(FieldDeterminerEntity::getDeterminerId, data.getDeterminerId());
            wrapper.eq(FieldDeterminerEntity::getDchinseName, data.getDchinseName());
            if (fieldDeterminerMapper.selectCount(wrapper) == 0 || StringUtils.isBlank(data.getDname())) {
                String dName = getDNameByChines(data.getDchinseName());
                data.setDname(dName);
            }
            if (data.getReleaseDate() == null) {
                String todayStr = DateUtil.formatDateTime(new Date(), DateUtil.DEFAULT_PATTERN_DATE_SIMPLE);
                data.setReleaseDate(Integer.valueOf(todayStr));
            }
            if (data.getOnDate() == null) {
                data.setOnDate(new Date());
            }
            if (data.getVersions() == null) {
                data.setVersions(searchFieldDeterminer.getVersions());
            }
            //大版本号 从系统配置获取
            String version = dgnCommonSettingMapper.searchVersion();
            String versions = JSONObject.parseObject(version).getString("fieldDeterminerVersions");
            data.setVersions(versions);
            log.info(">>>>>>需要编辑的限定词数据为{}", JSONObject.toJSONString(data));
            //如果这条数据的更新时间小于数据库中的数据 说明该条记录已经被别人更新，本次更新失败
            if (fieldDeterminerMapper.upOneData(data) == 0) {
                throw new Exception(String.format("%s：数据已经被别的页面更新，本次更新失败，请刷新页面后重新修改", ErrorCodeEnum.QUERY_SQL_ERROR));
            }

            if ("05".equals(searchFieldDeterminer.getDeterminerStateNum())) {
                String uuid = UUIDUtil.getUUID();
                Date updateTime = new Date();
                //生成限定词版本信息
                injectDeterminerVersion(data, searchFieldDeterminer, versions, uuid, updateTime);
                //保存至备份表SYNLTE.FIELDDETERMINER_HISTORY中
                injectDeterminerHis(searchFieldDeterminer, uuid, updateTime);
            }
            // 发送操作日志
            operateLogServiceImpl.fieldDeterminerSuccessLog(OperateLogHandleTypeEnum.ALTER, "限定词管理", data);
            return Common.UPDATE_SUCCESS;

        } catch (Exception e) {
            log.error(">>>>>>更新限定词失败：", e);
            return Common.UPDATE_FAIL;
        }
    }

    public void injectDeterminerHis(FieldDeterminerEntity fieldDeterminerOld, String uuid, Date updateTime) throws InvocationTargetException, IllegalAccessException {
        log.info(">>>>>>开始生成限定词历史信息");
        FieldDeterminerHisEntity hisEntity = new FieldDeterminerHisEntity();
        if (fieldDeterminerOld.getOnDate() == null){
            fieldDeterminerOld.setOnDate(new Date());
        }
        BeanUtils.copyProperties(hisEntity, fieldDeterminerOld);
        hisEntity.setFieldDeterminerVersion(uuid);
        hisEntity.setModDate(updateTime);
        hisEntity.setDeterminerState(fieldDeterminerOld.getDeterminerStateNum());
        fieldDeterminerHisMapper.insert(hisEntity);
        log.info(">>>>>>生成限定词历史信息结束");
    }

    public void injectDeterminerVersion(FieldDeterminerEntity data, FieldDeterminerEntity fieldDeterminerOld, String versions, String uuid, Date updateTime) {
        log.info(">>>>>>开始生成限定词版本信息");
        FieldDeterminerVersionEntity fieldDeterminerVersion = new FieldDeterminerVersionEntity();
        fieldDeterminerVersion.setDChineseName(fieldDeterminerOld.getDchinseName());
        if (!fieldDeterminerOld.getDname().isEmpty()) {
            fieldDeterminerVersion.setDName(fieldDeterminerOld.getDname());
        }
        StringBuffer stringMemo = new StringBuffer();
        if (!(data.getRegOrg().equals(fieldDeterminerOld.getRegOrg()))) {
            stringMemo.append("提交机构属性,");
        }
        if (!(data.getFacturer().equals(fieldDeterminerOld.getFacturer()))) {
            stringMemo.append("制作厂商属性,");
        }
        if (data.getDeterminerTypeNum() != fieldDeterminerOld.getDeterminerTypeNum()) {
            stringMemo.append("部标标准属性,");
        }
        if (!(data.getAuthor().equals(fieldDeterminerOld.getAuthor()))) {
            stringMemo.append("修订人属性,");
        }
        if (!(data.getMemo().equals(fieldDeterminerOld.getMemo()))) {
            stringMemo.append("限定词描述属性,");
        }
        if (data.getVersions() != null && !data.getVersions().equals(fieldDeterminerOld.getVersions())) {
            stringMemo.append("大版本号属性,");
        }
        if (!data.getDeterminerStateNum().equalsIgnoreCase(fieldDeterminerOld.getDeterminerStateNum())) {
            stringMemo.append("状态属性,");
        }
        StringBuffer constantStr = null;
        if (stringMemo.length() != 0) {
            constantStr = new StringBuffer("修改了");
            constantStr.append(stringMemo);
            if (constantStr.indexOf(",", constantStr.length() - 1) != -1) {
                fieldDeterminerVersion.setMemo(constantStr.substring(0, constantStr.length() - 1));
            }
        }
        fieldDeterminerVersion.setFieldDeterminerVersion(uuid);
        fieldDeterminerVersion.setVersions(versions);
        fieldDeterminerVersion.setVersion(fieldDeterminerOld.getReleaseDate());
        fieldDeterminerVersion.setAuthor(fieldDeterminerOld.getAuthor());
        fieldDeterminerVersion.setUpdateTime(updateTime);
        fieldDeterminerVersionMapper.insert(fieldDeterminerVersion);
        log.info(">>>>>>生成限定词版本信息结束");
    }

    @Override
    public String updateDeterminerState(String id, String state, String modDate) {
        try {
            log.info(">>>>>>启用/停用限定词的参数条件为determinerId:{} ,state:{} , modDate:{}", id, state, modDate);
            checkDataExist(id, modDate);
            //生成版本日期值，为当前日期
            String todayStr = DateUtil.formatDateTime(new Date(), DateUtil.DEFAULT_PATTERN_DATE_SIMPLE);
            Integer releaseDate = Integer.valueOf(todayStr);
            int updateNum = fieldDeterminerMapper.updateDeterminerState(id, state, modDate, releaseDate);
            if (updateNum == 0) {
                throw new Exception(String.format("%s：数据已经被别的页面更新，本次更新失败，请刷新页面后重新修改", ErrorCodeEnum.QUERY_SQL_ERROR));
            }
            log.info(">>>>>>启用/停用限定词结束");
            return Common.UPDATE_SUCCESS;
        } catch (Exception e) {
            log.error(">>>>>>数据更新失败：", e);
            return Common.UPDATE_FAIL;
        }
    }

    /**
     * 更新前检查数据
     *
     * @param id      限定词内部编码
     * @param modDate 修订时间
     */
    private void checkDataExist(String id, String modDate) {
        // 检查时间格式
        try {
            DateUtil.parseDate(modDate, DateUtil.DEFAULT_PATTERN_DATETIME);
            // 检查该条记录的数据是否已经存在
            // 检查这个id对应的数据是否存在，如果不存在，说明该条记录在数据库中被删除，无法添加
            int tableCount = fieldDeterminerMapper.getCountByIdModDate(id, modDate);
            if (tableCount == 0) {
                throw new Exception(String.format("%s：内部标识符[%s]在数据库中不存在或该条记录已经被更新，请刷新页面", ErrorCodeEnum.CHECK_PARAMETER_ERROR, id));
            }
        } catch (Exception e) {
            log.error(">>>>>>格式错误：", e);
        }
    }

    @Override
    public String getDNameByChines(String chineseName) {
        try {
            // 中文转拼音
            String dName = PinYinUtil.getFirstSpell(chineseName);
            if (StringUtils.isBlank(dName)) {
                log.error(">>>>>>自动生成的限定词标示符为空");
                return Common.NULL;
            }
            // 然后根据拼音在数据库中查询相同拼音的有几个 获取最大的那个
            List<String> list = fieldDeterminerMapper.findAllDBNameList(dName);
            if (list == null || list.isEmpty()) {
                // 为空，直接用拼音首字母
                return dName;
            }
            Optional<String> maxOptional = list.stream().filter(d -> {
                if (StringUtils.isNotBlank(d)) {
                    String[] list1 = d.toLowerCase().trim().split(dName.toLowerCase(), -1);
                    return (StringUtils.isBlank(list1[1]) ||
                            StringUtils.isNumeric(list1[1])) && list1.length == 2;
                } else {
                    return false;
                }
            }).map(d -> {
                if (!StringUtils.equalsIgnoreCase(d, dName)) {
                    return d.toLowerCase().replaceAll(dName.toLowerCase(), "");
                } else {
                    return "0";
                }
            }).max(Comparator.comparingInt(Integer::parseInt));
            if (maxOptional.isPresent() && StringUtils.isNotBlank(maxOptional.get())) {
                int num = Integer.parseInt(maxOptional.get());
                if (num < 9) {
                    return (dName.toLowerCase() + "0" + (num + 1));
                } else {
                    return (dName.toLowerCase() + (num + 1));
                }
            } else {
                return dName;
            }
        } catch (Exception e) {
            log.error(">>>>>>从数据库中获取顺序报错：", e);
            return Common.NULL;
        }
    }

    @Override
    public String getDeterminerId() {
        // 找出数据库中 DQXXXXX，五位数字递增，可编辑。
        List<String> idList = fieldDeterminerMapper.getDeterminerIdList();
        if (idList == null || idList.isEmpty()) {
            return Common.COMMON_ID;
        }
        Optional<String> str = idList.stream().filter(d -> {
            if (StringUtils.isNotBlank(d) && StringUtils.containsIgnoreCase(d, Common.DQ)) {
                String[] list1 = d.toLowerCase().trim().split(Common.DQ, -1);
                return (StringUtils.isBlank(list1[1]) ||
                        StringUtils.isNumeric(list1[1])) && list1.length == 2;
            } else {
                return false;
            }
        }).map(d -> {
            if (!StringUtils.equalsIgnoreCase(d, Common.DQ)) {
                return d.toLowerCase().replaceAll(Common.DQ, "").trim();
            } else {
                return "0";
            }
        }).max(Comparator.comparingInt(Integer::parseInt));
        if (str.isPresent() && StringUtils.isNotBlank(str.get())) {
            int num = Integer.parseInt(str.get()) + 1;
            DecimalFormat format = new DecimalFormat("00000");
            return Common.DQ + format.format(num);
        } else {
            return Common.COMMON_ID;
        }
    }

    @Override
    public List<KeyValueVO> searchDeterminerNameList(String searchName) {
        List<KeyValueVO> list = new ArrayList<>();
        try {
            list = fieldDeterminerMapper.searchFieldDeterminerNameList(searchName);
            if (list == null || list.isEmpty()) {
                return new ArrayList<>();
            }
            list.sort((s1, s2) -> Collator.getInstance(java.util.Locale.CHINA).compare(s1.getLabel(), s2.getLabel()));
        } catch (Exception e) {
            log.error(">>>>>>获取限定词下拉列表信息报错：", e);
        }
        return list;
    }

}
