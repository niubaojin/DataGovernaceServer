package com.synway.dataoperations.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 数据积压监控表（kafka）
 */
@Data
@TableName("DO_DATA_PILED_MONITOR")
public class DoDataPiledMonitorEntity {
    //数据名称
    @TableField("DATA_NAME")
    private String dataName;

    // 数据中心ID
    @TableField(exist = false)
    private String dataCenterID;
    // 数据中心名称
    @TableField(exist = false)
    private String dataCenterName;
    // 数据仓库ID
    @TableField(exist = false)
    private String dataWarehouseID;

    //数据源名称
    @TableField("DATA_WAREHOUSENAME")
    private String dataWarehouseName;

    //消费主题
    @TableField("CONSUM_TOPIC")
    private String consumTopic;

    //消费组
    @TableField("CONSUM_GROUP")
    private String consumGroup;

    // 偏移量告警阈值（一般）
    @TableField(exist = false)
    private String yiban;
    // 偏移量告警阈值（告警）
    @TableField(exist = false)
    private String jinggao;
    // 偏移量告警阈值（严重）
    @TableField(exist = false)
    private String yanzhong;
    // 是否启用
    @TableField(exist = false)
    private String isEnable;
    // 备注
    @TableField(exist = false)
    private String remark;
    // 数据源连接信息
    @TableField(exist = false)
    private String connectInfo;

    //消费偏移量
    @TableField("OFFSET")
    private long offset;

    //最大偏移量
    @TableField("LOGSIZE")
    private long logSize;

    //剩余偏移量
    @TableField("`LAG`")
    private long lag;

    //积压率
    @TableField("PILED_RATE")
    private String piledRate;

    //推送时点
    @TableField("PUSH_HOUR")
    private String pushHour;

    //数据入库时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    @TableField("INSERTTIME")
    private Date insertTime;

    //数据种类：1.正常数据种类，2.堆积数据种类，3.异常数据种类
    @TableField("DATA_TYPE")
    private String dataType;
}
