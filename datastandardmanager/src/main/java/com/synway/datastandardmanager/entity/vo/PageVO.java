package com.synway.datastandardmanager.entity.vo;

import com.google.common.collect.Lists;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description ="分页响应体")
public class PageVO<T> {

    @Schema(description = "当前页数")
    private Integer pageNum;
    @Schema(description = "每页记录数")
    private Integer pageSize;
    @Schema(description = "总记录数")
    private Integer size;
    @Schema(description = "总页数")
    private Integer pages;
    @Schema(description = "总记录数")
    private Long total;

    private List<T> rows;

    public PageVO<T> emptyResult(){
        this.setPages(0);
        this.setTotal(0L);
        this.setSize(0);
        this.setRows(Lists.newArrayList());
        return this;
    }
}
