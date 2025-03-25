package com.synway.datarelation.util;

import java.util.regex.Pattern;

/**
 *  druid里面sql解析信息
 * @author wangdongwei
 */
public class SqlParseMatch {
    private final static Pattern p1 = Pattern.compile("(lifecycle\\s+\\d+)|(create\\s+table\\s+.*?like.*?;)|(-*\\s*read.*?[;\\n])|(-*\\s*$)|(-*\\s*read\\s+\\w+)" +
            "|(-*\\s*list\\s+functions[;\\n])|(row\\s*format.*?[\n])|(lines\\s*terminated.*?[\n])|(stored\\s*as\\s*textfile)");

    private final static Pattern p2 = Pattern.compile("\\s+\\w*[.]*\\w+\\((\\s*\\w+\\s*,)*\\s*\\w+\\s*\\)\\s+as\\s+\\((\\s*\\w+\\s*,)*\\s*\\w+\\s*\\)");

    private final static Pattern p3 =Pattern.compile("--.*?[\\n]");

    private final static Pattern p4 = Pattern.compile("==");

    private final static Pattern p5 = Pattern.compile("create\\s+table\\s+if\\s+not\\s+exists\\s+");

    public static  String sqlParse(String sql,String dataType){
        if("hive".equalsIgnoreCase(dataType)){
            sql = sql.toLowerCase().replaceAll("\\$\\{","").replaceAll("\\}","")
                    .replaceAll(":",".").replaceAll("default.","");
            sql = p1.matcher(sql).replaceAll("");
            // udtf 无法解析 将这个去除掉
            //            sql = Pattern.compile("\\s+\\w+\\((\\s*\\w+\\s*,)*\\s*\\w+\\s*\\)\\s+as\\s+\\((\\s*\\w+\\s*,)*\\s*\\w+\\s*\\)").matcher(sql).replaceAll(" * ");
//            sql = Pattern.compile("\\s+\\w*[.]*\\w+\\((\\s*(\\w+|\\S+)\\s*,)*\\s*(\\w+|\\S+)\\s*\\)\\s+as\\s+\\((\\s*(\\w+|\\S+)\\s*,)*\\s*(\\w+|\\S+)\\s*\\)")
//                    .matcher(sql).replaceAll("  *  ");
            sql = p2.matcher(sql).replaceAll("  *  ");
            // 去除sql语句中最后的  --
            sql = p3.matcher(sql.trim()+"\n").replaceAll("");
            // 还存在  on == 的情况
            sql = p4.matcher(sql).replaceAll("=");
            // create table if not exis
            sql = p5.matcher(sql).replaceAll("create table ");
        }else if("odps".equalsIgnoreCase(dataType)){
            sql = sql.toLowerCase();
        }
        // sql中存在转义符情况 需要解决掉这个
        sql = sql.replace("&lt;","<").replace("&lt;=","<=")
                .replace("&gt;",">").replace("&gt;=",">=")
                .replace("&amp;","&").replace("&apos;","'")
                .replace("&quot;","\"");

        return sql;
    }
}
