//package com.synway.datastandardmanager.util;
//
//import org.apache.poi.hssf.usermodel.*;
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.util.StringUtils;
//
//import jakarta.servlet.ServletOutputStream;
//import java.io.IOException;
//import java.lang.reflect.Method;
//import java.nio.charset.StandardCharsets;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
///**
// * @author Administrator
// */
//public class ExportHelper {
//    private static Logger LOGGER= LoggerFactory.getLogger(ExportHelper.class);
//
//    public static void exportDirectly(ServletOutputStream out,Object obj) throws IOException {
//        StringBuffer sbf = new StringBuffer(obj.toString());
//        sbf.append("\n");
//        out.write(sbf.toString().getBytes());
//        out.flush();
//        out.close();
//    }
//
//    public static void exportToTxt(ServletOutputStream out, List<?> list, String name, String[] titles, String[] fieldName) throws IOException {
//        StringBuffer sbf = new StringBuffer();
//        for (String title : titles) {
//            sbf.append(title).append("\t");
//        }
//        sbf.append("\n");
//        out.write(sbf.toString().getBytes());
//        for (int i = 0; i < list.size(); i++) {
//            out.write(handleExportData(list.get(i), fieldName).getBytes());
//        }
//        out.flush();
//        out.close();
//    }
//
//
////    public static void exportToCsv(ServletOutputStream out, List<?> list, String name, String[] titles, String[] fieldName) throws IOException {
////        StringBuffer sbf = new StringBuffer();
////        for (String title : titles) {
////            sbf.append(title).append(",");
////        }
////        sbf.append("\n");
////        out.write(new byte[]{(byte)0xEF, (byte)0xbb, (byte)0xbF});
////        out.write(sbf.toString().getBytes());
////        for (int i = 0; i < list.size(); i++) {
////            out.write(handleExportData(list.get(i), fieldName).getBytes());
////        }
////        out.flush();
////        out.close();
////    }
//
//    /**
//     * 处理csv导出数据
//     */
//    private static String handleExportData(Object exportData, String[] fieldName) {
//        StringBuffer sbf = new StringBuffer();
//        for (String field : fieldName) {
//            String val = objectToString(getFieldValueByName(field, exportData));
//            if (org.apache.commons.lang.StringUtils.equals("", val)) {
//                sbf.append("-,");
//            } else {
//                String temp = val.replaceAll("\r", "").replaceAll("\n", "");
//                if (temp.contains(",")) {
//                    if (temp.contains("\"")) {
//                        temp = temp.replaceAll("\"", "\"\"");
//                    }
//                    temp = "\""+ temp + "\"";
//                }
//                sbf.append(temp).append(",");
//            }
//        }
//        sbf.append("\n");
//        return sbf.toString();
//    }
//
////    public static void exportXlxs(Object object,String[] titles,String sheetname,List<Object>  list,String[] fieldName,ServletOutputStream out) throws Exception {
////        //单个sheet页可存储的数据量
////        int sheetCount = 200;
////        try{
////            XSSFWorkbook workbook = new XSSFWorkbook();
//////            CellStyle hssfTitleCellStyle = getHeader(workbook);
//////            CellStyle hssfContentCellStyle = getContext(workbook);
////
////            XSSFSheet hssfSheet = workbook.createSheet(sheetname);
////            //创建第一行
////            XSSFRow row = hssfSheet.createRow(0);
////            XSSFCell hssfCell = null;
////            for (int i = 0; i < titles.length; i++) {
////                //列索引从0开始
////                hssfCell = row.createCell(i);
////                //列名1
////                hssfCell.setCellValue(titles[i]);
////                //列居中显示
//////                hssfCell.setCellStyle(hssfTitleCellStyle);
////            }
////            for (int i = 0; i < list.size(); i++) {
////                if(i!=0 && i%sheetCount==0){
////                    hssfSheet = workbook.createSheet(sheetname+String.valueOf(i/sheetCount));
////                    //创建第一行
////                    row = hssfSheet.createRow(0);
////                    for (int m = 0; m < titles.length; m++) {
////                        //列索引从0开始
////                        hssfCell = row.createCell(m);
////                        //列名1
////                        hssfCell.setCellValue(titles[m]);
////                        //列居中显示
//////                        hssfCell.setCellStyle(hssfTitleCellStyle);
////                    }
////                }
////                if((i+1)%sheetCount==0){
////                    row = hssfSheet.createRow(sheetCount);
////                }else{
////                    row = hssfSheet.createRow((i+1)%sheetCount);
////                }
////                object = list.get(i);
////                //数据填充
////                for(int j=0;j<fieldName.length;j++){
////                    String value = fieldName[j];
////                    String str = objectToString(getFieldValueByName(value, object));
////                    hssfCell = row.createCell(j);
////                    hssfCell.setCellValue(str);
////                    //列居中显示
//////                    hssfCell.setCellStyle(hssfContentCellStyle);
////                }
////                // 列宽自适应
////                for (int k = 0; k < fieldName.length; k++) {
////                    hssfSheet.autoSizeColumn(k);
////                }
////                setSizeColumn(hssfSheet, fieldName.length);
////            }
////            try {
////                workbook.write(out);
////                out.flush();
////                out.close();
////            } catch (Exception e) {
////                LOGGER.error(ExceptionUtil.getExceptionTrace(e));
////            }
////        }catch(Exception e){
////            LOGGER.error(ExceptionUtil.getExceptionTrace(e));
////            throw new Exception("导出信息失败！");
////        }
////    }
//
////    public static void exportXls(Object object, String[] titles, String sheetname, List<Object>  list, String[] fieldName, ServletOutputStream out) throws Exception {
////        //单个sheet页可存储的数据量
////        int sheetCount = 200;
////        try{
////            HSSFWorkbook workbook = new HSSFWorkbook();
////            HSSFCellStyle hssfTitleCellStyle = (HSSFCellStyle)getHeader(workbook);
////            HSSFCellStyle hssfContentCellStyle = (HSSFCellStyle)getContext(workbook);
////
////            HSSFSheet hssfSheet = workbook.createSheet(sheetname);
////            //创建第一行
////            HSSFRow row = hssfSheet.createRow(0);
////            HSSFCell hssfCell = null;
////            for (int i = 0; i < titles.length; i++) {
////                //列索引从0开始
////                hssfCell = row.createCell(i);
////                //列名1
////                hssfCell.setCellValue(titles[i]);
////                //列居中显示
////                hssfCell.setCellStyle(hssfTitleCellStyle);
////            }
////            for (int i = 0; i < list.size(); i++) {
////                if(i!=0 && i%sheetCount==0){
////                    hssfSheet = workbook.createSheet(sheetname+String.valueOf(i/sheetCount));
////                    //创建第一行
////                    row = hssfSheet.createRow(0);
////                    for (int m = 0; m < titles.length; m++) {
////                        //列索引从0开始
////                        hssfCell = row.createCell(m);
////                        //列名1
////                        hssfCell.setCellValue(titles[m]);
////                        //列居中显示
////                        hssfCell.setCellStyle(hssfTitleCellStyle);
////                    }
////                }
////                if((i+1)%sheetCount==0){
////                    row = hssfSheet.createRow(sheetCount);
////                }else{
////                    row = hssfSheet.createRow((i+1)%sheetCount);
////                }
////                object = list.get(i);
////                //数据填充
////                for(int j=0;j<fieldName.length;j++){
////                    String value = fieldName[j];
////                    String str = objectToString(getFieldValueByName(value, object));
////                    hssfCell = row.createCell(j);
////                    hssfCell.setCellValue(str);
////                    //列居中显示
////                    hssfCell.setCellStyle(hssfContentCellStyle);
////                }
////                // 列宽自适应
////                for (int k = 0; k < fieldName.length; k++) {
////                    hssfSheet.autoSizeColumn(k);
////                }
////                setSizeColumn(hssfSheet, fieldName.length);
////            }
////            try {
////                workbook.write(out);
////                out.flush();
////                out.close();
////            } catch (Exception e) {
////                LOGGER.error(ExceptionUtil.getExceptionTrace(e));
////            }
////        }catch(Exception e){
////            LOGGER.error(ExceptionUtil.getExceptionTrace(e));
////            throw new Exception("导出信息失败！");
////        }
////    }
//
////    private static void setSizeColumn(Sheet hssfSheet, int length) {
////        for (int columnNum = 0; columnNum < length; columnNum++) {
////            int columnWidth = hssfSheet.getColumnWidth(columnNum) / 256;
////            for (int rowNum = 0; rowNum <= hssfSheet.getLastRowNum(); rowNum ++) {
////                Row currentRow;
////                if (hssfSheet.getRow(rowNum) == null) {
////                    currentRow = hssfSheet.createRow(rowNum);
////                } else {
////                    currentRow = hssfSheet.getRow(rowNum);
////                }
////                if (null != currentRow.getCell(columnNum)) {
////                    Cell currentCell = currentRow.getCell(columnNum);
////                    if (currentCell.getCellType().equals(CellType.STRING)) {
////                        int len = currentCell.getStringCellValue().getBytes(StandardCharsets.UTF_8).length;
////                        if (columnWidth < len) {
////                            columnWidth = len;
////                        }
////                    }
////                }
////            }
////            //width过大原因
////            hssfSheet.setColumnWidth(columnNum, ((columnWidth > 255) ? 255:columnWidth) * 256);
////        }
////    }
//
//    /**
//     * Object转成String类型，便于填充单元格
//     * */
//    public static String objectToString(Object object){
//        String str = "";
//        if(object==null){
//        }else if(object instanceof Date){
//            DateFormat from_type = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            Date date = (Date)object;
//            str = from_type.format(date);
//        }else if(object instanceof String){
//            str = (String)object;
//        }else if(object instanceof Integer){
//            str = ((Integer)object).intValue()+"";
//        }else if(object instanceof Double){
//            str = ((Double)object).doubleValue()+"";
//        }else if(object instanceof Long){
//            str = Long.toString(((Long)object).longValue());
//        }else if(object instanceof Float){
//            str = Float.toHexString(((Float)object).floatValue());
//        }else if(object instanceof Boolean){
//            str = Boolean.toString((Boolean)object);
//        }else if(object instanceof Short){
//            str = Short.toString((Short)object);
//        }
//        return str;
//    }
//
//    /**
//     *
//     * 根据属性名获取属性值
//     * fieldName 属性名 object 属性所属对象
//     * 支持Map扩展属性, 不支持List类型属性，
//     * return 属性值
//     */
//    public static Object getFieldValueByName(String fieldName, Object object) {
//        try {
//            Object fieldValue = null;
//            if (StringUtils.hasLength(fieldName) && object != null) {
//                // 首字母
//                String firstLetter = "";
//                // get方法
//                String getter = "";
//                // 方法
//                Method method = null;
//                String extraKey = null;
//                // 处理扩展属性 extraData.key
//                if (fieldName.indexOf(Constant.DOT) > 0) {
//                    String[] extra = fieldName.split("\\.");
//                    fieldName = extra[0];
//                    extraKey = extra[1];
//                }
//                firstLetter = fieldName.substring(0, 1).toUpperCase();
//                getter = "get" + firstLetter + fieldName.substring(1);
//                method = object.getClass().getMethod(getter, new Class[] {});
//                fieldValue = method.invoke(object, new Object[] {});
//                if (extraKey != null) {
//                    Map<String, Object> map = (Map<String, Object>) fieldValue;
//                    fieldValue = map==null ? "":map.get(extraKey);
//                }
//            }
//            return fieldValue;
//        } catch (Throwable e) {
//            LOGGER.error(ExceptionUtil.getExceptionTrace(e));
//            return null;
//        }
//    }
//
//    /**
//     * 标题样式
//     */
//    public static CellStyle getHeader(Workbook workbook){
//
//        CellStyle format = workbook.createCellStyle();
//        Font font = workbook.createFont();
//        //加粗
//        font.setBold(true);
//        font.setFontName("黑体");
//        font.setFontHeightInPoints((short)10);
//        format.setAlignment(HorizontalAlignment.CENTER);
//        format.setVerticalAlignment(VerticalAlignment.CENTER);
//        format.setFont(font);
//        return format;
//    }
//
//    /**
//     * 内容样式
//     */
//    public static CellStyle getContext(Workbook workbook){
//        CellStyle format = workbook.createCellStyle();
//        Font font = workbook.createFont();
//        font.setFontName("宋体");
//        format.setAlignment(HorizontalAlignment.CENTER);
//        format.setVerticalAlignment(VerticalAlignment.CENTER);
//        format.setFont(font);
//        return format;
//    }
//
////    /**
////     * 导出数据质量的错误数据，
////     *      由于涉及业务，此处为定制化代码
////     * @author Chen KaiWei
////     * @date 2020/10/30 10:03
////     * @param colsName 列名
////     * @param data 数据
////     * @param out
////     * @return
////     * @throws
////     */
////    public static void exportDQCDirtyData(List<String> colsName, List<DqcDirtyData> data, ServletOutputStream out) throws Exception{
////        int cols = colsName.size();
////        int rowNum = data.size();
////
////        LOGGER.info("行:"+rowNum+" 列:"+cols);
////
////        //创建行列
////        int rowPreSheet=200,sheetNum=1;
////        HSSFWorkbook workbook = new HSSFWorkbook();
////        HSSFSheet hssfSheet = null;
////        for (int i=0;i < rowNum;i++){
////            if(i % rowPreSheet == 0){
////                hssfSheet = workbook.createSheet("错误数据页" + sheetNum++);
////            }
////            HSSFRow row = hssfSheet.createRow(i % rowPreSheet);
////            for (int j=0;j < cols;j++){
////                row.createCell(j);
////            }
////        }
////
////        //填充数据
////        int dataRowNum = 0;
////        Iterator<Sheet> iterator = workbook.sheetIterator();
////        while(iterator.hasNext()){
////            HSSFSheet sheet = (HSSFSheet)iterator.next();
////            Iterator<Row> rowIterator = sheet.rowIterator();
////
////            while(rowIterator.hasNext()){
////                HSSFRow row = (HSSFRow)rowIterator.next();
////                Iterator<Cell> cellIterator = row.cellIterator();
////                DqcDirtyData dqcDirtyData = data.get(dataRowNum++);
////                //第一行写标题
////                if(row.getRowNum()==0){
////                    for (int k=0;k< colsName.size();k++) {
////                        row.getCell(k).setCellStyle(getHeader(workbook));
////                        row.getCell(k).setCellValue(colsName.get(k));
////                    }
////                    continue;
////                }
////                //渲染有错误的行
////                setErrorCellFont(row,workbook,dqcDirtyData);
////
////                while(cellIterator.hasNext()){
////                    HSSFCell cell = (HSSFCell)cellIterator.next();
////                    String colName = colsName.get(cell.getColumnIndex());
////                    Record record = dqcDirtyData.getRecord();
////                    Column column = record.getColumnByColName(colName);
////                    cell.setCellValue((String)column.getRawData());
////                }
////            }
////        }
////        try {
////            workbook.write(out);
////            out.flush();
////            out.close();
////        } catch (Exception e) {
////            LOGGER.error(ExceptionUtil.getExceptionTrace(e));
////        }
////    }
//
////    /**
////     * 将错误数据标红
////     */
////    public static void setErrorCellFont(HSSFRow row,HSSFWorkbook workbook,DqcDirtyData dqcDirtyData){
////
////        CellStyle cellStyle = workbook.createCellStyle();
////        Font font = workbook.createFont();
////        font.setFontName("宋体");
////        font.setColor(Font.COLOR_RED);
////        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
////        cellStyle.setAlignment(HorizontalAlignment.CENTER);
////        cellStyle.setFont(font);
////
////        Set<String> errorRow = new HashSet<>();
////        Collections.addAll(errorRow,dqcDirtyData.getFieldUniqueness().split(Constant.DEFAULT_DELIMITER));
////        Collections.addAll(errorRow,dqcDirtyData.getFormatValidity().split(Constant.DEFAULT_DELIMITER));
////        Collections.addAll(errorRow,dqcDirtyData.getLogicalValidity().split(Constant.DEFAULT_DELIMITER));
////        Collections.addAll(errorRow,dqcDirtyData.getRangeValidity().split(Constant.DEFAULT_DELIMITER));
////        Collections.addAll(errorRow, dqcDirtyData.getInceptRealTime().split(Constant.DEFAULT_DELIMITER));
////        Collections.addAll(errorRow,dqcDirtyData.getPrimaykeyUniqueness().split(Constant.DEFAULT_DELIMITER));
////        Collections.addAll(errorRow,dqcDirtyData.getAttributeCompleteness().split(Constant.DEFAULT_DELIMITER));
////
////        Iterator<String> iterator = errorRow.iterator();
////        while(iterator.hasNext()){
////            String errorNum = iterator.next();
////            int cellNumber = Integer.parseInt(errorNum);
////            if(cellNumber == 0){
////                continue;
////            }
////            row.getCell(Integer.parseInt(errorNum)).setCellStyle(cellStyle);
////        }
////    }
//
//}
