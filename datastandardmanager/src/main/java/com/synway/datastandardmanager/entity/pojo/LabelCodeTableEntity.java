package com.synway.datastandardmanager.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("LABEL_CODE_TABLE")
public class LabelCodeTableEntity {

    // 标签代号
    @TableField("LABEL_LEVEL")
    private String labelLevel;

    // 标签名称
    @TableField("LABEL_NAME")
    private String labelName;

}
