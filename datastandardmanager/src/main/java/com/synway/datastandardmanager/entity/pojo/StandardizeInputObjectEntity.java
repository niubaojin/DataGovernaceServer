package com.synway.datastandardmanager.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("STANDARDIZE_INPUTOBJECT")
public class StandardizeInputObjectEntity {

    //对象唯一标识
    @TableField("OBJ_GUID")
    private String objGuid;

    //对象唯一标识
    @TableField("IOBJ_GUID")
    private String iobjGuid;

    //对象类型
    @TableField("IOBJ_TYPE")
    private Integer iobjType;

    //需要处理该协议的模块
    @TableField("IOBJ_MODULE")
    private String iobjModule;

    //备注
    @TableField("IOBJ_MEMO")
    private String iobjMemo;

    //状态。0：禁用；1：启用。
    @TableField("IOBJ_STATUS")
    private Integer iobjStatus;

    //是否另存。0：否；1：是。
    @TableField("IOBJ_SAVEAS")
    private Integer iobjSaveas;

    //编码 0:UTF8 1:GB2312 2:GBK
    @TableField("IOBJ_ENCODE")
    private Integer iobjEnCode;

    //各厂商专有协议，1：普天；2：汇智；3：三所；4：烽火；默认为0表示适用于所有厂商
    @TableField("IOBJ_SOURCE")
    private Integer iobjSource;

    //是否全中标。0：否；1：是。
    @TableField("IOBJ_ALLMATCH")
    private Integer iobjAllMatch;

}
