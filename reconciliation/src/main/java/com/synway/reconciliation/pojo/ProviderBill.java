package com.synway.reconciliation.pojo;

import lombok.Data;

/**
 * @author: chenfei
 * @date: 2023/8/3 14:11
 * @param:
 * @return:
 * @describe:
 **/
@Data
public class ProviderBill {
    /**
     * 数据提供方账单号
     */
    private String providerBillNo;
    /**
     * 数据提供方编号
     */
    private String providerNo;
    /**
     * 接入方编号
     */
    private String acceptorNo;
    /**
     * 数据资源编号
     */
    private String resourceId;
    /**
     * 数据条数
     */
    private long dataCount;
    /**
     * 数据指纹
     */
    private String dataFingerprint;
    /**
     * 数据指纹类型
     */
    private String fingerprintType;
    /**
     * 数据大小
     */
    private double dataSize;
    /**
     * 数据起始编号
     */
    private String startNo;
    /**
     * 数据结束编号
     */
    private String endNo;
    /**
     * 数据来源存储位置
     */
    private String sourceLocation;
    /**
     * 账单状态（0.未对账1.对账成功2.对账失败3.已销账）
     */
    private int billState;
    /**
     * 上次失败账单号
     */
    private String lastFailBillNo;
    /**
     * 数据发送时间
     */
    private long sendTime;
    /**
     * 数据对账时间
     */
    private String checkTime;
    /**
     * 对账方法（1.及时对账，2定时对账，3盘点对账）
     */
    private int checkMethod;
    /**
     * 账单类型（1.提供方账单2.接入方账单）
     */
    private int billType;
    /**
     * 对账结果描述
     */
    private String resultDes;
    /**
     * 数据提供方管理员
     */
    private String providerAdmin;
    /**
     * 管理员联系电话
     */
    private String providerTel;
    /**
     * 数据来源名称
     */
    private String dataSourceName;
    /**
     * 数据来源类型（1.数据包2.数据文件3.数据库）
     */
    private int dataSourceType;
    /******************非部标要求字段******************/
    /**
     * 提供方系统IP地址
     */
    private String serviceIp;
    /**
     * 提供方系统端口号
     */
    private int servicePort;
    /**
     * 系统代码
     */
    private String sysCode;
    /**
     * 数据输出目标 / 数据去向
     */
    private String dataDirection;
    /**
     * 数据Id,用来关联账单
     */
    private String dataId;
    /**
     * ETL 任务时间
     */
    private long inceptDataTime;
    /**
     * ETL读的数据包的ID
     */
    private String inceptDataId;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 1.7本地仓或2.2托管
     */
    private String isLocal;
    /**
     * 任务ID
     */
    private String jobId;
    /**
     * 任务名
     */
    private String jobName;
    /**
     * 任务执行batchId
     */
    private String batchId;
    /**
     * 表英文名
     */
    private String tableNameEn;
    /**
     * 接入单位或城市名称，用来分类统计账单
     */
    private String local;
    /**
     * 行政区划编码
     */
    private String codeAllocationOrganization;
}
