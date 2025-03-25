package com.synway.datastandardmanager.service;

import com.github.pagehelper.PageInfo;
import com.synway.datastandardmanager.pojo.FilterObject;
import com.synway.datastandardmanager.pojo.PageSelectOneValue;
import com.synway.datastandardmanager.pojo.relationTableInfo;
import com.synway.datastandardmanager.pojo.synltefield.SynlteFieldExcel;
import com.synway.datastandardmanager.pojo.synltefield.SynlteFieldFilter;
import com.synway.datastandardmanager.pojo.synltefield.SynlteFieldObject;
import com.synway.datastandardmanager.pojo.synltefield.SynlteFieldParameter;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 数据元的后台接口
 *
 * @author wangdongwei
 */
public interface SynlteFieldService {

    /**
     * 获取表格中的数据
     *
     * @param parameter
     * @return
     */
    Map<String, Object> searchAllTable(SynlteFieldParameter parameter);

    /**
     * 获取筛选值
     *
     * @return
     */
    SynlteFieldFilter getFilterObject();

    /**
     * 获取搜索的提示内容
     *
     * @param searchName
     * @return
     */
    List<String> getSearchNameSuggest(String searchName);

    /**
     * 添加一条新的数据元信息
     *
     * @param synlteFieldObject
     * @return
     */
    String addObject(SynlteFieldObject synlteFieldObject);

    /**
     * 编辑一条新的数据元信息  根据fieldId 确定
     *
     * @param synlteFieldObject
     * @return
     */
    String updateObject(SynlteFieldObject synlteFieldObject);


    /**
     * 更新数据元状态
     *
     * @param list 数据元id的列表
     * @param status 状态
     * @return
     */
    String updateSynlteFieldStatus(List<String> list, String status);

    /**
     * 获取语义类型的相关下拉框信息
     *
     * @param searchName
     * @return
     */
    List<FilterObject> getSameWordList(String searchName);

    /**
     * 标识符是在数据应用中对数据元的统一标识。标识符由该数据元中文名称中每个汉字的汉语拼音首字母
     * 不区分大小写）组成.。不同数据元的标识符若出现重复，则在后面加01～99予以区别
     *
     * @param name
     * @param oldSimList
     * @return
     */
    String getSimChinese(String name, List<String> oldSimList);

    /**
     * 一些选择框需要从码表库里面查询结构
     *
     * @param name EXPRESSION_WORD：表示词
     * @return
     */
    List<FilterObject> getSelectObjectByName(String name);

    /**
     * 从码表里查询数据安全分级字段的列表
     *
     * @return
     */
    List<PageSelectOneValue> searchDataSecurityLevel();

    /**
     * 根据数据元id查询相关数据元信息
     *
     * @param fieldId 数据元id
     * @return
     */
    SynlteFieldObject searchSynlteFieldById(String fieldId);

    /**
     * 关键字搜索数据元下拉框
     *
     * @param searchText 搜索内容
     * @param fieldType 融合单位类型
     * @return
     */
    List<PageSelectOneValue> getGadsjFieldByText(String searchText,String fieldType);

    /**
     * 下载(导出)数据元
     *
     * @param response
     * @param parameter 数据元列表
     * @param name      excel文档名
     * @param object
     * @return
     */
    void downloadSynlteFieldExcel(HttpServletResponse response,
                                  SynlteFieldParameter parameter,
                                  String name,
                                  Object object);

    /**
     * 导入数据元
     *
     * @param file
     * @return
     */
    String importSynlteFieldExcel(MultipartFile file);

    /**
     * 先清空，再导入数据元
     *
     * @param file
     * @return
     */
    List<SynlteFieldExcel> clearImportSynlteFieldExcel(MultipartFile file);

    /**
     * 导出数据元sql文件
     *
     * @param parameter 数据元过滤参数
     * @param response
     * @param fileName 文件名
     * @return
     */
    void downloadSynlteFieldSql(SynlteFieldParameter parameter, HttpServletResponse response, String fileName);

    /**
     * 数据元管理页面 关联数据集
     * @param pageIndex
     * @param pageSize
     * @param fieldId 数据元唯一标识
     * @param searchName 模糊搜索
     * @return
     */
    PageInfo<relationTableInfo> getAllTableNameByFieldId(int pageIndex, int pageSize, String fieldId,String searchName);

    List<relationTableInfo> getAllTableNameByFieldId(String fieldId);

    //    /**
//     * 更新标识符
//     *
//     * @return
//     */
//    String updateSimChinese();
//
//    /**
//     * 删除数据通过fieldId
//     *
//     * @param fieldId
//     * @return
//     */
//    String delObjectByFieldId(String fieldId);
}
