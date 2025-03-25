package com.synway.property.pojo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * ODPSSQL语句分析类，用于数据使用频次
 * @author admin
 */
public enum ODPSSQLAnalyze {
	SELECT(1,"SELECT",Pattern.compile("select.*?from\\s+([^\\(^\\s]+)", Pattern.CASE_INSENSITIVE)),
	INSERT(2,"INSERT",Pattern.compile("insert\\s*(into|overwrite){1}\\s*(table|view){1}\\s*(\\S+)\\s*",Pattern.CASE_INSENSITIVE)),
	COUNT(3,"COUNT",Pattern.compile("^count\\s+(\\S+)\\s*",Pattern.CASE_INSENSITIVE)),
	DELETE(4,"DELETE",null),
	UPDATE(5,"UPDATE",null),
	CREATE(6,"CREATE",Pattern.compile("create\\s+(table|VIEW){1}\\s+(if\\s+)*(not\\s+)*(exists\\s+)*(\\S+)",Pattern.CASE_INSENSITIVE)),
	DROP(7,"DROP",Pattern.compile("drop\\s+(table|VIEW){1}\\s+(if\\s+)*(not\\s+)*(exists\\s+)*(\\S+)",Pattern.CASE_INSENSITIVE)),
	SELECT_JOIN(8,"SELECT_JOIN",Pattern.compile("join\\s*([^\\(^\\s]+)\\s+",Pattern.CASE_INSENSITIVE)),
	ALTER(9,"ALTER",Pattern.compile("alter\\s+(table|VIEW){1}\\s+(\\S+)\\s+",Pattern.CASE_INSENSITIVE)),
	ADD_STATISTIC(10,"ADD_STATISTIC",Pattern.compile("^add\\s+STATISTIC\\s+(\\S+)\\s*",Pattern.CASE_INSENSITIVE)),
	ANAYLZE(11,"ANAYLZE",Pattern.compile("analyze\\s+(table|VIEW){1}\\s+(\\S+)\\s+",Pattern.CASE_INSENSITIVE)),
	REMOVE(12,"REMOVE",Pattern.compile("^remove\\s+STATISTIC\\s+(\\S+)\\s+",Pattern.CASE_INSENSITIVE)),
	SHOW(13,"SHOW",Pattern.compile("^show\\s+.*?\\s+(\\S+)\\s*",Pattern.CASE_INSENSITIVE)),
	TRUNCATE(14,"TRUNCATE",Pattern.compile("^truncate.*?(table|VIEW){1}\\s+(\\S+)\\s*",Pattern.CASE_INSENSITIVE)),
	DESC(15,"DESC",Pattern.compile("^desc\\s+(\\S+)",Pattern.CASE_INSENSITIVE));
	
	public static List<CountTableUse> analyzeSQL(DBOperatorMonitor monitor){
		ArrayList<CountTableUse> odpsTableTypeList = new ArrayList<CountTableUse>();
		if(monitor==null || monitor.getSql()==null) {
            return odpsTableTypeList;
        }
		for (ODPSSQLAnalyze element : values()) {
			Pattern p =element.getExpressPattern();
			if(p==null || element.index==6 || element.index==7){//4567忽略
				continue;
			}
			Matcher m = p.matcher(monitor.getSql());
			while(m.find()){
				String odpsTableName = m.group(m.groupCount()).split("\\)")[0].replace("\\", "");
				String tableName=odpsTableName.toUpperCase();
				String projectName=monitor.getProjectName().toUpperCase();
				if(odpsTableName.contains(".")){
					String[] tempStr =odpsTableName.trim().split("\\.");
					if(tempStr.length==2){//如果
						projectName=tempStr[0].toUpperCase();
						tableName=tempStr[1].toUpperCase();
						tableName=tableName.replace(";","");
					}else{
						logger.info("odpsTableName错误：["+odpsTableName+"]跳过处理");
					}
				}
				CountTableUse countTableUse=new CountTableUse(monitor.getId(),tableName,projectName,monitor.getMonitorTime(),element.getIndex());
				odpsTableTypeList.add(countTableUse);
			}
		}
		return odpsTableTypeList;
	}
	
	private Integer index;
	private String name;
	private Pattern expressPattern;
	private ODPSSQLAnalyze(){}
	private static Logger logger = LoggerFactory.getLogger(ODPSSQLAnalyze.class);
	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private ODPSSQLAnalyze(Integer index, String name, Pattern express){
		this.index=index;
		this.name=name;
		this.expressPattern=express;
	}
	
	public static Integer getIndexByName(String name){
		for(ODPSSQLAnalyze element: values()){
			if(element.name.equals(name)){
				return element.index;
			}
		}
		return null;
	}
	
	public static String getNameByIndex(Integer index){
		for(ODPSSQLAnalyze element:values()){
			if(element.index.equals(index)){
				return element.name;
			}
		}
		return null;
	}

	public Pattern getExpressPattern() {
		return expressPattern;
	}

	public void setExpressPattern(Pattern expressPattern) {
		this.expressPattern = expressPattern;
	}

}
