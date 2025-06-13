package com.synway.property.pojo.datastoragemonitor;

import com.google.common.base.Objects;
import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * 表组织资产的实体类
 *
 * @author 数据接入
 */
@Data
public class TableOrganizationData implements Serializable {

    //表协议名
    private String sjxjbm;
    // 表中文名
    private String name;
    // 表英文名
    private String tableNameEn;
    //数据来源一级分类中文名
    private String primaryDatasourceCh;
    // 数据来源二级分类中文名
    private String secondaryDatasourceCh;
    // 数据组织一级分类中文名
    private String primaryOrganizationCh;
    //数据组织二级分类中文名
    private String secondaryOrganizationCh;
    //数据组织三级分类中文名
    private String threeLevelOrganizationCh;
    //数据资源要素一级分类中文名
    private String factorOneName;
    //数据资源要素二级分类中文名
    private String factorTwoName;
    //数据资源要素细目分类代码名称
    private String factorDetailedName;
    //数据资源属性分类代码名称
    private String factorAttributesName;
    //表数据总行数 到昨天分区
    private BigInteger tableAllCount = new BigInteger("0");
    //表数据总大小 到昨天分区 byte
    private BigInteger tableSize = new BigInteger("0");
    //表的项目名
    private String tableProject = "";
    // 表的生命周期
    private String lifeCycle = "";
    //表类型 odps/ads/''
    private String tableType = "";
    // 昨天分区的数据量
    private BigInteger yesterdayCount = new BigInteger("0");
    // 是否为分区表 0:分区表  1:不是分区表  -1：不确定是否为分区表
    private String partitionMessage;
    // 日均数据量
    private BigInteger averageDailyVolume = new BigInteger("0");
    // 日均数据大小
    private BigInteger averageDailySize = new BigInteger("0");
    //  有值率
    private Double valueRate = 0.0;
    // 空值率
    private Double nullRate = 0.0;
    //  状态  正常/异常
    private String tableState = "正常";
    //异常信息
    private String exceptionMessage;
    //  全量/增量/""
    private String updateType = "";
    //   统计时间
    private Date statisticsTime;
    // 频次(前一个月次数/总次数)
    private String frequency;
    // 活表类型  LIVING/NONLIVING  活表/死表
    private String liveType;
    //注册状态
    private String registerState;
    //object表的objectid
    private String objectId;
    //object的停用启用
    private String objectState;
    //标签
//    private String labels;
    //生命周期更新状态
    private String lifeCycleStatus;
    //表创建时间
    private String tableCreatedTime;
    //更新周期
    private String updateDate;
    //更新批次
    private String updatePeriod;
    //资源标识
    private String belongSystemCode;
    //最后修改时间
    private String lastDataModifiedTime;
    //月使用次数
    private String tableUseCountOfmonth;

//    //用户id
//    private int userId;
//    //关联权限表id
//    private String id;
//    //用户名
//    private String userName;

    //分区数据
    private String partitionNum;
    //数据源id
    private String resourceId;

    // 数据组织id（最后一级）
    private String organizationIdLastLevel;
    // 数据来源id（最后一级）
    private String dataresourceIdLastLevel;

    // 数据源名称
    private String resName;
    // 是否对标
    private String isStandard;
    // 告警级别
    private String alarmLevel;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TableOrganizationData)) {
            return false;
        }
        TableOrganizationData that = (TableOrganizationData) o;
        return Objects.equal(getSjxjbm(), that.getSjxjbm()) &&
                Objects.equal(getTableNameEn(), that.getTableNameEn());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getSjxjbm(), getTableNameEn());
    }

}
