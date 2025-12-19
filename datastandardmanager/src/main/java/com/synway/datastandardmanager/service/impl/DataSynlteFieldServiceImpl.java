package com.synway.datastandardmanager.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageHelper;
import com.synway.common.exception.SystemException;
import com.synway.datastandardmanager.constants.Common;
import com.synway.datastandardmanager.entity.dto.DataSynlteFieldDTO;
import com.synway.datastandardmanager.entity.dto.SynlteFieldStatusDTO;
import com.synway.datastandardmanager.entity.pojo.*;
import com.synway.datastandardmanager.entity.vo.*;
import com.synway.datastandardmanager.enums.*;
import com.synway.datastandardmanager.listener.ExcelListener;
import com.synway.datastandardmanager.mapper.*;
import com.synway.datastandardmanager.service.DataSynlteFieldService;
import com.synway.datastandardmanager.util.*;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.Collator;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DataSynlteFieldServiceImpl implements DataSynlteFieldService {

    @Autowired()
    private Environment env;

    @Resource
    private SynlteFieldMapper synlteFieldMapper;

    @Resource
    private SynlteFieldHisMapper synlteFieldHisMapper;

    @Resource
    private SynlteFieldVersionMapper synlteFieldVersionMapper;

    @Resource
    private SameWordMapper sameWordMapper;

    @Resource
    private FieldCodeMapper fieldCodeMapper;

    @Resource
    private FieldCodeValMapper fieldCodeValMapper;

    @Resource
    private DsmAllCodeDataMapper allCodeDataMapper;

    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private DgnCommonSettingMapper dgnCommonSettingMapper;

    @Resource
    private OperateLogServiceImpl operateLogServiceImpl;


    @Override
    public PageVO<SynlteFieldEntity> querySynlteFieldList(DataSynlteFieldDTO dto) {
        log.info(">>>>>>查询数据元的请求参数为：{}", JSONObject.toJSONString(dto));
        int pageNum = null != dto.getPageIndex() && dto.getPageIndex() > 0 ? dto.getPageIndex() : 1;
        int pageSize = null != dto.getPageSize() && dto.getPageSize() > 0 ? dto.getPageSize() : 10;
        PageVO<SynlteFieldEntity> pageVO = new PageVO<>();
        pageVO.setPageNum(pageNum);
        pageVO.setPageSize(pageSize);

        PageHelper.startPage(pageNum, pageSize, true, true, false);
        List<SynlteFieldEntity> list = synlteFieldMapper.querySynlteFieldList(dto);

        // 需要对状态进行翻译
        if (list != null && !list.isEmpty()) {
            for (SynlteFieldEntity synlteField : list) {
                synlteField.setStatus(KeyStrEnum.getValueByKeyAndType(synlteField.getStatusNum(), Common.SYNLTEFIELDSTATUS));
                //回填数据分级中文
                if (StringUtils.isNotBlank(synlteField.getSecretClass())) {
                    synlteField.setSecretClassCh(KeyStrEnum.getValueByKeyAndType("2_" + synlteField.getSecretClass(), Common.DATASECURITYLEVEL));
                }
                if (synlteField.getFieldType() != null) {
                    synlteField.setFieldTypeCh(SynlteFieldTypeEnum.getSynlteFieldType(synlteField.getFieldType()));
                }
                if (StringUtils.isNotBlank(synlteField.getFieldClass())) {
                    synlteField.setFieldClassCh(SynlteFieldClassEnum.getValueById(synlteField.getFieldClass()));
                }
            }
        } else {
            return pageVO.emptyResult();
        }
        PageInfo<SynlteFieldEntity> pageInfo = new PageInfo<>(list);
        PageHelper.clearPage();
        pageVO.setPageNum(pageInfo.getPageNum());
        pageVO.setPageSize(pageInfo.getPageSize());
        pageVO.setTotal(pageInfo.getTotal());
        pageVO.setRows(pageInfo.getList());
        return pageVO;
    }

    @Override
    public SynlteFieldFilterVO getFilterObjectForSF() {
        log.info(">>>>>>查询数据元的表格筛选内容");
        SynlteFieldFilterVO data = new SynlteFieldFilterVO();
        List<ValueTextVO> list = synlteFieldMapper.getFilterObjectForSF();
        if (list == null || list.isEmpty()) {
            return data;
        }
        List<ValueTextVO> listStatus = new ArrayList<>();
        List<ValueTextVO> listFieldClass = new ArrayList<>();
        List<ValueTextVO> securityLevelList = new ArrayList<>();
        list.stream().forEach(d -> {
            if (StringUtils.equalsIgnoreCase(d.getText(), Common.STATUS)) {
                d.setValue(StringUtils.isBlank(d.getValue()) ? "" : d.getValue());
                d.setText(KeyStrEnum.getValueByKeyAndType(d.getValue(), Common.SYNLTEFIELDSTATUS));
                listStatus.add(d);
            }
            if (StringUtils.equalsIgnoreCase(d.getText(), Common.FIELDCLASS)) {
                d.setValue(StringUtils.isBlank(d.getValue()) ? "" : d.getValue());
                d.setText(SynlteFieldClassEnum.getValueById(d.getValue()));
                listFieldClass.add(d);
            }
            if (StringUtils.equalsIgnoreCase(d.getText(), Common.SECURITYLEVEL)) {
                d.setValue(StringUtils.isBlank(d.getValue()) ? "" : d.getValue());
                d.setText(KeyStrEnum.getValueByKeyAndType("2_" + d.getValue(), Common.DATASECURITYLEVEL));
                securityLevelList.add(d);
            }
        });
        data.setStatusFilter(listStatus);
        data.setFieldClassFilter(listFieldClass);
        data.setSecurityLevelFilter(securityLevelList);
        return data;
    }

    @Override
    public List<String> getSearchNameSuggest(String searchName) {
        log.info(">>>>>>查询数据元的提示框信息");
        // 如果这个为null 则只提示200条数据
        List<String> list = synlteFieldMapper.getSearchNameSuggest(searchName);
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        String finalSearchName = searchName;
        list = list.stream().filter(StringUtils::isNotBlank).sorted((s1, s2) -> Collator.getInstance(Locale.CHINA)
                        .compare(s2.replaceFirst(finalSearchName, ""), s1.replaceFirst(finalSearchName, "")))
                .limit(100).collect(Collectors.toList());
        return list;
    }

    @Override
    public String updateSynlteFieldStatus(SynlteFieldStatusDTO synlteFieldStatusDTO) {
        log.info(">>>>>>开始更新数据元状态");
        String status = synlteFieldStatusDTO.getStatus();
        List<String> list = synlteFieldStatusDTO.getFieldIdList();
        if (list == null || list.isEmpty()) {
            throw SystemException.asSystemException(ErrorCodeEnum.CHECK_PARAMETER_ERROR, "未选择需要发布的数据元信息");
        }
        // 版本发布日期，on_date：YYYYMMDD
        Date date;
        try {
            String todayStr = DateUtil.formatDateTime(new Date(), DateUtil.DEFAULT_PATTERN_DATE_SIMPLE);
            date = DateUtil.parseDate(todayStr, DateUtil.DEFAULT_PATTERN_DATE_SIMPLE);
        } catch (Exception e) {
            log.error("解析时间报错:", e);
            date = new Date();
        }
        list = list.stream().map(String::toUpperCase).collect(Collectors.toList());
        LambdaUpdateWrapper<SynlteFieldEntity> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.set(SynlteFieldEntity::getStatusNum, status)
                .set(SynlteFieldEntity::getOnDate, date)
                .in(SynlteFieldEntity::getFieldId, list);
        int updateNum = synlteFieldMapper.update(updateWrapper);
        log.info(">>>>>>更新synltefield状态的数据量为：{}", updateNum);
        return String.format("状态修改成功:%s条", updateNum);
    }

    @Override
    public String addSynlteField(SynlteFieldEntity synlteField) {
        log.info(">>>>>>添加新的数据元信息为:{}", JSONObject.toJSONString(synlteField));
        // 检查 数据是否正确
        checkAddData(synlteField.getFieldId(), synlteField.getColumnName(), synlteField.getSameId(), synlteField.getCodeId());
        try {
            dataBackFill(synlteField, new SynlteFieldEntity());
            LambdaQueryWrapper<SynlteFieldEntity> queryWrapper = Wrappers.lambdaQuery();
            queryWrapper.eq(SynlteFieldEntity::getFieldId, synlteField.getFieldId())
                    .or().eq(SynlteFieldEntity::getColumnName, synlteField.getColumnName());

            if (synlteFieldMapper.selectCount(queryWrapper) <= 0) {
                log.info(">>>>>>新增的数据元数据为：{}", synlteField);
                int num = synlteFieldMapper.insert(synlteField);
                log.info(">>>>>>插入synlte.SYNLTEFIELD表中数据量为：{}", num);
                if (num == 0) {
                    throw SystemException.asSystemException(ErrorCodeEnum.CHECK_ERROR, "插入失败,[内部标识符]或[数据库字段名称]重复,请刷新后重新插入");
                }
            // 发送操作日志
            operateLogServiceImpl.synlteFieldSuccessLog(OperateLogHandleTypeEnum.ADD, "数据元管理", synlteField);
            }
        } catch (Exception e) {
            log.error("新增数据元失败：", e);
            return Common.ADD_FAIL;
        }
        return Common.ADD_SUCCESS;
    }

    @Override
    public String updateSynlteField(SynlteFieldEntity synlteField) {
        log.info(">>>>>>对数据元信息进行编辑：", JSONObject.toJSONString(synlteField));
        // 检查一些码表值是否正确
        checkUpdateData(synlteField.getFieldId(), synlteField.getUpdateTime(), synlteField.getSameId(), synlteField.getCodeId());
        // 判断是否修改数据元属性值，未做任何改动，直接返回，不更新数据库
        LambdaQueryWrapper<SynlteFieldEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SynlteFieldEntity::getFieldId, synlteField.getFieldId());
        SynlteFieldEntity synlteFieldOld = synlteFieldMapper.selectOne(wrapper);
        if (synlteFieldOld != null) {
            synlteFieldOld.setStatus(KeyStrEnum.getValueByKeyAndType(synlteFieldOld.getStatusNum(), Common.SYNLTEFIELDSTATUS));
        }
        if (synlteField.equals(synlteFieldOld)) {
            return Common.NOT_CHANGED;
        }

        try {
            dataBackFill(synlteField, synlteFieldOld);
            // 相关事件进行修改
            int num = synlteFieldMapper.updateSynlteField(synlteField);
            log.info(">>>>>>更新synlte.SYNLTEFIELD表中数据量为：{}", num);
            if (num == 0) {
                throw SystemException.asSystemException(ErrorCodeEnum.CHECK_ERROR, "更新失败，请刷新后重新更改数据");
            }
            //生成数据元版本信息
            if ("05".equals(synlteFieldOld.getStatusNum())) {
                log.info(">>>>>>开始生成数据元版本信息");
                SynlteFieldVersionEntity synlteFieldVersion = new SynlteFieldVersionEntity();
                String uuid = UUIDUtil.getUUID();
                synlteFieldVersion.setFieldIdVersion(uuid);
                synlteFieldVersion.setFieldId(synlteFieldOld.getFieldId());
                synlteFieldVersion.setColumnName(synlteFieldOld.getColumnName());
                // 添加被修改的属性
                synlteFieldVersion.setMemo(synlteFieldChange(synlteField, synlteFieldOld));
                String todayStr = DateUtil.formatDateTime(new Date(), DateUtil.DEFAULT_PATTERN_DATE_SIMPLE);
                synlteFieldVersion.setVersion(Integer.valueOf(todayStr));
                synlteFieldVersion.setVersions(synlteField.getVersions());
                synlteFieldVersion.setAuthor(StringUtils.isBlank(synlteField.getUpdater()) ? "admin" : synlteField.getUpdater());
                Date date = DateUtil.parseDateTime(DateUtil.formatDateTime(new Date()));
                synlteFieldVersion.setUpdateTime(date);
                synlteFieldVersionMapper.insert(synlteFieldVersion);
                log.info(">>>>>>数据元版本信息插入成功");

                // 原有记录信息保存至历史版本信息表SYNLTE.SYNLTEFIELD_history中，并且数据元状态改为废止（07）
                synlteFieldOld.setUpdateTime(date);
                synlteFieldOld.setReleaseDate(Integer.valueOf(todayStr));
                SynlteFieldHisEntity synlteFieldHis = buildSynlteFieldHisObj(synlteFieldOld);
                synlteFieldHisMapper.insert(synlteFieldHis);
                log.info(">>>>>>数据元历史信息插入成功");
            }
            // 发送操作日志
            operateLogServiceImpl.synlteFieldSuccessLog(OperateLogHandleTypeEnum.ALTER, "数据元管理", synlteField);
        } catch (ParseException e) {
            log.error("更新数据元失败：", e);
            throw new RuntimeException(e);
        }
        return Common.UPDATE_SUCCESS;
    }

    private SynlteFieldHisEntity buildSynlteFieldHisObj(SynlteFieldEntity synlteFieldOld) {
        SynlteFieldHisEntity synlteFieldHis = new SynlteFieldHisEntity();
        synlteFieldHis.setFieldId(synlteFieldOld.getFieldId());
        synlteFieldHis.setFieldName(synlteFieldOld.getFieldName());
        synlteFieldHis.setFieldChineseName(synlteFieldOld.getFieldChineseName());
        synlteFieldHis.setFieldDescribe(synlteFieldOld.getFieldDescribe());
        synlteFieldHis.setFieldType(synlteFieldOld.getFieldType());
        synlteFieldHis.setFieldLen(synlteFieldOld.getFieldLen().toString());
        synlteFieldHis.setDefaultValue(synlteFieldOld.getDefaultValue());
        synlteFieldHis.setMemo(synlteFieldOld.getMemo());
        synlteFieldHis.setColumnName(synlteFieldOld.getColumnName());
        synlteFieldHis.setCodeId(synlteFieldOld.getCodeId());
        synlteFieldHis.setSameId(synlteFieldOld.getSameId());
        synlteFieldHis.setIs530New(synlteFieldOld.getIs530New());
        synlteFieldHis.setVersions(synlteFieldOld.getVersions());
        synlteFieldHis.setReleaseDate(synlteFieldOld.getReleaseDate());
        synlteFieldHis.setFullChinsee(synlteFieldOld.getFullChinese());
        synlteFieldHis.setSimChinese(synlteFieldOld.getSimChinese());
        synlteFieldHis.setSynonyName(synlteFieldOld.getSynonyName());
        synlteFieldHis.setObjectType(synlteFieldOld.getObjectType());
        synlteFieldHis.setExpressionWord(synlteFieldOld.getExpressionWord());
        synlteFieldHis.setCodeIdDetail(synlteFieldOld.getCodeIdDetail());
        synlteFieldHis.setIsNormal(synlteFieldOld.getIsNorMal() == null ? 0 : synlteFieldOld.getIsNorMal());
        synlteFieldHis.setRelate(synlteFieldOld.getRelate());
        synlteFieldHis.setUnit(synlteFieldOld.getUnit());
        synlteFieldHis.setFuseType(synlteFieldOld.getFuseType());
        synlteFieldHis.setFuseFieldId(synlteFieldOld.getFuseFieldId());
        synlteFieldHis.setStatus(KeyStrEnum.getValueByKeyAndType("07", Common.SYNLTEFIELDSTATUS));
        synlteFieldHis.setSubOrg(synlteFieldOld.getSubOrg());
        synlteFieldHis.setSubAuthor(synlteFieldOld.getSubAuthor());
        synlteFieldHis.setOnDate(synlteFieldOld.getOnDate());
        synlteFieldHis.setRegOrg(synlteFieldOld.getRegOrg());
        synlteFieldHis.setFieldClass(synlteFieldOld.getFieldClass());
        synlteFieldHis.setSecretClass(synlteFieldOld.getSecretClass());
        synlteFieldHis.setFieldStandard(synlteFieldOld.getFieldStandard());
        synlteFieldHis.setFacturer(synlteFieldOld.getFacturer());
        synlteFieldHis.setContext(synlteFieldOld.getContext());
        synlteFieldHis.setConstraint(synlteFieldOld.getConstraint());
        synlteFieldHis.setCreateTime(new Date());
        synlteFieldHis.setUpdateTime(synlteFieldOld.getUpdateTime());
        synlteFieldHis.setCreator(synlteFieldOld.getCreator());
        synlteFieldHis.setUpdater(synlteFieldOld.getUpdater());
        synlteFieldHis.setFieldIdVersion(synlteFieldOld.getFieldId());

        return synlteFieldHis;
    }

    // 修改的属性
    private String synlteFieldChange(SynlteFieldEntity synlteField, SynlteFieldEntity synlteFieldOld) {
        StringBuffer stringBuffer = new StringBuffer();
        if (!synlteField.getFieldChineseName().equals(synlteFieldOld.getFieldChineseName())) {
            stringBuffer.append("中文名称属性、");
        }
        if (!synlteField.getFieldName().equals(synlteFieldOld.getFieldName())) {
            stringBuffer.append("英文名称属性、");
        }
        if (!(synlteField.getSimChinese().equals(synlteFieldOld.getSimChinese()))) {
            stringBuffer.append("标识符属性、");
        }
        if (StringUtils.isNotBlank(synlteField.getObjectType())){
            if (!(synlteField.getObjectType().equals(synlteFieldOld.getObjectType()))) {
                stringBuffer.append("对象类词属性、");
            }
        }
        if (StringUtils.isNotBlank(synlteField.getSameId())){
            if (!(synlteField.getSameId().equals(synlteFieldOld.getSameId()))) {
                stringBuffer.append("语义类型属性、");
            }
        }
        if (StringUtils.isNotBlank(synlteField.getAttributeWord())){
            if (!(synlteField.getAttributeWord().equalsIgnoreCase(synlteFieldOld.getAttributeWord()))) {
                stringBuffer.append("特性词属性、");
            }
        }
        if (!(synlteField.getRelate().equals(synlteFieldOld.getRelate()))) {
            stringBuffer.append("关系属性、");
        }
        if (StringUtils.isNotBlank(synlteField.getExpressionWord())){
            if (!(synlteField.getExpressionWord().equals(synlteFieldOld.getExpressionWord()))) {
                stringBuffer.append("表示词属性、");
            }
        }
        if (StringUtils.isNotBlank(synlteField.getUnit())){
            if (!(synlteField.getUnit().equals(synlteFieldOld.getUnit()))) {
                stringBuffer.append("计量单位属性、");
            }
        }
        if (StringUtils.isNotBlank(synlteField.getCodeIdDetail())){
            if (!(synlteField.getCodeIdDetail().equals(synlteFieldOld.getCodeIdDetail()))) {
                stringBuffer.append("值域描述属性、");
            }
        }
        if (!(synlteField.getSubOrg().equals(synlteFieldOld.getSubOrg()))) {
            stringBuffer.append("提交机构属性、");
        }
        if (!(synlteField.getStatusNum().equals(synlteFieldOld.getStatusNum()))) {
            stringBuffer.append("状态属性、");
        }
        if (!(synlteField.getFuseType().equals(synlteFieldOld.getFuseType()))) {
            stringBuffer.append("融合单位类型属性、");
        }
        if (!(synlteField.getVersions().equals(synlteFieldOld.getVersions()))) {
            stringBuffer.append("大版本属性、");
        }
        String resultStr = "";
        StringBuffer constantStr;
        if (stringBuffer.length() != 0) {
            constantStr = new StringBuffer("修改了");
            constantStr.append(stringBuffer);
            if (constantStr.indexOf("、", constantStr.length() - 1) != -1) {
                resultStr = constantStr.substring(0, constantStr.length() - 1);
            }
        } else {
            constantStr = new StringBuffer("未修改内容");
            resultStr = constantStr.toString();
        }
        return resultStr;
    }

    /**
     * 编辑时的数据检查
     *
     * @param fieldId
     * @param updateTime
     * @param sameId
     * @param codeId
     */
    private void checkUpdateData(String fieldId, Date updateTime, String sameId, String codeId) {
        // 1： 先验证 fieldId是否存在，如果不存在说明被删除
        LambdaQueryWrapper<SynlteFieldEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SynlteFieldEntity::getFieldId, fieldId).and(wrapper -> wrapper
                .eq(SynlteFieldEntity::getDeleted, 0)
                .or()
                .isNull(SynlteFieldEntity::getDeleted)
        );
        List<SynlteFieldEntity> fieldEntities = synlteFieldMapper.selectList(queryWrapper);
        if (fieldEntities.size() <= 0) {
            throw SystemException.asSystemException(ErrorCodeEnum.CHECK_ERROR,
                    String.format("内部标识符[%s]在数据库中不存在，更新数据失败", fieldId));
        }
        if (fieldEntities.size() > 1) {
            throw SystemException.asSystemException(ErrorCodeEnum.CHECK_ERROR,
                    String.format("内部标识符[%s]在数据库存在多个值，更新数据失败", fieldId));
        }
        if (fieldEntities.get(0).getUpdateTime() == null && updateTime == null) {
            log.info(">>>>>>数据库中的时间和页面传递的时间都为null，验证通过");
            return;
        }
        // 2：验证更新时间 UPDATETIME 是否相同 不相同说明已经被别人更新，本次更新失败
        try {
            Date dbDate = fieldEntities.get(0).getUpdateTime();
            if (dbDate.compareTo(updateTime) != 0) {
                throw SystemException.asSystemException(ErrorCodeEnum.CHECK_ERROR,
                        String.format("内部标识符[%s]在[%s]时被其它页面更新，本次更新失败", fieldId, dbDate));
            }
        } catch (Exception e) {
            log.error("数据检查失败：", e);
        }
        // 验证数据语义id
        if (StringUtils.isNotBlank(sameId)) {
            LambdaQueryWrapper<SameWordEntity> queryWrapper1 = Wrappers.lambdaQuery();
            queryWrapper1.eq(SameWordEntity::getSameId, sameId);
            if (sameWordMapper.selectCount(queryWrapper1) == 0) {
                throw SystemException.asSystemException(ErrorCodeEnum.CHECK_ERROR,
                        String.format("sameId的值[%s]在数据库表synlte.sameword不存在,更新失败", sameId));
            }
        }
        //  验证codeId 是否符合规范
        if (StringUtils.isNotBlank(codeId)) {
            if (countByCodeId(codeId) == 0) {
                throw SystemException.asSystemException(ErrorCodeEnum.CHECK_ERROR,
                        String.format("codeId的值[%s]在数据库表synlte.FIELDCODE不存在,更新失败", codeId));
            }
        }

    }

    // 检查新增数据
    private void checkAddData(String fieldId, String columnName, String sameId, String codeId) {
        // 检查 fieldId 和 columnName 是否已经存在，如果存在，则报错
        LambdaQueryWrapper<SynlteFieldEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SynlteFieldEntity::getFieldId, fieldId).and(wrapper -> wrapper
                .eq(SynlteFieldEntity::getDeleted, 0)
                .or()
                .isNull(SynlteFieldEntity::getDeleted)
        );
        if (synlteFieldMapper.selectCount(queryWrapper) > 0) {
            throw SystemException.asSystemException(ErrorCodeEnum.CHECK_ERROR,
                    String.format("fieldId的值[%s]在数据库表synlte.SYNLTEFIELD中已经存在，请重新填写该值", fieldId));
        }
        queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SynlteFieldEntity::getColumnName, columnName);
        if (synlteFieldMapper.selectCount(queryWrapper) > 0) {
            throw SystemException.asSystemException(ErrorCodeEnum.CHECK_ERROR,
                    String.format("columnName的值[%s]在数据库表synlte.SYNLTEFIELD中已经存在，请重新填写该值", columnName));
        }
        // 检查特性词
        if (StringUtils.isNotBlank(sameId)) {
            LambdaQueryWrapper<SameWordEntity> queryWrapper1 = Wrappers.lambdaQuery();
            queryWrapper1.eq(SameWordEntity::getSameId, sameId);
            if (sameWordMapper.selectCount(queryWrapper1) == 0) {
                throw SystemException.asSystemException(ErrorCodeEnum.CHECK_ERROR,
                        String.format("sameId的值[%s]在数据库表synlte.sameword不存在,新增失败", sameId));
            }
        }
        //  验证codeId 是否符合规范
        if (StringUtils.isNotBlank(codeId)) {
            if (countByCodeId(codeId) == 0) {
                throw SystemException.asSystemException(ErrorCodeEnum.CHECK_ERROR,
                        String.format("codeId的值[%s]在数据库表synlte.FIELDCODE不存在,新增失败", codeId));
            }
        }
    }

    public long countByCodeId(String codeId) {
        LambdaQueryWrapper<FieldCodeEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(FieldCodeEntity::getCodeId, codeId);
        return fieldCodeMapper.selectCount(queryWrapper);
    }

    /**
     * 赋值
     *
     * @param synlteField    页面上传递的数据元信息
     * @param oldSynltefield 数据库中的数据元信息
     */
    private void dataBackFill(SynlteFieldEntity synlteField, SynlteFieldEntity oldSynltefield) {
        try {
            // 获取限定词：如果是编辑并且数据库中的限定词和前端传递过来的限定词相同 则不需要更新
            if (oldSynltefield == null || !StringUtils.equalsIgnoreCase(oldSynltefield.getSimChinese(), synlteField.getSimChinese())) {
                String simChinese = getSimChinese(synlteField.getFieldChineseName(), null);
                synlteField.setSimChinese(simChinese);
            }
            // 批准时间 YYYYMMDD
            Date nowDate = DateUtil.parseDateTime(DateUtil.formatDateTime(new Date()));
            String nowDateStr = DateUtil.formatDateTime(new Date(), DateUtil.DEFAULT_PATTERN_DATE_SIMPLE);
            synlteField.setReleaseDate(Integer.valueOf(nowDateStr));
            synlteField.setOnDate(nowDate);
            // 中文全拼
            String fullChinese = PinYinUtil.getPySpell(synlteField.getFieldChineseName());
            synlteField.setFullChinese(StringUtils.isBlank(fullChinese) ? "" : StringUtils.substring(fullChinese, 0, 200));
            // 大版本 从版本管理中读取
            String version = dgnCommonSettingMapper.searchVersion();
            if (version != null) {
                JSONObject parse = (JSONObject) JSON.parse(version);
                String versions = (String) parse.get("synlteFieldVersions");
                synlteField.setVersions(versions);
            }
            oldSynltefield.setFieldClassCh(synlteField.getFieldClassCh());
            oldSynltefield.setFieldChineseName(synlteField.getFieldChineseName());
            oldSynltefield.setFullChinese(synlteField.getFullChinese());
            oldSynltefield.setSimChinese(synlteField.getSimChinese());
            oldSynltefield.setSubOrg(synlteField.getSubOrg());
        } catch (Exception e) {
            log.error("数据元赋值失败:", e);
        }
    }

    /**
     * 获取语义类型的相关下拉框信息
     *
     * @param searchName
     * @return
     */
    @Override
    public List<ValueLabelVO> getSameWordList(String searchName) {
        List<ValueLabelVO> list = sameWordMapper.getSameWordList(searchName);
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        list.sort((s1, s2) -> Collator.getInstance(java.util.Locale.CHINA).compare(s1.getLabel(), s2.getLabel()));
        return list;
    }

    /**
     * 标识符是在数据应用中对数据元的统一标识。标识符由该数据元中文名称中每个汉字的汉语拼音首字母(不区分大小写)组成
     * 不同数据元的标识符若出现重复，则在后面加01～99予以区别
     *
     * @param name 中文名称
     * @return String
     */
    @Override
    public String getSimChinese(String name, List<String> listOld) {
        try {
            // 中文转拼音
            String dName = PinYinUtil.getFirstSpell(name);
            if (StringUtils.isBlank(dName)) {
                return dName;
            }
            // 然后根据拼音在数据库中查询相同拼音的有几个 获取最大的那个
            List<String> list;
            if (listOld == null || listOld.isEmpty()) {
                LambdaQueryWrapper<SynlteFieldEntity> queryWrapper = Wrappers.lambdaQuery();
                queryWrapper.like(SynlteFieldEntity::getSimChinese, name);
                List<SynlteFieldEntity> synlteFieldEntities = synlteFieldMapper.selectList(queryWrapper);
                list = synlteFieldEntities.stream().map(synlteField -> synlteField.getSimChinese()).distinct().collect(Collectors.toList());
            } else {
                list = listOld.stream().filter(d -> StringUtils.startsWithIgnoreCase(d, dName)).collect(Collectors.toList());
            }
            // 为空，直接用拼音首字母
            if (list == null || list.isEmpty()) {
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
            log.error("从数据库中获取顺序报错：", e);
            throw SystemException.asSystemException(ErrorCodeEnum.CHECK_PARAMETER_ERROR, "从数据库中获取顺序报错：" + e.getMessage());
        }
    }

    @Override
    public List<ValueTextVO> getSelectObjectByName(String searchName) {
        return allCodeDataMapper.getSelectObjectByName(searchName);
    }

    @Override
    public List<ValueLabelVO> searchDataSecurityLevel() {
        log.info(">>>>>>开始查询数据安全分级列表");
        List<ValueLabelVO> securityLevelList = fieldCodeValMapper.queryLabelValueByCodeId("GACODE000415");
        if (securityLevelList.size() == 0 || securityLevelList.isEmpty()) {
            throw SystemException.asSystemException(ErrorCodeEnum.CHECK_ERROR, "数据安全分类查询失败");
        }
        return securityLevelList;
    }

    @Override
    public SynlteFieldEntity searchSynlteFieldById(String fieldId) {
        log.info(">>>>>>开始查询id为:{}的数据元信息", fieldId);
        if (StringUtils.isBlank(fieldId)){
            return new SynlteFieldEntity();
        }
        LambdaQueryWrapper<SynlteFieldEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SynlteFieldEntity::getFieldId, fieldId);
        return synlteFieldMapper.selectOne(wrapper);
    }

    @Override
    public List<ValueLabelVO> getGadsjFieldByText(String searchText, String fieldType) {
        log.info(">>>>>>查询数据元下拉框的参数为:{}", searchText);
        if (StringUtils.isNotBlank(fieldType) && fieldType.indexOf(",") != -1) {
            fieldType = null;
        }
        List<ValueLabelVO> resultList = synlteFieldMapper.getGadsjFieldByText(searchText, fieldType, null);
        if (resultList == null || resultList.isEmpty()) {
            return new ArrayList<>();
        }
        resultList = resultList.stream().sorted((s1, s2) -> Collator.getInstance(Locale.CHINA)
                        .compare(s2.getLabel(), s1.getLabel()))
                .limit(100).collect(Collectors.toList());
        return resultList;
    }

    @Override
    public void downloadSynlteFieldExcel(HttpServletResponse response, DataSynlteFieldDTO dto, String name, Object object) {
        log.info(">>>>>>开始下载数据元的相关信息");
        try {
            List<SynlteFieldEntity> synlteFieldEntities = dto.getSynlteFieldObjectList();
            if (synlteFieldEntities == null || synlteFieldEntities.isEmpty() || synlteFieldEntities.size() == 0) {
                //查询数据库里的数据元
                synlteFieldEntities = synlteFieldMapper.querySynlteFieldList(dto);
            }
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode(name, "UTF-8") + ".xlsx";
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName);
            EasyExcel.write(response.getOutputStream(), object.getClass()).autoCloseStream(Boolean.FALSE)
                    .sheet("标准数据元").doWrite(synlteFieldEntities);
        } catch (Exception e) {
            log.error(">>>>>>下载数据元报错:", e);
        }
        log.info(">>>>>>下载数据元的相关信息结束");
    }

    @Override
    public String importSynlteFieldExcel(MultipartFile file) {
        try {
            log.info(">>>>>>开始将文件[{}]导入到数据库中...", file.getName());
            ExcelListener<SynlteFieldEntity> listener = new ExcelListener<>();
            ArrayList<SynlteFieldEntity> failedList = new ArrayList<>();
            ArrayList<SynlteFieldEntity> successList = new ArrayList<>();
            ArrayList<SynlteFieldEntity> versionList = new ArrayList<>();

            List<SynlteFieldEntity> list = EasyExcelUtil.readExcelUtil(file, new SynlteFieldEntity(), listener);
            // 这里插入每一行数据，需要验证必填项是否为空，fieldId是否唯一
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                SynlteFieldEntity synlteFieldExcel = (SynlteFieldEntity) iterator.next();
                if (checkSynlteFieldRule(synlteFieldExcel)) {
                    //验证格式
                    boolean checkFlag = ValidatorUtil.checkObjectValidator(synlteFieldExcel);
                    if (!checkFlag) {
                        failedList.add(synlteFieldExcel);
                        throw new NullPointerException("导入文件报错");
                    }
                    //通过sameId回填同义词名称
                    if (StringUtils.isNotBlank(synlteFieldExcel.getSameId())) {
                        SameWordEntity sameWord = sameWordMapper.selectById(synlteFieldExcel.getSameId());
                        synlteFieldExcel.setSynonyName(sameWord.getWordname());
                    }
                    //通过codeId回填值域信息描述
                    if (StringUtils.isNotBlank(synlteFieldExcel.getCodeId())) {
                        synlteFieldExcel.setCodeIdDetail(fieldCodeMapper.searchFieldCodeByCodeId(synlteFieldExcel.getCodeId()));
                    }
                    LambdaQueryWrapper<SynlteFieldEntity> queryWrapper = Wrappers.lambdaQuery();
                    queryWrapper.eq(SynlteFieldEntity::getFieldId, synlteFieldExcel.getFieldId());
                    queryWrapper.eq(SynlteFieldEntity::getColumnName, synlteFieldExcel.getColumnName());
                    SynlteFieldEntity synlteFieldOld = synlteFieldMapper.selectOne(queryWrapper);
                    if (synlteFieldOld != null && synlteFieldOld.getFieldId() != null) {
                        // 数据元已存在，比较版本日期，新数据元小于等于旧数据元版本日期不处理，大于的进行更新
                        Integer oldReleaseDate = synlteFieldOld.getReleaseDate();
                        if (oldReleaseDate != null) {
                            if (oldReleaseDate >= synlteFieldExcel.getReleaseDate()) {
                                versionList.add(synlteFieldExcel);
                                iterator.remove();
                            } else {
                                //将旧数据插入到历史库中
                                log.info(">>>>>>数据元历史信息开始生成");
                                String todayStr = DateUtil.formatDateTime(new Date(), DateUtil.DEFAULT_PATTERN_DATE_SIMPLE);
                                Date date = DateUtil.parseDateTime(DateUtil.formatDateTime(new Date()));
                                synlteFieldOld.setStatusNum("07");
                                synlteFieldOld.setStatus(KeyStrEnum.getValueByKeyAndType(synlteFieldOld.getStatusNum(), Common.SYNLTEFIELDSTATUS));
                                synlteFieldOld.setUpdateTime(date);
                                synlteFieldOld.setReleaseDate(Integer.valueOf(todayStr));
                                SynlteFieldHisEntity synlteFieldHis = buildSynlteFieldHisObj(synlteFieldOld);
                                synlteFieldHisMapper.insert(synlteFieldHis);
                                log.info(">>>>>>数据元历史信息插入成功");

                                //更新数据元
                                SynlteFieldEntity updateSynlteField = new SynlteFieldEntity();
                                ConvertUtils.register(new DateConverter(null), java.util.Date.class);
                                BeanUtils.copyProperties(updateSynlteField, synlteFieldExcel);
                                dataBackFill(updateSynlteField, null);
                                int updateCount = synlteFieldMapper.updateSynlteField(updateSynlteField);
                                if (updateCount == 1) {
                                    iterator.remove();
                                    successList.add(synlteFieldExcel);
                                    log.info(">>>>>>数据元更新成功");
                                } else {
                                    failedList.add(synlteFieldExcel);
                                    log.error(">>>>>>数据元更新失败");
                                }
                            }
                        }
                    } else {
                        // 不存在直接插入
                        dataBackFill(synlteFieldExcel, null);
                        int addFlag = synlteFieldMapper.insert(synlteFieldExcel);
                        log.info(">>>>>>数据元插入成功");
                        if (addFlag == 0) {
                            failedList.add(synlteFieldExcel);
                            throw SystemException.asSystemException(ErrorCodeEnum.CHECK_ERROR, "插入失败,[内部标识符]或[数据库字段名称]重复,请刷新后重新插入");
                        }
                        if (addFlag == 1) {
                            iterator.remove();
                            successList.add(synlteFieldExcel);
                        } else {
                            log.error(">>>>>>数据元插入失败");
                            failedList.add(synlteFieldExcel);
                        }
                    }
                } else {
                    failedList.add(synlteFieldExcel);
                }
            }
            return String.format("文件处理完成，读取记录数：%d，入库成功记录数：%d，忽略低版本记录：%d，入库失败记录：%d", list.size(), successList.size(), versionList.size(), failedList.size());
        } catch (Exception e) {
            log.error("导入文件报错:", e);
            throw new NullPointerException("导入文件报错");
        }
    }

    private boolean checkSynlteFieldRule(SynlteFieldEntity synlteFieldExcel) {
        //判断必填项是否为空
        if (StringUtils.isBlank(synlteFieldExcel.getFieldId()) && StringUtils.isBlank(synlteFieldExcel.getFieldName())
                && StringUtils.isBlank(synlteFieldExcel.getFieldChineseName())
                && synlteFieldExcel.getFieldLen() == null && StringUtils.isBlank(synlteFieldExcel.getStatusNum())
                && StringUtils.isBlank(synlteFieldExcel.getGadsjFieldId()) && StringUtils.isBlank(synlteFieldExcel.getColumnName())
                && StringUtils.isBlank(synlteFieldExcel.getFullChinese()) && StringUtils.isBlank(synlteFieldExcel.getVersions())
                && StringUtils.isBlank(String.valueOf(synlteFieldExcel.getReleaseDate())) && StringUtils.isBlank(synlteFieldExcel.getFieldClass())) {
            return false;
        }
        return true;
    }

    @Override
    public String clearImportSynlteFieldExcel(MultipartFile file) {
        try {
            log.info(">>>>>>清空所有数据元之后导入数据...");
            //先将所有数据元导入到版本库中，再清空导入
            LambdaQueryWrapper<SynlteFieldEntity> queryWrapper = Wrappers.lambdaQuery();
            queryWrapper.eq(SynlteFieldEntity::getStatus, "05");
            List<SynlteFieldEntity> synlteFieldEntities = synlteFieldMapper.selectList(queryWrapper);
            log.info(">>>>>>查出旧的数据元共有{}条", synlteFieldEntities.size());
            // 时间 YYYYMMDD
            String todayStr = DateUtil.formatDateTime(new Date(), DateUtil.DEFAULT_PATTERN_DATE_SIMPLE);
            Date date = DateUtil.parseDateTime(DateUtil.formatDateTime(new Date()));

            //回填数据元历史数据
            for (SynlteFieldEntity data : synlteFieldEntities) {
                data.setStatusNum("07");
                data.setStatus(KeyStrEnum.getValueByKeyAndType(data.getStatusNum(), Common.SYNLTEFIELDSTATUS));
                data.setUpdateTime(date);
                data.setReleaseDate(Integer.valueOf(todayStr));
                SynlteFieldHisEntity synlteFieldHis = buildSynlteFieldHisObj(data);
                synlteFieldHisMapper.insert(synlteFieldHis);
            }
//            int deleteCount = synlteFieldMapper.delete(queryWrapper);
//            log.info(">>>>>>清空数据元的数据量为:{}",deleteCount);
            // 开始导入数据元
            log.info(">>>>>>开始将文件[{}]导入到数据库中", file.getName());
            ExcelListener<SynlteFieldEntity> listener = new ExcelListener<>();
            List<SynlteFieldEntity> list = new ArrayList<>();
            List<SynlteFieldEntity> failedList = new ArrayList<>();
            //开始导入数据
            list = EasyExcelUtil.readExcelUtil(file, new SynlteFieldEntity(), listener);
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                SynlteFieldEntity synlteField = (SynlteFieldEntity) iterator.next();
                //这里导入的每一条数据都要验证必填项是否为空
                if (checkSynlteFieldRule(synlteField)) {
                    int addFlag = synlteFieldMapper.insert(synlteField);
                    if (addFlag == 1) {
                        iterator.remove();
                    } else {
                        log.error(">>>>>>数据元插入失败");
                        failedList.add(synlteField);
                    }
                } else {
                    failedList.add(synlteField);
                }
            }
            log.info(String.format(">>>>>>导入成功的数量为：%d，导入失败的数量为：", list.size(), failedList.size()));
            return Common.IMPORT_SUCCESS;
        } catch (Exception e) {
            log.error(">>>>>>导入文件报错:", e);
            throw new NullPointerException("导入文件报错");
        }
    }

    @Override
    public void downloadSynlteFieldSql(DataSynlteFieldDTO dto, HttpServletResponse response, String fileName) {
        log.info(">>>>>>开始导出数据元sql文件");
        List<SynlteFieldEntity> synlteFieldEntities = new ArrayList<SynlteFieldEntity>();
        //设置响应信息
        setServletResponse(response, fileName);

        BufferedOutputStream buff = null;
        ServletOutputStream outStr = null;
        //判断是导出页面勾选的数据还是库中全部数据
        if (dto.getSynlteFieldObjectList().size() != 0) {
            //页面选中的数据元导出
            synlteFieldEntities = dto.getSynlteFieldObjectList();
        } else {
            //查询数据库里的数据元
            synlteFieldEntities = synlteFieldMapper.querySynlteFieldList(dto);
        }
        // 拼接sql
        StringBuffer sql = new StringBuffer();
        for (SynlteFieldEntity data : synlteFieldEntities) {
            sql.append(jointSql(data));
        }
        log.info(">>>>>>sql语句为:{}", sql);
        try {
            outStr = response.getOutputStream();
            buff = new BufferedOutputStream(outStr);
            buff.write(sql.toString().getBytes("UTF-8"));
            buff.flush();
        } catch (Exception e) {
            log.error(">>>>>>下载数据元sql信息出错:", e);
        } finally {
            try {
                buff.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                outStr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private HttpServletResponse setServletResponse(HttpServletResponse response, String fileName) {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName);
        return response;
    }

    private StringBuffer jointSql(SynlteFieldEntity data) {
        String databaseType = env.getProperty("database.type");
        StringBuffer baseSql = new StringBuffer();
        if (Common.DAMENG.equalsIgnoreCase(databaseType)) {
            baseSql = new StringBuffer("insert into synlte.synltefield(FIELDID,FIELDNAME,FIELDCHINESENAME,FIELDDESCRIBE," +
                    "FIELDTYPE,FIELDLEN,DEFAULTVALUE,MEMO,COLUMNNAME,CODEID,SAMEID,IS530NEW,FIELDLEN2,FIELD_CLASS," +
                    "SECRET_CLASS,SIM_CHINESE,\"VERSIONS\",RELEASEDATE,SYNONY_NAME,OBJECT_TYPE,EXPRESSION_WORD,CODEID_DETAIL," +
                    "ISNORMAL,RELATE,UNIT,FUSE_TYPE,FUSE_FIELDID,SUB_ORG,SUB_AUTHOR,ON_DATE,REG_ORG,FIELD_STANDARD,FACTURER," +
                    "\"CONTEXT\",\"CONSTRAINT\",CREATETIME,UPDATETIME,CREATOR,UPDATER,STATUS,FULL_CHINSEE,ATTRIBUTE_WORD,GADSJ_FIELDID)\n" +
                    "values('");
        } else {
            baseSql = new StringBuffer("insert into synlte.synltefield(FIELDID,FIELDNAME,FIELDCHINESENAME,FIELDDESCRIBE," +
                    "FIELDTYPE,FIELDLEN,DEFAULTVALUE,MEMO,COLUMNNAME,CODEID,SAMEID,IS530NEW,FIELDLEN2,FIELD_CLASS," +
                    "SECRET_CLASS,SIM_CHINESE,VERSIONS,RELEASEDATE,SYNONY_NAME,OBJECT_TYPE,EXPRESSION_WORD,CODEID_DETAIL," +
                    "ISNORMAL,RELATE,UNIT,FUSE_TYPE,FUSE_FIELDID,SUB_ORG,SUB_AUTHOR,ON_DATE,REG_ORG,FIELD_STANDARD,FACTURER," +
                    "CONTEXT,CONSTRAINT,CREATETIME,UPDATETIME,CREATOR,UPDATER,STATUS,FULL_CHINSEE,ATTRIBUTE_WORD,GADSJ_FIELDID)\n" +
                    "values('");
        }
        baseSql.append(data.getFieldId() + "','" + data.getFieldName() + "','" + data.getFieldChineseName() + "','");
        baseSql.append(StringUtils.isNotBlank(data.getFieldDescribe()) ? (data.getFieldDescribe() + "',") : "',");
        baseSql.append((data.getFieldType() != null) ? (data.getFieldType() + ",") : "null,");
        baseSql.append(data.getFieldLen() != null ? ("'" + data.getFieldLen() + "',") : "'',");
        baseSql.append(StringUtils.isNotBlank(data.getDefaultValue()) ? ("'" + data.getDefaultValue() + "',") : "'',");
        baseSql.append(StringUtils.isNotBlank(data.getMemo()) ? ("'" + data.getMemo() + "',") : "'',");
        baseSql.append("'" + data.getColumnName() + "',");
        baseSql.append(StringUtils.isNotBlank(data.getCodeId()) ? ("'" + data.getCodeId() + "',") : "'',");
        baseSql.append(StringUtils.isNotBlank(data.getSameId()) ? ("'" + data.getSameId() + "',") : "'',");
        baseSql.append(data.getIs530New() != null ? ("'" + data.getIs530New() + "',") : "'',");
        baseSql.append(data.getFieldLen2() != null ? ("'" + data.getFieldLen2() + "',") : "'',");
        baseSql.append(StringUtils.isNotBlank(data.getFieldClass()) ? ("'" + data.getFieldClass() + "',") : "'',");
        baseSql.append(StringUtils.isNotBlank(data.getSecretClass()) ? ("'" + data.getSecretClass() + "',") : "'',");
        baseSql.append(StringUtils.isNotBlank(data.getSimChinese()) ? ("'" + data.getSimChinese() + "',") : "'',");
        baseSql.append(StringUtils.isNotBlank(data.getVersions()) ? ("'" + data.getVersions() + "',") : "'',");
        baseSql.append((data.getReleaseDate() != null) ? (data.getReleaseDate() + ",") : "null,");
        baseSql.append(StringUtils.isNotBlank(data.getSynonyName()) ? ("'" + data.getSynonyName() + "',") : "'',");
        baseSql.append(StringUtils.isNotBlank(data.getObjectType()) ? ("'" + data.getObjectType() + "',") : "'',");
        baseSql.append(StringUtils.isNotBlank(data.getExpressionWord()) ? ("'" + data.getExpressionWord() + "',") : "'',");
        baseSql.append(StringUtils.isNotBlank(data.getCodeIdDetail()) ? ("'" + data.getCodeIdDetail() + "',") : "'',");
        baseSql.append(StringUtils.isNotBlank(String.valueOf(data.getIsNorMal())) ? ("'" + data.getIsNorMal() + "',") : "'',");
        baseSql.append(StringUtils.isNotBlank(data.getRelate()) ? ("'" + data.getRelate() + "',") : "'',");
        baseSql.append(StringUtils.isNotBlank(data.getUnit()) ? ("'" + data.getUnit() + "',") : "'',");
        baseSql.append(StringUtils.isNotBlank(data.getFuseType()) ? ("'" + data.getFuseType() + "',") : "'',");
        baseSql.append(StringUtils.isNotBlank(data.getFuseFieldId()) ? ("'" + data.getFuseFieldId() + "',") : "'',");
        baseSql.append(StringUtils.isNotBlank(data.getSubOrg()) ? ("'" + data.getSubOrg() + "',") : "'',");
        baseSql.append(StringUtils.isNotBlank(data.getSubAuthor()) ? ("'" + data.getSubAuthor() + "',") : "'',");
        if (data.getOnDate() != null) {
            String onDate = DateUtil.formatDate(data.getOnDate(), DateUtil.DEFAULT_PATTERN_NEW_TIME);
            baseSql.append("to_date('" + onDate + "','dd-MM-yyyy'),");
        } else {
            baseSql.append("'',");
        }
        baseSql.append(StringUtils.isNotBlank(data.getRegOrg()) ? ("'" + data.getRegOrg() + "',") : "'',");
        baseSql.append(StringUtils.isNotBlank(String.valueOf(data.getFieldStandard())) ? ("'" + data.getFieldStandard() + "',") : "'',");
        baseSql.append(StringUtils.isNotBlank(data.getFacturer()) ? ("'" + data.getFacturer() + "',") : "'',");
        baseSql.append(StringUtils.isNotBlank(data.getContext()) ? ("'" + data.getContext() + "',") : "'',");
        baseSql.append(StringUtils.isNotBlank(data.getConstraint()) ? ("'" + data.getConstraint() + "',") : "'',");
        baseSql.append("sysdate," + "null,");
        baseSql.append(StringUtils.isNotBlank(data.getCreator()) ? ("'" + data.getCreator() + "',") : "'',");
        baseSql.append(StringUtils.isNotBlank(data.getUpdater()) ? ("'" + data.getUpdater() + "',") : "'',");
        baseSql.append(StringUtils.isNotBlank(data.getStatusNum()) ? ("'" + data.getStatusNum() + "',") : "'',");
        baseSql.append(StringUtils.isNotBlank(data.getFullChinese()) ? ("'" + data.getFullChinese() + "',") : "'',");
        baseSql.append(StringUtils.isNotBlank(data.getAttributeWord()) ? ("'" + data.getAttributeWord() + "',") : "'',");
        baseSql.append(StringUtils.isNotBlank(data.getGadsjFieldId()) ? ("'" + data.getGadsjFieldId() + "');") : "'');");
        baseSql.append("\n");
        return baseSql;
    }

    @Override
    public PageVO<TableInfoVO> getAllTableNameByFieldId(int pageIndex, int pageSize, String fieldId, String searchName) {
        log.info(">>>>>>开始查询关联表信息");
        PageVO<TableInfoVO> pageVO = new PageVO<>();
        PageHelper.startPage(pageIndex, pageSize, true, true, false);
        List<TableInfoVO> tableNameList = objectMapper.getObjectTableNameByFieldId(fieldId, searchName);
        if (tableNameList == null || tableNameList.isEmpty()) {
            return pageVO.emptyResult();
        }
        PageInfo<TableInfoVO> pageInfo = new PageInfo<>(tableNameList);
        pageVO.setPageNum(pageIndex);
        pageVO.setPageSize(pageSize);
        pageVO.setRows(pageInfo.getList());
        pageVO.setTotal(pageInfo.getTotal());
        return pageVO;
    }

    @Override
    public void metadataExportTableNames(HttpServletResponse response, String fieldId, String name) {
        try {
            if (StringUtils.isBlank(fieldId)) {
                throw new NullPointerException("传递的参数为空");
            }
            log.info(String.format(">>>>>>导出关联表的相关信息为，fieldId：%s，name：%s", fieldId, name));
            //文件名称
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(name + ".xls", "UTF-8"));
            //表标题
            String[] titles = {"数据集中文名", "数据集英文名", "数据集编码"};
            //列对应字段
            String[] fieldName = new String[]{"tableNameCh", "tableName", "tableId"};

            List<TableInfoVO> tableNameList = objectMapper.getObjectTableNameByFieldId(fieldId, null);
            List<Object> listNew = new ArrayList<>();
            for (TableInfoVO tableInfo : tableNameList) {
                listNew.add(tableInfo);
            }
            ServletOutputStream out = response.getOutputStream();
            ExcelHelper.export(new TableInfo(), titles, "关联表信息", listNew, fieldName, out);
            log.info(">>>>>>关联表的相关信息导出结束");
        } catch (Exception e) {
            log.error(">>>>>>导出关联表的相关信息报错：", e);
        }
    }


}
