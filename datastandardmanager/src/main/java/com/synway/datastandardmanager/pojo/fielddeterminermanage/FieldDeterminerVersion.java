package com.synway.datastandardmanager.pojo.fielddeterminermanage;

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
 * @author obito
 * @version 1.0
 * @date
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FieldDeterminerVersion implements Serializable {

    private static final long serialVersionUID = 4223445263445348123L;

    /**
     * 表版本主键
     */
    private String fieldDeterminerVersion;

    /**
     *限定词中文名称
     */
    @NotNull(message = "【限定词中文名称】不能为空")
    private String dChineseName;

    /**
     *限定词标识符
     */
    private String dName;

    /**
     *  修订内容
     */
    @NotNull(message = "【修订内容】不能为空")
    private String memo="";

    /**
     * 小版本号
     * 版本发布日期，日期的表示采用YYYYMMDD的格式
     */
    @NotNull(message = "【小版本号】不能为空")
    private Integer version;

    /**
     * 大版本号
     */
    @NotNull(message = "【大版本号】不能为空")
    private String versions;

    /**
     * 更新日期
     */
    @NotNull(message = "【更新日期】不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 更新人
     */
    @NotNull(message = "【更新人】不能为空")
    @Size(max=20,message = "【更新人】长度不能超过20")
    private String author;
}
