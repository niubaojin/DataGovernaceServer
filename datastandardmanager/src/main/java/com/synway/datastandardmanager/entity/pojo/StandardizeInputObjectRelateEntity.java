package com.synway.datastandardmanager.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("STANDARDIZE_INPUTOBJECTRELATE")
public class StandardizeInputObjectRelateEntity {

    //与【输入协议表】主键对应
    @TableField("IOBJ_GUID")
    private String iobjGuid;

    //与【输出协议表】主键对应
    @TableField("OOBJ_GUID")
    private String oobjGuid;

    //id
    @TableField("IOR_GUID")
    private String iorGuid;

    //memo
    @TableField("IOR_MEMO")
    private String iorMemo;

    //0：禁用；1：启用
    @TableField("IOR_STATUS")
    private Integer iorStatus;

    //各厂商专有协议，1：普天；2：汇智；3：三所；4：烽火；默认为0表示适用于所有厂商
    @TableField("IOR_SOURCE")
    private Integer iorSource;

}
