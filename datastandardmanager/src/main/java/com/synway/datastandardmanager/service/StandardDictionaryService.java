package com.synway.datastandardmanager.service;

import com.synway.datastandardmanager.entity.dto.StandardDictionaryDTO;
import com.synway.datastandardmanager.entity.pojo.FieldCodeEntity;
import com.synway.datastandardmanager.entity.pojo.FieldCodeValEntity;
import com.synway.datastandardmanager.entity.vo.KeyValueVO;
import com.synway.datastandardmanager.entity.vo.PageVO;
import com.synway.datastandardmanager.entity.vo.SelectFieldVO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StandardDictionaryService {

    /**
     * 获取代码集信息
     */
    PageVO selectCodeTable(int pageIndex, int pageSize, String codeName, String codeText, String codeId);

    /**
     * 获取代码集值信息
     */
    PageVO selectCodeValTable(int pageIndex, int pagesize, String valValue, String valText, String codeId);

    List<FieldCodeValEntity> selectCodeValTableNew(String codeId);

    /**
     * 删除选择的指定的代码集基本信息
     */
    String delCodeValTableServiceImpl(FieldCodeValEntity deleteFieldCodeVal);

    /**
     * 新增/修改 指定的代码集值信息
     */
    String addCodeValTableService(FieldCodeValEntity addFieldCodeVal);

    List<SelectFieldVO> getCodeValIdListService(String condition);

    String addOneCodeMessageService(FieldCodeEntity fieldCodeEntity);

    String delCodeTableServiceImpl(FieldCodeEntity fieldCodeEntity);

    String delAllSelectCode(List<FieldCodeEntity> fieldCodeEntities);

    List<FieldCodeValEntity> uploadCodeValXlsFile(MultipartFile codeValXlsFile);

    void downElementCodeTemplateFile(HttpServletResponse response);

    void downloadStandardDictionaryFieldExcel(HttpServletResponse response, StandardDictionaryDTO data, String name, Object object);

    String delBatchCodeValTable(List<FieldCodeValEntity> fieldCodeValEntities);

    String addOneCodeValMessage(StandardDictionaryDTO dictionaryDTO);

    /**
     * 模糊搜索数据字典下拉框
     * @param searchText
     */
    List<KeyValueVO> searchDictionaryList(String searchText);

}
