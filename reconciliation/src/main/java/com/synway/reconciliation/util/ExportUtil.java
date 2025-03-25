package com.synway.reconciliation.util;

import com.synway.reconciliation.common.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

/**
 * 导出工具类
 * @author ym
 */
public class ExportUtil {

    private static Logger logger = LoggerFactory.getLogger(ExportUtil.class);

    /**
     * 导出csv
     * @param out
     * @param list
     * @param name
     * @param titles
     * @param fieldName
     * @param indexVisible
     * @return void
     * @throws IOException
     */
    public static void exportToCsv(ServletOutputStream out, List<Object> list, String name, String[] titles, String[] fieldName, boolean indexVisible) throws IOException {
        StringBuffer sbf = new StringBuffer();
        // 序号列
        if(indexVisible){
            sbf.append("序号").append(",");
        }
        for (String title : titles) {
            sbf.append(title).append(",");
        }
        sbf.append("\n");
        out.write(new byte[]{(byte)0xEF, (byte)0xbb, (byte)0xbF});
        out.write(sbf.toString().getBytes());
        for (int i = 0; i < list.size(); i++) {
            out.write(handleExportData(list.get(i), i, fieldName, indexVisible).getBytes());
        }
        out.flush();
        out.close();
    }

    /**
     * 处理csv导出数据
     * @param exportData
     * @param i
     * @param fieldName
     * @param indexVisible
     * @return java.lang.String
     */
    public static String handleExportData(Object exportData, int i, String[] fieldName, boolean indexVisible) {
        StringBuffer sbf = new StringBuffer();
        if (indexVisible) {
            sbf.append(i + 1).append(",");
        }
        for (String field : fieldName) {
            addStringBuffer(sbf, exportData, field);
        }
        sbf.append("\n");
        return sbf.toString();
    }

    /**
     * 特殊字符转义替换
     * @param sbf
     * @param exportData
     * @param field
     * @return void
     */
    public static void addStringBuffer(StringBuffer sbf, Object exportData, String field) {
        String val = ExcelHelper.objectToString(ExcelHelper.getFieldValueByName(field, exportData));
        if (StringUtils.equals("", val)) {
            sbf.append("-,");
        } else {
            String temp = val.replaceAll("\r", "").replaceAll("\n", "");
            if (temp.contains(Constants.CHARACTER_COMMA)) {
                if (temp.contains("\"")) {
                    temp = temp.replaceAll("\"", "\"\"");
                }
                temp = "\""+ temp + "\"";
            }
            sbf.append(temp).append(",");
        }
    }

    /**
     * 导出word
     * @param out
     * @param list
     * @param name
     * @param titles
     * @param fieldName
     * @param indexVisible
     * @return void
     */
    public static void exportToWord(ServletOutputStream out, List<Object> list, String name, String[] titles, String[] fieldName, boolean indexVisible) {
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
        pageSize.setW(BigInteger.valueOf(15840));
        pageSize.setH(BigInteger.valueOf(12240));
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
        CTTblWidth width = table.getCTTbl().addNewTblPr().addNewTblW();
        width.setType(STTblWidth.DXA);
        width.setW(BigInteger.valueOf(12240));
        // 表头
        XWPFTableRow rowHead = table.getRow(0);
        XWPFParagraph cellParagraph = rowHead.getCell(0).getParagraphs().get(0);
        XWPFRun cellParagraphRun = cellParagraph.createRun();
        // 序号列
        if (indexVisible) {
            cellParagraph.setAlignment(ParagraphAlignment.CENTER);
            cellParagraphRun = cellParagraph.createRun();
            cellParagraphRun.setFontSize(14);
            cellParagraphRun.setBold(true);
            cellParagraphRun.setText("序号");
        }
        for (int i = 0; i < titles.length; i++) {
            if (indexVisible || i > 0) {
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
            // 序号列
            if(indexVisible){
                XWPFParagraph cellParagraphC = rowsContent.getCell(0).getParagraphs().get(0);
                cellParagraphC.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun cellParagraphCRun = cellParagraphC.createRun();
                cellParagraphCRun.setFontSize(12);
                cellParagraphCRun.setText(String.valueOf(j+1));
            }
            for (int k = 0; k < fieldName.length; k++) {
                XWPFParagraph cellParagraphC = rowsContent.getCell(indexVisible ? k + 1 : k).getParagraphs().get(0);
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
        try {
            doc.write(out);
            out.flush();
        } catch (IOException e) {
            logger.error(e.toString());
        } finally {
            FileUtil.close(out);
            FileUtil.close(doc);
        }
    }
}
