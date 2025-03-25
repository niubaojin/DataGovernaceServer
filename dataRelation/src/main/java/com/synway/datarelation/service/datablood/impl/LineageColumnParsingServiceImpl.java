package com.synway.datarelation.service.datablood.impl;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLObject;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.*;
import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.dialect.hive.ast.HiveInsert;
import com.alibaba.druid.sql.dialect.hive.stmt.HiveCreateTableStatement;
import com.alibaba.druid.sql.dialect.hive.visitor.HiveSchemaStatVisitor;
import com.alibaba.druid.sql.dialect.odps.ast.OdpsCreateTableStatement;
import com.alibaba.druid.sql.dialect.odps.ast.OdpsInsertStatement;
import com.alibaba.druid.sql.dialect.odps.visitor.OdpsSchemaStatVisitor;
import com.alibaba.druid.sql.visitor.SchemaStatVisitor;
import com.alibaba.fastjson.JSONObject;
import com.synway.datarelation.dao.DAOHelper;
import com.synway.datarelation.dao.datablood.LineageColumnParsingDao;
import com.synway.datarelation.pojo.dataresource.FieldInfo;
import com.synway.datarelation.pojo.lineage.*;
import com.synway.datarelation.service.datablood.LineageColumnParsingService;
import com.synway.common.exception.ExceptionUtil;
import com.synway.datarelation.util.RestTemplateHandle;
import com.synway.datarelation.util.SqlParseMatch;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 *  字段血缘的相关解析程序
 */
@Service
public class LineageColumnParsingServiceImpl implements LineageColumnParsingService {
    private Logger logger = LoggerFactory.getLogger(LineageColumnParsingServiceImpl.class);

    @Autowired
    private RestTemplateHandle restTemplateHandle;

    @Autowired
    private LineageColumnParsingDao lineageColumnParsingDao;

    @Override
    public void parseLineageColumn(String sql, String dbType, String projectName, String nodeId){
        // 开始解析sql数据
        try{
            if(StringUtils.isEmpty(sql)){
                throw new Exception("sql信息为空，不能解析字段血缘信息");
            }
//            logger.info("nodeId为："+nodeId);
            List<ColumnLineageTree> columnLineageTreeList = parseColumn(sql,dbType,projectName,nodeId);
            // 解析出来之后如何将数据写入到数据库中
            List<ColumnLineDb> columnLineDbs = new ArrayList<>();
            for(ColumnLineageTree columnLineageTree:columnLineageTreeList){
                // 如何迭代类型了？？？？
                List<ColumnLineageNode> list = columnLineageTree.getRoot().getNodes();
                list.forEach(columnLineageNode -> {
                    List<ColumnLineDb> inputColumnList = new ArrayList<>();
                    parseColumnDb(inputColumnList,columnLineageNode.getNodes(),columnLineageNode.getData().getColumnName(),
                            StringUtils.isNotEmpty(columnLineageNode.getData().getCaseStr())?columnLineageNode.getData().getCaseStr():"");
                    inputColumnList.forEach(inputColumn ->{
                        ColumnLineDb columnLineDb = new ColumnLineDb();
                        columnLineDb.setOutputTableName(columnLineageNode.getData().getTableName());
                        columnLineDb.setDbType(dbType);
                        columnLineDb.setOutputColumnName(columnLineageNode.getData().getColumnName());
                        columnLineDb.setProcessJson(JSONObject.toJSONString(columnLineageNode.getNodes())
                                .replaceAll("\\n","").replaceAll("\\t",""));
                        columnLineDb.setNodeId(nodeId);
                        columnLineDb.setInputTableName(StringUtils.isNotBlank(inputColumn.getInputTableName())?
                                        (inputColumn.getInputTableName().contains(".")?
                                        inputColumn.getInputTableName():projectName+"."+inputColumn.getInputTableName())
                                        :inputColumn.getInputTableName());
                        columnLineDb.setInputColumnName(StringUtils.isNotEmpty(inputColumn.getInputColumnName())?
                                inputColumn.getInputColumnName().replaceAll("\\n","").replaceAll("\\t","")
                                :inputColumn.getInputColumnName());
                        columnLineDb.setCaseStr(StringUtils.isNotEmpty(inputColumn.getCaseStr())?
                                inputColumn.getCaseStr().replaceAll("\\n","").replaceAll("\\t","")
                                :inputColumn.getCaseStr());
                        columnLineDbs.add(columnLineDb);
                    });
                    inputColumnList.clear();
                });
            }
            if(columnLineDbs.size() >0 ){
                DAOHelper.insertDelList(columnLineDbs , lineageColumnParsingDao , "insertColumnLineage",100);
                columnLineDbs.clear();
            }
        }catch (Exception e){
            logger.error("解析字段血缘报错,节点id为"+nodeId+"错误信息为："+ExceptionUtil.getExceptionTrace(e));
        }
    }

    /**
     * 迭代获取解析后的数据 然后生成数据库需要的实体类
     */
    private void parseColumnDb(List<ColumnLineDb> inputColumnList,List<ColumnLineageNode> nodes,String parentColumn,String processStr){
        for(ColumnLineageNode columnLineageNode:nodes){
            if(columnLineageNode.getNodes().size() != 0){
                if(StringUtils.isNotEmpty(columnLineageNode.getData().getCaseStr())){
                    processStr = StringUtils.isNotEmpty(processStr)?columnLineageNode.getData().getCaseStr() + " -> "
                            + processStr:columnLineageNode.getData().getCaseStr();
                }
                parseColumnDb(inputColumnList,columnLineageNode.getNodes(),
                        "*".equals(columnLineageNode.getData().getColumnName())?parentColumn:columnLineageNode.getData().getColumnName()
                        ,processStr);
            }else{
                ColumnName columnName = columnLineageNode.getData();
                ColumnLineDb columnLineDb = new ColumnLineDb();
                columnLineDb.setInputColumnName("*".equals(columnName.getColumnName())?parentColumn:columnName.getColumnName());
                columnLineDb.setInputTableName(columnName.getTableName());
                if(StringUtils.isNotEmpty(processStr)){
                    if(StringUtils.isNotBlank(columnName.getCaseStr())){
                        columnLineDb.setCaseStr(processStr+ " -> "+columnName.getCaseStr());
                    }else{
                        columnLineDb.setCaseStr(processStr);
                    }
                }else{
                    columnLineDb.setCaseStr(columnName.getCaseStr());
                }
                inputColumnList.add(columnLineDb);
            }
        }
    }

