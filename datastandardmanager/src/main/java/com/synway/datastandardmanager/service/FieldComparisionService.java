package com.synway.datastandardmanager.service;

import com.synway.datastandardmanager.pojo.ColumnComparisionPage;
import com.synway.datastandardmanager.pojo.ColumnComparisionSearch;
import com.synway.datastandardmanager.pojo.SaveColumnComparision;

/**
 * @author wangdongwei
 */
public interface FieldComparisionService {


    /**
     * 字段对比的相关信息
     * @param query
     * @return
     * @throws Exception
     */
    ColumnComparisionPage getColumnComparisionPage(ColumnComparisionSearch query) throws Exception;

    /**
     *  如果需要审批，则先在审批流程中保存数据
     * @param data
     * @return
     * @throws Exception
     */
    String saveOrUpdateApprovalInfoService(SaveColumnComparision data) throws Exception;

    /**
     * 根据相关信息生成对应的建表sql
     * @param data
     * @return
     * @throws Exception
     */
    String saveFieldComparison(SaveColumnComparision data) throws Exception;


    /**
     * 检查这张表是否正在使用 主要是到 数据加工血缘中检查表是否存在
     * 存在则表示 这张表正在使用，返回的字符创中展示具体的使用情况
     * eg：1：表[syndw_dev.ysk_0104_cssj091711_1111]正在工作流节点[syndm.零星贩毒]中使用
     *     2：表[synods.nb_tab_mee]正在工作流节点[syndm.零星贩毒]中使用
     *
     * @param tableNameEn  表英文名 项目名.表名
     * @return
     */
    String checkTableUsage(String tableNameEn);
}
