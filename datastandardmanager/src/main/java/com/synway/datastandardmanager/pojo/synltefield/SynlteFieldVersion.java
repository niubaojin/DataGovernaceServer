package com.synway.datastandardmanager.pojo.synltefield;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
public class SynlteFieldVersion implements Serializable {
    private static final long serialVersionUID = 4269867435168741168L;

    /**
     * 数据元版本管理主键
     */
    private String fieldIdVersion;

    /**
     * 数据元标识符
     */
    @NotBlank(message="[内部标识符]不能为空")
    @Size(max=40,message = "[内部标识符]长度不能超过40")
    @Pattern(regexp = "^[a-zA-Z0-9]+",message = "[数据库字段]只能是字母和数字组成")
    private String fieldId;

    /**
     * 数据库字段名称
     */
    @NotBlank(message="[数据库字段]不能为空")
    @Size(max=100,message = "[数据库字段]长度不能超过100")
    @Pattern(regexp = "^[a-zA-Z0-9_]+",message = "[数据库字段]只能是字母，数字和下划线组成")
    private String columnName;

    /**
     *  修订内容
     */
    @NotNull
    private String memo;

    /**
     * 小版本 版本发布日期，日期的表示采用YYYYMMDD的格式
     */
    @NotNull
    private Integer version;

    /**
     * 大版本
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
