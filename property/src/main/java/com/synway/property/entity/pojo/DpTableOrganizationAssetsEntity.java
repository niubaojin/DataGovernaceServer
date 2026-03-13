package com.synway.property.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 表组织资产的相关数据
 * @author nbj
 * @date 2025年11月20日11:03:30
 */
@Data
@TableName("DP_TABLE_ORGANIZATION_ASSETS")
public class DpTableOrganizationAssetsEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    //表协议名
    @TableField("SJXJBM")
    private String sjxjbm;
    
    //表中文名
    @TableField("NAME")
    private String name;
    
    //表英文名
    @TableField("TABLE_NAME_EN")
    private String tableNameEn;
    
    //数据来源一级分类中文名
    @TableField("PRIMARY_DATASOURCE_CH")
    private String primaryDatasourceCh;
    
    //数据来源二级分类中文名
    @TableField("SECONDARY_DATASOURCE_CH")
    private String secondaryDatasourceCh;
    
    //数据组织一级分类中文名
    @TableField("PRIMARY_ORGANIZATION_CH")
    private String primaryOrganizationCh;
    
    //数据组织二级分类中文名
    @TableField("SECONDARY_ORGANIZATION_CH")
    private String secondaryOrganizationCh;
    
    //数据资源要素一级分类中文名
    @TableField("FACTOR_ONE_NAME")
    private String factorOneName;
    
    //数据资源要素二级分类中文名
    @TableField("FACTOR_TWO_NAME")
    private String factorTwoName;
    
    //数据资源要素细目分类代码名称
    @TableField("FACTOR_DETAILED_NAME")
    private String factorDetailedName;
    
    //数据资源属性分类代码名称
    @TableField("FACTOR_ATTRIBUTES_NAME")
    private String factorAttributesName;
    
    //表数据总行数 到昨天分区
    @TableField("TABLE_ALL_COUNT")
    private Long tableAllCount;
    
    //表数据总大小 到昨天分区 byte
    @TableField("TABLE_SIZE")
    private Long tableSize;
    
    //表的项目名
    @TableField("TABLE_PROJECT")
    private String tableProject;
    
    //表的生命周期
    @TableField("LIFE_CYCLE")
    private String lifeCycle;
    
    //表类型 odps/ads/'''''
    @TableField("TABLE_TYPE")
    private String tableType;
    
    //昨天分区的数据量
    @TableField("YESTERDAY_COUNT")
    private Long yesterdayCount;
    
    //是否为分区表  0:分区表  1:不是分区表  -1：不确定是否为分区表
    @TableField("PARTITION_MESSAGE")
    private String partitionMessage;
    
    //日均数据量
    @TableField("AVERAGE_DAILY_VOLUME")
    private Long averagDailyVolume;
    
    //有值率
    @TableField("VALUE_RATE")
    private Integer valueRate;
    
    //空值率
    @TableField("NULL_RATE")
    private Integer nullRate;
    
    //状态  正常/异常
    @TableField("TABLE_STATE")
    private String tableState;
    
    //全量/增量/
    @TableField("UPDATE_TYPE")
    private String updateType;

    //统计时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField("STATISTICS_TIME")
    private Date statisticsTime;
    
    //最近一个月使用次数/总使用次数
    @TableField("FREQUENCY")
    private String frequency;
    
    //NONLIVING：死表 LIVING：活表
    @TableField("LIVETYPE")
    private String livetype;
    
    //注册状态 -1:未注册 1:已注册
    @TableField("REGISTER_STATE")
    private String registerState = "-1";
    
    //object表中的objectid
    @TableField("OBJECT_ID")
    private String objectId;
    
    //日均数据大小
    @TableField("AVERAGE_DAILY_SIZE")
    private Long averageDailySize;
    
    //停用，启用状态，1为启用
    @TableField("OBJECT_STATE")
    private String obectState;
    
    //数据组织三级分类中文名
    @TableField("THREE_ORGANIZATION_CH")
    private String threeOrganizationCh;
    
    //数据资源标签
    @TableField("LABELS")
    private String labels;
    
    //更新批次
    @TableField("UPDATE_PERIOD")
    private String updatePeriod;
    
    //更新周期
    @TableField("UPDATE_DATE")
    private String updateDate;
    
    //资源服务平台组织机构代码
    @TableField("DATARESOURCE_CODE")
    private String dataResourceCode;
    
    //异常信息
    @TableField("EXCEPTION_MESSAGE")
    private String exceptionMessage;
    
    //生命周期审批状态
    @TableField("LIFE_CYCLE_STATUS")
    private String lifeCycleStatus;
    
    //最后修改时间
    @TableField("LASTDATAMODIFIEDTIME")
    private String lastDataModifiedTime;
    
    //表创建时间
    @TableField("TABLECREATEDTIME")
    private String tableCreatedTime;
    
    //分区数量
    @TableField("PARTITION_NUM")
    private String partitionNum;
    
    //最后访问时间
    @TableField("LASTVISITEDTIME")
    private String lastVisitedTime;
    
    //月使用量
    @TableField("TABLEUSECOUNTOFMONTH")
    private String tableUseCountOfMonth;
    
    //数据资源id，用于管理权限表
    @TableField("RESOURCEID")
    private String resourceId;
    
    //数据组织三级分类中文名
    @TableField("THREELEVEL_ORGANIZATION_CH")
    private String threelevelOrganizationCh;
    
    //数据组织分类id(最后一级)
    @TableField("ORGANIZATION_ID_LAST_LEVEL")
    private String organizationIdLastLevel;
    
    //数据来源分类id(最后一级)
    @TableField("DATASOURCE_LAST_LEVEL")
    private String dataSourceLastLevel;
    
    //数据源名称
    @TableField("RES_NAME")
    private String resName;
    
    //是否对标
    @TableField("ISSTANDARD")
    private String isStandard;
    
    //告警级别
    @TableField("ALARM_LEVEL")
    private String alarmLevel;
    
    //数据资源标签5
    @TableField("SJZYBQ5")
    private String sjzybq5;

}
