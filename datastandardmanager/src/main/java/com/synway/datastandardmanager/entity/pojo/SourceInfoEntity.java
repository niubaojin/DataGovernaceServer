package com.synway.datastandardmanager.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.synway.datastandardmanager.util.UUIDUtil;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 来源信息表
 */
@Data
@TableName("SOURCEINFO")
public class SourceInfoEntity {

    //id
    @TableField("ID")
    private String id;

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

    //来源数据协议
    @TableField("SOURCEPROTOCOL")
    private String sourceProtocol;

    //来源数据名(表中文名)
    @TableField("TABLENAME")
    private String tableName;

    //来源系统代码
    @TableField("SOURCESYSTEM")
    private String sourceSystem;

    //来源厂商
    @TableField("SOURCEFIRM")
    private String sourceFirm;

    //来源数据名(表中文名)
    @TableField("DATANAME")
    private String dataName;

    //数据源
    @TableField("DATA_ID")
    private String dataId;

    //项目空间
    @TableField("PROJECT_NAME")
    private String projectName;

    //数据中心名称
    @TableField("CENTER_NAME")
    private String centerName;

    //数据中心id
    @TableField("CENTER_ID")
    private String centerId;

    public SourceInfoEntity() {
    }

    public SourceInfoEntity(String sourceProtocol, String tableName, String sourceSystem, String sourceFirm) {
        Date now = new Date();
        this.sourceProtocol = sourceProtocol;
        this.tableName = tableName;
        this.sourceSystem = sourceSystem;
        this.sourceFirm = sourceFirm;
        this.createTime = now;
        this.updateTime = now;
        this.id = UUIDUtil.getUUID();
    }

    public SourceInfoEntity(String sourceProtocol, String tableName, String sourceSystem, String sourceFirm, String dataName,
                            String dataId, String projectName, String centerName, String centerId) {
        Date now = new Date();
        this.sourceProtocol = sourceProtocol;
        this.tableName = tableName;
        this.sourceSystem = sourceSystem;
        this.sourceFirm = sourceFirm;
        this.dataName = dataName;
        this.dataId = dataId;
        this.projectName = projectName;
        this.centerName = centerName;
        this.centerId = centerId;
        this.createTime = now;
        this.updateTime = now;
        this.id = UUIDUtil.getUUID();
    }

}
