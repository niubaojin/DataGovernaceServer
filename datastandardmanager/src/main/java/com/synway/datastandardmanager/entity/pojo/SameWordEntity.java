package com.synway.datastandardmanager.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 数据语义表
 * @author nbj
 * @date 2025年5月12日20:12:30
 */
@Data
@TableName("SYNLTE.SAMEWORD")
public class SameWordEntity {

    //主键ID
    @TableField("SAMEID")
    private String sameId;

    //语义英文名称
    @TableField("WORD")
    private String word;

    //语义中文名称
    @TableField("WORDNAME")
    private String wordname;

    //备注
    @TableField("MEMO")
    private String memo;

    //DELETED
    @TableField("DELETED")
    private Integer deleted;

    //关联主体
    @TableField("ELEMNTOBJECT")
    private String elementObject;

    //主体类型翻译 1.人员 2.物 3.组织 4.地 5.事 6.时间 7.信息
    @TableField(exist = false)
    private String elementObjectVo;

}
