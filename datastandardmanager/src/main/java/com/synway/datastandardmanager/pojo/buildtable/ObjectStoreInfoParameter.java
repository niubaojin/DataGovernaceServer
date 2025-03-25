package com.synway.datastandardmanager.pojo.buildtable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 建表信息管理的参数
 * @author liuxinpeng
 * @date 2022/04/11 19:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ObjectStoreInfoParameter implements Serializable {
    private static final long serialVersionUID = 12425654257542L;

    /**
     * 关键字类型 值有all/objectName/standTableName/tableId/realTableName
     */
    private String searchType;

    /**
     * 输入框里面的关键词 筛选出机构名称与机构代码的记录。
     * 输入框内容为空搜索，清除筛选条件，显示初始化记录信息。
     */
    private String searchText;

    //平台类型筛选
    private List<Integer> storeTypeList;

    /**
     * 数据源筛选
     */
    private List<String> dataIdList;

    /**
     * 项目空间(存储位置筛选)
     */
    private List<String> projectNameList;

    /**
     * 表状态筛选
     */
    private List<String> tableStatusList;

    /**
     * 创建人筛选
     */
    private List<String> creatorList;

    /**
     * 分页当前页数
     */
    private Integer pageIndex;

    /**
     * 分页每页条数
     */
    private Integer pageSize;

    //排序字段
    private String sort="tableCreateTime";

    //字段顺序  desc / asc
    private String sortOrder = "desc";


}
