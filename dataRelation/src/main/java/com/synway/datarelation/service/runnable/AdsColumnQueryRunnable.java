package com.synway.datarelation.service.runnable;


import com.synway.datarelation.util.SpringBeanUtil;
import com.synway.datarelation.dao.DAOHelper;

import com.synway.datarelation.dao.datablood.FieldBloodlineDao;
import com.synway.datarelation.pojo.databloodline.FieldColumn;
import com.synway.datarelation.pojo.databloodline.QueryBloodlineRelationInfo;
import com.synway.datarelation.pojo.databloodline.QueryDataBloodlineTable;
import com.synway.datarelation.pojo.dataresource.FieldInfo;
import com.synway.datarelation.util.AdsClient;
import com.synway.common.exception.ExceptionUtil;
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
public class AdsColumnQueryRunnable implements Runnable{
    private static final  Logger logger = LoggerFactory.getLogger(AdsColumnQueryRunnable.class);
    private final QueryDataBloodlineTable queryDataBloodlineTable;
    private FieldBloodlineDao fieldBloodlineDao;
    private ConcurrentMap<String,QueryBloodlineRelationInfo> concurrentHashMap;
    public AdsColumnQueryRunnable(QueryDataBloodlineTable queryDataBloodlineTable, AdsClient adsClient,
                                  FieldBloodlineDao fieldBloodlineDao, ConcurrentMap<String,QueryBloodlineRelationInfo> concurrentHashMap){
        this.queryDataBloodlineTable = queryDataBloodlineTable;
        this.fieldBloodlineDao = fieldBloodlineDao;
        this.concurrentHashMap=concurrentHashMap;
    }

    @Override
    public void run(){
        String tabelNameEn = "";
        try{
            tabelNameEn = queryDataBloodlineTable.getTableNameEn().toLowerCase();
            if(StringUtils.isEmpty(tabelNameEn)){
                throw new NullPointerException("表名为空，不需要进行查询字段信息");
            }
            logger.info("开始在ads数据库中查询参数为"+ tabelNameEn+"的表结构信息");
            String projectName = tabelNameEn.split("\\.")[0];
            String tableName = tabelNameEn.split("\\.")[1];
            if(StringUtils.isBlank(tableName) || StringUtils.isBlank(projectName)){
                logger.info(tabelNameEn+"存在空值，不需要查询字段信息");
                throw new NullPointerException(tabelNameEn+"存在空值，不需要查询字段信息");
            }
            //20201202 不再直接连接这两个信息
            RestTemplateHandle restTemplateHandle = SpringBeanUtil.getBean(RestTemplateHandle.class);
            List<FieldInfo> tableFieldList = restTemplateHandle.getLocalTableStructinfo("ads",tabelNameEn);

            if(tableFieldList == null || tableFieldList.isEmpty()){
                logger.error("未获取Odps中["+projectName+"]项目中的["+tableName+"]表结构");
            }else{
                List<FieldColumn> fieldColumnList = new ArrayList<>();
                for(FieldInfo columnName:tableFieldList){
                    FieldColumn adsFiled=new FieldColumn();
                    adsFiled.setFieldName(columnName.getFieldName());
                    adsFiled.setFieldType(columnName.getType());
                    adsFiled.setTableNameEn(tabelNameEn.toUpperCase());
                    adsFiled.setTableId(queryDataBloodlineTable.getTableId());
                    adsFiled.setTargetCode(queryDataBloodlineTable.getTargetCode());
                    adsFiled.setDataType(FieldColumn.ADS);
                    fieldColumnList.add(adsFiled);
                }
                // 如果数据量大于0，先清除指定 tableId 和表名下所有的字段信息 然后再
                if(!fieldColumnList.isEmpty()){
                    int delNum = fieldBloodlineDao.deleteFieldColumnByNameOrId(tabelNameEn,queryDataBloodlineTable.getTableId(),FieldColumn.ADS);
                    logger.info("表名"+tabelNameEn+"删除的数据量为"+delNum);
                    DAOHelper.insertList(fieldColumnList,fieldBloodlineDao,"insertFieldColumn");
                }
            }

        }catch (Exception e){
            logger.error("定时任务报错"+e.getMessage());
        }
        // 结束之后将map中的指定key结束掉
        try{
            String mapKey = queryDataBloodlineTable.getTableNameEn()+"|"+queryDataBloodlineTable.getTableId()+"|"+
                    queryDataBloodlineTable.getTargetCode();
            concurrentHashMap.remove(mapKey.toUpperCase());
            logger.info(mapKey+"从map中删除");
        }catch (Exception e){
            logger.error("从全局map中清除掉任务报错"+ ExceptionUtil.getExceptionTrace(e));
        }
    }
}
