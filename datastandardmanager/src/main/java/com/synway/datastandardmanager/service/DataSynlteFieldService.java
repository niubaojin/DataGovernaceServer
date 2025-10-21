package com.synway.datastandardmanager.service;

import com.synway.datastandardmanager.entity.dto.DataSynlteFieldDTO;
import com.synway.datastandardmanager.entity.dto.SynlteFieldStatusDTO;
import com.synway.datastandardmanager.entity.pojo.SynlteFieldEntity;
import com.synway.datastandardmanager.entity.vo.KeyValueVO;
import com.synway.datastandardmanager.entity.vo.PageVO;
import com.synway.datastandardmanager.entity.vo.SynlteFieldFilterVO;
import com.synway.datastandardmanager.entity.vo.TableInfoVO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DataSynlteFieldService {

    PageVO<SynlteFieldEntity> querySynlteFieldList(DataSynlteFieldDTO synlteFieldDTO);

    SynlteFieldFilterVO getFilterObjectForSF();

    List<String> getSearchNameSuggest(String searchName);

    String updateSynlteFieldStatus(SynlteFieldStatusDTO synlteFieldStatusDTO);

    String addSynlteField(SynlteFieldEntity synlteField);

    String updateSynlteField(SynlteFieldEntity synlteField);

    List<KeyValueVO> getSameWordList(String searchName);

    List<KeyValueVO> getSelectObjectByName(String searchName);

    String getSimChinese(String name, List<String> oldSimList);

    List<KeyValueVO> searchDataSecurityLevel();

    SynlteFieldEntity searchSynlteFieldById(String fieldId);

    List<KeyValueVO> getGadsjFieldByText(String searchText, String fieldType);

    /**
     * 下载(导出)数据元
     *
     * @param response
     * @param dto      数据元列表
     * @param name     excel文档名
     * @param object
     */
    void downloadSynlteFieldExcel(HttpServletResponse response,
                                  DataSynlteFieldDTO dto,
                                  String name,
                                  Object object);

    /**
     * 导入数据元
     *
     * @param file
     */
    String importSynlteFieldExcel(MultipartFile file);

    String clearImportSynlteFieldExcel(MultipartFile file);

    /**
     * 导出数据元sql文件
     *
     * @param dto      数据元过滤参数
     * @param response
     * @param fileName 文件名
     */
    void downloadSynlteFieldSql(DataSynlteFieldDTO dto, HttpServletResponse response, String fileName);

    /**
     * 数据元管理页面 关联数据集
     *
     * @param pageIndex
     * @param pageSize
     * @param fieldId    数据元唯一标识
     * @param searchName 模糊搜索
     */
    PageVO<TableInfoVO> getAllTableNameByFieldId(int pageIndex, int pageSize, String fieldId, String searchName);

    void metadataExportTableNames(HttpServletResponse response, String fieldId, String name);

}
