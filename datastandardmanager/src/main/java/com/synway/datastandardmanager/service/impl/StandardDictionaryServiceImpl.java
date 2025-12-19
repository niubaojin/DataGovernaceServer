package com.synway.datastandardmanager.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.synway.datastandardmanager.constants.Common;
import com.synway.datastandardmanager.entity.dto.StandardDictionaryDTO;
import com.synway.datastandardmanager.entity.pojo.FieldCodeEntity;
import com.synway.datastandardmanager.entity.pojo.FieldCodeValEntity;
import com.synway.datastandardmanager.entity.vo.ValueLabelVO;
import com.synway.datastandardmanager.entity.vo.PageVO;
import com.synway.datastandardmanager.entity.vo.SelectFieldVO;
import com.synway.datastandardmanager.entity.vo.TableInfo;
import com.synway.datastandardmanager.enums.OperateLogFailReasonEnum;
import com.synway.datastandardmanager.enums.OperateLogHandleTypeEnum;
import com.synway.datastandardmanager.mapper.FieldCodeMapper;
import com.synway.datastandardmanager.mapper.FieldCodeValMapper;
import com.synway.datastandardmanager.service.StandardDictionaryService;
import com.synway.datastandardmanager.util.*;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.text.Collator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StandardDictionaryServiceImpl implements StandardDictionaryService {

    @Resource
    FieldCodeMapper fieldCodeMapper;
    @Resource
    FieldCodeValMapper fieldCodeValMapper;
    @Resource
    private OperateLogServiceImpl operateLogServiceImpl;


    @Override
    public PageVO selectCodeTable(int pageIndex, int pageSize, String codeName, String codeText, String codeId) {
        PageVO pageVO = new PageVO<>();
        try {
            if ((pageIndex != 0) && (pageSize != 0)) {
                PageHelper.startPage(pageIndex, pageSize, true, true, false);
            }
            LambdaQueryWrapper<FieldCodeEntity> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(FieldCodeEntity::getDeleted, 0);
            if (StringUtils.isNotBlank(codeId)){
                LambdaQueryWrapper<FieldCodeEntity> wrapper1 = Wrappers.lambdaQuery();
                wrapper1.eq(FieldCodeEntity::getCodeId, codeId);
                List<FieldCodeEntity> list = fieldCodeMapper.selectList(wrapper);
                List<String> parCodeIds = list.stream().map(FieldCodeEntity::getParCodeId).collect(Collectors.toList());
                wrapper.in(FieldCodeEntity::getCodeId, parCodeIds);
            }
            if (StringUtils.isNotBlank(codeName)){
                if (StringUtils.isNotBlank(codeText)){
                    wrapper.nested(wrapper2 -> {
                        wrapper2.apply("lower(codeName) like lower({0})", "%" + codeName.toLowerCase() + "%");
                        wrapper2.or().apply("lower(codeText) like lower({0})", "%" + codeText.toLowerCase() + "%");
                    });
                }else {
                    wrapper.apply("lower(codeName) like lower({0})", "%" + codeName.toLowerCase() + "%");
                }
            }else {
                if (StringUtils.isNotBlank(codeText)){
                    wrapper.apply("lower(codeText) like lower({0})", "%" + codeText.toLowerCase() + "%");
                }
            }
            List<FieldCodeEntity> fieldcodeList = fieldCodeMapper.selectList(wrapper);
            // 序号
            int pageNum = (pageIndex - 1) * pageSize;
            for (FieldCodeEntity fieldcode : fieldcodeList) {
                pageNum = pageNum + 1;
                fieldcode.setSerialNum(pageNum);
            }
            PageInfo<FieldCodeEntity> pageInfo = new PageInfo<FieldCodeEntity>(fieldcodeList);
            pageVO.setPageSize(pageSize);
            pageVO.setPageNum(pageIndex);
            pageVO.setRows(pageInfo.getList());
            pageVO.setTotal(pageInfo.getTotal());
        } catch (Exception e) {
            log.error(">>>>>>获取标准字典代码集管理出错：", e);
            return pageVO.emptyResult();
        }
        return pageVO;
    }

    @Override
    public PageVO selectCodeValTable(int pageIndex, int pagesize, String valValue, String valText, String codeId) {
        PageVO pageVO = new PageVO<>();
        try {
            LambdaQueryWrapper<FieldCodeValEntity> wrapper = Wrappers.lambdaQuery();
            if (StringUtils.isNotBlank(codeId)) {
                wrapper.eq(FieldCodeValEntity::getCodeId, codeId);
            }
            if (StringUtils.isNotBlank(valValue)) {
                wrapper.apply("lower(valvalue) like lower({0})", "%" + valValue.toLowerCase() + "%");
            }
            if (StringUtils.isNotBlank(valText)) {
                wrapper.apply("lower(valtext) like lower({0})", "%" + valText.toLowerCase() + "%");
            }
            PageHelper.startPage(pageIndex, pagesize, true, true, false);
            List<FieldCodeValEntity> fieldCodeValList = fieldCodeValMapper.selectList(wrapper);
            PageInfo pageInfo = new PageInfo<FieldCodeValEntity>(fieldCodeValList);
            pageVO.setPageNum(pageIndex);
            pageVO.setPageSize(pagesize);
            pageVO.setTotal(pageInfo.getTotal());
            pageVO.setRows(pageInfo.getList());
        } catch (Exception e) {
            log.error(">>>>>>获取代码集值信息出错：", e);
            return pageVO.emptyResult();
        }
        return pageVO;
    }

    @Override
    public List<FieldCodeValEntity> selectCodeValTableNew(String codeId) {
        List<FieldCodeValEntity> fieldCodeValList = new ArrayList<>();
        try {
            if (StringUtils.isEmpty(codeId)) {
                return fieldCodeValList;
            } else {
                fieldCodeValList = SelectUtil.selectCodeValTableByCodeId(fieldCodeValMapper, codeId, null, null);
            }
        } catch (Exception e) {
            log.error(">>>>>>查询codeValTable值报错：", e);
        }
        return fieldCodeValList;
    }

    @Override
    public String delCodeValTableServiceImpl(FieldCodeValEntity deleteFieldCodeVal) {
        try {
            if (StringUtils.isEmpty(deleteFieldCodeVal.getCodeValId())) {
                log.error(">>>>>>删除指定的数据中codeValId为空");
                throw new Exception("删除指定的数据中【codeValId】为空");
            }
            if (StringUtils.isEmpty(deleteFieldCodeVal.getValValue())) {
                log.error(">>>>>>删除指定的数据中【代码集值】为空");
                throw new Exception("删除指定的数据中【代码集值】为空");
            }
            LambdaQueryWrapper<FieldCodeValEntity> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(FieldCodeValEntity::getCodeValId, deleteFieldCodeVal.getCodeValId());
            wrapper.eq(FieldCodeValEntity::getValValue, deleteFieldCodeVal.getValValue());
            fieldCodeValMapper.delete(wrapper);
        } catch (Exception e) {
            log.error(">>>>>>删除指定的代码集值信息报错：", e);
            return Common.DEL_FAIL;
        }
        return Common.DEL_SUCCESS;
    }

    @Override
    public String addCodeValTableService(FieldCodeValEntity fieldCodeVal) {
        try {
            if (StringUtils.isEmpty(fieldCodeVal.getValText()) || StringUtils.isEmpty(fieldCodeVal.getValValue())) {
                log.error(">>>>>>代码集值/代码集名不能为空");
                throw new Exception("代码集值/代码集名不能为空");
            }
            if (StringUtils.isEmpty(fieldCodeVal.getCodeId())) {
                log.error(">>>>>>代码值的主键ID不能为空");
                throw new Exception("代码值的主键ID不能为空");
            }
            // 判断codeid在表 fieldcode 中是否存在，不存在表示没有保存
            LambdaQueryWrapper<FieldCodeEntity> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(FieldCodeEntity::getCodeId, fieldCodeVal.getCodeId());
            if (fieldCodeMapper.selectCount(wrapper) == 0) {
                log.error(String.format(">>>>>>%s在对应的fieldcod表里没有对应数据", fieldCodeVal.getCodeId()));
                throw new Exception(fieldCodeVal.getCodeId() + "在对应的fieldcod表里没有对应数据");
            }

            // 判断唯一性的数据
            // 校验对象的长度等内容是否合规
            ValidatorUtil.checkObjectValidator(fieldCodeVal);
            // 如果为空 表示是新增的代码集信息，如果存在，表示为修改原有数据
            String codeValIdNew = fieldCodeVal.getCodeId() + fieldCodeVal.getValValue();
            if (StringUtils.isEmpty(fieldCodeVal.getCodeValId())) {
                fieldCodeVal.setCodeValId(codeValIdNew);
                fieldCodeValMapper.insert(fieldCodeVal);
            } else {
                LambdaUpdateWrapper<FieldCodeValEntity> wrapperFCV = Wrappers.lambdaUpdate();
                wrapperFCV.set(FieldCodeValEntity::getValText, fieldCodeVal.getValText())
                        .set(FieldCodeValEntity::getValValue, fieldCodeVal.getValValue())
                        .set(FieldCodeValEntity::getValTextTitle, fieldCodeVal.getValTextTitle())
                        .set(FieldCodeValEntity::getSortIndex, fieldCodeVal.getSortIndex())
                        .set(FieldCodeValEntity::getMemo, fieldCodeVal.getMemo())
                        .set(FieldCodeValEntity::getCodeValId, codeValIdNew)
                        .eq(FieldCodeValEntity::getCodeValId, fieldCodeVal.getCodeValId());
            }
        } catch (Exception e) {
            log.error(">>>>>>新增/修改 指定的代码集值信息报错：", e);
            return Common.UPDATE_FAIL;
        }
        return Common.UPDATE_SUCCESS;
    }

    @Override
    public List<SelectFieldVO> getCodeValIdListService(String condition) {
        try {
            log.info("搜索" + condition + "对应的代码集父ID/引用的代码集Id");
            return fieldCodeMapper.getCodeValIdListDao(condition);
        } catch (Exception e) {
            log.error(">>>>>>获取提示框信息出错：", e);
            return new ArrayList<>();
        }
    }

    @Override
    public String addOneCodeMessageService(FieldCodeEntity fieldCodeEntity) {
        try {
            // 先进行逻辑判断 codeId 是否为非中文
            if (PinYinUtil.isContainsPattern(fieldCodeEntity.getCodeId(), "[\u4E00-\u9FA5]")) {
                log.error(">>>>>>主键ID中存在中文，不能用中文");
                throw new Exception("主键ID中存在中文，不能用中文");
            }
            if (PinYinUtil.isContainsPattern(fieldCodeEntity.getCodeName(), "[\u4E00-\u9FA5]")) {
                log.error(">>>>>>代码英文名称存在中文，不能用中文");
                throw new Exception("代码英文名称存在中文，不能用中文");
            }
            if (StringUtils.isEmpty(fieldCodeEntity.getCodeId())) {
                log.error(">>>>>>主键ID不能为空");
                throw new Exception("主键ID不能为空");
            }
            if (StringUtils.isEmpty(fieldCodeEntity.getCodeName())) {
                log.error(">>>>>>代码英文名称不能为空");
                throw new Exception("代码英文名称不能为空");
            }
            if (StringUtils.isEmpty(fieldCodeEntity.getCodeText())) {
                log.error(">>>>>>代码中文名称不能为空");
                throw new Exception("代码中文名称不能为空");
            }
            String message = "";
            // 校验对象的长度等内容是否合规
            ValidatorUtil.checkObjectValidator(fieldCodeEntity);
            // 判断codeId是否已经存在，不存在表示是插入新的数据，存在表示是更新数据
            LambdaQueryWrapper<FieldCodeEntity> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(FieldCodeEntity::getCodeId, fieldCodeEntity.getCodeId());
            if (fieldCodeMapper.selectCount(wrapper) == 0) {
                fieldCodeMapper.insert(fieldCodeEntity);
                operateLogServiceImpl.elementCodeSuccessLog(OperateLogHandleTypeEnum.ADD, "标准字典代码集管理", fieldCodeEntity);
                return Common.ADD_SUCCESS;
            } else {
                LambdaUpdateWrapper<FieldCodeEntity> updateWrapper = Wrappers.lambdaUpdate();
                updateWrapper.set(FieldCodeEntity::getCodeName, fieldCodeEntity.getCodeName())
                        .set(FieldCodeEntity::getCodeText, fieldCodeEntity.getCodeText())
                        .set(FieldCodeEntity::getDeleted, 0)
                        .set(FieldCodeEntity::getParCodeId, fieldCodeEntity.getParCodeId())
                        .set(FieldCodeEntity::getBrotherCodeId, fieldCodeEntity.getBrotherCodeId())
                        .eq(FieldCodeEntity::getCodeId, fieldCodeEntity.getCodeId());
                fieldCodeMapper.update(updateWrapper);
                operateLogServiceImpl.elementCodeSuccessLog(OperateLogHandleTypeEnum.ALTER, "标准字典代码集管理", fieldCodeEntity);
                return Common.UPDATE_SUCCESS;
            }
        } catch (Exception e) {
            log.error(">>>>>>在fieldCode表中添加/更新数据失败：", e);
            return Common.UPDATE_FAIL;
        }
    }

    @Override
    public String delCodeTableServiceImpl(FieldCodeEntity fieldCodeEntity) {
        String message = "";
        try {
            if (StringUtils.isEmpty(fieldCodeEntity.getCodeId())) {
                log.error(">>>>>>元素代码信息的主键ID为空，不能被删除");
                throw new Exception("元素代码信息的主键ID为空，不能被删除");
            }
            LambdaUpdateWrapper<FieldCodeEntity> updateWrapper = Wrappers.lambdaUpdate();
            updateWrapper.set(FieldCodeEntity::getDeleted, 1)
                    .eq(FieldCodeEntity::getCodeId, fieldCodeEntity.getCodeId());
            if (fieldCodeMapper.update(updateWrapper) > 0) {
                operateLogServiceImpl.elementCodeSuccessLog(OperateLogHandleTypeEnum.DELETE, "标准字典代码集管理", fieldCodeEntity);
                message = "代码" + fieldCodeEntity.getCodeText() + "删除成功";
            } else {
                operateLogServiceImpl.elementCodeFailLog(OperateLogHandleTypeEnum.DELETE, OperateLogFailReasonEnum.YYXTFM, "标准字典代码集管理", fieldCodeEntity);
                message = "代码" + fieldCodeEntity.getCodeText() + "删除失败";
            }
        } catch (Exception e) {
            log.error(">>>>>>删除指定的代码值信息报错：", e);
            message = "代码" + fieldCodeEntity.getCodeText() + "删除失败";
        }
        return message;
    }

    @Override
    public String delAllSelectCode(List<FieldCodeEntity> fieldCodeEntities) {
        log.info(">>>>>>要删除的数据为:" + JSONObject.toJSONString(fieldCodeEntities));
        try {
            fieldCodeEntities.forEach(((element) -> {
                        delCodeTableServiceImpl(element);
                    })
            );
            return Common.DEL_SUCCESS;
        } catch (Exception e) {
            log.error(">>>>>>删除代码值信息报错：", e);
            return Common.DEL_FAIL;
        }
    }

    @Override
    public List<FieldCodeValEntity> uploadCodeValXlsFile(MultipartFile codeValXlsFile) {
        List<FieldCodeValEntity> fieldCodeValEntities = new ArrayList<>();
        try {
            String fileName = codeValXlsFile.getOriginalFilename();
            log.info(">>>>>>上传的文件名为：" + fileName);
            List<Map> list = ImportHelper.importExcel3(codeValXlsFile);

            for (Map element : list) {
                FieldCodeValEntity oneFieldCodeVal = new FieldCodeValEntity();
                String codeId = String.valueOf(element.getOrDefault("主键ID*", ""));
                String valText = String.valueOf(element.getOrDefault("代码集名*", ""));
                String memo = String.valueOf(element.getOrDefault("备注", ""));
                String valValue = String.valueOf(element.getOrDefault("代码集值*", ""));
                String sortIndex = String.valueOf(element.getOrDefault("代码值顺序", ""));
                String valTextTitle = String.valueOf(element.getOrDefault("英文缩写", ""));
                if (!StringUtils.isNumeric(sortIndex)) {
                    throw new Exception("代码值顺序存在非数字的值，导入数据失败");
                }
                // 判断该代码值是否已经存在，如果存在，不需要重新插入数据
                if (StringUtils.isNotEmpty(codeId.trim()) && StringUtils.isNotEmpty(valValue)) {
                    LambdaQueryWrapper<FieldCodeValEntity> wrapper = Wrappers.lambdaQuery();
                    wrapper.eq(FieldCodeValEntity::getCodeId, codeId.trim());
                    wrapper.eq(FieldCodeValEntity::getValValue, valValue);
                    if (fieldCodeValMapper.selectCount(wrapper) > 0) {
                        continue;
                    }
                    oneFieldCodeVal.setCodeValId(codeId + valValue);
                    oneFieldCodeVal.setCodeId(codeId);
                    oneFieldCodeVal.setValText(valText);
                    oneFieldCodeVal.setMemo(memo);
                    oneFieldCodeVal.setValValue(valValue);
                    oneFieldCodeVal.setSortIndex(Integer.valueOf(sortIndex));
                    oneFieldCodeVal.setValTextTitle(valTextTitle);
                    fieldCodeValEntities.add(oneFieldCodeVal);
                }
            }
            log.info(">>>>>>导入的数据为：" + JSONObject.toJSONString(fieldCodeValEntities));
        } catch (Exception e) {
            log.error(">>>>>>导入文件报错：", e);
        }
        return fieldCodeValEntities;
    }

    @Override
    public void downElementCodeTemplateFile(HttpServletResponse response) {
        try {
            //文件名称
            String name = "元素代码值导入";
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(name + ".xls", "UTF-8"));
            //表标题
            String[] titles = {"主键ID*", "代码集名*", "代码集值*", "代码值顺序", "英文缩写", "备注"};
            //列对应字段
            String[] fieldName = new String[]{};
            List<Object> listNew = new ArrayList<>();
            ServletOutputStream out = response.getOutputStream();
            ExcelHelper.export(new TableInfo(), titles, "代码值信息", listNew, fieldName, out);
            log.info(">>>>>>导出模板文件结束");
        } catch (Exception e) {
            log.error(">>>>>>导出模板文件报错：", e);
        }
    }

    @Override
    public void downloadStandardDictionaryFieldExcel(HttpServletResponse response, StandardDictionaryDTO data, String name, Object object) {
        try {
            log.info(">>>>>>开始下载标准字典的相关信息");
            List<FieldCodeValEntity> dictionaryFieldList = new ArrayList<>();
            if (data.getStandardDictionaryFieldPojoList() == null || data.getStandardDictionaryFieldPojoList().isEmpty()) {
                dictionaryFieldList = SelectUtil.selectCodeValTableByCodeId(fieldCodeValMapper, data.getId(), "", "");
            } else {
                dictionaryFieldList = data.getStandardDictionaryFieldPojoList();
            }
            response.setContentType("application/x-xls");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode(name, "UTF-8") + ".xlsx";
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName);
            EasyExcel.write(response.getOutputStream(), object.getClass()).autoCloseStream(Boolean.FALSE).sheet("标准字典表管理").doWrite(dictionaryFieldList);
        } catch (Exception e) {
            log.error(">>>>>>下载标准字典表出错：", e);
        }
    }

    @Override
    public String delBatchCodeValTable(List<FieldCodeValEntity> fieldCodeValEntities) {
        try {
            for (FieldCodeValEntity fieldCodeVal : fieldCodeValEntities) {
                delCodeValTableServiceImpl(fieldCodeVal);
            }
            return Common.DEL_SUCCESS;
        } catch (Exception e) {
            log.error(">>>>>>批量删除指定的代码集值信息报错：", e);
            return Common.DEL_FAIL;
        }
    }

    @Override
    public String addOneCodeValMessage(StandardDictionaryDTO dictionaryDTO) {
        try {
            addOneCodeMessageService(dictionaryDTO.getOneFieldcode());
            List<FieldCodeValEntity> newFieldCodeValList = dictionaryDTO.getOneFieldCodeVal();
            // 分成几个列表 区分成需要删除的 需要新增 需要更新的
            String codeId = dictionaryDTO.getOneFieldcode().getCodeId();
            List<FieldCodeValEntity> oldFieldCodeValList = SelectUtil.selectCodeValTableByCodeId(fieldCodeValMapper, codeId, "", "");
            List<String> oldCodeValIdList = oldFieldCodeValList.stream().filter(e -> StringUtils.isNotEmpty(e.getCodeValId())).
                    map(e -> e.getCodeValId().toLowerCase()).distinct().collect(Collectors.toList());
            List<String> newCodeValIdList = newFieldCodeValList.stream().filter(e -> StringUtils.isNotEmpty(e.getCodeValId())).
                    map(e -> e.getCodeValId().toLowerCase()).distinct().collect(Collectors.toList());
            List<FieldCodeValEntity> deleteFieldCodeValList = new ArrayList<>();
            List<FieldCodeValEntity> insertFieldCodeValList = new ArrayList<>();
            List<FieldCodeValEntity> updateFieldCodeValList = new ArrayList<>();
            for (FieldCodeValEntity fieldCodeVal : newFieldCodeValList) {
                if (!oldCodeValIdList.contains(fieldCodeVal.getCodeValId().toLowerCase())) {
                    // 需要新增的，校验对象的长度等内容是否合规
                    ValidatorUtil.checkObjectValidator(fieldCodeVal);
                    insertFieldCodeValList.add(fieldCodeVal);
                } else {
                    // 需要更新的
                    updateFieldCodeValList.add(fieldCodeVal);
                }
            }
            for (FieldCodeValEntity oldCodeVal : oldFieldCodeValList) {
                if (!newCodeValIdList.contains(oldCodeVal.getCodeValId().toLowerCase())) {
                    deleteFieldCodeValList.add(oldCodeVal);
                }
            }
            // 新增新添加的数据
            if (!insertFieldCodeValList.isEmpty()){
                fieldCodeValMapper.uploadCodeValXlsFileDao(insertFieldCodeValList);
            }
            // 删除需要删除的数据
            for (FieldCodeValEntity deleteFieldCode : deleteFieldCodeValList) {
                delCodeValTableServiceImpl(deleteFieldCode);
            }
            // 编辑需要编辑的数据
            for (FieldCodeValEntity fieldCodeVal : updateFieldCodeValList) {
                addCodeValTableService(fieldCodeVal);
            }
            return Common.SAVE_SUCCESS;
        } catch (Exception e) {
            log.error(">>>>>>添加数据报错：", e);
            return Common.SAVE_FAIL;
        }
    }

    @Override
    public List<ValueLabelVO> searchDictionaryList(String searchText) {
        List<ValueLabelVO> resultList = new ArrayList<>();
        try {
            log.info(">>>>>>开始查询外部字典的信息");
            resultList = fieldCodeMapper.searchDictionaryList(searchText);
            if (resultList == null || resultList.isEmpty()) {
                return resultList;
            }
            resultList = resultList.stream().sorted((s1, s2) -> Collator.getInstance(Locale.CHINA)
                    .compare(s2.getLabel(), s1.getLabel())).collect(Collectors.toList());
        } catch (Exception e) {
            log.error(">>>>>>获取数据字典下拉框信息报错：", e);
        }
        return resultList;
    }

}
