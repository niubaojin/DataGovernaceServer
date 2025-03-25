package com.synway.datastandardmanager.pojo.versionManage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author obito
 * @version 1.0
 * @date
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VersionManageParameter implements Serializable {
    private static final long serialVersionUID = 4212533413158719945L;

    /**
     * 输入框里面的关键词 筛选出【限定词名称】【数据集名称】【数据元名称】与输入框内容模糊匹配的记录。
     * 输入框内容为空搜索，清除筛选条件，显示初始化记录信息。
     */
    private String searchText;
    /**
     * 大版本号的筛选
     */
    private List<String> versionsList;

    /**
     * 修订人的筛选
     *
     */
    private List<String> authorList;

    /**
     * 排序字段名称
     * 默认按提交时间降序
     */
    private String sort="";

    /**
     * 排序的相关参数
     * 字段顺序  desc / asc
     */
    private String sortOrder = "desc";

    /**
     * 起始时间 格式yyyyMMdd
     */
    private String startTimeText;

    /**
     *  截止时间 格式yyyyMMdd
     */
    private String endTimeText;
}
