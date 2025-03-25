package com.synway.datastandardmanager.databaseparse.addcolumn;

import com.alibaba.fastjson.JSONObject;
import com.synway.datastandardmanager.SpringBeanUtil;
import com.synway.datastandardmanager.dao.standard.ObjectDao;
import com.synway.datastandardmanager.dao.standard.TableOrganizationDao;
import com.synway.datastandardmanager.databaseparse.TableColumnHandle;
import com.synway.datastandardmanager.entity.BuildTableInfoVo;
import com.synway.datastandardmanager.enums.EsType;
import com.synway.datastandardmanager.enums.RegionType;
import com.synway.datastandardmanager.exceptionhandler.ErrorCode;
import com.synway.datastandardmanager.exceptionhandler.SystemException;
import com.synway.datastandardmanager.pojo.ObjectField;
import com.synway.datastandardmanager.pojo.SaveColumnComparision;
import com.synway.datastandardmanager.pojo.buildtable.HuaWeiCreateTable;
import com.synway.datastandardmanager.pojo.dataresource.CreateTableData;
import com.synway.datastandardmanager.util.ExceptionUtil;
import com.synway.datastandardmanager.util.RestTemplateHandle;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangdongwei
 * @ClassName HbaseTableColumnHandle
 * @description TODO
 * @date 2020/9/26 13:37
 */
public class HbaseTableColumnHandle implements TableColumnHandle {
    private static final Logger logger = LoggerFactory.getLogger(HbaseTableColumnHandle.class);
    @Override
    public String getAddColumnSql(SaveColumnComparision data) {
        logger.info("开始拼接hbase新增字段的相关操作");
        return null;
    }

    @Override
    public String addColumnByData(SaveColumnComparision data) throws Exception {
        return null;
    }

    @Override
    public String getCreateSql(BuildTableInfoVo buildTableInfoVo) throws Exception{
        List columnList = Lists.newArrayList();
        final String[] esType = {"day"};
        final String[] regiontype = {"day"};
        Arrays.stream(EsType.values()).forEach(ele -> {
            if (buildTableInfoVo.getEsSplitType() == ele.getType()) {
                esType[0] = ele.getName();
            }
        });
        Arrays.stream(RegionType.values()).forEach(ele -> {
            if (buildTableInfoVo.getRegionType() == ele.getCode()) {
                regiontype[0] = ele.getName();
            }
        });
        String properties = String.format("(estype='%s',escount=%d,esshard=%d,regiontype='%s',regioncount=%d,lifecycle=%d,geohashredundant=%b)",
                esType[0], buildTableInfoVo.getEsSplitCount(), buildTableInfoVo.getEsShards(), regiontype[0], buildTableInfoVo.getRegionCount(),
                buildTableInfoVo.getLifeCycle(), buildTableInfoVo.getGeoRedundant());
        List<ObjectField> columns = new ArrayList<>(buildTableInfoVo.getColumnData());
        columns.stream().forEach(col -> {
            if (col.getRecno() < 0) {
                throw new RuntimeException("column recno less 1");
            }
            if (!col.getColumnName().matches("[a-zA-Z]+.*")) {
                throw new RuntimeException("columnName must be start with letters");
            }
            String[] columnType = {"varchar"};
            columnType[0] = col.getCreateColumnType();
            String column;
            String columnCh = col.getFieldChineseName();
            if(StringUtils.isEmpty(columnCh)){
                columnCh = " ";
            }
            if (columnType[0].equals("varchar")) {
                column = String.format("%s %s(%d) COMMENT '%s' with (rowkey=%b,index=%b,source=%b,store=%b,docvalue=%b,filter=%b,md5=%d)", col.getColumnName()
                        , columnType[0], col.getFieldLen(),columnCh, col.getIsRowkey() == null ? false : col.getIsRowkey() == 0 ? false : true, col.getIsIndexs(), col.getIsSource() == null ? false : col.getIsSource() == 0 ? false : true, col.getIsStore() == null ? false : col.getIsStore() == 0 ? false : true,
                        col.getIsDocval() == null ? false : col.getIsDocval() == 0 ? false : true, col.getIsFilter() == null ? false : col.getIsFilter() == 0 ? false : true,  col.getMd5Index() == null? 0 : col.getMd5Index());
            } else {
                column = String.format("%s %s COMMENT '%s' with (rowkey=%b,index=%b,source=%b,store=%b,docvalue=%b,filter=%b,md5=%d)", col.getColumnName()
                        , columnType[0],columnCh , col.getIsRowkey() == null ? false : col.getIsRowkey() == 0 ? false : true, col.getIsIndexs(), col.getIsSource() == null ? false : col.getIsSource() == 0 ? false : true, col.getIsStore() == null ? false : col.getIsStore() == 0 ? false : true,
                        col.getIsDocval() == null ? false : col.getIsDocval() == 0 ? false : true, col.getIsFilter() == null ? false : col.getIsFilter() == 0 ? false : true, col.getMd5Index() == null? 0 : col.getMd5Index());
            }
            columnList.add(column);
        });
        String sql = String.format("create table %s.%s (%s) comment '%s' with %s", buildTableInfoVo.getSchema(),
                buildTableInfoVo.getTableName().toUpperCase(), StringUtils.strip(columnList.toString(), "[]"),
                buildTableInfoVo.getPrestoMemo(), properties);
        return sql;
    }


