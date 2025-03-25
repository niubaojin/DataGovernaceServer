package com.synway.datastandardmanager.pojo.originalDictionary;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author obito
 * @ClassName originalDictionaryPojo
 * @description 外部字典管理基本信息pojo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OriginalDictionaryPojo implements Serializable {
    private static final long serialVersionUID = 4331423458342L;

    /**
     * 原始字典表id
     */
    private String id;

    /**
     * 字典名称
     */
    private String dictionaryName;

    /**
     * 厂商信息id
     */
    private String facturerId;

    /**
     * 厂商信息
     */
    private String facturer;

    /**
     * 创建日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDate;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 对应标准字典
     */
    private String standardDictionary;

    /**
     * 标准字典编码
     */
    private String standardDictionaryId;

    /**
     * 备注说明
     */
    private String memo;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 原始字典项
     */
    private List<OriginalDictionaryFieldPojo> originalDictionaryFieldList;
}
