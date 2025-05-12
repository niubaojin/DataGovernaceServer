package com.synway.datastandardmanager.util;


import com.synway.datastandardmanager.service.impl.DbManageServiceImpl;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImportHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImportHelper.class);

    public static  List<Map> importExcel(File file) {
        try {
            InputStream is = new FileInputStream(file);
            Workbook workbook = WorkbookFactory.create(is);
            Sheet sheet = workbook.getSheetAt(0);
            //获得表头
            Row rr =sheet.getRow(0);
            //保存表头
            String[] arrHead =new String[rr.getPhysicalNumberOfCells()];
            int j=0;
            for(Cell ce :rr){
                int roC= rr.getPhysicalNumberOfCells();
                int ceC=ce.getColumnIndex();
                arrHead[j]=ce.getStringCellValue();
                j++;
            }
            //构建List对象集合
            List<Map> list =new ArrayList<Map>();
            for (int i=1;i<sheet.getPhysicalNumberOfRows();i++){
                Row row  =sheet.getRow(i);
                Map<String,Object> map =new HashMap<String,Object>();
                for(int k=0;k<row.getPhysicalNumberOfCells();k++){
                    //获取行数据
                    Cell cell =row.getCell(k);
                    if(cell == null) {
                        continue;
                    }
                    //获取值并自己格式化
                    switch(cell.getCellType()){
                        case CellType.STRING:
                            map.put(arrHead[k],cell.getStringCellValue());
                            break;
                        case CellType.NUMERIC:
                            int number =(int)cell.getNumericCellValue();
                            map.put(arrHead[k],number);
                            break;
                        case CellType.BLANK:
                            String uu=" ";
                            map.put(arrHead[k],uu);
                            break;
                        default:
                            break;
                    }
                }
                list.add(map);
            }
            return  list;
        } catch (Exception e) {
            LOGGER.error("exceL解析错误"+e.getMessage());
        }
        return new ArrayList<Map>();
    }

    public static  List<Map> importExcel2(File file) throws Exception {
        InputStream is = new FileInputStream(file);
        Workbook workbook = WorkbookFactory.create(is);
        Sheet sheet = workbook.getSheetAt(0);
        //获得表头
        Row rr =sheet.getRow(0);
        //保存表头
        String[] arrHead =new String[rr.getPhysicalNumberOfCells()];
        int j=0;
        for(Cell ce :rr){
            int roC= rr.getPhysicalNumberOfCells();
            int ceC=ce.getColumnIndex();
            arrHead[j]=ce.getStringCellValue();
            j++;
        }
        //构建List对象集合
        List<Map> list =new ArrayList<Map>();
        for (int i=1;i<sheet.getPhysicalNumberOfRows();i++){
            Row row  =sheet.getRow(i);
            Map<String,Object> map =new HashMap<String,Object>();
            for(int k=0;k<row.getPhysicalNumberOfCells();k++){
                //获取行数据
                Cell cell =row.getCell(k);
                if(cell == null) {
                    continue;
                }
                //获取值并自己格式化
                switch(cell.getCellType()){
                    case CellType.STRING:
                        map.put(arrHead[k],cell.getStringCellValue());
                        break;
                    case CellType.NUMERIC:
                        int number =(int)cell.getNumericCellValue();
                        map.put(arrHead[k],number);
                        break;
                    case CellType.BLANK:
                        String uu=" ";
                        map.put(arrHead[k],uu);
                        break;
                    default:
                        break;
                }
            }
            list.add(map);
        }
        return  list;
    }

    public static  List<Map> importExcel3(MultipartFile file) {
        try {
            InputStream is = file.getInputStream();
            Workbook workbook = WorkbookFactory.create(is);
            Sheet sheet = workbook.getSheetAt(0);
            //获得表头
            Row rr =sheet.getRow(0);
            //保存表头
            String[] arrHead =new String[rr.getPhysicalNumberOfCells()];
            int j=0;
            for(Cell ce :rr){
                int roC= rr.getPhysicalNumberOfCells();
                int ceC=ce.getColumnIndex();
                ce.setCellType(CellType.STRING);
                arrHead[j]=ce.getStringCellValue().toUpperCase().trim();
                j++;
            }
            //构建List对象集合
            List<Map> list =new ArrayList<Map>();
            for (int i=1;i<sheet.getPhysicalNumberOfRows();i++){
                Row row  =sheet.getRow(i);
                Map<String,Object> map =new HashMap<String,Object>();
                for(int k=0;k<row.getPhysicalNumberOfCells();k++){
                    //获取行数据
                    Cell cell =row.getCell(k);
                    if(cell == null) {
                        continue;
                    }
                    //获取值并自己格式化
                    switch(cell.getCellType()){
                        case CellType.STRING:
                            map.put(arrHead[k],cell.getStringCellValue());
                            break;
                        case CellType.NUMERIC:
                            int number =(int)cell.getNumericCellValue();
                            map.put(arrHead[k],number);
                            break;
                        case CellType.BLANK:
                            String uu=" ";
                            map.put(arrHead[k],uu);
                            break;
                        default:
                            break;
                    }
                }
                list.add(map);
            }
            return  list;
        } catch (Exception e) {
            LOGGER.error("exceL解析错误"+e.getMessage());
        }
        return new ArrayList<Map>();
    }

}
