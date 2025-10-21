package com.synway.datastandardmanager.entity.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * 数据定义管理 前端传递的参数
 * @author obito
 * @version 1.0
 * @date 2022/01/06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataDefinitionDTO implements Serializable {
    private static final long serialVersionUID = 4242123356723454352L;

    /**
     * 输入框里面的关键词 筛选出限定词名称与输入框内容模糊匹配的记录。
     * 输入框内容为空搜索，清除筛选条件，显示初始化记录信息。
     */
    private String searchText;

    /**
     * 关键字搜索的类型 :数据集中文名、数据集英文名、数据集编码、数据来源协议(objectChineseName、objectEnglishName、tableId、sourceId)
     */
    private String searchType;

    /**
     * 排序字段名称
     * 默认按提交时间降序
     */
    private String sort="modDate";

    /**
     * 排序的相关参数
     * 字段顺序  desc / asc
     */
    private String sortOrder = "desc";

    /**
     * 分页当前页数
     */
    @NotNull
    private Integer pageIndex;

    /**
     * 分页每页条数
     */
    @NotNull
    private Integer pageSize;

    //数据源id
    private String resId;
    //项目空间名称
    private String projectName;
    //表英文名
    private String tableNameEn;

}
