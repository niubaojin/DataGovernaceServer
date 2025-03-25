package com.synway.datastandardmanager.dao.master;

import java.util.List;

import com.synway.datastandardmanager.pojo.Synltefield;
import com.synway.datastandardmanager.pojo.TableInfo;
import com.synway.datastandardmanager.pojo.relationTableInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author wangdongwei
 */
@Mapper
@Repository
@Deprecated
public interface MetadataDefineTableDao {

    /**
     * 获取
     * @param fieldId
     * @param columName
     * @param fieldChineseName
     * @param sort
     * @param sortOrder
     * @return
     */
	public List<Synltefield> findByCondition(@Param("fieldId") String fieldId,
                                             @Param("columName") String columName,
                                             @Param("fieldChineseName") String fieldChineseName,
                                             @Param("sort") String sort,
                                             @Param("sortOrder") String sortOrder);

    /**
     * 根据filedid获取所有的表名
     * @param fieldId
     * @return
     */
	List<relationTableInfo> getAllTableNameByFieldId(@Param("fieldId") String fieldId);
}
