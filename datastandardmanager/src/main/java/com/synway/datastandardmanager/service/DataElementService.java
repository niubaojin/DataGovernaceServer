package com.synway.datastandardmanager.service;

import com.synway.datastandardmanager.entity.dto.EntityElementDTO;
import com.synway.datastandardmanager.entity.pojo.EntityElementEntity;
import com.synway.datastandardmanager.entity.vo.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DataElementService {

    List<EntityElementVO> getDataElementList(EntityElementDTO parameter);

    String addOneData(EntityElementEntity element);

    String upOneData(EntityElementEntity element);

    String deleteElementByCode(String elementCode);

    Boolean checkIsDelete(String elementCode);

    List<SelectFieldVO> searchSynlteField(String searchName);
    List<SelectFieldVO> searchSecondField(String searchName);
    List<SelectFieldVO> searchAnotherField(String codeId);

    List<TreeNodeValueVO> searchElementObject();

    List<SelectFieldVO> searchAllObject();

    List<String> searchElementChname(String searchName);

    List<ValueLabelVO> searchIsElement();

    String searchSameWord(String sameId);

    List<ValueLabelChildrenVO> searchElementTotal();

    void downloadAllElementTableExcel(HttpServletResponse response,
                                      List<EntityElementEntity> elementList,
                                      String name, Object object);

    List<EntityElementEntity> importElementTableExcel(MultipartFile file);

    void downloadErrorElementTableExcel(HttpServletResponse response,
                                        List<EntityElementEntity> list,
                                        String name,EntityElementEntity object);

    void downloadElmentTemplateTableExcel(HttpServletResponse response,
                                        List<EntityElementEntity> list,
                                        String name,EntityElementEntity object);

}
