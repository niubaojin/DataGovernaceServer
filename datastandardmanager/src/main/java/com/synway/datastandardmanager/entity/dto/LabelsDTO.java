package com.synway.datastandardmanager.entity.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 查询参数
 *
 * @author wdw
 * @version 1.0
 * @date 2021/6/8 19:26
 */
@Data
public class LabelsDTO implements Serializable {
    private static final long serialVersionUID = 2939852413128125848L;

    private String treeId;              //左侧树的id值
    private String searchName;          //查询内容
    private String sortOrder = "desc";  //排序的相关参数：desc/asc
    private String sort = "";           //排序字段名称
    private List<String> labelLevelStrFilter;   //标签类型的筛选中文
    private List<String> classIdStrFilter;      //常用组织分类的筛选中文

}
