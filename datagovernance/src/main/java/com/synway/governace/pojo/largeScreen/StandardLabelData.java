package com.synway.governace.pojo.largeScreen;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * 1：原始库日接入情况  按照二级分类（去除视频数据）
 * 2：资源库资产情况
 * 3：主题库资产
 * @author wangdongwei
 * @date 2021/4/25 14:29
 */
public class StandardLabelData implements Serializable {

    /**
     * 分类的名称
     */
    private String name;

    private long standTableCount;

    /**
     * 该分类下有多少张表
     */
    private long tableCount;

    /**
     * 该分类下所有表的昨日新增数据量
     * 单位 是条
     */
    private BigInteger tableDataVolume;

    /**
     * 页面上展示的数据条数  12亿条  这种
     */
    private String tableDataVolumeStr;

    public long getStandTableCount() {
        return standTableCount;
    }

    public void setStandTableCount(long standTableCount) {
        this.standTableCount = standTableCount;
    }

    public String getTableDataVolumeStr() {
        return tableDataVolumeStr;
    }

    public void setTableDataVolumeStr(String tableDataVolumeStr) {
        this.tableDataVolumeStr = tableDataVolumeStr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTableCount() {
        return tableCount;
    }

    public void setTableCount(long tableCount) {
        this.tableCount = tableCount;
    }

    public BigInteger getTableDataVolume() {
        return tableDataVolume;
    }

    public void setTableDataVolume(BigInteger tableDataVolume) {
        this.tableDataVolume = tableDataVolume;
    }
}
