package com.synway.datastandardmanager.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.synway.common.exception.SystemException;
import com.synway.datastandardmanager.constants.Common;
import com.synway.datastandardmanager.entity.dto.LabelsDTO;
import com.synway.datastandardmanager.entity.pojo.LabelsEntity;
import com.synway.datastandardmanager.entity.pojo.PublicDataInfoEntity;
import com.synway.datastandardmanager.entity.vo.*;
import com.synway.datastandardmanager.enums.ErrorCodeEnum;
import com.synway.datastandardmanager.enums.OperateLogHandleTypeEnum;
import com.synway.datastandardmanager.listener.ExcelListener;
import com.synway.datastandardmanager.mapper.FieldCodeMapper;
import com.synway.datastandardmanager.mapper.LabelCodeTableMapper;
import com.synway.datastandardmanager.mapper.LabelsMapper;
import com.synway.datastandardmanager.mapper.PublicDataInfoMapper;
import com.synway.datastandardmanager.service.ResourceLabelManageService;
import com.synway.datastandardmanager.util.EasyExcelUtil;
import com.synway.datastandardmanager.util.TemplateExcelUtil;
import com.synway.datastandardmanager.util.ValidatorUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.text.Collator;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ResourceLabelManageServiceImpl implements ResourceLabelManageService {

    @Resource
    private LabelsMapper labelsMapper;
    @Resource
    private LabelCodeTableMapper labelCodeTableMapper;
    @Resource
    private FieldCodeMapper fieldCodeMapper;
    @Resource
    private PublicDataInfoMapper publicDataInfoMapper;
    @Resource
    private OperateLogServiceImpl operateLogServiceImpl;

    @Override
    public List<TreeNodeValueVO> getLabelTreeData() {
        List<TreeNodeValueVO> listData = new ArrayList<>();
        try {
            log.info(">>>>>>开始查询标签的汇总树信息");
            List<SelectFieldVO> list = labelsMapper.getAllLabelTreeDataDao();
            TreeNodeValueVO treeRootNodeVue = new TreeNodeValueVO();
            treeRootNodeVue.setId("");
            treeRootNodeVue.setLevel(1);
            treeRootNodeVue.setSortLevel(1);
            if (list == null || list.isEmpty()) {
                treeRootNodeVue.setLabel("标签类型(0)");
            } else {
                int count = list.stream().mapToInt(d -> Integer.parseInt(d.getValue())).sum();
                treeRootNodeVue.setLabel("标签类型(" + count + ")");
                List<TreeNodeValueVO> childList = new ArrayList<>();
                for (SelectFieldVO data : list) {
                    TreeNodeValueVO treeNode = new TreeNodeValueVO();
                    treeNode.setId(data.getId());
                    treeNode.setLevel(2);
                    treeNode.setParent("");
                    treeNode.setLabel(data.getName() + "(" + data.getValue() + ")");
                    childList.add(treeNode);
                }
                treeRootNodeVue.setChildren(childList);
            }
            listData.add(treeRootNodeVue);
        } catch (Exception e) {
            log.error(">>>>>>查询标签的汇总树信息：", e);
        }
        return listData;
    }

    @Override
    public List<ValueLabelVO> getLabelTotalList() {
        ArrayList<ValueLabelVO> listData = new ArrayList<>();
        try {
            log.info(">>>>>>开始查询标签的统计信息");
            List<SelectFieldVO> labelList = labelCodeTableMapper.searchLabelTotalList();
            if (labelList == null || labelList.isEmpty()) {
                return listData;
            }
            for (SelectFieldVO data : labelList) {
                List<ValueLabelVO> secondLabelList = labelsMapper.searchSecondLabelList(data.getId());
                ValueLabelVO parentLabel = new ValueLabelVO(data.getId(), data.getName() + "(" + data.getValue() + ")", secondLabelList);
                listData.add(parentLabel);
            }
        } catch (Exception e) {
            log.error(">>>>>>查询标签的统计信息出错：", e);
        }
        return listData;
    }

    @Override
    public LabelManagePageVO getAllLabelManageData(LabelsDTO labelsDTO) {
        LabelManagePageVO labelManagePage = new LabelManagePageVO();
        try {
            if (StringUtils.isBlank(labelsDTO.getSortOrder())) {
                labelsDTO.setSortOrder("desc");
            }
            if (StringUtils.equalsIgnoreCase(labelsDTO.getSort(), "labelLevelStr")) {
                labelsDTO.setSort("LABELLEVEL");
            }
            // 获取所有的数据
            List<LabelsEntity> list = labelsMapper.getAllLabelManageData(labelsDTO);
            // 表头的筛选值；根据查询条件中的筛选值来筛选数据
            List<KeyValueVO> labelLevelFilter = new ArrayList<>();
            List<KeyValueVO> classIdsFilter = new ArrayList<>();
            if (list != null && !list.isEmpty()) {
                list.stream().sorted(Comparator.comparing(LabelsEntity::getClassId)).map(LabelsEntity::getClassIdStr).distinct().forEach(d -> {
                    classIdsFilter.add(new KeyValueVO(d, d));
                });
                list.stream().sorted(Comparator.comparingInt(LabelsEntity::getLabelLevel)).map(LabelsEntity::getLabelLevelStr).distinct().forEach(d -> {
                    labelLevelFilter.add(new KeyValueVO(d, d));
                });
                list = list.parallelStream().filter(d -> {
                    boolean flag = !StringUtils.isNotBlank(labelsDTO.getTreeId()) ||
                            StringUtils.equalsIgnoreCase(String.valueOf(d.getLabelLevel()), labelsDTO.getTreeId());
                    boolean flag1 = !StringUtils.isNotBlank(labelsDTO.getSearchName()) ||
                            StringUtils.containsIgnoreCase(d.getLabelName(), labelsDTO.getSearchName());
                    boolean flag2 = (labelsDTO.getClassIdStrFilter() == null || labelsDTO.getClassIdStrFilter().isEmpty())
                            || labelsDTO.getClassIdStrFilter().contains(d.getClassIdStr());
                    boolean flag3 = (labelsDTO.getLabelLevelStrFilter() == null || labelsDTO.getLabelLevelStrFilter().isEmpty())
                            || labelsDTO.getLabelLevelStrFilter().contains(d.getLabelLevelStr());
                    return flag && flag1 && flag2 && flag3;
                }).collect(Collectors.toList());
            }
            labelManagePage.setClassIdsFilter(classIdsFilter);
            labelManagePage.setLabelLevelFilter(labelLevelFilter);
            labelManagePage.setTableList(list);
        } catch (Exception e) {
            log.error(">>>>>>查询标签信息出错：", e);
        }
        return labelManagePage;
    }

    @Override
    public String addUpdateLabelManageData(LabelsEntity labelsEntity) {
        try {
            if (labelsEntity == null) {
                throw new NullPointerException("传入的标签对象为空");
            }
            if (StringUtils.isBlank(labelsEntity.getLabelCode()) || StringUtils.isBlank(labelsEntity.getLabelName())) {
                throw new NullPointerException("标签名称或代码为空");
            }
            labelsEntity.setId(labelsEntity.getLabelCode());
            if (labelsEntity.getAddUpdateTag().equalsIgnoreCase("update")) {
                // 更新，根据id值和时间字符串，如果更新的数据大于0，表示更新成功，反之报更新失败
                LambdaUpdateWrapper<LabelsEntity> updateWrapper = Wrappers.lambdaUpdate();
                updateWrapper.set(LabelsEntity::getLabelName, labelsEntity.getLabelName())
                        .set(LabelsEntity::getClassId, labelsEntity.getClassId())
                        .set(LabelsEntity::getLabelDescribe, labelsEntity.getLabelDescribe())
                        .set(LabelsEntity::getAuthor, labelsEntity.getAuthor())
                        .set(LabelsEntity::getModDate, new Date())
                        .eq(LabelsEntity::getLabelCode, labelsEntity.getLabelCode());
                labelsMapper.update(updateWrapper);
                // 发送操作日志
                operateLogServiceImpl.labelManageSuccessLog(OperateLogHandleTypeEnum.ALTER, "资源标签管理", labelsEntity.getLabelName());
            } else {
                boolean isNum = StringUtils.isNumeric(labelsEntity.getLabelCode());
                if (!(isNum && labelsEntity.getLabelCode().length() == 4)) {
                    throw new NullPointerException("标签代码值必须为4位数字，且第一位和标签类型匹配");
                }
                boolean one = Integer.valueOf(labelsEntity.getLabelCode().substring(0, 1)) == labelsEntity.getLabelLevel();
                if (!one) {
                    throw new NullPointerException("标签代码值必须为4位数字，且第一位和标签类型匹配");
                }
                // 检查修改后的 code值和name值是不是已经存在，存在返回报错，提示已经存在，请重新填值
                LambdaQueryWrapper<LabelsEntity> wrapper = Wrappers.lambdaQuery();
                wrapper.eq(LabelsEntity::getLabelCode, labelsEntity.getLabelCode())
                        .or().eq(LabelsEntity::getLabelName, labelsEntity.getLabelName());
                labelsMapper.selectCount(wrapper);
                if (labelsMapper.selectCount(wrapper) > 0) {
                    throw new NullPointerException("标签代码值:" + labelsEntity.getLabelCode() + "存在重复数据");
                }
                labelsEntity.setModDate(new Date());
                labelsMapper.insert(labelsEntity);
                // 发送操作日志
                operateLogServiceImpl.labelManageSuccessLog(OperateLogHandleTypeEnum.ADD, "资源标签管理", labelsEntity.getLabelName());
            }
        } catch (Exception e) {
            log.error(">>>>>>新增/修改标签信息出错：", e);
            return Common.ADD_FAIL;
        }
        return Common.ADD_SUCCESS;
    }

    @Override
    public List<SelectFieldVO> getLabelTypeList() {
        return labelCodeTableMapper.getLabelTypeList();
    }

    @Override
    public List<SelectFieldVO> getClassidTypeList() {
        return fieldCodeMapper.getClassidTypeList();
    }

    @Override
    public String delLabelById(String id, String labelCode) {
        try {
            if (StringUtils.isBlank(id)) {
                throw new NullPointerException("请求参数中的id信息为空");
            }
            if (StringUtils.isBlank(labelCode)) {
                throw new NullPointerException("请求参数中的labelCode信息为空");
            }
            LambdaQueryWrapper<PublicDataInfoEntity> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(PublicDataInfoEntity::getZyzt, "1").and(w -> w
                    .eq(PublicDataInfoEntity::getSjzybq1, labelCode).or()
                    .eq(PublicDataInfoEntity::getSjzybq2, labelCode).or()
                    .eq(PublicDataInfoEntity::getSjzybq3, labelCode).or()
                    .eq(PublicDataInfoEntity::getSjzybq4, labelCode).or()
                    .eq(PublicDataInfoEntity::getSjzybq5, labelCode).or()
                    .eq(PublicDataInfoEntity::getSjzybq6, labelCode).or());
            if (publicDataInfoMapper.selectCount(wrapper) > 0) {
                throw new NullPointerException("标签[" + labelCode + "]已经被使用，不能被删除");
            }
            int delNum = labelsMapper.deleteById(id);
            if (delNum > 0) {
                List<LabelsEntity> list = labelsMapper.getAllLabelManageData(new LabelsDTO());
                // 发送操作日志
                List<String> lableNames = new ArrayList<>();
                list.stream().forEach(d -> {
                    if (d.getLabelCode().equalsIgnoreCase(labelCode)) {
                        lableNames.add(d.getLabelName());
                    }
                });
                operateLogServiceImpl.labelManageSuccessLog(OperateLogHandleTypeEnum.DELETE, "资源标签管理", String.join(",", lableNames));
            }
        } catch (Exception e) {
            log.error(">>>>>>删除指定的标签信息失败：", e);
            return Common.DEL_FAIL;
        }
        return Common.DEL_SUCCESS;
    }

    @Override
    public void downloadLabelsTableExcel(HttpServletResponse response, List<LabelsEntity> list, String name, Object object) {
        try {
            log.info(">>>>>>开始下载标签表的相关信息");
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode(name, "UTF-8") + ".xlsx";
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName);
            EasyExcel.write(response.getOutputStream(), object.getClass()).autoCloseStream(Boolean.FALSE)
                    .sheet("标签管理信息").doWrite(list);
            log.info(">>>>>>下载标签表的相关信息结束");
        } catch (Exception e) {
            log.error(">>>>>>下载标签管理数据报错：", e);
        }
    }

    @Override
    public String importLabelsTableExcel(MultipartFile file) {
        log.info(">>>>>>开始将文件：{}导入到数据库中", file.getName());
        ExcelListener<LabelsEntity> listener = new ExcelListener<>();
        ArrayList<LabelsEntity> successList = new ArrayList<>();
        ArrayList<LabelsEntity> failedList = new ArrayList<>();
        List<LabelsEntity> list = new ArrayList<>();
        try {
            list = EasyExcelUtil.readExcelUtil(file, new LabelsEntity(), listener);
            // 这里插入每一行数据，需要验证labelcode，这个所有的都是新增的
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                LabelsEntity labelManage = (LabelsEntity) iterator.next();
                boolean flag = checkLabelCodeConformRule(labelManage.getLabelCode(), labelManage.getLabelLevel(), labelManage.getLabelName());
                if (flag) {
                    try {
                        LabelsEntity addlabelManage = new LabelsEntity();
                        BeanUtils.copyProperties(addlabelManage, labelManage);
                        //验证格式
                        ValidatorUtil.checkObjectValidator(addlabelManage);
                        addlabelManage.setAddUpdateTag("add");
                        if (addUpdateLabelManageData(addlabelManage).equalsIgnoreCase(Common.ADD_SUCCESS)) {
                            successList.add(addlabelManage);
                            iterator.remove();
                        } else {
                            failedList.add(labelManage);
                            log.error("标签[" + addlabelManage.getLabelName() + "]插入失败");
                        }
                    } catch (Exception e) {
                        failedList.add(labelManage);
                        log.error("标签[" + labelManage.getLabelName() + "]插入失败：" + e.getMessage());
                    }
                } else {
                    failedList.add(labelManage);
                }
            }
        } catch (Exception e) {
            failedList.addAll(list);
            log.error(">>>>>>导入文件报错：", e);
            throw new NullPointerException("导入文件报错");
        }
        return "导入完成,导入成功的条数为:" + successList.size() + ",导入失败的条数为:" + failedList.size();
    }

    /**
     * 生成规则：A00X，A代表层级码值（1,2,3,4,5），00x为三位流水号
     * 检查标签代码是否符合规则
     *
     * @return true:合规 false:不合规
     */
    private boolean checkLabelCodeConformRule(String labelCode, Integer labelLevel, String labelName) {
        if (StringUtils.isBlank(labelCode)) {
            return true;
        }
        if (StringUtils.isBlank(labelCode) && labelLevel == null && StringUtils.isBlank(labelName)) {
            //标签类型、标签名称、标签代码值不能为空。
            return false;
        }
        //同一标签分类下的标签代码值不能重复
        LambdaQueryWrapper<LabelsEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(LabelsEntity::getLabelCode, labelCode);
        wrapper.eq(LabelsEntity::getLabelLevel, labelLevel);
        labelsMapper.selectCount(wrapper);
        if (labelsMapper.selectCount(wrapper) > 0) {
            return false;
        }
        // 数据库中是否存在
        wrapper = Wrappers.lambdaQuery();
        wrapper.eq(LabelsEntity::getLabelCode, labelCode);
        return labelsMapper.selectCount(wrapper) > 0 ? false : true;
    }

    @Override
    public void exportLabelsErrorMessage(HttpServletResponse response, List<LabelsEntity> labelsEntities, String name, LabelsEntity object) {
        ExcelWriter excelWriter = null;
        try {
            if (labelsEntities == null || labelsEntities.isEmpty()) {
                log.info(">>>>>>入参为空");
                return;
            }
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode(name, "UTF-8") + ".xlsx";
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName);
            excelWriter = EasyExcel.write(response.getOutputStream()).build();
            WriteSheet writeSheetTwo = EasyExcel.writerSheet(0, "标签管理信息").head(LabelsEntity.class).build();
            excelWriter.write(labelsEntities, writeSheetTwo);
            WriteSheet writeSheetThree = EasyExcel.writerSheet(1, "标签码值").head(TemplateCodeValueVO.class).build();
            excelWriter.write(TemplateExcelUtil.getListData(), writeSheetThree);
        } catch (Exception e) {
            log.error(">>>>>>下载标签管理数据报错：", e);
        } finally {
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }

    @Override
    public String getLabelCodeByLevel(int level) {
        String firstStr = String.valueOf(level);
        LambdaQueryWrapper<LabelsEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(LabelsEntity::getLabelLevel, level);
        List<LabelsEntity> labelsEntities = labelsMapper.selectList(wrapper);
        List<String> list = labelsEntities.stream().map(LabelsEntity::getLabelCode).distinct().collect(Collectors.toList());
        if (list != null && !list.isEmpty()) {
            OptionalInt maxData = list.stream().filter(d -> d.indexOf(String.valueOf(level)) == 0 && StringUtils.isNumeric(d))
                    .mapToInt(d -> Integer.parseInt(d.substring(1))).max();
            if (maxData.isPresent()) {
                // 几位数不定死，最小3个
                int dbMaxNum = maxData.getAsInt();
                return firstStr + String.format("%03d", (1 + dbMaxNum));
            } else {
                return firstStr + "001";
            }
        } else {
            return firstStr + "001";
        }
    }

    @Override
    public LabelManageSelectVO getLabelManageDataByClassId(String classId, Integer labelLevel) {
        if (labelLevel == null) {
            throw new NullPointerException("labelLevel不能为空");
        }
        List<KeyValueVO> data = labelsMapper.getLabelManageDataByClassId(classId, labelLevel);
        if (data != null) {
            data.sort((s1, s2) -> Collator.getInstance(java.util.Locale.CHINA).compare(s1.getLabel(), s2.getLabel()));
        }
        LabelManageSelectVO labelManageSelect = new LabelManageSelectVO();
        labelManageSelect.setLabelLevel(labelLevel);
        labelManageSelect.setList(data);
        return labelManageSelect;
    }

    @Override
    public List<LabelsEntity> getLabelManageByLabelCode(List<String> labelCodeList) {
        if (labelCodeList == null || labelCodeList.isEmpty()) {
            return new ArrayList<>();
        }
        return labelsMapper.getLabelManageByLabelCode(labelCodeList);
    }

    @Override
    public Boolean checkLabelCodeIsExist(String labelCode, Integer labelLevel) {
        log.info(">>>>>>验证同一个分类下（LABELLEVEL）标签代码值是否重复");
        if (StringUtils.isBlank(labelCode) && labelLevel == null) {
            throw SystemException.asSystemException(ErrorCodeEnum.DATA_IS_NULL, "标签代码值或者标签等级为空查询失败");
        }
        LambdaQueryWrapper<LabelsEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(LabelsEntity::getLabelCode, labelCode);
        wrapper.eq(LabelsEntity::getLabelLevel, labelLevel);
        return labelsMapper.selectCount(wrapper) == 0 ? true : false;
    }

}
