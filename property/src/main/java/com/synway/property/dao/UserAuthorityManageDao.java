package com.synway.property.dao;

import com.synway.property.pojo.UserAuthority;
import com.synway.property.pojo.datastoragemonitor.NeedAddRealTimeTable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wdw
 * @version 1.0
 * @date 2021/6/11 22:58
 */
@Mapper
@Repository
public interface UserAuthorityManageDao {

    int updateInsertUserAuthority(@Param("userAuthority") UserAuthority userAuthority);

    int delUserAddRealTimeTable(@Param("list")List<NeedAddRealTimeTable> list,
                                @Param("userId") Integer userId,
                                @Param("moduleCode") String moduleCode);
}
