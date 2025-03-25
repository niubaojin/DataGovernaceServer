package com.synway.governace.pojo;
//表结构实体类 classfiled_table
public class TableConstruct {
	private String  md5id;         //md5ID
	private String dateInceptType;  //数据接入类型
      private String className;         //类别（大）
      private String subClass;           //小类
      private String tableId;           //表协议ID
      private String sourceCode ;         //来源代码
      private String protocol;            //小协议
      private String tableNameEn;        //英文表名
      private String tableNameCh;        //中文表名
//      private String protocols;         //协议
//	  private String odpsproject;       //项目名称
      
      public TableConstruct (){
    	  System.out.println("-----------------------");
      }

//	public String getOdpsproject() {
//		return odpsproject;
//	}
//
//	public void setOdpsproject(String odpsproject) {
//		this.odpsproject = odpsproject;
//	}
      public String getMd5id() {
  		return md5id;
  	}
  	public void setMd5id(String md5id) {
  		this.md5id = md5id;
  	}
	public String getDateInceptType() {
		return dateInceptType;
	}
	public void setDateInceptType(String dateInceptType) {
		this.dateInceptType = dateInceptType;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getSubClass() {
		return subClass;
	}
	public void setSubClass(String subClass) {
		this.subClass = subClass;
	}
	public String getTableId() {
		return tableId;
	}
	public void setTableId(String tableId) {
		this.tableId = tableId;
	}
	public String getSourceCode() {
		return sourceCode;
	}
	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getTableNameEn() {
		return tableNameEn;
	}
	public void setTableNameEn(String tableNameEn) {
		this.tableNameEn = tableNameEn;
	}
	public String getTableNameCh() {
		return tableNameCh;
	}
	public void setTableNameCh(String tableNameCh) {
		this.tableNameCh = tableNameCh;
	}
//	public String getProtocols() {
//		return protocols;
//	}
//	public void setProtocols(String protocols) {
//		this.protocols = protocols;
//	}
	@Override
	public String toString() {
		return  className+subClass+
				tableNameEn+tableId+
				protocol;
	}
	
}
