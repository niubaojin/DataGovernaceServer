package com.synway.datarelation.service.runnable;


import com.synway.datarelation.util.SpringBeanUtil;
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

/**
 * @author wangdongwei
 */
public class OdpsColumnQueryRunnable implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(OdpsColumnQueryRunnable.class);

    private final QueryDataBloodlineTable queryDataBloodlineTable;
    private FieldBloodlineDao fieldBloodlineDao;
    private ConcurrentMap<String,QueryBloodlineRelationInfo> concurrentHashMap;
    public OdpsColumnQueryRunnable(QueryDataBloodlineTable queryDataBloodlineTable,
                                   FieldBloodlineDao fieldBloodlineDao, ConcurrentMap<String,QueryBloodlineRelationInfo> concurrentHashMap){
        this.queryDataBloodlineTable = queryDataBloodlineTable;
        this.fieldBloodlineDao = fieldBloodlineDao;
        this.concurrentHashMap=concurrentHashMap;
    }

    @Override
    public void run() {
        String tabelNameEn = "";
        try{
            tabelNameEn = queryDataBloodlineTable.getTableNameEn();
            if(StringUtils.isEmpty(tabelNameEn)){
                throw new NullPointerException("表名为空，不需要进行查询字段信息");
            }
            logger.info("开始查询参数为"+ tabelNameEn+"的表结构信息");
            String projectName = tabelNameEn.split("\\.")[0];
            String tableName = tabelNameEn.split("\\.")[1];
            if(StringUtils.isBlank(tableName) || StringUtils.isBlank(projectName)){
                logger.info(tabelNameEn+"存在空值，不需要查询字段信息");
                throw new NullPointerException(tabelNameEn+"存在空值，不需要查询字段信息");
            }
            // 20201202 从数据仓库本地仓获取相关数据，不再直接连接odps
            RestTemplateHandle restTemplateHandle = SpringBeanUtil.getBean(RestTemplateHandle.class);
            List<FieldInfo> tableFieldList = restTemplateHandle.getLocalTableStructinfo("odps",tabelNameEn);

            logger.info("查询表"+tabelNameEn+"任务查询结束，开始将查询到的字段信息写入到oracle中");
            if(tableFieldList==null){
                logger.error("未获取Odps中["+projectName+"]项目中的["+tableName+"]表结构");
            }else{
                List<FieldColumn> fieldColumnList = new ArrayList<>();
                for(FieldInfo column:tableFieldList){
                    String type=column.getType();
                    FieldColumn odpsFiled=new FieldColumn();
                    String columnName=column.getFieldName();
                    odpsFiled.setFieldName(columnName);
                    odpsFiled.setFieldType(type);
                    odpsFiled.setTableNameEn(tabelNameEn.toUpperCase());
                    odpsFiled.setTableId(queryDataBloodlineTable.getTableId());
                    odpsFiled.setTargetCode(queryDataBloodlineTable.getTargetCode());
                    odpsFiled.setDataType(FieldColumn.ODPS);
                    fieldColumnList.add(odpsFiled);
                }
                // 如果数据量大于0，先清除指定 tableId 和表名下所有的字段信息 然后再
                if(!fieldColumnList.isEmpty()){
                    int delNum = fieldBloodlineDao.deleteFieldColumnByNameOrId(tabelNameEn,queryDataBloodlineTable.getTableId(),FieldColumn.ODPS);
                    logger.info("表名"+tabelNameEn+"删除的数据量为"+delNum);
                    DAOHelper.insertList(fieldColumnList,fieldBloodlineDao,"insertFieldColumn");
                }
            }
            logger.info("将"+tabelNameEn+"从odps查询到的字段信息插入到数据库结束");
        }catch(Exception e){
            logger.error("查询表"+tabelNameEn+"在ODPS字段信息报错"+ExceptionUtil.getExceptionTrace(e));
        }
        // 结束之后将map中的指定key结束掉
        try{
            String mapKey = queryDataBloodlineTable.getTableNameEn()+"|"+queryDataBloodlineTable.getTableId()+"|"+
                    queryDataBloodlineTable.getTargetCode();
            concurrentHashMap.remove(mapKey.toUpperCase());
            logger.info(mapKey+"从map中删除");
        }catch (Exception e){
            logger.error("从全局map中清除掉任务报错"+ExceptionUtil.getExceptionTrace(e));
        }
    }
}
