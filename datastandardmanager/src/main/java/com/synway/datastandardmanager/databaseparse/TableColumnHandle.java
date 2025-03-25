package com.synway.datastandardmanager.databaseparse;

import com.synway.datastandardmanager.entity.BuildTableInfoVo;
import com.synway.datastandardmanager.pojo.SaveColumnComparision;

/**
 * 添加字段的相关解析方法
 * @author wangdongwei
 */
public interface TableColumnHandle {

    /**
     *  拼接出新增字段的sql信息 目前支持 hive odps ads
     * @param data
     * @return
     */
    public String getAddColumnSql(SaveColumnComparision data);

    /**
     *  通过相关数据来新增字段
     * @param data
     * @return
     */
    public String addColumnByData(SaveColumnComparision data) throws Exception;

    /**
     * 展示建表语句
     * @param buildTableInfoVo
     * @return
     */
    public String getCreateSql(BuildTableInfoVo buildTableInfoVo) throws Exception;

    /**
     * 通过页面来建表 目前只支持 hive hbase
     * odps/ads没有以下内容
     * @return
     */
    public default String createTableByPage(BuildTableInfoVo buildTableInfoVo) throws Exception{return null;}

    /**
     * hive/hbase  建表之前的检查   并返回 标准协议中的 tableId
     * @param buildTableInfoVo
     * @throws Exception
     */
    public default String createTableBeforeCheck(BuildTableInfoVo buildTableInfoVo) throws Exception{return null;}

    /**
     * 更新表名
     * @param oldTableName  需要更新的表名
     * @param newTableName  更新后的表名
     * @return   返回更新结果
     * @throws Exception  抛出异常
     */
    public boolean updateTableName(String oldTableName,String newTableName) throws Exception;
}
