package com.synway.datastandardmanager.pojo.versionManage;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * 版本管理中的数据集
 * @author obito
 * @version 1.0
 * @date
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ObjectVersionVo implements Serializable {
    private static final long serialVersionUID = 42416254413234165L;
    private int num;

    /**
     *  基准表ID
     */
    private String objectId;

    /**
     *  数据集名称
     */
    @NotNull
    private String objectName;

    /**
     *  数据集英文名
     */
    @NotNull
    @Size(max=80,message = "[数据集英文名]长度不能超过80")
    private String tableName;

    /**
     *  小版本号
     */
    @NotNull
    private Integer version;

    /**
     *  大版本号
     */
    @NotNull
    private String versions;

    /**
     * 修订时间 采用YYYYMMDD HH:mm:ss的格式
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    @NotNull
    private Date updateTime;

    /**
     * 修订内容
     */
    @NotNull
    private String author;

    /**
     * 修订内容
     */
    @NotNull
    private String memo;
}
