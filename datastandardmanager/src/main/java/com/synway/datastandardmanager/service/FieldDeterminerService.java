package com.synway.datastandardmanager.service;


import com.synway.datastandardmanager.pojo.PageSelectOneValue;
import com.synway.datastandardmanager.pojo.fielddeterminermanage.FieldDeterminer;
import com.synway.datastandardmanager.pojo.fielddeterminermanage.FieldDeterminerParameter;
import com.synway.datastandardmanager.pojo.fielddeterminermanage.FieldDeterminerTable;

import java.util.List;

/**
 * 限定词管理的相关参数
 * @author wangdongwei
 */
public interface FieldDeterminerService {


    /**
     * 页面表格的查询
     * @param parameter
     * @return
     */
    List<FieldDeterminer> searchTable(FieldDeterminerParameter parameter);


    /**
     * 获取筛选值
     * @return
     */
    FieldDeterminerTable getFilterObject();


    /**
     * 新建限定词信息
     * @param data
     * @return
     */
    String addOneData(FieldDeterminer data);

    /**
     * 更新限定词信息
     * @param data
     * @return
     */
    String upOneData(FieldDeterminer data);


    /**
     * 启用/停用 限定词
     * @param id
     * @param state
     * @param modDate
     * @return
     */
    String updateDeterminerState(String id,
                                 String state,
                                 String modDate);

    /**
     * 根据中文名获取标识符
     * @param dChinesName
     * @return
     */
    String getDName(String dChinesName);

    /**
     * 点击停用，验证标准字段信息表objectfield是否存在引用关联
     * 与字段 FieldDeterminerID  相关联
     * @param id
     * @param modDate
     * @return
     */
    boolean checkIsDeactivate(String id,
                              String modDate);


    /**
     * 获取默认的 限定词内部编码 信息
     * @return
     */
    String getDeterminerId();

    /**
     * 获取限定词状态为05的中文名称列表
     * @param searchName 模糊搜索关键字
     * @return
     */
    List<PageSelectOneValue> searchDeterminerNameList(String searchName);
}