    public List<ColumnLineageTree> parseColumn(String sql,String dbType,String projectName,String nodeId)  throws Exception{
//        String sql = "";
//        String dbType = "hive";
        // sql里面的有些参数需要去除掉hive里面的清除数据
        sql = SqlParseMatch.sqlParse(sql,dbType);
        String result = SQLUtils.format(sql, dbType);
        List<SQLStatement> statementList = new ArrayList<>();
        try{
            statementList = SQLUtils.parseStatements(result,dbType);
        }catch (Exception e){
            logger.error(e.getMessage());
            result = result.replace("\n","  ").replace("\r","  ");
            String[] sqlList = result.split(";");
            for(String sqlStr:sqlList){
                try{
                    statementList.addAll(SQLUtils.parseStatements(sqlStr,dbType));
                }catch (Exception e1){
                    logger.error(e1.getMessage());
                }
            }
        }
//        List<SQLStatement> statementList = SQLUtils.parseStatements(result, dbType);
        List<ColumnLineageTree> resultList = new ArrayList<>();

        Map<String,List<String>> columnMap = new HashMap<>();
        for(int i = 0; i < statementList.size(); i++) {
            try{
                SQLStatement sqlStatement = statementList.get(i);
                SchemaStatVisitor visitor = null;
                if ("odps".equalsIgnoreCase(dbType)) {
                    visitor = new OdpsSchemaStatVisitor();
                } else if ("hive".equalsIgnoreCase(dbType)) {
                    visitor = new HiveSchemaStatVisitor();
                } else {
                    visitor = new OdpsSchemaStatVisitor();
                }
                sqlStatement.accept(visitor);
                String tableName = "";
                List<SQLTableElement> listColumn = null;
                SQLSelect sqlSelect = null;
                // 判断这个是不是建表语句，如果是，则获取到建表语句中的字段信息
                // 如果从数据仓库中无法获取到字段信息，就使用这个字段
                if(sqlStatement instanceof SQLInsertStatement){
                    SQLInsertStatement insertResultStr = (SQLInsertStatement)sqlStatement;
                    tableName = (StringUtils.isEmpty(insertResultStr.getTableSource().getSchema())? projectName
                            :insertResultStr.getTableSource().getSchema())+ "."
                            +  insertResultStr.getTableName().getSimpleName();
                    sqlSelect = insertResultStr.getQuery();
                }else if(sqlStatement instanceof OdpsInsertStatement){
                    // odps 的insert 语句里面最后封装的是hive 有点神奇
                    // 如果里面的 from 不为空 表示这个语句是 这种，神奇的写法
                    /**
                     *from tmp_roy_features_wz_dlwz_h1
                     * insert overwrite table sk_features_wz partition(
                     *     features_type='dlwz',
                     *     features_name='top1_jndd'
                     * )
                     * select sfzhm, dd
                     * where t2.rank=1
                     * insert overwrite table sk_features_wz partition(
                     *     features_type='dlwz',
                     *     features_name='top2_jndd'
                     * )
                     * select sfzhm, dd
                     * where t2.rank=2;
                     */
                    OdpsInsertStatement insertResultStr = (OdpsInsertStatement)sqlStatement;
                    List<HiveInsert> list = insertResultStr.getItems();
                    SQLTableSource fromSql = insertResultStr.getFrom();
                    if(list!= null && !list.isEmpty()){
                        // 这个存在多个的可能
                        for(HiveInsert hiveInsert:list){
                            sqlSelect = hiveInsert.getQuery();
                            tableName = (StringUtils.isEmpty(hiveInsert.getTableSource().getSchema())? projectName
                                    :hiveInsert.getTableSource().getSchema())+ "."
                                    +  hiveInsert.getTableName().getSimpleName();
                            getColumnLineageTree(sqlSelect,nodeId,tableName,dbType,columnMap,resultList,fromSql);
                        }
                    }
                    continue;
                } else if(sqlStatement instanceof HiveCreateTableStatement){
                    HiveCreateTableStatement hiveCreateTableStatement = (HiveCreateTableStatement)sqlStatement;
                    tableName = hiveCreateTableStatement.getTableSource().getExpr().toString();
                    if(!tableName.contains(".")){
                        tableName = projectName+"."+tableName;
                    }
                    sqlSelect = hiveCreateTableStatement.getSelect();
                    listColumn = hiveCreateTableStatement.getTableElementList();
                    List<SQLColumnDefinition> listPartitions  = hiveCreateTableStatement.getPartitionColumns();
                    if(listPartitions != null && !listPartitions.isEmpty()){
                        listColumn.addAll(listPartitions);
                    }
                }else if(sqlStatement instanceof  OdpsCreateTableStatement){
                    OdpsCreateTableStatement odpsCreateTableStatement = (OdpsCreateTableStatement)sqlStatement;
                    tableName = odpsCreateTableStatement.getTableSource().getExpr().toString();
                    if(!tableName.contains(".")){
                        tableName = projectName+"."+tableName;
                    }
                    sqlSelect = odpsCreateTableStatement.getSelect();
                    listColumn =  odpsCreateTableStatement.getTableElementList();
                    // 如果存在分区 则还需要把分区的信息也加上
                    List<SQLColumnDefinition> listPartitions = odpsCreateTableStatement.getPartitionColumns();
                    if(listPartitions != null && !listPartitions.isEmpty()){
                        listColumn.addAll(listPartitions);
                    }
                }else{
                    logger.info("sql的类型为"+sqlStatement.getClass());
                }
                if(listColumn != null && !listColumn.isEmpty()){
                    List<String> columnList = new ArrayList<>();
                    for(SQLTableElement sqlTableElement:listColumn){
                        String columnName = ((SQLColumnDefinition)sqlTableElement).getName().toString();
                        columnList.add(columnName);
                    }
                    columnMap.put(tableName.toLowerCase(),columnList);
                }
                if(sqlSelect == null){
                    logger.info("该sql不需要解析出字段血缘信息其中nodeId为【"+nodeId+"】");
                    continue;
                }
                // 解析血缘信息
                getColumnLineageTree(sqlSelect,nodeId,tableName,dbType,columnMap,resultList,null);
            }catch (Exception e){
                logger.error("【字段血缘】解析报错，其中nodeId为【"+nodeId+"】"+ExceptionUtil.getExceptionTrace(e));
            }
        }
        columnMap.clear();
        return resultList;
    }


