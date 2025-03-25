package com.synway.datastandardmanager.dao.master;

import com.synway.datastandardmanager.pojo.PageSelectOneValue;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author obito
 * @ClassName FieldCodeValDao
 * @version 1.0
 * @date
 */
@Mapper
@Repository
public interface FieldCodeValDao {
    /**
     * 查询厂商信息
     * @return
     */
    List<PageSelectOneValue> searchValtext();

    /**
     * 查询版本
     * @return
     */
    String searchVersion();
}
