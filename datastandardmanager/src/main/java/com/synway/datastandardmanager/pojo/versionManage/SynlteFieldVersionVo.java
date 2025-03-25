package com.synway.datastandardmanager.pojo.versionManage;

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
public class SynlteFieldVersionVo implements Serializable {
    private static final long serialVersionUID = 412643614164146112L;
    private Integer num;

    /**
     *  数据元中文名称
     */
    @NotNull
    private String fieldChineseName;

    /**
     *  数据元标识符
     */
    @NotNull
    private String fieldId;

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
