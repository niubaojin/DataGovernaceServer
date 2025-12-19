package com.synway.datastandardmanager.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 大版本号和修订人的筛选值
 *
 * @author obito
 * @date 2025年8月27日20:59:09
 */
@Data
public class FilterListVO implements Serializable {
    private static final long serialVersionUID = 421264681424681476L;

    //大版本号的筛选值
    private List<ValueLabelVO> versionsList;
    //修订人的筛选值
    private List<ValueLabelVO> authorList;
}
