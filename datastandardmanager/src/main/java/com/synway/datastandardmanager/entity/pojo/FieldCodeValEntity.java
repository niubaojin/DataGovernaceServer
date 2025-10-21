package com.synway.datastandardmanager.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 元素代码集取值表
 * @author nbj
 * @date 2025年5月12日16:06:46
 */
@Data
@TableName("SYNLTE.FIELDCODEVAL")
public class FieldCodeValEntity {
    //主键ID
    @TableField("CODEVALID")
    private String codeValId;

    //元素代码集ID
    @TableField("CODEID")
    private String codeId;

    //元素代码值名称
    @TableField("VALTEXT")
    private String valText;

    //备注
    @TableField("MEMO")
    private String memo;

    //元素集的代码值
    @TableField("VALVALUE")
    private String valValue;

    //元素代码值顺序
    @TableField("SORTINDEX")
    private Integer sortIndex;

    //元素代码值名称英文简写
    @TableField("VALTEXTTITLE")
    private String valTextTitle;

    //ID
    @TableField("ID")
    private Integer id;

    //字典数据首次创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("CREATETIME")
    private Date createTime;

    //默认0 ，0:未删除  1：已删除；当为1时，表示该值已不可使用
    @TableField("DELETED")
    private Integer deleted;
}