    /**
     * 解析字段血缘的相关信息
     * @param sqlSelect
     * @param nodeId
     * @param tableName
     * @param dbType
     * @param columnMap
     * @param resultList
     * @throws Exception
     */
    private void getColumnLineageTree(SQLSelect sqlSelect,String nodeId,String tableName,
                                      String dbType,Map<String,List<String>> columnMap,
                                      List<ColumnLineageTree> resultList,SQLTableSource fromSql) throws Exception{
        List<ColumnParseData> columnParseDataList = new ArrayList();
        // 1: 获取输入表的字段信息
        List<String> insertTableColumnList = new ArrayList<>();
        try{
            List<FieldInfo> tableFields = restTemplateHandle.getLocalTableStructinfo(dbType,tableName);

            tableFields.stream().filter(d->!d.getIsPartition()).forEach(d ->{
                        insertTableColumnList.add(d.getFieldName());
                    }
            );
        }catch (Exception e){
            logger.error("【字段血缘】从数据仓库中获取表"+tableName+"字段信息报错"+ExceptionUtil.getExceptionTrace(e));
            logger.info("【字段血缘】从数据仓库中获取表"+tableName+"字段信息报错"+e.getMessage());
        }
        if(insertTableColumnList.size() == 0){
            logger.info("【字段血缘】从数据库中获取表"+tableName+"的字段为空");
            if(columnMap.getOrDefault(tableName.toLowerCase(),null) == null){
                throw new NullPointerException("【字段血缘】从数据库中获取表"+tableName+"的字段为空");
            }else{
                insertTableColumnList.clear();
                insertTableColumnList.addAll(columnMap.get(tableName.toLowerCase()));
            }
        }
        // 2:解析sql语句
        SQLSelectQuery sqlSelectQuery = sqlSelect.getQuery();
        List sqlSelectQueryList;
        if (sqlSelectQuery instanceof SQLSelectQueryBlock) {
            SQLSelectQueryBlock sqlSelectQueryBlock = (SQLSelectQueryBlock)sqlSelectQuery;
            sqlSelectQueryList = sqlSelectQueryBlock.getSelectList();
            ColumnParseData columnParseData = new ColumnParseData();
            String uuid = UUID.randomUUID().toString();
            columnParseData.setColumnList(this.parseSqlColumn(sqlSelectQueryList));
            columnParseData.setId(uuid);
            columnParseData.setTableNameType("alias");
            SQLTableSource tableSource = (fromSql == null?sqlSelectQueryBlock.getFrom():fromSql);
            columnParseDataList.add(columnParseData);
            this.getTableFromMap(tableSource, columnParseDataList, uuid,"");
        } else if (sqlSelectQuery instanceof SQLUnionQuery) {
            SQLUnionQuery sqlUnionQuery = (SQLUnionQuery)sqlSelectQuery;
            sqlSelectQueryList = sqlUnionQuery.getRelations();
            Iterator var25 = sqlSelectQueryList.iterator();

            while(var25.hasNext()) {
                SQLSelectQuery selectQuery = (SQLSelectQuery)var25.next();
                SQLSelectQueryBlock sqlSelectQueryBlock = (SQLSelectQueryBlock)selectQuery;
                ColumnParseData columnParseData = new ColumnParseData();
                columnParseData.setColumnList(this.parseSqlColumn(sqlSelectQueryBlock.getSelectList()));
                columnParseData.setId(UUID.randomUUID().toString());
                columnParseData.setTableNameType("alias");
                SQLTableSource tableSource = (fromSql == null? sqlSelectQueryBlock.getFrom():fromSql);
                columnParseDataList.add(columnParseData);
                this.getTableFromMap(tableSource, columnParseDataList, columnParseData.getId(),"");
            }
        } else {
            logger.info("【字段血缘】解析insert第一层sql时没有配置对应的解析类型，其中nodeId为【"+nodeId+"】该sql的解析类型为："+sqlSelectQuery.getClass());
            logger.error("【字段血缘】解析insert第一层sql时没有配置对应的解析类型，其中nodeId为【"+nodeId+"】该sql的解析类型为："+sqlSelectQuery.getClass());
        }

        // 3: 根据输出表的字段来匹配出对应的；流程
        ColumnLineageTree columnLineageTree = new ColumnLineageTree();

        String finalTableName = tableName;
        columnParseDataList.stream().filter(d -> {
            return StringUtils.isEmpty(d.getParentId());
        }).forEach(columnParseDatax -> {
            List<ColumnName> columnNameList = columnParseDatax.getColumnList();
            // 如果第一层是 * 所以从下一层获取字段信息
            if (columnNameList != null && columnNameList.stream().anyMatch(d -> {
                return "*".equals(d.getColumnName());
            })) {
                // 这个 是如果第一层为 *  则 从下一层获取字段信息
                List<ColumnName> columnNameListAll = columnNameList.stream().filter(d -> {
                    return "*".equals(d.getColumnName());
                }).collect(Collectors.toList());

                for(int num = 0;num <columnNameListAll.size();num++) {
                    ColumnName columnName1x = columnNameListAll.get(num);
                    List<ColumnName> list = new ArrayList();
                    this.getPeakColumnString(columnName1x, columnParseDataList, columnParseDatax, list,dbType);
                    int asteriskIndex = columnNameList.indexOf(columnName1x);
                    columnNameList.addAll(asteriskIndex, list);
                    columnNameList.remove(columnName1x);
                    list.clear();
                }
            }
            // insertTableColumnList.size() == columnNameList.size() &&
            if ((columnNameList.stream().noneMatch(d -> {
                return "*".equals(d.getColumnName());
            } ))){
                for(int num = 0; num < insertTableColumnList.size(); num++) {
                    try{
                        ColumnName columnName = new ColumnName();
                        columnName.setTableNameType("insert");
                        columnName.setTableName(finalTableName);
                        columnName.setColumnName(insertTableColumnList.get(num));
                        columnName.setType("sqlProperty");
                        String uuid = UUID.randomUUID().toString();
                        columnLineageTree.add("root", columnName, uuid);
                        ColumnName columnName1 = columnNameList.get(num);

                        if(columnName1.getColumnName().contains("#&#")){
                            // 这个表示是字段里面有多个 表示是 带有方式的字段信息
                            List<String> columnList = Arrays.asList(columnName1.getColumnName().split("#&#"));
                            columnList.forEach(column ->{
                                ColumnName columnName2 = new ColumnName();
                                String methodName = column.split("&&").length == 2?column.split("&&")[1]:"";
                                String columnStr =  column.split("&&")[0];
                                columnName2.setColumnName(columnStr.contains(".")?columnStr.split("\\.")[1]:columnStr.split("\\.")[0]);
                                columnName2.setType(ColumnName.SQL_METHOD);
                                columnName2.setAliasName(columnName1.getAliasName());
                                columnName2.setMethodName(methodName);
                                columnName2.setOwner(columnStr.contains(".")?columnStr.split("\\.")[0]:"");
                                columnName2.setTableName(columnName1.getTableName());
                                columnName2.setAliasTableName(columnName1.getAliasTableName());
                                columnName2.setTableNameType(columnName1.getTableNameType());
                                columnName2.setCaseStr(columnName1.getCaseStr());
                                String uuid1 = UUID.randomUUID().toString();
                                columnLineageTree.add(uuid, columnName2, uuid1);
                                this.getColumnLineageSql(columnLineageTree, columnName2, columnParseDataList, columnParseDatax.getId(), uuid1,dbType);
                            });
                        }else{
                            String uuid1 = UUID.randomUUID().toString();
                            String methodName = columnName1.getColumnName().split("&&").length == 2?columnName1.getColumnName().split("&&")[1]:"";
                            String columnStr =  columnName1.getColumnName().split("&&")[0];
                            columnName1.setColumnName(columnStr.contains(".")?columnStr.split("\\.")[1]:columnStr.split("\\.")[0]);
                            columnName1.setMethodName(methodName);
                            if(StringUtils.isEmpty(columnName1.getOwner())){
                                columnName1.setOwner(columnStr.contains(".")?columnStr.split("\\.")[0]:"");
                            }
                            columnLineageTree.add(uuid, columnName1, uuid1);
                            this.getColumnLineageSql(columnLineageTree, columnName1, columnParseDataList, columnParseDatax.getId(), uuid1,dbType);
                        }
                    }catch (Exception e){
                        logger.error("获取输出表的字段信息报错"+ExceptionUtil.getExceptionTrace(e));
                    }
                }
            } else {
                logger.info("【字段血缘】数据库字段的数据量为空");
            }
        });
        resultList.add(columnLineageTree);
        logger.debug("【字段血缘】解析的结果为：其中nodeId为【"+nodeId+"】"+JSONObject.toJSONString(columnLineageTree));
    }



