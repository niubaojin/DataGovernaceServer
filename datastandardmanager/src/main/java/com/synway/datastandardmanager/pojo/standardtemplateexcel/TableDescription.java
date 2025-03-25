package com.synway.datastandardmanager.pojo.standardtemplateexcel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.write.style.*;

/**
 * @author wangdongwei
 * @ClassName TableDescription
 * @description 表注释的相关说明,因为这个不需要表头 并且是倒置的表格内容，所以这个类无所谓
 *    需要根据key的内容来解析相关数据
 * @date 2020/12/9 17:26
 */
// 内容的字体设置为9
@ContentFontStyle(fontHeightInPoints = 9)
@ContentRowHeight(15)
@HeadRowHeight(20)
@ColumnWidth(20)
@ContentStyle()
public class TableDescription {
    @ColumnWidth(value=18)
    private String key;

    @ColumnWidth(value=18)
    private String value;


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
