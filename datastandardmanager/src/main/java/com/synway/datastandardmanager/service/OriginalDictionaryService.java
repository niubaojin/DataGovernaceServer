package com.synway.datastandardmanager.service;


import com.synway.datastandardmanager.pojo.OneSuggestValue;
import com.synway.datastandardmanager.pojo.PageSelectOneValue;
import com.synway.datastandardmanager.pojo.labelmanage.LabelTreeNodeVue;
import com.synway.datastandardmanager.pojo.originalDictionary.OriginalDictionaryFieldPojo;
import com.synway.datastandardmanager.pojo.originalDictionary.OriginalDictionaryParameter;
import com.synway.datastandardmanager.pojo.originalDictionary.OriginalDictionaryPojo;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 原始字典Service接口
 * @author obito
 * @version 1.0
 * @date
 */
public interface OriginalDictionaryService {

    /**
     * 获取左侧树信息
     * @return
     */
    List<LabelTreeNodeVue> getLeftTreeInfo();
    /**
     * 增加一条原始字典信息
     * @param originalDictionaryPojo
     * @return
     */
    String addOrUpdateOneOriginalDictionary(OriginalDictionaryPojo originalDictionaryPojo) throws Exception;

    /**
     * 删除一条原始字典信息
     * @param id 字典表id
     * @param dictionaryName 原始字典名称
     * @return
     */
    String deleteOneOriginalDictionary(String id,String dictionaryName);

    /**
     * 根据字典id和名称查询原始字典信息
     * @param id 字典表id
     * @param dictionaryName 原始字典名称
     * @return
     */
    OriginalDictionaryPojo searchDictionaryByIdAndName(String id,String dictionaryName);

    /**
     * 查询标准字典下拉框
     * @param searchText 关键字
     * @return
     */
    List<OneSuggestValue> searchStandardDictionaryListInfo(String searchText);

    /**
     * 通过
     * @param codeId 码表id
     * @return
     */
    List<PageSelectOneValue> searchDictionaryValueListByCodeId(String codeId);

    /**
     * 下载原始字典表
     * @param response 相应体
     * @param data 数据
     * @param name 文件名
     * @param object
     * @return
     */
    void downloadDictionaryFieldExcel(HttpServletResponse response, OriginalDictionaryParameter data,
                                      String name, Object object);

    /**
     * 导入原始字典表
     * @param file 文件
     * @param id 原始字典表的id
     * @return
     */
    List<OriginalDictionaryFieldPojo> importDictionaryFieldExcel(MultipartFile file, String id);

    /**
     * 下载原始字典项信息
     *
     * @param response
     * @param list 模板数据
     * @param name 文件名
     * @param originalDictionaryFieldPojo 模板类
     */
    void downloadDictionaryExcelTemplate(HttpServletResponse response, List<OriginalDictionaryFieldPojo> list, String name,
                                         OriginalDictionaryFieldPojo originalDictionaryFieldPojo);

    /**
     * 仓库探查分析中字段探查原始字典的下拉内容
     *
     * @return
     */
    List<PageSelectOneValue> getOriginalDictionaryNameList();
}
