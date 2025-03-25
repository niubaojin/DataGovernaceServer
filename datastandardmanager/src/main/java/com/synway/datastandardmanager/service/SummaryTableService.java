package com.synway.datastandardmanager.service;

import com.synway.datastandardmanager.pojo.PageSelectOneValue;
import com.synway.datastandardmanager.pojo.summaryobjectpage.SummaryObjectTable;
import com.synway.datastandardmanager.pojo.summaryobjectpage.SummaryQueryParams;
import com.synway.datastandardmanager.pojo.summaryobjectpage.SummaryTable;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 汇总页面的查询接口
 * @author wangdongwei
 */
public interface SummaryTableService {

    /**
     * 查询汇总表格的相关数据
     * @param summaryQueryParams
     * @return
     * @throws Exception
     */
    SummaryTable searchSummaryTable(SummaryQueryParams summaryQueryParams) throws Exception;

    /**
     * 根据选择的数据资源目录的两大分类之一获取对应的一级分类信息
     * @param mainClassify
     * @return
     * @throws Exception
     */
    List<PageSelectOneValue> getPrimaryClassifyData(String mainClassify) throws Exception;


    /**
     * 根据选择的数据资源目录的三大分类之一和 一级分类信息 来获取二级分类信息
     * @param mainClassify  主类名称
     * @param primaryClassifyCode   一级分类code
     * @return
     * @throws Exception
     */
    List<PageSelectOneValue> getSecondaryClassifyData(String mainClassify,String primaryClassifyCode) throws Exception;

    /**
     * 根据选择的二级分类来获取三级分类信息
     * @param primaryClassifyCode  二级分类码表
     * @param secondClassifyCode   三级分类码表
     * @return
     * @throws Exception
     */
    List<PageSelectOneValue> getThreeClassifyData(String primaryClassifyCode,String secondClassifyCode) throws Exception;


    /**
     *  获取资源状态的信息
     * @return
     * @throws Exception
     */
    List<PageSelectOneValue> getResourceStatus() throws Exception;




    /**
     * 搜索提示框的内容  支持数据名、真实表名、资源标识模糊匹配
     * @param searchValue
     * @return
     */
    List<String> queryConditionSuggestion(String searchValue);



//    /**
//     * 导汇总出列表中的数据
//     * @param response
//     * @param summaryObjectTableList  查询参数
//     */
//    void downloadSummaryTableExcel(HttpServletResponse response,List<SummaryObjectTable> summaryObjectTableList);

}
