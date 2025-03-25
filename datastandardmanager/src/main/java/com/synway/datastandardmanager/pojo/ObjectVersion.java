package com.synway.datastandardmanager.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author obito
 * @version 1.0
 * @date
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ObjectVersion implements Serializable {
    private static final long serialVersionUID = 4213446515124711246L;

    /**
     * 标准表版本主键
     */
    @NotNull
    private String objectVersion;

    /**
     * 基准表Id
     */
    @NotNull
    private String objectId;

    /**
     * 基准表名 可为空
     */
    private String tableName;

    /**
     * 修订内容
     */
    @NotNull
    private String memo;

    /**
     * 小版本号 版本发布日期，日期的表示采用YYYYMMDD的格式
     */
    @NotNull
    private Integer version;

    /**
     * 大版本号
     */
    @NotNull
    private String versions;

    /**
     * 更新日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    @NotNull
    private Date updateTime;

    /**
     * 更新人
     */
    @NotNull
    private String author;
}
