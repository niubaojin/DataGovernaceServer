package com.synway.datastandardmanager.service;

import com.synway.datastandardmanager.entity.vo.importDownload.ObjectFieldSheetVO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface DataSetStandardDownloadService {

    /**
     * 导入标准信息和标准字段
     *
     * @param file                 导入的文件
     * @param objectFieldSheetPage 标准信息页脚
     * @param objectSheetPage      标准字段页脚
     */
    String importObjectInfoExcel(MultipartFile[] file, Integer objectSheetPage, Integer objectFieldSheetPage);

    /**
     * 导出(下载)标准文件
     *
     * @param tableIdList 标准协议列表
     */
    void downloadObjectInfoExcel(HttpServletResponse response, List<String> tableIdList) throws IOException;

    /**
     * 导出(下载)标准sql文本
     *
     * @param tableIdList 标准信息tableId列表
     */
    void downloadObjectInfoSql(HttpServletResponse response, List<String> tableIdList);

    void downloadFieldTempExcel(HttpServletResponse response);

    List<ObjectFieldSheetVO> analysisFieldExcel(MultipartFile multipartFile);

    /**
     * 导出标准建表语句
     */
    void downloadCreateTableSql(HttpServletResponse response, List<String> tableIdList, String dsType);

}
