package com.synway.property.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * ODPS SQL 统计
 *
 * @author DataGovernaceServer
 * @date 2025-11-21
 */
@Data
@TableName("DP_UT_ODPSSQLCOUNT")
public class DpUtOdpsqlcountEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 项目名
     */
    @TableField("PROJECT")
    private String project;

    /**
     * 表名
     */
    @TableField("TABLENAME")
    private String tableName;

    /**
     * 某一个类型的使用次数
     */
    @TableField("TABLE_USE_COUNT")
    private Integer tableUseCount;

    /**
     * 值包含：1.SELECT 2.INSERT 3.COUNT
     */
    @TableField("SQLTYPE")
    private Integer sqlType;

    /**
     * 该条数据的插入时间，时间格式 yyyy-MM-dd
     */
    @TableField("DT")
    private String dt;

    /**
     * 外键，关联 DP_DBOPERATE_MONITOR 的主键
     */
    @TableField("ID")
    private String id;
}
