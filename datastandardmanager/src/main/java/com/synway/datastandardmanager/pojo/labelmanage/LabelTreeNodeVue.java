package com.synway.datastandardmanager.pojo.labelmanage;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author 数据接入
 */
@Data
@ToString
public class LabelTreeNodeVue {
	private String label;
	private String id;
	private int showIcon;
	private List<LabelTreeNodeVue> children;
	private String parent;
	private int level;
	private String grandpar;
	private int sortLevel;


}
