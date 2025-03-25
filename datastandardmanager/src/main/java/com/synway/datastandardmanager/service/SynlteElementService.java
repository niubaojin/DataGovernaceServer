package com.synway.datastandardmanager.service;

import com.synway.datastandardmanager.pojo.FilterObject;
import com.synway.datastandardmanager.pojo.LayuiClassifyPojo;
import com.synway.datastandardmanager.pojo.labelmanage.LabelManageData;
import com.synway.datastandardmanager.pojo.labelmanage.LabelManageExcel;
import com.synway.datastandardmanager.pojo.labelmanage.LabelTreeNodeVue;
import com.synway.datastandardmanager.pojo.synlteelement.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * 数据元素Service接口
 * @author obito
 * @version 1.0
 * @date
 */
public interface SynlteElementService {
    /**
     * 获取所有数据要素的信息 页面表格
     * @param synlteElementParameter
     * @return
     */
    List<SynlteElementVO> searchAllElement(SynlteElementParameter synlteElementParameter);

    /**
     * 新建数据要素信息
     * @param data
     * @return
     */
    String addOneData(SynlteElement data);

    /**
     * 更新数据要素信息
     * @param data
     * @return
     */
    String upOneData(SynlteElementVO data);

    /**
     * 查询数据要素左侧树主体信息
     * @return
     */
    List<LabelTreeNodeVue> searchElementObject();

    /**
     * 根据elementCode删除数据要素
     * @param elementCode
     * @return
     */
    String deleteElementByCode(String elementCode);

    /**
     * 生成方式第一行数据元查询 过滤非"wb"开头
     * @param searchName
     * @return
     */
    List<SelectField> searchSynlteField(String searchName);

    /**
     * 生成方式第二行数据元查询 过滤空的codeId的信息
     * @param searchName
     * @return
     */
    List<SelectField> searchSecondField(String searchName);

    /**
     * 根据codeId 从元素代码集中取值
     * @param codeId
     * @return
     */
    List<SelectField> searchAnotherField(String codeId);

    /**
     * 根据elementCode 查询标准是否使用
     * @param elementCode
     * @return
     */
    Boolean checkIsRelevance(String elementCode);

    /**
     * 新增时主体信息下拉列表
     * @return
     */
    List<SelectField> searchAllObject();


    /**
     * 查询数据要素名称时 下拉列表选择提示框
     * @param searchName
     * @return
     */
    List<String> searchElementChname(String searchName);


    /**
     * 下载数据
     * @param
     * @return
     */
    void downloadAllElementTableExcel(HttpServletResponse response,
                                  List<SynlteElement> elementList,
                                  String name, Object object);


    /**
     * 导入数据
     * @param file
     * @return
     */
    List<SynlteElement> importElementTableExcel( MultipartFile file);

    /**
     *  导入失败时 下载数据
     * @param response
     * @param summaryObjectTableList
     * @param name
     * @return
     */
    void downloadErrorElementTableExcel(HttpServletResponse response,
                                  List<SynlteElement> summaryObjectTableList,
                                  String name,SynlteElement object);

    /**
     *  下载模板数据
     * @param response
     * @param summaryObjectTableList
     * @param name
     * @return
     */
    void downloadElmentTemplateTableExcel(HttpServletResponse response,
                                  List<SynlteElement> summaryObjectTableList,
                                  String name,SynlteElement object);

    /**
     *  查询数据来源码值
     * @return
     */
    List<FilterObject> searchIsElement();

    /**
     * 根据sameId查询语义类型
     * @param sameId
     * @return
     */
    String searchSameWord(String sameId);

    /**
     * 数据定义页面筛选条件
     * @return
     */
    List<LayuiClassifyPojo> searchElementTotal();



}
