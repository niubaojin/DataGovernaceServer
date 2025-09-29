package com.synway.dataoperations.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 历史数据监测-应用类型
 */
@Data
@TableName("DO_APP_TYPE")
public class DoAppTypeEntity {
    //数据表名
    @TableField("TABLENAME")
    private String tableName;

    //数据协议代码
    @TableField("TABLEID")
    private String tableId;

    //数据中文名称
    @TableField("TABLENAME_CH")
    private String tableNameCh;

    //数据来源分类
    @TableField("DATA_TYPE")
    private String dataType;

    //运营商
    @TableField("OPERATOR_NET")
    private String operatorNet;

    //网络制式
    @TableField("DATA_SOURCE")
    private String dataSource;

    //城市区号
    @TableField("CITYCODE")
    private String cityCode;

    //协议类型
    @TableField("PROTOCOL")
    private String protocol;

    //协议名称
    @TableField("VALTEXT")
    private String valText;

    //记录数
    @TableField("RECORDS_ALL")
    private long recordsAll;

    //数据日期
    @TableField("DT")
    private String dt;
}
