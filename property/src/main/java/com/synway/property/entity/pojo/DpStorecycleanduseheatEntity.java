package com.synway.property.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 存储周期和使用热度
 *
 * @author DataGovernaceServer
 * @date 2025-11-21
 */
@Data
@TableName("DP_STORECYCLEANDUSEHEAT")
public class DpStorecycleanduseheatEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableField("ID")
    private String id;

    /**
     * LASTDATE
     */
    @TableField("LASTDATE")
    private String lastDate;

    /**
     * TABLETYPE
     */
    @TableField("TABLETYPE")
    private String tableType;

    /**
     * CLASSNAME
     */
    @TableField("CLASSNAME")
    private String className;

    /**
     * SUBCLASS
     */
    @TableField("SUBCLASS")
    private String subClass;

    /**
     * TABLENAMEEN
     */
    @TableField("TABLENAMEEN")
    private String tableNameEn;

    /**
     * TABLENAMEZH
     */
    @TableField("TABLENAMEZH")
    private String tableNameZh;

    /**
     * LIFECYCLE
     */
    @TableField("LIFECYCLE")
    private Integer lifecycle;

    /**
     * TABLEALLCOUNT
     */
    @TableField("TABLEALLCOUNT")
    private Long tableAllCount;

    /**
     * TABLESIZE
     */
    @TableField("TABLESIZE")
    private Long tableSize;

    /**
     * TABLEUSECOUNT
     */
    @TableField("TABLEUSECOUNT")
    private Long tableUseCount;

    /**
     * TABLEUSECOUNTOFDAY
     */
    @TableField("TABLEUSECOUNTOFDAY")
    private Long tableUseCountOfDay;

    /**
     * TABLEUSECOUNTOFWEEK
     */
    @TableField("TABLEUSECOUNTOFWEEK")
    private Long tableUseCountOfWeek;

    /**
     * TABLEUSECOUNTOFMONTH
     */
    @TableField("TABLEUSECOUNTOFMONTH")
    private Long tableUseCountOfMonth;
}