    /**
     *  这个第一层为 * 时 从下一层数据中获取对应的字段
     * @param columnName
     * @param columnParseDataList
     * @param parseDataRoot
     * @param columnList
     */
    private void getPeakColumnString(ColumnName columnName, List<ColumnParseData> columnParseDataList,
                                     ColumnParseData parseDataRoot, List<ColumnName> columnList,String dbType) {
        columnParseDataList.stream().filter(d -> {
            return !StringUtils.isEmpty(columnName.getOwner()) ? columnName.getOwner().equalsIgnoreCase(d.getTableName())
                    && parseDataRoot.getId().equalsIgnoreCase(d.getParentId()) : parseDataRoot.getId().equalsIgnoreCase(d.getParentId());
        }).forEach(parseData -> {
            List<ColumnName> childCoumns = parseData.getColumnList();
            //  如果 parseData 是 表 并且字段 里面只有 * 则需要从 接口里面再获取字段
            if(ColumnParseData.TABLE.equalsIgnoreCase(parseData.getTableNameType())
                    &&(childCoumns == null||(childCoumns.stream().anyMatch(d -> "*".equals(d.getColumnName()))
                    &&childCoumns.size() == 1))){
                //  从数据仓库接口获取字段信息
                try{
                    if(childCoumns == null){
                        childCoumns = new ArrayList<>();
                    }else{
                        childCoumns.clear();
                    }
                    List<FieldInfo> tableFields = restTemplateHandle.getLocalTableStructinfo(dbType,parseData.getTableName());
                    for(FieldInfo tableField:tableFields){
                        if(!tableField.getIsPartition()){
                            ColumnName columnDataBase = new ColumnName();
                            columnDataBase.setType(ColumnName.SQL_PROPERTY);
                            columnDataBase.setColumnName(tableField.getFieldName());
                            columnDataBase.setTableName(parseData.getTableName());
                            childCoumns.add(columnDataBase);
                        }
                    }
                }catch (Exception e){
                    logger.error(""+ExceptionUtil.getExceptionTrace(e));
                }
            }
            for(ColumnName columnNameChild: childCoumns){
                if (!"*".equalsIgnoreCase(columnNameChild.getColumnName())) {
                    ColumnName columnNamePeak = new ColumnName();
                    columnNamePeak.setColumnName(columnNameChild.getColumnName());
                    columnNamePeak.setAliasName(columnNameChild.getAliasName());
                    columnNamePeak.setMethodName(columnNameChild.getMethodName());
                    columnNamePeak.setOwner(columnName.getOwner());
                    columnNamePeak.setTableName(columnName.getTableName());
                    columnNamePeak.setTableNameType(columnName.getTableNameType());
                    columnNamePeak.setAliasTableName(columnName.getAliasTableName());
                    columnNamePeak.setType(columnName.getType());
                    columnList.add(columnNamePeak);
                } else {
                    this.getPeakColumnString(columnNameChild, columnParseDataList, parseData, columnList,dbType);
                }
            }

        });
    }

