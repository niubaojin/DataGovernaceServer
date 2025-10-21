package com.synway.datastandardmanager.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 单位机构管理 前端传参数
 * @author obito
 * @version 1.0
 * @date 2022/03/07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitOrganizationDTO implements Serializable {
    private static final long serialVersionUID = 4242123343516761376L;

    /**
     * 输入框里面的关键词 筛选出机构名称与机构代码的记录。
     * 输入框内容为空搜索，清除筛选条件，显示初始化记录信息。
     */
    private String searchText;

    //机构代码
    private String unitCode;

    //机构类型
    private Integer unitType;


    //分页当前页数
    private Integer pageIndex;

    //分页每页条数
    private Integer pageSize;

    //排序
    private String sortOrder;

    //机构级别筛选列表
    private List<String> unitLevelFilterList;
}
