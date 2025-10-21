package com.synway.datastandardmanager.entity.vo;

import lombok.Data;

import java.util.List;

/**
 * @author wdw
 * @version 1.0
 * @date 2021/6/10 23:05
 */
@Data
public class LabelManageSelectVO {
    private int labelLevel;
    private List<KeyValueVO> list;
}
