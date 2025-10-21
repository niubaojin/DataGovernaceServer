package com.synway.datastandardmanager.service;

import com.synway.datastandardmanager.entity.dto.StandardDictionaryDTO;
import com.synway.datastandardmanager.entity.pojo.StandardizeOriginalDFEntity;
import com.synway.datastandardmanager.entity.pojo.StandardizeOriginalDictEntity;
import com.synway.datastandardmanager.entity.vo.KeyValueVO;
import com.synway.datastandardmanager.entity.vo.SelectFieldVO;
import com.synway.datastandardmanager.entity.vo.TreeNodeValueVO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface OriginalDictionaryService {

    /**
     * 获取左侧树信息
     */
    List<TreeNodeValueVO> getLeftTreeInfo();

    /**
     * 增加一条原始字典信息
     */
    String addOrUpdateOneOriginalDictionary(StandardizeOriginalDictEntity originalDictionaryPojo);

    /**
     * 删除一条原始字典信息
     *
     * @param id             字典表id
     * @param dictionaryName 原始字典名称
     */
    String deleteOneOriginalDictionary(String id, String dictionaryName);

    /**
     * 根据字典id和名称查询原始字典信息
     *
     * @param id             字典表id
     * @param dictionaryName 原始字典名称
     */
    StandardizeOriginalDictEntity searchDictionaryByIdAndName(String id, String dictionaryName);

    /**
     * 查询标准字典下拉框
     *
     * @param searchText 关键字
     */
    List<SelectFieldVO> searchStandardDictionaryListInfo(String searchText);

    /**
     * 根据codeId获取对应的标准代码列表
     *
     * @param codeId 码表id
     */
    List<KeyValueVO> searchDictionaryValueListByCodeId(String codeId);

    /**
     * 下载原始字典表
     *
     * @param response 相应体
     * @param data     数据
     * @param name     文件名
     */
    void downloadDictionaryFieldExcel(HttpServletResponse response, StandardDictionaryDTO data, String name, Object object);

    /**
     * 下载原始字典项信息
     *
     * @param response
     * @param name     文件名
     */
    void downloadDictionaryExcelTemplate(HttpServletResponse response, String name);

    /**
     * 导入原始字典表
     *
     * @param file 文件
     * @param id   原始字典表的id
     */
    List<StandardizeOriginalDFEntity> importDictionaryFieldExcel(MultipartFile file, String id);

    /**
     * 仓库探查分析中字段探查原始字典的下拉内容
     */
    List<KeyValueVO> getOriginalDictionaryNameList();

}
