package com.synway.datastandardmanager.pojo;

import jakarta.validation.constraints.Size;
import java.io.Serializable;

/**
 * 数据语义表 (SAMEWORD)实体类
 */

public class Sameword implements Serializable {
	private String	id;
	@Size(max = 120,message = "[语义中文名称]长度不能超过100")
	private String	wordname;
	@Size(max = 26,message = "[语义英文名称]长度不能超过40")
	private String	word;
	@Size(max = 128,message = "[备注]长度不能超过40")
	private String	memo = "";

	@Size(max = 50,message = "[主体]不能为空")
	private String elementObject;

	/**
	 * 主体类型翻译 1.人员 2.物 3.组织 4.地 5.事 6.时间 7.信息
	 */
	private String elementObjectVo;

	public String getElementObjectVo() {
		return elementObjectVo;
	}

	public void setElementObjectVo(String elementObjectVo) {
		this.elementObjectVo = elementObjectVo;
	}

	public String getElementObject() {
		return elementObject;
	}

	public void setElementObject(String elementObject) {
		this.elementObject = elementObject;
	}

	private Byte deleted;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getWordname() {
		return wordname;
	}
	public void setWordname(String wordname) {
		this.wordname = wordname;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Byte getDeleted() {
		return deleted;
	}
	public void setDeleted(Byte deleted) {
		this.deleted = deleted;
	}
	
	


}