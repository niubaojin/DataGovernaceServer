package com.synway.datastandardmanager.entity.vo.createTable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ck 在 排序主键 和分区主键中可能存在 函数(字段) 以及 字段的情况
 * @author wangdongwei
 * @date 2021/9/25 10:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CkColumnVO implements Serializable {
    private static final long serialVersionUID = 8024099556950547896L;

    /**
     *  dateFunction: 日期函数
     *  column： 纯字段
     */
    private String partitionType;

    /**
     * 分区字段需要的 函数 信息
     */
    private String partitionFunction;

    /**
     * 字段信息
     */
    private String columnName;

}
