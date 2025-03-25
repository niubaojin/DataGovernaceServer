package com.synway.datarelation.service.heat.impl;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.hive.visitor.HiveSchemaStatVisitor;
import com.alibaba.druid.sql.dialect.odps.visitor.OdpsSchemaStatVisitor;
import com.alibaba.druid.sql.visitor.SchemaStatVisitor;
import com.alibaba.druid.stat.TableStat;
import com.alibaba.druid.util.JdbcConstants;
import com.aliyun.odps.utils.StringUtils;
import com.synway.datarelation.constant.Common;
import com.synway.datarelation.dao.DAOHelper;
import com.synway.datarelation.dao.heat.UseHeatDao;
import com.synway.datarelation.pojo.common.CountTableUse;
import com.synway.datarelation.service.heat.UseHeatService;
import com.synway.datarelation.util.DateUtil;
import com.synway.common.exception.ExceptionUtil;
import com.synway.datarelation.util.SqlParseMatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 直接从sql中解析所有的表名，将sql中解析到表名插入到数据库中
 * @author wangdongwei
 * @date 2021/4/24 10:09
 */
@Service
public class UseHeatServiceImpl implements UseHeatService {
    private Logger logger = LoggerFactory.getLogger(UseHeatServiceImpl.class);
    @Autowired
    private UseHeatDao useHeatDao;

    /**
     * 解析出sql中的所有存在的表，然后将数据插入到使用次数表中
     * @param sqlValue
     * @param dbType   hive:类型需要去除掉部分无法解析的sql语句
     * @param nodeProjectName  项目名，如果解析到的里面没有 使用这个
     * @param countTableUseMap 存储获取到的信息  key值是 表名(带项目名)+ @@ +  1/2   1:表示 select 2:表示 insert
     */
    @Override
    public void getUseHeatTable(String sqlValue,
                                String dbType,
                                String nodeProjectName,
                                Map<String, Integer> countTableUseMap){
        if(StringUtils.isBlank(sqlValue)){
            return;
        }
        // 先
        try{
            sqlValue = SqlParseMatch.sqlParse(sqlValue,dbType);
        }catch (Exception e){
            logger.error("替换不能解析的内容报错"+e.getMessage());
        }
        List<SQLStatement> statementList = null;
        try{
            String result = SQLUtils.format(sqlValue,dbType);
            statementList = SQLUtils.parseStatements(result,dbType);
        }catch (Exception e){
            logger.error("将节点中的sql分隔成单独的语句报错："+e.getMessage());
            statementList = new ArrayList<>(1);
        }
        for (SQLStatement sqlStatement : statementList) {
            try {
                SchemaStatVisitor visitor = null;
                if (JdbcConstants.ODPS.equalsIgnoreCase(dbType)) {
                    visitor = new OdpsSchemaStatVisitor();
                } else if (JdbcConstants.HIVE.equalsIgnoreCase(dbType)) {
                    visitor = new HiveSchemaStatVisitor();
                } else {
                    visitor = new OdpsSchemaStatVisitor();
                }
                sqlStatement.accept(visitor);
                Map<TableStat.Name, TableStat> map = visitor.getTables();
                for (Map.Entry<TableStat.Name, TableStat> entry : map.entrySet()) {
                    // 这个是能获取 各种类型的使用次数 select insert count
                    TableStat tableStat = entry.getValue();
                    // 这个是获取表名 可能包括项目名，需要
                    String tableName = entry.getKey().getName();
                    if (!tableName.contains(".")) {
                        tableName = nodeProjectName + "." + tableName;
                    }
                    int selectCount = tableStat.getSelectCount();
                    if (selectCount != 0) {
                        String tableKey = tableName + Common.KEY_CONN + 1;
                        countTableUseMap.merge(tableKey, selectCount, Integer::sum);
                    }
                    int insertCount = tableStat.getInsertCount() + tableStat.getMergeCount() + tableStat.getUpdateCount();
                    if (insertCount != 0) {
                        String tableKey = tableName + Common.KEY_CONN + 2;
                        countTableUseMap.merge(tableKey, insertCount, Integer::sum);
                    }
                }
            } catch (Exception e) {
                logger.error("获取解析的表信息报错：" + ExceptionUtil.getExceptionTrace(e));
            }
        }
    }


    /**
     *  将获取到的数据 插入到数据库中
     * @param countTableUseMap  存储获取到的信息  key值是 表名(带项目名)+ @@ +  1/2   1:表示 select 2:表示 insert
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void inertUseTableToOracle(Map<String,Integer> countTableUseMap) throws Exception{
        List<CountTableUse> countTableUses = new ArrayList<>();
        String dt = DateUtil.formatDate(DateUtil.addDay(new Date(),-1),DateUtil.DEFAULT_PATTERN_DATE);
        for(Map.Entry<String,Integer> entry: countTableUseMap.entrySet()){
            String tableName = entry.getKey().split(Common.KEY_CONN)[0];
            String type = entry.getKey().split(Common.KEY_CONN)[1];
            String projectName = "";
            if(tableName.contains(".")){
                projectName = tableName.split("\\.")[0];
                tableName = tableName.split("\\.")[1];
            }
            CountTableUse countTableUse = new CountTableUse(null,tableName,projectName,dt,Integer.parseInt(type),entry.getValue());
            countTableUses.add(countTableUse);
        }
        if(!countTableUses.isEmpty()){
            int delNum = useHeatDao.delSqlCount();
            logger.info("删除表ut_odpssqlcount的数据量为"+delNum);
            DAOHelper.insertList(countTableUses,useHeatDao,"insertUseHeatList");
        }else{
            logger.info("使用次数的统计结果为空，不能进行统计");
        }
    }

}
