package com.synway.datastandardmanager.service;

import com.synway.datastandardmanager.pojo.PageSelectOneValue;
import com.synway.datastandardmanager.pojo.publicDataManage.PublicDataField;
import com.synway.datastandardmanager.pojo.publicDataManage.PublicDataFieldExcel;
import com.synway.datastandardmanager.pojo.publicDataManage.PublicDataPojo;
import com.synway.datastandardmanager.pojo.synlteelement.SynlteElement;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 公共数据项Service接口
 * @author obito
 * @version 1.0
 * @date
 */
public interface PublicDataManageService {

    /**
     * 插入一条公共数据项信息
     * @param publicDataPojo
     * @return
     */
    String addPublicData(PublicDataPojo publicDataPojo) throws Exception;

    /**
     * 根据Id删除一条公共数据项分组信息
     * @param id
     * @param groupName
     * @return
     */
    String deleteOnePublicDataGroup(String id,String groupName);

    /**
     * 更新一条公共数据项分组信息
     * @param publicDataPojo
     * @return
     */
    String updateOnePublicDataGroup(PublicDataPojo publicDataPojo);

    /**
     * 数据项中文名模糊搜索
     * @param searchText
     * @return
     */
    List<PageSelectOneValue> searchFieldChineseList(String searchText);

    /**
     * 根据公共数据组名称查询
     * @param groupName
     * @return
     */
    PublicDataPojo searchPublicDataByGroupName(String groupName);

    /**
     * 查询公共数据项分组名称列表
     * @return
     */
    List<PageSelectOneValue> searchGroupNameList();

    /**
     * 导出公共数据项
     * @param response
     * @param elementList 公共数据项列表
     * @param name excel文档名
     * @param object
     * @return
     */
    void downloadPublicDataFieldExcel(HttpServletResponse response,
                                      List<PublicDataField> elementList,
                                      String name, Object object);

    /**
     * 导入公共数据项数据
     * @param file
     * @return
     */
    List<PublicDataField> importPublicDataFieldExcel( MultipartFile file,String id);
}
