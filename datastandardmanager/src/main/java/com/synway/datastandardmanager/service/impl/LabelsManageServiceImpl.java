package com.synway.datastandardmanager.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.synway.datastandardmanager.dao.standard.LabelsManageDao;
import com.synway.datastandardmanager.enums.OperateLogHandleTypeEnum;
import com.synway.datastandardmanager.exceptionhandler.ErrorCode;
import com.synway.datastandardmanager.exceptionhandler.SystemException;
import com.synway.datastandardmanager.listener.ExcelListener;
import com.synway.datastandardmanager.pojo.*;
import com.synway.datastandardmanager.pojo.labelmanage.*;
import com.synway.datastandardmanager.pojo.sourcedata.PublicDataInfo;
import com.synway.datastandardmanager.service.LabelsManageService;
import com.synway.datastandardmanager.util.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;

import javax.annotation.Resource;
import java.net.URLEncoder;
import java.text.Collator;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author wdw
 * @version 1.0
 * @date 2021/6/8 17:39
 */
@Service
@Slf4j
public class LabelsManageServiceImpl implements LabelsManageService {

    @Autowired
    private LabelsManageDao labelsManageDao;

    @Resource
    private OperateLogServiceImpl operateLogServiceImpl;

    private static Pattern pattern = Pattern.compile("[A-Z]\\d{3,}");
    private static Lock lock = new ReentrantLock();

    @Override
    public List<LabelTreeNodeVue> getLabelTreeData() {
        log.info("--------------开始查询标签的汇总树信息--------------------");
        List<OneSuggestValue> list = labelsManageDao.getAllLabelTreeDataDao();
        List<LabelTreeNodeVue> listData = new ArrayList<>();
        LabelTreeNodeVue treeRootNodeVue = new LabelTreeNodeVue();
        treeRootNodeVue.setId("");
        treeRootNodeVue.setLevel(1);
        treeRootNodeVue.setSortLevel(1);
        if (list == null || list.isEmpty()) {
            treeRootNodeVue.setLabel("标签类型(0)");
        } else {
            int count = list.stream().mapToInt(d -> Integer.parseInt(d.getValue())).sum();
            treeRootNodeVue.setLabel("标签类型(" + count + ")");
            List<LabelTreeNodeVue> childList = new ArrayList<>();
            for (OneSuggestValue data : list) {
                LabelTreeNodeVue treeNode = new LabelTreeNodeVue();
                treeNode.setId(data.getId());
                treeNode.setLevel(2);
                treeNode.setParent("");
                treeNode.setLabel(data.getName() + "(" + data.getValue() + ")");
                childList.add(treeNode);
            }
            treeRootNodeVue.setChildren(childList);
        }
        listData.add(treeRootNodeVue);
        log.info("--------------查询标签的汇总树信息结束--------------------");
        return listData;
    }

    @Override
    public List<LayuiClassifyPojo> getLabelTotalList() {
        log.info("--------------开始查询标签的统计信息--------------------");
        List<LabelSelect> labelList = labelsManageDao.searchLabelTotalList();
        if (labelList == null || labelList.isEmpty()) {
            return new ArrayList<>();
        }
        ArrayList<LayuiClassifyPojo> listData = new ArrayList<>();
        for (LabelSelect data : labelList) {
            List<LayuiClassifyPojo> secondLabelList = labelsManageDao.searchSecondLabelList(data.getId());
            LayuiClassifyPojo parentLabel = new LayuiClassifyPojo(data.getId(), data.getName() + "(" + data.getValue() + ")", secondLabelList);
            listData.add(parentLabel);
        }
        log.info("--------------查询标签的统计信息结束--------------------");
        return listData;
    }

