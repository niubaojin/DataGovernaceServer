package com.synway.reconciliation.pojo.issue;



import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.converters.date.DateStringConverter;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 下发数据按天统计表(导出excle)
 * @author DZH
 */
@Data
public class IssueDayStatisticsExcel implements Serializable {

    /**
     * 主键id
     */
    @ExcelIgnore
    private Integer id;

    /**
     * 同一任务的唯一标识
     */
    @ExcelIgnore
    private String jobId;

    /**
     * 账单日期（必填）：统计账单核账日期；
     */
    @ExcelProperty(value = "账单日期")
    private String checkTime;

    /**
     * 数据中文名称
     */
    @ExcelProperty("数据名称")
    private String dataNameZh;

    /**
     * 数据表名：数据英文名称；
     */
    @ExcelProperty("数据表名")
    private String dataNameEn;

    /**
     * 是否分区表；
     */
    @ExcelProperty("表类型")
    private String isPartition;

    /**
     * 数据推送的目标地市
     */
    @ExcelProperty("数据接收方")
    private String acceptCity;

    /**
     * 数据推送位置
     */
    @ExcelProperty("数据交换位置")
    private String exchangePosition;

    /**
     * 源表数据量
     */
    @ExcelProperty("源表数据量")
    private Long sourceCount;

    /**
     * 总队读出数据量
     */
    @ExcelProperty("总队读出数据量")
    private Long providerReadCount;

    /**
     * 总队推送数据量
     */
    @ExcelProperty("总队推送数据量")
    private Long providerWriteCount;

    /**
     * 地市接收数据量
     */
    @ExcelProperty("地市接收数据量")
    private Long acceptorReadCount;

    /**
     * 地市入库数据量
     */
    @ExcelProperty("地市入库数据量")
    private Long acceptorWriteCount;

    /**
     * 目标表数据量
     */
    @ExcelProperty("目标表数据量")
    private Long destCount;

    /**
     * 历程环比
     */
    @ExcelProperty("历程环比")
    private String courseChainRatio;

    /**
     * 历史同比
     */
    @ExcelProperty("历史同比")
    private String historicalComparison;

    /**
     * 总队开始读出时间
     */
    @ExcelProperty(value = "总队开始读出时间", converter = DateStringConverter.class)
    private Date providerStartTime;

    /**
     * 总队推送完成时间
     */
    @ExcelProperty(value = "总队推送完成时间", converter = DateStringConverter.class)
    private Date providerEndTime;

    /**
     * 地市开始接收时间
     */
    @ExcelProperty(value = "地市开始接收时间", converter = DateStringConverter.class)
    private Date acceptorStartTime;

    /**
     * 地市入库完成时间
     */
    @ExcelProperty(value = "地市入库完成时间", converter = DateStringConverter.class)
    private Date acceptorEndTime;

    /**
     * 总队推送时长
     */
    @ExcelProperty("总队推送时长")
    private Long providerDuration;

    /**
     * 地市入库时长
     */
    @ExcelProperty("地市入库时长")
    private Long acceptorDuration;

    /**
     * 总时长
     */
    @ExcelProperty("总时长")
    private Long allDuration;

    /**
     * 任务状态
     */
    @ExcelProperty("任务状态")
    private String taskState;

    /**
     * 更新结果
     */
    @ExcelProperty("更新结果")
    private String updateResult;

    /**
     * 更新结果描述
     */
    @ExcelProperty("更新结果描述")
    private String updateResultReason;

    /**
     * 基线时间
     */
    @ExcelProperty("基线时间")
    private String baseTime;

    /**
     * 基线时间是否启用
     */
    @ExcelProperty("基线时间是否启用")
    private String baseTimeEnable;

    /**
     * 异常字段（用逗号分隔）
     */
    @ExcelIgnore
    private String exceptionField;
}
