package com.synway.datastandardmanager.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.synway.common.exception.SystemException;
import com.synway.datastandardmanager.constants.Common;
import com.synway.datastandardmanager.entity.dto.EntityElementDTO;
import com.synway.datastandardmanager.entity.pojo.*;
import com.synway.datastandardmanager.entity.vo.*;
import com.synway.datastandardmanager.enums.ErrorCodeEnum;
import com.synway.datastandardmanager.enums.KeyStrEnum;
import com.synway.datastandardmanager.enums.OperateLogHandleTypeEnum;
import com.synway.datastandardmanager.listener.ExcelListener;
import com.synway.datastandardmanager.mapper.*;
import com.synway.datastandardmanager.service.DataElementService;
import com.synway.datastandardmanager.util.EasyExcelUtil;
import com.synway.datastandardmanager.util.ElementTemplateExcelUtils;
import com.synway.datastandardmanager.util.ValidatorUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DataElementServiceImpl implements DataElementService {

    @Resource
    private AllCodeDataMapper allCodeDataMapper;
    @Resource
    private EntityElementMapper elementMapper;
    @Resource
    private SynlteFieldMapper synlteFieldMapper;
    @Resource
    private FieldCodeValMapper fieldCodeValMapper;
    @Resource
    private SameWordMapper sameWordMapper;

    @Resource
    private OperateLogServiceImpl operateLogServiceImpl;


    @Override
    public List<EntityElementVO> getDataElementList(EntityElementDTO elementDTO) {
        if ("isElementType".equals(elementDTO.getSort())) {
            elementDTO.setSort("iselement");
        } else {
            elementDTO.setSort("modDate");
        }
        List<EntityElementVO> elementList = elementMapper.getDataElementList(elementDTO);
        if (elementList != null && !elementList.isEmpty()) {
            int num = 1;
            for (EntityElementVO data : elementList) {
                data.setNum(num++);
                data.setElementObjectType(KeyStrEnum.getValueByKeyAndType("3_" + data.getElementObject(), Common.DATAELEMENTCODE));
                data.setIsElementType(KeyStrEnum.getValueByKeyAndType("2_" + data.getIsElement(), Common.DATAELEMENTCODE));
            }
        }
        return elementList;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String addOneData(EntityElementEntity element) {
        //检查数据是否正确
        LambdaQueryWrapper<EntityElementEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(EntityElementEntity::getElementCode, element.getElementCode());
        EntityElementEntity entityOle = elementMapper.selectOne(wrapper);
        if (entityOle != null) {
            throw SystemException.asSystemException(ErrorCodeEnum.CHECK_UNION_ERROR, String.format("要素标识符[%s]重复", element.getElementCode()));
        }
        LambdaQueryWrapper<SynlteFieldEntity> wrapperSF = Wrappers.lambdaQuery();
        wrapperSF.eq(SynlteFieldEntity::getFieldId, element.getDataElementId());
        SynlteFieldEntity synlteField = synlteFieldMapper.selectOne(wrapperSF);
        element.setSameId(synlteField.getSameId());
        log.info("插入到数据要素的数据为：", JSONObject.toJSONString(element));
        elementMapper.insert(element);

        // 发送操作日志
        operateLogServiceImpl.synlteElementSuccessLog(OperateLogHandleTypeEnum.ADD, "数据要素管理", element.getElementChname());
        return Common.ADD_SUCCESS;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String upOneData(EntityElementEntity element) {
        //检查数据是否正确
        LambdaQueryWrapper<EntityElementEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(EntityElementEntity::getElementCode, element.getElementCode());
        EntityElementEntity entityOld = elementMapper.selectOne(wrapper);
        if (entityOld == null) {
            throw SystemException.asSystemException(ErrorCodeEnum.CHECK_ERROR, String.format("要素标识符[%s]在数据库中不存在，更新数据失败", element.getElementCode()));
        }
        if (StringUtils.isBlank(element.getAuthor())) {
            throw SystemException.asSystemException(ErrorCodeEnum.CHECK_ERROR, String.format("author的值[%s]为空，更新失败", element.getAuthor()));
        }
        //判断数据要素是否更新，未做任何改动，直接返回，不更新数据库
        entityOld.setElementObjectType(KeyStrEnum.getValueByKeyAndType("3_" + entityOld.getElementObject(), Common.DATAELEMENTCODE));
        if (entityOld.equals(element)) {
            return "数据要素没改变，更新失败";
        }
        try {
            LambdaUpdateWrapper<EntityElementEntity> updateWrapper = Wrappers.lambdaUpdate();
            updateWrapper
                    .set(EntityElementEntity::getElementChname, element.getElementChname())
                    .set(EntityElementEntity::getElementObject, element.getElementObject())
                    .set(EntityElementEntity::getElementRule, element.getElementRule())
                    .set(EntityElementEntity::getAuthor, element.getAuthor())
                    .set(EntityElementEntity::getModDate, new Date())
                    .eq(EntityElementEntity::getElementCode, element.getElementCode());
            int num = elementMapper.update(updateWrapper);
            log.info("更新synlte.element表中数据量为：{}", num);
            if (num == 0) {
                throw SystemException.asSystemException(ErrorCodeEnum.CHECK_ERROR, "更新失败，请刷新后重新更改数据");
            }
            // 发送操作日志
            operateLogServiceImpl.synlteElementSuccessLog(OperateLogHandleTypeEnum.ALTER, "数据要素管理", element.getElementChname());
            return Common.UPDATE_SUCCESS;
        } catch (Exception e) {
            log.error("数据要素更新失败：", e);
            return Common.UPDATE_FAIL;
        }
    }

    @Override
    public String deleteElementByCode(String elementCode) {
        LambdaQueryWrapper<EntityElementEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(EntityElementEntity::getElementCode, elementCode);
        EntityElementEntity entityOld = elementMapper.selectOne(wrapper);
        operateLogServiceImpl.synlteElementSuccessLog(OperateLogHandleTypeEnum.DELETE, "数据要素管理", entityOld.getElementChname());
        return elementMapper.delete(wrapper) < 1 ? "数据删除失败" : "数据删除成功";
    }

    @Override
    public Boolean checkIsDelete(String elementCode) {
        if (StringUtils.isBlank(elementCode)) {
            throw SystemException.asSystemException(ErrorCodeEnum.CHECK_PARAMETER_ERROR, "codeId值为空");
        }
        LambdaQueryWrapper<EntityElementEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(EntityElementEntity::getElementCode, elementCode);
        return elementMapper.selectCount(queryWrapper) <= 0;
    }

    @Override
    public List<SelectFieldVO> searchSynlteField(String searchName) {
        List<SelectFieldVO> fieldList = synlteFieldMapper.getSelectField(searchName);
        fieldList.stream().limit(100);
        fieldList.sort((s1, s2) -> Collator.getInstance(java.util.Locale.CHINA).compare(s1.getValue(), s2.getValue()));
        return fieldList;
    }

    @Override
    public List<SelectFieldVO> searchSecondField(String searchName) {
        List<SelectFieldVO> selectSecondFields = synlteFieldMapper.searchSecondField(searchName);
        selectSecondFields.stream().limit(100);
        selectSecondFields.sort((s1, s2) -> Collator.getInstance(java.util.Locale.CHINA).compare(s1.getValue(), s2.getValue()));
        return selectSecondFields;
    }

    @Override
    public List<SelectFieldVO> searchAnotherField(String codeId) {
        if (StringUtils.isBlank(codeId)) {
            throw SystemException.asSystemException(ErrorCodeEnum.CHECK_PARAMETER_ERROR, "该数据字典未查到有子元素，请更换数据字典");
        }
        LambdaQueryWrapper<FieldCodeValEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(FieldCodeValEntity::getCodeId, codeId);
        List<FieldCodeValEntity> fieldList = fieldCodeValMapper.selectList(wrapper);
        if (fieldList.isEmpty()) {
            throw SystemException.asSystemException(ErrorCodeEnum.QUERY_SQL_ERROR, "根据codeId查询元素代码取值表时结果为空");
        }
        List<SelectFieldVO> responseList = new ArrayList<>();
        for (FieldCodeValEntity entity : fieldList) {
            SelectFieldVO field = new SelectFieldVO();
            field.setId(entity.getValValue());
            field.setValue(entity.getValText() + "(" + entity.getValValue() + ")");
            responseList.add(field);
        }
        responseList.stream().distinct().collect(Collectors.toList());
        responseList.sort((s1, s2) -> Collator.getInstance(java.util.Locale.CHINA).compare(s1.getValue(), s2.getValue()));
        return responseList;
    }

    @Override
    public List<TreeNodeValueVO> searchElementObject() {
        log.info("--------------开始查询数据要素的汇总树信息--------------------");
        List<SelectFieldVO> searchList = elementMapper.selectElementObject();
        List<TreeNodeValueVO> elementTreeNodeList = new ArrayList<>();
        TreeNodeValueVO rootNode = new TreeNodeValueVO();
        rootNode.setId("");
        rootNode.setLevel(1);
        rootNode.setSortLevel(1);
        if (searchList == null || searchList.isEmpty()) {
            rootNode.setLabel("主体(0)");
        } else {
            int count = searchList.stream().mapToInt(d -> Integer.parseInt(d.getValue())).sum();
            rootNode.setLabel("主体(" + count + ")");
            List<TreeNodeValueVO> childrenTree = new ArrayList<>();
            for (SelectFieldVO data : searchList) {
                TreeNodeValueVO treeNode = new TreeNodeValueVO();
                treeNode.setId(data.getId());
                treeNode.setLevel(2);
                treeNode.setParent("");
                treeNode.setLabel(data.getId() + "(" + data.getValue() + ")");
                childrenTree.add(treeNode);
            }
            rootNode.setChildren(childrenTree);
        }
        elementTreeNodeList.add(rootNode);
        log.info("--------------查询数据要素的汇总树信息结束--------------------");
        return elementTreeNodeList;
    }

    @Override
    public List<SelectFieldVO> searchAllObject() {
        LambdaQueryWrapper<AllCodeDataEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(AllCodeDataEntity::getCodeId, "ELEMENTOBJECT");
        List<AllCodeDataEntity> entities = allCodeDataMapper.selectList(wrapper);
        if (entities.isEmpty()) {
            throw SystemException.asSystemException(ErrorCodeEnum.QUERY_SQL_ERROR, "synlte.fieldcodeval表中没有数据");
        }
        List<SelectFieldVO> elementObjectInfo = new ArrayList<>();
        entities.stream().forEach(data -> {
            SelectFieldVO field = new SelectFieldVO();
            field.setSameId(data.getCodeValue());
            field.setValue(data.getCodeText());
            elementObjectInfo.add(field);
        });
        elementObjectInfo.sort((s1, s2) -> Collator.getInstance(java.util.Locale.CHINA).compare(s1.getSameId(), s2.getSameId()));
        return elementObjectInfo;
    }

    @Override
    public List<String> searchElementChname(String searchName) {
        List<EntityElementEntity> entities = elementMapper.queryEntityElementList(searchName);
        List<String> fieldList = new ArrayList<>();
        if (entities == null || entities.isEmpty()) {
            return fieldList;
        }
        entities.stream().forEach(d -> {
            fieldList.add(d.getElementChname());
        });
        fieldList.sort((s1, s2) -> Collator.getInstance(java.util.Locale.CHINA).compare(s1, s2));
        return fieldList;
    }

    @Override
    public List<KeyValueVO> searchIsElement() {
        List<EntityElementEntity> entities = elementMapper.queryEntityElementList("");
        if (entities.isEmpty()) {
            return new ArrayList<>();
        } else {
            List<KeyValueVO> lists = new ArrayList<>();
            for (EntityElementEntity data : entities) {
                String isElement = data.getIsElement();
                KeyValueVO field = new KeyValueVO(isElement, KeyStrEnum.getValueByKeyAndType("2_" + isElement, Common.DATAELEMENTCODE));
                lists.add(field);
            }
            return lists.stream().distinct().collect(Collectors.toList());
        }
    }

    @Override
    public String searchSameWord(String sameId) {
        log.info(">>>>>>根据sameId查询语义名称");
        LambdaQueryWrapper<SameWordEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SameWordEntity::getSameId, sameId);
        SameWordEntity sameWord = sameWordMapper.selectOne(wrapper);
        return sameWord == null ? "" : sameWord.getWordname();
    }

    @Override
    public List<ValueLabelVO> searchElementTotal() {
        List<SelectFieldVO> elementTotalList = elementMapper.searchElementTotal();
        if (elementTotalList == null || elementTotalList.isEmpty()) {
            return new ArrayList<>();
        }
        List<ValueLabelVO> listData = new ArrayList<>();
        for (SelectFieldVO data : elementTotalList) {
            List<ValueLabelVO> secondElementList = elementMapper.searchSecondElementName(data.getId());
            String label = String.format("%s(%s)", data.getName(), data.getValue());
            ValueLabelVO parentElement = new ValueLabelVO(data.getId(), label, secondElementList);
            listData.add(parentElement);
        }
        return listData;
    }

    @Override
    public void downloadAllElementTableExcel(HttpServletResponse response, List<EntityElementEntity> elementList, String name, Object object) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode(name, "UTF-8") + ".xlsx";
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName);
            if (elementList == null && elementList.isEmpty()) {
                elementList = elementMapper.selectList(Wrappers.lambdaQuery());
            }
            for (EntityElementEntity data : elementList) {
                data.setIsElementType(KeyStrEnum.getValueByKeyAndType("2_" + data.getIsElement(), Common.DATAELEMENTCODE));
                data.setElementObjectType(KeyStrEnum.getValueByKeyAndType("3_" + data.getElementObject(), Common.DATAELEMENTCODE));
                data.setCreateModeType(KeyStrEnum.getValueByKeyAndType("1_" + data.getCreateMode(), Common.DATAELEMENTCODE));
            }
            EasyExcel.write(response.getOutputStream(), object.getClass()).autoCloseStream(Boolean.FALSE)
                    .sheet("数据要素管理").doWrite(elementList);

        } catch (Exception e) {
            log.error("下载数据要素报错：", e);
        }
    }

    @Override
    public List<EntityElementEntity> importElementTableExcel(MultipartFile file) {
        ExcelListener<EntityElementEntity> listener = new ExcelListener<>();
        List<EntityElementEntity> list = new ArrayList<>();
        List<EntityElementEntity> failedList = new ArrayList<>();
        List<String> elementChnames = new ArrayList<>();
        try {
            list = EasyExcelUtil.readExcelUtil(file, new EntityElementEntity(), listener);
            // 这里插入每一行数据，需要验证elementcode，这个所有的都是新增的
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                EntityElementEntity elementManage = (EntityElementEntity) iterator.next();
                boolean flag = checkElementCodeConformRule(elementManage.getElementObject());
                if (flag) {
                    EntityElementEntity addElementManage = new EntityElementEntity();
                    BeanUtils.copyProperties(addElementManage, elementManage);
                    //验证格式
                    ValidatorUtil.checkObjectValidator(addElementManage);
                    int addFlag = elementMapper.insert(addElementManage);
                    if (addFlag == 1) {
                        elementChnames.add(addElementManage.getElementChname());
                        iterator.remove();
                    } else {
                        log.error(String.format("数据要素%s插入失败", addElementManage.getElementChname()));
                    }
                } else {
                    failedList.add(elementManage);
                    log.info("数据要素" + elementManage.getElementCode() + "已在数据库中存在");
                }
            }
        } catch (Exception e) {
            log.error("导入文件报错:", e);
            throw new NullPointerException("导入文件报错:" + e.getMessage());
        }
        // 发送操作日志
        if (elementChnames.size() > 0){
            operateLogServiceImpl.synlteElementSuccessLog(OperateLogHandleTypeEnum.ADD, "数据要素管理", String.join(",", elementChnames));
        }
        log.info(String.format("导入成功的数量为：%s，导入失败的数量为：%s", list.size(), failedList.size()));
        return list;
    }

    @Override
    public void downloadErrorElementTableExcel(HttpServletResponse response, List<EntityElementEntity> list, String name, EntityElementEntity object) {
        ExcelWriter excelWriter = null;
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode(name, "UTF-8") + ".xlsx";
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName);
            excelWriter = EasyExcel.write(response.getOutputStream()).build();
            WriteSheet writeSheetTwo = EasyExcel.writerSheet(0, "数据要素管理信息")
                    .head(EntityElementEntity.class)
                    .build();
            excelWriter.write(list, writeSheetTwo);
        } catch (Exception e) {
            log.error("下载标签管理数据报错:", e);
        } finally {
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }

    @Override
    public void downloadElmentTemplateTableExcel(HttpServletResponse response, List<EntityElementEntity> list, String name, EntityElementEntity object) {
        ExcelWriter excelWriter = null;
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode(name, "UTF-8") + ".xlsx";
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName);
            excelWriter = EasyExcel.write(response.getOutputStream()).build();
            WriteSheet writeSheetTwo = EasyExcel.writerSheet(0, "数据要素模板管理信息").head(EntityElementEntity.class)
                    .build();
            excelWriter.write(list, writeSheetTwo);
            WriteSheet writeSheetThree = EasyExcel.writerSheet(1, "数据要素码值").head(TemplateCodeValueVO.class)
                    .build();
            excelWriter.write(ElementTemplateExcelUtils.getListData(), writeSheetThree);
        } catch (Exception e) {
            log.error("下载数据要素管理数据报错:", e);
        } finally {
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }

    private boolean checkElementCodeConformRule(String elementCode) {
        if (StringUtils.isBlank(elementCode)) {
            return true;
        }
        if (!StringUtils.isNumeric(elementCode)) {
            return false;
        }
        // 数据库中是否存在
        LambdaQueryWrapper<EntityElementEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(EntityElementEntity::getElementCode, elementCode);
        EntityElementEntity entity = elementMapper.selectOne(wrapper);
        if (entity != null) {
            return false;
        }
        return true;
    }


}
