package com.synway.datarelation.dao.heat;


import com.synway.datarelation.dao.BaseDAO;
import com.synway.datarelation.pojo.common.CountTableUse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 使用次数的相关方法
 * @author wangdongwei
 */
@Mapper
@Repository
public interface UseHeatDao extends BaseDAO {

    /**
     * 删除数据
     * @return
     */
    int delSqlCount();

    /**
     * 插入数据
     * @param countTableUseList
     * @return
     */
    int insertUseHeatList(@Param("rcList") List<CountTableUse> countTableUseList);
}
