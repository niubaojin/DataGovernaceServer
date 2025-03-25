package com.synway.datastandardmanager.pojo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serializable;

public class Fieldcode implements Serializable {

	@NotBlank(message = "[主键ID]值不能为空")
	@Size(max= 50, message = "[主键ID]长度不能超过50")
	private String codeId;

	@NotBlank(message = "[代码英文名称]值不能为空")
	@Size(max= 60, message = "[代码英文名称]长度不能超过60")
    private String codeName;

	@NotBlank(message = "[代码中文名称]值不能为空")
	@Size(max= 100, message = "[代码中文名称]长度不能超过100")
    private String codeText;

	@Size(max= 800, message = "[备注]长度不能超过800")
	private String memo;

	@Size(max= 50, message = "[代码集父ID]长度不能超过50")
	private String parcodeId;
            
    private Byte deleted;
            
    private Byte transrule;
            
    private String brotherCodeId;

    // 序号
    private Integer serialNum;

    public Integer getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(Integer serialNum) {
        this.serialNum = serialNum;
    }

    public String getCodeId() {
		return codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getCodeText() {
		return codeText;
	}

	public void setCodeText(String codeText) {
		this.codeText = codeText;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getParcodeId() {
		return parcodeId;
	}

	public void setParcodeId(String parcodeId) {
		this.parcodeId = parcodeId;
	}

	public Byte getDeleted() {
		return deleted;
	}

	public void setDeleted(Byte deleted) {
		this.deleted = deleted;
	}

	public Byte getTransrule() {
		return transrule;
	}

	public void setTransrule(Byte transrule) {
		this.transrule = transrule;
	}

	public String getBrotherCodeId() {
		return brotherCodeId;
	}

	public void setBrotherCodeId(String brotherCodeId) {
		this.brotherCodeId = brotherCodeId;
	}

	

    
}
