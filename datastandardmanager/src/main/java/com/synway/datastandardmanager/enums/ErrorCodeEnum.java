package com.synway.datastandardmanager.enums;


/**
 * 根据现有日志数据分析各类错误，进行细化
 *
 * @author
 */
public enum ErrorCodeEnum implements com.synway.common.spi.ErrorCode {
    DB_CONNECT_ERROR("E1001", "数据库连接失败"),
    DB_RELEASE_ERROR("E1002", "数据库资源释放失败"),
    DB_ROLLBACK_ERROR("E1003", "数据库回滚失败"),
    DATA_INSERT_ERROR("E1004", "表数据写入失败"),
    QUERY_SQL_ERROR("E1005", "SQL语句执行出错"),
    FILE_GET_ERROR("E2001", "文件获取失败"),
    FACTORY_GET_ERROR("E2002", "SqlSessionFactory获取失败"),
    EXPORT_EXCEL_ERROR("E2003", "导出表格失败"),
    CHECK_PARAMETER_ERROR("E2004","参数检查异常"),
    CHECK_UNION_ERROR("E2005", "不是唯一值"),
    CHECK_ERROR("E2006", ""),
    CREATE_TABLE_ERROR("E2007", "建表错误"),
    UNION_ERROR("E2008", "必填值不能为空"),
    DATA_IS_NULL("E3001","数据为空"),
    INSERT_ERROR("E3002","数据插入失败"),
    DELETE_ERROR("E3003","数据删除失败"),
    DOWNLOAD_ERROR("E4001","导出文件失败");

    private final String code;

    private final String description;

    private ErrorCodeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return String.format("errorID: %s, 错误信息: %s. ", this.code,
                this.description);
    }

}
