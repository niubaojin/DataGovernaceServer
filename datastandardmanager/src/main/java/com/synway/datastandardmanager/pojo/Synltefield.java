package com.synway.datastandardmanager.pojo;

import java.math.BigDecimal;

public class Synltefield {
	private String fieldid;//元素编码	
	private String fieldname;//标准中元素项英文描述 标准列名
	private String fieldchinesename ;//中文名称
	private String fielddescribe ;
	private  Byte fieldtype;
	private  Long fieldlen;
	private  String defaultvalue;
	private  String memo = "";
	private  String columnname;//数据库字段名称  列名
	private  Byte deleted;
	private  String codeid="";
	private  String sameid="";
	private  BigDecimal is530new;
	private  BigDecimal fieldlen2;
	// 类型的中文名称
	private  String fieldtypeName;
	//  字段分类的代码值
	private String fieldClass = "";
	// 字段分类的中文名
	private String fieldClassCh ="";
	// 敏感分类的回填值 SECRET_CLASS
	private String secretClass;

	// 敏感分类的中文名称
	private String secretClassCh;

	//20211026 新增的2个字段
	private Integer fieldStandard;

	//元素编码
	private String gadsjFieldId="";

	public Integer getFieldStandard() {
		return fieldStandard;
	}

	public void setFieldStandard(Integer fieldStandard) {
		this.fieldStandard = fieldStandard;
	}

	public String getGadsjFieldId() {
		return gadsjFieldId;
	}

	public void setGadsjFieldId(String gadsjFieldId) {
		this.gadsjFieldId = gadsjFieldId;
	}

	@Override
	public String toString() {
		return "Synltefield{" +
				"fieldid='" + fieldid + '\'' +
				", fieldname='" + fieldname + '\'' +
				", fieldchinesename='" + fieldchinesename + '\'' +
				", fielddescribe='" + fielddescribe + '\'' +
				", fieldtype=" + fieldtype +
				", fieldlen=" + fieldlen +
				", defaultvalue='" + defaultvalue + '\'' +
				", memo='" + memo + '\'' +
				", columnname='" + columnname + '\'' +
				", deleted=" + deleted +
				", codeid='" + codeid + '\'' +
				", sameid='" + sameid + '\'' +
				", is530new=" + is530new +
				", fieldlen2=" + fieldlen2 +
				", fieldtypeName='" + fieldtypeName + '\'' +
				", fieldClass='" + fieldClass + '\'' +
				", fieldClassCh='" + fieldClassCh + '\'' +
				", secretClass='" + secretClass + '\'' +
				", secretClassCh='" + secretClassCh + '\'' +
				", fieldStandard=" + fieldStandard +
				", gadsjFieldId='" + gadsjFieldId + '\'' +
				", id=" + id +
				", fieldcode=" + fieldcode +
				", fieldMessage='" + fieldMessage + '\'' +
				", wordName='" + wordName + '\'' +
				", codeText='" + codeText + '\'' +
				", button='" + button + '\'' +
				", sameword=" + sameword +
				'}';
	}

	public String getSecretClass() {
		return secretClass;
	}

	public void setSecretClass(String secretClass) {
		this.secretClass = secretClass;
	}

	public String getSecretClassCh() {
		return secretClassCh;
	}

	public void setSecretClassCh(String secretClassCh) {
		this.secretClassCh = secretClassCh;
	}

	public String getFieldClass() {
		return fieldClass;
	}

	public void setFieldClass(String fieldClass) {
		this.fieldClass = fieldClass;
	}

	public String getFieldClassCh() {
		return fieldClassCh;
	}

	public void setFieldClassCh(String fieldClassCh) {
		this.fieldClassCh = fieldClassCh;
	}

	public String getFieldtypeName() {
		return fieldtypeName;
	}

	public void setFieldtypeName(String fieldtypeName) {
		this.fieldtypeName = fieldtypeName;
	}

	public String getButton() {
		return button;
	}

	public void setButton(String button) {
		this.button = button;
	}

	private Long id ;
	private Fieldcode fieldcode;
	private String fieldMessage;//前段页面  字段信息
	private String wordName="";//数据语义表 (SAMEWORD) 语义中文名称
	private String codeText= "";//元素代码集定义表 (FIELDCODE) 代码中文名称
	private String button;

	
	
	public String getWordName() {
		return wordName;
	}
	public void setWordName(String wordName) {
		this.wordName = wordName;
	}
	public String getCodeText() {
		return codeText;
	}
	public void setCodeText(String codeText) {
		this.codeText = codeText;
	}
	public String getFieldMessage() {
		return fieldMessage;
	}
	public void setFieldMessage(String fieldMessage) {
		this.fieldMessage = fieldMessage;
	}

	private Sameword sameword;

	public Fieldcode getFieldcode() {
		return fieldcode;
	}

	public void setFieldcode(Fieldcode fieldcode) {
		this.fieldcode = fieldcode;
	}

	public Sameword getSameword() {
		return sameword;
	}

	public void setSameword(Sameword sameword) {
		this.sameword = sameword;
	}

	public String getFieldid() {
		return fieldid;
	}

	public void setFieldid(String fieldid) {
		this.fieldid = fieldid;
	}

	public String getFieldname() {
		return fieldname;
	}

	public void setFieldname(String fieldname) {
		this.fieldname = fieldname;
	}

	public String getFieldchinesename() {
		return fieldchinesename;
	}

	public void setFieldchinesename(String fieldchinesename) {
		this.fieldchinesename = fieldchinesename;
	}

	public String getFielddescribe() {
		return fielddescribe;
	}

	public void setFielddescribe(String fielddescribe) {
		this.fielddescribe = fielddescribe;
	}

	public Byte getFieldtype() {
		return fieldtype;
	}

	public void setFieldtype(Byte fieldtype) {
		this.fieldtype = fieldtype;
	}

	public Long getFieldlen() {
		return fieldlen;
	}

	public void setFieldlen(Long fieldlen) {
		this.fieldlen = fieldlen;
	}

	public String getDefaultvalue() {
		return defaultvalue;
	}

	public void setDefaultvalue(String defaultvalue) {
		this.defaultvalue = defaultvalue;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getColumnname() {
		return columnname;
	}

	public void setColumnname(String columnname) {
		this.columnname = columnname;
	}

	public Byte getDeleted() {
		return deleted;
	}

	public void setDeleted(Byte deleted) {
		this.deleted = deleted;
	}

	public String getCodeid() {
		return codeid;
	}

	public void setCodeid(String codeid) {
		this.codeid = codeid;
	}

	public String getSameid() {
		return sameid;
	}

	public void setSameid(String sameid) {
		this.sameid = sameid;
	}

	public BigDecimal getIs530new() {
		return is530new;
	}

	public void setIs530new(BigDecimal is530new) {
		this.is530new = is530new;
	}

	public BigDecimal getFieldlen2() {
		return fieldlen2;
	}

	public void setFieldlen2(BigDecimal fieldlen2) {
		this.fieldlen2 = fieldlen2;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

}
