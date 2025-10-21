package com.synway.datastandardmanager.entity.vo;

import com.synway.datastandardmanager.entity.pojo.LabelsEntity;
import lombok.Data;

import java.util.List;

/**
 * @author wdw
 * @version 1.0
 * @date 2021/6/8 22:28
 */
@Data
public class LabelManagePageVO {

    /**
     * 表数据
     */
    private List<LabelsEntity> tableList;

    /**
     * 标签类型的筛选
     */
    private List<KeyValueVO> labelLevelFilter;

    /**
     * 常用组织分类的筛选
     */
    private List<KeyValueVO> classIdsFilter;

}