    @Override
    public LabelManagePage getAllLabelManageData(QueryParames queryParames) {
        if (StringUtils.isBlank(queryParames.getSortOrder())) {
            queryParames.setSortOrder("desc");
        }
        if (StringUtils.equalsIgnoreCase(queryParames.getSort(), "labelLevelStr")) {
            queryParames.setSort("LABELLEVEL");
        }
        LabelManagePage labelManagePage = new LabelManagePage();
        // 获取所有的数据
        List<LabelManageData> list = labelsManageDao.getAllLabelManageData(queryParames);
        //  表头的筛选值
        // 根据查询条件中的筛选值来筛选数据
        List<FilterObject> labelLevelFilter = new ArrayList<>();
        List<FilterObject> classIdsFilter = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            list.stream().sorted(Comparator.comparing(LabelManageData::getClassId)).map(LabelManageData::getClassIdStr).distinct().forEach(d -> {
                classIdsFilter.add(new FilterObject(d, d));
            });
            list.stream().sorted(Comparator.comparingInt(LabelManageData::getLabelLevel))
                    .map(LabelManageData::getLabelLevelStr).distinct().forEach(d -> {
                        labelLevelFilter.add(new FilterObject(d, d));
                    });
            list = list.parallelStream().filter(d -> {
                boolean flag = !StringUtils.isNotBlank(queryParames.getTreeId()) ||
                        StringUtils.equalsIgnoreCase(String.valueOf(d.getLabelLevel()), queryParames.getTreeId());
                boolean flag1 = !StringUtils.isNotBlank(queryParames.getSearchName()) ||
                        StringUtils.containsIgnoreCase(d.getLabelName(), queryParames.getSearchName());
                boolean flag2 = (queryParames.getClassIdStrFilter() == null || queryParames.getClassIdStrFilter().isEmpty())
                        || queryParames.getClassIdStrFilter().contains(d.getClassIdStr());
                boolean flag3 = (queryParames.getLabelLevelStrFilter() == null || queryParames.getLabelLevelStrFilter().isEmpty())
                        || queryParames.getLabelLevelStrFilter().contains(d.getLabelLevelStr());
                return flag && flag1 && flag2 && flag3;
            }).collect(Collectors.toList());
        }
        labelManagePage.setClassIdsFilter(classIdsFilter);
        labelManagePage.setLabelLevelFilter(labelLevelFilter);
        labelManagePage.setTableList(list);
        log.info("=======标签信息查询结束========");
        return labelManagePage;
    }

    @Override
    public List<LabelSelect> getLabelTypeList() {
        List<LabelSelect> list = labelsManageDao.getLabelTypeList();
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    @Override
    public List<LabelSelect> getClassidTypeList() {
        List<LabelSelect> list = labelsManageDao.getClassidTypeList();
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    /**
     * 修订时间由后台程序确定
     *
     * @param labelManageData
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addUpdateLabelManageData(LabelManageData labelManageData) {
        if (labelManageData == null) {
            throw new NullPointerException("传入的标签对象为null");
        }
        labelManageData.setId(labelManageData.getLabelCode());
        lock.lock();
        try {
            if (StringUtils.isBlank(labelManageData.getLabelCode()) || StringUtils.isBlank(labelManageData.getLabelName())) {
                throw new NullPointerException("标签名称或代码为空");
            }
            if (labelManageData.getAddUpdateTag().equalsIgnoreCase("update")) {
                log.info("----------------------开始更新标签表中指定标签的信息-----------------");
                // 更新 根据 id值和 时间字符串，如果更新的数据大于0，表示更新成功，反之报更新失败
                int updateCount = labelsManageDao.updateLabelsManageById(labelManageData);
                if (updateCount > 0) {
                    // 发送操作日志
                    operateLogServiceImpl.labelManageSuccessLog(OperateLogHandleTypeEnum.ALTER, "资源标签管理", labelManageData.getLabelName());
                    return true;
                } else {
                    throw new NullPointerException("更新标签信息失败，请刷新到最新数据后再进行修改！！！");
                }
            } else {
                boolean isNum = StringUtils.isNumeric(labelManageData.getLabelCode());
                if (!(isNum && labelManageData.getLabelCode().length() == 4)) {
                    throw new NullPointerException("标签代码值必须为4位数字，且第一位和标签类型匹配");
                }
                boolean one = Integer.valueOf(labelManageData.getLabelCode().substring(0, 1)) == labelManageData.getLabelLevel();
                if (!one) {
                    throw new NullPointerException("标签代码值必须为4位数字，且第一位和标签类型匹配");
                }
                // 检查修改后的 code值和name值是不是已经存在，存在返回报错，提示已经存在，请重新填值
                int flag = labelsManageDao.checkExitsCodeOrName(labelManageData.getLabelCode(), labelManageData.getLabelName());
                if (flag > 0) {
                    throw new NullPointerException("标签代码值:" + labelManageData.getLabelCode() + "存在重复数据");
                }
                log.info("-----------------开始新增标签表中指定标签的信息--------------");
                labelsManageDao.insertLabelsManage(labelManageData);
                // 发送操作日志
                operateLogServiceImpl.labelManageSuccessLog(OperateLogHandleTypeEnum.ADD, "资源标签管理", labelManageData.getLabelName());
            }
        } finally {
            lock.unlock();
        }
        log.info("-----------------修改标签表中标签信息结束--------------");
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String delLabelById(String id, String labelCode) {
        if (StringUtils.isBlank(id)) {
            throw new NullPointerException("请求参数中的id信息为空");
        }
        if (StringUtils.isBlank(labelCode)) {
            throw new NullPointerException("请求参数中的labelCode信息为空");
        }
        int tableUsedCount = labelsManageDao.getLabelUsedCount(labelCode);
        if (tableUsedCount > 0) {
            throw new NullPointerException("标签[" + labelCode + "]已经被使用，不能被删除");
        }
        List<LabelManageData> list = labelsManageDao.getAllLabelManageData(new QueryParames());
        int delNum = labelsManageDao.delLabelById(id);
        if (delNum > 0){
            // 发送操作日志
            List<String> lableNames = new ArrayList<>();
            list.stream().forEach(d ->{
                if (d.getLabelCode().equalsIgnoreCase(labelCode)){
                    lableNames.add(d.getLabelName());
                }
            });
            operateLogServiceImpl.labelManageSuccessLog(OperateLogHandleTypeEnum.DELETE, "资源标签管理", String.join(",", lableNames));
        }
        return delNum > 0 ? "删除成功" : "删除失败";
    }

    @Override
    public void downloadLabelsTableExcel(HttpServletResponse response, List<LabelManageData> list, String name, Object object) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode(name, "UTF-8") + ".xlsx";
            response.setHeader("Content-disposition",
                    "attachment;filename*=utf-8''" + fileName);
            EasyExcel.write(response.getOutputStream(), object.getClass()).autoCloseStream(Boolean.FALSE)
                    .sheet("标签管理信息").doWrite(list);

        } catch (Exception e) {
            log.error("下载标签管理数据报错" + ExceptionUtil.getExceptionTrace(e));
        }
    }


    @Override
    public void downloadLabelsTableExcel(HttpServletResponse response, List<LabelManageExcel> list
            , String name, LabelManageExcel object) {
        ExcelWriter excelWriter = null;
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode(name, "UTF-8") + ".xlsx";
            response.setHeader("Content-disposition",
                    "attachment;filename*=utf-8''" + fileName);
            excelWriter = EasyExcel.write(response.getOutputStream()).build();
            WriteSheet writeSheetTwo = EasyExcel.writerSheet(0, "标签管理信息").head(LabelManageExcel.class)
                    .build();
            excelWriter.write(list, writeSheetTwo);
            WriteSheet writeSheetThree = EasyExcel.writerSheet(1, "标签码值").head(TemplateCodeValue.class)
                    .build();
            excelWriter.write(TemplateExcelUtils.getListData(), writeSheetThree);
        } catch (Exception e) {
            log.error("下载标签管理数据报错" + ExceptionUtil.getExceptionTrace(e));
        } finally {
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }

    @Override
    public String importLabelsTableExcel(MultipartFile file) {
        log.info("=============开始将文件{}导入到数据库中======", file.getName());
        ExcelListener<LabelManageExcel> listener = new ExcelListener<>();
        ArrayList<LabelManageData> successList = new ArrayList<>();
        ArrayList<LabelManageExcel> failedList = new ArrayList<>();
        List<LabelManageExcel> list = new ArrayList<>();
        try {
            list = EasyExcelUtil.readExcelUtil(file, new LabelManageExcel(), listener);
            // 这里插入每一行数据，需要验证labelcode，这个所有的都是新增的
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                LabelManageExcel labelManage = (LabelManageExcel) iterator.next();
                boolean flag = checkLabelCodeConformRule(labelManage.getLabelCode(), labelManage.getLabelLevel(), labelManage.getLabelName(), labelManage.getClassId());
                if (flag) {
                    try {
                        LabelManageData addlabelManage = new LabelManageData();
                        BeanUtils.copyProperties(addlabelManage, labelManage);
                        //验证格式
                        ValidatorUtil.checkObjectValidator(addlabelManage);
                        addlabelManage.setAddUpdateTag("add");
                        boolean addFlag = addUpdateLabelManageData(addlabelManage);
                        if (addFlag) {
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
            log.error("导入文件报错" + ExceptionUtil.getExceptionTrace(e));
            throw new NullPointerException("导入文件报错");
        }
        return "导入完成,导入成功的条数为:" + successList.size() + ",导入失败的条数为:" + failedList.size();
    }


    /**
     * 根据资源标签层级 获取 资源标资源标签码值签码值
     *
     * @param level 资源标签层级
     * @return
     */
    @Override
    public String getLabelCodeByLevel(int level) {
        String firstStr = String.valueOf(level);
        List<String> list = labelsManageDao.getLabelCodeByLevel(level);
        if (list != null && !list.isEmpty()) {
            OptionalInt maxData = list.stream().filter(d -> d.indexOf(String.valueOf(level)) == 0
                            && StringUtils.isNumeric(d))
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
    public void setLabelToList(PublicDataInfo publicDataInfo) {
        List<LabelManageData> list = new ArrayList<>();
        getLabelManageData(publicDataInfo.getSJZYBQ1(), list);
        getLabelManageData(publicDataInfo.getSJZYBQ2(), list);
        getLabelManageData(publicDataInfo.getSJZYBQ3(), list);
        getLabelManageData(publicDataInfo.getSJZYBQ4(), list);
        getLabelManageData(publicDataInfo.getSJZYBQ5(), list);
        getLabelManageData(publicDataInfo.getSJZYBQ6(), list);
        publicDataInfo.setLabels(list);
    }

    @Override
    public void setLabelColumnByList(PublicDataInfo publicDataInfo) {
        try {
            List<LabelManageData> list = publicDataInfo.getLabels();
            if (list != null && !list.isEmpty()) {
                for (LabelManageData labelManageData : list) {
                    switch (labelManageData.getLabelLevel()) {
                        case 1:
                            publicDataInfo.setSJZYBQ1(labelManageData.getLabelCode());
                            break;
                        case 2:
                            publicDataInfo.setSJZYBQ2(labelManageData.getLabelCode());
                            break;
                        case 3:
                            publicDataInfo.setSJZYBQ3(labelManageData.getLabelCode());
                            break;
                        case 4:
                            publicDataInfo.setSJZYBQ4(labelManageData.getLabelCode());
                            break;
                        case 5:
                            publicDataInfo.setSJZYBQ5(labelManageData.getLabelCode());
                            break;
                        case 6:
                            publicDataInfo.setSJZYBQ6(labelManageData.getLabelCode());
                            break;
                        default:
                            log.error("标签{},级别{}没有编写对应的回填PUBLIC_DATA_INFO表字段的方法，请确定是否正确",
                                    labelManageData.getLabelCode(), labelManageData.getLabelLevel());
                            break;
                    }
                }
            }
        } catch (Exception e) {
            log.error(ExceptionUtil.getExceptionTrace(e));
        }
    }

    @Override
    public List<PageSelectOneValue> getLabelManageDataByClassId(String classId, Integer labelLevel) {
        if (StringUtils.isBlank(classId)) {
            classId = null;
        }
        List<PageSelectOneValue> data = labelsManageDao.getLabelManageDataByClassId(classId, labelLevel);
        if (data != null) {
            data.sort((s1, s2) -> Collator.getInstance(java.util.Locale.CHINA).compare(s1.getLabel(), s2.getLabel()));
        }
        return data;
    }

    @Override
    public List<LabelManageData> getLabelManageByLabelCode(List<String> labelCodeList) {
        if (labelCodeList == null || labelCodeList.isEmpty()) {
            return new ArrayList<>();
        }
        List<LabelManageData> list = labelsManageDao.getLabelManageByLabelCode(labelCodeList);
        return list;
    }

    @Override
    public Boolean checkLabelCodeIsExist(String labelCode, Integer labelLevel) {
        log.info("=====验证同一个分类下（LABELLEVEL）标签代码值是否重复=====");
        if (StringUtils.isBlank(labelCode) && labelLevel == null) {
            throw SystemException.asSystemException(ErrorCode.DATA_IS_NULL, "标签代码值或者标签等级为空查询失败");
        }
        int labelCodeCount = labelsManageDao.checkLabelCodeIsExist(labelCode, labelLevel);
        if (labelCodeCount == 0) {
            return true;
        } else {
            return false;
        }
    }

    private boolean getLabelManageData(String labelId, List<LabelManageData> list) {
        try {
            if (StringUtils.isBlank(labelId)) {
                return false;
            }
            LabelManageData data = labelsManageDao.getLabelManageData(labelId);
            list.add(data);
        } catch (Exception e) {
            log.error(ExceptionUtil.getExceptionTrace(e));
        }
        return true;
    }

    /**
     * 生成规则：A00X，A代表层级码值（1,2,3,4,5），00x为三位流水号
     * 检查标签代码是否符合规则
     *
     * @param labelCode
     * @param labelLevel
     * @param classId
     * @return true:合规 false:不合规
     */
    private boolean checkLabelCodeConformRule(String labelCode, Integer labelLevel, String labelName, String classId) {
        if (StringUtils.isBlank(labelCode)) {
            return true;
        }
        if (!StringUtils.isNumeric(classId)) {
            return false;
        }
//        if(labelCode.indexOf(String.valueOf(labelLevel)) != -1){
//            return false;
//        }
        if (StringUtils.isBlank(labelCode) && labelLevel == null && StringUtils.isBlank(labelName)) {
            //标签类型、标签名称、标签代码值不能为空。
            return false;
        }
        //同一标签分类下的标签代码值不能重复
        int labelCodeIsExist = labelsManageDao.checkLabelCodeIsExist(labelCode, labelLevel);
        if (labelCodeIsExist > 0) {
            return false;
        }
        // 数据库中是否存在
        int flag = labelsManageDao.checkExitsCode(null, labelCode);
        if (flag > 0) {
            return false;
        }
        return true;
    }

}
