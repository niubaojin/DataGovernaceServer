package com.synway.datastandardmanager.util;

import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.util.deparser.StatementDeParser;


@Slf4j
public class SqlFormatterUtil {

    public static String formatSql(String sql) {
        try {
            log.info(">>>>>>开始格式化sql语句...");
            Statement stmt = CCJSqlParserUtil.parse(sql);
            StringBuilder formattedSql = new StringBuilder();
            StatementDeParser deParser = new StatementDeParser(formattedSql);
            stmt.accept(deParser);
            return formattedSql.toString();
        }catch (Exception e){
            log.error(">>>>>>格式化sql出错：", e);
            return sql;
        }
    }

}
