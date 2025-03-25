package com.synway.datastandardmanager.pojo.dataDefinitionManagement;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


/**
 * 数据定义pojo
 * @author obito
 * @version 1.0
 * @date 2022/01/11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataDefinitionPojo implements Serializable {

    private static final long serialVersionUID = 4245646487456789972L;

    /**
     * 序号
     */
    private Integer recno;

    /**
     * 数据集id
     */
    private String objectId;

    /**
     * 数据集中文名
     */
    private String objectChineseName;

    /**
     * 数据集英文名
     */
    private String objectEnglishName;

    /**
     * 数据集编码
     */
    private String tableId;

    /**
     * 数据来源协议
     */
    private String sourceId;

    /**
     * 数据来源分类
     */
    private String dataSourceClassify;

    /**
     * 发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    private Date modDate;
}
