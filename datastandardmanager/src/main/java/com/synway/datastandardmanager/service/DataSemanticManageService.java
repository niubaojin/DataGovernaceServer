package com.synway.datastandardmanager.service;

import com.synway.datastandardmanager.entity.pojo.SameWordEntity;
import com.synway.datastandardmanager.entity.vo.PageVO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DataSemanticManageService {

    /**
     * 数据语义管理页面表数据
     *
     * @param sameId   语义id
     * @param word     语义英文名称
     * @param wordName 语义中文名称
     */
    public List<SameWordEntity> findByCondition(String sameId, String wordName, String word);

    void semanticTableExport(HttpServletResponse response, String sameId, String wordName, String word, String elementObject);

    /**
     * 添加一种语义信息
     */
    String addOneSemanticManage(SameWordEntity sameword);

    /**
     * 删除一种语义信息
     */
    String delOneSemanticManage(SameWordEntity sameword);

    String delAllSemanticManage(List<SameWordEntity> samewords);

    PageVO getMetadataDefineTableBySameid(int pageIndex, int pageSize, String sameId);

    /**
     * 根据上传的excel文件中获取到语义数据导入到数据库中
     */
    String uploadSemanticFile(MultipartFile semanticFile);

    void downSemanticTableTemplateFile(HttpServletResponse response);

}
