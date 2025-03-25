package com.synway.datastandardmanager.service;

import com.synway.datastandardmanager.pojo.FilterObject;
import com.synway.datastandardmanager.pojo.LayuiClassifyPojo;
import com.synway.datastandardmanager.pojo.PageSelectOneValue;
import com.synway.datastandardmanager.pojo.labelmanage.*;
import com.synway.datastandardmanager.pojo.sourcedata.PublicDataInfo;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author wdw
 * @version 1.0
 * @date 2021/6/8 17:39
 */
public interface LabelsManageService {

    /**
     * 获取标签名称的左侧树信息
     * @return
     */
    List<LabelTreeNodeVue>  getLabelTreeData();

    /**
     * 获取标签名称的统计列表
     * @return
     */
    List<LayuiClassifyPojo> getLabelTotalList();

    /**
     * 获取标签表的相关信息
     * @param queryParameter  查询参数
     * @return
     */
    LabelManagePage getAllLabelManageData(QueryParames queryParameter);

    /**
     * 标签类型的选择框
     * @return
     */
    List<LabelSelect> getLabelTypeList();

    /**
     * 常用组织分类的选择框
     * @return
     */
    List<LabelSelect> getClassidTypeList();


    /**
     * 新增/编辑 标签信息
     * @param labelManageData
     * @return
     */
    boolean addUpdateLabelManageData(LabelManageData labelManageData);


    /**
     * 根据id值删除标签数据,如果这个标签已经被使用，则不能被删除，跑出异常
     * @param id
     * @return
     */
    String delLabelById(String id, String labelCode);

    /**
     *  下载数据
     * @param response
     * @param summaryObjectTableList
     * @param name
     * @return
     */
    void downloadLabelsTableExcel(HttpServletResponse response,
                                    List<LabelManageData> summaryObjectTableList,
                                    String name,Object object);

    /**
     *  下载数据
     * @param response
     * @param summaryObjectTableList
     * @param name
     * @return
     */
    void downloadLabelsTableExcel(HttpServletResponse response,
                                  List<LabelManageExcel> summaryObjectTableList,
                                  String name,LabelManageExcel object);


    /**
     * 导入数据
     * @param file
     * @return
     */
    String importLabelsTableExcel( MultipartFile file);

    /**
     * 获取资源标签的代码值
     * @param level
     * @return
     */
    String getLabelCodeByLevel(int level);

    /**
     * 在查询过程中 标签信息插入在6个字段里面，将其信息汇总，然后放在列表中
     * @param publicDataInfo
     */
    void setLabelToList(PublicDataInfo publicDataInfo);

    /**
     * 将 列表中的数据回填到对应的字段中
     * @param publicDataInfo
     */
    void setLabelColumnByList(PublicDataInfo publicDataInfo);


    /**
     * 根据 classId信息获取 标签信息
     * @param classId
     * @return
     */
    List<PageSelectOneValue> getLabelManageDataByClassId(String classId,Integer labelLevel);

    List<LabelManageData> getLabelManageByLabelCode(List<String> labelCodeList);

    /**
     * 验证规则同一个分类下（LABELLEVEL）标签代码值是否重复
     * @param labelCode 标签代码值
     * @param labelLevel 标签等级
     * @return
     */
    Boolean checkLabelCodeIsExist(String labelCode,Integer labelLevel);

}
