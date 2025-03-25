package com.synway.datastandardmanager.pojo.enums;

/**
 * Object表的dataType对应信息
 * @author admin
 *
 */
public enum ObjectDataType {

	SOURCE_DATA("源数据"),
	BASE_DATA("基础数据"),
	REPOSITORY("资源数据(知识库)"),
	BEHAVIOR_LOG_LIB("资源数据（行为日志库）"),
	SPECIAL_LIB("专题库"),
	LABEL_DATA("标签数据"),
	NEARLY_LINE_QUERY_DATA("近线查询数据"),
	CUSTOM_TABLE_TYPE("自定义表类型");
	
	private String value=null;

	ObjectDataType(String value){
		this.value=value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public static String getDataType(int value){
		if(value==0){
			return ObjectDataType.SOURCE_DATA.getValue();
		}else if (value==1) {
			return ObjectDataType.BASE_DATA.getValue();
		}else if (value==2) {
			return ObjectDataType.REPOSITORY.getValue();
		}else if (value==3) {
			return ObjectDataType.BEHAVIOR_LOG_LIB.getValue();
		}else if (value==4) {
			return ObjectDataType.CUSTOM_TABLE_TYPE.getValue();
		}else if (value==5) {
			return ObjectDataType.SPECIAL_LIB.getValue();
		}else if (value==901) {
			return ObjectDataType.LABEL_DATA.getValue();
		}else if (value==902) {
			return ObjectDataType.NEARLY_LINE_QUERY_DATA.getValue();
		}
		return null;
	}

}
