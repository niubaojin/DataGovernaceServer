package com.synway.datastandardmanager.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 输出对象（协议）表
 *
 * @author nbj
 * @date 2025年6月25日16:32:31
 */
@Data
@TableName("STANDARDIZE_OUTPUTOBJECT")
public class StandardizeOutputObjectEntity {

    //对象唯一标识
    @TableField("OBJ_GUID")
    private String objGuid;

    //输入对象唯一标识
    @TableField("OOBJ_GUID")
    private String oobjGuid;

    //对象英文扩展名XML
    @TableField("OOBJ_RESERVENAME_XML")
    private String oobjReserveNameXml;

    //对象类型
    @TableField("OOBJ_TYPE")
    private Integer oobjType;

    //备注
    @TableField("OOBJ_MEMO")
    private String oobjMemo;

    //状态，0：禁用；1：启用
    @TableField("OOBJ_STATUS")
    private Integer oobjStatus;

    //各厂商专有协议，1：普天；2：汇智；3：三所；4：烽火；默认为0表示适用于所有厂商
    @TableField("OOBJ_SOURCE")
    private Integer oobjSource;

}
