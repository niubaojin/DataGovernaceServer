package com.synway.governace.dao;

import com.synway.governace.pojo.largeScreen.PropertyLargeDetailed;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wdw
 * @version 1.0
 * @date 2021/6/15 17:49
 */
@Mapper
@Repository
public interface DetailedLargeScreenDao {

    /**
     * 获取原始库资产情况指定二级分类的表名
     * @param name
     * @return
     */
    List<PropertyLargeDetailed>  getDetailedOriginalData(@Param("name") String name,
                                                         @Param("dateStr") String dateStr);


    /**
     * 获取主题库/资源库 指定二级分类的详细表信息
     * @param searchName
     * @param type
     * @return
     */
    List<PropertyLargeDetailed> getDetailedResourceThemeData(@Param("searchName")String searchName,
                                 @Param("type")String type);
}
