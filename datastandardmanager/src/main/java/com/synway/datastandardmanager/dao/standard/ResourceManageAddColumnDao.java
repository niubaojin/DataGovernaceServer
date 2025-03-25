package com.synway.datastandardmanager.dao.standard;

import com.synway.datastandardmanager.pojo.ObjectField;
import com.synway.datastandardmanager.pojo.ObjectFieldStandard;
import com.synway.datastandardmanager.pojo.ObjectPojoTable;
import com.synway.datastandardmanager.pojo.PageSelectOneValue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ResourceManageAddColumnDao {

	/**
     * 获取指定表id下最大的聚集列id值
	 * @param objectId
	 * @return
     */
	String getMaxClustRecno(@Param("objectId") Long objectId);

	String getMaxPkRecno(@Param("objectId") Long objectId);

	String getMaxMd5Index(@Param("objectId") Long objectId);

    String getMaxClustRecnoByColumn(@Param("objectId") Long objectId,
                                    @Param("columnName") String columnName);

    String getMaxPkRecnoByColumn(@Param("objectId") Long objectId,
                                 @Param("columnName") String columnName);

    String getMaxMd5IndexByColumn(@Param("objectId") Long objectId,
                                  @Param("columnName") String columnName);


	String getMaxRecno(@Param("objectId") Long objectId);

	int addObjectField(ObjectFieldStandard objectField);

    /**
     * 判断这个需要插入的字段在表中是否已经存在
     * @param objectId    表对象ID
     * @param columnName
     * @return
     */
    int getcolumnCountByFieldId(@Param("objectId") Long objectId,
                                @Param("columnName") String columnName);

    /**
     * 根据fieldId和columnName查询该字段在表中是否存在
     * @param fieldId
     * @param columnName
     * @param objectId
     */
    int searchObjectFieldByFieldAndColumnName(@Param("fieldId") String fieldId,
                                              @Param("columnName")String columnName,
                                              @Param("objectId")  Long objectId);

    /**
     * 根据gadsj_fieldId查询查询是否重复
     * @param gadsj_fieldId
     * @param objectId
     */
    int searchFieldCount(@Param("gadsj_fieldId") String gadsj_fieldId,@Param("objectId")Long objectId);

    /**
     * 查询fieldId是否唯一
     * @param fieldId
     */
    int searchFieldIdCount(@Param("fieldId") String fieldId);

    /**
     * 将 objectField表中指定字段改成被删除状态
     * @param objectId   表id
     * @param columnName
     * @return
     */
    int deleteObjectField(@Param("objectId")Long objectId,
                          @Param("columnName")String columnName,
                          @Param("randomStr") String randomStr);

    int updateObjectField(ObjectFieldStandard objectField);

    int updateObjectFieldClustRecno(ObjectFieldStandard objectField);

    int updateObjectFieldPkRecno(ObjectFieldStandard objectField);

    int updateObjectFieldMd5Index(ObjectFieldStandard objectField);


    ObjectFieldStandard getCommonColumnByListDao(@Param("fieldId")String fieldId);

    /**
     *  编辑标准表字段将 更新前的数据存储到历史表中
     * @param objectField
     * @return
     */
    int saveOldObjectField(ObjectField objectField);

}
