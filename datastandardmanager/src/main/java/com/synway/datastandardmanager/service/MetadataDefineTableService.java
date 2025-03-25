package com.synway.datastandardmanager.service;

import com.github.pagehelper.PageInfo;
import com.synway.datastandardmanager.pojo.Synltefield;
import com.synway.datastandardmanager.pojo.TableInfo;
import com.synway.datastandardmanager.pojo.relationTableInfo;


/**
 * @author wangdongwei
 */
@Deprecated
public interface MetadataDefineTableService {
    /**
     * 获取元数据定义
     * @param pageIndex
     * @param pageSize
     * @param fieldId
     * @param columName
     * @param fieldChineseName
     * @param sort
     * @param sortOrder
     * @return
     */
	public PageInfo<Synltefield> findByCondition(int pageIndex, int pageSize,
                                                 String fieldId, String columName,
                                                 String fieldChineseName,
                                                 String sort,String sortOrder);

    /**
     * 根据 fileid获取使用的表名信息
     * @param pageIndex
     * @param pageSize
     * @param fieldId
     * @return
     */
	public PageInfo<relationTableInfo> getAllTableNameByFieldId(int pageIndex, int pageSize, String fieldId);
}
