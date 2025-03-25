package com.synway.datarelation.service.datablood;


import com.synway.datarelation.pojo.common.ApplicationFilterQuery;
import com.synway.datarelation.pojo.common.ApplicationFilterResult;
import com.synway.datarelation.pojo.common.BloodManagementQuery;
import com.synway.datarelation.pojo.common.TreeNode;
import com.synway.datarelation.pojo.databloodline.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 *   应用血缘的查询
 * @author wangdongwei
 */
public interface ApplicationSystemNodeService {

    /**
     * 获取应用血缘的信息
     * @param dataBloodlineQueryParams
     * @param nodeId
     * @param clearFlag
     * @return
     */
    TreeNode getApplicationSystemNodeQuery(DataBloodlineQueryParams dataBloodlineQueryParams, int nodeId, Boolean clearFlag);

    /**
     * 查询应用血缘的节点信息
     * @param queryData
     * @param showType
     * @param nodeShow
     * @return
     */
    List<DataBloodlineNode.BloodNode> getApplicationSystemNode(QueryDataBloodlineTable queryData, String showType, Boolean nodeShow);

    /**
     *   查询应用血缘的相关信息 ,目前的需求是 主模块/子模块 点击之后都能查询到对应的应用血缘信息
     * @param queryData
     * @param showType
     * @param nodeShow
     * @return
     */
    DataBloodlineNode getApplicationSystemNodeLink(QueryDataBloodlineTable queryData, String showType, Boolean nodeShow);


    /**
     * 获取 应用系统个数以及 涉及表的个数
     * @return
     */
    SummaryData searchSummaryData();


    /**
     *  应用血缘管理页面的查询内容
     * @param bloodManagementQuery
     * @return
     */
    ApplicationTableManage searchApplicationBloodTable(BloodManagementQuery bloodManagementQuery);


    /**
     * 添加一条应用血缘的记录，先添加到数据库中，然后再重新查询缓存
     * @param applicationSystemTable
     * @return
     */
    boolean addApplicationBlood(ApplicationSystemTable applicationSystemTable)  throws Exception;

    /**
     * 删除指定的数据
     * @param applicationSystemTable
     * @return
     */
    boolean deleteApplicationBlood(ApplicationSystemTable applicationSystemTable,boolean flag);

    /**
     * 下载模板文件
     * @param response
     */
    void downloadTemplateExcel(HttpServletResponse response);


    /**
     * 导出表格中的数据
     * @param response
     * @param applicationSystemTableList
     */
    void exportTableExcel(HttpServletResponse response,List<ApplicationSystemTable> applicationSystemTableList);


    /**
     *  搜索提示框的内容 支持物理表名和中文表名
     * @param searchValue
     * @return
     */
    List<String> queryConditionSuggestion(String searchValue);

    /**
     * 新增应用血缘管理中 系统名称  一级模块 二级模块点击后的筛选值
     * @param applicationFilterQuery
     * @return
     */
    ApplicationFilterResult queryApplicationFilterQuery(ApplicationFilterQuery applicationFilterQuery);

    /**
     * 根据表英文名查询出表中文名
     * @param tableName
     * @return
     */
    String queryTableChByEn(String tableName);



    /**
     * 导入应用血缘数据
     * @param list
     * @return
     */
    public List importApplicationSystemExcel(List<Map> list);

    /**
     * 导出应用血缘报错的信息
     * @param param
     * @param response
     */
    void exportApplicationSystemError(List<Map<String, String>> param, HttpServletResponse response);

}
