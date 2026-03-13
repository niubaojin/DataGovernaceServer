package com.synway.property.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 存储数据历程的相关信息
 *
 * @author DataGovernaceServer
 * @date 2025-11-21
 */
@Data
@TableName("DP_DATAPROCESS")
public class DpDataProcessEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 地区行政编号（参考标准 附录一）
     */
    @TableField("AREA_ID")
    private String areaId;

    /**
     * 部门名称
     */
    @TableField("DEPT")
    private String dept;

    /**
     * 操作人名称
     */
    @TableField("OPERATOR")
    private String operator;

    /**
     * 警号
     */
    @TableField("POLICENO")
    private String policeNo;

    /**
     * 系统代码
     */
    @TableField("APP_ID")
    private String appId;

    /**
     * 模块代码 (大写) 仓库 (SJCK) 探查 (SJTC) 接入 (SJJR) 标准 (BZGL) 质检 (ZLJC) 对账 (SJDZ) 资产 (ZCGL) 血缘 (SJXY) 家产登记 (JCDJ) 运维管理 (YWGL)
     */
    @TableField("MODULE_ID")
    private String moduleId;

    /**
     * 操作时间 YYYY-MM-DD HH:mm:ss
     */
    @TableField("OPERATE_TIME")
    private String operateTime;

    /**
     * IP 地址/操作设备地址
     */
    @TableField("IP")
    private String ip;

    /**
     * 操作类型代码 (大写)探查  业务探查：SJTC001 接入任务  新建：SJJR001 删除：SJJR001 修改：SJJR001 标准  建表：BZGL001 登记：BZGL002 质检任务  新建：ZLJC001 删除：ZLJC001 修改：ZLJC001
     */
    @TableField("LOG_TYPE")
    private String logType;

    /**
     * 日志摘要   操作行为的简要说明 不同模块对应的格式不同
     */
    @TableField("DIGEST")
    private String digest;

    /**
     * 数据库类型 odps/ads/hive/hbase
     */
    @TableField("DATA_BASE_TYPE")
    private String dataBaseType;

    /**
     * 包括  项目名。表名
     */
    @TableField("TABLE_NAME_EN")
    private String tableNameEn;

    /**
     * 表协议 ID
     */
    @TableField("TABLE_ID")
    private String tableId;

    /**
     * 该条记录的插入时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("INSERT_TIME")
    private Date insertTime;

    /**
     * 任务名 只有数据接入，质量检测才有这个
     */
    @TableField("TASK_NAME")
    private String taskName;

    /**
     * 唯一主键
     */
    @TableField("ID")
    private Long id;
}
