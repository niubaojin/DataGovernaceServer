package com.synway.datastandardmanager.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.synway.datastandardmanager.listener.ExcelListener;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author wdw
 * @version 1.0
 * @date 2021/6/9 19:23
 */
public class EasyExcelUtil {
    public static List readExcelUtil(MultipartFile file, Object object, ExcelListener excelListener) throws IOException {
        InputStream inputStream = file.getInputStream();
        ExcelReader excelReader = EasyExcel.read(inputStream, object.getClass(), excelListener).build();
        try {
            ReadSheet readSheet = EasyExcel.readSheet(0).build();
            excelReader.read(readSheet);
        } finally {
            excelReader.finish();
        }
        return excelListener.getVoList();
    }

    public static List readExcelSheetUtil(MultipartFile file, Object object, ExcelListener excelListener, Integer sheetPage) throws IOException {

        InputStream inputStream = file.getInputStream();
        ExcelReader excelReader = EasyExcel.read(inputStream, object.getClass(), excelListener).build();
        try {

            ReadSheet readSheet = EasyExcel.readSheet(sheetPage).build();
            readSheet.setHeadRowNumber(1);
            excelReader.read(readSheet);
        } finally {
            excelReader.finish();
        }
        return excelListener.getVoList();
    }

    public static List readObjectExcelSheetUtil(MultipartFile file, Object object, ExcelListener excelListener, Integer sheetPage) throws IOException {

        InputStream inputStream = file.getInputStream();
        ExcelReader excelReader = EasyExcel.read(inputStream, object.getClass(), excelListener).build();
        try {

            ReadSheet readSheet = EasyExcel.readSheet(sheetPage).build();
            readSheet.setHeadRowNumber(1);
            excelReader.read(readSheet);
        } finally {
            excelReader.finish();
        }
        return excelListener.getVoList();
    }

    public static List readObjectFieldExcelSheetUtil(MultipartFile file, Object object, ExcelListener excelListener, Integer sheetPage) throws IOException {
        InputStream inputStream = file.getInputStream();
        ExcelReader excelReader = EasyExcel.read(inputStream, object.getClass(), excelListener).build();
        try {
            ReadSheet readSheet = EasyExcel.readSheet(sheetPage).build();
            readSheet.setHeadRowNumber(1);
            excelReader.read(readSheet);
        } finally {
            excelReader.finish();
        }
        return excelListener.getVoList();
    }

}
