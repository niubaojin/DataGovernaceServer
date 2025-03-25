package com.synway.reconciliation.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 对账信息响应结果
 * @author Administrator
 */
@Data
public class ReconInfoResponse {

    /**
     * 数据资源名称
     */
    @JsonProperty("SJZYMC")
    @JSONField(name = "SJZYMC")
    private String SJZYMC;

    /**
     * 标准数据项集编码
     */
    @JsonProperty("BZSJXJBM")
    @JSONField(name = "BZSJXJBM")
    private String BZSJXJBM;

    /**
     * 数据接入方_对账单编号
     */
    @JsonProperty("SJJRF_DZDBH")
    @JSONField(name = "SJJRF_DZDBH")
    private String SJJRF_DZDBH;

    /**
     * 数据条数
     */
    @JsonProperty("SJTS")
    @JSONField(name = "SJTS")
    private int SJTS;

    /**
     * 生成时间_日期时间
     */
    @JsonProperty("SCSJ_RQSJ")
    @JSONField(name = "SCSJ_RQSJ")
    private String SCSJ_RQSJ;

    /**
     * 对账单状态代码
     */
    @JsonProperty("DZDZTDM")
    @JSONField(name = "DZDZTDM")
    private String DZDZTDM;

    /**
     * 对账时间_日期时间
     */
    @JsonProperty("DZSJ_RQSJ")
    @JSONField(name = "DZSJ_RQSJ")
    private String DZSJ_RQSJ;

    /**
     * 消息任务编号
     */
    @JsonProperty("XXRWBH")
    @JSONField(name = "XXRWBH")
    private String XXRWBH;

    /**
     * 数据_电子文件名称(数据包名)
     */
    @JsonProperty("SJ_DZWJMC")
    @JSONField(name = "SJ_DZWJMC")
    private String SJ_DZWJMC;
}
