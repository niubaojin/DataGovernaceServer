package com.synway.datastandardmanager.pojo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
import com.alibaba.excel.enums.BooleanEnum;
import com.alibaba.excel.enums.poi.FillPatternTypeEnum;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@ContentRowHeight(18)
@HeadRowHeight(20)
@ColumnWidth(15)
// 设置excel头的背景色为 暗板岩蓝 浅色
@HeadStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND,fillForegroundColor = 40)
//头字体设置为 9
@HeadFontStyle(fontHeightInPoints =  10,bold = BooleanEnum.FALSE)
//内容的字体设置为9
@ContentFontStyle(fontHeightInPoints = 10)
public class FieldCodeVal implements Serializable {
	@ExcelIgnore
	private String id;

	@ExcelProperty("代码主键ID")
	@NotBlank(message = "[主键ID]值不能为空")
	@Size(max= 50, message = "[主键ID]长度不能超过50")
	private String codeValId;

	@ExcelIgnore
	@Size(max= 50, message = "[元素代码集ID]长度不能超过50")
	private String codeId;

	@ExcelProperty("代码中文名")
	@NotBlank(message = "[元素代码值名称]值不能为空")
	@Size(max= 100, message = "[元素代码值名称]长度不能超过100")
	private String valText;

	@ExcelProperty("备注说明")
	@Size(max= 800, message = "[备注]长度不能超过800")
	private String memo;

	@ExcelProperty("代码值")
	@NotBlank(message = "[元素集的代码值]值不能为空")
	@Size(max= 40, message = "[元素集的代码值]长度不能超过40")
	private String valValue;

	@ExcelIgnore
    private String sortIndex;

	@ExcelProperty("英文缩写")
	@Size(max= 100, message = "[元素代码值名称英文缩写]长度不能超过100")
	private String valTextTitle;

	@ExcelIgnore
	private String oldCodeValId;

	@ExcelIgnore
	private String codeText;

	public String getCodeText() {
		return codeText;
	}

	public void setCodeText(String codeText) {
		this.codeText = codeText;
	}

	public String getOldCodeValId() {
        return oldCodeValId;
    }

    public void setOldCodeValId(String oldCodeValId) {
        this.oldCodeValId = oldCodeValId;
    }

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCodeValId() {
		return codeValId;
	}

	public void setCodeValId(String codeValId) {
		this.codeValId = codeValId;
	}

	public String getCodeId() {
		return codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	public String getValText() {
		return valText;
	}

	public void setValText(String valText) {
		this.valText = valText;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getValValue() {
		return valValue;
	}

	public void setValValue(String valValue) {
		this.valValue = valValue;
	}

	public String getSortIndex() {
		return sortIndex;
	}

	public void setSortIndex(String sortIndex) {
		this.sortIndex = sortIndex;
	}

	public String getValTextTitle() {
		return valTextTitle;
	}

	public void setValTextTitle(String valTextTitle) {
		this.valTextTitle = valTextTitle;
	}

	

}
