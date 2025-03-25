package com.synway.reconciliation.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 接收方账单
 * @author Administrator
 */
@Data
public class DacAcceptorBill {

    /**
     * 数据接入方_对账单编号
     * acceptorBillNo
     */
    @JsonProperty("SJJRF_DZDBH")
    @JSONField(name = "SJJRF_DZDBH")
    private String SJJRF_DZDBH;

    /**
     * 数据提供方_对账单编号
     * providerBillNo
     */
    @JsonProperty("SJTGF_DZDBH")
    @JSONField(name = "SJTGF_DZDBH")
    private String SJTGF_DZDBH;

    /**
     * 数据接收位置_简要情况
     * sourceLocation
     */
    @JsonProperty("SJJSWZ_JYQK")
    @JSONField(name = "SJJSWZ_JYQK")
    private String SJJSWZ_JYQK;

    /**
     * 数据存储形式代码
     * dataSourceType
     */
    @JsonProperty("SJCCXSDM")
    @JSONField(name = "SJCCXSDM")
    private String SJCCXSDM;

    /**
     * 数据_电子文件名称
     * inceptDataId
     */
    @JsonProperty("SJ_DZWJMC")
    @JSONField(name = "SJ_DZWJMC")
    private String SJ_DZWJMC;

    /**
     * 数据资源标识符
     * 暂时没用
     */
    @JsonProperty("SJZYBSF")
    @JSONField(name = "SJZYBSF")
    private String SJZYBSF;

    /**
     * 数据资源名称
     * resourceName
     */
    @JsonProperty("SJZYMC")
    @JSONField(name = "SJZYMC")
    private String SJZYMC;

    /**
     * 标准数据项集编码
     * resourceId
     */
    @JsonProperty("BZSJXJBM")
    @JSONField(name = "BZSJXJBM")
    private String BZSJXJBM;

    /**
     * 数据条数
     * dataCount
     */
    @JsonProperty("SJTS")
    @JSONField(name = "SJTS")
    private long SJTS;

    /**
     * 数据_电子文件大小(byte)
     * dataSize
     */
    @JsonProperty("SJ_DZWJDX")
    @JSONField(name = "SJ_DZWJDX")
    private long SJ_DZWJDX;

    /**
     * 数据起始编号
     * startNo
     */
    @JsonProperty("SJQSBH")
    @JSONField(name = "SJQSBH")
    private String SJQSBH;

    /**
     * 数据结尾编号
     * endNo
     */
    @JsonProperty("SJJWBH")
    @JSONField(name = "SJJWBH")
    private String SJJWBH;

    /**
     * 数据校验值
     * dataFingerprint
     */
    @JsonProperty("SJJYZ")
    @JSONField(name = "SJJYZ")
    private String SJJYZ;

    /**
     * 数据校验算法名称
     * fingerprintType
     */
    @JsonProperty("SJJYSFMC")
    @JSONField(name = "SJJYSFMC")
    private String SJJYSFMC;

    /**
     * 上次失败_对账单编号
     * lastFailBillNo
     */
    @JsonProperty("SCSB_DZDBH")
    @JSONField(name = "SCSB_DZDBH")
    private String SCSB_DZDBH;

    /**
     * 生成时间_日期时间
     * acceptTime
     */
    @JsonProperty("SCSJ_RQSJ")
    @JSONField(name = "SCSJ_RQSJ")
        private String SCSJ_RQSJ;

    /**
     * 对账时间_日期时间
     * checkTime
     */
    @JsonProperty("DZSJ_RQSJ")
    @JSONField(name = "DZSJ_RQSJ")
    private String DZSJ_RQSJ;

    /**
     * 对账方法代码
     * checkMethod
     */
    @JsonProperty("DZFFDM")
    @JSONField(name = "DZFFDM")
    private String DZFFDM;

    /**
     * 对账单类型代码（1.提供方账单2.接入方账单）
     * billType
     */
    @JsonProperty("DZDLXDM")
    @JSONField(name = "DZDLXDM")
    private String DZDLXDM;

    /**
     * 对账单状态代码（0.未对账1.对账成功2.对账失败3.已销账）
     * billState
     */
    @JsonProperty("DZDZTDM")
    @JSONField(name = "DZDZTDM")
    private String DZDZTDM;

    /**
     * 对账结果_简要情况
     * resultDes
     */
    @JsonProperty("DZJG_JYQK")
    @JSONField(name = "DZJG_JYQK")
    private String DZJG_JYQK;

    /**
     * 数据接入方管理员_姓名
     * acceptorAdmin
     */
    @JsonProperty("SJJRFGLY_XM")
    @JSONField(name = "SJJRFGLY_XM")
    private String SJJRFGLY_XM;

    /**
     * 数据接入方管理员_联系电话
     * acceptorTel
     */
    @JsonProperty("SJJRFGLY_LXDH")
    @JSONField(name = "SJJRFGLY_LXDH")
    private String SJJRFGLY_LXDH;

    /**
     * 消息任务编号
     * inceptDataTime
     */
    @JsonProperty("XXRWBH")
    @JSONField(name = "XXRWBH")
    private String XXRWBH;

    /**
     * 任务id
     * 下发对账新增
     */
    @JsonProperty("jobId")
    @JSONField(name = "jobId")
    private String jobId;

    /**
     * 批次id
     * 下发对账新增
     */
    @JsonProperty("batchId")
    @JSONField(name = "batchId")
    private String batchId;
}
