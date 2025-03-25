package com.synway.reconciliation.common;

/**
 * 根据现有日志数据分析各类错误，进行细化
 *
 * @author
 */
public enum ErrorCode {

    DB_CONNECT_ERROR("E1001", "数据库连接失败"),
    DB_RELEASE_ERROR("E1002", "数据库资源释放失败"),
    DB_ROLLBACK_ERROR("E1003", "数据库回滚失败"),
    DATA_INSERT_ERROR("E1004", "表数据写入失败"),
    QUERY_SQL_ERROR("E1005", "SQL语句执行出错"),
    DATA_DELETE_ERROR("E1005", "表数据删除失败"),

    FILE_GET_ERROR("E2001", "文件获取失败"),
    FACTORY_GET_ERROR("E2002", "SqlSessionFactory获取失败"),
    EXPORT_EXCEL_ERROR("E2003", "导出表格失败");


    private final String code;

    private final String description;

    private ErrorCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return String.format("errorID: %s, 错误信息: %s. ", this.code,
                this.description);
    }

}
