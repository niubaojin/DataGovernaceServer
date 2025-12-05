package com.synway.datastandardmanager.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 所有的代码信息
 * @author nbj
 * @date 2025年5月13日14:01:44
 */
@Data
@TableName("DSM_ALL_CODE_DATA")
public class DsmAllCodeDataEntity {
    //每个码值的唯一uuid
    @TableField("ID")
    private String id;

    //每种码值的id值
    @TableField("CODE_ID")
    private String codeId;

    //
    @TableField("CODE_VALUE")
    private String codeValue;

    //
    @TableField("CODE_TEXT")
    private String codeText;

    //注释
    @TableField("MEMO")
    private String memo;
}
