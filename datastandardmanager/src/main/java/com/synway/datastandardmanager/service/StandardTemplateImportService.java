package com.synway.datastandardmanager.service;


import com.synway.datastandardmanager.pojo.standardtemplateexcel.ObjectFieldSheet;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 标准管理的excel文件导入导出的相关信息
 * @author wangdongwei
 */
public interface StandardTemplateImportService {

    /**
     * 导出(下载)标准文件
     * @param response
     * @param name 文件名
     * @param tableIdList 标准协议列表
     */
    void downloadObjectInfoExcel(HttpServletResponse response, String name, List<String> tableIdList);

    /**
     * 导出(下载)标准sql文本
     * @param response
     * @param name 文件名
     * @param tableIdList 标准信息tableId列表
     */
    void downloadObjectInfoSql(HttpServletResponse response, String name,List<String> tableIdList);

    /**
     * 导入标准信息和标准字段
     *
     * @param file 导入的文件
     * @param objectFieldSheetPage 标准信息页脚
     * @param objectSheetPage 标准字段页脚
     * @return
     */
    String importObjectInfoExcel(MultipartFile[] file,Integer objectSheetPage,Integer objectFieldSheetPage);

    //    /**
//     * 下载模板Excel模板文件
//     * @param response
//     */
//    void downloadTemplateExcel(HttpServletResponse response);

    void downloadFieldTempExcel(HttpServletResponse response);
    List<ObjectFieldSheet> analysisFieldExcel(MultipartFile multipartFile, List<ObjectFieldSheet> fieldInfoList, int type);

}
