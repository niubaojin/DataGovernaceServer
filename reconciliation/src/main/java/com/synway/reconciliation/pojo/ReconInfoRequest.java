package com.synway.reconciliation.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * 对账信息请求
 * @author Administrator
 */
@Data
public class ReconInfoRequest {

    /**
     * 页数
     */
    private int pageNum;

    /**
     * 每一页大小
     */
    private int pageSize;

    /**
     * 账单时间
     */
    @JsonProperty("SCSJ_RQSJ")
    @JSONField(name = "SCSJ_RQSJ")
    private String SCSJ_RQSJ;

    /**
     * 账单编号
     */
    @JsonProperty("SJJRF_DZDBH")
    @JSONField(name = "SJJRF_DZDBH")
    private String SJJRF_DZDBH;

    /**
     * 对账方法
     */
    @JsonProperty("DZFFDM")
    @JSONField(name = "DZFFDM")
    private int DZFFDM;

    /**
     * 数据资源编号
     */
    @JsonProperty("BZSJXJBM")
    @JSONField(name = "BZSJXJBM")
    private String BZSJXJBM;

    /**
     * 数据名称
     */
    @JsonProperty("SJZYMC")
    @JSONField(name = "SJZYMC")
    private String SJZYMC;

    /**
     * 1.接入2.处理3.入库
     */
    @JsonProperty("DZZDHJ")
    @JSONField(name = "DZZDHJ")
    private int DZZDHJ;

    /**
     * 账单状态0.未对账1.对账成功2.对账失败3.已销账
     */
    @JsonProperty("DZDZTDM")
    @JSONField(name = "DZDZTDM")
    private Integer DZDZTDM;


    private int startTime;
    private int endTime;

    private int type;
    private List<String> values;
}