    /**
     *  解析出带
     * @param columnLineageTree
     * @param parentColumnName
     * @param columnParseDataList
     * @param parentNodeId
     * @param parentLineageId
     */
    public void getColumnLineageSql(ColumnLineageTree columnLineageTree, ColumnName parentColumnName,
                                    List<ColumnParseData> columnParseDataList, String parentNodeId, String parentLineageId
            ,String dbType) {
        columnParseDataList.stream().filter((d) -> {
            if (!"sqlProperty".equalsIgnoreCase(parentColumnName.getType())
                    && !"sqlAll".equalsIgnoreCase(parentColumnName.getType())
                    && !"sqlMethod".equalsIgnoreCase(parentColumnName.getType())
                    && !"sqlCase".equalsIgnoreCase(parentColumnName.getType())) {
                return false;
            } else {
                return StringUtils.isEmpty(parentColumnName.getOwner()) ? parentNodeId.equalsIgnoreCase(d.getParentId()) : parentNodeId.equalsIgnoreCase(d.getParentId())
                        && (parentColumnName.getOwner().equalsIgnoreCase(d.getTableName())||parentColumnName.getOwner().equalsIgnoreCase(d.getAlias()));
            }
        }).forEach(columnParseData -> {
            if (columnParseData.getColumnList() != null && columnParseData.getColumnList().size() > 0) {
                //  sql中的查询字段存在 select * , column1 ,column2 from dual ,还需要判断其它的字段是否存在，存在则不使用这个 *
                //  所以这个会重复 头疼
                Long columnCount = columnParseData.getColumnList().stream().filter(d->{
                    if(StringUtils.isNotEmpty(parentColumnName.getOwner())){
                       return  d.getColumnName().equalsIgnoreCase(parentColumnName.getColumnName())||
                            parentColumnName.getColumnName().equalsIgnoreCase(d.getAliasName());
                    }else{
                        return !StringUtils.isEmpty(d.getAliasName()) ? d.getAliasName().equalsIgnoreCase(
                            parentColumnName.getColumnName()) : d.getColumnName().equalsIgnoreCase(parentColumnName.getColumnName());
                }}).count();
                columnParseData.getColumnList().stream().filter(d -> {
                    if ("*".equalsIgnoreCase(d.getColumnName()) && columnCount == 0) {
                        return true;
                    } else {
//                        return !StringUtils.isEmpty(d.getAliasName()) ? d.getAliasName().equalsIgnoreCase(
//                                parentColumnName.getColumnName()) : d.getColumnName().equalsIgnoreCase(parentColumnName.getColumnName());
                        if(StringUtils.isNotEmpty(parentColumnName.getOwner())){
                            return  d.getColumnName().equalsIgnoreCase(parentColumnName.getColumnName())||
                                    parentColumnName.getColumnName().equalsIgnoreCase(d.getAliasName());
                        }else{
                            return !StringUtils.isEmpty(d.getAliasName()) ? d.getAliasName().equalsIgnoreCase(
                                    parentColumnName.getColumnName()) : d.getColumnName().equalsIgnoreCase(parentColumnName.getColumnName());
                        }
                    }
                }).forEach(columnNamex -> {
                    // 因为 如果这个字段是 函数/ case 的字段信息 则会存在多个字段信息，需要这样来循环遍历
                    List<String> columnList = Arrays.asList(columnNamex.getColumnName().split("#&#"));
                    columnList.forEach(column ->{
                        ColumnName columnName1 = new ColumnName();
                        String methodName = column.split("&&").length == 2?column.split("&&")[1]:"";
                        String columnStr =  column.split("&&")[0];
                        columnName1.setTableName(columnParseData.getTableName());
                        columnName1.setTableNameType(columnParseData.getTableNameType());
                        if("*".equals(columnStr)){
                            columnName1.setColumnName(parentColumnName.getColumnName());
                        }else{
                            columnName1.setColumnName(columnStr.contains(".")?columnStr.split("\\.")[1]:columnStr.split("\\.")[0]);
                            columnName1.setAliasName(columnNamex.getAliasName());
                        }
                        columnName1.setOwner(columnStr.contains(".")?columnStr.split("\\.")[0]:columnNamex.getOwner());
                        columnName1.setType(columnNamex.getType());
                        columnName1.setMethodName(methodName);
                        columnName1.setCaseStr(columnNamex.getCaseStr());
                        String uuid = UUID.randomUUID().toString();
                        if (!"table".equalsIgnoreCase(columnNamex.getTableNameType())) {
                            columnLineageTree.add(parentLineageId, columnName1, uuid);
                            this.getColumnLineageSql(columnLineageTree, columnName1, columnParseDataList, columnParseData.getId(), uuid,dbType);
                        } else {
                            // 如果最后是 表 则需要从数据仓库平台获取这个表的字段信息
                            //  获取字段信息已经写入到缓存中
                            try{
                                if("*".equals(columnNamex.getColumnName())){
                                    List<FieldInfo> tableFields = restTemplateHandle.getLocalTableStructinfo(dbType,columnParseData.getTableName());
                                    for (FieldInfo tableField : tableFields) {
                                        if(tableField.getFieldName().equalsIgnoreCase(parentColumnName.getColumnName())){
                                            columnName1.setColumnName(tableField.getFieldName());
                                            columnName1.setAliasName(tableField.getFieldName());
                                        }
                                    }
                                }
                                columnLineageTree.add(parentLineageId, columnName1, uuid);
                            }catch (Exception e){
                                logger.error("【字段解析】从数据仓库获取表【"+columnParseData.getTableName()+"】的字段信息报错"+ ExceptionUtil.getExceptionTrace(e));
                                columnLineageTree.add(parentLineageId, columnName1, uuid);
                            }
                        }
                    });
                });
            } else {
                ColumnName columnName = columnLineageTree.get(parentLineageId);
                if (columnName != null) {
                    columnName.setAliasTableName(columnName.getTableName());
                    columnName.setTableName(columnParseData.getTableName());
                    columnName.setTableNameType("table");
                    columnLineageTree.setData(parentLineageId, columnName);
                }
            }

        });
    }


