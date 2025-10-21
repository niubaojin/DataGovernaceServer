package com.synway.datastandardmanager.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 元素代码集定义表
 * @author nbj
 * @date 2025年5月12日15:55:55
 */
@Data
@TableName("SYNLTE.FIELDCODE")
public class FieldCodeEntity {

    //序号
    @TableField(exist = false)
    private Integer serialNum;

    //主键ID
    @TableField("CODEID")
    private String codeId;

    //代码英文名称
    @TableField("CODENAME")
    private String codeName;

    //代码中文名称
    @TableField("CODETEXT")
    private String codeText;

    //备注
    @TableField("MEMO")
    private String memo;

    //代码集父ID
    @TableField("PARCODEID")
    private String parCodeId;

    //是否已删除
    @TableField("DELETED")
    private Integer deleted;

    //对应的翻译类型
    @TableField("TRANSRULE")
    private Integer transRule;

    //引用的代码集ID
    @TableField("BROTHERCODEID")
    private String brotherCodeId;

    //字典数据首次创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("CREATETIME")
    private Date createTime;

    //0：公司标准；1：公安部标；2：技侦部标；3：网安部标；99：伪标准
    @TableField(exist = false)
    private Integer codeStandard;

    //元素代码集来源标准规范，描述当前元素代码集出自哪份标准规范文件
    @TableField(exist = false)
    private String fromFile;

    //元素代码值名称
    @TableField(exist = false)
    private String valText;

    //元素集的代码值
    @TableField(exist = false)
    private String valValue;
}
