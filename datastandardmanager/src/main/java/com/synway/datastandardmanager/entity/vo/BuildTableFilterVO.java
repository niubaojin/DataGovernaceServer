package com.synway.datastandardmanager.entity.vo;

import lombok.Data;

import java.util.List;

/**
 * 建表信息管理筛选pojo
 * @author
 * @date 2021/7/22 11:46
 */
@Data
public class BuildTableFilterVO {
    //平台类型筛选
    private List<ValueLabelChildrenVO> storeTypeList;
    //存储数据源筛选
    private List<ValueLabelChildrenVO> resNameList;
    //项目空间筛选
    private List<ValueLabelChildrenVO> projectList;
    //创建人
    private List<ValueLabelChildrenVO> creatorList;
}