    /**
     *  创建hbase平台的相关数据
     * @param buildTableInfoVo
     * @return
     * @throws Exception
     */
    @Override
    public String createTableByPage(BuildTableInfoVo buildTableInfoVo) throws Exception{
        logger.info("HBASE建表的相关信息为："+JSONObject.toJSONString(buildTableInfoVo));
        // 检查 建表的相关参数
        this.createTableBeforeCheck(buildTableInfoVo);
        String dataId = buildTableInfoVo.getDataId();
        // 生成建表语句
        String createSql = this.getCreateSql(buildTableInfoVo);
        logger.info("HBASE生成的建表语句为： "+createSql);
        CreateTableData huaWeiCreateTable = new CreateTableData();
        huaWeiCreateTable.setData(createSql);
        if(buildTableInfoVo.getDsType().equalsIgnoreCase(HuaWeiCreateTable.HBASE_CDH)){
            huaWeiCreateTable.setType(HuaWeiCreateTable.HBASE_CDH);
        }else{
            huaWeiCreateTable.setType(HuaWeiCreateTable.HBASE_HUAWEI);
        }
        huaWeiCreateTable.setResId(dataId);
        huaWeiCreateTable.setTableId(buildTableInfoVo.getTableId());
        huaWeiCreateTable.setTableName(buildTableInfoVo.getSchema()+"."+buildTableInfoVo.getTableName());
        RestTemplateHandle restTemplateHandle = SpringBeanUtil.getBean(RestTemplateHandle.class);
        String message;
        try{
            message= restTemplateHandle.sendCreateTableInfo(huaWeiCreateTable);
            logger.info("----------------开始将数据写入到已建表中---------------");
            buildTableInfoVo.setLifeCycle(buildTableInfoVo.getLifeCycle());
            TableOrganizationDao tableOrganizationDao = SpringBeanUtil.getBean(TableOrganizationDao.class);
            try{
                tableOrganizationDao.insertInfo(buildTableInfoVo);
                tableOrganizationDao.insertInfoTemp(buildTableInfoVo);
            }catch (Exception e){
                logger.error("向资产那边插入数据"+ ExceptionUtil.getExceptionTrace(e));
            }
        }catch (Exception e){
            logger.error(ExceptionUtil.getExceptionTrace(e));
            throw SystemException.asSystemException(ErrorCode.CREATE_TABLE_ERROR,e.getMessage());
        }
        return message;
    }


    /**
     *  建表时先检查相关参数是否正确
     * @param buildTableInfoVo
     * @throws Exception
     */
    @Override
    public  String createTableBeforeCheck(BuildTableInfoVo buildTableInfoVo) throws Exception{
        //  在hbase建表中，如果不是 固定分区/总共一个索引，则字段中必须要有  CAPTURE_TIME 这个字段，否则会建表失败
        // 如果 regionType 的值不是 1 / -1  则表示必须要有 CAPTURE_TIME 这个字段
        List<String> columnNameList = buildTableInfoVo.getColumnData().stream().map(e->e.getColumnName().toUpperCase())
                .distinct().collect(Collectors.toList());
        if(buildTableInfoVo.getRegionType() == 0 || buildTableInfoVo.getRegionType() == 2 || buildTableInfoVo.getRegionType() == 3){
            if(!columnNameList.contains("CAPTURE_TIME")){
                throw new NullPointerException(" 当选择该【HBASE分区类型】时，需要有【CAPTURE_TIME】字段");
            }
        }
        if(buildTableInfoVo.getEsSplitType() == 0 || buildTableInfoVo.getEsSplitType() == 3){
            if(!columnNameList.contains("CAPTURE_TIME")){
                throw new NullPointerException(" 当选择该【ES索引类型】时，需要有【CAPTURE_TIME】字段");
            }
        }
        // 如果存在CAPTURE_TIME 这个字段 则必须是 bigint类型
        for(ObjectField objectField:buildTableInfoVo.getColumnData()){
            if("CAPTURE_TIME".equalsIgnoreCase(objectField.getColumnName()) && !"bigint".equalsIgnoreCase(objectField.getCreateColumnType())){
                throw new NullPointerException("【CAPTURE_TIME】字段的建表类型必须是bigint");
            }
        }
        ObjectDao objectDao = SpringBeanUtil.getBean(ObjectDao.class);
        String tableId = buildTableInfoVo.getTableId();
        int temp = objectDao.countObjectByTableId(tableId,buildTableInfoVo.getTableName());
        if(temp==0) {
            throw new NullPointerException(" 标准中无表名或协议编号信息!!!");
        }
        String dataId = buildTableInfoVo.getDataId();
        if(StringUtils.isEmpty(dataId)){
            throw new NullPointerException(" 数据源ID为空，请选择具体的数据源信息");
        }
//        String createSql = this.getCreateSql(buildTableInfoVo);
//        if(StringUtils.isBlank(createSql)){
//            throw new NullPointerException(" 生成的sql为空，请检查具体的日志信息");
//        }
        return tableId;
    }

    /**
     *
     * @param oldTableName  需要更新的表名
     * @param newTableName  更新后的表名
     * @return
     * @throws Exception
     */
    @Override
    public boolean updateTableName(String oldTableName, String newTableName) throws Exception {
        return false;
    }
}
