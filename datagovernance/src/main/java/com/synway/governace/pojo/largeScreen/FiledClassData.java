package com.synway.governace.pojo.largeScreen;

import java.io.Serializable;

/**
 * 字段分类的页面展示信息
 * @author wangdongwei
 * @date 2021/4/25 13:50
 */
public class FiledClassData implements Serializable {
    /**
     * 字段分类的id值
     */
    private String fieldClassId;
    /**
     * 字段分类的中文名
     */
    private String fieldClassCh;
    /**
     * 字段分类被使用的表数据量
     */
    private long tableCount;

    public String getFieldClassId() {
        return fieldClassId;
    }

    public void setFieldClassId(String fieldClassId) {
        this.fieldClassId = fieldClassId;
    }

    public String getFieldClassCh() {
        return fieldClassCh;
    }

    public void setFieldClassCh(String fieldClassCh) {
        this.fieldClassCh = fieldClassCh;
    }

    public long getTableCount() {
        return tableCount;
    }

    public void setTableCount(long tableCount) {
        this.tableCount = tableCount;
    }
}
