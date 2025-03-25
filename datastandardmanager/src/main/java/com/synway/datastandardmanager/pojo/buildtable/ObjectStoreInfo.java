package com.synway.datastandardmanager.pojo.buildtable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.List;

/**
 * 表 OBJECT_STORE_INFO 的相关字段信息
 * @author wangdongwei
 * @date 2021/1/27 15:57
 */
@Data
public class ObjectStoreInfo implements Serializable {

    /**
     * 表存储信息ID
     */
    private String tableInfoId;
    /**
     *标准数据项集编码
     */
    private String tableId;
    /**
     *表英文名称
     */
    private String tableName;
    /**
     *数据项集中文名称
     */
    private String objectName;
    /**
     *平台类型 1：odps  2：hc  3：hp  4：hbase
     * 5：hive  6: ES  7: clickhouse  8: libra 9: TRS
     */
    private int storeType;
    /**
     * 平台类型中文名称
     */
    private String storeTypeCh;
    /**
     *表空间名称
     */
    private String projectName;
    /**
     *表空间中文名称
     */
    private String projectNameCh="";
    /**
     *存储描述信息
     */
    private String memo;
    /**
     * 创建者
     */
    private String creater;
    /**
     *创建者代码
     */
    private String createrId;
    /**
     *表创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @DateTimeFormat(pattern ="yyyy-MM-dd")
    private String tableCreateTime;
    /**
     *表结构修改时间
     */
    private String tableModTime;
    /**
     * 是否入库  1：入库程序读取并执行入库操作
     * 0：入库程序不进行入库操作
     */
    private int importFlag;

    /**
     * 服务默认查询标识
     * 0：服务非默认查询对象表
     * 1：服务默认查询对象表
     * 默认值：0
     */
    private Integer searchFlag = 0;

    /**
     *是否为分区表 0:是 1:不是
     */
    private Integer isPartition;

    /**
     *是否实时表 0:静态表 1:实时表(默认)
     */
    private Integer isActiveTable = 1;

    /**
     * 数据源id
     */
    private String dataId;

    /**
     *生命周期 0:永久/非分区 其它：生命周期天数
     */
    private Integer lifeCycle;
    private String lifeCycleStr;

    /**
     * 分区类型
     */
    private Integer partitionType;

    //分区数(KAFKA创建主题时配置的分区数值；datahub平台代表通道数)
    private Integer partitionCount;

    /**
     * 建表信息管理页面需要展示的字段信息
     */
    //标准英文名
    private String standTableName;
    //数据中心名称
    private String centerName;
    private String centerId;
    //存储数据源(数据源名称)
    private String resName;
    // 数据源id
    private String resId;
    //注册资源数
    private Integer registerCount;

    //数据组织id
    private String organizationClassificationId;
    //数据组织中文
    private String organizationClassificationCh;

    //表字段信息
    private List<ObjectStoreFieldInfo> objectStoreFieldInfos;

    // 真实表名（物理表名）
    private String realTableName;


}
