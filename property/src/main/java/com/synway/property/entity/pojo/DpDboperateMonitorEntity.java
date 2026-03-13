package com.synway.property.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 数据库操作监控（数据取自阿里 m_task）
 *
 * @author DataGovernaceServer
 * @date 2025-11-21
 */
@Data
@TableName("DP_DBOPERATE_MONITOR")
public class DpDboperateMonitorEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableField("ID")
    private String id;

    /**
     * 数据库类型
     */
    @TableField("DB_TYPE")
    private String dbType;

    /**
     * 项目名
     */
    @TableField("PROJECT_NAME")
    private String projectName;

    /**
     * SQL 类型（0:未解析出来  1:select 查询   2:insert 插入   3:count 计数   4:delete 删出   5:update 修改   6:create 建表   7:drop 删表）
     */
    @TableField("SQL_TYPE")
    private Integer sqlType;

    /**
     * 执行状态 (4:Failed, 5:Terminated(成功))
     */
    @TableField("EXECUTE_STATE")
    private String executeState;

    /**
     * 开始时间（XX 时 XX 分 XX 秒）
     */
    @TableField("START_TIME")
    private String startTime;

    /**
     * 结束时间
     */
    @TableField("END_TIME")
    private String endTime;

    /**
     * 执行时长
     */
    @TableField("EXECUTE_TIME")
    private String executeTime;

    /**
     * 账户名
     */
    @TableField("INST_OWNER_NAME")
    private String instOwnerName;

    /**
     * 监控日期（年 - 月 - 日）
     */
    @TableField("MONITOR_TIME")
    private String monitorTime;

    /**
     * SQL 内容
     */
    @TableField("SQL")
    private String sql;
}
