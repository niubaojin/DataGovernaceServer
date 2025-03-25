package com.synway.reconciliation.dao;

import com.synway.reconciliation.pojo.*;
import com.synway.reconciliation.pojo.issue.AdministrativeDivisionCode;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 缓存管理
 * @author Administrator
 */
@Mapper
@Repository
public interface CacheManageDao {

    /**
     * 获取源表信息
     * @return
     */
    List<SourceTableCache> getSourceTable();

    /**
     * 获取DS_DETECTED_TABLE所有信息
     * @return
     */
    List<DsDetectedTable> getAllDetectedTableInfo();

    /**
     * 获取所有下发的行政区划编码
     * @return
     */
    List<AdministrativeDivisionCode> getAllCodes();
}
