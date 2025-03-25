package com.synway.datastandardmanager.pojo.dataJoinSkip;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 数据跳转参数实体类
 * @author obito
 * @version 1.0
 * @date 2021/7/27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Deprecated
public class DataSkipParameterPojo implements Serializable {
    private static final long serialVersionUID = 4546874613464867612L;

    /**
     * 表objectId
     */
    @NotNull(message = "表tableId不能为空")
    private String tableId;

    /**
     * 传参需要的dataId
     */
    private String dataId;

    /**
     * 数据中心Id
     */
    private String centerId;

    /**
     * 源Id
     */
    private String sourceId;

    /**
     * 来源系统代码
     */
    private String inSourceProtocol;

    /**
     * 来源厂商
     */
    private String inSourceFirmCode;

    /**
     * 资源标识
     */
    private String outTableName;

    /**
     * 来源系统名
     */
    private String outSysChiName;

    /**
     * 来源系统代码
     */
    private String outSourceSystem;

    /**
     * 标题
     */
    private String title;

    /**
     * 数据名称
     */
    private String dataName;

    /**
     * 任务大类
     */
    private String firstLevel;

    /**
     * 任务小类
     */
    private String secondLevel;

    /**
     * 物理表名
     */
    private String tableNameEn;

    /**
     * 是否新建一个接入任务
     */
    private Boolean choose;

}
