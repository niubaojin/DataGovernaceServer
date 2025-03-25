package com.synway.datastandardmanager.pojo.labelmanage;

import com.synway.datastandardmanager.pojo.summaryobjectpage.ReceiveTag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 查询参数
 * @author wdw
 * @version 1.0
 * @date 2021/6/8 19:26
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class QueryParames implements Serializable {
    private static final long serialVersionUID = 2939852413128125848L;
    /**
     * 左侧树的id值
     */
    private String treeId;
    /**
     * 查询内容
     */
    private String searchName;
    /**
     * 排序的相关参数
     * 字段顺序  desc / asc
     */
    private String sortOrder = "desc";
    /**
     * 字段名称
     */
    private String sort="";

    /**
     * 标签类型的筛选中文
     */
    private List<String> labelLevelStrFilter ;

    /**
     * 常用组织分类的筛选中文
     */
    private List<String> classIdStrFilter ;

}
