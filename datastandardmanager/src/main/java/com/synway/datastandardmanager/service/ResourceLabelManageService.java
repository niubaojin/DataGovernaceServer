package com.synway.datastandardmanager.service;

import com.synway.datastandardmanager.entity.dto.LabelsDTO;
import com.synway.datastandardmanager.entity.pojo.LabelsEntity;
import com.synway.datastandardmanager.entity.vo.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ResourceLabelManageService {

    /**
     * 获取标签名称的左侧树信息
     */
    List<TreeNodeValueVO> getLabelTreeData();

    /**
     * 获取标签名称的统计列表
     */
    List<ValueLabelVO> getLabelTotalList();

    /**
     * 获取标签表的相关信息
     */
    LabelManagePageVO getAllLabelManageData(LabelsDTO labelsDTO);

    /**
     * 新增/编辑 标签信息
     */
    String addUpdateLabelManageData(LabelsEntity labelsEntity);

    /**
     * 标签类型的选择框
     */
    List<SelectFieldVO> getLabelTypeList();

    /**
     * 常用组织分类的选择框
     */
    List<SelectFieldVO> getClassidTypeList();

    /**
     * 根据id值删除标签数据,如果这个标签已经被使用，则不能被删除，跑出异常
     */
    String delLabelById(String id, String labelCode);

    /**
     * 下载数据
     */
    void downloadLabelsTableExcel(HttpServletResponse response, List<LabelsEntity> summaryObjectTableList, String name, Object object);

    /**
     * 导入数据
     */
    String importLabelsTableExcel(MultipartFile file);

    /**
     * 下载数据
     */
    void exportLabelsErrorMessage(HttpServletResponse response, List<LabelsEntity> labelsEntities, String name, LabelsEntity object);

    /**
     * 获取资源标签的代码值
     */
    String getLabelCodeByLevel(int level);

    /**
     * 根据 classId信息获取 标签信息
     */
    LabelManageSelectVO getLabelManageDataByClassId(String classId, Integer labelLevel);

    List<LabelsEntity> getLabelManageByLabelCode(List<String> labelCodeList);

    /**
     * 验证规则同一个分类下（LABELLEVEL）标签代码值是否重复
     *
     * @param labelCode  标签代码值
     * @param labelLevel 标签等级
     */
    Boolean checkLabelCodeIsExist(String labelCode, Integer labelLevel);

}
