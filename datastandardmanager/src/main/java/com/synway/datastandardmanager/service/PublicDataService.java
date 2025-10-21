package com.synway.datastandardmanager.service;

import com.synway.datastandardmanager.entity.pojo.StandardizePublicDataEntity;
import com.synway.datastandardmanager.entity.pojo.StandardizePublicDataFieldEntity;
import com.synway.datastandardmanager.entity.vo.KeyValueVO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PublicDataService {

    /**
     * 插入/更新一条公共数据项信息
     */
    String addOrUpdateOnePublicData(StandardizePublicDataEntity publicData);

    /**
     * 根据Id删除一条公共数据项分组信息
     */
    String deleteOnePublicDataGroup(String id, String groupName);

    /**
     * 数据项中文名模糊搜索
     */
    List<KeyValueVO> searchFieldChineseList(String searchText);

    /**
     * 根据公共数据组名称查询
     */
    StandardizePublicDataEntity searchPublicDataByGroupName(String groupName);

    /**
     * 查询公共数据项分组名称列表
     */
    List<KeyValueVO> searchGroupNameList();

    /**
     * 导出公共数据项
     *
     * @param response
     * @param fieldEntities 公共数据项列表
     * @param object
     */
    void downloadPublicDataFieldExcel(HttpServletResponse response,
                                      List<StandardizePublicDataFieldEntity> fieldEntities,
                                      Object object);

    /**
     * 导入公共数据项数据
     * @param file
     */
    List<StandardizePublicDataFieldEntity> importPublicDataFieldExcel(MultipartFile file, String id);

}
