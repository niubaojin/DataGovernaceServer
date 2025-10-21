package com.synway.datastandardmanager.entity.vo;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author nbj
 */
@Data
@ToString
public class TreeNodeValueVO {
    private String label;
    private String id;
    private String parent;
    private String grandpar;
    private int level;
    private int sortLevel;
    private int showIcon;
    private List<TreeNodeValueVO> children;

}
