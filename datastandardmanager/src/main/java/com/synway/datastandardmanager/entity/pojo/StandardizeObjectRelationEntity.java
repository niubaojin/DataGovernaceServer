package com.synway.datastandardmanager.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@TableName("STANDARDIZE_OBJECT_RELATION")
public class StandardizeObjectRelationEntity {

    //表id
    @TableField("ID")
    private String id;

    //数据集 id
    @TableField("OBJECT_ID")
    private Integer objectId;

    //数据名称
    @TableField("OBJECT_NAME")
    private String objectName;

    //标准编码
    @TableField("TABLE_ID")
    private String tableId;

    //当object_id原始汇聚，是：-1，否：关联的原始汇聚标准的object_id
    @TableField("PARENT_ID")
    private String parentId;

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