    private void getTableFromMap(SQLObject tableSource, List<ColumnParseData> columnParseDataList, String parentId,String parentAliasName) {
//        SQLSubqueryTableSource sqlRight;
        if (tableSource instanceof SQLJoinTableSource) {
            SQLJoinTableSource sqlJoinTableSource = (SQLJoinTableSource)tableSource;
            if (sqlJoinTableSource.getLeft() instanceof SQLExprTableSource) {
                this.getTableFromMap(sqlJoinTableSource.getLeft(), columnParseDataList, parentId,"");
            } else if (sqlJoinTableSource.getLeft() instanceof SQLSubqueryTableSource) {
                SQLSubqueryTableSource sqlLeft = (SQLSubqueryTableSource)sqlJoinTableSource.getLeft();
                ColumnParseData leftParseData = new ColumnParseData();
                leftParseData.setId(UUID.randomUUID().toString());
                leftParseData.setParentId(parentId);
                leftParseData.setTableNameType("alias");
                leftParseData.setTableName(sqlLeft.getAlias());
                leftParseData.setColumnList(this.parseSqlColumn(((SQLSelectQueryBlock)sqlLeft.getSelect().getQuery()).getSelectList()));
                columnParseDataList.add(leftParseData);
                this.getTableFromMap(((SQLSelectQueryBlock)sqlLeft.getSelect().getQuery()).getFrom(), columnParseDataList,
                        leftParseData.getId(),sqlLeft.getAlias());
            } else if(sqlJoinTableSource.getLeft() instanceof SQLJoinTableSource){
                this.getTableFromMap(sqlJoinTableSource.getLeft(), columnParseDataList, parentId,"");
            }else{
                logger.error("解析sql语句没有编写相关处理操作");
            }

            if (sqlJoinTableSource.getRight() instanceof SQLExprTableSource) {
                this.getTableFromMap(sqlJoinTableSource.getRight(), columnParseDataList, parentId,"");
            } else if (sqlJoinTableSource.getRight() instanceof SQLSubqueryTableSource) {
                SQLSubqueryTableSource sqlRight = (SQLSubqueryTableSource)sqlJoinTableSource.getRight();
                ColumnParseData rightParseData = new ColumnParseData();
                rightParseData.setId(UUID.randomUUID().toString());
                rightParseData.setParentId(parentId);
                rightParseData.setTableNameType("alias");
                rightParseData.setTableName(sqlRight.getAlias());
                rightParseData.setColumnList(this.parseSqlColumn(((SQLSelectQueryBlock)sqlRight.getSelect().getQuery()).getSelectList()));
                columnParseDataList.add(rightParseData);
                this.getTableFromMap(((SQLSelectQueryBlock)sqlRight.getSelect().getQuery()).getFrom(), columnParseDataList, rightParseData.getId(),sqlRight.getAlias());
            } else if(sqlJoinTableSource.getRight() instanceof SQLJoinTableSource){
                this.getTableFromMap(sqlJoinTableSource.getRight(), columnParseDataList, parentId,"");
            }else {
                logger.error("解析sql语句没有编写相关处理操作");
            }
        } else if (tableSource instanceof SQLUnionQueryTableSource) {
            SQLUnionQuery sqlUnionQuery = ((SQLUnionQueryTableSource)tableSource).getUnion();
            List<SQLSelectQuery> sqlSelectQueryList = sqlUnionQuery.getRelations();
            Iterator var15 = sqlSelectQueryList.iterator();

            while(var15.hasNext()) {
                SQLSelectQuery sqlSelectQuery = (SQLSelectQuery)var15.next();
                if(sqlSelectQuery instanceof SQLSelectQueryBlock){
                    SQLSelectQueryBlock sqlSelectQueryBlock = (SQLSelectQueryBlock)sqlSelectQuery;
                    ColumnParseData columnParseData = new ColumnParseData();
                    columnParseData.setColumnList(this.parseSqlColumn(sqlSelectQueryBlock.getSelectList()));
                    columnParseData.setId(UUID.randomUUID().toString());
                    columnParseData.setParentId(parentId);
                    columnParseData.setTableNameType("alias");
                    columnParseData.setTableName(((SQLUnionQueryTableSource)tableSource).getAlias());
                    columnParseDataList.add(columnParseData);
                    this.getTableFromMap(sqlSelectQueryBlock.getFrom(), columnParseDataList, columnParseData.getId()
                            ,((SQLUnionQueryTableSource)tableSource).getAlias());
                }else{
                    this.getTableFromMap(sqlSelectQuery,columnParseDataList,parentId,((SQLUnionQueryTableSource)tableSource).getAlias());
                }

            }
        } else if (tableSource instanceof SQLExprTableSource) {
            SQLExprTableSource sqlExpr = (SQLExprTableSource)tableSource;
            ColumnParseData columnParseData = new ColumnParseData();
            columnParseData.setTableName(sqlExpr.getName().toString());
            columnParseData.setTableNameType("table");
            columnParseData.setParentId(parentId);
            columnParseData.setId(UUID.randomUUID().toString());
//            columnParseData.setAlias(StringUtils.isNotEmpty(parentAliasName)?parentAliasName:sqlExpr.getAlias());
            columnParseData.setAlias(sqlExpr.getAlias());
            columnParseDataList.add(columnParseData);
        } else if (tableSource instanceof SQLSubqueryTableSource) {
            ColumnParseData columnParseData = new ColumnParseData();
            SQLSubqueryTableSource sqlRight = (SQLSubqueryTableSource)tableSource;
            SQLSelectQueryBlock sqlSelectQuery = (SQLSelectQueryBlock)sqlRight.getSelect().getQuery();
            columnParseData.setTableName(sqlRight.getAlias());
            columnParseData.setId(UUID.randomUUID().toString());
            columnParseData.setParentId(parentId);
            columnParseData.setTableNameType("alias");
            columnParseData.setColumnList(this.parseSqlColumn(sqlSelectQuery.getSelectList()));
            columnParseDataList.add(columnParseData);
            this.getTableFromMap(sqlSelectQuery.getFrom(), columnParseDataList, columnParseData.getId(),sqlRight.getAlias());
        } else if(tableSource instanceof SQLUnionQuery){
            SQLUnionQuery sqlUnionQuery = (SQLUnionQuery)tableSource;
            List<SQLSelectQuery> sqlSelectQueryList = sqlUnionQuery.getRelations();
            for(SQLSelectQuery sqlSelectQuery:sqlSelectQueryList){
                if(sqlSelectQuery instanceof SQLSelectQueryBlock){
                    SQLSelectQueryBlock sqlSelectQueryBlock = (SQLSelectQueryBlock)sqlSelectQuery;
                    ColumnParseData columnParseData = new ColumnParseData();
                    columnParseData.setColumnList(this.parseSqlColumn(sqlSelectQueryBlock.getSelectList()));
                    columnParseData.setId(UUID.randomUUID().toString());
                    columnParseData.setParentId(parentId);
                    columnParseData.setTableNameType("alias");
                    columnParseData.setTableName(parentAliasName);
                    columnParseDataList.add(columnParseData);
                    this.getTableFromMap(sqlSelectQueryBlock.getFrom(), columnParseDataList, columnParseData.getId(),parentAliasName);
                }else{
                    this.getTableFromMap(sqlSelectQuery,columnParseDataList,parentId,parentAliasName);
                }
            }
        }else {
            logger.error("解析sql语句没有编写相关处理操作"+tableSource.getClass());
        }

    }

