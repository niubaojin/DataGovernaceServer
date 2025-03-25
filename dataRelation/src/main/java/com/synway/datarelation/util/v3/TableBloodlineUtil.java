package com.synway.datarelation.util.v3;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.hive.visitor.HiveSchemaStatVisitor;
import com.alibaba.druid.sql.dialect.odps.visitor.OdpsSchemaStatVisitor;
import com.alibaba.druid.sql.visitor.SchemaStatVisitor;
import com.alibaba.druid.stat.TableStat;
import com.alibaba.druid.util.JdbcConstants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.synway.datarelation.pojo.modelrelation.ModelLinkData;
import com.synway.common.exception.ExceptionUtil;
import com.synway.datarelation.util.SqlParseMatch;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数据血缘关系的正则解析方式
 */
public class TableBloodlineUtil {
    private static Logger logger = LoggerFactory.getLogger(TableBloodlineUtil.class);


    // 解析同步任务中的表信息
    public static ModelLinkData getSynchronousTask(String sql, String projectNameEn){
        ModelLinkData modelLinkData = null;
        try{
            // dataWork 上datax的脚本存在两种版本 1.0  2.0
            JSONObject dataxJson = JSON.parseObject(sql);
            Configuration config = Configuration.from(dataxJson);
            String version = config.getString("version");
            if("1.0".equalsIgnoreCase(version)){
                // 解析1.0中的reader
                modelLinkData = new ModelLinkData();
                String pluginReader = config.getString("configuration.reader.plugin","");
                String projectReader = "";
                String tableNameReader = config.getString("configuration.reader.parameter.table","");
                if(tableNameReader.contains(".")){
                    projectReader = tableNameReader.split("\\.")[0];
                    tableNameReader = tableNameReader.split("\\.")[1];
                }
                if(StringUtils.isEmpty(projectReader)){
                    projectReader = config.getString("configuration.reader.name","").replaceAll(pluginReader+"_","");
                    projectReader = projectReader.replaceAll("algo_","").replaceAll("alg_","").replaceAll("_product","");
                }
                if("first".equalsIgnoreCase(projectReader)){
                    projectReader = projectNameEn;
                }
                if("odps".equalsIgnoreCase(projectReader)){
                    projectReader = projectNameEn;
                }
                modelLinkData.setParentnode(StringUtils.isEmpty(projectReader)?tableNameReader:projectReader+"."+tableNameReader);
                // writer
                String pluginWriter = config.getString("configuration.writer.plugin","");
                String projectWriter = "";
                String tableNameWriter = config.getString("configuration.writer.parameter.table","");
                if(tableNameWriter.contains(".")){
                    projectWriter = tableNameWriter.split("\\.")[0];
                    tableNameWriter = tableNameWriter.split("\\.")[1];
                }
                if(StringUtils.isEmpty(projectWriter)){
                    projectWriter = config.getString("configuration.writer.name","").replaceAll(pluginWriter+"_","");
                    projectWriter = projectWriter.replaceAll("algo_","").replaceAll("alg_","").replaceAll("_product","");
                }
                if("first".equalsIgnoreCase(projectWriter)){
                    projectWriter = projectNameEn;
                }
                if("odps".equalsIgnoreCase(projectWriter)){
                    projectWriter = projectNameEn;
                }
                if("ads".equalsIgnoreCase(projectWriter)){
                    projectWriter = "hc_db";
                }
                modelLinkData.setChildnode(StringUtils.isEmpty(projectWriter)?tableNameWriter:projectWriter+"."+tableNameWriter);
            }else if("2.0".equalsIgnoreCase(version)){
                modelLinkData = new ModelLinkData();
                String stepTypeParent = config.getString("steps[0].steptype","");
                //
                String projectNameReader = "";
                String dataSourceReader = config.getString("steps[0].parameter.datasource","");
                if(StringUtils.isEmpty(dataSourceReader)){
                    dataSourceReader = config.getString("steps[0].parameter.connection[0].datasource","");
                }
                String[] projectNameList = dataSourceReader.split(stepTypeParent+"_");
                if(projectNameList.length == 2){
                    projectNameReader = projectNameList[1];
                }else if(projectNameList.length == 1){
                    projectNameReader = projectNameList[0];
                }else{
                    projectNameReader = "";
                }
                String tableName =  config.getString("steps[0].parameter.table","");
                if(StringUtils.isEmpty(tableName)){
                    List<String> tableNameList =  config.getList("steps[0].parameter.connection[0].table",String.class);
                    tableName = tableNameList.get(0);
                }
                if(tableName.contains(".")){
                    projectNameReader = tableName.split("\\.")[0];
                    tableName = tableName.split("\\.")[1];
                }
                if("first".equalsIgnoreCase(projectNameReader)){
                    projectNameReader = projectNameEn;
                }
                if("odps".equalsIgnoreCase(projectNameReader)){
                    projectNameReader = projectNameEn;
                }
                modelLinkData.setParentnode(projectNameReader+"."+tableName);

                String stepTypeChild = config.getString("steps[1].steptype","");
                String dataSourceWriter =config.getString("steps[1].parameter.datasource","");
                if(StringUtils.isEmpty(dataSourceWriter)){
                    dataSourceWriter = config.getString("steps[1].parameter.connection[0].datasource","");
                }
                String[] projectNameChildList = dataSourceWriter.split(stepTypeChild+"_");
                String projectNameWriter = "";
                if(projectNameChildList.length == 2){
                    projectNameWriter = projectNameChildList[1];
                }else if(projectNameChildList.length == 1){
                    projectNameWriter = projectNameChildList[0];
                }else{
                    projectNameWriter = "";
                }
                String tableNameChild =  config.getString("steps[1].parameter.table","");
                if(StringUtils.isEmpty(tableNameChild)){
                    tableNameChild =  config.getString("steps[1].parameter.connection[0].table","");
                }
                if(tableNameChild.contains(".")){
                    projectNameWriter = tableNameChild.split("\\.")[0];
                    tableNameChild = tableNameChild.split("\\.")[1];
                }
                if("first".equalsIgnoreCase(projectNameWriter)){
                    projectNameWriter = projectNameEn;
                }
                if("odps".equalsIgnoreCase(projectNameWriter)){
                    projectNameWriter = projectNameEn;
                }
                modelLinkData.setChildnode(projectNameWriter+"."+tableNameChild);
            }else{
                throw new NullPointerException("版本号为"+version+"不正确，没有解析的程序");
            }
        }catch (Exception e){
            modelLinkData = null;
            logger.error("[sql解析失败]"+sql+"："+ ExceptionUtil.getExceptionTrace(e));
        }
        return modelLinkData;
    }


