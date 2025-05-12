package com.synway.datastandardmanager.pojo.standardtemplateexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
import com.alibaba.excel.enums.BooleanEnum;
import com.alibaba.excel.enums.poi.FillPatternTypeEnum;

/**
 * @author wangdongwei
 * @ClassName RevisionDescription
 * @description 修订说明的相关信息
 * 修订人	修订日期	修订动作	字段ID	字段中文名称	备注说明
 * @date 2020/12/8 17:35
 */
@ContentRowHeight(10)
@HeadRowHeight(20)
@ColumnWidth(10)
// 设置excel头的背景色为 暗板岩蓝 浅色
@HeadStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND,fillForegroundColor = 40)
//头字体设置为 9
@HeadFontStyle(fontHeightInPoints =  9,bold = BooleanEnum.FALSE)
// 内容的字体设置为9
@ContentFontStyle(fontHeightInPoints = 9)
public class RevisionDescription {

    @ExcelProperty("修订人")
    private String modifier;

    @ExcelProperty("修订日期")
    private String revisionTime;
    //
    @ExcelProperty("修订动作")
    private String revisionAction;
    //
    @ExcelProperty("字段ID")
    private String fieldId;

    @ExcelProperty("字段中文名称")
    private String fieldName;

    @ExcelProperty("备注说明")
    private String memo;

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public String getRevisionTime() {
        return revisionTime;
    }

    public void setRevisionTime(String revisionTime) {
        this.revisionTime = revisionTime;
    }

    public String getRevisionAction() {
        return revisionAction;
    }

    public void setRevisionAction(String revisionAction) {
        this.revisionAction = revisionAction;
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
