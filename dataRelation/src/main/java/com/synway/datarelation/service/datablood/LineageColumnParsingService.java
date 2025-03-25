package com.synway.datarelation.service.datablood;

/**
 * sql解析的相关代码
 * @author wangdongwei
 */
public interface LineageColumnParsingService {

    /**
     *   用于解析 大数据平台的sql语句 生成表血缘信息和字段血缘信息
     * @param sql
     * @param dbType
     * @param projectName
     * @param nodeId
     */
    void parseLineageColumn(String sql,String dbType,String projectName,String nodeId);

}
