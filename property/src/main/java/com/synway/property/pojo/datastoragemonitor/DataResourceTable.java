package com.synway.property.pojo.datastoragemonitor;

import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;

/**
 * @author majia
 * @version 1.0
 * @date 2020/10/30 16:11
 */

@Data
public class DataResourceTable implements Serializable {

    //表的项目名
    private String projectName;

    //表名
    private String tableName;
    private String tableNameCh;

    //表的生命周期
    private String tableLife;

    //表的类型
    private String tableType;

    //是否分区表
    private String isPartitionTable;

    //更新批次
    private String updatePreiod;

    //资源服务平台组织机构代码
    private String belongSystemCode;

    //分区日期
    private String partionDate;

    //分区size
    private String partionSize;

    //总size
    private String totalSize;

    //分区量
    private String partionCount;

    //总的量
    private String totalCount;

    //更新周期
    private String updateDate;

    // 日均数据量
    private BigInteger averageDailyVolume;

    // 日均数据大小
    private BigInteger averageDailySize;

    // 频次(前一个月次数/总次数)
    private String frequency;

    // 活表类型  LIVING/NONLIVING  活表/死表
    private String liveType;

    // 表创建时间
    private String tableCreatedTime;

    // 最后修改时间
    private String lastDataModifiedTime;

    //月使用次数
    private String tableUseCountOfmonth;

//    //用户id
//    private int userId;
//    //关联权限表id
//    private String id;
//    // 用户名
//    private String userName;

    // 分区数
    private String partitionNum;
    // 数据源id
    private String resourceId;

    //注册  1:未注册， 2：已注册（仓库新定规则，还不确定）
    private String isRegistered;

    //审批  1:未审批， 2：审批中  3：审批通过  4：审批失败
    private String isApproved;

    // 数据源名称
    private String resName;

    // 插入时间
    private String insertDataTime;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DataResourceTable)) {
            return false;
        }
        DataResourceTable that = (DataResourceTable) o;
        return Objects.equals(getProjectName() !=null ? getProjectName().toLowerCase() : getProjectName(),
                            that.getProjectName() !=null ? that.getProjectName().toLowerCase() : that.getProjectName()) &&
                Objects.equals(getTableName() != null ? getTableName().toLowerCase() : getTableName(),
                            that.getTableName() != null ? that.getTableName().toLowerCase() : that.getTableName()) &&
                Objects.equals(getFrequency(), that.getFrequency()) &&
                Objects.equals(getTableUseCountOfmonth(), that.getTableUseCountOfmonth()) &&
                Objects.equals(getTableType() != null ? getTableType().toLowerCase() : getTableType(),
                            that.getTableType() != null ? that.getTableType().toLowerCase() : that.getTableType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProjectName() !=null ? getProjectName().toLowerCase() : getProjectName(),
                            getTableName() != null ? getTableName().toLowerCase() : getTableName(),
                            getTableType() != null ? getTableType().toLowerCase() : getTableType());
    }

}
