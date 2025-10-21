package com.synway.datastandardmanager.entity.vo.warehouse;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据源对象
 */
@Data
public class DataResource implements Serializable {

    /**
     * 数据源id,唯一
     */
    private String resId;

    /**
     *  数据库名称,唯一
     */
    private String resName;

    /**
     * 数据库类型
     */
    private String resType;

    /**
     * 数据库是否连通
     */
    private int status;

    /**
     * 数据库连接参数信息
     */
    private String connectInfo;

    /**
     * 数据中心id
     */
    private String centerId;

    /**
     * 数据中心名称
     */
    private String centerName;

    /**
     * 0:全部（默认）1：非本地 2：本地
     */
    private int  isLocal;

    /**
     * 总存储空间
     */
    private String totalSpace;

    /**
     * 已用空间
     */
    private String usedSpace;

    /**
     * 审批 0:不需审批 1:审批中 2：审批通过 3、审批不通过
     */
    private int approved;

    /**
     *  版本号
     */
    private String version;

    /**
     * 管理人
     */
    private String manager;

    /**
     * 管理单位
     */
    private String manageUnit;

    /**
     * 创建人
     */
    private String creater;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date gmtCreate;

    /**
     * 最近更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date gmtModified;

    /**
     * 数据源用途,，0：其他(默认)，1：数据处理用，2：质检用，3：数据对账用
     */
    private int  purpose = 0;

    /**
     * 项目空间，建表用
     */
    private String projectName;

    /**
     * 跨系统数据源比对ID
     */
    private String checkId;

}
