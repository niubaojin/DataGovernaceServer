package com.synway.reconciliation.interceptor;

import com.synway.reconciliation.pojo.AuthorizedUser;
import com.synway.reconciliation.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * mybatis拦截器的相关工具类
 *
 * @author
 */
@Slf4j
public class SqlAuthorParse {

    private static final String PATTERN_SQL_BZGL = "  (SELECT l11.* FROM {0} l11 inner join " +
            "(select id from USER_AUTHORITY where userid = {1} and upper(modulecode) = ''BZGL'') r11 on upper(l11.{2}) = upper(r11.id) )  ";

    /**
     * 解析旧的sql 获取到新的sql信息
     *
     * @param oldSql        旧的sql信息
     * @param authorControl dao层函数注解的方法
     * @param userInfo      用户的相关信息，主要用到userId
     * @return
     */
    public static String getNewSql(String oldSql, AuthorControl authorControl, AuthorizedUser userInfo) {
        try {
            if (null == userInfo || userInfo.getIsAdmin() || StringUtils.isBlank(oldSql)) {
                return oldSql;
            }
            String[] tableNames = authorControl.tableNames();
            String[] columnNames = authorControl.columnNames();
            if (oldSql.indexOf("synlte") != -1) {
                oldSql = getStandardNewSql(oldSql, tableNames, columnNames, userInfo);
            } else {
                oldSql = getReconciliationNewSql(oldSql, tableNames, columnNames, userInfo);
            }
        } catch (Exception e) {
            log.error("拦截sql报错：" + ExceptionUtil.getExceptionTrace(e));
        }
        return oldSql;
    }

    private static String getStandardNewSql(String oldSql, String[] tableNames, String[] columnNames, AuthorizedUser userInfo) {
        for (int i = 0; i < tableNames.length; i++) {
            String sqlStr = MessageFormat.format(PATTERN_SQL_BZGL, tableNames[i], String.valueOf(userInfo.getUserId()), columnNames[i]);
            oldSql = oldSql.replaceAll("([\\s|\\(])(?i)" + tableNames[i] + "([\\s|\\)])", "$1" + sqlStr + "$2");
        }
        return oldSql;
    }

    private static String getReconciliationNewSql(String oldSql, String[] tableNames, String[] columnNames, AuthorizedUser userInfo) {
        Matcher matcher = null;
        Pattern pattern = null;
        for (int i = 0; i < tableNames.length; i++) {
            String regEx = tableNames[i] + "[\\s\\S]*?WHERE";
            pattern = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
            matcher = pattern.matcher(oldSql);
            if (matcher.find()) {
                String matchStr = matcher.group();
                String sqlStr = matchStr + " "+columnNames[i]+"='" + userInfo.getUserId() + "' AND ";
                oldSql = matcher.replaceAll(sqlStr);
            } else {
                String reg = tableNames[i];
                pattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
                matcher = pattern.matcher(oldSql);
                if (matcher.find()) {
                    String sqlStr = tableNames[i] + " WHERE "+columnNames[i]+"='" + userInfo.getUserId() + "'";
                    oldSql = matcher.replaceAll(sqlStr);
                }
            }
        }
        return oldSql;
    }

}
