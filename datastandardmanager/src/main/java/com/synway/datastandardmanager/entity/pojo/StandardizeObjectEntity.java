package com.synway.datastandardmanager.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 对象（协议）表
 *
 * @author nbj
 * @date 2025年6月25日16:08:54
 */
@Data
@TableName("STANDARDIZE_OBJECT")
public class StandardizeObjectEntity {

    //系统代号
    @TableField("SYS_ID")
    private String sysId;

    //对象唯一标识
    @TableField("OBJ_GUID")
    private String objGuid;

    //对象英文名
    @TableField("OBJ_ENGNAME")
    private String objEngName;

    //对象中文名
    @TableField("OBJ_CHINAME")
    private String objChiName;

    //备注
    @TableField("OBJ_MEMO")
    private String objMemo;

    //各厂商专有协议，1：普天；2：汇智；3：三所；4：烽火；默认为0表示适用于所有厂商
    @TableField("SYS_SOURCE")
    private Integer sysSource;

    //数据仓库的data_id数据，用于跳转到数据接入
    @TableField("DATA_ID")
    private String dataId;

    //数据仓库的table_id数据，用于跳转到数据接入
    @TableField("TABLE_ID")
    private String tableId;

    //数据仓库的center_id数据，用于跳转到数据接入
    @TableField("CENTER_ID")
    private String centerId;

    //数据的插入时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    @TableField("INSERT_DATE")
    private Date insertDate;

}