    private List<ColumnName> parseSqlColumn(List<SQLSelectItem> list) {
        List<ColumnName> result = new ArrayList();
        list.forEach((sqlSelectItem) -> {
            ColumnName columnName = new ColumnName();
            parseSqlColumnExpr(columnName,sqlSelectItem.getExpr(),sqlSelectItem.getAlias());
            result.add(columnName);
        });
        return result;
    }


    /**
     * 迭代解析字段的数据信息
     */
    private void parseSqlColumnExpr(ColumnName columnName,SQLExpr sqlExpr,String aliasName){
        if (sqlExpr instanceof SQLPropertyExpr) {
            // 简单的字段信息
            columnName.setColumnName(((SQLPropertyExpr)sqlExpr).getName());
            columnName.setOwner(((SQLPropertyExpr)sqlExpr).getOwnernName());
            columnName.setAliasName(aliasName);
            columnName.setType("sqlProperty");
//            result.add(columnName);
        } else if (sqlExpr instanceof SQLMethodInvokeExpr) {
            // 带有函数的字段信息  比如 md5(a) 需要解析出所有的字段信息，然后获取到字段信息
            // 存在一种情况 只是函数名称 里面没有字段信息 或者 里面即存在字段信息又存在固定值
            columnName.setAliasName(aliasName);
            String methodName = ((SQLMethodInvokeExpr) sqlExpr).getMethodName();
//                columnName.setColumnName(sqlSelectItem.getExpr().toString());
            List<String> columnList = new ArrayList<>();
            columnName.setType("sqlMethod");
            methodColumnIteration(((SQLMethodInvokeExpr) sqlExpr).getArguments(),columnList,methodName,methodName);
            if(columnList.size() == 0){
                columnName.setColumnName(sqlExpr.toString());
                columnName.setMethodName(sqlExpr.toString());
            }else{
                columnName.setColumnName(StringUtils.join(columnList,"#&#"));
//                columnName.setMethodName(methodName);
            }
            columnName.setCaseStr(((SQLMethodInvokeExpr)sqlExpr).toString());
            columnList.clear();
//                columnName.setMethodName(methodName);
        } else if (sqlExpr instanceof SQLAllColumnExpr) {
            // * 的字段信息
            columnName.setColumnName(((SQLAllColumnExpr)sqlExpr).toString());
            columnName.setType("sqlAll");
        } else if (sqlExpr instanceof SQLIdentifierExpr) {
            columnName.setColumnName(((SQLIdentifierExpr)sqlExpr).getName());
            columnName.setAliasName(aliasName);
            columnName.setType("sqlProperty");
        } else if (sqlExpr instanceof SQLSelectItem) {
            // select  等 选择类型的字段信息
            columnName.setColumnName(aliasName);
            columnName.setType("sqlProperty");
        } else if(sqlExpr instanceof SQLCaseExpr){
            // @ case when 的字段信息 需要解析出 这个字段所有的 可能存在多个
            List<String> resultList = new ArrayList<>();
            //  case 的迭代获取字段信息
            sqlCaseMainIteration(resultList,sqlExpr);
            columnName.setCaseStr(sqlExpr.toString());
            columnName.setAliasName(aliasName);
            columnName.setType(ColumnName.SQL_CASE);
            columnName.setColumnName(StringUtils.join(resultList,"#&#"));
            resultList.clear();
        }else if(sqlExpr instanceof  SQLCastExpr){
//                columnName.setColumnName(((SQLCastExpr) sqlSelectItem.getExpr()).getExpr().toString());
            parseSqlColumnExpr(columnName,((SQLCastExpr) sqlExpr).getExpr(),aliasName);
            columnName.setType("sqlMethod");
            columnName.setMethodName("cast");
            columnName.setAliasName(aliasName);
//            columnName.setColumnName("datatype");
//            columnName.setOwner("t2");
        }else if(sqlExpr instanceof SQLBinaryOpExpr){
            // 这个是数学运算
            List<SQLExpr> list = new ArrayList<>();
            list.add(((SQLBinaryOpExpr) sqlExpr).getLeft());
            list.add(((SQLBinaryOpExpr) sqlExpr).getRight());
            columnName.setType("sqlMethod");
            List<String> columnList = new ArrayList<>();
            methodColumnIteration(list,columnList,"","");
            columnName.setColumnName(StringUtils.join(columnList,"#&#"));
            columnName.setCaseStr(((SQLBinaryOpExpr) sqlExpr).toString());
            columnList.clear();
        } else {
            //  固定值 比如 "1" as columnName
            columnName.setColumnName(sqlExpr.toString());
            columnName.setAliasName(aliasName);
            columnName.setType("sqlFixed");
        }
    }

    private void sqlCaseMainIteration(List<String> resultList,SQLExpr sqlExpr){
        List<SQLCaseExpr.Item> resultCase = ((SQLCaseExpr) sqlExpr).getItems();
        // 迭代获取对应的字段信息
        for(SQLCaseExpr.Item item:resultCase){
            if(item.getConditionExpr() instanceof SQLBinaryOpExpr){
                sqlCaseColumnIteration(((SQLBinaryOpExpr)item.getConditionExpr()).getLeft(),resultList);
                sqlCaseColumnIteration(((SQLBinaryOpExpr)item.getConditionExpr()).getRight(),resultList);
            }else{
                sqlCaseColumnIteration(item.getConditionExpr(),resultList);
            }
            // case 里面还有 then 对应的数据
            sqlCaseColumnIteration(item.getValueExpr(),resultList);
        }
        // else 里面的值
        if(!(((SQLCaseExpr) sqlExpr).getElseExpr() instanceof SQLNullExpr)
                && ((SQLCaseExpr) sqlExpr).getElseExpr() != null ){
            if(((SQLCaseExpr) sqlExpr).getElseExpr() instanceof SQLCaseExpr){
                sqlCaseMainIteration(resultList,((SQLCaseExpr) sqlExpr).getElseExpr());
            }else{
                if((((SQLCaseExpr) sqlExpr).getElseExpr() instanceof  SQLPropertyExpr) ||
                        (((SQLCaseExpr) sqlExpr).getElseExpr() instanceof  SQLBinaryOpExpr) ){
                    resultList.add(((SQLCaseExpr) sqlExpr).getElseExpr().toString());
                }else{
                    logger.info("case里面else里面字段信息没有对应解决方案"+((SQLCaseExpr) sqlExpr).getElseExpr().getClass());
                }
            }
        }
        // valueExpr 里面的值
        if(!(((SQLCaseExpr) sqlExpr).getValueExpr() instanceof SQLNullExpr)
                && ((SQLCaseExpr) sqlExpr).getValueExpr() != null){
            if(((SQLCaseExpr) sqlExpr).getValueExpr() instanceof SQLCaseExpr){
                sqlCaseMainIteration(resultList,((SQLCaseExpr) sqlExpr).getValueExpr());
            }else{
                if((((SQLCaseExpr) sqlExpr).getValueExpr() instanceof  SQLPropertyExpr) ||
                        (((SQLCaseExpr) sqlExpr).getValueExpr() instanceof  SQLBinaryOpExpr) ){
                    resultList.add(((SQLCaseExpr) sqlExpr).getValueExpr().toString());
                }else{
                    logger.info("case里面第一层的参数里面字段信息没有对应解决方案"+((SQLCaseExpr) sqlExpr).getValueExpr().getClass());
                }
            }
        }


    }


