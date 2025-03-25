package com.synway.property.util;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdsOrMysqlClient {
    private static Logger logger = LoggerFactory.getLogger(AdsOrMysqlClient.class);

    public DruidPooledConnection getMysqlConn(DruidDataSource druidDataSource){
        try {
            return druidDataSource.getConnection();
        }catch (Exception e){
            logger.error("获取connection失败：\n" + ExceptionUtil.getExceptionTrace(e));
            return null;
        }
    }

    public JSONArray execBySql(DruidDataSource mysqlDataSource,String sql) throws SQLException {
        // TODO Auto-generated method stub
        DruidPooledConnection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Object[]> list = new ArrayList<Object[]>();
        JSONArray jsonArray = new JSONArray();
        try {
            conn = mysqlDataSource.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            int colCount = rs.getMetaData().getColumnCount();
            while (colCount > 0 && rs.next()) {
//                Object[] obj = new Object[colCount];
//                for (int i = 0; i < obj.length; i++) {
//                    if (null == rs.getObject(i + 1)) {
//                        obj[i] = "";
//                    } else {
//                        obj[i] = rs.getObject(i + 1);
//                    }
//                }
//                list.add(obj);

                JSONObject jsonObject = new JSONObject();
                for (int i = 1; i <= colCount; i++){
                    String columnName = rs.getMetaData().getColumnLabel(i);
                    jsonObject.put(columnName, rs.getString(columnName));
                }
                jsonArray.add(jsonObject);
            }
        } catch (SQLException e) {
            logger.error("执行sql出错，错误信息:\n" + ExceptionUtil.getExceptionTrace(e));
        } finally {
            closeAll(ps, conn, rs,null);
        }
        return jsonArray;
    }

    public void closeAll(PreparedStatement ps, Connection conn, ResultSet rs, Statement st) {
        closeResource(ps);
        closeResource(rs);
        closeResource(conn);
        closeResource(st);
    }

    private void closeResource(AutoCloseable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (Exception e) {
                logger.error(ExceptionUtil.getExceptionTrace(e));
            }
        }
    }

}
