package com.synway.property.interceptor;

import com.synway.property.pojo.LoginUser;
import com.synway.property.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;

/**
 * mybatis拦截器的相关工具类
 * @author wangdongwei
 * @date 2021/5/27 11:04
 */
@Slf4j
public class SqlAuthorParse {


    private static final String PATTERN_SQL = "  (SELECT l11.* FROM {0} l11 inner join " +
            "(select id from USER_AUTHORITY where (0, organid) in {1}  and upper(modulecode) = ''SJCK'') r11 on upper(l11.{2}) = upper(r11.id) )  ";

    private static final String PATTERN_SQL1 = "  (SELECT l11.* FROM {0} l11 inner join " +
            "(select id from USER_AUTHORITY where (0, organid) in {1}  and upper(modulecode) = ''BZGL'') r11 on upper(l11.{2}) = upper(r11.id) )  ";

    /**
     *  解析旧的sql 获取到新的sql信息
     * @param oldSql  旧的sql信息
     * @param authorControl  dao层函数注解的方法
     * @param object 用户的相关信息，主要用到userId
     * @param methodName 方法名
     * @return
     */
    public static String getNewSql(String oldSql, AuthorControl authorControl
            , LoginUser object, String methodName){
        try{
            if(object == null || StringUtils.isBlank(oldSql)){
                return oldSql;
            }
            String[] tableNames = authorControl.tableNames();
            String[] columnNames = authorControl.columnNames();
            if(tableNames.length != columnNames.length){
                log.error("方法"+methodName+"配置的AuthorControl注解中标名和字段没有对应上");
                return oldSql;
            }
            for(int i = 0; i < tableNames.length;i++){
                // 部分页面功能采用标准数据的权限控制
                if (methodName.contains("getObjectInfos") || methodName.contains("getDataOrganizationTable") || methodName.contains("getCIAD")){
                    String sqlStr = MessageFormat.format(PATTERN_SQL1,tableNames[i],object.getFormatDataAuthIds(),columnNames[i]);
                    oldSql = oldSql.replaceAll("([\\s|\\(])(?i)"+tableNames[i]+"([\\s|\\)])*","$1"+sqlStr+"$2");
                }else {
                    String sqlStr = MessageFormat.format(PATTERN_SQL,tableNames[i],object.getFormatDataAuthIds(),columnNames[i]);
                    oldSql = oldSql.replaceAll("([\\s|\\(])(?i)"+tableNames[i]+"([\\s|\\)])*","$1"+sqlStr+"$2");
                }
            }
        }catch (Exception e){
            log.error("拦截sql报错："+ ExceptionUtil.getExceptionTrace(e));
        }
        return oldSql;
    }

}
