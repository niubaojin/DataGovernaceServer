package com.synway.datastandardmanager.entity.vo;


import lombok.Data;

import java.util.List;

/**
 * 数据集返回结果
 *
 * @author wangdongwei
 * @date 2020/12/14 16:49
 */
@Data
public class DataSetManageVO {
    private int tableCount = 0;                               //总共有多少张表
    private List<DataSetTableInfoVO> summaryObjectTable;      //表格中的数据
    //    private List<PageSelectOneValue> usingTags;         //资源状况  启用   停用
    private List<ValueLabelVO> dataSourceFilter;                //应用系统的筛选
    private List<ValueLabelVO> dataOrganizationClassifyFilter;  //数据组织分类的筛选
    private List<ValueLabelVO> dataSourceClassifyFilter;        //数据来源分类的筛选
    private List<ValueLabelVO> objectStatesFilter;              //数据状态的筛选
    private List<ValueLabelVO> creatorFilter;                   //创建人的筛选
    private List<ValueLabelVO> updaterFilter;                   //最后修改人的筛选

}
