package com.synway.datarelation.util;


import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import jakarta.servlet.ServletOutputStream;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class ExcelHelper {
    private static Logger logger = LoggerFactory.getLogger(ExcelHelper.class);

	public static void export(Object object,String[] titles,String sheetname,List<Object>  list,String[] fieldName,ServletOutputStream out) throws Exception
	{
		int sheetCount = 20000;//单个sheet页可存储的数据量
		try{
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFCellStyle hssfTitleCellStyle = getHeader(workbook);
			HSSFCellStyle hssfContentCellStyle = getContext(workbook);

            HSSFSheet hssfSheet = workbook.createSheet(sheetname);
            HSSFRow row = hssfSheet.createRow(0);//创建第一行
            HSSFCell hssfCell = null;
            for (int i = 0; i < titles.length; i++) {
                hssfCell = row.createCell(i);//列索引从0开始
                hssfCell.setCellValue(titles[i]);//列名1
                hssfCell.setCellStyle(hssfTitleCellStyle);//列居中显示
            }
            for (int i = 0; i < list.size(); i++) {

            	    if(i!=0 && i%sheetCount==0){
            	    	hssfSheet = workbook.createSheet(sheetname+String.valueOf(i/sheetCount));
            	    	row = hssfSheet.createRow(0);//创建第一行
            	    	for (int m = 0; m < titles.length; m++) {
            	                hssfCell = row.createCell(m);//列索引从0开始
            	                hssfCell.setCellValue(titles[m]);//列名1
            	                hssfCell.setCellStyle(hssfTitleCellStyle);//列居中显示
            	        }
                    }
            	    if((i+1)%sheetCount==0){
            	    	row = hssfSheet.createRow(sheetCount);
            	    }else{
            	    	row = hssfSheet.createRow((i+1)%sheetCount);
            	    }
                    object = list.get(i);
                    //数据填充
                    for(int j=0;j<fieldName.length;j++){
                    	String value = fieldName[j];
                    	String str = objectToString(getFieldValueByName(value, object));
                    	hssfCell = row.createCell(j);
                    	hssfCell.setCellValue(str);
                    	hssfCell.setCellStyle(hssfContentCellStyle);//列居中显示
                    }

            }
            try {
                   workbook.write(out);
                   out.flush();
                   out.close();
            } catch (Exception e) {
                logger.error(e.getMessage());
                //e.printStackTrace();
            }
        }catch(Exception e){
            //e.printStackTrace();
            logger.error(e.getMessage());
           throw new Exception("导出信息失败！");
        }
	}


    /**
     *  支持多行表头
     * @param mergeList 合并表格
     * @param titleList 标题
     * @param fieldNames 需要写入的字段名
     * @param list 数据集合
     * @param out 输出流对象
     * @throws Exception
     */
    public static void export(List<int[]> mergeList, List<String[]> titleList, String[] fieldNames, List<?> list, ServletOutputStream out) throws Exception {
        int sheetCount = 20000;//单个sheet页可存储的数据量
        String sheetName = "sheet";
        try{
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFCellStyle hssfTitleCellStyle = getHeader(workbook);
            HSSFCellStyle hssfContentCellStyle = getContext(workbook);
            HSSFSheet hssfSheet = null;
            HSSFRow row = null;
            HSSFCell hssfCell = null;
            Object obj = null;

            /**
             *  处理 无数据时导出的excel打开报错，创建表头
             */
            if(list.size()==0){
            	hssfSheet = workbook.createSheet(sheetName);
                int merge1[];
                if(mergeList!=null){
                    for (int j = 0; j < mergeList.size(); j++) {
                        merge1 = mergeList.get(j);
                        hssfSheet.addMergedRegion(new CellRangeAddress(merge1[0],merge1[1],merge1[2],merge1[3]));
                    }
                }
                String[] title1 = null;
                //设置表头,
                for (int j = 0; j < titleList.size(); j++) {
                    row = hssfSheet.createRow(j);//创建第一行
                    title1 = titleList.get(j);
                    for (int k = 0; k < title1.length; k++) {
                        hssfCell = row.createCell(k);//列索引从0开始
                        hssfCell.setCellValue(title1[k]);//列名1
                        hssfCell.setCellStyle(hssfTitleCellStyle);//列居中显示
                    }
                }
            }

            for (int i = 0; i < list.size(); i++) {
                //新建一个Sheet，并设置表头
                if(i%sheetCount==0){
                    sheetName = "sheet"+i/sheetCount;
                    hssfSheet = workbook.createSheet(sheetName);
                    int merge[];
                    if(mergeList!=null){
                        for (int j = 0; j < mergeList.size(); j++) {
                            merge = mergeList.get(j);
                            hssfSheet.addMergedRegion(new CellRangeAddress(merge[0],merge[1],merge[2],merge[3]));
                        }
                    }
                    String[] title = null;
                    //设置表头,
                    for (int j = 0; j < titleList.size(); j++) {
                        row = hssfSheet.createRow(j);//创建第一行
                        title = titleList.get(j);
                        for (int k = 0; k < title.length; k++) {
                            hssfCell = row.createCell(k);//列索引从0开始
                            hssfCell.setCellValue(title[k]);//列名1
                            hssfCell.setCellStyle(hssfTitleCellStyle);//列居中显示
                        }
                    }
                }
                obj = list.get(i);
                row = hssfSheet.createRow(i%sheetCount+titleList.size());
                for(int j=0;j<fieldNames.length;j++){
                    String fieldName = fieldNames[j];
                    hssfCell = row.createCell(j);
                    hssfCell.setCellStyle(hssfContentCellStyle);//列居中显示
                    hssfCell.setCellValue(BeanUtils.getProperty(obj,fieldName));
                }
            }
            try {
                workbook.write(out);
                out.flush();
                out.close();
            } catch (Exception e) {
                out.close();
                logger.error(e.getMessage());
//                e.printStackTrace();
            }
        }catch(Exception e){
//            e.printStackTrace();
            logger.error(e.getMessage());
            throw new Exception("导出信息失败！");
        }
    }

    /**
     * Object转成String类型，便于填充单元格
     * */
    public static String objectToString(Object object){
        String str = "";
        if(object==null){
        }else if(object instanceof Date){
                DateFormat from_type = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = (Date)object;
                str = from_type.format(date);
        }else if(object instanceof String){
            str = (String)object;
        }else if(object instanceof Integer){
            str = ((Integer)object).intValue()+"";
        }else if(object instanceof Double){
            str = ((Double)object).doubleValue()+"";
        }else if(object instanceof Long){
            str = Long.toString(((Long)object).longValue());
        }else if(object instanceof Float){
            str = Float.toHexString(((Float)object).floatValue());
        }else if(object instanceof Boolean){
            str = Boolean.toString((Boolean)object);
        }else if(object instanceof Short){
            str = Short.toString((Short)object);
        }
        return str;
    }

    /**
     *
     * 根据属性名获取属性值
     * fieldName 属性名 object 属性所属对象
     * 支持Map扩展属性, 不支持List类型属性，
     * return 属性值
     */
    public static Object getFieldValueByName(String fieldName, Object object) {
        try {
            Object fieldValue = null;
            if (StringUtils.hasLength(fieldName) && object != null) {
                String firstLetter = ""; // 首字母
                String getter = ""; // get方法
                Method method = null; // 方法
                String extraKey = null;
                // 处理扩展属性 extraData.key
                if (fieldName.indexOf(".") > 0) {
                    String[] extra = fieldName.split("\\.");
                    fieldName = extra[0];
                    extraKey = extra[1];
                }
                firstLetter = fieldName.substring(0, 1).toUpperCase();
                getter = "get" + firstLetter + fieldName.substring(1);
                method = object.getClass().getMethod(getter, new Class[] {});
                fieldValue = method.invoke(object, new Object[] {});
                if (extraKey != null) {
                    Map<String, Object> map = (Map<String, Object>) fieldValue;
                    fieldValue = map==null ? "":map.get(extraKey);
                }
            }
            return fieldValue;
        } catch (Throwable e) {
            logger.error(e.getMessage());
//            e.printStackTrace();
            return null;
        }
    }

    //标题样式
    public static HSSFCellStyle getHeader(HSSFWorkbook workbook){

        HSSFCellStyle format = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
//        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  //加粗
        font.setFontName("黑体");
        font.setFontHeightInPoints((short)10);
//        format.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//        format.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        format.setFont(font);
        return format;
    }

    //内容样式
    public static HSSFCellStyle getContext(HSSFWorkbook workbook){
        HSSFCellStyle format = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setFontName("宋体");
//        format.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//        format.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        format.setFont(font);
        return format;
    }

    /**
     * 支持导入两个excel
     * 2019/05/21  12:50
     * **/
    public static void exportTwoList(Object object,String[] titles,String[] titles1,String sheetname,List<Object>  list,List<Object>  list1,String[] fieldName,String[] fieldName1,ServletOutputStream out) throws Exception
    {
        int sheetCount = list.size();//单个sheet页可存储的数据量
        try{
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFCellStyle hssfTitleCellStyle = getHeader(workbook);
            HSSFCellStyle hssfContentCellStyle = getContext(workbook);

            HSSFSheet hssfSheet = workbook.createSheet(sheetname);
            HSSFRow row = hssfSheet.createRow(0);//创建第一行
            HSSFCell hssfCell = null;
            for (int i = 0; i < titles.length; i++) {
                hssfCell = row.createCell(i);//列索引从0开始
                hssfCell.setCellValue(titles[i]);//列名1
                hssfCell.setCellStyle(hssfTitleCellStyle);//列居中显示
            }
            for (int i = 0; i <=list.size(); i++) {
                if (i != 0 && i % sheetCount == 0) {
                    hssfSheet = workbook.createSheet(sheetname + String.valueOf(i / sheetCount));
                    row = hssfSheet.createRow(0);//创建第一行
                    for (int m = 0; m < titles1.length; m++) {
                        hssfCell = row.createCell(m);//列索引从0开始
                        hssfCell.setCellValue(titles1[m]);//列名1
                        hssfCell.setCellStyle(hssfTitleCellStyle);//列居中显示
                    }
                    sheetCount =list1.size();
                    for (int t = 0; t< list1.size(); t++) {
                        if (t != 0 && t % sheetCount == 0) {
                            hssfSheet = workbook.createSheet(sheetname + String.valueOf(t / sheetCount));
                            row = hssfSheet.createRow(0);//创建第一行
                            for (int s= 0; s < titles1.length; s++) {
                                hssfCell = row.createCell(s);//列索引从0开始
                                hssfCell.setCellValue(titles1[s]);//列名1
                                hssfCell.setCellStyle(hssfTitleCellStyle);//列居中显示
                            }

                        }
                        if ((t + 1) % sheetCount == 0) {
                            row = hssfSheet.createRow(sheetCount);
                        } else {
                            row = hssfSheet.createRow((t + 1) % sheetCount);
                        }
                        object = list1.get(t);
                        //数据填充
                        for (int j = 0; j < fieldName1.length; j++) {
                            String value = fieldName1[j];
                            String str = objectToString(getFieldValueByName(value, object));
                            hssfCell = row.createCell(j);
                            hssfCell.setCellValue(str);
                            hssfCell.setCellStyle(hssfContentCellStyle);//列居中显示
                        }
                    }
                    try {
                        workbook.write(out);
                        out.flush();
                        out.close();
                    } catch (Exception e) {
                        logger.error(e.getMessage());
                    }
                }
                if ((i + 1) % sheetCount == 0) {
                    row = hssfSheet.createRow(sheetCount);
                } else {
                    row = hssfSheet.createRow((i + 1) % sheetCount);
                }
                object = list.get(i);
                //数据填充
                for (int j = 0; j < fieldName.length; j++) {
                    String value = fieldName[j];
                    String str = objectToString(getFieldValueByName(value, object));
                    hssfCell = row.createCell(j);
                    hssfCell.setCellValue(str);
                    hssfCell.setCellStyle(hssfContentCellStyle);//列居中显示
                }

            }
            try {
                workbook.write(out);
                out.flush();
                out.close();
            } catch (Exception e) {
//                e.printStackTrace();
                logger.error(e.getMessage());
            }
        }catch(Exception e){
//            e.printStackTrace();
            logger.error(e.getMessage());
            throw new Exception("导出信息失败！");
        }
    }
}
