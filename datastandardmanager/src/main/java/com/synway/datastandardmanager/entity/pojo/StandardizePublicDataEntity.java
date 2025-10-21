package com.synway.datastandardmanager.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 公共字段分组信息
 * @author nbj
 * @date 2025年5月12日20:18:53
 */
@Data
@TableName("SYNLTE.STANDARDIZE_PUBLIC_DATA")
public class StandardizePublicDataEntity {
    //分类id
    @TableField("ID")
    private String id;

    //分组名称
    @TableField("GROUP_NAME")
    private String groupName;

    //创建人
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    @TableField("CREATOR")
    private String creator;

    //备注
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
     * 公共数据项
     */
    @TableField(exist = false)
    private List<StandardizePublicDataFieldEntity> publicDataFieldList;

}
