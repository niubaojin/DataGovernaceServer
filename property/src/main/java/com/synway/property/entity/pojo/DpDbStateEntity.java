package com.synway.property.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据库概况
 *
 * @author DataGovernaceServer
 * @date 2025-11-21
 */
@Data
@TableName("DP_DB_STATE")
public class DpDbStateEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 名字
     */
    @TableField("NAME")
    private String name;

    /**
     * 已使用物理存储 (GB)
     */
    @TableField("USED_CAPACITY")
    private String usedCapacity;

    /**
     * 总物理存储 (GB)
     */
    @TableField("BARE_CAPACITY")
    private String bareCapacity;

    /**
     * 记录数
     */
    @TableField("TABLE_COUNT")
    private String tableCount;

    /**
     * 活表率
     */
    @TableField("LIVE_TABLE_ROTE")
    private String liveTableRote;

    /**
     * 修改时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("MODIFY_TIME")
    private Date modifyTime;

    /**
     * 表数量
     */
    @TableField("TABLE_SUM")
    private String tableSum;
}
