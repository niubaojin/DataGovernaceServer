package com.synway.datastandardmanager.pojo.synlteelement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 数据要素 前端传参数
 * @author obito
 * @version 1.0
 * @date 2021/08/05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SynlteElementParameter implements Serializable {
    private static final long serialVersionUID = 4242123341316761376L;

    /**
     * 输入框里面的关键词 筛选出限定词名称与输入框内容模糊匹配的记录。
     * 输入框内容为空搜索，清除筛选条件，显示初始化记录信息。
     */
    private String searchText;

    /**
     * 要素来源的筛选
     */
    private List<String> isElementList;

    /**
     * 主体的筛选
     */
    private String elementObject;

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
}
