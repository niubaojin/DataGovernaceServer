//package com.synway.datastandardmanager.pojo;
//
//
//import java.math.BigDecimal;
//
//
//@Entity
//@Table(name = "V_OBJECTFIELDVIEW", schema = "SYNLTE")
//public class VObjectfieldview implements java.io.Serializable {
//
//	private String		tablename;
//	private String		objectname;
//	private BigDecimal	objectstate;
//	private String		fieldname;
//	private String		tableid;
//	private String		columnname;
//	private String		fieldchineename;
//	private Long		datatype;
//	private String		dataSource;
//	private String		fieldtypename;
//	private Byte		fieldtype;
//	private Long		fieldlen;
//	private BigDecimal	fieldlen2;
//	private String		fieldtypenameObj;
//	private Short		fieldlenObj;
//	private Boolean		tableindex;
//	private Boolean		isindex;
//	private Boolean		needvalue;
//	private BigDecimal	isquery;
//	private Short		recno;
//	private String		sameid;
//	private String		wordname;
//	private String		word;
//	private String		codeid;
//	private String		codename;
//	private String		codetext;
//	private Byte		deleted;
//	private BigDecimal	columnnameState;
//
//
//	public String getTablename() {
//		return this.tablename;
//	}
//
//	public void setTablename(String tablename) {
//		this.tablename = tablename;
//	}
//
//	@Column(name = "OBJECTNAME", length = 128)
//	public String getObjectname() {
//		return this.objectname;
//	}
//
//	public void setObjectname(String objectname) {
//		this.objectname = objectname;
//	}
//
//	@Column(name = "OBJECTSTATE", precision = 22, scale = 0)
//	public BigDecimal getObjectstate() {
//		return this.objectstate;
//	}
//
//	public void setObjectstate(BigDecimal objectstate) {
//		this.objectstate = objectstate;
//	}
//
//	@Column(name = "FIELDNAME", nullable = false, length = 100)
//	public String getFieldname() {
//		return this.fieldname;
//	}
//
//	public void setFieldname(String fieldname) {
//		this.fieldname = fieldname;
//	}
//
//	@Column(name = "TABLEID", nullable = false, length = 40)
//	public String getTableid() {
//		return this.tableid;
//	}
//
//	public void setTableid(String tableid) {
//		this.tableid = tableid;
//	}
//
//
//	@Column(name = "COLUMNNAME", nullable = false, length = 100)
//	public String getColumnname() {
//		return this.columnname;
//	}
//
//	public void setColumnname(String columnname) {
//		this.columnname = columnname;
//	}
//
//	@Column(name = "FIELDCHINEENAME", length = 80)
//	public String getFieldchineename() {
//		return this.fieldchineename;
//	}
//
//	public void setFieldchineename(String fieldchineename) {
//		this.fieldchineename = fieldchineename;
//	}
//
//	@Column(name = "DATATYPE", precision = 10, scale = 0)
//	public Long getDatatype() {
//		return this.datatype;
//	}
//
//	public void setDatatype(Long datatype) {
//		this.datatype = datatype;
//	}
//
//	@Column(name = "DATA_SOURCE", length = 3)
//	public String getDataSource() {
//		return this.dataSource;
//	}
//
//	public void setDataSource(String dataSource) {
//		this.dataSource = dataSource;
//	}
//
//	@Column(name = "FIELDTYPENAME", length = 8)
//	public String getFieldtypename() {
//		return this.fieldtypename;
//	}
//
//	public void setFieldtypename(String fieldtypename) {
//		this.fieldtypename = fieldtypename;
//	}
//
//	@Column(name = "FIELDTYPE", nullable = false, precision = 2, scale = 0)
//	public Byte getFieldtype() {
//		return this.fieldtype;
//	}
//
//	public void setFieldtype(Byte fieldtype) {
//		this.fieldtype = fieldtype;
//	}
//
//	@Column(name = "FIELDLEN", nullable = false, precision = 10, scale = 0)
//	public Long getFieldlen() {
//		return this.fieldlen;
//	}
//
//	public void setFieldlen(Long fieldlen) {
//		this.fieldlen = fieldlen;
//	}
//
//	@Column(name = "FIELDLEN2", precision = 22, scale = 0)
//	public BigDecimal getFieldlen2() {
//		return this.fieldlen2;
//	}
//
//	public void setFieldlen2(BigDecimal fieldlen2) {
//		this.fieldlen2 = fieldlen2;
//	}
//
//	@Column(name = "FIELDTYPENAME_OBJ", length = 8)
//	public String getFieldtypenameObj() {
//		return this.fieldtypenameObj;
//	}
//
//	public void setFieldtypenameObj(String fieldtypenameObj) {
//		this.fieldtypenameObj = fieldtypenameObj;
//	}
//
//	@Column(name = "FIELDLEN_OBJ", precision = 4, scale = 0)
//	public Short getFieldlenObj() {
//		return this.fieldlenObj;
//	}
//
//	public void setFieldlenObj(Short fieldlenObj) {
//		this.fieldlenObj = fieldlenObj;
//	}
//
//	@Column(name = "TABLEINDEX", precision = 1, scale = 0)
//	public Boolean getTableindex() {
//		return this.tableindex;
//	}
//
//	public void setTableindex(Boolean tableindex) {
//		this.tableindex = tableindex;
//	}
//
//	@Column(name = "ISINDEX", nullable = false, precision = 1, scale = 0)
//	public Boolean getIsindex() {
//		return this.isindex;
//	}
//
//	public void setIsindex(Boolean isindex) {
//		this.isindex = isindex;
//	}
//
//	@Column(name = "NEEDVALUE", nullable = false, precision = 1, scale = 0)
//	public Boolean getNeedvalue() {
//		return this.needvalue;
//	}
//
//	public void setNeedvalue(Boolean needvalue) {
//		this.needvalue = needvalue;
//	}
//
//	@Column(name = "ISQUERY", precision = 22, scale = 0)
//	public BigDecimal getIsquery() {
//		return this.isquery;
//	}
//
//	public void setIsquery(BigDecimal isquery) {
//		this.isquery = isquery;
//	}
//
//	@Column(name = "RECNO", nullable = false, precision = 4, scale = 0)
//	public Short getRecno() {
//		return this.recno;
//	}
//
//	public void setRecno(Short recno) {
//		this.recno = recno;
//	}
//
//	@Column(name = "SAMEID", length = 50)
//	public String getSameid() {
//		return this.sameid;
//	}
//
//	public void setSameid(String sameid) {
//		this.sameid = sameid;
//	}
//
//	@Column(name = "WORDNAME", length = 128)
//	public String getWordname() {
//		return this.wordname;
//	}
//
//	public void setWordname(String wordname) {
//		this.wordname = wordname;
//	}
//
//	@Column(name = "WORD", length = 32)
//	public String getWord() {
//		return this.word;
//	}
//
//	public void setWord(String word) {
//		this.word = word;
//	}
//
//	@Column(name = "CODEID", length = 50)
//	public String getCodeid() {
//		return this.codeid;
//	}
//
//	public void setCodeid(String codeid) {
//		this.codeid = codeid;
//	}
//
//	@Column(name = "CODENAME", length = 32)
//	public String getCodename() {
//		return this.codename;
//	}
//
//	public void setCodename(String codename) {
//		this.codename = codename;
//	}
//
//	@Column(name = "CODETEXT", length = 128)
//	public String getCodetext() {
//		return this.codetext;
//	}
//
//	public void setCodetext(String codetext) {
//		this.codetext = codetext;
//	}
//
//	@Column(name = "DELETED", precision = 2, scale = 0)
//	public Byte getDeleted() {
//		return this.deleted;
//	}
//
//	public void setDeleted(Byte deleted) {
//		this.deleted = deleted;
//	}
//
//	@Column(name = "COLUMNNAME_STATE", precision = 22, scale = 0)
//	public BigDecimal getColumnnameState() {
//		return this.columnnameState;
//	}
//
//	public void setColumnnameState(BigDecimal columnnameState) {
//		this.columnnameState = columnnameState;
//	}
//
//
//	private VObjectfieldviewId	id;
//
//	// Constructors
//
//	/** default constructor */
//	public VObjectfieldview() {
//	}
//
//	/** full constructor */
//	public VObjectfieldview(VObjectfieldviewId id) {
//		this.id = id;
//	}
//
//	public VObjectfieldviewId getId() {
//		return this.id;
//	}
//
//	public void setId(VObjectfieldviewId id) {
//		this.id = id;
//	}
//
//}