    /**
     * 解析出sql中表的关系，其中insert create 为输出表 其他为输入表
     *
     * @param sql
     * @param nodeId  节点id
     * @return
     */
    public static List<ModelLinkData> getTableRelation(String sql,String nodeId,String projectName,String nodeType,String dbType){
        // 要去除掉阿里的datawork里面的特殊字符  ${} 这些内容
        List<ModelLinkData> list = new ArrayList<>();
        try{
            if(sql == null ||StringUtils.isEmpty(sql)){
                throw new Exception("sql语句为空，不能解析数据");
            }
            sql = sql.toLowerCase();
            if("23".equalsIgnoreCase(nodeType)){
                ModelLinkData tableLink = getSynchronousTask(sql.toLowerCase(),projectName);
                if(tableLink== null || StringUtils.isEmpty(tableLink.getParentnode()) || tableLink.getParentnode().equals(".")){
                    throw new NullPointerException("解析到的输入表信息为空");
                }
                if(StringUtils.isEmpty(tableLink.getChildnode()) || tableLink.getChildnode().equals(".")){
                    throw new NullPointerException("解析到的输出表信息为空");
                }
                ModelLinkData tableLinkNew = new ModelLinkData();
                tableLinkNew.setParentnode(nodeId+"_"+tableLink.getParentnode());
                tableLinkNew.setChildnode(nodeId+"_"+tableLink.getChildnode());
                list.add(tableLinkNew);
                return list;
            }
            try{
                sql = SqlParseMatch.sqlParse(sql,dbType);
            }catch (Exception e){
                logger.error("替换不能解析的内容报错"+e.getMessage());
            }
            String result = SQLUtils.format(sql,dbType);
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
                        List<ModelLinkData> tableBloodlineList = TableBloodlineUtil.getTableBloodlineList(
                                sqlStr,nodeId,projectName);
                        list.addAll(tableBloodlineList);
                        logger.error(e1.getMessage());
                    }
                }
            }
            List<String> outTableNameList = new ArrayList<>();
            List<String> inTableNameList = new ArrayList<>();
            for(int i =0; i<statementList.size(); i++){
                try{
                    SQLStatement sqlStatement = statementList.get(i);
                    SchemaStatVisitor visitor = null;
                    if(JdbcConstants.ODPS.equalsIgnoreCase(dbType)){
                        visitor = new OdpsSchemaStatVisitor();
                    }else if(JdbcConstants.HIVE.equalsIgnoreCase(dbType)){
                        visitor = new HiveSchemaStatVisitor();
                    }else{
                        visitor = new OdpsSchemaStatVisitor();
                    }
                    sqlStatement.accept(visitor);
                    Map<TableStat.Name, TableStat> tableMap = visitor.getTables();
                    // 只有有两张表时，才说明存在表关系，一张表不需要表关系
                    if(tableMap.size() >=1){
                        for(TableStat.Name tableName:tableMap.keySet()){
                            TableStat tableStat = tableMap.get(tableName);
                            // 如果 这张表  insert create update
                            if(tableStat.getInsertCount() > 0 || tableStat.getCreateCount() >0 || tableStat.getUpdateCount()>0||tableStat.getAlterCount()>0 ){
                                // 如果表名中不存在项目，则需要加上项目名 一般是生产环境
                                String tableNameEn = tableName.getName().toLowerCase();
                                if(!tableNameEn.contains(".")){
                                    tableNameEn = projectName+"."+tableNameEn;
                                }
                                outTableNameList.add(tableNameEn.toLowerCase());
                            }
                            if(tableStat.getMergeCount()>0||tableStat.getSelectCount()>0||tableStat.getReferencedCount()>0){
                                String tableNameEn = tableName.getName().toLowerCase();
                                if(!tableNameEn.contains(".")){
                                    tableNameEn = projectName+"."+tableNameEn;
                                }
                                inTableNameList.add(tableNameEn.toLowerCase());
                            }
                        }
                        if(inTableNameList.size() >0){
                            inTableNameList.forEach( inTableName -> {
                                if(outTableNameList.size() >0){
                                    outTableNameList.forEach(outTableName -> {
                                        if (!inTableName.equalsIgnoreCase(outTableName)) {
                                            ModelLinkData modelLinkData = new ModelLinkData();
                                            modelLinkData.setParentnode(nodeId + "_" + inTableName);
                                            modelLinkData.setChildnode(nodeId + "_" + outTableName);
                                            list.add(modelLinkData);
                                        }
                                    });
                                }
                            });
                        }else{
                            List<ModelLinkData> tableBloodlineList = TableBloodlineUtil.getTableBloodlineList(sqlStatement.toLowerCaseString(),nodeId,projectName);
                            list.addAll(tableBloodlineList);
                        }
                        inTableNameList.clear();
                        outTableNameList.clear();
                    }

                }catch (Exception e){
                    logger.info("通过druid解析出错，使用字符串匹配解析");
                    SQLStatement sqlStatement = statementList.get(i);
                    List<ModelLinkData> tableBloodlineList = TableBloodlineUtil.getTableBloodlineList(sqlStatement.toLowerCaseString(),nodeId,projectName);
                    list.addAll(tableBloodlineList);
                    logger.error("解析sql里面的表信息报错"+ExceptionUtil.getExceptionTrace(e));
                }
            }
        }catch (Exception e){
            logger.error("解析sql里面的表信息报错"+ExceptionUtil.getExceptionTrace(e));
        }
        logger.info("解析出来的表关系为："+ JSONObject.toJSONString(list));
        return  list;
    }

    /**
     * 只是解析出节点里面有什么表信息 包含表关系的解析不使用该函数
     *  @TODO 还有23 同步任务的表名解析没有做，等结束之后再做这个
     * @param sql
     */
    public static List<String> getNodeContineTables(String sql,String projectNameEn,String nodeType){
        List<String> list = new ArrayList<>();
        try{
            if(sql == null ||StringUtils.isEmpty(sql)){
                throw new Exception("sql语句为空，不能解析数据");
            }
            if(nodeType.equalsIgnoreCase("23")){
                ModelLinkData tableLink = TableBloodlineUtil.getSynchronousTask(sql.toLowerCase(),projectNameEn);
                if(tableLink!= null &&!StringUtils.isEmpty(tableLink.getParentnode()) && !tableLink.getParentnode().equals(".")){
                    list.add(tableLink.getParentnode());
                }
                if(tableLink!= null &&!StringUtils.isEmpty(tableLink.getChildnode()) && !tableLink.getChildnode().equals(".")){
                    list.add(tableLink.getChildnode());
                }
                return list;
            }
            sql = sql.toLowerCase();
            sql = sql.replaceAll("\\$\\{","").replaceAll("\\}","");
            String dbType = JdbcConstants.ODPS;
            String result = SQLUtils.format(sql,dbType);
            List<SQLStatement> statementList = SQLUtils.parseStatements(result,dbType);
            for(int i =0; i<statementList.size(); i++){
                try{
                    SQLStatement sqlStatement = statementList.get(i);
                    OdpsSchemaStatVisitor visitor = new OdpsSchemaStatVisitor();
                    sqlStatement.accept(visitor);
                    Map<TableStat.Name, TableStat> tableMap = visitor.getTables();
                    for(TableStat.Name tableName:tableMap.keySet()){
                        String tableNameEn = tableName.getName().toLowerCase();
                        if(!tableNameEn.contains(".")){
                            tableNameEn = projectNameEn+"."+tableNameEn;
                        }
                        list.add(tableNameEn.toLowerCase());
                    }
                }catch (Exception e){
                    logger.info("通过druid解析出错，使用字符串匹配解析");
                    SQLStatement sqlStatement = statementList.get(i);
                    list.addAll(TableBloodlineUtil.getContainTable(sqlStatement.toLowerCaseString()));
                    logger.error("解析sql里面的表信息报错"+ExceptionUtil.getExceptionTrace(e));
                }
            }
        }catch (Exception e){
            logger.error("解析sql里面的表信息报错"+ExceptionUtil.getExceptionTrace(e));
        }
        logger.info("解析出来的表名为"+JSONObject.toJSONString(list));
        return  list;
    }



    public static List<String> getContainTable(String sql){
        List<String> tableNameList = new ArrayList<>();
        try{
            String sqlInfo = removeAnnotation(sql.replaceAll("\n", " ").replaceAll("\t", " ").replaceAll("  ", " ").toLowerCase());
            String inTableName = "";
            String outTableName = "";
            try {
                inTableName = getInTable(sqlInfo);
                outTableName = getOutTable(sqlInfo);
            }
            catch (Exception localException2)
            {
            }
            String[] inTableNames = null;
            String[] outputTableNames = null;
            if(!StringUtils.isEmpty(inTableName)){
                inTableNames = inTableName.split("\\,");
                tableNameList.addAll(Arrays.asList(inTableNames));
            }
            if(!StringUtils.isEmpty(outTableName)){
                outputTableNames = outTableName.split("\\,");
                tableNameList.addAll(Arrays.asList(outputTableNames));
            }
        }catch (Exception e){
            logger.error("解析sql报错"+ ExceptionUtil.getExceptionTrace(e));
        }
        return tableNameList;
    }

    public static List<ModelLinkData> getTableBloodlineList(String sql,String nodeId,String projectName){
        List<ModelLinkData> list = new ArrayList<>();
        try{
            String sqlInfo = removeAnnotation(sql.replaceAll("\n", " ").replaceAll("\t", " ").replaceAll("  ", " ").toLowerCase());
            String inTableName = "";
            String outTableName = "";
            try {
                inTableName = getInTable(sqlInfo);
                outTableName = getOutTable(sqlInfo);
            }
            catch (Exception localException2)
            {
            }
            String[] inTableNames = null;
            String[] outputTableNames = null;
            if(!StringUtils.isEmpty(inTableName)){
                inTableNames = inTableName.split("\\,");

            }
            if(!StringUtils.isEmpty(outTableName)){
                outputTableNames = outTableName.split("\\,");
            }
            if(inTableNames != null){
                for(String inTableNameStr:inTableNames){
                    if(!StringUtils.containsIgnoreCase(inTableNameStr,".")){
                        inTableNameStr = projectName+"."+inTableNameStr;
                    }
                    if(outputTableNames != null){
                        for(String outTableNameStr:outputTableNames){
                            if(!StringUtils.containsIgnoreCase(outTableNameStr,".")){
                                outTableNameStr = projectName+"."+outTableNameStr;
                            }
                            ModelLinkData modelLinkData = new ModelLinkData();
                            modelLinkData.setParentnode(nodeId+"_"+inTableNameStr);
                            modelLinkData.setChildnode(nodeId+"_"+outTableNameStr);
                            list.add(modelLinkData);
                        }
                    }else{
                        ModelLinkData modelLinkData = new ModelLinkData();
                        modelLinkData.setParentnode(nodeId+"_"+inTableNameStr);
                        modelLinkData.setChildnode("");
                        list.add(modelLinkData);
                    }
                }
            }else{
                if(outputTableNames != null){
                    for(String outTableNameStr:outputTableNames){
                        if(!StringUtils.containsIgnoreCase(outTableNameStr,".")){
                            outTableNameStr = projectName+"."+outTableNameStr;
                        }
                        ModelLinkData modelLinkData = new ModelLinkData();
                        modelLinkData.setParentnode("");
                        modelLinkData.setChildnode(nodeId+"_"+outTableNameStr);
                        list.add(modelLinkData);
                    }
                }
            }
        }catch (Exception e){
            logger.error(""+ExceptionUtil.getExceptionTrace(e));
        }
        return list;
    }

    public static String getOutTable(String sql)
    {
        Map<String,String> map = new HashMap();
        getOutTableByInsert(sql, map);
        getOutTableByCreateAs(sql, map);

        StringBuilder sb = new StringBuilder();
        for (String key : map.keySet()) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(key);
        }
        return sb.toString();
    }

    public static void getOutTableByCreateAs(String sql, Map<String, String> map)
    {
        if ((sql.indexOf("create table ") != -1) && (sql.indexOf(" as ") != -1)) {
            sql = sql.substring(sql.indexOf("create table "));
            if (sql.indexOf(" as ") != -1) {
                String insertSql = sql.substring(0, sql.indexOf(" as ") + " as ".length());
                boolean inde = false;
                if (insertSql.indexOf(" lifecycle ") != -1) {
                    inde = true;
                }
                if (insertSql.length() < 100) {
                    String table = insertSql.replaceAll(" ", "").replaceAll("createtableifnotexists", "").replace("createtableifexists", "").replaceAll("createtable", "");
                    table = table.substring(0, table.length() - 2);

                    sql = sql.substring(sql.indexOf(insertSql) + insertSql.length());
                    if (table.indexOf(")") != -1) {
                        table = table.replaceAll("\\)", "");
                    }
                    if (table.indexOf(";") != -1) {
                        table = table.replaceAll(";", "");
                    }
                    if (inde) {
                        try {
                            String[] life = table.split("lifecycle");
                            StringBuilder tmpTable = new StringBuilder();
                            for (int i = 0; i < life.length - 1; i++) {
                                if (i != 0) {
                                    tmpTable.append("lifecycle");
                                }
                                tmpTable.append(life[i]);
                            }
                            table = tmpTable.toString();
                        }
                        catch (Exception localException)
                        {
                        }
                    }

                    getOutTableByCreateAs(sql, map);
                    map.put(table, table);
                    getOutTableByInsert(sql, map);
                } else {
                    sql = sql.substring("create ".length());
                    getOutTableByCreateAs(sql, map);
                    getOutTableByInsert(sql, map);
                }
            }
        }
        else {
            return;
        }
    }


    public static void getOutTableByInsert(String sql, Map<String, String> map)
    {
        if (sql.indexOf("insert ") != -1) {
            sql = sql.substring(sql.indexOf("insert "));
            String insertSql = sql.substring(0, sql.indexOf(" table ") + " table ".length());
            if (("insertoverwritetable".equals(insertSql.replaceAll(" ", ""))) || ("insertintotable".equals(insertSql.replaceAll(" ", "")))) {
                sql = sql.substring(sql.indexOf(" table ") + " table ".length());
                sql = removeTheBlankSpaceByStart(sql);
                String table = sql.substring(0, sql.indexOf(" "));
                sql = sql.substring(table.length());
                if (table.indexOf(")") != -1) {
                    table = table.replaceAll("\\)", "");
                }
                if (table.indexOf(";") != -1) {
                    table = table.replaceAll(";", "");
                }
                map.put(table, table);

                getOutTableByInsert(sql, map);
            } else {
                sql = sql.substring("insert ".length());
                getOutTableByInsert(sql, map);
            }
        } else {
            return;
        }
    }

    public static String removeAnnotation(String sql)
    {
        StringBuilder sb = new StringBuilder();
        try {
            if (sql.indexOf("--") != -1) {
                String[] sql_pass = sql.split("\n");
                for (String s : sql_pass) {
                    s = removeTheBlankSpaceByStart(s);
                    Pattern zhengce = Pattern.compile("--.+?\\';");
                    Matcher matcher = zhengce.matcher(s);
                    if (matcher.find()) {
                        continue;
                    }
                    s = removeAnnotations(s);

                    if (s.startsWith("--")) {
                        continue;
                    }
                    sb.append(s + " ");
                }
            }
            else {
                return sql;
            }
        } catch (Exception e) {
            e.getMessage();
        }

        return sb.toString();
    }

    public static String getInTable(String sql)
            throws Exception
    {
        Map<String,String> map = new HashMap();
        getInTableByFrom(sql, map);
        getInTableByJson(sql, map);
        StringBuilder sb = new StringBuilder();
        for (String key : map.keySet()) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(key);
        }
        return sb.toString();
    }
    public static String removeTheBlankSpaceByStart(String sql)
    {
        if (sql.startsWith(" ")) {
            sql = sql.substring(1);
            return removeTheBlankSpaceByStart(sql);
        }if (sql.startsWith("\n")) {
        sql = sql.substring("\n".length());
        return removeTheBlankSpaceByStart(sql);
    }if (sql.startsWith("\t")) {
        sql = sql.substring("\t".length());
        return removeTheBlankSpaceByStart(sql);
    }
        return sql;
    }

    public static String removeAnnotations(String sql) throws Exception {
        if (sql.indexOf("--") != -1) {
            String startSql = sql.substring(0, sql.indexOf("--"));
            String tmpSql = sql.substring(sql.indexOf("--"));
            String endSql = "";
            try {
                if (tmpSql.indexOf("\n") != -1)
                    endSql = tmpSql.substring(tmpSql.indexOf("\n") + "\n".length());
            }
            catch (Exception e) {
                throw e;
            }
            return removeAnnotations(startSql + endSql);
        }
        return sql;
    }
    public static void getInTableByFrom(String sql, Map<String, String> map)
            throws Exception
    {
        if (sql.indexOf(" from ") != -1) {
            sql = sql.substring(sql.indexOf(" from ") + " from ".length());
            sql = removeTheBlankSpaceByStart(sql);
            if (sql.startsWith("(")) {
                getInTableByFrom(sql, map);
                return;
            }
            String table = "";
            try {
                if (sql.indexOf(" ") != -1)
                    table = sql.substring(0, sql.indexOf(" "));
                else if (sql.indexOf(";") != -1)
                    table = sql.substring(0, sql.indexOf(";"));
            }
            catch (Exception e)
            {
                System.out.println(sql);
                throw e;
            }
            sql = sql.substring(table.length());
            if (table.indexOf(")") != -1) {
                table = table.substring(0, table.indexOf(")"));
            }
            if (table.indexOf(";") != -1) {
                table = table.replaceAll(";", "");
            }
            map.put(table, table);

            getInTableByFrom(sql, map);
        } else if (sql.startsWith("from ")) {
            sql = sql.substring(sql.indexOf("from ") + "from ".length());
            sql = removeTheBlankSpaceByStart(sql);
            String table = "";
            try {
                if (sql.indexOf(" ") != -1)
                    table = sql.substring(0, sql.indexOf(" "));
                else if (sql.indexOf(";") != -1)
                    table = sql.substring(0, sql.indexOf(";"));
            }
            catch (Exception e)
            {
                System.out.println(sql);
                throw e;
            }
            sql = sql.substring(table.length());
            if (table.indexOf(")") != -1) {
                table = table.substring(0, table.indexOf(")"));
            }
            if (table.indexOf(";") != -1) {
                table = table.replaceAll(";", "");
            }
            map.put(table, table);

            getInTableByFrom(sql, map);
        } else {
            return;
        }
    }

    public static void getInTableByJson(String sql, Map<String, String> map)
    {
        if (sql.indexOf(" join ") != -1) {
            sql = sql.substring(sql.indexOf(" join ") + " join ".length());
            sql = removeTheBlankSpaceByStart(sql);
            if (sql.startsWith("(")) {
                getInTableByJson(sql, map);
                return;
            }
            String table = sql.substring(0, sql.indexOf(" "));
            sql = sql.substring(table.length());
            if (table.indexOf(")") != -1) {
                table = table.substring(0, table.indexOf(")"));
            }
            if (table.indexOf(";") != -1) {
                table = table.replaceAll(";", "");
            }
            map.put(table, table);

            getInTableByJson(sql, map);
        } else {
            return;
        }
    }
}
