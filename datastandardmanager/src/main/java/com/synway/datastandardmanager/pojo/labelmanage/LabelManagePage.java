package com.synway.datastandardmanager.pojo.labelmanage;

import com.synway.datastandardmanager.pojo.FilterObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @author wdw
 * @version 1.0
 * @date 2021/6/8 22:28
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LabelManagePage {

    /**
     * 表数据
     */
    private List<LabelManageData> tableList;

    /**
     * 标签类型的筛选
     */
    private List<FilterObject> labelLevelFilter;

    /**
     * 常用组织分类的筛选
     */
    private List<FilterObject> classIdsFilter;
}
