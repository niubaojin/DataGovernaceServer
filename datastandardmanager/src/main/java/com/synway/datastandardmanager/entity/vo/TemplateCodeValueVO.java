package com.synway.datastandardmanager.entity.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import lombok.Data;

/**
 * @author wdw
 * @version 1.0
 * @date 2021/6/11 12:22
 */
@Data
public class TemplateCodeValueVO {
    /**
     * 模板中的码值信息
     */
    @ExcelProperty(index = 0, value = "  ")
    @ColumnWidth(value = 20)
    @HeadStyle(fillForegroundColor = 1, fillBackgroundColor = 1)
    private String codeValue;

    @ExcelProperty(index = 1, value = "  ")
    @ColumnWidth(value = 20)
    @HeadStyle(fillForegroundColor = 1, fillBackgroundColor = 1)
    private String codeName;
}
