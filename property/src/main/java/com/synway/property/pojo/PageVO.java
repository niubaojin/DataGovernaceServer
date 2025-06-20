package com.synway.property.pojo;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页响应体
 *
 * @author sds
 * @date 2021/11/10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageVO<T> {

    private Integer pageNum;
    private Integer pageSize;
    private Integer size;
    private Integer pages;
    private Long total;

    private List<T> rows;

    // 二级过滤列表
    private List<String> filterSec;

    public PageVO<T> emptyResult(){
        this.setPages(0);
        this.setTotal(0L);
        this.setSize(0);
        this.setRows(Lists.newArrayList());
        return this;
    }
}
