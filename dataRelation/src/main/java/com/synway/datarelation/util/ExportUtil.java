package com.synway.datarelation.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import jakarta.servlet.ServletOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

public class ExportUtil {

    // 导出csv
    public static void exportToCsv(ServletOutputStream out, List<Object> list, String name, String[] titles, String[] fieldName) throws IOException {
        StringBuffer sbf = new StringBuffer();
        for (String title : titles) {
            sbf.append(title).append(",");
        }
        sbf.append("\n");
        out.write(new byte[]{(byte)0xEF, (byte)0xbb, (byte)0xbF});
        out.write(sbf.toString().getBytes());
        for (int i = 0; i < list.size(); i++) {
            out.write(handleExportData(list.get(i), fieldName).getBytes());
        }
        out.flush();
        out.close();
    }

    // 处理csv导出数据
    public static String handleExportData(Object exportData, String[] fieldName) {
        StringBuffer sbf = new StringBuffer();
        for (String field : fieldName) {
            addStringBuffer(sbf, exportData, field);
        }
        sbf.append("\n");
        return sbf.toString();
    }

    // 特殊字符转义替换
    public static void addStringBuffer(StringBuffer sbf, Object exportData, String field) {
        String val = ExcelHelper.objectToString(ExcelHelper.getFieldValueByName(field, exportData));
        if (StringUtils.equals("", val)) {
            sbf.append("-,");
        } else {
            String temp = val.replaceAll("\r", "").replaceAll("\n", "");
            if (temp.contains(",")) {
                if (temp.contains("\"")) {
                    temp = temp.replaceAll("\"", "\"\"");
                }
                temp = "\""+ temp + "\"";
            }
            sbf.append(temp).append(",");
        }
    }

    // 导出word
    public static void exportToWord(ServletOutputStream out, List<Object> list, String name, String[] titles, String[] fieldName, String pageSizeA, int[] colWidths) throws IOException {
        XWPFDocument doc = new XWPFDocument();
        // 纸张方向横向
        CTDocument1 document = doc.getDocument();
        CTBody body = document.getBody();
        if(!body.isSetSectPr()){
            body.addNewSectPr();
        }
        CTSectPr section = body.getSectPr();
        if(!section.isSetPgSz()){
            section.addNewPgSz();
        }
        CTPageSz pageSize = section.getPgSz();
        if (StringUtils.equals("A3", pageSizeA)) {
            pageSize.setW(BigInteger.valueOf(23811));
            pageSize.setH(BigInteger.valueOf(16838));
        } else if (StringUtils.equals("A4", pageSizeA)) {
            pageSize.setW(BigInteger.valueOf(16838));
            pageSize.setH(BigInteger.valueOf(11906));
        } else {
            pageSize.setW(BigInteger.valueOf(15840));
            pageSize.setH(BigInteger.valueOf(12240));
        }
        pageSize.setOrient(STPageOrientation.LANDSCAPE);
        // 添加标题
        XWPFParagraph titleParagraph = doc.createParagraph();
        titleParagraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun run = titleParagraph.createRun();
        run.setText(name);
        run.setColor("000000");
        run.setFontSize(20);
        // 添加表格
        XWPFTable table = doc.createTable();
        // 设置宽度
        CTTbl ttbl = table.getCTTbl();
        CTTblPr tblPr = null != table.getCTTbl() ? ttbl.getTblPr() : ttbl.addNewTblPr();
        CTTblLayoutType type = tblPr.isSetTblLayout() ? tblPr.getTblLayout() : tblPr.addNewTblLayout();
        type.setType(STTblLayoutType.FIXED);
        CTTblGrid tblGrid = null != ttbl.getTblGrid() ? ttbl.getTblGrid() : ttbl.addNewTblGrid();
        for (int i : colWidths) {
            CTTblGridCol gridCol = tblGrid.addNewGridCol();
            gridCol.setW(new BigInteger(i + ""));
        }
        // 设置居中
        CTTblPr tablePr = table.getCTTbl().addNewTblPr();
        tablePr.addNewJc().setVal(STJc.CENTER);
        // 表头
        XWPFTableRow rowHead = table.getRow(0);
        XWPFParagraph cellParagraph = rowHead.getCell(0).getParagraphs().get(0);
        XWPFRun cellParagraphRun = cellParagraph.createRun();
        for (int i = 0; i < titles.length; i++) {
            if (i > 0) {
                cellParagraph = rowHead.addNewTableCell().getParagraphs().get(0);
            }
            cellParagraph.setAlignment(ParagraphAlignment.CENTER);
            cellParagraphRun = cellParagraph.createRun();
            cellParagraphRun.setFontSize(14);
            cellParagraphRun.setBold(true);
            cellParagraphRun.setText(titles[i]);
        }
        int rows = list.size();
        // 表格内容
        for (int j = 0; j < rows; j++) {
            XWPFTableRow rowsContent = table.createRow();
            Object obj = list.get(j);
            for (int k = 0; k < fieldName.length; k++) {
                XWPFParagraph cellParagraphC = rowsContent.getCell(k).getParagraphs().get(0);
                cellParagraphC.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun cellParagraphCRun = cellParagraphC.createRun();
                cellParagraphCRun.setFontSize(12);
                cellParagraphCRun.setText(ExcelHelper.objectToString(ExcelHelper.getFieldValueByName(fieldName[k], obj)));
            }
        }
        // 设置单元格居中
        if (rows == 0) {
            for (int l = 0; l < titles.length; l++) {
                XWPFTableCell cell = table.getRow(0).getCell(l);
                cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
            }
        } else {
            for (int m = 0; m <= rows; m ++) {
                for (int n = 0; n < titles.length; n++) {
                    XWPFTableCell cell = table.getRow(m).getCell(n);
                    cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                }
            }
        }
        doc.write(out);
        out.flush();
        out.close();
    }
}
