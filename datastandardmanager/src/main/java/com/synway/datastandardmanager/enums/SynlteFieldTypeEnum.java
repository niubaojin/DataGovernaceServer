package com.synway.datastandardmanager.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 数据元字段类型
 * @author admin
 *
 */
public enum SynlteFieldTypeEnum {
	INTEGER("integer"),
	FLOAT("float"),
	STRING("string"),
	DATE("date"),
	DATETIME("datetime"),
	BLOB("blob");

	private String value=null;

	private static final List<String> fieldType0 = new ArrayList<>(Arrays.asList("integer","int","int8","int32","int64","uint8","uint16","uint32","uint64",
			"long","bigint","tinyint","smallint","number","mediumint"));
	private static final List<String> fieldType1 = new ArrayList<>(Arrays.asList("float","float32","float64","double","decimal"));
	private static final List<String> fieldType2 = new ArrayList<>(Arrays.asList("string","varchar","varchar2","char","text","longtext","mediumtext"));
	private static final List<String> fieldType3 = new ArrayList<>(Arrays.asList("date"));
	private static final List<String> fieldType4 = new ArrayList<>(Arrays.asList("datetime","timestamp"));
	private static final List<String> fieldType5 = new ArrayList<>(Arrays.asList("blob","longblob","binary"));

	SynlteFieldTypeEnum(String value){
		this.value=value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public static String getSynlteFieldType(int value){
		if(value==0){
			return SynlteFieldTypeEnum.INTEGER.getValue();
		}else if (value==1) {
			return SynlteFieldTypeEnum.FLOAT.getValue();
		}else if (value==2) {
			return SynlteFieldTypeEnum.STRING.getValue();
		}else if (value==3) {
			return SynlteFieldTypeEnum.DATE.getValue();
		}else if (value==4) {
			return SynlteFieldTypeEnum.DATETIME.getValue();
		}else if (value==5) {
			return SynlteFieldTypeEnum.BLOB.getValue();
		}
		return null;
	}

	// 根据传入的具体字段中文类型获取数字类型
    public static int getSynlteNumType(String value){
		if (StringUtils.isNotBlank(value)){
			value = value.toLowerCase();
		}else {
			return -1;
		}
		if(value.equalsIgnoreCase(SynlteFieldTypeEnum.INTEGER.getValue()) || fieldType0.indexOf(value) != -1){
			return 0;
		}else if (value.equalsIgnoreCase(SynlteFieldTypeEnum.FLOAT.getValue()) || fieldType1.indexOf(value) != -1) {
			return 1;
		}else if (value.equalsIgnoreCase(SynlteFieldTypeEnum.STRING.getValue()) || fieldType2.indexOf(value) != -1) {
			return 2;
		}else if (value.equalsIgnoreCase(SynlteFieldTypeEnum.DATE.getValue()) || fieldType3.indexOf(value) != -1) {
			return 3;
		}else if (value.equalsIgnoreCase(SynlteFieldTypeEnum.DATETIME.getValue()) || fieldType4.indexOf(value) != -1) {
			return 4;
		}else if (value.equalsIgnoreCase(SynlteFieldTypeEnum.BLOB.getValue()) || fieldType5.indexOf(value) != -1) {
			return 5;
		}
        return -1;
    }
}
