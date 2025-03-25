package com.synway.datastandardmanager.pojo.fielddeterminermanage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangdongwei
 * @date 2021/7/16 9:20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FieldDeterminerParameter implements Serializable {
    private static final long serialVersionUID = -494957200231738682L;

    /**
     * 输入框里面的关键词 筛选出限定词名称与输入框内容模糊匹配的记录。
     * 输入框内容为空搜索，清除筛选条件，显示初始化记录信息。
     */
    private String searchName;

    /**
     *  是否为部标标准 1：是  0：否  对应了 限定词类型
     */
    private List<Integer> determinerTypeList;

    /**
     * 状态 的筛选  01：新建  05：发布  07：废弃
     */
    private List<String> determinerStateList;

    /**
     * 版本的筛选
     */
    private List<String> versionList;

    /**
     * 选择提交的机构
     */
    private List<String> regOrgList;

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
