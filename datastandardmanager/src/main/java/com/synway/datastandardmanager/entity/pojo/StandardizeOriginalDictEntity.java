package com.synway.datastandardmanager.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 字典表
 */
@Data
@TableName("STANDARDIZE_ORIGINAL_DICT")
public class StandardizeOriginalDictEntity {

    //原始字典表id
    @TableId(type = IdType.NONE)
    @TableField("ID")
    private String id;

    //字典名称
    @TableField("DICTIONARY_NAME")
    private String dictionaryName;

    //厂商信息id
    @TableField("FACTURER_ID")
    private String facturerId;

    //厂商信息
    @TableField("FACTURER")
    private String facturer;

    //创建日期
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @TableField("CREATE_DATE")
    private Date createDate;

    //创建人
    @TableField("CREATOR")
    private String creator;

    //对应标准字典
    @TableField("STANDARD_DICTIONARY")
    private String standardDictionary;

    //标准字典编码
    @TableField("STANDARD_DICTIONARY_ID")
    private String standardDictionaryId;

    //备注说明
    @TableField("MEMO")
    private String memo;

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

    /**
     * 原始字典项
     */
    @TableField(exist = false)
    private List<StandardizeOriginalDFEntity> originalDictionaryFieldList;

}
