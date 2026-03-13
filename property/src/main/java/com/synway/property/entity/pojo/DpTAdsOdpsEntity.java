package com.synway.property.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * ADS/ODPS/OSS/TRS 存储信息
 *
 * @author DataGovernaceServer
 * @date 2025-11-21
 */
@Data
@TableName("DP_T_ADS_ODPS")
public class DpTAdsOdpsEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableField("ID")
    private String id;

    /**
     * 4 种名字：1.ADS,2.ODPS,3.OSS,4.TRS
     */
    @TableField("NAME")
    private String name;

    /**
     * 已使用容量
     */
    @TableField("USED_CAPACITY")
    private Long usedCapacity;

    /**
     * 裸容量
     */
    @TableField("BARE_CAPACITY")
    private Long bareCapacity;

    /**
     * 入库时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("DT")
    private Date dt;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("UT")
    private Date ut;

    /**
     * 已使用容量的百分比值，例如 20% 记录值为 20
     */
    @TableField("RATE")
    private BigDecimal rate;

    /**
     * 文件数，主要针对 oss
     */
    @TableField("FILE_COUNT")
    private String fileCount;
}
