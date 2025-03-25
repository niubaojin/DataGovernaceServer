package com.synway.datastandardmanager.pojo.fielddeterminermanage;

import com.synway.datastandardmanager.pojo.FilterObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangdongwei
 * @date 2021/7/16 9:43
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FieldDeterminerTable implements Serializable {
    private static final long serialVersionUID = 2221157135410553458L;

    /**
     *  状态 的筛选  01：新建  05：发布  07：废弃
     */
    private List<FilterObject>  determinerStateFilter;


    /**
     *  部标标准的筛选   1：是  0：否
     */
    private List<FilterObject>  determinerTypeFilter;



    /**
     *  版本的筛选
     */
    private List<FilterObject>  versionFilter;

    /**
     * 提交机构 的筛选
     */
    private List<FilterObject> regOrgFilter;


}