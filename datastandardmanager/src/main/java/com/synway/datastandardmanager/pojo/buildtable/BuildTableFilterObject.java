package com.synway.datastandardmanager.pojo.buildtable;

import com.synway.datastandardmanager.pojo.FilterObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 建表信息管理筛选pojo
 * @author
 * @date 2021/7/22 11:46
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuildTableFilterObject {

    //平台类型筛选
    private List<FilterObject> storeTypeList;

    //存储数据源筛选
    private List<FilterObject> resNameList;

    //项目空间筛选
    private List<FilterObject> projectList;

    //创建人
    private List<FilterObject> creatorList;
}
