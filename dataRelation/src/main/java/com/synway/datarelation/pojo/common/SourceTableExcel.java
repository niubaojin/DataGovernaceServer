package com.synway.datarelation.pojo.common;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
import org.apache.poi.ss.usermodel.FillPatternType;

/**
 * @author wangdongwei
 * @ClassName SourceTableExcel
 * @description TODO
 * @date 2021/3/10 19:11
 */
@ContentRowHeight(18)
@HeadRowHeight(20)
@ColumnWidth(10)
// 设置excel头的背景色为 暗板岩蓝 浅色
@HeadStyle(fillPatternType = FillPatternType.SOLID_FOREGROUND,fillForegroundColor = 40)
//头字体设置为 9
@HeadFontStyle(fontHeightInPoints =  10,bold = false)
// 内容的字体设置为9
@ContentFontStyle(fontHeightInPoints = 10)
public class SourceTableExcel {

    @ExcelProperty("协议ID")
    @ColumnWidth(value=18)
    private String sourceId;

    @ExcelProperty("协议中文名")
    @ColumnWidth(value=18)
    private String nodeName;


    @ExcelProperty("源系统代号中文")
    @ColumnWidth(value=18)
    private String sourceCodeCh;

    @ExcelProperty("源系统代号")
    @ColumnWidth(value=18)
    private String sourceCode;


    @ExcelProperty("源协议厂商")
    @ColumnWidth(value=18)
    private String sourceFactory;

    public String getSourceFactory() {
        return sourceFactory;
    }

    public void setSourceFactory(String sourceFactory) {
        this.sourceFactory = sourceFactory;
    }

    public String getSourceCodeCh() {
        return sourceCodeCh;
    }

    public void setSourceCodeCh(String sourceCodeCh) {
        this.sourceCodeCh = sourceCodeCh;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }


}
