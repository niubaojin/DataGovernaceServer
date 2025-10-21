package com.synway.datastandardmanager.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 字典字段表
 *
 * @author nbj
 * @date 2025年8月20日14:17:37
 */
@Data
@TableName("STANDARDIZE_ORIGINAL_D_F")
public class StandardizeOriginalDFEntity {

    //原始字典表id
    @TableField("GROUP_ID")
    private String groupId;

    //字典代码项id
    @TableField("ID")
    private String id;

    //字段序号值
    @TableField("RECNO")
    private Integer recno;

    //代码名称
    @TableField("CODE_VALTEXT")
    private String codeValText;

    //代码值
    @TableField("CODE_VALVALUE")
    private String codeValValue;

    //对应标准代码名称
    @TableField("STANDARD_CODE_VALTEXT")
    private String standardCodeValText;

    //对应标准代码值
    @TableField("STANDARD_CODE_VALVALUE")
    private String standardCodeValValue;

    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("CREATE_TIME")
    private Date createTime;

    //更新时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("UPDATE_TIME")
    private Date updateTime;

}
