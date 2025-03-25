package com.synway.datarelation.service.runnable;

import com.synway.datarelation.dao.DAOHelper;
import com.synway.datarelation.dao.datablood.FieldBloodlineDao;
import com.synway.datarelation.pojo.databloodline.FieldColumn;
import com.synway.datarelation.pojo.databloodline.QueryBloodlineRelationInfo;
import com.synway.datarelation.pojo.databloodline.QueryDataBloodlineTable;
import com.synway.common.exception.ExceptionUtil;
import com.synway.datarelation.pojo.dataresource.FieldInfo;
import com.synway.datarelation.util.RestTemplateHandle;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

public class HuaWeiColumnQueryRunnable implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(HuaWeiColumnQueryRunnable.class);

    private final QueryDataBloodlineTable queryDataBloodlineTable;
    private FieldBloodlineDao fieldBloodlineDao;
    private ConcurrentMap<String,QueryBloodlineRelationInfo> concurrentHashMap;
    // 表的数据库类型
    private String tableType;
    private RestTemplateHandle restTemplateHandle;

    public HuaWeiColumnQueryRunnable(QueryDataBloodlineTable queryDataBloodlineTable, FieldBloodlineDao fieldBloodlineDao,
                                     ConcurrentMap<String,QueryBloodlineRelationInfo> concurrentHashMap, String tableType,
                                     RestTemplateHandle restTemplateHandle){
        this.queryDataBloodlineTable = queryDataBloodlineTable;
        this.fieldBloodlineDao = fieldBloodlineDao;
        this.concurrentHashMap=concurrentHashMap;
        this.tableType = tableType;
        this.restTemplateHandle = restTemplateHandle;
    }

    @Override
    public void run() {
        String tabelNameEn = "";
        try{
            tabelNameEn = queryDataBloodlineTable.getTableNameEn();
            if(StringUtils.isEmpty(tabelNameEn)){
                throw new Exception("表名为空，不需要进行查询字段信息");
            }
            logger.info("开始查询参数为"+ tabelNameEn+"的表结构信息");
            String projectName = tabelNameEn.split("\\.")[0];
            String tableName = tabelNameEn.split("\\.")[1];
            if(StringUtils.isBlank(tableName) || StringUtils.isBlank(projectName)){
                logger.info(tabelNameEn+"存在空值，不需要查询字段信息");
                throw new Exception(tabelNameEn+"存在空值，不需要查询字段信息");
            }
            List<FieldInfo> tableFieldList = restTemplateHandle.getLocalTableStructinfo(tableType,tabelNameEn);
            logger.info("查询表"+tabelNameEn+"任务查询结束，开始将查询到的字段信息写入到oracle中");
            if(tableFieldList==null || tableFieldList.size() ==0){
                logger.error("未获取表"+tabelNameEn+"的表结构");
            }else{
                List<FieldColumn> fieldColumnList = new ArrayList<>();
                for(FieldInfo column:tableFieldList){
                    FieldColumn fieldColumn =new FieldColumn();
                    fieldColumn.setFieldName(column.getFieldName());
                    fieldColumn.setFieldType(column.getType());
                    fieldColumn.setTableNameEn(tabelNameEn.toUpperCase());
                    fieldColumn.setTableId(queryDataBloodlineTable.getTableId());
                    fieldColumn.setTargetCode(queryDataBloodlineTable.getTargetCode());
                    fieldColumn.setDataType(tableType);
                    fieldColumnList.add(fieldColumn);
                }
                // 如果数据量大于0，先清除指定 tableId 和表名下所有的字段信息 然后再
                if(fieldColumnList.size() >0){
                    int delNum = fieldBloodlineDao.deleteFieldColumnByNameOrId(tabelNameEn,queryDataBloodlineTable.getTableId(),tableType);
                    logger.info("表名"+tabelNameEn+"删除的数据量为"+delNum);
                    DAOHelper.insertList(fieldColumnList,fieldBloodlineDao,"insertFieldColumn");
                }
            }
        }catch (Exception e){
            logger.error("查询hive/hbase数据库类型报错"+ ExceptionUtil.getExceptionTrace(e));
        }
        // 结束之后将map中的指定key结束掉
        try{
            String mapKey = queryDataBloodlineTable.getTableNameEn()+"|"+queryDataBloodlineTable.getTableId()+"|"+
                    queryDataBloodlineTable.getTargetCode();
            concurrentHashMap.remove(mapKey.toUpperCase());
            logger.info(mapKey+"从全局map中删除");
        }catch (Exception e){
            logger.error("从全局map中清除掉任务报错"+ExceptionUtil.getExceptionTrace(e));
        }
    }
}