    /**
     *  如果这个字段是 case 的类型 则需要解析出
     * @param sqlExpr
     * @param methodColumnList
     */
    private void sqlCaseColumnIteration(SQLExpr sqlExpr, List<String> methodColumnList){
        if(sqlExpr instanceof SQLBinaryOpExpr){
            sqlCaseColumnIteration(((SQLBinaryOpExpr) sqlExpr).getLeft(),methodColumnList);
            sqlCaseColumnIteration(((SQLBinaryOpExpr) sqlExpr).getRight(),methodColumnList);
        }else if(sqlExpr instanceof SQLIdentifierExpr){
            if(StringUtils.isNotBlank(sqlExpr.toString()) &&
                    !methodColumnList.contains(sqlExpr.toString().toLowerCase())){
                methodColumnList.add(sqlExpr.toString().toLowerCase());
            }
        }else if(sqlExpr instanceof SQLPropertyExpr){
            if(StringUtils.isNotBlank(sqlExpr.toString()) &&
                    !methodColumnList.contains(sqlExpr.toString().toLowerCase())){
                methodColumnList.add(sqlExpr.toString().toLowerCase());
            }
        }else if(sqlExpr instanceof SQLInListExpr){
            if(StringUtils.isNotEmpty(((SQLInListExpr)sqlExpr).getExpr().toString()) &&
                    !methodColumnList.contains(((SQLInListExpr)sqlExpr).getExpr().toString().toLowerCase())){
                methodColumnList.add(((SQLInListExpr)sqlExpr).getExpr().toString().toLowerCase());
            }
        }else if(sqlExpr instanceof SQLMethodInvokeExpr){
            // case里面是一个函数
            List<SQLExpr> list = ((SQLMethodInvokeExpr) sqlExpr).getArguments();
            for(SQLExpr sqlExpr1:list){
                sqlCaseColumnIteration(sqlExpr1,methodColumnList);
            }
        }else if(sqlExpr instanceof SQLArrayExpr){
            SQLExpr sqlExpr1 = ((SQLArrayExpr) sqlExpr).getExpr();
            sqlCaseColumnIteration(sqlExpr1,methodColumnList);
        }else{
            logger.info("没有配置对应的字段类型"+sqlExpr.getClass());
        }
    }

    /**
     *  如果这个字段是 函数类型，并且里面包含字段信息 则需要迭代获取对应的字段关系
     * @param sqlExprList
     * @param methodColumnList
     */
    private void methodColumnIteration(List<SQLExpr> sqlExprList,List<String> methodColumnList
            ,String methodName,String mainMethodName){
        if(sqlExprList != null){
            for(SQLExpr sqlExpr:sqlExprList){
                if(sqlExpr instanceof SQLPropertyExpr){
                    // 这个直接是字段信息
                    methodColumnList.add(StringUtils.isEmpty(((SQLPropertyExpr) sqlExpr).getOwnernName())
                            ?((SQLPropertyExpr) sqlExpr).getName()+ "&&" +methodName:((SQLPropertyExpr) sqlExpr).getOwnernName()+"."+((SQLPropertyExpr) sqlExpr).getName()+ "&&" +methodName);
                    methodName =  mainMethodName;
                }else if(sqlExpr instanceof SQLMethodInvokeExpr){
                    // 还有一层 函数信息
                    if( StringUtils.isNotBlank(((SQLMethodInvokeExpr) sqlExpr).getMethodName())){
                        methodName = methodName +" -> "+ ((SQLMethodInvokeExpr) sqlExpr).getMethodName();
                    }
                    methodColumnIteration(((SQLMethodInvokeExpr) sqlExpr).getArguments(),methodColumnList,methodName,mainMethodName);
                }else if(sqlExpr instanceof SQLBinaryOpExpr){
                    List<SQLExpr> list = new ArrayList<>();
                    list.add(((SQLBinaryOpExpr) sqlExpr).getLeft());
                    list.add(((SQLBinaryOpExpr) sqlExpr).getRight());
                    methodColumnIteration(list,methodColumnList,methodName,mainMethodName);
                }else if(sqlExpr instanceof SQLIdentifierExpr){
                    methodColumnList.add(((SQLIdentifierExpr) sqlExpr).getName()+ "&&" +methodName);
                }else if(sqlExpr instanceof SQLCastExpr){
                    SQLExpr sqlExpr1 = ((SQLCastExpr) sqlExpr).getExpr();
                    List<SQLExpr> sqlExprList1 = new ArrayList<>();
                    sqlExprList1.add(sqlExpr1);
                    methodColumnIteration(sqlExprList1,methodColumnList,"cast",mainMethodName);
                }else if(sqlExpr instanceof SQLCaseExpr){
                    sqlCaseMainIteration(methodColumnList,sqlExpr);
                }else{
                    logger.info("函数信息迭代的方法中没有编写对应的解析方法"+sqlExpr.getClass());
                    logger.error("函数信息迭代的方法中没有编写对应的解析方法"+sqlExpr.getClass());
                }
            }
        }
    }

}
